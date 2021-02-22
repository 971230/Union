package services;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.service.IBrandManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-24 15:15
 * To change this template use File | Settings | File Templates.
 */
public class BrandServ implements BrandInf {

    @Resource
    private IBrandManager brandManager;

    @Override
	public boolean checkUsed(String ids) {
        return this.brandManager.checkUsed(ids);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public boolean checkname(String name, String brandid) {
        return this.brandManager.checkname(name,brandid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void add(Brand brand) {
       this.brandManager.add(brand);
    }

    @Override
	public void update(Brand brand) {
       this.brandManager.update(brand);
    }

    @Override
	public Page list(String order, int page, int pageSize) {
        return this.brandManager.list(order,page,pageSize);
    }

    @Override
	public Page listTrash(String order, int page, int pageSize) {
        return this.brandManager.listTrash(order,page,pageSize);
    }

    @Override
	public void revert(String bid) {
        this.brandManager.revert(bid);
    }

    @Override
	public void delete(String bid) {
        this.brandManager.delete(bid);
    }

    @Override
	public void clean(String bid) {
       this.brandManager.clean(bid);
    }

    @Override
	public List<Brand> list() {
        return this.brandManager.list();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List<Brand> listByTypeId(String typeid) {
        return this.brandManager.listByTypeId(typeid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List listByCatId(Integer catid) {
        return this.brandManager.listByCatId(catid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List listByGoodsId(String goodsid) {
        return this.brandManager.listByGoodsId(goodsid);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List groupByCat() {
        return this.brandManager.groupByCat();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public Brand get(String brand_id) {
        return this.brandManager.get(brand_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public Page getGoods(String brand_id, int pageNo, int pageSize) {
        return this.brandManager.getGoods(brand_id,pageNo,pageSize);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void insertBrandType(String brand_id, String type_id) {
        this.brandManager.insertBrandType(brand_id,type_id);
    }

    @Override
	public void updateBrandJoin(String type_id) {
        this.brandManager.updateBrandJoin(type_id);
    }
}
