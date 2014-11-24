package com.appspot.gardemallorie.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import com.appspot.gardemallorie.domain.BabySitter;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

    public Converter<BabySitter, String> getBabySitterToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.appspot.gardemallorie.domain.BabySitter, java.lang.String>() {
            public String convert(BabySitter babySitter) {
                return babySitter.getFirstName();
            }
        };
    }
    
}
