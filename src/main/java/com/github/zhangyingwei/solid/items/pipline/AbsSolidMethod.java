package com.github.zhangyingwei.solid.items.pipline;

import java.math.BigDecimal;

/**
 * 取绝对值
 * @author zhangyw
 * @date 2018/7/3
 */
public class AbsSolidMethod implements SolidMethod<Object> {
    @Override
    public String doFormate(Object content, Object[] args) {
        BigDecimal input = new BigDecimal(content.toString());
        return input.abs().toString();
    }
}
