package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.PromotionAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author 黄记新
 * 打折请求参数。
 */
public class PromotionAddReq extends ZteRequest<PromotionAddResp> {
	
	@ZteSoftCommentAnnotationParam(name = "用户ID", type = "String", isNecessary = "Y", desc = "用户ID")
	private String userId;
	
	@ZteSoftCommentAnnotationParam(name = "活动名称", type = "String", isNecessary = "Y", desc = "活动名称")
	private String name;
	
	@ZteSoftCommentAnnotationParam(name = "活动类型", type = "String", isNecessary = "Y", desc = "活动类型:001	打折;002	积分翻倍;003	满赠;004	满减;005	免运费;006	直降;007	预售;008	团购;009	秒杀;010	促销")
	private String promotion_type="006";
	
	@ZteSoftCommentAnnotationParam(name = "活动期间是否允许使用优惠券", type = "String", isNecessary = "Y", desc = "活动期间是否允许使用优惠券:0_否；1_是")
	private String ifcoupon = "0";
	
	@ZteSoftCommentAnnotationParam(name = "活动图片", type = "String", isNecessary = "Y", desc = "活动图片")
	private String imageUrl;
	
	@ZteSoftCommentAnnotationParam(name = "规则描述", type = "String", isNecessary = "Y", desc = "规则描述")
	private String describe;
	
	@ZteSoftCommentAnnotationParam(name = "活动起止时间", type = "String", isNecessary = "Y", desc = "活动起止时间")
	private String begin_time;
	
	@ZteSoftCommentAnnotationParam(name = "活动截止时间", type = "String", isNecessary = "Y", desc = "活动截止时间")
	private String end_time;
	
	@ZteSoftCommentAnnotationParam(name = "打折折扣9则输入0.9", type = "String", isNecessary = "Y", desc = "打折折扣，9则输入0.9")
	private String discount;
	
	@ZteSoftCommentAnnotationParam(name = "促销类型", type = "String", isNecessary = "Y", desc = "1_优惠券;0_促销活动")
	private String pmt_type="1";
	
	@ZteSoftCommentAnnotationParam(name = "会员列表", type = "String", isNecessary = "Y", desc = "会员列表：用“,”分割；全部传“-1”")
	private String lvIds="0,1,2";
	
	@ZteSoftCommentAnnotationParam(name = "是否限购", type = "String", isNecessary = "Y", desc = "是否限购：为0不限购，为1就是限购")
	private String if_limit;
	
	@ZteSoftCommentAnnotationParam(name = "限购数量", type = "String", isNecessary = "Y", desc = "限购数量")
	private String limit_num;
	
	@ZteSoftCommentAnnotationParam(name = "优惠商品列表", type = "String", isNecessary = "Y", desc = "优惠商品列表：goods_id用“,”分割；全部传“-1”")
	private String goodsIds;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.promotion.add";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPromotion_type() {
		return promotion_type;
	}

	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}

	public String getIfcoupon() {
		return ifcoupon;
	}

	public void setIfcoupon(String ifcoupon) {
		this.ifcoupon = ifcoupon;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getLvIds() {
		return lvIds;
	}

	public void setLvIds(String lvIds) {
		this.lvIds = lvIds;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}

	/**
	 * @return the if_limit
	 */
	public String getIf_limit() {
		return if_limit;
	}

	/**
	 * @param if_limit the if_limit to set
	 */
	public void setIf_limit(String if_limit) {
		this.if_limit = if_limit;
	}

	/**
	 * @return the limit_num
	 */
	public String getLimit_num() {
		return limit_num;
	}

	/**
	 * @param limit_num the limit_num to set
	 */
	public void setLimit_num(String limit_num) {
		this.limit_num = limit_num;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
