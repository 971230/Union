package zte.net.ecsord.common.element;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import consts.ConstsCore;



/**
 * 
 * @author wu.i
 * 构造实体元素
 *
 */
public class OrderAttrFactory{
	public static OrderAttrFactory dataFactory;
	public static OrderAttrFactory getInstance(){
		if(dataFactory ==null)
			dataFactory = new OrderAttrFactory();
		return dataFactory;
	}
	/**
	 * 获取属性实体
	 * @param resp
	 * @return
	 */
	public AbsOrderAttrElement getAttrElement(AttrInstLoadReq req,AttrInstLoadResp resp){
		if(ConstsCore.FIELD_TYPE_INPUT.equals(resp.getField_type()))
			return new OrderAttrInput(req,resp);
		else if(ConstsCore.FIELD_TYPE_SELECT.equals(resp.getField_type()))
			return new OrderAttrSelect(req,resp);
		else if(ConstsCore.FIELD_TYPE_TEXT.equals(resp.getField_type()))
			return new OrderAttrText(req,resp);
		else if(ConstsCore.FIELD_TYPE_TEXTAREA.equals(resp.getField_type()))
			return new OrderAttrTextArea(req,resp);
		else if(ConstsCore.FIELD_TYPE_CHECKBOX.equals(resp.getField_type()))
			return new OrderAttrCheckbox(req,resp);
		else if(ConstsCore.FIELD_TYPE_RADIO.equals(resp.getField_type()))
			return new OrderAttrRadio(req,resp);
		else if(ConstsCore.FIELD_TYPE_DATE.equals(resp.getField_type()))
			return new OrderAttrDate(req,resp);
		else 
			return new OrderAttrInput(req, resp);
	}
}
