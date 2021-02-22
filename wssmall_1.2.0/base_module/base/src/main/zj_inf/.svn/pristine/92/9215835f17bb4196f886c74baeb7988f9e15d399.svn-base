package zte.net.ecsord.params.wyg.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.zb.vo.Para;
import zte.net.ecsord.params.zb.vo.QueryParas;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NumberResourceQueryWYGRequest extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;
	
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;
	
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型")
	private String channelType;
	
	@ZteSoftCommentAnnotationParam(name = "是否3G", type = "String", isNecessary = "N", desc = "is3G：是否3G")
	private String is3G;
	
	@ZteSoftCommentAnnotationParam(name = "受理类型", type = "String", isNecessary = "Y", desc = "serType：受理类型")
	private String serType;
	
	@ZteSoftCommentAnnotationParam(name = "网别", type = "String", isNecessary = "Y", desc = "segmentCode：号段网别")
	private String segmentCode;
	
	@ZteSoftCommentAnnotationParam(name = "其他查询条件", type = "List", isNecessary = "N", desc = "queryParas：其他查询条件")
	private List<QueryParas> queryParas;
	
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "Para", isNecessary = "Y", desc = "Para：保留字段")
	private Para para;
	
	private EmpOperInfoVo essOperInfo;
		
	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getChannelId() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getSerType() {
		serType=CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
		if("BAK".equals(serType)){
			serType="2";
		}else if("PRE".equals(serType)){
			serType="1";
		}
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public List<QueryParas> getQueryParas() {
		return queryParas;
	}

	public void setQueryParas(List<QueryParas> queryParas) {
		this.queryParas = queryParas;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getIs3G() {
		String net_type=CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if("2G".equals(net_type)){
			is3G="0";
		}else if("3G".equals(net_type)||"4G".equals(net_type)){
			is3G="1";
		}else{
			is3G=" ";
		}
		return is3G;
	}

	public void setIs3G(String is3g) {
		is3G = is3g;
	}

	public String getSegmentCode() {
		return segmentCode;
	}

	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}
	

	

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	
	

	public Para getPara() {
		Para para1=new Para();
		para1.setParaID(" ");
		para1.setParaValue(" ");
		para=para1;
		return para;
	}

	public void setPara(Para para) {
		this.para = para;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.wyg..numberResourceQueryWYG";
	}

}
