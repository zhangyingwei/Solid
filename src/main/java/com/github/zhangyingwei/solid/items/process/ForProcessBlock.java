package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date 2018/7/3
 * for  控制流
 */
public class ForProcessBlock extends ProcessBlock {

    private String leftMark = Constants.PROCESS_LEFTMARK;
    private String rightMark = Constants.PROCESS_RIGHTMARK;
    private String itemName;
    private String sourcesName;
    private Object sources;

    public ForProcessBlock(String topMark, SolidContext context) {
        super(topMark,context);
        super.tag = Constants.TAG_FOR;
        super.endTag = Constants.TAG_FOR_END;
        this.getNames(topMark);
    }

    private void getNames(String topMark) {
        String forName = topMark.trim().substring(
                Constants.PROCESS_LEFTMARK.length(),
                topMark.trim().length() - Constants.PROCESS_RIGHTMARK.length()
        ).trim();
        String[] itemAndObject = forName.split("in");
        this.itemName = itemAndObject[0].replace("for","").trim();
        this.sourcesName = itemAndObject[1].trim();
        SolidResult<Object> sourcesResult = SolidUtils.getFromPlaceholderOrNot(super.context, sourcesName);
        this.sources = sourcesResult.getResult();
    }

    @Override
    public SolidResult render() {
        List<SolidResult> childs = new ArrayList<SolidResult>();
        if (this.sources instanceof Collection) {
            Collection collection = (Collection) this.sources;
            for (Object object : collection) {
                context.bindArgs(this.itemName, object);
                childs.addAll(super.childsResult());
                context.unbindArgs(this.itemName);
            }
        } else {
            Object[] objects = (Object[]) this.sources;
            for (Object object : objects) {
                context.bindArgs(this.itemName, object);
                childs.addAll(super.childsResult());
                context.unbindArgs(this.itemName);
            }
        }
        StringBuilder sBuilder = new StringBuilder();
        childs.stream().forEach(child -> {
            sBuilder.append(child.getResult());
        });
        return new StringResult(sBuilder.toString());
    }
}
