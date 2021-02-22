package zte.net.iservice;

import zte.params.order.req.DlyTypeAddressListReq;
import zte.params.order.req.DlyTypeListReq;
import zte.params.order.req.DlyTypePriceListReq;
import zte.params.order.resp.DlyTypeAddressListResp;
import zte.params.order.resp.DlyTypeListResp;
import zte.params.order.resp.DlyTypePriceListResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="配送方式管理API",summary="配送方式管理API[查询配送方式、查询配送地址]")
public interface IDlyTypeAddressService {

	/**
	 * 查询配送方式
	 * @作者 MoChunrun
	 * @创建日期 2014-3-6 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="上门提货地址",summary="上门提货地址")
	public DlyTypeAddressListResp listTypeAddress(DlyTypeAddressListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询配送方式",summary="查询配送方式")
	public DlyTypeListResp listDlyType(DlyTypeListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询配送方式金额",summary="查询配送方式金额")
	public DlyTypePriceListResp listDlyTypePrice(DlyTypePriceListReq req);
	
}
