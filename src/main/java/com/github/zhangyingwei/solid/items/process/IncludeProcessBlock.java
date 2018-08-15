package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.common.StringConveyor;
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
    }

    private void init() {
        String template = SolidUtils.subMarkToTemplate(SolidUtils.formateAsNomal(super.topMark), super.leftMark, super.rightMark);
        StringConveyor conveyor = new StringConveyor(template);
        conveyor.getUntil(super.tag,true);
        this.url = this.url + this.getIncludeTemplateUrl(conveyor);
        this.templateContent = conveyor.trimLeft().string();
        this.includeTemplate = new Template(new SolidConfiguration(super.context), this.url);
    }

    private String getIncludeTemplateUrl(StringConveyor conveyor) {
        String result;
        conveyor.trimLeft();
        if (conveyor.startWith("{{")) {
            String key = conveyor.getBetween("{{", "}}").result().trim();
            result = SolidUtils.getFromPlaceholderOrNot(context, key).getResult().toString();
            conveyor.getUntil("}}", true);
        } else {
            result = conveyor.getUntil(" ", false).result().trim();
        }
        return result;
    }

    @Override
    public SolidResult render() {
        this.init();
        this.bind();
        return new StringResult(this.includeTemplate.render());
    }

    /**
     * 解析 include 中的参数并绑定到 context 中
     */
    private void bind() {
        StringConveyor conveyor = new StringConveyor(templateContent);
        while (conveyor.length() > 0) {
            String key = conveyor.getUntil("=",false).result().trim();
            conveyor.getUntil("=", true);
            conveyor.trimLeft();
            Object value = null;
            if (conveyor.startWith("\"")) {
                value = conveyor.getFromTo("\"","\"").result();
            } else if (conveyor.trimLeft().startWith("{{")) {
                value = conveyor.getBetween("{{", "}}").result().trim();
                value = SolidUtils.getFromPlaceholderOrNot(super.context, value.toString());
                value = ((SolidResult) value).getResult();
            }
            super.context.bindArgs(key.trim(), value);
        }
    }
}
