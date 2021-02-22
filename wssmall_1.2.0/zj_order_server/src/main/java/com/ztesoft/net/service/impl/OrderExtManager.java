package com.ztesoft.net.service.impl;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.MarkTime;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.vo.BSSGonghaoInfoVO;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderMapConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IOrdIntentManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;
import com.ztesoft.net.sqls.SF;

import commons.CommonTools;
import consts.ConstsCore;
import net.sf.json.JSONObject;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import params.req.ZbAuditStatusUpdateReq;
import params.req.ZbCrawlerStatusUpdateReq;
import rule.params.coqueue.req.CoQueueRuleReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.HuaShengGoodsReq;
import zte.net.ecsord.params.base.resp.HuaShengGoodsResp;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderWorksBusiRequest;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.vo.AccountInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.CarryInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.ExpInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.GiftInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.GoodInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.History_TrackingInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.OrderInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.PackInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.PayInfo;
import zte.net.ecsord.params.busiopen.ordinfo.vo.PostInfo;
import zte.net.ecsord.params.ecaop.util.AopInterUtil;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.goodscats.req.CatGetByIdReq;
import zte.params.goodscats.resp.CatGetResp;
import zte.params.orderctn.req.OrderCtnReq;
import zte.params.orderctn.resp.OrderCtnResp;


public class OrderExtManager extends BaseSupport implements IOrderExtManager {

	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IOrdIntentManager ordIntentManager;
	@Resource
	private IOrderManager orderManager;
	@Resource
	private ICacheUtil cacheUtil;
	ICoQueueManager coQueueManager;
	
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	
	@Resource
	private IWorkCustomEngine workCustomEngine;

	@Override
	public String getOrderIdByOutTid(String out_tid) {
		String sql = "select t.order_id from es_order_ext t where t.out_tid=? and t.source_from=? and rownum < 2";
		ArrayList para = new ArrayList();
		para.add(out_tid);
		para.add(CommonTools.getSourceForm());
		String order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return order_id;
	}

	@Override
	public String getOrderIdByBssId(String bss_id) {
		String sql = "select t.order_id from es_order_items_ext t where t.bssorderid=? and rownum < 2";
		ArrayList para = new ArrayList();
		para.add(bss_id);
		String order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return order_id;
	}

	@Override
	public String getHisOrderIdByOutTid(String out_tid) {
		String sql = "select t.order_id from es_order_ext_his t where t.out_tid=? and t.source_from=? and rownum < 2";
		ArrayList para = new ArrayList();
		para.add(out_tid);
		para.add(CommonTools.getSourceForm());
		String order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return order_id;
	}

	@Override
	public String getOrderIdByInfId(String inf_id) {
		String sql = "select t.order_id from es_order_ext t where t.zb_inf_id=? and t.source_from=?";
		ArrayList para = new ArrayList();
		para.add(inf_id);
		para.add(CommonTools.getSourceForm());
		String order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return order_id;
	}

	@Override
	public String getOrderIdByVBELN(String vbeln) {
		String sql = "select t.order_id from es_huasheng_order t where t.vbeln=? and t.source_from=?";
		ArrayList para = new ArrayList();
		para.add(vbeln);
		para.add(CommonTools.getSourceForm());
		String order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return order_id;
	}

	@Override
	public String getOutOrderIdByInfId(String order_id) {
		String sql = "select i.out_tid from es_order_ext i where i.order_id=? and i.source_from=?";
		ArrayList para = new ArrayList();
		para.add(order_id);
		para.add(CommonTools.getSourceForm());
		String out_order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return out_order_id;
	}

	@Override
	public String getShippingTypeById(String order_id, String inf_id) {
		String sql = "select eo.shipping_type from es_order eo,es_order_ext eoe where eo.order_id = eoe.order_id and eoe.source_from = ?";
		ArrayList para = new ArrayList();
		para.add(CommonTools.getSourceForm());
		if (StringUtils.isEmpty(order_id)) {
			sql += " and eoe.zb_inf_id=?";
			para.add(inf_id);
		} else {
			sql += " and eo.order_id=?";
			para.add(order_id);
		}
		String shipping_type = this.baseDaoSupport.queryForString(sql, para.toArray());
		return shipping_type;
	}

	@Override
	public String getOrderCountByLogino(String logi_no, String order_id) {
		String sql = SF.ecsordSql("ORDER_COUNT_BY_LOGI_NO");
		ArrayList para = new ArrayList();
		para.add(logi_no);
		para.add(order_id);
		para.add(logi_no);
		para.add(order_id);
		String count = this.baseDaoSupport.queryForString(sql, para.toArray());
		return count;
	}

	@Override
	public List<OrderInfo> getOrderInfo(OrderInfoReq req) {

		List<Map<String, String>> orderIds = new ArrayList<Map<String, String>>();
		// 判断是否分页
		final boolean isPgae = StringUtils.isEmpty(req.getOut_order_id());

		List<String> sqlParams = new ArrayList<String>();
		if (isPgae) {
			// 如果没有传分页信息.设置默认值,当前页为第1页,每页10条数据
			if (null == req.getPage_no())
				req.setPage_no(1);
			if (null == req.getPage_size())
				req.setPage_size(10);
			String sql = getQueryOrderInfoSql(req, sqlParams).toString();
			String countSql = "select count(1) from (" + sql + ") cord";
			orderIds = this.baseDaoSupport.queryForListPage(sql, req.getPage_no(), req.getPage_size(), sqlParams.toArray());
		} else {
			String sql = getQueryOrderInfoSql(req, sqlParams).toString();
			orderIds = this.baseDaoSupport.queryForList(sql, sqlParams.toArray());
		}
		return getOrderInfoResp(req.getResult_type(), orderIds);

	}

