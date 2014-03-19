package com.appspot.gardemallorie.web.tags.html5;

import org.springframework.web.servlet.tags.form.InputTag;

public class ColorInputTag extends InputTag {

	private static final long serialVersionUID = -8242650074580176815L;
	private static final String COLOR_TYPE = "color";
	
	@Override
	protected String getType() {
		return COLOR_TYPE;
	}

}
