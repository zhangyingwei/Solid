package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniqSolidMethodTest {

    @Test
    public void test1() {
        String templateString = "{% assign my_array = \"ants, bugs, bees, bugs, ants\" | split: \", \" %} {{ my_array | uniq | join: \",\" }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), " ants,bugs,bees");
    }

}