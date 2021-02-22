package zte.net.ecsord.params.bss.req;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class NumberStateChangeBss1Request extends NumberStateChangeBssRequest{

	public String getPro_key() {
		return pro_key;
	}

	public void setPro_key(String pro_key) {
		this.pro_key = pro_key;
	}

	public String getEparchy_code() {
		//这是3位的
		String city= getEssOperInfo().getCity();
		//现换成6位的
		city = CommonDataFactory.getInstance().getDictCodeValue("city", city);
		//再映射成4位的
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
		return getEssOperInfo().getEss_emp_id();
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

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	public EmpOperInfoVo getEssOperInfo() {
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}
}
