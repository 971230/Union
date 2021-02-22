package com.ztesoft.net.mall.core.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.ztesoft.net.framework.database.ObjectNotFoundException;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.ActivityAttr;
import com.ztesoft.net.mall.core.model.ActivityCo;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.PromotionActivity;


/**
 * 促销活动管理接口 
 * @author kingapex
 *2010-4-15下午12:04:07
 */
public interface IPromotionActivityManager {
	
	
	/**
	 * 活动发布
	 * @param activity_id
	 * @param org_ids
	 */
	public void doPublishActivity(String activity_id,String org_ids);
	
	
	/**
	 * 添加促销活动
	 * @param activity 促销活动
	 * @throws IllegalStateException 如有下列情况之一：
	 * <li>activity 为null</li>
	 * <li>name 为null</li>
	 * <li>begin_time 为null</li> 
	 * <li>end_time 为null</li> 
	 */
	public void add(PromotionActivity  activity, String[] tagids);
	
	
	/**
	 * 读取促销活动
	 * @param id 促销活动id
	 * @return 促销活动实体
	 * @throws IllegalStateException 如果id为null
	 * @throws ObjectNotFoundException 如果未找到相应的促销活动
	 */ 
	public PromotionActivity get(String id);

	
	
	
	/**
	 * 分页读取促销活动列表
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @return  促销活动列表
	 */
	public Page list(PromotionActivity activity,int pageNo,int pageSize);
	
	
	
	
	/**
	 * 修改促销活动
	 * @param activity 促销活动
	 * @throws IllegalStateException 如有下列情况之一：
	 * <li>id 为null</li>
	 * <li>name 为null</li>
	 * <li>begin_time 为null</li> 
	 * <li>end_time 为null</li> 
	 */	
	public void edit(PromotionActivity  activity, String[] tagids);
	
	
	
	
	/**
	 * 指删除某些促销活动<br>
	 * 删除促销活动，同时删除此活动对应的促销规则（通过pmta_id关联）,及规则关联的商品促销对应数据 、会员级别促销对应表数据<br/>
	 * @param idArray 促销活动id数组
	 * 如果此参数为null或大小为0则不做任何处理 
	 */
	public void delete(String[] idArray);
	
	/**
	 * 根据标签标识获取活动信息
	 * @param tagId
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page pageList(String tagId, String userId, int pageNo, int pageSize);
	/**
	 * 查找promotion_activity 的主键
	 */
		
		public String queryActivityId(String name);
		/**
		 * 活动选择器选择活动列表
		 * @param name
		 * @param order
		 * @param page
		 * @param pageSize
		 * @return
		 */
		public Page list(String name, String order, int page,
				int pageSize);
		public List list(String[] ids) ;
		//修改活动状态
		public void editEnable(String editEnable, String activity_id);


	/**
	 * 根据活动标识获取活动基本信息及规则和适用该活动的商品列表
	 * @param pmt_id
	 * @return
	 */
	public Map getPromotionMap(String activity_id);
	
	
	/**
	 * 批量导入活动
	 * @param file
	 * @return
	 */
	public Map importActivity(File file,String fileFileName) throws Exception;
	
	
	/**
	 * 分页读取促销活动列表ECS
	 * @param pageNo 当前页码
	 * @param pageSize 每页大小
	 * @return  促销活动列表
	 */
	public Page listECS(PromotionActivity activity,ActivityAttr activityAttr,Promotion promotion,int pageNo,int pageSize);
	
	/**
	 * 根据条件批量发布活动
	 * @param activity
	 * @param activityAttr
	 * @param promotion
	 * @return 发布结果
	 */
	public String batchActivityPublish(PromotionActivity activity,ActivityAttr activityAttr,Promotion promotion);
	
	/**
	 * 商品列表
	 * @param pageNo
	 * @param pageSize
	 * @param name
	 * @return
	 */
	public Page getGoodsList(int pageNo, int pageSize,String name,String type,String type_id,String sku);
	
	/**
	 * 货品包列表
	 * @param pageNo
	 * @param pageSize
	 * @param name
	 * @return
	 */
	
	Page getPackageList(int pageNo, int pageSize, String name, String sku);
	
	/**
	 * 广东联通保存活动
	 * @param activity
	 * @param activityAttr
	 * @param promotion
	 */
	public void saveOrUpdateActECS(PromotionActivity activity,ActivityAttr activityAttr,Promotion promotion);
	
	/**
	 * 根据活动标识获取活动规则
	 * @param pmt_id
	 * @return
	 */
	public Promotion getPromotion(String activity_id);
	
	/**
	 * 根据活动标识获取赠品规则
	 * @param pmt_id
	 * @return
	 */
	public Promotion getGiftPromo(String activity_id);
	
	/**
	 * 根据规则id，获取商品
	 * @param pmt_id
	 * @return
	 */
	public List<Goods> getGoodsByPmtId(String pmt_id);
	
	/**
	 * 根据活动activity_id，获取商品发布组织信息
	 * @param pmt_id
	 * @return
	 */
	public List<ActivityCo> getActivityCo(String activity_id);
	
	/**
	 * 判断活动编码是否存在
	 * @param pmt_code
	 * @return true 已经存在，false 不存在
	 */
	public String checkPmtCode(String pmt_code);
	
	/**
	 * 修改活动状态
	 * @param enable
	 * @return
	 */
	public Map editEnableECS(PromotionActivity activity,ActivityAttr activityAttr);
	
	/**
	 * 获取活动的发布状态
	 * @param activity_id
	 * @return
	 */
	public Integer getActivityPubStatus(String activity_id);
	
	/**
	 * 定时任务导入活动
	 */
	public boolean importActivity();
	
	/**
	 * 活动导入日志
	 * @param batch_id
	 * @param begin_date
	 * @param end_date
	 * @param deal_flag
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page listActivityImportLogsECS(String batch_id,String begin_date,String end_date,String deal_flag,int pageNum,int pageSize);
	
	
	/**
	 * 
	 * @param activity
	 * @param activityAttr
	 * @param promotion
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page listActivityModifyLogsECS(PromotionActivity activity,ActivityAttr activityAttr,Promotion promotion,int pageNo, int pageSize);

	/**
	 * 查询活动同步日志
	 * @param params
	 * @return
	 */
	public Page searchActivitySynLogs(Map params,int pageNum,int pageSize);
	
	/**
	 * 重新发送失败的活动
	 * @param params
	 * @return
	 */
	public int publishAgain(Map params);

	/**
	 * 获取活动规格：活动类型，活动名称，优惠信息等
	 * @param activity_id
	 * @return
	 */
	public Map getActivitySpecs(String activity_code);
	
	
	public List<HashMap> listAllActivitySpecs();


	public List queryActivityExport(PromotionActivity activity);


	/**
	 * 批量校验活动
	 * @param file
	 * @return
	 */
	public void checkImport(File file,String fileFileName,HttpServletResponse response) throws Exception;
	
	/**
	 * 根据活动id，获取商品
	 * @author zengxianlian
	 * @param id
	 * @return
	 */
	public List<Goods> getGoodsById(String id);
	
	/**
	 * 活动发布前校验
	 * @author zengxianlian
	 * @param file
	 * @return
	 */
	public Map<String,String> checkPublishAct(Map<String,String> params) throws Exception;
	
	/**
	 * 活动批量发布
	 * @author zengxianlian
	 * @return
	 */
	public String publishAct(List<String> ids) throws Exception;

}
