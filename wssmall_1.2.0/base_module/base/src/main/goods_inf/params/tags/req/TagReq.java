package params.tags.req;

import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;

public class TagReq extends ZteRequest {
	String goods_num;
	String tag_id;

	public TagReq(String goods_num){
		this.goods_num =goods_num;
	}
	
	public TagReq(){}
	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
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
