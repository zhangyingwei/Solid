package com.github.zhangyingwei.solid.items.pipline;

import java.util.*;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class UniqSolidMethod implements SolidMethod<Object> {
    @Override
    public Object doFormate(Object content, Object[] args) {
        if (content.getClass().isArray()) {
            Set<Object> result = new HashSet<Object>();
            result.addAll(Arrays.asList((Object[]) content));
            return result;
        } else if (content instanceof Collection) {
            Collection input = (Collection) content;
            return (Set<Object>)input;
        }
        return new Object();
    }
}
