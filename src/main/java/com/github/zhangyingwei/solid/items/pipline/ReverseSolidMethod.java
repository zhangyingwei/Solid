package com.github.zhangyingwei.solid.items.pipline;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class ReverseSolidMethod implements SolidMethod<Object> {
    @Override
    public Object doFormate(Object content, Object[] args) {
        if (content.getClass().isArray()) {
            List<String> result = new ArrayList<>();
            result.addAll(Arrays.asList((String[]) content));
            Collections.reverse(result);
            return result;
        } else if (content instanceof Collection) {
            Collection input = (Collection) content;
            Collections.reverse((List<?>) input);
            return input;
        }
        return new ArrayList<Object>();
    }
}
