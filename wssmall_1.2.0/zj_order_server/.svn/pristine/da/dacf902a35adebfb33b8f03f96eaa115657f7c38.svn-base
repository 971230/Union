package com.ztesoft.net.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import oracle.sql.BLOB;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderWorksBusiRequest;
import zte.net.ecsord.params.ecaop.req.CustInfoModReq;
import zte.net.ecsord.params.ecaop.req.IntentOrderQueryForCBReq;
import zte.net.ecsord.params.ecaop.resp.CustInfoModRsp;
import zte.net.ecsord.params.ecaop.resp.OrderQueryRespone;
import zte.net.ecsord.params.ecaop.resp.vo.OrderInfoRespVo;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.IRuleService;
import zte.net.params.req.RuleLogReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleLogRsp;
import zte.net.params.resp.RuleTreeExeResp;
import cn.ai.paycenter.PayCenterRSA;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.powerise.ibss.util.RuleUtil;
import com.taobao.tair.json.JSONArray;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.ecsord.params.ecaop.req.GeneralOrderQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoListQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderResultQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderStatusQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PayWorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QueryGoodsListReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderListQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderMixOrdUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderInfoBackfill.ORDER_INFO_BACKFILL_REQ;
import com.ztesoft.net.ecsord.params.ecaop.req.orderInfoBackfill.OrderInfoBackfillReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.GeneralOrderQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderResultQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderStatusQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayWorkOrderUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderMixOrdUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderInfoBackfill.OrderInfoBackfillRsp;
import com.ztesoft.net.ecsord.params.ecaop.vo.CustInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.DeveloperInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.Flow_Info_Cat_Cfg;
import com.ztesoft.net.ecsord.params.ecaop.vo.Flow_Info_Cfg;
import com.ztesoft.net.ecsord.params.ecaop.vo.Flow_Info_Node_Cfg;
import com.ztesoft.net.ecsord.params.ecaop.vo.Flow_Info_Rule_Cfg;
import com.ztesoft.net.ecsord.params.ecaop.vo.GoodsInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderListQueryInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderResultQueryInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderResultResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.ProdsInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.WorkDeveloperInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo.GeneralOrderInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo.OrderContacrInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo.OrderCustInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo.OrderDeveloperInfoVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo.OrderGoodInfo;
import com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo.OrderPartenersInfo;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.inf.InfCompareReq;
import com.ztesoft.net.model.inf.InfCompareResultVO;
import com.ztesoft.net.model.inf.InfRecVO;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderInfManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;
import com.ztesoft.net.sqls.SqlInBuilder;
import com.ztesoft.net.sqls.SqlUtil;
import commons.CommonTools;

import consts.ConstsCore;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 接口测试类,编码未规范
 * 
 * @author xuefeng
 */
public class OrderInfManager extends BaseSupport implements IOrderInfManager {

	private static Logger logger = Logger.getLogger(OrderInfManager.class);

	@Resource
	private IOrdWorkManager ordWorkManager;
	@Resource
	private IRuleService ruleService;
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	@Resource
	private IWorkCustomEngine workCustomEngine;
	private JdbcTemplate jdbcTemplate;

	private final String rec_req = "seq_es_ord_inf_record";
	private final String comp_result_01 = "01"; // 相同
	private final String comp_result_02 = "02"; // 不相同
	private final String comp_result_03 = "03"; // 无法比较
	private final String comp_result_04 = "04"; // 列表数据

	private final String field_type_list = "list";

	@Override
	public String getinfContent(String orderId, String ruleId, String op_code) {
		this.delRunLog(orderId, ruleId, op_code);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderId);
		RuleTreeExeReq req = new RuleTreeExeReq();
		req.setRule_id(ruleId);
		req.setFact(fact);
		CommonDataFactory.getInstance().exeRuleTree(req);
		return "";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void delRunLog(String orderId, String ruleId, String op_code) {
		String delRuleSql = "delete from es_rule_exe_log a where " + "a.obj_id = '" + orderId + "' " + "and a.rule_id = '" + ruleId + "'";
		// ****************** add by qin.yingxiong at 2016/10/20 start
		// *******************
		boolean useOrgTable = RuleUtil.useOrgTable(orderId);
		if (useOrgTable) {
			delRuleSql = RuleUtil.replaceTableNameForOrgBySql(delRuleSql);
		}
		// ****************** add by qin.yingxiong at 2016/10/20 end
		// *******************
		this.daoSupport.execute(delRuleSql);
	}

	private String getRunResult(String orderId, String op_code) {
		String result = "";
		Connection conn = DataSourceUtils.getConnection(this.jdbcTemplate.getDataSource());
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			if (!StringUtils.isEmpty(op_code)) {
				String sql = "select a.req_xml from inf_comm_client_calllog a " + "where a.col3 = ? " + "and a.op_code = ? and (source_from is null or source_from is not null)";
				pst = conn.prepareStatement(sql);
				pst.setObject(1, orderId);
				pst.setObject(2, op_code);
				rs = pst.executeQuery();
				while (rs.next()) {
					BLOB blob = (BLOB) rs.getBlob("req_xml");
					byte[] bdata = blob.getBytes(1, (int) blob.length());
					result = new String(bdata);
					logger.info("=====");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (conn != null) {
					DataSourceUtils.doReleaseConnection(conn, this.jdbcTemplate.getDataSource());
				}
			} catch (Exception f) {
			}
		}
		return result;
	}

	@Override
	public String compareInfContent(InfCompareReq cmpReq) {
		List<InfCompareResultVO> compareList = new ArrayList<InfCompareResultVO>();
		InfRecVO recVo = null;
		try {
			// 生成对比记录
			recVo = this.addCompRec(cmpReq);
			String newXml = cmpReq.getNewXml();
			String oldXml = cmpReq.getOldXml();
			if (!StringUtils.isEmpty(newXml) && !StringUtils.isEmpty(oldXml)) {
				Map<String, Object> newMap = (Map<String, Object>) JSONObject.fromObject(newXml);
				Map<String, Object> oldMap = (Map<String, Object>) JSONObject.fromObject(oldXml);
				mapCompRec(oldMap, newMap, "", "", compareList);
			}
			this.recCompareList(recVo.getRec_id(), cmpReq.getOp_code(), compareList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != recVo) {
				recVo.setComp_result(CommonTools.beanToJsonNCls(compareList));
				this.daoSupport.update("es_ord_inf_record", recVo, " rec_id = '" + recVo.getRec_id() + "'");
			}
		}
		return recVo == null ? "" : recVo.getRec_id();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private InfRecVO addCompRec(InfCompareReq cmpReq) {
		String rec_id = this.daoSupport.getSequences(rec_req, "1", 16);
		InfRecVO recVo = new InfRecVO();
		recVo.setRec_id(rec_id);
		recVo.setOp_code(cmpReq.getOp_code());
		recVo.setOrder_id(cmpReq.getOrder_id());
		recVo.setOut_id(cmpReq.getOut_id());
		recVo.setNew_xml(cmpReq.getNewXml());
		recVo.setOld_xml(cmpReq.getOldXml());
		recVo.setCompare_time(Consts.SYSDATE);
		this.daoSupport.insert("es_ord_inf_record", recVo);
		return recVo;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	private void recCompareList(String rec_id, String op_code, List<InfCompareResultVO> list) {
		String batchSql = "insert into es_inf_compare_result(rec_id, filed_path, filed_name, filed_type, comp_result, "
				+ "old_filed_value, new_filed_value, ignore_flag, source_from, op_code) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		List<String[]> sqlParam = new ArrayList<String[]>();
		if (null != list && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				InfCompareResultVO compVo = list.get(i);
				String[] infResult = new String[] { rec_id, StringUtils.isEmpty(compVo.getFiled_path()) ? "-1" : compVo.getFiled_path(), compVo.getFiled_name(), compVo.getFiled_type(),
						compVo.getComp_result(), compVo.getOld_filed_value(), compVo.getNew_filed_value(), compVo.getIgnore_flag(), ManagerUtils.getSourceFrom(), op_code };
				sqlParam.add(infResult);
			}
		}
		this.daoSupport.batchExecute(batchSql, sqlParam);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void mapCompRec(Map<String, Object> oldMap, Map<String, Object> newMap, String field_path, String real_path, List<InfCompareResultVO> compareList) {
		Iterator it = oldMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = oldMap.get(key);
			// 报文字段路径
			if (null != value) {
				if (value instanceof Map) {
					if (StringUtils.isEmpty(field_path)) {
						field_path = key;
					} else {
						field_path += "." + key;
					}
					if (StringUtils.isEmpty(real_path)) {
						real_path = key;
					} else {
						real_path += "." + key;
					}
					mapCompRec((Map<String, Object>) value, newMap, field_path, real_path, compareList);
					String[] mapField = field_path.split("\\.");
					if (mapField != null && mapField.length > 1) {
						field_path = field_path.substring(0, field_path.lastIndexOf("."));
					} else {
						field_path = "";
					}
					real_path = "";
				} else if (value instanceof List) {
					if (StringUtils.isEmpty(field_path)) {
						field_path = key;
					} else {
						field_path += "." + key;
					}
					if (StringUtils.isEmpty(real_path)) {
						real_path = key;
					} else {
						real_path += "." + key;
					}
					List list = (List) value;
					InfCompareResultVO listEle = this.listCompRec(field_path, real_path, key, list, newMap, compareList);
					compareList.add(listEle);
					String[] listFiled = field_path.split("\\.");
					if (listFiled != null && listFiled.length > 1) {
						field_path = field_path.substring(0, field_path.lastIndexOf("."));
					} else {
						field_path = "";
					}
					real_path = "";
				} else {
					InfCompareResultVO icr = this.findValue(field_path, real_path, key, value.toString(), newMap);
					compareList.add(icr);
				}
			} else {

			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private InfCompareResultVO listCompRec(String field_path, String real_path, String field_name, List list, Map<String, Object> newMap, List<InfCompareResultVO> compareList) {

		InfCompareResultVO result = new InfCompareResultVO();
		result.setFiled_path(field_path);
		result.setFiled_name(field_name);
		result.setFiled_type(field_type_list);
		List newList = null;
		newList = (List) newMap.get(real_path);
		if (newList != null) {
			if (newList.size() == list.size()) {
				if (list.size() == 1) {
					mapCompRec((Map<String, Object>) list.get(0), (Map<String, Object>) newList.get(0), field_path, "", compareList);
					result.setComp_result(comp_result_04);
				} else if (list.size() == 0) {
					result.setComp_result(comp_result_01);
				} else {
					result.setComp_result(comp_result_03);
				}
			} else {
				result.setComp_result(comp_result_02);
			}
			result.setNew_filed_value(JSONArray.toJSONString(newList));
		}
		result.setOld_filed_value(JSONArray.toJSONString(list));
		return result;
	}

	@SuppressWarnings("unchecked")
	private InfCompareResultVO findValue(String field_path, String real_path, String field_name, String field_value, Map<String, Object> newMap) {
		InfCompareResultVO result = new InfCompareResultVO();

		result.setFiled_path(field_path);
		result.setFiled_name(field_name);
		result.setOld_filed_value(field_value);
		Object new_value = null;
		if (!StringUtils.isEmpty(real_path)) {
			String[] fields = real_path.split("\\.");
			if (null != fields && fields.length > 0) {
				for (int i = 0; i < fields.length; i++) {
					newMap = (Map<String, Object>) newMap.get(fields[i]);
				}
			}
		}
		if (null != newMap) {
			new_value = newMap.get(field_name);
		}
		if (null != new_value) {
			result.setNew_filed_value(new_value.toString());
			if (new_value.toString().equals(field_value)) {
				result.setComp_result(comp_result_01);
			} else {
				result.setComp_result(comp_result_02);
			}
		}
		return result;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page getInfList(String rec_id, String order_id, String out_id, String op_code, Integer pageIndex, Integer pageSize) {
		String sql = "select a.*  from es_ord_inf_record a where 1 = 1";
		if (!StringUtils.isEmpty(rec_id)) {
			sql += " and a.rec_id = '" + rec_id + "' ";
		}
		if (!StringUtils.isEmpty(order_id)) {
			sql += " and a.order_id = '" + order_id + "' ";
		}
		if (!StringUtils.isEmpty(out_id)) {
			sql += " and a.out_id = '" + out_id + "' ";
		}
		if (!StringUtils.isEmpty(op_code)) {
			sql += " and a.op_code = '" + op_code + "' ";
		}
		sql += " order by a.compare_time desc";
		Page page = this.baseDaoSupport.queryForPage(sql, pageIndex, pageSize, InfRecVO.class);
		return page;
	}

	@Override
	public Page getInfDetail(String rec_id, String qry_cond, Integer pageIndex, Integer pageSize) {
		String sql = "select a.*, nvl(b.filed_name, 'N') ignore_field  " + "from es_inf_compare_result a left join es_inf_ignore_field b on "
				+ "(a.filed_path = b.filed_path and  a.filed_name = b.filed_name and a.op_code = b.op_code) " + "where a.rec_id = ? and a.source_from is not null ";
		if (!StringUtils.isEmpty(qry_cond) && !"ALL".equals(qry_cond)) {
			if ("SUCC".equals(qry_cond)) {
				sql += " and a.comp_result = '01' ";
			} else if ("FAIL".equals(qry_cond)) {
				sql += " and a.comp_result = '02' " + "and not exists(select 1 from es_inf_ignore_field t " + "where a.filed_path = t.filed_path and a.filed_name = t.filed_name "
						+ "and a.op_code = t.op_code)";
			} else if ("IGNORE".equals(qry_cond)) {

				sql += "and exists(select 1 from es_inf_ignore_field t " + "where a.filed_path = t.filed_path and a.filed_name = t.filed_name " + "and a.op_code = t.op_code)";
			}
		}
		Page page = this.baseDaoSupport.queryForPage(sql, pageIndex, pageSize, InfCompareResultVO.class, rec_id);
		return page;
	}

	public List getBlobList(String table_name) {
		String sql = "";
		if ("es_rule_config_audit".equals(table_name)) {
			sql = "select a.* from es_rule_config_audit a where a.status_cd = '00A'";
		} else if ("es_rule_config".equals(table_name)) {
			sql = "select a.* from es_rule_config a " + "where a.status_cd = '00A' and a.rule_id in " + "(select t.rule_id from es_rule_config_audit t where t.status_cd = '00A')";
		}
		List list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	public List getReqBlobList() {
		String sql = "select a.* from inf_comm_client_request a ";
		List list = this.daoSupport.queryForList(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map map = new HashMap();
				map.put("req_id", rs.getString("req_id"));
				map.put("gvar_id", rs.getString("gvar_id"));
				map.put("source_from", rs.getString("source_from"));
				map.put("qname", rs.getString("qname"));
				map.put("oper_classname", rs.getString("oper_classname"));
				map.put("oper_method", rs.getString("oper_method"));
				map.put("class_path", rs.getString("class_path"));
				map.put("qname_encode", rs.getString("qname_encode"));
				// BLOB blob = (BLOB) rs.getBlob("req_tpl"); //weblogic
				// 下报错，暂时也不需要该字段
				// if(null != blob){
				// byte[] bdata = blob.getBytes(1, (int) blob.length());
				// map.put("req_tpl", new String(bdata));
				// }
				return map;
			}
		});
		return list;
	}

	public List getRespBlobList() {
		String sql = "select a.* from inf_comm_client_response a ";
		List list = this.daoSupport.queryForList(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map map = new HashMap();
				map.put("rsp_id", rs.getString("rsp_id"));
				map.put("cdata_path", rs.getString("cdata_path"));
				map.put("xml_mapper", rs.getString("xml_mapper"));
				map.put("trans_fault", rs.getString("trans_fault"));
				map.put("rsp_classpath", rs.getString("rsp_classpath"));
				map.put("rsp_type", rs.getString("rsp_type"));
				map.put("source_from", rs.getString("source_from"));
				// BLOB blob = (BLOB) rs.getBlob("trans_tpl");//weblogic
				// 下报错，暂时也不需要该字段
				// if(null != blob){
				// byte[] bdata = blob.getBytes(1, (int) blob.length());
				// map.put("trans_tpl", new String(bdata));
				// }
				return map;
			}
		});
		return list;
	}

	public List getEndpointList() {
		String sql = "select a.* from inf_comm_client_endpoint  a ";
		List list = this.daoSupport.queryForList(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map map = new HashMap();
				map.put("ep_id", rs.getString("ep_id"));
				map.put("ep_address", rs.getString("ep_address"));
				map.put("ep_desc", rs.getString("ep_desc"));
				map.put("timeout", rs.getString("timeout"));
				map.put("ep_type", rs.getString("ep_type"));
				map.put("ep_mall", rs.getString("ep_mall"));
				map.put("source_from", rs.getString("source_from"));
				return map;
			}
		});
		return list;
	}

