/**
 * 
 */
package com.ztesoft.net.mall.core.service;

import java.util.List;

import zte.params.order.resp.GrabRedPkgResp;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.CouponsRedPackageSearch;
import com.ztesoft.net.mall.core.model.PromotionRedPkg;
import com.ztesoft.net.mall.core.model.PromotionRedPkgDetail;

/**
 * @author ZX
 * IPromotionRed.java
 * 2015-3-16
 */
public interface IPromotionRedManager {

	/**
	 * 查询红包列表
	 * @param redSearch
	 * @param pageNo
	 * @param pageSize
	 * @param order
	 * @return
	 */
	Page redlist(CouponsRedPackageSearch redSearch, int pageNo, int pageSize, String order);
	/**
	 * 增加红包
	 * @param promotionRedPkg
	 * @return
	 */
	String addred(PromotionRedPkg promotionRedPkg);
	/**
	 * 生成红包	
	 * @param red_id
	 * @return
	 */
	String redcreate(String red_id);
	/**
	 * 抢红包
	 * @param red_id
	 * @param member_id
	 * @return
	 */
	String grabredpkg(String red_id , String member_id, GrabRedPkgResp resp);
	/**
	 * 查看红包信息
	 * @param red_id
	 * @return
	 */
	PromotionRedPkg redview(String red_id);
	
	/**
	 * 修改红包
	 * @param red_id
	 * @return
	 */
	PromotionRedPkg rededit(String red_id, String name, String memo);
	
	/**
	 * 红包优惠券列表
	 * @param red_id
	 * @return
	 */
	List<PromotionRedPkgDetail> redCpnsList(String red_id);
	
	/**
	 * 删除红包
	 * @param red_ids
	 * @return
	 */
	String reddel(String red_ids);
	
	/**
	 * 从页面上新增红包
	 * @param promotionRedPkg
	 * @return
	 */
	String addredbypage(PromotionRedPkg promotionRedPkg);
}
