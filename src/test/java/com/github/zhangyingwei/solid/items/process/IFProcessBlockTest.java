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
        Template template = builder.resolve("{% if username==\"admin\" %} adsf {% endif %}{% if username!=\"admin\" %} adsf {% endif %}{% if age == 10 %} age is {{age}} {% endif %} ");
        template.bind("username","admin");
        template.bind("age",10);
        System.out.println(template.render());
    }

    @Test
    public void elseRender() throws Exception {
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve("{% if age == 10 %} age is {{age}} {% elsif age == 11 %} this is elseif {% else %} this is else {% endif %} ");
        template.bind("username","admin");
        template.bind("age",10);
        System.out.println(template.render());
    }
}