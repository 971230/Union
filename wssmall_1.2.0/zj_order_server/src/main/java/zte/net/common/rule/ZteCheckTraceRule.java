package zte.net.common.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.remote.inf.IRemoteSmsService;

import commons.CommonTools;
import consts.ConstsCore;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion; 
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.workCustom.po.ES_WORK_SMS_SEND; 

@ZteServiceAnnontion(trace_name = "ZteCheckTraceRule", trace_id = "1", version = "1.0", desc = "订单校验环节")
public class ZteCheckTraceRule extends ZteTraceBaseRule{
	private static Logger logger = Logger.getLogger(ZteCheckTraceRule.class);
	/**
	 * 订单校验环节入口
	 */
	@Override
	@ZteMethodAnnontion(name = "订单校验环节入口", group_name = "订单校验环节", order = "1", page_show = true, path = "zteCheckTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_1);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();

		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	
	/**
	 * 下一步(订单校验环节)
	 */
	@ZteMethodAnnontion(name = "下一步(订单校验环节)", group_name = "订单校验环节", order = "2", page_show = true, path = "zteCheckTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = this.nextflow(busiCompRequest, false, "K");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 匹配工号
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name = "匹配工号", group_name = "订单校验环节", order = "3", page_show = true, path = "zteCheckTraceRule.matchingNumber")
	public BusiCompResponse matchingNumber(BusiCompRequest busiCompRequest) throws ApiBusiException {
		String order_id = busiCompRequest.getOrder_id();
		String district_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "district_code");
		String order_from = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_from();
		IEcsOrdManager ecsOrdManager = SpringContextHolder.getBean("ecsOrdManager");
		List list = ecsOrdManager.getDcSqlByDcName("DC_COUNTY_USERS_"+order_from);
		String gonghao  = "";
		String allot_status = "0";
		//直接匹配县分
		for (int i = 0; i < list.size(); i++) {
			String value = Const.getStrValue((Map) list.get(i), "value");
			String value_desc = Const.getStrValue((Map) list.get(i), "value_desc");
			if (StringUtil.equals(value, district_code)) {
				gonghao = value_desc;
				break;
			}
		}
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		//匹配地市工号
		if (StringUtils.isEmpty(gonghao) && !StringUtils.isEmpty(orderExt.getOrder_city_code())) {
			list = ecsOrdManager.getDcSqlByDcName("DC_CITY_USERS_" + order_from);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					String value = Const.getStrValue((Map) list.get(i), "value");
					String value_desc = Const.getStrValue((Map) list.get(i), "value_desc");
					if (StringUtil.equals(value, orderExt.getOrder_city_code())) {
						gonghao = value_desc;
						break;
					}
				}
			}
			allot_status = "1";//短信暂不发送
		}
		//匹配省份工号
		if(StringUtils.isEmpty(gonghao)){
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			gonghao = cacheUtil.getConfigInfo("PRO_USER_ID_"+order_from);
			allot_status = "1";
		}
		if(!StringUtils.isEmpty(gonghao)){
			String trace_id = orderExt.getFlow_trace_id();
			OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance().getOrderLockBusiRequest(orderTree,trace_id);
			if(orderLockBusiRequest==null){//根据当前环节编码找不到锁定记录，则认为是没有锁定，此时要可以锁定订单
				orderLockBusiRequest = new OrderLockBusiRequest();
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				orderLockBusiRequest.setLock_id(baseDaoSupport.getSequences("o_outcall_log"));
				orderLockBusiRequest.setOrder_id(order_id);
				orderLockBusiRequest.setTache_code(trace_id);
				orderLockBusiRequest.setLock_user_id(gonghao);
				orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
				try {
					orderLockBusiRequest.setLock_time(DateUtil.getTime2());
				} catch (FrameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				orderLockBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
				orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				orderLockBusiRequest.store();
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{"allot_status"}, new String[]{allot_status});
			/**
			 * Add by Wcl
			 * 增加发短信条件判断，已分配订单才发送短信
			 */
			if(StringUtils.equals("0", allot_status)) {
				/**
				 * Add by wcl
				 * 获取该县分下所有工号，发短信
				 * Todo
				 * 根据district_code 在表es_adminuser中找到所有对应地市下的工号      user_id->工号，phone_num -> 手机号
				 * orgid -> es_organization -> org_name
				 * 17681818166
				 * 短信信息：
				 * 您的【${orgname}订单池】收到待处理订单${orderid}（订单编号），可用工号【${userid}】登录订单中心领取处理。
				 */
				try {
				    CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				    String kdyqwitch = cacheUtil.getConfigInfo("kdyqSemss");//配置商品小类  b的副卡
			        List<Map> gonghaoInfos = java.util.Collections.emptyList();
				    if("0".equals(kdyqwitch)){
				        gonghaoInfos = ecsOrdManager.queryAllGonghaoByCountyId(district_code,gonghao);
				    }
					if(gonghaoInfos != null && gonghaoInfos.size() > 0) {
						ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
						List<ES_WORK_SMS_SEND> listPojo = new ArrayList<ES_WORK_SMS_SEND>();
						for (Map map : gonghaoInfos) {
							Map data = new HashMap();
							data.put("orgname", MapUtils.getString(map, "org_name")); 
							data.put("orderid", order_id);
							data.put("userid", MapUtils.getString(map, "userid"));
							
							IRemoteSmsService localRemoteSmsService = ApiContextHolder.getBean("localRemoteSmsService");
							String smsContent = localRemoteSmsService.getSMSTemplate("SMS_LOGIN_CODE", data);
							ES_WORK_SMS_SEND smsSendPojo = new ES_WORK_SMS_SEND();
							smsSendPojo.setBill_num("10010");// 短信发送号码
							smsSendPojo.setService_num(MapUtils.getString(map, "phone_num"));// 接受号码--省内联通号码
							smsSendPojo.setSms_data(smsContent);
							smsSendPojo.setOrder_id(order_id);
							listPojo.add(smsSendPojo);
							/*
							AopSmsSendReq smsSendReq = new AopSmsSendReq();
							smsSendReq.setBill_num("10010");// 短信发送号码
							smsSendReq.setService_num(MapUtils.getString(map, "phone_num"));// 接受号码--省内联通号码
							smsSendReq.setSms_data(smsContent);
							TaskThreadPool taskPool = new TaskThreadPool(new Task(smsSendReq) {
								@Override
								public ZteResponse execute(ZteRequest zteRequest) throws Exception {
									// TODO Auto-generated method stub
									AopSmsSendReq smsSendReq = (AopSmsSendReq) zteRequest;
									ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
									AopSmsSendResp resp =client.execute(smsSendReq, AopSmsSendResp.class);
									return resp;
								}
							});
							ThreadPoolFactory.orderExecute(taskPool);
							*/
						}
						AopSmsSendReq smsSendReq = new AopSmsSendReq();
						smsSendReq.setListpojo(listPojo);
						client.execute(smsSendReq, AopSmsSendResp.class);
					}
				}catch(Exception e) {
					logger.info(e.getMessage());
					logger.info(e.getMessage());
				}
			}
		}else{
			CommonTools.addBusiError("-1","匹配不到工号");
		}
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	

}
