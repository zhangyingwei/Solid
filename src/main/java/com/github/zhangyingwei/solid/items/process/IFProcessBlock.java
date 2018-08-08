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
public class IFProcessBlock extends ProcessBlock {
    private List<IfItem> ifitems = new ArrayList<IfItem>();
    private List<IfControlItem> controlItems = new ArrayList<IfControlItem>();
    private List<ElsIFProcessBlock> elseIfBlock = new ArrayList<ElsIFProcessBlock>();

    public IFProcessBlock(String topMark, SolidContext context) {
        super(SolidUtils.formateAsNomal(topMark), context);
        super.tag = Constants.TAG_IF;
        super.endTag = Constants.TAG_IF_END;
        this.splitTemplateToIfItems();
    }

    private void splitTemplateToIfItems() {
        String itemsLine = SolidUtils.subMarkToTemplate(super.topMark,super.leftMark,super.rightMark);
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
        if (super.flag) {
            super.childsResult(this.valid()).stream().map(item -> item.getResult()).forEach(child -> {
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
        IfItemCompare firstItem = this.ifitems.get(0).valid();
        for (int i = 0; i < controlItems.size(); i++) {
            IfControlItem control = controlItems.get(i);
            firstItem = control.compare(firstItem, ifitems.get(i + 1).valid());
        }
        return firstItem.flag;
    }

    /**
     * add else if block
     * @param elseBlock
     */
    public void addElseIfBlock(ElsIFProcessBlock elseBlock) {
        this.elseIfBlock.add(elseBlock);
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
        SolidResult first;
        /**
         * 第二个对象
         */
        SolidResult second;
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
            String templateText = template.replaceFirst(tag,"").replaceAll(" ", "");
            if (templateText.contains("=") || templateText.contains(">") || templateText.contains("<")) {
                String spliter = "";
                if (templateText.contains("==")) {
                    spliter = "==";
                } else if (templateText.contains("!=")) {
                    spliter = "!=";
                }else if (templateText.contains(">=")) {
                    spliter = ">=";
                } else if (templateText.contains("<=")) {
                    spliter = "<=";
                } else if (templateText.contains(">")) {
                    spliter = ">";
                } else if (templateText.contains("<")) {
                    spliter = "<";
                }
                String[] params = templateText.split(spliter);
                this.first = SolidUtils.getFromPlaceholderOrNot(context, params[0]);
                this.second = SolidUtils.getFromPlaceholderOrNot(context, params[1]);
                this.symbol = spliter;
            } else {
                this.first = SolidUtils.getFromPlaceholderOrNot(context, super.template);
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
            this.formate();
            if (second == null) {
                if (first instanceof WowResult) {
                    return new IfItemCompare(false);
                } else {
                    return new IfItemCompare(true);
                }
            } else if (first instanceof SolidResult && second instanceof SolidResult) {
                if (first instanceof WowResult || second instanceof WowResult) {
                    return new IfItemCompare(false);
                }else if (this.getFirst() instanceof NumResult || this.getSecond() instanceof NumResult) {
                    BigDecimal bfrist = new BigDecimal(this.getFirst().getResult().toString());
                    BigDecimal bsecond = new BigDecimal(this.getSecond().getResult().toString());
                    if (this.symbol.equals("==")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == 0
                        );
                    } else if (this.symbol.equals("!=")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) != 0
                        );
                    } else if (this.symbol.equals(">")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == 1
                        );
                    } else if (this.symbol.equals("<")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == -1
                        );
                    } else if (this.symbol.equals(">=")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == 1
                        ).orWith(
                                new IfItemCompare(bfrist.compareTo(bsecond) == 0)
                        );
                    } else if (this.symbol.equals("<=")) {
                        return new IfItemCompare(
                                bfrist.compareTo(bsecond) == -1
                        ).orWith(
                                new IfItemCompare(bfrist.compareTo(bsecond) == 0)
                        );
                    }
                } else {
                    if (this.symbol.equals("==")) {
                        return new IfItemCompare(this.getFirst().getResult().equals(this.getSecond().getResult()));
                    } else if (this.symbol.equals("!=")) {
                        return new IfItemCompare(!this.getFirst().getResult().equals(this.getSecond().getResult()));
                    } else if (this.symbol.equals(">")) {
                        return new IfItemCompare(
                                this.getFirst().getResult().toString().compareTo(this.getSecond().getResult().toString()) > 0
                        );
                    } else if (this.symbol.equals("<")) {
                        return new IfItemCompare(
                                this.getFirst().getResult().toString().compareTo(this.getSecond().getResult().toString()) < 0
                        );
                    } else if (this.symbol.equals(">=")) {
                        return new IfItemCompare(
                                this.getFirst().getResult().toString().compareTo(this.getSecond().getResult().toString()) > 0
                        ).orWith(
                                new IfItemCompare(this.getFirst().getResult().equals(this.getSecond().getResult()))
                        );
                    } else if (this.symbol.equals("<=")) {
                        return new IfItemCompare(
                                this.getFirst().getResult().toString().compareTo(this.getSecond().getResult().toString()) < 0
                        ).orWith(
                                new IfItemCompare(this.getFirst().getResult().equals(this.getSecond().getResult()))
                        );
                    }
                }
            }
            return new IfItemCompare(false);
        }

        public SolidResult getFirst() {
            return first;
        }

        public SolidResult getSecond() {
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
