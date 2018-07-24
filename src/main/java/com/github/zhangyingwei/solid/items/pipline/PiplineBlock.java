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
    protected Object input;

    public PiplineBlock(String methodName, SolidContext context) {
        this.methodName = this.getMethod(methodName).trim();
        String args = methodName.replaceAll(this.methodName,"");
        if (this.methodName.endsWith(":")) {
            this.arg = args.trim();
        } else {
            this.arg = null;
        }
        this.context = context;
    }

    private String getMethod(String methodName) {
        return methodName.trim().split(" ")[0];
    }

    public PiplineBlock input(Object input) {
        this.input = input;
        return this;
    }

    @Override
    public Block setFlag(boolean flag) {
        return this;
    }

    @Override
    public SolidResult render() {
        SolidMethod method = this.context.getMethod(this.getRealMethodName(methodName));
        Object result = null;
        if (null != method) {
            result = method.doFormate(this.input, SolidUtils.getFromPlaceholderOrNot(context,this.arg).getResult());
        } else {
            try {
                throw new SolidMethodNotFoundException(methodName);
            } catch (SolidMethodNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (null == result) {
            return new WowResult(this.input + "");
        }
        return new StringResult(result);
    }

    private String getRealMethodName(String methodName) {
        if (methodName.endsWith(":")) {
            return methodName.substring(0, methodName.length() - 1);
        }
        return methodName;
    }
}
