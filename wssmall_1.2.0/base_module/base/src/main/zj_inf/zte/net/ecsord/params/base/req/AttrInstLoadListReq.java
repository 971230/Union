package zte.net.ecsord.params.base.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.model.AttrDef;

import params.ZteRequest;
import zte.net.ecsord.params.base.resp.AttrInstLoadListResp;

public class AttrInstLoadListReq extends ZteRequest<AttrInstLoadListResp>{
	private List<AttrSwitchParams> attrs;
	
	public List<AttrSwitchParams> getAttrs(){
		return attrs;
	}
	public void setAttrs(List<AttrSwitchParams> attrs){
		this.attrs = attrs;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.ecsord.params.attr.req.AttrInstLoadListReq";
	}
}
