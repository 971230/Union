package com.ztesoft.net.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListByWorkQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListByWorkQueryResp;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.sqls.SqlUtil;
import com.ztesoft.net.util.ZjCommonUtils;

import consts.ConstsCore;
import net.sf.json.JSONObject;
import params.order.req.OrderHandleLogsReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderWorksBusiRequest;

@SuppressWarnings("rawtypes")
public class OrdWorkManager extends BaseSupport implements IOrdWorkManager {

	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;

	@Resource
	private ICacheUtil cacheUtil;

	/**
	 * 工单相关订单列表查询接口
	 * 
	 * @author song.qi
	 * @param requ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrderListByWorkQueryResp queryOrderListByWork(OrderListByWorkQueryReq requ) {
		OrderListByWorkQueryResp resp = new OrderListByWorkQueryResp();
		String status = requ.getStatus();
		String operator_num = requ.getOperator_num();
		String start_time = requ.getStart_time();
		String end_time = requ.getEnd_time();// yyyymmdd
		if (StringUtils.isEmpty(status) || StringUtils.isEmpty(operator_num) || StringUtils.isEmpty(start_time)
				|| StringUtils.isEmpty(end_time) || end_time.length() != 8 || start_time.length() != 8) {
			resp.setResp_code("1");// 1失败、0成功
			resp.setResp_msg("查询失败：必填参数为空或格式异常");// 失败原因
			return resp;
		}
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlFrom = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		// StringBuffer sqlOrderBy = new StringBuffer();
		sqlSelect.append(
				" select w.order_id,w.order_contact_name, w.order_contact_phone,w.order_contact_addr,w.order_product_name,w.order_amount,(select a.pname from es_dc_public_ext a where a.stype='WORK_STATUS' and a.pkey=w.status) status ");
		sqlFrom.append(" from es_work_order w ");
		sqlWhere.append(" where w.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
		// 处理状态 0—未处理 1—处理成功 2—处理失败
		// 0–全部1–未处理2–已处理
		if ("1".equals(status)) {
			sqlWhere.append(" and w.status = '0'");
		} else if ("2".equals(status)) {
			sqlWhere.append(" and w.status <> '0'");
		}
		sqlWhere.append(" and w.operator_num = '" + operator_num + "'");
		sqlWhere.append(" and w.create_time <= to_date('" + end_time + "235959','yyyymmddHH24miss') ");// 当天
		sqlWhere.append(" and w.create_time >= to_date('" + start_time + "000000','yyyymmddHH24miss') ");
		sql.append(sqlSelect).append(sqlFrom).append(sqlWhere);
		sql.append(
				"group by w.order_id, w.order_contact_name, w.order_contact_phone, w.order_contact_addr, w.order_product_name, w.order_amount, w.status having count(w.order_id) > 0");
		logger.info("工单相关订单列表查询接口:" + sql);
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql.toString());
		if (null != list && list.size() > 0) {
			for (Map<String, Object> map : list) {
				String order_amount = map.get("order_amount") + "";
				Double amount = Double.parseDouble(order_amount) * 1000;
				map.put("order_amount", amount.intValue());
			}
		}
		resp.setResp_code("0");// 1失败、0成功
		resp.setResp_msg("查询成功");// 失败原因
		resp.setWork_order_list(list);
		return resp;
	}

	/**
	 * 根据订单id查询工单列表
	 * 
	 * @param requ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderWorksBusiRequest> queryWorkListByOrderId(String order_id) {
		String sql = "select * from es_work_order where order_id='" + order_id + "' and source_from = '"
				+ ManagerUtils.getSourceFrom() + "' ";
		Page page = this.baseDaoSupport.queryForPage(sql, 1, 999, OrderWorksBusiRequest.class);
		return page.getResult();
	}

	/**
	 * 审核校验工单派送情况 未实名对应实名单，未收费对应收费单
	 * 
	 * @param order_id
	 * @return
	 */
	@Override
	public String orderCheck(String order_id) {
		// 二期
		String catId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
		// String GOODS_TYPE =
		// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
		// AttrConsts.GOODS_TYPE);
		String gyrh_cat_id = cacheUtil.getConfigInfo("gyrh_cat_id");// 固移融合单商品小类
		String flag = "";
		List<OrderWorksBusiRequest> list = queryWorkListByOrderId(order_id);
		if (gyrh_cat_id.contains(catId)) {
			flag = "固移融合单";
			if (null != list && list.size() > 0) {
				for (OrderWorksBusiRequest work : list) {
					if ("06".equals(work.getType()) && "0".equals(work.getStatus())) {
						flag = "";
						break;
					}
				}
			}
			return flag;
		}
		// 二期end
		// 是否收费,0否;1是
		String is_pay = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_PAY);
		// 是否实名0未实名1已实名
		String is_real_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_REAL_NAME);
		if ("1".equals(is_pay) && "1".equals(is_real_name)) {
			return "";
		}
		if (null != list && list.size() > 0) {
			if ("0".equals(is_pay)) {
				Boolean i = false;
				// 01 – 收费单；02 - 外勘单；03 – 实名单；04 – 挽留单；05 – 写卡单
				for (OrderWorksBusiRequest work : list) {
					// 工单处理状态0 — 未处理1 — 处理成功2 — 处理失败
					if ("01".equals(work.getType()) && "0".equals(work.getStatus())) {
						i = true;
						break;
					}
				}
				if (!i) {
					flag = flag + " 收费单 ";
				}
			}
			if ("0".equals(is_real_name)) {
				Boolean j = false;
				for (OrderWorksBusiRequest work : list) {
					if ("03".equals(work.getType()) && "0".equals(work.getStatus())) {
						j = true;
						break;
					}
				}
				if (!j) {
					flag = flag + " 实名单 ";
				}
			}
		} else {
			if ("0".equals(is_pay)) {
				flag = flag + " 收费单 ";
			}
			if ("0".equals(is_real_name)) {
				flag = flag + " 实名单 ";
			}
		}
		return flag;
	}

	/**
	 * 根据工单id查询工单
	 * 
	 * @param requ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrderWorksBusiRequest queryWorkByWorkOrderId(String work_order_id) {
		String sql = "select * from es_work_order where work_order_id='" + work_order_id + "' and source_from = '"
				+ ManagerUtils.getSourceFrom() + "' ";
		Page page = this.baseDaoSupport.queryForPage(sql, 1, 999, OrderWorksBusiRequest.class);
		if (page.getResult() != null && page.getResult().size() == 1) {
			return (OrderWorksBusiRequest) page.getResult().get(0);
		} else {
			return null;
		}
	}

	/**
	 * 页面工单列表查询
	 * 
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page queryWorkListByOrder_id(OrderQueryParams params, int pageNo, int pageSize) {
		String sql = "SELECT * FROM (SELECT ROWNUM rn, order_from,order_id, order_time,M,N FROM ( ";
		String sqlSum = "select a.order_from,a.order_id,to_char(a.order_time,'yyyy-MM-dd HH24:mi:ss') as order_time,count(a.m) as M,count(a.n) as N from ( select (select distinct pname from es_dc_public_ext where stype='source_from' and pkey=t.order_from) as order_from,t.work_order_id, t.order_id, t.order_time, case when t.status in ('1','2','3') then 1 end as m, "
				+ "case when (t.status not in ('1','2','3') or t.status is null) then 0 end as n from es_work_order t where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "'";
		if (!StringUtils.isEmpty(params.getOrder_id())) {
			sqlSum += " and t.order_id='" + params.getOrder_id() + "' ";
		}
		if (!StringUtils.isEmpty(params.getOrder_from())) {
			sqlSum += " and t.order_from='" + params.getOrder_from() + "' ";
		}
		if (!StringUtils.isEmpty(params.getCreate_start())) {
			sqlSum += " and t.order_time > to_date('" + params.getCreate_start() + "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (!StringUtils.isEmpty(params.getCreate_end())) {
			sqlSum += " and t.order_time < to_date('" + params.getCreate_end() + "','yyyy-MM-dd HH24:mi:ss')";
		}
		sqlSum += " group by t.order_from,t.work_order_id,t.order_id,t.order_time,t.status ) a group by a.order_from,a.order_id,a.order_time order by a.order_time desc";
		String totalCountSql = "select count(1) from ( " + sqlSum + " ) aa";
		int totalCount = this.baseDaoSupport.queryForInt(totalCountSql);
		sql += sqlSum;
		if (pageNo > 0 && pageSize > 0) {
			sql += ") WHERE ROWNUM <= " + pageNo * pageSize + ") t2 WHERE T2.rn >= "
					+ (pageNo * pageSize - pageSize + 1);
		} else {// 异常
			sql += ") WHERE ROWNUM <= 20) t2 WHERE T2.rn >= 1";
		}
		// 总数
		List<Map<String, String>> list = this.baseDaoSupport.queryForList(sql);
		Page webpage = null;
		if (totalCount > 0 && null != list && list.size() > 0) {
			webpage = new Page();
			webpage.setData(list);
			webpage.setTotalCount(totalCount);
			webpage.setPageSize(pageSize);
			webpage.setStart(pageNo);
		}
		// Page webpage = this.baseDaoSupport.queryForPage(sql.toString(),
		// pageNo, pageSize, new RowMapper() {
		// public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("order_id", rs.getString("order_id"));
		// map.put("order_time", rs.getString("order_time"));
		// map.put("M", rs.getString("M"));// N条未处理工单，M条已处理工单
		// map.put("N", rs.getString("N"));
		// return map;
		// }
		// });
		return webpage;
	}

	/**
	 * 单个订单工单列表
	 * 
	 * @param order_id
	 * @return
	 */
	public Page getWorkListByOrderId(String order_id, int pageNo, int pageSize) {
		String sql = "select  (select distinct pname from es_dc_public_ext  where stype='WORK_TYPE' and pkey=t.type) as type,t.work_order_id,(select distinct pname from es_dc_public_ext where stype='WORK_STATUS' and pkey=t.status) as status,to_char(t.create_time,'yyyy-MM-dd HH24:mi:ss') as create_time,case when t.update_time is null then '' else to_char(t.update_time, 'yyyy-MM-dd HH24:mi:ss') end as update_time,t.order_send_username,t.operator_name,t.remark,t.result_remark,t.req_xml,(select distinct pname from es_dc_public_ext where stype='source_from' and pkey=t.order_from) as order_from from es_work_order t where t.order_id='"
				+ order_id + "' and t.source_from = '" + ManagerUtils.getSourceFrom()
				+ "' order by decode(t.status,'2',0),decode(t.status,'0',0),t.create_time desc";
		Page webpage = this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", rs.getString("type"));
				map.put("work_order_id", rs.getString("work_order_id"));
				map.put("status", rs.getString("status"));
				map.put("create_time", rs.getString("create_time"));
				map.put("update_time", rs.getString("update_time"));
				map.put("operator_name", rs.getString("operator_name"));
				map.put("order_send_username", rs.getString("order_send_username"));
				map.put("remark", rs.getString("remark"));
				map.put("result_remark", rs.getString("result_remark"));
				map.put("order_from", rs.getString("order_from"));
				if (!StringUtils.isEmpty(rs.getString("req_xml"))) {
					JSONObject json = JSONObject.fromObject(rs.getString("req_xml"));
					map.put("detail", getDetail(json, rs.getString("type")));
				} else {
					map.put("detail", "");
				}
				return map;
			}
		});
		return webpage;
	}

	/**
	 * 工单类型不同返回不同的详情
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	private String getDetail(JSONObject json, String type) {
		if (json.isEmpty() || StringUtils.isEmpty(type)) {
			return null;
		}
		String detail = "";
		// Map map = new HashMap();
		JSONObject developer_info = json.getJSONObject("developer_info");// 操作员节点信息
		JSONObject cust_info = json.getJSONObject("cust_info");// 客户信息节点
		// JSONObject developer = new JSONObject();
		// if (!developer_info.isEmpty()) {
		// Iterator iterator = developer_info.keys();
		// while (iterator.hasNext()) {
		// String key = (String) iterator.next();
		// if (!StringUtils.isEmpty(developer_info.getString(key))) {
		// developer.put(key, developer_info.getString(key));
		// }
		// }
		// }
		// JSONObject cust = new JSONObject();
		// if (!cust_info.isEmpty()) {
		// Iterator iterator = cust_info.keys();
		// while (iterator.hasNext()) {
		// String key = (String) iterator.next();
		// if (!StringUtils.isEmpty(cust_info.getString(key))) {
		// cust.put(key, cust_info.getString(key));
		// }
		// }
		// }
		// 01–收费单02-外勘单03–实名单04–挽留单05–写卡单
		if ("01".equals(type)) {
			if (!StringUtils.isEmpty(json.getString("pay_result"))) {
				if ("0".equals(json.getString("pay_result"))) {
					// map.put("支付结果", "支付成功");
					detail = detail + "支付结果:支付成功" + "&#10;";
				} else if ("-1".equals(json.getString("pay_result"))) {
					// map.put("支付结果", "支付失败");
					detail = detail + "支付结果:支付失败" + "&#10;";
				} else {
					// map.put("支付结果", json.getString("pay_result"));
					detail = detail + "支付结果:" + json.getString("pay_result") + "&#10;";
				}
			}
			if (!StringUtils.isEmpty(json.getString("pay_sequ"))) {
				// map.put("支付发起流水", json.getString("pay_sequ"));
				detail = detail + "支付发起流水:" + json.getString("pay_sequ") + "&#10;";
			}
			if (!StringUtils.isEmpty(json.getString("pay_back_sequ"))) {
				// map.put("支付返回流水", json.getString("pay_back_sequ"));
				detail = detail + "支付返回流水:" + json.getString("pay_back_sequ") + "&#10;";
			}
			if (!StringUtils.isEmpty(json.getString("pay_type"))) {
				// map.put("支付类型", json.getString("pay_type"));
				detail = detail + "支付类型:" + json.getString("pay_type") + "&#10;";
			}
			if (!StringUtils.isEmpty(json.getString("pay_method"))) {
				// map.put("支付方式", json.getString("pay_method"));
				detail = detail + "支付方式:" + json.getString("pay_method") + "&#10;";
			}
		} else if ("03".equals(type)) {
			if (!cust_info.isEmpty()) {
				if (!StringUtils.isEmpty(cust_info.getString("is_real_name"))) {// 0未实名；1已实名。
					if ("0".equals(cust_info.getString("is_real_name"))) {
						detail = detail + "是否实名校验:未实名&#10;";
					} else if ("1".equals(cust_info.getString("is_real_name"))) {
						detail = detail + "是否实名校验:已实名&#10;";
					} else {
						detail = detail + "是否实名校验:" + cust_info.getString("is_real_name") + "&#10;";
					}
				}
				if (!StringUtils.isEmpty(cust_info.getString("customer_name"))) {
					detail = detail + "客户姓名:" + cust_info.getString("customer_name") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cert_type"))) {
					detail = detail + "证件类型:" + cust_info.getString("cert_type") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cert_num"))) {
					detail = detail + "证件号码:" + cust_info.getString("cert_num") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cert_addr"))) {
					detail = detail + "证件地址:" + cust_info.getString("cert_addr") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cert_nation"))) {
					detail = detail + "名族:" + cust_info.getString("cert_nation") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cert_sex"))) {
					detail = detail + "性别:" + cust_info.getString("cert_sex") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cust_birthday"))) {
					detail = detail + "客户生日:" + cust_info.getString("cust_birthday") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cert_issuedat"))) {
					detail = detail + "签发机关 :" + cust_info.getString("cert_issuedat") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cert_expire_date"))) {
					detail = detail + "证件失效时间 :" + cust_info.getString("cert_expire_date") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cert_effected_date"))) {
					detail = detail + "证件生效时间 :" + cust_info.getString("cert_effected_date") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cust_tel"))) {
					detail = detail + "客户电话 :" + cust_info.getString("cust_tel") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("customer_type"))) {
					detail = detail + "客户类型 :" + cust_info.getString("customer_type") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("cust_id"))) {
					detail = detail + "客户编号 :" + cust_info.getString("cust_id") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("group_code"))) {
					detail = detail + "收入归集集团 :" + cust_info.getString("group_code") + "&#10;";
				}
				if (!StringUtils.isEmpty(cust_info.getString("req_swift_num"))) {
					detail = detail + "拍照流水 :" + cust_info.getString("req_swift_num") + "&#10;";
				}
			}
		} else {// 暂无
			// if (!StringUtils.isEmpty(json.getString("field_survey_result")))
			// {// 0失败；1成功
			// if ("0".equals(json.getString("field_survey_result"))) {
			// detail = detail + "工单处理结果:失败&#10;";
			// } else if ("1".equals(json.getString("field_survey_result"))) {
			// detail = detail + "工单处理结果: 成功&#10;";
			// } else {
			// detail = detail + "工单处理结果: " +
			// json.getString("field_survey_result") + "&#10;";
			// }
			// }
		}
		if (!developer_info.isEmpty()) {// 通用节点
			if (!StringUtils.isEmpty(developer_info.getString("develop_point_code"))) {
				detail = detail + "发展点编码:" + developer_info.getString("develop_point_code") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("develop_point_name"))) {
				detail = detail + "发展点名称:" + developer_info.getString("develop_point_name") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("develop_code"))) {
				detail = detail + "发展人编码:" + developer_info.getString("develop_code") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("develop_name"))) {
				detail = detail + "发展人姓名:" + developer_info.getString("develop_name") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("referee_num"))) {
				detail = detail + "推荐人号码:" + developer_info.getString("referee_num") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("referee_name"))) {
				detail = detail + "推荐人名称:" + developer_info.getString("referee_name") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("county_id"))) {
				detail = detail + "营业县分编码:" + developer_info.getString("county_id") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("deal_office_type"))) {// 01：营业渠道02：行销渠道03：代理渠道
				if ("01".equals(developer_info.getString("deal_office_type"))) {
					detail = detail + "渠道类型1:营业渠道&#10;";
				} else if ("02".equals(developer_info.getString("deal_office_type"))) {
					detail = detail + "渠道类型1:行销渠道&#10;";
				} else if ("03".equals(developer_info.getString("deal_office_type"))) {
					detail = detail + "渠道类型1:代理渠道&#10;";
				} else {
					detail = detail + "渠道类型1:" + developer_info.getString("deal_office_type") + "&#10;";
				}
			}
			if (!StringUtils.isEmpty(developer_info.getString("channelType"))) {
				detail = detail + "渠道分类:" + developer_info.getString("channelType") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("deal_office_id"))) {
				detail = detail + "操作点:" + developer_info.getString("deal_office_id") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("deal_operator"))) {
				detail = detail + "操作员编码:" + developer_info.getString("deal_operator") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("deal_operator_name"))) {
				detail = detail + "操作员姓名:" + developer_info.getString("deal_operator_name") + "&#10;";
			}
			if (!StringUtils.isEmpty(developer_info.getString("deal_operator_num"))) {
				detail = detail + "操作员号码:" + developer_info.getString("deal_operator_num") + "&#10;";
			}
		}
		return detail.replace("\"", "-");
	}

	/**
	 * 
	 */
	public Page queryIntent(OrderQueryParams params, int pageNo, int pageSize) {
		String sql = "select (select distinct field_value_desc from es_dc_public_dict_relation where stype='bss_area_code' and field_value=t.order_city_code)as order_city_code, (select distinct pname from es_dc_public_ext where stype='source_from' and pkey=t.source_system_type)as source_system_type, (select distinct pname from es_dc_public_ext where stype='INTENT_STATUS' and pkey=t.status)as status,(select a.pname from es_dc_public_ext a where a.stype = 'WORK_STATUS' and a.pkey = dd.status) work_status, t.order_id, t.intent_order_id, t.referee_name, t.referee_num, t.goods_name, t.ship_name, t.ship_tel, t.ship_addr, (select distinct other_field_value_desc from es_dc_public_dict_relation where stype ='county' and other_field_value=t.county_id)as order_county_code, t.remark, to_char(t.create_time, 'yyyy-MM-dd HH24:mi:ss') as create_time,t.is_work_custom as is_work_custom,lock_id||lock_name as lock_id ,top_seed_professional_line,top_seed_type,top_seed_group_id from es_order_intent t left join (select row_number() OVER(PARTITION BY b.order_id order by b.create_time desc) as rw, b.* from es_work_order b) dd on dd.rw = 1 and dd.order_id = t.order_id where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "' ";
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(1) from es_order_intent t left join (select row_number() OVER(PARTITION BY b.order_id order by b.create_time desc) as rw, b.* from es_work_order b) dd on dd.rw = 1 and dd.order_id = t.order_id where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "' ") ;
		
		//能否查到线上转线下订单开关
		String ONLINE_ORDER_INTENT_QUERY_FLAG = cacheUtil.getConfigInfo("ONLINE_ORDER_INTENT_QUERY_FLAG");
		if("0".equals(ONLINE_ORDER_INTENT_QUERY_FLAG)){
			//0--不能查询，加上查询限制
			
			sql += " and (t.is_online_order is null or t.is_online_order<>1) ";
			countSql.append(" and (t.is_online_order is null or t.is_online_order<>1) ");
		}
		
		if (!StringUtils.isEmpty(params.getOrder_id())) {
			sql = sql + " and t.order_id='" + params.getOrder_id() + "'";
			countSql = countSql.append(" and t.order_id='" + params.getOrder_id() + "'");
		}
		if (!StringUtils.isEmpty(params.getIntent_order_id())) {
			sql = sql + " and t.intent_order_id='" + params.getIntent_order_id() + "'";
			countSql = countSql.append(" and t.intent_order_id='" + params.getIntent_order_id() + "'");
		}
		if (!StringUtils.isEmpty(params.getStatus())) {
			sql = sql + " and t.status='" + params.getStatus() + "'";
			countSql = countSql.append(" and t.status='" + params.getStatus() + "'");
		}
		if (!StringUtils.isEmpty(params.getShip_tel())) {
			sql = sql + " and t.ship_tel like '%" + params.getShip_tel() + "%'";
			countSql = countSql.append(" and t.ship_tel like '%" + params.getShip_tel() + "%'");
		}
		if (!StringUtils.isEmpty(params.getCreate_start())) {
			sql = sql + " and t.create_time >= to_date('" + params.getCreate_start() + "','yyyy-MM-dd HH24:mi:ss')";
			countSql = countSql.append(" and t.create_time >= to_date('" + params.getCreate_start() + "','yyyy-MM-dd HH24:mi:ss')");
		}
		if (!StringUtils.isEmpty(params.getCreate_end())) {
			sql = sql + " and t.create_time <= to_date('" + params.getCreate_end() + "','yyyy-MM-dd HH24:mi:ss')";
			countSql = countSql.append(" and t.create_time <= to_date('" + params.getCreate_end() + "','yyyy-MM-dd HH24:mi:ss')");
		}
		if (!StringUtils.isEmpty(params.getOrder_city_code())) {
			sql = sql + " and t.order_city_code='" + params.getOrder_city_code() + "'";
			countSql = countSql.append(" and t.order_city_code='" + params.getOrder_city_code() + "'");
		}
		if (!StringUtils.isEmpty(params.getOrder_county_code())) {
			sql = sql + " and t.order_county_code='" + params.getOrder_county_code() + "'";
			countSql = countSql.append(" and t.order_county_code='" + params.getOrder_county_code() + "'");
		}
		if (!StringUtils.isEmpty(params.getPhone_num())) {
			sql = sql + " and t.acc_nbr='" + params.getPhone_num() + "'";
			countSql = countSql.append(" and t.acc_nbr='" + params.getPhone_num() + "'");
		}
		if (!"1".equals(ManagerUtils.getAdminUser().getUserid())) {
			sql = sql + " and t.lock_id = '" + ManagerUtils.getAdminUser().getUserid() + "'";
			countSql = countSql.append(" and t.lock_id = '" + ManagerUtils.getAdminUser().getUserid() + "'");
		}
		if (!StringUtils.isEmpty(params.getOrder_from())) {
			sql = sql + " and t.source_system_type in('" + params.getOrder_from().replace(",", "','") + "')";
			countSql = countSql.append(" and t.source_system_type in('" + params.getOrder_from().replace(",", "','") + "')");
		}
		if (!StringUtils.isEmpty(params.getTop_seed_professional_line())) {
			sql = sql + " and t.top_seed_professional_line in('" + params.getTop_seed_professional_line().replace(" ", "").replace(",", "','") + "')";
			countSql.append(" and t.top_seed_professional_line in('" + params.getTop_seed_professional_line().replace(" ", "").replace(",", "','") + "')");
		}
		if (!StringUtils.isEmpty(params.getTop_seed_type())) {
			sql = sql + " and t.top_seed_type in('" + params.getTop_seed_type().replace(" ", "").replace(",", "','") + "')";
			countSql.append(" and t.top_seed_type in('" + params.getTop_seed_type().replace(" ", "").replace(",", "','") + "')");
		}
		if (!StringUtils.isEmpty(params.getTop_seed_group_id())) {
			sql = sql + " and t.top_seed_group_id in('" + params.getTop_seed_group_id().replace(" ", "").replace(",", "','") + "')";
			countSql.append(" and t.top_seed_group_id in('" + params.getTop_seed_group_id().replace(" ", "").replace(",", "','") + "')");
		}
		sql = sql + " and t.status not in ('03','00') order by t.create_time desc";
		countSql = countSql.append(" and t.status not in ('03','00')");
		Page webpage = this.baseDaoSupport.queryForPage(sql.toString(),countSql.toString(), pageNo, pageSize, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("source_system_type", rs.getString("source_system_type"));
				map.put("order_id", rs.getString("order_id"));
				map.put("intent_order_id", rs.getString("intent_order_id"));
				map.put("referee_name", rs.getString("referee_name"));
				map.put("referee_num", rs.getString("referee_num"));
				map.put("ship_name", rs.getString("ship_name"));
				map.put("ship_tel", rs.getString("ship_tel"));
				map.put("ship_addr", rs.getString("ship_addr"));
				map.put("order_county_code", rs.getString("order_county_code"));
				map.put("status", rs.getString("status"));
				map.put("work_status", rs.getString("work_status"));
				map.put("remark", rs.getString("remark"));
				map.put("create_time", rs.getString("create_time"));
				map.put("goods_name", rs.getString("goods_name"));
				map.put("order_city_code", rs.getString("order_city_code"));
				map.put("is_work_custom", rs.getString("is_work_custom"));
				map.put("lock_id", rs.getString("lock_id"));
				map.put("top_seed_professional_line", rs.getString("top_seed_professional_line"));
				map.put("top_seed_type", rs.getString("top_seed_type"));
				map.put("top_seed_group_id", rs.getString("top_seed_group_id"));
				return map;
			}
		});
		return webpage;
	}

	public Page intentHandleQuery(OrderQueryParams params, int pageNo, int pageSize) {
		String sql = "select (select distinct field_value_desc from es_dc_public_dict_relation where stype='bss_area_code' and field_value=t.order_city_code)as order_city_code, (select distinct pname from es_dc_public_ext where stype='source_from' and pkey=t.source_system_type)as source_system_type, (select distinct pname from es_dc_public_ext where stype='INTENT_STATUS' and pkey=t.status)as status, (select a.pname from es_dc_public_ext a where a.stype = 'WORK_STATUS' and a.pkey = dd.status) work_status, t.order_id, t.intent_order_id, t.referee_name, t.referee_num, t.goods_name, t.ship_name, t.ship_tel, t.ship_addr, (select distinct other_field_value_desc from es_dc_public_dict_relation where stype ='county' and other_field_value=t.county_id)as order_county_code, t.remark, to_char(t.create_time, 'yyyy-MM-dd HH24:mi:ss') as create_time,t.is_work_custom as is_work_custom,lock_id||lock_name as lock_id ,top_seed_professional_line,top_seed_type,top_seed_group_id  from es_order_intent t left join (select row_number() OVER(PARTITION BY b.order_id order by b.create_time desc) as rw, b.* from es_work_order b) dd on dd.rw = 1 and dd.order_id = t.order_id where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "' ";
		StringBuffer countSql = new StringBuffer();
		countSql.append("select count(1) from es_order_intent t left join (select row_number() OVER(PARTITION BY b.order_id order by b.create_time desc) as rw, b.* from es_work_order b) dd on dd.rw = 1 and dd.order_id = t.order_id where t.source_from='"
				+ ManagerUtils.getSourceFrom() + "' ") ;
		
		//能否查到线上转线下订单开关
		String ONLINE_ORDER_INTENT_QUERY_FLAG = cacheUtil.getConfigInfo("ONLINE_ORDER_INTENT_QUERY_FLAG");
		if("0".equals(ONLINE_ORDER_INTENT_QUERY_FLAG)){
			//0--不能查询，加上查询限制
			
			sql += " and (t.is_online_order is null or t.is_online_order<>1) ";
			countSql.append(" and (t.is_online_order is null or t.is_online_order<>1) ");
		}
		
		if (!StringUtils.isEmpty(params.getOrder_id())) {
			sql = sql + " and t.order_id='" + params.getOrder_id() + "'";
			countSql.append(" and t.order_id='" + params.getOrder_id() + "'");
		}
		if (!StringUtils.isEmpty(params.getIntent_order_id())) {
			sql = sql + " and t.intent_order_id='" + params.getIntent_order_id() + "'";
			countSql.append(" and t.intent_order_id='" + params.getIntent_order_id() + "'");
		}
		if (!StringUtils.isEmpty(params.getShip_tel())) {
			sql = sql + " and t.ship_tel like '%" + params.getShip_tel() + "%'";
			countSql.append(" and t.ship_tel like '%" + params.getShip_tel() + "%'");
		}
		if (!StringUtils.isEmpty(params.getStatus())) {
			sql = sql + " and t.status in('" + params.getStatus().replace(" ", "").replace(",", "','") + "')";
			countSql.append(" and t.status in('" + params.getStatus().replace(" ", "").replace(",", "','") + "')");
		}
		if (!StringUtils.isEmpty(params.getCreate_start())) {
			sql = sql + " and t.create_time >= to_date('" + params.getCreate_start() + "','yyyy-MM-dd HH24:mi:ss')";
			countSql.append(" and t.create_time >= to_date('" + params.getCreate_start() + "','yyyy-MM-dd HH24:mi:ss')");
		}
		if (!StringUtils.isEmpty(params.getCreate_end())) {
			sql = sql + " and t.create_time <= to_date('" + params.getCreate_end() + "','yyyy-MM-dd HH24:mi:ss')";
			countSql.append(" and t.create_time <= to_date('" + params.getCreate_end() + "','yyyy-MM-dd HH24:mi:ss')");
		}
		
		if (!StringUtils.isEmpty(params.getTop_seed_professional_line())) {
			sql = sql + " and t.top_seed_professional_line in('" + params.getTop_seed_professional_line().replace(" ", "").replace(",", "','") + "')";
			countSql.append(" and t.top_seed_professional_line in('" + params.getTop_seed_professional_line().replace(" ", "").replace(",", "','") + "')");
		}
		if (!StringUtils.isEmpty(params.getTop_seed_type())) {
			sql = sql + " and t.top_seed_type in('" + params.getTop_seed_type().replace(" ", "").replace(",", "','") + "')";
			countSql.append(" and t.top_seed_type in('" + params.getTop_seed_type().replace(" ", "").replace(",", "','") + "')");
		}
		if (!StringUtils.isEmpty(params.getTop_seed_group_id())) {
			sql = sql + " and t.top_seed_group_id in('" + params.getTop_seed_group_id().replace(" ", "").replace(",", "','") + "')";
			countSql.append(" and t.top_seed_group_id in('" + params.getTop_seed_group_id().replace(" ", "").replace(",", "','") + "')");
		}

		// modifeid by zhaochen 20181105 地市县分权限控制
		AdminUser user = ManagerUtils.getAdminUser();

		if (!StringUtils.isEmpty(params.getOrder_city_code())) {
			sql = sql + " and t.order_city_code='" + params.getOrder_city_code() + "'";
			countSql.append(" and t.order_city_code='" + params.getOrder_city_code() + "'");
		} else if (!"1".equals(user.getPermission_level())) {
			sql = sql + SqlUtil.getSqlInStr("t.order_city_code", user.getPermission_region(), false, true);
			countSql.append(SqlUtil.getSqlInStr("t.order_city_code", user.getPermission_region(), false, true));
		}

		if (!StringUtils.isEmpty(params.getOrder_county_code())) {
			sql = sql + " and t.order_county_code='" + params.getOrder_county_code() + "'";
			countSql.append(" and t.order_county_code='" + params.getOrder_county_code() + "'");
		} else if ("3".equals(user.getPermission_level())) {
			sql = sql + SqlUtil.getSqlInStr("t.order_county_code", user.getPermission_county(), false, true);
			countSql.append(SqlUtil.getSqlInStr("t.order_county_code", user.getPermission_county(), false, true));
		}
		// end

		if (!StringUtils.isEmpty(params.getPhone_num())) {
			sql = sql + " and t.acc_nbr='" + params.getPhone_num() + "'";
			countSql.append(" and t.acc_nbr='" + params.getPhone_num() + "'");
		}
		if (!StringUtils.isEmpty(params.getOrder_from())) {
			sql = sql + " and t.source_system_type in('" + params.getOrder_from().replace(",", "','") + "')";
			countSql.append(" and t.source_system_type in('" + params.getOrder_from().replace(",", "','") + "')");
		}
		String intent_query_flag = cacheUtil.getConfigInfo("intent_query_flag");
		if (!StringUtil.isEmpty(intent_query_flag)) {
			// 管理员和省份的工号展示所有,没有地市一级的工号
			if ("07XX".equals(ManagerUtils.getAdminUser().getOrg_id())
					|| "0010".equals(ManagerUtils.getAdminUser().getOrg_id())) {
			} else {
				if ("1".equals(ManagerUtils.getAdminUser().getFounder())
						|| "3".equals(ManagerUtils.getAdminUser().getFounder())) {// 管理员，一般没有不是省份的超级管理员
					sql = sql + " and t.order_county_code='" + ManagerUtils.getAdminUser().getOrg_id() + "'";// 没有地市一级的工号,只做县分
					countSql.append(" and t.order_county_code='" + ManagerUtils.getAdminUser().getOrg_id() + "'");
				} else {// 普通员工
					sql = sql + " and t.lock_id = '" + ManagerUtils.getAdminUser().getUserid() + "'";
					countSql.append(" and t.lock_id = '" + ManagerUtils.getAdminUser().getUserid() + "'");
				}
			}
		}
		sql = sql + "order by t.create_time desc";// 前期状态不过滤
		logger.info("意向单查询" + sql);
		Page webpage = this.baseDaoSupport.queryForPage(sql.toString(),countSql.toString(), pageNo, pageSize, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("source_system_type", rs.getString("source_system_type"));
				map.put("order_id", rs.getString("order_id"));
				map.put("intent_order_id", rs.getString("intent_order_id"));
				map.put("referee_name", rs.getString("referee_name"));
				map.put("referee_num", rs.getString("referee_num"));
				map.put("ship_name", rs.getString("ship_name"));
				map.put("ship_tel", rs.getString("ship_tel"));
				map.put("ship_addr", rs.getString("ship_addr"));
				map.put("order_county_code", rs.getString("order_county_code"));
				map.put("status", rs.getString("status"));
				map.put("work_status", rs.getString("work_status"));
				map.put("remark", rs.getString("remark"));
				map.put("create_time", rs.getString("create_time"));
				map.put("goods_name", rs.getString("goods_name"));
				map.put("order_city_code", rs.getString("order_city_code"));
				map.put("is_work_custom", rs.getString("is_work_custom"));
				map.put("lock_id", rs.getString("lock_id"));
				map.put("top_seed_professional_line", rs.getString("top_seed_professional_line"));
				map.put("top_seed_type", rs.getString("top_seed_type"));
				map.put("top_seed_group_id", rs.getString("top_seed_group_id"));
				return map;
			}
		});
		return webpage;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getIntentStatusList(String type) {
		String sql = "select a.pkey value,a.pname value_desc from es_dc_public_ext a where a.stype='INTENT_STATUS'";
		if ("handle".equals(type)) {
			sql = sql + " and a.pkey not in ('00','03')";
		}
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	/**
	 * 行政县分对应营业县分 county_EJX
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getIntentCountyList(String order_city_code) {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		/*String sql = "select distinct field_value,other_field_value_desc as field_value_desc,"
				+ "other_field_value from es_dc_public_dict_relation  where stype ='county' {county}   order by other_field_value ";*/
		String sql=cacheUtil.getConfigInfo("ALL_COUNTY_SQL");
		if (!StringUtils.isEmpty(order_city_code)&& !order_city_code.equals("330000")) {
			sql = sql.replace("{county}", "and (col1='"+order_city_code+"' or col1='330000')");
		}else {
			sql = sql.replace("{county}","");
		}
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	/**
	 * 所有的营业县分
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCountyList(String order_city_code) {
		String sql = "select countyid as field_value,countyname as field_value_desc from es_busicty where countyid <> 'Z00' ";
		if (!StringUtils.isEmpty(order_city_code)) {
			sql = sql
					+ "and areaid in (select other_field_value from es_dc_public_dict_relation where stype='bss_area_code' and field_value='"
					+ order_city_code + "')";
		}
		logger.info("---用户权限是营业县分:" + sql);
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	/**
	 * 意向单操作记录
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getIntentLogs(String order_id) {
		String sql = "SELECT action, op_id, op_name, to_char(t.create_time, 'yyyy-MM-dd HH24:mi:ss') AS create_time, remark, work_handle_id, work_handle_name, to_char(t.work_result_time, 'yyyy-MM-dd HH24:mi:ss') AS work_result_time, work_result_remark,(select decode(ewo.operator_num,'','','('||ewo.operator_num||')') from es_work_order ewo where ewo.work_order_id = t.work_order_id ) as operator_num FROM es_intent_handle_logs t where order_id = '"
				+ order_id + "' and source_from='" + ManagerUtils.getSourceFrom() + "' order by create_time desc";
		logger.info("意向单详情：" + sql);
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	/**
	 * 自定义工单信息查询
	 */
	@Override
	public List<Map<String, Object>> getWorkCustomLogs(String order_id) {
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("   select c.node_name as action, ");
		sbBuffer.append("    c.op_id as op_id, ");
		sbBuffer.append("     c.op_name as op_name, ");
		sbBuffer.append("     to_char(c.create_date, 'yyyy-MM-dd HH24:mi:ss') AS create_time, ");
		sbBuffer.append("     c.remark as remark, ");
		sbBuffer.append("    c.curr_user_id as work_handle_id, ");
		sbBuffer.append("    c.curr_user_name as work_handle_name, ");
		sbBuffer.append("    to_char(c.update_date, 'yyyy-MM-dd HH24:mi:ss') AS work_result_time, ");
		sbBuffer.append("    (select decode(a.phone_num, ");
		sbBuffer.append("                '', ");
		sbBuffer.append("                '', ");
		sbBuffer.append(
				"               '(' || a.phone_num || ')') from  es_adminuser a where a.userid = c.curr_user_id ) as operator_num ,");
		sbBuffer.append("     c.ext_1 as ext_1     ");
		sbBuffer.append("   from es_work_custom_node_ins_h c  where source_from='").append(ManagerUtils.getSourceFrom())
				.append("' and order_id = '").append(order_id).append("'   order by ext_1 desc");
		logger.info("定义流程详情：" + sbBuffer.toString());
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sbBuffer.toString());
		return list;
	}

	/**
	 * 意向单详情
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getIntentDetail(String order_id) {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		String sql="";
		String source_from= ManagerUtils.getSourceFrom();
		
		sql=cacheUtil.getConfigInfo("INTENT_DETAIL_SQL");
		
		sql=sql.replace("{order_id}", order_id);
		sql=sql.replace("{source_from}", source_from);		
		
		logger.info("意向单详情:"+sql);


		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		if (list.size() > 0) {
			Map<String, Object> map = list.get(0);
			String real_offer_price = "0";
			if (!StringUtils.isEmpty(map.get("real_offer_price") + "")) {
				real_offer_price = map.get("real_offer_price") + "";
			}
			Double price = Double.parseDouble(real_offer_price);
			if (price > 0) {
				map.put("real_offer_price", price / 1000);
			} else {
				map.put("real_offer_price", "0");
			}
			sql = "select * from es_order where order_id='" + order_id + "'";
			list = this.baseDaoSupport.queryForList(sql);
			if (list.size() > 0) {// 判断是否生产正式订单
				map.put("exist", "1");
			}
			return map;
		}
		return null;

	}

	/**
	 * 工单回收关闭
	 * 
	 * @param order_id
	 * @return
	 */
	public String closeOrdWork(String order_id, String work_reason) {
		List<OrderWorksBusiRequest> workList = queryWorkListByOrderId(order_id);
		if (workList == null || workList.size() == 0) {
			return "只有未处理的工单才能回收!";
		} else {
			List<OrderWorksBusiRequest> list = new ArrayList<OrderWorksBusiRequest>();
			for (OrderWorksBusiRequest work : workList) {
				String status = cacheUtil.getConfigInfo("WORK_CLOSE_STATUS");
				if (status.contains(work.getStatus())) {
					list.add(work);
				}
			}
			if (list.size() == 0) {
				return "只有未处理的工单才能回收!";
			}
			String userId = ManagerUtils.getAdminUser().getUserid();
			for (OrderWorksBusiRequest work : list) {
				if (!userId.equals(work.getOrder_send_usercode())&&  !userId.equals("1") ) {
					return "该订单存在其他派发人工单，无法关闭";
				}
			}
			// 正常关闭工单
			Map<String, Object> fieldsMap = new HashMap<String, Object>();
			fieldsMap.put("status", "3");// 已撤单
			fieldsMap.put("update_time", new Date());
			fieldsMap.put("result_remark", work_reason);
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("order_id", order_id);
			//whereMap.put("order_send_usercode", userId);
			daoSupport.update("es_work_order", fieldsMap, whereMap);
			// 更新意向单备注
			for (OrderWorksBusiRequest work : list) {
				String sql = "select t.* from es_intent_handle_logs t where t.order_id='" + order_id
						+ "' and t.work_order_id='" + work.getWork_order_id() + "' and t.source_from='"
						+ ManagerUtils.getSourceFrom() + "'";
				List logs = daoSupport.queryForList(sql);
				if (logs.size() > 0) {
					fieldsMap.clear();
					fieldsMap.put("work_result_remark", "工单回收原因 ：" + work_reason);
					fieldsMap.put("work_result_time", new Date());
					whereMap.clear();
					whereMap.put("order_id", order_id);
					whereMap.put("work_order_id", work.getWork_order_id());
					daoSupport.update("es_intent_handle_logs", fieldsMap, whereMap);
				}
			}
			OrderHandleLogsReq req = new OrderHandleLogsReq();
			OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(order_id);
			if(otreq!=null){
				String is_work_custom = otreq.getOrderExtBusiRequest().getIs_work_custom();
				 ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	            String kgs = cacheUtil.getConfigInfo("work_backe_kg");
		        	//如果有当前环节权限则锁定订单，如果没有则解锁
				if("1".equals(is_work_custom) &&"1".equals(kgs)){
					String sql = "select t.node_name,t.instance_id,t.workflow_id from es_work_custom_node_ins t where t.order_id='"+order_id+"' and t.is_curr_step ='1' and t.is_done='0' and t.source_from='"+ManagerUtils.getSourceFrom()+"'";
					List<Map<String, Object>> node_insinfo = this.baseDaoSupport.queryForList(sql);
					AdminUser user = ManagerUtils.getAdminUser();
					Map<String, Object> map = new HashMap();
					if(node_insinfo.size()>0){
						map.put("instance_id",node_insinfo.get(0).get("instance_id"));
						map.put("workflow_id", node_insinfo.get(0).get("workflow_id"));
						if(!StringUtil.isEmpty(node_insinfo.get(0).get("node_name")+"")){
							map.put("node_name", node_insinfo.get(0).get("node_name"));
						}else{
							map.put("node_name","派单");
						}
					}
					map.put("order_id", order_id);
					map.put("source_from", "ECS");
					map.put("create_date", "sysdate");
					map.put("op_id", user.getUserid());
					map.put("op_name",user.getRealname());
                    map.put("remark", work_reason+"  /工单回收");
					baseDaoSupport.insert("es_work_custom_log", map);
				}else{
					AdminUser user = ManagerUtils.getAdminUser();
					req.setOp_id(user.getUserid());
					req.setOp_name(user.getRealname());
					req.setOrder_id(otreq.getOrder_id());
					req.setFlow_id(otreq.getOrderExtBusiRequest().getFlow_id());
					req.setFlow_trace_id(otreq.getOrderExtBusiRequest().getFlow_trace_id());
					req.setComments(work_reason);
					req.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
					req.setType_code("o_closeWork");
					IOrderFlowManager ordFlowManager = SpringContextHolder.getBean("ordFlowManager");
					ordFlowManager.insertOrderHandLog(req);
				}
			} 
			
			return "";
		}
	}

	/**
	 * 订单领取新页面显示的订单详情
	 * 
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrderDetail(String order_id) {
		StringBuffer sql = new StringBuffer();
		sql.append(//development_code  development_name
				"SELECT t.order_id, t.out_tid, i.phone_num, d.ship_tel, v.buyer_name, d.ship_addr, v.develop_code_new as development_code, v.develop_name_new AS development_name, o.order_amount, o.paymoney, d.ship_name, "
						+ "to_char(t.tid_time, 'yyyy-MM-dd HH24:mi:ss') AS tid_time, "
						+ "(SELECT DISTINCT p.pname FROM es_dc_public_ext p WHERE p.stype = 'source_from' AND p.pkey = t.order_from) AS order_from, "
						+ "(SELECT DISTINCT p.pname FROM es_dc_public_ext p WHERE p.stype = 'DIC_ORDER_NEW_TYPE' AND p.pkey = o.order_type) AS order_type, "
						+ "(SELECT DISTINCT a.local_name FROM es_regions a WHERE a.p_region_id = '330000' AND a.region_id = t.order_city_code) AS order_city_code, "
						+ "(select field_value_desc from es_dc_public_dict_relation where stype='county' and field_value = v.district_code)as district_code, "
						+ "(SELECT DISTINCT a.pname FROM es_dc_public_ext a WHERE a.stype = 'channel_mark' AND a.pkey = t.channel_mark) AS channel_mark, "
						+ "(SELECT DISTINCT a.pname FROM es_dc_public_ext a WHERE a.stype = 'pay_type' AND a.pkey = t.pay_type) AS pay_type, "
						+ "(SELECT DISTINCT a.pname FROM es_dc_public_ext a WHERE a.stype = 'pay_way' AND a.pkey = p.pay_method) AS pay_method, "
						+ "(SELECT DISTINCT a.pname FROM es_dc_public_ext a WHERE a.stype = 'pay_status' AND a.pkey = o.pay_status) AS pay_status, "
						+ "(SELECT DISTINCT p.pname value_desc FROM es_dc_public_ext p WHERE p.stype = 'shipping_time' AND p.pkey = d.shipping_time) AS shipping_time, "
						+ "(SELECT DISTINCT name FROM es_regions_zb WHERE add_level = '1' AND code = d.province_id) AS province_id, "
						+ "(SELECT DISTINCT name FROM es_regions_zb WHERE add_level = '2' AND pre_code = '330000' AND code = d.city_id) AS city_id, "
						+ "(SELECT DISTINCT name FROM es_regions_zb WHERE add_level = '3' AND pre_code = d.city_id AND code = d.region_id) AS region_id, "
						+ "(SELECT DISTINCT a.adsl_addr_desc FROM es_order_zhwq_adsl a WHERE a.order_id = t.order_id AND a.adsl_addr_desc is NOT null) AS adsl_addr_desc "
						+ "FROM es_order_ext t LEFT JOIN es_order o ON t.order_id = o.order_id LEFT JOIN es_order_extvtl v ON t.order_id = v.order_id "
						+ "LEFT JOIN es_payment_logs p ON t.order_id = p.order_id LEFT JOIN es_delivery d ON t.order_id = d.order_id LEFT JOIN es_order_items_ext i ON t.order_id = i.order_id ");
		sql.append("WHERE t.source_from = '" + ManagerUtils.getSourceFrom() + "' AND t.order_id='" + order_id + "' ");
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql.toString());
		if (list.size() > 0) {
			Map<String, Object> map = list.get(0);
			return map;
		}
		return null;
	}

	/**
	 * 订单领取新页面显示的详情下的订单记录日志
	 * 
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrderHandleLogsReq> getOrderLogs(String order_id) {
		String sql = "select * from es_order_handle_logs where order_id='" + order_id + "' and source_from = '"
				+ ManagerUtils.getSourceFrom() + "' ";
		Page page = this.baseDaoSupport.queryForPage(sql, 1, 999, OrderHandleLogsReq.class);
		return page.getResult();
	}

	/**
	 * 意向单客户联系结果列表
	 * 
	 * @return
	 */
	public List getContactResultsList() {
		String sql = "select a.result_id, a.results from es_customer_contact_results a where a.grade = '01' and a.state = '1' order by a.result_id ";
		List  list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	public List getContactSecondResultsList(String linkId) {
		String sql = "select a.result_id , a.results  from es_customer_contact_results a where a.grade = '02' and a.state = '1' and a.link_id='"+linkId+"' ";
		List list = this.baseDaoSupport.queryForList(sql);
		return list;
	}
	
	@Override
	public void updateOrderState(String order_id, String order_state, String msg) throws ApiBusiException {
		// 宽带一期模式
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		orderTree.getOrderBusiRequest().setOrder_state(order_state);
		ZjCommonUtils.updateOrderTree(" order_state = '" + order_state + "' ", "es_order", order_id, orderTree);

		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		Map<String, Object> whereMap = new HashMap<String, Object>();
		// 宽带预约单
		if (isKDYYD(order_id, orderTree)) {
			String sql = "select * from es_kd_order_status_syn where order_id='" + order_id + "' and source_from = '"
					+ ManagerUtils.getSourceFrom() + "' and syn_status in ('WCL','CLSB') ";
			List list = this.baseDaoSupport.queryForList(sql);
			if (list.size() > 0) {
				fieldsMap.put("dealtime", DateFormatUtils.getFormatedDateTime());
				fieldsMap.put("dealstate", order_state);
				fieldsMap.put("syn_num", 0);
				fieldsMap.put("syn_status", "WCL");

				whereMap.put("order_id", order_id);
				daoSupport.update("es_kd_order_status_syn", fieldsMap, whereMap);
			} else {
				fieldsMap.put("id", this.baseDaoSupport.getSequences("seq_kd_status_syn"));
				fieldsMap.put("bespeakid", orderTree.getOrderExtBusiRequest().getOut_tid());
				fieldsMap.put("order_id", order_id);
				fieldsMap.put("dealstate", order_state);
				fieldsMap.put("dealtime", DateFormatUtils.getFormatedDateTime());
				fieldsMap.put("syn_status", "WCL");
				fieldsMap.put("syn_num", 0);
				fieldsMap.put("source_from", ManagerUtils.getSourceFrom());
				daoSupport.insert("es_kd_order_status_syn", fieldsMap);
			}

			// 退单环节
			// Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
			// String plan_mode = m.get("plan_id").toString();
			// String rule_mode = m.get("app_rule_id").toString();

			// 清除之前的日志
			// CommonDataFactory.getInstance().delRuleExeLog(plan_mode, null,
			// order_id);

			// 修改订单状态为退单中的状态
			OrderBusiRequest orderBusiReq = orderTree.getOrderBusiRequest();
			orderBusiReq.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_12);

			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();// 退单申请，只登记不走接口
			orderExt.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
			orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_08);
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();
			// 规则没有业务
			// TacheFact fact = new TacheFact();
			// fact.setOrder_id(orderTree.getOrder_id());
			// RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_mode,
			// rule_mode, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
			// if(resp!=null && "0".equals(resp.getError_code())){}
			String handler_comments = ZjEcsOrderConsts.REFUND_APPLY_DESC + "：提交接口异常" + msg;
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_DESC },
					new String[] { handler_comments });
			AdminUser user = ManagerUtils.getAdminUser();
			// //写日志
			OrderHandleLogsReq req = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			req.setOrder_id(order_id);
			req.setFlow_id(flow_id);
			req.setFlow_trace_id(flowTraceId);
			req.setComments(handler_comments);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
			req.setType_code(EcsOrderConsts.REFUND_STATUS_08);
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getUsername());
			if (StringUtil.isEmpty(req.getOp_id())) {
				req.setOp_id("1");
				req.setOp_name("系统管理员");
			}
			req.setCreate_time("sysdate");
			this.baseDaoSupport.insert("es_order_handle_logs", req);

		}
	}

	@Override
	public Boolean isKDYYD(String order_id, OrderTreeBusiRequest orderTree) throws ApiBusiException {
		if (null == orderTree || StringUtils.isEmpty(orderTree.getOrder_id())) {
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}
		String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
		String kdyyd_order_from = cacheUtil.getConfigInfo("kdyyd_order_from");
		if (kdyyd_order_from.contains(order_from)) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean isKDYQ(String order_id, OrderTreeBusiRequest orderTree) throws ApiBusiException {
		try {
			if (null == orderTree || StringUtils.isEmpty(orderTree.getOrder_id())) {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			}
			String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
			String goods_type = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
					.getGoods_type();
			String kdyq_order_from = cacheUtil.getConfigInfo("kdyq_order_from");// 包含的订单来源
			String kdyq_goods_type = cacheUtil.getConfigInfo("kdyq_goods_type");// 不含商品类型
			if (kdyq_order_from.contains(order_from) && !kdyq_goods_type.contains(goods_type)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 退单处理 退单选择
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDisposeResultsList() {
		String sql = "select e.pkey value,e.pname value_dese from es_dc_public_ext e where stype='retreat_order_dispose'";
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	@Override
	public String getCountyName(String county_code) {
		String sql = "select bus.countyname " + "  from es_busicty bus "
				+ "  where bus.countyid = (select distinct t.other_field_value "
				+ "  from es_dc_public_dict_relation t " + "  where stype = 'county' " + "  and t.field_value = '"
				+ county_code + "')";
		return this.baseDaoSupport.queryForString(sql);
	}

	/**
	 * 意向单转正式订单，工单状态转处理中
	 */
	@Override
	public String upWorkStatus(String order_id) {
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		fieldsMap.put("status", "4");// 处理中
		fieldsMap.put("update_time", new Date());
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("order_id", order_id);
		whereMap.put("status", "0");
		daoSupport.update("es_work_order", fieldsMap, whereMap);

		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getBusiCountyByIds(List<String> regionIdList, List<String> countyIdList)
			throws Exception {
		StringBuilder builder = new StringBuilder();

		builder.append(" select a.countyid    as county_id, ");
		builder.append(" a.countyname  as county_name, ");
		builder.append("  c.field_value as region_id ");
		builder.append(" from es_busicty a, es_dc_public_dict_relation c ");
		builder.append(" where a.countyid <> 'Z00' ");
		builder.append(" and c.stype = 'region' ");
		builder.append(" and a.areaid = c.other_field_value ");

		if (regionIdList != null && regionIdList.size() > 0) {
			builder.append(" and exists ( ");
			builder.append(" SELECT b.field_value FROM es_dc_public_dict_relation b ");
			builder.append(" WHERE b.stype='region'  ");
			builder.append(SqlUtil.getSqlInStr("b.field_value", regionIdList, false, true));
			builder.append(" and a.areaid=b.other_field_value )  ");
		}

		if (countyIdList != null && countyIdList.size() > 0) {
			builder.append(SqlUtil.getSqlInStr("a.countyid", countyIdList, false, true));
		}

		builder.append(" order by a.countyid ");

		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(builder.toString());

		return list;
	}

	/**
	 * 判断工单类型
	 */
	@SuppressWarnings("unchecked")
	public String isMixOrd(String order_id) {
		String json = null;
		try {
			String sql = "select * from es_order_intent where order_id =?";
			List<Map<String, String>> list = baseDaoSupport.queryForList(sql, order_id);
			if (list.size() > 0) {
				String order_from = list.get(0).get("source_system_type");
				if ("10093".equals(order_from)||"10101".equals(order_from)) {
					sql = "select order_id from es_work_order where order_id=? and type='08' and status in ('0','4')";
					list = baseDaoSupport.queryForList(sql, order_id);
					if (list.size() > 0) {
						json = "{result:0,message:'自传播',isMixOrd:'refer',type:'08',flag:'false'}";
					} else {
						json = "{result:0,message:'自传播',isMixOrd:'refer',type:'08',flag:'true'}";
					}
				} else if ("100103".equals(order_from)) {
					sql = "select order_id from es_work_order where order_id=? and type='09' and status in ('0','4')";
					list = baseDaoSupport.queryForList(sql, order_id);
					if (list.size() > 0) {
						json = "{result:0,message:'政企专属',isMixOrd:'enterprise',type:'09',flag:'false'}";
					} else {
						json = "{result:0,message:'政企专属',isMixOrd:'enterprise',type:'09',flag:'true'}";
					}
				} else {
					sql = "select order_id from es_work_order where order_id=? and type='07' and status in ('0','4')";
					list = baseDaoSupport.queryForList(sql, order_id);
					if (list.size() > 0) {
						json = "{result:0,message:'意向单单',isMixOrd:'intent',type:'07',flag:'false'}";
					} else {
						json = "{result:0,message:'意向单单',isMixOrd:'intent',type:'07',flag:'true'}";
					}
				}
			} else {
				// 根据商品小类判断是否为固移融合
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				String goods_type = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest()
						.getGoods_type();
				String cfgGoodsTypeId = cacheUtil.getConfigInfo("MIXORD_TYPE_ID");// 宽带极简
				if (cfgGoodsTypeId.contains(goods_type)) {
					sql = "select order_id from es_work_order where order_id=? and type='06' and status in ('0','4')";
					list = baseDaoSupport.queryForList(sql, order_id);
					if (list.size() > 0) {
						json = "{result:0,message:'固移融合单',isMixOrd:'mixord',type:'06',flag:'false'}";
					} else {
						json = "{result:0,message:'固移融合单',isMixOrd:'mixord',type:'06',flag:'true'}";
					}
				} else {
					List<OrderWorksBusiRequest> workList = queryWorkListByOrderId(order_id);
					String type = ",01,02,03,04,05"; // 允许派发工单的类型
					String should = "";
					// 01收费单,02外勘单,03实名单,04 挽留单,05写卡单
					if (null != workList && workList.size() > 0) {
						for (OrderWorksBusiRequest work : workList) {
							if ("0,4".contains(work.getStatus())) {
								type = type.replace("," + work.getType(), "");
							}
						}
					}
					if (isKDYQ(order_id, orderTree) || isKDYYD(order_id, orderTree)) {
						// 是否收费,0否;1是
						String is_pay = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_PAY);
						// 是否实名0未实名1已实名
						String is_real_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
								AttrConsts.IS_REAL_NAME);
						if ("1".equals(is_pay)) {
							type.replace(",01", "");
						} else if ("0".equals(is_pay)) {
							if (type.contains("01")) {
								should += "01";
							}
						}
						if ("1".equals(is_real_name)) {
							type.replace(",03", "");
						} else if ("0".equals(is_real_name)) {
							if (type.contains("03")) {
								if ("".equals(should)) {
									should += "03";
								} else {
									should += ",03";
								}
							}
						}
					}
					if (!"".equals(type)) {
						type = type.substring(1);
					}
					json = "{result:0,message:'默认工单',isMixOrd:'normal',type:'" + type + "',flag:'true',should:'"
							+ should + "'}";
				}
			}

		} catch (Exception e) {
			json = "{result:1,message:'判断工单类型失败',isMixOrd:false}";
		}

		return json;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTopSeedProfessionalLine(String type) {
		String sql = "select a.pkey value,a.pname value_desc from es_dc_public_ext a where a.stype='top_seed_professional_line'";
		if ("handle".equals(type)) {
			sql = sql + " and a.pkey not in ('00','03')";
		}
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTopSeedType(String type) {
		String sql = "select a.pkey value,a.pname value_desc from es_dc_public_ext a where a.stype='top_seed_type'";
		if ("handle".equals(type)) {
			sql = sql + " and a.pkey not in ('00','03')";
		}
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTopSeedGroupId(String type) {
		String sql = "select a.pkey value,a.pname value_desc from es_dc_public_ext a where a.stype='top_seed_group_id'";
		if ("handle".equals(type)) {
			sql = sql + " and a.pkey not in ('00','03')";
		}
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql);
		return list;
	}
}
