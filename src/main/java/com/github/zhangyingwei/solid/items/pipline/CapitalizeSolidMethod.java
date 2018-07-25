package com.github.zhangyingwei.solid.items.pipline;

/**
 * 首字母大写
 * @author zhangyw
 * @date 2018/7/3
 */
public class CapitalizeSolidMethod implements SolidMethod<String> {
    @Override
    public String doFormate(String content, Object[] args) {
        return content.substring(0, 1).toUpperCase().concat(content.substring(1).toLowerCase());
    }
}
