package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.common.SolidUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class DateSolidMethod implements SolidMethod<Object> {
    @Override
    public String doFormate(Object content, Object[] args) {
        Long timestamp = null;
        if (SolidUtils.isNum(content.toString())) {
            timestamp = Long.parseLong(content.toString());
        } else if ("now".equals(content)) {
            timestamp = System.currentTimeMillis();
        } else {
            timestamp = System.currentTimeMillis();
        }
        String formater = (String) args[0];
        SimpleDateFormat format = new SimpleDateFormat(formater);
        return format.format(new Date(timestamp));
    }
}
