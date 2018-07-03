package com.github.zhangyingwei.solid.exception;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class SolidMethodNotFoundException extends Exception {
    public SolidMethodNotFoundException() {
        super();
    }

    public SolidMethodNotFoundException(String message) {
        super(message);
    }

    public SolidMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SolidMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SolidMethodNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
