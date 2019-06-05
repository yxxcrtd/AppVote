package com.chinaedustar.app.vote.model;



/**
 * 被用在模板时候的模型对象接口定义.
 * 
 *
 */
public interface TemplateModelObject extends ModelObject{

    /**
     * 返回模板中变量的名字，如 'SiteUrl'
     * 
     * @return
     */
    public String getVariableName();
}
