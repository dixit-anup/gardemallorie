package com.appspot.gardemallorie.web.tags.html5;

import org.springframework.web.servlet.tags.form.InputTag;

public class DateInputTag extends InputTag {

	private static final long serialVersionUID = -2540083904160019276L;
	private static final String DATE_TYPE = "date";
	
	@Override
	protected String getType() {
		return DATE_TYPE;
	}

}
