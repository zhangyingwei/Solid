package com.github.zhangyingwei.solid.config;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public interface SolidTemplateResourcesLoader {
    String load(String source);

    void setPrefix(String prefix);

    void setSuffix(String suffix);
}
