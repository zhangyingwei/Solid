package com.github.zhangyingwei.solid.items.pipline;

import java.net.URLEncoder;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class UrlEncodeSolidMethod implements SolidMethod<String> {
    @Override
    public String doFormate(String content, Object[] args) {
        return URLEncoder.encode(content);
    }
}
