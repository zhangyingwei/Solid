package com.github.zhangyingwei.solid.items.process;

import com.github.zhangyingwei.solid.SolidContext;
import com.github.zhangyingwei.solid.common.Constants;
import com.github.zhangyingwei.solid.common.SolidUtils;
import com.github.zhangyingwei.solid.result.SolidResult;
import com.github.zhangyingwei.solid.result.StringResult;
import com.github.zhangyingwei.solid.result.WowResult;
import com.github.zhangyingwei.solid.template.TemplateParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class IFProcessBlock extends ProcessBlock {
    private String leftMark = Constants.PROCESS_LEFTMARK;
    private String rightMark = Constants.PROCESS_RIGHTMARK;
    private List<IfItem> ifitems = new ArrayList<IfItem>();
    private List<IfControlItem> controlItems = new ArrayList<IfControlItem>();

    public IFProcessBlock(String topMark, SolidContext context) {
        super(SolidUtils.removeExtraSpaces(topMark.trim()).toLowerCase(), context);
        super.tag = Constants.TAG_IF;
        super.endTag = Constants.TAG_IF_END;
        this.splitTemplateToIfItems();
    }

    private void splitTemplateToIfItems() {
        String itemsLine = super.topMark.substring(super.tag.length());
        TemplateParser.TemplateFlow templateFlow = new TemplateParser.TemplateFlow(itemsLine);
        while (templateFlow.isNotEmpty()) {
            if (templateFlow.startWith("and")) {
                templateFlow.pull(3);
                controlItems.add(new IfControlItem("and"));
            } else if (templateFlow.startWith("or")) {
                templateFlow.pull(2);
                controlItems.add(new IfControlItem("or"));
            } else {
                StringBuilder item = new StringBuilder();
                while (templateFlow.isNotEmpty()) {
                    if (!templateFlow.startWith("and") && !templateFlow.startWith("or")) {
                        item.append(templateFlow.pull(1));
                    } else {
                        break;
                    }
                }
                ifitems.add(new IfItem(item.toString().trim()));
            }
        }
    }

    @Override
    public SolidResult render() {
        StringBuilder result = new StringBuilder("");
        if (this.valid()) {
            super.childsResult().stream().map(item -> item.getResult()).forEach(child -> {
                result.append(child.toString());
            });
        }
        return new StringResult(result);
    }

    /**
     * 验证条件是否为 true
     * @return
     */
    private boolean valid() {
        IfItemCompare firstItem = this.ifitems.remove(0).valid();
        for (int i = 0; i < controlItems.size(); i++) {
            IfControlItem control = controlItems.get(i);
            firstItem = control.compare(firstItem, ifitems.get(i).valid());
        }
        return firstItem.flag;
    }

    /**
     * 逻辑判断器
     */
    class IfItemCompare {
        Boolean flag;

        public IfItemCompare(Boolean flag) {
            this.flag = flag;
        }

        IfItemCompare andWith(IfItemCompare other) {
            return new IfItemCompare(this.flag && other.flag);
        }

        IfItemCompare orWith(IfItemCompare other) {
            return new IfItemCompare(this.flag || other.flag);
        }
    }

    abstract class SolidIfItem {
        String template;
        public SolidIfItem(String template) {
            this.template = template;
        }
    }
    /**
     * 条件元素类
     */
    class IfItem extends SolidIfItem {
        /**
         * 第一个对象
         */
        Object first;
        /**
         * 第二个对象
         */
        Object second;
        /**
         * 条件符号
         */
        String symbol;

        public IfItem(String template) {
            super(template.trim());
        }

        /**
         * 判断是 a == b ; a > b ; a < b
         * 还是 if a.b
         */
        //TODO
        void formate() {
            String templateText = template.replaceAll(" ", "");
            if (templateText.contains("=") || templateText.contains(">") || templateText.contains("<")) {
                String spliter = "";
                if (templateText.contains("==")) {
                    spliter = "==";
                } else if (templateText.contains("!=")) {
                    spliter = "!=";
                } else if (templateText.contains(">")) {
                    spliter = ">";
                } else if (templateText.contains("<")) {
                    spliter = "<";
                } else if (templateText.contains(">=")) {
                    spliter = ">=";
                } else if (templateText.contains("<=")) {
                    spliter = "<=";
                }
                String[] params = templateText.split(spliter);
                this.first = params[0];
                this.second = params[1];
                this.symbol = spliter;
            } else {
                this.first = SolidUtils.getObjectFromContext(super.template, context);
                this.second = null;
            }
        }

        /**
         * 判断一个条件是否成立
         *
         * @return
         */
        //TODO
        IfItemCompare valid() {
            if (first instanceof SolidResult) {
                return new IfItemCompare(!(first instanceof WowResult));
            } else {
                if (this.symbol.equals("==")) {
                    return new IfItemCompare(this.getFirst().equals(this.getSecond()));
                } else if (this.symbol.equals("!=")) {
                    return new IfItemCompare(!this.getFirst().equals(this.getSecond()));
                } else if (this.symbol.equals(">")) {
                    return new IfItemCompare(
                            this.getFirst().toString().compareTo(this.getSecond().toString()) > 0
                    );
                } else if (this.symbol.equals("<")) {
                    return new IfItemCompare(
                            this.getFirst().toString().compareTo(this.getSecond().toString()) < 0
                    );
                } else if (this.symbol.equals(">=")) {
                    new IfItemCompare(
                            this.getFirst().toString().compareTo(this.getSecond().toString()) > 0
                    ).orWith(
                            new IfItemCompare(this.getFirst().equals(this.getSecond()))
                    );
                } else if (this.symbol.equals("<=")) {
                    new IfItemCompare(
                            this.getFirst().toString().compareTo(this.getSecond().toString()) < 0
                    ).orWith(
                            new IfItemCompare(this.getFirst().equals(this.getSecond()))
                    );
                }
            }
            return new IfItemCompare(false);
        }

        public Object getFirst() {
            return first;
        }

        public Object getSecond() {
            return second;
        }
    }

    /**
     * 逻辑控制类
     */
    class IfControlItem extends SolidIfItem {
        public IfControlItem(String template) {
            super(template.trim().toLowerCase());
        }

        IfItemCompare compare(IfItemCompare before, IfItemCompare after) {
            if ("and".equals(super.template)) {
                return before.andWith(after);
            } else if ("or".equals(super.template)) {
                return before.orWith(after);
            }
            return new IfItemCompare(false);
        }
    }
}
