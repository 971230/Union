package zte.params.template.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
/**
 * 模板转出 出参
 * @author xuefeng
 */
public class TemplateOuter2XmlConvertResp extends ZteResponse{
	
	@ZteSoftCommentAnnotationParam(name="报文结构",type="String",isNecessary="Y",desc="模板结构")
	private String xmlMsg;

	public String getXmlMsg() {
		return xmlMsg;
	}

	public void setXmlMsg(String xmlMsg) {
		this.xmlMsg = xmlMsg;
	}
}