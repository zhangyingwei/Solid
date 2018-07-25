package com.github.zhangyingwei.solid.items.pipline;

import java.math.BigDecimal;

/**
 * 向上取整
 * 1.2 => 2
 * @author zhangyw
 * @date 2018/7/3
 */
public class CeilSolidMethod implements SolidMethod<Object> {
    @Override
    public String doFormate(Object content, Object[] args) {
        BigDecimal input = new BigDecimal(content.toString());
        return input.setScale(0, BigDecimal.ROUND_UP).longValue() + "";
    }
}
