package com.chinaedustar.app.vote.model;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.ServletContextAware;


@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ModelManagerImpl implements ServletContextAware {

    /** ServletContext */
    private ServletContext servlet_ctxt;

    /** 被共享的模型对象集合，在 Spring 中注入 */
    private List<TemplateModelObject> shared_model = null;

    /** 被共享的模型对象集合，在 Spring 中注入 */
    public void setSharedModels(List<TemplateModelObject> value) {
        this.shared_model = value;
    }

    public List<TemplateModelObject> getSharedModels() {
        return this.shared_model;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servlet_ctxt = servletContext;
    }

    /** 初始化方法，在 Spring 配置被用作 init-method  @PostConstruct*/    
    public void init() {
        if (shared_model != null) {
            putServletContextSharedModel();
        }
    }

    /** 关闭方法，在 Spring 中被配置为 destroy-method @PreDestroy*/    
    public void destroy() {
        this.shared_model = null;
    }

    /** 将支持 Webapp 级别的全局共享变量放到 servlet_ctxt 里面, 从而任何 struts freemarker 模板均可访问该对象 */
    private void putServletContextSharedModel() {
        for (int i = 0; i < this.shared_model.size(); ++i) {
            Object o = this.shared_model.get(i);
            if (o instanceof TemplateModelObject) {
                TemplateModelObject model = (TemplateModelObject) o;
                this.servlet_ctxt.setAttribute(model.getVariableName(), model);
            }
        }
    }
}