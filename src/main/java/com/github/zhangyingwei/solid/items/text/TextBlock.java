package com.github.zhangyingwei.solid.items.text;

import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class TextBlock implements Block {
    private String text;
    private boolean flag = true;

    public TextBlock(String text) {
        this.text = text;
    }

    @Override
    public Block setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    @Override
    public SolidResult render() {
        if (!flag) {
            return new StringResult("");
        }
        return new StringResult(text);
    }

    @Override
    public String text() {
        return text;
    }

    @Override
    public String toString() {
        return "Text(" + text + ")";
    }
}