	private StringBuffer getQueryOrderInfoSql(OrderInfoReq orderInfoReq, List<String> sqlParams) {
		StringBuffer sqlAll = new StringBuffer();
		StringBuffer sqlSb = new StringBuffer();
		StringBuffer sqlSbHis = new StringBuffer();

		sqlSb.append("select distinct t.orderId orderId from (	select o.order_id orderId ");
		sqlSb.append("  from es_order o ");
		sqlSb.append("  left join es_order_ext oe ");
		sqlSb.append("    on o.order_id = oe.order_id ");
		sqlSb.append("   and o.source_from = oe.source_from ");
		sqlSb.append("  left join es_member em ");
		sqlSb.append("    on o.source_from = em.source_from ");
		sqlSb.append("   and o.member_id = em.member_id ");
		sqlSb.append("  left join es_delivery d ");
		sqlSb.append("    on o.order_id = d.order_id ");
		sqlSb.append("   and o.source_from = d.source_from ");
		sqlSb.append("   and o.member_id = d.member_id ");
		sqlSb.append("   and d.delivery_type = '0' ");
		sqlSb.append("  left join es_order_items oi ");
		sqlSb.append("    on o.order_id = oi.order_id ");
		sqlSb.append("   and o.source_from = oi.source_from ");
		sqlSb.append("  left join es_order_items_ext eoie");
		sqlSb.append("   on oi.order_id = eoie.order_id ");
		sqlSb.append("   and oi.item_id = eoie.item_id ");
		sqlSb.append("  left join es_order_items_prod oip ");
		sqlSb.append("    on oi.order_id = oip.order_id ");
		sqlSb.append("   and oi.item_id = oip.item_id ");
		sqlSb.append("   and oi.source_from = oip.source_from ");
		sqlSb.append("  left join es_order_items_prod_ext oipe ");
		sqlSb.append("    on oip.order_id = oipe.order_id ");
		sqlSb.append("   and oip.source_from = oipe.source_from ");
		sqlSb.append("   and oip.item_prod_inst_id = oipe.item_prod_inst_id ");
		sqlSb.append("   and oip.item_id = oipe.item_id ");
		sqlSb.append("  left join es_goods g on o.goods_id = g.goods_id");
		sqlSb.append("  left join es_order_extvtl oev on o.order_id = oev.order_id");
		sqlSb.append(" where o.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
		sqlSb.append(" and (oe.order_if_cancel is null or oe.order_if_cancel <> '1') ");

		sqlSbHis.append("select distinct t.orderId orderId from (	select o.order_id orderId ");
		sqlSbHis.append("  from es_order_his o ");
		sqlSbHis.append("  left join es_order_ext_his oe ");
		sqlSbHis.append("    on o.order_id = oe.order_id ");
		sqlSbHis.append("   and o.source_from = oe.source_from ");
		sqlSbHis.append("  left join es_member em ");
		sqlSbHis.append("    on o.source_from = em.source_from ");
		sqlSbHis.append("   and o.member_id = em.member_id ");
		sqlSbHis.append("  left join es_delivery_his d ");
		sqlSbHis.append("    on o.order_id = d.order_id ");
		sqlSbHis.append("   and o.source_from = d.source_from ");
		sqlSbHis.append("   and o.member_id = d.member_id ");
		sqlSbHis.append("   and d.delivery_type = '0' ");
		sqlSbHis.append("  left join es_order_items_his oi ");
		sqlSbHis.append("    on o.order_id = oi.order_id ");
		sqlSbHis.append("   and o.source_from = oi.source_from ");
		sqlSbHis.append("  left join es_order_items_ext_his eoie");
		sqlSbHis.append("   on oi.order_id = eoie.order_id ");
		sqlSbHis.append("   and oi.item_id = eoie.item_id ");
		sqlSbHis.append("  left join es_order_items_prod_his oip ");
		sqlSbHis.append("    on oi.order_id = oip.order_id ");
		sqlSbHis.append("   and oi.item_id = oip.item_id ");
		sqlSbHis.append("   and oi.source_from = oip.source_from ");
		sqlSbHis.append("  left join es_order_items_prod_ext_his oipe ");
		sqlSbHis.append("    on oip.order_id = oipe.order_id ");
		sqlSbHis.append("   and oip.source_from = oipe.source_from ");
		sqlSbHis.append("   and oip.item_prod_inst_id = oipe.item_prod_inst_id ");
		sqlSbHis.append("   and oip.item_id = oipe.item_id ");
		sqlSbHis.append("  left join es_goods g on o.goods_id = g.goods_id");
		sqlSbHis.append("  left join es_order_extvtl_his oev on o.order_id = oev.order_id");
		sqlSbHis.append(" where o.source_from = '" + ManagerUtils.getSourceFrom() + "' ");
		sqlSbHis.append(" and (oe.order_if_cancel is null or oe.order_if_cancel <> '1') ");

		int i = 0;
		// 订单商城来源
		if (!StringUtil.isEmpty(orderInfoReq.getOrder_origin())) {
			sqlSb.append("   and oe.order_origin = ? ");
			sqlSbHis.append("   and oe.order_origin = ? ");
			sqlParams.add(i++, orderInfoReq.getOrder_origin());
			sqlParams.add(orderInfoReq.getOrder_origin());
		}

		// 订单开始时间
		if (!StringUtil.isEmpty(orderInfoReq.getS_time())) {
			sqlSb.append("    and oe.tid_time >= ").append(DBTUtil.to_sql_date("?", 2));
			sqlSbHis.append("    and oe.tid_time >= ").append(DBTUtil.to_sql_date("?", 2));
			sqlParams.add(i++, orderInfoReq.getS_time());
			sqlParams.add(orderInfoReq.getS_time());
		}

		// 订单结束时间
		if (!StringUtil.isEmpty(orderInfoReq.getE_time())) {
			sqlSb.append("    and oe.tid_time <= ").append(DBTUtil.to_sql_date("?", 2));
			sqlSbHis.append("    and oe.tid_time <= ").append(DBTUtil.to_sql_date("?", 2));
			sqlParams.add(i++, orderInfoReq.getE_time());
			sqlParams.add(orderInfoReq.getE_time());
		}

		// 内部订单
		if (!StringUtil.isEmpty(orderInfoReq.getOrder_id())) {
			sqlSb.append("   and oe.order_id = ? ");
			sqlSbHis.append("   and oe.order_id = ? ");
			sqlParams.add(i++, orderInfoReq.getOrder_id());
			sqlParams.add(orderInfoReq.getOrder_id());
		}

		// 外部订单
		if (!StringUtil.isEmpty(orderInfoReq.getOut_order_id())) {
			sqlSb.append("   and oe.out_tid = ? ");
			sqlSbHis.append("   and oe.out_tid = ? ");
			sqlParams.add(i++, orderInfoReq.getOut_order_id());
			sqlParams.add(orderInfoReq.getOut_order_id());
		}

		// 商品sku G_SKU_STR
		if (!StringUtil.isEmpty(orderInfoReq.getG_sku_str())) {
			String goodsSkuStr = orderInfoReq.getG_sku_str();
			goodsSkuStr = goodsSkuStr.startsWith(",") ? goodsSkuStr.substring(1, goodsSkuStr.length()) : goodsSkuStr;
			goodsSkuStr = goodsSkuStr.endsWith(",") ? goodsSkuStr.substring(0, goodsSkuStr.length() - 1) : goodsSkuStr;
			goodsSkuStr = goodsSkuStr.replaceAll(",", "','");
			goodsSkuStr = "'" + goodsSkuStr + "'";
			sqlSb.append("    and g.sku in ( ").append(goodsSkuStr).append(" )");
			sqlSbHis.append("    and g.sku in ( ").append(goodsSkuStr).append(" )");
		}

		// 第三方系统与供应商映射关系
		if (!StringUtil.isEmpty(orderInfoReq.getThird_sys_code())) {
			String supply_id = "";
			boolean flag = false;
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
			List<Map> list = dcPublicCache.getList(StypeConsts.THIRD_SYS_VS_SUPPLY_ID);
			for (Map<String, String> map : list) {
				if (StringUtils.equals(map.get("pkey"), orderInfoReq.getThird_sys_code())) {
					if (StringUtils.equals(map.get("pname"), "admin")) {// admin权限可以查到所有订单,admin可以定义成常量
						flag = true;
						break;
					}
					supply_id = supply_id + ",'" + map.get("pname") + "'";
				}
			}
			if (!flag) {// 没有admin权限，需要根据供应商筛选订单
				if (StringUtils.isNotEmpty(supply_id)) {// 有对应的供应商
					supply_id = supply_id.substring(1, supply_id.length());// 去除第一个逗号
					sqlSb.append(" and oev.supply_id in ( ").append(supply_id).append(")");
					sqlSbHis.append(" and oev.supply_id in ( ").append(supply_id).append(")");
				} else {// 没有对应的供应商，不返回任何订单
					sqlSb.append(" and 1=2 ");
					sqlSbHis.append(" and 1=2 ");
				}
			}
		}

		// 用户手机号码 es_order_items_ext phone_num
		if (!StringUtil.isEmpty(orderInfoReq.getMobile_tel())) {
			sqlSb.append("   and eoie.phone_num = ? ");
			sqlSbHis.append("   and eoie.phone_num = ? ");
			sqlParams.add(i++, orderInfoReq.getMobile_tel());
			sqlParams.add(orderInfoReq.getMobile_tel());
		}

		// 用户证件号码
		if (!StringUtil.isEmpty(orderInfoReq.getCerti_no())) {
			sqlSb.append("   and oipe.cert_card_num = ? ");
			sqlSbHis.append("   and oipe.cert_card_num = ? ");
			sqlParams.add(i++, orderInfoReq.getCerti_no());
			sqlParams.add(orderInfoReq.getCerti_no());
		}

		// 收货人联系电话
		if (!StringUtil.isEmpty(orderInfoReq.getConsignee_num())) {
			sqlSb.append("   and (d.ship_tel = ? or d.ship_mobile = ? )");
			sqlSbHis.append("   and (d.ship_tel = ? or d.ship_mobile = ? )");
			sqlParams.add(i++, orderInfoReq.getConsignee_num());
			sqlParams.add(i++, orderInfoReq.getConsignee_num());
			sqlParams.add(orderInfoReq.getConsignee_num());
			sqlParams.add(orderInfoReq.getConsignee_num());
		}
		sqlSb.append(" order by o.create_time desc ) t ");
		sqlSbHis.append(" order by o.create_time desc ) t ");

		sqlAll.append(sqlSb.toString()).append(" union all ").append(sqlSbHis.toString());
		return sqlAll;
	}

	private List<OrderInfo> getOrderInfoResp(String resultType, List<Map<String, String>> orderIds) {

		// 订单返回信息
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>(orderIds.size());

		// 物流公司id与名称对应关系
		StringBuilder sql = new StringBuilder("SELECT T.ID, T.NAME FROM ES_LOGI_COMPANY T");
		List<Map> list = this.baseDaoSupport.queryForList(sql.toString());
		Map<String, String> shippingIDName = new HashMap<String, String>();
		for (Map map : list) {
			shippingIDName.put((String) map.get("ID"), (String) map.get("NAME"));
		}

		OrderInfo orderInfo;
		// 设置返回对象-订单信息和支付信息
		for (Map<String, String> orderMap : orderIds) {
			OrderTreeBusiRequest tree = null;
			String is_his = "";// 默认非历史单
			try {
				tree = CommonDataFactory.getInstance().getOrderTree(orderMap.get("orderId"));
			} catch (java.lang.NullPointerException e) {
				e.printStackTrace();
			}
			if (null == tree) {
				try {
					tree = CommonDataFactory.getInstance().getOrderTreeHis(orderMap.get("orderId"));
					is_his = "1";// 订单为历史单
				} catch (java.lang.NullPointerException e) {
					e.printStackTrace();
				}
				if (null == tree)
					continue;
			}

			final String orderId = tree.getOrder_id();

			// 1000订单基础信息
			orderInfo = new OrderInfo();

			if (resultType.contains(InfConst.ORDER_BASE_INFO)) {
				// 外部订单号
				orderInfo.setOrigOrderId(tree.getOrderExtBusiRequest().getOut_tid());
				// 内部订单号
				orderInfo.setOrderId(orderId);
				// 订单来源
				final String SourceFrom = tree.getOrderExtBusiRequest().getOrder_from();
				orderInfo.setOrderOrigin(EcsOrderMapConsts.ORDER_ORIGIN_MAP.containsKey(SourceFrom) ? EcsOrderMapConsts.ORDER_ORIGIN_MAP.get(SourceFrom) : SourceFrom);
				// 归属地市
				final String orderCityCode = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_CITY_CODE);
				final String orderCity = CommonDataFactory.getInstance().getLanCode(orderCityCode);
				orderInfo.setOrderCity(EcsOrderMapConsts.ORDER_CITY.containsKey(orderCity) ? EcsOrderMapConsts.ORDER_CITY.get(orderCity) : orderCity);
				// 当前环节
				final String flowNode = tree.getOrderExtBusiRequest().getFlow_trace_id();
				orderInfo.setFlowNode(EcsOrderMapConsts.FLOW_NODE.containsKey(flowNode) ? EcsOrderMapConsts.FLOW_NODE.get(flowNode) : flowNode);
				// 用户下单时间
				orderInfo.setOrderCreateTime(tree.getOrderExtBusiRequest().getTid_time());
				// 订单生成时间
				orderInfo.setCreateTime(tree.getOrderBusiRequest().getCreate_time());
				// 闪电送开始时间(暂缓)
				orderInfo.setLightTime(tree.getOrderExtBusiRequest().getSyn_wl_time());
				// 外部订单状态
				final String extOrderStatus = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PLATFORM_STATUS);
				orderInfo.setExtOrderStatus(EcsOrderMapConsts.EXT_ORDER_STATUS.containsKey(extOrderStatus) ? EcsOrderMapConsts.EXT_ORDER_STATUS.get(extOrderStatus) : extOrderStatus);
				// 买家留言
				orderInfo.setBuyerMessage(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.BUYER_MESSAGE));
				// 卖家留言
				orderInfo.setSellerMessage(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SELLER_MESSAGE));
				// 订单备注
				orderInfo.setMemo(tree.getOrderBusiRequest().getRemark());
				// 订单兑换积分（支付订单所使用的积分）
				orderInfo.setOrderPoints(tree.getOrderExtBusiRequest().getOrder_points());

