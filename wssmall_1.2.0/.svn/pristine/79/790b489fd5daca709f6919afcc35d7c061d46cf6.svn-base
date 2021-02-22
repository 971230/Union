package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.goods.resp.GoodsPageListResp;

/**
 * 根据多个id及名称匹配商品的传参
 * @author hu.yi
 * @date 2014.03.10
 */
public class GoodsPageByIdsReq extends ZteRequest<GoodsPageListResp>{
	@ZteSoftCommentAnnotationParam(name="商品名称",type="String",isNecessary="N",desc="name：商品名称")
	private String name;
	@ZteSoftCommentAnnotationParam(name="逗号分割的id",type="String",isNecessary="N",desc="ids：逗号分割的id")
	private String ids;
	@ZteSoftCommentAnnotationParam(name="页码",type="String",isNecessary="N",desc="pageNo：页码，返回第几页数据，默认为1。")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="N",desc="pageSize：分页大小，每页多少条数据，默认为10。")
	private int pageSize=10;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.pagebyids";
	}
	
	
}
