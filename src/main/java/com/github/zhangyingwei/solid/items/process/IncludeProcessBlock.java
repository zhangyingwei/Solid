package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.config.SolidConfiguration;
import com.github.zhangyingwei.solid.items.object.ObjectBlock;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.template.Template;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class IncludeProcessBlock extends ProcessBlock {
    private String url ="_includes/";
    private Template includeTemplate;

    public IncludeProcessBlock(String topMark, SolidContext context) {
        super(topMark, context);
        super.tag = Constants.TAG_INCLUDE;
        super.endTag = Constants.TAG_NO_END;
        this.init();
    }

    private void init() {
        String template = SolidUtils.subMarkToTemplate(SolidUtils.formateAsNomal(super.topMark), super.leftMark, super.rightMark);
        this.url = this.url + SolidUtils.formateAsNomal(
                template.replaceFirst(super.tag, "")
        );
        this.includeTemplate = new Template(new SolidConfiguration(context), this.url);
    }

    @Override
    public SolidResult render() {
        return new StringResult(this.includeTemplate.render());
    }
}
