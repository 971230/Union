package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import params.adminuser.req.AdminUserReq;
import services.AdminUserInf;
import services.LanInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Lan;
import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Cloud;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.ItemCard;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.IAccNbrManager;
import com.ztesoft.net.mall.core.service.ICloudManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.service.impl.cache.IGoodsCacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;


/**
 * 云卡调拨处理类
 * 
 * @author wui
 */

public class CloudManager extends BaseSupport implements ICloudManager {

	IAccNbrManager accNbrManager;
	AdminUserInf adminUserServ;
	ICacheUtil cacheUtil;
	IGoodsCacheUtil goodsCacheUtil;
	public IGoodsCacheUtil getGoodsUtil() {
		return goodsCacheUtil;
	}

	public void setGoodsUtil(IGoodsCacheUtil goodsCacheUtil) {
		this.goodsCacheUtil = goodsCacheUtil;
	}

	IOrderManager orderManager;
	IOrderNFlowManager orderNFlowManager;
	IOrderUtils orderUtils;
    @Resource
    private LanInf lanServ;

	@SuppressWarnings("unchecked")
	@Override
	public void transfer_cloud_for_collect(OrderRequst orderRequst,
			OrderResult orderResult) {

		// 此流程二级向一级申请的时候才会执行

		// 云卡申请处理，库存占用
		Order order = orderResult.getOrder();
		Integer goods_num = orderRequst.getGoodsApply().getGoods_num();// order.getGoods_num();
		// Double price = order.getOrder_amount();

		// 根据金额获取市场价格
		Double marketPrice = orderUtils.getMarketPrice(order);

		GoodsApply goodsApply = orderRequst.getGoodsApply();
		// String begin_nbr =goodsApply.getBegin_nbr();
		// String end_nbr =goodsApply.getEnd_nbr();
		Integer price_int = ManagerUtils.getIntegerVal(marketPrice);

		// 设置销售品id
		String crm_offer_id = goodsCacheUtil.getGoodsById(order.getGoods_id())
				.getCrm_offer_id();

		/**
		 * 去掉号码相关的验证 add by hu.yi 去掉了号码的选择
		 */

		/*
		 * if(StringUtils.isEmpty(begin_nbr) || StringUtils.isEmpty(end_nbr)){
		 * throw new RuntimeException("起始号码、结止号码不能为空"); }
		 * 
		 * if(new Long(begin_nbr)>new Long(end_nbr)){ throw new
		 * RuntimeException("开始号码不能大于结束号码！"); }
		 * 
		 * if(begin_nbr.length() !=11 || end_nbr.length() != 11){ throw new
		 * RuntimeException("号码格式非法！"); }
		 * 
		 * if(!begin_nbr.substring(0, 3).equals(end_nbr.substring(0, 3))) {
		 * throw new RuntimeException("只能申请同个号码段，请检查 ！"); }
		 * 
		 * 
		 * String accInterval = ((new Long(end_nbr).longValue()-new
		 * Long(begin_nbr).longValue()) +1)+""; if(new
		 * Long(accInterval)<goodsApply.getGoods_num()){ throw new
		 * RuntimeException("申请号码区间小于订购数量,请重新输入！"); }
		 * 
		 * 
		 * 
		 * Cloud accCloud = this.getCloudByAccNbr(begin_nbr); if(accCloud ==
		 * null || StringUtils.isEmpty(accCloud.getPhone_num()) || (
		 * accCloud.getShip_state() != null &&
		 * !Consts.SHIP_STATE_5.equals(accCloud.getShip_state()) ) ) //加上状态检查 {
		 * throw new RuntimeException("开始号码无效，请检查"); }
		 * 
		 * 
		 * accCloud = this.getCloudByAccNbr(end_nbr); if(accCloud == null ||
		 * StringUtils.isEmpty(accCloud.getPhone_num()) || (
		 * accCloud.getShip_state() != null &&
		 * !Consts.SHIP_STATE_5.equals(accCloud.getShip_state()) ) ) //加上状态检查 {
		 * throw new RuntimeException("结束号码无效，请检查"); }
		 */

		// 获取可用的卡数量
		Cloud cloud = new Cloud();
		cloud.setState(Consts.CARD_INFO_STATE_0);
		cloud.setP_apply_show_parent_stock("yes");
		cloud.setGoods_id(order.getGoods_id());
		cloud.setPay_money(marketPrice);
		cloud.setLan_id(order.getLan_id()); // 库存判断加上本地网判断
		// cloud.setBegin_nbr(begin_nbr); // 库存判断加上号段判断
		// cloud.setEnd_nbr(end_nbr); // 库存判断加上号段判断

		Map cardMap = listc(cloud);
		String ableCloud_count = (String) cardMap.get("ableCloud_count");
		if (StringUtil.isEmpty(ableCloud_count))
			ableCloud_count = "0";
		Integer canUsedCountInt = new Integer(ableCloud_count).intValue();

		/*
		 * //号码区间大于1，不允许申请 List lanIds = this.getLanCounts(begin_nbr,
		 * end_nbr,cloud);
		 * 
		 * if(!ListUtil.isEmpty(lanIds) && lanIds.size()>1) { throw new
		 * RuntimeException("只能申请同本地网号码，请检查！"); }else{
		 * if(ListUtil.isEmpty(lanIds)) throw new
		 * RuntimeException("申请号码已失效，请检查！");
		 * 
		 * String lan_id =(String)((Map)lanIds.get(0)).get("lan_id");
		 * if(!StringUtil.isEmpty(order.getLan_id())) {
		 * if(!lan_id.equals(order.getLan_id())){ throw new
		 * RuntimeException("只能申请同本地网号码，请检查！"); } } }
		 * 
		 * 
		 * //判断商品的支付价格与调拨的号码价格是否一致，不一致不允许处理 List payMoneys =
		 * this.getPayMoneyLevel(begin_nbr, end_nbr);
		 * if(!ListUtil.isEmpty(payMoneys)) { if(payMoneys.size()>1) throw new
		 * RuntimeException("只能调拨同一面值的的云卡！"); else if(payMoneys.size() ==1) {
		 * Object pay_money = ((Map)payMoneys.get(0)).get("pay_money"); Double
		 * mktprice =
		 * cacheUtil.getGoodsById(orderRequst.getGoodsApply().getGoods_id
		 * ()).getMktprice(); Integer pay_money_int
		 * =ManagerUtils.getIntegerVal(new Double(pay_money.toString()));
		 * Integer mktpriceInt =ManagerUtils.getIntegerVal(new
		 * Double(mktprice)); if( pay_money_int.intValue()
		 * !=mktpriceInt.intValue()) { throw new
		 * RuntimeException("调拨云卡面值为"+pay_money
		 * +"而实际申请云卡金额为"+mktprice+",金额不一致，请检查！"); } } }
		 */

		// 区间限制
		List limitScope = cacheUtil
				.doDcPublicQuery(Consts.DC_PUBLIC_DEF_ACC_SCOPE_LIMIT);
		String limit = (String) ((Map) limitScope.get(0)).get("codea");
		if (StringUtils.isEmpty(limit))
			limit = "0";

		// 号码段区间控制
		List maxCount = cacheUtil
				.doDcPublicQuery(Consts.DC_PUBLIC_DEF_ACC_MAX_COUNT);
		String maxcount_s = (String) ((Map) maxCount.get(0)).get("codea");
		if (goods_num > new Integer(maxcount_s).intValue()) {
			throw new RuntimeException("一次最多只能申请" + maxcount_s + "个号码，请检查");
		}

		// 申请号码区间太大，直接提示异常
		if (new Long(canUsedCountInt) > (goodsApply.getGoods_num() + new Integer(
				limit).intValue())) {
			throw new RuntimeException("申请号码区间太大,请重新输入！");
		}

		if (canUsedCountInt < goods_num) {
			throw new RuntimeException("用户购买云卡数量" + goods_num + "（个），超出可用库存数"
					+ canUsedCountInt + "，库存不足，请先进货!");
		}

		String ship_state = "";
		String appendSql = "";
		String whereCond = "";
		if (ManagerUtils.isFirstPartner()
				|| Consts.APPLY_TYPE_NET.equals(goodsApply.getApply_from())) {

			// 一级向电信申请
			ship_state = Consts.SHIP_STATE_0;
			appendSql = "  ship_state ='" + Consts.SHIP_STATE_0 + "'"; // 预占中
			whereCond = "  and to_userid is null  and ship_state is null ";

			/*
			 * cloud.setBegin_nbr(begin_nbr); cloud.setEnd_nbr(end_nbr);
			 * //根据其始号码结束号码获取本地网 String lan_id
			 * =getLanId(begin_nbr,end_nbr,cloud);
			 * if(StringUtil.isEmpty(lan_id)) throw new
			 * RuntimeException("号码区间没有可用的号码");
			 */
		} else if (ManagerUtils.isSecondPartner()) { // 二级向一级申请
			ship_state = Consts.SHIP_STATE_00;
			appendSql = " ship_state ='" + Consts.SHIP_STATE_00 + "'"; // 调拨二级不需要电信处理
			AdminUser adminUser = ManagerUtils.getAdminUser();
			whereCond = " and ship_state ='" + Consts.SHIP_STATE_5
					+ "' and to_userid ='" + adminUser.getParuserid()
					+ "' and lan_id = '" + order.getLan_id() + "'"; // 查询上级已调拨库存
																	// //加上本地网过滤
		}

		// lan_id 从页面选择。 已经不用选择开始结束号码，所以号码是否跨本地网的判断也不需要了
		// List lanIds = this.getLanCounts(null, null, cloud);
		// if(!ListUtil.isEmpty(lanIds)){
		// String lan_id = (String)((Map)lanIds.get(0)).get("lan_id");
		// Order uOrder = new Order();
		// uOrder.setOrder_id(order.getOrder_id());
		// uOrder.setAccept_status(null);
		// uOrder.setLan_id(lan_id);
		// orderNFlowManager.updateOrder(uOrder);
		// }

		// 分配库存,根据号段占用
		/*String updateSql = "update es_cloud_info  cf set order_id = ?, "
				+ appendSql
				+ "   where offer_id = ? and pay_money = ? and  state="
				+ Consts.CARD_INFO_STATE_0 + "  and ROWNUM < "
				+ (goods_num + 1) + " " + whereCond + " ";*/
		String updateSql = SF.orderSql("SERVICE_CLOUD_INFO_UPDATE") + " and  state="
				+ Consts.CARD_INFO_STATE_0 + DBTUtil.andRownum((goods_num + 1)+"") + " " + whereCond + " ";
		this.baseDaoSupport.execute(updateSql, order.getOrder_id(),ship_state,
				crm_offer_id, price_int);

	}

