package com.github.zhangyingwei.solid.items.pipline;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class ConcatSolidMethod implements SolidMethod<Object> {
    @Override
    public Object doFormate(Object content, Object[] args) {
        if (content.getClass().isArray()) {
            List<Object> result = new ArrayList<Object>();
            result.addAll(Arrays.asList((Object[]) content));
            result.addAll(this.asList(args[0]));
            return result;
        } else if (content instanceof Collection) {
            Collection input = (Collection) content;
            Collection other = this.asList(args[0]);
            input.addAll(other);
            return input;
        }
        return new Object();
    }

    private Collection<?> asList(Object args) {
        if (args.getClass().isArray()) {
            return Arrays.asList((Object[]) args);
        } else if (args instanceof Collection) {
            return (Collection<?>) args;
        }
        return Arrays.asList(args);
    }
}
