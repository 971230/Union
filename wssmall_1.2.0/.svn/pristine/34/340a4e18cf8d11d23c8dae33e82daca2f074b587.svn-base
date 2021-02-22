package zte.net.iservice;


import zte.params.brand.req.BrandGetReq;
import zte.params.brand.req.BrandGoodsListReq;
import zte.params.brand.req.BrandListAllReq;
import zte.params.brand.req.BrandListByCatReq;
import zte.params.brand.req.BrandListByTypeReq;
import zte.params.brand.req.BrandListReq;
import zte.params.brand.req.BrandModelListAllReq;
import zte.params.brand.req.BrandModelListByBrandReq;
import zte.params.brand.req.BrandModelListByModelCodeReq;
import zte.params.brand.resp.BrandGetResp;
import zte.params.brand.resp.BrandGoodsListResp;
import zte.params.brand.resp.BrandListByTypeResp;
import zte.params.brand.resp.BrandListResp;
import zte.params.brand.resp.BrandModelListByBrandResp;
import zte.params.brand.resp.BrandModelListByModelCodeResp;
import zte.params.brand.resp.BrandModelListResp;
import zte.params.goodscats.req.CatBrandListReq;
import zte.params.goodscats.resp.CatBrandListResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="品牌API",summary="品牌管理API[查询品牌 查询品牌商品]")
public interface IGoodsBrandService {

	/**
	 * 根据品牌ID获取品牌
	 * @param pageReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="获取品牌",summary="根据品牌ID获取品牌")
	public BrandGetResp getBrand(BrandGetReq req);

	/**
	 * 查询品牌商品
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询品牌商品",summary="查询品牌商品")
	public BrandGoodsListResp listBrandRelGoods(BrandGoodsListReq pageReq);

	//取得商品分类的第一级列表
	@ZteSoftCommentAnnotation(type="method",desc="取得商品分类的第一级列表",summary="取得商品分类的第一级列表")
	public CatBrandListResp listCatBrand(CatBrandListReq req);
	
	//根据商品ID获取品牌
	@ZteSoftCommentAnnotation(type="method",desc="根据商品id获取品牌",summary="根据商品id获取品牌")
	public BrandListResp listBrand(BrandListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据分类id获取品牌",summary="根据分类id获取品牌")
	public BrandListResp listBrandByCatId(BrandListByCatReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取所有有效品牌列表",summary="获取所有有效品牌列表")
	public BrandListResp brandListAll(BrandListAllReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取所有型号列表",summary="获取所有型号列表")
	public BrandModelListResp brandModelListAll(BrandModelListAllReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据类型id获取品牌列表",summary="根据类型id获取品牌列表",isOpen=false)
	public BrandListByTypeResp listBrandByTypeId(BrandListByTypeReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据品牌ID获取型号列表",summary="根据品牌ID获取型号列表",isOpen=false)
	public BrandModelListByBrandResp listBrandModelByBrandId(BrandModelListByBrandReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据型号获取型号列表",summary="根据型号获取型号列表",isOpen=false)
	public BrandModelListByModelCodeResp listBrandModelByModel(BrandModelListByModelCodeReq req);
}
