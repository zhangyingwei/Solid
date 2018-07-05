package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.config.Configuration;
import com.github.zhangyingwei.solid.exception.SolidException;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.items.process.EndProcessBlock;
import com.github.zhangyingwei.solid.items.process.ProcessBlock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangyw
 * @date 2018/7/4
 */
public class Template implements SolidTemplate {
    private Configuration configuration;
    private TemplateParser templateParser;
    private String source;

    public Template(Configuration configuration, TemplateParser templateParser, String source) {
        this.configuration = configuration;
        this.templateParser = templateParser;
        this.source = source;
    }

    @Override
    public void bind(String key, Object value) {
        this.configuration.getContext().bindArgs(key, value);
    }

    @Override
    public String render() {
        String content = this.configuration.getResourcesLoader().load(source);
        List<Block> blocks = this.templateParser.parse(content);
        System.out.println(blocks);
        StringBuilder resultText = new StringBuilder();
        List<Block> resultBlocks = new ArrayList<Block>();
        Iterator<Block> iterator = blocks.iterator();
        while (iterator.hasNext()) {
            Block tempBlock = iterator.next();
            iterator.remove();
            if (tempBlock instanceof ProcessBlock) {
                this.bulidProcessBlock((ProcessBlock) tempBlock, iterator);
            }
            resultBlocks.add(tempBlock);
        }
        resultBlocks.stream().map(block -> block.render().getResult()).forEach(res -> {
            resultText.append(res);
        });
        return resultText.toString();
    }

    private void bulidProcessBlock(ProcessBlock rootBlock, Iterator<Block> iterator) {
        while (iterator.hasNext()) {
            Block tempBlock = iterator.next();
            iterator.remove();
            if (tempBlock instanceof ProcessBlock) {
                if (tempBlock instanceof EndProcessBlock) {
                    EndProcessBlock end = (EndProcessBlock) tempBlock;
                    if (end.isEndOf(rootBlock)) {
                        break;
                    }
                } else {
                    bulidProcessBlock((ProcessBlock) tempBlock, iterator);
                }
            }
            rootBlock.addChildBlock(tempBlock);
        }
        if (!iterator.hasNext()) {
            try {
                throw new SolidException("the end of " + rootBlock + " is not found");
            } catch (SolidException e) {
                e.printStackTrace();
            }
        }
    }

}
