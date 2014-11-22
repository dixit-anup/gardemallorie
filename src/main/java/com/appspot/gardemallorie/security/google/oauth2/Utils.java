package com.appspot.gardemallorie.security.google.oauth2;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.appengine.api.users.UserServiceFactory;

public class Utils {

	private static final String APPLICATION_NAME = "BabySitting";
	private static final AppEngineDataStoreFactory DATA_STORE_FACTORY = AppEngineDataStoreFactory.getDefaultInstance();
	private static final HttpTransport HTTP_TRANSPORT = UrlFetchTransport.getDefaultInstance();
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);
	
	public static String getUserEmail() {
		return UserServiceFactory.getUserService().getCurrentUser().getEmail();
	}
	
	public static String getRedirectUri(HttpServletRequest req) {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath("/oauth2_callback");
		return url.build();
	}
	
	public static AuthorizationCodeFlow newAuthorizationCodeFlow() throws IOException {

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
			HTTP_TRANSPORT,
			JSON_FACTORY,
			"707675818670-93tsf19go0vt3nffn2c4i4p6npc2nn4d.apps.googleusercontent.com",
			"rWeD4DEoCnAHuA4wGyJ8GvjK",
			Collections.singleton(CalendarScopes.CALENDAR)
		)
		.setDataStoreFactory(DATA_STORE_FACTORY)
		/*.addRefreshListener(new DataStoreCredentialRefreshListener(getUserId(), DATA_STORE_FACTORY))
		  .addRefreshListener(new CredentialRefreshListener() {
		
		    @Override
		    public void onTokenResponse(Credential credential, TokenResponse tokenResponse)
		        throws IOException {
		      logger.error("onTokenResponse");
		    }
		
		    @Override
		    public void onTokenErrorResponse(Credential credential,
		        TokenErrorResponse tokenErrorResponse) throws IOException {
		      logger.error("onTokenErrorResponse");
		    }})*/
		.build();
		LOGGER.debug("flow.refreshListeners: {}", flow.getRefreshListeners());
		return flow;
	}
	
	public static Calendar loadCalendarClient() throws IOException {

		Credential credential = newAuthorizationCodeFlow().loadCredential(getUserEmail());
	    
		if (LOGGER.isDebugEnabled()) {
			if (credential == null) {
				LOGGER.debug("loadCalendarClient(), credential: null");
			}
			else {
				LOGGER.debug("loadCalendarClient(), credential.refreshListeners: {}", credential.getRefreshListeners());
			}
		}
	
		return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
	}
	
	private Utils() {
	}
  
}
