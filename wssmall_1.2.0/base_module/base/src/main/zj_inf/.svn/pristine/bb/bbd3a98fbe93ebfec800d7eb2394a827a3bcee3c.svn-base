package zte.net.ecsord.params.bss.req;

import params.ZteBusiRequest;
import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.bss.resp.NumberResourceChangePreCaptureZjResponse;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NumberResourceNewGroupOrderZjRequest  extends ZteBusiRequest {
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "N", desc = "notNeedReqStrOrderId:订单系统内部订单")
	protected String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "预占号码", type = "String", isNecessary = "Y", desc = "预占号码，非空[20]")
	protected String service_num;
	
	@ZteSoftCommentAnnotationParam(name = "客户身份证号码", type = "String", isNecessary = "N", desc = "客户身份证号码，可空[30]")
	protected String cert_num;
	
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "操作点，非空")
	protected String office_id;

	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员，非空")
	protected String operator_id;
	
	@ZteSoftCommentAnnotationParam(name = "预留字段", type = "String", isNecessary = "N", desc = "时间分钟，可空[256]")
	protected String selection_time;
	
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "证件类型，可空[256]")
    protected String cert_type;

	

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		
		return "com.zte.unicomService.bss.NumberResourceNewGroupOrderZjRequest";
	}

    public String getNotNeedReqStrOrderId() {
        return notNeedReqStrOrderId;
    }

    public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
        this.notNeedReqStrOrderId = notNeedReqStrOrderId;
    }

    public String getService_num() {
        return service_num;
    }

    public void setService_num(String service_num) {
        this.service_num = service_num;
    }

    public String getCert_num() {
        return cert_num;
    }

    public void setCert_num(String cert_num) {
        this.cert_num = cert_num;
    }

    public String getOffice_id() {
        return office_id;
    }

    public void setOffice_id(String office_id) {
        this.office_id = office_id;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getSelection_time() {
        return selection_time;
    }

    public void setSelection_time(String selection_time) {
        this.selection_time = selection_time;
    }

    public String getCert_type() {
        return cert_type;
    }

    public void setCert_type(String cert_type) {
        this.cert_type = cert_type;
    }

	
}
