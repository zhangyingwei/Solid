package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Test;

public class AssignProcessBlockTest {

    @Test
    public void render() {
        String templateString = "{% assign __path = 123 %} {{ __path }} {% assign username = uname %} {{ username }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("uname", "bob");
        System.out.println(template.render());
    }
}