	@Override
	@SuppressWarnings("unchecked")
	public void transfer_cloud_for_accept(OrderRequst orderRequst,
			OrderResult orderResult) {

		// TODO 调用云卡调拨处理方法调拨云卡

		Order order = orderResult.getOrder();

		String to_userid = orderResult.getOrder().getUserid();
		String from_userid = ManagerUtils.getUserId();

		String appendSql = "";
		String whereCond = "";

		// 更新一级分销商
		AdminUser fAdminUser = orderUtils.getAdminUserById(orderResult
				.getOrder().getUserid());

		if (ManagerUtils.isNetStaff()) { // 电信调拨给一级
			appendSql = " username ='" + fAdminUser.getUsername()
					+ "', first_userid = '"
					+ orderResult.getOrder().getUserid()
					+ "' , first_orderid ='" + order.getOrder_id()
					+ "', ship_state ='" + Consts.SHIP_STATE_1 + "'"; // 处理中
			whereCond = " and ship_state ='" + Consts.SHIP_STATE_0 + "'"; // 处理中
		} else if (ManagerUtils.isFirstPartner()) { // 一级调拨给二级
			appendSql = " ship_state ='" + Consts.SHIP_STATE_11 + "'"; // 设置为处理中
			//whereCond = " and  ship_state ='" + Consts.SHIP_STATE_00
			//		+ "' and rownum<=" + order.getGoods_num(); // 查询预占,可用的
			whereCond = " and  ship_state ='" + Consts.SHIP_STATE_00
					+ "'" +DBTUtil.andRownum(order.getGoods_num()+"") ; // 查询预占,可用的
		}

		// 分配库存
		String updateSql = SF.orderSql("SERVICE_CLOUD_ACCEPT_UPDATE").replaceAll("#updatecol", appendSql)
				+ whereCond;
		this.baseDaoSupport.execute(updateSql, from_userid, to_userid,
				order.getOrder_id());

		// 接口处理
		if (ManagerUtils.isNetStaff()) {
			List<ItemCard> itemCards = orderManager.getItemCardByOrderId(order
					.getOrder_id());
			ItemCard begAccItemCard = getItemCard(itemCards, "begin_nbr"); // orderManager.getItemCardByFieldName(order.getOrder_id(),
																			// "begin_nbr",
																			// itemCards);
			ItemCard endAccItemCard = getItemCard(itemCards, "end_nbr"); // orderManager.getItemCardByFieldName(order.getOrder_id(),
																			// "end_nbr",
																			// itemCards);
			String begin_accnbr = begAccItemCard.getField_value();
			String end_accnbr = endAccItemCard.getField_value();

			String crm_offer_id = goodsCacheUtil.getGoodsById(order.getGoods_id())
					.getCrm_offer_id();
			String lan_id = order.getLan_id();
			AdminUser currAdmin = orderUtils
					.getAdminUserById(order.getUserid());

			
			Map curSysMap = new HashMap(); //add by wui暂时先屏蔽
			//orderUtils.getAgentSystemUser(currAdmin
				//	.getRelcode());

			Map operOrgMap = orderUtils.getOrgByStaffIdAndLanid(ManagerUtils
					.getAdminUser().getRelcode(), lan_id);

			Map p_card = new HashMap();

			p_card.put("prodOfferId", crm_offer_id);

			String union_org_code = (String) curSysMap.get("union_org_code");
			if (StringUtil.isEmpty(union_org_code))
				union_org_code = (String) curSysMap.get("org_code");
			p_card.put("descOrgCode", union_org_code);
			p_card.put("descStaffCode", currAdmin.getRelcode());

			logger.info("descOrgCode=" + union_org_code
					+ ":descStaffCode=" + currAdmin.getRelcode());

			p_card.put("lanId", lan_id);
			p_card.put("orgCode", operOrgMap.get("org_code"));
			p_card.put("operCode", ManagerUtils.getAdminUser().getRelcode());

			logger.info("orgCode=" + operOrgMap.get("org_code")
					+ ":operCode=" + ManagerUtils.getAdminUser().getRelcode());

			p_card.put("allocate_num", order.getGoods_num());
			p_card.put("state", "1");
			p_card.put("cardState", "1");
			p_card.put("descOrgTypeId", "8");

			List<Map> accNbrList = this.getAccNbrInfo(order.getOrder_id(),
					Consts.SHIP_STATE_1);
			StringBuffer accNbrInfo = new StringBuffer();
			for (Map cloudInfo : accNbrList) {
				accNbrInfo.append("	<acc_nbr_info>");
				accNbrInfo.append("		<acc_nbr>" + cloudInfo.get("phone_num")
						+ "</acc_nbr>");
				accNbrInfo.append("		<lan_id>" + cloudInfo.get("lan_id")
						+ "</lan_id>");
				accNbrInfo.append("	</acc_nbr_info>");
			}
			p_card.put("accNbrInfo", accNbrInfo.toString());

			p_card.put("orderId", order.getOrder_id());

			Map resultMap = null;

			String code = ManagerUtils.getResponseCode(resultMap);

			// 返回0，重置号码，重新根据号码段预占
			Map result = (Map) resultMap.get("Result");
			if (result != null) {
				String totalNumber = result.get("total_number").toString();
				if (totalNumber.equals("0")) {
					orderUtils.updateAccIntervalState(order.getOrder_id(),
							Consts.ITEM_CARD_ACC_COL1_STATE1);
					this.resetUnValidUsedCloudOrderId(order.getOrder_id());
					orderResult.setCode(Consts.CODE_FAIL);
					orderResult.setMessage("申请号段号码【" + begin_accnbr + "--"
							+ end_accnbr + "】之间没有可用的号码，请输入新的号码段");
					// throw new
					// RuntimeException("申请号段号码【"+begin_accnbr+"——"+end_accnbr+"】之间没有可用的号码，请输入新的号码段");
					// //处理失败抛出异常

				}
			}
			if (!Consts.CODE_SUCC.equals(code)) {
				orderResult.setCode(Consts.CODE_FAIL);
				orderResult.setMessage(ManagerUtils
						.getResponseMessage(resultMap));
				// throw new RuntimeException(); //处理失败抛出异常
			}

		}

	}

