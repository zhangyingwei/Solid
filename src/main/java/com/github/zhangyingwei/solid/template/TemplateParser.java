package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.items.object.ObjectBlock;
import com.github.zhangyingwei.solid.items.process.ProcessBlock;
import com.github.zhangyingwei.solid.items.text.TextBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class TemplateParser {
    private SolidContext context;

    public TemplateParser(SolidContext context) {
        this.context = context;
    }

    public List<Block> parse(String content) {
        List<Block> blocks = new ArrayList<Block>();
        TemplateFlow templateFlow = new TemplateFlow(content);
        while (templateFlow.isNotEmpty()) {
            Block block = null;
            if (templateFlow.startWith(Constants.OBJ_LEFTMARK)) {
                StringBuilder tempObj = new StringBuilder();
                while (!templateFlow.startWith(Constants.OBJ_RIGHTMARK)) {
                    tempObj.append(templateFlow.pull(1));
                }
                tempObj.append(templateFlow.pull(Constants.OBJ_RIGHTMARK.length()));
                block = new ObjectBlock(this.context, tempObj.toString());
            }else if (templateFlow.startWith(Constants.PROCESS_LEFTMARK)) {
                StringBuilder tempObj = new StringBuilder();
                while (!templateFlow.startWith(Constants.PROCESS_RIGHTMARK)) {
                    tempObj.append(templateFlow.pull(1));
                }
                tempObj.append(templateFlow.pull(Constants.PROCESS_RIGHTMARK.length()));
                block = SolidUtils.routeProcessBlock(tempObj.toString(),this.context);
            }else{
                StringBuilder tempObj = new StringBuilder();
                while (!templateFlow.startWith(Constants.OBJ_LEFTMARK) && !templateFlow.startWith(Constants.PROCESS_LEFTMARK ) && templateFlow.isNotEmpty()) {
                    tempObj.append(templateFlow.pull(1));
                }
                block = new TextBlock(tempObj.toString());
            }
            blocks.add(block);
        }
        return blocks;
    }

    public static class TemplateFlow {
        StringBuilder contentBuilder;
        public TemplateFlow(String content) {
            this.contentBuilder = new StringBuilder(content);
        }

        public boolean startWith(String str) {
            return this.contentBuilder.indexOf(str) == 0;
        }

        public String pull(int length) {
            String result = contentBuilder.substring(0, length);
            contentBuilder.delete(0, length);
            return result;
        }

        public boolean isNotEmpty() {
            return this.contentBuilder.length() > 0;
        }
    }

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder("{{ user.name }} ");
        System.out.println(builder.indexOf("{{"));
    }
}
