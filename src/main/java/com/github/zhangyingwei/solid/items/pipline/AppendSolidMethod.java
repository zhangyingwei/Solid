package com.github.zhangyingwei.solid.items.pipline;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class AppendSolidMethod implements SolidMethod {
    @Override
    public String doFormate(String content, String args) {
        return content.concat(args);
    }
}
