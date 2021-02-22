package com.ztesoft.net.mall.core.action.backend;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import params.member.req.MemberLvListReq;
import params.member.resp.MemberLvListResp;
import params.tags.req.TagCat_typeReq;
import params.tags.resp.TagResp;
import services.CouponInf;
import services.MemberLvInf;
import zte.net.iservice.IGoodsService;
import zte.params.goods.req.PromotionActAddReq;
import zte.params.goods.req.PromotionActEditReq;

import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.consts.GoodsConsts;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.plugin.promotion.IPromotionPlugin;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IPromotionManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.promotion.IPromotionMethod;
import com.ztesoft.net.mall.core.service.promotion.PromotionConditions;
//import com.ztesoft.net.mall.core.service.IPromotionActivityManager;
//import com.ztesoft.net.mall.core.service.ITagManager;
//import com.ztesoft.net.mall.core.service.impl.TagManager;

/**
 * 促销规则管理
 * @author kingapex
 *2010-4-21上午11:35:55
 */
public class PromotionAction extends WWAction {
	
	private CouponInf couponServ;
	
	//添动id如果为空，则说明是再为优惠卷设置规则
	private String activityid;
	
	//促销规则id，如果不为空则在编辑状态
	private String pmtid;
	
	protected IPromotionManager promotionManager ;
	
	
	private List pmtList;
	private List pluginList;
	protected List lvList;
	protected List goodsList;
	private String pluginid;
	private List promotionTypeList;
	protected  PromotionConditions conditions;
	private IDcPublicInfoManager dcPublicInfoManager;
	protected String solutionHtml;
	private Double money_from;
	private Double money_to;
	private String time_begin;
	private String time_end;
	private Integer[] lvidArray;
	private String[] goodsidArray;
	private String  pmtidArray;
	private int ifcoupon; //是否使用优惠卷
	private String describe;
	protected Promotion promotion ;
	private PromotionActivity activity;
	private String promotion_type;
	private MemberLvInf memberLvServ;
	private String goodsid;
	private List tagLst = null;
	private String begin_time;
	private String end_time;
	private String[] id;
	@Resource
	private IGoodsService goodServices;
//	private IPromotionActivityManager promotionActivityManager;
	private String pmts_id ;
//	private ITagManager tagManager;
	private String activity_id;
	private String[] tag_ids;
	private String picFileName;
	private File pic;
	private String isNetStaff = "F";
	/**
	 * 是优惠券(yes)还是活动
	 */
	protected String cps;//
	/**
	 * 读取某活动的促销规则列表
	 * @return
	 */
	public String listActivity(){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	    this.promotionTypeList = dcPublicCache.getList("1443");
	    this.webpage = promotionManager.queryPromotionAct(activity, promotion, goodsid, this.getPage(), this.getPageSize());
	  
		return "listActivity";
	}
	/**
	 * 读取某活动的促销规则列表
	 * @return
	 */
	public String list(){
		pmtList = this.promotionManager.listByActivityId(activityid);
		return "list";
	}

	/**
	 * 选择促销插件
	 * @return
	 */
	public String select_plugin(){
		if(StringUtils.isNotEmpty(this.pmtid)){//编辑时找到相应插件id
			promotion= 	promotionManager.get(pmtid);
			this.pluginid= promotion.getPmts_id();
		}
		this.pluginList = promotionManager.listPmtPlugins();
		return "select_plugin";
	}
	/**
	 * 查询 列表
	 */
	
	/**
	 * 新增活动
	 * 
	 */
	
