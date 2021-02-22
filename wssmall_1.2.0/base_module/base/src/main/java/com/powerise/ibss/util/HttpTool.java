package com.powerise.ibss.util;


import com.powerise.ibss.framework.FrameException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
public class HttpTool {
	private HttpClient m_Client = null;
    private String m_Server = null;
    private String m_Protocol = "http";
    private int m_Port = 80;
    
    
    private void Connect() throws FrameException {
        
    	m_Client = new HttpClient();
        m_Client.getHostConfiguration().setHost(m_Server, m_Port, m_Protocol);
     
    }
    
    public static String CallService(String strServer,int iPort,String strServlet,String strData,boolean bSecurity) throws FrameException 
    {
    	
    	

    	HttpTool t = new HttpTool();
    	return t.PostServlet(strServer,iPort,strServlet,strData,bSecurity);
    }
    public static String CallService(String strServer,int iPort,String strServlet,String strData) throws FrameException 
    {
    
    	return CallService(strServer,iPort,strServlet,strData,false);
    }
    public  String PostServlet(String strServer,int iPort,String strServlet,String strData,boolean bSecurity) throws FrameException
    {
    	String strRet = null;
    	StringRequestEntity strRE = null;
    	int iRetCode=200;
    	
    	m_Server = strServer;
    	m_Port = iPort;
    	if(bSecurity==true)
    		m_Protocol="https";
    	else
    		m_Protocol = "http";
    	
    	Connect();
    	strRE = new StringRequestEntity(strData);
    	PostMethod pm = new PostMethod(strServlet);
    	pm.setRequestEntity(strRE);
    	try{
    	m_Client.executeMethod(pm);
    	strRet = pm.getResponseBodyAsString();
    	pm.releaseConnection();
    	iRetCode = pm.getStatusCode();    	
    	}catch(Exception e)
    	{
    		throw new FrameException(-22993001,"调用远程HTTP方法失败:"+e.getMessage(),e);
    	}
    	if(iRetCode!=200)
    		throw new FrameException(-22993002,"调用远程HTTP方法失败,服务器返回错误码:"+String.valueOf(iRetCode));
    	
    	
    	return strRet;
    }

}
