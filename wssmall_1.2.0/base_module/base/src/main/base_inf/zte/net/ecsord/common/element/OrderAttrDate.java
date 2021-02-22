package zte.net.ecsord.common.element;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;

import com.ztesoft.net.framework.util.StringUtil;


/**
 * 
 * @author wu.i
 * 日期对象
 *
 */
public class OrderAttrDate  extends AbsOrderAttrElement{

	public OrderAttrDate(AttrInstLoadReq req, AttrInstLoadResp resp) {
		super(req, resp);
	}

	@Override
	public String toElement() {
		StringBuffer selectBuffer = new StringBuffer("<input class='ipt_new dateinput' ").append((!StringUtil.isEmpty(getReq().getStyle()))+"?style='"+getReq().getStyle()+"'").append(renderAttr()).append("value ='").append(StringUtil.isEmpty(getResp().getField_value())?"":getResp().getField_value()).append("' ").append(("disabled".equals(getResp().getDisabled()))?" disabled='disabled' ":" ").append(">");
		List<Map<String,String>> staticDatas = getResp().getStaticDatas();
		if(!StringUtil.isEmpty(getReq().getAppen_options())){
			selectBuffer.append(getReq().getAppen_options());
		}
		selectBuffer.append("</input>");
		return selectBuffer.toString();
	}
	
}
