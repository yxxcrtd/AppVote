package com.chinaedustar.app.vote.common.exception;

/**
 * 运行时异常基类
 * 
 * @author liangzh
 *
 */
public class ProRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProRuntimeException(String message) {
        super(message);
    }

    public ProRuntimeException(String message, Throwable e) {
        super(message, e);
    }
}