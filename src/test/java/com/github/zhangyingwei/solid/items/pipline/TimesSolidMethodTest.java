package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimesSolidMethodTest {

    @Test
    public void test1() {
        String templateString = "{{ 3 | times: 2 }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "6");
    }

    @Test
    public void test2() {
        String templateString = "{{ 24 | times: 7 }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "168");
    }

    @Test
    public void test3() {
        String templateString = "{{ 183.357 | times: 12 }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "2200.284");
    }
}