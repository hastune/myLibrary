package com.sm.utils;

import javax.servlet.http.HttpServletRequest;

import com.sm.beans.user;

public class getRequestInfo {
	
	public static String key = "userInfo";
	
	
	public static String getRealName(HttpServletRequest rq,String Suffix) {
		String prefix = ((user)rq.getSession().getAttribute(key)).getUsername();
		return prefix+Suffix;
	}
	
	public static String getprefix(HttpServletRequest rq) {
		return ((user)rq.getSession().getAttribute(key)).getUsername();
	}
	
	public static int getId(HttpServletRequest rq) {
		return ((user)rq.getSession().getAttribute(key)).getId();
	}
	
	public static int get_pid(HttpServletRequest rq) {
		return ((user)rq.getSession().getAttribute(key)).getP_id();
	}
	
	public static user getUser(HttpServletRequest rq) {
		return ((user)rq.getSession().getAttribute(key));
	}
}
