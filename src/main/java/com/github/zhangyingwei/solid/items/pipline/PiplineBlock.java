package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.exception.SolidMethodNotFoundException;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.result.WowResult;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class PiplineBlock implements Block {
    private final SolidContext context;
    private String methodName;
    private String arg;
    protected String baseString;

    public PiplineBlock(String methodName, SolidContext context) {
        this.methodName = this.getMethod(methodName);
        String args = methodName.replaceAll(this.methodName,"");
        if (null == args || args.trim().length() > 0) {
            this.arg = args.trim();
        } else {
            this.arg = null;
        }
        this.context = context;
    }

    private String getMethod(String methodName) {
        return methodName.trim().split(" ")[0];
    }

    public PiplineBlock baseString(String baseString) {
        this.baseString = baseString;
        return this;
    }

    @Override
    public Block setFlag(boolean flag) {
        return this;
    }

    @Override
    public SolidResult render() {
        SolidMethod method = this.context.getMethod(methodName);
        Object result = null;
        if (null != method) {
            result = method.doFormate(this.baseString, SolidUtils.getFromPlaceholderOrNot(context,this.arg).getResult().toString());
        } else {
            try {
                throw new SolidMethodNotFoundException(methodName);
            } catch (SolidMethodNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (null == result) {
            return new WowResult();
        }
        return new StringResult(result);
    }
}
