/**
 * 
 */
package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * PromotionRedPkg.java
 * 2015-3-12
 * 促销活动红包
 * name="ES_PROMOTION_REDPKG", desc="红包活动列表"
 */
public class PromotionRedPkg {
	
	@ZteSoftCommentAnnotationParam(name="促销红包ID",type="long",isNecessary="Y",desc="促销红包ID")
	private String id;
	@ZteSoftCommentAnnotationParam(name="促销红包名称",type="String",isNecessary="Y",desc="促销红包名称")
	private String name;
	@ZteSoftCommentAnnotationParam(name="促销红包类型",type="String",isNecessary="Y",desc="促销红包类型")
	private String type;
	@ZteSoftCommentAnnotationParam(name="促销红包个数",type="long",isNecessary="Y",desc="促销红包类型")
	private String num;
	@ZteSoftCommentAnnotationParam(name="促销红包描述",type="String",isNecessary="Y",desc="促销红包描述")
	private String memo;
	@ZteSoftCommentAnnotationParam(name="促销红包已使用个数",type="long",isNecessary="Y",desc="促销红包已使用个数(默认0)")
	private String usednum;
	@ZteSoftCommentAnnotationParam(name="促销红包创建时间",type="String",isNecessary="Y",desc="促销红包创建时间")
	private String create_time;
	@ZteSoftCommentAnnotationParam(name="促销红包数据库来源",type="String",isNecessary="Y",desc="促销红包数据库来源")
	private String source_from;
	@ZteSoftCommentAnnotationParam(name="促销红包总金额",type="long",isNecessary="Y",desc="促销红包总金额")
	private String amount;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUsednum() {
		return usednum;
	}
	public void setUsednum(String usednum) {
		if (usednum != null && !usednum.trim().equals("")) {
			this.usednum = usednum;
		} else {
			this.usednum = "0";
		}
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
