package services;

import com.ztesoft.net.mall.core.model.WarhouseGoodsStore;
import com.ztesoft.net.mall.core.service.IWarhouseGoodsStoreManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 10:06
 * To change this template use File | Settings | File Templates.
 */
public class WarhouseGoodsStoreServ implements WarhouseGoodsStoreInf {
    @Resource
    private IWarhouseGoodsStoreManager warhouseGoodsStoreManager;

    @Override
    public void addStore(String goods_id, String house_id, int addStore) {
        this.warhouseGoodsStoreManager.addStore(goods_id, house_id, addStore);
    }

    @Override
    public void descStore(String goods_id, String house_id, int descStore) {
        this.warhouseGoodsStoreManager.descStore(goods_id, house_id, descStore);
    }

    @Override
    public int sumStoreByGoodsId(String goods_id, String house_id) {
        return this.warhouseGoodsStoreManager.sumStoreByGoodsId(goods_id, house_id);
    }

    @Override
    public List<WarhouseGoodsStore> sumStoreByGoodsAndHouse(String goodsIds, String hous_id) {
        return this.warhouseGoodsStoreManager.sumStoreByGoodsAndHouse(goodsIds, hous_id);
    }
}
