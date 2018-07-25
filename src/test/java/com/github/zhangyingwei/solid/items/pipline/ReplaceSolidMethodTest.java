package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReplaceSolidMethodTest {
    @Test
    public void test1() {
        String templateString = "{{ \"Take my protein pills and put my helmet on\" | replace: \"my\", key }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("key", "your");
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "Take your protein pills and put your helmet on");
    }
}