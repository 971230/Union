package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.PromotionResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author 黄记新
 * 打折请求参数。
 */
public class PromotionReq extends ZteRequest<PromotionResp> {
//	@ZteSoftCommentAnnotationParam(name = "打折实体对象", type = "Promotion", isNecessary = "Y", desc = "打折实体对象")
//	private Promotion promotion;
//	
//	@ZteSoftCommentAnnotationParam(name = "促销活动实体对象", type = "PromotionActivity", isNecessary = "Y", desc = "促销活动实体对象")
//	private PromotionActivity activity;


	@ZteSoftCommentAnnotationParam(name = "会员ID", type = "String", isNecessary = "Y", desc = "会员ID")
	private String userid;
	
	@ZteSoftCommentAnnotationParam(name = "活动名称", type = "String", isNecessary = "Y", desc = "活动名称")
	private String name;
	
	@ZteSoftCommentAnnotationParam(name = "活动开始时间", type = "String", isNecessary = "Y", desc = "活动开始时间")
	private String begin_time;
	
	@ZteSoftCommentAnnotationParam(name = "活动结束时间", type = "String", isNecessary = "Y", desc = "活动结束时间")
	private String end_time;
	
	@ZteSoftCommentAnnotationParam(name = "活动类型，打折为‘006’，默认为‘006’", type = "String", isNecessary = "Y", desc = "活动类型，打折为‘006’，默认为‘006’")
	private String promotion_type = "006";
	
	@ZteSoftCommentAnnotationParam(name = "分销商id", type = "String", isNecessary = "N", desc = "分销商id")
	private String partnerid;
	
	@ZteSoftCommentAnnotationParam(name = "商品id", type = "String", isNecessary = "Y", desc = "商品id")
	private String goodsid;
	
	@ZteSoftCommentAnnotationParam(name = "是否可用，1为可用，0为不可用，默认-1则不进行过滤", type = "Integer", isNecessary = "Y", desc = "是否可用")
	private int enable = -1;

	@ZteSoftCommentAnnotationParam(name = "第几页", type = "Integer", isNecessary = "Y", desc = "商品id")
	private int pageNo = 1;
	
	

	@ZteSoftCommentAnnotationParam(name = "分页条目", type = "Integer", isNecessary = "Y", desc = "商品id")
	private int pageSize = 10;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.promotion.list";
	}

//	public Promotion getPromotion() {
//		return promotion;
//	}
//
//	public void setPromotion(Promotion promotion) {
//		this.promotion = promotion;
//	}
//
//	public PromotionActivity getActivity() {
//		return activity;
//	}
//
//	public void setActivity(PromotionActivity activity) {
//		this.activity = activity;
//	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPromotion_type() {
		return promotion_type;
	}

	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}
	
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the partnerid
	 */
	public String getPartnerid() {
		return partnerid;
	}

	/**
	 * @param partnerid the partnerid to set
	 */
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
}
