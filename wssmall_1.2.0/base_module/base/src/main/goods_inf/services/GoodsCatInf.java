package services;

import com.ztesoft.net.mall.core.model.support.GoodsView;

import params.ZteRequest;
import params.goods.resp.HotGoodsResp;
import params.goods.resp.RemdGoodsResp;
import params.goodscats.req.CatsReq;
import params.goodscats.req.GoodsQueryReq;
import params.goodscats.req.GoodsQueryResp;
import params.goodscats.resp.GoodsCatsResp;

import java.util.List;

/**
 * 商品分类service
 * 
 * @作者 wu.i
 * @创建日期 2013-9-23
 * @版本 V 1.0
 * 
 * 
 * 		GoodsQueryReq req = new GoodsQueryReq();
		req.setCat("1000961280");
		String json = CommonTools.beanToJson(req);
		String str = HttpUtil.readPostAsString(URL+"GoodCatsServ/queryPageGoodsByCond", "param_json="+json, "utf-8", "utf-8", 100000, 100000);
		GoodsQueryResp tagResp = CommonTools.jsonToBean(str, GoodsQueryResp.class);
 */
public interface GoodsCatInf {

    public String getCatPathById(String catId);


    /**
	 * 查询所有商品分类
	 * @param json
	 */
	public GoodsCatsResp queryAllCats(ZteRequest zteRequest);
	
	
	
	/**
	 * 根据分类ID查询 分类商品的路径
	 * @param json
	 */
	public GoodsCatsResp queryCatsByCatId(CatsReq catsReq);
	

	
	/**
	 * 根据分类ID查询 分类ID归属一级分类下所有的分类目录树
	 * @param json
	 */
	public GoodsCatsResp queryFChildrenCatsByCatIds(CatsReq catsReq);
	
	
	
	/**
	 * 根据分类ID查询相关已售卖商品信息
	 * @param json
	 */
	public HotGoodsResp queryHotGoodsCatId(CatsReq catsReq);
	
	
	/**
	 * 根据分类ID查询分类相关商品
	 * @param json
	 */
	public RemdGoodsResp queryRemdGoodsCatId(CatsReq catsReq);
	
	
	
	/**
	 *商品详情页面商品搜索处理
	 * @param json
	 */
	public GoodsQueryResp queryPageGoodsByCond(GoodsQueryReq goodsQueryReq);

    /**
     * 获取关联商品
     * @param catId
     * @return
     */
    public List<GoodsView> getComplexGoodsByCatId(String catId);
    public String _getComplexGoodsByCatId(String cat_id);
}