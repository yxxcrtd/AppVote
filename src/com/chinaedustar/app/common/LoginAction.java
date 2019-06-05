package com.chinaedustar.app.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinaedustar.app.vote.common.ProBaseAction;
import com.chinaedustar.common.utils.DataUtils;
import com.chinaedustar.common.utils.JsonUtils;
import com.chinaedustar.hessian.factory.HessianFactoryUtils;
import com.chinaedustar.honeybee.constants.HessianConst;
import com.chinaedustar.honeybee.domain.SUserBaseInfo;
import com.chinaedustar.octopus.model.sso.SsoReturnObject;
import com.chinaedustar.octopus.model.sso.User;
import com.chinaedustar.octopus.sso.util.SsoUtils;

/**
 * 处理登录的过程
 */
public class LoginAction extends ProBaseAction {

	private static final long serialVersionUID = -7969099556045629179L;

	private HttpServletResponse response;

	private String ticket;
	private String returnFlag;
	private String returnUrl;
	private String loginUrl;

	/**
	 * 登录ACTION处理方法
	 * 
	 * @return
	 */
	public String login() throws IOException {
		// 等用户访问路径为空是给出默认指向路径
		if (this.returnUrl == null || "".equals(this.returnUrl)) {
			this.returnUrl = SsoUtils.clientLoginUrl;
		}

		// 调用用户管理系统进行自动登录，成功与否用户管理系统会重定向回到verify.do处理结果
		try {
			String result = SsoUtils.isAutoLogin(URLEncoder.encode(this.returnUrl, "UTF-8"));
			if (result != null && "redirected".equals(result)) {
				// System.out.println("com.octopus.sso.util.SsoUtils.isAutoLogin 返回的 result=" + result);
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			this.logger.error("com.octopus.sso.util.SsoUtils.isAutoLogin 处理异常：" + e.toString(), e);
		}
		this.loginUrl = SsoUtils.clientLoginUrl + "?fromServer=1&returnUrl=" + URLEncoder.encode(this.returnUrl, "UTF-8");
		response.sendRedirect(loginUrl);
		return NONE;
	}

	/**
	 * 用户管理系统重定向回来VERIFY处理方法
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public String verify() throws IOException {
		// 当前用户不为空转到verify处理
		if (this.loginUser != null) {
			response.sendRedirect(this.returnUrl);
			return NONE;
		}

		// 获取sso远程登录返回结果
		String json = SsoUtils.verify();

		// 返回登录信息封装成对象：包含状态码、是否成功、用户信息和应用系统发过去的参数
		SsoReturnObject ssoObj = JSON.parseObject(json, SsoReturnObject.class);

		// 返回状态码处理
		this.returnFlag = ssoObj.getReturnFlag();
		int flag = DataUtils.stringToInt(this.returnFlag);

		// 转为远程用户对象
		User ssoUser = ssoObj.getUserData();

		if (flag == 0 && ssoUser != null) {
			List<String> userids = new ArrayList<String>();
			userids.add(ssoUser.getPassportId());
			String jsonUser = HessianFactoryUtils.honeyBeeServiceFactory.getUserService().queryUserList(JsonUtils.toJson(userids));
			JSONObject jUser = JSON.parseObject(jsonUser);
			if (jUser.getString("status").equals(HessianConst.STATUS_SUCCESS)) {
				List<SUserBaseInfo> snsList = JSON.parseArray(jUser.getString("data"), SUserBaseInfo.class);
				this.loginUser = snsList.get(0);
			}
			this.session.put(AppConst.SESSION_USER_KEY, this.loginUser);
			response.sendRedirect(this.returnUrl);
			return NONE;
		} else {
			this.loginUrl = SsoUtils.clientLoginUrl + "?fromServer=1&returnUrl=" + URLEncoder.encode(this.returnUrl, "UTF-8");
			response.sendRedirect(this.loginUrl);
			return NONE;
		}
	}

	/**
	 * 用户退出
	 */
	public String logout() {
		this.session.clear();
		// 退出用户管理系统
		String result = SsoUtils.ssoLogOut(SsoUtils.clientLoginUrl);
		if (result != null && "redirected".equals(result)) {
			return null;
		}
		return SUCCESS;
	}

	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getReturnFlag() {
		return this.returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		if (returnFlag == null || "".equals(returnFlag)) {
			this.returnFlag = "0";
		} else {
			this.returnFlag = returnFlag;
		}
	}

	public String getReturnUrl() {
		return this.returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getLoginUrl() {
		return this.loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
