/**
 * 
 */
package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * PromotionRedPkgDetail.java
 * 2015-3-12
 * 优惠券编码
 */
@ZteSoftCommentAnnotationParam(name="ES_PROMOTION_REDPKG_DETAIL",type="String",isNecessary="Y",desc="红包优惠券编码")
public class PromotionRedPkgDetail {
	
	@ZteSoftCommentAnnotationParam(name="促销红包明细ID",type="String",isNecessary="Y",desc="促销红包明细ID")
	private String id;
	@ZteSoftCommentAnnotationParam(name="促销红包优惠券ID",type="String",isNecessary="Y",desc="ES_COUPONS表CPNS_ID字段")
	private String cpns_id;
	@ZteSoftCommentAnnotationParam(name="促销红包优惠券创建明细时间",type="String",isNecessary="Y",desc="促销红包优惠券创建明细时间")
	private String create_time;
	@ZteSoftCommentAnnotationParam(name="促销红包优惠券修改明细时间",type="String",isNecessary="Y",desc="促销红包优惠券修改明细时间")
	private String update_time;
	@ZteSoftCommentAnnotationParam(name="促销红包优惠券修改明细状态",type="String",isNecessary="Y",desc="0生成、1已发放、2已使用")
	private String state;
	@ZteSoftCommentAnnotationParam(name="促销红包数据库来源",type="String",isNecessary="Y",desc="促销红包数据库来源")
	private String source_from;
	@ZteSoftCommentAnnotationParam(name="促销红包优惠券编码",type="String",isNecessary="Y",desc="ES_MEMBER_COUPON表MEMC_CODE字段")
	private String cpns_code;
	@ZteSoftCommentAnnotationParam(name="促销红包ID",type="String",isNecessary="Y",desc="促销红包ID")
	private String redpkgid;
	
	private String uname; // 会员名称
	
	@NotDbField
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCpns_id() {
		return cpns_id;
	}
	public void setCpns_id(String cpns_id) {
		this.cpns_id = cpns_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getCpns_code() {
		return cpns_code;
	}
	public void setCpns_code(String cpns_code) {
		this.cpns_code = cpns_code;
	}
	public String getRedpkgid() {
		return redpkgid;
	}
	public void setRedpkgid(String redpkgid) {
		this.redpkgid = redpkgid;
	}
	
}
