package com.zte.cbss.util;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class CustomWebBindingInitializer implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		DatePropertyEditor dateEditor = new DatePropertyEditor();
		binder.registerCustomEditor(dateEditor.getEntityClass(), dateEditor);
	}

}
