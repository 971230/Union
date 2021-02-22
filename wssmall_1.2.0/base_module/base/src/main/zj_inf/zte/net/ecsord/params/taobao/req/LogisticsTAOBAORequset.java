package zte.net.ecsord.params.taobao.req;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.taobao.vo.LogisticsTAOBAOOnline;
import zte.net.ecsord.params.taobao.vo.LogisticsTAOBAOVirtual;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;


/**
 * 
 * @author sguo 
 * 发货通知接口
 * 
 */
public class LogisticsTAOBAORequset extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="发货标识",type="String",isNecessary="Y",desc="在线：EcsOrderConsts.TB_SHIPPING_MARK_ONLINE 虚拟：EcsOrderConsts.TB_SHIPPING_MARK_VIRTUAL")
	private String mark;

	@ZteSoftCommentAnnotationParam(name="在线订单发货",type="String",isNecessary="Y",desc="online：在线订单发货")
	private LogisticsTAOBAOOnline online;

	@ZteSoftCommentAnnotationParam(name="虚拟发货结构",type="String",isNecessary="Y",desc="virtual：虚拟发货")
	private LogisticsTAOBAOVirtual virtual;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public LogisticsTAOBAOOnline getOnline() {
		//online = new LogisticsTAOBAOOnline();
		//外部订单号
		String out_tid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
		online.setTid(out_tid);
		//online.setFeature(CommonDataFactory.getInstance().getFeatureFromTaobao(notNeedReqStrOrderId, out_tid));
		return online;
	}

	public void setOnline(LogisticsTAOBAOOnline online) {
		this.online = online;
	}

	public LogisticsTAOBAOVirtual getVirtual() {
		//外部订单号
		String out_tid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
		//运单号
		String logi_no = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.LOGI_NO);
		//物流公司编码
		String company_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
		company_code = CommonDataFactory.getInstance().getLogiCompanyCode(company_code);
		Pattern pmobile = Pattern.compile("^POST|EMS|STO|YTO|YUNDA|ZJS|BEST|FEDEX|DBL|SHQ|ZTO|CCES|HTKY|TTKDEX|SF|AIRFEX|APEX|CYEXP|DTW|YUD|STARS|ANTO|CRE|EBON|HZABC|DFH|SY|YC|ZY|YCT|XB|NEDA|XFHONG|LB|WLB-ABC|WLB-SAD|WLB-STARS|FAST|UC|QRT|LTS|QFKD|UNIPS|UAPEX");
		Matcher m1 = pmobile.matcher(company_code);
		if (!m1.find()) {
			company_code = EcsOrderConsts.TAOBAO_OTHER_COMPANY_CODE;
		}
		virtual = new LogisticsTAOBAOVirtual();
		virtual.setTid(out_tid);
		virtual.setOut_sid(logi_no);
		virtual.setCompany_code(company_code);
		virtual.setSeller_ip("");
		//virtual.setFeature(CommonDataFactory.getInstance().getFeatureFromTaobao(notNeedReqStrOrderId, out_tid));
		return virtual;
	}

	public void setVirtual(LogisticsTAOBAOVirtual virtual) {
		this.virtual = virtual;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.taobao.logistics";
	}

}
