package zte.params.order.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.DlyType;

import params.ZteResponse;

public class DlyTypeListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="区、县ID",type="String",isNecessary="N",desc="区、县ID",hasChild=true)
	private List<DlyType> dlyTypeList;

	public List<DlyType> getDlyTypeList() {
		return dlyTypeList;
	}

	public void setDlyTypeList(List<DlyType> dlyTypeList) {
		this.dlyTypeList = dlyTypeList;
	}
	
}
