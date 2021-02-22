package com.ztesoft.net.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderFileBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.ecsord.params.ecaop.req.DownloadRecordReq;
import com.ztesoft.net.ecsord.params.ecaop.req.InitiationCallReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QueryCalllogReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.DownloadRecordResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.InitiationCallResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.QueryCalllogResp;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IVOX2WAVManager;
import com.ztesoft.net.service.impl.VOX2WAVManager;

import consts.ConstsCore;

public class OrderCalllogAction extends WWAction{

	private static final long serialVersionUID = 1L;
	private String order_id;// 订单ID
	private String includePage;
	private String first_load;//是否首次加载
	private String calllogPageRowCount;
	private Page calllogPage;
	private String file_id;
	private String callBeginTime;
	private String callerPhone;
	private String calleePhone;
	private String call_order_type;
	private static IVOX2WAVManager VOX2WAVManager ;
	static INetCache cache;
	static{
		VOX2WAVManager = SpringContextHolder.getBean("vOX2WAVManager");
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	public String toinitiationCall() {
		AdminUser adminuser = ManagerUtils.getAdminUser();
		try {
			callerPhone = VOX2WAVManager.getUserPhone(adminuser.getUserid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if("order".equals(call_order_type)){
			calleePhone = ordertree.getOrderDeliveryBusiRequests().get(0).getShip_mobile();
		}else{
			calleePhone = VOX2WAVManager.queryShipTel(order_id);
		}
		
		return "to_initiationcall";
	}
	
	public String initiationCall(){
		try{
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			InitiationCallReq req = new InitiationCallReq();
			req.setNotNeedReqStrOrderId(order_id);
			AdminUser adminuser = ManagerUtils.getAdminUser();
			String caller = "";
			Map params = null;
			try {
				caller = VOX2WAVManager.getUserPhone(adminuser.getUserid());
				if(StringUtil.isEmpty(caller)){
					json = "{result:1,message:'工号未配置外呼号码'}";
					return this.JSON_MESSAGE;
				}
				params = VOX2WAVManager.getParams("CALLLOG_PARAMS");
				if(null==params||params.size()<=0){
					json = "{result:1,message:'接口基本参数未配置'}";
					return this.JSON_MESSAGE;
				}
			} catch (Exception e) {
				json = "{result:1,message:'处理失败，请重试！'}";
				return this.JSON_MESSAGE;
			}
			//req.setService(Const.getStrValue(params, "service"));
			req.setPartner(Const.getStrValue(params, "partner"));
			req.setInput_charset(Const.getStrValue(params, "input_charset"));
			req.setAccessParty(Const.getStrValue(params, "accessParty"));
			req.setBusinessType(Const.getStrValue(params, "businessType"));
			req.setTemplate_id(Const.getStrValue(params, "template_id"));
			req.setCaller(callerPhone);//主叫号码
			req.setCallee(calleePhone);//被叫号码
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			InitiationCallResp resp = client.execute(req, InitiationCallResp.class);
			if(!StringUtil.isEmpty(resp.getResultCode())&&"0".equals(resp.getResultCode())){
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String file_path = cacheUtil.getConfigInfo("CALLLOG_FILE_PATH");
				String recordOutID = req.getRecordOutID();
				String flow_trace_id = "";
				if("order".equals(call_order_type)){
					flow_trace_id = ordertree.getOrderExtBusiRequest().getFlow_trace_id();
					OrderFileBusiRequest file = new OrderFileBusiRequest();
					file.setOrder_id(order_id);
					file.setFile_id(recordOutID);
					file.setFile_path(file_path);
					file.setFile_type("vox");
					file.setFile_name("");
					file.setCreate_time(Consts.SYSDATE);
					file.setOperator_id(adminuser.getUserid());// 默认超级管理员
					file.setStatus("1");// 正常
					file.setTache_code(ordertree.getOrderExtBusiRequest().getFlow_trace_id());// 订单归档环节
					file.setSource_from(ManagerUtils.getSourceFrom());
					file.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					file.setDb_action(ConstsCore.DB_ACTION_INSERT);
					ordertree.getOrderFileBusiRequests().add(file);
					String insert_sql = " insert into es_order_file (order_id,file_id,file_path,file_type,file_name,create_time,operator_id,status,tache_code,source_from) "
							  + " values (?,?,?,?,?,sysdate,?,?,?,?) ";
					String log_sql = " insert into  es_order_call_sms_logs (log_id,order_id,caller,callee,recordoutid,create_time,userid,username,source_from) "
						   + " values (?,?,?,?,?,sysdate,?,?,?) ";
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					baseDaoSupport.execute(insert_sql, new String[]{order_id,recordOutID,file_path,"vox","",adminuser.getUserid(),"1",flow_trace_id,ManagerUtils.getSourceFrom()});
					baseDaoSupport.execute(log_sql, new String[]{baseDaoSupport.getSequences("s_es_order"),order_id,callerPhone,calleePhone,recordOutID,adminuser.getUserid(),adminuser.getRealname(),ManagerUtils.getSourceFrom()});
					cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + order_id,
							ordertree, RequestStoreManager.time);
				}else{
					String insert_sql = " insert into es_order_intent_file (order_id,file_id,file_path,file_type,file_name,create_time,operator_id,status,tache_code,source_from) "
							  + " values (?,?,?,?,?,sysdate,?,?,?,?) ";
					String log_sql = " insert into  es_order_call_sms_logs (log_id,order_id,caller,callee,recordoutid,create_time,userid,username,source_from) "
						   + " values (?,?,?,?,?,sysdate,?,?,?) ";
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					baseDaoSupport.execute(insert_sql, new String[]{order_id,recordOutID,file_path,"vox","",adminuser.getUserid(),"1",flow_trace_id,ManagerUtils.getSourceFrom()});
					baseDaoSupport.execute(log_sql, new String[]{baseDaoSupport.getSequences("s_es_order"),order_id,callerPhone,calleePhone,recordOutID,adminuser.getUserid(),adminuser.getRealname(),ManagerUtils.getSourceFrom()});
				}
				json = "{result:0,message:'操作成功！请耐心等待'}";
				return this.JSON_MESSAGE;
			}else{
				json = "{result:1,message:'"+resp.getResultMsg()+"'}";
				return this.JSON_MESSAGE;
			}
		}catch(Exception e){
			e.printStackTrace();
			json = "{result:1,message:'"+e.getMessage()+"'}";
			return this.JSON_MESSAGE;
		}
		
		
		
	}
	
	public String queryCalllog(){
		String order_id = this.order_id;
		if("order".equals(call_order_type)){
			if(!StringUtil.isEmpty(first_load)&&"yes".equals(first_load)){
				calllogPage = VOX2WAVManager.queryCalllog(order_id,this.getPage(),this.getPageSize());
				calllogPageRowCount = calllogPage.getTotalCount()+"";
				return includePage;
			}else{
				calllogPage = VOX2WAVManager.queryCalllog(order_id,this.getPage(),this.getPageSize());
				return "calllog_info_before";
			}
		}else{
			if(!StringUtil.isEmpty(first_load)&&"yes".equals(first_load)){
				calllogPage = VOX2WAVManager.queryIntentCalllog(order_id,this.getPage(),this.getPageSize());
				calllogPageRowCount = calllogPage.getTotalCount()+"";
				return includePage;
			}else{
				calllogPage = VOX2WAVManager.queryIntentCalllog(order_id,this.getPage(),this.getPageSize());
				return "calllog_info_before";
			}
		}
		
		
	}

	public String newQueryCalllog(){
		String order_id = this.order_id;
			if(!StringUtil.isEmpty(first_load)&&"yes".equals(first_load)){
				calllogPage = new Page();
				//calllogPageRowCount = calllogPage.getTotalCount()+"";
				return includePage;
			}else{
				calllogPage = VOX2WAVManager.queryCalllog(order_id,this.getPage(),this.getPageSize());
				return "new_calllog_info_before";
			}
		
		
	}
	
	public String downCalllog() throws Exception{
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderFileBusiRequest> fileList = ordertree.getOrderFileBusiRequests();
		String file_name = "";
		String file_path = "";
		String begin_time = "";
		for (int i = 0; i < fileList.size(); i++) {
			OrderFileBusiRequest file = fileList.get(i);
			String r_file_id = file.getFile_id();
			if(file_id.equals(r_file_id)){
				file_path = file.getFile_path();
				file_name = file.getFile_name();
				begin_time = file.getCreate_time();
				break;
			}
		}
		if(!StringUtil.isEmpty(file_name)){
			String real_file_path = file_path.substring(0, file_path.lastIndexOf("/"))+"/"+file_name.substring(0, file_name.lastIndexOf(".vox"))+".wav";
			File dowF = new File(real_file_path);
			if(dowF.exists()){
				VOX2WAVManager.downLoad(real_file_path, ServletActionContext.getResponse(), false);
				json = "{result:0,message:'成功'}";
				return this.JSON_MESSAGE;
			}else{
				dowFile(ordertree,begin_time);
				return this.JSON_MESSAGE;
			}
		}else{
			dowFile(ordertree,begin_time);
			return this.JSON_MESSAGE;
		}
		
		
	}
	
	public String qryCalllog() {
		try{
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			
			String file_name = "";
			String file_path = "";
			String begin_time = "";
			if("order".equals(call_order_type)){
				List<OrderFileBusiRequest> fileList = ordertree.getOrderFileBusiRequests();
				for (int i = 0; i < fileList.size(); i++) {
					OrderFileBusiRequest file = fileList.get(i);
					String r_file_id = file.getFile_id();
					if(file_id.equals(r_file_id)){
						file_path = file.getFile_path();
						file_name = file.getFile_name();
						begin_time = file.getCreate_time();
						break;
					}
				}
				qryFile(ordertree,begin_time);
				return this.JSON_MESSAGE;
			}else{
				qryFileIntent(order_id,file_id,calleePhone);
				return this.JSON_MESSAGE;
			}
		}catch(Exception e){
			e.printStackTrace();
			json = "{result:1,message:'处理失败，请重试！',playurl:''}";
			return this.JSON_MESSAGE;
		}
		
		
		/*if(!StringUtil.isEmpty(file_name)){
			String real_file_path = file_path.substring(0, file_path.lastIndexOf("/"))+"/"+file_name.substring(0, file_name.lastIndexOf(".vox"))+".wav";
			File dowF = new File(real_file_path);
			if(dowF.length()>0){
				json = "{result:0,message:'成功',playurl:'"+real_file_path+"'}";
				return this.JSON_MESSAGE;
			}else{
				qryFile(ordertree,begin_time);
				return this.JSON_MESSAGE;
			}
		}else{
			qryFile(ordertree,begin_time);
			return this.JSON_MESSAGE;
		}*/
		
		
	}
	public String dowFile(OrderTreeBusiRequest ordertree,String begin_time) throws Exception{
		List<OrderFileBusiRequest> fileList = ordertree.getOrderFileBusiRequests();
		QueryCalllogReq req = new QueryCalllogReq();
		req.setNotNeedReqStrOrderId(order_id);
		Map params = null;
		try {
			params = VOX2WAVManager.getParams("CALLLOG_PARAMS");
			if(null==params||params.size()<=0){
				json = "{result:1,message:'接口基本参数未配置'}";
				return null;
			}
		} catch (Exception e) {
			json = "{result:1,message:'处理失败，请重试！'}";
			return null;
		}
		//req.setService(Const.getStrValue(params, "service"));
		req.setPartner(Const.getStrValue(params, "partner"));
		req.setInput_charset(Const.getStrValue(params, "input_charset"));
		req.setAccessParty(Const.getStrValue(params, "accessParty"));
		req.setBusinessType(Const.getStrValue(params, "businessType"));
		req.setTemplate_id(Const.getStrValue(params, "template_id"));
		req.setCallee(ordertree.getOrderDeliveryBusiRequests().get(0).getShip_mobile());//被叫号码
		req.setTrafficInformation(file_id);
		req.setCallbeginTime(begin_time.substring(0,begin_time.indexOf(" "))+" 00:00:00");
		req.setCallEndTime(begin_time.substring(0,begin_time.indexOf(" "))+" 23:59:59");
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		QueryCalllogResp resp = client.execute(req, QueryCalllogResp.class);
		if(!StringUtil.isEmpty(resp.getResultCode())&&"0".equals(resp.getResultCode())){
			String callOnlyMark = resp.getRecordInfo().getCallOnlyMark();
			String callBeginTime = resp.getRecordInfo().getCallBeginTime();
			DownloadRecordReq reqe = new DownloadRecordReq();
			reqe.setNotNeedReqStrOrderId(order_id);
			reqe.setPartner(Const.getStrValue(params, "partner"));
			reqe.setInput_charset(Const.getStrValue(params, "input_charset"));
			reqe.setAccessParty(Const.getStrValue(params, "accessParty"));
			reqe.setCallBeginTime(callBeginTime);
			reqe.setCallOnlyMark(callOnlyMark);
			DownloadRecordResp respe = client.execute(reqe, DownloadRecordResp.class);
			if(!StringUtil.isEmpty(respe.getResultCode())&&"0".equals(respe.getResultCode())){
				ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
				fileList = ordertree.getOrderFileBusiRequests();
				String new_file_path = "";
				String new_file_name = "";
				for (int i = 0; i < fileList.size(); i++) {
					OrderFileBusiRequest file = fileList.get(i);
					String r_file_id = file.getFile_id();
					if(file_id.equals(r_file_id)){
						String old_file_path = file.getFile_path();
						new_file_name = respe.getCallOnlyMark();
						new_file_path = old_file_path.substring(0, old_file_path.lastIndexOf("/"))+"/"+new_file_name;
						file.setFile_path(new_file_path);
						file.setFile_name(respe.getCallOnlyMark());
						break;
					}
				}
				String update_sql = " update es_order_file set file_path=?,file_name=? where file_id=? ";
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				baseDaoSupport.execute(update_sql, new String[]{new_file_path,new_file_name,file_id});
				cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + order_id,
						ordertree, RequestStoreManager.time);
				String inf_file_path = new_file_path.substring(0, new_file_path.lastIndexOf(".vox"))+".wav";
				VOX2WAVManager.upload(respe.getCallOnlyMark(),respe.getCallOnlyMark().substring(0, respe.getCallOnlyMark().lastIndexOf(".vox"))+".wav");
				VOX2WAVManager.downLoad(respe.getCallOnlyMark().substring(0, respe.getCallOnlyMark().lastIndexOf(".vox"))+".wav", ServletActionContext.getResponse(), false);
				/*VOX2WAVManager.voxConvert(new_file_path, inf_file_path, 1, 6000, 1);
				VOX2WAVManager.downLoad(inf_file_path, ServletActionContext.getResponse(), false);*/
				json = "{result:0,message:'成功'}";
				return null;
			}else{
				json = "{result:1,message:'"+respe.getResultMsg()+"'}";
				return null;
			}
			
		}else{
			json = "{result:1,message:'"+resp.getResultMsg()+"'}";
			return null;
		}
	}
	
	public String qryFile(OrderTreeBusiRequest ordertree,String begin_time) {
		
		try {
			List<OrderFileBusiRequest> fileList = ordertree.getOrderFileBusiRequests();
			QueryCalllogReq req = new QueryCalllogReq();
			req.setNotNeedReqStrOrderId(order_id);
			Map params = null;
			params = VOX2WAVManager.getParams("CALLLOG_PARAMS");
			if(null==params||params.size()<=0){
				json = "{result:1,message:'接口基本参数未配置',playurl:''}";
				return null;
			}
			//req.setService(Const.getStrValue(params, "service"));
			req.setPartner(Const.getStrValue(params, "partner"));
			req.setInput_charset(Const.getStrValue(params, "input_charset"));
			req.setAccessParty(Const.getStrValue(params, "accessParty"));
			req.setBusinessType(Const.getStrValue(params, "businessType"));
			req.setTemplate_id(Const.getStrValue(params, "template_id"));
			req.setCallee(calleePhone);//被叫号码
			req.setTrafficInformation(file_id);
			req.setCallbeginTime(begin_time.substring(0,begin_time.indexOf(" "))+" 00:00:00");
			req.setCallEndTime(begin_time.substring(0,begin_time.indexOf(" "))+" 23:59:59");
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			QueryCalllogResp resp = client.execute(req, QueryCalllogResp.class);
			if(!StringUtil.isEmpty(resp.getResultCode())&&"0".equals(resp.getResultCode())){
				String new_file = resp.getRecordInfo().getRecordFile();
				String callOnlyMark = resp.getRecordInfo().getCallOnlyMark();
				String callBeginTime = resp.getRecordInfo().getCallBeginTime();
				DownloadRecordReq reqe = new DownloadRecordReq();
				reqe.setNotNeedReqStrOrderId(order_id);
				reqe.setPartner(Const.getStrValue(params, "partner"));
				reqe.setInput_charset(Const.getStrValue(params, "input_charset"));
				reqe.setAccessParty(Const.getStrValue(params, "accessParty"));
				reqe.setCallBeginTime(callBeginTime);
				reqe.setCallOnlyMark(callOnlyMark);
				DownloadRecordResp respe = client.execute(reqe, DownloadRecordResp.class);
				if(!StringUtil.isEmpty(respe.getResultCode())&&"0".equals(respe.getResultCode())){
					ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
					fileList = ordertree.getOrderFileBusiRequests();
					String new_file_path = "";
					String new_file_name = "";
					for (int i = 0; i < fileList.size(); i++) {
						OrderFileBusiRequest file = fileList.get(i);
						String r_file_id = file.getFile_id();
						if(file_id.equals(r_file_id)){
							String old_file_path = file.getFile_path();
							new_file_name = new_file;
							new_file_path = old_file_path.substring(0, old_file_path.lastIndexOf("/"))+"/"+new_file_name;
							file.setFile_path(new_file_path);
							file.setFile_name(new_file);
							break;
						}
					}
					String update_sql = " update es_order_file set file_path=?,file_name=? where file_id=? ";
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					baseDaoSupport.execute(update_sql, new String[]{new_file_path,new_file_name,file_id});
					cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + order_id,
							ordertree, RequestStoreManager.time);
					String inf_file_path = new_file_path.substring(0, new_file_path.lastIndexOf(".vox"))+".wav";
					//VOX2WAVManager.voxConvert(new_file_path, inf_file_path, 1, 6000, 1);
					VOX2WAVManager.upload(new_file,new_file.substring(0, new_file.lastIndexOf(".vox"))+".wav");
					json = "{result:0,message:'成功',playurl:'"+inf_file_path+"'}";
					return null;
				}else{
					json = "{result:1,message:'录音记录获取失败："+respe.getResultMsg()+"',playurl:''}";
					return null;
				}
				
			}else{
				json = "{result:1,message:'"+resp.getResultMsg()+"',playurl:''}";
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1,message:'处理失败，请重试！',playurl:''}";
			return null;
		}
		
	}
	
