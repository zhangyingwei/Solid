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

    public TextBlock(String text) {
        this.text = text;
    }

    @Override
    public SolidResult render() {
        return new StringResult(text);
    }

    @Override
    public String toString() {
        return "Text(" + text + ")";
    }
}
