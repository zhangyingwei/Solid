package com.github.zhangyingwei.solid.common;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.demo.User;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.result.WowResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class SolidUtils {

    public static SolidResult getValueFromContext(String template, SolidContext context) {
        String[] objectKeys = template.split("\\.");
        Object tempValue = context.getParams();
        for (String objectKey : objectKeys) {
            tempValue = getValueFromObject(tempValue, objectKey);
            if (tempValue instanceof WOW) {
                return new WowResult();
            }
        }
        if (tempValue instanceof WOW) {
            return new WowResult();
        } else {
            return new StringResult(tempValue.toString());
        }
    }

    private static Object getValueFromObject(Object object, String key) {
        if (object instanceof Map) {
            if (((Map) object).containsKey(key)) {
                return ((Map) object).get(key);
            } else {
                return new WOW();
            }
        } else {
            return getFromObject(object, key);
        }
    }

    private static Object getFromObject(Object object, String key) {
        Class<? extends Object> clazz = object.getClass();
        String methodName = "get".concat(
                key.substring(0,1).toUpperCase().concat(key.substring(1).toLowerCase())
        );
        try {
            Method method = clazz.getMethod(methodName);
            Object result = method.invoke(object);
            if (null == result) {
                return new WOW();
            }
            return result.toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return new WOW();
        }
    }
}
