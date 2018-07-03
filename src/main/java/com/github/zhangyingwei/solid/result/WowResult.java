package com.github.zhangyingwei.solid.result;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class WowResult implements SolidResult<String> {
    private String result = "";

    @Override
    public String getResult() {
        return result;
    }
}
