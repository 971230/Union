package services;

import java.util.List;

import javax.annotation.Resource;

import services.FavoriteInf;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IFavoriteManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-27 17:02
 * To change this template use File | Settings | File Templates.
 */
public class FavoriteServ implements FavoriteInf {
    @Resource
    private IFavoriteManager favoriteManager;

    @Override
    public Page list(int pageNo, int pageSize) {
        return this.favoriteManager.list(pageNo,pageSize);
    }

    @Override
    public Page listFavPartner(String member_id, int pageNo, int pageSize) {
        return this.favoriteManager.listFavPartner(member_id,pageNo,pageSize);
    }
    
    @Override
    public Page listFavSuppler(String member_id, int pageNo, int pageSize) {
        return this.favoriteManager.listFavSuppler(member_id,pageNo,pageSize);
    }

    @Override
    public Page listFavGoods(String member_id, int pageNo, int pageSize) {
        return this.favoriteManager.listFavGoods(member_id,pageNo,pageSize);
    }

    @Override
    public List list() {
        return this.favoriteManager.list();
    }

    @Override
    public void delete(String favorite_id) {
        this.favoriteManager.delete(favorite_id);
    }

    @Override
    public void add(String goodsid) {
         this.favoriteManager.add(goodsid);
    }

    @Override
    public void addFav(String fav_rel_id, String member_id, String fav_type) {
         this.favoriteManager.addFav(fav_rel_id,member_id,fav_type);
    }

	@Override
	public int countFav(String member_id, String goods_id,String type) {
		int count =  this.favoriteManager.countFavByMemIdAndGoods(member_id, goods_id,type);
		return count;
	}
}
