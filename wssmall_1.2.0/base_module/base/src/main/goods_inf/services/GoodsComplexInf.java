package services;

import com.ztesoft.net.mall.core.model.GoodsComplex;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-24 15:55
 * To change this template use File | Settings | File Templates.
 * 相关商品
 */
public interface GoodsComplexInf {
    /**
     * 读取某个商品所有的关联商品，包括其单向关联的，或其它商品双向关联它的。
     * @param goods_id
     * @return
     */
    public List listAllComplex(String goods_id);



    /**
     * 读取某个商品单项关联的商品
     * @param goods_id
     * @return
     */
    public List listComplex(String goods_id);



    public void addCoodsComplex(GoodsComplex complex);

    /**
     * 全局更新相关商品
     *
     * @param goods_1
     * @param list
     */
    public void globalGoodsComplex(String goods_1, List<GoodsComplex> list);
}
