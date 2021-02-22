package com.ztesoft.net.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import params.order.req.OrderExceptionLogsReq;
import sun.misc.BASE64Encoder;
import util.EssOperatorInfoUtil;
import util.PDFReader;
import util.Utils;
import util.WYGOperatorIDSynUtil;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.bss.req.LocalGoodsStatusSynchronizationReq;
import zte.net.ecsord.params.bss.req.OrderAccountOrBuybackInformReq;
import zte.net.ecsord.params.bss.req.PurchaseCouponsExchangeReq;
import zte.net.ecsord.params.bss.req.PurchaseCouponsLockReq;
import zte.net.ecsord.params.bss.resp.BaseBSSResp;
import zte.net.ecsord.params.bss.resp.LocalGoodsStatusSynchronizationResp;
import zte.net.ecsord.params.bss.resp.OrderAccountOrBuybackInformResp;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ems.req.EmsRoutePushServiceReq;
import zte.net.ecsord.params.hs.req.DeliveNotifyReq;
import zte.net.ecsord.params.hs.req.OrderCheckOutReq;
import zte.net.ecsord.params.hs.req.ReturnWarehousingReq;
import zte.net.ecsord.params.hs.resp.DeliveNotifyResp;
import zte.net.ecsord.params.hs.resp.OrderCheckOutResp;
import zte.net.ecsord.params.hs.resp.ReturnWarehousingResp;
import zte.net.ecsord.params.oldsys.req.NotifyOrderInfo2OldSysRequest;
import zte.net.ecsord.params.oldsys.resp.NotifyOrderInfo2OldSysResponse;
import zte.net.ecsord.params.order.req.GoodsSynWMSReq;
import zte.net.ecsord.params.order.resp.GoodsSynWMSResp;
import zte.net.ecsord.params.wyg.req.MallOpIDSynInfoReq;
import zte.net.ecsord.params.wyg.req.NotifyOrderInfoWYGRequset;
import zte.net.ecsord.params.wyg.resp.MallOpIDSynInfoResp;
import zte.net.ecsord.params.wyg.resp.NotifyOrderInfoWYGResponse;
import zte.net.ecsord.params.wyg.resp.WYGAcceptanceQueryResp;
import zte.net.ecsord.params.wyg.vo.CBSSInfo;
import zte.net.ecsord.params.wyg.vo.StaffInfo;
import zte.net.ecsord.params.wyg.vo.StaffInfoResp;
import zte.net.ecsord.params.zb.req.StateSynToZBRequest;
import zte.net.ecsord.params.zb.req.StateUtil;
import zte.net.ecsord.params.zb.resp.StateSynToZBResponse;
import zte.net.ecsord.params.zb.vo.Order;
import zte.net.ecsord.rule.tache.TacheFact;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.ListUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.inf.infclient.paramsenum;
import com.ztesoft.net.ecsord.params.ecaop.req.CancelOrderStatusQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ObjectQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.RateStatusReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ResourCecenterAppReq;
import com.ztesoft.net.ecsord.params.ecaop.req.TabuserBtocbSubReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.CancelOrderStatusQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ObjectQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.RateStatusResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ResourCecenterAppResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.TabuserBtocbSubResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.ORDERINFOVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PROGRESSINFOVO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.OrderReleaseRecord;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.ICommonManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.net.util.SendWMSUtil;

import commons.CommonTools;
import consts.ConstsCore;

public class CommonManager extends BaseSupport implements ICommonManager {
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	
	@Resource
	private IOrderFlowManager ordFlowManager;

