package com.github.zhangyingwei.solid.common;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.demo.User;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.items.process.EndProcessBlock;
import com.github.zhangyingwei.solid.items.process.ForProcessBlock;
import com.github.zhangyingwei.solid.items.text.TextBlock;
import com.github.zhangyingwei.solid.result.ObjectResult;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.result.WowResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class SolidUtils {

    public static SolidResult<Object> getObjectFromContext(String template, SolidContext context) {
        String[] objectKeys = template.split("\\.");
        Object tempValue = context.getParams();
        for (String objectKey : objectKeys) {
            tempValue = getValueFromObject(tempValue, objectKey);
            if (tempValue instanceof WowResult) {
                return (SolidResult) tempValue;
            }
        }
        return (SolidResult<Object>) tempValue;
    }

    private static SolidResult getValueFromObject(Object object, String key) {
        if (object instanceof SolidResult) {
            object = ((SolidResult) object).getResult();
        }
        if (object instanceof Map) {
            if (((Map) object).containsKey(key)) {
                return new ObjectResult(((Map) object).get(key));
            } else {
                return new WowResult();
            }
        } else {
            return getFromObject(object, key);
        }
    }

    private static SolidResult getFromObject(Object object, String key) {
        Class<? extends Object> clazz = object.getClass();
        String methodName = "get".concat(
                key.substring(0,1).toUpperCase().concat(key.substring(1).toLowerCase())
        );
        try {
            Method method = clazz.getMethod(methodName);
            Object result = method.invoke(object);
            if (null == result) {
                return new WowResult();
            }
            return new StringResult<>(result.toString());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return new WowResult();
        }
    }

    public static Block routeProcessBlock(String template, SolidContext context) {
        String command = template.trim().substring(
                Constants.PROCESS_LEFTMARK.length(),
                template.trim().length() - Constants.PROCESS_RIGHTMARK.length()
        ).trim();
        if (command.startsWith(Constants.TAG_FOR)) {
            return new ForProcessBlock(template, context);
        } else if (command.startsWith(Constants.TAG_FOR_END)) {
            return new EndProcessBlock(template,context).setTag(Constants.TAG_FOR_END);
        }
        return new TextBlock("not find process block , return a text block");
    }

    /**
     * 去掉多余的空格
     * @param content
     * @return
     */
    public static String removeExtraSpaces(String content) {
        while (content.indexOf("  ") >= 0) {
            content = content.replaceAll("  ", " ");
        }
        return content;
    }
}
