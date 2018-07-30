package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.config.FileTemplateResourceLoader;
import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.demo.User;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IncludeProcessBlockTest {

    @Test
    public void render() {
        String templateString = "{% assign __path = 123 %} {{ __path }} {% include {{ childpath }} %} {% assign username = uname %} {{ username }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("uname", "bob");
        template.bind("age", 123456);
        template.bind("childpath", "hello.txt");
        System.out.println(template.render());
    }
}