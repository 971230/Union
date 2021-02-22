package services;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.Specification;
import com.ztesoft.net.mall.core.service.IProductManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-25 10:25
 * To change this template use File | Settings | File Templates.
 */
public class ProudctServ implements ProductInf {


    @Resource
    private IProductManager productManager;


    @Override
	public void add(List<Product> productList, String goodsId) {
       this.productManager.add(productList,goodsId);
    }

    @Override
	public Product get(String productid) {
        return this.productManager.get(productid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public Product getByGoodsId(String goodsid) {
        return this.productManager.getByGoodsId(goodsid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List<Specification> listSpecs(String goods_id) {
        return this.productManager.listSpecs(goods_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public Page list(String name, String sn, int pageNo, int pageSize, String order) {
        return this.productManager.list(name,sn,pageNo,pageSize,order);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List list(String[] productids) {
        return this.productManager.list(productids);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List<Product> list(String goods_id) {
        return this.productManager.list(goods_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void delete(String[] goodsid) {
        this.productManager.delete(goodsid); //To change body of implemented methods use File | Settings | File Templates.
    }
}