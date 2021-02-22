package com.ztesoft.net.mall.widget.memberlv;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.MemberLv;

public interface IMemberLVSpec {

	/**
	 * 按等级ID查询会员等级
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-16 
	 * @param memberID
	 * @return
	 */
	public List<MemberLv> qryMLVbyIDS(String lvIDs);
	/**
	 * 按等级ID与商品ID查询会员等级
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-21 
	 * @param lvIDs
	 * @return
	 */
	public List<MemberLv> qryMLVbyIdsAndGoodsID(String lvIDs,String goods_id);
	
	/**
	 * 根据productID与lvID查看该会员是否可以购买商品
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-21 
	 * @param productID
	 * @param lvID
	 * @return
	 */
	public boolean memberLvCanBy(String productID,String lvID);
	
	/**
	 * 获取会员价
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-3 
	 * @param productID
	 * @param goodsID
	 * @param lvid
	 * @return
	 */
	public Map getMemerLvPrice(String productID,String goodsID,String lvid);


    public boolean  memberLvCanByGoodsid(String goods_id,String lvID);
	
}
