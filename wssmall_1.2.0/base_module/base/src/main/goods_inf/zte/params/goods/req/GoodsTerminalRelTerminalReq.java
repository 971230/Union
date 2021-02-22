package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsTerminalRelTerminalReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="goods_id：查询商品绑定商品商品ID 不能为空。")
	private String goods_id ;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(goods_id==null || "".equals(goods_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "商品ID不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.terminal.to.terminal.get";
	}

}
