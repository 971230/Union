/**
 * 
 */
package zte.params.goods.req;

import java.util.List;
import java.util.Map;

import params.ZteRequest;
import zte.params.goods.resp.SubtractInventoryNumResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * @version 2015-11-25
 * @see 扣减库存
 *
 */
public class SubtractInventoryNumReq extends ZteRequest<SubtractInventoryNumResp> {
	
	@ZteSoftCommentAnnotationParam(name="商品ID,货品ID,数量,来源",type="String",isNecessary="Y",desc="goods_id,product_id,num,org_id : 商品ID,货品ID,数量,来源")
	private List<Map<String, String>> list;
	
//	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="goods_id : 商品ID")
//	private String goods_id;
//	@ZteSoftCommentAnnotationParam(name="货品ID",type="String",isNecessary="Y",desc="product_id : 货品ID")
//	private String product_id;
//	@ZteSoftCommentAnnotationParam(name="数量",type="String",isNecessary="Y",desc="num :数量")
//	private String num;
//	@ZteSoftCommentAnnotationParam(name="商城来源",type="String",isNecessary="Y",desc="org_id :商城来源")
//	private String org_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	
	public List<Map<String, String>> getList() {
		return list;
	}

	public void setList(List<Map<String, String>> list) {
		this.list = list;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.params.goods.req.subtractinventorynumreq";
	}
	
}
