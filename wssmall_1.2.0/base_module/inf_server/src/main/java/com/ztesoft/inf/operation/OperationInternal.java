package com.ztesoft.inf.operation;

import com.ztesoft.inf.extend.xstream.mapper.MapperContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OperationInternal {

	private List inArgMapperContext=new ArrayList();
	private List inArgXpaths=new ArrayList();
	private Method method;
	private MapperContext resultMapperContext;
	private Object target;

	
	public void addArgMapperContext(MapperContext ctx) {
		this.inArgMapperContext.add(ctx);
	}
	public void addArgXpath(String xpath) {
		this.inArgXpaths.add(xpath);
	}
	public List getInArgMapperContext() {
		return inArgMapperContext;
	}
	public List getInArgXpaths() {
		return inArgXpaths;
	}
	public Method getMethod() {
		return method;
	}
	public MapperContext getResultMapperContext() {
		return resultMapperContext;
	}
	public MapperContext getResultMapperCtx() {
		return resultMapperContext;
	}
	public Object getTarget() {
		return target;
	}
	public void setInArgMapperContext(List inArgMapperContext) {
		this.inArgMapperContext = inArgMapperContext;
	}
	public void setInArgXpaths(List inArgXpaths) {
		this.inArgXpaths = inArgXpaths;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public void setResultMapperContext(MapperContext resultMapperContext) {
		this.resultMapperContext = resultMapperContext;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
}

