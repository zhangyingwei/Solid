package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;


//TODO
public class ElseProcessBlock extends ProcessBlock {
    public ElseProcessBlock(String topMark, SolidContext context) {
        super(topMark, context);
        super.tag = Constants.TAG_ELSE;
        super.endTag = Constants.TAG_ELSIF_END;
    }
    @Override
    public SolidResult render() {
        StringBuilder resultString = new StringBuilder();
        super.childsResult(flag).stream().forEach(child -> {
            resultString.append(child.getResult().toString());
        });
        return new StringResult(resultString.toString());
    }

    @Override
    public String text() {
        return topMark;
    }
}
