package services;

import com.ztesoft.net.framework.database.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-27 17:02
 * 商品收藏管理
 */
public interface FavoriteInf {

    /**
     * 列表我的收藏
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page list(int pageNo, int pageSize);



    public Page listFavPartner(String member_id,int pageNo, int pageSize);
    public Page listFavSuppler(String member_id,int pageNo, int pageSize);
    public Page listFavGoods(String member_id,int pageNo, int pageSize);

    /**
     * 读取会员的所有收藏商品
     * @return
     */
    public List list();

    /**
     * 删除收藏
     *
     * @param favorite_id
     */
    public void delete(String favorite_id);



    /**
     * 添加一个收藏
     * @param goodsid
     * @param memberid
     */
    public void add(String goodsid);


    /**
     * 添加一个收藏
     * @param goodsid
     * @param memberid
     */
    public void addFav(String fav_rel_id,String member_id,String fav_type);

    /**
     * 查询会员和收藏的goods_id条件总数。
     * @param member_id
     * @param goods_id
     * @return
     */
    public int countFav(String member_id,String goods_id,String type);

	

}
