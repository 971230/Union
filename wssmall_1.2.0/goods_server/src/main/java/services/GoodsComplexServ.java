package services;

import com.ztesoft.net.mall.core.model.GoodsComplex;
import com.ztesoft.net.mall.core.service.IGoodsComplexManager;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-24 15:58
 * To change this template use File | Settings | File Templates.
 */
public class GoodsComplexServ implements GoodsComplexInf {
    @Resource
    private IGoodsComplexManager goodsComplexManager;

    @Override
	public List listAllComplex(String goods_id) {
        return this.goodsComplexManager.listAllComplex(goods_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public List listComplex(String goods_id) {
        return this.goodsComplexManager.listComplex(goods_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
	public void addCoodsComplex(GoodsComplex complex) {
       this.goodsComplexManager.addCoodsComplex(complex);
    }

    @Override
	public void globalGoodsComplex(String goods_1, List<GoodsComplex> list) {
        this.goodsComplexManager.globalGoodsComplex(goods_1,list);
    }
}
