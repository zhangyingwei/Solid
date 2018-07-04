package com.github.zhangyingwei.solid.template;

import com.github.zhangyingwei.solid.config.Configuration;
import com.github.zhangyingwei.solid.items.Block;

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
//        blocks.stream().map(block -> block.render().getResult()).forEach(res -> {
//            resultText.append(res);
//        });
        return resultText.toString();
    }
}
