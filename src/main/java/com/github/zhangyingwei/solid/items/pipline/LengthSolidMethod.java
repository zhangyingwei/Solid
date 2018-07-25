package com.github.zhangyingwei.solid.items.pipline;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class LengthSolidMethod implements SolidMethod<Object> {
    @Override
    public Integer doFormate(Object content, Object[] args) {
        if (content instanceof String) {
            return ((String) content).length();
        } else if (content instanceof Collection) {
            return ((Collection) content).size();
        } else if (content instanceof Object[]) {
            Object[] objects = (Object[]) content;
            return objects.length;
        } else {
            Class<? extends Object> clazz = content.getClass();
            try {
                Method lengthMethod = clazz.getMethod("length");
                return (Integer) lengthMethod.invoke(content);
            } catch (NoSuchMethodException e) {
                try {
                    Method sizeMethod = clazz.getMethod("size");
                    return (Integer) sizeMethod.invoke(content);
                } catch (NoSuchMethodException e1) {
                    e.printStackTrace();
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