	// 获取需要调拨的号码段
	public ItemCard getItemCard(List<ItemCard> itemCards, String field_name) {
		List<ItemCard> beginItemCards = orderManager.getItemCardsByFieldName(
				field_name, itemCards);
		for (ItemCard itemCard : beginItemCards) {
			if (StringUtil.isEmpty(itemCard.getCol1()))
				return itemCard;
		}

		return null;
	}

	/**
	 * 获取云卡列表
	 * 
	 * @param cloudIds
	 *            多个id以逗号分隔
	 */
	@Override
	public List<Cloud> getCloudList(String cloudIds) {

		String sql = SF.orderSql("SERVICE_CLOUD_INFO_SELECT") + "and cloud_id in ('"
				+ cloudIds.replaceAll(",", "','") + "')";
		return this.baseDaoSupport.queryForList(sql, Cloud.class);
	}

	// 获取调拨卡的价格，价格做验证处理
	@Override
	public String getTransferPrice(String cloudIds) {
		String sql = SF.orderSql("SERVICE_TRANS_PRICE_SUM")+" and cloud_id in ('"
				+ cloudIds.replaceAll(",", "','") + "')";
		return this.baseDaoSupport.queryForString(sql);

	}

	// 判断调拨号码是否跨本地网
	@Override
	public List getLanCounts(String begin_accnbr, String end_accnbr, Cloud cloud) {
		String sql = SF.orderSql("SERVICE_DISTINCT_LAN_SELECT")+ getWhereCond(cloud);
		return this.baseDaoSupport.queryForList(sql);
	}

