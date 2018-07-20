package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
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
public class ElsIFProcessBlock extends IFProcessBlock {

    public ElsIFProcessBlock(String topMark, SolidContext context) {
        super(topMark, context);
        super.endTag = Constants.TAG_ELSIF_END;
        super.tag = Constants.TAG_ELSE_IF;
    }
}
