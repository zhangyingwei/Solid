package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.config.FileTemplateResourceLoader;
import com.github.zhangyingwei.solid.config.StringTemplateResourceLoader;
import com.github.zhangyingwei.solid.demo.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class TemplateTest {

    @Test
    public void templateFormateTest() {
        SolidConfiguration configuration = new SolidConfiguration(new FileTemplateResourceLoader("src/main/resources"));
        TemplateResolver resolver = new TemplateResolver(configuration);
        resolver.setSuffix(".txt");
        Template template = resolver.resolve("template");
        template.bind("username", "JoinWe");
        template.bind("password","123456");
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("name" + i);
            user.setAge(i);
            users.add(user);
        }
        template.bind("users",users);
        String result = template.render();
        System.out.println(result);
    }

    @Test
    public void renderTest() {
        User user = new User();
        user.setName("xiaoming");
        user.setAge(123);
        SolidConfiguration configuration = new SolidConfiguration(new StringTemplateResourceLoader());
        TemplateResolver builder = new TemplateResolver(configuration);
        Template template = builder.resolve("Hello my name is {{ user.name | append: \" wang \" }} and my age is {{user.age }} and my names length is {{ user.name | length }}. and my hobbies have {% for hobby in hobbies %} {{ hobby }} {% endfor %}. haha {{hobbies}} ");
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
        SolidConfiguration configuration = new SolidConfiguration(new FileTemplateResourceLoader("src/main/resources"));
        TemplateResolver resolver = new TemplateResolver(configuration);
        resolver.setSuffix(".html");
        Template template = resolver.resolve("test");
        template.bind("username", "admin");
        template.bind("password","密码");
        template.render();
        System.out.println(template.render());
    }

    @Test
    public void forifRender() throws InterruptedException {
        SolidConfiguration configuration = new SolidConfiguration(new FileTemplateResourceLoader("src/main/resources"));
        TemplateResolver resolver = new TemplateResolver(configuration);
        resolver.setSuffix(".html");
        Template template = resolver.resolve("index");
        template.bind("username", "张英伟");
        template.bind("password","123456password");
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("name" + i);
            user.setAge(i);
            users.add(user);
        }
        template.bind("users",users);
        template.render();
        System.out.println(template.render());
    }
}