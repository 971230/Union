package com.ztesoft.net.service;

import java.util.List;

import javax.annotation.Resource;

import services.ServiceBase;
import zte.net.iservice.IGoodsBrandService;
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

import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.BrandModel;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IBrandManager;
import com.ztesoft.net.mall.core.service.IBrandModelManager;
import com.ztesoft.net.mall.core.service.IGoodsManager;

public class GoodsBrandService extends ServiceBase implements IGoodsBrandService {

	private IGoodsManager goodsManager;
	private IBrandManager brandManager;
	
	@Resource
	private IBrandModelManager brandModelManager;
	
	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public IBrandManager getBrandManager() {
		return brandManager;
	}

	public void setBrandManager(IBrandManager brandManager) {
		this.brandManager = brandManager;
	}

	/**
	 * @title 获取品牌信息
	 * @param req
	 */
	@Override
	public BrandGetResp getBrand(BrandGetReq req) {
		BrandGetResp resp = new BrandGetResp();
		String goods_id = req.getGoods_id();
		String brand_id = req.getBrand_id();
		
		GoodsNetCacheRead reader = new GoodsNetCacheRead();
		if(goods_id!=null){
			Goods goods = reader.getCachesGoods(goods_id);
			brand_id = goods.getBrand_id();
		}
		Brand brand = reader.getCachesGoodsBrand(brand_id);
		resp.setBrand(brand);
		if(brand.getBrand_id()!=null){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		
		addReturn(req,resp);
		return resp;
	}

	/**
	 * @title 查询品牌商品
	 * @param listReq 请求入参
	 */
	@Override
	public BrandGoodsListResp listBrandRelGoods(BrandGoodsListReq listReq) {
		BrandGoodsListResp resp = new BrandGoodsListResp();
		String brand_id = listReq.getBrand_id();
		List goods = goodsManager.listGoodsByBrandId(brand_id);
		resp.setGoodsList(goods);
		if(goods!=null && goods.size()>0){
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		}
		else{
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败：返回为空");
		}
		addReturn(listReq,resp);
		return resp;
	}

	@Override
	public CatBrandListResp listCatBrand(CatBrandListReq req) {
		List cats = brandManager.groupByCat();
		CatBrandListResp resp = new CatBrandListResp();
		resp.setListCat(cats);
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

	@Override
	public BrandListResp listBrand(BrandListReq req) {
		String good_id = req.getGood_id();
		List brands = brandManager.listByGoodsId(good_id);
		BrandListResp resp = new BrandListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setBrands(brands);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public BrandListResp listBrandByCatId(BrandListByCatReq req) {
		Integer cat_id = Integer.valueOf(req.getCat_id());
		List brandList  = brandManager.listByCatId(cat_id);
		BrandListResp resp = new BrandListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setBrands(brandList);
		addReturn(req,resp);
		return resp;
	}

	@Override
	public BrandListResp brandListAll(BrandListAllReq req) {
		
		List<Brand> brandList = brandManager.list();
		BrandListResp resp = new BrandListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setBrands(brandList);
		this.addReturn(req, resp);
		
		return resp;
	}

	@Override
	public BrandModelListResp brandModelListAll(BrandModelListAllReq req) {
		
		String model_code = req.getModel_code();
		List<BrandModel> brandModelLst = brandModelManager.getBrandModelListAll(model_code);
		
		BrandModelListResp resp = new BrandModelListResp();
		resp.setResult(true);
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setBrandModelLst(brandModelLst);
		this.addReturn(req, resp);
		
		return resp;
	}

	@Override
	public BrandListByTypeResp listBrandByTypeId(BrandListByTypeReq req) {
		String type_id = req.getType_id();
		List<Brand> brands = brandManager.listCacheBrandByTypeId(type_id);
		BrandListByTypeResp resp = new BrandListByTypeResp();
		resp.setBrandList(brands);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
	
	@Override
	public BrandModelListByBrandResp listBrandModelByBrandId(BrandModelListByBrandReq req){
		String brand_id = req.getBrand_id();
		List<BrandModel> models = brandModelManager.listCacheModelByBrandId(brand_id);
		BrandModelListByBrandResp resp = new BrandModelListByBrandResp();
		resp.setModelList(models);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public BrandModelListByModelCodeResp listBrandModelByModel(
			BrandModelListByModelCodeReq req) {
		String model_code = req.getModel_code();
		List<BrandModel> models = brandModelManager.listCacheModelByModel(model_code);
		BrandModelListByModelCodeResp resp = new BrandModelListByModelCodeResp();
		resp.setModelList(models);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}
}
