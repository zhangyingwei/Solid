package com.github.zhangyingwei.solid.result;

import java.math.BigDecimal;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class NumResult implements SolidResult {
    private BigDecimal result;

    public NumResult(String result) {
        this.result = new BigDecimal(result);
    }

    @Override
    public BigDecimal getResult() {
        return result;
    }
}
