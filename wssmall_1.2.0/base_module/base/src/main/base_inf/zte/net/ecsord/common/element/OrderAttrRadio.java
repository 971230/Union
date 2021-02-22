package zte.net.ecsord.common.element;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;

import com.ztesoft.net.framework.util.StringUtil;


/**
 * 
 * @author wu.i
 * 构造实体元素
 *
 */
public class OrderAttrRadio  extends AbsOrderAttrElement{

	public OrderAttrRadio(AttrInstLoadReq req, AttrInstLoadResp resp) {
		super(req, resp);
	}

	@Override
	public String toElement() {
		
		StringBuffer selectBuffer = new StringBuffer("<div class='checkLabel'><div class='clearfix'>");
		List<Map<String,String>> staticDatas = getResp().getStaticDatas();
		//静态数据处理
		if(staticDatas!=null && staticDatas.size()>0){
			for (Map date : staticDatas) {
				String checked = "";
				String value = (String) date.get("value");
				String value_desc = (String) date.get("value_desc");
				
				if (!StringUtil.isEmpty(getResp().getField_value()) && value !=null && value.equals(getResp().getField_value())) {
					checked = " checked ";
				}
				selectBuffer.append(checkboxHtml(value, value_desc, checked));
			}
		}
		selectBuffer.append("</div></div>");
		return selectBuffer.toString();
	}
	private String checkboxHtml(String value, String desc, String checked) {
		String disable = ("disabled".equals(getResp().getDisabled()))?" disabled='disabled' ":" ";
		return "<label> <input type='radio' value='"+value+"' "+renderAttr()+" '"+checked+"' "+disable+"/>" + desc + "</label>";
	}
	
}
