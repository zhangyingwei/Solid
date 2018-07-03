package com.github.zhangyingwei.solid.result;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class ObjectResult<Object> implements SolidResult {
    private Object result;

    public ObjectResult(Object result) {
        this.result = result;
    }

    @Override
    public Object getResult() {
        return result;
    }
}
