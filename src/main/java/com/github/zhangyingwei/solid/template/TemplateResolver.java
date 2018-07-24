package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.cache.CacheBuilder;
import com.github.zhangyingwei.solid.cache.SolidCache;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.config.SolidConfiguration;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class TemplateResolver {
    private SolidConfiguration configuration;
    private SolidCache templateCache;
    private String contentType;

    public TemplateResolver(SolidConfiguration configuration) {
        this.configuration = configuration;
        if (Constants.TEMPLATE_CACHE) {
            this.templateCache = CacheBuilder.getOrCreateCache(Constants.KEY_TEMPLATE_CACHE);
        }
    }

    /**
     * 根据 source 构建一个 template
     * @param source
     * @return
     */
    public Template resolve(String source) {
        Template template = null;
        if (Constants.TEMPLATE_CACHE) {
            template = (Template) templateCache.get(source);
        }
        if (template == null) {
            template = new Template(this.configuration,source);
            if (this.contentType != null && this.contentType.length() > 0) {
                template.setContentType(this.contentType);
            }
        }
        if (Constants.TEMPLATE_CACHE) {
            templateCache.cache(source,template,Constants.KEY_TEMPLATE_TIMEOUT_MILLISECOND);
        }
        return template;
    }

    public void setPrefix(String prefix) {
        this.configuration.getResourcesLoader().setPrefix(prefix);
    }

    public void setSuffix(String suffix) {
        this.configuration.getResourcesLoader().setSuffix(suffix);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}