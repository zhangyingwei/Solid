package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.result.SolidResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public abstract class ProcessBlock implements Block {
    protected String topMark;
    protected List<Block> childBlocks = new ArrayList<Block>();
    protected SolidContext context;

    public ProcessBlock(String topMark,SolidContext context) {
        this.topMark = topMark;
        this.context = context;
    }

    protected List<SolidResult> childsResult() {
        return childBlocks.stream().map(child -> child.render()).collect(Collectors.toList());
    }
}
