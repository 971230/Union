package freemarker.template;

import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;


public class TemplatePaser    {
 
private static long line;

	public static void parse(){
		long now =  System.currentTimeMillis() ;
		try{
			
			if(line!=0&& now-line< (24*3600*1000)) {
				return ;    
			}else{
				
			}   
			Thread thread = new Thread (new TParser(ThreadContextHolder.getHttpRequest()));
			thread.start();
			line = now;
		}catch(Exception e){
			line = now;
		}	
	}
	
}
