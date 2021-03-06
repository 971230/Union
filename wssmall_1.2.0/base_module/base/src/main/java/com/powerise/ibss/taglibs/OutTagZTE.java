package com.powerise.ibss.taglibs;

import org.apache.taglibs.standard.tag.common.core.OutSupport;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

import javax.servlet.jsp.JspException;
import java.lang.reflect.Method;

public class OutTagZTE extends OutSupport {

	private String val_;
	private String fun;
	private String param;
	private String parameterType;

	public OutTagZTE()
	{
	  init();
	}
	
	@Override
	public int doStartTag() throws JspException
	{
	    evaluateExpressions();
	    return super.doStartTag();
	}
	
	@Override
	public void release()
	{
	    super.release();
	    init();
	}
	
	private void init()
	{
	  this.val_ = null;
	}	
	
	public void setFun(String fun) {
		this.fun = fun;
	}
	 
	public void setValue(String val_)
	{
	  this.val_ = val_;
	}
	  
	public void setval_(String val_) {
		this.val_ = val_;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	  
	private void evaluateExpressions() throws JspException
	{
		String tail = "";
	    try {
	    	this.value = ExpressionUtil.evalNotNull("out", "value", this.val_, Object.class, this, this.pageContext);
	    	ExpressionUtil.evalNotNull("out", "fun", this.fun, Object.class, this, pageContext);
	    	ExpressionUtil.evalNotNull("out", "param", this.param, Object.class, this, pageContext);
	    	ExpressionUtil.evalNotNull("out", "parameterType", this.parameterType, Object.class, this, pageContext);
	    	
	    	Class[] parameterType = null;
	    	Object[] param = null;
	    	
	    	//
	    	if(this.param==null||"".equals(this.param)){
	    		 parameterType = null;
	    		 param = null;
	    	}else{
	    		String [] parameterTypeArray = this.parameterType.split(",");
	    		String [] paramArray = this.param.split(",");
	    		int len1=parameterTypeArray.length,len2=paramArray.length;
		    	parameterType  = new Class[len1];
		    	param = new Object[len1];
		    	for(int i=0;i<len1;i++){
		    		if("int".equals(parameterTypeArray[i])){
		    			parameterType[i] = Integer.TYPE;
		    		}else{
		    			parameterType[i]=Class.forName(parameterTypeArray[i]);
		    		}
		    	}	
		    	
		    	for(int i=0;i<len1;i++){
		    		//取的长度大于字符串实际长度
		    		if("substring".equals(fun)&&i==1&&((String)this.value).length()<Integer.parseInt(paramArray[i])){
		    			param[i]= Integer.valueOf(String.valueOf(((String)this.value).length()));
		    		}else{
		    			if("int".equals(parameterTypeArray[i])){
		    				param[i] = Integer.valueOf(paramArray[i]);
		    			}else if("String".equals(parameterTypeArray[i])){
		    				param[i] = paramArray[i];
		    			}
		    		}
		    	}
		    	//加尾巴
		    	if("substring".equals(fun)&&((String)this.value).length()>Integer.parseInt(paramArray[1])&&len2>len1){
		    		tail = paramArray[len1];
		    	}
	    	}

	    	try {
				this.value=callMethod("java.lang.String",this.fun,param,parameterType)+tail;
			} catch (Exception e) {
				e.printStackTrace();
				this.value="";
			}
	    	
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    	this.value="";
	    }	    

	}
	  
	private Object callMethod(String className,String methodName,Object[] param,Class[] parameterType) throws Exception {
	    Class cObj = Class.forName(className);
	    Method m = cObj.getDeclaredMethod(methodName,parameterType);
	    return m.invoke(this.value,param);
	}

}