	public String select_condition_ProAct(){
		try{
			TagResp respTag = new TagResp();
			TagCat_typeReq reqTag = new TagCat_typeReq();
			reqTag.setCat_type(GoodsConsts.TAGS_CAT_TYPE_200);
			respTag =  this.goodServices.tagListByCatType(reqTag);
			this.tagLst = respTag.getTags();
			MemberLvListReq req = new MemberLvListReq();
			MemberLvListResp resp = memberLvServ.getMemberLvList(req);
			if(resp != null){
				this.lvList = resp.getMemberLvs();
			}
			String solution =null;
			if(StringUtils.isNotEmpty(pmtid)){ //编辑时使用库中读取的开始、结束时间
				this.promotion = this.promotionManager.get(pmtid);
				this.time_begin =promotion.getPmt_time_begin()+"";
				this.time_end =promotion.getPmt_time_end()+"";
				solution = this.promotion.getPmt_solution();
				this.describe = this.promotion.getPmt_describe();
				this.ifcoupon = this.promotion.getPmt_ifcoupon();
				this.money_from = this.promotion.getOrder_money_from();
				this.money_to = this.promotion.getOrder_money_to();
				
				//使关联的会员级别id选中
				List<Integer> dbLvList  = this.promotionManager.listMemberLvId(pmtid);
				for(int i=0;i<lvList.size();i++){
					MemberLv lv =(MemberLv)lvList.get(i);
					for(Integer dbLvid :dbLvList ){
						if(Integer.parseInt(lv.getLv_id()) == dbLvid.intValue()){
							lv.setSelected(true);
						}
					}
				}
				
				this.goodsList=this.promotionManager.listGoodsId(pmtid);
				
			}else{ //新增时开始时间为今天，结束时间为之后的第10天
			
				this.time_begin = DateFormatUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance(); 
				cal.add(Calendar.DAY_OF_MONTH, +10);  
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.time_end = df.format(cal.getTime());
				//this.time_end =org.apache.commons.lang.time.DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss"); 
			}
			if(promotion!=null && !pluginid.equals(promotion.getPmts_id()))
				solution = null;
			IPromotionPlugin plugin = this.promotionManager.getPlugin(pluginid);
			conditions = new PromotionConditions( plugin.getConditions());
			String methodBeanName = plugin.getMethods();
			IPromotionMethod pmtMethod=  SpringContextHolder.getBean(methodBeanName);
			if("006".equals(promotion_type)){
				solutionHtml="<h5>请输入直降金额</h5>"+
						"<input type=\"text\""+
					" name=\"discount\" value=\"\""+
					"/>";
			}else if ("007".equals(promotion_type)){
				solutionHtml="<h5>请输入预销金额</h5>"+
						"<input type=\"discount_check\" vtype=\"discount_check\""+
					" class=\"_x_ipt discount_check\" style=\"\" required=\"true\" name=\"discount\" value=\"\""+
					" autocomplete=\"off\"/>";
			}
			else{
				
				solutionHtml = pmtMethod.getInputHtml(pmtid, solution);
			}
			
			
			return "select_condition_ProAct";
		}catch(RuntimeException e){
			logger.info(e);
			this.msgs.add(e.getMessage());
			return WWAction.MESSAGE;
		}
	}
	/**
	 * 选择规则条件
	 * @return
	 */
	public String select_condition(){
		try{
			TagResp respTag = new TagResp();
			TagCat_typeReq reqTag = new TagCat_typeReq();
			reqTag.setCat_type(GoodsConsts.TAGS_CAT_TYPE_200);
			this.tagLst =  this.goodServices.tagListByCatType(reqTag).getTags();
			MemberLvListReq req = new MemberLvListReq();
			MemberLvListResp resp = memberLvServ.getMemberLvList(req);
			if(resp != null){
				this.lvList = resp.getMemberLvs();
			}
			String solution =null;
			if(StringUtils.isNotEmpty(pmtid)){ //编辑时使用库中读取的开始、结束时间
				this.promotion = this.promotionManager.get(pmtid);
				this.time_begin =promotion.getPmt_time_begin()+"";
				this.time_end =promotion.getPmt_time_end()+"";
				solution = this.promotion.getPmt_solution();
				this.describe = this.promotion.getPmt_describe();
				this.ifcoupon = this.promotion.getPmt_ifcoupon();
				this.money_from = this.promotion.getOrder_money_from();
				this.money_to = this.promotion.getOrder_money_to();
				
				//使关联的会员级别id选中
				List<Integer> dbLvList  = this.promotionManager.listMemberLvId(pmtid);
				for(int i=0;i<lvList.size();i++){
					MemberLv lv =(MemberLv)lvList.get(i);
					for(Integer dbLvid :dbLvList ){
						if(Integer.parseInt(lv.getLv_id()) == dbLvid.intValue()){
							lv.setSelected(true);
						}
					}
				}
				
				this.goodsList=this.promotionManager.listGoodsId(pmtid);
				
			}else{ //新增时开始时间为今天，结束时间为之后的第10天
			
				this.time_begin = DateFormatUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance(); 
				cal.add(Calendar.DAY_OF_MONTH, +10);  
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.time_end = df.format(cal.getTime());
				//this.time_end =org.apache.commons.lang.time.DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss"); 
			}
			if(promotion!=null && !pluginid.equals(promotion.getPmts_id()))
				solution = null;
			IPromotionPlugin plugin = this.promotionManager.getPlugin(pluginid);
			conditions = new PromotionConditions( plugin.getConditions());
			String methodBeanName = plugin.getMethods();
			IPromotionMethod pmtMethod=  SpringContextHolder.getBean(methodBeanName);
			if("006".equals(promotion_type)){
				solutionHtml="<h5>请输入直降金额</h5>"+
						"<input type='discount_check' vtype='discount_check'"+
					"class='_x_ipt discount_check' style='' required='true' name='discount' value=''"+
					"autocomplete='off'>";
			}else if ("007".equals(promotion_type)){
				solutionHtml="<h5>请输入预售金额</h5>"+
						"<input type='discount_check' vtype='discount_check'"+
					"class='_x_ipt discount_check' style='' required='true' name='discount' value=''"+
					"autocomplete='off'>";
			}
			else{
				
				solutionHtml = pmtMethod.getInputHtml(pmtid, solution);
			}
			
			
			return "select_condition";
		}catch(RuntimeException e){
			logger.info(e);
			this.msgs.add(e.getMessage());
			return WWAction.MESSAGE;
		}
	}
	
	
	/**
	 * 查看优惠规则
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-10 
	 * @return
	 */
	public String show(){
		
		try{
			MemberLvListReq req = new MemberLvListReq();
			MemberLvListResp resp = memberLvServ.getMemberLvList(req);
			if(resp != null){
				this.lvList = resp.getMemberLvs();
			}
			
			String solution =null;
			if(StringUtils.isNotEmpty(pmtid)){ //编辑时使用库中读取的开始、结束时间
				this.promotion = this.promotionManager.get(pmtid);
				this.time_begin =promotion.getPmt_time_begin()+"";
				this.time_end =promotion.getPmt_time_end()+"";
				this.pmts_id = promotion.getPmts_id();
				solution = this.promotion.getPmt_solution();
				this.describe = this.promotion.getPmt_describe();
				this.ifcoupon = this.promotion.getPmt_ifcoupon();
				this.money_from = this.promotion.getOrder_money_from();
				this.money_to = this.promotion.getOrder_money_to();
				this.promotion_type = this.promotion.getPromotion_type();
				//使关联的会员级别id选中
				List<Integer> dbLvList  = this.promotionManager.listMemberLvId(pmtid);
				for(int i=0;i<lvList.size();i++){
					MemberLv lv =(MemberLv)lvList.get(i);
					for(Integer dbLvid :dbLvList ){
						if(Integer.parseInt(lv.getLv_id()) == dbLvid.intValue()){
							lv.setSelected(true);
						}
					}
				}
				
				this.goodsList=this.promotionManager.listGoodsId(pmtid);
				
			}else{ //新增时开始时间为今天，结束时间为之后的第10天
			
				this.time_begin =DateFormatUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance(); 
				cal.add(Calendar.DAY_OF_MONTH, +10);  
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.time_end = df.format(cal.getTime());
				//this.time_end =org.apache.commons.lang.time.DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss"); 
			}
			IPromotionPlugin plugin = this.promotionManager.getPlugin(pluginid);
			conditions = new PromotionConditions( plugin.getConditions());
			String methodBeanName = plugin.getMethods();
			IPromotionMethod pmtMethod=  SpringContextHolder.getBean(methodBeanName);
			if("006".equals(promotion_type)){
				solutionHtml="<h5>请输入直降金额</h5>"+
						"<input type='discount_check' vtype='discount_check'"+
						"class='_x_ipt discount_check' style='' required='true' name='discount' value='"+solution+
						"' autocomplete='off'>";
			}else if ("007".equals(promotion_type)){
				solutionHtml="<h5>请输入预售金额</h5>"+
						"<input type='discount_check' vtype='discount_check'"+
					"class='_x_ipt discount_check' style='' required='true' name='discount' value='"+solution+
					"' autocomplete='off'>";
			}else{
			solutionHtml = pmtMethod.getInputHtml(pmtid, solution);
			}
			return "show";
		}catch(RuntimeException e){
			logger.info(e);
			this.msgs.add(e.getMessage());
			return WWAction.MESSAGE;
		}
	}
	
