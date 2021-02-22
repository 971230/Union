package com.zte.cbss.util;

import java.util.Date;

import org.springframework.util.StringUtils;

public class DatePropertyEditor extends BasePropertyEditor {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(!StringUtils.hasText(text)) {
			setValue(null);
		} else {
			setValue(DateUtil.parseToDate(text, DateUtil.FULL_PATTERN));
		}
	}
	
	@Override
	public String getAsText() {
		Date date = (Date) getValue();
		return date == null ? "" : DateUtil.format(date, DateUtil.FULL_PATTERN);
	}

	@Override
	public Class<?> getEntityClass() {
		return Date.class;
	}
}
