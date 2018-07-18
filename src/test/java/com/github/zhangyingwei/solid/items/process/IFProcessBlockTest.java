package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.config.Configuration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zhangyw
 * @date 2018/7/5
 */
public class IFProcessBlockTest {
    @Test
    public void render() throws Exception {
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateBuilder builder = new TemplateBuilder(configuration);
        Template template = builder.bulidTemplate("{% if \"1\"<=\"2\" %} adsf {% endif %} ");
        template.bind("username","admin");
        System.out.println(template.render());
    }
}