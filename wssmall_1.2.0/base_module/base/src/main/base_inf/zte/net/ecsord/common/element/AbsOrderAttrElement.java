package zte.net.ecsord.common.element;

import zte.net.ecsord.common.AttrBusiInstTools;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;

import com.ztesoft.net.framework.util.StringUtil;

/**
 * 
 * @author wu.i
 * 基础属性实体
 *
 */
public abstract class AbsOrderAttrElement {
	
	public AbsOrderAttrElement(AttrInstLoadReq req,AttrInstLoadResp resp){
		this.req = req;
		this.resp = resp;
	}
	private AttrInstLoadResp resp;

	private AttrInstLoadReq req;
	
	public abstract String toElement();
	
	/**
	 * 构造attr属性
	 * @return
	 */
	public  String renderAttr(){
		String style = StringUtil.isEmpty(getReq().getStyle())?"width:200px;": getReq().getStyle();
		StringBuffer attrBuffer = new StringBuffer( " style='").append(style).append("'");
		String name =AttrBusiInstTools.genAttrsInstElementId(getResp().getOrder_id(),getResp().getAttr_inst_id(), getResp().getField_attr_id(), getResp().getField_name());
		attrBuffer.append(" name=").append("'").append(name).append("'")
				.append(" field_name=").append("'").append(getResp().getField_name()).append("'")
				.append(" id=").append("'").append(getResp().getAttr_inst_id()).append("'")
				.append(" is_null=").append("'").append(getResp().getIs_null()).append("'")
				.append(" is_edit=").append("'").append(getResp().getIs_edit()).append("'");
			if(!StringUtil.isEmpty(getResp().getCheck_message()))
				attrBuffer.append(" check_message=").append("'").append(getResp().getCheck_message()).append("'");
			if(!StringUtil.isEmpty(getResp().getAttr_code()))
				attrBuffer.append(" attr_code=").append("'").append(getResp().getAttr_code()).append("'");
		return attrBuffer.toString();
	}
	
	public AttrInstLoadResp getResp() {
		return resp;
	}

	public void setResp(AttrInstLoadResp resp) {
		this.resp = resp;
	}

	public AttrInstLoadReq getReq() {
		return req;
	}

	public void setReq(AttrInstLoadReq req) {
		this.req = req;
	}
	
}
