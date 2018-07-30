package com.github.zhangyingwei.solid.items.object;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.exception.SolidException;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.items.pipline.PiplineBlock;
import com.github.zhangyingwei.solid.result.ObjectResult;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.result.WowResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class ObjectBlock implements Block {
    private String leftMark = Constants.OBJ_LEFTMARK;
    private String rightMark = Constants.OBJ_RIGHTMARK;
    private String template;
    private String templateContent;
    private SolidContext context;
    private boolean flag = true;
    private String key;
    private List<PiplineBlock> piplines;

    public ObjectBlock(SolidContext context, String template) {
        this.template = template;
        this.context = context;
        this.init();
    }


    @Override
    public Block setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    private void init() {
        this.template = SolidUtils.formateAsNomal(this.template);
        this.templateContent = SolidUtils.subMarkToTemplate(template.trim(), leftMark, rightMark);
        List<String> items = Arrays.stream(this.templateContent.split("\\|")).collect(Collectors.toList());
        this.key = items.remove(0).trim();
        this.piplines = this.bulidPipLines(items);
    }

    private List<PiplineBlock> bulidPipLines(List<String> items) {
        return items.stream().map(item -> new PiplineBlock(item,context)).collect(Collectors.toList());
    }

    @Override
    public SolidResult<String> render() {
        return new StringResult(renderObject().getResult().toString());
    }

    @Override
    public String text() {
        return this.template;
    }

    public SolidResult renderObject() {
        if (!flag) {
            return new StringResult("");
        }
        return getResult();
    }

    private SolidResult getResult() {
        SolidResult value = SolidUtils.getFromPlaceholderOrNot(this.context, this.key);
        for (PiplineBlock piplineBlock : this.piplines) {
            SolidResult pipLineResult = piplineBlock.input(value.getResult()).render();
            value = pipLineResult;
        }
        return value;
    }

    @Override
    public String toString() {
        return "Object(templte=" + template + ")";
    }
}
