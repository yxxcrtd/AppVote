package com.chinaedustar.app.vote.model;

import com.chinaedustar.app.common.ConfigEnum;
import com.chinaedustar.app.common.Configuration;

/**
 * 统一配置对象设置。
 * 
 * @author
 */
public class ConfigModel implements TemplateModelObject {

    @Override
    public String getVariableName() {
        return "Config";
    }

    /** sns地址 */
    public String getSnsUrl() {
        return Configuration.getValue("honeybee.siteUrl", ConfigEnum.ESI);
    }

    /** 统一用户地址 */
    public String getSsoUrl() {
        return Configuration.getValue("octopus.siteUrl", ConfigEnum.ESI);
    }
    /**
     * 默认的头像地址
     * 
     * @return
     */
    public String getSnsDefaultUserIconUrl() {
        return getSnsUrl() + "/images/demoimg/default1.gif";
    }

    /**
     * 默认的圈子图片地址
     * 
     * @return
     */
    public String getSnsDefaultGroupIconUrl() {
        return getSnsUrl() + "/images/demoimg/default_group_avatar.gif";
    }

    /**
     * 默认的视频缩略图地址
     * 
     * @return
     */
    public String getSnsDefaultVideoUrl() {
        return getSnsUrl() + "/images/demoimg/default_video.png";
    }
}
