package com.appspot.gardemallorie.service;

import java.io.IOException;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;

public interface GoogleOauth2Service {

	AuthorizationCodeFlow createAuthorizationCodeFlow() throws IOException;

	Credential getCurrentUserCredential() throws IOException;

	String getCurrentUserEmail();

	void removeCurrentUserCredential() throws IOException;

}