	public String qryFileIntent(String order_id,String file_id,String calleePhone) throws Exception{
		QueryCalllogReq req = new QueryCalllogReq();
		req.setNotNeedReqStrOrderId(order_id);
		Map params = null;
		try {
			params = VOX2WAVManager.getParams("CALLLOG_PARAMS");
			if(null==params||params.size()<=0){
				json = "{result:1,message:'接口基本参数未配置',playurl:''}";
				return null;
			}
		} catch (Exception e) {
			json = "{result:1,message:'处理失败，请重试！',playurl:''}";
			return null;
		}
		//req.setService(Const.getStrValue(params, "service"));
		req.setPartner(Const.getStrValue(params, "partner"));
		req.setInput_charset(Const.getStrValue(params, "input_charset"));
		req.setAccessParty(Const.getStrValue(params, "accessParty"));
		req.setBusinessType(Const.getStrValue(params, "businessType"));
		req.setTemplate_id(Const.getStrValue(params, "template_id"));
		req.setCallee(calleePhone);//被叫号码
		req.setTrafficInformation(file_id);
		Map map = VOX2WAVManager.qryCallFileIntent(file_id);
		String begin_time = (String)map.get("create_time");
		req.setCallbeginTime(begin_time.substring(0,begin_time.indexOf(" "))+" 00:00:00");
		req.setCallEndTime(begin_time.substring(0,begin_time.indexOf(" "))+" 23:59:59");
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		QueryCalllogResp resp = client.execute(req, QueryCalllogResp.class);
		if(!StringUtil.isEmpty(resp.getResultCode())&&"0".equals(resp.getResultCode())){
			String new_file = resp.getRecordInfo().getRecordFile();
			String callOnlyMark = resp.getRecordInfo().getCallOnlyMark();
			String callBeginTime = resp.getRecordInfo().getCallBeginTime();
			DownloadRecordReq reqe = new DownloadRecordReq();
			reqe.setNotNeedReqStrOrderId(order_id);
			reqe.setPartner(Const.getStrValue(params, "partner"));
			reqe.setInput_charset(Const.getStrValue(params, "input_charset"));
			reqe.setAccessParty(Const.getStrValue(params, "accessParty"));
			reqe.setCallBeginTime(callBeginTime);
			reqe.setCallOnlyMark(callOnlyMark);
			DownloadRecordResp respe = client.execute(reqe, DownloadRecordResp.class);
			if(!StringUtil.isEmpty(respe.getResultCode())&&"0".equals(respe.getResultCode())){
				String new_file_path = "";
				String new_file_name = "";
				String old_file_path = (String)map.get("file_path");
				new_file_name = new_file;
				new_file_path = old_file_path.substring(0, old_file_path.lastIndexOf("/"))+"/"+new_file_name;
				String update_sql = " update es_order_intent_file set file_path=?,file_name=? where file_id=? ";
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				baseDaoSupport.execute(update_sql, new String[]{new_file_path,new_file_name,file_id});
				String inf_file_path = new_file_path.substring(0, new_file_path.lastIndexOf(".vox"))+".wav";
				VOX2WAVManager.upload(new_file,new_file.substring(0, new_file.lastIndexOf(".vox"))+".wav");
				json = "{result:0,message:'成功',playurl:'"+inf_file_path+"'}";
				return null;
			}else{
				json = "{result:1,message:'录音记录获取失败："+respe.getResultMsg()+"',playurl:''}";
				return null;
			}
			
		}else{
			json = "{result:1,message:'"+resp.getResultMsg()+"',playurl:''}";
			return null;
		}
	}
	
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getIncludePage() {
		return includePage;
	}

	public void setIncludePage(String includePage) {
		this.includePage = includePage;
	}

	public String getFirst_load() {
		return first_load;
	}

	public void setFirst_load(String first_load) {
		this.first_load = first_load;
	}

	public String getCalllogPageRowCount() {
		return calllogPageRowCount;
	}

	public void setCalllogPageRowCount(String calllogPageRowCount) {
		this.calllogPageRowCount = calllogPageRowCount;
	}

	public Page getCalllogPage() {
		return calllogPage;
	}

	public void setCalllogPage(Page calllogPage) {
		this.calllogPage = calllogPage;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getCallBeginTime() {
		return callBeginTime;
	}

	public void setCallBeginTime(String callBeginTime) {
		this.callBeginTime = callBeginTime;
	}

	public String getCallerPhone() {
		return callerPhone;
	}

	public void setCallerPhone(String callerPhone) {
		this.callerPhone = callerPhone;
	}

	public String getCalleePhone() {
		return calleePhone;
	}

	public void setCalleePhone(String calleePhone) {
		this.calleePhone = calleePhone;
	}

	public String getCall_order_type() {
		return call_order_type;
	}

	public void setCall_order_type(String call_order_type) {
		this.call_order_type = call_order_type;
	}
}
