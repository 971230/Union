package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Cloud;
import com.ztesoft.net.mall.core.model.Contract;
import com.ztesoft.net.mall.core.service.IReportManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportManager extends BaseSupport implements IReportManager {

	private final static String CONDITION_SQL = "conditionSql";
	private final static String QUERY_FIELD = "queryField";
	private final static String QUERY_PARAM = "queryParam";
	private final static String GROUP_FIELD = "groupField";
	
	
	/**
	 * 云卡激活率
	 */
	@Override
	public Page getCloudActivePage(String userid, String[] lan_id,  String start_time,
			String end_time, int pageNo, int pageSize, boolean isExport) {

		Map<String, Object> map = buildActiveCloud(userid, lan_id, null, start_time, end_time, false);
		StringBuffer sql = new StringBuffer();
		/*sql.append("select sum(1) all_count, sum(decode(t.state,'2',1,0)) active_count, " +
				" round(sum(decode(t.state,'2',1,0))/sum(1),2)*100||'%' active_rate, " +
				" u.username, t.lan_name," +
				" t.lan_id,u.realname, " + map.get(QUERY_FIELD) + " from es_cloud_info t, es_order o, es_adminuser u ");
		*/
		//DBTUtil.decode("t.state", "2", "1", "0");
		sql.append(SF.orderSql("SERVICE_CLOUD_ACTIVE_SELECT").replace("#column", map.get(QUERY_FIELD).toString()));
		
		sql.append(map.get(CONDITION_SQL));
		sql.append(" group by " + map.get(GROUP_FIELD) + ",u.username,t.lan_name,t.lan_id,u.realname");
		logger.debug("====云卡激活率====：" + sql.toString());
		
		Map<String, Object> param = (Map<String, Object>) map.get(QUERY_PARAM);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql.toString(), param);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql.toString(), pageNo, pageSize, param);
		}
	}
	
	/**
	 * 云卡激活率详情
	 */
	@Override
	public Page getCloudActiveDetail(String userid, String[] lan_id, String cloudState, 
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport) {
		
		Map<String, Object> map = buildActiveCloud(userid, lan_id, cloudState, start_time, end_time, true);
		StringBuffer sql = new StringBuffer();
		sql.append(SF.orderSql("SERVICE_CLOUD_ACTIVE_DETAIL_SELECT"));
		//DBTUtil.decode("t.state", "0", "'可用'", "1", "'预占'", "2", "'已用'" , "3", "'失效'","''"); decode(t.state, 0, '可用', 1, '预占', 2, '已用' , 3, '失效','')
		sql.append(map.get(CONDITION_SQL));
		sql.append(" order by t.create_date desc");
		logger.debug("====云卡激活率详情====：" + sql.toString());
		
		Map<String, Object> param = (Map<String, Object>) map.get(QUERY_PARAM);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql.toString(), param);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql.toString(), pageNo, pageSize, param);
		}
	}
	
	
	@Deprecated
	@Override
	public Page getCloudActiveOfPartnerPage(String userid, String state, String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport ) {
		String cond = buildPartnerCondition(userid, null, state, start_time, end_time); 
		String sql = SF.orderSql("SERVICE_CLOUD_ACTIVE_PARTNER_SELECT").replace("#cond", cond);
		logger.debug("====云卡激活率-分销商：" + sql.toString());
		
		Map<String, Object> map = buildParamMap(userid, null, null,	start_time, end_time);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql.toString(), map);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize, map);
		}
	}
	
	@Deprecated
	@Override
	public Page getCloudActiveOfPartnerDetail(String userid, String[] lan_id, String state, String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport ) {
		StringBuffer sql = new StringBuffer(SF.orderSql("SERVICE_CLOUD_PARTNER_DETAIL"));
		sql.append(buildPartnerCondition(userid, lan_id, state, start_time, end_time));
		logger.debug("====云卡激活率详情-分销商：" + sql.toString());
		
		Map<String, Object> map = buildParamMap(userid, null, lan_id,	start_time, end_time);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql.toString(), map);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql.toString(), pageNo, pageSize, map);
		}
	}
	
	@Deprecated
	protected String buildPartnerCondition(String userid, String[] lan_id, String state, String start_time, String end_time) { 
//		StringBuffer sql = new StringBuffer(
//				" where 1=1 and t.to_userid = u.userid and t.deal_time >= to_date(:start_time, 'yyyy-mm-dd hh24:mi:ss') "
//						+ " and t.deal_time <= to_date(:end_time, 'yyyy-mm-dd hh24:mi:ss') ");
		StringBuffer sql = new StringBuffer(
				" where 1=1 and t.to_userid = u.userid and t.deal_time >= "+DBTUtil.to_sql_date(":start_time", 2)
						+ " and t.deal_time <= "+DBTUtil.to_sql_date(":end_time", 2));
		if (ManagerUtils.isFirstPartner()) {
			sql.append(" and (t.to_userid = :userid or t.from_userid = :userid) ");
		} else if (ManagerUtils.isSecondPartner()) {
			sql.append(" and t.to_userid = :userid ");
		} else {
			throw new RuntimeException("未知分销商等级userid：" + userid);
		}		
		if (lan_id != null && lan_id.length > 0) {
			sql.append(" and t.lan_id in (:lan_ids)");
		}
		if (!StringUtil.isEmpty(state)) {
			sql.append(" and t.state = " + state + " ");
		}
		return sql.toString();
	}
	
	
	
	@Deprecated
	@Override
	public Page getCloudActiveOfNetPage(String userid, String[] lan_id, String state, String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport ) {
		String cond = buildCloudActiveNetSql(userid, lan_id, state, start_time, end_time);
		String sql = SF.orderSql("SERVICE_CLOUD_PARTNER_NET_SELECT").replace("#cond", cond);
		logger.debug("====云卡激活率-电信员工：" + sql.toString());
		
		Map<String, Object> map = buildParamMap(userid, null, null,	start_time, end_time);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql.toString(), map);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize, map);
		}
	}
	
	@Deprecated
	@Override
	public Page getCloudActiveOfNetDetail(String userid, String[] lan_id, String state, String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport ) {
		StringBuffer sql = new StringBuffer(SF.orderSql("SERVICE_CLOUD_PARTNER_DETAIL"));
		sql.append(buildCloudActiveNetSql(userid, lan_id, state, start_time, end_time));
		logger.debug("====云卡激活率详情-电信员工：" + sql.toString());
		
		Map<String, Object> map = buildParamMap(userid, null, null,	start_time, end_time);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql.toString(), map);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql.toString(), pageNo, pageSize, map);
		}
	}
	
	@Deprecated
	protected String buildCloudActiveNetSql(String userid, String[] lan_id, String state, String start_time, String end_time) {
		StringBuffer sql = new StringBuffer(" where 1=1 and t.first_userid = u.userid " 
											+ "and t.deal_time >=  "+DBTUtil.to_sql_date(":start_time", 2)
											+ " and t.deal_time <= "+DBTUtil.to_sql_date(":end_time", 2));
		if (ManagerUtils.isProvStaff()) { // 省员工 查询全省
			if (lan_id != null && lan_id.length > 0 && !Consts.LAN_PROV.equals(lan_id[0])) {
			    	sql.append(" and t.lan_id in (" + lan_id[0] + ")");
			}
		} 
		if (ManagerUtils.isLanStaff()) { // 本地网员工 只查看自己
			sql.append(" and t.lan_id in (" + ManagerUtils.getLanId() + ")");
		} 
		if (!StringUtil.isEmpty(userid)) {
			sql.append(" and t.first_userid = :userid");
		}	
		if (!StringUtil.isEmpty(state)) {
			sql.append(" and t.state = " + state + " ");
		}
		return sql.toString();
	}
	
	
	
	/*******************合约计划受理量***************************/

	@Override
	public Page getContractHandlePage(String userid, String[] lan_id, String state, String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport) {
		Map<String, Object> map = buildContract(userid, lan_id, state, start_time, end_time, false);
		String cond = map.get(CONDITION_SQL).toString();
		String sql = SF.orderSql("SERVICE_CONTRACT_HANDLE_COUNT")
																		.replace("#cond", cond)
																		.replace("#column", map.get(QUERY_FIELD).toString())
																		.replace("#groupcol", map.get(GROUP_FIELD).toString());
		logger.debug("====合约计划受理量：" + sql.toString());
		
//		Map<String, Object> param = buildParamMap(userid, null, lan_id, start_time, end_time);
		Map<String, Object> param = (Map<String, Object>) map.get(QUERY_PARAM);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql.toString(), param);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize, param);
		}
	}
	
	
	@Override
	public Page getBuyoutCloudPage(String userid, String[] lan_id,
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport) {
		if (ManagerUtils.isProvStaff()) { // 省员工 查询全省
			if (lan_id != null && lan_id.length > 0
					&& Consts.LAN_PROV.equals(lan_id[0])) {
				lan_id = null;
			}
		}
		if (ManagerUtils.isLanStaff()) { // 本地网员工 只查看自己
			lan_id = new String[] { ManagerUtils.getLanId() };
		}
		if (!ManagerUtils.isNetStaff()) { // 分销商只查看自己报表 一级分销商也只是查看自己的报表？
			userid = ManagerUtils.getUserId();
		}
//		String sql = buildBuyOutCardSql(userid, lan_id, start_time, end_time);
		String sql = buildBuyoutCardSql002(userid, lan_id, start_time, end_time);
		logger.debug("====云卡买断率：" + sql);
		
		Map<String, Object> map = buildParamMap(userid, null, lan_id,
				start_time, end_time);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql, map);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize, map);
		}
	}

	@Override
	public Page getBuyoutCloudDetail(String userid, String[] lan_id, 
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport) {
//		String sql = "select t.cloud_id, t.crm_order_id, t.offer_name, t.taochan_name, "
//				+ " t.phone_num, t.uim, t.batch_id, t.batch_oper_id, t.order_id, t.state,deal_time ,t.pay_money,"
//				+ " t.create_date, t.to_userid, t.from_userid, t.lan_name, u.realname from es_cloud_info t, es_adminuser u " 
//			    + " where t.from_userid = u.userid " 
//			    + " and t.from_userid = :userid "
//				+ " and t.lan_id in (:lan_ids) "
//				+ " and t.state_date >= to_date(:start_time, 'yyyy-mm-dd hh24:mi:ss') "
//				+ " and t.state_date <= to_date(:end_time, 'yyyy-mm-dd hh24:mi:ss')";
//		String sql = buildBuyoutCardDetail(userid, lan_id, start_time, end_time);
//		String sql = buildBuyoutCardDetail002(userid, lan_id, start_time, end_time);
		String sql = buildBuyoutCardDetail003(userid, lan_id, start_time, end_time);
		logger.debug("====云卡买断率 详情：" + sql);
		
		Map<String, Object> map = buildParamMap(userid,null, lan_id,
				start_time, end_time);
		if (isExport) {
			List<Map<String, Object>> list = baseDaoSupport.queryForListByMap(sql, map);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize, map);
		}
	}

	@Override
	public Page getContractSaleroomPage(String userid, String[] lan_id,
			String start_time, String end_time, int pageNo, int pageSize,
			boolean isExport) {
		Map<String, Object> map = buildContract(userid, lan_id, Consts.CONTRACT_STATE_SUCC, start_time, end_time, false);
		String cond = map.get(CONDITION_SQL).toString();
		String sql = SF.orderSql("SERVICE_CONTRACT_SALER_ROOM_SELECT")
																				.replace("#cond", cond)
																				.replace("#column", map.get(QUERY_FIELD).toString())
																				.replace("#groupcol", map.get(GROUP_FIELD).toString());
																				
		logger.debug("====合约计划销售额：" + sql.toString());
		
//		Map<String, Object> param = buildParamMap(userid, null, lan_id,
//				start_time, end_time);
		Map<String, Object> param = (Map<String, Object>) map.get(QUERY_PARAM);
		if (isExport) {
			List<Map> list = baseDaoSupport.queryForListByMap(sql, param);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize, param);
		}
	}

	@Override
	public Page getContractDetail(String userid, String[] lan_id, String state,
			String start_time, String end_time, int pageNo, int pageSize, 
			boolean isExport) {
		Map<String, Object> map = buildContract(userid, lan_id, state, start_time, end_time, true);
		
		String sql = SF.orderSql("SERVICE_CONTRACT_DETAIL_SELECT");
		sql += map.get(CONDITION_SQL);
		
		logger.debug("====合约计划处理额/销售额  contract表 detail：" + sql);
		
//		Map<String, Object> param = buildParamMap(userid, null, lan_id,
//				start_time, end_time);
		Map<String, Object> param = (Map<String, Object>) map.get(QUERY_PARAM);
		if (isExport) {
			List<Cloud> list = baseDaoSupport.queryForListByMap(sql,
					Contract.class, param);
			return new Page(0, list.size(), list.size(), list); // 导出excel
		} else {
			return baseDaoSupport.queryForPageByMap(sql, pageNo, pageSize,
					Contract.class, param);
		}
	}

	@Deprecated
	protected String buildBuyOutCardSql(String userid, String[] lan_id,
			String start_time, String end_time) {
		String founder = Consts.CURR_FOUNDER_3;
		if (ManagerUtils.isSecondPartner()) {
			founder = Consts.CURR_FOUNDER_2;
		}
		StringBuffer cond = new StringBuffer();
		String sql = SF.orderSql("SERVICE_BUY_CARD_SELECT").replace("#value", founder);

		if (!StringUtil.isEmpty(userid)) {
			cond.append(" and o.userid = :userid  ");
		}
		if (null != lan_id && lan_id.length > 0) {
			cond.append(" and o.lan_id in (:lan_ids) ");
		}
		sql = sql.replace("#cond", cond);
		return sql.toString();
	}
	
	protected String buildBuyoutCardSql002(String userid, String[] lan_id, 
			String start_time, String end_time) {
		String loginUserId = ManagerUtils.getUserId();
		String sql = SF.orderSql("SERVICE_BUY_CARD_SELECT_002");
		StringBuffer cond = new StringBuffer();
		if (ManagerUtils.isNetStaff()) {
			cond.append(" and o.source_from = '" + OrderStatus.SOURCE_FROM_AGENT_ONE + "'");
			cond.append(" and u.founder = " + Consts.CURR_FOUNDER_3);
		} else {
			if (ManagerUtils.isFirstPartner()) {
				cond.append("and (");
				cond.append("(o.source_from = '" + OrderStatus.SOURCE_FROM_TAOBAO + "' and o.userid = '" + loginUserId + "') ");
				cond.append(" or ");
				cond.append("(o.source_from = '" + OrderStatus.SOURCE_FROM_AGENT_TOW + "' and exists (select 1 from es_adminuser uu where uu.userid = o.userid "
						+ "and uu.paruserid = '" + loginUserId + "' ))");
				cond.append(")");
			} else if (ManagerUtils.isSecondPartner()) {
				cond.append(" and o.source_from = '" + OrderStatus.SOURCE_FROM_TAOBAO + "' and o.userid = '" + loginUserId + "' ");
				cond.append(" and u.founder = " + Consts.CURR_FOUNDER_2);
			} else {
				throw new RuntimeException("未知账号类型====userid=====" + loginUserId);
			}
		}
		
		if (ManagerUtils.isNetStaff() && !StringUtil.isEmpty(userid)) {
			cond.append(" and o.userid = :userid  ");
		}
		if (null != lan_id && lan_id.length > 0) {
			cond.append(" and o.lan_id in (:lan_ids) ");
		}
		sql = sql.replace("#cond", cond);
		return sql;
	}
	
	// 参数暂时没有用到
	@Deprecated
	protected String buildBuyoutCardDetail(String userid, String[] lan_id,
			String start_time, String end_time) {
		String loginUserId = ManagerUtils.getUserId();
		String sql = SF.orderSql("SERVICE_BUY_CARD_DETAIL_SELECT");
		StringBuffer sb = new StringBuffer();
		
		if (ManagerUtils.isNetStaff()) {
			 sb.append(" and u.userid = t.first_userid and t.ship_state is not null and t.ship_state <> " + Consts.SHIP_STATE_1);
			 sb.append(" and t.first_userid = :userid");
		} else {
			sb.append(" and t.to_userid = u.userid ");
			sb.append(" and (");
				sb.append(" (t.to_userid = u.userid and t.sec_order_id is not null and t.state = " + Consts.CLOUD_INFO_STATE_2);
				sb.append(" and t.to_userid = " +  loginUserId + ")");
			if (ManagerUtils.isFirstPartner()) {
				sb.append(" or ");
				sb.append(" (t.from_userid = " + loginUserId + " and exists (" +
						  " select 1 from es_adminuser uu where uu.userid = t.to_userid and uu.paruserid = '" + loginUserId + "')");
				sb.append(")");
			} else if (ManagerUtils.isSecondPartner()) {
				// do nothing | don't remove , it can validate the loginUser belonging to which partner.
			} else {
				throw new RuntimeException("未知账号类型====userid=====" + loginUserId);
			}
			sb.append(")");
		}
		sql = sql.replace("#cond", sb);
		return sql;
	}
	
	@Deprecated
	protected String buildBuyoutCardDetail002(String userid, String[] lan_id,
			String start_time, String end_time) {
		String loginUserId = ManagerUtils.getUserId();
		String selectHeader = "select t.cloud_id, t.crm_order_id, t.offer_name, t.taochan_name, "
				+ " t.phone_num, t.uim, t.batch_id, t.batch_oper_id, t.order_id, t.state,deal_time ,t.pay_money,"
				+ " t.create_date, t.to_userid, t.from_userid, t.lan_name ";
		StringBuffer sb = new StringBuffer("select * from (" +selectHeader); 
		if (ManagerUtils.isNetStaff()) {
			sb.append(" from es_cloud_info t , es_order o where 1=1");
			sb.append(" and t.first_orderid = o.order_id "
					+ " and t.first_userid = :userid "
					+ " and o.lan_id in (:lan_ids) "
					+ " and o.source_from = '" + OrderStatus.SOURCE_FROM_AGENT_ONE + "' "
					+ " and o.pay_status = '" + OrderStatus.PAY_YES + "' "
					+ " and o.type_code = '" + OrderBuilder.CLOUD_KEY + "' "
					+ " and o.pay_time > = "+DBTUtil.to_sql_date(":start_time", 2)
					+ " and o.pay_time < = "+DBTUtil.to_sql_date(":end_time", 2));
		} else {
			sb.append(" from es_cloud_info t where t.to_userid = :userid " 
					+ " and t.state = " + Consts.CLOUD_INFO_STATE_2 + " and t.sec_order_id is not null"
					+ " and t.lan_id in (:lan_ids) "
					+ " and t.state_date > = "+DBTUtil.to_sql_date(":start_time", 2)
					+ " and t.state_date < = "+DBTUtil.to_sql_date(":end_time", 2));
			if (ManagerUtils.isFirstPartner()) {
				sb.append(" union all " + selectHeader );
				sb.append(" from es_cloud_info t , es_order o where 1=1");
				sb.append(" and t.order_id = o.order_id "
						+ " and t.from_userid = :userid and t.to_userid <> :userid "
						+ " and o.lan_id in (:lan_ids) "
						+ " and o.source_from = '" + OrderStatus.SOURCE_FROM_AGENT_TOW + "' "
						+ " and o.pay_status = '" + OrderStatus.PAY_YES + "' "
						+ " and o.type_code = '" + OrderBuilder.CLOUD_KEY + "' "
						+ " and o.pay_time > = "+DBTUtil.to_sql_date(":start_time", 2)
						+ " and o.pay_time < = "+DBTUtil.to_sql_date(":end_time", 2));
			} else if (ManagerUtils.isSecondPartner()) {
				// do nothing | don't remove , it can validate the loginUser belonging to which partner.
			} else {
				throw new RuntimeException("当前工号异常userid====" + loginUserId);
			}
		}
		sb.append(" ) tt order by tt.create_date desc");
		return sb.toString();
	}

	
	protected String buildBuyoutCardDetail003(String userid, String[] lan_id,
			String start_time, String end_time) {
		String loginUserId = ManagerUtils.getUserId();
		String selectHeader = "select t.cloud_id, t.crm_order_id, t.offer_name, t.taochan_name, "
				+ " t.phone_num, t.uim, t.batch_id, t.batch_oper_id, t.order_id, t.state, deal_time ,t.pay_money,"
				+ " t.create_date, t.to_userid, t.from_userid, t.lan_name, "+DBTUtil.decode("t.state", "0", "'可用'", "1", "'预占'", "2", "'已用'" , "3", "'失效'","''")+" state_name";
		//"+DBTUtil.decode("t.state", "0", "'可用'", "1", "'预占'", "2", "'已用'" , "3", "'失效'","''")+" decode(t.state, 0, '可用', 1, '预占', 2, '已用' , 3, '失效','')
		StringBuffer sb = new StringBuffer("select * from (" +selectHeader); 
		if (ManagerUtils.isNetStaff()) {
			sb.append(" from es_cloud_info t , es_order o where 1=1");
			sb.append(" and t.first_orderid = o.order_id "
					+ " and t.first_userid = :userid "
					+ " and o.lan_id in (:lan_ids) "
					+ " and o.source_from = '" + OrderStatus.SOURCE_FROM_AGENT_ONE + "' "
					+ " and o.pay_status = '" + OrderStatus.PAY_YES + "' "
					+ " and o.type_code = '" + OrderBuilder.CLOUD_KEY + "' "
					+ " and o.pay_time > = "+DBTUtil.to_sql_date(":start_time", 2)
					+ " and o.pay_time < = "+DBTUtil.to_sql_date(":end_time", 2));
		} else {
			sb.append(" from es_cloud_info t, es_order o where 1=1  " 
					+ " and t.sec_order_id = o.order_id " 
					+ " and o.lan_id in (:lan_ids) "
					+ " and o.source_from = '" + OrderStatus.SOURCE_FROM_TAOBAO + "' "
					+ " and o.pay_status = '" + OrderStatus.PAY_YES + "' "
					+ " and o.type_code = '" + OrderBuilder.CLOUD_KEY + "' "
					+ " and o.userid = :userid "
					+ " and o.pay_time > = "+DBTUtil.to_sql_date(":start_time", 2)
					+ " and o.pay_time < = "+DBTUtil.to_sql_date(":end_time", 2));
			if (ManagerUtils.isFirstPartner()) {
				sb.append(" union all " + selectHeader );
				sb.append(" from es_cloud_info t , es_order o where 1=1");
				sb.append(" and t.order_id = o.order_id "
						+ " and o.lan_id in (:lan_ids) "
						+ " and o.source_from = '" + OrderStatus.SOURCE_FROM_AGENT_TOW + "' "
						+ " and o.pay_status = '" + OrderStatus.PAY_YES + "' "
						+ " and o.type_code = '" + OrderBuilder.CLOUD_KEY + "' "
						+ " and o.userid = :userid "
						+ " and o.pay_time > = "+DBTUtil.to_sql_date(":start_time", 2)
						+ " and o.pay_time < = "+DBTUtil.to_sql_date(":end_time", 2)
						+ " and exists (select 1 from es_adminuser uu where uu.userid = o.userid and uu.paruserid ='" + loginUserId + "')");
			} else if (ManagerUtils.isSecondPartner()) {
				// do nothing | don't remove , it can validate the loginUser belonging to which partner.
			} else {
				throw new RuntimeException("当前工号异常userid====" + loginUserId);
			}
		}
		sb.append(" ) tt order by tt.create_date desc");
		return sb.toString();
	}
	protected String buildContractSaleroomSql(String userid, String[] lan_id, 
			String start_time, String end_time) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select c.lan_name, c.userid, c.username, c.lan_id, sum(c.crm_fee) sum_crm_fee, sum(c.sec_fee) sum_sec_fee, u.realname "
				+ " from es_contract_accept c, es_adminuser u"
				+ " where c.state = '1' and c.userid = u.userid "
				+ " and c.status_date>= "+DBTUtil.to_sql_date(":start_time", 2)
				+ " and c.status_date <= "+DBTUtil.to_sql_date(":end_time", 2));

		if (!StringUtil.isEmpty(userid)) {
			sb.append(" and c.userid = :userid  ");
		}
		if (null != lan_id && lan_id.length > 0) {
			sb.append(" and c.lan_id in (:lan_ids) ");
		}

		sb.append("group by c.userid, c.lan_name, c.lan_id, c.username, u.realname");
		return sb.toString();
	}
	
	protected Map<String, Object> buildContract(String userid, String[] lan_id, String state,
			String start_time, String end_time, boolean isDetail) {
		
		String first_userid = null;
		String queryField =  null;
		String groupField = null;
		String joinUserField = null;
		
		if (ManagerUtils.isNetStaff()) {
			//查询sql 拼接字段处理
			groupField = " c.first_userid ";
			queryField = groupField + " as userid ";
			joinUserField = groupField;
			
			//查询参数处理
			first_userid = userid;
			userid = null; //problem? is ok
			if (ManagerUtils.isProvStaff()) { // 省员工 查询全省
				if (lan_id != null && lan_id.length > 0
						&& Consts.LAN_PROV.equals(lan_id[0])) {
					lan_id = null;
				}
			}
			if (ManagerUtils.isLanStaff()) { // 本地网员工 只查看自己
				lan_id = new String[] { ManagerUtils.getLanId() };
			}
			
		} else {        // 分销商
			groupField = " c.userid ";
			queryField = groupField;
			joinUserField = groupField;
			
			if (ManagerUtils.isFirstPartner()) {
				first_userid = ManagerUtils.getUserId();
				if (!isDetail) {
					userid = null;
				}
			}
			else if (ManagerUtils.isSecondPartner()) 
			  userid = ManagerUtils.getUserId();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("	where 1=1 and " + joinUserField + " = u.userid ");
		if (!StringUtil.isEmpty(start_time)) {
			sb.append(" and status_date>= "+DBTUtil.to_sql_date(":start_time", 2));
		}
		if (!StringUtil.isEmpty(end_time)) {
			sb.append("	and status_date<= "+DBTUtil.to_sql_date(":end_time", 2));
		}
		if (!StringUtil.isEmpty(userid)) {
			sb.append(" and c.userid = :userid  ");
		}
		if (!StringUtil.isEmpty(first_userid)) { //一级代理商 
			sb.append(" and c.first_userid = '" + first_userid + "' ");
		}
		if (null != lan_id && lan_id.length > 0) {
			sb.append(" and c.lan_id in (:lan_ids) ");
		}
		if (!StringUtil.isEmpty(state)) {
			sb.append(" and c.state = " + state + " ");
		}
		map.put(CONDITION_SQL, sb.toString());
		map.put(QUERY_FIELD, queryField);
		map.put(GROUP_FIELD, groupField);
		map.put(QUERY_PARAM, buildParamMap(userid, null, lan_id, start_time, end_time)); 
		return map;
	}
	
	protected Map<String, Object> buildActiveCloud(String userid, String[] lan_id, String cloudState,
			String start_time, String end_time, boolean isDetail) {
		
		String first_userid = null;
		String queryField =  null;
		String groupField = null;
		String joinUserField = null;
		String joinOrderField = null;
		
		if (ManagerUtils.isNetStaff()) {
			//查询sql 拼接字段处理
			groupField = " o.userid ";
			queryField = groupField ;
			joinUserField = groupField;
			joinOrderField = " t.first_orderid ";
			//查询参数处理
			if (ManagerUtils.isProvStaff()) { // 省员工 查询全省
				if (lan_id != null && lan_id.length > 0
						&& Consts.LAN_PROV.equals(lan_id[0])) {
					lan_id = null;
				}
			}
			if (ManagerUtils.isLanStaff()) { // 本地网员工 只查看自己
				lan_id = new String[] { ManagerUtils.getLanId() };
			}
			
		} else {        // 分销商
			if (ManagerUtils.isFirstPartner()) {
				groupField = " t.to_userid ";
				queryField = groupField + "as userid " ;
				joinUserField = groupField;
				joinOrderField = " t.order_id ";
				first_userid = ManagerUtils.getUserId();
				if (!isDetail) {
					userid = null;
				}
			}
			else if (ManagerUtils.isSecondPartner()) {
				groupField = " o.userid ";
				queryField = groupField ;
				joinUserField = groupField;
				joinOrderField = " t.order_id ";
				userid = ManagerUtils.getUserId();
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		StringBuffer sb = new StringBuffer();
		sb.append("	where 1=1 and " + joinUserField + " = u.userid and o.order_id = " + joinOrderField + 
				" and o.accept_status = " + OrderStatus.ACCEPT_STATUS_3);
		if (!StringUtil.isEmpty(start_time)) {
			sb.append(" and o.accept_time >= "+DBTUtil.to_sql_date(":start_time", 2));
		}
		if (!StringUtil.isEmpty(end_time)) {
			sb.append("	and o.accept_time <= "+DBTUtil.to_sql_date(":end_time", 2));
		}
		if (!StringUtil.isEmpty(cloudState)) {
			sb.append(" and t.state = " + cloudState);
		}
		if (!StringUtil.isEmpty(userid)) {
			sb.append(" and o.userid = :userid  ");
		}
		if (!StringUtil.isEmpty(first_userid)) { //一级代理商 
			sb.append(" and t.first_userid = '" + first_userid + "' ");
		}
		if (null != lan_id && lan_id.length > 0) {
			sb.append(" and o.lan_id in (:lan_ids) ");
		}
		map.put(CONDITION_SQL, sb.toString());
		map.put(QUERY_FIELD, queryField);
		map.put(GROUP_FIELD, groupField);
		map.put(QUERY_PARAM, buildParamMap(userid, null, lan_id, start_time, end_time)); 
		return map;
	}
	
	
	protected Map<String, Object> buildParamMap(String userid, String username,
			String[] lan_id, String start_time, String end_time) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("username", username);
		map.put("lan_ids",
				Arrays.asList(lan_id == null ? new String[] {} : lan_id));
		map.put("start_time", start_time + " 00:00:00");
		map.put("end_time", end_time + " 23:59:59");
		return map;
	}
	
	

}
