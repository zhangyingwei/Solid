package com.github.zhangyingwei.solid.items.pipline;


import java.util.HashMap;
import java.util.Map;

/**
 * 追加
 * @author zhangyw
 * @date 2018/7/3
 */
public class EscapeSolidMethod implements SolidMethod<String> {
    private  static Map<String, String> ruleMap = new HashMap<String, String>();

    public EscapeSolidMethod() {
        ruleMap.put("&", "&amp;");
        ruleMap.put("\"", "&quot;");
        ruleMap.put("<", "&lt;");
        ruleMap.put(">", "&gt;");
    }

    @Override
    public String doFormate(String content, Object[] args) {
        for (Map.Entry<String, String> rule : ruleMap.entrySet()) {
            content = content.replaceAll(rule.getKey(), rule.getValue());
        }
        return content;
    }
}
