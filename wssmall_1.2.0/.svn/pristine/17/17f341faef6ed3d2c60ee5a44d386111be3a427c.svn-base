package services;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.FreeOffer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-25 14:19
 * To change this template use File | Settings | File Templates.
 * 赠品管理
 */
public interface FreeOfferInf {
    public FreeOffer get(int fo_id);

    public void saveAdd(FreeOffer freeOffer);

    public void update(FreeOffer freeOffer);

    public void delete(String bid);

    public void revert(String bid);

    public void clean(String bid);




    /**
     * 分页读取所有赠品列表
     * @param page
     * @param pageSize
     * @return modify by kingapex - 2010-5-4
     */
    public Page list(int page,int pageSize);




    public Page list(String name,String order,int page,int pageSize);

    public Page pageTrash(String name,String order,int page,int pageSize);

    /**
     * 取得订单对应的赠品列表
     * @param order_id
     * @return
     */
    public List getOrderGift(String order_id);


    /**
     * 根据某些赠品id读取赠品列表
     * @param ids 赠品id数组
     * @return 赚品列表
     * @author kingapex
     */
    public List list(Long[] ids);
}
