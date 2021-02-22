package zte.net.workCustomBean.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.action.OrderAutoAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.sqls.SqlUtil;
import com.ztesoft.remote.inf.IRemoteSmsService;

import consts.ConstsCore;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.bss.req.OrderListForWorkReq;
import zte.net.ecsord.params.bss.resp.OrderListForWorkResp;
import zte.net.ecsord.params.bss.vo.StaffList;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;

public class AutomaticBean extends BasicBusiBean {

	private Logger logger = Logger.getLogger(OrderAutoAction.class);
	@Resource
	private IOrdWorkManager ordWorkManager;
	@Resource
	private IOrderExtManager orderExtManager;
	private String instance_id;
	private String order_id;
	private IOrderFlowManager ordFlowManager;
	public String getInstance_id() {
		return instance_id;
	}

	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String doBusi() throws Exception{
		this.order_id=this.flowData.getOrderIntent().getOrder_id();
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String sql22="select a.county_id,a.top_share_num from es_order_intent a where a.order_id='"+order_id+"' and a.source_from = 'ECS'";
		Map queryForMap = baseDaoSupport.queryForMap(sql22, null);
		String sql_instance= " select instance_id from es_work_custom_node_ins  t where is_curr_step='1' and is_done='0' and order_id='"+order_id+"'" ;
        instance_id = baseDaoSupport.queryForString(sql_instance);
		String county_id=(String) queryForMap.get("county_id");
		String operator_num = (String) queryForMap.get("top_share_num");
		String work_reason ="系统自动派单";
		String work_type="09";
		try {

			// sgf 宽带一期工单派发
			OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(this.order_id);
			Boolean flags = ordWorkManager.isKDYQ(this.order_id, otreq);
			if (flags) {
				List<OrderAdslBusiRequest> lists = otreq.getOrderAdslBusiRequest();
				if (lists.size() > 0) {
					String adsl_addrS = lists.get(0).getAdsl_addr();
					if (StringUtils.isEmpty(adsl_addrS) || "-1".equals(adsl_addrS)) {
						return "false";
					}
				}
			}
			if (StringUtils.isEmpty(operator_num)) {
				return "false";
			}

			String order_id = this.order_id;
			Map work = new HashMap();
			work.put("order_id", this.order_id);
			// work.put("type", this.work_type);
			work.put("remark", work_reason);
			
			//自定义流程流程环节实例编号
			work.put("node_ins_id", SqlUtil.getStrValue(this.instance_id));
			//
			OrderListForWorkResp resp = getResp();
			if ("00000".equals(resp.getCode())) {
				String operator_id = "";
				String operator_name = "";
				String operator_office_id = "";
				List<StaffList> staffList = resp.getRespJson().getStaffList();
				if (null != staffList && staffList.size() > 0) {
					work.put("operator_id", operator_id);// 操作员工号
					work.put("operator_office_id", operator_office_id);// 操作点id
					work.put("operator_num", operator_num);// 操作员联系电话
					work.put("operator_name", operator_name);// 操作员姓名
					// 预留字段
					work.put("order_priority", "C");// 工单优先级预留，例如： A：高；B：中；C：普通
					work.put("order_time_limit", new Date());// 工单处理时限
					work.put("order_send_usercode", "0000");//标识系统自动派单
					String[] types = work_type.replace(" ", "").trim().split(",");// 多选框处理
					String flag = "";
					for (String type : types) {
						if (!StringUtil.isEmpty(type.trim())) {
							work.put("type", type.trim());
							flag += orderExtManager.saveWork(work);
						}
					}
					if (!StringUtil.isEmpty(flag) && flag.indexOf("0") != -1) {
						if (types.length == flag.length()) {// 全部保存成功
						} else {// 部分工单成功
							flag = flag.replace("0", "");
						}
						ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
						String bill_num = cacheUtil.getConfigInfo("SYN_BILL_NUM");
						String msg = "工单";// 发送内容
						// 工单类型01 – 收费单；02 -- 外勘单；03 – 实名单；04 – 挽留单；05 – 写卡单
						
						IDaoSupport baseDaoSupport1 = null;
						if (baseDaoSupport1 == null) {
						baseDaoSupport1 = SpringContextHolder.getBean("baseDaoSupport");
						 }
						
						 String sql = "select o.order_contact_phone, o.order_contact_addr, decode(i.phone_num, '',t.acc_nbr , i.phone_num) as acc_nbr from es_work_order o left join es_order_intent t on o.order_id = t.order_id left join es_order_items_ext i on o.order_id = i.order_id where o.source_from = '"+ManagerUtils.getSourceFrom()+"' and o.order_id = '"+order_id+"' and o.status='0' ";
						 List<Map<String,String>> list = baseDaoSupport1.queryForList(sql);
						 Map map = list.get(0);
						 
						Map data = new HashMap();
						data.put("order_contact_phone", MapUtils.getObject(map, "order_contact_phone"));
						data.put("order_contact_addr", MapUtils.getObject(map, "order_contact_addr"));
						data.put("acc_nbr", MapUtils.getObject(map, "acc_nbr"));
						
						IRemoteSmsService localRemoteSmsService = ApiContextHolder.getBean("localRemoteSmsService");
						String smsContent = localRemoteSmsService.getSMSTemplate("WORK_INFO", data);
						AopSmsSendReq smsSendReq = new AopSmsSendReq();
						smsSendReq.setSms_data(smsContent);
						smsSendReq.setBill_num(bill_num);// 短信发送号码
						smsSendReq.setService_num(operator_num);// 接受号码--省内联通号码
						smsSendReq.setOrder_id(order_id);
						ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
						AopSmsSendResp smsSendResp = client.execute(smsSendReq, AopSmsSendResp.class);
						if (smsSendResp != null && ConstsCore.ERROR_SUCC.equals(smsSendResp.getError_code())) {
							logger.info("工单提醒内容发送成功！");
						} else {
							logger.info("工单提醒内容发送失败！");
						}
					} else {
						if (flag.indexOf("订单") != -1) {
						} else if (flag.indexOf("单") != -1) {
							flag = flag + "已存在，请不要重复提交";
						}
					}
				}
			} else {
				return "false";
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "true";
	}
	
	private OrderListForWorkResp getResp() {
		OrderListForWorkReq req = new OrderListForWorkReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOrdertype("09");
		// 从页面取值
		// req.setCityCode(cityCode);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderListForWorkResp resp = client.execute(req, OrderListForWorkResp.class);
		return resp;
	}
	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception{
		return true;
	}
}