package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Test;

import static org.junit.Assert.*;

public class DateSolidMethodTest {

    @Test
    public void test1() {
        String templateString = "{{ published_at | date: \"YYYY-HH-mm\" }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("published_at","785971906000");
        System.out.println(template.render());
    }

    @Test
    public void test2() {
        String templateString = "{{ \"now\" | date: \"YYYY-HH-mm\" }}";
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("published_at","785971906000");
        System.out.println(template.render());
    }

}