package com.appspot.gardemallorie.web.google;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;

@Configurable
public class GaeAuthorizationCodeCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8776823298503288728L;

	@Autowired
	private GoogleOauth2Controller googleOauth2Controller;
	
	@Override
	protected String getRedirectUri(HttpServletRequest request) throws ServletException, IOException {
		return googleOauth2Controller.getRedirectUri(request);
	}

	@Override
	protected String getUserId(HttpServletRequest request) throws ServletException, IOException {
		return googleOauth2Controller.getCurrentUserEmail();
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws IOException {
		return googleOauth2Controller.createAuthorizationCodeFlow();
	}

	@Override
	protected void onError(HttpServletRequest request, HttpServletResponse response, AuthorizationCodeResponseUrl errorResponse) throws ServletException, IOException {
		response.getWriter().print("<h3>" + googleOauth2Controller.getCurrentUserEmail() + ", why don't you want to play with me?</h1>");
		response.setStatus(200);
		response.addHeader("Content-Type", "text/html");
	}

	@Override
	protected void onSuccess(HttpServletRequest request, HttpServletResponse response, Credential credential) throws ServletException, IOException {
		response.sendRedirect("/");
	}

}
