package zte.params.template.resp;

import java.util.List;
import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 模板转出 出参
 * 
 * @author xuefeng
 */
public class GetAttrDefTableResp extends ZteResponse {

	
	@ZteSoftCommentAnnotationParam(name = "属性定义集合", type = "String", isNecessary = "Y", desc = "属性定义集合")
	private List<AttrDefTableVO> attrDefTableVOList;

	public List<AttrDefTableVO> getAttrDefTableVOList() {
		return attrDefTableVOList;
	}

	public void setAttrDefTableVOList(List<AttrDefTableVO> attrDefTableVOList) {
		this.attrDefTableVOList = attrDefTableVOList;
	}
}
