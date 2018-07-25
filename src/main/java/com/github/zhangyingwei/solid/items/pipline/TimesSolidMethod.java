package com.github.zhangyingwei.solid.items.pipline;

import java.math.BigDecimal;

/**
 * 乘以
 * @author zhangyw
 * @date 2018/7/3
 */
public class TimesSolidMethod implements SolidMethod<Object> {
    @Override
    public Object doFormate(Object content, Object[] args) {
        BigDecimal first = new BigDecimal(content.toString());
        BigDecimal other = new BigDecimal(args[0].toString());
        return first.multiply(other);
    }
}
