package zte.net.iservice;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

import zte.params.supplier.req.SupplierGetReq;
import zte.params.supplier.req.SupplierListReq;
import zte.params.supplier.resp.SupplierGetResp;
import zte.params.supplier.resp.SupplierListResp;

@ZteSoftCommentAnnotation(type="class",desc="供货商管理API",summary="供货商管理API[查询商家销售排行列表]")
public interface ISupplierService {

	/**
	 * 查询商家销售排行列表
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询商家销售排行列表",summary="查询商家销售排行列表")
	public SupplierListResp listSupplier(SupplierListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取商家信息",summary="根据商家名称获取商家信息")
	public SupplierGetResp getSupplier(SupplierGetReq req);
}
