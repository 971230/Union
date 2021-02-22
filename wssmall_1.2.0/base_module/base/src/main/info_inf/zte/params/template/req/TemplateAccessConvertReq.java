package zte.params.template.req;

import java.util.Map;

import params.ZteError;
import params.ZteRequest;
import zte.params.template.resp.TemplateAccessConvertResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.Consts;
import commons.CommonNTools;


/**
 * 模板接入入参
 * @author xuefeng 
 * modify wui模版转入信息保持干净
 */
public class TemplateAccessConvertReq extends ZteRequest<TemplateAccessConvertResp>{
	@ZteSoftCommentAnnotationParam(name="外围系统 报文",type="Map<String, Object>",isNecessary="Y",desc="map结构报文")
	private Map<String, Object> inMap;
	
	/**
	 * Map结构:<类名称<字段名称,字段值>>
	 */
	@ZteSoftCommentAnnotationParam(name="业务参数map",type=" Map<String,Map<String,Object>>",isNecessary="Y",desc="业务参数map")
	private Map<String,Map<String,Object>> busiParamMap;
	
	@ZteSoftCommentAnnotationParam(name="模板编码",type="String",isNecessary="Y",desc="模板编码")
	private String tpl_code;
	
	@ZteSoftCommentAnnotationParam(name="模板版本号",type="String",isNecessary="Y",desc="模板版本号")
	private String tpl_ver;
	
	@ZteSoftCommentAnnotationParam(name="内部订单号",type="String",isNecessary="Y",desc="内部订单号")
	private String order_id;
	
	@ZteSoftCommentAnnotationParam(name="模版转换类型",type="String",isNecessary="Y",desc="in#out or out#in or in or out")
	private String tpl_inout_type;
	
	@ZteSoftCommentAnnotationParam(name="是否订单创建",type="boolean",isNecessary="Y",desc="是否订单创建 true:是  false:否")
	private boolean orderCreateFlag = false;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(order_id))
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "order_id不能为空"));
		if(null == inMap || inMap.isEmpty())
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "报文不能为空"));
		if(StringUtils.isEmpty(tpl_code))
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "模板ID不能为空"));
		if(StringUtils.isEmpty(tpl_ver))
			CommonNTools.addError(new ZteError(Consts.ERROR_FAIL, "模板版本号不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.template.tplAccessConvert";
	}

	

	public Map<String, Map<String, Object>> getBusiParamMap() {
		return busiParamMap;
	}

	public void setBusiParamMap(Map<String, Map<String, Object>> busiParamMap) {
		this.busiParamMap = busiParamMap;
	}

	public Map<String, Object> getInMap() {
		return inMap;
	}

	public void setInMap(Map<String, Object> inMap) {
		this.inMap = inMap;
	}

	public String getTpl_code() {
		return tpl_code;
	}

	public void setTpl_code(String tpl_code) {
		this.tpl_code = tpl_code;
	}

	public String getTpl_ver() {
		return tpl_ver;
	}

	public void setTpl_ver(String tpl_ver) {
		this.tpl_ver = tpl_ver;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getTpl_inout_type() {
		return tpl_inout_type;
	}

	public void setTpl_inout_type(String tpl_inout_type) {
		this.tpl_inout_type = tpl_inout_type;
	}

	public boolean getOrderCreateFlag() {
		return orderCreateFlag;
	}

	public void setOrderCreateFlag(boolean orderCreateFlag) {
		this.orderCreateFlag = orderCreateFlag;
	}
	
	
}
