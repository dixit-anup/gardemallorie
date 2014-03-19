package com.appspot.gardemallorie.web.tags.html5;

import org.springframework.web.servlet.tags.form.InputTag;

public class TimeInputTag extends InputTag {

	private static final long serialVersionUID = 4238136702253536329L;
	private static final String TIME_TYPE = "time";
	
	@Override
	protected String getType() {
		return TIME_TYPE;
	}

}
