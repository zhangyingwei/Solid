package com.github.zhangyingwei.solid.items.pipline;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.exception.SolidMethodNotFoundException;
import com.github.zhangyingwei.solid.items.Block;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.result.WowResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class PiplineBlock implements Block {
    private final SolidContext context;
    private String methodName;
    private String[] args;
    protected Object input;

    public PiplineBlock(String methodName, SolidContext context) {
        this.methodName = this.getMethod(methodName).trim();
        String argsTemplate = methodName.replaceAll(this.methodName,"").trim();
        if (this.methodName.endsWith(":")) {
            this.args = this.splitArgs(argsTemplate);
        } else {
            this.args = new String[0];
        }
        this.context = context;
    }

    private String[] splitArgs(String argsTemplate) {
        List<String> resultArgs = new ArrayList<String>();
        if (argsTemplate.replaceAll(" ", "").equals("\",\"")) {
            return new String[]{argsTemplate.trim()};
        }
        String[] argsArray = argsTemplate.split(",");
        String item = null;
        //TODO
        for (String itemArg : argsArray) {
            if (item != null
                    && item.trim().startsWith("\"")
                    && !item.trim().endsWith("\"")
                    && item.trim().equals("\"")
                    ) {
                item = item.concat(",").concat(itemArg);
            } else {
                item = itemArg;
            }
            if (item != null) {
                if ((item.trim().startsWith("\"") && item.trim().endsWith("\"")&& !item.trim().equals("\""))
                        || (!item.trim().startsWith("\"") && !item.trim().endsWith("\""))) {
                    resultArgs.add(item.trim());
                    item = null;
                }
            }
        }
        return resultArgs.toArray(new String[resultArgs.size()]);
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
            result = method.doFormate(this.input, this.getArgsResults());
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

    private Object[] getArgsResults() {
        return Arrays.stream(this.args).map(key -> SolidUtils.getFromPlaceholderOrNot(context, key).getResult()).toArray();
    }

    private String getRealMethodName(String methodName) {
        if (methodName.endsWith(":")) {
            return methodName.substring(0, methodName.length() - 1);
        }
        return methodName;
    }
}
