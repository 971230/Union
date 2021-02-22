package zte.params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

/**
 * @title 查询分类信息，按会员类型查询不同的分类
 * @author zou.qinghua
 *
 */
public class CatsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="会员类型ID",type="String",isNecessary="Y",desc="会员类型ID")
	private String lv_id;

	public String getLv_id() {
		return lv_id;
	}

	public void setLv_id(String lvId) {
		lv_id = lvId;
	}

	@Override
	public void check() throws ApiRuleException {
		if(lv_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"lv_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodscats.list";
	}

}
