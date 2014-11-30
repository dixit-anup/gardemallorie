package com.appspot.gardemallorie.web.tags.custom.google;

import javax.servlet.jsp.PageContext;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Functions {
	
	static UserService getUserService() {
		return UserServiceFactory.getUserService();
	}

	public static String createLogoutURL(PageContext pageContext) {
		return getUserService().createLogoutURL("/");
	}

	public static boolean isUserAdmin() {
		return isUserLoggedIn() && getUserService().isUserAdmin();
	}

	public static String getUserEmail() {
		User currentUser = getUserService().getCurrentUser();
		return currentUser == null ? null : currentUser.getEmail();
	}

	public static boolean isUserLoggedIn() {
		return getUserService().isUserLoggedIn();
	}

}
