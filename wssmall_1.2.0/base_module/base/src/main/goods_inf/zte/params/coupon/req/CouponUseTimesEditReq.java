package zte.params.coupon.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class CouponUseTimesEditReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="优惠代码",type="String",isNecessary="Y",desc="memc_code：优惠代码")	
	private String memc_code;
	@ZteSoftCommentAnnotationParam(name="会员等级ID",type="String",isNecessary="Y",desc="member_id：会员等级ID")	
	private String member_id;
	@ZteSoftCommentAnnotationParam(name="使用次数",type="String",isNecessary="Y",desc="addUseTimes：使用次数")	
	private int addUseTimes;
	
	public String getMemc_code() {
		return memc_code;
	}

	public void setMemc_code(String memc_code) {
		this.memc_code = memc_code;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getAddUseTimes() {
		return addUseTimes;
	}

	public void setAddUseTimes(int addUseTimes) {
		this.addUseTimes = addUseTimes;
	}

	@Override
	public void check() throws ApiRuleException {
		if(memc_code==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"memc_code不能为空"));
		if(member_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"member_id不能为空"));
		if(addUseTimes<=0) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"addUseTimes不能为小于等于0"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.coupon.usetimes.edit";
	}

}
