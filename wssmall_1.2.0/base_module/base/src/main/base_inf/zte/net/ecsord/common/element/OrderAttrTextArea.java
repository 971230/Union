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
public class OrderAttrTextArea  extends AbsOrderAttrElement{

	public OrderAttrTextArea(AttrInstLoadReq req, AttrInstLoadResp resp) {
		super(req, resp);
	}

	@Override
	public String toElement() {
		String rows = StringUtil.isEmpty(getReq().getRows())?"5 ": getReq().getRows();
		String cols = StringUtil.isEmpty(getReq().getCols())?"30": getReq().getCols();
		StringBuffer selectBuffer = new StringBuffer("<textarea class='ipt_new'")
				.append(" rows=").append("'").append(rows).append("'")
				.append(" cols=").append("'").append(cols).append("'")
				.append(renderAttr()).append((!StringUtil.isEmpty(getReq().getStyle()))+"?style='"+getReq().getStyle()+"'").append(("disabled".equals(getResp().getDisabled()))?" disabled='disabled' ":" ").append(">").append(StringUtil.isEmpty(getResp().getField_value())?"":getResp().getField_value())
		.append("</textarea>");
		return selectBuffer.toString();
	}
	
}
