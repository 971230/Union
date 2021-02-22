package services;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.Specification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-25 10:24
 * To change this template use File | Settings | File Templates.
 * 货品管理
 */
public interface ProductInf {

    // 批量添加货品
    public void add(List<Product> productList ,String goodsId);


    /**
     * 读取货品详细
     * @param productid
     * @return
     */
    public Product get(String productid);


    /**
     * 读取某个商品的货品，一般用于无规格商品或捆绑商品
     * @param goodsid
     * @return
     */
    public Product getByGoodsId(String goodsid);


    /**
     * 查询某个商品的规格
     *
     * @param goods_id
     * @return
     */
    public List<Specification> listSpecs(String goods_id);

    /**
     * 分页列出全部货品<br/>
     * lzf add
     *
     * @return
     */
    public Page list(String name,String sn,int pageNo, int pageSize, String order);


    /**
     * 根据一批货品id读取货品列表
     * @param productids 货品id数组，如果为空返回 空list
     * @return 货品列表
     */
    public List list(String[] productids);


    /**
     * 查询某个商品的货品
     *
     * @param goods_id
     * @return
     */
    public List<Product> list(String goods_id);


    /**
     * 删除某商品的所有货品
     * @param goodsid
     */
    public void delete(String[] goodsid);

}
