package com.github.zhangyingwei.solid.result;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class WowResult implements SolidResult<String> {
    private String result = "wow";
    private String key;

    public WowResult(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String getResult() {
        return result;
    }
}