	/**
	 * 保存规则
	 */

public String save(){
		

		try{
			IPromotionPlugin plugin = this.promotionManager.getPlugin(pluginid);
			conditions = new PromotionConditions( plugin.getConditions());
			
			if(StringUtils.isNotEmpty(pmtid)){
				this.promotion = this.promotionManager.get(pmtid);
			}else{
				this.promotion = new Promotion();
			}
			
			if(money_from == null)
				money_from = 0.0 ;
			if(money_to == null)
				money_to = 0.0 ;
			promotion.setOrder_money_from(money_from);
			promotion.setOrder_money_to(money_to);
			promotion.setPmt_time_begin(this.time_begin);
			promotion.setPmt_time_end(this.time_end);
 			promotion.setPmt_ifcoupon(ifcoupon);
			promotion.setPmt_describe(this.describe);
			
 		
 			
 			if(StringUtils.isNotEmpty(this.activityid)){
 				promotion.setPmta_id(this.activityid); //促销活动id
 				promotion.setPmt_type("0"); //促销活动
 			}else{
 				promotion.setPmt_type("1"); //优惠券
 			}
 			
 			promotion.setPmts_id(this.pluginid);
 			if("goodsDiscountPlugin".equals(this.pluginid)){
 				if(StringUtils.isNotEmpty(promotion_type)){
 					if("006".equals(promotion_type)){
 						promotion.setPromotion_type("006");
 					}
 					if("007".equals(promotion_type)){
 						promotion.setPromotion_type("007");
 					}
 				}else{
 					promotion.setPromotion_type("001");
 				}
 			}
 			if("goodsTimesPointPlugin".equals(this.pluginid)){
 				promotion.setPromotion_type("002");
 			}
 			if("enoughPriceGiveGiftPlugin".equals(this.pluginid)){
 				promotion.setPromotion_type("003");
 			}
 			if("enoughPriceReducePrice".equals(this.pluginid)){
 				promotion.setPromotion_type("004");
 			}
 			if("enoughPriceFreeDeliveryPlugin".equals(this.pluginid)){
 				promotion.setPromotion_type("005");
 			}
			if(StringUtils.isNotEmpty(pmtid)){
				this.promotionManager.edit(promotion, lvidArray, goodsidArray);

				if(StringUtils.isNotEmpty(this.activityid)){ //优惠卷
					Coupons coupons = (Coupons)ThreadContextHolder.getSessionContext().getAttribute("coupons");
					if(coupons != null && StringUtils.isNotEmpty( coupons.getCpns_id()) ){
						coupons.setPmt_id(pmtid);
						this.couponServ.edit(coupons);
						//this.msgs.add("优惠券修改成功");
					}/*else{
						this.msgs.add("优惠券修改成功");
					}*/
					if("yes".equals(cps)){
						this.msgs.add("优惠券修改成功");
					}else{
						this.msgs.add("活动修改成功");
					}
				}else{
					this.msgs.add("促销规则修改成功");
				}
				
				
			}else{
				pmtid = this.promotionManager.add(promotion, lvidArray, goodsidArray);
				
				if(StringUtils.isEmpty(this.activityid)){ //添加优惠卷
					Coupons coupons = (Coupons)ThreadContextHolder.getSessionContext().getAttribute("coupons");
					if(coupons != null && StringUtils.isNotEmpty( coupons.getCpns_id()) ){
						coupons.setPmt_id(pmtid);
						this.couponServ.edit(coupons);
						if("yes".equals(cps)){
							this.msgs.add("优惠券修改成功");
						}else{
							this.msgs.add("活动修改成功");
						}
					}else{
						coupons.setPmt_id(pmtid);
						this.couponServ.add(coupons);
						if("yes".equals(cps)){
							this.msgs.add("优惠券添加成功");
						}else{
							this.msgs.add("活动添加成功");
						}
					}
					
				}else{
					this.msgs.add("促销规则添加成功");
				}
			}
			
			if(StringUtils.isNotEmpty(this.activityid)){ 
				this.urls.put("此活动促销规则列表", "promotion!list.do?activityid="+promotion.getPmta_id());
			}else{
				this.urls.put("优惠券列表", "coupon!list.do");
			}
			
			
			return WWAction.MESSAGE;
		 
			
		}catch(RuntimeException e){
			e.printStackTrace();
			 logger.info(e.getStackTrace());
			this.msgs.add(e.getMessage());
			return WWAction.MESSAGE;
		}
		
	
	}

