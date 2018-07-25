package com.github.zhangyingwei.solid.items.pipline;


import java.util.HashMap;
import java.util.Map;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class NewlineToBrSolidMethod implements SolidMethod<String> {
    @Override
    public String doFormate(String content, Object[] args) {
        return content.replaceAll("\n", "<br/>");
    }
}
