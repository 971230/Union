package params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class CatsReq extends ZteRequest {

	private String cat_id;
	
	private String goods_id;
	
	
	private boolean showimage;

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public boolean isShowimage() {
		return showimage;
	}

	public void setShowimage(boolean showimage) {
		this.showimage = showimage;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
