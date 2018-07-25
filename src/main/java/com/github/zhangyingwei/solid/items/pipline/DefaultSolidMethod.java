package com.github.zhangyingwei.solid.items.pipline;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class DefaultSolidMethod implements SolidMethod<Object> {
    @Override
    public String doFormate(Object content, Object[] args) {
        if (content == null || content.equals("") || content.equals("wow") || content.equals("false")) {
            return args[0].toString();
        }
        return content.toString();
    }
}
