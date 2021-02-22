package services;

import com.ztesoft.net.app.base.core.model.AdColumn;
import com.ztesoft.net.app.base.core.model.Adv;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Cat;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsCatManager;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsSearchManager;
import com.ztesoft.net.mall.widget.goods.list.GoodsDataProvider;
import com.ztesoft.net.mall.widget.goods.list.Term;
import com.ztesoft.remote.inf.IAdvService;
import com.ztesoft.remote.params.adv.req.AdColumnReq;
import com.ztesoft.remote.params.adv.req.AdvReq;
import params.goods.req.GoodsListReq;
import params.goods.req.LevelGoodsReq;
import params.goods.resp.GoodsListResp;
import params.goods.resp.LevelGoodsResp;
import params.goods.sales.req.GoodsSalesReq;
import params.goods.sales.resp.GoodsSalesResp;
import vo.GoodsPlugin;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 通用服务类
 * @author wui
 *
 *		举例：
 *		LevelGoodsReq levelGoodsReq = new LevelGoodsReq("50");
		String json = CommonTools.beanToJson(levelGoodsReq);
		String str = HttpUtil.readPostAsString(URL+"GoodsServ/queryLevelGoods", "param_json="+json, "utf-8", "utf-8", 100000, 100000);
		
		LevelGoodsResp tagResp = CommonTools.jsonToBean(str, LevelGoodsResp.class);
		
 *
 */
public  class GoodsServ  extends ServiceBase implements GoodsInf{

    @Resource
	private GoodsDataProvider goodsDataProvider;
    @Resource
	private IGoodsCatManager goodsCatManager;
    @Resource
	private IAdvService advService;
    @Resource
	private IGoodsSearchManager goodsSearchManager;
    @Resource
	private IGoodsManager goodsManager;

	
    public IGoodsManager getGoodsManager() {
        return goodsManager;
    }

    public void setGoodsManager(IGoodsManager goodsManager) {
        this.goodsManager = goodsManager;
    }


    @Override
    public List<GoodsPlugin> getGoodsPlugin() {
        return this.goodsManager.getGoodsPlugin();
    }

    @Override
	public Map get(String goods_id) {
        return this.goodsManager.get(goods_id);
    }

    @Override
	public Goods getGoodsById(String goodsid) {
        return this.goodsManager.getGoodsById(goodsid);
    }

    @Override
	public Goods getGoods(String goods_id) {
        return this.goodsManager.getGoods(goods_id);
    }

    /**
	 * 查询首页楼层分类商品、分类广告
	 */
	@Override
	@SuppressWarnings("unchecked")
	public LevelGoodsResp queryLevelGoods(LevelGoodsReq levelGoodsReq) {
		LevelGoodsResp levelGoodsResp = new LevelGoodsResp();
		List<Cat> cat_tree = goodsCatManager.listAllChildren("0");
		for (Cat cat : cat_tree) {
			//获取楼层广告图片
			
			AdColumnReq adColumn = new AdColumnReq();
			adColumn.setCatid(cat.getCat_id());
			adColumn.setPosition("lb");
			
			AdColumn adc = this.advService.getAdColumnDetail(adColumn).getAdColumn();
			Adv adv=new Adv();
			
			if(adc!=null){
				AdvReq advReq = new AdvReq();
				advReq.setAcid(adc.getAcid()+"");
				List<Adv> advList = this.advService.queryAdByAcId(advReq).getAdvList();
				if (advList!=null&&advList.size()>0) {
					adv=advList.get(0);
				}
			}
			//cat
			for (Cat s_cat : cat.getChildren()) {
				
				
				// 获取二级节点
				Term term = new Term();
				term.setCatid(s_cat.getCat_id());
				term.setTagid("");

				List goodsList = goodsDataProvider.list(term, new Integer(levelGoodsReq.getGoods_num()).intValue());// 设置显示的数量
				s_cat.setCatGoodsList(goodsList);
			}
			cat.setAdv(adv);
		}
		
		addReturn(levelGoodsReq, levelGoodsResp);
		levelGoodsResp.setCat_tree(cat_tree);
		return levelGoodsResp;
	}
	

	@Override
	public GoodsListResp qryGoodsList(GoodsListReq req) {
		GoodsListResp resp = new GoodsListResp();
		String search_key = null;
		
		/**
		 * String typeid,String cat_path, int page, int pageSize,
			String order,String time_order, String brand_str, String propStr, String keyword,String search_key,
			String minPrice, String maxPrice, String tagids,String attrStr,String agn,String member_lv_id
		 */
		
		Page goodsPage = goodsSearchManager.qryProducts(req.getType_id(), req.getCat_path(), req.getPageNo(), req.getPageSize(), req.getOrder_by(),
				null, req.getBrand(),req.getProp(), req.getKeyword(),req.getKeyword(), req.getMinPrice(), req.getMaxPrice(),
				null, null, req.getAgn(),req.getMember_lv_id());
		resp.setGoodsPage(goodsPage);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

    @Override
    public Goods getGoodsByType(String typeCode, Integer price) {
        return this.goodsManager.getGoodsByType(typeCode,price);
    }

    //获取商品库存数量
	@Override
	public GoodsSalesResp getGoodsStore(GoodsSalesReq goodsSalesReq){
		GoodsSalesResp goodsSalesResp = new GoodsSalesResp();
		int stores = goodsManager.getGoodsStore(goodsSalesReq.getGoods_id(),goodsSalesReq.getLan_code(), null, null);
		goodsSalesResp.setStores(stores);
		addReturn(goodsSalesReq, goodsSalesResp);
		return goodsSalesResp;
	}

    @Override
    public String getPaySequ(String seq) {
        return this.goodsManager.getPaySequ(seq);
    }

    @Override
    public Page listGoodsByUserId(String name, int pageNo, int pageSize) {
        return this.goodsManager.listGoodsByUserId(name,pageNo,pageSize);
    }


    @Override
    public int groundingGoods() {
        return this.goodsManager.groundingGoods();
    }

    @Override
    public int undercarriageGoods() {
        return this.goodsManager.undercarriageGoods();
    }

    @Override
    public int outofRegister() {
        return this.goodsManager.outofRegister();
    }

    @Override
    public void delGoodsByStaffNo(String staffno) {
        this.goodsManager.delGoodsByStaffNo(staffno);
    }

    @Override
    public Goods getGoodsByProductID(String productID) {
        return this.goodsManager.getGoodsByProductID(productID);
    }

    @Override
    public List<Goods> listGoods() {
        return this.goodsManager.listGoods();
    }


}