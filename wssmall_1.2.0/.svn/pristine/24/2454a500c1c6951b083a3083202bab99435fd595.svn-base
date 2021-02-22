package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsServReq extends ZteRequest  {
	
	@ZteSoftCommentAnnotationParam(name="服务id",type="ES_GOODS_STYPE",isNecessary="Y",desc="表ES_GOODS_STYPE:服务类型，如5——终端，6——套餐，7——合约机", hasChild=true)
	private String style_id;
	
	@ZteSoftCommentAnnotationParam(name="第几页",type="String",isNecessary="Y",desc="第几页  默认1")
	private String page_index ="1";
	
	@ZteSoftCommentAnnotationParam(name="每页记录数",type="String",isNecessary="Y",desc="每页记录数：默认50")
	private String page_size ="50";
	
	public String getStyle_id() {
		return style_id;
	}

	public void setStyle_id(String style_id) {
		this.style_id = style_id;
	}

	public String getPage_index() {
		return page_index;
	}

	public void setPage_index(String page_index) {
		this.page_index = page_index;
	}

	public String getPage_size() {
		return page_size;
	}

	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(style_id==null || "".equals(style_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "服务类型不允许为空"));
		if(page_index==null || "".equals(page_index))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "第几页不允许为空"));
		if(page_size==null || "".equals(page_size))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "每页记录数不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.serv.get";
	}

}
