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
import org.springframework.web.util.NestedServletException;

import com.appspot.gardemallorie.service.CalendarException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class GoogleLogoutFilter implements Filter {
	
	public static String REDIRECT_URL_PARAM_NAME = "redirectURL";
	private String redirectURL;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		redirectURL = filterConfig.getInitParameter(REDIRECT_URL_PARAM_NAME);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try {
			chain.doFilter(request, response);
		}
		catch (NestedServletException nse) {
			logger.error("################## NestedServletException:", nse);
			Throwable cause = nse.getCause();
			boolean is401Error = false;
			if (cause != null && cause instanceof CalendarException) {
				cause = cause.getCause();
				if ( cause instanceof GoogleJsonResponseException ) {
					int statusCode = ((GoogleJsonResponseException) cause).getStatusCode();
					logger.error("##################### statusCode: {}", statusCode);
					if (statusCode == SC_UNAUTHORIZED ) {
						is401Error = true;
					}
				}
			}

			if (is401Error) {
				logger.debug("Redirecting to {}", redirectURL);
				((HttpServletResponse) response).sendRedirect(redirectURL);
			}
			else {
				throw nse;
			}
		}

	}

	@Override
	public void destroy() {
	}

}
