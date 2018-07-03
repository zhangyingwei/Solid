package com.github.zhangyingwei.solid.exception;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class SolidException extends Exception {
    public SolidException() {
        super();
    }

    public SolidException(String message) {
        super(message);
    }

    public SolidException(String message, Throwable cause) {
        super(message, cause);
    }

    public SolidException(Throwable cause) {
        super(cause);
    }

    protected SolidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
