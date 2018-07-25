package com.github.zhangyingwei.solid.items.pipline;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class ReplaceFirstSolidMethod implements SolidMethod<String> {
    @Override
    public String doFormate(String content, Object[] args) {
        return content.replaceFirst(args[0].toString(),args[1].toString());
    }
}
