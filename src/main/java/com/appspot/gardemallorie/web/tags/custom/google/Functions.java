package com.appspot.gardemallorie.web.tags.custom.google;

import javax.servlet.jsp.PageContext;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Functions {
	
	static UserService getUserService() {
		return UserServiceFactory.getUserService();
	}

	public static String createLogoutURL(PageContext pageContext) {
		System.out.println("createLogoutURL(" + pageContext + ")");
		//HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		//String destinationURL = request.getRequestURI();
		return getUserService().createLogoutURL("/");
	}

	public static boolean isUserAdmin() {
		return isUserLoggedIn() && getUserService().isUserAdmin();
	}

	public static boolean isUserLoggedIn() {
		return getUserService().isUserLoggedIn();
	}

}
