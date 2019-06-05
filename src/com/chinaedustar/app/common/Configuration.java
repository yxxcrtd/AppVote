package com.chinaedustar.app.common;

import com.chinaedustar.common.configure.BaseConfig;

/**
 * 系统级配置
 * 
 * @author liangzh
 *
 */
public class Configuration extends BaseConfig {

    public static void main(String[] args) {
        System.out.println(Configuration.getValue("octopus.clientServerUrl", ConfigEnum.ESI));
    }
}

/**
 * 配置类型枚举
 * 
 * @author liangzh
 *
 * 2013年8月29日 上午8:24:28
 *
 */
