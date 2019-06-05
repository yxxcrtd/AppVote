package com.chinaedustar.app.vote.common.exception;

/**
 * 受查异常基类
 * 
 * @author liangzh
 *
 */
public class ProException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProException(String message) {
        super(message);
    }

    public ProException(String message, Throwable e) {
        super(message, e);
    }
}