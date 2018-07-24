package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.items.process.*;

import java.util.*;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class Template implements SolidTemplate {
    private SolidConfiguration configuration;
    private TemplateParser templateParser;
    private String source;
    private String contentType = Constants.CONTENT_TYPE;
    private List<Block> resultBlocks;

    public Template(SolidConfiguration configuration, String source) {
        this.configuration = configuration;
        this.templateParser = new TemplateParser(this.configuration.getContext());
        this.source = source;
        this.init();
    }

    /**
     * init the blocks
     */
    private void init() {
        String content = this.configuration.getResourcesLoader().load(source);
        List<Block> blocks = this.templateParser.parse(content);
//        System.out.println(blocks);
        this.resultBlocks = new ArrayList<Block>();
        Stack<Block> blockStack = new Stack<Block>();
        Collections.reverse(blocks);
        blockStack.addAll(blocks);
        while (!blockStack.empty()) {
            Block tempBlock = blockStack.pop();
            if (tempBlock instanceof ProcessBlock) {
                ProcessBlock processBlock = (ProcessBlock) tempBlock;
                if (!processBlock.isNoEndBlock()) {
                    this.bulidProcessBlock((ProcessBlock) tempBlock, blockStack);
                }
            }
            resultBlocks.add(tempBlock);
        }
    }

    @Override
    public void bind(String key, Object value) {
        this.configuration.getContext().bindArgs(key, value);
    }

    @Override
    public String render() {
        StringBuilder resultText = new StringBuilder();
        resultBlocks.stream().map(block -> block.render().getResult()).forEach(res -> {
            resultText.append(res);
        });
        return resultText.toString();
    }

    /**
     * bulid blocks as a tree
     * @param rootBlock
     * @param blockStack
     */
    private void bulidProcessBlock(ProcessBlock rootBlock, Stack<Block> blockStack) {
        while (!blockStack.empty()) {
            Block tempBlock = blockStack.pop();
            if (tempBlock instanceof ProcessBlock) {
                ProcessBlock processBlock = (ProcessBlock) tempBlock;
                if (tempBlock instanceof EndProcessBlock) {
                    EndProcessBlock end = (EndProcessBlock) processBlock;
                    if (end.isEndOf(rootBlock)) {
                        break;
                    } else {
                        blockStack.push(end);
                        break;
                    }
                }
                if (processBlock.isNoEndBlock()) {
                    rootBlock.addChildBlock(tempBlock);
                    break;
                }
                bulidProcessBlock((ProcessBlock) tempBlock, blockStack);
            }
            rootBlock.addChildBlock(tempBlock);
        }
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
