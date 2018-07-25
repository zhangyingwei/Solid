package com.github.zhangyingwei.solid.items.pipline;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyw
 * @date 2018/7/10
 */
public class RangeSolidMethod implements SolidMethod<Object> {

    @Override
    public List doFormate(Object content, Object[] args) {
        List<Integer> result = new ArrayList<Integer>();
        Integer total = (Integer) content;
        for (Integer i = 0; i < total; i++) {
            result.add(i);
        }
        return result;
    }
}