	// 获取调拨卡的价格，价格做验证处理
	public String getLanId(String begin_accnbr, String end_accnbr, Cloud cloud) {
		String sql = SF.orderSql("SERVICE_LAN_SELECT")
				+ getWhereCond(cloud) +" "+ DBTUtil.andRownum("2");
		return this.baseDaoSupport.queryForString(sql);
	}

	// 判断调拨号码金额是否不一致
	@Override
	public List getPayMoneyLevel(String begin_accnbr, String end_accnbr) {
		String sql = SF.orderSql("SERVICE_PAYMONEY_SELECT");
		return this.baseDaoSupport.queryForList(sql, begin_accnbr, end_accnbr);
	}

	public List<Map> getAccNbrInfo(String orderId, String shipState) {
		String sql = SF.orderSql("SERVICE_ACC_NBR_INFO_SELECT");
		logger.info("11");
		return this.baseDaoSupport.queryForList(sql, shipState, orderId);
	}

	@Override
	public Cloud getCloudByAccNbr(String phone_num) {
		String sql = SF.orderSql("SERVICE_CLOUD_SELECT_BY_ACCNBR");
		return (Cloud) this.baseDaoSupport.queryForObject(sql, Cloud.class,
				phone_num);

	}

	@Override
	public void updateCloud(Cloud cloud) {

		this.baseDaoSupport.update("es_cloud_info", cloud, " cloud_id = '"
				+ cloud.getCloud_id() + "'");
	}

