package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.items.object.ObjectBlock;
import com.github.zhangyingwei.solid.result.NumResult;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.result.WowResult;
import com.github.zhangyingwei.solid.template.TemplateParser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class AssignProcessBlock extends ProcessBlock {
    private String key;
    private String value;
    private String template;
    public AssignProcessBlock(String topMark, SolidContext context) {
        super(topMark, context);
        super.tag = Constants.TAG_ASSIGN;
        super.endTag = Constants.TAG_NO_END;
        this.init();
    }

    private void init() {
        this.template = SolidUtils.subMarkToTemplate(super.topMark, super.leftMark, super.rightMark);
        this.template = SolidUtils.formateAsNomal(this.template);
        String templateContent = SolidUtils.formateAsNomal(this.template.replaceFirst(this.tag, ""));
        int index = templateContent.indexOf("=");
        this.key = SolidUtils.formateAsNomal(templateContent.substring(0, index));
        this.value = SolidUtils.formateAsNomal(templateContent.substring(index + 1));
    }

    @Override
    public SolidResult render() {
        super.context.bindArgs(this.key, new ObjectBlock(context,SolidUtils.bulidObjectTemplateFromTemplateContent(this.value)).renderObject().getResult());
        return new StringResult("");
    }

    @Override
    public String text() {
        return this.topMark;
    }
}
