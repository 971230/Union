package zte.net.ecsord.common.element;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;

import com.ztesoft.net.framework.util.StringUtil;

/**
 * 
 * @author wu.i
 * 下拉框
 *
 */
public class OrderAttrSelect  extends AbsOrderAttrElement{
	
	public OrderAttrSelect(AttrInstLoadReq req, AttrInstLoadResp resp) {
		super(req, resp);
	}
	@Override
	public String toElement() {
		
		StringBuffer selectBuffer = new StringBuffer("<select class='ipt_new' ").append((!StringUtil.isEmpty(getReq().getStyle()))+"?style='"+getReq().getStyle()+"'").append(renderAttr()).append(("disabled".equals(getResp().getDisabled()))?" disabled='disabled' ":" ").append(">");
		List<Map<String,String>> staticDatas = getResp().getStaticDatas();
		if(!StringUtil.isEmpty(getReq().getAppen_options())){
			selectBuffer.append(getReq().getAppen_options());
		}
		selectBuffer.append("<option value=''>-----请选择-----</option>");
		//静态数据处理
		if(staticDatas!=null && staticDatas.size()>0){
			for (Map date : staticDatas) {
				String selected_str = "";
				String value = (String) date.get("value");
				String value_desc = (String) date.get("value_desc");
				
				if (!StringUtil.isEmpty(getResp().getField_value()) && value !=null && value.equals(getResp().getField_value())) {
					selected_str = " selected ";
				}
				selectBuffer.append(optionHtml(value, value_desc, selected_str));
			}
		}
		selectBuffer.append("</select>");
		return selectBuffer.toString();
	}
	private String optionHtml(String value, String desc, String selected) {
		return "<option value=" + value + selected + ">" + desc + "</option>";
	}
}
