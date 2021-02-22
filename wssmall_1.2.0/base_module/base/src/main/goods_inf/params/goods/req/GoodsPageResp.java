package params.goods.req;

import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;

public class GoodsPageResp extends ZteResponse {
	Page page;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
}