	public List getOperationList() {
		String sql = "select * from inf_comm_client_operation";
		List list = this.daoSupport.queryForList(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map map = new HashMap();
				map.put("op_code", rs.getString("op_code"));
				map.put("ep_mall", rs.getString("ep_mall"));
				/*
				 * map.put("ep_desc", rs.getString("ep_desc"));
				 * map.put("timeout", rs.getString("timeout"));
				 * map.put("ep_type", rs.getString("ep_type"));
				 * map.put("ep_mall", rs.getString("ep_mall"));
				 * map.put("source_from", rs.getString("source_from"));
				 */
				return map;
			}
		});
		return list;
	}

	/**
	 * 根据iccid和收货人电话查询订单id
	 *
	 * @param order_id
	 * @return
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月27日
	 */
	public String queryOrderInfo(String iccid, String shipMobile) {
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append("select ").append("	t1.order_id ").append("from ").append("	es_delivery t1, ").append("	es_order_extvtl t2 ").append("where ").append("	t1.ship_mobile = '").append(shipMobile)
				.append("'").append("	and substr(t2.iccid,length(t2.iccid)-7,7) = '").append(iccid).append("'").append("	and t1.order_id = t2.order_id ")
				.append("	and t1.source_from = '" + ManagerUtils.getSourceFrom() + "'").append("	and t2.source_from = '" + ManagerUtils.getSourceFrom() + "'");
		String orderId = daoSupport.queryForString(sqlSb.toString());
		return orderId;
	}

	
	public String getLiangPrice(String order_id) {
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append("select ").append("	t1.liang_price ").append("from ").append("	es_order_extvtl t1 ").append("where ").append("	t1.order_id = '").append(order_id)
				.append("'").append("	and t1.source_from = '" + ManagerUtils.getSourceFrom() + "'");
		String liang_price = daoSupport.queryForString(sqlSb.toString());
		return liang_price;
	}
	/**
	 * 工单列表查询
	 * 
	 * @author 宋琪  update sgf20190802
	 * @date 2017年12月1日
	 */
	@SuppressWarnings("unchecked")
	public WorkOrderListQueryResp queryWorkOrderListByRequ(WorkOrderListQueryReq requ) {
		WorkOrderListQueryResp resp = new WorkOrderListQueryResp();
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlFrom = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		StringBuffer sqlOrderBy = new StringBuffer();

	/*	sqlSelect.append(
				"  SELECT w.work_order_id, '10000' AS connected_system, w.order_id AS connected_system_id, w.type, decode(w.status, '0', '1', '1', '2', '2', '2', '3', '2', '4', '3') AS status, w.operator_id, w.operator_office_id, w.operator_num, w.operator_name, (case WHEN w.order_contact_name is NULL THEN (case WHEN v.phone_owner_name is NULL THEN oi.ship_name ELSE v.phone_owner_name end) ELSE w.order_contact_name end) AS order_contact_name, (case WHEN w.order_contact_phone is NULL THEN (case WHEN d.ship_tel is NULL THEN oi.ship_tel ELSE d.ship_tel end) ELSE w.order_contact_phone end) AS order_contact_phone, (case WHEN w.order_contact_addr is NULL THEN (case WHEN d.ship_addr is NULL THEN oi.ship_addr ELSE d.ship_addr end) ELSE w.order_contact_addr end) AS order_contact_addr, (case WHEN w.order_product_name is NULL THEN (case WHEN v.GoodsName is NULL THEN oi.goods_name ELSE v.GoodsName end) ELSE w.order_product_name end) AS order_product_name, o.paymoney * 1000 AS order_amount, to_char(w.create_time, 'yyyymmddhh24miss') AS create_time, to_char(w.update_time, 'yyyymmddhh24miss') AS update_time, w.remark, w.col1, w.col2, w.col3, w.order_send_usercode, w.order_send_userphone, w.order_send_username, w.order_priority, to_char(w.order_time_limit, 'yyyymmddhh24miss') AS order_time_limit, (SELECT pname FROM es_dc_public_ext WHERE stype = 'source_from' AND pkey = w.order_from) AS order_from, w.result_remark, decode(w.type, '03', v.phone_owner_name, '') AS customer_name, decode(w.type, '03', p.certi_type, '') AS cert_type, eoi.cert_addr AS cert_addr, decode(w.type, '03', p.cert_card_num, '') AS cert_num, decode(w.type, '03', p.cert_num2, '') AS cert_num2, decode(w.type, '03', v.out_office, '') AS staff_id, decode(w.type, '03', '', '') AS trade_type_code, decode(w.type, '07', oi.referee_name, v.recommended_name) AS referee_name, decode(w.type, '07', oi.referee_num, v.recommended_phone) AS referee_num, to_char(decode(w.type, '07', oi.create_time, e.tid_time), 'yyyymmddhh24miss') AS create_time2, decode(w.type, '07', oi.remark, '') AS remark2, decode(v.development_code, '', (select distinct develop_code from es_order_zhwq_adsl where order_id = w.order_id), '') AS develop_code, decode(v.development_name, '', (select distinct develop_name from es_order_zhwq_adsl where order_id = w.order_id), '') AS develop_name, decode(v.development_point_code, '', (select distinct develop_point_code from es_order_zhwq_adsl where order_id = w.order_id), '') AS develop_point_code, decode(v.development_point_name, '', (select distinct develop_point_name from es_order_zhwq_adsl where order_id = w.order_id), '') AS develop_point_name, eoi.acc_nbr, eoi.contract_month, eoi.lhscheme_id, eoi.advancepay, eoi.pre_fee, eoi.classid, eoi.lowcostpro, eoi.timedurpro, dbms_lob.substr(eoi.is_no_modify) AS is_no_modify, decode(oi.mainnumber, '', v.mainnumber, oi.mainnumber) as mainnumber, oi.group_code ");
		*/
		/*sqlSelect.append(
                "  SELECT w.work_order_id, '10000' AS connected_system, w.order_id AS connected_system_id, w.type, decode(w.status, '0', '1', '1', '2', '2', '2', '3', '2', '4', '3') AS status, w.operator_id, w.operator_office_id, w.operator_num, w.operator_name, (case WHEN w.order_contact_name is NULL THEN (case WHEN v.phone_owner_name is NULL THEN oi.ship_name ELSE v.phone_owner_name end) ELSE w.order_contact_name end) AS order_contact_name, (case WHEN w.order_contact_phone is NULL THEN (case WHEN d.ship_tel is NULL THEN oi.ship_tel ELSE d.ship_tel end) ELSE w.order_contact_phone end) AS order_contact_phone, (case WHEN w.order_contact_addr is NULL THEN (case WHEN d.ship_addr is NULL THEN oi.ship_addr ELSE d.ship_addr end) ELSE w.order_contact_addr end) AS order_contact_addr, (case WHEN w.order_product_name is NULL THEN (case WHEN v.GoodsName is NULL THEN oi.goods_name ELSE v.GoodsName end) ELSE w.order_product_name end) AS order_product_name, o.paymoney * 1000 AS order_amount, to_char(w.create_time, 'yyyymmddhh24miss') AS create_time, to_char(w.update_time, 'yyyymmddhh24miss') AS update_time, w.remark, w.col1, w.col2, w.col3, w.order_send_usercode, w.order_send_userphone, w.order_send_username, w.order_priority, to_char(w.order_time_limit, 'yyyymmddhh24miss') AS order_time_limit, (SELECT pname FROM es_dc_public_ext WHERE stype = 'source_from' AND pkey = w.order_from) AS order_from, w.result_remark, decode(w.type, '03', v.phone_owner_name, '') AS customer_name, decode(w.type, '03', p.certi_type, '') AS cert_type, eoi.cert_addr AS cert_addr, decode(w.type, '03', p.cert_card_num, '') AS cert_num, decode(w.type, '03', p.cert_num2, '') AS cert_num2, decode(w.type, '03', v.out_office, '') AS staff_id, decode(w.type, '03', '', '') AS trade_type_code, decode(w.type, '07', oi.referee_name, v.recommended_name) AS referee_name, decode(w.type, '07', oi.referee_num, v.recommended_phone) AS referee_num, to_char(decode(w.type, '07', oi.create_time, e.tid_time), 'yyyymmddhh24miss') AS create_time2, decode(w.type, '07', oi.remark, '') AS remark2, decode(v.develop_code_new, '', (select distinct develop_code from es_order_zhwq_adsl where order_id = w.order_id), '') AS develop_code, decode(v.develop_name_new, '', (select distinct develop_name from es_order_zhwq_adsl where order_id = w.order_id), '') AS develop_name, decode(v.develop_point_code_new, '', (select distinct develop_point_code from es_order_zhwq_adsl where order_id = w.order_id), '') AS develop_point_code, decode(v.development_point_name, '', (select distinct develop_point_name from es_order_zhwq_adsl where order_id = w.order_id), '') AS develop_point_name, eoi.acc_nbr, eoi.contract_month, eoi.lhscheme_id, eoi.advancepay, eoi.pre_fee, eoi.classid, eoi.lowcostpro, eoi.timedurpro, dbms_lob.substr(eoi.is_no_modify) AS is_no_modify, decode(oi.mainnumber, '', v.mainnumber, oi.mainnumber) as mainnumber, oi.group_code ,w.terminal_type,w.terminal_name ");
        
		sqlFrom.append(
				"  FROM es_work_order w LEFT JOIN es_order o ON w.order_id = o.order_id LEFT JOIN es_order_items_prod_ext p ON o.order_id = p.order_id LEFT JOIN es_delivery d ON p.order_id = d.order_id LEFT JOIN es_order_ext e ON d.order_id = e.order_id LEFT JOIN es_order_extvtl v ON e.order_id = v.order_id LEFT JOIN es_order_items i ON v.order_id = i.order_id LEFT JOIN es_goods g ON i.goods_id = g.goods_id LEFT JOIN es_order_intent oi ON w.order_id = oi.order_id LEFT JOIN es_order_intent eoi ON w.order_id = eoi.order_id ");
		sqlWhere.append(" where w.source_from = '" + ManagerUtils.getSourceFrom() + "' ");*/
		
		sqlSelect.append(
                "  SELECT w.work_order_id, '10000' AS connected_system, w.order_id AS connected_system_id, w.type, decode(w.status, '0', '1', '1', '2', '2', '2', '3', '2', '4', '3') AS status, w.operator_id, w.operator_office_id, w.operator_num, w.operator_name, (case WHEN w.order_contact_name is NULL THEN (case WHEN v.phone_owner_name is NULL THEN oi.ship_name ELSE v.phone_owner_name end) ELSE w.order_contact_name end) AS order_contact_name, (case WHEN w.order_contact_phone is NULL THEN (case WHEN d.ship_tel is NULL THEN oi.ship_tel ELSE d.ship_tel end) ELSE w.order_contact_phone end) AS order_contact_phone, (case WHEN w.order_contact_addr is NULL THEN (case WHEN d.ship_addr is NULL THEN oi.ship_addr ELSE d.ship_addr end) ELSE w.order_contact_addr end) AS order_contact_addr, (case WHEN w.order_product_name is NULL THEN (case WHEN v.GoodsName is NULL THEN oi.goods_name ELSE v.GoodsName end) ELSE w.order_product_name end) AS order_product_name, o.paymoney * 1000 AS order_amount, to_char(w.create_time, 'yyyymmddhh24miss') AS create_time, to_char(w.update_time, 'yyyymmddhh24miss') AS update_time, w.remark, w.col1, w.col2, w.col3, w.order_send_usercode, w.order_send_userphone, w.order_send_username, w.order_priority, to_char(w.order_time_limit, '') AS order_time_limit, (SELECT pname FROM es_dc_public_ext WHERE stype = 'source_from' AND pkey = w.order_from) AS order_from, w.result_remark, decode(w.type, '03', v.phone_owner_name, '') AS customer_name, decode(w.type, '03', p.certi_type, '') AS cert_type, eoi.cert_addr AS cert_addr, decode(w.type, '03', p.cert_card_num, '') AS cert_num, decode(w.type, '03', p.cert_num2, '') AS cert_num2, decode(w.type, '03', v.out_office, '') AS staff_id, decode(w.type, '03', '', '') AS trade_type_code, decode(w.type, '07', oi.referee_name, v.recommended_name) AS referee_name, decode(w.type, '07', oi.referee_num, v.recommended_phone) AS referee_num, to_char(decode(w.type, '07', oi.create_time, e.tid_time), 'yyyymmddhh24miss') AS create_time2, decode(w.type, '07', oi.remark, '') AS remark2, decode(v.develop_code_new, '',eoi.develop_code,v.develop_code_new) AS develop_code, decode(v.develop_name_new, '',eoi.develop_name,v.develop_name_new) AS develop_name, decode(v.develop_point_code_new, '',eoi.develop_point_code,v.develop_point_code_new) AS develop_point_code, decode(v.development_point_name, '',eoi.develop_point_name,v.development_point_name) AS develop_point_name, eoi.acc_nbr, eoi.contract_month, eoi.lhscheme_id, eoi.advancepay, eoi.pre_fee, eoi.classid, eoi.lowcostpro, eoi.timedurpro, dbms_lob.substr(eoi.is_no_modify) AS is_no_modify, decode(oi.mainnumber, '', v.mainnumber, oi.mainnumber) as mainnumber, oi.group_code ,w.terminal_type,w.terminal_name ");
        
		sqlFrom.append(
				"  FROM es_work_order w LEFT JOIN es_order o ON w.order_id = o.order_id LEFT JOIN es_order_items_prod_ext p ON o.order_id = p.order_id LEFT JOIN es_delivery d ON p.order_id = d.order_id LEFT JOIN es_order_ext e ON d.order_id = e.order_id LEFT JOIN es_order_extvtl v ON e.order_id = v.order_id LEFT JOIN es_order_items i ON v.order_id = i.order_id LEFT JOIN es_goods g ON i.goods_id = g.goods_id LEFT JOIN es_order_intent oi ON w.order_id = oi.order_id LEFT JOIN es_order_intent eoi ON w.order_id = eoi.order_id ");
		sqlWhere.append(" where w.source_from = '" + ManagerUtils.getSourceFrom() + "' ");

		String status = requ.getStatus();
		String operator_num = requ.getOperator_num();
		String start_time = requ.getStart_time();
		String end_time = requ.getEnd_time();// yyyymmdd
		String order_id = requ.getOrder_id();
		String msg = "";
		// 工单处理状态 0 — 未处理 1 — 处理成功 2 — 处理失败3已撤单4处理中
		// 0 – 全部；1 – 未处理；2 – 已处理；3-处理中
		if (!StringUtils.isEmpty(status)) {
			if(!"0".equals(status)){
				if ("1".equals(status)) {
					sqlWhere.append(" and w.status = '0'");
				} else if ("2".equals(status)) {
					sqlWhere.append(" and w.status <> '0' and w.status <> '4'");
				} else if ("3".equals(status)) {
					sqlWhere.append(" and w.status = '4'");
				}
			}
		} else {
			msg = msg + "status,";
		}
		// 操作员联系电话
		if (!StringUtils.isEmpty(operator_num)) {
			sqlWhere.append(" and w.operator_num = '" + operator_num + "'");
		} else {
			msg = msg + "operator_num,";
		}
		if (!StringUtils.isEmpty(order_id)) {
			sqlWhere.append(" and w.order_id = '" + order_id + "'");
		}
		if (!StringUtils.isEmpty(start_time)) {
			// update < to_date('20171206000000','yyyymmddhh24miss')
			if (start_time.length() == 8) {
				sqlWhere.append(" and to_char(trunc(w.create_time,'dd'),'yyyymmdd') >='" + start_time + "'");
			} else {
				resp.setResp_code("1");// 1失败；0成功
				resp.setResp_msg("参数异常：start_time");
				return resp;
			}
		} else {
			msg = msg + "start_time,";
		}
		if (!StringUtils.isEmpty(end_time)) {
			if (end_time.length() == 8) {
				sqlWhere.append(" and to_char(trunc(w.create_time,'dd'),'yyyymmdd') <='" + end_time + "'");
			} else {
				resp.setResp_code("1");// 1失败；0成功
				resp.setResp_msg("参数异常：end_time");
				return resp;
			}
		} else {
			msg = msg + "end_time,";
		}
		if (!StringUtils.isEmpty(msg)) {
			resp.setResp_code("1");// 1失败；0成功
			resp.setResp_msg("参数异常：" + msg + "为空");
			return resp;
		}
		sqlOrderBy.append(" order by w.create_time desc ");
		sql.append(sqlSelect).append(sqlFrom).append(sqlWhere).append(sqlOrderBy);
		logger.info("工单列表查询接口:"+sql);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String kg_develop_info = cacheUtil.getConfigInfo("kg_develop_info");
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql.toString());
		if (list.size() > 0) {
			for (Map<String, Object> mapWork : list) {
				// 工单操作员节点developer_info
				String order_send_usercode = mapWork.get("order_send_usercode") + "";
				String order_send_username = mapWork.get("order_send_username") + "";
				String order_send_userphone = mapWork.get("order_send_userphone") + "";
				Map<String, Object> developer_info = new HashMap<String, Object>();
				developer_info.put("order_send_usercode", order_send_usercode);
				developer_info.put("order_send_username", order_send_username);
				developer_info.put("order_send_userphone", order_send_userphone);
				mapWork.put("developer_info", developer_info);
				// 辅助信息work_info
				String customer_name = mapWork.get("customer_name") + "";
				String cert_type = mapWork.get("cert_type") + "";
				String cert_num = mapWork.get("cert_num") + "";
				String cert_num2 = mapWork.get("cert_num2") + "";
				String cert_addr = mapWork.get("cert_addr") + "";
				String staff_id = mapWork.get("staff_id") + "";
				String trade_type_code = mapWork.get("trade_type_code") + "";
				String order_contact_name = mapWork.get("order_contact_name") + "";
				String order_contact_phone = mapWork.get("order_contact_phone") + "";
				String order_contact_addr = mapWork.get("order_contact_addr") + "";
				String order_product_name = mapWork.get("order_product_name") + "";
				String order_amount = mapWork.get("order_amount") + "";
				String goods_cat_id="";
		        goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValueHis(String.valueOf(mapWork.get("connected_system_id")),"goods_cat_id");
		        if("90000000000000901".equals(goods_cat_id)) {
		        	StringBuffer sql_order_amount=new StringBuffer();
		        	sql_order_amount.append("select a.order_amount * 1000 AS order_amount from es_order a where a.order_id='").append(String.valueOf(mapWork.get("connected_system_id"))).append("'");
		        	List<Map<String, Object>> list_amount=baseDaoSupport.queryForList(sql_order_amount.toString());
		        	if(list_amount!=null&&list_amount.size()>0&&list_amount.get(0).containsKey("order_amount")) {
		        		order_amount=String.valueOf(list_amount.get(0).get("order_amount"));
		        	}
		        }
				String referee_name = mapWork.get("referee_name") + "";
				String referee_num = mapWork.get("referee_num") + "";
				String create_time = mapWork.get("create_time2") + "";// 意向单创建时间yyyymmddhh24miss
				String remark = mapWork.get("remark2") + "";// 意向单备注
				
				Map<String, Object> work_info = new HashMap<String, Object>();
				if(!StringUtil.isEmpty(kg_develop_info)&&"1".equals(kg_develop_info)){
					String develop_code = mapWork.get("develop_code") + "";
					String develop_name = mapWork.get("develop_name") + "";
					String develop_point_code = mapWork.get("develop_point_code") + "";
					String develop_point_name = mapWork.get("develop_point_name") + "";
					work_info.put("develop_code", develop_code);
					work_info.put("develop_name", develop_name);
					work_info.put("develop_point_code", develop_point_code);
					work_info.put("develop_point_name", develop_point_name);
					mapWork.remove("develop_code");
					mapWork.remove("develop_name");
					mapWork.remove("develop_point_code");
					mapWork.remove("develop_point_name");
				}
				
				
				work_info.put("customer_name", customer_name);
				work_info.put("cert_type", cert_type);
				work_info.put("cert_num", cert_num);
				work_info.put("cert_num2", cert_num2);
				work_info.put("cert_addr",cert_addr);
				work_info.put("staff_id", staff_id);
				work_info.put("trade_type_code", trade_type_code);
				work_info.put("order_contact_name", order_contact_name);
				work_info.put("order_contact_phone", order_contact_phone);
				work_info.put("order_contact_addr", order_contact_addr);
				work_info.put("order_product_name", order_product_name);
				work_info.put("order_amount", order_amount);// 单位厘
				work_info.put("referee_name", referee_name);
				work_info.put("referee_num", referee_num);
				work_info.put("create_time", create_time);
				work_info.put("remark", remark);
				work_info.put("terminal_type", mapWork.get("terminal_type").toString());
				work_info.put("terminal_name", mapWork.get("terminal_name").toString());
				mapWork.put("work_info", work_info);
				
				/*
				 * add by wcl phone_info
				 */
				Map<String,Object>phone_info = new HashMap<String,Object>();
				String contractMonth = MapUtils.getString(mapWork, "contract_month");
				String accNbr = MapUtils.getString(mapWork, "acc_nbr");
				phone_info.put("acc_nbr", accNbr);
				phone_info.put("contract_month", contractMonth);
				Map<String,Object>nice_info = new HashMap<String,Object>();
				String lhschemeId = MapUtils.getString(mapWork, "lhscheme_id");
				String preFee = MapUtils.getString(mapWork, "pre_fee");
				String classId = MapUtils.getString(mapWork, "classid");
				String advancePay = MapUtils.getString(mapWork, "advancepay");
				String lowcostpro = MapUtils.getString(mapWork, "lowcostpro");
				String timedurpro = MapUtils.getString(mapWork, "timedurpro");
				String mainNumber = MapUtils.getString(mapWork, "mainnumber");// 主卡号码
				nice_info.put("lhscheme_id", lhschemeId); 
				nice_info.put("pre_fee", preFee); 
				nice_info.put("advancePay", advancePay);
				nice_info.put("classId",classId);
				nice_info.put("lowCostPro", lowcostpro);
				nice_info.put("timeDurPro", timedurpro);
				phone_info.put("nice_info", nice_info);
				phone_info.put("mainNumber", mainNumber);
				mapWork.put("phone_info",phone_info);
				
				
				
				mapWork.remove("order_send_usercode");
				mapWork.remove("order_send_username");
                mapWork.remove("cert_num2");
				mapWork.remove("order_send_userphone");
				mapWork.remove("order_contact_name");
				mapWork.remove("order_contact_phone");
				mapWork.remove("order_contact_addr");
				mapWork.remove("order_product_name");
				mapWork.remove("order_amount");
				mapWork.remove("create_time2");
				mapWork.remove("update_time");
				mapWork.remove("col1");
				mapWork.remove("col2");
				mapWork.remove("col3");
				mapWork.remove("ship_name");
				mapWork.remove("ship_tel");
				mapWork.remove("ship_addr");
				mapWork.remove("goods_name");
				mapWork.remove("real_offer_price");
				mapWork.remove("referee_name");
				mapWork.remove("referee_num");
				mapWork.remove("create_time");
				mapWork.remove("remark2");
				mapWork.remove("customer_name");
				mapWork.remove("cert_type");
				mapWork.remove("cert_num");
				mapWork.remove("cert_addr");
				mapWork.remove("staff_id");
				mapWork.remove("trade_type_code");
				
				mapWork.remove("contract_month");
				mapWork.remove("acc_nbr");
				mapWork.remove("lhscheme_id");
				mapWork.remove("pre_fee");
				mapWork.remove("classid");
				mapWork.remove("advancepay");
				mapWork.remove("lowcostpro");
				mapWork.remove("timedurpro");
				mapWork.remove("mainNumber");
				mapWork.remove("terminal_type");
				mapWork.remove("terminal_name");
			}
		}

		resp.setResp_code("0");// 1失败；0成功
		resp.setResp_msg("查询成功");
		resp.setWork_order_list(list);
		return resp;
	};

	/**
	 * 工单状态同步接口
	 * 
	 * @author 宋琪
	 * @throws Exception 
	 * @date 2017年12月1日
	 */
	public WorkOrderUpdateResp updateWorkOrderUpdateByRequ(WorkOrderUpdateReq requ) throws Exception {
		WorkOrderUpdateResp resp = new WorkOrderUpdateResp();
		resp.setResp_code("1");// 失敗标记
		String work_order_id = requ.getWork_order_id();
		String field_survey_result = requ.getField_survey_result();// 0 失败；1 成功
		String remark = requ.getRemark();
		if (StringUtils.isEmpty(work_order_id)) {
			resp.setResp_msg("更新失败:work_order_id为空");
			return resp;
		}
		if (StringUtils.isEmpty(field_survey_result)) {
			resp.setResp_msg("更新失败:field_survey_result为空");
			return resp;
		}
		OrderWorksBusiRequest work = ordWorkManager.queryWorkByWorkOrderId(work_order_id);
		if (null == work) {
			resp.setResp_msg("更新失败:work_order_id异常");
			return resp;
		}
		// 0 — 未处理 1 — 处理成功 2 — 处理失败
		if ("1".equals(work.getStatus())) {
			resp.setResp_msg("更新失败:处理成功的工单不允许再次更新");
			return resp;
		}
		String order_id = work.getOrder_id();
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		fieldsMap.put("update_time", new Date());
		JSONObject jsonObject = JSONObject.fromObject(requ);
		fieldsMap.put("req_xml", jsonObject.toString());
		// 操作员节点信息
		WorkDeveloperInfo developer_info = requ.getDeveloper_info();
		if (null != developer_info) {
			String deal_office_id = developer_info.getDeal_office_id();// 操作点
			String deal_operator = developer_info.getDeal_operator();// 操作员编码
			String deal_operator_name = developer_info.getDeal_operator_name();// 操作员姓名
			String deal_operator_num = developer_info.getDeal_operator_num();// 操作员号码
			if (!StringUtils.isEmpty(deal_office_id)) {
				fieldsMap.put("operator_office_id", deal_office_id);
			}
			if (!StringUtils.isEmpty(deal_operator)) {
				fieldsMap.put("operator_id", deal_operator);
			}
			if (!StringUtils.isEmpty(deal_operator_name)) {
				fieldsMap.put("operator_name", deal_operator_name);
			}
			if (!StringUtils.isEmpty(deal_operator_num)) {
				fieldsMap.put("operator_num", deal_operator_num);
			}
			String develop_point_code = developer_info.getDevelop_point_code();// 发展点编码
			String develop_point_name = developer_info.getDevelop_point_name();// 发展点名称
			String develop_code = developer_info.getDevelop_code();// 发展人编码
			String develop_name = developer_info.getDevelop_name();// 发展人姓名
			String referee_num = developer_info.getReferee_num();// 推荐人号码/推荐人号码（联系方式）
			String referee_name = developer_info.getReferee_name();// 推荐人名称
			String county_id = developer_info.getCounty_id();// 营业县分编码--根据业务具体确认county_id
			// 渠道类型01：营业渠道02：行销渠道03：代理渠道 用于支付是判断发起方
			String deal_office_type = developer_info.getDeal_office_type();
			String channelType = developer_info.getChannelType();
			String[] names = new String[30];
			String[] values = new String[30];
			int i = 0;
			if (!StringUtil.isEmpty(develop_point_code)) {
				names[i] = "development_point_code";
				values[i] = develop_point_code;
				i++;
			}
			if (!StringUtil.isEmpty(develop_point_name)) {
				names[i] = "development_point_name";
				values[i] = develop_point_name;
				i++;
			}
			if (!StringUtil.isEmpty(develop_code)) {
				names[i] = "development_code";
				values[i] = develop_code;
				i++;
			}
			if (!StringUtil.isEmpty(develop_code)) {
				names[i] = "development_name";
				values[i] = develop_name;
				i++;
			}
			if (!StringUtil.isEmpty(referee_num)) {
				names[i] = "recommended_phone";
				values[i] = referee_num;
				i++;
			}
			if (!StringUtil.isEmpty(referee_name)) {
				names[i] = "recommended_name";
				values[i] = referee_name;
				i++;
			}
			if (!StringUtil.isEmpty(county_id)) {
				names[i] = "county_id";
				values[i] = county_id;
				i++;
			}
			if (!StringUtil.isEmpty(deal_office_type)) {
				names[i] = "spread_channel";
				values[i] = deal_office_type;
				i++;
			}
			if (!StringUtils.isEmpty(channelType)) {
				names[i] = AttrConsts.CHANNEL_TYPE;
				values[i] = channelType;
				i++;
			}

			if (i > 0) {
				String[] name = new String[i];
				String[] value = new String[i];
				for (int j = 0; j < i; j++) {
					name[j] = names[j];
					value[j] = values[j];
				}
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderTree.store();
			}
		}
		// 实名单节点工单类型01 – 收费单；02 -- 外勘单；03 – 实名单；04 – 挽留单；05 – 写卡单
		if ("03".equals(work.getType()) && "1".equals(field_survey_result)) {
			CustInfo custInfo = requ.getCust_info();
			if (null != custInfo) {
				String is_real_name = custInfo.getIs_real_name();// 0–未实名；1–已实名。
				String customer_name = custInfo.getCustomer_name();// 客户姓名
				String cert_type = custInfo.getCert_type();// 详见附录证件类型
				String cert_num = custInfo.getCert_num();// 证件号码
				String cert_addr = custInfo.getCert_addr();// 证件地址
				String cert_nation = custInfo.getCert_nation();// 名族
				String cert_sex = custInfo.getCert_sex();// 性别，固定长度1位， M：男， F：女
				String cust_birthday = custInfo.getCust_birthday();// 客户生日
				String cert_issuedat = custInfo.getCert_issuedat();// 签发机关
				String cert_expire_date = custInfo.getCert_expire_date();// 证件失效时间yyyymmdd
				String cert_effected_date = custInfo.getCert_effected_date();// 证件生效时间yyyymmdd
				String cust_tel = custInfo.getCust_tel();// 客户电话
				String customer_type = custInfo.getCustomer_type();// 客户类型100：个人客户(联通)，不传时默认值
				// 50：个人企业客户（小微）
				String cust_id = custInfo.getCust_id();// 客户编号 老客户传BSS系统客户ID
				String group_code = custInfo.getGroup_code();// 收入归集集团 集团15位编码
				String req_swift_num = custInfo.getReq_swift_num();// 实名制拍照流水
				if (StringUtils.isEmpty(is_real_name) || StringUtils.isEmpty(customer_name) || StringUtils.isEmpty(cert_type) || StringUtils.isEmpty(cert_num) || StringUtils.isEmpty(cert_addr)
						|| StringUtils.isEmpty(cert_nation) || StringUtils.isEmpty(cert_sex) || StringUtils.isEmpty(cust_birthday) || StringUtils.isEmpty(cert_issuedat)
						|| StringUtils.isEmpty(cert_expire_date) || StringUtils.isEmpty(cert_effected_date)) {
					resp.setResp_msg("实名单必传节点异常");
					return resp;
				}
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				String[] names = new String[30];
				String[] values = new String[30];
				int i = 0;
				if (!StringUtils.isEmpty(is_real_name)) {
					names[i] = "is_real_name";
					values[i] = is_real_name;
					i++;
				}
				if (!StringUtils.isEmpty(customer_name)) {
					names[i] = AttrConsts.CERT_CARD_NAME;
					values[i] = customer_name;
					i++;
				}
				// 宽带预提交取值在订单树
				OrderItemProdExtBusiRequest orderItemProdExt = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest();
				if (!StringUtils.isEmpty(cert_type)) {
					// names[i] = AttrConsts.CERTI_TYPE;
					// values[i] = cert_type;
					// i++;
					orderItemProdExt.setCerti_type(cert_type);
				}
				if (!StringUtils.isEmpty(cert_num)) {
					names[i] = AttrConsts.CERT_CARD_NUM;
					values[i] = cert_num;
					i++;
					orderItemProdExt.setCert_card_num(cert_num);
				}
				if (!StringUtils.isEmpty(cert_addr)) {
					names[i] = AttrConsts.CERT_ADDRESS;
					values[i] = cert_addr;
					i++;
					orderItemProdExt.setCert_address(cert_addr);
				}
				if (!StringUtils.isEmpty(cert_nation)) {
					names[i] = AttrConsts.CERT_CARD_NATION;
					values[i] = cert_nation;
					i++;
					orderItemProdExt.setCert_card_nation(cert_nation);
				}
				if (!StringUtils.isEmpty(cert_sex)) {
					names[i] = AttrConsts.CERTI_SEX;
					values[i] = cert_sex;
					i++;
					orderItemProdExt.setCert_card_sex(cert_sex);
				}
				if (!StringUtils.isEmpty(cust_birthday)) {
					names[i] = AttrConsts.CERT_CARD_BIRTH;
					values[i] = cust_birthday;
					i++;
				}
				if (!StringUtils.isEmpty(cert_issuedat)) {
					names[i] = AttrConsts.CERT_ISSUER;
					values[i] = cert_issuedat;
					i++;
				}

				if (!StringUtils.isEmpty(cert_expire_date)) {
					// names[i] = AttrConsts.CERT_FAILURE_TIME;
					// values[i] = cert_expire_date;
					// i++;
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
					java.util.Date tDate = dateFormator.parse(cert_expire_date + "000000", new ParsePosition(0));
					dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
					orderItemProdExt.setCert_failure_time(format.format(tDate));
					// cert_failure_time
					CommonDataFactory.getInstance().updateOrderTree(order_id, AttrConsts.CERT_FAILURE_TIME);
				}
				orderItemProdExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemProdExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemProdExt.store();
				if (!StringUtils.isEmpty(cert_effected_date)) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
					java.util.Date tDate = dateFormator.parse(cert_effected_date + "000000", new ParsePosition(0));
					dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
					names[i] = AttrConsts.CERT_EFF_TIME;
					values[i] = format.format(tDate);
					i++;
				}
				if (!StringUtils.isEmpty(cust_tel)) {
					// names[i] = "ship_mobile";
					// values[i] = cust_tel;
					// i++;
					OrderDeliveryBusiRequest orderDelivery = orderTree.getOrderDeliveryBusiRequests().get(0);
					orderDelivery.setShip_mobile(cust_tel);
					orderDelivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderDelivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderDelivery.store();
				}
				if (!StringUtils.isEmpty(customer_type)) {
					customer_type = "100";// 默认
				}
				if (!StringUtils.isEmpty(customer_type)) {
					names[i] = AttrConsts.CUSTOMER_TYPE;
					values[i] = customer_type;
					i++;
				}
				if (!StringUtils.isEmpty(cust_id)) {
					names[i] = AttrConsts.CUST_ID;
					values[i] = cust_id;
					i++;
				}
				if (!StringUtils.isEmpty(group_code)) {
					names[i] = AttrConsts.GROUP_CODE;
					values[i] = group_code;
					i++;
				}

				if (i > 0) {
					String[] name = new String[i];
					String[] value = new String[i];
					for (int j = 0; j < i; j++) {
						name[j] = names[j];
						value[j] = values[j];
					}
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);
				}
				if (!StringUtils.isEmpty(req_swift_num)) {
					List<OrderAdslBusiRequest> orderAdslBusiRequest = orderTree.getOrderAdslBusiRequest();
					for (OrderAdslBusiRequest adsl : orderAdslBusiRequest) {
						adsl.setReq_swift_num(req_swift_num);
						adsl.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						adsl.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						adsl.store();
					}
				}
				orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderTree.store();

				String flag = orderUpdate(order_id);
				if (!"0".equals(flag)) {
					resp.setResp_msg("更新失败:" + flag);
					return resp;
				}
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_REAL_NAME }, new String[] { "1" });
				CommonDataFactory.getInstance().updateOrderTree(order_id, AttrConsts.IS_REAL_NAME);
			} else {
				resp.setResp_msg("更新失败:cust_info为空");
				return resp;
			}
		}

		// 0 失败；1 成功
		// 0 — 未处理 1 — 处理成功 2 — 处理失败
		if ("0".equals(field_survey_result)) {
			fieldsMap.put("status", 2);
		} else if ("1".equals(field_survey_result)) {
			fieldsMap.put("status", 1);
		}
		if (!StringUtils.isEmpty(remark)) {
			fieldsMap.put("result_remark", remark);
		}
		
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("work_order_id", work_order_id);
		daoSupport.update("es_work_order", fieldsMap, whereMap);
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String types = cacheUtil.getConfigInfo("TYPE_INTENT_HANDLE");
		if(!StringUtils.isEmpty(types)){
			//意向单工单反馈，修改到意向单操作记录
			if (types.contains(work.getType())) {
				fieldsMap.clear();
				fieldsMap.put("work_result_remark", remark);
				fieldsMap.put("work_result_time", new Date());
				whereMap.clear();
				whereMap.put("order_id", order_id);
				whereMap.put("work_order_id", work_order_id);
				daoSupport.update("es_intent_handle_logs", fieldsMap, whereMap);
			}
		}
		
		StringBuffer sql_cfg=new StringBuffer();
		sql_cfg.append("select distinct a.cfg_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(work.getOrder_id()).append("' ");
		String cfg_id=this.daoSupport.queryForString(sql_cfg.toString());
		cfg_id = cfg_id==null ? "" : cfg_id;
		String cfg_ids=cacheUtil.getConfigInfo("work_order_update_flow");
		String[] strs=cfg_ids.split(",");
        List list=Arrays.asList(strs);
		if(org.apache.commons.lang.StringUtils.isNotBlank(work.getNode_ins_id())
				&& "1".equals(field_survey_result)){
			//自定义流程的意向单，反馈成功的，修改意向单状态为完成
			String info = requ.getWork_order_id()+"|"+requ.getField_survey_result()+"|"+requ.getRemark();
			
			Map<String, Object> setMapIntent = new HashMap<String, Object>();
            setMapIntent.put("status","03");
            setMapIntent.put("remark",info);
            
            Map<String, Object> whileMapIntent = new HashMap<String, Object>();
            whileMapIntent.put("order_id",order_id);
            
            daoSupport.update("es_order_intent", setMapIntent,whileMapIntent);
            String json_param="";
            
            if(list.contains(cfg_id)) {
            	json_param="WORK_UPDATE_SUCCESS";
            	this.workCustomEngine.runNodeManualByCode(order_id, "update_result", true, "", "",json_param);
            }else {
            	//执行自定义流程
                this.workCustomEngine.runNodeManual(work.getNode_ins_id(), true, null, info,json_param);
            }
            
		}
		
		if(StringUtil.equals("0", field_survey_result)&&list.contains(cfg_id)) {
			String info = requ.getWork_order_id()+"|"+requ.getField_survey_result()+"|"+requ.getRemark();
			//意向单状态翻转
			Map<String, Object> setMapIntent = new HashMap<String, Object>();
            setMapIntent.put("status","00");
            setMapIntent.put("remark",info);
            Map<String, Object> whileMapIntent = new HashMap<String, Object>();
            whileMapIntent.put("order_id",order_id);
            daoSupport.update("es_order_intent", setMapIntent,whileMapIntent);
            //进入退单流程
            Map map_param = new HashMap();
            if(StringUtil.isEmpty(work.getRemark())) {
            	map_param.put("dealDesc","退单");
            }else {
            	map_param.put("dealDesc", work.getRemark());
            }
    		map_param.put("returnedReasonCode", "");
    		String json_param = JSON.toJSONString(map_param);

    		this.workCustomEngine.gotoNode(order_id, "judge_pay", "");
    		WORK_CUSTOM_FLOW_DATA flow_data = this.workCustomEngine.runNodeManualByCode(order_id, 
    				"judge_pay", true, "", "",json_param);
    		if(ConstsCore.ERROR_FAIL.equals(flow_data.getRun_result())) {
    			resp.setResp_msg("更新失败，"+flow_data.getRun_msg());
        		return resp;
    		}
		}
		
		//sgf
        try {
          Boolean falg = ordWorkManager.isKDYQ(order_id, null);
          if( !falg  && "0".equals(field_survey_result) ){
              fieldsMap.clear();
              fieldsMap.put("order_state", 6);//受理失败
              whereMap.clear();
              whereMap.put("order_id", order_id);
              daoSupport.update("es_order", fieldsMap, whereMap);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
		resp.setResp_code("0");// 成功标记
		resp.setResp_msg("更新成功");
		return resp;
	};

	public String orderUpdate(String order_id) {
		String flag = "0";
		String rule_id = "";
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String RULE_ID_KD = cacheUtil.getConfigInfo("RULE_ID_KD");// 180092116412000052
		String RULE_ID_RH = cacheUtil.getConfigInfo("RULE_ID_RH");// 180092117382000069
		String RULE_ID_WK = cacheUtil.getConfigInfo("RULE_ID_WK");// 180421913012001112
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if ("20021".equals(goods_type)) {
			rule_id = RULE_ID_KD;
		} else if ("170502112412000711".equals(goods_type)) {
			rule_id = RULE_ID_RH;
		}else if("180441456282001431".equals(goods_type)){
			rule_id = RULE_ID_WK;
		}
		RuleTreeExeReq requ = new RuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(order_id);
		requ.setRule_id(rule_id);
		requ.setFact(fact);
		requ.setCheckAllRelyOnRule(true);
		requ.setCheckCurrRelyOnRule(true);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		RuleTreeExeResp rsp = client.execute(requ, RuleTreeExeResp.class);
		if (!"0".equals(rsp.getError_code())) {
			flag = rsp.getError_code() + rsp.getError_msg();
		}
		return flag;
	}

	/**
	 * 收费单同步接口
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	public PayWorkOrderUpdateResp updatePayWorkOrderUpdateByRequ(PayWorkOrderUpdateReq requ) {

		PayWorkOrderUpdateResp resp = new PayWorkOrderUpdateResp();
		resp.setResp_code("1");
		String msg = "";// 验证支付信息
		if (StringUtils.isEmpty(requ.getWork_order_id())) {
			msg = msg + "work_order_id ";
		}
		if (StringUtils.isEmpty(requ.getPay_result())) {
			msg = msg + "pay_result ";// 支付结果 0：支付成功 -1：支付失败
		}
		// 可以不校验,保持收单不变
		// if (StringUtils.isEmpty(requ.getPay_type())) {
		// msg = msg + "pay_type ";// 支付类型
		// }
		if (StringUtils.isEmpty(requ.getPay_method())) {
			msg = msg + "pay_method ";// 支付方式
		}
		if (!StringUtils.isEmpty(msg)) {
			msg = "更新失败，参数" + msg + "为空";
			resp.setResp_msg(msg);
		}
		OrderWorksBusiRequest work = ordWorkManager.queryWorkByWorkOrderId(requ.getWork_order_id());
		if (null == work) {
			resp.setResp_msg("收费单更新失败:work_order_id异常");
			return resp;
		}
		if ("1".equals(work.getStatus())) {// 0未处理 1处理成功 2处理失败
			resp.setResp_msg("更新失败:处理成功的工单不允许再次更新");
			return resp;
		}

		String order_id = requ.getConnected_system_id();
		OrderTreeBusiRequest orderTree = null;
		if (StringUtils.isEmpty(order_id)) {
			order_id = work.getOrder_id();
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		} else {
			orderTree = CommonDataFactory.getInstance().getOrderTree(requ.getConnected_system_id());
			if (null == orderTree) {
				resp.setResp_msg("处理失败：connected_system_id异常");
				return resp;
			}
		}
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		fieldsMap.put("update_time", new Date());
		JSONObject jsonObject = JSONObject.fromObject(requ);
		fieldsMap.put("req_xml", jsonObject.toString());
		// 订单支付状态更新0：支付成功-1：支付失败
		// 0 — 未处理 1 — 处理成功 2 — 处理失败
		if ("0".equals(requ.getPay_result())) {
			fieldsMap.put("status", 1);
		} else if ("-1".equals(requ.getPay_result())) {
			fieldsMap.put("status", 2);
		} else {
			resp.setResp_msg("收费单更新:pay_result异常");
			return resp;
		}
		if (!StringUtils.isEmpty(requ.getRemark())) {// 工单反馈信息
			fieldsMap.put("result_remark", requ.getRemark());
		}
		Map<String, Object> whereMap = new HashMap<String, Object>();
		whereMap.put("work_order_id", requ.getWork_order_id());
		// daoSupport.update("es_work_order", fieldsMap, whereMap);
		if ("0".equals(requ.getPay_result())) {
			DeveloperInfo developer_info = requ.getDeveloper_info();
			String[] names = new String[30];
			String[] values = new String[30];
			int i = 0;
			// 发展点编码
			if (!StringUtils.isEmpty(developer_info.getDevelop_point_code())) {
				names[i] = "development_point_code";
				values[i] = developer_info.getDevelop_point_code();
				i++;
			}
			// 发展点名称
			if (!StringUtils.isEmpty(developer_info.getDevelop_point_name())) {
				names[i] = "development_point_name";
				values[i] = developer_info.getDevelop_point_name();
				i++;
			}
			// 发展人编码
			if (!StringUtils.isEmpty(developer_info.getDevelop_code())) {
				names[i] = AttrConsts.DEVELOPMENT_CODE;
				values[i] = developer_info.getDevelop_code();
				i++;
			}
			// 发展人姓名
			if (!StringUtils.isEmpty(developer_info.getDevelop_name())) {
				names[i] = AttrConsts.DEVELOPMENT_NAME;
				values[i] = developer_info.getDevelop_name();
				i++;
			}
			// 推荐人号码
			if (!StringUtils.isEmpty(developer_info.getReferee_num())) {
				names[i] = AttrConsts.RECOMMENDED_PHONE;
				values[i] = developer_info.getReferee_num();
				i++;
			}
			// 推荐人名称
			if (!StringUtils.isEmpty(developer_info.getReferee_name())) {
				names[i] = AttrConsts.RECOMMENDED_NAME;
				values[i] = developer_info.getReferee_name();
				i++;
			}
			// 营业县分编码
			if (orderTree.getOrderAdslBusiRequest().size() > 0 && !StringUtil.isEmpty(developer_info.getCounty_id())) {
				for (OrderAdslBusiRequest adsl : orderTree.getOrderAdslBusiRequest()) {
					adsl.setCounty_id(developer_info.getCounty_id());
					adsl.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					adsl.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					adsl.store();
				}
			}
			// 渠道类型1
			if (!StringUtils.isEmpty(developer_info.getDeal_office_type())) {
				// names[i] = "spread_channel";
				// values[i] = developer_info.getDeal_office_type();
				// i++;
				OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
				orderExt.setSpread_channel(developer_info.getDeal_office_type());
				orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderExt.store();
			}
			// 渠道分类
			if (!StringUtils.isEmpty(developer_info.getChannelType())) {
				names[i] = AttrConsts.CHANNEL_TYPE;
				values[i] = developer_info.getChannelType();
				i++;
			}
			// 操作点
			if (!StringUtils.isEmpty(developer_info.getDeal_office_id())) {
				names[i] = AttrConsts.OUT_OFFICE;
				values[i] = developer_info.getDeal_office_id();
				i++;
				String out_office = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
				if (!StringUtils.isEmpty(out_office)) {
					names[i] = AttrConsts.OLD_OUT_OFFICE;
					values[i] = out_office;
					i++;
				}
			}
			// 操作员编码
			if (!StringUtils.isEmpty(developer_info.getDeal_operator())) {
				names[i] = AttrConsts.OUT_OPERATOR;
				values[i] = developer_info.getDeal_operator();
				i++;
				String out_operator = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
				if (!StringUtils.isEmpty(out_operator)) {
					names[i] = AttrConsts.OLD_OUT_OPERATOR;
					values[i] = out_operator;
					i++;
				}
			}
			// 操作员姓名
			// if
			// (!StringUtils.isEmpty(developer_info.getDeal_operator_name()))
			// {
			// names[i] = "";
			// values[i] = developer_info.getDeal_operator_name();
			// i++;
			// }
			// 操作员号码
			// if
			// (!StringUtils.isEmpty(developer_info.getDeal_operator_num()))
			// {
			// names[i] = "";
			// values[i] = developer_info.getDeal_operator_num();
			// i++;
			// }
			if (i > 0) {
				String[] name = new String[i];
				String[] value = new String[i];
				for (int j = 0; j < i; j++) {
					name[j] = names[j];
					value[j] = values[j];
				}
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);
			}
			orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderTree.store();

			// 保存支付信息-订单流转
			Map<String, String> pay_info = new HashMap<String, String>();
			pay_info.put("order_id", order_id);
			pay_info.put("pay_result", requ.getPay_result());
			pay_info.put("pay_sequ", requ.getPay_sequ());
			pay_info.put("pay_back_sequ", requ.getPay_back_sequ());
			// 更新的时候不修改，保持收单值
			// pay_info.put("pay_type", requ.getPay_type());
			pay_info.put("pay_method", requ.getPay_method());

			String resp_msg = updateStatus(pay_info);
			if ("0".equals(resp_msg)) {
				fieldsMap.put("status", 1);
				resp.setResp_code("0");
				resp.setResp_msg("收费单更新成功");
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_PAY }, new String[] { "1" });
				CommonDataFactory.getInstance().updateOrderTree(order_id, AttrConsts.IS_PAY);
			} else {
				fieldsMap.put("status", 2);
				resp.setResp_msg("收费单更新失败:" + resp_msg);
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String status_back = cacheUtil.getConfigInfo("IS_STATUS_BACK");
				if(!StringUtil.isEmpty(status_back)){
					fieldsMap.put("status", 0);
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderBusiRequest OrderBusi = orderTree.getOrderBusiRequest();
					OrderBusi.setPay_status(0);
					String Pay_Plan_id = cacheUtil.getConfigInfo("PAY_RULE_ID");
					String sql ="update es_order set pay_status=0 where order_id=?";
					String del_sql = "delete from es_rule_exe_log where obj_id='"+order_id+"' and plan_id='"+Pay_Plan_id+"'";
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					baseDaoSupport.execute(sql, order_id);
					baseDaoSupport.execute(del_sql, null);
					//更新订单树缓存
					INetCache cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
					cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, RequestStoreManager.time);
				}
			}
			daoSupport.update("es_work_order", fieldsMap, whereMap);
		} else if ("-1".equals(requ.getPay_result())) {
			resp.setResp_code("0");
			resp.setResp_msg("收费单更新成功");
			daoSupport.update("es_work_order", fieldsMap, whereMap);
		}
		return resp;
	}

	/**
	 * 保存支付信息-订单流转 song.qi 2017年12月7日
	 * 
	 * @return
	 */
	public String updateStatus(Map<String, String> pay_info) {
		String order_id = pay_info.get("order_id");
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// String order_from = tree.getOrderExtBusiRequest().getOrder_from();
		// String goods_type =
		// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
		// AttrConsts.GOODS_TYPE);
		// 设置参数
		OrderInfoUpdateReq request = new OrderInfoUpdateReq();
		request.setOrder_id(order_id);
		request.setOut_order_id(tree.getOrderExtBusiRequest().getOut_tid());
		request.setPay_result(pay_info.get("pay_result"));
		request.setPay_method(pay_info.get("pay_method"));
		request.setPay_sequ(pay_info.get("pay_sequ"));
		request.setPay_back_sequ(pay_info.get("pay_back_sequ"));
		// request.setIs_pay(pay_info.get("pay_result"));
		// request.setIs_bss(pay_info.get("pay_result"));
		// request.setDeal_operator(pay_info.get("pay_result"));
		// request.setDeal_office_id(pay_info.get("pay_result"));
		// request.setCbss_order_id(pay_info.get("pay_result"));
		// request.setBss_order_id(pay_info.get("pay_result"));
		String flag = "1";
		try {
			JSONObject jsonObj = JSONObject.fromObject(request);
			String jsoStr = jsonObj.toString();
			String batch_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_BATCH);
			if (StringUtil.isEmpty(batch_id)) {
				batch_id = "1";
				for (int i = 0; i < 17; i++) {
					batch_id += (int) (Math.random() * 10);
				}
			}
			CoQueueAddReq req = new CoQueueAddReq();
			req.setCo_name("支付结果通知订单中心");
			req.setService_code("CO_PAY_RESULT_BD");
			req.setAction_code("A");
			req.setObject_type("DINGDAN");
			req.setContents(jsoStr);
			req.setObject_id(order_id);
			req.setBatch_id(batch_id);
			// 信息写入es_co_queue
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			CoQueueAddResp coresp = client.execute(req, CoQueueAddResp.class);
			IOrderExtManager orderExtManager = SpringContextHolder.getBean("orderExtManager");
			// 返回 BSS订单收费 规则执行结果
			flag = orderExtManager.updateStatus(order_id, "CO_PAY_RESULT_BD");
		} catch (Exception e) {
			e.printStackTrace();
			flag = e.getMessage();
		}
		return flag;
	}

	/**
	 * 根据 查询订单列表
	 * 
	 * @param requ
	 * @return
	 * @author 宋琪
	 * @throws Exception 
	 * @date 2017年6月1日
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public OrderInfoListQueryResp queryOrderInfoList(OrderInfoListQueryReq requ) throws Exception {
		OrderInfoListQueryResp resp = new OrderInfoListQueryResp();
		List<OrderListQueryResp> query_resp = new ArrayList<OrderListQueryResp>();
		resp.setQuery_resp(query_resp);
		// 查询的可选参数
		OrderListQueryInfo query_info = requ.getQuery_info();
		if (query_info == null) {
			resp.setPage_total("0");
			resp.setResp_code("1");
			resp.setResp_msg("查询失败:参数query_info为空");
			return resp;
		}
		StringBuffer sqlSb = new StringBuffer();
		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlFrom = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		// 订单中心订单状态自己判断
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String select_order_info_list = cacheUtil.getConfigInfo("select_order_info_list");
		String from_order_info_list = cacheUtil.getConfigInfo("from_order_info_list");
		String is_select_order_info_list = cacheUtil.getConfigInfo("is_select_order_info_list");
		if ("1".equals(is_select_order_info_list)) {
			sqlSelect.append(select_order_info_list);
			sqlFrom.append(from_order_info_list);
		}else{
			sqlSelect.append("select distinct a.order_id, a.out_tid, c.phone_num, d.pay_status, a.sf_status, e.active_flag,"
					+" to_char(a.tid_time, 'yyyymmddhh24miss') as tid_time, b.GoodsName, d.paymoney, f.pay_method, b.bss_refund_status,"
					+" a.abnormal_status, a.refund_status, a.flow_trace_id, a.refund_deal_type, nvl(b.script_seq, '') scriptseq, nvl(b.proc_id, '') proc_id,"
					+" nvl(b.simid, '') imsi, nvl(b.iccid, '') icc_id, nvl(b.phone_owner_name, '') cust_name,"
					+" (case when c.bssorderid is not null then c.bssorderid when c.active_no is not null then c.active_no else '' end) bss_order_id,"
					+" nvl(d.goods_id, '') goods_id, c.goods_cat_id goods_cat_id, nvl(prod_ext.cert_failure_time, '') cert_end_date,"
					+" nvl(prod_ext.cert_address, '') cert_addr, nvl(prod_ext.cert_card_num, '') cert_code, nvl(delivery.ship_tel, '') contact_phone,"
					+" nvl(delivery.ship_email, '') email, nvl(b.couponbatchid, '') req_swift_num, (case when phone.classid is null then 'no' when phone.classid = 9 then 'no' else 'yes' end) is_nice_num, "
					+"  delivery.region,delivery.city, delivery.province, delivery.ship_addr, a.refund_desc,elc.name as shipping_company, delivery.logi_no, b.short_url ");
			sqlFrom.append(" from es_order_ext a left join es_order_extvtl b on a.order_id = b.order_id left join es_order_items_ext c on b.order_id = c.order_id"
					+" left join es_order d on c.order_id = d.order_id left join es_order_realname_info e on d.order_id = e.order_id"
					+" left join es_payment_logs f on e.order_id = f.order_id left join es_order_items_prod_ext prod_ext on a.order_id = prod_ext.order_id"
					+" left join es_delivery delivery on a.order_id = delivery.order_id "
					+" left join es_order_phone_info phone on a.order_id = phone.order_id "
					+ "left join es_logi_company elc on elc.id = delivery.shipping_company ");
		}
		
		sqlWhere.append(" where a.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
		// 订单开始时间
		if (query_info.getOrder_date_begin() != null && !"".equals(query_info.getOrder_date_begin())) {
			sqlWhere.append(" and a.tid_time >= to_date('" + query_info.getOrder_date_begin() + "','yyyymmddhh24miss') ");
		}
		// 订单结束时间
		if (query_info.getOrder_date_end() != null && !"".equals(query_info.getOrder_date_end())) {
			sqlWhere.append(" and a.tid_time <= to_date('" + query_info.getOrder_date_end() + "','yyyymmddhh24miss') ");
		}
		// 订单中心单号
		if (query_info.getOrder_id() != null && !"".equals(query_info.getOrder_id())) {
			sqlWhere.append(" and a.order_id = '" + query_info.getOrder_id() + "' ");
		}
		//种子对应手机号码
		if (query_info.getShare_svc_num() != null && !"".equals(query_info.getShare_svc_num())) {
			sqlWhere.append(" and b.share_svc_num = '" + query_info.getShare_svc_num() + "' ");
			sqlWhere.append(" and b.share_svc_num is not null ");
		}
		//种子用户id
		if (query_info.getMarket_user_id() != null && !"".equals(query_info.getMarket_user_id())) {
			sqlWhere.append(" and b.market_user_id = '" + query_info.getMarket_user_id() + "' ");
			sqlWhere.append(" and b.market_user_id is not null ");
		}
		//商品id
		if (query_info.getGoods_id() != null && !"".equals(query_info.getGoods_id())) {
			sqlWhere.append(" and d.goods_id = '" + query_info.getGoods_id() + "' ");
		}
		//联系电话
		if (query_info.getShip_mobile() != null && !"".equals(query_info.getShip_mobile())) {
			sqlWhere.append(" and (delivery.ship_tel = '" + query_info.getShip_mobile() + "' or delivery.ship_mobile= '" + query_info.getShip_mobile() + "')  ");
		}
		//证件号码
		if (query_info.getCert_card_num() != null && !"".equals(query_info.getCert_card_num())) {
			sqlWhere.append(" and prod_ext.cert_card_num= '" + query_info.getCert_card_num() + "' ");
		}
		//入网姓名
		if (query_info.getShip_name() != null && !"".equals(query_info.getShip_name())) {
			sqlWhere.append(" and d.ship_name= '" + query_info.getShip_name() + "' ");
		}
		
		// 商城订单号
		if (query_info.getOut_order_id() != null && !"".equals(query_info.getOut_order_id())) {
			sqlWhere.append(" and a.out_tid = '" + query_info.getOut_order_id() + "' ");
		}
		// 操作点
		if (query_info.getDeal_office_id() != null && !"".equals(query_info.getDeal_office_id())) {
			sqlWhere.append(" and b.out_office = '" + query_info.getDeal_office_id() + "' ");
		}
		// 操作员
		if (query_info.getDeal_operator() != null && !"".equals(query_info.getDeal_operator())) {
			sqlWhere.append(" and b.out_operator = '" + query_info.getDeal_operator() + "' ");
		}
		// 订单来源 行销APP:10071
		if (query_info.getSource_system() != null && !"".equals(query_info.getSource_system())) {
			if("10071".equals(query_info.getSource_system())){
				String ext_order_from = cacheUtil.getConfigInfo("ORDER_LIST_QRY_EXT_ORDER_FROM");
				
				String str = query_info.getSource_system()+","+ext_order_from;
				String[] str_arr = str.split(",");
				List<String> str_list = Arrays.asList(str_arr);
				
				sqlWhere.append(SqlUtil.getSqlInStr("a.order_from", str_list, false, true));
			}else{
				sqlWhere.append(" and a.order_from = '" + query_info.getSource_system() + "' ");
			}
		}
		// 业务号码
		if (query_info.getBus_num() != null && !"".equals(query_info.getBus_num())) {
			sqlWhere.append(" and c.phone_num = '" + query_info.getBus_num() + "' ");
		}
		// 订单分类 暂无分类，后续补充
		if (query_info.getOrder_type() != null && !"".equals(query_info.getOrder_type())) {
			// sqlWhere.append(" and d.order_type =
			// '"+query_info.getOrder_type()+"' ");
		}
		// 支付状态0、未支付1、已支付2、已经退款3、部分退款4、部分付款5、退款申请中
		// 00 全部 01未支付 02 已支付
		if (query_info.getOrder_pay_status() != null && !"".equals(query_info.getOrder_pay_status())) {
			if (!query_info.getOrder_pay_status().equals("00")) {
				if (!query_info.getOrder_pay_status().equals("01")) {
					sqlWhere.append(" and d.pay_status = '0' ");
				} else if (!query_info.getOrder_pay_status().equals("02")) {
					sqlWhere.append(" and d.pay_status = '1' ");
				}
			}
		}
		// 后激活状态00 全部 0未激活 2线下激活成功 3线上激活成功 4人工认证中 5人工认证失败
		if (query_info.getOrder_activite_status() != null && !"".equals(query_info.getOrder_activite_status())) {
			if (!query_info.getOrder_activite_status().equals("00")) {
				sqlWhere.append(" and e.active_flag = '" + query_info.getOrder_activite_status() + "' ");
			}
		}
		// 订单状态 00 全部 01 处理中 02 处理完成 03 已退单 04 已退款 05 异常
		if (query_info.getOrder_status() != null && !"".equals(query_info.getOrder_status())) {
			if (!query_info.getOrder_status().equals("00")) {
				if (query_info.getOrder_status().equals("01")) {// 01处理中flow_trace_id除了L、J
					sqlWhere.append(" and a.flow_trace_id not in ('L','J') ");
				} else if (query_info.getOrder_status().equals("02")) {// 02处理完成flow_trace_id-L、J
					sqlWhere.append(" and a.flow_trace_id in ('L','J') ");
				} else if (query_info.getOrder_status().equals("03")) {// 03已退单refund_status04、05、06-refund_deal_type01正常02退单
					sqlWhere.append(" and (a.refund_status in ('04','05','06') or a.refund_deal_type = '02') ");
				} else if (query_info.getOrder_status().equals("04")) {// 04
																		// 已退款bss_refund_status1、3
					sqlWhere.append(" and b.bss_refund_status in ('1','3') ");
				} else if (query_info.getOrder_status().equals("05")) {// 05
																		// 异常abnormal_statust1
																		// 异常
					sqlWhere.append(" and a.abnormal_status = '1' ");
				}
			}
		}
		sqlCount.append("select count(1) ").append(sqlFrom).append(sqlWhere);
		int sum = Integer.parseInt(daoSupport.queryForString(sqlCount.toString()));
		resp.setPage_total(sum + "");
		resp.setResp_code("0");
		resp.setResp_msg("查询成功");
		if (sum > 0) {// 记录数大于0在继续查询相应的记录
			int pageNo = 1;
			int pageSize = 10;
			try {
				pageNo = Integer.parseInt(query_info.getPage_no());
				pageSize = Integer.parseInt(query_info.getPage_size());
				if (pageNo < 1 || pageSize < 1) {
					resp.setPage_total("0");
					resp.setResp_code("1");
					resp.setResp_msg("查询失败:参数page_size,page_no异常");
					return resp;
				}
			} catch (Exception e) {
				resp.setPage_total("0");
				resp.setResp_code("1");
				resp.setResp_msg("查询失败:参数page_size,page_no转换异常");
				return resp;
			}
			sqlSb.append(sqlSelect).append(sqlFrom).append(sqlWhere).append(" order by tid_time desc ");
			// 结果查询后还要进行转换
			Page page = this.baseDaoSupport.queryForPage(sqlSb.toString(), pageNo, pageSize, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map map = new HashMap();
					map.put("order_id", rs.getString("order_id")); // 订单中心单号order_id
					map.put("out_tid", rs.getString("out_tid"));// 商城订单号out_order_id
					map.put("phone_num", rs.getString("phone_num"));// 订单对应的业务号码bus_num
					map.put("pay_status", rs.getString("pay_status"));// 支付状态order_pay_status
					map.put("sf_status", rs.getString("sf_status"));// 物流状态order_delivery_status
					map.put("active_flag", rs.getString("active_flag"));// 后激活状态order_activite_status
					map.put("tid_time", rs.getString("tid_time"));// 订单提交的时间order_date
					map.put("GoodsName", rs.getString("GoodsName"));// 订单对应的商品名称order_goods_name
					map.put("paymoney", rs.getString("paymoney"));// 订单实收金额order_pay_amount
					map.put("pay_method", rs.getString("pay_method"));// 订单支付类型pay_method
					// 订单中心订单状态 order_status //优先级，已退款>异常>已退单>处理完成、处理中
					map.put("bss_refund_status", rs.getString("bss_refund_status"));// 1、3已退款
					map.put("abnormal_status", rs.getString("abnormal_status"));// 1异常
					map.put("refund_status", rs.getString("refund_status"));// 04、05、06已退单
					map.put("refund_deal_type", rs.getString("refund_deal_type"));// 退单--01正常02退单
					map.put("flow_trace_id", rs.getString("flow_trace_id"));// L、J处理完成，除了L、J处理中
					
					map.put("scriptseq", rs.getString("scriptseq"));
					map.put("proc_id", rs.getString("proc_id"));
					map.put("imsi", rs.getString("imsi"));
					map.put("icc_id", rs.getString("icc_id"));
					map.put("cust_name", rs.getString("cust_name"));
					
					map.put("bss_order_id", rs.getString("bss_order_id"));
					map.put("goods_id", rs.getString("goods_id"));
					map.put("goods_cat_id", rs.getString("goods_cat_id"));
					map.put("cert_end_date", rs.getString("cert_end_date"));
					map.put("region", rs.getString("region"));
					map.put("city", rs.getString("city"));
					map.put("province", rs.getString("province"));
					map.put("cert_addr", rs.getString("cert_addr"));
					
					map.put("cert_code", rs.getString("cert_code"));
					map.put("contact_phone", rs.getString("contact_phone"));
					map.put("email", rs.getString("email"));
					map.put("req_swift_num", rs.getString("req_swift_num"));
					map.put("is_nice_num", rs.getString("is_nice_num"));
					map.put("ship_addr", rs.getString("ship_addr"));
					map.put("refund_desc", rs.getString("refund_desc"));
					map.put("shipping_company", rs.getString("shipping_company"));
					map.put("logi_no", rs.getString("logi_no"));
					map.put("short_url", rs.getString("short_url"));
					
					return map;
				}
			});
			OrderListQueryResp orderInfo;
			
			List<String> orderIds = new ArrayList<String>();
			
			// map.get("order_id")为null的处理
			for (Map map : (List<Map>) page.getResult()) {
				orderInfo = new OrderListQueryResp();
				if (map.get("order_id") == null) {
					orderInfo.setOrder_id("");
				} else {
					orderInfo.setOrder_id(map.get("order_id") + "");
					
					orderIds.add(map.get("order_id") + "");
				}
				
				if (map.get("out_tid") == null) {
					orderInfo.setOut_order_id("");
				} else {
					orderInfo.setOut_order_id(map.get("out_tid") + "");
				}
				if (map.get("phone_num") == null) {
					orderInfo.setBus_num("");
				} else {
					orderInfo.setBus_num(map.get("phone_num") + "");
				}

				// "支付状态0、未支付。""1、已支付。 "2、已经退款。 "3、部分退款。 "4、部分付款。 "5、退款申请中。"
				// 00 全部 01未支付 02 已支付
				// 支付类型
				/*
				 * 一码付-WOPAY沃钱包40 一码付-ALIPAY支付宝 41 一码付-TENPAY微信 42 沃账户代扣-账户代扣 43
				 * 沃账户代扣-沃账户银行卡代扣 44 MISPOS 7 现金 1
				 */
				if (map.get("pay_status") == null) {
					orderInfo.setOrder_pay_status("");
				} else {
					if ("0".equals(map.get("pay_status") + "")) {
						orderInfo.setOrder_pay_status("01");
						orderInfo.setPay_type("");// 支付类型
					} else if ("1".equals(map.get("pay_status") + "")) {
						orderInfo.setOrder_pay_status("02");
						if (null != map.get("pay_method")) {
							orderInfo.setPay_type(map.get("pay_method") + "");// 支付类型
						} else {
							orderInfo.setPay_type("");// 支付类型
						}
					}
				}

				// 00 全部 01 物流已下单 02 物流已发货 03 物流运输中
				// orderInfo.setOrder_delivery_status(map.get("sf_status").toString());
				orderInfo.setOrder_delivery_status("");// 暂时先不返回

				// 后激活状态00 全部 0未激活 2线下激活成功 3线上激活成功 4人工认证中 5人工认证失败
				// 0 待激活 1 线上激活失败 2 线下激活成功 3 线上激活成功
				if (map.get("active_flag") == null) {
					orderInfo.setOrder_activite_status("");
				} else {
					orderInfo.setOrder_activite_status(map.get("active_flag") + "");
				}
				if (map.get("tid_time") == null) {
					orderInfo.setOrder_date("");
				} else {
					orderInfo.setOrder_date(map.get("tid_time") + "");
				}
				if (map.get("GoodsName") == null) {
					orderInfo.setOrder_goods_name("");
				} else {
					orderInfo.setOrder_goods_name(map.get("GoodsName") + "");
				}
				// 实收金额 厘
				if (map.get("paymoney") == null) {
					orderInfo.setOrder_pay_amount("");
				} else {
					Double paymoney = Double.parseDouble(map.get("paymoney") + "");
					orderInfo.setOrder_pay_amount((int) (paymoney * 1000) + "");// 小数点
				}
				String bss_refund_status = map.get("bss_refund_status") + "";
				String abnormal_status = map.get("abnormal_status") + "";
				String refund_status = map.get("refund_status") + "";
				String flow_trace_id = map.get("flow_trace_id") + "";
				String refund_deal_type = map.get("refund_deal_type") + "";
				if (map.get("bss_refund_status") == null) {
					bss_refund_status = "";
				}
				if (map.get("abnormal_status") == null) {
					abnormal_status = "";
				}
				if (map.get("refund_status") == null) {
					refund_status = "";
				}
				if (map.get("flow_trace_id") == null) {
					flow_trace_id = "";
				}
				if (map.get("refund_deal_type") == null) {
					refund_deal_type = "";
				}
				// 优先级，已退款>异常>已退单>处理完成、处理中
				// if("1".equals(bss_refund_status) ||
				// "3".equals(bss_refund_status)){
				// orderInfo.setOrder_status("04");//已退款
				// }else if("1".equals(abnormal_status)){
				// orderInfo.setOrder_status("05");//异常
				// }else if("04".equals(refund_status) ||
				// "05".equals(refund_status) || "06".equals(refund_status)){
				// orderInfo.setOrder_status("03");//已退单
				// }else if("L".equals(flow_trace_id) ||
				// "J".equals(flow_trace_id) ){
				// orderInfo.setOrder_status("02");//处理完成
				// }else if(!"L".equals(flow_trace_id) &&
				// !"J".equals(flow_trace_id) ){
				// orderInfo.setOrder_status("01");//处理中
				// }else{
				// orderInfo.setOrder_status("");//全部
				// }
				/**
				 * es_order_extvtl bss_refund_status 1、3 已退款 es_order_ext
				 * abnormal_status 1 异常 es_order_ext refund_status 04、05、06 已退单
				 * a.refund_deal_type,--01正常02退单 es_order_ext flow_trace_id L、J
				 * 处理完成 es_order_ext flow_trace_id 除了L、J 处理中
				 */
				// 优先级，已退款>异常>已退单>处理完成、处理中
				if ("1".equals(bss_refund_status) || "3".equals(bss_refund_status)) {
					orderInfo.setOrder_status("04");// 已退款
				} else {
					if ("1".equals(abnormal_status)) {
						orderInfo.setOrder_status("05");// 异常
					} else {
						if ("04".equals(refund_status) || "05".equals(refund_status) || "06".equals(refund_status) || "02".equals(refund_deal_type)) {
							orderInfo.setOrder_status("03");// 已退单
						} else {
							if ("L".equals(flow_trace_id) || "J".equals(flow_trace_id)) {
								orderInfo.setOrder_status("02");// 处理完成
							} else if (!"L".equals(flow_trace_id) && !"J".equals(flow_trace_id)) {
								orderInfo.setOrder_status("01");// 处理中
							}
						}
					}
				}
				
				orderInfo.setFlow_trace_id(this.getStringValue(map.get("flow_trace_id")));
				orderInfo.setScriptseq(this.getStringValue(map.get("scriptseq")));
				orderInfo.setProc_id(this.getStringValue(map.get("proc_id")));
				orderInfo.setImsi(this.getStringValue(map.get("imsi")));
				orderInfo.setIcc_id(this.getStringValue(map.get("icc_id")));
				orderInfo.setCust_name(this.getStringValue(map.get("cust_name")));
				orderInfo.setBss_order_id(this.getStringValue(map.get("bss_order_id")));
				orderInfo.setGoods_id(this.getStringValue(map.get("goods_id")));
				orderInfo.setGoods_cat_id(this.getStringValue(map.get("goods_cat_id")));
				orderInfo.setCert_end_date(this.getStringValue(map.get("cert_end_date")));
				orderInfo.setCert_addr(this.getStringValue(map.get("cert_addr")));
				orderInfo.setCert_code(this.getStringValue(map.get("cert_code")));
				orderInfo.setContact_phone(this.getStringValue(map.get("contact_phone")));
				orderInfo.setEmail(this.getStringValue(map.get("email")));
				orderInfo.setReq_swift_num(this.getStringValue(map.get("req_swift_num")));
				orderInfo.setIs_nice_num(this.getStringValue(map.get("is_nice_num")));
				orderInfo.setShip_addr(this.getStringValue(map.get("ship_addr")));
				orderInfo.setRegion(this.getStringValue(map.get("region")));
				orderInfo.setCity(this.getStringValue(map.get("city")));
				orderInfo.setProvince(this.getStringValue(map.get("province")));
				orderInfo.setRefund_desc(this.getStringValue(map.get("refund_desc")));
				orderInfo.setShipping_company(this.getStringValue(map.get("shipping_company")));
				orderInfo.setLogi_no(this.getStringValue(map.get("logi_no")));
				orderInfo.setShort_url(this.getStringValue(map.get("short_url")));
				
				query_resp.add(orderInfo);
			}
			
			//增加流程信息查询
			this.getFlowInfo(query_resp);
			
			resp.setQuery_resp(query_resp);

		}
		return resp;
	}

	/**
	 * 2.3.8. 订单收单结果查询接口
	 * 
	 * @author 宋琪
	 * @date 2017年6月29日
	 */
	@SuppressWarnings("rawtypes")
	public OrderResultQueryResp queryOrderResult(OrderResultQueryReq requ) {
		OrderResultQueryResp resp = new OrderResultQueryResp();
		List<OrderResultResp> query_resp = new ArrayList<OrderResultResp>();
		resp.setQuery_resp(query_resp);
		// 查询的可选参数
		OrderResultQueryInfo query_info = requ.getQuery_info();
		// 直接用外部单号查询
		String outId = requ.getMall_order_id();
		if (StringUtils.isEmpty(outId)) {
			outId = query_info.getMall_order_id();
			if (StringUtils.isEmpty(outId)) {
				resp.setResp_code("1");
				resp.setResp_msg("查询失败:参数为空");
				return resp;
			}
		}
		// 01收单成功未处理 02 处理中 03 处理成功 04 处理失败
		/*
		 * 02先不要 --01收单成功未处理 es_order_queue 存在 订单树不在 --03 处理成功 订单树存在 --04 处理失败
		 * Es_Order_Queue_Fail 存在
		 */
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.DEAL_DESC from Es_Order_Queue_Fail t ");
		sql.append(" where t.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
		sql.append(" and t.object_id = '" + outId + "'");
		sql.append(" order by t.created_date desc ");
		List list = daoSupport.queryForListByMap(sql.toString(), null);
		OrderResultResp orderResultResp = new OrderResultResp();
		if (list.size() > 0) {// --04 处理失败 Es_Order_Queue_Fail 存在
			Map map = (Map) list.get(list.size() - 1);
			resp.setResp_code("0");
			resp.setResp_msg("查询成功");
			orderResultResp.setOrder_id("");
			orderResultResp.setOrder_receive_msg("处理失败:" + map.get("DEAL_DESC"));
			orderResultResp.setOrder_receive_status("04");
			query_resp.add(orderResultResp);
			return resp;
		}
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(outId);
		if (orderTree != null) {// --03 处理成功 订单树存在
			resp.setResp_code("0");
			resp.setResp_msg("查询成功");
			orderResultResp.setOrder_id(orderTree.getOrder_id());
			orderResultResp.setOrder_receive_msg("收单成功");
			orderResultResp.setOrder_receive_status("03");
			query_resp.add(orderResultResp);
			return resp;
		} else {// --01收单成功未处理 es_order_queue 存在 订单树不在
			sql = new StringBuffer();
			sql.append(" select NVL(t.ORDER_ID,' ') as ORDER_ID,t.WORK_STATE from es_order_queue t ");
			sql.append(" where t.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
			sql.append(" and t.object_id = '" + outId + "'");
			sql.append(" order by t.created_date desc ");
			list = daoSupport.queryForListByMap(sql.toString(), null);
			if (list.size() > 0) {//
				Map map = (Map) list.get(list.size() - 1);
				resp.setResp_code("0");
				resp.setResp_msg("查询成功");
				orderResultResp.setOrder_id((map.get("ORDER_ID") + "").trim());
				orderResultResp.setOrder_receive_msg("收单成功未处理");
				orderResultResp.setOrder_receive_status("01");
				query_resp.add(orderResultResp);
				return resp;
			} else {// 外部单号异常
				resp.setResp_code("1");
				resp.setResp_msg("查询失败");
				orderResultResp.setOrder_id("");
				orderResultResp.setOrder_receive_msg("数据不存在");
				orderResultResp.setOrder_receive_status("");
				query_resp.add(orderResultResp);
				return resp;
			}
		}
	}

	/**
	 * 根据order_id 查询订单
	 * 
	 * @return
	 * @author 宋琪
	 * @date 2017年6月14日 20:15:59
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getOrderInfo(String order_id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select c.paymoney, ").append(" to_char(c.create_time, 'yyyymmddhh24miss') as create_time, ").append(" b.payplatformorderid, ").append(" b.bss_refund_status, ")
				.append(" a.abnormal_status, ").append(" a.refund_status, ").append(" d.bssorderid, ").append(" a.flow_trace_id, ").append(" a.exception_desc, ").append(" a.refund_deal_type, ")
				.append(" f.name ").append(" from es_order_ext a ").append(" left join es_order_extvtl b ").append(" on a.order_id = b.order_id ").append(" left join es_order c ")
				.append(" on b.order_id = c.order_id").append(" left join es_order_items_ext d ").append(" on c.order_id = d.order_id").append(" left join es_order_items e ")
				.append(" on d.order_id = e.order_id").append(" left join es_goods f ").append(" on e.goods_id = f.goods_id").append(" where a.order_id = '" + order_id + "'")
				.append(" and a.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
		List list = daoSupport.queryForListByMap(sql.toString(), null);
		Map map = (Map) list.get(0);
		// 实收金额
		if (map.get("paymoney") == null) {
			map.put("paymoney", "");
		} else {
			Double discount_amount = Double.parseDouble(map.get("paymoney") + "") * 1000;
			map.put("paymoney", discount_amount.intValue());
		}

		if (map.get("create_time") == null) {
			map.put("create_time", "");
		}
		if (map.get("payplatformorderid") == null) {
			map.put("payplatformorderid", "");
		}
		if (map.get("bssorderid") == null) {
			map.put("bssorderid", "");
		}
		// 订单状态
		map.put("order_status", "");// 数据异常处理
		String bss_refund_status = map.get("bss_refund_status") + "";
		String abnormal_status = map.get("abnormal_status") + "";
		String refund_status = map.get("refund_status") + "";
		String flow_trace_id = map.get("flow_trace_id") + "";
		String refund_deal_type = map.get("refund_deal_type") + "";
		if (map.get("bss_refund_status") == null) {
			bss_refund_status = "";
		}
		if (map.get("abnormal_status") == null) {
			abnormal_status = "";
		}
		if (map.get("refund_status") == null) {
			refund_status = "";
		}
		if (map.get("flow_trace_id") == null) {
			flow_trace_id = "";
		}
		if (map.get("refund_deal_type") == null) {
			refund_deal_type = "";
		}
		// 优先级，已退款>异常>已退单>处理完成、处理中
		// if("1".equals(bss_refund_status) || "3".equals(bss_refund_status)){
		// map.put("order_status", "04");//已退款
		// }else if("1".equals(abnormal_status)){
		// map.put("order_status", "05");//异常
		// }else if("04".equals(refund_status) || "05".equals(refund_status) ||
		// "06".equals(refund_status)){
		// map.put("order_status", "03");//已退单
		// }else if("L".equals(flow_trace_id) || "J".equals(flow_trace_id) ){
		// map.put("order_status", "02");//处理完成
		// }else if(!"L".equals(flow_trace_id) && !"J".equals(flow_trace_id) ){
		// map.put("order_status", "01");//处理中
		// }
		// 优先级，已退款>异常>已退单>处理完成、处理中 --01正常02退单
		if ("1".equals(bss_refund_status) || "3".equals(bss_refund_status)) {
			map.put("order_status", "04");// 已退款
		} else {
			if ("1".equals(abnormal_status)) {
				map.put("order_status", "05");// 异常
			} else {
				if ("04".equals(refund_status) || "05".equals(refund_status) || "06".equals(refund_status) || "02".equals(refund_deal_type)) {
					map.put("order_status", "03");// 已退单 --01正常02退单
				} else {
					if ("L".equals(flow_trace_id) || "J".equals(flow_trace_id)) {
						map.put("order_status", "02");// 处理完成
					} else if (!"L".equals(flow_trace_id) && !"J".equals(flow_trace_id)) {
						map.put("order_status", "01");// 处理中
					}
				}
			}
		}
		return map;
	}

	public String queryGoodsInfo(String serial_no, String source_system) {
		String sql = "select  request  from es_goods_req  where serial_no = ? and receive_system = ?";
		return daoSupport.queryForString(sql, serial_no, source_system);
	}

	public void addRspTime(String seq_no, String source_system) {
		Map fieldsMap = new HashMap();
		fieldsMap.put("rsp_time", new Date());
		Map whereMap = new HashMap();
		whereMap.put("serial_no", seq_no);
		whereMap.put("receive_system", source_system);
		daoSupport.update("es_goods_req", fieldsMap, whereMap);
	}

	public List<GoodsInfoVO> queryGoodsList(QueryGoodsListReq req) {
		StringBuffer sb = new StringBuffer();
		List objs = new ArrayList();
		sb.append(" select distinct eg.goods_id,eg.name as goods_name,eg.simple_name,eg.type_id,eg.cat_id,eg.price from es_goods eg");
		sb.append(" left join es_goods_co egc on eg.goods_id = egc.goods_id");
		sb.append(" left join es_goods_rel_county egrc on egrc.goods_id = eg.goods_id");
		sb.append(" left join es_goods_rel_community egrcm on egrcm.goods_id = eg.goods_id");
		sb.append(" left join es_goods_rel_cust egrcu on egrcu.goods_id = eg.goods_id");
		sb.append(" left join es_goods_rel_develop egrd on egrd.goods_id = eg.goods_id");
		sb.append(" left join es_goods_rel_office egro on egro.goods_id = eg.goods_id ");
		sb.append(" left join es_goods_rel_staff egrs on egrs.goods_id = eg.goods_id");
		sb.append(" where 1=1 and egc.status = '1' and eg.market_enable='1' ");
		String sqlFrom = " and egc.source_from='" + ManagerUtils.getSourceFrom() + "'";
		sb.append(sqlFrom);

		sb.append(" and egc.org_id = ?");
		objs.add(req.getSource_system());

		if (!StringUtils.isEmpty(req.getCity_code())) {
			sb.append(" and egrc.countyid = ?");
			objs.add(req.getCity_code());
		}

		if (!StringUtils.isEmpty(req.getCounty_id())) {
			sb.append(" and egrc.countyid = ?");
			objs.add(req.getCounty_id());
		}

		if (!StringUtils.isEmpty(req.getCommunity_code())) {
			sb.append(" and egrcm.community_code = ?");
			objs.add(req.getCommunity_code());
		}

		if (!StringUtils.isEmpty(req.getType_id())) {
			sb.append(" and eg.type_id = ?");
			objs.add(req.getType_id());
		}

		if (!StringUtils.isEmpty(req.getCat_id())) {
			sb.append(" and eg.cat_id = ?");
			objs.add(req.getCat_id());
		}

		if (!StringUtils.isEmpty(req.getGoods_id())) {
			sb.append(" and eg.goods_id = ?");
			objs.add(req.getGoods_id());
		}

		if (!StringUtils.isEmpty(req.getCust_id())) {
			sb.append(" and egrcu.cust_id = ?");
			objs.add(req.getCust_id());
		}

		if (!StringUtils.isEmpty(req.getStaff_id())) {
			sb.append(" and egrs.staff_id = ?");
			objs.add(req.getStaff_id());
		}

		if (!StringUtils.isEmpty(req.getDevelop_id())) {
			sb.append(" and egrd.decelop_rela_id = ?");
			objs.add(req.getDevelop_id());
		}

		if (!StringUtils.isEmpty(req.getOffice_id())) {
			sb.append(" and egro.office_id = ?");
			objs.add(req.getOffice_id());
		}
		logger.info(sb.toString());
		List<GoodsInfoVO> list = daoSupport.queryForList(sb.toString(), GoodsInfoVO.class, objs.toArray());

		// 根据商品编码查询货品信息
		for (GoodsInfoVO goodsInfoVO : list) {
			String goods_id = goodsInfoVO.getGoods_id();
			// 商品对应多个货品id
			String sql = " select eg.goods_id as prods_id, eg.name as prods_name" + " from es_goods eg" + " where eg.goods_id in" + " (select z_goods_id" + " from es_goods_rel egr"
					+ "  where egr.a_goods_id = ?)";
			List<ProdsInfoVO> prodsInfo = daoSupport.queryForList(sql, goods_id);

			goodsInfoVO.setProds_info(prodsInfo);
		}

		return list;
	}

	public Map qryGoodsDtl(String order_id) {
		OrderTreeBusiRequest tree = new OrderTreeBusiRequest();
		tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String goods_id = tree.getOrderItemBusiRequests().get(0).getGoods_id();
		String sql = "select t.p_code,t.sn from es_goods_package t where t.goods_id='" + goods_id + "' and t.source_from='" + ManagerUtils.getSourceFrom() + "'";
		List list = daoSupport.queryForListByMap(sql, null);
		Map map = (Map) list.get(0);
		return map;

	}

	public String checkRefund(String order_id, String trace_id) {
		String sql = "select t.comments from es_order_handle_logs t where t.order_id='" + order_id + "' and t.handler_type='cancelreturned' and t.flow_trace_id='" + trace_id + "' ";
		List list = daoSupport.queryForListByMap(sql, null);
		if (list.size() >= 1) {
			return "yes";
		} else {
			return "no";
		}

	}

	public String getRSASign(Map map) {
		// 对业务参的json串进行数字签名
		logger.info("*******************支付中心加密开始*****************");
		
		Object obj = map;
		String busiConten = JSON.toJSONString(obj);
		
		logger.info("加密前原始报文：" + busiConten);
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String public_key = cacheUtil.getConfigInfo("DIC_PAY_PUBLIC_KEY");
		
		logger.info("加密公钥：" + public_key);
		
		// String public_key =
		// "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApwNADvSBwMi+sAZBqT6y+PC5u99PcvSAUyZ+4DxYYedqiOJR4JUqmXZ0R0vNri/r/1VJePpx3caGx4ZRgE+xNVtzZJ7C9spexka4V07e0TfT4cHzZTVm8c5SP1hL6lmSjwphVb3Vv1arLIszi0/JyYXzzulHNXbpK3tmJxYVvhQWDOfapzr/viZF8+R761RIiN70Z2V33UaCoKqutw+FEewI16Qu2vDnveH4YBQU+XrrnU77g8cn7PAfXa1apSwJeCmEpU7QL3gI8HEjd0+DCJHVIuwt72Ceo93PWyULr1DQ9T8bDmYfS/1F7XdR1+uVsxXDf4ImXO2ZlfgfoYakCQIDAQAB";
		String signStr = "";
		// String private_key =
		// "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnA0AO9IHAyL6wBkGpPrL48Lm7309y9IBTJn7gPFhh52qI4lHglSqZdnRHS82uL+v/VUl4+nHdxobHhlGAT7E1W3NknsL2yl7GRrhXTt7RN9PhwfNlNWbxzlI/WEvqWZKPCmFVvdW/VqssizOLT8nJhfPO6Uc1dukre2YnFhW+FBYM59qnOv++JkXz5HvrVEiI3vRnZXfdRoKgqq63D4UR7AjXpC7a8Oe94fhgFBT5euudTvuDxyfs8B9drVqlLAl4KYSlTtAveAjwcSN3T4MIkdUi7C3vYJ6j3c9bJQuvUND1PxsOZh9L/UXtd1HX65WzFcN/giZc7ZmV+B+hhqQJAgMBAAECggEAQTcksXJKpHP++g9HXoYsLscZT8JQbMwKu+1hR8BoprpNwGvXmwHEY658k0aHZ0gxOEAec44gHoCiKpFJi/gSc4plUdoouWOmhHgjDXyvxzSsmRA6McAblbqCJVgeNMGLWC2uemOpbpCyQK+Ojgy29+lFnfs7oiXvwNnrtsGJ9fGQhkiY8aGVZxWCIm+DtP9yvXU2anvN8do+G4cehrsD7fI+XJrbLqQUxAS+sKKGXygGRpLbplYAINZonBgdu3MBIHgdL1MikC5ArgOuowHSPTMqLYhwyGrUbdBn/RHqPYPevVtI7b4b8FhHeWoDhiYTD0jK445S36+VTOCqT4L/zQKBgQDxin//Wtb66nFc73xqlktJ8XLfhRkr4vd0kDNOIH5qNFGeO8xa880kwN/o9npzvA29bY92k61DxBjKPQ5l/IBx06fA+KFWofk9Hmr4ZBi2fKbZWViL15VjDAr7iSbdEGhLVcbvWVge42mhUeDLs0mcT9VJlpaTutcH2yfmKA7TTwKBgQCxAqO5tE1M74cAfGwWcudyAMULSyw2EtDH7AVvy4UfxxqCJgeL8U+F4sYScJSWh4TBH0fvXQXem/TMZPKbWhC2TwOxiKRK7uQxdqX6Wd1D27jP565cAn2BypKyZM4zv4x1ATvJYeUj8kr/0BoANuIVqf+KqYkp9M1709KQPkOdJwKBgQC29IqZeExIiNZPZ9NcxPTIUIwLhLyZQnTfpO5HqlM2zORZr7/N0Me9pB62TiRYj8P0jQ1S6u8d2mZQocjLNWRxU7nIcEt83Hl2cxjhhqg1advT3Fo2qpgsCw4ykyON5QSqs5FY8NrPor/7ApL8mJQUQ2AyOk9aHPcNq1Hggs0f/wKBgEy3jCZMUsRpe+VmB6KjIlRyqPQAHKVC+mHrlV+ioYvoS0DlIpgwefrZEmeji4LCMXNSD2datdSJW0vK5KsYGe4pyLwFMM+ZQiV9+unKUO+8JxlUnGkcWzEJjxLqRcR/3GxJoJPjbeBqfr7ofpxhLROenjaGy//3LafhYn1Jvxw5AoGAHyDMp5LewvqcUEiKPPBvHJL37Az1y5yghagWJKgGm292+ZW2vmIajb3kY8QXIrs3Pez4z0PT0ZHlOuZtczV55a9MdQNnN2ePL9SpXP/hv2v7SMqjXRF69LdzzCI8H1H8uBFlN06ixpbh+uwf1tHyNVnG+Vnx883c0k286X9MvWQ=";
		try {
			signStr = PayCenterRSA.encryptRSA(busiConten, public_key);
			// logger.info("加密后得到的串："+signStr);
			// 解密
			// String decStr = PayCenterRSA.decryptRSA(signStr, private_key);
			// logger.info("解密后得到的串："+decStr);
			
			logger.info("加密后报文：" + signStr);

		} catch (Exception e) {
			logger.info("加密失败：" + e.getMessage(),e);
			e.printStackTrace();
		}
		
		logger.info("*******************支付中心加密结束*****************");
		
		return signStr;
	}

	public String decodeInfo(String str) {
		logger.info("*******************支付中心解密开始*****************");
		
		logger.info("解密前原始报文：" + str);
		
		String new_str = "";
		try {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String private_key = cacheUtil.getConfigInfo("DIC_PAY_PRIVTE_KEY");
			
			logger.info("解密私钥：" + private_key);
			
			// String private_key =
			// "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFG4EEw4baXFQmFcXKyaM4USaOav2012hHcnSaTis/S8IgE2A2U6TcTkesMLU53QaUEHyoIpQbdqAPHvBleCXR6UOErMdlLV5TM7+TvYbn30dkUt76Yj0J2o59RKpA0xjXDIwVULN2PaT4mrAtKuvkqnX3NsI4kzmsEDDOkGVRsBsFJtsO5bNhu9PI8AT4FQfScCVcXvMeMfM3BZq9RP5BBenH8RkcXvWMbsUvdehnKl3ZgXZRN/uUgkRSCI3GATL5vU9TKhwT8vFSEjHNHJYbhRHpBMB3h9wPhNAj983KVx/z2cy+BiYZFicopXIscYD7DZ48beBSeXv85XdaLc9nAgMBAAECggEAI524aA3ph4QtAAPTaO+LPQZ5yqHWQQ1iWbvGd4NzDvy2dN66X88BzfEkPG7MDy+VEwlWUabu+mpDachdiskbVY+VAWs571i0ZI70NBQChQi5kZ6bC2Q98OUKO3qkE7RocSJqWUm+u2TDP8IkYfGaLRVvezarwXejdubeKGd6tOCCX1WjFYJBNJ0ylwcjxNqUUXF0hyxYzbB+Kn1jd7anyiTy1cvQ20IYK3Idsu5ZBED9gYCUbeNtUB5q6K2tzYiun/mWpzvDD8Lb6lgj0+BeVjfKNVmryrKdvGz+k+cSQsaKwTG1hlxH/ec8vfXNIEtz6de0EB/LDvEndxd9NOFcAQKBgQD+76e57CCTBgPjiNXkdnZh9z+La58ruAwGIW4nfaleTyl1+2mr8GruMQVghNtty2+GIzKHNX3vnik5BBj9QYag77GJ94sMLn0ySu/PJiTCDi4VVDy+OgcuNScAQabbyNO55PnXphiVTq4F+icDqqb+qMiS8Zh9Kx5OO+mK0r0IHQKBgQCFqbNqUPC1C9vN2lqrivylNqroh6B633joBNbHfsJn3+xhMIT+WmRUPV0dHkgV5hg/Yj+qOb5SN5P6YiGwWpLrI56YCDL/2EsQlkHx5JIwr5YLzi87ouXtoibCymiuH/u6CGLlU3ay+k0c5r+r2UHOKA5q8cjYMbBXNcmkaCmGUwKBgQChwJFdJ/LrFg/1mGaOFJO8SNIqtdhCNv56DVAFAngp/HREBYbgryRJiRTuTp2jJkbWhxKSM2B6XpGtOWpZle/DwRGWhlfVHNIWzPEnL/52meSnZ9E6qTrRGKxsPzuNi/i6pAvZxesnIMrNxBBpBSf7wD+y7FNiZ69M2Dqv+FI0CQKBgEDntwfZZgmESKgdcAn849IWScfAlF0WR0/jiag2FZD7W32WYf5hRnbhlWWeXsLDiOK+gsvWXmTuOB1Nqa5JTS6BjXGmCZ3TGiBj1oHvzE139hSleqoq4BwUsV3FjC+BuGsNx3g0n18pzGhMZKa+SQYizV2BK1ZQSqEOrWv4aLGhAoGBAO3EWcZ+xmW596LpFgePvoeL3Nw3MMQt5q+dxDIPszsX5cOsLMf2DDZgGsheJLEg1BgfXGa+Bl14YMIQsm3MoGKquaoAR1hJnQlLbSSF1dtoQR8md0qYWgPE5bYslaW89hLkW010lyOlg4obG3TVLqAL3UkOhQQvb64qQo2FRVwN{publicKey=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhRuBBMOG2lxUJhXFysmjOFEmjmr9tNdoR3J0mk4rP0vCIBNgNlOk3E5HrDC1Od0GlBB8qCKUG3agDx7wZXgl0elDhKzHZS1eUzO/k72G599HZFLe+mI9CdqOfUSqQNMY1wyMFVCzdj2k+JqwLSrr5Kp19zbCOJM5rBAwzpBlUbAbBSbbDuWzYbvTyPAE+BUH0nAlXF7zHjHzNwWavUT+QQXpx/EZHF71jG7FL3XoZypd2YF2UTf7lIJEUgiNxgEy+b1PUyocE/LxUhIxzRyWG4UR6QTAd4fcD4TQI/fNylcf89nMvgYmGRYnKKVyLHGA+w2ePG3gUnl7/OV3Wi3PZwIDAQAB,privateKey=MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFG4EEw4baXFQmFcXKyaM4USaOav2012hHcnSaTis/S8IgE2A2U6TcTkesMLU53QaUEHyoIpQbdqAPHvBleCXR6UOErMdlLV5TM7+TvYbn30dkUt76Yj0J2o59RKpA0xjXDIwVULN2PaT4mrAtKuvkqnX3NsI4kzmsEDDOkGVRsBsFJtsO5bNhu9PI8AT4FQfScCVcXvMeMfM3BZq9RP5BBenH8RkcXvWMbsUvdehnKl3ZgXZRN/uUgkRSCI3GATL5vU9TKhwT8vFSEjHNHJYbhRHpBMB3h9wPhNAj983KVx/z2cy+BiYZFicopXIscYD7DZ48beBSeXv85XdaLc9nAgMBAAECggEAI524aA3ph4QtAAPTaO+LPQZ5yqHWQQ1iWbvGd4NzDvy2dN66X88BzfEkPG7MDy+VEwlWUabu+mpDachdiskbVY+VAWs571i0ZI70NBQChQi5kZ6bC2Q98OUKO3qkE7RocSJqWUm+u2TDP8IkYfGaLRVvezarwXejdubeKGd6tOCCX1WjFYJBNJ0ylwcjxNqUUXF0hyxYzbB+Kn1jd7anyiTy1cvQ20IYK3Idsu5ZBED9gYCUbeNtUB5q6K2tzYiun/mWpzvDD8Lb6lgj0+BeVjfKNVmryrKdvGz+k+cSQsaKwTG1hlxH/ec8vfXNIEtz6de0EB/LDvEndxd9NOFcAQKBgQD+76e57CCTBgPjiNXkdnZh9z+La58ruAwGIW4nfaleTyl1+2mr8GruMQVghNtty2+GIzKHNX3vnik5BBj9QYag77GJ94sMLn0ySu/PJiTCDi4VVDy+OgcuNScAQabbyNO55PnXphiVTq4F+icDqqb+qMiS8Zh9Kx5OO+mK0r0IHQKBgQCFqbNqUPC1C9vN2lqrivylNqroh6B633joBNbHfsJn3+xhMIT+WmRUPV0dHkgV5hg/Yj+qOb5SN5P6YiGwWpLrI56YCDL/2EsQlkHx5JIwr5YLzi87ouXtoibCymiuH/u6CGLlU3ay+k0c5r+r2UHOKA5q8cjYMbBXNcmkaCmGUwKBgQChwJFdJ/LrFg/1mGaOFJO8SNIqtdhCNv56DVAFAngp/HREBYbgryRJiRTuTp2jJkbWhxKSM2B6XpGtOWpZle/DwRGWhlfVHNIWzPEnL/52meSnZ9E6qTrRGKxsPzuNi/i6pAvZxesnIMrNxBBpBSf7wD+y7FNiZ69M2Dqv+FI0CQKBgEDntwfZZgmESKgdcAn849IWScfAlF0WR0/jiag2FZD7W32WYf5hRnbhlWWeXsLDiOK+gsvWXmTuOB1Nqa5JTS6BjXGmCZ3TGiBj1oHvzE139hSleqoq4BwUsV3FjC+BuGsNx3g0n18pzGhMZKa+SQYizV2BK1ZQSqEOrWv4aLGhAoGBAO3EWcZ+xmW596LpFgePvoeL3Nw3MMQt5q+dxDIPszsX5cOsLMf2DDZgGsheJLEg1BgfXGa+Bl14YMIQsm3MoGKquaoAR1hJnQlLbSSF1dtoQR8md0qYWgPE5bYslaW89hLkW010lyOlg4obG3TVLqAL3UkOhQQvb64qQo2FRVwN}";
			// String private_key = (String) keyParam.get("PRIVATE_KEY");
			String decStr = PayCenterRSA.decryptRSA(str, private_key);
			new_str = java.net.URLDecoder.decode(decStr, "utf-8");
			
			logger.info("解密后报文：" + new_str);
			
		} catch (Exception e) {
			logger.info("解密失败：" + e.getMessage(),e);
			e.printStackTrace();
		}
		
		logger.info("*******************支付中心解密结束*****************");
		
		return new_str;

	}
	
	public static void main(String[] args) throws Exception{
		String data = "ZcL3++Jk7ZD43xlEvVq16si6gby8UxVDcPksAmaf7z1U06h2KdYUcqnx/ZobIG3MWVurrhIvVeZAqRvgwBXgUymkP3wADyYpblmM5zyw0eW6PwWFMokIIRb41hY0xTRPKvgnY/zGspymLPeM9sf29ksC/A3uK48LJPtYOxm2ZNrItDlkzRe+DWe4/Noq2yIJg2qlJZ/+jI7nX5+kGTtypKBjWD5rbVL1prK4SNrzSd8RLxjOSq72VJmcHdT2zVh78VOaEmSFKaQf2Vsc/vbXib8NxTKRlJGO3UHni/SC2GeuOBdUcSojLw5KJqRfOUE0hn0u8HKPYldOpTNjsjwbIA==#ad2bqBRST+6LfmAlUPeDcTyDx5T5aRMgwzBdsgTQnCMyEGIdB67oEpjkdxY54/M1gXJKm8Pxi9EtUZf0OrlpAMKh+7B8b6Pp9UeAEEv+m7+N2hctHHbYZxajcRTqyTtfgPJ36JXJBOtnzEVmtd/cHBA5DNB4mqEsv4J17Ktm6zXpCQfCoOSPSY8oheCgSw2HFvlPhZgEh5kefHCIypN/Y2GjtImB9QScl0AcEDgX7CQtAIk02bjUVzCcvN5o+1WwVkvTFpPWbxwRZU/LN09OakzdyT9Hc5ZtkG9wb7urFBsNpP6oCJ0eKY9B4Sd1fU5orv+14BvgzG/NwqLnatzGWw==#";
		
		String private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC1xIhBTopdZOwTYaPAqCFpnu7SjLANqldlHiYexOuOn8updRVz2uu2yEYiZCsSVWfkFGl2/2YkuhAaUCrbwfVNoWMV9MukoKsyEN1n13mXoIyrpMKaC8xCiKrkQHnM/+AdBCDU4ycsIHyFkG4L+NbB7JEZ21Bt8fr0SkBMp6CjjIfBI/HghE258eNT2CSeCE+7DSbUanz4TNN4yz/7h9oOYjws4muuwsuvo2bxUGGNw86G2qwn6ctgmNgu2v9mrbPhvaIx87zVVPfwh9JMGH4rwb2wA+duTnOFbmOQlQ8DVP18PyvkAuRwcpc1fKjQOJE+NiMxOwCF6BeaSt+hiUlNAgMBAAECggEAaDs72x87rtSpn2+VJGwFRPjzgF9WsW9l0EFaoLhRT6cIdbMDSz1Vf6oWta/1Fv1MpvihhbBtFVBw3XXtLblqfkq2dCe9AzlzEjGjlOF2A2H4aJEBWq67Qcf3Hw5uCgUMsfblEM71bpNudHTe9hSKqgeLKWWfGHzzaFSPzKUvM7jxmgBFtVZz8gFHkXBkcKMF8jaKp7BmUSy0zPAgu0jq0NCWSG6nu3/+s+qnOZZvVdfgu+uV4Olu6jUV8bl1bSqxjE7CbDtcTjVkfA1RgJRrcFYOUrzkjayN099h/xUWaV6Lh0IgXi/flCxit9glRVjG/MeuhGHO7sXU62LBCKfdAQKBgQDhTkyeAhgsOzzwYOwuyxSbLUF8bCC+maDgIMT6yybSoXQGCNtjb9x7ZVlochKfam3utYRnPNQ9DhsCfX58kAPpBsJN9u/fbwj6EbElApBKVa2RLgSAT2T4zAi/8k+PrOZpOC+jMii8a58NuhcopeUAaRH418xIXBFCSGIsDirJjQKBgQDOh88l//Mg0UQjoQkWx7IKeLBWRXjHtj75HbG4+cpZEY8dr4lkNLzzOBXHkFWyMjfWIeuTMOlR/36k8qF5adByA473YHm6eIjU0ZA8zxgzQkbIPL0zyDH0hN5eRrXGhPi0sS1IyGlmUFgHvy7jcHt+wbWCkg0dluZxCirwI1AuwQKBgE4D08nexcoDFR3TIdUuIst8xzEzK79z6N3S2wJQd/f1SfyQ8o6i/ECKHoRpA4PJ0ZkXp2osCP2/NaEOHU86evAklAF1IShIBV6545ICKj5jHn+n5I0dcMQpulnkqKICZ6JFf1FOHAbqaR/SsRQ1JW+X8k4tQyJHJv7Bm6TPnwc9AoGAGccxv9h2jD4Sdy/+Zm1+d3gg6mO5c5P3wDhImoHzE7LxXg8u4ucCPoldjo8hYyCJf5y0Pqb4OFffgs8WAIWIkvKGwbG0/+VTjtypWC2xI5S+KPgL1Y7DTXXYG4RP4/ts34DN2ivJnA+jLTRbsd+0Sln7QdTg9jSDhtByE48UzwECgYAdDoKfK7ybk4nUIgD0OfgRE/mrsmT/5Es3VrDMdX5A/SO0VgAdI/1YdDegoStX3R9XenIJvLV2kG2/dq+YZy5fMX8XQI/YoIhNuonTPE5cGj7s5rjZ3BaQ4V1L68w2E/LEZyh7miuPCEw/w/jln33kjUybWT7ECiwBL2M8F4OJOw=={publicKey=MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtcSIQU6KXWTsE2GjwKghaZ7u0oywDapXZR4mHsTrjp/LqXUVc9rrtshGImQrElVn5BRpdv9mJLoQGlAq28H1TaFjFfTLpKCrMhDdZ9d5l6CMq6TCmgvMQoiq5EB5zP/gHQQg1OMnLCB8hZBuC/jWweyRGdtQbfH69EpATKego4yHwSPx4IRNufHjU9gknghPuw0m1Gp8+EzTeMs/+4faDmI8LOJrrsLLr6Nm8VBhjcPOhtqsJ+nLYJjYLtr/Zq2z4b2iMfO81VT38IfSTBh+K8G9sAPnbk5zhW5jkJUPA1T9fD8r5ALkcHKXNXyo0DiRPjYjMTsAhegXmkrfoYlJTQIDAQAB, privateKey=MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC1xIhBTopdZOwTYaPAqCFpnu7SjLANqldlHiYexOuOn8updRVz2uu2yEYiZCsSVWfkFGl2/2YkuhAaUCrbwfVNoWMV9MukoKsyEN1n13mXoIyrpMKaC8xCiKrkQHnM/+AdBCDU4ycsIHyFkG4L+NbB7JEZ21Bt8fr0SkBMp6CjjIfBI/HghE258eNT2CSeCE+7DSbUanz4TNN4yz/7h9oOYjws4muuwsuvo2bxUGGNw86G2qwn6ctgmNgu2v9mrbPhvaIx87zVVPfwh9JMGH4rwb2wA+duTnOFbmOQlQ8DVP18PyvkAuRwcpc1fKjQOJE+NiMxOwCF6BeaSt+hiUlNAgMBAAECggEAaDs72x87rtSpn2+VJGwFRPjzgF9WsW9l0EFaoLhRT6cIdbMDSz1Vf6oWta/1Fv1MpvihhbBtFVBw3XXtLblqfkq2dCe9AzlzEjGjlOF2A2H4aJEBWq67Qcf3Hw5uCgUMsfblEM71bpNudHTe9hSKqgeLKWWfGHzzaFSPzKUvM7jxmgBFtVZz8gFHkXBkcKMF8jaKp7BmUSy0zPAgu0jq0NCWSG6nu3/+s+qnOZZvVdfgu+uV4Olu6jUV8bl1bSqxjE7CbDtcTjVkfA1RgJRrcFYOUrzkjayN099h/xUWaV6Lh0IgXi/flCxit9glRVjG/MeuhGHO7sXU62LBCKfdAQKBgQDhTkyeAhgsOzzwYOwuyxSbLUF8bCC+maDgIMT6yybSoXQGCNtjb9x7ZVlochKfam3utYRnPNQ9DhsCfX58kAPpBsJN9u/fbwj6EbElApBKVa2RLgSAT2T4zAi/8k+PrOZpOC+jMii8a58NuhcopeUAaRH418xIXBFCSGIsDirJjQKBgQDOh88l//Mg0UQjoQkWx7IKeLBWRXjHtj75HbG4+cpZEY8dr4lkNLzzOBXHkFWyMjfWIeuTMOlR/36k8qF5adByA473YHm6eIjU0ZA8zxgzQkbIPL0zyDH0hN5eRrXGhPi0sS1IyGlmUFgHvy7jcHt+wbWCkg0dluZxCirwI1AuwQKBgE4D08nexcoDFR3TIdUuIst8xzEzK79z6N3S2wJQd/f1SfyQ8o6i/ECKHoRpA4PJ0ZkXp2osCP2/NaEOHU86evAklAF1IShIBV6545ICKj5jHn+n5I0dcMQpulnkqKICZ6JFf1FOHAbqaR/SsRQ1JW+X8k4tQyJHJv7Bm6TPnwc9AoGAGccxv9h2jD4Sdy/+Zm1+d3gg6mO5c5P3wDhImoHzE7LxXg8u4ucCPoldjo8hYyCJf5y0Pqb4OFffgs8WAIWIkvKGwbG0/+VTjtypWC2xI5S+KPgL1Y7DTXXYG4RP4/ts34DN2ivJnA+jLTRbsd+0Sln7QdTg9jSDhtByE48UzwECgYAdDoKfK7ybk4nUIgD0OfgRE/mrsmT/5Es3VrDMdX5A/SO0VgAdI/1YdDegoStX3R9XenIJvLV2kG2/dq+YZy5fMX8XQI/YoIhNuonTPE5cGj7s5rjZ3BaQ4V1L68w2E/LEZyh7miuPCEw/w/jln33kjUybWT7ECiwBL2M8F4OJOw==}";
		
		String decStr = PayCenterRSA.decryptRSA(data, private_key);
		String new_str = java.net.URLDecoder.decode(decStr, "utf-8");
		
		System.out.println(new_str);
		
	}

	public List getRuleExeLog(String order_id, String rule_id, String exeResult) {
		String logSql = "select * from es_rule_exe_log where obj_id='" + order_id + "' and rule_id='" + rule_id + "' and exe_result = '" + exeResult + "' ";
		List list = daoSupport.queryForList(logSql);
		return list;
	}

	public CustInfoModRsp custInfoMod(CustInfoModReq req) {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CustInfoModRsp resp = client.execute(req, CustInfoModRsp.class);
		if ("".equals(resp.getError_code())) {

		}
		return resp;
	}

	/**
	 * 固移融合单状态更新,入参校验
	 * 
	 * @param req
	 *            输入参数
	 * @return 工单数据封装类
	 * @throws Exception
	 */
	private OrderWorksBusiRequest doMixOrdCheck(WorkOrderMixOrdUpdateReq req) throws Exception {
		String work_order_id = req.getWork_order_id();// 获取工单号
		String field_survey_result = req.getField_survey_result();// 0 失败；1 成功
		String sys_option = req.getSys_option();
		String bus_order_id = req.getBus_order_id();

		// 校验
		if (StringUtils.isEmpty(work_order_id)) {
			throw new Exception("更新失败:work_order_id为空");
		}

		if (StringUtils.isEmpty(field_survey_result)) {
			throw new Exception("更新失败:field_survey_result为空");
		}

		if (StringUtils.isEmpty(sys_option)) {
			throw new Exception("更新失败:sys_option为空");
		}

		if (!"0".equals(sys_option) && !"1".equals(sys_option)) {
			throw new Exception("更新失败:sys_option不支持枚举值" + sys_option);
		}

		if (StringUtils.isEmpty(bus_order_id)) {
			throw new Exception("更新失败:bus_order_id为空");
		}

		OrderWorksBusiRequest work = ordWorkManager.queryWorkByWorkOrderId(work_order_id);

		if (null == work) {
			throw new Exception("更新失败:根据工单号" + work_order_id + "未找到工单");
		}

		// 0 — 未处理 1 — 处理成功 2 — 处理失败
		if ("1".equals(work.getStatus())) {
			throw new Exception("更新失败:处理成功的工单不允许再次更新");
		}

		return work;
	}

	/**
	 * 更新操作员信息
	 * 
	 * @param requ
	 *            固移融合单状态更新接口，请求数据封装类
	 * @param fieldsMap
	 *            字段MAP
	 * @param order_id
	 *            订单编号
	 */
	private void doUpdateDeveloperInfo(WorkOrderMixOrdUpdateReq requ, Map<String, Object> fieldsMap, String order_id) {
		// 操作员节点信息
		WorkDeveloperInfo developer_info = requ.getDeveloper_info();

		if (null != developer_info) {
			String deal_office_id = developer_info.getDeal_office_id();// 操作点
			String deal_operator = developer_info.getDeal_operator();// 操作员编码
			String deal_operator_name = developer_info.getDeal_operator_name();// 操作员姓名
			String deal_operator_num = developer_info.getDeal_operator_num();// 操作员号码

			if (!StringUtils.isEmpty(deal_office_id)) {
				fieldsMap.put("operator_office_id", deal_office_id);
			}
			if (!StringUtils.isEmpty(deal_operator)) {
				fieldsMap.put("operator_id", deal_operator);
			}
			if (!StringUtils.isEmpty(deal_operator_name)) {
				fieldsMap.put("operator_name", deal_operator_name);
			}
			if (!StringUtils.isEmpty(deal_operator_num)) {
				fieldsMap.put("operator_num", deal_operator_num);
			}

			String develop_point_code = developer_info.getDevelop_point_code();// 发展点编码
			String develop_point_name = developer_info.getDevelop_point_name();// 发展点名称
			String develop_code = developer_info.getDevelop_code();// 发展人编码
			String develop_name = developer_info.getDevelop_name();// 发展人姓名
			String referee_num = developer_info.getReferee_num();// 推荐人号码/推荐人号码（联系方式）
			String referee_name = developer_info.getReferee_name();// 推荐人名称
			String county_id = developer_info.getCounty_id();// 营业县分编码--根据业务具体确认county_id
			// 渠道类型01：营业渠道02：行销渠道03：代理渠道 用于支付是判断发起方
			String deal_office_type = developer_info.getDeal_office_type();
			String channelType = developer_info.getChannelType();

			String[] names = new String[30];
			String[] values = new String[30];

			int i = 0;

			if (!StringUtil.isEmpty(develop_point_code)) {
				names[i] = "development_point_code";
				values[i] = develop_point_code;
				i++;
			}
			if (!StringUtil.isEmpty(develop_point_name)) {
				names[i] = "development_point_name";
				values[i] = develop_point_name;
				i++;
			}
			if (!StringUtil.isEmpty(develop_code)) {
				names[i] = "development_code";
				values[i] = develop_code;
				i++;
			}
			if (!StringUtil.isEmpty(develop_code)) {
				names[i] = "development_name";
				values[i] = develop_name;
				i++;
			}
			if (!StringUtil.isEmpty(referee_num)) {
				names[i] = "recommended_phone";
				values[i] = referee_num;
				i++;
			}
			if (!StringUtil.isEmpty(referee_name)) {
				names[i] = "recommended_name";
				values[i] = referee_name;
				i++;
			}
			if (!StringUtil.isEmpty(county_id)) {
				names[i] = "county_id";
				values[i] = county_id;
				i++;
			}
			if (!StringUtil.isEmpty(deal_office_type)) {
				names[i] = "spread_channel";
				values[i] = deal_office_type;
				i++;
			}
			if (!StringUtils.isEmpty(channelType)) {
				names[i] = AttrConsts.CHANNEL_TYPE;
				values[i] = channelType;
				i++;
			}

			if (i > 0) {
				String[] name = new String[i];
				String[] value = new String[i];

				for (int j = 0; j < i; j++) {
					name[j] = names[j];
					value[j] = values[j];
				}

				// 更新字段
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);
			}
		}
	}

	/**
	 * 保存固移融合单信息
	 * 
	 * @param req
	 *            固移融合请求对象封装类
	 * @param order_id
	 *            订单编号
	 */
	private void doSaveMixOrdInfo(WorkOrderMixOrdUpdateReq req, String order_id) {
		String sys_option = req.getSys_option();// 系统类型
		String bus_order_id = req.getBus_order_id();// 工单编号

		logger.info("固移融合单状态更新接口，订单编号：" + order_id);
		logger.info("固移融合单状态更新接口，sys_option=" + sys_option);
		logger.info("固移融合单状态更新接口，bus_order_id=" + bus_order_id);

		String[] fieldNameArr = null;// 更新的属性名称数组
		String[] fieldValueArr = null;// 更新的属性值数组

		if ("0".equals(sys_option)) {

			fieldNameArr = new String[] { AttrConsts.SYS_OPTION, AttrConsts.BUS_ORDER_ID, AttrConsts.BSSORDERID };
			fieldValueArr = new String[] { sys_option, bus_order_id, bus_order_id };
		} else {

			fieldNameArr = new String[] { AttrConsts.SYS_OPTION, AttrConsts.BUS_ORDER_ID, AttrConsts.ACTIVE_NO };
			fieldValueArr = new String[] { sys_option, bus_order_id, bus_order_id };
		}

		// 更新订单属性
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, fieldNameArr, fieldValueArr);
	}

	/**
	 * 执行固移融合单校验规则
	 * 
	 * @param order_id
	 *            订单编号
	 * @return 执行结果
	 */
	private String exeMixOrdCheckRule(String order_id) {
		String flag = "0";
		String rule_id = "";

		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		// 规则编号
		rule_id = cacheUtil.getConfigInfo("MIXORD_CHECK_RULE");

		RuleTreeExeReq requ = new RuleTreeExeReq();
		TacheFact fact = new TacheFact();

		fact.setOrder_id(order_id);

		requ.setRule_id(rule_id);
		requ.setFact(fact);
		requ.setCheckAllRelyOnRule(true);
		requ.setCheckCurrRelyOnRule(true);

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		// 执行规则
		RuleTreeExeResp rsp = client.execute(requ, RuleTreeExeResp.class);

		if (!"0".equals(rsp.getError_code())) {
			flag = rsp.getError_code() + rsp.getError_msg();
		}

		return flag;
	}

	@Override
	public WorkOrderMixOrdUpdateResp doWorkOrderMixOrdUpdate(WorkOrderMixOrdUpdateReq requ) throws Exception {

		WorkOrderMixOrdUpdateResp resp = new WorkOrderMixOrdUpdateResp();
		resp.setResp_code("1");// 初始化失败标记

		String work_order_id = requ.getWork_order_id();// 获取工单号
		String field_survey_result = requ.getField_survey_result();// 获取返回结果 0
																	// 失败；1 成功
		String remark = requ.getRemark();

		OrderWorksBusiRequest work = null;

		// 请求对象校验
		work = this.doMixOrdCheck(requ);

		// 订单编号
		String order_id = work.getOrder_id();
		
		Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SGF 意向单
		if(!StringUtil.isEmpty(work.getNode_ins_id())){//如果为空则不是为意向单所产生的工单
		    String info = requ.getWork_order_id()+"|"+requ.getField_survey_result()+"|"+requ.getRemark();
		    if("1".equals(field_survey_result)){//返回成功
		        if("0".equals(requ.getSys_option())){//0 bss 1 cbss
		            resp = this.bssOrderCheckByIntent(requ,order_id);
		        }else if("1".equals(requ.getSys_option())){
		            resp = this.cbssOrderCheckByIntent(requ,order_id);
                }else if(!"0".equals(requ.getSys_option()) || !"1".equals(requ.getSys_option()) ){
                    throw new Exception("更新失败:" + work.getWork_order_id() + "工单返回系统类型不正确");
                }
		        if("0".equals(resp.getResp_code())){//b或者cb的成功
		            Map<String, Object> setMap = new HashMap<String, Object>();
	                setMap.put("status","1");
	                setMap.put("result_remark",info);
	                setMap.put("update_time",new Date());
	                Map<String, Object> whileMap = new HashMap<String, Object>();
	                whileMap.put("work_order_id",work_order_id);
	                daoSupport.update("es_work_order", setMap,whileMap);
	                
	                Map<String, Object> setMapIntent = new HashMap<String, Object>();
	                setMapIntent.put("status","03");
	                setMapIntent.put("remark",info);
                    Map<String, Object> whileMapIntent = new HashMap<String, Object>();
                    whileMapIntent.put("order_id",order_id);
                    daoSupport.update("es_order_intent", setMapIntent,whileMapIntent);
	                this.workCustomEngine.runNodeManual(work.getNode_ins_id(), true, null, info,null);
	                resp.setResp_msg("更新成功");
		        }
		    }else{//处理失败  工单表为处理失败
		        Map<String, Object> setMap = new HashMap<String, Object>();
		        setMap.put("status","2");
		        setMap.put("result_remark",info);
		        setMap.put("update_time",new Date());
		        
		        Map<String, Object> whileMap = new HashMap<String, Object>();
		        whileMap.put("work_order_id",work_order_id);
		        daoSupport.update("es_work_order", setMap,whileMap);
		        this.changNodeInsInfo(work.getNode_ins_id(),info);
		        resp.setResp_code("0");
		        resp.setResp_msg("更新成功");
		    }
		}else{
		    // 工单更新字段MAP
		    Map<String, Object> fieldsMap = new HashMap<String, Object>();
		    JSONObject jsonObject = JSONObject.fromObject(requ);
		    
		    fieldsMap.put("update_time", new Date());
		    fieldsMap.put("req_xml", jsonObject.toString());
		    
		    // 沉淀订单中传入的系统（BSS或CBSS），和订单编号（BSS或CBSS系统的订单编号）
		    if ("06".equals(work.getType()) && "1".equals(field_survey_result)) {
		        this.doSaveMixOrdInfo(requ, order_id);
		    } else {
		        throw new Exception("更新失败:" + work.getWork_order_id() + "工单的工单类型不为固移融合单");
		    }
		    
		    // 更新操作员信息
		    this.doUpdateDeveloperInfo(requ, fieldsMap, order_id);
		    
		    // 更新orderTree
		    OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		    
		    orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		    orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		    
		    orderTree.store();
		    
		    // 执行规则
		    String flag = this.exeMixOrdCheckRule(order_id);
		    
		    if (!"0".equals(flag)) {
		        throw new Exception("更新失败:" + flag);
		    }
		    
		    // 0 失败；1 成功
		    // 0 — 未处理 1 — 处理成功 2 — 处理失败
		    if ("0".equals(field_survey_result)) {
		        fieldsMap.put("status", 2);
		    } else if ("1".equals(field_survey_result)) {
		        fieldsMap.put("status", 1);
		    }
		    
		    if (!StringUtils.isEmpty(remark)) {
		        fieldsMap.put("result_remark", remark);
		    }
		    
		    Map<String, Object> whereMap = new HashMap<String, Object>();
		    whereMap.put("work_order_id", work_order_id);
		    
		    // 更新es_work_order工单表
		    daoSupport.update("es_work_order", fieldsMap, whereMap);
		    
		    resp.setResp_code("0");// 成功标记
		    resp.setResp_msg("更新成功");
		}

		return resp;
	}
	/**
	 *更改信息es_custom_node_ins表数据信息 
	 * @param order_id
	 * @param info 
	 * @throws Exception 
	 */
	private void changNodeInsInfo(String node_ins_id, String info) throws Exception {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    ES_WORK_CUSTOM_NODE_INS pojo = new ES_WORK_CUSTOM_NODE_INS();
	    pojo.setInstance_id(node_ins_id);
	    pojo.setRemark(info);
	    pojo.setUpdate_date(time.format(new Date()));
	    List<ES_WORK_CUSTOM_NODE_INS> list = new ArrayList<ES_WORK_CUSTOM_NODE_INS>();
	    list.add(pojo);
        workCustomCfgManager.updateNodeInstances(list);
    }

    /**
	 * 调用cbss接口 进行单号校验
	 * sgf
	 * @version 1.0
	 * @param requ 
	 */
    private WorkOrderMixOrdUpdateResp cbssOrderCheckByIntent(WorkOrderMixOrdUpdateReq requ,String order_ids) {
        String sql = "select order_city_code,source_system_type from es_order_intent where order_id='"+order_ids+"' and source_from='"+ManagerUtils.getSourceFrom()+"'";
        List<Map> intent_list = daoSupport.queryForList(sql);
        Map<String, Object> map = new HashMap<String, Object>();
        if(intent_list.size()>0){
            String order_city_code =  intent_list.get(0).get("order_city_code")+"";
            String source_system_type = intent_list.get(0).get("source_system_type")+"";
            if(!StringUtil.isEmpty(order_city_code)){
                map.put("order_city_code", order_city_code);
            }else{
                map.put("order_city_code", "");
            }
            if(!StringUtil.isEmpty(source_system_type)){
                map.put("source_system_type", source_system_type);
            }else{
                map.put("source_system_type", "");
            }
        }
        WorkOrderMixOrdUpdateResp workOrderMixOrdUpdateResp = new WorkOrderMixOrdUpdateResp();
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        IntentOrderQueryForCBReq req = new IntentOrderQueryForCBReq();
        req.setNotNeedReqStrOrderId(order_ids);
        req.setOrdersId(requ.getBus_order_id());
        WorkDeveloperInfo developer_info = requ.getDeveloper_info();
        if(!StringUtil.isEmpty(developer_info.getDevelop_code())){
            map.put("development_code", developer_info.getDevelop_code());
        }else{
            map.put("development_code", "");
        }
        if(!StringUtil.isEmpty(developer_info.getDevelop_name())){
            map.put("development_name", developer_info.getDevelop_name());
        }else{
            map.put("development_name", "");
        }
        if(!StringUtil.isEmpty(developer_info.getDeal_operator())){
            map.put("deal_operator", developer_info.getDeal_operator());
        }else{
            map.put("deal_operator", "");
        }
        if(!StringUtil.isEmpty(developer_info.getDeal_office_id())){
            map.put("deal_office_id", developer_info.getDeal_office_id());
        }else{
            map.put("deal_office_id", "");
        }
        if(!StringUtil.isEmpty(developer_info.getChannelType())){
            map.put("channelType", developer_info.getChannelType());
        }else{
            map.put("channelType", "");
        }
        req.setParmas(map);
        OrderQueryRespone resp = client.execute(req, OrderQueryRespone.class);
        if (!resp.getError_code().equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")) {
            workOrderMixOrdUpdateResp.setResp_code("1");
            workOrderMixOrdUpdateResp.setResp_msg(resp.getError_msg() +resp.getDetail());
            return workOrderMixOrdUpdateResp;
        } else {
            if (null != resp.getOrdiInfo() && resp.getOrdiInfo().size() > 0) {
                String bssOrderId = requ.getBus_order_id();
                for (OrderInfoRespVo orderInfo : resp.getOrdiInfo()) {
                    if (bssOrderId.equals(orderInfo.getOrdersId())) {//单号需要相同
                        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
                        String cbssOrderCheck = cacheUtil.getConfigInfo("cbssOrderCheck");
                        // 订单状态0：订单成功1：订单失败2：提交未制卡3：订单已返销4：其它
                        if (cbssOrderCheck.contains(orderInfo.getOrderCode())) {
                            workOrderMixOrdUpdateResp.setResp_code("0");
                            workOrderMixOrdUpdateResp.setResp_msg("CBSS订单校验成功");
                            return workOrderMixOrdUpdateResp;
                        }
                    }
                }
            }
        }
        workOrderMixOrdUpdateResp.setResp_code(resp.getCode());
        workOrderMixOrdUpdateResp.setResp_msg(resp.getDetail() + resp.getError_msg());
        return workOrderMixOrdUpdateResp;
        
    }
    /**
     *调用bss接口   进行单号校验 
     *sgf
     * @version 1.0
     * @param requ 
     * @param order_ids 
     */
    private WorkOrderMixOrdUpdateResp bssOrderCheckByIntent(WorkOrderMixOrdUpdateReq requ, String order_ids) {
        WorkOrderMixOrdUpdateResp workOrderMixOrdUpdateResp = new WorkOrderMixOrdUpdateResp();
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        OrderStatusQryReq req = new OrderStatusQryReq();
        req.setNotNeedReqStrOrderId(order_ids);
        req.setBms_accept_id(requ.getBus_order_id());
        OrderStatusQryResp resp = client.execute(req, OrderStatusQryResp.class);
        if (!resp.getCode().equals(EcsOrderConsts.INF_RESP_CODE_00000)) {
           workOrderMixOrdUpdateResp.setResp_code("1");
           workOrderMixOrdUpdateResp.setResp_msg(resp.getMsg()+resp.getError_msg());
           return workOrderMixOrdUpdateResp;
        } else {
            if (null != resp.getRespJson()) {
                ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
                String bssOrderCheck = cacheUtil.getConfigInfo("bssOrderCheck");
                String order_status = resp.getRespJson().getOrder_status();// 0预订单生成;4已作废;8正在执行;9已竣工
                if (bssOrderCheck.contains(order_status)) {// 正常
                    workOrderMixOrdUpdateResp.setResp_code("0");
                    workOrderMixOrdUpdateResp.setResp_msg("BSS订单校验成功");
                    return workOrderMixOrdUpdateResp;
                } 
            } 
        }
        workOrderMixOrdUpdateResp.setResp_code(resp.getCode());
        workOrderMixOrdUpdateResp.setResp_msg(resp.getError_msg()+resp.getMsg());
        return workOrderMixOrdUpdateResp;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes" })
    public Map getOrderStateAndLockName(String order_id) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select o.order_state, (select realname from es_adminuser where userid = ol.lock_user_id) as lock_user_name from es_order_ext oe left join es_order o on o.order_id = oe.order_id left join es_order_lock ol ");
        sqlBuilder.append(" on oe.order_id = ol.order_id and oe.flow_trace_id = ol.tache_code  ");
        sqlBuilder.append(" where oe.order_id = '"+order_id+"' ");
        sqlBuilder.append(" and oe.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
        List list =  baseDaoSupport.queryForListByMap(sqlBuilder.toString(), null);
           Map map = (Map) list.get(0);
           if(map.get("lock_user_name")==null){
               map.put("lock_user_name", "");
           }
           if(map.get("order_state")==null){
               map.put("order_state", "");
           }
           return map;
    }
    
    private String getStringValue(Object v){
		if(v == null)
			return "";
		else
			return String.valueOf(v);
	}
	
	private Set<String> filteOrderIds(List<OrderListQueryResp> query_resp,Flow_Info_Cfg cfg,
			Set<String> customOrderIdSet) throws Exception{
		Set<String> orderIdSet = new HashSet<String>();
		
		Set<String> catSet = new HashSet<String>();
		
		for(int i=0;i<cfg.getCats().size();i++){
			catSet.add(cfg.getCats().get(i).getGoods_cat_id());
		}
		
		for(OrderListQueryResp orderInfo : query_resp){
			String catId = orderInfo.getGoods_cat_id();
			
			if(catSet.contains(catId) && 
					(!customOrderIdSet.contains(orderInfo.getOrder_id()))){
				orderIdSet.add(orderInfo.getOrder_id());
			}
		}
		
		return orderIdSet;
	}
	
	private void getFlowInfo(List<OrderListQueryResp> query_resp) throws Exception{
		if(query_resp==null || query_resp.size()==0)
			return;
		
		//查询配置
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		String strCfgJson = cacheUtil.getConfigInfo("QUERY_ORDERlIST_FLOW_CFG");
		
		if(StringUtil.isEmpty(strCfgJson))
			return;
		
		StringBuilder builder = new StringBuilder();
		builder.append(strCfgJson);
		
		for(int i=1;i<=10;i++){
			String key = "QUERY_ORDERlIST_FLOW_CFG_"+i;
			
			String str = cacheUtil.getConfigInfo(key);
			
			if(org.apache.commons.lang.StringUtils.isBlank(str))
				continue;
			else
				builder.append(str);
		}
		
		Map<String,Class<?>> classMap = new HashMap<String,Class<?>>();
		classMap.put("cats", Flow_Info_Cat_Cfg.class);
		classMap.put("nodes", Flow_Info_Node_Cfg.class);
		classMap.put("rules", Flow_Info_Rule_Cfg.class);
		JSONObject jsonObj = JSONObject.fromObject(builder.toString());//将json字符串转换为json对象
		
		//将配置JSON转化为配置对象
		Flow_Info_Cfg cfg = (Flow_Info_Cfg)JSONObject.toBean(jsonObj,Flow_Info_Cfg.class,classMap);//将建json对象转换为对象
		
		if(cfg==null || cfg.getCats().size()==0)
			return;
		
		Set<String> customOrderIdSet = this.getWorkCustomFlowInfo(query_resp);
		
		//根据订单的商品小类过滤订单编号
		Set<String> filteOrderIdSet = this.filteOrderIds(query_resp, cfg,customOrderIdSet);
		
		if(filteOrderIdSet==null || filteOrderIdSet.size()==0)
			return;
		
		//环节配置
		Map<String,Flow_Info_Node_Cfg> nodeCfgMap = this.getNodeCfgMap(cfg);
		
		List<String> ruleIds = this.getRuleIds(cfg);
		
		RuleLogReq req = new RuleLogReq();
		req.setOrderIds(new ArrayList<String>(filteOrderIdSet));
		req.setRuleIds(ruleIds);
		
		//查询规则执行记录
		RuleLogRsp ret = ruleService.qryRuleLog(req);
		
		Map<String,List<RuleExeLog>> orderRuleLogMap = this.getOrderRulelogsMap(ret.getRuleLogs());
		
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
			
		for(int i=0;i<query_resp.size();i++){
			OrderListQueryResp orderInfo = query_resp.get(i);
			
			String orderId = orderInfo.getOrder_id();
			
			if(filteOrderIdSet.contains(orderId)){
				String traceId = orderInfo.getFlow_trace_id();
				String catId = orderInfo.getGoods_cat_id();
				String nodeKey = catId+"_"+traceId;
				
				String curr_node = "receive";
				String curr_rule = "";
				String exe_status = "receiving";
				String error_msg = "订单处理中，请稍后查询";
				
				if(nodeCfgMap.containsKey(nodeKey)){
					Flow_Info_Node_Cfg nodeCfg = nodeCfgMap.get(nodeKey);

					String retNodeCode = nodeCfg.getRet_code();
					String isDoing = nodeCfg.getIsDoing();
					
					//设置当前环节
					curr_node = retNodeCode;
					
					if(!"yes".equals(isDoing)){
						//已经不在业务环节，说明业务已完成
						exe_status = "done";
						error_msg = "";
					}else{
						List<Flow_Info_Rule_Cfg> ruleCfgs = nodeCfg.getRules();
						
						if(ruleCfgs==null || ruleCfgs.size()==0){
							exe_status = "todo";
							error_msg = catId+"订单小类"+retNodeCode+"环节配置的过滤规则为空，请联系管理员";
						}else{
							//规则排序
							Collections.sort(ruleCfgs,new Comparator<Flow_Info_Rule_Cfg>(){
					            public int compare(Flow_Info_Rule_Cfg arg0, Flow_Info_Rule_Cfg arg1) {
					            	Integer index0 = Integer.valueOf(arg0.getRule_index());
					            	Integer index1 = Integer.valueOf(arg1.getRule_index());
					            	
					            	if(index0 >= index1){
					            		return 1;
					            	}else{
					            		return -1;
					            	}
					            }
					        });
							
							//匹配规则执行记录
							for(int j=0;j<ruleCfgs.size();j++){
								Flow_Info_Rule_Cfg ruleCfg = ruleCfgs.get(j);
								
								//设置当前规则
								curr_rule = ruleCfg.getRule_code();
								
								String ruleId = ruleCfg.getRule_id();
								String key = orderId+"_"+ruleId;
								
								//异常时联系管理员标识
								boolean isContactAdmin = false;
								if("yes".equals(ruleCfg.getContact_admin()))
									isContactAdmin = true;
								
								if(!orderRuleLogMap.containsKey(key)){
									//没有当前规则的执行记录，说明规则等待执行
									exe_status = "todo";
									error_msg = "";
									//不在判断下一条规则执行记录
									break;
								}
									
								List<RuleExeLog> logs = orderRuleLogMap.get(key);
								
								Date errorLogDate = null;
								
								for(RuleExeLog log : logs){
									if("0".equals(String.valueOf(log.getExe_result()))){
										//规则执行记录--已执行
										exe_status = "done";
										error_msg = "";
										break;
									}else if("-1".equals(String.valueOf(log.getExe_result()))){
										//规则执行记录--异常
										exe_status = "error";
										
										if(errorLogDate==null){
											//设置异常信息
											error_msg = log.getResult_msg();
											
											if(isContactAdmin)
												error_msg += "，请联系管理员";
											
											try{
												errorLogDate = format.parse(log.getCreate_time());
											}catch(Exception e){
												
											}
										}else{
											try{
												Date logDate = format.parse(log.getCreate_time());
												
												//比较时间
												if(logDate.getTime() > errorLogDate.getTime()){
													errorLogDate = logDate;
													//设置异常信息
													error_msg = log.getResult_msg();
													
													if(isContactAdmin)
														error_msg += "，请联系管理员";
												}
											}catch(Exception e){
												
											}
										}
									}else{
										//其它情况，规则已执行，判断下一条执行记录
										exe_status = "done";
										error_msg = log.getResult_msg();
									}
								}
								
								//规则执行异常，没有执行成功的记录
								if("error".equals(exe_status))
									//不匹配下一条规则的执行日志
									break;
							}
						}
					}
				}
				
				orderInfo.setCurr_node(curr_node);
				orderInfo.setCurr_rule(curr_rule);
				orderInfo.setExe_status(exe_status);
				orderInfo.setError_msg(error_msg);
			}
		}
	}
	
	private Map<String,List<RuleExeLog>> getOrderRulelogsMap(List<RuleExeLog> ruleLogs) throws Exception{
		Map<String,List<RuleExeLog>> orderRulelogsMap = new HashMap<String, List<RuleExeLog>>();
		
		for(int i=0;i<ruleLogs.size();i++){
			RuleExeLog log = ruleLogs.get(i);

			String orderId = log.getObj_id();
			String ruleId = log.getRule_id();
			String key = orderId+"_"+ruleId;
			
			if(orderRulelogsMap.containsKey(key)){
				List<RuleExeLog> logs = orderRulelogsMap.get(key);
				logs.add(log);
				
				orderRulelogsMap.put(key, logs);
			}else{
				List<RuleExeLog> logs = new ArrayList<RuleExeLog>();
				logs.add(log);
				
				orderRulelogsMap.put(key, logs);
			}
		}
		
		return orderRulelogsMap;
	}
	
	private List<String> getRuleIds(Flow_Info_Cfg cfg) throws Exception{
		List<String> ruleIds = new ArrayList<String>();
		
		for(int i=0;i<cfg.getCats().size();i++){
			Flow_Info_Cat_Cfg catCfg = cfg.getCats().get(i);
			
			List<Flow_Info_Node_Cfg> nodes = catCfg.getNodes();
			
			if(nodes!=null && nodes.size()>0){
				for(int j=0;j<nodes.size();j++){
					Flow_Info_Node_Cfg nodeCfg = nodes.get(j);
					
					List<Flow_Info_Rule_Cfg> rules = nodeCfg.getRules();
					
					if(rules!=null && rules.size()>0){
						for(int k=0;k<rules.size();k++){
							Flow_Info_Rule_Cfg rule = rules.get(k);
							
							String ruleId = rule.getRule_id();
							
							ruleIds.add(ruleId);
						}
					}
				}
			}
		}
		
		return ruleIds;
	}
	
	private Map<String,Flow_Info_Node_Cfg> getNodeCfgMap(Flow_Info_Cfg cfg) throws Exception{
		Map<String,Flow_Info_Node_Cfg> nodeCfgMap = new HashMap<String,Flow_Info_Node_Cfg>();
		
		for(int i=0;i<cfg.getCats().size();i++){
			Flow_Info_Cat_Cfg catCfg = cfg.getCats().get(i);
			
			String catId = catCfg.getGoods_cat_id();
			List<Flow_Info_Node_Cfg> nodes = catCfg.getNodes();
			
			if(nodes!=null && nodes.size()>0){
				for(int j=0;j<nodes.size();j++){
					Flow_Info_Node_Cfg nodeCfg = nodes.get(j);
					
					String traceid = nodeCfg.getNode_code();
					
					String key = catId+"_"+traceid;
					
					nodeCfgMap.put(key, nodeCfg);
				}
			}
		}
		
		return nodeCfgMap;
	}
	
	/**
	 * 获取自定义流程环节信息
	 * @param query_resp
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private Set<String> getWorkCustomFlowInfo(List<OrderListQueryResp> query_resp) throws Exception{
		Set<String> orderIdSet = new HashSet<String>();
		
		if(query_resp==null || query_resp.size()==0)
			return orderIdSet;
		
		List<String> addIds = new ArrayList<String>();
		for(OrderListQueryResp orderInfo : query_resp){
			addIds.add(orderInfo.getOrder_id());
		}
		
		if(addIds.size() == 0)
			return orderIdSet;
		
		//查询自定义流程的订单
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT a.order_id,a.flow_trace_id,a.is_work_custom FROM es_order_ext a ");
		builder.append(" where 1=1 ");
		builder.append(SqlUtil.getSqlInStr("a.order_id", addIds, false, true));
		
		List ret = this.baseDaoSupport.queryForList(builder.toString());
		
		Map<String,String> orderIdFlowTraceMap = new HashMap<String, String>();
		
		if(ret!=null && ret.size()>0){
			//订单所在流程的MAP
			for(int i=0;i<ret.size();i++){
				Map m = (Map)ret.get(i);
				
				String order_id = String.valueOf(m.get("order_id"));
				String flow_trace_id = String.valueOf(m.get("flow_trace_id"));
				String is_work_custom = String.valueOf(m.get("is_work_custom"));
				
				if("1".equals(is_work_custom)){
					orderIdFlowTraceMap.put(order_id, flow_trace_id);
				}
			}
			
			//查询订单所在步骤
			if(orderIdFlowTraceMap.size() > 0){
				List<String> ids = new ArrayList<String>(orderIdFlowTraceMap.keySet());
				
				List<SqlBuilderInterface> sqlBuilds = new ArrayList<SqlBuilderInterface>();
				SqlInBuilder orderId_conditin = new SqlInBuilder("order_id", ids);
				sqlBuilds.add(orderId_conditin);
				
				ES_WORK_CUSTOM_NODE_INS param = new ES_WORK_CUSTOM_NODE_INS();
				param.setIs_curr_step("1");
				
				List<ES_WORK_CUSTOM_NODE_INS> curr_nodes = this.workCustomCfgManager.qryInsList(param, sqlBuilds);
				Map<String,ES_WORK_CUSTOM_NODE_INS> currNodeMap = new HashMap<String, ES_WORK_CUSTOM_NODE_INS>();
				
				if(curr_nodes!=null && curr_nodes.size()>0){
					for(int i=0;i<curr_nodes.size();i++){
						currNodeMap.put(curr_nodes.get(i).getOrder_id(), curr_nodes.get(i));
					}
				}
				
				for(int i=0;i<query_resp.size();i++){
					OrderListQueryResp orderInfo = query_resp.get(i);
					
					String order_id = orderInfo.getOrder_id();
					
					if(orderIdFlowTraceMap.containsKey(order_id)){
						String curr_node = "";
						String curr_rule = "";
						String exe_status = "";
						String error_msg = "";
						
						if(currNodeMap.containsKey(order_id)){
							//当前环节表能查到步骤的，说明正在执行
							String flowTraceId = orderIdFlowTraceMap.get(order_id);
							
							if("D".equals(flowTraceId)){
								curr_node = "open";
							}else if("X".equals(flowTraceId)){
								curr_node = "wirte_card";
							}else{
								curr_node = "busi_done";
							}
							
							ES_WORK_CUSTOM_NODE_INS ins = currNodeMap.get(order_id);
							String deal_url = ins.getDeal_url();
							
							if(this.workCustomCfgManager.isUpdateOrderNode(deal_url)){
								//如果是等待更新订单数据的环节
								curr_node = "receive";
							}
							
							curr_rule = ins.getOut_node_code();
							if("1".equals(ins.getIs_done())){
								exe_status = "done";
							}else if("error".equals(ins.getState_code())){
								exe_status = "error";
								error_msg = ins.getRemark();
							}else if("receive".equals(curr_node)){
								exe_status = "receiving";
							}else{
								exe_status = "todo";
							}
						}else{
							//当前环节表不能查到步骤的，说明订单已完成或者取消
							curr_node = "busi_done";
							curr_rule = "";
							exe_status = "done";
							error_msg = "";
						}

						orderInfo.setCurr_node(curr_node);
						orderInfo.setCurr_rule(curr_rule);
						orderInfo.setExe_status(exe_status);
						orderInfo.setError_msg(error_msg);
					}
				}
			}
		}
		
		return orderIdFlowTraceMap.keySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public GeneralOrderQueryResp generalOrderQuery(GeneralOrderQueryReq req) {
		GeneralOrderQueryResp resp = new GeneralOrderQueryResp();
		List<GeneralOrderInfo> query_resp = new ArrayList<GeneralOrderInfo>();
		resp.setOrder_info(query_resp);
		if(req ==null){
			resp.setTotal_count("0");
			resp.setResp_code("1");
			resp.setResp_msg("参数不能为空");
			return resp;
		}
		if(req != null && StringUtil.isEmpty(req.getQuery_type())){
			resp.setTotal_count("0");
			resp.setResp_code("1");
			resp.setResp_msg("查询类型query_type不能为空");
			return resp;
		}
		if(req != null && !StringUtil.isEmpty(req.getQuery_para()) && !("01".equals(req.getQuery_type()) || "02".equals(req.getQuery_type()) || "03".equals(req.getQuery_type()))){
			resp.setTotal_count("0");
			resp.setResp_code("1");
			resp.setResp_msg("查询参数query_type传值错误");
			return resp;
		}
		if(req != null && StringUtil.isEmpty(req.getQuery_para())){
			resp.setTotal_count("0");
			resp.setResp_code("1");
			resp.setResp_msg("查询参数Query_para不能为空");
			return resp;
		}
		if(req != null && (StringUtil.isEmpty(req.getPage_no())||StringUtil.isEmpty(req.getPage_size()))){
			resp.setTotal_count("0");
			resp.setResp_code("1");
			resp.setResp_msg("查询条数Page_no和页数tPage_size不能为空");
			return resp;
		}
		StringBuffer listSql = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		
		String sql = createSqlGeneral(req);
		countSql.append("select count(1)  from (").append(sql).append(") where source_from='").append(ManagerUtils.getSourceFrom()).append("' order by order_time desc,order_id desc");
		int sum = Integer.parseInt(daoSupport.queryForString(countSql.toString()));
		resp.setTotal_count(sum + "");
		resp.setResp_code("0");
		resp.setResp_msg("查询成功");
		listSql.append("select *  from (").append(sql).append(") where source_from='").append(ManagerUtils.getSourceFrom()).append("' order by order_time desc,order_id desc");
		if(sum>0){
			int pageNo = 1;
			int pageSize = 10;
			try {
				pageNo = Integer.parseInt(req.getPage_no());
				pageSize = Integer.parseInt(req.getPage_size());
				if (pageNo < 1 || pageSize < 1) {
					resp.setTotal_count("0");
					resp.setResp_code("1");
					resp.setResp_msg("查询失败:参数page_size,page_no异常");
					return resp;
				}
			} catch (Exception e) {
				resp.setTotal_count("0");
				resp.setResp_code("1");
				resp.setResp_msg("查询失败:参数page_size,page_no转换异常");
				return resp;
			}
			Page page = this.baseDaoSupport.queryForPage(listSql.toString(), pageNo, pageSize, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map<String, String> map = new HashMap<String, String>();
					map.put("out_order_id", rs.getString("out_order_id")); 
					map.put("order_time", rs.getString("order_time"));
					map.put("order_id", rs.getString("order_id"));
					map.put("order_type", rs.getString("order_type"));
					map.put("acc_nbr", rs.getString("acc_nbr"));
					map.put("ship_name", rs.getString("ship_name"));
					map.put("ship_tel", rs.getString("ship_tel"));
					map.put("ship_addr", rs.getString("ship_addr"));
					map.put("ship_tel2", rs.getString("ship_tel2"));
					map.put("ship_province", rs.getString("ship_province"));
					map.put("ship_city", rs.getString("ship_city"));
					map.put("ship_district", rs.getString("ship_district"));
					map.put("cert_num", rs.getString("cert_num"));
					map.put("customer_name", rs.getString("customer_name"));
					map.put("cert_addr", rs.getString("cert_addr"));
					map.put("cust_tel", rs.getString("cust_tel"));
					map.put("customer_type", rs.getString("customer_type"));
					map.put("prod_code", rs.getString("prod_code"));
					map.put("prod_name", rs.getString("prod_name"));
					map.put("cat_id", rs.getString("cat_id"));
					map.put("share_svc_num", rs.getString("share_svc_num"));
					map.put("market_user_id", rs.getString("market_user_id"));
					map.put("seed_user_id", rs.getString("seed_user_id"));
					map.put("top_share_userid", rs.getString("top_share_userid"));
					map.put("top_share_num", rs.getString("top_share_num"));
					map.put("develop_point_code", rs.getString("develop_point_code"));
					map.put("develop_code", rs.getString("develop_code"));
					map.put("deal_operator", rs.getString("deal_operator"));
					map.put("deal_office_id", rs.getString("deal_office_id"));
					return map;
				}
			});
			GeneralOrderInfo respinfo ;
			for (Map map : (List<Map>) page.getResult()) {
				respinfo = new GeneralOrderInfo();
				OrderContacrInfo orderContacrInfo = new OrderContacrInfo();
				OrderCustInfoVO orderCustInfo = new OrderCustInfoVO();
				OrderDeveloperInfoVO orderDeveloperInfo = new OrderDeveloperInfoVO();
				OrderGoodInfo  orderGoodInfo = new OrderGoodInfo();
				OrderPartenersInfo  orderPartenersInfo = new OrderPartenersInfo();
				
				respinfo.setOut_order_id(this.getStringValue(map.get("out_order_id")));
				respinfo.setOrder_id(this.getStringValue(map.get("order_id")));
				respinfo.setOrder_type(this.getStringValue(map.get("order_type")));
				respinfo.setOrder_time(this.getStringValue(map.get("order_time")));
				respinfo.setAcc_nbr(this.getStringValue(map.get("acc_nbr")));
				
				orderContacrInfo.setShip_addr(this.getStringValue(map.get("ship_addr")));
				orderContacrInfo.setShip_city(this.getStringValue(map.get("ship_city")));
				orderContacrInfo.setShip_district(this.getStringValue(map.get("ship_district")));
				orderContacrInfo.setShip_name(this.getStringValue(map.get("ship_name")));
				orderContacrInfo.setShip_province(this.getStringValue(map.get("ship_province")));
				orderContacrInfo.setShip_tel(this.getStringValue(map.get("ship_tel")));
				orderContacrInfo.setShip_tel2(this.getStringValue(map.get("ship_tel2")));
				respinfo.setContact_info(orderContacrInfo);
				
				orderCustInfo.setCert_addr(this.getStringValue(map.get("cert_addr")));
				orderCustInfo.setCert_num(this.getStringValue(map.get("cert_num")));
				orderCustInfo.setCust_tel(this.getStringValue(map.get("cust_tel")));
				orderCustInfo.setCustomer_name(this.getStringValue(map.get("customer_name")));
				orderCustInfo.setCustomer_type(this.getStringValue(map.get("customer_type")));
				respinfo.setCust_info(orderCustInfo);
				
				orderDeveloperInfo.setDeal_office_id(this.getStringValue(map.get("deal_office_id")));
				orderDeveloperInfo.setDeal_operator(this.getStringValue(map.get("deal_operator")));
				orderDeveloperInfo.setDevelop_code(this.getStringValue(map.get("develop_code")));
				orderDeveloperInfo.setDevelop_point_code(this.getStringValue(map.get("develop_point_code")));
				respinfo.setDeveloper_info(orderDeveloperInfo);
				orderGoodInfo.setCat_id(this.getStringValue(map.get("cat_id")));
				orderGoodInfo.setProd_code(this.getStringValue(map.get("prod_code")));
				orderGoodInfo.setProd_name(this.getStringValue(map.get("prod_name")));
				respinfo.setGood_info(orderGoodInfo);
				
				orderPartenersInfo.setMarket_user_id(this.getStringValue(map.get("market_user_id")));
				orderPartenersInfo.setSeed_user_id(this.getStringValue(map.get("seed_user_id")));
				orderPartenersInfo.setShare_svc_num(this.getStringValue(map.get("share_svc_num")));
				orderPartenersInfo.setTop_share_num(this.getStringValue(map.get("top_share_num")));
				orderPartenersInfo.setTop_share_userid(this.getStringValue(map.get("top_share_userid")));
				respinfo.setPartners_info(orderPartenersInfo);
				query_resp.add(respinfo);
			}
			resp.setOrder_info(query_resp);
		}
		return resp;
	}

	private String createSqlGeneral(GeneralOrderQueryReq req) {
		StringBuffer orderTable = new StringBuffer();
		StringBuffer intentTable = new StringBuffer();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String general_sql_intent = cacheUtil.getConfigInfo("general_sql_intent");
        String general_sql_order = cacheUtil.getConfigInfo("general_sql_order");
        String general_sql_kg = cacheUtil.getConfigInfo("general_sql_kg");
        if(!StringUtil.isEmpty(general_sql_kg) && "1".equals(general_sql_kg)&&!StringUtil.isEmpty(general_sql_order) && !StringUtil.isEmpty(general_sql_intent)){
        	intentTable.append(general_sql_intent);
    		intentTable.append("  and t.source_from ='").append(ManagerUtils.getSourceFrom()).append("' ");
    		
    		orderTable.append(general_sql_order);
    		orderTable.append("  where  eoe.source_from ='").append(ManagerUtils.getSourceFrom()).append("' ");
    		logger.info("通用查询sql--配置表sql:"+intentTable.toString()+orderTable.toString());
        }else{
        	intentTable.append(" select t.intent_order_id as out_order_id, ");
        	intentTable.append("       to_char(t.create_time, 'yyyy-mm-dd hh24:mi:ss') as order_time, t.order_id,");
        	intentTable.append("      '1' as order_type, t.acc_nbr,  t.ship_name, t.ship_tel,t.ship_addr, t.ship_tel2,");
        	intentTable.append("       decode(t.order_province_code,'浙江省','330000',t.order_province_code) as ship_province, t.order_city_code as ship_city, substr(t.order_county_code,-6) as ship_district, t.customer_name, t.cert_num, t.cert_addr,  t.cust_tel,");
        	intentTable.append("      t.cert_type as customer_type,     t.goods_id   as prod_code, t.goods_name as prod_name,");
        	intentTable.append("     (select     c.cat_id from es_goods c where c.goods_id = t.goods_id) as cat_id,");
        	intentTable.append("       t.share_svc_num, t.market_user_id, t.seed_user_id,");
        	intentTable.append("        t.top_share_userid, t.top_share_num,");
        	intentTable.append("        t.develop_point_code, t.develop_code, t.deal_operator,");
        	intentTable.append("       t.deal_office_id ,t.source_from as source_from from es_order_intent t where t.order_id not in (select order_id from es_order) and t.is_online_order !='1' and t.source_from ='").append(ManagerUtils.getSourceFrom()).append("' ");
        	
        	orderTable.append(" union all ");
        	orderTable.append("select eoe.out_tid as out_order_id,");
        	orderTable.append("       to_char(eoe.tid_time, 'yyyy-mm-dd hh24:mi:ss') as order_time, eoe.order_id,");
        	orderTable.append("       '0' as order_type,  eoie.phone_num as acc_nbr,");
        	orderTable.append("       delivery.ship_name, delivery.ship_tel,  delivery.ship_addr,   delivery.ship_mobile as ship_tel2,");
        	orderTable.append("       to_char(delivery.province_id) as ship_province,  to_char(delivery.city_id) as ship_city,");
        	orderTable.append("       to_char(delivery.region_id) as ship_district,eoext.phone_owner_name as customer_name, prod_ext.cert_card_num as cert_num,");
        	orderTable.append("       prod_ext.cert_address as cert_addr, delivery.ship_tel as cust_tel,  prod_ext.certi_type as customer_type,");
        	orderTable.append("       eo.goods_id as prod_code, eoext.goodsname as prod_name,");
        	orderTable.append("       (select n.cat_id from es_goods n where n.goods_id = eo.goods_id) as cat_id, eoext.share_svc_num,");
        	orderTable.append("       eoext.market_user_id, eoext.seed_user_id, eoext.top_share_userid, eoext.top_share_num,");
        	orderTable.append("       eoext.development_point_code as develop_point_code, eoext.develop_code_new       as develop_code,");
        	orderTable.append("       eoext.out_operator           as deal_operator,  eoext.out_office             as deal_office_id,eoe.source_from as source_from");
        	orderTable.append("  from es_order_ext eoe");
        	orderTable.append("  left join es_order_extvtl eoext on eoe.order_id = eoext.order_id");
        	orderTable.append("  left join es_order_items_ext eoie on eoext.order_id = eoie.order_id");
        	orderTable.append("  left join es_order eo on eoie.order_id = eo.order_id");
        	orderTable.append("  left join es_order_items_prod_ext prod_ext on eoe.order_id = prod_ext.order_id");
        	orderTable.append("  left join es_delivery delivery on eoe.order_id = delivery.order_id");
        	orderTable.append("  where  eoe.source_from ='").append(ManagerUtils.getSourceFrom()).append("' ");
    		logger.info("通用查询sql--代码sql:"+intentTable.toString()+orderTable.toString());
        }
		if (req.getEnd_date()!=null && !"".equals(req.getEnd_date())) {
			intentTable.append(" and t.create_time < to_date('" + req.getEnd_date() + "','yyyymmddhh24miss') ");
			orderTable.append(" and eoe.tid_time < to_date('" + req.getEnd_date() + "','yyyymmddhh24miss') ");
		}
		if (req.getStart_date()!=null && !"".equals(req.getStart_date())) {
			intentTable.append(" and t.create_time >= to_date('" + req.getStart_date() + "','yyyymmddhh24miss') ");
			orderTable.append(" and eoe.tid_time >= to_date('" + req.getStart_date() + "','yyyymmddhh24miss') ");
		}
		if (req.getOut_order_id()!=null && !"".equals(req.getOut_order_id())) {
			intentTable.append(" and t.intent_order_id ='"+req.getOut_order_id()+"'");
			orderTable.append(" and eoe.out_tid  ='"+req.getOut_order_id()+"'");
		}
		
		if (req.getOrder_id()!=null && !"".equals(req.getOrder_id())) {
			intentTable.append(" and t.order_id ='"+req.getOrder_id()+"'");
			orderTable.append(" and eoe.order_id  ='"+req.getOrder_id()+"'");
		}

		
		if (req.getMarket_user_id()!=null && !"".equals(req.getMarket_user_id())) {
			intentTable.append(" and t.market_user_id ='"+req.getMarket_user_id()+"'");
			orderTable.append(" and eoext.market_user_id  ='"+req.getMarket_user_id()+"'");
		}
		//当 query_type 01 按照订单来源  02 按照种子用户id 03 按照种子用户号码
		if(req.getQuery_type()!= null && !"".equals(req.getQuery_type())){
			if("01".equals(req.getQuery_type())){
				if (req.getQuery_para()!=null && !"".equals(req.getQuery_para())) {//订单来源
					intentTable.append(" and t.source_system_type ='"+req.getQuery_para()+"'");
					orderTable.append(" and eoe.order_from  ='"+req.getQuery_para()+"'");
				}
			}
			if("02".equals(req.getQuery_type())){
				if (req.getQuery_para()!=null && !"".equals(req.getQuery_para())) {//种子用户id
					intentTable.append(" and t.seed_user_id ='"+req.getQuery_para()+"'");
					orderTable.append(" and eoext.seed_user_id  ='"+req.getQuery_para()+"'");
				}
			}
			if("03".equals(req.getQuery_type())){
				if (req.getQuery_para()!=null && !"".equals(req.getQuery_para())) {//种子用户号码
					intentTable.append(" and t.share_svc_num ='"+req.getQuery_para()+"'");
					orderTable.append(" and eoext.share_svc_num  ='"+req.getQuery_para()+"'");
				}
			}
		}
		if (req.getAcc_nbr()!=null && !"".equals(req.getAcc_nbr())) {
			intentTable.append(" and t.acc_nbr ='"+req.getAcc_nbr()+"'");
			orderTable.append(" and eoie.phone_num  ='"+req.getAcc_nbr()+"'");
		}
		if (req.getCert_type()!=null && !"".equals(req.getCert_type())) {
			if("01".equals(req.getCert_type())){
				intentTable.append(" and t.cert_type ='SFZ18'");
				orderTable.append(" and prod_ext.certi_Type  ='SFZ18'");
			}
		}
		if (req.getCert_num()!=null && !"".equals(req.getCert_num())) {
			intentTable.append(" and t.cert_num ='"+req.getCert_num()+"'");
			orderTable.append(" and prod_ext.cert_card_num = '"+req.getCert_num()+"'");
		}
		String sql = intentTable.toString()+orderTable.toString();
		return sql;
	}

	@Override
	public List<Map<String, String>> orderinfoBack(String order_id,String info) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> mapcode = new HashMap<String, String>();
		String sql="";
		if(StringUtil.isEmpty(info)){
			mapcode.put("CODE", "9999");
			mapcode.put("MSG", "参数错误");
			list.add(mapcode);
			return list;
		}
		if("order".equals(info)){
			 sql = "select q.contents, e.* from  es_order_extvtl e left join es_order_ext eoe on e.order_id = eoe.order_id left join es_order_queue_his q  on e.order_id = q.order_id where e.order_id is not null and e.order_id = '"
					+ order_id + "' and e.source_from='" + ManagerUtils.getSourceFrom()
					+ "' and eoe.order_from='100104' ";
		}
		if("intent".equals(info)){
			sql = "select q.contents,e.* from es_order_intent i left join es_order_extvtl e on i.order_id = e.order_id left join es_order_queue_his q on i.order_id = q.order_id where e.order_id is not null and i.order_id ='"
					+ order_id + "' and i.source_from='" + ManagerUtils.getSourceFrom()
					+ "' and i.order_from='100104' ";
		}
		List<Map<String, Object>> intentList = baseDaoSupport.queryForLists(sql);
		if (intentList.size() != 1) {
			mapcode.put("CODE", "0000");
			mapcode.put("MSG", "自动跳过");
			list.add(mapcode);
			return list;
		}
		OrderInfoBackfillReq req = new OrderInfoBackfillReq();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		req.setNotNeedReqStrOrderId(order_id);
		Gson gson = new Gson();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		Map<String, Object> mapVtl = intentList.get(0);
		mapReq = gson.fromJson(intentList.get(0).get("contents").toString(), mapReq.getClass());
		Map<String, Object> ORDER = (Map<String, Object>) mapReq.get("ORDER");
		ORDER_INFO_BACKFILL_REQ backfillReq = new ORDER_INFO_BACKFILL_REQ();
		Map<String, String> REQ_HEAD = new HashMap<String, String>();
		REQ_HEAD.put("IN_MODE_CODE", ORDER.get("IN_MODE_CODE") + "");// 接入渠道
		Map<String, Object> REQ_DATA = new HashMap<String, Object>();
		REQ_DATA.put("ORDER_ID", ORDER.get("ORDER_ID") + "");// 上游系统客户订单编号
		if (null != ORDER.get("ORDER_LINE")) {
			List<Map> ORDER_LINE = (List) ORDER.get("ORDER_LINE");
			if (ORDER_LINE.size() > 0) {
				REQ_DATA.put("ORDER_LINE_ID", ORDER_LINE.get(0).get("ORDER_LINE_ID"));// 上游系统业务订单编号

			}
		}
		if (null != ORDER.get("COMMON_INFO")) {
			List<Map> COMMON_INFO = (List) ORDER.get("COMMON_INFO");// 公共信息
			for (Map COMMON : COMMON_INFO) {
				String STAFF_ID = Const.getStrValue(COMMON, "STAFF_ID");// 操作员工
				REQ_DATA.put("DEAL_STAFF_ID", STAFF_ID);// 处理员工
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		REQ_DATA.put("DEAL_TIME", sdf.format(new Date()));// V10 处理时间2018-11-21
		List<Map<String, String>> FEE_INFO = new ArrayList();
		List<Map<String, String>> PARA = new ArrayList();
		// 列表
		Map<String, String> PRODUCE_TIME = new HashMap<String, String>();
		PRODUCE_TIME.put("PARA_TYPE", "11");// 信息类型：10：写卡信息,11:生产信息
		PRODUCE_TIME.put("PARA_ID", "PRODUCE_TIME");// 生产时间
		PRODUCE_TIME.put("PARA_VALUE", sdf.format(new Date()));
		PARA.add(PRODUCE_TIME);
		Map<String, String> PRODUCE_ORDER_STATE = new HashMap<String, String>();
		PRODUCE_ORDER_STATE.put("PARA_TYPE", "11");//字段需要调整
		PRODUCE_ORDER_STATE.put("PARA_ID", "PRODUCE_ORDER_STATE");// 生产结果状态
		PRODUCE_ORDER_STATE.put("PARA_VALUE", "3");//3是撤单
		PARA.add(PRODUCE_ORDER_STATE);
		Map<String, String> PRODUCE_ORDER_ID = new HashMap<String, String>();
		PRODUCE_ORDER_ID.put("PARA_TYPE", "11");
		PRODUCE_ORDER_ID.put("PARA_ID", "PRODUCE_ORDER_ID");// 生产单号
		//ORDER.get("ORDER_ID") + ""
		PRODUCE_ORDER_ID.put("PARA_VALUE",orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getActive_no());//生产失败的生产单号是什么
		PARA.add(PRODUCE_ORDER_ID);

		if (FEE_INFO.size() > 0) {
			REQ_DATA.put("FEE_INFO", FEE_INFO);
		}
		REQ_DATA.put("PARA", PARA);// 回填信息节点*
		backfillReq.setREQ_HEAD(REQ_HEAD);
		backfillReq.setREQ_DATA(REQ_DATA);

		ORDER_INFO_BACKFILL_REQ ORDER_INFO_BACKFILL_REQ = new ORDER_INFO_BACKFILL_REQ();
		ORDER_INFO_BACKFILL_REQ.setREQ_HEAD(REQ_HEAD);
		ORDER_INFO_BACKFILL_REQ.setREQ_DATA(REQ_DATA);
		req.setORDER_INFO_BACKFILL_REQ(ORDER_INFO_BACKFILL_REQ);

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderInfoBackfillRsp resp = client.execute(req, OrderInfoBackfillRsp.class);
		if (null != resp.getResultMsg()) {
			Map<String, Object> resultMsg = (Map<String, Object>) resp.getResultMsg();
			if (null != resultMsg.get("UNI_BSS_BODY")) {
				Map<String, Object> UNI_BSS_BODY = (Map<String, Object>) resultMsg.get("UNI_BSS_BODY");
				if (null != UNI_BSS_BODY.get("ORDER_INFO_BACKFILL_RSP")) {
					Map<String, String> ORDER_INFO_BACKFILL_RSP = (Map<String, String>) UNI_BSS_BODY
							.get("ORDER_INFO_BACKFILL_RSP");
					mapcode.put("CODE", "0000");
					mapcode.put("MSG", "自动跳过");
					list.add(mapcode);
				}
			}
		} else {
			mapcode.put("CODE", "9999");
			mapcode.put("MSG", "商品信息回填失败");
			list.add(mapcode);
		}
		return list;
	}
}