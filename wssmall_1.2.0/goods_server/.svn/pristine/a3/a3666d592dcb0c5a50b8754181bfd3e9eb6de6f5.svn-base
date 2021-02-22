package com.ztesoft.net.cache.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.eop.sdk.utils.UploadUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.BrandModel;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.SerializeHashMap;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.model.support.GoodsRefreshDTO;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsManagerN;

public class GoodsNetCacheRead extends GoodsNetCache{
	
	private IGoodsManager goodsManager = null;
	private IGoodsManagerN goodsManagerN = null;
	
	public static GoodsNetCacheRead getInstance(){
		return new GoodsNetCacheRead();
	}
	
	////////////////////////////////////////////静态方法抽取开始
	private void init(){
		goodsManager = SpringContextHolder.getBean("goodsManager");
		goodsManagerN = SpringContextHolder.getBean("goodsManagerN");
	}
	
	private Object getCachesByGoodsId(String key,String goods_id){
		Object obj = this.get(key);		
		if(null==obj){
			this.init();
			GoodsRefreshDTO goodsRefreshDTO = new GoodsRefreshDTO();
			goodsRefreshDTO.setGoods_ids(goods_id);
			goodsManager.initGoodsCacheByCondition(goodsRefreshDTO);
			obj = this.get(key);			
		}		
		return obj;
	}
	private Object getCachesBySku(String key,String sku){
		Object obj =this.get(key);
		if(null==obj){
			this.init();
			GoodsRefreshDTO goodsRefreshDTO = new GoodsRefreshDTO();
			goodsRefreshDTO.setSku(sku);
			goodsManager.initGoodsCacheByCondition(goodsRefreshDTO);
			obj = this.get(key);
		}		
		return obj;
	}
	private Object getCachesByProductid(String key,String productid){
		Object obj = this.get(key);
		if(null==obj){
			this.init();
			GoodsRefreshDTO goodsRefreshDTO = new GoodsRefreshDTO();
			Goods goods = goodsManager.getProductInfo(productid); //add by wui
			if(goods !=null){
				goodsRefreshDTO.setGoods_ids(goods.getGoods_id());
				goodsManager.initGoodsCacheByCondition(goodsRefreshDTO);
			}
			obj = this.get(key);
		}		
		return obj;
	}
	private Object getCachesProductByName(String key, String name){
		Object obj = this.get(key);
		if(obj == null){
			this.init();
			GoodsRefreshDTO goodsRefreshDTO = new GoodsRefreshDTO();
			goodsRefreshDTO.setName(name);
			List<Map> list = this.goodsManager.listVproductsByCondition(goodsRefreshDTO);
			if(list.size()!=0){
				Map<String, String> map = list.get(0);
				SerializeHashMap serialMap = new SerializeHashMap();
				serialMap.setSpecMap(map);
				this.set(key, serialMap);
			}
			obj = this.get(key);
		}
		return obj;
	}
	private Object getCachesCatGoodsByCatId(String key, String cat_id){
		Object obj = this.get(key);
		if(obj == null){
			this.init();
			List<Goods> goodsList = this.goodsManagerN.listGoodsByCatId(cat_id);
			if(goodsList!=null && goodsList.size()>0){
				SerializeList serializeList = new SerializeList();
				serializeList.setObj(goodsList);
				this.set(key, serializeList);
			}
			obj = this.get(key);
		}
		return obj;
	}
	private Object getCachesGoodsByTagId(String key, String tag_id){
		Object obj = this.get(key);
		if(obj == null){
			this.init();
			List<Goods> goodsList = this.goodsManager.listGoodsByRelTag(tag_id);
			if(goodsList!=null && goodsList.size()>0){
				SerializeList serializeList = new SerializeList();
				serializeList.setObj(goodsList);
				this.set(key, serializeList);
			}
			obj = this.get(key);
		}
		return obj;
	}
	/////////////////////////////////////////静态方法抽取结束
	
	/**
	 * 商品缓存
	 * @param goods_id
	 * @return
	 */
	public Goods getCachesGoods(String goods_id){
		Object obj = getCachesByGoodsId(this.CACHE_GOODS+goods_id,goods_id);
		if(null != obj){
            Goods goods=(Goods)obj;
            if(StringUtils.isNotBlank(goods.getImage_default())){
               goods.setImage_default( UploadUtils.replacePath(goods.getImage_default()));
            }
            if(StringUtils.isNotBlank(goods.getImage_file())){
                goods.setImage_file(UploadUtils.replacePath(goods.getImage_file()));
            }
			return goods;
		}else{
			return new Goods();
		}
	}
	
