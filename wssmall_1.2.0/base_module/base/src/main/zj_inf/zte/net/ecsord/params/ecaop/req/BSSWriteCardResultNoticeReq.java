package zte.net.ecsord.params.ecaop.req;

import java.text.SimpleDateFormat;
import java.util.Date;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @see 写卡结果通知
 */
@SuppressWarnings("rawtypes")
public class BSSWriteCardResultNoticeReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7271931385797407809L;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "服务号码", type = "String", isNecessary = "Y", desc = "服务号码")
	private String UserNum; // Y 服务号码
	@ZteSoftCommentAnnotationParam(name = "Iccid", type = "String", isNecessary = "Y", desc = "Iccid")
	private String Iccid; // Y 
	@ZteSoftCommentAnnotationParam(name = "Imsi", type = "String", isNecessary = "N", desc = "Imsi")
	private String Imsi; // Y 
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "N", desc = "地市")
	private String EparchyCode; // Y 	
	@ZteSoftCommentAnnotationParam(name = "UpdateTime", type = "String", isNecessary = "N", desc = "UpdateTime")
	private String UpdateTime; // Y 
	@ZteSoftCommentAnnotationParam(name = "流水", type = "String", isNecessary = "Y", desc = "流水")
	private String ProcId2; // 流水
	@ZteSoftCommentAnnotationParam(name = "写卡结果", type = "String", isNecessary = "N", desc = "写卡结果")
	private String OperRst; // 写卡结果0：写卡成功	非0则	由读卡器返回的错误代码
	@ZteSoftCommentAnnotationParam(name = "错误说明	", type = "String", isNecessary = "N", desc = "错误说明	")
	private String OperComm; // 错误说明	由写卡器返回的错误说明，如果ReasonID为非0，则必填
	@ZteSoftCommentAnnotationParam(name = "工号", type = "String", isNecessary = "Y", desc = "工号")
	private String StaffId; // 工号
	@ZteSoftCommentAnnotationParam(name = "部门", type = "String", isNecessary = "N", desc = "部门")
	private String DepartId; // 部门
	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "String", isNecessary = "N", desc = "扩展字段")
	private String Para;

	private EmpOperInfoVo essOperInfo;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getUserNum() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setUserNum(String userNum) {
		UserNum = userNum;
	}

	public String getIccid() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
	}

	public void setIccid(String iccid) {
		Iccid = iccid;
	}

	public String getImsi() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIMID);
	}

	public void setImsi(String imsi) {
		Imsi = imsi;
	}

	public String getEparchyCode() {
		return getEssOperInfo().getCity();
	}

	public void setEparchyCode(String eparchyCode) {
		EparchyCode = eparchyCode;
	}

	public String getUpdateTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}

	public void setUpdateTime(String updateTime) {
		UpdateTime = updateTime;
	}

	public String getProcId2() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PROC_ID);
	}

	public void setProcId2(String procId2) {
		ProcId2 = procId2;
	}

	public String getOperRst() {
		return "0";
	}

	public void setOperRst(String operRst) {
		OperRst = operRst;
	}

	public String getOperComm() {
		return OperComm;
	}

	public void setOperComm(String operComm) {
		OperComm = operComm;
	}

	public String getStaffId() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setStaffId(String staffId) {
		StaffId = staffId;
	}

	public String getDepartId() {
		return getEssOperInfo().getDep_id();
	}

	public void setDepartId(String departId) {
		DepartId = departId;
	}

	public String getPara() {
		return Para;
	}

	public void setPara(String para) {
		Para = para;
	}

	@NotDbField
	public void check() throws ApiRuleException {

	}

	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.writeCardResultNotice.bss";
	}


	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}
}
