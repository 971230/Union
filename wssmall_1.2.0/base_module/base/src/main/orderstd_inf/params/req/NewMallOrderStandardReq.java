package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;

public class NewMallOrderStandardReq extends ZteRequest<ZteResponse>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name="请求报文",type="String",isNecessary="N",desc="请求报文")
	private String req_content;//请求报文
	@ZteSoftCommentAnnotationParam(name="是否是异常系统调用 ",type="String",isNecessary="N",desc="是否是异常系统调用 :true是异常系统调用,false正常系统")
	private boolean is_exception=false;//是否是异常系统调用 
		@Override
	public void check() throws ApiRuleException {
//		if(StringUtils.isEmpty(this.getBase_order_id()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "订单编码不能为空"));
		if(StringUtils.isEmpty(this.getBase_co_id()))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "队列Co_id不能为空"));
	}
		
	@ZteSoftCommentAnnotationParam(name="外部单号",type="String",isNecessary="N",desc="外部单号")
	private String out_id="";//是否是异常系统调用 
	@ZteSoftCommentAnnotationParam(name = "订单来源 ", type = "String", isNecessary = "N", desc = "订单来源")
	private String  order_from;

	@Override
	public String getApiMethodName() {
		return "zte.net.orderstd.newMallOrderStandard";
	}

	public String getReq_content() {
		return req_content;
	}

	public void setReq_content(String req_content) {
		this.req_content = req_content;
	}

	public boolean isIs_exception() {
		return is_exception;
	}

	public void setIs_exception(boolean is_exception) {
		this.is_exception = is_exception;
	}

	public String getOut_id() {
		return out_id;
	}

	public void setOut_id(String out_id) {
		this.out_id = out_id;
	}

	public String getOrder_from() {
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
