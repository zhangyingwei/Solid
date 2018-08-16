package com.github.zhangyingwei.solid.common;

import java.util.ArrayList;
import java.util.List;

public class StringConveyor {
    private int globalToIndex = 0;
    private int globalFromIndex = 0;
    private String template;

    public StringConveyor(String template) {
        this.template = template;
    }

    public StringConveyor getFromTo(String from,String to) {
        getUntil(from, true);
        if (this.globalToIndex == this.templateLength()) {
            this.globalToIndex = this.globalFromIndex;
            return this;
        }
        getUntil(to, true);
        this.globalFromIndex -= from.length();
        return this;
    }

    public StringConveyor getBetween(String from, String to) {
        getUntil(from, true);
        getUntil(to, false);
        if (this.globalToIndex == this.templateLength()) {
            this.globalToIndex = this.globalFromIndex;
            return this;
        }
        return this;
    }

    /**
     * 获取直到 unitStr 之前的字符串
     * contain=true 包含untilStr
     * contain=false 不包含untilStr
     * @param untilStr
     * @param contain
     * @return
     */
    public StringConveyor getUntil(String untilStr,Boolean contain) {
        this.globalFromIndex = this.globalToIndex;
        int strLength = untilStr.length();
        int length = this.templateLength();
        for (int i = this.globalToIndex; i < length; i++) {
            boolean eq = true;
            for (int j = 0; j < strLength; j++) {
                char tempChar = this.template.charAt(i + j);
                if (untilStr.charAt(j) != tempChar) {
                    eq = false;
                    break;
                }
            }
            if (eq) {
                this.globalToIndex = i;
                if (contain) {
                    this.globalToIndex += strLength;
                }
                break;
            }
        }
        if (this.globalFromIndex == this.globalToIndex) {
            this.globalToIndex = this.templateLength();
        }
        return this;
    }

    private int templateLength() {
        return this.template.length();
    }

    /**
     * 返回剩余字符串的长度
     * @return
     */
    public int length() {
        return this.template.length() - this.globalToIndex;
    }

    /**
     * 输出剩余的内容
     * @return
     */
    public String string() {
        return this.template.substring(this.globalToIndex);
    }

    public String result() {
        return this.template.substring(this.globalFromIndex, this.globalToIndex);
    }

    @Override
    public String toString() {
        return this.string();
    }

    public boolean startWith(String str) {
        return this.string().startsWith(str);
    }

    public StringConveyor trimLeft() {
        if (this.length() > 0) {
            char cut = template.charAt(this.globalToIndex);
            while (cut == ' ' && this.length() > 0) {
                this.globalToIndex++;
                if (this.length() > 0) {
                    cut = template.charAt(this.globalToIndex);
                }
            }
        }
        return this;
    }
}