	@Override
	public void updateCloudByOrderId(String orderId, String ship_state) {
		this.baseDaoSupport.execute(
				SF.orderSql("SERVICE_CLOUD_SHIP_STATE_UPDATE"),
				orderId, ship_state);
	}

	// 取消、撤单资源重置
	@Override
	public void resetCloudByOrderId(String orderId) {
		// this.baseDaoSupport.execute("update es_cloud_info set ship_state ='',order_id=first_orderid  where order_id = ? ",
		// orderId);

		Order order = orderManager.get(orderId);
		AdminUser adminUser = orderUtils.getAdminUserById(order.getUserid());
		if (Consts.CURR_FOUNDER3 == adminUser.getFounder()) // 一级分销商订单
			this.baseDaoSupport
					.execute(SF.orderSql("SERVICE_CLOUD_RESET_UPDATE"),orderId);
		else if (Consts.CURR_FOUNDER2 == adminUser.getFounder()) {
			this.baseDaoSupport.execute(SF.orderSql("SERVICE_SHIP_STATE_FINISH_UPDATE"),orderId);
		}

	}

	// 调拨成功后，将已预占、且没有处理成功的释放掉
	@Override
	public void resetUnUsedCloudOrderId(String orderId) {
		Order order = orderManager.get(orderId);
		AdminUser adminUser = orderUtils.getAdminUserById(order.getUserid());
		if (Consts.CURR_FOUNDER3 == adminUser.getFounder()) // 一级分销商订单
			this.baseDaoSupport
					.execute(
							SF.orderSql("SERVICE_CLOUD_RESET_BY_ORDER"),
							orderId, Consts.SHIP_STATE_2);
		else if (Consts.CURR_FOUNDER2 == adminUser.getFounder()) {
			this.baseDaoSupport
					.execute(
							SF.orderSql("SERVICE_CLOUD_RELEASE_UPDATE"),
							orderId, Consts.SHIP_STATE_00);
		}
	}

	// 对于号码段之间的号码没有一个可用的，直接设置为失效云卡,因为此号段在电信已经被使用掉
	@Override
	public void resetUnValidUsedCloudOrderId(String orderId) {
		Order order = orderManager.get(orderId);
		AdminUser adminUser = orderUtils.getAdminUserById(order.getUserid());
		if (Consts.CURR_FOUNDER3 == adminUser.getFounder()) // 一级分销商订单
			this.baseDaoSupport
					.execute(
							SF.orderSql("SERVICE_COLUD_DISABLED_UPDATE"),
							orderId, Consts.SHIP_STATE_2);
		else if (Consts.CURR_FOUNDER2 == adminUser.getFounder()) {
			this.baseDaoSupport
					.execute(
							SF.orderSql("SERVICE_CLOUD_DELI_UPDATE"),
							orderId, Consts.SHIP_STATE_00);
		}
	}

	@Override
	public void updateCloudByOrderId(String orderId) {
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_CLOUD_DELI_UPDATE_BY_ORDER"), orderId);
	}

	@Override
	public Integer getCloudCountByOrderId(String orderId, String ship_state) {
		Integer countInt = 0;
		String countStr = this.baseDaoSupport
				.queryForString(SF.orderSql("SERVICE_CLOUD_COUNT")+" and order_id =  '"
						+ orderId + "' and ship_state ='" + ship_state + "'");
		if (!StringUtil.isEmpty(countStr))
			countInt = new Integer(countStr);
		return countInt;
	}

