package com.powerise.ibss.taglibs ;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.system.SessionOBJ;
import com.powerise.ibss.util.SysSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
public class ComboTag extends BodyTagSupport {

	private String  m_empty    = null ;//如果为空的时候显示的值
	private String  m_item     = null ;//数据条目
	private boolean m_remote   = false;//远程调用标志
	private String  m_func_id  = null ;//执行服务
	private String  m_action   = null ;//执行动作  tab 表(m_func_id为表名称 disp为字段名) psql 动态SQL serv 服务(默认)
	private String  m_rs_name  = null ;//返回数据集合名
	private String  m_disp     = null ;//显示字段名称
	private String  m_value    = null ;//值字段名称
	private String  m_cond     = null ;//条件值对 (以`分隔,例如：`name=lgm`pwd=123456)
	private boolean m_execute  = true ;//执行标志
	private boolean m_trace    = false;//调试标志
	
	private DynamicDict m_dict = null;//请求数据
	
	private String  m_data     = null ;//不执行服务直接取数据
	private static Logger logger = Logger.getLogger(ComboTag.class);
	
	public ComboTag() {
		super();
		init();
	}
	public void init() {
		m_item    = null;
		m_empty   = null;
		m_func_id = null;
		m_action  = null;
		m_rs_name = null;
		m_disp    = null;
		m_value   = null;
		m_cond    = null;
		m_trace   = false;
		m_remote  = false;
		m_execute = true ;
		m_data    = null;
		if(m_dict!=null) m_dict.destroy();
		m_dict = null;
	}
	@Override
	public void release() {
		init();
		if(m_dict!=null) m_dict.destroy();
		m_dict = null;
		
	}
	@Override
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}
	@Override
	public int doAfterBody(){
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		JspWriter    jspWriter = null;
		ArrayList    aList     = null;
		String       strHTML   = null;
		try {
			jspWriter = pageContext.getOut();
			aList   = getComoboData();
			strHTML = getComoboHtml(aList);
			//System.out.print("aList-->\n"+aList);
			jspWriter.print(strHTML);
		}catch(Exception e){
			e.printStackTrace();
			//logger.info("执行【 ComboTag 】异常"+e.getMessage());
			logger.info("执行【 ComboTag 】异常"+e.getMessage());
		}finally{
			init();
		}
		return EVAL_PAGE;
	}
	public ArrayList getComoboData() {
		JspWriter    jspWriter = null;
		ArrayList    aList    = null;
		Object       objValue = null;
		HashMap      hashPara  = null;
		HashMap      aHash     = null;
		Object       obj       = null;
		int          iLoop     = 0;
		int          iLoopNum  = 0;
		Iterator     it        = null;
		String       strHTML   = "";
		StringBuffer sbTrace   = null;
		
		javax.servlet.http.HttpServletRequest request = (HttpServletRequest) pageContext.getRequest () ;
		javax.servlet.http.HttpSession        session = request.getSession () ;
		
		
		try {
			jspWriter = pageContext.getOut();
			if(this.m_data!=null && !this.m_data.trim().equals("")){
				this.m_data = this.m_data.trim();
				obj = TagFunc.getObject(pageContext,pageContext,this.m_data,null);
				if(obj==null) obj = TagFunc.getObject(pageContext,pageContext,this.m_data,"session");
				if(m_trace){
					sbTrace = new StringBuffer("");
					sbTrace.append("<table border='1'>\n");
					sbTrace.append("<tr><td>m_data</td>\n<td>");
					sbTrace.append(this.m_data);
					sbTrace.append("</td></tr>\n");
					sbTrace.append("<tr><td>obj</td><td>");
					sbTrace.append(obj);
					sbTrace.append("</td></tr>\n");
					sbTrace.append("</table>\n");
				}
				if(obj==null || !obj.getClass().getName().equals("java.util.ArrayList")) return null;
				aList = (ArrayList)obj;
				return aList;
			}
			
			if(m_dict==null) {
				m_dict = new DynamicDict (1) ;
				aHash  = new HashMap();
				m_dict.setValueByName("PARAMETER",aHash);
			}
			if(m_func_id ==null) m_func_id = "";
			if(m_action  ==null) m_action  = "";
			if(m_rs_name ==null) m_rs_name = "";
			m_func_id = m_func_id.trim();
			m_action  = m_action.trim();
			m_rs_name = m_rs_name.trim();
			
			//从参数表中取得配置信息
			m_dict.flag = 0 ;
			
			if(m_rs_name.equals("")) m_rs_name = m_func_id;
			if(m_action.equals("psql") || m_action.equals("sql")) m_dict.flag = 1 ;
			m_dict.m_ActionId = m_func_id ;
			//表(m_func_id为表名称 disp为字段名)
			if(m_action.equals("tab")){
				m_dict.m_ActionId = "MSM_151" ;
				m_dict.setValueByName ("table_name", m_func_id) ;
				m_dict.setValueByName ("field_name", m_disp) ;
				m_rs_name = m_func_id;
				m_disp  = "text";
				m_value = "value";
			}
			
			if(m_func_id.equalsIgnoreCase("MSM_002")){
				SessionOBJ user = null;
				if(session!=null){
					user = (SessionOBJ)session.getAttribute("user");
					if(user!=null) m_dict.setValueByName("STAFF-NO", user.getStaffNo());
				}
				m_rs_name = "BUREAU";
			}
			try{
				obj = m_dict.getValueByName("parameter");
			}catch(Exception e){
			}
			if(obj==null){
				aHash = new HashMap();
				m_dict.setValueByName("parameter",aHash);
			}else{
				if(!m_action.equals("tab") && !m_func_id.equalsIgnoreCase("MSM_002")){
					hashPara =  TagFunc.getValues(m_cond);
					if(obj.getClass().getName().endsWith("HashMap")) {
						aHash    = (HashMap)obj;
						TagFunc.copyHash(hashPara,aHash,true);
					}
				}
			}
			if(m_trace){
				sbTrace = new StringBuffer("");
				sbTrace.append("<table border='1'>\n");
				sbTrace.append("<tr><td width='1%' nowrap>执行功能</td>\n<td nowrap>");
				sbTrace.append(m_dict.m_ActionId);
				sbTrace.append("</td><tr>");
				
				sbTrace.append("<tr><td width='1%' nowrap>执行方式</td>\n<td nowrap>");
				if(!m_remote) sbTrace.append("本地");
				if( m_remote) sbTrace.append("远程");
				sbTrace.append("</td><tr>");
				
				sbTrace.append("<tr><td width='1%' nowrap>是否执行</td>\n<td nowrap>");
				if(!m_execute) sbTrace.append("否");
				if( m_execute) sbTrace.append("是");
				sbTrace.append("</td><tr>");
				
				sbTrace.append("<tr><td width='1%' nowrap>请求数据</td>\n<td nowrap>\n<textarea rows='4' name='SupperQueryResult' cols='90'>");
				sbTrace.append(m_dict.m_Values);
				sbTrace.append("</textarea></td><tr>");
				sbTrace.append("</table>\n");
			}
			if(!this.m_execute) return null;
			if(m_remote){
				m_dict = SysSet.callRemoteService(m_dict);
			}else{
				m_dict = ActionDispatch.dispatch(m_dict);
			}
			
			if(m_trace ){
				if(sbTrace==null) sbTrace = new StringBuffer("");
				if(m_dict==null){
					sbTrace.append("<table border='1'>\n");
					sbTrace.append("<tr><td width='1%' nowrap>返回null</td>\n<tr>\n");
				}else{
					sbTrace.append("<table border='1'>\n");
					sbTrace.append("<tr><td width='1%' nowrap>返回标志</td>\n<td nowrap>");
					sbTrace.append(m_dict.flag);
					sbTrace.append("</td><tr>");
					sbTrace.append("<tr><td width='1%' nowrap>返回消息</td>\n<td nowrap>");
					sbTrace.append(m_dict.msg);
					sbTrace.append("</td><tr>");
					sbTrace.append("<tr><td width='1%' nowrap>返回数据</td>\n<td nowrap>\n<textarea rows='4' name='SupperQueryResult' cols='90'>");
					sbTrace.append(m_dict.m_Values);
					sbTrace.append("</textarea></td><tr>");
				}
			}
			if (m_dict==null) return null;
			if (m_dict.flag < 0) {
				//异常信息
				//logger.error (m_dict.flag + "    错误信息：" + m_dict.msg + "异常信息：" + m_dict.exception) ;
				logger.info(m_dict.flag + "    错误信息：" + m_dict.msg + "异常信息：" + m_dict.exception);
				return null;
			}
			obj  = m_dict.getValueByName (m_rs_name, false) ;
			if(obj!=null && obj.getClass().getName().equals("java.util.ArrayList") ) aList = (ArrayList)obj;
			return aList;
		}catch(Exception e){
			e.printStackTrace();
			//logger.info("执行【 ComboTag 】异常"+e.getMessage());
			logger.info("执行【 ComboTag 】异常"+e.getMessage());
			if(sbTrace!=null) {
				sbTrace.append("<tr><td width='1%' nowrap>异常消息</td>\n<td nowrap>\n<textarea rows='6' name='StackTrace' cols='90'>");
				sbTrace.append(e.toString());
				sbTrace.append("\n");
				sbTrace.append( TagFunc.getStackInfo(e));
				sbTrace.append("</textarea></td><tr>");
			}
		}finally{
			try{
				if(jspWriter!=null && sbTrace!=null) {
					sbTrace.append("</table>\n");
					jspWriter.print(sbTrace.toString());
				}
			}catch(IOException eIO ){
			}
			if(sbTrace!=null) sbTrace.setLength(0);
			sbTrace = null;
		}
		return null;
	}
	public String getComoboHtml(ArrayList aList) {
		//取得显示的值
		String f_value = null;
		Object objValue= null;
		Object obj     = null;
		String strHTML = "";
		HashMap aHash  = null;
		String  s_disp = null;
		String  s_value= null;
		int          iLoop     = 0;
		int          iLoopNum  = 0;
		Iterator     it        = null;
		StringBuffer sbHtml    = new StringBuffer("");
		
		try {
			if(this.m_item!=null && !this.m_item.trim().equals("")){
				objValue = TagFunc.getObject(pageContext,pageContext,m_item,null);
			}
			if(objValue!=null) {
				if(objValue.getClass().getName().indexOf("String")>-1){
					f_value = (String)objValue;
				}
			}
			if(m_empty!=null) sbHtml.append("<option value=''>"+this.m_empty+"</option>\n");
			
			iLoopNum = 0;
			if(aList!=null) iLoopNum = aList.size();
			for (iLoop=0;iLoop<iLoopNum;iLoop++){
				obj   = aList.get(iLoop);
				if(obj==null || !obj.getClass().getName().equals("java.util.HashMap")) continue;
				aHash   = (HashMap)obj;
				s_disp  = (String)aHash.get(this.m_disp);
				s_value = (String)aHash.get(this.m_value);
				if(s_value==null) continue;
				if(s_disp ==null) s_disp = "";
				sbHtml.append("<option value=\""+s_value+"\" ");
				
				it = aHash.keySet().iterator();
				while(it!=null && it.hasNext()){
					String keyObj  = (String)it.next();
					String valueObj= (String)aHash.get(keyObj);
					if(keyObj==null || valueObj==null )continue;
					if(keyObj.equals(m_disp) || keyObj.equals(m_value)) continue;
					sbHtml.append( keyObj +"=\"" + valueObj + "\" ");
				}

				if(s_value.equals(f_value) || s_disp.equals(f_value)) sbHtml.append(" selected ");
				sbHtml.append(" >"+s_disp+"</option> \n");
			}
			strHTML = sbHtml.toString();
		}catch(Exception e){
			
		}finally{
			if(sbHtml!=null) sbHtml.setLength(0);
			sbHtml = null;
		}
		return strHTML;
	}
	public void pushPara(String rs_name,String paraName,String paraValue,String m_crypt,boolean m_empty) {
    	if(paraName==null ) return ;
    	if(paraValue==null){
    		if(m_empty) return ;
    		this.m_execute = false;
    		return ;
    	}
    	
    	try{
    		if(m_dict==null) {
	    		HashMap aHash = new HashMap();
	    		m_dict = new DynamicDict(1);
				m_dict.setValueByName("PARAMETER",aHash);
	    	}
			if(m_crypt!=null && !m_crypt.trim().equals("")){
				paraValue = TagFunc.crypt(paraValue,m_crypt);
			}
			TagFunc.pushPara(m_dict, rs_name, paraName, paraValue);
		}catch(Exception e){
			//logger.info("ComboTag -->pushPara " + e.getMessage());
			logger.info("ComboTag -->pushPara " + e.getMessage());
			e.printStackTrace();
		}
    }
	//数据条目
    public void setItem(String s_item) {
    	if(s_item==null) s_item = "";
		this.m_item = s_item.trim();
    }
	//执行服务
	public void setFunc_id(String s_func_id) {
    	if(s_func_id==null) s_func_id = "";
		this.m_func_id = s_func_id;
    }
	//执行动作  tab 表(m_func_id为表名称 disp为字段名) psql 动态SQL
	public void setAction(String s_action) {
    	if(s_action==null) s_action = "";
		this.m_action = s_action;
    }
	//显示字段名称
	public void setFdisp(String s_disp) {
    	if(s_disp==null) s_disp = "";
		this.m_disp = s_disp;
    }
	//值字段名称
	public void setFvalue(String s_value) {
    	if(s_value==null) s_value = "";
		this.m_value = s_value;
    }
	//条件值对 (以`分隔,例如：`name=lgm`pwd=123456)
	public void setCond(String s_cond) {
    	if(s_cond==null) s_cond = "";
		this.m_cond = s_cond;
    }
	
    //如果为空的时候显示的值
    public void setEmpty(String s_empty) {
    	if(s_empty==null) s_empty = "";
		this.m_empty = s_empty;
    }
    //返回数据集合名
    public void setRs(String s_rs_name) {
    	if(s_rs_name==null) s_rs_name = "";
		this.m_rs_name = s_rs_name;
    }
    //调试标志
    public void setTrace(boolean bTrace) {
		this.m_trace = bTrace;
    }
    //远程调用标志
    public void setRemote(boolean bRemote) {
		this.m_remote = bRemote;
    }
    //执行标志
	public void setExecute(boolean bExec) {
		this.m_execute = bExec;
    }
    
    //不执行服务直接取数据
	public void setData(String s_data) {
		if(s_data==null || s_data.trim().equals("")) {
			s_data = null; 
		}else{
			s_data = s_data.trim();
		}
		this.m_data = s_data;
    }    
}
