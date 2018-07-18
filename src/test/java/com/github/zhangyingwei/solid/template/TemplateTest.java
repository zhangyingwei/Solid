package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.config.Configuration;
import com.github.zhangyingwei.solid.config.FileTemplateResourceLoader;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.demo.User;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class TemplateTest {

    @Test
    public void renderTest() {
        System.out.println("render");
        User user = new User();
        user.setName("xiaoming");
        user.setAge(123);
        Configuration configuration = new Configuration(new StringTemplateResourceLoader());
        TemplateBuilder builder = new TemplateBuilder(configuration);
        Template template = builder.bulidTemplate("Hello my name is {{ user.name | append \" wang \" }} and my age is {{user.age }} and my names length is {{ user.name | length }}. and my hobbies have {% for hobby in hobbies %} {{ hobby }} {% endfor %}. haha {{hobbies}} ");
        template.bind("user", user);
        template.bind("hobbies",new ArrayList<String>(){
            {
                add("足球");
                add("篮球");
                add("羽毛球");
            }
        });
        System.out.println(template.render());
    }

    @Test
    public void htmlRender() throws InterruptedException {
        Configuration configuration = new Configuration(new FileTemplateResourceLoader("src/main/resources"));
        TemplateBuilder builder = new TemplateBuilder(configuration);
        Template template = builder.bulidTemplate("test.html");
        template.bind("username", "admin");
        template.bind("password","123456");
        System.out.println(template.render());
    }
}