	@Override
	public Map listc(Cloud cloud) {
		Map countMap = new HashMap();
		cloud.setState(Consts.ACC_NBR_STATE_0);

		String countSql = SF.orderSql("SERVICE_CLOUD_COUNT")
				+ getWhereCond(cloud).toString();
		String ableCar_count = this.baseDaoSupport.queryForString(countSql);
		if (StringUtil.isEmpty(ableCar_count))
			ableCar_count = "0";
		countMap.put("ableCloud_count", ableCar_count);
		cloud.setState("");
		countSql = SF.orderSql("SERVICE_CLOUD_COUNT")
				+ getWhereCond(cloud).toString();

		String car_count = this.baseDaoSupport.queryForString(countSql);
		if (StringUtil.isEmpty(car_count))
			car_count = "0";
		countMap.put("cloud_count", car_count);
		return countMap;
	}

	public String getWhereCond(Object... args) {

		String userId = ManagerUtils.getUserId();
		Cloud cloud = null;
		Integer pay_money = null;

		if (args[0] != null && args[0] instanceof Cloud) {
			cloud = (Cloud) args[0];
		} else if (args[1] != null && args[1] instanceof Cloud) {
			cloud = (Cloud) args[1];
		}

		String phoneNum = "";
		String state = "";
		String offer_id = "";
		String batch_id = "";
		String p_apply_show_parent_stock = "";
		String offer_name = "";
		Double payMoney = null;
		if (cloud != null) {
			phoneNum = cloud.getPhone_num();
			state = cloud.getState();
			batch_id = cloud.getBatch_id();
			offer_id = cloud.getOffer_id();
			offer_name = cloud.getOffer_name();
			payMoney = cloud.getPay_money();

			if (!StringUtil.isEmpty(cloud.getGoods_id())) {
				Goods goods = goodsCacheUtil.getGoodsById(cloud.getGoods_id());
				offer_id = goods.getCrm_offer_id();
				pay_money = ManagerUtils.getIntegerVal(goods.getMktprice());
			} else {
				if (cloud.getPay_money() != null) {
					pay_money = ManagerUtils
							.getIntegerVal(cloud.getPay_money());
				}
			}
			p_apply_show_parent_stock = cloud.getP_apply_show_parent_stock();
		}
		StringBuffer sql = new StringBuffer();

		// 查询电信库存
		if ((ManagerUtils.isNetStaff())
				|| (Consts.APPLY_SHOW_PARENT_STOCK
						.equals(p_apply_show_parent_stock) && ManagerUtils
						.isFirstPartner())) {// 一级查询电信的库存
			sql.append(" and to_userid is null and ship_state is null  and state='"
					+ Consts.CARD_INFO_STATE_0 + "' ");
		} else {
			// 查询一级、二级库存
			if (((ManagerUtils.isFirstPartner() || ManagerUtils
					.isSecondPartner()))) {

				// 二级查看一级分销商可用库存
				if ((ManagerUtils.isSecondPartner() && Consts.APPLY_SHOW_PARENT_STOCK
						.equals(p_apply_show_parent_stock))) {
					sql.append(" and to_userid = '"
							+ ManagerUtils.getAdminUser().getParuserid()
							+ "' and ship_state = '" + Consts.SHIP_STATE_5
							+ "' ");
				} else {
					// 查看自己库存
					sql.append(" and to_userid = '" + userId
							+ "' and ship_state = '" + Consts.SHIP_STATE_5
							+ "' ");
				}
			}
		}

		if (phoneNum != null && !"".equals(phoneNum)) {
			sql.append(" and phone_num like '%" + phoneNum + "%'");
		}
		if (!StringUtil.isEmpty(state)) {
			sql.append(" and state = '" + state + "'");
		}

		if (batch_id != null && !"".equals(batch_id)) {
			sql.append(" and batch_id = '" + batch_id + "'");
		}

		// 调拨等额面值的金额卡
		if (pay_money != null) {
			sql.append(" and pay_money = '" + pay_money + "'");
		}

		if (payMoney != null) {
			sql.append(" and pay_money = '" + payMoney + "'");
		}

		// 调拨等额面值的金额卡
		if (!StringUtil.isEmpty(offer_id)) {
			sql.append(" and offer_id = '" + offer_id + "'");
		}

		// 可用资源处理
		if (cloud != null) {
			// 号码类型
			if (!StringUtil.isEmpty(cloud.getAcc_type())
					&& !"0".equals(cloud.getAcc_type())) {
				sql.append(" and phone_num  like  '" + cloud.getAcc_type()
						+ "%'");
			}
			// 本地网
			if (!StringUtil.isEmpty(cloud.getLan_id())
					&& !Consts.LAN_PROV.equals(cloud.getLan_id())) {
				sql.append(" and lan_id = '" + cloud.getLan_id() + "'");
			}

			if (!StringUtil.isEmpty(cloud.getBegin_nbr())) {
				sql.append(" and phone_num >= '" + cloud.getBegin_nbr() + "'");
			}

			if (!StringUtil.isEmpty(cloud.getEnd_nbr())) {
				sql.append(" and phone_num <= '" + cloud.getEnd_nbr() + "'");
			}

			if (!StringUtils.isEmpty(offer_name)) {
				sql.append(" and offer_name like '%" + offer_name + "%'");
			}
		}

		return sql.toString();

	}

