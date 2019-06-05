package com.chinaedustar.app.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录用户工具类
 */
public class LoginUserUtil {
	
	public static SnsUser getLoginUser(HttpServletRequest request) {
		SnsUser user = null;
		HttpSession session = request.getSession(false);
		if (null != session && null != session.getAttribute(AppConst.SESSION_USER_KEY)) {
			user = (SnsUser) session.getAttribute(AppConst.SESSION_USER_KEY);
		}
		return user;
	}
	
}
