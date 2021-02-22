package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;



import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Discount implements Serializable{

	@ZteSoftCommentAnnotationParam(name="优惠活动类型",type="String",isNecessary="Y",desc="DiscountType：1：总部代金券 2：省分代金券 3：折扣 注：可根据活动拓展")
	private String DiscountType;

	@ZteSoftCommentAnnotationParam(name="优惠活动编码",type="String",isNecessary="Y",desc="DiscountID：优惠活动编码")
	private String DiscountID;

	@ZteSoftCommentAnnotationParam(name="优惠活动名称",type="String",isNecessary="Y",desc="DiscountName：优惠活动名称")
	private String DiscountName;

	@ZteSoftCommentAnnotationParam(name="优惠金额",type="String",isNecessary="Y",desc="DiscountValue：单位为厘")
	private String DiscountValue;
	
	public String getDiscountType() {
		return DiscountType;
	}

	public void setDiscountType(String discountType) {
		DiscountType = discountType;
	}

	public String getDiscountID() {
		return DiscountID;
	}

	public void setDiscountID(String discountID) {
		DiscountID = discountID;
	}

	public String getDiscountName() {
		return DiscountName;
	}

	public void setDiscountName(String discountName) {
		DiscountName = discountName;
	}

	public String getDiscountValue() {
		return DiscountValue;
	}

	public void setDiscountValue(String discountValue) {
		DiscountValue = discountValue;
	}		
	
}
