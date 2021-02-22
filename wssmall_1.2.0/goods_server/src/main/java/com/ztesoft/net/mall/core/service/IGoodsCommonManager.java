package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsBusiness;

public interface IGoodsCommonManager {

	/**
	 * 保存商品外系统属性
	 * @param goods_id
	 * @param goodsBusiness
	 */
	public void saveGoodsBus(String goodsId, GoodsBusiness goodsBusiness);
	
	/**
	 * 设置与用户相关的信息，如search_key,staff_no等
	 * @param goods
	 * @param userId
	 */
	public void setGoodsUserInfo(Goods goods, String userId);
	/**
	 * 添加操作日志
	 * @param userId
	 * @param realName
	 * @param goodsId
	 */
	public void addOperLog(String userId,String realName,String goodsId,String... args);
	
	/**
	 * 商品修改活动编码与产品编码
	 * @param goodsId 商品ID
	 * @param actCode 活动编码
	 * @param prodCode 产品编码
	 */
	public void saveGoodsPackageEdit(String goodsId,String actCode,String prodCode);
	
	/**
	 * 修改商品货品信息
	 */
	public void saveGoodsEdit(String goodsId, Map goodsMap);
	
	/**
	 * 设置型号编码
	 * @param goods
	 * @param type
	 * @param typeId
	 * @param products
	 */
	public void setGoodsModelCode(Map goods, String type, String typeId, List<Map<String, Object>> products);
	
	/**
	 * 设置商品规格信息
	 * @param goods
	 * @param specImgs
	 */
	public void setGoodsSpecs(Map goods, String specImgs);
	
	/**
	 * 移除值为空的键值对
	 * @param goodsMap
	 */
	public void removeEmptyFields(Map goodsMap);
	/**
	 * 设置商品属性信息
	 * @param goods
	 * @param taocanLevel 套餐（货品）档次
	 * @param propvalues 属性
	 */
	public void setGoodsProps(Map goods, String taocanLevel, String[] propvalues);
	
	/**
	 * 保存商品包
	 * @param goodsId
	 * @param String
	 */
	public void saveGoodsTags(String goodsId, String[] tagsArr);
	
	public void savePricePriv(String goodsId);
	
	/**
	 * 保存商品会员价
	 * @param goodsId
	 * @param lvid
	 * @param lvPrice
	 * @param lvDiscount
	 */
	public void saveGoodsLvPrices(String goodsId,String[] lvid,String[] lvPrice,String[] lvDiscount);
	
	/**
	 * 保存商品与货品、货品与货品关系
	 * @param aGoodsId 商品goodsId，货品（合约计划）goodsId
	 * @param zGoodsIds 关联货品goodsId，不能为空
	 * @param productids 货品productId，不能为空
	 * @param relCodes 关联编码，如果为空，也要传入值为空的数组
	 * @param relTypes 关联类型【商品关联货品：PRO_REL_GOODS，合约关联套餐：CONTRACT_OFFER】，不能为空
	 */
	public void saveGoodsRelations(String aGoodsId, List<Map<String, Object>> products,String relTypes);
	
	/**
	 * 保存es_product表信息
	 * @param goods
	 * @param lvIds
	 * @param lvPrices
	 */
	public void saveProductInfo(Map goods, String[] lvIds, String[] lvPrices);
	
	/**
	 * 新增修改商品后，更新缓存
	 * @param goodsIds
	 */
	public void updateGoodsCache(String goodsIds);
}
