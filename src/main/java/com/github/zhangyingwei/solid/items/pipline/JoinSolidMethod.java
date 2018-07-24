package com.github.zhangyingwei.solid.items.pipline;

import java.util.*;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class JoinSolidMethod implements SolidMethod<Object> {
    @Override
    public String doFormate(Object content, Object args) {
        if (content.getClass().isArray()) {
            Set<String> result = new HashSet<String>();
            result.addAll(Arrays.asList((String[]) content));
            return String.join(args.toString(), result);
        } else if (content instanceof Collection) {
            Collection input = (Collection) content;
            return String.join(args.toString(), input);
        }
        return "";
    }
}
