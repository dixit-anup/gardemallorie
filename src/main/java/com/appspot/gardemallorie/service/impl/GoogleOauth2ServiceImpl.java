package com.appspot.gardemallorie.service.impl;

import java.io.IOException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appspot.gardemallorie.service.GoogleOauth2Service;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.appengine.api.users.UserServiceFactory;

@Service
public class GoogleOauth2ServiceImpl implements GoogleOauth2Service {

	@Value("${calendar.clientId}")
	private String clientId;
	
	@Value("${calendar.clientSecret}")
	private String clientSecret;

	private final DataStoreFactory dataStoreFactory = AppEngineDataStoreFactory.getDefaultInstance();
	
	private final HttpTransport httpTransport = UrlFetchTransport.getDefaultInstance();
	
	private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Credential getCurrentUserCredential() throws IOException {

		Credential credential = createAuthorizationCodeFlow().loadCredential(getCurrentUserEmail());

		if (credential == null) {
			logger.debug("loadCalendarClient(), credential: null");
		}
		else {
			logger.debug("loadCalendarClient(), credential.refreshListeners: {}", credential.getRefreshListeners());
		}
	
		return credential;
	}

	@Override
	public String getCurrentUserEmail() {
		return UserServiceFactory.getUserService().getCurrentUser().getEmail();
	}

	@Override
	@Transactional
	public AuthorizationCodeFlow createAuthorizationCodeFlow() throws IOException {

		CustomCredentialRefreshListener customCredentialRefreshListener = new CustomCredentialRefreshListener();
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport,
				jsonFactory,
				clientId,
				clientSecret,
				Collections.singleton(CalendarScopes.CALENDAR)
			)
			.setDataStoreFactory(dataStoreFactory)
			.addRefreshListener(customCredentialRefreshListener)
			.build();
		
		customCredentialRefreshListener.setAuthorizationCodeFlow(flow);
		
		logger.debug("flow.refreshListeners: {}", flow.getRefreshListeners());

		return flow;
	}

	@Override
	@Transactional
	public void removeCurrentUserCredential() throws IOException {
		
		String currentUserEmail = getCurrentUserEmail();
		logger.debug("Removing Credential for {}", currentUserEmail);
		StoredCredential.getDefaultDataStore(dataStoreFactory).delete(currentUserEmail);
	}

	class CustomCredentialRefreshListener implements CredentialRefreshListener {

		private AuthorizationCodeFlow authorizationCodeFlow;
		private final Logger logger = LoggerFactory.getLogger(getClass());
		
		public void setAuthorizationCodeFlow(AuthorizationCodeFlow authorizationCodeFlow) {
			this.authorizationCodeFlow = authorizationCodeFlow;
		}

		@Override
		public void onTokenResponse(Credential credential, TokenResponse tokenResponse) throws IOException {
			logger.debug("credential: {}, tokenResponse: {}", credential, tokenResponse);
		}

		@Override
		public void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse) throws IOException {
			logger.error("credential: {}, tokenErrorResponse: {}, clientId: {}", credential, tokenErrorResponse, authorizationCodeFlow.getClientId());
			authorizationCodeFlow.getCredentialDataStore().delete(authorizationCodeFlow.getClientId());
		}
		
	}

}
