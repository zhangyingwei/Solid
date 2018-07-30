package com.github.zhangyingwei.solid.items.object;


import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.demo.User;
import com.github.zhangyingwei.solid.items.pipline.AppendSolidMethod;
import com.github.zhangyingwei.solid.items.pipline.LengthSolidMethod;
import com.github.zhangyingwei.solid.template.Template;
import com.github.zhangyingwei.solid.template.TemplateResolver;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class ObjectBlockTest {
    @Test
    public void render() throws Exception {
        User user = new User();
        user.setName("xiaoming");
        SolidContext context = new SolidContext();
        context.bindArgs("user", user);
        String template = "{{ user.name }}";
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        Assert.assertEquals(result, "xiaoming");
    }

    @Test
    public void testPipline() throws Exception {
        User user = new User();
        user.setName("xiaoming");
        user.setAge(123);

        User user1 = new User();
        user1.setName("baobo");
        user1.setAge(456);

        SolidContext context = new SolidContext();
        context.bindArgs("user", user);
        context.bindArgs("user1", user1);
        context.bindMethod("append",new AppendSolidMethod());
        String template = "{{ user.name | append: \" === \" | append: user1.name}}";
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        System.out.println(result);
        Assert.assertEquals(result,"xiaoming === baobo");
    }

    @Test
    public void lengthTest() throws Exception {
        User user = new User();
        user.setName("xiaoming");
        SolidContext context = new SolidContext();
        context.bindArgs("user", user);
        context.bindMethod("length",new LengthSolidMethod());
        String template = "{{ user.name | length }}";
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        System.out.println(result);
        Assert.assertEquals(Integer.parseInt(result), 8);
    }

    @Test
    public void booleanTest() throws Exception {
        SolidContext context = new SolidContext();
        String template = "{{ has }}";
        context.bindArgs("has", false);
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        System.out.println(result);
        Assert.assertEquals(result, "false");
    }

    @Test
    public void arrTest() throws Exception {
        SolidContext context = new SolidContext();
        String template = "{{ has.first }}";
        String[] hass = new String[]{
                "1",
                "2",
                "3"
        };
        context.bindArgs("has", hass);
        ObjectBlock objectBlock = new ObjectBlock(context, template);
        String result = objectBlock.render().getResult();
        System.out.println(result);
        Assert.assertEquals(result, "1");
    }

    @Test
    public void arrFromIndexTest() throws Exception {
        SolidContext context = new SolidContext();
        String templateString = "{{ has[0] }}";
        String[] hass = new String[]{
                "1",
                "2",
                "3"
        };
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("has", hass);
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "1");
    }

    @Test
    public void objectFromIndexTest() throws Exception {
        SolidContext context = new SolidContext();
        String templateString = "{{ user[namekey] | append: \" is \" | append: user.age }}";
        User user = new User();
        user.setName("wangerxiao");
        user.setAge(102);
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve(templateString);
        template.bind("user", user);
        template.bind("namekey", "name");
        System.out.println(template.render());
        Assert.assertEquals(template.render(), "wangerxiao is 102");
    }
}