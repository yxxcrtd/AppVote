package com.chinaedustar.app.vote.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志基类
 * 
 * @author liangzh
 *
 */
public class ProLogger {
    private Logger log = null;

    public ProLogger() {
        this.log = LoggerFactory.getLogger("convert");
    }

    public ProLogger(Class<?> clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }

    public void debug(String message) {
        this.log.debug(message);
    }

    public void info(String message) {
        this.log.info(message);
    }

    public void warn(String message) {
        this.log.warn(message);
    }

    public void error(String message) {
        this.log.error(message);
    }

    public void error(String message, Throwable e) {
        this.log.error(message, e);
    }
}