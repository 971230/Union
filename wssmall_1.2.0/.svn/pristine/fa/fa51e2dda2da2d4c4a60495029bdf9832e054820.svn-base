package com.ztesoft.net.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseBill {

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Method [] mts = this.getClass().getDeclaredMethods();
		for(Method m:mts){
			if(m.getName().startsWith("get")){
				try {
					sb.append(m.invoke(this)).append("|");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		if(sb.length()>1)
			sb.deleteCharAt(sb.length()-1);
		sb.append("\n");
		return sb.toString();
	}
	
}