	@Override
	public Page transfer_list_query(int pageNO, int pageSize, Object... args) {

		StringBuffer sql = new StringBuffer();
		String sqlCol = SF.orderSql("SERVICE_CLOUD_SELECT")
				+ getWhereCond(args).toString();

		String countSql = SF.orderSql("SERVICE_CLOUD_COUNT")
				+ getWhereCond(args).toString();

		Page page = this.baseDaoSupport.queryForCPage(sqlCol + sql.toString()
				+ " order by phone_num asc ", pageNO, pageSize, Cloud.class,
				countSql + sql.toString());
		return page;
	}

	@Override
	public Page transfer_list(int pageNO, int pageSize, Object... args) {

		StringBuffer sql = new StringBuffer();
		String sqlCol = SF.orderSql("SERVICE_CLOUD_SELECT")
				+ getWhereCond(args).toString();

		String countSql = SF.orderSql("SERVICE_CLOUD_COUNT")
				+ getWhereCond(args).toString();

		Page page = this.baseDaoSupport.queryForCPage(sqlCol + sql.toString()
				+ " order by deal_time desc ", pageNO, pageSize, Cloud.class,
				countSql + sql.toString());
		return page;
	}

	@Override
	public List list(String orderId) {
		return this.baseDaoSupport
				.queryForList(
						SF.orderSql("SERVICE_CLOUD_SELECT_BY_SHIP_STATE"),
						orderId, Consts.SHIP_STATE_11, Consts.SHIP_STATE_22,
						Consts.SHIP_STATE_5, Consts.SHIP_STATE_2);

	}

	public IAccNbrManager getAccNbrManager() {
		return accNbrManager;
	}

	public void setAccNbrManager(IAccNbrManager accNbrManager) {
		this.accNbrManager = accNbrManager;
	}


	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	/**
	 * 导入云卡
	 * 
	 * @param inList
	 * @return
	 */
	@Override
	public BatchResult importCloud(List inList) {
		BatchResult batchResult = new BatchResult();

		String batchId = this.baseDaoSupport.getSequences("s_es_cust_returned");
		int sucNum = 0; // 成功返档条数
		int failNum = 0; // 返档失败条数
		String err_msg = "";
		List<Object[]> list = new ArrayList<Object[]>();

		if (inList != null && !inList.isEmpty()) {
			for (int i = 0; i < inList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) inList.get(i);
				String cloud_id = baseDaoSupport.getSequences("S_ES_CLOUD_INFO");
				Object[] obj = new Object[] { cloud_id,map.get("offer_name"),
						map.get("offer_name"), map.get("phone_num"),
						map.get("pay_money"), map.get("uim"),
						map.get("lan_id"), map.get("offer_id"), new Date(),
						"0", batchId, ManagerUtils.getUserId() };
				list.add(obj);
			}
		}

		try {
			insert(list);
			sucNum = inList.size();
			failNum = 0;
		} catch (Exception e) {
			err_msg = e.getMessage();
			failNum = inList.size();
			sucNum = 0;
			e.printStackTrace();
		}

