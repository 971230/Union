package zte.net.ecsord.common.element;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;

import com.ztesoft.net.framework.util.StringUtil;


/**
 * 
 * @author wu.i
 * 文本对象
 *
 */
public class OrderAttrText  extends AbsOrderAttrElement{

	public OrderAttrText(AttrInstLoadReq req, AttrInstLoadResp resp) {
		super(req, resp);
	}

	@Override
	public String toElement() {
		/*StringBuffer selectBuffer = new StringBuffer("<span").append(renderAttr()).append(">");
		List<Map<String,String>> staticDatas = getResp().getStaticDatas();
		
		selectBuffer.append("</span>");*/
		//return selectBuffer.toString();
		//String field_name =getResp().getField_name();
		//String value=SensitiveInfoUtils.desensitization(field_name, getResp().getField_value());
		String value = getResp().getField_value();
		if(StringUtil.isEmpty(value))value="";
		return value;
	}
	
}
