package com.appspot.gardemallorie.web.google;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appspot.gardemallorie.service.CalendarException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class GoogleLogoutFilter implements Filter {
	
	public static String LOGOUT_REDIRECT_URL_PARAM_NAME = "logoutRedirectURL";
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private String logoutRedirectURL;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logoutRedirectURL = filterConfig.getInitParameter(LOGOUT_REDIRECT_URL_PARAM_NAME);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try {
			chain.doFilter(request, response);
		}
		catch (CalendarException ce) {
			logger.error("################## CalendarException:", ce);
			Throwable t = ce.getCause();
			logger.error("##################### CalendarException: {}, cause:", ce, t);
			if ( (t instanceof GoogleJsonResponseException) ) {
				int statusCode = ((GoogleJsonResponseException)t).getStatusCode();
				logger.error("##################### statusCode: {}", statusCode);
				if (statusCode == SC_UNAUTHORIZED ) {
					logger.debug("Redirecting to {}", logoutRedirectURL);
					((HttpServletResponse) response).sendRedirect(logoutRedirectURL);
				}
			}
			else {
				throw ce;
			}
		}
		catch (GoogleJsonResponseException e) {
			logger.error("################## GoogleJsonResponseException:", e);
		}
		catch (Throwable t) {
			logger.error("##################### Throwable:", t);
			throw new ServletException(t);
		}

	}

	@Override
	public void destroy() {
	}

}
