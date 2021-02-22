package services;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Goods;
import params.goods.req.GoodsListReq;
import params.goods.req.LevelGoodsReq;
import params.goods.resp.GoodsListResp;
import params.goods.resp.LevelGoodsResp;
import params.goods.sales.req.GoodsSalesReq;
import params.goods.sales.resp.GoodsSalesResp;
import vo.GoodsPlugin;

import java.util.List;
import java.util.Map;


/**
 * 商品处理类
 * @author wui
 *
 *
 *
 *
 */
public  interface GoodsInf  {

    public List<GoodsPlugin> getGoodsPlugin();

    /**
     * 读取一个商品的详细
     *
     * @param Goods_id
     * @return
     */
    public Map get(String goods_id);
    public Goods getGoodsById(String goodsid);
    public Goods getGoods(String goods_id);

	/**
	 * 查询首页目录楼层商品
	 * @param levelGoodsReq
	 * @return
	 */
	public LevelGoodsResp queryLevelGoods(LevelGoodsReq levelGoodsReq);
	
	/**
	 * 获取商品库存数量
	 * @param goodsSalesReq
	 * @return
	 */
	public GoodsSalesResp getGoodsStore(GoodsSalesReq goodsSalesReq);
	
	
	/**
	 * 商品列表查询
	 * @作者 MoChunrun
	 * @创建日期 2013-10-28 
	 * @param req
	 * @return
	 */
	public GoodsListResp qryGoodsList(GoodsListReq req);


    public Goods getGoodsByType(String typeCode, Integer price);

    public String getPaySequ(String seq);


    /**
     * 根据登录用户ID查询商品
     * @return
     */
    public Page listGoodsByUserId(String name,int pageNo,int pageSize);

    /*
 * 上架商品
 *
 */
    public int groundingGoods();

    /*
     * 下架商品
     *
     */
    public int undercarriageGoods();

    /**
     *
     * @return  缺货登记商品总数
     */
    public int outofRegister();

    /**
     * 删除商户时下架该商户对应工号下的商品
     *
     * @param staffno
     */
    public void delGoodsByStaffNo(String staffno);

    /**
     * 按productid查询商品
     * @作者 MoChunrun
     * @创建日期 2013-8-26
     * @param productID
     * @return
     */
    public Goods getGoodsByProductID(String productID);


    /*
    * */
    public List<Goods> listGoods();

}