		batchResult.setErr_msg(err_msg);
		batchResult.setBatchId(batchId);
		batchResult.setSucNum(sucNum);
		batchResult.setFailNum(failNum);
		return batchResult;
	}

	@Override
	public Map<String, Object> importCloudFromCRM(String refCode, String orgId, String orgName, 
			String orderId, List<Map<String, Object>> cloudList) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isEmpty(orderId) || StringUtil.isEmpty(orgId) || StringUtil.isEmpty(orgName)) {
			result.put("result", "ERROR");
			result.put("message", "orderId/orgId/orgName为空：{orderId: " + orderId + " ,orgId：" + orgId + ",orgName:" + orgName + "}");
			return result;
		}
		
		AdminUser importUser = null;
		if (!StringUtil.isEmpty(refCode)) {  					//refCode不为空的时候查询es_adminuser表关联的用户工号
			AdminUserReq adminUserReq = new AdminUserReq();
			/*adminUserReq.setRefCode(refCode);
			importUser = adminUserServ.getAdminuserByRole(adminUserReq);*/
//			ManagerUtils.createSession(importUser);
			if (null == importUser) {
				result.put("result", "ERROR");
				result.put("message", "关联工号：" + refCode + "不存在");
				return result;
			}
		} else {
			logger.info("订单" + orderId + "传入的refCode为空"); //refCode 允许为空。 ship_state = 0, importUserId fromUserid 为空;
		}
		
		Order order = orderManager.get(orderId);
		if (null == order || order.getOrder_id() == null) {
			result.put("result", "ERROR");
			result.put("message", "订单单号：" + orderId + "不存在");
			return result;
		}
		if (OrderStatus.ORDER_PAY != order.getStatus()) {
			result.put("result", "ERROR");
			result.put("message", "订单单号：" + orderId + "状态必须是已支付、待受理：【 "
					+ OrderStatus.ORDER_PAY + " 】");
			return result;
		}

		logger.info("==== 电信调拨给一级分销商begin ==== 关联工号: " + refCode + " 订单号: "
				+ orderId);

		int goodsNum = order.getGoods_num(); // 订购数量
		int shipsCount_2 = getCloudCountByOrderId(orderId, Consts.SHIP_STATE_2); // 已经调拨的数量。
																					// 正常的话
																					// 应该是0
		if (shipsCount_2 == goodsNum) { // 正常来讲 不会出现这种情况
			orderNFlowManager.accept(orderId); // 调拨完成
			// 已经全部调拨
			result.put("result", "ERROR");
			result.put("message", "订单单号：" + orderId + "已经全部调拨完成。不要重复调拨");
			return result;
		}
		if (cloudList == null || cloudList.isEmpty()) {
			result.put("result", "ERROR");
			result.put("message", "调拨云卡列表为空");
			return result;
		}

		int needCount = goodsNum - shipsCount_2; // 还需要调拨的数量
		int cloudSize = cloudList.size(); // 准备导入调拨卡的数量
		int importCount = cloudSize; // 实际导入卡的数量
		boolean isAcceptIng = true;

		if (cloudSize >= needCount) {
			importCount = needCount;
			isAcceptIng = false;
		}

		AdminUser fAdminUser = orderUtils.getAdminUserById(order.getUserid());
		Lan lan = this.lanServ.getLanByID(order.getLan_id());

		List<Object[]> list = new ArrayList<Object[]>();
		List<Map<String, Object>> cloudListCopy = new ArrayList<Map<String, Object>>(
				cloudList);// 记录没有被使用的号码
		
		String batchId = this.baseDaoSupport.getSequences("s_es_cust_returned");
		String importUserId = importUser == null ? "" : importUser.getUserid();
		String shipState = importUser == null ? Consts.SHIP_STATE_0 : Consts.SHIP_STATE_2;
		
		for (int i = 0; i < importCount; i++) {
			Date date = new Date();
			Map<String, Object> map = cloudList.get(i);
			String cloud_id = baseDaoSupport.getSequences("S_ES_CLOUD_INFO");
			Object[] obj = new Object[] { cloud_id,map.get("offer_name"),
					map.get("offer_name"), map.get("phone_num"),
					map.get("pay_money"), map.get("uim"), map.get("lan_id"),
					map.get("offer_id"), date, date, Consts.CLOUD_INFO_STATE_0,
					shipState, batchId, importUserId,
					order.getUserid(), lan.getLan_name(),
					fAdminUser.getUsername(), importUserId,
					order.getUserid(), orderId, orderId, orgId, orgName, Consts.CLOUD_FROM_SRC_1}; // 直接设置为调拨成功状态Consts.SHIP_STATE_2
			list.add(obj);
			cloudListCopy.remove(0); // remove 掉被使用的号码
		}

		String insertSql = SF.orderSql("SERVICE_CLOUD_IMPORT_INSERT");

		baseDaoSupport.batchExecute(insertSql, list);

		if (isAcceptIng || importUser == null) {
			
			orderNFlowManager.accept_ing(orderId); // 调拨数量不够 || refCode为空。
		} else {
			orderNFlowManager.accept(orderId); // 调拨完成
		}
		result.put("result", "SUCCESS");
		result.put("message", " 传入卡数量：" + cloudSize + " 实际调拨数量：" + importCount
				+ " 未使用的卡数量：" + cloudListCopy.size());
		result.put("return_list", cloudListCopy); // 可能CRM系统要使用到

		logger.info("==== 电信调拨给一级分销商end ==== " + result);
		return result;
	}

	public void insert(List<Object[]> list) {
		this.baseDaoSupport
				.batchExecute(
						SF.orderSql("SERVICE_CLOUD_INSERT"),
						list);
	}

	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}

	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
	}

	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}

	@Override
	public int isExistsCloud(List list) {

		int count = 0;

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) list.get(i);

				StringBuffer sql = new StringBuffer();
				sql.append(SF.orderSql("SERVICE_CLOUD_COUNT"));
				sql.append(" and (phone_num = '" + map.get("phone_num") + "'");
				sql.append(" or uim = '" + map.get("uim") + "')");

				String item = this.baseDaoSupport
						.queryForString(sql.toString());

				if (!StringUtil.isEmpty(item)) {
					count = count + Integer.valueOf(item);
				}
			}
		}
		return count;
	}

	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}

}
