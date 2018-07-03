package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.result.SolidResult;

import java.util.List;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class ForProcessBlock extends ProcessBlock {

    private String leftMark = Constants.PROCESS_LEFTMARK;
    private String rightMark = Constants.PROCESS_RIGHTMARK;
    private String itemName;
    private String sourcesName;
    private Object sources;

    public ForProcessBlock(String topMark, SolidContext context) {
        super(topMark,context);
        this.getNames(leftMark);
    }

    private void getNames(String leftMark) {
        String forName = leftMark.trim().replaceFirst(leftMark, "").replace(rightMark, "").trim();
        String[] itemAndObject = forName.split("in");
        this.itemName = itemAndObject[0];
        this.sourcesName = itemAndObject[1];
        this.sources = SolidUtils.getValueFromContext(sourcesName, super.context);
    }

    @Override
    public SolidResult render() {
        return null;
    }
}
