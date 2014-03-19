package com.appspot.gardemallorie.web.tags.html5;

public class InputTag extends org.springframework.web.servlet.tags.form.InputTag {

	private static final long serialVersionUID = 3750325455941455442L;

	@Override
	protected String getType() {
		Object type = getDynamicAttributes().get("htmlType");
		if (type == null) {
			return "text";
		}
		else {
			return type.toString();
		}
	}

}
