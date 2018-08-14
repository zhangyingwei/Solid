package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.common.StringHandler;
import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.items.object.ObjectBlock;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.template.Template;

import java.util.Arrays;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class IncludeProcessBlock extends ProcessBlock {
    private String url =Constants.INCLUDE_PATH;
    private Template includeTemplate;
    private String templateContent;

    public IncludeProcessBlock(String topMark, SolidContext context) {
        super(topMark, context);
        super.tag = Constants.TAG_INCLUDE;
        super.endTag = Constants.TAG_NO_END;
        this.init();
    }

    private void init() {
        String template = SolidUtils.subMarkToTemplate(SolidUtils.formateAsNomal(super.topMark), super.leftMark, super.rightMark);
        this.templateContent = this.url + SolidUtils.formateAsNomal(
                template.replaceFirst(super.tag, "")
        );
        String[] contents = SolidUtils.formateAsNomal(templateContent.trim()).split(" ");
        this.url = contents[0];
        this.includeTemplate = new Template(new SolidConfiguration(super.context), this.url);
    }

    @Override
    public SolidResult render() {
        this.bind();
        return new StringResult(this.includeTemplate.render());
    }

    private void bind() {
        templateContent = templateContent.substring(this.url.length());
        StringHandler contentHander = new StringHandler(templateContent);
        while (!contentHander.isOver()) {
            String key = contentHander.getUntil('=');
            contentHander.trimLeft();
            Object value = null;
            if (contentHander.startWith('"')) {
                value = contentHander.getFromTo('"','"');
            } else if (contentHander.startWith("{{")) {
                value = contentHander.getFromTo("{{","}}").trim();
                value = SolidUtils.getFromPlaceholderOrNot(super.context, value.toString());
                value = ((SolidResult) value).getResult();
            }
            super.context.bindArgs(key.trim(),value);
        }
    }
}
