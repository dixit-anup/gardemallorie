package com.appspot.gardemallorie.security.google.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GaeAuthorizationCodeCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8776823298503288728L;

	@Override
	protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
		return Utils.getRedirectUri(req);
	}

	@Override
	protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		return Utils.getUserEmail();
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws IOException {
		return Utils.newAuthorizationCodeFlow();
	}

	@Override
	protected void onError(HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse) throws ServletException, IOException {
		String nickname = UserServiceFactory.getUserService().getCurrentUser().getNickname();
		resp.getWriter().print("<h3>" + nickname + ", why don't you want to play with me?</h1>");
		resp.setStatus(200);
		resp.addHeader("Content-Type", "text/html");
	}

	@Override
	protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential) throws ServletException, IOException {
		resp.sendRedirect("/");
	}

}