	private String discount;
	/**
	 * 保存创建活动 跟规则信息
	 *  
	 * @return
	 */
	public String saveAll(){
		HttpServletRequest request  = ThreadContextHolder.getHttpRequest();
		request.setAttribute("discount", discount);
		//设置图片路径
		if (this.pic != null) {

			if (FileBaseUtil.isAllowUp(this.picFileName)) {
				String path = UploadUtil.upload(this.pic, this.picFileName, "goods");
				this.activity.setAtturl(path);
			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
				return WWAction.MESSAGE;
			}
		}
		
		
		try {
			String name = activity.getName();
			String pmt_code = activity.getPmt_code();
			this.begin_time = time_begin;
			this.end_time = time_end ;
			String  id ="";
			String brief =this.describe;
			this.activity.setBegin_time(begin_time);
			this.activity.setEnd_time(end_time);
			this.activity.setBrief(brief);
			this.activity.setPmt_code(pmt_code);
//			this.activity.set
			if(StringUtils.isNotEmpty(activity.getId())){
				PromotionActEditReq req = new PromotionActEditReq();
				req.setActivity(activity);
				req.setTagids(tag_ids);
				this.goodServices.editPromotionActivity(req);
				id = activity.getId();
			}else{
				PromotionActAddReq req = new PromotionActAddReq();
				req.setActivity(activity);
				req.setTagids(this.tag_ids);
				goodServices.addPromotionActivity(req);
//			this.promotionActivityManager.add(activity, this.tag_ids);
			id = activity.getId();
			}
			IPromotionPlugin plugin = this.promotionManager.getPlugin(pluginid);
			conditions = new PromotionConditions( plugin.getConditions());
			
			if(StringUtils.isNotEmpty(pmtid)){
				this.promotion = this.promotionManager.get(pmtid);
			}else{
				this.promotion = new Promotion();
			}
			
			if(money_from == null)
				money_from = 0.0 ;
			if(money_to == null)
				money_to = 0.0 ;
			promotion.setPmta_id(id);
			promotion.setOrder_money_from(money_from);
			promotion.setOrder_money_to(money_to);
			promotion.setPmt_time_begin(this.time_begin);
			promotion.setPmt_time_end(this.time_end);
 			promotion.setPmt_ifcoupon(ifcoupon);
			promotion.setPmt_describe(this.describe);
			promotion.setPmt_solution(this.discount);
 		
 			if(StringUtils.isNotEmpty(this.activityid)){
 				promotion.setPmta_id(this.activityid); //促销活动id
 				promotion.setPmt_type("0"); //促销活动
 			}else{
 				promotion.setPmt_type("1"); //优惠券
 			}
 			
 			promotion.setPmts_id(this.pluginid);
 			if("goodsDiscountPlugin".equals(this.pluginid)){
 				if(promotion_type!=""){
 					if("006".equals(promotion_type)){
 						promotion.setPromotion_type("006");
 					}
 					if("007".equals(promotion_type)){
 						promotion.setPromotion_type("007");
 					}
 					if("001".equals(promotion_type)){
 						promotion.setPromotion_type("001");
 					}
 				}else{
 					promotion.setPromotion_type("001");
 				}
 			}
 			if("goodsTimesPointPlugin".equals(this.pluginid)){
 				promotion.setPromotion_type("002");
 			}
 			if("enoughPriceGiveGiftPlugin".equals(this.pluginid)){
 				promotion.setPromotion_type("003");
 			}
 			if("enoughPriceReducePrice".equals(this.pluginid)){
 				promotion.setPromotion_type("004");
 			}
 			if("enoughPriceFreeDeliveryPlugin".equals(this.pluginid)){
 				promotion.setPromotion_type("005");
 			}
 			pmtid = this.promotionManager.add(promotion, lvidArray, goodsidArray);
			this.msgs.add("活动添加成功");
		} catch (Exception e) {
			this.msgs.add("活动添加失败");
			e.printStackTrace();
			return WWAction.MESSAGE;
		}
		
		this.urls.put("促销活动列表", "promotion!listActivity.do");
		return WWAction.MESSAGE;
}
	
 
	
