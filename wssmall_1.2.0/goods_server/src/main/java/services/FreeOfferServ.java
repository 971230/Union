package services;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.FreeOffer;
import com.ztesoft.net.mall.core.service.IFreeOfferManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-25 14:20
 * To change this template use File | Settings | File Templates.
 */
public class FreeOfferServ implements FreeOfferInf {
    @Resource
    private IFreeOfferManager freeOfferManager;

    @Override
    public FreeOffer get(int fo_id) {
        return this.freeOfferManager.get(fo_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void saveAdd(FreeOffer freeOffer) {
        this.freeOfferManager.saveAdd(freeOffer);//To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(FreeOffer freeOffer) {
        this.freeOfferManager.update(freeOffer);//To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(String bid) {
        this.freeOfferManager.delete(bid);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void revert(String bid) {
        this.freeOfferManager.revert(bid);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clean(String bid) {
        this.freeOfferManager.clean(bid);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page list(int page, int pageSize) {
        return this.freeOfferManager.list(page,pageSize);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page list(String name, String order, int page, int pageSize) {
        return this.freeOfferManager.list(name,order,page,pageSize);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page pageTrash(String name, String order, int page, int pageSize) {
        return this.freeOfferManager.pageTrash(name,order,page,pageSize);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List getOrderGift(String order_id) {
        return this.freeOfferManager.getOrderGift(order_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List list(Long[] ids) {
        return this.freeOfferManager.list(ids);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
