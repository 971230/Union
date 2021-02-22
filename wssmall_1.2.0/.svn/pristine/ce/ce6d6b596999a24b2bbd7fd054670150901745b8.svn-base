package com.ztesoft.ibss.common.service;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import org.directwebremoting.util.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Reason
 * @version Created Sep 16, 2011
 */
public class CommonService {
	public static final Logger log = Logger.getLogger(CommonService.class);
	
	/**
	 * 
	 * @param actionId 服务id
	 * @param sqlService 是否sql服务
	 * @param args 参数
	 * @return
	 * @throws DWRException 
	 */
	public HashMap excute(String actionId,boolean sqlService,HashMap args) throws Exception {
		
		log.debug("=======reason.yea dwr service action:"+actionId+",sqlservice:"+sqlService+",args:"+args);
		HashMap reply = new HashMap();
		if(StringUtils.isEmpty(actionId)){
			return reply;
		}
		DynamicDict m_dict = new DynamicDict(1);
		m_dict.flag = 0;
		m_dict.m_ActionId = actionId;
		
		//访问限制过滤
		boolean filterFlag = ServiceFilter.getInst().filterDWR(actionId);
		if (!filterFlag) {
			//给所调用的js取来判断设值用
			m_dict.flag = -1;
			m_dict.msg = "请求过于频繁，请稍后！";//不带错误日志序号：
			m_dict.m_Values.put("flag", m_dict.flag + "");
			m_dict.m_Values.put("msg", m_dict.msg);
			m_dict.m_Values.put("_msg", getExceptionMsg(m_dict.msg));//不带错误日志序号：
			return m_dict.m_Values;
		}
		
		if(sqlService){//true:sql操作时flag=1
			m_dict.flag	  = 1;
		}
		//填充参数
		fillDictArgs(m_dict,args);
		m_dict.setValueByName("ip", ContextHelper.getIp());
		//执行
		m_dict = ActionDispatch.dispatch(m_dict);
		if(m_dict.flag==1){
			Const.setPageModel(m_dict);
		}
		reply = m_dict.m_Values;
		log.debug("=======reason.yea dwr reply:"+reply);	
		
		handleException(m_dict);
		
		//给所调用的js取来判断设值用
		m_dict.m_Values.put("flag", m_dict.flag + "");
		m_dict.m_Values.put("msg", m_dict.msg);
		m_dict.m_Values.put("_msg", getExceptionMsg(m_dict.msg));//不带错误日志序号：
		
		return m_dict.m_Values;
	}

	/**
	 * 设置service的参数
	 * @param m_dict
	 * @param args
	 */
	private void fillDictArgs(DynamicDict m_dict, HashMap args) {
		if(args==null || args.isEmpty())return;
		try {
			String pageIndex = Const.getStrValue(args, Const.PAGE_PAGEINDEX);
			String pageSize = Const.getStrValue(args, Const.PAGE_PAGESIZE);
			if(!pageIndex.equals("") && !pageSize.equals("")){//添加分页参数
				m_dict.setValueByName(Const.PAGE_PAGEINDEX, pageIndex);
				m_dict.setValueByName(Const.PAGE_PAGESIZE, pageSize);
				args.remove(Const.PAGE_PAGEINDEX);
				args.remove(Const.PAGE_PAGESIZE);
			}else{//设置默认最大分页大小
				log.debug("service:"+m_dict.m_ActionId+" don't has set the PAGE_INDEX and PAGE_SIZE!");
				m_dict.setValueByName(Const.PAGE_PAGEINDEX, "1");
				m_dict.setValueByName(Const.PAGE_PAGESIZE, Const.PAGE_MAX_SIZE);
			}
			
			if(m_dict.flag==1){//sql服务
				m_dict.setValueByName(Const.ACTION_PARAMETER, args);
				return;
			}
			Set set = args.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Entry entry = (Entry) iterator.next();
				String name = (String)entry.getKey();
				Object value = entry.getValue();
				m_dict.setValueByName(name, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理9999和10000的异常信息，抛出到页面上
	 * @param m_dict
	 * @throws DWRException
	 */
	private void handleException(DynamicDict m_dict) throws DWRException {
		int flag = m_dict.flag;
		String msg = m_dict.msg;//replaceLineStr(m_dict.msg);
		String ex = m_dict.exception;//replaceLineStr(m_dict.exception);
		if(flag==Const.ALERT_LEVEL || flag ==Const.ERROR_LEVEL){//9999,10000:异常到页面
			if(flag==Const.ALERT_LEVEL){
				ex="";
				msg = getExceptionMsg(msg);
			}
			log.debug("=======reason.yea dwr exception:["+msg+"]");	
			throw new DWRException(flag,msg,ex);
		}
	}

	private String getExceptionMsg(String msg) {
		if(msg==null ){
			return msg;
		}
		int begin = msg.indexOf("(错误日志序号：");
		if(begin>-1){
			msg = msg.substring(0,begin);
		}
		return msg;
	}

}
