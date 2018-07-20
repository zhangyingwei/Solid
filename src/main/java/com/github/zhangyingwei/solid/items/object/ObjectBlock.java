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
    private SolidContext context;
    private boolean flag = true;

    public ObjectBlock(SolidContext context, String template) {
        this.template = template;
        this.context = context;
    }


    @Override
    public Block setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    @Override
    public SolidResult<String> render() {
        if (!flag) {
            return new StringResult("");
        }
        String templatContent = template.trim().substring(leftMark.length()).substring(0, template.length() - leftMark.length() - rightMark.length());
        List<String> items = Arrays.stream(templatContent.trim().split("\\|")).map(item -> item.trim()).collect(Collectors.toList());
        String templateObject = items.remove(0);
        SolidResult result = SolidUtils.getFromPlaceholderOrNot(context, templateObject);
        String objectValue = result.getResult().toString();
        List<PiplineBlock> pipLineList = items.stream().map(item -> new PiplineBlock(item,context)).collect(Collectors.toList());
        for (PiplineBlock piplineBlock : pipLineList) {
            SolidResult pipLineResult = piplineBlock.baseString(objectValue).render();
            if (pipLineResult instanceof WowResult) {
                try {
                    throw new SolidException("pipline execute error:" + piplineBlock);
                } catch (SolidException e) {
                    e.printStackTrace();
                }
            } else {
                objectValue = pipLineResult.getResult().toString();
            }
        }
        return new StringResult(objectValue);
    }

    @Override
    public String toString() {
        return "Object(templte=" + template + ")";
    }
}
