package com.ztesoft.net.mall.core.service;


/**
 *  充值卡处理类
 * 
 * @author wui
 */
public interface ICardManager {
	
	//public void transfer_card(OrderRequst orderRequst,OrderResult orderResult);
	
//	public Page transfer_list(int pageNO, int pageSize,Object... args);
//	
//	
//	public Card getCardByUserIdAndMoney(String userid,String money,String type_code,String goodsId);
//	
//	//获取卡信息
//	public Card getCardByOrderId(String order_id);
//	
//	
//	//根据用户id,金额获取等额的充值卡
//	public Card getCardNoTnsByUserIdAndMoney(String userid,String money,String type_code);
//	
//	
//	public void resetFailCardByOrderId();
//	
//	
//	//根据号码获取本地网西信息
//	public String  getLanIdByAccNbr(String acc_nbr);
//	
//	
//	//获取调拨卡的价格，价格做验证处理
//	public String  getTransferPrice(String cardIds);
//	
//	
//	public void updateCardByOrderId(String orderId);
//	
//	//充值成功后更新订单id
//	public void updateCard(Card card);
//	
//	
//	public void resetCardByOrderId(String orderId);
//	
//	
//	//调用UVC接口充值
//	public Map uvcRecharge(Card card, CardInfRequest cardInfo);
//	
//	public List list(String orderId);
//	
//	public List<Card> getCardList(String rateIds);
//	
//	public Map listc(Card card);
//	
//	@Transactional(propagation = Propagation.REQUIRED)
//	public String returnedCards(String sec_order_id);
//		
//	/**
//	 * 检查申请退货的卡是否可以退货 state <> '0' || sec_order_id is not null
//	 * @param cardIds 待检查的卡IDs 格式=id1,id2,id3
//	 * @return 不能退货的卡列表
//	 */
//	public List<Card> checkUnavailableCard(String cardIds);
//	
//	//充值卡退费申请处理
//	public String occupyCards(String cardIds,String sec_order_id);
//	
//	//获取商品金额信息
//	public String getGoodsIdByCardId(String cardid);
//	
//	//导入充值卡流量卡
//	public BatchResult importCard(List inList,String cardType);
//	
//	/**
//	 * 查询可申请商品的总量(不带goodsId)
//	 * @author hu.yi
//	 * @date 2013.5.16
//	 * @return
//	 */
//	public Map listAvialableC(Card card);
//	
//	
//	/**
//	 * 查询所有可申请商品的总量(不带goodsId)
//	 * @author hu.yi
//	 * @date 2013.5.17
//	 * @return
//	 */
//	public Page transfer_allList(int pageNO, int pageSize,Object... args);
//	
//	/**
//	 * 从txt导入卡信息
//	 * @param map
//	 * @return
//	 */
//	public BatchResult importCardByTxt(Map map);
//	
//	/**
//	 * 查询是否存在已有记录
//	 * @param list
//	 * @param cardType
//	 * @return
//	 */
//	public int isExistsExCard(List list,String cardType);
//	
//	public int isExistsTxtCard(Map map);
//	
//	public List getGoodsList(String type_code);
}
