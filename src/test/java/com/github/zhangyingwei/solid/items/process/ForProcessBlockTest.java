package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.items.object.ObjectBlock;
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
        forProcessBlock.addChildBlock(objectBlock);

        System.out.println(forProcessBlock.render().getResult());
    }
}