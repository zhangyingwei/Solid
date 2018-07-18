package com.github.zhangyingwei.solid.items.object;


import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.demo.User;
import com.github.zhangyingwei.solid.items.pipline.AppendSolidMethod;
import com.github.zhangyingwei.solid.items.pipline.LengthSolidMethod;
import org.junit.Assert;
import org.junit.Test;

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
        String template = "{{ user.name | append \" === \" | append: user1.name}}";
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
//        Assert.assertEquals(result, "xiaoming");
    }
}