package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrependSolidMethodTest {
    @Test
    public void test1() {
        String templateString = "{{ \"apples, oranges, and bananas\" | prepend: \"Some fruit: \" }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "Some fruit: apples, oranges, and bananas");
    }
    @Test
    public void test2() {
        String templateString = "{% assign url = \"liquidmarkup.com\" %}" +
                "{{ \"/index.html\" | prepend: url }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "liquidmarkup.com/index.html");
    }
}