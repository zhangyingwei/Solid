package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.cache.CacheBuilder;
import com.github.zhangyingwei.solid.cache.SolidCache;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.config.Configuration;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class TemplateBuilder {
    private Configuration configuration;
    private SolidCache templateCache = CacheBuilder.getOrCreateCache(Constants.KEY_TEMPLATE_CACHE);

    public TemplateBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 根据 source 构建一个 template
     * @param source
     * @return
     */
    public Template bulidTemplate(String source) {
        Template template = (Template) templateCache.get(source);
        if (template == null) {
            template = new Template(this.configuration,source);
            templateCache.cache(source,template,Constants.KEY_TEMPLATE_TIMEOUT_MILLISECOND);
        }
        return template;
    }
}