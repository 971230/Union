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
public class ChildCatsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分类父ID",type="String",isNecessary="Y",desc="分类父ID")
	private String parent_id;
	@ZteSoftCommentAnnotationParam(name="搜索关键字（福建电商）",type="String",isNecessary="N",desc="search_key结构：_商家_分类_品牌_商品名称_，为like %关键字%")
	private String search_key;
	public String getSearch_key() {
		return search_key;
	}

	public void setSearch_key(String search_key) {
		this.search_key = search_key;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parentId) {
		parent_id = parentId;
	}

	@Override
	public void check() throws ApiRuleException {
		if(parent_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"parent_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodscats.childCatslist";
	}

}
