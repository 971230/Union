package com.ztesoft.common.application;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class AppTest {
private ArrayList bb=new ArrayList();
private static Logger logger = Logger.getLogger(AppTest.class);
public static void main(String[]agrs){
	AppTest bo=new AppTest();
	System.out.print(bo.getClass().getName());
}
public AppTest(String a){
	//
}
public AppTest(){
	//
}
public void set(String item){
	bb.add(item);
}
public void get(){
	for(int i=0;i<bb.size();i++){
		logger.info("\n"+(String)bb.get(i));
	}
}

public void bfprint(String myString){
	logger.info("bfInterc");
}
public void bfprint2(AppProxy parentApx,AppProxy currApx,ArrayList myString) throws Exception{
	parentApx.addAfIInterc("com.ztesoft.common.application.AppTest/", 
			"afprint2", "com.ztesoft.common.application.AppTest", "aafprint2",null);
	logger.info("bfprint2");
}
public void aafprint2(AppProxy parentApx,AppProxy currApx,ArrayList myString) throws Exception{
	parentApx.addAfIInterc("com.ztesoft.common.application.AppTest/com.ztesoft.common.application.AppTest/", 
			"aafprint2", "com.ztesoft.common.application.AppTest", "aaafprint2",null);
	logger.info("aafprint2");
}
public void aaafprint2(AppProxy parentApx,AppProxy currApx,ArrayList myString) throws Exception{
	boolean test=parentApx.isContainKey("aaaafprint2");
	parentApx.addAfIInterc("com.ztesoft.common.application.AppTest/com.ztesoft.common.application.AppTest/com.ztesoft.common.application.AppTest/", 
			"aaafprint2", "com.ztesoft.common.application.AppTest", "aaaafprint2",null);
	boolean test1=parentApx.isContainKey("aaaafprint2");
	logger.info("aaafprint2");
}
public void aaaafprint2(AppProxy parentApx,AppProxy currApx,ArrayList myString) throws Exception{
	logger.info("aaaafprint2");
}
public String print(String myString){
	logger.info("正常的"+myString);
	return "方法后的拦截器调用打印";
}
public void afprint(String myString){
	logger.info("afInterc");
}
public void afprint2(AppProxy parentApx,AppProxy currApx,ArrayList myString){
	logger.info("afInterc2");
}

}
