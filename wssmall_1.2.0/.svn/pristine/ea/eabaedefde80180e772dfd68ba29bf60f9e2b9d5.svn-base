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
public class OrderAttrCheckbox  extends AbsOrderAttrElement{

	public OrderAttrCheckbox(AttrInstLoadReq req, AttrInstLoadResp resp) {
		super(req, resp);
	}

	/**
	 * 
	 * <div class="checkLabel">
                    	<div class="clearfix">
                        	<label>
                                <input type="checkbox" id="CheckboxGroup1_0" value="1" name="CheckboxGroup1">
                                18位身份证</label>
                          	<label>
                                <input type="checkbox" id="CheckboxGroup1_1" value="2" name="CheckboxGroup1_">
                                15位身份证</label>
                          	<label>
                                <input type="checkbox" id="CheckboxGroup1_2" value="3" name="CheckboxGroup1_">
                                护照</label>
                          	<label>
                                <input type="checkbox" id="CheckboxGroup1_3" value="4" name="CheckboxGroup1_">
                                军官证</label>
                          	<label>
                                <input type="checkbox" id="CheckboxGroup1_4" value="5" name="CheckboxGroup1_">
                                港澳台身份证</label>
                          	<label>
                                <input type="checkbox" id="CheckboxGroup1_5" value="6" name="CheckboxGroup1_">
                                营业执照</label>
                          	<label>
                                <input type="checkbox" id="CheckboxGroup1_6" value="7" name="CheckboxGroup1_">
                                银行卡</label>
                          	<label>
                                <input type="checkbox" id="CheckboxGroup1_7" value="8" name="CheckboxGroup1_">
                                其他</label>
                        </div>
                    	<div class="checkEx"><span>是否已上传：</span> <span>资料回收方式：</span></div>
               	  	</div>
               	  	
	 */
	@Override
	public String toElement() {
		
		StringBuffer selectBuffer = new StringBuffer("<div class='checkLabel'><div class='clearfix'>");
		List<Map<String,String>> staticDatas = getResp().getStaticDatas();
		String field_value = ","+getResp().getField_value();
		//静态数据处理
		if(staticDatas!=null && staticDatas.size()>0){
			for (Map date : staticDatas) {
				String checked = "";
				String value = (String) date.get("value");
				String value_desc = (String) date.get("value_desc");
				//匹配则设置
				if (!StringUtil.isEmpty(getResp().getField_value()) && value !=null && field_value.indexOf(","+value)>-1) {
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
		return "<label> <input type='checkbox' value='"+value+"' "+renderAttr()+" '"+checked+"' "+disable+"/>" + desc + "</label>";
	}
}
