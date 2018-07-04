package com.github.zhangyingwei.solid.template;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public interface SolidTemplate {
    void bind(String key,Object value);
    String render();
}