	/**
	 * 商品缓存
	 * @param sku
	 * @return
	 */
	public Goods getCachesGoodsBySku(String sku){
		Object obj = this.getCachesBySku(this.CACHE_GOODS_SKU+sku, sku);
		if(null != obj){
            //UploadUtils.replacePath(goods.getImage_default())
            Goods goods=(Goods)obj;
            if(StringUtils.isNotBlank(goods.getImage_default())){
               goods.setImage_default( UploadUtils.replacePath(goods.getImage_default()));
            }
            if(StringUtils.isNotBlank(goods.getImage_file())){
                goods.setImage_file(UploadUtils.replacePath(goods.getImage_file()));
            }

			return goods;
		}else{
			return new Goods();
		}
	}
	
	/**
	 * 货品缓存
	 * @param product_id
	 * @return
	 */
	public Goods getCachesProducts(String product_id){
		Object obj = this.getCachesByProductid(this.CACHE_PRODUCTS+product_id, product_id);
		if(null != obj){
            //UploadUtils.replacePath(goods.getImage_default())
            Goods goods=(Goods)obj;
            if(StringUtils.isNotBlank(goods.getImage_default())){
               goods.setImage_default( UploadUtils.replacePath(goods.getImage_default()));
            }
            if(StringUtils.isNotBlank(goods.getImage_file())){
                goods.setImage_file(UploadUtils.replacePath(goods.getImage_file()));
            }

			return goods;
		}else{
			return new Goods();
		}
	}
	/**
	 * 货品缓存 -对应视图v_product
	 * @param name
	 * @return
	 */
	public Map<String, String> getProducts(String name){
		Object obj = this.getCachesProductByName(this.CACHE_PRODUCTS_NAME+name, name);
		Map<String, String> product = null;
		if(obj!=null){
			product = ((SerializeHashMap)obj).getSpecMap();
		}
		else{
			product = new HashMap<String, String>();
		}
		return product;
	}
	/**
	 * 终端缓存
	 * @param goods_id
	 * @return
	 */
	public Goods getCachesTerminal(String goods_id){
		Object obj = getCachesByGoodsId(this.GOODS_TERMINAL+goods_id,goods_id);
		if(null != obj){
			return (Goods)obj;
		}else{
			return new Goods();
		}
	}
	
	/**
	 * 套餐缓存
	 * @param goods_id
	 * @return
	 */
	public Goods getCachesGoodsTC(String goods_id){
		Object obj = getCachesByGoodsId(this.GOODS_TC+goods_id,goods_id);
		if(null != obj){
			return (Goods)obj;
		}else{
			return new Goods();
		}
	}
	
	/**
	 * 合约机缓存
	 * @param goods_id
	 * @return
	 */
	public Goods getCachesGoodsContract(String goods_id){
		Object obj = getCachesByGoodsId(this.GOODS_CONTRACT+goods_id,goods_id);
		if(null != obj){
			return (Goods)obj;
		}else{
			return new Goods();
		}		
	}

