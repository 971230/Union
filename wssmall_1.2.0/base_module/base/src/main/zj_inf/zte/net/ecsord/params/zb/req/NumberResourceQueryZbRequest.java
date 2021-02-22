package zte.net.ecsord.params.zb.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.Message;
import zte.net.ecsord.params.zb.vo.ParaZb;
import zte.net.ecsord.params.zb.vo.QueryParasZb;

public class NumberResourceQueryZbRequest extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="ess工号")
	private String operatorId;
	
	@ZteSoftCommentAnnotationParam(name="省份",type="String",isNecessary="Y",desc="ess工号的省市区")
	private String province;	
	
	@ZteSoftCommentAnnotationParam(name="地市",type="String",isNecessary="Y",desc="地市")
	private String city;
	
	@ZteSoftCommentAnnotationParam(name="区县",type="String",isNecessary="Y",desc="区县")
	private String district;
	
	@ZteSoftCommentAnnotationParam(name="渠道编码",type="String",isNecessary="Y",desc="渠道编码")
	private String channelId;
	
	@ZteSoftCommentAnnotationParam(name="渠道类型",type="String",isNecessary="Y",desc="渠道类型")
	private String channelType;
	
	@ZteSoftCommentAnnotationParam(name="受理类型",type="number",isNecessary="Y",desc="受理类型 1：后付费  2：预付费")
	private String serType;
	
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="N",desc="业务类型，不传默认都为手机类 1：手机类 2：上网卡类 分两类：14段和非14段")
	private String busType;
	
	@ZteSoftCommentAnnotationParam(name="是否集团号码标记",type="String",isNecessary="N",desc="是否集团号码标记，不传按员工权限查询号码数据")
	private String groupFlag;
	
	@ZteSoftCommentAnnotationParam(name="是否3G",type="String",isNecessary="N",desc="是否3G")
	private String is3G;
	
	@ZteSoftCommentAnnotationParam(name="选号参数",type="String",isNecessary="Y",desc="选号参数")
	private List<QueryParasZb> queryParas;
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="Y",desc="保留字段")
	private ParaZb para;

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	
	
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getIs3G() {
		return is3G;
	}

	public void setIs3G(String is3g) {
		is3G = is3g;
	}

	public List<QueryParasZb> getQueryParas() {
		
		//设置查询参数默认值，测试用
		
		
		
		
		
		return queryParas;
	}

	public void setQueryParas(List<QueryParasZb> queryParas) {
		this.queryParas = queryParas;
	}

	public ParaZb getPara() {
		
		//设置保留参数默认值，测试用
		
		
		
		return para;
	}

	public void setPara(ParaZb para) {
		this.para = para;
	}

	

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub	
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.numberResourceQueryZb";
	}

	


	
	
}
