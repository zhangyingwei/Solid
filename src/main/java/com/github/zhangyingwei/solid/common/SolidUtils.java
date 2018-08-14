package com.github.zhangyingwei.solid.common;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.exception.SolidException;
import com.github.zhangyingwei.solid.exception.SolidParamNotFoundException;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.items.process.*;
import com.github.zhangyingwei.solid.items.text.TextBlock;
import com.github.zhangyingwei.solid.result.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class SolidUtils {

    /**
     *  get value from context
     * @param template
     * @param context
     * @return
     */
    private static SolidResult<Object> getObjectFromContext(String template, SolidContext context) {
        StringHandler templateHandler = new StringHandler(template);
        String fatherTemplate = templateHandler.getUntil('[');
        String childTemplate = templateHandler.getUntil(']');
        String[] objectKeys = fatherTemplate.split("\\.");
        Object tempValue = context.getParams();
        for (String objectKey : objectKeys) {
            tempValue = getValueFromObject(tempValue, objectKey);
            if (tempValue instanceof WowResult) {
                return (SolidResult) tempValue;
            }
        }
        SolidResult result = (SolidResult) tempValue;
        if (childTemplate != null && childTemplate.length() > 0) {
            if (result.getResult().getClass().isArray()) {
                Object[] objects = (Object[]) result.getResult();
                tempValue = new ObjectResult(objects[Integer.parseInt(childTemplate.trim())]);
            } else if (tempValue instanceof Collection) {
                Collection collection = (Collection) result.getResult();
                tempValue = new ObjectResult(collection.toArray()[Integer.parseInt(childTemplate.trim())]);
            } else {
                tempValue = getValueFromObject(result.getResult(), getFromPlaceholderOrNot(context, childTemplate).getResult().toString());
            }
        }
        return (SolidResult<Object>) tempValue;
    }

    /**
     * get value from object
     * @param object
     * @param key
     * @return
     */
    private static SolidResult getValueFromObject(Object object, String key) {
        if (object instanceof SolidResult) {
            object = ((SolidResult) object).getResult();
        }
        if (object instanceof Map) {
            if (((Map) object).containsKey(key)) {
                return new ObjectResult(((Map) object).get(key));
            } else {
                return new WowResult(key);
            }
        } else {
            return getFromObject(object, key);
        }
    }

    /**
     * get value from object
     * @param object
     * @param key
     * @return
     */
    private static SolidResult getFromObject(Object object, String key) {
        if (object.getClass().isArray()) {
            Object[] objects = (Object[]) object;
            if ("first".equals(key)) {
                return new ObjectResult(objects[0]);
            } else if ("last".equals(key)) {
                return new ObjectResult(objects[objects.length - 1]);
            }
        }
        Class<? extends Object> clazz = object.getClass();
        String methodName = "get".concat(
                key.substring(0,1).toUpperCase().concat(key.substring(1).toLowerCase())
        );
        try {
            Method method = clazz.getMethod(methodName);
            Object result = method.invoke(object);
            if (null == result) {
                return new WowResult(key);
            }
            return new StringResult<>(result.toString());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return new WowResult(key);
        }
    }

    public static Block routeProcessBlock(String template, SolidContext context) {
        List<String> commandItemList = Arrays.stream(template.trim().split(" ")).collect(Collectors.toList());
        commandItemList.remove(0);
        commandItemList.remove(commandItemList.size() - 1);
        String command = String.join(" ", commandItemList);

        if (command.startsWith(Constants.TAG_FOR)) {
            return new ForProcessBlock(template, context);
        } else if (command.startsWith(Constants.TAG_FOR_END)) {
            return new EndProcessBlock(template,context).setTag(Constants.TAG_FOR_END);
        } else if (command.startsWith(Constants.TAG_IF)) {
            return new IFProcessBlock(template, context);
        } else if (command.startsWith(Constants.TAG_ELSE_IF)) {
            return new ElsIFProcessBlock(template, context);
        } else if (command.startsWith(Constants.TAG_ELSE)) {
            return new ElseProcessBlock(template, context);
        } else if (command.startsWith(Constants.TAG_IF_END)) {
            return new EndProcessBlock(template, context).setTag(Constants.TAG_IF_END);
        } else if (command.startsWith(Constants.TAG_ASSIGN)) {
            return new AssignProcessBlock(template, context);
        } else if (command.startsWith(Constants.TAG_INCLUDE)) {
            return new IncludeProcessBlock(template, context);
        } else if (command.startsWith(Constants.TAG_RAW)) {
            return new RawProcessBlock(template, context);
        } else if (command.startsWith(Constants.TAG_RAW_END)) {
            return new EndProcessBlock(template, context).setTag(Constants.TAG_RAW_END);
        }
        return new TextBlock("not find process block , return a text block");
    }

    /**
     * 去掉多余的空格
     * @param content
     * @return
     */
    private static String removeExtraSpaces(String content) {
        while (content.indexOf("  ") >= 0) {
            content = content.replaceAll("  ", " ");
        }
        return content;
    }

    public static String formateAsNomal(String content) {
        return removeExtraSpaces(content.trim());
    }

    /**
     * 是否为占位符
     * @return
     */
    private static Boolean isPlaceholder(String text) {
        char first = text.charAt(0);
        char last = text.charAt(text.length() - 1);
        return (first != '"' && last != '"') && (first != '\'' && last != '\'');
    }

    /**
     * 1. is template is placeholder ?
     * 2. if true ,get result of placeholder from context
     * 3. if not , return template itself
     * @param context
     * @param template
     * @return
     */
    public static SolidResult getFromPlaceholderOrNot(SolidContext context,String template) {
        if (template == null || template.length() == 0) {
            return new StringResult("");
        }else if (isNum(template.trim())){
            return new NumResult(template);
        }else if (isPlaceholder(template)) {
            return getObjectFromContext(template, context);
        } else {
            return new StringResult(template.substring(1, template.length() - 1));
        }
    }

    /**
     * the text is num or not?
     * @return
     */
    public static Boolean isNum(String text) {
        Pattern pattern = Pattern.compile("[+\\-]?[0-9]*[.]?[0-9]*?");
        return pattern.matcher(text).matches();
    }

    /**
     * read from file
     *
     * @param filePath
     * @return
     */
    public static String readContentFromFile(String filePath, String encoding) {
        File file = new File(filePath);
        InputStreamReader reader = null;
        try {
            StringBuilder result = new StringBuilder();
            reader = new InputStreamReader(new FileInputStream(file), encoding);
            int flag;
            while ((flag = reader.read()) != -1) {
                result.append((char) flag);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    public static void main(String[] args) {
        System.out.println(readContentFromFile("src/main/resources/test.html",Constants.CHAR_SET_UTF_8));
    }

    /**
     * {% template %} => template
     * @param mark
     * @param leftMark
     * @param rightMark
     * @return
     */
    public static String subMarkToTemplate(String mark, String leftMark, String rightMark) {
        return mark.substring(leftMark.length(), (mark.length() - rightMark.length()));
    }

    /**
     * @param value
     */
    public static void checkTypeAndThrow(Object value) {
        if (value instanceof WowResult) {
            WowResult wow = (WowResult) value;
            try {
                throw new SolidParamNotFoundException(wow.getKey());
            } catch (SolidParamNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param value
     * @throws SolidException
     */
    public static void checkType(Object value) throws SolidException {
        if (value instanceof WowResult) {
            WowResult wow = (WowResult) value;
            throw new SolidException(wow.getKey());
        }
    }

    public static Boolean checkTypeIf(Object value) throws SolidException {
        return value instanceof WowResult;
    }

    public static String bulidObjectTemplateFromTemplateContent(String value) {
        return Constants.OBJ_LEFTMARK + value + Constants.OBJ_RIGHTMARK;
    }
}