	/**
	 * 删除规则
	 * @return
	 */
	public String delete(){
		try{
			String a[] = pmtidArray.split(",");
			String pmt_id = "";
			for (int i = 0; i < a.length; i++) {
				pmt_id += a[i].replaceAll(a[i], "" + a[i] + "") + ',';
			}
			pmt_id = pmt_id.substring(0,
					pmt_id.length() - 1);
			this.promotionManager.delete(pmt_id);
			this.json="{result:0,message:'删除成功'}";
		}catch(RuntimeException e){
			this.json="{result:1,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}



	public IPromotionManager getPromotionManager() {
		return promotionManager;
	}



	public void setPromotionManager(IPromotionManager promotionManager) {
		this.promotionManager = promotionManager;
	}



	public List getPmtList() {
		return pmtList;
	}



	public void setPmtList(List pmtList) {
		this.pmtList = pmtList;
	}


	public List getPluginList() {
		return pluginList;
	}


	public void setPluginList(List pluginList) {
		this.pluginList = pluginList;
	}


	public String getPluginid() {
		return pluginid;
	}


	public void setPluginid(String pluginid) {
		this.pluginid = pluginid;
	}


	public PromotionConditions getConditions() {
		return conditions;
	}


	public void setConditions(PromotionConditions conditions) {
		this.conditions = conditions;
	}


	public String getSolutionHtml() {
		return solutionHtml;
	}


	public void setSolutionHtml(String solutionHtml) {
		this.solutionHtml = solutionHtml;
	}


	public Double getMoney_from() {
		return money_from;
	}


	public void setMoney_from(Double moneyFrom) {
		money_from = moneyFrom;
	}


	public Double getMoney_to() {
		return money_to;
	}


	public void setMoney_to(Double moneyTo) {
		money_to = moneyTo;
	}


	public String getTime_begin() {
		return time_begin;
	}


	public void setTime_begin(String timeBegin) {
		time_begin = timeBegin;
	}


	public String getTime_end() {
		return time_end;
	}


	public void setTime_end(String timeEnd) {
		time_end = timeEnd;
	}


	public Integer[] getLvidArray() {
		return lvidArray;
	}


	public void setLvidArray(Integer[] lvidArray) {
		this.lvidArray = lvidArray;
	}


	public String[] getGoodsidArray() {
		return goodsidArray;
	}


	public void setGoodsidArray(String[] goodsidArray) {
		this.goodsidArray = goodsidArray;
	}

	public Promotion getPromotion() {
		return promotion;
	}


	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}


	public int getIfcoupon() {
		return ifcoupon;
	}


	public void setIfcoupon(int ifcoupon) {
		this.ifcoupon = ifcoupon;
	}


	public String getDescribe() {
		return describe;
	}


	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public List getLvList() {
		return lvList;
	}


	public void setLvList(List lvList) {
		this.lvList = lvList;
	}




	public String getPmtid() {
		return pmtid;
	}

	

	public void setPmtid(String pmtid) {
		this.pmtid = pmtid;
	}


	public List getGoodsList() {
		return goodsList;
	}


	public void setGoodsList(List goodsList) {
		this.goodsList = goodsList;
	}


	public String getCps() {
		return cps;
	}

	public void setCps(String cps) {
		this.cps = cps;
	}

	public MemberLvInf getMemberLvServ() {
		return memberLvServ;
	}

	public void setMemberLvServ(MemberLvInf memberLvServ) {
		this.memberLvServ = memberLvServ;
	}


	public CouponInf getCouponServ() {
		return couponServ;
	}


	public void setCouponServ(CouponInf couponServ) {
		this.couponServ = couponServ;
	}


	public List getPromotionTypeList() {
		return promotionTypeList;
	}


	public void setPromotionTypeList(List promotionTypeList) {
		this.promotionTypeList = promotionTypeList;
	}


	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}


	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}
	public String getPromotion_type() {
		return promotion_type;
	}
	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}
	public List getTagLst() {
		return tagLst;
	}
	public void setTagLst(List tagLst) {
		this.tagLst = tagLst;
	}
	public PromotionActivity getActivity() {
		return activity;
	}
	public void setActivity(PromotionActivity activity) {
		this.activity = activity;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

//	public IPromotionActivityManager getPromotionActivityManager() {
//		return promotionActivityManager;
//	}
//
//	public void setPromotionActivityManager(
//			IPromotionActivityManager promotionActivityManager) {
//		this.promotionActivityManager = promotionActivityManager;
//	}

	
	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String[] getTag_ids() {
		return tag_ids;
	}

	public void setTag_ids(String[] tag_ids) {
		this.tag_ids = tag_ids;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public String getIsNetStaff() {
		return isNetStaff;
	}

	public void setIsNetStaff(String isNetStaff) {
		this.isNetStaff = isNetStaff;
	}
	public String getPmts_id() {
		return pmts_id;
	}
	public void setPmts_id(String pmts_id) {
		this.pmts_id = pmts_id;
	}
	public String getPmtidArray() {
		return pmtidArray;
	}
	public void setPmtidArray(String pmtidArray) {
		this.pmtidArray = pmtidArray;
	}
	public IGoodsService getGoodServices() {
		return goodServices;
	}
	public void setGoodServices(IGoodsService goodServices) {
		this.goodServices = goodServices;
	}
	




	
}