package services;

import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.service.IWarehouseManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 10:18
 * To change this template use File | Settings | File Templates.
 */
public class WarehouseServ implements WarehouseInf {
    @Resource
    private IWarehouseManager warehouseManager;

    @Override
    public Warehouse findWarehouseByHoustId(String house_id) {
        return this.warehouseManager.findWarehouseByHoustId(house_id);
    }

    @Override
    public List<Warehouse> qrySupplierWareHouse() {
        return this.warehouseManager.qrySupplierWareHouse();
    }

    @Override
    public List warehouseList() {
        return this.warehouseManager.warehouseList();
    }
}
