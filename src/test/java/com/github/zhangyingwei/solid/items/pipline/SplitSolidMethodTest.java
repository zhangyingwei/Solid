package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SplitSolidMethodTest {

    @Test
    public void test1() {
        String templateString = "{% assign beatles = \"John, Paul, George, Ringo\" | split: \", \" %}\n" +
                "\n" +
                "{% for member in beatles %}\n" +
                "  {{ member }}\n" +
                "{% endfor %}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        System.out.println(template.render());
    }
}