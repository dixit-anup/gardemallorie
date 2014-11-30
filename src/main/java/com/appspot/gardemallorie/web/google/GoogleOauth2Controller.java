package com.appspot.gardemallorie.web.google;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appspot.gardemallorie.service.GoogleCalendarService;
import com.appspot.gardemallorie.service.GoogleOauth2Service;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.services.calendar.Calendar;

@Service
public class GoogleOauth2Controller {

	@Autowired
	private GoogleCalendarService googleCalendarService;

	@Autowired
	private GoogleOauth2Service googleOauth2Service;
	
	public AuthorizationCodeFlow createAuthorizationCodeFlow() throws IOException {
		return googleOauth2Service.createAuthorizationCodeFlow();
	}

	public Calendar createCalendarClient() throws IOException {
		return googleCalendarService.createCalendarClient();
	}

	public String getCurrentUserEmail() {
		return googleOauth2Service.getCurrentUserEmail();
	}

	public String getRedirectUri(HttpServletRequest req) {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath("/oauth2_callback");
		return url.build();
	}

	public void removeCurrentUserCredential() throws IOException {
		googleOauth2Service.removeCurrentUserCredential();
	}

}
