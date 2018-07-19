package com.github.zhangyingwei.solid.config;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class StringTemplateResourceLoader implements SolidTemplateResourcesLoader {
    @Override
    public String load(String source) {
        return source;
    }

    @Override
    public void setPrefix(String prefix) {}

    @Override
    public void setSuffix(String suffix) {}
}
