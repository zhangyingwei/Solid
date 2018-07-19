package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Test;

/**
 * @author zhangyw
 * @date 2018/7/5
 */
public class IFProcessBlockTest {
    @Test
    public void render() throws Exception {
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve("{% if \"1\"<=\"2\" %} adsf {% endif %} ");
        template.bind("username","admin");
        System.out.println(template.render());
    }
}