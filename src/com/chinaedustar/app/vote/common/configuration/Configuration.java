package com.chinaedustar.app.vote.common.configuration;

import com.chinaedustar.common.configure.BaseConfig;
import com.chinaedustar.common.configure.ConfigEnumInter;

/**
 * 系统级配置
 * 
 * @author liangzh
 *
 */
public class Configuration extends BaseConfig {

    public static void main(String[] args) {
        System.out.println(getValue("0001", ConfigEnum.TEST));
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
enum ConfigEnum implements ConfigEnumInter {
    TEST("测试", "properties/tips/test");
    private String name;
    private String value;

    // 构造方法
    private ConfigEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
