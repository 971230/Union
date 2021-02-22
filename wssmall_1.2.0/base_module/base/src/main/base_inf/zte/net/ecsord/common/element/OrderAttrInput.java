package zte.net.ecsord.common.element;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;

import com.ztesoft.net.framework.util.StringUtil;


/**
 * 
 * @author wu.i
 * 文本框
 *
 */
public class OrderAttrInput  extends AbsOrderAttrElement{

	public OrderAttrInput(AttrInstLoadReq req, AttrInstLoadResp resp) {
		super(req, resp);
	}

	@Override
	public String toElement() {
		//String field_name =getResp().getField_name();
		//String field_value=SensitiveInfoUtils.desensitization(field_name, getResp().getField_value()); = 
		String field_value = getResp().getField_value();
		StringBuffer selectBuffer = new StringBuffer("<input class='ipt_new'").append((!StringUtil.isEmpty(getReq().getStyle()))+"?style='"+getReq().getStyle()+"'").append(renderAttr()).append("value ='").append(StringUtil.isEmpty(field_value)?"":field_value).append("' ").append(("disabled".equals(getResp().getDisabled()))?" disabled='disabled' ":" ").append(">")
		.append("</input>");
		return selectBuffer.toString();
	}
	
}
