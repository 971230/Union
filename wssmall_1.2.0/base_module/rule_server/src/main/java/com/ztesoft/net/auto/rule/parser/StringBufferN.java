package com.ztesoft.net.auto.rule.parser;

import org.drools.core.util.StringUtils;

public class StringBufferN{
	StringBuffer sb;
	
	public StringBufferN(){
		sb = new StringBuffer();
	}
	
	public StringBufferN(int length) {
		sb = new StringBuffer(length);
	}

	public StringBufferN append(String str){
		if(!StringUtils.isEmpty(str))sb.append(str);
		return this;
	}
	
	@Override
	public String toString(){
		return this.sb.toString();
	}
}
