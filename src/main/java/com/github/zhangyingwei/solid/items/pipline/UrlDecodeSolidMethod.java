package com.github.zhangyingwei.solid.items.pipline;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class UrlDecodeSolidMethod implements SolidMethod<String> {
    @Override
    public String doFormate(String content, Object[] args) {
        return URLDecoder.decode(content);
    }
}
