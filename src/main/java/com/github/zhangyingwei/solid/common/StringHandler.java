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

    public String getFromTo(char from,char to) {
        getUntil(from);
        for (int i = index; i < template.length(); i++) {
            if (template.charAt(i) == to) {
                i += 1;
                String result = template.substring(index - 1, i);
                index = i;
                return result;
            }
        }
        String result = template.substring(index);
        index = template.length();
        return result;
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

    public Boolean isOver() {
        return index == template.length();
    }

    public static void main(String[] args) {
        String template = "\"hello world\",123";
        StringHandler handler = new StringHandler(template.substring(1));
        System.out.println(handler.getUntil('"'));
        System.out.println(handler.getUntilFrom(1,'3'));
    }

    public boolean startWith(char c) {
        for (int i = index; i < template.length(); i++) {
            if (template.charAt(i) != ' ') {
                return template.charAt(i) == c;
            }
        }
        return false;
    }

    public StringHandler resetIndex(int index) {
        this.index = index;
        return this;
    }
}
