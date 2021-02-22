package params.order.resp;

import com.ztesoft.net.mall.core.model.DlyType;
import params.ZteResponse;

import java.util.List;

public class DlyTypeResp extends ZteResponse {

	private List<DlyType> dlyTypeList;

	public List<DlyType> getDlyTypeList() {
		return dlyTypeList;
	}

	public void setDlyTypeList(List<DlyType> dlyTypeList) {
		this.dlyTypeList = dlyTypeList;
	}
	
}
