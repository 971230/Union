package zte.params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Cat;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class CatEditReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品分类",type="Cat",isNecessary="Y",desc="cat：商品分类。")
	private Cat cat;

	public Cat getCat() {
		return cat;
	}

	public void setCat(Cat cat) {
		this.cat = cat;
	}

	@Override
	public void check() throws ApiRuleException {
		if(cat.getName() == null || "".equals(cat.getName())) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "name：分类名称不允许为空"));
		if(cat.getCat_id() == null || "".equals(cat.getCat_id())) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "cat_id：分类ID不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.cat.edit";
	}

}
