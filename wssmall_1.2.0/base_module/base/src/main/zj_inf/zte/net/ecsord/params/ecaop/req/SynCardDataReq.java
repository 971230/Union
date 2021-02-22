package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 
 * 获取白卡数据
 *
 */
public class SynCardDataReq extends ZteRequest {


	@ZteSoftCommentAnnotationParam(name="预占号码",type="String",isNecessary="Y",desc="service_num：预占号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name="地区编码",type="String",isNecessary="Y",desc="region_id：地区编码")
	private String region_id;
	@ZteSoftCommentAnnotationParam(name="写卡状态",type="String",isNecessary="Y",desc="write_status：写卡状态，0:写卡成功，未正式占用1:写卡失败9:写卡成功，正式占用")
	private String write_status;
	@ZteSoftCommentAnnotationParam(name="白卡卡号",type="String",isNecessary="Y",desc="iccid：白卡卡号")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name="写卡结果",type="String",isNecessary="Y",desc="reason_id：0：写卡成功非0则由读卡器返回的错误代码或系统错误原因")
	private String reason_id;
	@ZteSoftCommentAnnotationParam(name="写卡结果",type="String",isNecessary="Y",desc="imsi：IMSI值")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name="操作点",type="String",isNecessary="Y",desc="proc_id：发起方业务流水")
	private String proc_id;
	@ZteSoftCommentAnnotationParam(name="操作点",type="String",isNecessary="Y",desc="office_id：操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name="操作员",type="String",isNecessary="Y",desc="operator_id：操作员")
	private String operator_id;
	


	
	@Override
	public String getApiMethodName() {
		return "ecaop.trades.serv.newu.opencarddata.syn";
	}

	@Override
	public void check() throws ApiRuleException {
		
	}


	public String getService_num() {
		return service_num;
	}




	public void setService_num(String service_num) {
		this.service_num = service_num;
	}




	public String getRegion_id() {
		return region_id;
	}




	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}




	public String getWrite_status() {
		return write_status;
	}




	public void setWrite_status(String write_status) {
		this.write_status = write_status;
	}




	public String getIccid() {
		return iccid;
	}




	public void setIccid(String iccid) {
		this.iccid = iccid;
	}




	public String getReason_id() {
		return reason_id;
	}




	public void setReason_id(String reason_id) {
		this.reason_id = reason_id;
	}




	public String getImsi() {
		return imsi;
	}




	public void setImsi(String imsi) {
		this.imsi = imsi;
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

	public String getProc_id() {
		return proc_id;
	}

	public void setProc_id(String proc_id) {
		this.proc_id = proc_id;
	}

	
	
}
