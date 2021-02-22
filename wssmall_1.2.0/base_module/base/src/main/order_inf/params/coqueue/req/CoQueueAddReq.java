package params.coqueue.req;

import params.ZteError;
import params.ZteRequest;
import params.coqueue.resp.CoQueueAddResp;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

public class CoQueueAddReq extends ZteRequest<CoQueueAddResp> {

	@ZteSoftCommentAnnotationParam(name="消息队列名称",type="String",isNecessary="Y",desc="消息队列名称")
	private String co_name;
	
	@ZteSoftCommentAnnotationParam(name="批次号",type="String",isNecessary="Y",desc="批次号")
	private String batch_id;
	
	@ZteSoftCommentAnnotationParam(name="业务编码",type="String",isNecessary="Y",
			desc="CO_DINGDAN-订单同步；CO_HUOPIN-货品同步；CO_SHANGPIN-商品同步；CO_HAOMA-号码同步"
			   + "CO_FENLEI-分类同步；CO_LEIXING-类型同步；CO_PINPAI-品牌同步；CO_XINGHAO-型号同步；"
			   + "CO_HUOPIN_KUCUN-货品库存同步；CO_SHANGPIN_KUCUN-商品库存同步；CO_HUODONG-活动同步")
	private String service_code;
	
	@ZteSoftCommentAnnotationParam(name="动作编码", type="String", isNecessary="Y", 
			desc="A-新增；M-修改；D-删除或停用")
	private String action_code;
	
	@ZteSoftCommentAnnotationParam(name="对象类型", type="String", isNecessary="Y",
			desc="DINGDAN-订单；HUOPIN-货品；SHANGPIN-商品；HAOMA-号码；FENLEI-分类;PINPAI-品牌；XINGHAO-型号;KUCUN-库存")
	private String object_type;
	
	@ZteSoftCommentAnnotationParam(name="对象标识", type="String", isNecessary="Y", desc="关系表的主键")
	private String object_id;
	
	@ZteSoftCommentAnnotationParam(name="同步的数据内容",type="String",isNecessary="N",desc="同步的数据内容(json格式的字符串)")
	private String contents;
	
	@ZteSoftCommentAnnotationParam(name="工号",type="String",isNecessary="N",desc="工号")
	private String oper_id;
	
	@ZteSoftCommentAnnotationParam(name="发往的组织标识串用逗号,隔开",type="String",isNecessary="N",desc="发往的组织标识串用逗号,隔开")
	private String org_id_str;
	
	@ZteSoftCommentAnnotationParam(name="发往的平台组织标识",type="String",isNecessary="N",desc="发往的平台组织标识")
	private String org_id_belong;
	
	@ZteSoftCommentAnnotationParam(name="请求URL地址",type="String",isNecessary="N",desc="请求URL地址")
	private String url;
	@ZteSoftCommentAnnotationParam(name="系统编码",type="String",isNecessary="N",desc="系统编码[old老系统new新系统]")
	private String sys_code;
	
	@ZteSoftCommentAnnotationParam(name="发送方式",type="String",isNecessary="N",desc="发送方式")
	private String sending_type;
	
	public String getCo_name() {
		return co_name;
	}

	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}

	public String getObject_type() {
		return object_type;
	}

	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}

	public String getObject_id() {
		return object_id;
	}

	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	
	public String getOrg_id_str() {
		return org_id_str;
	}

	public void setOrg_id_str(String org_id_str) {
		this.org_id_str = org_id_str;
	}

	public String getOrg_id_belong() {
		return org_id_belong;
	}

	public void setOrg_id_belong(String org_id_belong) {
		this.org_id_belong = org_id_belong;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void check() throws ApiRuleException {
		
		if (StringUtils.isEmpty(batch_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "批次号【batch_id】不能为空！"));
        }
		if (StringUtils.isEmpty(service_code)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "服务编码【service_code】不能为空！"));
        }
		if (StringUtils.isEmpty(action_code)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "动作编码【action_code】不能为空！"));
        }
		if (StringUtils.isEmpty(object_type)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "对象类型【object_type】不能为空！"));
        }
		if (StringUtils.isEmpty(object_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "对象标识【object_id】不能为空！"));
        }
		
	}
	
	@Override
	public String getApiMethodName() {
		return "zte.service.coqueue.add";
	}

	public String getSys_code() {
		return sys_code;
	}

	public void setSys_code(String sys_code) {
		this.sys_code = sys_code;
	}

	public String getSending_type() {
		return sending_type;
	}

	public void setSending_type(String sending_type) {
		this.sending_type = sending_type;
	}
	
}
