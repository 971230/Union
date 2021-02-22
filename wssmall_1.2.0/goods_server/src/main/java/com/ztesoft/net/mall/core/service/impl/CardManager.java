package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.service.ICardManager;


/**
 * 充值卡处理类
 * 
 * @author wui
 */

public class CardManager extends BaseSupport implements ICardManager {


//	ICacheUtil cacheUtil;
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public void transfer_card(OrderRequst orderRequst, OrderResult orderResult) {
//		// CardRequest cardRequest = orderRequst.getCardRequest();
//		// TODO 调充值卡调拨处理方法,调拨成功后将order_id更新为当前订单id
//
//		String to_userid = orderResult.getOrder().getUserid();
//		String from_userid = ManagerUtils.getUserId();
//		Order order = orderResult.getOrder();
//
//		Integer goods_num = order.getGoods_num();
//
//		// add by wui 根据金额数量调拨
//
//		// 获取卡面额
//		com.ztesoft.net.mall.core.model.Goods goods = cacheUtil.getGoodsById(order
//				.getGoods_id());
//		Double makDouble = goods.getMktprice();
//		Integer price_int = ManagerUtils.getIntegerVal(makDouble);
//
//		// add by wui事物管控锁表处理
//		this.baseDaoSupport.execute(SF.goodsSql("DC_PUBLIC_FOR_UPDATE"), "2");
//
//		// 获取可用的卡数量
//		Card card = new Card();
//		card.setState(Consts.CARD_INFO_STATE_0);
//		card.setGoods_id(order.getGoods_id());
//		card.setType_code(order.getType_code());
//		Map cardMap = listc(card);
//		String ableCar_count = (String) cardMap.get("ableCar_count");
//		if (StringUtil.isEmpty(ableCar_count))
//			ableCar_count = "0";
//		Integer canUsedCountInt = new Integer(ableCar_count).intValue();
//		if (canUsedCountInt < goods_num) {
//			throw new RuntimeException("用户购买卡数量" + goods_num + "（个），超出可用库存数"
//					+ canUsedCountInt + "个，库存不足，请先进货！");
//		}
//
//		String appendSql = "";
//		String whereCond = "";
//		if (ManagerUtils.isNetStaff()) { // 电信调拨给一级分销商
//			appendSql = "  first_userid = '"
//					+ orderResult.getOrder().getUserid()
//					+ "' , first_orderid ='" + order.getOrder_id() + "' ,";
//			whereCond = "  and to_userid is null  and ship_state is null  ";
//		} else if (ManagerUtils.isFirstPartner()) {// 一级分销商调拨给二级分销商
//			AdminUser adminUser = ManagerUtils.getAdminUser();
//			whereCond = " and ship_state ='" + Consts.SHIP_STATE_5
//					+ "' and to_userid ='" + adminUser.getUserid() + "' "; // 查询上级已调拨库存
//		}
//		// 分配库存 marked
//		String updateSql = "update es_card_info set order_id = ?,goods_id = ? , from_userid = ?, to_userid = ?, "
//				+ appendSql
//				+ " ship_state = ? ,deal_time="
//				+ DBTUtil.current()
//				+ "  where card_price = ?  and type_code = ?  and state ='"
//				+ Consts.CARD_INFO_STATE_0
//				+ "' "
//				+ whereCond
//				+ " and  rownum<=?";
//		this.baseDaoSupport
//				.execute(updateSql, order.getOrder_id(), order.getGoods_id(),
//						from_userid, to_userid, Consts.SHIP_STATE_4, price_int,
//						order.getType_code(), goods_num);
//
//	}
//
//	public void updateCardByOrderId(String orderId) {
//		this.baseDaoSupport.execute(SF.goodsSql("CARD_INFO_UPDATE"), orderId);
//	}
//
//	/**
//	 * 查询充值卡列表
//	 * 
//	 * @param rateIds
//	 *            , 多个id以逗号分隔
//	 */
//	public List<Card> getCardList(String rateIds) {
//		String sql = SF.goodsSql("GOODS_GET_CARD_LIST") + " and a.card_id in (" + rateIds.replaceAll(",", "','") +")";
//		return this.baseDaoSupport.queryForList(sql, Card.class);
//	}
//
//	// 获取调拨卡的价格，价格做验证处理
//	public String getTransferPrice(String cardIds) {
//		String sql = SF.goodsSql("GET_TRANSFER_PRICE") + " and a.card_id in ( " + cardIds.replaceAll(",", "','") + " )";
//
//		return this.baseDaoSupport.queryForString(sql);
//	}
//
//	public String getLanIdByAccNbr(String acc_nbr) {
//		try {
//			String sql = SF.goodsSql("GET_LANID_BY_ACCNBR");
//			return (String) this.baseDaoSupport.queryForString(sql, acc_nbr);
//		} catch (Exception e) {
//
//		}
//		return "";
//	}
//
//	public void resetCardByOrderId(String orderId) {
//		String sql = SF.goodsSql("RESET_CARD_BY_ORDERID");
//		this.baseDaoSupport.execute(sql, orderId);
//	}
//
//	public void resetFailCardByOrderId() {
//		// this.baseDaoSupport.execute("update es_card_info set ship_state ='',order_id=first_orderid  where order_id = ? ",
//		// orderId);
//	}
//
//	@SuppressWarnings("unchecked")
//	public Map listc(Card card) {
//
//		Map countMap = new HashMap();
//		card.setState(Consts.ACC_NBR_STATE_0);
//		String countSql = "select count(*) from es_card_info where 1 = 1 "
//				+ getWhereCond(card).toString();
//		String ableCar_count = this.baseDaoSupport.queryForString(countSql);
//
//		if (StringUtil.isEmpty(ableCar_count))
//			ableCar_count = "0";
//
//		countMap.put("ableCar_count", ableCar_count);
//		card.setState("");
//		countSql = "select count(*) from es_card_info where 1 = 1 "
//				+ getWhereCond(card).toString();
//		String car_count = this.baseDaoSupport.queryForString(countSql);
//		if (StringUtil.isEmpty(car_count))
//			car_count = "0";
//		countMap.put("car_count", car_count);
//		return countMap;
//	}
//
//	@SuppressWarnings("unchecked")
//	public Map listAvialableC(Card card) {
//		Map countMap = new HashMap();
//		StringBuffer countSql = new StringBuffer();
//
//		String userId = ManagerUtils.getUserId();
//		String type_code = "";
//		String area_state = Consts.GOODS_USAGE_STATE_1;
//		Integer goods_state = Consts.GOODS_DISABLED_0;
//
//		if (null != card) {
//			type_code = card.getType_code();
//		}
//
//		countSql.append("select count(1)");
//		countSql.append(" from es_card_info c");
//		countSql.append(" where 1 = 1");
//
//		String whereSql = "";
//
//		if (ManagerUtils.isNetStaff()) {
//			whereSql = getWhereCond(card);
//		} else {
//			whereSql = getAvialableWhere(card);
//		}
//
//		String car_count = this.baseDaoSupport.queryForString(countSql
//				.toString() + whereSql.toString());
//
//		if (StringUtil.isEmpty(car_count)) {
//			car_count = "0";
//		}
//		countMap.put("car_count", car_count);
//		return countMap;
//	}
//
//	public String getWhereCond(Object... args) {
//		// marked
//
//		String userId = ManagerUtils.getUserId();
//		Card card = null;
//
//		if (args[0] != null && args[0] instanceof Card) {
//			card = (Card) args[0];
//		} else if (args[1] != null && args[1] instanceof Card) {
//			card = (Card) args[1];
//		}
//
//		String cardCode = "";
//		String state = "";
//		Integer card_price = 0;
//		String type_code = "";
//		String batch_id = "";
//		String goods_id = "";
//		String search_price = "";
//		String p_apply_show_parent_stock = "";
//
//		if (null != card) {
//			cardCode = card.getCard_code();
//			state = card.getState();
//			type_code = card.getType_code();
//			goods_id = card.getGoods_id();
//			batch_id = card.getBatch_id();
//			p_apply_show_parent_stock = card.getP_apply_show_parent_stock();
//			search_price = card.getCard_price();
//			if (!StringUtil.isEmpty(goods_id)) {
//				// 根据金额获取商品对应的面值
//				com.ztesoft.net.mall.core.model.Goods goods = cacheUtil
//						.getGoodsById(goods_id);
//				Double makDouble = goods.getMktprice();
//				card_price = ManagerUtils.getIntegerVal(makDouble);
//				GoodsType goodsType = cacheUtil.getGoodsTypeById(goods
//						.getType_id());
//				type_code = goodsType.getType_code();
//			}
//		}
//
//		StringBuffer sql = new StringBuffer();
//		if (Consts.APPLY_SHOW_PARENT_STOCK.equals(p_apply_show_parent_stock)) {
//			if (ManagerUtils.isNetStaff() || ManagerUtils.isFirstPartner()) {
//
//				sql.append(" and to_userid is null and ship_state is null  and state='"
//						+ Consts.CARD_INFO_STATE_0 + "' ");
//
//			} else if (((ManagerUtils.isFirstPartner() || ManagerUtils
//					.isSecondPartner()))) {
//
//				// 二级查看一级分销商可用库存
//				if (ManagerUtils.isSecondPartner()) {
//					sql.append(" and to_userid = '"
//							+ ManagerUtils.getAdminUser().getParuserid()
//							+ "' and ship_state = '" + Consts.SHIP_STATE_5
//							+ "' ");
//				} else {
//					// 查看自己库存
//					sql.append(" and to_userid = '" + userId
//							+ "' and ship_state = '" + Consts.SHIP_STATE_5
//							+ "' ");
//				}
//
//			}
//		}
//
//		// 如果申请查看可用的库存，则需要状态为可用
//		if (Consts.APPLY_SHOW_PARENT_STOCK.equals(p_apply_show_parent_stock)) {
//			sql.append(" and state = '" + Consts.CARD_INFO_STATE_0 + "'");
//		}
//
//		if (cardCode != null && !"".equals(cardCode)) {
//			sql.append(" and card_code like '%" + cardCode + "%'");
//		}
//		if (null != state && !"".equals(state)) {
//			sql.append(" and state = '" + state + "'");
//		}
//		if (!StringUtil.isEmpty(type_code)) {
//			sql.append(" and type_code = '" + type_code + "'");
//		}
//		// 价格
//		if (card_price != 0) {
//			sql.append(" and card_price = '" + card_price + "'");
//		}
//
//		if (goods_id != null && !"".equals(goods_id)) {
//			sql.append(" and goods_id = '" + goods_id + "'");
//		}
//
//		// 导入批次号
//		if (batch_id != null && !"".equals(batch_id)) {
//			sql.append(" and batch_id like '%" + batch_id + "%'");
//		}
//		if (search_price != null && !"".equals(search_price)) {
//			sql.append(" and card_price = '" + search_price + "' ");
//		}
//
//		sql.append(" and eff_date<" + DBTUtil.current() + " and exp_date>"
//				+ DBTUtil.current()); // 查询有效的库存
//
//		sql.append(" order by deal_time desc");
//
//		return sql.toString();
//
//	}
//
//	public Page transfer_list(int pageNO, int pageSize, Object... args) {
//		String sqlCol = SF.goodsSql("GET_TRANSFER_LIST")
//				+ getWhereCond(args).toString();
//		String countSql = "select count(*) from es_card_info where 1 = 1 "
//				+ getWhereCond(args).toString();
//
//		Page page = this.baseDaoSupport.queryForCPage(sqlCol, pageNO, pageSize,
//				Card.class, countSql);
//		return page;
//	}
//
//	public Page transfer_allList(int pageNO, int pageSize, Object... args) {
//		String whereSql = "";
//		if (ManagerUtils.isNetStaff()) {
//			whereSql = getWhereCond(args);
//		} else {
//			whereSql = getAvialableWhere(args);
//		}
//		String sqlCol = SF.goodsSql("TRANSFER_ALL_LIST") + whereSql.toString();
//		String countSql = "select count(*) from es_card_info c where 1 = 1 "
//				+ whereSql.toString();
//
//		Page page = this.baseDaoSupport.queryForCPage(sqlCol, pageNO, pageSize,
//				Card.class, countSql);
//
//		return page;
//	}
//
//	public String getAvialableWhere(Object... args) {
//
//		String userId = "";
//		Card card = null;
//
//		if (args[0] != null && args[0] instanceof Card) {
//			card = (Card) args[0];
//		} else if (args[1] != null && args[1] instanceof Card) {
//			card = (Card) args[1];
//		}
//
//		String type_code = "";
//		String area_state = Consts.GOODS_USAGE_STATE_1;
//		Integer goods_state = Consts.GOODS_DISABLED_0;
//
//		if (null != card) {
//			type_code = card.getType_code();
//		}
//
//		StringBuffer sql = new StringBuffer();
//
//		if (ManagerUtils.isFirstPartner()) {
//
//			sql.append(" and c.to_userid is null and c.ship_state is null  and c.state='"
//					+ goods_state + "' ");
//
//			userId = ManagerUtils.getUserId();
//		} else if (ManagerUtils.isSecondPartner()) {
//
//			sql.append(" and c.to_userid = '"
//					+ ManagerUtils.getAdminUser().getParuserid()
//					+ "' and c.ship_state = '" + Consts.SHIP_STATE_5 + "' ");
//
//			userId = ManagerUtils.getAdminUser().getParuserid();
//		}
//		sql.append(" and c.type_code = '" + type_code + "'");
//		sql.append(" and c.state = '" + Consts.CARD_INFO_STATE_0 + "'");
//		sql.append(" and exists (select 1");
//		sql.append(" from es_goods_usage a, es_goods g,es_goods_type ty,es_goods_area eg");
//		sql.append(" where a.userid = '" + userId + "'");
//		sql.append(" and a.goods_id = g.goods_id");
//		sql.append(" and eg.goods_id = a.goods_id");
//		sql.append(" and eg.state  = '" + area_state + "'");
//		sql.append(" and a.state = '" + goods_state + "'");
//		sql.append(" and g.type_id = ty.type_id");
//		sql.append(" and ty.type_code = '" + type_code + "'");
//		sql.append(" and g.mktprice = c.card_price)");
//
//		sql.append(" order by deal_time desc");
//
//		return sql.toString();
//
//	}
//
//	// 根据用户id,金额获取等额的充值卡,单独给定行锁定
//	@SuppressWarnings("unchecked")
//	@Transactional(propagation = Propagation.REQUIRED)
//	public Card getCardByUserIdAndMoney(String userid, String money,
//			String type_code, String goods_id) {
//
//		Card card = null;
//
//		// 事物控制，将方法锁定
//		this.baseDaoSupport.execute(SF.goodsSql("DC_PUBLIC_8002"), "00A");
//
//		String sql = SF.goodsSql("GET_CARD_BY_USERID_AND_MONEY");
//		List lists = this.baseDaoSupport.queryForList(sql, Card.class,
//				goods_id, userid, money, type_code);
//
//		if (lists != null && lists.size() > 0) {
//			card = (Card) lists.get(0);
//			// 将卡号预占用
//			card.setState(Consts.CARD_INFO_STATE_1);
//			this.updateCard(card);
//		}
//		return card;
//
//	}
//
//	// 根据用户id,金额获取等额的充值卡,没有事物控制
//	@SuppressWarnings("unchecked")
//	public Card getCardNoTnsByUserIdAndMoney(String userid, String money,
//			String type_code) {
//		Card card = null;
//		synchronized (this) {
//
//			String sql = SF.goodsSql("GET_CARDNOTNS_BY_USERID_AND_MONEY");
//			List lists = this.baseDaoSupport.queryForList(sql, Card.class,
//					userid, money, type_code);
//
//			// 将卡号预占用
//			if (lists != null && lists.size() > 0) {
//				card = (Card) lists.get(0);
//				card.setState(Consts.CARD_INFO_STATE_1);
//				this.updateCard(card);
//			}
//
//			return card;
//		}
//
//	}
//
//	// 获取商品金额信息
//	public String getGoodsIdByCardId(String cardid) {
//
//		String sql = SF.goodsSql("GET_GOODSID_BY_CARDID");
//		return (String) this.baseDaoSupport.queryForString(sql, cardid);
//	}
//
//	// 获取卡信息
//	public Card getCardByOrderId(String order_id) {
//		String sql = SF.goodsSql("GET_CARD_BY_ORDERID");
//		return (Card) this.baseDaoSupport.queryForObject(sql, Card.class,
//				order_id);
//	}
//
//	// 充值成功后更新订单id
//	public void updateCard(Card card) {
//		this.baseDaoSupport.update("es_card_info", card,
//				" card_id = '" + card.getCard_id() + "'");
//	}
//
//	// 调用UVC接口充值
//	@SuppressWarnings("unchecked")
//	public Map uvcRecharge(Card card, CardInfRequest cardInfRequest) {
//		Map retMap = new HashMap();
//		Map params = new HashMap();
//		params.put("operationCode", "0"); // 请求类型 0－普通充值；1－批量充值；2－卡分拆充值
//		params.put("accessType", Consts.CARD_ACCESS_TYPE); // 充值接入途径
//		params.put("cardPin", card.getCard_password());
//		params.put("requestAmount", "1"); // 充值请求记录数
//		params.put("sequence", "1"); // 分拆序号 从 1 开始
//		params.put("balanceType", "1"); // 余额类型 默认 1
//		params.put("destinationId", cardInfRequest.getAccNbr()); //
//		params.put("destinationAttr", "2"); // 被充值号码属性 2
//		params.put("destinationAttrDetail", "999"); //
//		params.put("objType", "3"); // 号码类型，1：帐户；2：客户；3：用户；99：其他
//		params.put("rechargeAmount", "0"); // 充值金额数量（卡分拆充值时使用，其他情况填缺省值0）
//		params.put("rechargeUnit", "0"); // 充值金额单位：0－分；1－条；
//		// params.put("subId", "20130426000000000099");
//		// //子流水号（分拆/分批充值时此项必填YYYYMMDD+8位循环数字+2位各省自用+99(固定值)
//		params.put("requestId", "20130426120355000000000014"); // 请求流水
//		params.put("requestSource", "192.168.10.201"); // 自服务平台标识
//		params.put("requestTime", "20130426120355"); // YYYYMMDDHHMMSS 请求时间
//		params.put("requestUser", "sample"); // 登陆的填写登陆用户未登录填写用户ip
//		Map temp = null;
//		Map rechargeReturn = (Map) temp.get("rechargeReturn");
//		Map rechargeInfoList = (Map) rechargeReturn.get("rechargeInfoList");
//		Map item = (Map) rechargeInfoList.get("item");
//		String code = item.get("result").toString();
//		if (code.equals("0")) {
//			retMap.put("code", "0000");
//			retMap.put("massage", "充值成功");
//		} else {
//			retMap.put("code", "-1");
//			retMap.put("massage", "充值失败,错误编码：" + code);
//		}
//		return retMap;
//
//	}
//
//	/**
//	 * 插入导入日志
//	 */
//
//	public CardLog insertCardLog(CardLog cardLog) {
//		cardLog.setLog_id(System.currentTimeMillis() + "");
//		cardLog.setStartdate(DateFormatUtils.formatToDate(cardLog
//				.getStartdate()));
//		cardLog.setStopdate((DateFormatUtils.formatToDate(cardLog.getStopdate())));
//		cardLog.setReservestopdate(DateFormatUtils.formatToDate(cardLog
//				.getReservestopdate()));
//
//		this.baseDaoSupport.insert("cloud_info_log", cardLog);
//
//		return cardLog;
//	}
//
//	@Override
//	public List list(String orderId) {
//		String sql = SF.goodsSql("GOODS_GET_CARD_INFO");
//		return this.baseDaoSupport.queryForList(sql, orderId);
//	}
//
//	// 充值卡退费处理
//	@Override
//	public String returnedCards(String sec_order_id) {
//		String sql = SF.goodsSql("RETURNED_CARDS");
//		this.baseDaoSupport.execute(sql, sec_order_id);
//		return null;
//	}
//
//	// 检查申请退货的卡是否 可以退货
//	@Override
//	public List<Card> checkUnavailableCard(String cardIds) {
//		if (StringUtil.isEmpty(cardIds)) {
//			return Collections.emptyList();
//		}
//		if (cardIds.endsWith(","))
//			cardIds = cardIds.substring(0, cardIds.length() - 1);
//		cardIds = cardIds.replaceAll(",", "','");
//
//		String sql = SF.goodsSql("CHECK_UNAVAILABLE_CARD") + " and a.card_id in (" + cardIds + ")";
//		return baseDaoSupport.queryForList(sql, Card.class,
//				Consts.CARD_INFO_STATE_0);
//	}
//
//	// 充值卡退费申请处理,更新预占的号码
//	@Override
//	public String occupyCards(String cardIds, String sec_order_id) {
//		String sql = SF.goodsSql("OCCUPY_CARDS") + " and card_id  in(" + cardIds.replaceAll(",", "','") + ")";
//		this.baseDaoSupport.execute(sql, sec_order_id);
//		return null;
//	}
//
//	public ICacheUtil getCacheUtil() {
//		return cacheUtil;
//	}
//
//	public void setCacheUtil(ICacheUtil cacheUtil) {
//		this.cacheUtil = cacheUtil;
//	}
//
//	/**
//	 * 导入流量卡充值卡
//	 * 
//	 * @param inList
//	 * @return
//	 */
//	public BatchResult importCard(List inList, String cardType) {
//		BatchResult batchResult = new BatchResult();
//
//		String batchId = this.baseDaoSupport.getSequences("s_es_cust_returned");
//		int sucNum = 0; // 成功返档条数
//		int failNum = 0; // 返档失败条数
//		String err_msg = "";
//		List<Object[]> list = new ArrayList<Object[]>();
//
//		if (inList != null && !inList.isEmpty()) {
//			for (int i = 0; i < inList.size(); i++) {
//				Map<String, Object> map = (Map<String, Object>) inList.get(i);
//
//				Object[] obj = new Object[] { map.get("card_code"),
//						map.get("card_password"), map.get("card_price"),
//						new Date(), "0", cardType, batchId, "1",
//						ManagerUtils.getUserId() };
//				list.add(obj);
//			}
//		}
//
//		try {
//			insert(list);
//			sucNum = inList.size();
//			failNum = 0;
//		} catch (Exception e) {
//			err_msg = e.getMessage();
//			failNum = inList.size();
//			sucNum = 0;
//			e.printStackTrace();
//		}
//
//		batchResult.setErr_msg(err_msg);
//		batchResult.setBatchId(batchId);
//		batchResult.setSucNum(sucNum);
//		batchResult.setFailNum(failNum);
//		return batchResult;
//	}
//
//	public void insert(List<Object[]> list) {
//		String sql = SF.goodsSql("CARD_INFO_INSERT");
//		this.baseDaoSupport.batchExecute(sql, list);
//	}
//
//	/**
//	 * 从txt导入卡信息
//	 * 
//	 * @param map
//	 * @return
//	 */
//	@Transactional(propagation = Propagation.REQUIRED)
//	public BatchResult importCardByTxt(Map map) {
//
//		BatchResult batchResult = new BatchResult();
//
//		int sucNum = 0; // 成功返档条数
//		int failNum = 0; // 返档失败条数
//		String err_msg = "";
//		List list = (List) map.get("list");
//		CardLog cardLog = (CardLog) map.get("cardLog");
//		String cardType = (String) map.get("cardType");
//		String batchId = cardLog.getBatch_id();
//
//		List<Object[]> insertList = new ArrayList<Object[]>();
//
//		if (list != null && !list.isEmpty()) {
//			for (int i = 0; i < list.size(); i++) {
//				Map cardMap = (Map) list.get(i);
//				String cardNum = (String) cardMap.get("cardNum");
//				String password = (String) cardMap.get("password");
//
//				Card card = new Card();
//
//				card.setCard_type(cardLog.getCardtype());
//				card.setBatch_id(cardLog.getBatch_id());
//				card.setComments(cardLog.getCardtheme());
//				card.setEff_date(DateFormatUtils.formatToDate(cardLog
//						.getStartdate()));
//				card.setExp_date(DateFormatUtils.formatToDate(cardLog
//						.getStopdate()));
//				card.setCreate_date(DBTUtil.current());
//				card.setCard_price(cardLog.getFacevalue());
//				card.setState("0");
//				card.setType_code(cardType);
//				card.setImport_userid(ManagerUtils.getUserId());
//				card.setCard_code(cardNum);
//				card.setCard_password(password);
//				card.setImport_state(Consts.IMPORT_STATE_0);
//				card.setGoods_id(cardLog.getGoods_id());
//
//				try {
//					insertCard(card);
//					sucNum++;
//				} catch (Exception e) {
//					err_msg = e.getMessage();
//					failNum++;
//				}
//
//				// 批量插入使用以下方法
//				// Object[] obj = new
//				// Object[]{cardLog.getBatch_id(),cardLog.getCardtype(),cardLog.getCardtheme(),
//				// DateFormatUtils.parseStrToDate(cardLog.getStartdate()),DateFormatUtils.parseStrToDate(cardLog.getStopdate()),
//				// new
//				// Date(),StringUtil.transMoneyToYuan(Integer.parseInt(cardLog.getFacevalue())),"0",cardType,ManagerUtils.getUserId(),cardNum,password};
//				// insertList.add(obj);
//			}
//		}
//
//		// 插入日志表数据
//		try {
//			insertCardLog(cardLog);
//		} catch (Exception e) {
//			err_msg = e.getMessage();
//			e.printStackTrace();
//		}
//
//		batchResult.setErr_msg(err_msg);
//		batchResult.setBatchId(batchId);
//		batchResult.setSucNum(sucNum);
//		batchResult.setFailNum(failNum);
//		return batchResult;
//
//	}
//
//	@Transactional(propagation = Propagation.REQUIRED)
//	public void insertFromCardLog(List<Object[]> list) {
//		String sql = SF.goodsSql("INSERT_FROMCARD_LOG");
//		this.baseDaoSupport.batchExecute(sql, list);
//	}
//
//	@Transactional(propagation = Propagation.REQUIRED)
//	public void insertCard(Card card) {
//		this.baseDaoSupport.insert("card_info", card);
//	}
//
//	public int isExistsExCard(List list, String cardType) {
//
//		int count = 0;
//
//		if (list != null && !list.isEmpty()) {
//			for (int i = 0; i < list.size(); i++) {
//				Map<String, Object> map = (Map<String, Object>) list.get(i);
//
//				String sql = SF.goodsSql("IS_EXISTS_EXCARD");
//
//				String item = (String) this.baseDaoSupport.queryForString(sql,
//						map.get("card_code"), cardType);
//
//				if (!StringUtil.isEmpty(item)) {
//					count = count + Integer.valueOf(item);
//				}
//			}
//		}
//		return count;
//	}
//
//	public int isExistsTxtCard(Map map) {
//
//		int count = 0;
//
//		List list = (List) map.get("list");
//		String cardType = (String) map.get("cardType");
//
//		if (list != null && !list.isEmpty()) {
//			for (int i = 0; i < list.size(); i++) {
//				Map cardMap = (Map) list.get(i);
//				String sql = SF.goodsSql("IS_EXISTS_TXTCARD");
//				String cardNum = (String) cardMap.get("cardNum");
//				String item = this.baseDaoSupport.queryForString(sql, cardNum, cardType);
//
//				if (!StringUtil.isEmpty(item)) {
//					count = count + Integer.valueOf(item);
//				}
//			}
//		}
//		return count;
//	}
//
//	public List getGoodsList(String type_code) {
//		
//		String sql = SF.goodsSql("GET_GOODS_LIST");
//		return this.baseDaoSupport.queryForList(sql,type_code);
//	}

}
