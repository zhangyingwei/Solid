package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.items.object.ObjectBlock;
import com.github.zhangyingwei.solid.items.text.TextBlock;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author: zhangyw
 * @date: 2018/7/3
 * @time: 下午10:00
 * @desc:
 */
public class ForProcessBlockTest {
    @Test
    public void render() throws Exception {
        String topMark = "{% for item in items %}";
        SolidContext context = new SolidContext();
        context.bindArgs("items",new ArrayList<String>(){
            {
                add("a");
                add("b");
                add("c");
                add("d");
            }
        });
        ForProcessBlock forProcessBlock = new ForProcessBlock(topMark,context);
        ObjectBlock objectBlock = new ObjectBlock(context,"{{ item }}");
        TextBlock textBlock = new TextBlock("<a>");
        TextBlock textBlock2 = new TextBlock("<\\a>");
        forProcessBlock.addChildBlock(textBlock);
        forProcessBlock.addChildBlock(objectBlock);
        forProcessBlock.addChildBlock(textBlock2);
        System.out.println(forProcessBlock.render().getResult());
        Assert.assertEquals(forProcessBlock.render().getResult(),"<a>a<\\a><a>b<\\a><a>c<\\a><a>d<\\a>");
    }

    @Test
    public void forInForTest() {
        String topMark = "{% for item in items %}";
        String topMark2 = "{% for item2 in items2 %}";
        SolidContext context = new SolidContext();
        context.bindArgs("items",new ArrayList<String>(){
            {
                add("a");
                add("b");
                add("c");
                add("d");
            }
        });
        context.bindArgs("items2",new ArrayList<String>(){
            {
                add("1");
                add("2");
                add("3");
                add("4");
            }
        });
        ForProcessBlock forProcessBlock = new ForProcessBlock(topMark,context);
        ObjectBlock objectBlock = new ObjectBlock(context,"{{ item }}");

        ForProcessBlock forProcessBlock2 = new ForProcessBlock(topMark2,context);
        ObjectBlock objectBlock2 = new ObjectBlock(context,"{{ item2 }}");
        forProcessBlock2.addChildBlock(objectBlock2);

        TextBlock textBlock = new TextBlock("<a>");
        TextBlock textBlock2 = new TextBlock("<\\a>");
        forProcessBlock.addChildBlock(textBlock);
        forProcessBlock.addChildBlock(objectBlock);
        forProcessBlock.addChildBlock(forProcessBlock2);
        forProcessBlock.addChildBlock(textBlock2);
        System.out.println(forProcessBlock.render().getResult());
        Assert.assertEquals(forProcessBlock.render().getResult(),"<a>a1234<\\a><a>b1234<\\a><a>c1234<\\a><a>d1234<\\a>");
    }

    @Test
    public void forInRangeTest() {
        String topMark = "{% for item in range(10) %}";
    }
}