package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.config.Configuration;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class TemplateBuilder {
    private Configuration configuration;
    private TemplateParser templateParser;

    public TemplateBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.templateParser = new TemplateParser(this.configuration.getContext());
    }

    /**
     * 根据 source 构建一个 template
     * @param source
     * @return
     */
    public Template bulidTemplate(String source) {
        Template template = new Template(this.configuration,this.templateParser,source);
        return template;
    }
}
