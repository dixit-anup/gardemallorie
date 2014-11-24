package com.appspot.gardemallorie.web.tags.custom.spring;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.InputTag;
import org.springframework.web.servlet.tags.form.TagWriter;

public class Html5InputTag extends InputTag {

	private static final long serialVersionUID = 3750325455941455442L;

	/**
	 * The name of the '{@code required}' attribute.
	 */
	public static final String REQUIRED_ATTRIBUTE = "required";

	private String required;

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

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

	protected boolean isRequired() throws JspException {
		return evaluateBoolean(REQUIRED_ATTRIBUTE, getRequired());
	}

	@Override
	protected void writeOptionalAttributes(TagWriter tagWriter) throws JspException {

		super.writeOptionalAttributes(tagWriter);

		if (isRequired()) {
			writeOptionalAttribute(tagWriter, REQUIRED_ATTRIBUTE, REQUIRED_ATTRIBUTE);
		}
	}

}
