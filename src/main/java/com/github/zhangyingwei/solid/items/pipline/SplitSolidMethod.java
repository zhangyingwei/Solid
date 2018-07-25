package com.github.zhangyingwei.solid.items.pipline;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class SplitSolidMethod implements SolidMethod<String> {
    @Override
    public String[] doFormate(String content, Object[] args) {
        return content.split(args[0].toString());
    }
}
