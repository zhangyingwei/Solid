package com.github.zhangyingwei.solid.common;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class Constants {
    public static String INCLUDE_PATH = "_includes/";
    public static final String CHAR_SET_UTF_8 = "UTF-8";
    public static final String KEY_TEMPLATE_CACHE = "template_cache";
    public static final String CONTENT_TYPE = "text/html; charset=utf-8";
    public static String TEMPLATE_SUFFIX = null;
    public static String TEMPLATE_PREFIX = null;
    public static Boolean TEMPLATE_CACHE = true; // 是否缓存模板
    public static long KEY_TEMPLATE_TIMEOUT_MILLISECOND = 10000; // 10s
    public static String OBJ_LEFTMARK = "{{";
    public static String OBJ_RIGHTMARK = "}}";
    public static String PROCESS_LEFTMARK = "{%";

    public static String PROCESS_RIGHTMARK = "%}";


    public static final String TAG_RAW = "raw";
    public static final String TAG_RAW_END = "endraw";
    public static final String TAG_NO_END = "No";
    public static final String TAG_ASSIGN = "assign";
    public static final String TAG_INCLUDE = "include";
    public static final String TAG_FOR = "for";
    public static final String TAG_FOR_END = "endfor";
    public static final String TAG_IF = "if";
    public static final String TAG_IF_END = "endif";
    public static final String TAG_ELSE_IF = "elsif";
    public static final String TAG_ELSIF_END = "end of ensif hahahah$#%#$%";
    public static final String TAG_ELSE = "else";
}
