package params.resp;

import java.util.List;

import params.ZteResponse;

public class QueryExpCatalogResp extends ZteResponse{
    private List list;
    
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
    
}