	@Override
	public BusiDealResult setOrdRefund(BusiCompRequest busiCompRequest) {
		BusiDealResult result = new BusiDealResult();
		
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		String order_id = fact.getOrder_id();
		String sendZB = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getSend_zb();
//		if(!EcsOrderConsts.SEND_ZB_1.equals(sendZB)){
//			return result;
//		} ZX add 2016-03-11 10:21 默认不与总部交互 IsSendZbHandler第58行注释
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		StateSynToZBRequest stateSynToZBReq = new StateSynToZBRequest();
		stateSynToZBReq.setNotNeedReqStrOrderId(order_id);
		stateSynToZBReq.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_CANLODR);
		stateSynToZBReq.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_CANLODR);
		stateSynToZBReq.setNotNeedReqStrStateChgDesc("异常单或测试单申请退单");
		StateSynToZBResponse stateSynToZBResponse = client.execute(stateSynToZBReq, StateSynToZBResponse.class);
		if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(stateSynToZBResponse.getRespCode())){
			result.setError_code("-999999");
			if(!StringUtil.isEmpty(stateSynToZBResponse.getRespDesc())){
				result.setError_msg("退单申请失败:"+stateSynToZBResponse.getRespDesc());
			}
			List<Order> Orders = stateSynToZBResponse.getOrders();
			if(!ListUtil.isEmpty(Orders)){
				result.setError_msg(Orders.get(0).getRespDesc());
			}
			if(StringUtil.isEmpty(result.getError_msg())){
				result.setError_msg("总部申请退单失败");
			}
			return result;
		}
		return result;
	}
	
	@Override
	public BusiDealResult orderAccountOrBuybackInform(String order_id,String operatorFlag) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderAccountOrBuybackInformReq req = new OrderAccountOrBuybackInformReq();
		req.setNotNeedReqStrOrderId(order_id);
		//设置是否返销
		req.setxTag(operatorFlag);
		OrderAccountOrBuybackInformResp infResp = client.execute(req, OrderAccountOrBuybackInformResp.class);
		if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(infResp.getError_code())){
			result.setError_msg(infResp.getError_msg());
			result.setError_code(infResp.getxCheckInfo());
		}
		return result;
	}

	@Override
	public BusiDealResult synOrderToOldSys(BusiCompRequest busiCompRequest) {
		BusiDealResult result = new BusiDealResult();
		
		try{
			//获取订单id
			Map params = busiCompRequest.getQueryParams();
			TacheFact fact = (TacheFact) params.get("fact");
			String order_id = fact.getOrder_id();
			String sys_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYS_CODE);
			if(StringUtil.isEmpty(sys_code)){
				CommonTools.addBusiError("-9999", "sys_code:不能为空");
			}
			//还原备份数据
			OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
//			String sql = SF.ecsordSql("CO_QUEUE_BAK_BY_OUT_ID");
			String sql ="select a.* from es_co_queue_bak a where " + 
					" 	 exists(select a.*,a.rowid from es_order_outer t where t.order_id = a.object_id " +
					"	 and t.old_sec_order_id = ? " + ManagerUtils.apSFromCond("t") + ")" + ManagerUtils.apSFromCond("a")+
					" union all   select a.* from es_co_queue a where " + 
					"    exists(select a.*,a.rowid from es_order_outer t where t.order_id = a.object_id " +
					"    and t.old_sec_order_id = ? " + ManagerUtils.apSFromCond("t") + ")" + ManagerUtils.apSFromCond("a");
			List<CoQueue> list =  this.baseDaoSupport.queryForList(sql, CoQueue.class, orderExt.getOut_tid(),orderExt.getOut_tid());
			if(null != list && !list.isEmpty()){
				CoQueue queBak = list.get(0);
				queBak.setService_code(Consts.SERVICE_CODE_CO_GUIJI);			//service_code改为老系统
				queBak.setSys_code(sys_code);
				queBak.setCo_id("");
				queBak.setCo_name("订单归集");
				queBak.setStatus(Consts.CO_QUEUE_STATUS_XYSB);
				this.baseDaoSupport.insert("es_co_queue", queBak);
				
				//add by wui 优化处理：统一由定时任务处理
//				try{
//					//add by wui老系统订单直接归档处理
//					ZteCommonTraceRule commonTraceRule = SpringContextHolder.getBean("zteCommonTraceRule");
//					commonTraceRule.orderStanding(queBak);
//					result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
//				}catch(Exception e){
//					logger.info("订单编号"+order_id+"同步老系统标准化出错!!");
//					e.printStackTrace();
//				}
			}else {
				CommonTools.addBusiError("-9999", "根据订单号[" + order_id + "]，未查询到相应的数据");
			}
		}catch(Exception e){
			result.setError_code("-9999");
			result.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public BusiDealResult synOrderStatusToOldSys(BusiCompRequest busiCompRequest) {
		BusiDealResult result = new BusiDealResult();
		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		NotifyOrderInfo2OldSysRequest req = new NotifyOrderInfo2OldSysRequest ();
		OrderExtBusiRequest orderExt = null;
		try{
			Map params = busiCompRequest.getQueryParams();
			TacheFact fact = (TacheFact) params.get("fact");
			String order_id = fact.getOrder_id();
			String process_type = fact.getProcess_type();
			String abnormal_type = fact.getAbnormal_type();
			orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			if(StringUtils.isEmpty(abnormal_type) && orderExt!=null){
				abnormal_type = orderExt.getAbnormal_type();
			}
			if(EcsOrderConsts.PROCESS_TYPE_2.equals(process_type)){//标记自动化异常
				req.setAutoOrderExceptionFlag(EcsOrderConsts.IS_DEFAULT_VALUE);
				req.setAutoExceptionType(fact.getException_type());
				req.setAutoExceptionDesc(fact.getException_desc());
				//异常记录
				OrderExceptionLogsReq exceptionLogReq = new OrderExceptionLogsReq();
				exceptionLogReq.setOrder_id(order_id);
					abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_3;

				exceptionLogReq.setAbnormal_type(abnormal_type);
				exceptionLogReq.setException_desc(fact.getException_desc());
				exceptionLogReq.setException_type(fact.getException_type());
				this.ordFlowManager.insertOrderExceptionLog(exceptionLogReq);
			}
			else if(EcsOrderConsts.PROCESS_TYPE_3.equals(process_type)){//标记人工异常
				req.setHandExceptionFlag(EcsOrderConsts.IS_DEFAULT_VALUE);
				req.setHandExceptionType(fact.getException_type());
				req.setHandExceptionDesc(fact.getException_desc());
				//异常记录
				OrderExceptionLogsReq exceptionLogReq = new OrderExceptionLogsReq();
				exceptionLogReq.setOrder_id(order_id);
				abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_1;
				exceptionLogReq.setAbnormal_type(abnormal_type);
				AdminUser user = new AdminUser();
				user = ManagerUtils.getAdminUser();
				if(null != user){
					exceptionLogReq.setMark_op_id(user.getUserid());
					exceptionLogReq.setMark_op_name(user.getUsername());
				}
				exceptionLogReq.setException_desc(fact.getException_desc());
				exceptionLogReq.setException_type(fact.getException_type());
				this.ordFlowManager.insertOrderExceptionLog(exceptionLogReq);
			}
			else if(EcsOrderConsts.PROCESS_TYPE_5.equals(process_type) || EcsOrderConsts.PROCESS_TYPE_1.equals(process_type)){//恢复异常
				if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type) || EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type)){//恢复自动化异常
					req.setAutoOrderExceptionFlag(EcsOrderConsts.NO_DEFAULT_VALUE);
					req.setAutoExceptionType(orderExt.getException_type());
					req.setAutoExceptionDesc("恢复异常");
					//异常处理记录
					this.ordFlowManager.updateOrderExceptionLog(order_id);
				}
				else if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_1.equals(abnormal_type)){//恢复人工标记的异常
					req.setHandExceptionFlag(EcsOrderConsts.NO_DEFAULT_VALUE);
					req.setHandExceptionType(orderExt.getException_type());
					req.setHandExceptionDesc("恢复异常");
					//异常处理记录
					this.ordFlowManager.updateOrderExceptionLog(order_id);
				}
			}
			req.setNotNeedReqStrOrderId(order_id);
			req.setProcessType(process_type);
			req.setFlowNode(fact.getSrc_tache_code());
			req.setTarFlowNode(fact.getTar_tache_code());
			req.setAutoExceptionSystem(fact.getException_from());
			
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			NotifyOrderInfo2OldSysResponse response = client.execute(req, NotifyOrderInfo2OldSysResponse.class);
			if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(response.getError_code())){
				result.setError_code("-9999");
				result.setError_msg(response.getError_msg());
			}
		}catch(Exception e){
			result.setError_code("-9999");
			result.setError_msg(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 保存终端串号/号码释放记录
	 * @return
	 */
	@Override
	public String saveReleaseRecord(OrderReleaseRecord record){
		String release_id = "";
		if(record!=null){
			record.setIs_deal(EcsOrderConsts.NOT_RELEASE_YET);//未处理
			if(!DateUtil.isDateFormat(record.getCreate_time(), DateUtil.DATE_FORMAT_2)){
				try {
					record.setCreate_time(DateUtil.getTime2());
				} catch (Exception e) {
					record.setCreate_time(null);
				}
			}
			record.setDeal_time("");
			release_id=this.baseDaoSupport.getSequences("S_ES_ORDER_RELEASE_RECORD");
			record.setRelease_id(release_id);
			record.setSource_from(ManagerUtils.getSourceFrom());
			this.baseDaoSupport.insert("es_order_release_record", record);
		}
		return release_id;
	}

	/**
	 * 新商城同步工号
	 * @return
	 */
	@Override
	public MallOpIDSynInfoResp wygOperatorIDSyn(MallOpIDSynInfoReq req) {
		MallOpIDSynInfoResp resp = new MallOpIDSynInfoResp();
		
		String error_msg = WYGOperatorIDSynUtil.checkReq(req);//校验请求参数合法性
		if(!"".equals(error_msg)){//数据不合法，返回失败
			resp.setResp_code("1");//失败
			resp.setResp_msg(error_msg);
		}else{//数据合法
			StringBuffer err_msg = new StringBuffer();
			List<StaffInfoResp> StaffInfo = new ArrayList<StaffInfoResp>();
			for(StaffInfo info:req.getStaffInfo()){//对每一个工号分别进行处理
				boolean success = true;//默认工号同步成功
				StringBuffer id_err_msg = new StringBuffer();
				
				String StaffCode = info.getStaffCode();//工号
				String CodeState = info.getCodeState();//员工编码状态
				
				StaffInfoResp staff = new StaffInfoResp();
				staff.setStaffCode(StaffCode);
				
				try{
					//以下订单系统工号更新或新增
					AdminUser user = null;
					String countsql = SF.ecsordSql("ADMINUSER_COUNT_BY_USERNAME");
					int count = this.baseDaoSupport.queryForInt(countsql,StaffCode);
					
					String action = ConstsCore.DB_ACTION_UPDATE;//默认更新
					
					if(count<1){//新建工号
						action = ConstsCore.DB_ACTION_INSERT;//新增
						user = new AdminUser();
						user.setUserid(StaffCode);
						user.setUsername(StaffCode);
						user.setSource_from(ManagerUtils.getSourceFrom());
						user.setFounder(0);//普通工号
					}else{//修改工号
						String sql = SF.ecsordSql("ADMINUSER_BY_USERNAME");
						user = (AdminUser) this.baseDaoSupport.queryForObject(sql, AdminUser.class, StaffCode);
						Integer i = user.getFounder();
						if(null!=i&&1==i){
							if(1==user.getFounder()){//超级管理员,不允许修改,直接跳到下一个工号
								err_msg.append("工号:"+StaffCode+"不允许修改.");
								staff.setResultCode("1");
								staff.setResultMsg("不允许修改");
								StaffInfo.add(staff);
								continue;
							}
						}
					}
					user.setUsertype(2);//新增以及更新的工号均设定为接口工号
					user.setRealname(info.getStaffName());//员工名称
					user.setPhone_num(info.getStaffPhone());//员工电话
					user.setEmail(info.getStaffEmail());//员工邮箱
					if(!StringUtils.isEmpty(info.getUpdateTime())){
						SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
						sdf.setLenient(false);
						user.setCol8(new Timestamp(sdf.parse(info.getUpdateTime()).getTime()));//更新时间
					}
					user.setCol10(info.getBelongDesc());//员工归属系统描述	没用的字段
					user.setCol9(info.getUpdateStaff());//更新员工工号	没用的字段
					
					if("10".equals(CodeState)){//正常
						user.setState(1);//使工号可以登录
					}else if("20".equals(CodeState)){//注销
						user.setState(0);//这个字段可以限制工号登录，但接口工号本身不需要登录，所以没什么意义
					}else{
						//什么都不做
					}
					
					if(ConstsCore.DB_ACTION_UPDATE.equals(action)){//更新
						this.baseDaoSupport.update("es_adminuser", user, "userid='"+user.getUserid()+"'");
					}else{//新增
						this.baseDaoSupport.insert("es_adminuser", user);
					}
				}catch(Exception e){
					e.printStackTrace();
					err_msg.append("工号:"+StaffCode+"注册/更新失败.");
					staff.setResultCode("1");
					staff.setResultMsg("注册/更新失败");
					StaffInfo.add(staff);
					continue;//直接处理下一个工号
				}
				
				//以下绑定cbss工号
				
				//删除旧的绑定关系
				try{
					EssOperatorInfoUtil.deleteBindRelByOrderGonghao(StaffCode);
				}catch (Exception e){
					success = false;
					e.printStackTrace();
					err_msg.append("工号:"+StaffCode+"解除旧的绑定关系失败.");
					id_err_msg.append("工号"+StaffCode+"绑定关系删除失败");
					//删除旧的绑定关系失败,影响范围：决定不让用的工号或绑定关系，还可以继续用
					//删除旧的绑定关系失败,可能会影响新的绑定关系的建立
				}
				
				//建立新的绑定关系
				if("10".equals(CodeState)&&null!=info.getCBSSInfo()){//注销的工号不建立新的绑定关系
					for(CBSSInfo cbssInfo:info.getCBSSInfo()){
						try{
							
							//添加新记录
							EmpOperInfoVo empInfo = new EmpOperInfoVo();
							empInfo.setOrder_gonghao(StaffCode);//订单工号
							empInfo.setEss_emp_id(cbssInfo.getCBSSStaff());//cbss工号
							empInfo.setProvince(cbssInfo.getProvince());//省份
							empInfo.setCity(cbssInfo.getCity());//城市
							empInfo.setSource_from(ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_operator_rel_ext", empInfo);
						}catch(Exception e){
							success = false;
							e.printStackTrace();
							err_msg.append("工号:"+StaffCode+"绑定cbss工号:"+cbssInfo.getCBSSStaff()+"失败.");
							id_err_msg.append("绑定cbss工号:"+cbssInfo.getCBSSStaff()+"失败.");
							continue;//依然处理本工号的下一个绑定关系
						}
						
					}
					EssOperatorInfoUtil.refreshorderOperIdInfoCache(StaffCode);//刷新订单操作员ID绑定ESS工号缓存
				}
				if(success){//整个处理过程完全没有异常
					staff.setResultCode("0");
					staff.setResultMsg("同步成功");
				}else{//删除旧的绑定关系或建立新的绑定关系出现异常会到这里
					staff.setResultCode("1");
					staff.setResultMsg(id_err_msg.toString());
				}
				
				StaffInfo.add(staff);
			}
			if("".equals(err_msg.toString())){
				resp.setResp_code("0");//成功
				resp.setResp_msg("同步成功");
			}else{
				resp.setResp_code("2");//部分失败
				resp.setResp_msg(err_msg.toString());
			}
			resp.setStaffInfo(StaffInfo);
		}
		// TODO Auto-generated method stub
		return resp;
	}
	
	
	/**
	 * Added on 2015-07-06
	 * @param goodsSynReq
	 * @param goodsSynResp
	 */
	public GoodsSynWMSResp synchronizedGoodsToWMS(GoodsSynWMSReq req)
	{
		GoodsSynWMSResp resp = new GoodsSynWMSResp();
		resp.setError_code("0");
		resp.setError_msg("同步成功");
		
		String result="";
		String service_code = req.getService_code();
		String goods_id = req.getGoods_id();
		String action_code = req.getAction_code();
		
		String sqlWMSStr = "SELECT e.addr FROM ES_INF_ADDR_WMS e where e.service_code= '"+ service_code + "'";
		try
		{
			String urlWMS = baseDaoSupport.queryForString(sqlWMSStr);
			if (null != urlWMS && !"".equals(urlWMS) && !"null".equals(urlWMS))
			{
				SendWMSUtil sWMS = new SendWMSUtil();
				String msg = searchProductWMS(goods_id,	action_code);
				// 虚拟货品不同步WMS
				if (null != msg && !"".equals(msg))
				{
					result = sWMS.getWSDLCall(urlWMS, msg);
					logger.info("货品["+ goods_id+ "]同步WMS报文：" + msg);
					logger.info("货品["+ goods_id+ "],同步WMS系统返回结果:" + result);
				}
				else
				{ 					
					resp.setError_cause(Consts.CO_QUEUE_STATUS_XYSB);		
					resp.setError_code("1");
					resp.setError_msg("虚拟货品不同步WMS");					
					return resp;
				}
				
				if(null!=result && !"".equalsIgnoreCase(result))
				{//同步有返回
					String ret_val =  JSONObject.fromObject(result).getJSONObject("goods_info_resp").get("resp_code").toString();
					if ("0".equalsIgnoreCase(ret_val)) {
						resp.setError_cause(Consts.RESP_CODE_000);
						resp.setError_code("0");
						resp.setError_msg("成功");
						return resp;
					}
					else
					{
						resp.setError_cause(Consts.CO_QUEUE_STATUS_XYSB);
						resp.setError_code("1");
						resp.setError_msg(JSONObject.fromObject(result).getJSONObject("goods_info_resp").get("resp_msg").toString());
						return resp;
					}
				}
			}
		}
		catch (Exception ex)
		{
			logger.info(ex.getMessage(), ex);
			resp.setError_cause(Consts.CO_QUEUE_STATUS_XYSB);
			resp.setError_code("1");
			resp.setError_msg("应用抛出异常啦");
			return resp;
		}
		return resp;	
	}
	
	/**
	 * 同步货品到WMS
	 * @param object_id
	 * @param action_code
	 * @return
	 */
	private String searchProductWMS(String object_id,String action_code){
		StringBuffer jsonStr = new StringBuffer();
		try {
			String goods_id = object_id;
			String str = "SELECT * from v_product ep where  ep.product_id ='"+goods_id+"'";
			Map tmpMap = baseDaoSupport.queryForMap(str);
			
			//ur:197933
			//请在同步货品给WMS的接口中过滤掉虚拟货品，即不同步虚拟货品
			String type_id = tmpMap.get("type_id").toString();	//货品大类
			String cat_id = tmpMap.get("cat_name").toString();	//货品小类
			//判断货品大类是否为虚拟货品
			String sql = "select count(*) nums from es_mall_config c where c.field_name = 'is_virtual' and c.field_value = '"+type_id+"'";
			String rs_type = baseDaoSupport.queryForString(sql);
			//判断货品小类是否为虚拟货品
			sql = "select count(*) nums from es_mall_config c where c.field_name = 'isvirtualgift' and c.field_value = '"+cat_id+"'";
			String rs_cat = baseDaoSupport.queryForString(sql);
			//虚拟货品不同步WMS
			if (!"0".equals(rs_type) || !"0".equals(rs_cat)) {
				logger.warn("货品[" + goods_id + "]是虚拟货品.大类["+type_id+"],小类["+cat_id+"].");
				return "";
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			String dtime = df.format(new Date());
			String seq = baseDaoSupport.getSequences("seq_goods");
			jsonStr.append("{\"goods_info_req\":");
			jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");
			jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");
			jsonStr.append("\"sku\":").append("\"").append((String)tmpMap.get("skua")).append("\",");
			jsonStr.append("\"action\":").append("\"").append(action_code).append("\",");
			jsonStr.append("\"goods_name\":").append("\"").append(tmpMap.get("name")).append("\",");
			jsonStr.append("\"goods_brand\":").append("\"").append(tmpMap.get("brand_name")).append("\",");
			jsonStr.append("\"goods_category\":").append("\"").append(type_id).append("\",");
			jsonStr.append("\"goods_type\":").append("\"").append(cat_id).append("\",");
			String tmpa=tmpMap.get("model_code")==null?"":tmpMap.get("model_code").toString();
			jsonStr.append("\"goods_class\":").append("\"").append(tmpa).append("\",");//tmpMap.get("model_code")
			if (null == tmpMap.get("state")) {
				jsonStr.append("\"goods_state\":").append("\"").append("0").append("\",");
			}else {
				jsonStr.append("\"goods_state\":").append("\"").append(tmpMap.get("state")).append("\",");
			}
			jsonStr.append("\"goods_attr\":[");
			String strpar = tmpMap.get("params").toString();
			strpar=strpar.substring(1,strpar.lastIndexOf("]"));
			paramsL pl = null;
			try {
				pl = JsonUtil.fromJson(strpar, paramsL.class);
				if(pl.getParamList().size()>0){
					for(int i=0;i<pl.getParamList().size();i++){
						paramsenum tmp=pl.getParamList().get(i);
						jsonStr.append("{");
						jsonStr.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
						jsonStr.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
						Map tmpMapdc=null;
						Map tmpMap_yanshe=null;
						if(null != tmp.getAttrcode() && !"".equalsIgnoreCase(tmp.getAttrcode())){
							String strdc="select DC_SQL from es_dc_sql  a where  a.dc_name = '"+tmp.getAttrcode() +"'";
							try {
								tmpMapdc = baseDaoSupport.queryForMap(strdc);
								String dc_sql =  tmpMapdc.get("dc_sql").toString();
								String str_yanshe="select T2.value_desc from (select value_desc,VALUE,'ECS' source_from  from ("+dc_sql+") T ) T2 where T2.VALUE = '"+tmp.getValue().toString() + "'";
								tmpMap_yanshe = baseDaoSupport.queryForMap(str_yanshe);
								jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
								jsonStr.append("\"param_value\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
							} catch (Exception e) {
								
							}
						}else {
							jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
							jsonStr.append("\"param_value_code\":").append("\"").append("").append("\",");
						}
						jsonStr.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\"");
						jsonStr.append("}");
						if(i<pl.getParamList().size()-1){
							jsonStr.append(",");
						}
							
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			jsonStr.append("]");
			jsonStr.append("}}");
		} catch (Exception e) {
			jsonStr = new StringBuffer();
			logger.info("同步货品["+object_id+"]失败.");
			logger.info(e.getMessage(), e);
		}
		return jsonStr.toString().replaceAll("\"null\"", "\"\"");
	}

	@Override
	public BusiDealResult ordFlowTraceSyn(String order_id,StateUtil vo) {
		BusiDealResult result = new BusiDealResult();
		NotifyOrderInfoWYGRequset req = new NotifyOrderInfoWYGRequset();
		req.setNotNeedReqStrOrderId(order_id);
		if(vo!=null && StringUtils.isNotEmpty(vo.getNotNeedReqStrStateTag())){
			req.setTrace_code(vo.getNotNeedReqStrStateTag());
			req.setDeal_desc(vo.getNotNeedReqStrStateChgDesc());
		}
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOrderInfoWYGResponse infResp = client.execute(req, NotifyOrderInfoWYGResponse.class);

		if (!infResp.getResp_code().equals(EcsOrderConsts.NEW_SHOP_INF_SUCC_CODE)){//接口没有返回成功编码
			result.setError_msg("错误编码：" + infResp.getResp_code() + ";错误信息："
					+ infResp.getResp_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务失败;BusiDealResult.error_code有默认值"0000",要防坑
		}else{//接口返回成功编码
			result.setError_msg(infResp.getResp_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务成功;BusiDealResult.error_code有默认值"0000",这里不动都没问题
		}
		
		return result;
	}

	@Override
	public BusiDealResult purchaseCouponsLock(String order_id) {
		BusiDealResult result = new BusiDealResult();
		
		PurchaseCouponsLockReq req = new PurchaseCouponsLockReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BaseBSSResp resp = client.execute(req, BaseBSSResp.class);
		
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){//接口级别错误(业务级别错误也被封装到接口级别)
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息："+ resp.getError_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
			//更新代金券状态为锁定
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.COUPONS_LOCKED},//待换成常量
					new String[]{EcsOrderConsts.COUPONS_STATUS_1});
		}
		return result;
	}

	@Override
	public BusiDealResult purchaseCouponsExchange(String order_id) {
		BusiDealResult result = new BusiDealResult();
		
		PurchaseCouponsExchangeReq req = new PurchaseCouponsExchangeReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BaseBSSResp resp = client.execute(req, BaseBSSResp.class);//转兑返回没有包体,直接用Base类(实际是哪个类由数据库配置决定)
		
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){//接口级别错误(业务级别错误也被封装到接口级别)
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息："+ resp.getError_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
			//更新代金券状态为已转兑
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.COUPONS_LOCKED},//待换成常量
					new String[]{EcsOrderConsts.COUPONS_STATUS_2});
		}
		return result;
	}
	
	@Override
	public WYGAcceptanceQueryResp acceptanceQuery(String order_id){
		WYGAcceptanceQueryResp resp = new WYGAcceptanceQueryResp();
		byte[] pdfByte;
		String ss = "";
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String accept_type = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.WYG_ACCEPT_TYPE, goods_type);
		String temp = "";
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(StypeConsts.GOODSTYPE_VS_ACCEPTTEMP);
        for(Map<String,String> map:list){
        	if(StringUtils.equals(map.get("pkey"), goods_type)){
        		temp = map.get("pname");
        	}
        }
		if(StringUtils.isEmpty(accept_type)){
			resp.setResp_code(EcsOrderConsts.WYG_INF_FAIL_CODE);
			resp.setResp_msg("此订单模板不在协议可查询模板范围。");
		}else if(StringUtils.isEmpty(temp)){
			resp.setResp_code(EcsOrderConsts.WYG_INF_FAIL_CODE);
			resp.setResp_msg("未配置打印模板,请联系运维人员处理。");
		}else{
			try{
				Map map = Utils.getAcceptanceFormMap(order_id);
				pdfByte = PDFReader.editPdfTemplate(temp,map);
				ss = new BASE64Encoder().encode(pdfByte);
				resp.setResp_code(EcsOrderConsts.WYG_INF_SUCC_CODE);
				resp.setResp_msg("查询成功");
			}catch(Exception e){
				logger.info(order_id+"受理单模板获取报错:");
				e.printStackTrace();
				resp.setResp_code(EcsOrderConsts.WYG_INF_FAIL_CODE);
				resp.setResp_msg("获取受理单数据出错");
			}
		}
//		resp.setSerial_no("");
//		resp.setTime("");
		resp.setAccept_type(accept_type);
		resp.setAccept_form(ss);
		
		return resp;
	}

	/**
	 * 出库信息回传SAP
	 * @return
	 */
	@Override
	public BusiDealResult deliveNotifyHS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		
		DeliveNotifyReq req = new DeliveNotifyReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		DeliveNotifyResp resp = client.execute(req, DeliveNotifyResp.class);
		
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){//接口级别错误(业务级别错误也被封装到接口级别)
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息："+ resp.getError_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
		}
		return result;
	}

	/**
	 * 退货入库传输SAP
	 * @return
	 */
	@Override
	public BusiDealResult returnWarehousingHS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		
		ReturnWarehousingReq req = new ReturnWarehousingReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ReturnWarehousingResp resp = client.execute(req, ReturnWarehousingResp.class);
		
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){//接口级别错误(业务级别错误也被封装到接口级别)
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息："+ resp.getError_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
		}
		return result;
	}

	/**
	 * 仓储商采购退货订单出库回传SAP接口
	 * @return
	 */
	@Override
	public BusiDealResult orderCheckOutB2B(String order_id) {
		BusiDealResult result = new BusiDealResult();
		
		OrderCheckOutReq req = new OrderCheckOutReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderCheckOutResp resp = client.execute(req, OrderCheckOutResp.class);
		
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){//接口级别错误(业务级别错误也被封装到接口级别)
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息："+ resp.getError_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
		}
		return result;
	}

	@Override
	public BusiDealResult setOrderStatusAllArchive(String order_id) {
		BusiDealResult dealResult = new BusiDealResult();
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(null == orderTreeBusiRequest){
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("订单OrderId:" + order_id + " 订单树不存在");
			return dealResult;
		}
		try {
			OrderBusiRequest orderBusiRequest = orderTreeBusiRequest.getOrderBusiRequest();
			orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_83);
			orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderBusiRequest.store();
			dealResult.setError_code(ConstsCore.ERROR_SUCC);
			dealResult.setError_msg("执行成功！");
		} catch (Exception e) {
			e.printStackTrace();
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("执行失败！");
		}
		return dealResult;
	}
	
	@Override
	public BusiDealResult setOrderStatusPartArchive(String order_id) {
		BusiDealResult dealResult = new BusiDealResult();
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(null == orderTreeBusiRequest){
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("订单OrderId:" + order_id + " 订单树不存在");
			return dealResult;
		}
		try {
			OrderBusiRequest orderBusiRequest = orderTreeBusiRequest.getOrderBusiRequest();
			orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_82);
			orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderBusiRequest.store();
			dealResult.setError_code(ConstsCore.ERROR_SUCC);
			dealResult.setError_msg("执行成功！");
		} catch (Exception e) {
			e.printStackTrace();
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("执行失败！");
		}
		return dealResult;
	}
	
	@Override
	public BusiDealResult emsRoutePush(EmsRoutePushServiceReq req){
		return null;
	}
	
	@Override
	public BusiDealResult localGoodsStatusSynchronization(String order_id){
		BusiDealResult dealResult = new BusiDealResult();
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(null == orderTreeBusiRequest) {
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("订单OrderId:" + order_id + " 订单树不存在");
			return dealResult;
		}
		LocalGoodsStatusSynchronizationReq req = new LocalGoodsStatusSynchronizationReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		LocalGoodsStatusSynchronizationResp resp = client.execute(req, LocalGoodsStatusSynchronizationResp.class);
		
		if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(resp.getError_code())){//接口级别错误(业务级别错误也被封装到接口级别)
			dealResult.setError_msg("错误编码：" + resp.getError_code() + ";错误信息："+ resp.getError_msg());
			dealResult.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}else{
			dealResult.setError_code(ZjEcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
			dealResult.setError_msg("本地商城订单状态同步成功！");
		}
		
		return dealResult;
	}
	@Override
	public BusiDealResult zsRefundApply(String order_id){
		BusiDealResult dealResult = new BusiDealResult();
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(null == orderTreeBusiRequest) {
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("订单OrderId:" + order_id + " 订单树不存在");
			return dealResult;
		}
		///////////////////////////改成调用爬虫
		LocalGoodsStatusSynchronizationReq req = new LocalGoodsStatusSynchronizationReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		LocalGoodsStatusSynchronizationResp resp = client.execute(req, LocalGoodsStatusSynchronizationResp.class);
		
		if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(resp.getError_code())){//接口级别错误(业务级别错误也被封装到接口级别)
			dealResult.setError_msg("错误编码：" + resp.getError_code() + ";错误信息："+ resp.getError_msg());
			dealResult.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}else{
			dealResult.setError_code(ZjEcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
			dealResult.setError_msg("本地商城订单状态同步成功！");
		}
		
		return dealResult;
	}
	
	public BusiDealResult cancelOrderStatusQry(String order_id){
		BusiDealResult dealResult = new BusiDealResult();
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(null == orderTreeBusiRequest) {
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("订单OrderId:" + order_id + " 订单树不存在");
			return dealResult;
		}
		CancelOrderStatusQryReq req = new CancelOrderStatusQryReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CancelOrderStatusQryResp resp = client.execute(req, CancelOrderStatusQryResp.class);
		if(!StringUtil.isEmpty(resp.getCode())&&StringUtil.equals(resp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)){
			String is_back = resp.getRespJson().getIs_back();
			if(!StringUtil.isEmpty(is_back)&&StringUtil.equals(is_back, EcsOrderConsts.WYG_BACK_ORDER_STATUS_1)){
				//CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.BSS_REFUND_STATUS}, new String[]{EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC});	
				dealResult.setError_code(ConstsCore.ERROR_SUCC);
				dealResult.setError_msg("执行成功！");
			}else{
				dealResult.setError_code(ConstsCore.ERROR_FAIL);
				dealResult.setError_msg("执行失败！订单未返销");
			}
			
		}else{
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg(resp.getMsg());
		}
		
		return dealResult;
		
	}
	
	public BusiDealResult tabuserBtocbSub(String order_id){
		BusiDealResult dealResult = new BusiDealResult();
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(null == orderTreeBusiRequest) {
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("订单OrderId:" + order_id + " 订单树不存在");
			return dealResult;
		}
		TabuserBtocbSubReq req = new TabuserBtocbSubReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		TabuserBtocbSubResp resp = client.execute(req, TabuserBtocbSubResp.class);
		if(!StringUtil.isEmpty(resp.getCode())&&StringUtil.equals(resp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)){
			String operator_id = resp.getRespJson().getOperator_id();
			String dept_id = resp.getRespJson().getDept_id();
			String channelType = resp.getRespJson().getChannelType();
			String county_id= resp.getRespJson().getCounty_id();
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.CHANNEL_TYPE,AttrConsts.DISTRICT_CODE,"cbss_out_operator","cbss_out_office",AttrConsts.ORDER_CHA_CODE}, new String[]{channelType,county_id,operator_id,dept_id,dept_id});
			dealResult.setError_code(ConstsCore.ERROR_SUCC);
			dealResult.setError_msg("执行成功！");
		}else{
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg(resp.getMsg());
		}
		
		return dealResult;
		
	}
	
	public BusiDealResult resourCecenterApp(String order_id){
		BusiDealResult dealResult = new BusiDealResult();
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(null == orderTreeBusiRequest) {
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg("订单OrderId:" + order_id + " 订单树不存在");
			return dealResult;
		}
		ResourCecenterAppReq req = new ResourCecenterAppReq();
		req.setNotNeedReqStrOrderId(order_id);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ResourCecenterAppResp resp = client.execute(req, ResourCecenterAppResp.class);
		if(!StringUtil.isEmpty(resp.getCode())&&StringUtil.equals(resp.getCode(), EcsOrderConsts.INF_RESP_CODE_00000)){
			if(!StringUtil.isEmpty(resp.getRespJson().getSELECTED_NUM_RSP().getRESP_CODE())&&StringUtil.equals(resp.getRespJson().getSELECTED_NUM_RSP().getRESP_CODE(), EcsOrderConsts.INF_RESP_CODE_0000)){
				dealResult.setError_code(ConstsCore.ERROR_SUCC);
				dealResult.setError_msg("执行成功！");
			}else{
				dealResult.setError_code(ConstsCore.ERROR_FAIL);
				dealResult.setError_msg(resp.getRespJson().getSELECTED_NUM_RSP().getRESP_DESC());
			}
		}else{
			dealResult.setError_code(ConstsCore.ERROR_FAIL);
			dealResult.setError_msg(resp.getMsg());
		}
		
		return dealResult;
		
	}
	
	@Override
	public BusiDealResult objectQry(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ObjectQryReq req = new ObjectQryReq();
		req.setNotNeedReqStrOrderId(order_id);
		//设置是否返销
		ObjectQryResp infResp = client.execute(req, ObjectQryResp.class);
		if(!EcsOrderConsts.INF_RESP_CODE_00000.equals(infResp.getCode())){
			result.setError_msg(infResp.getMsg());
			result.setError_code(infResp.getCode());
		}else{
			if(infResp.getRespJson()==null||infResp.getRespJson().size()<1){
				result.setError_code("9999");
				result.setError_msg("未返回赠品ID！");
			}
			List<OrderAdslBusiRequest> orderAdslBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderAdslBusiRequest();
			for (int i = 0; i < orderAdslBusiRequest.size(); i++) {
				if(!StringUtils.isEmpty(orderAdslBusiRequest.get(i).getProduct_type())&&"TV".equals(orderAdslBusiRequest.get(i).getProduct_type())){
					orderAdslBusiRequest.get(i).setModerm_id(infResp.getRespJson().get(0).getObject_id());
					orderAdslBusiRequest.get(i).setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderAdslBusiRequest.get(i).setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderAdslBusiRequest.get(i).store();
				}
			}
			result.setError_code(EcsOrderConsts.INF_RESP_CODE_0000);
			result.setError_msg("执行成功！");
		}
		return result;
	}
	
	
	@Override
	public BusiDealResult rateStatusQry(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		RateStatusReq req = new RateStatusReq();
		req.setNotNeedReqStrOrderId(order_id);
		RateStatusResp infResp = client.execute(req, RateStatusResp.class);
		if(!StringUtils.isEmpty(infResp.getRespJson().getUNI_BSS_BODY().getRESP_CODE())
				&&EcsOrderConsts.INF_RESP_CODE_0000.equals(infResp.getRespJson().getUNI_BSS_BODY().getRESP_CODE())){
			ORDERINFOVO ORDER_INFO = infResp.getRespJson().getUNI_BSS_BODY().getORDER_INFO();
			String del_sql = " delete from es_construction_schedule where accept_no = ? ";
			String accept_no = ORDER_INFO.getACCEPT_NO();
			Map<String,String> addMap=new HashMap<String, String>();
			addMap.put("accept_no", accept_no);
			addMap.put("user_name", ORDER_INFO.getUSER_NAME());
			addMap.put("eparchy_code", ORDER_INFO.getEPARCHY_CODE());
			addMap.put("product_type_code", ORDER_INFO.getPRODUCT_TYPE_CODE());
			List<PROGRESSINFOVO> PROGRESS_INFO = ORDER_INFO.getPROGRESS_INFO();
			List<Map<String,String>> batchMap = new ArrayList<Map<String,String>>();
			List<Map<String, String>> list=new ArrayList<Map<String,String>>();
			if(PROGRESS_INFO==null&&PROGRESS_INFO.size()<=0){
				result.setError_code("9999");
				result.setError_msg("未查询到进度明细！");
				return result;
			}else{
				baseDaoSupport.execute(del_sql, new String[]{accept_no});
			}
			for (PROGRESSINFOVO progressinfovo : PROGRESS_INFO) {
				Map<String,String> newDataMap=new HashMap<String, String>();
				if(addMap!=null&&!addMap.isEmpty()){
					newDataMap.putAll(addMap);
				}
				Map<String, String> map = (Map<String, String>)PROGRESS_INFO;
				Set<String> set=map.keySet();
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					String new_value=map.get(key);
					newDataMap.put(key, new_value);
				}
				batchMap.add(newDataMap);
			}
			String insert_sql = " insert into es_construction_schedule "
					          + " (accept_no,user_name,eparchy_code,product_type_code,progress_no,progress_stage_name,progress_desc,progress_state,stage_start_time,staff_start_time,staff_name,staff_mode,para_id,para_value,source_from) "
					          + " values(:accept_no,:user_name,:eparchy_code,:product_type_code,:progress_no,:progress_stage_name,:progress_desc,:progress_state,:stage_start_time,:staff_start_time,:staff_name,:staff_mode,:para_id,:para_value,:source_from) ";
			this.baseDaoSupport.batchExecuteByListMap(insert_sql, batchMap);
		}else{
			result.setError_code(infResp.getRespJson().getUNI_BSS_BODY().getRESP_CODE());
			result.setError_msg(infResp.getRespJson().getUNI_BSS_BODY().getRESP_DESC());
			return result;
		}
		return result;
	}

    @SuppressWarnings("static-access")
    @Override
    public EmpOperInfoVo findEssOperatorInfo(String oper_from,String city_id,String order_id) {
        EssOperatorInfoUtil essinfo = new EssOperatorInfoUtil();
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String groupFlowKg = cacheUtil.getConfigInfo("groupFlowKg");
        String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);//地市
        String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);//地市
        if(StringUtils.isNotEmpty(groupFlowKg) &&"1".equals(groupFlowKg)&& "10093".equals(order_from) && "221668199563784192".equals(cat_id)){
             return essinfo.getEmpInfoFromDataBase(null,oper_from,city_id);//无线宽带线上取默认的渠道和工号信息
        }else{
            return null;
        }
    }
}
