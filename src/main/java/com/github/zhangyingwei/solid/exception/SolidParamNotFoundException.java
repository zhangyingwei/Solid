package com.github.zhangyingwei.solid.exception;

/**
 * @author zhangyw
 * @date 2018/7/3
 */
public class SolidParamNotFoundException extends Exception {
    public SolidParamNotFoundException() {
        super();
    }

    public SolidParamNotFoundException(String message) {
        super(message);
    }

    public SolidParamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SolidParamNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SolidParamNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
