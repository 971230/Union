package zte.net.ecsord.params.base.resp;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.model.AttrDef;

import params.ZteResponse;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;

public class GroupAttrInstLoadResp extends ZteResponse{

	/**
	 * 属性表对应值，key为表名
	 */
	private Map<String,List<AttrDef>> attrDefTable;
	
	/**
	 * 属属实例数据
	 */
	private List<AttrInstBusiRequest> attInstList;
	
	private Map values;

	public Map<String, List<AttrDef>> getAttrDefTable() {
		return attrDefTable;
	}

	public void setAttrDefTable(Map<String, List<AttrDef>> attrDefTable) {
		this.attrDefTable = attrDefTable;
	}

	public List<AttrInstBusiRequest> getAttInstList() {
		return attInstList;
	}

	public void setAttInstList(List<AttrInstBusiRequest> attInstList) {
		this.attInstList = attInstList;
	}
	
	public Map getValues(){
		return values;
	}
	public void setValues(Map values){
		this.values = values;
	}
}
