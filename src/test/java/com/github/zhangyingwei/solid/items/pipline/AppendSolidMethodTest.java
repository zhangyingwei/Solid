package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppendSolidMethodTest {

    @Test
    public void test1() {
        String templateString = "{{ \"/my/fancy/url\" | append: \".html\" }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "/my/fancy/url.html");
    }

    @Test
    public void test2() {
        String templateString = "{% assign filename = \"/index.html\" %}{{ \"website.com\" | append: filename }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "website.com/index.html");
    }


    @Test
    public void test3() {
        String templateString = "" +
                "{% assign fruits = \"apples, oranges, peaches\" | split: \", \" %}" +
                "{% assign vegetables = \"carrots, turnips, potatoes\" | split: \", \" %}" +
                "{% assign everything = fruits | concat: vegetables | concat: vegetables %}" +
                "{% for item in everything %}" +
                "- {{ item }}\n" +
                "{% endfor %}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
    }


}