	/**
	 * 按服务类型获取数据
	 * @param style_id
	 * @return
	 */
	public List<Goods> getAllGoodsByServ(String style_id){
		Object obj = this.get(this.GOODS_SERV+style_id);
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList<Goods>();
		}		
	}
	
	/**
	 * 商品促销缓存
	 * @param pmt_id
	 * @return
	 */
	public List<HashMap> getCachesGoodsPromotion(String pmt_id){
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap obj = (HashMap) this.get(this.GOODS_PROMOTION+pmt_id);
		if(null != obj){
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * 商品标签
	 * @param tag_id
	 * @return
	 */
	public Tag getCachesGoodsTag(String tag_id){
		Object obj = this.get(this.GOODS_TAGS+tag_id);
		if(null != obj){
			return (Tag)obj;
		}else{
			return new Tag();
		}
		
	}
	
	/**
	 * 标签关联商品
	 * @param tag_id
	 * @return
	 */
	public List<Goods> getCacheGoodsRelTags(String tag_id){
		Object obj = this.getCachesGoodsByTagId(this.GOODS_TAGS_REL_GOODS+tag_id, tag_id);//this.GOODS_TAGS_REL_GOODS+tag_id
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList<Goods>();
		}
		
	}
	
	/**
	 * @title 从缓存获取绑定商品
	 * @param goods_id
	 * @return
	 */
	public List getCachesGoodsComplex(String goods_id){
		Object obj = getCachesByGoodsId(this.GOODS_BINDING+goods_id,goods_id);
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList();
		}
		
	}
	
	/**
	 * @title 从缓存获取商品配件信息
	 * @param goods_id
	 * @return
	 */
	public List getCachesGoodsAdjunct(String goods_id){
		Object obj = getCachesByGoodsId(this.GOODS_ADJUNCT+goods_id,goods_id);
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList();
		}
	}
	
	/**
	 * @title 从缓存获取分类推荐的商品信息
	 * @param cat_id
	 * @return
	 */
	public List<Goods> getCachesCatComplex(String cat_id){
		Object obj = this.get(this.CATS_REMDGOODS+cat_id);
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList();
		}
	}
	
	public List<Goods> getCacheCatGoods(String cat_id){
		Object obj = this.getCachesCatGoodsByCatId(this.CAT_GOODS_LIST+cat_id, cat_id);
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList();
		}
	}
	
	/**
	 * @title 从缓存获取商品类型缓存信息
	 * @param type_id
	 * @return
	 */
	public GoodsType getCachesGoodsType(String type_id){
		Object obj = this.get(this.GOODS_TYPE+type_id);
		if(null != obj){
			return (GoodsType)obj;
		}else{
			
			GoodsNetCacheWrite write = new GoodsNetCacheWrite();
			write.loadAllTypes();
			obj = this.get(this.GOODS_TYPE+type_id);
			if(null != obj)
				return (GoodsType)obj;
			return new GoodsType();
		}
	}
	
	/**
	 * @title 从缓存获取货品类型缓存信息
	 * @param type_id
	 * @return
	 */
	public GoodsType getCachesProductType(String type_id){
		Object obj = this.get(this.PRODUCT_TYPE+type_id);
		if(null != obj){
			return (GoodsType)obj;
		}else{
			GoodsNetCacheWrite write = new GoodsNetCacheWrite();
			write.loadProductType();
			obj = this.get(this.PRODUCT_TYPE+type_id);
			if(null != obj)
				return (GoodsType)obj;
			return new GoodsType();
		}
	}
	
	/**
	 * @title 从缓存获取品牌信息
	 * @param brand_id
	 * @return
	 */
	public Brand getCachesGoodsBrand(String brand_id){
		Object obj = this.get(this.GOODS_BRANDS+brand_id);
		if(null != obj){
			return (Brand)obj;
		}else{
			GoodsNetCacheWrite write = new GoodsNetCacheWrite();
			write.loadBrand();
			obj = this.get(this.GOODS_BRANDS+brand_id);
			if(null != obj)
				return (Brand)obj;
			return new Brand();
		}
	}
	

	/**
	 * @title 从缓存获取商品分类
	 * @param lv_id
	 * @return
	 */
	public List getCachesGoodsCat(String lv_id){
		Object obj = this.get(this.GOODS_MEMBER_CATS+lv_id);
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList();
		}
	}

	/**
	 * @title 从缓存获取分类商品（门户展示一级分类下所有商品）
	 * @param lv_id
	 * @return
	 */
	public List getGoodsByCatLvI(String cat_id){
		Object obj = this.get(this.GOODS_CAT_I+cat_id);
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList();
		}		
	}	
	
	
	/**
	 * @title:限购数量
	 * @desc：获取限购购物车已添加的数量
	 */
	public int loadLimitBuy(String goods_id){
		Object lBuyCount = this.get(this.LIMIT_GOODS+goods_id);
		if(lBuyCount == null)
			lBuyCount =0;
		return (Integer)lBuyCount;
	
	}
	
	
	public List<Goods> getGoodsRelProducts(String goods_id){
		Object obj = this.getCachesByGoodsId(this.CACHE_GOODS_REL_PRODUCTS+goods_id, goods_id);
		if(null != obj){
			return ((SerializeList)obj).getObj();
		}else{
			return new ArrayList();
		}		
	}
	
	public Map getSpec(String goods_id){
		Object obj = this.getCachesByGoodsId(this.CACHE_GOODS+goods_id, goods_id);
		Map spec = new HashMap();
		if(obj != null){
			Goods goods = (Goods) obj;
			try {
				spec = BeanUtils.bean2Map(spec,goods);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return spec;
	}
	
	
	/**
	 * 获取全部商品货品类型
	 * 
	 * @return
	 */
	public List<GoodsType> getTypes(){
		Object obj = this.get(this.GOODS_TYPE_LIST);
		List<GoodsType> types = null;
		if(obj !=null)
			types = ((SerializeList)obj).getObj();
		else{
			GoodsNetCacheWrite write = new GoodsNetCacheWrite();
			write.loadAllTypes();
			Object objn = this.get(this.GOODS_TYPE_LIST);
			if(objn!=null)
				types = ((SerializeList)obj).getObj();
			else
				types = new ArrayList<GoodsType>();
		}
		return types;
	}

	/**
	 * 根据类型ID获取缓存的品牌信息
	 * @param type_id 类型ID
	 * @return
	 */
	public List<Brand> getTypeRelBrands(String type_id){
		List<Brand> brands = null;
		Object obj = this.get(this.BRAND_LIST_BY_TYPE+type_id);
		if(obj!=null)
			brands = ((SerializeList)obj).getObj();
		else{
			GoodsNetCacheWrite write = new GoodsNetCacheWrite();
			write.loadBrandByTypeId();
			Object objn = this.get(this.BRAND_LIST_BY_TYPE+type_id);
			if(objn!=null)
				brands = ((SerializeList)obj).getObj();
			else
				brands = new ArrayList<Brand>();
		}
		return brands;
	}
	
	public List<BrandModel> getBrandModelByBrandId(String brand_id){
		List<BrandModel> models = null;
		Object obj = this.get(this.BRAND_MODEL_LIST+brand_id);
		if(obj!=null)
			models = ((SerializeList)obj).getObj();
		else{//add by wui临时缓存处理
			GoodsNetCacheWrite write = new GoodsNetCacheWrite();
			write.loadBrandModel();
			Object objn = this.get(this.GOODS_CAT_ID+brand_id);
			if(objn!=null)
				models = ((SerializeList)objn).getObj();
			else
				models = new ArrayList<BrandModel>();
		}
		return models;
	}
	
	public List<BrandModel> getBrandModelByModelCode(String model_code){
		List<BrandModel> models = null;
		Object obj = this.get(this.BRAND_MODEL_LIST);
		if(obj!=null && ((SerializeList)obj).getObj().size()>0)
			models = ((SerializeList)obj).getObj();
		else{
			
			GoodsNetCacheWrite write = new GoodsNetCacheWrite();
			write.loadBrandModel();
			Object objn = this.get(this.BRAND_MODEL_LIST);
			if(objn!=null)
				models = ((SerializeList)objn).getObj();
			else
				models = new ArrayList<BrandModel>();
		}
		List<BrandModel> rtnModels = new ArrayList<BrandModel>();
		for(BrandModel brandModel :models){
			if(model_code.equals(brandModel.getModel_code())){
				rtnModels.add(brandModel);
			}
		}
		return rtnModels;
	}
	
	public List<Cat> getCatsByTypeId(String type_id){
		List<Cat> cats = null;
		Object obj = this.get(this.CAT_LIST_BY_TYPE+type_id);
		if(obj!=null)
			cats = ((SerializeList)obj).getObj();
		else
			cats = new ArrayList<Cat>();
		return cats;
	}
	
	public List<HashMap> getTerminalNumList(){
		List<HashMap> terminalNumList = null;
		Object obj = this.get(this.TERMINAL_NUM_LIST);
		if(obj!=null)
			terminalNumList = ((SerializeList)obj).getObj();
		else
			terminalNumList = new ArrayList<HashMap>();
		if(terminalNumList.isEmpty()){
			IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
			terminalNumList = goodsManager.listTerminalNum();
			SerializeList serializeList = new SerializeList();
			serializeList.setObj(terminalNumList);
			this.set(this.TERMINAL_NUM_LIST, serializeList,60*24*60);
		}
		return terminalNumList;
	}
	
	public Cat getCacheCat(String cat_id){
		Object obj = this.get(this.GOODS_CAT_ID+cat_id);
		if(obj!=null)
			return (Cat) obj;
		else{
			GoodsNetCacheWrite write = new GoodsNetCacheWrite();
			write.loadAllCats();
			Object objn = this.get(this.GOODS_CAT_ID+cat_id);
			if(objn!=null)
				return  (Cat) objn;
			return null;
		}
	}
}
