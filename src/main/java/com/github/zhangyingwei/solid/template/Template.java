package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.common.StringConveyor;
import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.items.process.*;
import com.github.zhangyingwei.solid.items.text.TextBlock;

import java.util.*;
import java.util.stream.Collectors;

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
    private Header header;

    public Template(SolidConfiguration configuration, String source) {
        this.configuration = configuration;
        this.templateParser = new TemplateParser(this.configuration.getContext());
        this.source = source;
    }

    /**
     * init the blocks
     */
    private void init() {
        String content = this.configuration.getResourcesLoader().load(source);
        StringConveyor conveyor = new StringConveyor(content);
        String headerContent = conveyor.getFromTo("---".concat(Constants.Wrap()), "---".concat(Constants.Wrap())).result();
        content = conveyor.string();
        this.header = new Header(headerContent);
        List<Block> blocks = this.templateParser.parse(content);
        for (int i = 0; i < blocks.size() - 2; i++) {
            Block before = blocks.get(i);
            Block current = blocks.get(i + 1);
            Block after = blocks.get(i + 2);
            if (current instanceof ProcessBlock) {
                ProcessBlock currentProcess = (ProcessBlock) current;
                if (currentProcess.isDeleteBlank()) {
                    if (before instanceof TextBlock) {
                        TextBlock beforeTextBlock = (TextBlock) before;
                        if (beforeTextBlock.textIs("\n")) {
                            beforeTextBlock.skip();
                        }
                    }
                    if (after instanceof TextBlock) {
                        TextBlock afterTextBlock = (TextBlock) after;
                        if (afterTextBlock.textIs("\n")) {
                            afterTextBlock.skip();
                        }
                    }
                }
            }
        }
        this.resultBlocks = new ArrayList<Block>();
        Stack<Block> blockStack = new Stack<Block>();
        Collections.reverse(blocks);
        blockStack.addAll(blocks);
        while (!blockStack.empty()) {
            Block tempBlock = blockStack.pop();
            Block resultBlock = null;
            if (tempBlock instanceof ProcessBlock) {
                ProcessBlock processBlock = (ProcessBlock) tempBlock;
                if (!processBlock.isNoEndBlock()) {
                    resultBlock = this.bulidProcessBlock((ProcessBlock) tempBlock, blockStack);
                }
            }
            resultBlocks.add(tempBlock);
            if (resultBlock != null) {
                if (resultBlock instanceof EndProcessBlock) {
                    EndProcessBlock endProcessBlock = (EndProcessBlock) resultBlock;
                    if (endProcessBlock.isEndOf((ProcessBlock) tempBlock)) {
                        resultBlocks.add(resultBlock);
                    }
                }
            }
        }
    }

    @Override
    public void bind(String key, Object value) {
        this.configuration.getContext().bindArgs(key, value);
    }

    @Override
    public String render() {
        this.init();
        StringBuilder resultText = new StringBuilder();
        resultBlocks.stream().map(block -> block.render().getResult()).forEach(res -> {
            resultText.append(res);
        });
        if (this.header.available()) {
            this.bind(Constants.LAYOUT_CONTENT_KEY, resultText.toString());
            return this.header.template().render();
        }
        return resultText.toString();
    }

    /**
     * bulid blocks as a tree
     * @param rootBlock
     * @param blockStack
     */
    private Block bulidProcessBlock(ProcessBlock rootBlock, Stack<Block> blockStack) {
        while (!blockStack.empty()) {
            Block tempBlock = blockStack.pop();
            Block resultBlock = null;
            if (tempBlock instanceof ProcessBlock) {
                ProcessBlock processBlock = (ProcessBlock) tempBlock;
                if (tempBlock instanceof EndProcessBlock) {
                    EndProcessBlock end = (EndProcessBlock) processBlock;
                    if (end.isEndOf(rootBlock)) {
                        return end;
                    } else {
                        blockStack.push(end);
                        break;
                    }
                }
                if (processBlock.isNoEndBlock()) {
                    rootBlock.addChildBlock(tempBlock);
                    break;
                }
                resultBlock = bulidProcessBlock((ProcessBlock) tempBlock, blockStack);
            }
            rootBlock.addChildBlock(tempBlock);

            if (resultBlock != null) {
                if (resultBlock instanceof EndProcessBlock) {
                    EndProcessBlock endProcessBlock = (EndProcessBlock) resultBlock;
                    if (endProcessBlock.isEndOf((ProcessBlock) tempBlock)) {
                        rootBlock.addChildBlock(resultBlock);
                    }
                }
            }
        }
        return null;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    /**
     * 模板文件的头信息
     * ---
     * layout: home
     * title: Home
     * ---
     */
    class Header {
        Map<String, String> params = new HashMap<String,String>();
        private String template;
        private TemplateResolver layoutResolver;

        Header(String template) {
            this.template = template;
            this.layoutResolver = SolidUtils.layoutResolver(configuration.getContext());
            this.analysis();
        }

        private void analysis() {
            StringConveyor conveyor = new StringConveyor(template);
            String content = conveyor.getBetween("---".concat(Constants.Wrap()), "---".concat(Constants.Wrap())).result();
            conveyor = new StringConveyor(content);
            while (conveyor.length() > 0) {
                String key = conveyor.getUntil(":", false).result();
                conveyor.getUntil(":", true);
                String value = conveyor.getUntil(Constants.Wrap(),false).result();
                conveyor.getUntil(Constants.Wrap(), true);
                params.put(key, value);
            }
        }

        boolean available() {
            return !params.isEmpty() && params.containsKey("layout") && !"null".equals(params.get("layout"));
        }

        SolidTemplate template() {
            Map<String,String> page = new HashMap<String, String>(params);
            page.remove("layout");
//            bind("page",page);
            page.entrySet().stream().forEach(e -> {
                bind(e.getKey(), e.getValue());
            });
            String layoutTemplateName = this.params.get("layout").trim();
            SolidTemplate layoutTemplate = layoutResolver.resolve(layoutTemplateName.trim());
            return layoutTemplate;
        }

    }
}
