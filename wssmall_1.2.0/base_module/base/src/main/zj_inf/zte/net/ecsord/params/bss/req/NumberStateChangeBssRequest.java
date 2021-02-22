package zte.net.ecsord.params.bss.req;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class NumberStateChangeBssRequest extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	protected String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "预占关键字", type = "String", isNecessary = "Y", desc = "预占关键字：pro_key")
	protected String pro_key;
	
	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "eparchy_code：地市编码")
	protected String eparchy_code;
	
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "N", desc = "city_code：区县")
	protected String city_code;
	
	@ZteSoftCommentAnnotationParam(name = "工号", type = "String", isNecessary = "Y", desc = "para_code2：bss工号")
	protected String para_code2;
	
	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "phone_num：号码")
	protected String phone_num;
	
	@ZteSoftCommentAnnotationParam(name = "号码状态", type = "String", isNecessary = "Y", desc = "prepay_tag：号码状态")
	protected String prepay_tag;
	
	@ZteSoftCommentAnnotationParam(name = "状态描述", type = "String", isNecessary = "N", desc = "param_value1：状态描述")
	protected String param_value1;
	
	@ZteSoftCommentAnnotationParam(name = "是否预占", type = "String", isNecessary = "Y", desc = "x_tagchar：是否预占")
	protected String x_tagchar;
	
	@ZteSoftCommentAnnotationParam(name = "备用字段", type = "String", isNecessary = "N", desc = "param_value2：备用字段")
	protected String param_value2;
	
	@ZteSoftCommentAnnotationParam(name = "备用字段", type = "String", isNecessary = "N", desc = "param_value3：备用字段")
	protected String param_value3;
	
	@ZteSoftCommentAnnotationParam(name = "备用字段", type = "String", isNecessary = "N", desc = "param_value4：备用字段")
	protected String param_value4;
	
	protected String operFlag;
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getPro_key() {
		OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getPhoneInfoBusiRequest();
		pro_key = orderPhoneInfoBusiRequest.getProKey();
		return pro_key;
	}

	public void setPro_key(String pro_key) {
		this.pro_key = pro_key;
	}

	public String getEparchy_code() {
		String city=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		eparchy_code = CommonDataFactory.getInstance().getOtherDictVodeValue("bss_area_code", city);
		return eparchy_code;
	}

	public void setEparchy_code(String eparchy_code) {
		this.eparchy_code = eparchy_code;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getPara_code2() {
			para_code2 =  getEssOperInfo().getEss_emp_id();
		return para_code2;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public void setPara_code2(String para_code2) {
		this.para_code2 = para_code2;
	}
	
	public String getPhone_num() {
		if(phone_num==null){
			phone_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		}
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getPrepay_tag() {
		return EcsOrderConsts.AOP_SUCCESS_0000;
	}

	public void setPrepay_tag(String prepay_tag) {
		this.prepay_tag = prepay_tag;
	}

	public String getParam_value1() {
		return param_value1;
	}

	public void setParam_value1(String param_value1) {
		this.param_value1 = param_value1;
	}

	public String getX_tagchar() {
		return x_tagchar;
	}

	public void setX_tagchar(String x_tagchar) {
		this.x_tagchar = x_tagchar;
	}

	public String getParam_value2() {
		String x_tagchar = getX_tagchar();
		if(StringUtils.equals(EcsOrderConsts.BSS_OCCUPIEDFLAG_1, x_tagchar)){
			String pay_status=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_STATUS);
			if(EcsOrderConsts.AOP_PAY_STUTAS_0.equals(pay_status)){
				//未付费预占7天
				param_value2 = "10080";	
			}else if(EcsOrderConsts.AOP_PAY_STUTAS_1.equals(pay_status)){
				//已付费预定到10年
				param_value2 = "5256000";
			}	
		}else if(StringUtils.equals(EcsOrderConsts.BSS_OCCUPIEDFLAG_2, x_tagchar)){
			param_value2 = "30";
		}
		return param_value2;
	}

	public void setParam_value2(String param_value2) {
		this.param_value2 = param_value2;
	}

	public String getParam_value3() {
		return param_value3;
	}

	public void setParam_value3(String param_value3) {
		this.param_value3 = param_value3;
	}

	public String getParam_value4() {
		return param_value4;
	}

	public void setParam_value4(String param_value4) {
		this.param_value4 = param_value4;
	}

	protected EmpOperInfoVo essOperInfo;

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.bss.numberStateChangeBss";
	}
	

	public String getTime(int time){
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
 	    Date date = new Date();
   	    Calendar c = Calendar.getInstance();
   	    c.setTime(date);
   	    c.add(Calendar.MINUTE, EcsOrderConsts.AOP_OCCUPIED_TIME*time);
	    String str = sdf.format(c.getTime());
	    return str;
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
