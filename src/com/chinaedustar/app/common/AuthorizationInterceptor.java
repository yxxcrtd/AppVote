package com.chinaedustar.app.common;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.chinaedustar.common.utils.JsonUtils;
import com.chinaedustar.honeybee.domain.SUserBaseInfo;
import com.chinaedustar.octopus.sso.util.SsoUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登录验证拦截器。实现登录验证，如果没有登录，则转向登录页面。
 * 
 * @author mxh
 * 
 */
public class AuthorizationInterceptor extends AbstractInterceptor {
    /**
     * 默认的序列化版本
     */
    private static final long serialVersionUID = 8331445869756783360L;
    private HttpServletResponse response;
    private HttpServletRequest request;
    
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext ctx = actionInvocation.getInvocationContext();
        Map<String, Object> session = ctx.getSession();
        request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
        // 本地session记录
        SUserBaseInfo snsUser = (SUserBaseInfo) session.get(AppConst.SESSION_USER_KEY);
        // System.out.println("snsUser=" + snsUser);
        if (snsUser != null) {
            // 需要检查memcached是否已经注销登录，如果注销了，则不能继续使用了
            boolean memcachedIsOnline = SsoUtils.isOnLine(snsUser.getUserId());
            // System.out.println("memcachedLogout=" + memcachedLogout);
            if (!memcachedIsOnline) {
                // 如果统一登录注销了，则这里也进行同步注销。
                session.remove(AppConst.SESSION_SSO_SESSIONID);
                session.remove(AppConst.SESSION_USER_KEY);
            } else {
                // 如果都没有注销，则进行执行其他的方法
                // ServletContext context = (ServletContext)
                // actionContext.get(StrutsStatics.SERVLET_CONTEXT);
                // ApplicationContext ctx =
                // WebApplicationContextUtils.getWebApplicationContext(context);
                ServletContext servletContext = (ServletContext) ctx.get(ServletActionContext.SERVLET_CONTEXT);
                // 绑定其他的方法。
                servletContext.setAttribute("ServerUrl", getWebServer(request));
                return actionInvocation.invoke();
            }
        }
        // 以下程序未登录才执行。
        String ajaxRequest = request.getHeader("X-Requested-With");
        if (ajaxRequest != null) {
            this.renderJson();
            return null;
        }
        String queryString = request.getQueryString();
        String originUrl = this.getWebServer(request) + request.getRequestURI() + (queryString == null ? "" : "?" + queryString);
        response.sendRedirect(request.getContextPath() + "/login.do?returnUrl=" + URLEncoder.encode(originUrl, "utf-8"));
        return null;
        //return "login";
    }

    /**
     * ajax请求的处理。
     */
    private void renderJson() {
        try {            
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("result", 0);
            dataMap.put("data", "请重新登录。");
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("sessionStatus", "sessionTimeout");
            PrintWriter pw = response.getWriter();
            pw.write(JsonUtils.toJson(dataMap));
            pw.flush();
            pw.close();
        } catch (Exception ex) {
        }
    }
    
    //
    public String getWebServer(HttpServletRequest request) {
        String root = "";
        if (request.getServerPort() == 80) {
            root = request.getScheme() + "://" + request.getServerName();
        } else {
            root = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        }
        return root;
    }
}
