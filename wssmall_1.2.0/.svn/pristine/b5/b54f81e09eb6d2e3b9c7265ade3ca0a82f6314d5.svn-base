package com.ztesoft.api.annotation.tool;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 作用：注解生成代码帮助文档Javadoc描述内容
 * @author sguo
 * @date 2014-01-07
 *
 */
public class JavaDocHandler {
	private Configuration configuration = null;
	private Map dataMap = null;
	private String templateFileName;
	private String outFileName;//输出文档路径及名称

    private String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	private String getOutFileName() {
		return outFileName;
	}

	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}

	public Map getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}
		
	public JavaDocHandler(){
		 this.configuration = new Configuration();
		 this.configuration.setDefaultEncoding("utf-8");
		 this.dataMap = new HashMap();
		 this.templateFileName="unit.html";
		 this.outFileName="D://Workspaces//zpaasfrm-ropV2//WebRoot//apidoc//unit"+System.currentTimeMillis()+".html";
	}
	
	public void createHtml(){
		this.configuration.setClassForTemplateLoading(this.getClass(), "/com/ztesoft/api/annotation/tool");
		 Writer out = null;
		 try {
	    	 Template t = configuration.getTemplate(this.getTemplateFileName());
	    	 File outFile = new File(this.getOutFileName());
	    	 out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));	
	    	 t.process(this.dataMap, out);
	    	 out.flush();
	     } catch (FileNotFoundException e1) {
	    	 e1.printStackTrace();
	     } catch (UnsupportedEncodingException e) {
	    	 e.printStackTrace();
		 } catch (TemplateException e) {
			 e.printStackTrace();
	     } catch (IOException e) {
	     	 e.printStackTrace();
	     } finally{ 
				try {
					out.close();
				} catch (IOException e) {
				}
	     }		
		
	}
	
	public static void main(String [] args){
		JavaDocHandler jh = new JavaDocHandler();
		jh.setTemplateFileName("unit.html");
		jh.setOutFileName("D://Workspaces//zpaasfrm-ropV2//WebRoot//apidoc//unit3.html");
		HashMap hm = new HashMap();
		hm.put("classname", "ceshi");
		hm.put("desc", "demo");
		jh.setDataMap(hm);
		jh.createHtml();
		
	}

}
