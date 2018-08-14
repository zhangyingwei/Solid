package com.github.zhangyingwei.solid.common;

public class StringHandler {
    private String template;
    private int index = 0;

    public StringHandler(String template) {
        this.template = template;
    }

    public String getUntilFrom(int index, char until) {
        this.index += index;
        return getUntil(until);
    }

    //TODO 这里还是有些问题
    public String getFromTo(char from,char to) {
        getUntil(from);
        return getUntil(to);
    }

    //TODO 这里同上 ，主要是逻辑上的问题
    public String getFromTo(String from,String to) {
        getUntil(from);
        return getUntil(to);
    }

    public String getUntil(char until) {
        for (int i = index; i < template.length(); i++) {
            if (template.charAt(i) == until) {
                String result = template.substring(index, i);
                index = i + 1;
                return result;
            }
        }
        String result = template.substring(index);
        index = template.length();
        return result;
    }

    public String getUntil(String until) {
        for (int i = index; i < template.length(); i++) {
            String subTemplate = template.substring(i);
            int index = subTemplate.indexOf(until);
            this.index += index;
            this.index += until.length();
            return subTemplate.substring(0, index);
        }
        String result = template.substring(index);
        index = template.length();
        return result;
    }

    public Boolean isOver() {
        return index == template.length();
    }

    public static void main(String[] args) {
        String template = "\"hello world\",12    3      aabbccddeeffgghhiijjkkll";
        StringHandler handler = new StringHandler(template.substring(1));
        System.out.println(handler.getUntil('"'));
        System.out.println(handler.getUntilFrom(1,'3'));
        System.out.println(handler.string());
//        System.out.println(handler.trimLeft().string());
        System.out.println(handler.getUntil("cc"));
        System.out.println(handler.getUntil("ii"));
    }

    public boolean startWith(char c) {
        for (int i = index; i < template.length(); i++) {
            if (template.charAt(i) != ' ') {
                return template.charAt(i) == c;
            }
        }
        return false;
    }

    public boolean startWith(String str) {
        for (int i = index; i < template.length(); i++) {
            String subTemplate = template.substring(i);
            return subTemplate.startsWith(str);
        }
        return false;
    }

    public StringHandler resetIndex(int index) {
        this.index = index;
        return this;
    }

    public StringHandler trimLeft() {
        while (template.charAt(this.index) == ' ') {
            this.index++;
        }
        return this;
    }

    public String string() {
        return this.template.substring(this.index);
    }
}