				String exp_status = tree.getOrderExtBusiRequest().getAbnormal_status();
				orderInfo.setExpStatus(exp_status);// 传编码
				Map params = new HashMap();
				params.put("order_id", orderId);
				params.put("is_his", is_his);
				List<OrderExceptionLogsReq> expLogs = this.orderManager.queryOrderExcLogs(params);
				if (expLogs != null && expLogs.size() > 0) {
					List<ExpInfo> expInfos = new ArrayList<ExpInfo>();
					ExpInfo expInfo = null;
					for (OrderExceptionLogsReq expLog : expLogs) {
						expInfo = new ExpInfo();

						expInfo.setCallBack(expLog.getNeed_customer_visit());// 编码
						expInfo.setAbnormalType(expLog.getAbnormal_type());// 编码
						expInfo.setExcType(expLog.getException_type());
						expInfo.setExcDesc(expLog.getException_desc());
						expInfo.setExcTime(expLog.getMark_time());
						expInfo.setExcDeal(expLog.getIs_deal());// 编码

						expInfos.add(expInfo);
					}

					orderInfo.setExpInfo(expInfos);
				}
			}

			// 1004 订单商品包信息
			List<PackInfo> packInfoList = null;
			if (resultType.contains(InfConst.ORDER_PACK_INFO)) {
				String sale_goodsname = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SALES_NAME);
				String prodOffernum = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.GOODS_NUM);
				packInfoList = new ArrayList<PackInfo>();
				PackInfo packInfo;
				for (OrderItemBusiRequest orderItem : tree.getOrderItemBusiRequests()) {
					packInfo = new PackInfo();
					// 商品包ID
					packInfo.setPackageId(orderItem.getItem_id());
					// 商品包编号(暂无字段)
					// packInfo.setProdOfferCode("");
					// 商品包名称
					packInfo.setProdOfferName(orderItem.getName());
					// 销售商品名称
					packInfo.setSale_goodsname(sale_goodsname);
					// 商品数量
					packInfo.setProdOffernum(prodOffernum);
					// 商品包类型
					final String prodOfferType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PACK_TYPE);
					packInfo.setProdOfferType(EcsOrderMapConsts.PROD_OFFER_TYPE.containsKey(prodOfferType) ? EcsOrderMapConsts.PROD_OFFER_TYPE.get(prodOfferType) : prodOfferType);

					packInfoList.add(packInfo);
				}
				orderInfo.setPackInfo(packInfoList);
			}

			// 2003 开户信息
			List<AccountInfo> accountInfoList = null;
			if (resultType.contains(InfConst.ORDER_ACCOUNT_INFO)) {
				accountInfoList = new ArrayList<AccountInfo>();
				AccountInfo accountInfo;
				for (OrderItemBusiRequest orderItem : tree.getOrderItemBusiRequests()) {
					accountInfo = new AccountInfo();
					// 结果说明
					accountInfo.setInfoAccountDesc(orderItem.getOrderItemExtBusiRequest().getEss_pre_desc());
					// 开户或者校验结果 开户状态(1开户成功、非1开户失败)
					accountInfo.setInfoAccountStatus("1".equals(orderItem.getOrderItemExtBusiRequest().getAccount_status()) ? "开户成功" : "开户失败");
					// 开户号码
					accountInfo.setMobileNo(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_NUM));

					accountInfoList.add(accountInfo);
				}

			}

			// 2002 赠品信息
			List<GiftInfo> giftInfoList = null;
			if (resultType.contains(InfConst.ORDER_GIFT_INFO)) {
				giftInfoList = new ArrayList<GiftInfo>();
				GiftInfo giftInfo;
				for (AttrGiftInfoBusiRequest gift : tree.getGiftInfoBusiRequests()) {
					giftInfo = new GiftInfo();
					// 面值
					giftInfo.setGiftFace(Integer.parseInt(gift.getGift_value()));
					// 礼品名称
					giftInfo.setGiftName(gift.getGoods_name());
					// 数量
					giftInfo.setGiftNum(String.valueOf(gift.getSku_num()));
					// 礼品内容
					giftInfo.setGiftText(gift.getGoods_desc());
					giftInfoList.add(giftInfo);
				}
			}

			// 2001 货品信息
			List<GoodInfo> goodInfoList = null;
			if (resultType.contains(InfConst.ORDER_GOOD_INFO)) {
				goodInfoList = new ArrayList<GoodInfo>();
				GoodInfo goodInfo;

				List<OrderItemExtvtlBusiRequest> multi_goods = tree.getOrderItemExtvtlBusiRequests();
				if (null != multi_goods && multi_goods.size() > 0) {// 多商品、货品，暂时只有B2B裸机
					for (OrderItemExtvtlBusiRequest good : multi_goods) {

						goodInfo = new GoodInfo();
						// 商品名称
						goodInfo.setProductName(good.getGoods_name());
						// 套餐名称
						goodInfo.setPackageName("");
						// 商品包类型
						final String packType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PACK_TYPE);
						goodInfo.setPackType(EcsOrderMapConsts.PROD_OFFER_TYPE.containsKey(packType) ? EcsOrderMapConsts.PROD_OFFER_TYPE.get(packType) : packType);
						// 号码预存款,以分为单位
						goodInfo.setGradeDeposits("");
						// 活动类型
						final String buyMode = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ACTIVE_TYPE);
						goodInfo.setBuyMode(EcsOrderMapConsts.BUY_MODE.containsKey(buyMode) ? EcsOrderMapConsts.BUY_MODE.get(buyMode) : buyMode);
						// 机型
						goodInfo.setHandsetType(good.getResources_model_name());
						// 颜色
						goodInfo.setHandsetColor(good.getResources_color());
						// 合约期
						goodInfo.setAgreementDate("");
						// 首月资费方式
						goodInfo.setFirstFeeType("");
						// 号码
						goodInfo.setMobileTel("");
						// 开户人姓名
						goodInfo.setBuyerName("");
						// 开户人证件类型
						goodInfo.setBuyerCardType("");
						// 开户人证件号码
						goodInfo.setBuyerCardNo("");
						// 开户人证件有效期
						goodInfo.setBuyerCardExp("");
						// 开户人证件地址
						goodInfo.setBuyerCardAddress("");
						// 大小卡
						goodInfo.setBscard("");
						goodInfoList.add(goodInfo);

					}
					PackInfo packInfo = null;
					if (null == packInfoList) {
						packInfoList = new ArrayList<PackInfo>();
						packInfo = new PackInfo();
						packInfoList.add(packInfo);
					} else {
						packInfo = packInfoList.get(0);
					}
					packInfo.setProdOffernum(String.valueOf(multi_goods.size()));
				} else {

					goodInfo = new GoodInfo();
					// 商品名称
					goodInfo.setProductName(CommonDataFactory.getInstance().getGoodSpec(orderId, null, SpecConsts.GOODS_NAME));
					// 套餐名称
					goodInfo.setPackageName(CommonDataFactory.getInstance().getProductSpec(orderId, SpecConsts.TYPE_ID_10002, SpecConsts.PLAN_TITLE));
					// 商品包类型
					final String packType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PACK_TYPE);
					goodInfo.setPackType(EcsOrderMapConsts.PROD_OFFER_TYPE.containsKey(packType) ? EcsOrderMapConsts.PROD_OFFER_TYPE.get(packType) : packType);
					// 号码预存款,以分为单位
					goodInfo.setGradeDeposits(dollarToPenny(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_DEPOSIT)));
					// 活动类型
					final String buyMode = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ACTIVE_TYPE);
					goodInfo.setBuyMode(EcsOrderMapConsts.BUY_MODE.containsKey(buyMode) ? EcsOrderMapConsts.BUY_MODE.get(buyMode) : buyMode);
					// 机型
					goodInfo.setHandsetType(CommonDataFactory.getInstance().getProductSpec(orderId, SpecConsts.TYPE_ID_10000, SpecConsts.MODEL_NAME));
					// 颜色
					goodInfo.setHandsetColor(CommonDataFactory.getInstance().getProductSpec(orderId, SpecConsts.TYPE_ID_10000, SpecConsts.COLOR_NAME));
					// 合约期
					String phoneNum = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_NUM);
					goodInfo.setAgreementDate(CommonDataFactory.getInstance().getNumberSpec(phoneNum, SpecConsts.NUMERO_PERIOD));
					// 首月资费方式
					final String firstFeeType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.FIRST_PAYMENT);
					goodInfo.setFirstFeeType(EcsOrderMapConsts.FIRST_FEE_TYPE.containsKey(firstFeeType) ? EcsOrderMapConsts.FIRST_FEE_TYPE.get(firstFeeType) : firstFeeType);
					// 号码
					goodInfo.setMobileTel(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_NUM));
					// 开户人姓名
					goodInfo.setBuyerName(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_CARD_NAME));
					// 开户人证件类型
					String buyerCardType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERTI_TYPE);
					goodInfo.setBuyerCardType(EcsOrderMapConsts.BUYER_CARD_TYPE.containsKey(buyerCardType) ? EcsOrderMapConsts.BUYER_CARD_TYPE.get(buyerCardType) : buyerCardType);
					// 开户人证件号码
					goodInfo.setBuyerCardNo(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_CARD_NUM));
					// 开户人证件有效期
					goodInfo.setBuyerCardExp(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_FAILURE_TIME));
					// 开户人证件地址
					goodInfo.setBuyerCardAddress(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_ADDRESS));
					// 大小卡
					final String bsCard = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.WHITE_CART_TYPE);
					goodInfo.setBscard(EcsOrderMapConsts.BS_CARD.containsKey(bsCard) ? EcsOrderMapConsts.BS_CARD.get(bsCard) : bsCard);
					goodInfoList.add(goodInfo);
				}
			}

			// 判断是存在 货品信息 赠品信息 开户信息
			final boolean isPackInfo = null != goodInfoList || null != giftInfoList || null != accountInfoList;
			if (isPackInfo) {
				PackInfo packInfo = null;
				if (null == packInfoList) {
					packInfoList = new ArrayList<PackInfo>();
					packInfo = new PackInfo();
				} else {
					packInfo = packInfoList.get(0);
				}
				if (null != goodInfoList) {
					packInfo.setGoodInfo(goodInfoList);
				}
				if (null != giftInfoList) {
					packInfo.setGiftInfo(giftInfoList);
				}
				if (null != accountInfoList) {
					packInfo.setAccountInfo(accountInfoList);
				}
				orderInfo.setPackInfo(packInfoList);
			}

			// 1003 订单配送信息
			if (resultType.contains(InfConst.ORDER_POST_INFO)) {
				List<PostInfo> postInfoList = new ArrayList<PostInfo>();
				PostInfo postInfo;
				for (OrderDeliveryBusiRequest orderDelivery : tree.getOrderDeliveryBusiRequests()) {
					if (EcsOrderConsts.LOGIS_NORMAL.equals(orderDelivery.getDelivery_type())) {// 正常发货
						postInfo = new PostInfo();
						// 物流公司名称
						postInfo.setPostName(shippingIDName.get(orderDelivery.getShipping_company()));
						// 物流公司代码
						postInfo.setPostId(orderDelivery.getShipping_company());
						// 配送类型 物流类型 0正常发货、1补寄、2重发
						String postType = orderDelivery.getDelivery_type();
						postInfo.setPOST_TYPE(EcsOrderMapConsts.POST_TYPE.containsKey(postType) ? EcsOrderMapConsts.POST_TYPE.get(postType) : postType);
						// 物流单编号
						postInfo.setPOST_NO(orderDelivery.getLogi_no());
						// 发货时间
						postInfo.setCARRY_TIME(CommonDataFactory.getInstance().getDcPublicDataByPkey("shipping_time", orderDelivery.getShipping_time()));
						// 发货状态
						// final String postStatus =
						// String.valueOf(orderDelivery.getShip_status());
						// postInfo.setPOST_STATUS(EcsOrderMapConsts.POST_STATUS.containsKey(postStatus)
						// ? EcsOrderMapConsts.POST_STATUS.get(postStatus) :
						// postStatus);
						postInfo.setPOST_STATUS(
								CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_LIGHT_NING_STATUS", CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.DELIVERY_STATUS)));
						// 地址关联carryInfo.carryId
						postInfo.setORDER_CARRY_ID(orderDelivery.getDelivery_id());

						postInfoList.add(postInfo);
					}
				}
				orderInfo.setPostInfo(postInfoList);
			}

			// 1002 收获地址信息
			if (resultType.contains(InfConst.ORDER_CARRY_INFO)) {
				List<CarryInfo> carryInfoList = new ArrayList<CarryInfo>();
				CarryInfo carryInfo;
				for (OrderDeliveryBusiRequest orderDelivery : tree.getOrderDeliveryBusiRequests()) {
					if (EcsOrderConsts.LOGIS_NORMAL.equals(orderDelivery.getDelivery_type())) {// 正常发货
						carryInfo = new CarryInfo();
						// 收货人姓名
						carryInfo.setConsigneeName(orderDelivery.getShip_name());
						// 收货邮编
						carryInfo.setReceiveZip(orderDelivery.getShip_zip());
						// 收货人电话
						carryInfo.setTelNum(orderDelivery.getShip_tel());
						// 收货人手机
						carryInfo.setTelMobileNum(orderDelivery.getShip_mobile());
						// 收货地址
						carryInfo.setCarryAddress(orderDelivery.getShip_addr());
						// 配送方式
						final String carryMode = orderDelivery.getShip_type();
						carryInfo.setCarryMode(EcsOrderMapConsts.CARRY_MODE.containsKey(carryMode) ? EcsOrderMapConsts.CARRY_MODE.get(carryMode) : carryMode);
						// 主键
						carryInfo.setCarryId(orderDelivery.getDelivery_id());
						// 配送类型,0:正常发货；1：补发;2:重发
						String delivery_type = orderDelivery.getDelivery_type();
						carryInfo.setPostType(EcsOrderMapConsts.POST_TYPE.containsKey(delivery_type) ? EcsOrderMapConsts.POST_TYPE.get(delivery_type) : delivery_type);

						carryInfoList.add(carryInfo);
					}
				}
				orderInfo.setCarryInfo(carryInfoList);
			}

			// 1001订单支付信息
			if (resultType.contains(InfConst.ORDER_PAY_INFO)) {
				List<PayInfo> payInfoList = new ArrayList<PayInfo>();
				PayInfo payInfo = new PayInfo();
				// 支付方式 PAY_TYPE
				final String payType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PAY_METHOD);
				payInfo.setPayType(EcsOrderMapConsts.PAY_TYPE.containsKey(payType) ? EcsOrderMapConsts.PAY_TYPE.get(payType) : payType);
				// 支付类型 PAY_MODE
				final String payMode = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PAY_TYPE);
				payInfo.setPayMode(EcsOrderMapConsts.PAY_MODE.containsKey(payMode) ? EcsOrderMapConsts.PAY_MODE.get(payMode) : payMode);
				// 支付状态 PAY_STATUS
				payInfo.setPayStatus(EcsOrderConsts.PAY_STATUS_PAYED.equals(tree.getOrderBusiRequest().getPay_status().toString()) ? "已支付" : "未支付");
				// 付款时间
				payInfo.setPayDatetime(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PAY_TIME));
				// 优惠金额
				payInfo.setDiscountFee(dollarToPenny(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.DISCOUNTRANGE)));
				// 调整金额
				// payInfo.setAdjustFee("0");
				// 订单金额
				payInfo.setOrderFee(dollarToPenny(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_AMOUNT)));
				// 实付金额
				payInfo.setPayFee(dollarToPenny(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_REAL_FEE)));
				payInfoList.add(payInfo);
				orderInfo.setPayInfo(payInfoList);
			}

			// 2004订单处理轨迹
			if (resultType.contains(InfConst.ORDER_DEAL_INFO)) {
				List<History_TrackingInfo> history_TrackingInfoList = new ArrayList<History_TrackingInfo>();

				List<OrderHandleLogsReq> handleList = orderManager.queryOrderHandlerLogs(orderId, Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE, is_his);
				History_TrackingInfo history_TrackingInfo = null;
				for (OrderHandleLogsReq handleLog : handleList) {
					history_TrackingInfo = new History_TrackingInfo();
					history_TrackingInfo.setLink(handleLog.getTrace_name());
					history_TrackingInfo.setProcess_mode(handleLog.getType_name());
					history_TrackingInfo.setDeal_desc(handleLog.getComments());
					history_TrackingInfo.setDeal_time(handleLog.getCreate_time());
					history_TrackingInfo.setDeal_staff(handleLog.getOp_name());
					history_TrackingInfoList.add(history_TrackingInfo);
				}
				orderInfo.setHistory_TrackingInfo(history_TrackingInfoList);
			}

			orderInfoList.add(orderInfo);

		}

		return orderInfoList;
	}

	/**
	 * 单位转换(圆转分)
	 *
	 * @param strMoney
	 * @return
	 */
	private String dollarToPenny(String strMoney) {
		String result = "";
		if (org.apache.commons.lang.math.NumberUtils.isNumber(strMoney)) {
			result = String.valueOf(Double.parseDouble(strMoney) * 100);
		} else {
			result = strMoney;
		}
		return result;
	}

	@Override
	public Map getMapByOutTid(String out_tid) {
		String sql = "select t.order_id,archive_type from es_order_ext_his t where t.out_tid=? and t.source_from=? and rownum < 2";
		ArrayList para = new ArrayList();
		para.add(out_tid);
		para.add(CommonTools.getSourceForm());
		Map map = this.baseDaoSupport.queryForMap(sql, para.toArray());
		return map;
	}

	@Override
	public Map getMapByOrderid(String order_id) {
		String sql = "select archive_type from es_order_ext_his t where t.order_id=? and t.source_from=? and rownum < 2";
		ArrayList para = new ArrayList();
		para.add(order_id);
		para.add(CommonTools.getSourceForm());
		Map map = this.baseDaoSupport.queryForMap(sql, para.toArray());
		return map;
	}

	@Override
	public HuaShengGoodsResp getHuaShengGoods(HuaShengGoodsReq req) {
		HuaShengGoodsResp resp = new HuaShengGoodsResp();
		String sql = "select c.id,c.matnr,c.maktx,c.meins,c.mtart,c.matkl,c.xchpf,c.mchk,c.cmmag " + " from es_goods_huasheng c where c.matnr = ? and c.source_from = ?";
		List<HuaShengGoodsResp> listRsp = this.baseDaoSupport.queryForList(sql, HuaShengGoodsResp.class, req.getMatnr(), ManagerUtils.getSourceFrom());
		if (null != listRsp && listRsp.size() > 0) {
			resp = listRsp.get(0);
		}
		return resp;
	}

	// ----------------------------------------
	@Override
	public OrderCtnResp saveManualOrder(Map manualOrder, String rpc_type) {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		String essMoney = Const.getStrValue(manualOrder, "ess_money");
		String payMoney = Const.getStrValue(manualOrder, "pay_money");
		Map reqMap = new HashMap();
		String out_tid = Const.getStrValue(manualOrder, "out_order_id");
		
		out_tid = StringUtils.isEmpty(out_tid) ? "ZJ" + SequenceTools.getShort18PrimaryKey() : out_tid;
		reqMap.put("serial_no", UUID.randomUUID());
		reqMap.put("order_id", out_tid);
		reqMap.put("source_system", Const.getStrValue(manualOrder, "source_system")); // 发起方系统标识，单点集成系统传递
		reqMap.put("receive_system", "10011");
		reqMap.put("source_from_system", Const.getStrValue(manualOrder, "source_system")); // 订单来源系统（大类）
		reqMap.put("source_from", Const.getStrValue(manualOrder, "source_from")); // 订单来源（小类）
		reqMap.put("time", com.ztesoft.net.framework.util.StringUtil.getTransDate());
		reqMap.put("development_code", "");
		reqMap.put("order_type", "1"); // 默认订购
		reqMap.put("status", "00"); // 订单状态，默认未知状态
		reqMap.put("platform_status", "已支付"); // 填各自平台的订单状态：是否已支付等
		reqMap.put("create_time", com.ztesoft.net.framework.util.StringUtil.getTransDate());
		reqMap.put("pay_time", com.ztesoft.net.framework.util.StringUtil.getTransDate());
		reqMap.put("ess_money", essMoney);
		reqMap.put("busi_money", essMoney);
		reqMap.put("order_heavy", "0");
		reqMap.put("order_disacount", "0");
		reqMap.put("order_comment", "");
		reqMap.put("order_amount", payMoney); // 订单总价
		
		String ship_province =Const.getStrValue(manualOrder, "ship_province");
		
		// 默认330000  
		if(org.apache.commons.lang.StringUtils.isBlank(ship_province)) {
			reqMap.put("order_provinc_code", "330000");
		}else{
			reqMap.put("order_provinc_code", Const.getStrValue(manualOrder, "ship_province"));
		}
		
		reqMap.put("order_city", Const.getStrValue(manualOrder, "order_city_code"));
		reqMap.put("order_city_code", Const.getStrValue(manualOrder, "order_city_code"));
		reqMap.put("abnormal_status", "正常");
		reqMap.put("pay_money", payMoney);
		reqMap.put("alipay_id", "");// 外部支付账号
		if (StringUtil.equals(Const.getStrValue(manualOrder, "source_from"), "10071")) {
			reqMap.put("pay_type", "XCZF");
		} else {
			reqMap.put("pay_type", "ZXZF");// 支付类型，默认在线支付
		}
		reqMap.put("payment_type", Const.getStrValue(manualOrder, "pay_method"));
		reqMap.put("channel_id", StringUtil.isEmpty(Const.getStrValue(manualOrder, "channelId")) ? "0000" : Const.getStrValue(manualOrder, "channelId")); // 渠道ID
		reqMap.put("chanel_name", "手工录入"); // 渠道名称
		/*
		 * 渠道标记： 1 传统营业厅 2 电话营销 3 非集团合作直销 4 非集团自有直销 5 集客 6 家客直销 7 宽固代理 8 社会渠道 9
		 * 社会微厅 10 网盟 11 异业联盟 12 员工渠道 13 自营渠道 14 自营微厅 15 其他
		 */
		reqMap.put("channel_mark", "13"); // 渠道标记，默认自营渠道
		reqMap.put("channel_type", Const.getStrValue(manualOrder, "channelType"));
		reqMap.put("develop_departId", Const.getStrValue(manualOrder, "develop_departId"));
		reqMap.put("bss_operator", ""); // BSS工号
		reqMap.put("bss_operator_name", ""); //
		reqMap.put("oss_operator", "GD001838"); // 订单支撑系统工号，如果不为空，订单标准化后使用该工号锁定订单
		reqMap.put("is_examine_card", "0"); // 是否需要校验证件照，默认已经手工校验
		reqMap.put("shipping_company", ""); // 物流公司编码
		reqMap.put("shipping_type", "EMS"); // 配送方式，默认平邮
		reqMap.put("shipping_time", "NOLIMIT");
		reqMap.put("ship_area", "");
		reqMap.put("ship_city", StringUtil.isEmpty(Const.getStrValue(manualOrder, "ship_city"))  ? "330100" : Const.getStrValue(manualOrder, "ship_city")); // 收货地市，订单补录走独立生产模式，默认杭州
		
		reqMap.put("ship_country", StringUtil.isEmpty(Const.getStrValue(manualOrder, "ship_county")) ? "330108" : Const.getStrValue(manualOrder, "ship_county")); // 收货区县，订单补录走独立生产模式，默认滨江区
		reqMap.put("ship_name", Const.getStrValue(manualOrder, "ship_name")); // 收货人姓名
		reqMap.put("ship_phone", Const.getStrValue(manualOrder, "ship_phone")); // 收货人电话
		reqMap.put("ship_addr", Const.getStrValue(manualOrder, "ship_addr"));
		reqMap.put("logi_no", Const.getStrValue(manualOrder, "logi_no"));
		reqMap.put("delivery_status", "已发货"); // 发货状态，默认已发货
		reqMap.put("shipping_amount", "0"); // 应收运费，默认0
		reqMap.put("n_shipping_amount", "0"); // 实收运费，默认0
		reqMap.put("shipping_quick", "02"); // 是否闪电送，默认否
		reqMap.put("name", Const.getStrValue(manualOrder, "cust_name")); // 买家名称cust_name
		reqMap.put("uname", Const.getStrValue(manualOrder, "cust_name")); // 买家昵称
		reqMap.put("uid", "510000"); // 买家标识
		
		reqMap.put("evdo_num",Const.getStrValue(manualOrder, "evdo_num"));
		//和宽带的用户种类分开存
		reqMap.put("user_kind",Const.getStrValue(manualOrder, "user_kind"));
		String is_examine_card = ZjEcsOrderConsts.IS_DEFAULT_VALUE;
		String isRealName = Const.getStrValue(manualOrder, "is_realname");
		if (ZjEcsOrderConsts.IS_DEFAULT_VALUE.equals(isRealName)) {
			is_examine_card = ZjEcsOrderConsts.NO_DEFAULT_VALUE;
		}
		reqMap.put("is_realname", isRealName);
		reqMap.put("is_examine_card", is_examine_card); // 是否需要校验身份证，如果已实名则不需要，未实名则需要校验
		reqMap.put("is_to4g", "0"); // 是否2G3G转4G，
		reqMap.put("is_deal", "1"); // 是否已办理完成
		
		reqMap.put("service_remarks", Const.getStrValue(manualOrder, "service_remarks"));//订单备注
		
		reqMap.put("buyer_message", Const.getStrValue(manualOrder, "buyer_message")); // 买家留言
		reqMap.put("iccid", Const.getStrValue(manualOrder, "iccid")); // ICCID
		reqMap.put("out_office", Const.getStrValue(manualOrder, "deal_office_id")); // 操作点
		reqMap.put("out_operator", Const.getStrValue(manualOrder, "deal_operator")); // 操作员
		
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "is_pay"))) {
			reqMap.put("is_pay", Const.getStrValue(manualOrder, "is_pay")); //
		}
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "couponbatchid"))) {
			reqMap.put("couponbatchid", Const.getStrValue(manualOrder, "couponbatchid")); //
		}
		
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "is_new"))) {
            reqMap.put("is_new", Const.getStrValue(manualOrder, "is_new")); //
        }
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "is_blankcard"))) {
            reqMap.put("is_blankcard", Const.getStrValue(manualOrder, "is_blankcard")); //
        }
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "develop_code"))) {
			reqMap.put("develop_code", Const.getStrValue(manualOrder, "develop_code"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "develop_name"))) {
			reqMap.put("develop_name", Const.getStrValue(manualOrder, "develop_name"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "group_code"))) {
			reqMap.put("group_code", Const.getStrValue(manualOrder, "group_code")); //
		}
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "customer_type"))) {
			manualOrder.put("CustomerType", Const.getStrValue(manualOrder, "customer_type")); //
		}
		reqMap.put("user_kind",Const.getStrValue(manualOrder, "user_kind"));
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "group_name"))) {
			reqMap.put("group_name", Const.getStrValue(manualOrder, "group_name")); //
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "cust_id"))) {
			reqMap.put("cust_id", Const.getStrValue(manualOrder, "cust_id"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_flag"))) {
			reqMap.put("certify_flag", Const.getStrValue(manualOrder, "certify_flag"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_cert_type"))) {
			reqMap.put("certify_cert_type", Const.getStrValue(manualOrder, "certify_cert_type")); //
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_cert_num"))) {
			reqMap.put("certify_cert_num", Const.getStrValue(manualOrder, "certify_cert_num"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_cust_name"))) {
			reqMap.put("certify_cust_name", Const.getStrValue(manualOrder, "certify_cust_name"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "certify_cert_addr"))) {
			reqMap.put("certify_cert_addr", Const.getStrValue(manualOrder, "certify_cert_addr"));
		}

         // 靓号信息
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "nice_info"))) {
			reqMap.put("nice_info", Const.getStrValue(manualOrder, "nice_info"));
		}
		
		reqMap.put("sale_mod_type", Const.getStrValue(manualOrder, "saleModType"));
		reqMap.put("marking_tag", Const.getStrValue(manualOrder, "markingTag"));
		reqMap.put("district_code", Const.getStrValue(manualOrder, "district"));
		reqMap.put("channelid", Const.getStrValue(manualOrder, "channelId"));
		String is_aop = Const.getStrValue(manualOrder, "is_aop");
		reqMap.put("is_aop", is_aop);
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "old_cust_id"))) {
			reqMap.put("old_cust_id", Const.getStrValue(manualOrder, "old_cust_id"));
		}
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "rel_order_id"))) {
			reqMap.put("intent_order_id", Const.getStrValue(manualOrder, "rel_order_id"));
		}
		
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "order_deal_method"))) {
			reqMap.put("order_deal_method", Const.getStrValue(manualOrder, "order_deal_method"));
		}
		
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "flow_code"))) {
			reqMap.put("flow_code", Const.getStrValue(manualOrder, "flow_code"));
		}
		
		List orderList = new ArrayList();
		Map goodsMap = new HashMap();
		goodsMap.put("prod_offer_code", Const.getStrValue(manualOrder, "prod_offer_code")); // 商品编码
		goodsMap.put("prod_offer_name", Const.getStrValue(manualOrder, "prod_offer_name")); // 商品名称
		String type_id = null;
		String cat_id = Const.getStrValue(manualOrder, "prod_offer_type");
		CatGetByIdReq req = new CatGetByIdReq();
		req.setCat_id(cat_id);
		CatGetResp catGetResp = client.execute(req, CatGetResp.class);
		Cat cat = catGetResp.getCat();
		if (cat != null) {
			type_id = cat.getType_id();
		}
		goodsMap.put("prod_offer_type", type_id);
		goodsMap.put("offer_disacount_price", "0"); // 优惠价
		goodsMap.put("offer_coupon_price", Const.getStrValue(manualOrder, "pay_money")); // 实收价格
		goodsMap.put("prod_offer_num", "1"); // 商品数量
		goodsMap.put("prod_offer_heavy", "0"); // 商品重量
		goodsMap.put("card_type", "NM"); // 卡类型：大卡小卡纳诺卡
		goodsMap.put("certi_type", Const.getStrValue(manualOrder, "certi_type"));
		goodsMap.put("cust_type", "GRKH"); // 默认个人客户
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "customer_type"))) {
			goodsMap.put("cust_type", Const.getStrValue(manualOrder, "customer_type"));
		}
		goodsMap.put("cust_name", Const.getStrValue(manualOrder, "cust_name"));
		goodsMap.put("certi_num", Const.getStrValue(manualOrder, "certi_num"));
		goodsMap.put("certi_address", "");
		goodsMap.put("invoice_print_type", "3"); // 发票打印方式:1一次性，2分月，3不打印
		goodsMap.put("invoice_no", Const.getStrValue(manualOrder, "invoice_no")); // 发票号码
		goodsMap.put("terminal_num", Const.getStrValue(manualOrder, "terminal_num")); // 终端串号
		goodsMap.put("offer_price", payMoney); // 商品价格
		goodsMap.put("good_no_deposit", "0"); // 靓号预存款
		goodsMap.put("offer_eff_type", Const.getStrValue(manualOrder, "offer_eff_type")); //
		goodsMap.put("package_sale", "0");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (manualOrder.get("cust_info") != null && manualOrder.get("cust_info") instanceof Map) {
			Map cust_info = (Map) manualOrder.get("cust_info");
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_num"))) {
				goodsMap.put("cert_num", Const.getStrValue(cust_info, "cert_num"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_num2"))) {
                goodsMap.put("cert_num2", Const.getStrValue(cust_info, "cert_num2"));
            }
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_addr"))) {
				goodsMap.put("cert_addr", Const.getStrValue(cust_info, "cert_addr"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "customer_name"))) {
				goodsMap.put("customer_name", Const.getStrValue(cust_info, "customer_name"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_effected_date"))) {

				try {
					SimpleDateFormat dateFormator = new SimpleDateFormat("yyyyMMddHHmmss");
					java.util.Date tDate = dateFormator.parse(Const.getStrValue(cust_info, "cert_effected_date") + "000000", new ParsePosition(0));
					dateFormator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					goodsMap.put("cert_effected_date", format.format(tDate));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_sex"))) {
				goodsMap.put("cert_sex", Const.getStrValue(cust_info, "cert_sex"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_nation"))) {
				goodsMap.put("cert_nation", Const.getStrValue(cust_info, "cert_nation"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cust_birthday"))) {
				goodsMap.put("cust_birthday", Const.getStrValue(cust_info, "cust_birthday"));
			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_expire_date"))) {
				try {
					SimpleDateFormat dateFormator = new SimpleDateFormat("yyyyMMddHHmmss");
					java.util.Date tDate = dateFormator.parse(Const.getStrValue(cust_info, "cert_expire_date") + "000000", new ParsePosition(0));
					dateFormator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					goodsMap.put("cert_expire_date", format.format(tDate));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (!StringUtils.isEmpty(Const.getStrValue(cust_info, "cert_issuedat"))) {
				goodsMap.put("certi_issuer", Const.getStrValue(cust_info, "cert_issuedat"));
			}
		}
		String planTitle = Const.getStrValue(manualOrder, "plan_title");
		String oldPlanTitle = Const.getStrValue(manualOrder, "old_Out_plan_name_ess");
		String isChange = "0";
		if (!StringUtils.isEmpty(planTitle) && !StringUtils.isEmpty(oldPlanTitle) && planTitle.equals(oldPlanTitle)) {
			isChange = "1";
		}
		goodsMap.put("is_change", isChange); // 是否变更套餐，如果原套餐名称和当前办理套餐名称不一致，则认为是变更套餐
		goodsMap.put("plan_title", Const.getStrValue(manualOrder, "plan_title")); // 套餐名称
		goodsMap.put("special_busi_type", Const.getStrValue(manualOrder, "special_busi_type")); // 业务类型
		goodsMap.put("old_Out_plan_name_ess", Const.getStrValue(manualOrder, "old_plan_title")); // 老用户原套餐

		List skuParamList = new ArrayList();
		Map skuMap1 = new HashMap();
		skuMap1.put("param_code", "acc_nbr");
		skuMap1.put("param_name", "用户号码");
		skuMap1.put("param_value", Const.getStrValue(manualOrder, "acc_nbr"));
		skuParamList.add(skuMap1);

		Map skuMap2 = new HashMap();
		skuMap2.put("param_code", "is_old");
		skuMap2.put("param_name", "是否老用户");
		skuMap2.put("param_value", Const.getStrValue(manualOrder, "is_old"));
		skuParamList.add(skuMap2);

		Map skuMap3 = new HashMap();
		skuMap3.put("param_code", "if_love_no");
		skuMap3.put("param_name", "是否情侣号");
		skuMap3.put("param_value", "0"); // 默认否
		skuParamList.add(skuMap3);

		Map skuMap4 = new HashMap();
		skuMap4.put("param_code", "net_type");
		skuMap4.put("param_name", "网别");
		skuMap4.put("param_value", "4G"); // 待确认
		skuParamList.add(skuMap4);

		Map skuMap5 = new HashMap();
		skuMap5.put("param_code", "is_goodno");
		skuMap5.put("param_name", "靓号"); // 是否靓号，默认否，选号时传入
		skuMap5.put("param_value", "0");
		skuParamList.add(skuMap5);

		Map skuMap6 = new HashMap();
		skuMap6.put("param_code", "bill_type");
		skuMap6.put("param_name", "账户付费方式");
		skuMap6.put("param_value", "10"); // 默认现金
		skuParamList.add(skuMap6);

		Map skuMap7 = new HashMap();
		skuMap7.put("param_code", "card_type");
		skuMap7.put("param_name", "卡类型");
		skuMap7.put("param_value", "NM");
		skuParamList.add(skuMap7);

		Map skuMap8 = new HashMap();
		skuMap8.put("param_code", "guarantor");
		skuMap8.put("param_name", "担保人");
		skuMap8.put("param_value", "无");
		skuParamList.add(skuMap8);

		Map skuMap9 = new HashMap();
		skuMap9.put("param_code", "bill_mail_type");
		skuMap9.put("param_name", "账单寄送方式");// 账单寄送方式，00：不寄送，01：寄送，默认不寄送
		skuMap9.put("param_value", "00");
		skuParamList.add(skuMap9);

		goodsMap.put("sku_param", skuParamList);
		orderList.add(goodsMap);
		reqMap.put("order_list", orderList);

		if(manualOrder.get("extMap") instanceof Map&&null!=manualOrder.get("extMap")){
			reqMap.put("extMap", manualOrder.get("extMap"));
		}
		Map orderMap = new HashMap();
		orderMap.put("order_req", reqMap);

		String jsonStr = JsonUtil.toJson(orderMap);
		
		MarkTime mark = new MarkTime("saveManualOrder out_order_id="+out_tid);

		OrderCtnReq orderCtnReq = new OrderCtnReq();
		
		String is_update = Const.getStrValue(manualOrder, "is_update");
		
		if("1".equals(is_update)){
			//更新时，设置为更新订单数据的OutServiceCode
			orderCtnReq.setOutServiceCode("update_order_newMallOrderStandard");
		}else if (!StringUtil.isEmpty(rpc_type) && StringUtil.equals("M", rpc_type)) {
			orderCtnReq.setOutServiceCode("create_order_newMallOrderStandardM");
		} else {
			orderCtnReq.setOutServiceCode("create_order_newMallOrderStandard");
		}

		orderCtnReq.setVersion("1.0");
		orderCtnReq.setReqMsgStr(jsonStr);
		orderCtnReq.setFormat("json");
		orderCtnReq.setOutOrderId(out_tid);
		Map<String, Object> reqParamsMap = new HashMap<String, Object>();
		reqParamsMap.put("orderSource", reqMap.get("source_from"));
		orderCtnReq.setReqParamsMap(reqParamsMap);
		
		OrderCtnResp resp = client.execute(orderCtnReq, OrderCtnResp.class);
		
		mark.markEndTime();
		
		if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
			return resp;
		}
		
		resp.setBase_order_id(out_tid);
		
		OrderTreeBusiRequest orderTree = null;
		
		if("1".equals(is_update)){
			//更新的订单不会更新原有订单的外部单号，这时外部单号为后台生成的意向单的单号
			orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(Const.getStrValue(manualOrder, "rel_order_id"));
		}else{
			orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(out_tid);
		}

		if (null != orderTree && !StringUtils.isEmpty(orderTree.getOrder_id())) {
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			if (StringUtil.equals(is_aop, "1")) {
				orderItemExtBusiRequest.setBssOrderId(Const.getStrValue(manualOrder, "cbss_order_id"));
			} else if (StringUtil.equals(is_aop, "2")) {
				orderItemExtBusiRequest.setBssOrderId(Const.getStrValue(manualOrder, "bss_order_id"));
			}
			orderItemExtBusiRequest.store();
		}
		return resp;
	}

	@Override
	public List queryZbAuditOrders() {
		String sql = "select out_tid from(select a.out_tid,a.source_from,rownum from es_order_audit_zb a where (a.audit_status='0' or (a.audit_status='2' and a.audit_num<3))) where rownum<200 and source_from=?";
		List orders = this.baseDaoSupport.queryForList(sql, ManagerUtils.getSourceFrom());
		return orders;
	}

	@Override
	public void updateZbAuditStatus(ZbAuditStatusUpdateReq req) {
		String sql = "update es_order_audit_zb a set a.zb_id=?,a.audit_status=?,a.audit_time=sysdate,a.audit_num=a.audit_num+1,a.audit_type=?,a.audit_result=? where a.out_tid=? and a.source_from=? ";
		this.baseDaoSupport.execute(sql, req.getZb_id(), req.getAudit_status(), req.getAudit_type(), req.getAudit_result(), req.getOut_tid(), ManagerUtils.getSourceFrom());
	}

	@Override
	public List queryZbAuditSuccOrders() {
		String sql = "select out_tid from(select a.out_tid,a.source_from,rownum from es_order_audit_zb a where a.audit_status='1' and a.crawler_status='0') where rownum<200 and source_from=?";
		List orders = this.baseDaoSupport.queryForList(sql, ManagerUtils.getSourceFrom());
		return orders;
	}

	@Override
	public void updateZbCrawlerStatus(ZbCrawlerStatusUpdateReq req) {
		String sql = "update es_order_audit_zb a set a.crawler_status=?,a.crawler_time=sysdate,a.crawler_result=? where a.out_tid=? and a.source_from=? ";
		this.baseDaoSupport.execute(sql, req.getCrawler_status(), req.getOut_tid(), req.getCrawler_result(), ManagerUtils.getSourceFrom());
	}

	// 异步改成同步,返回异常信息
	public String updateStatus(String order_id, String service_code) {
		String msg = "0";
		// ICoQueueManager coQueueManager =
		// SpringContextHolder.getBean("coQueueManager");
		String sql = "select t.* from es_co_queue t where t.object_id='" + order_id + "' and t.service_code='" + service_code + "'";
		Page coPage = this.baseDaoSupport.queryForPageNoCount(sql, 1, 10, CoQueue.class, null);
		List<CoQueue> coQueueLst = coPage.getResult();
		CoQueueRuleReq coQueueRuleReq = null;
		for (CoQueue coQueue : coQueueLst) {
			coQueueRuleReq = new CoQueueRuleReq();
			coQueueRuleReq.setObject_id(coQueue.getObject_id());
			coQueueRuleReq.setCoQueue(coQueue);
			Map params = new HashMap();
			coQueueRuleReq.setParams(params);

			List paramLst = new ArrayList();
			paramLst.add(Consts.CO_QUEUE_STATUS_FSZ);
			paramLst.add(coQueue.getCo_id());
			this.baseDaoSupport.execute(SF.baseSql("LOCK_CO_QUEUE_BY_ID"), paramLst.toArray()); // 锁单（不要下次再被扫到）
			// ThreadPoolFactory.orderExecute(taskThreadPool); // 异步单线程执行
			try {
				// String service_code = coQueue.getService_code();
				String contents = coQueue.getContents() == null ? "" : coQueue.getContents();
				JSONObject json = JSONObject.fromObject(contents);
				String pay_result = json.getString("pay_result") == null ? "" : json.getString("pay_result");
				String work_result = json.getString("work_result") == null ? "" : json.getString("work_result");
				String out_order_id = json.getString("out_order_id") == null ? "" : json.getString("out_order_id");
				// String order_id = json.getString("order_id") == null ? "" :
				// json.getString("order_id");
				String order_from = "";
				String goods_type = "";// 商品类型
				OrderTreeBusiRequest tree = null;
				if (StringUtil.isEmpty(order_id)) {
					if (!StringUtil.isEmpty(out_order_id)) {
						tree = CommonDataFactory.getInstance().getOrderTreeByOutId(out_order_id);
						order_id = tree.getOrder_id();
					} else {
						throw new Exception("外部单号和内部单号不能同时为空");
					}
				} else {
					tree = CommonDataFactory.getInstance().getOrderTree(order_id);
				}
				
				if(tree == null)
					throw new Exception("找不到"+order_id+"订单的订单树对象");
				
				if(tree.getOrderExtBusiRequest() == null)
					throw new Exception("找不到"+order_id+"订单的订单树扩展对象");
				
				order_from = tree.getOrderExtBusiRequest().getOrder_from();
				goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
                String cat_id_group = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CAT_ID);
                String opttype = CommonDataFactory.getInstance().getAttrFieldValue(order_id,"opttype");//00 订购  01 退单 02 变更
               /* && (!"02".equals(opttype) && "10093".equals(order_from))
                || ("02".equals(opttype) &&"10093".equals(order_from) && "170801434582003010".equals(SpecConsts.TYPE_ID_GOODS_CBSS))*/
				OrderInfoUpdateReq request = (OrderInfoUpdateReq) JSONObject.toBean(json, OrderInfoUpdateReq.class);
				logger.info("====================调用支付组件获取的内部订单号：" + order_id);
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String close_group_order = cacheUtil.getConfigInfo("close_group_order");
				String close_group_order_catid = cacheUtil.getConfigInfo("close_group_order_cat_id");
				if("-1".equals(pay_result) && "10093".equals(order_from) && StringUtils.isNotEmpty(close_group_order) && StringUtils.isNotEmpty(close_group_order_catid) && close_group_order.contains(order_from)&&close_group_order_catid.contains(cat_id_group)){//无线宽带融合 支付更新返回失败  订单状态翻转为关闭 add by sgf
				    OrderExtBusiRequest orderBusiObject = tree.getOrderExtBusiRequest();
                    orderBusiObject.setAbnormal_type("4");
                    orderBusiObject.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                    orderBusiObject.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                    orderBusiObject.store();
                    OrderHandleLogsReq req = new OrderHandleLogsReq();
                    String flow_id = tree.getOrderExtBusiRequest().getFlow_id();
                    String flow_trace_id = tree.getOrderExtBusiRequest().getFlow_trace_id();
                    req.setOrder_id(order_id);
                    req.setFlow_id(flow_id);
                    req.setFlow_trace_id(flow_trace_id);
                    req.setComments("H5支付失败触发订单关闭");
                    req.setHandler_type("DIC_HANDLE_TYPE");
                    req.setType_code("DEFAULT");
                    req.setOp_id("1");
                    req.setOp_name("系统管理员");
                    this.baseDaoSupport.insert("es_order_handle_logs", req);
                    msg="0";
                    return  msg;
				}
				if ("0".equals(pay_result) && "1".equals(tree.getOrderExtBusiRequest().getIs_work_custom())) {
					//自定义流程--等待支付结果
					
					this.dealWorkCustomWaitPayResult(tree.getOrder_id(),contents);
				}else if ("0".equals(pay_result)) {// 接收支付结果组件
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					fact.setRequest(request);
					RuleTreeExeResp resp = new RuleTreeExeResp();// 如果订单来源是行销APP
					String xx_card_center = cacheUtil.getConfigInfo("xx_card_center");//号卡-总部（AOP）
	                Boolean flag = false;
	                if(!StringUtils.isEmpty(xx_card_center) && xx_card_center.contains(goods_type)){
	                        flag = true;
	                }
					if (EcsOrderConsts.PLAT_TYPE_10071.equals(order_from) || "10076".equals(order_from) ||"10094".equals(order_from)||"10099".equals(order_from) || "10096".equals(order_from) || "10095".equals(order_from) ||
							"10081".equals(order_from) || "10080".equals(order_from) || 
							"10082".equals(order_from) || "10097".equals(order_from) || "10093".equals(order_from) ||"10083".equals(order_from) || "10110".equals(order_from) ||"10108".equals(order_from)|| 
							("10104").equals(order_from)||("10072").equals(order_from)|| ("10112").equals(order_from)||("10087").equals(order_from) || ("10088").equals(order_from) ||
							("10070".equals(order_from) && 
									("170401633552001805".equals(goods_type)||"172501423222000452".equals(goods_type)))) {// addby.yehaibo永乐APP10076
						String rule_id = "";
						// 如果商品类型是行销APP-活动受理
						if("10083".equals(order_from) && !"172971653252000777".equals(goods_type) && !"180120953252000238".equals(goods_type)){
							//微信公众号，接收支付结果规则规则编号
							rule_id = cacheUtil.getConfigInfo("WECHAT_PAY_RULE_ID");
							
							if(StringUtil.isEmpty(rule_id))
								rule_id = "181042037570001118";
							
						}else if(AopInterUtil.isAopBrdOpenApply(goods_type)){
							rule_id = cacheUtil.getConfigInfo("AOP_MIX_BORD_PAY_STAT_RULE_ID");
							
							if(StringUtil.isEmpty(rule_id))
								rule_id = "180681443300000255";
						}else if (EcsOrderConsts.GOODS_TYPE_XXAPP_HDSL.equals(goods_type) || "170401633552001805".equals(goods_type) || "172501422362000444".equals(goods_type)) {
							rule_id = EcsOrderConsts.RECEIVE_PAY_RESULTS_XXAPP_HDSL_RULE;
						} else if (!"10093".equals(order_from) &&(EcsOrderConsts.GOODS_TYPE_DKD.equals(goods_type) || "170502124302000763".equals(goods_type) || "170502123552000755".equals(goods_type)
								|| "170502125012000771".equals(goods_type) || "170502112412000711".equals(goods_type) || "251723379126562816".equals(goods_type))) {// 如果商品类型是行销APP-宽带
							rule_id = EcsOrderConsts.RECEIVE_PAY_RESULTS_XXAPP_KD_RULE;
						} else if (SpecConsts.TYPE_ID_GOODS_BSS.equals(goods_type) || EcsOrderConsts.GOODS_TYPE_ID_JKHK.equals(goods_type)) { // 号卡省内addbyxiang.yangbo
							rule_id = EcsOrderConsts.BUSI_CARD_BSS_PAYRESULT_RULE_ID;  
						} else if ((flag ||SpecConsts.TYPE_ID_GOODS_CBSS.equals(goods_type) ||"226723870818693120".equals(goods_type)) && !"10093".equals(order_from)&&!"10108".equals(order_from)) { // 号卡总部addbyhuang.zhisheng
							rule_id = EcsOrderConsts.BUSI_CARD_AOP_PAYRESULT_RULE_ID;
						} else if((flag ||SpecConsts.TYPE_ID_GOODS_CBSS.equals(goods_type) ||"226723870818693120".equals(goods_type)) && "10093".equals(order_from) && !"02".equals(opttype)){
                            rule_id = EcsOrderConsts.BUSI_CARD_AOP_PAYRESULT_RULE_ID;
                        }else if (SpecConsts.TYPE_ID_CBSS_ACTIVITY.equals(goods_type) || "181251022122000026".equals(goods_type) || ("02".equals(opttype) &&"10093".equals(order_from) && "170801434582003010".equals(SpecConsts.TYPE_ID_GOODS_CBSS))) { // 总部-活动受理addbyhuang.zhisheng
							rule_id = EcsOrderConsts.BUSI_CUNFEESONGFEE_AOP_PAY_RULE_ID;
						} else if ("170601900021724272".equals(goods_type)) {
							rule_id = "171101558402000694";
						} else if ("172971653252000777".equals(goods_type) || "180120953252000238".equals(goods_type) ) {// 总部-流量办理或视频+流量办理
							rule_id = cacheUtil.getConfigInfo("taizhou_rule_id");
						} else if ("172501418202000419".equals(goods_type) || "172501423222000452".equals(goods_type)) { // Add By Wcl流量包子产品，子产品
							String new_rule_id = cacheUtil.getConfigInfo("PAYMENT_UPDATE_ZICP");
							rule_id = new_rule_id;
						} else if ("215040095894368256".equals(goods_type)){//终端更换
                            rule_id = cacheUtil.getConfigInfo("ZDGHZFRULEID");
                        }else if("10093".equals(order_from) && "170502112412000711".equals(goods_type)){//add by sgf 
                            rule_id = cacheUtil.getConfigInfo("group_order_payinfo");
                        }else if ("90000000000000621".equals(goods_type)){//押金
                            rule_id = cacheUtil.getConfigInfo("DESPOSIT_RULE_ID");
                        }else if("10108".equals(order_from) && "02".equals(opttype) && "170801434582003010".equals(SpecConsts.TYPE_ID_GOODS_CBSS)){
                        	rule_id = EcsOrderConsts.BUSI_CUNFEESONGFEE_AOP_PAY_RULE_ID;
                        }
						String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CAT_ID);
						if("180241424132000098".equals(cat_id)){
							rule_id = cacheUtil.getConfigInfo("PAY_CHG_PRE_RULE_ID_"+order_from);
						}else if(StringUtils.equals(cat_id, EcsOrderConsts.GOODS_CAT_ID_WUXIANKD) && !"10093".equals(order_from)) {
							rule_id = EcsOrderConsts.BUSI_CARD_BSS_PAYRESULT_RULE_ID;
						}
						
						if (!StringUtils.isEmpty(rule_id)) {
							// add by xiang.yangbo begin
							String planId = EcsOrderConsts.PAY_PLAN_ID;// 号卡省内
							if (SpecConsts.TYPE_ID_GOODS_BSS.equals(goods_type) || EcsOrderConsts.GOODS_TYPE_ID_JKHK.equals(goods_type)) {
								planId = EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID;
							} else if ( (flag ||SpecConsts.TYPE_ID_GOODS_CBSS.equals(goods_type)) && !"10093".equals(order_from)&& !"10108".equals(order_from)) { // 号卡总部
								planId = EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID;
							}else if((flag ||SpecConsts.TYPE_ID_GOODS_CBSS.equals(goods_type)) && "10093".equals(order_from) && !"02".equals(opttype)){
	                             planId = EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID;
							} else if ("170601900021724272".equals(goods_type)) {
								planId = "100";
							}
							resp = RuleFlowUtil.executeRuleTree(planId, rule_id, fact, true, true, EcsOrderConsts.DEAL_FROM_INF);
							// addbyxiang.yangboend
						}
					} else if (("10078".equals(order_from) || EcsOrderConsts.PLAT_TYPE_10016.equals(order_from) || EcsOrderConsts.PLAT_TYPE_10070.equals(order_from)
							||"100901".equals(order_from)||"100902".equals(order_from)||"100903".equals(order_from)||"100904".equals(order_from)||"100905".equals(order_from)||"100906".equals(order_from)||"100907".equals(order_from)||"100908".equals(order_from)||"100909".equals(order_from))
							&& "170502112412000711".equals(goods_type)) { // 来源为码上购和10016
						if ("170502112412000711".equals(goods_type)) { // 融合业务
							resp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.PAY_PLAN_ID, EcsOrderConsts.BUSI_MSG10016_AOP_PAY_RULE_ID, fact, true, true, EcsOrderConsts.DEAL_FROM_INF);
						}
					} else if ("10072".equals(order_from)||"10112".equals(order_from)) {
						resp = RuleFlowUtil.executeRuleTree("100", "180121606500000066", fact, true, true, EcsOrderConsts.DEAL_FROM_INF);
					} else {
						resp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.PAY_PLAN_ID, EcsOrderConsts.RECEIVE_PAY_RESULTS_RULE, fact, true, true, EcsOrderConsts.DEAL_FROM_INF);// 单宽
					}
					logger.info("CO_PAY_RESULT_BD:" + resp.getError_code() + "," + resp.getError_msg());
					if (!"0".equals(resp.getError_code())) {
						msg = resp.getError_msg();
					}
				}
				// coQueueManager.del(coQueue.getCo_id());

				coQueue.setStatus(Consts.CO_QUEUE_STATUS_XYCG);
				coQueue.setResp_date(DateUtil.current(DateUtil.defaultPattern));
				baseDaoSupport.insert("es_co_queue_bak", coQueue);
				// 删除记录
				baseDaoSupport.execute(SF.baseSql("DELETE_CO_QUEUE_BY_ID"), coQueue.getCo_id());

			} catch (Exception e) {
				msg = e.getMessage();
				
				e.printStackTrace();
				// coQueueManager.modifyStatus(coQueue.getCo_id(), "-1",
				// e.getMessage());
				List paramList = new ArrayList();
				// 如果接口处理返回是不成功，就更新消息队列为失败，处理次数加1
				if (!Consts.RESP_CODE_000.equals("-1")) {
					paramList.add(Consts.CO_QUEUE_STATUS_XYSB);
					paramList.add(e.getMessage());
					paramList.add(coQueue.getCo_id());
					baseDaoSupport.execute(SF.baseSql("UPDATE_CO_QUEUE_BY_ID"), paramList.toArray());
				}
			}
		}
		return msg;
	}

	@Override
	public String saveWork(Map map) {
		String flag = "";
		String order_id = map.get("order_id") + "";
		try {
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			String sql_intent = "select * from es_order_intent where order_id=?";
			List<Map<String, Object>> list_intent = baseDaoSupport.queryForList(sql_intent, order_id);
			IOrdWorkManager ordWorkManager = SpringContextHolder.getBean("ordWorkManager");
			List<OrderWorksBusiRequest> workList = ordWorkManager.queryWorkListByOrderId(order_id);
			if (list_intent.size() > 0) {
				if ("03".equals(list_intent.get(0).get("status") + "")
						|| "00".equals(list_intent.get(0).get("status") + "")) {
					return "该意向单已经完成，不允许再次派发工单";
				}
				if (workList.size() > 0) {
					for (OrderWorksBusiRequest work : workList) {
						ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
						String status = cacheUtil.getConfigInfo("RETURN_WORK_STATUS");
						if (status.contains(work.getStatus())) {
							flag = "该意向单已经派发工单，不允许再次派发";
							return flag;
						}
					}
				}
				map.put("order_contact_name", list_intent.get(0).get("ship_name"));
				map.put("order_contact_phone", list_intent.get(0).get("ship_tel"));
				map.put("order_contact_addr", list_intent.get(0).get("ship_addr"));
				map.put("order_product_name", list_intent.get(0).get("goods_name"));
				map.put("order_product_id", list_intent.get(0).get("goods_id"));
				if (!StringUtils.isEmpty(list_intent.get(0).get("real_offer_price") + "")) {
					String order_amount = list_intent.get(0).get("real_offer_price") + "";
					Double amount = Double.parseDouble(order_amount) / 1000;
					map.put("order_amount", amount.intValue());
				} else {
					map.put("order_amount", 0);
				}
				map.put("order_from", list_intent.get(0).get("source_system_type"));
				map.put("order_time", list_intent.get(0).get("create_time"));
			} else {
				String type = map.get("type") + "";
				// 是否收费,0否;1是
				String is_pay = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_PAY);
				// 支付状态,0否;1是
				//String pay_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_STATUS);
				// 是否实名0未实名1已实名
				String is_real_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
						AttrConsts.IS_REAL_NAME);
				if ("1".equals(is_pay) && "01".equals(type)) {
					flag = "已经付费的订单不允许派发收费单";
					return flag;
				}
				if ("1".equals(is_real_name) && "03".equals(type)) {
					flag = "已经实名的订单不允许派发实名单";
					return flag;
				}
				if (null != workList && workList.size() > 0) {
					for (OrderWorksBusiRequest work : workList) {
						// 01 – 收费单；02 -- 外勘单；03 – 实名单；04 – 挽留单；05 – 写卡单
						// 工单处理状态0 — 未处理 1 — 处理成功2 — 处理失败
						// 相应工单处理失败可以再次派送
						if (type.equals(work.getType())
								&& ("0".equals(work.getStatus()) || "1".equals(work.getStatus()))) {
							flag = "已有同类工单类型";
							DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
							List<Map> typeList = dcPublicCache.getList("WORK_TYPE");
							for (int i = 0; i < typeList.size(); i++) {
								if (StringUtil.equals(type, (String) typeList.get(i).get("pkey"))) {
									flag = (String) typeList.get(i).get("pname");
									break;
								}
							}
							return flag;
						}
					}
				}
			}
			String goods_cat_id="";
			String sql_order_amount=" o.paymoney * 1000 AS order_amount, ";
	        goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id,"goods_cat_id");
	        if("90000000000000901".equals(goods_cat_id)) {
	        	sql_order_amount=" o.order_amount, ";
	        }
			String sql = "select e.order_from,e.tid_time,e.order_from,e.order_city_code,d.ship_name as order_contact_name, (case when d.ship_mobile = '-1' then d.ship_tel else d.ship_mobile end) as order_contact_phone, d.ship_addr as order_contact_addr, g.name as order_product_name, "+sql_order_amount+" g.goods_id as order_product_id, v.phone_owner_name as customer_name, p.certi_type as cert_type, p.cert_card_num as cert_num, v.develop_code_new as staff_id, t.goods_type as goods_type "
					+ "from es_order o left join es_order_ext e on o.order_id = e.order_id left join es_order_extvtl v on o.order_id = v.order_id left join es_order_items_prod_ext p on o.order_id = p.order_id left join es_order_items_ext t on o.order_id = t.order_id left join es_order_items i on o.order_id = i.order_id left join es_goods g on i.goods_id = g.goods_id left join es_delivery d on o.order_id = d.order_id where o.order_id = '"
					+ order_id + "' and o.source_from ='" + ManagerUtils.getSourceFrom() + "'";
			List list = this.baseDaoSupport.queryForList(sql);
			if (list.size() > 0) {
				Map info = (Map) list.get(0);
				map.put("order_contact_name", info.get("order_contact_name"));// 联系人姓名
				map.put("order_contact_phone", info.get("order_contact_phone"));// 联系人电话
				map.put("order_contact_addr", info.get("order_contact_addr"));// 联系人地址/宽带标准地址
				map.put("order_product_name", info.get("order_product_name"));// 订单商品名称
				map.put("order_amount", info.get("order_amount"));// 订单金额

				map.put("order_product_id", info.get("order_product_id"));// 订单商品ID
				map.put("customer_name", info.get("customer_name"));// 客户证件姓名
				map.put("cert_type", info.get("cert_type"));// 证件类型
				map.put("cert_num", info.get("cert_num"));// 证件号码
				map.put("order_time", info.get("tid_time"));// 订单创建时间
				//泛智能终端类型、名字沉淀
				StringBuffer sql_terminal_name=new StringBuffer();
				StringBuffer sql_terminal_type=new StringBuffer();
				sql_terminal_name.append("select distinct t.terminal_name from es_goods_action_element t left join es_order_extvtl b on t.element_id=b.element_id where b.order_id='").append(order_id).append("' ").append("and b.source_from='").append(ManagerUtils.getSourceFrom()).append("' ");
				sql_terminal_type.append("select distinct a.mobile_type from es_order_extvtl a where a.order_id='").append(order_id).append("' ");
				String terminal_type=daoSupport.queryForString(sql_terminal_type.toString());
				terminal_type = terminal_type == null ? "" : terminal_type;
				String terminal_name="";
				
				List<Map<String , Object>> list_map = daoSupport.queryForList(sql_terminal_name.toString());
				if(list_map!=null && list_map.size()>0 && list_map.get(0).containsKey("terminal_name")) {
					terminal_name = String.valueOf(list_map.get(0).get("terminal_name"));
					terminal_name = terminal_name == null ? "" : terminal_name;
				}
				map.put("terminal_type", terminal_type);
				map.put("terminal_name", terminal_name);
				
				if (StringUtil.isEmpty(info.get("staff_id") + "")) {
					BSSGonghaoInfoVO vo = new BSSGonghaoInfoVO();
					vo.setCity_id(info.get("order_city_code") + "");
					vo.setOrder_from(info.get("order_from") + "");
					map.put("staff_id", vo.getUser_code());
				} else {
					map.put("staff_id", info.get("staff_id"));// 受理工号-发展人编码
				}
				String goods_type = info.get("goods_type") + "";
				String trade_type_code = "";
				IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
				List<Map> listType = dcPublicCache.getList("TRADE_TYPE_CODE");
				for (Map<String, String> mapType : listType) {
					if (StringUtils.equals(mapType.get("pkey"), goods_type)) {
						trade_type_code = mapType.get("pname");
						break;
					}
				}
				// 业务类型 01：开户 02：返档: 03：使用人补录: 04：过户: 05：融合新装: 06：融合变更
				map.put("trade_type_code", trade_type_code);
				if (null == map.get("map") || StringUtils.isEmpty(map.get("map") + "")) {
					map.put("order_from", info.get("order_from"));
				}
			}
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String prefix = cacheUtil.getConfigInfo("WORK_PREFIX");
			if (StringUtil.isEmpty(prefix)) {
				prefix = "";
			}
			String work_order_id = prefix + this.baseDaoSupport.getSequences("s_es_order");
			map.put("work_order_id", work_order_id);
			map.put("status", "0");
			//判断是否是自动派单的标识
			if ("0000".equals(map.get("order_send_usercode"))) {
				map.put("order_send_usercode", "");
				map.put("order_send_username", "系统自动派单");
				map.put("order_send_userphone", "");
			}else{
				map.put("order_send_usercode", ManagerUtils.getAdminUser().getUserid());
				map.put("order_send_username", ManagerUtils.getAdminUser().getRealname());
				map.put("order_send_userphone", ManagerUtils.getAdminUser().getPhone_num());
			}
			this.baseDaoSupport.insert("es_work_order", map);

			String remark = map.get("remark") + "";
			String operator_num = map.get("operator_num") + "";// 操作员联系电话
			String operator_name = map.get("operator_name") + "";// 操作员姓名
			String operator_id = map.get("operator_id") + "";// 操作员工号
			String operator_office_id = map.get("operator_office_id") + "";// 操作点id

			if (list_intent.size() > 0) {// 意向单状态修改
				Map<String, Object> fieldsMap = new HashMap<String, Object>();
				fieldsMap.put("status", "02");
				Map<String, Object> whereMap = new HashMap<String, Object>();
				whereMap.put("order_id", order_id);
				daoSupport.update("es_order_intent", fieldsMap, whereMap);

				map.clear();// 添加意向单操作记录
				map.put("action", "派单");
				map.put("remark", remark);
				map.put("order_id", order_id);
				map.put("work_order_id", work_order_id);
				if (!StringUtils.isEmpty(operator_id)) {
					map.put("work_handle_id", operator_id);
				}
				map.put("work_handle_name", operator_name);

				ordIntentManager.addIntentRecord(map);
			}
			flag = "0";
		} catch (Exception e) {
			e.printStackTrace();
			flag = e.getMessage();
		} finally {
			return flag;
		}
	}

    @Override
    public Map<String, String> changeDevelopmetInfo(String order_id, OrderTreeBusiRequest orderTree,List<Map<String, String>> esOrderQueue) {
        String code="";
        String is_aop = "";
        String order_from  = "";
        if(esOrderQueue.size()>0){
         code = esOrderQueue.get(0).get("service_code");
         is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
         order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
        }
        String developmentCode = "";
        String development_point_code ="";
        String developmentName = "";
        String development_point_name = "";
 
        developmentName = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "develop_name_new");
        if("".equals(developmentName) || developmentName==null){
            if(orderTree.getOrderAdslBusiRequest().size()>0){
                developmentName =  orderTree.getOrderAdslBusiRequest().get(0).getDevelop_name();
            }
        }
        developmentCode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "develop_code_new");
        if("".equals(developmentCode) || developmentCode==null){
            if(orderTree.getOrderAdslBusiRequest().size()>0){
                developmentCode =  orderTree.getOrderAdslBusiRequest().get(0).getDevelop_code();
            }
        }
        development_point_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "develop_point_code_new");
        if("".equals(development_point_code) || development_point_code==null){
            if(orderTree.getOrderAdslBusiRequest().size()>0){
                development_point_code =  orderTree.getOrderAdslBusiRequest().get(0).getDevelop_point_code();
            }
        }
        development_point_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "development_point_name");
        if("".equals(development_point_name) || development_point_name==null){
            if(orderTree.getOrderAdslBusiRequest().size()>0){
                development_point_name =  orderTree.getOrderAdslBusiRequest().get(0).getDevelop_point_name();
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        if(StringUtils.isEmpty(development_point_name)){
            development_point_name = "";
        }
        if(StringUtils.isEmpty(development_point_code)){
            development_point_code ="";
        }
        if(StringUtils.isEmpty(developmentName)){
            developmentName ="";
        }
        if(StringUtils.isEmpty(developmentCode)){
            developmentCode = "";
        }
        map.put("developmentCode",developmentCode );
        map.put("developmentName", developmentName);
        map.put("development_point_name",development_point_name);
        map.put("development_point_code",development_point_code);
        return map;
    }
    
    /**
     * 处理自定义流程等待支付结果
     * @param order_id 订单编号
     * @param json	JSON字符串
     * @throws Exception
     */
	private void dealWorkCustomWaitPayResult(String order_id,String json) throws Exception{
		
		String instance_id = this.workCustomEngine.checkCurrNodes(order_id, 
				"等待支付结果", "WORKFLOW_MOBILE_WAIT_PAY_NODES");
		
		String json_param = Const.PAY_RESULT+"<separator>"+json;
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(instance_id)){
			WORK_CUSTOM_FLOW_DATA flowdata = this.workCustomEngine.runNodeManual(instance_id, 
					false, "", "等待支付结果", json_param);
			
			if(ConstsCore.ERROR_FAIL.equals(flowdata.getRun_result()))
				throw new Exception(flowdata.getRun_msg());
		}else{
			throw new Exception(order_id+"订单不在等待支付结果环节");
		}
	}
	@Override
	public OrderCtnResp insertManualOrder(Map manualOrder, String rpc_type) {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		Map reqMap = new HashMap();
		String out_tid = Const.getStrValue(manualOrder, "out_order_id");
		
		out_tid = StringUtils.isEmpty(out_tid) ? "ZJ" + SequenceTools.getShort18PrimaryKey() : out_tid;
		reqMap.put("serial_no", UUID.randomUUID());
		reqMap.put("create_time", com.ztesoft.net.framework.util.StringUtil.getTransDate());
		reqMap.put("source_system", Const.getStrValue(manualOrder, "source_system")); // 发起方系统标识，单点集成系统传递
		reqMap.put("source_system_type", Const.getStrValue(manualOrder, "source_system_type"));
		reqMap.put("out_order_id", out_tid);
		
		reqMap.put("order_province_code", Const.getStrValue(manualOrder, "order_province_code")); // 订单来源系统（大类）
		reqMap.put("order_city_code", Const.getStrValue(manualOrder, "order_city_code")); // 订单来源（小类）
		reqMap.put("service_num", Const.getStrValue(manualOrder, "service_num"));
		reqMap.put("prod_offer_code",Const.getStrValue(manualOrder, "prod_offer_code"));
		reqMap.put("prod_offer_type", Const.getStrValue(manualOrder, "prod_offer_code"));
		reqMap.put("prod_offer_name", Const.getStrValue(manualOrder, "prod_offer_name"));// 外部支付账号
		reqMap.put("is_pay", Const.getStrValue(manualOrder, "is_pay"));
		reqMap.put("fee_type", Const.getStrValue(manualOrder, "fee_type"));
		reqMap.put("orig_fee", Const.getStrValue(manualOrder, "orig_fee"));
		reqMap.put("relief_fee", Const.getStrValue(manualOrder, "relief_fee")); // BSS工号
		reqMap.put("real_fee", Const.getStrValue(manualOrder, "real_fee")); //
		reqMap.put("fee_rule_id", Const.getStrValue(manualOrder, "fee_rule_id")); // 订单支撑系统工号，如果不为空，订单标准化后使用该工号锁定订单
		reqMap.put("deal_operator", Const.getStrValue(manualOrder, "deal_operator")); // 物流公司编码
		reqMap.put("deal_office_id", Const.getStrValue(manualOrder, "deal_office_id")); // 配送方式，默认平邮
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "pay_type"))) {
			reqMap.put("pay_type", Const.getStrValue(manualOrder, "pay_type")); //
		}
		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "pay_method"))) {
			reqMap.put("pay_method", Const.getStrValue(manualOrder, "pay_method")); //
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "can_cancel_date"))) {
			reqMap.put("can_cancel_date", Const.getStrValue(manualOrder, "can_cancel_date"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "order_remark"))) {
			reqMap.put("order_remark", Const.getStrValue(manualOrder, "order_remark"));
		}

		if (!StringUtil.isEmpty(Const.getStrValue(manualOrder, "order_county_code"))) {
			reqMap.put("order_county_code", Const.getStrValue(manualOrder, "order_county_code")); //
		}
		
		Map orderMap = new HashMap();
		orderMap.put("order_req", reqMap);

		String jsonStr = JsonUtil.toJson(orderMap);
		
		MarkTime mark = new MarkTime("insertManualOrder out_order_id="+out_tid);

		OrderCtnReq orderCtnReq = new OrderCtnReq();
		
		orderCtnReq.setOutServiceCode("create_order_depositStandard");

		orderCtnReq.setVersion("1.0");
		orderCtnReq.setReqMsgStr(jsonStr);
		orderCtnReq.setFormat("json");
		orderCtnReq.setOutOrderId(out_tid);
		Map<String, Object> reqParamsMap = new HashMap<String, Object>();
		reqParamsMap.put("orderSource", reqMap.get("source_system_type"));
		orderCtnReq.setReqParamsMap(reqParamsMap);
		OrderCtnResp resp = client.execute(orderCtnReq, OrderCtnResp.class);
		
		mark.markEndTime();
		
		if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
			return resp;
		}
		
		resp.setBase_order_id(out_tid);
		
		OrderTreeBusiRequest orderTree = null;
		
		orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(out_tid);

		if (null != orderTree && !StringUtils.isEmpty(orderTree.getOrder_id())) {
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.store();
		}
		return resp;
	}
	@Override
	public String getActiveNo(String order_id){
		
		String sql ="select t.active_no from  es_order_items_ext t where t.order_id='"+order_id+"' and t.source_from='ECS'";
		
		String activeNo=this.baseDaoSupport.queryForString(sql);
		
		return activeNo;
	}
}