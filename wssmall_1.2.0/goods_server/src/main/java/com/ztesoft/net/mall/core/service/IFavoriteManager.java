package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import java.util.List;

/**
 * 商品收藏管理
 * 
 * @author lzf<br/>
 *         2010-3-24 下午02:39:25<br/>
 *         version 1.0<br/>
 */
public interface IFavoriteManager {

	/**
	 * 列表我的收藏
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page list(int pageNo, int pageSize);

	public Page listFavPartner(String member_id, int pageNo, int pageSize);

	public Page listFavSuppler(String member_id, int pageNo, int pageSize);

	public Page listFavGoods(String member_id, int pageNo, int pageSize);

	/**
	 * 使用会员和goods_id进行查询。
	 * @param member_id
	 * @param goods_id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public int countFavByMemIdAndGoods(String member_id, String goods_id,String type);

	/**
	 * 读取会员的所有收藏商品
	 * 
	 * @return
	 */
	public List list();

	/**
	 * 删除收藏
	 * 
	 * @param favorite_id
	 */
	public void delete(String favorite_id);

	/**
	 * 添加一个收藏
	 * 
	 * @param goodsid
	 * @param memberid
	 */
	public void add(String goodsid);

	/**
	 * 添加一个收藏
	 * 
	 * @param goodsid
	 * @param memberid
	 */
	public void addFav(String fav_rel_id, String member_id, String fav_type);

}
