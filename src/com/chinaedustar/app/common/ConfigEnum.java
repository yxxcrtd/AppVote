package com.chinaedustar.app.common;

import com.chinaedustar.common.configure.ConfigEnumInter;

public enum ConfigEnum implements ConfigEnumInter {
    ESI("ESI", "esi");
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
