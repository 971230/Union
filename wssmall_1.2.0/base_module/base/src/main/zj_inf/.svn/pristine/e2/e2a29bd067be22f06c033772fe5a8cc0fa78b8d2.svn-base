package zte.net.ecsord.params.bss.req;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * BSS激活请求
 * @author duan.shaochu
 *
 */
public class BSSActivateOperReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "沃云购流水", type = "String", isNecessary = "Y", desc = "沃云购流水")
	private String Subscribe_Id;
	@ZteSoftCommentAnnotationParam(name = "用户号码", type = "String", isNecessary = "Y", desc = "用户号码")
	private String Serial_Number;
	@ZteSoftCommentAnnotationParam(name = "激活时间yyyy-mm-dd hh24:mi:ss", type = "String", isNecessary = "Y", desc = "激活时间yyyy-mm-dd hh24:mi:ss")
	private String Accept_Date;
	@ZteSoftCommentAnnotationParam(name = "号码所在地市", type = "String", isNecessary = "Y", desc = "号码所在地市")
	private String Eparchy_Code;
	@ZteSoftCommentAnnotationParam(name = "部门", type = "String", isNecessary = "Y", desc = "部门")
	private String Trade_Depart_Id;
	@ZteSoftCommentAnnotationParam(name = "工号", type = "String", isNecessary = "Y", desc = "工号")
	private String Trade_Staff_Id;
	@ZteSoftCommentAnnotationParam(name = "备用1", type = "String", isNecessary = "Y", desc = "备用1")
	private String Rsrv_Str1;
	@ZteSoftCommentAnnotationParam(name = "备用2", type = "String", isNecessary = "Y", desc = "备用2")
	private String Rsrv_Str2;
	@ZteSoftCommentAnnotationParam(name = "备用3", type = "String", isNecessary = "Y", desc = "备用3")
	private String Rsrv_Str3;
	@ZteSoftCommentAnnotationParam(name = "备用4", type = "String", isNecessary = "Y", desc = "备用4")
	private String Rsrv_Str4;
	@ZteSoftCommentAnnotationParam(name = "备用5", type = "String", isNecessary = "Y", desc = "备用5")
	private String Rsrv_Str5;
	
	private EmpOperInfoVo essOperInfo;


	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return EcsOrderConsts.BSS_ORDER_ACTIVE;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSubscribe_Id() {
		Subscribe_Id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_INF_ID);
		if (StringUtils.isBlank(Subscribe_Id)) return null;
		return Subscribe_Id;
	}

	public void setSubscribe_Id(String subscribe_Id) {
		Subscribe_Id = subscribe_Id;
	}

	public String getSerial_Number() {
		return  CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setSerial_Number(String serial_Number) {
		Serial_Number = serial_Number;
	}

	public String getAccept_Date() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	public void setAccept_Date(String accept_Date) {
		Accept_Date = accept_Date;
	}

	public String getEparchy_Code() {
		return getEssOperInfo().getCity();
	}

	public void setEparchy_Code(String eparchy_Code) {
		Eparchy_Code = eparchy_Code;
	}

	public String getTrade_Depart_Id() {
		return getEssOperInfo().getChannel_id();
	}

	public void setTrade_Depart_Id(String trade_Depart_Id) {
		Trade_Depart_Id = trade_Depart_Id;
	}

	public String getTrade_Staff_Id() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setTrade_Staff_Id(String trade_Staff_Id) {
		Trade_Staff_Id = trade_Staff_Id;
	}

	public String getRsrv_Str1() {
		return Rsrv_Str1;
	}

	public void setRsrv_Str1(String rsrv_Str1) {
		Rsrv_Str1 = rsrv_Str1;
	}

	public String getRsrv_Str2() {
		return Rsrv_Str2;
	}

	public void setRsrv_Str2(String rsrv_Str2) {
		Rsrv_Str2 = rsrv_Str2;
	}

	public String getRsrv_Str3() {
		return Rsrv_Str3;
	}

	public void setRsrv_Str3(String rsrv_Str3) {
		Rsrv_Str3 = rsrv_Str3;
	}

	public String getRsrv_Str4() {
		return Rsrv_Str4;
	}

	public void setRsrv_Str4(String rsrv_Str4) {
		Rsrv_Str4 = rsrv_Str4;
	}

	public String getRsrv_Str5() {
		return Rsrv_Str5;
	}

	public void setRsrv_Str5(String rsrv_Str5) {
		Rsrv_Str5 = rsrv_Str5;
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
