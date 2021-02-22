package services;

import com.ztesoft.net.mall.core.service.IGoodsInventoryManager;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 17:18
 * To change this template use File | Settings | File Templates.
 */
public class GoodsInventoryServ implements GoodsInventoryInf {
    @Resource
    private IGoodsInventoryManager goodsInventoryManager;

    @Override
    public boolean goodsIsInventory(String house_id, String goods_id) {
        return this.goodsInventoryManager.goodsIsInventory(house_id,goods_id);
    }
}
