package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsControl;
import com.ztesoft.net.mall.core.model.GoodsRules;
import com.ztesoft.net.mall.core.model.support.GoodsEditDTO;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.params.GoodsExtDataECS;
import com.ztesoft.net.mall.core.params.GoodsPlatformInfo;

public interface IGoodsManagerN {
	/**
	 * 查询商品列表
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page searchGoods(Map<String, String> params, int pageNo, int pageSize);

	/**
	 * 添加商品
	 * @param goods 商品基本信息
	 * @param goodsRules  商品动作规则信息
	 * @param goodsExtData  商品扩展信息：配件、参数等
	 * 
	 */
	public void add(Goods goods, GoodsRules goodsRules, GoodsExtData goodsExtData) throws Exception;
	
	/**
	 * 添加商品
	 * @param goods
	 * @param goodsRules
	 * @param goodsExtData
	 * @throws Exception
	 */
	public void addGoods(Goods goods, GoodsRules goodsRules, GoodsExtData goodsExtData) throws Exception;
	
	/**
	 * 编辑商品
	 * @param goods
	 * @param goodsExtData
	 * @throws Exception
	 */
	public void edit(Goods goods, GoodsRules goodsRules, GoodsExtData goodsExtData) throws Exception;
	
	/**
	 * 编辑商品
	 * @param goods
	 * @param goodsRules
	 * @param goodsExtData
	 * @throws Exception
	 */
	public void editGoods(Goods goods, GoodsRules goodsRules, GoodsExtData goodsExtData) throws Exception;
	
	/**
	 * 删除商品
	 * @param goods_id 商品ID
	 */
	public void delete(String goods_id);
	
	/**
	 * 添加产品包信息-联通ECS
	 * @param goodsPackage
	 * @param goods_id
	 * @param type
	 * @param act_code
	 * @param prod_code
	 * @param lvidArray
	 * @param sns
	 * @param typeids
	 * @param z_goods_ids
	 */
	public void addGoodsPackage(GoodsExtDataECS goodsExtData);
	
	/**
	 * 修改时获取商品数据
	 * 
	 * @param goods_id
	 * @return
	 */
	public GoodsEditDTO getGoodsEditData(String goods_id);
	
	/**
     * 根据ID得到控制信息
     * @param goods_id
     * @return
     */
	public List<GoodsControl> getControlById(String goods_id);
	
	/**
	 * 填充添加前的数据
	 * 
	 */
	public  List<String> fillAddInputData();
	
	/**
	 * 更新某个商品的字段值
	 * 
	 * @param filedname
	 *            字段名称
	 * @param value
	 *            字段值
	 * @param goodsid
	 *            商品id
	 */
	public void updateField(String filedname,Object value,String goodsid);
	
	/**
	 * 取发布结果
	 * @param goods_id
	 * @return
	 */
	public List getOrgResultByGoodsId(String goods_id);
	
	/**
	 * 获取序列
	 * @param seq 序列名
	 * @return
	 */
	public String getSequence(String seq);
    
	/**
	 * 新增商品图片信息
	 * @param goodsPlatformInfo
	 */
	public void addGoodsPlatFormInfo(GoodsPlatformInfo goodsPlatformInfo);
	
	/**
	 * 获取商品平台信息：图片、图片描述、商品详情
	 * @param goods_id
	 * @return
	 */
	public GoodsPlatformInfo getGoodsPlatFormInfo(String goods_id);
	
	/**
	 * 删除商品平台信息
	 * @param goods_id
	 */
	public void delGoodsPlatformInfo(String goods_id);
	
	/**
	 * 获取商品会员价格
	 * @param goods_id
	 * @param product_id
	 * @param lv_id
	 * @return
	 */
	public String getAcceptPrice(String goods_id,String product_id,String lv_id);
	
	/**
	 * 添加代理商品
	 * @param proxy
	 * @return
	 */
	public void addProxyGoods(Map<String, String> proxy);
	
	/**
	 * 删除代理商品
	 * @param proxy
	 */
	public void deleteProxyGoods(Map<String, String> proxy);
	
	/**
	 * 复制商品
	 * @param goods_id
	 * @param user_id
	 * @param founder
	 * @param parent_user_id
	 */
	public void copyGoods(String goods_id, String user_id, String founder, String parent_user_id);
	
	/**
	 * 获取用户信息
	 * @param userid
	 * @return
	 */
	public AdminUser getUserInfo(String userid);
	
	/**
	 * 获取关联商品
	 * @param a_goods_id
	 * @param rel_type
	 * @return
	 */
	List<Goods> listRelGoods(String a_goods_id);
	
	/**
	 * 根据分类ID获取商品列表
	 * @param cat_id
	 * @return
	 */
	public List<Goods> listGoodsByCatId(String cat_id);
	
	/**
	 * 获取商品详情信息：包括商品基本信息，图片，简介和商品详情
	 * @param goods_id
	 * @return
	 */
	public Goods getGoodsIntros(String goods_id);
	
	/**
	 * 获取商品信息：包括商品基本信息
	 * @param goods_id
	 * @return
	 */
	public Goods getGoodsBaseInfo(String goods_id);
}
