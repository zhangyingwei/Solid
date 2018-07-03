package com.github.zhangyingwei.solid.result;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class StringResult implements SolidResult {
    private String result;

    public StringResult(String result) {
        this.result = result;
    }

    @Override
    public String getResult() {
        return result;
    }
}
