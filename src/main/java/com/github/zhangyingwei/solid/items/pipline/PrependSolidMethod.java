package com.github.zhangyingwei.solid.items.pipline;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class PrependSolidMethod implements SolidMethod<String> {
    @Override
    public String doFormate(String content, Object[] args) {
        return args[0].toString().concat(content);
    }
}
