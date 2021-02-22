package zte.params.order.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.model.AttrInst;

/**
 * 添加订单
 * @作者 wu.i
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class OrderOuterAttrQueryResp extends ZteResponse {
	 List<AttrInst> attrInsts;

	public List<AttrInst> getAttrInsts() {
		return attrInsts;
	}

	public void setAttrInsts(List<AttrInst> attrInsts) {
		this.attrInsts = attrInsts;
	}
	 

}
