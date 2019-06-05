package com.chinaedustar.app.common;

import com.chinaedustar.hessian.factory.HessianFactoryUtils;
import com.chinaedustar.hessian.factory.HoneyBeeServiceFactory;

public class HessianServiceUtil {
	public static HoneyBeeServiceFactory getHessianService() {
		return HessianFactoryUtils.honeyBeeServiceFactory;
	}
}
