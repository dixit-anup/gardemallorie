package com.appspot.gardemallorie.security.google.oauth2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GaeAuthorizationCodeServlet extends AbstractAuthorizationCodeServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6521005706469551253L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
	        com.google.api.services.calendar.Calendar client = Utils.loadCalendarClient();
	        com.google.api.services.calendar.Calendar.CalendarList.List listRequest =
	            client.calendarList().list();
	        listRequest.setFields("items(id,summary)");
	        CalendarList feed = listRequest.execute();
	        if (feed.getItems() != null) {
	          for (CalendarListEntry entry : feed.getItems()) {
	        	  logger.debug("CalendarListEntry: {}", entry.getSummary());
	          }
	        }
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println("<!doctype html><html><head>");
		writer.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		writer.println("<title>The title</title>");
		writer.println("</head><body>");
		UserService userService = UserServiceFactory.getUserService();
		writer.println("<div class=\"header\"><b>"
				+ request.getUserPrincipal()/*.getName()*/
				+ "</b> | "
				+ "<a href=\""
				+ userService.createLogoutURL(request.getRequestURL()
						.toString())
				+ "\">Log out</a> | "
				+ "<a href=\"http://code.google.com/p/google-api-java-client/source/browse"
				+ "/calendar-appengine-sample?repo=samples\">See source code for "
				+ "this sample</a></div>");
		writer.println("<div id=\"main\"/>");
		writer.println("</body></html>");
	}

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

}
