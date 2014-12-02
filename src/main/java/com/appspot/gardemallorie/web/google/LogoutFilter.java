package com.appspot.gardemallorie.web.google;

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

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class LogoutFilter implements Filter {
	
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
			logger.debug("################## CalendarException: {}", ce);
			Throwable t = ce.getCause();
			logger.debug("##################### CalendarException: {}, cause: {}", ce, t);
			if ( (t instanceof GoogleJsonResponseException) ) {
				int statusCode = ((GoogleJsonResponseException)t).getStatusCode();
				logger.debug("##################### statusCode: {}", statusCode);
				if (statusCode == SC_UNAUTHORIZED ) {
					logger.debug("Redirecting to {}", logoutRedirectURL);
					((HttpServletResponse) response).sendRedirect(logoutRedirectURL);
				}
			}
			else {
				throw ce;
			}
		}
		/*catch (Throwable t) {
			logger.debug("Throwable: {}", t);
		}*/

	}

	@Override
	public void destroy() {
	}

}
