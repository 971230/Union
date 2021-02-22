package services;

import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import params.goodstype.req.GoodsTypeReq;
import params.goodstype.resp.GoodsTypeResp;

import java.util.List;


/**
 * 商品类型
 * 
 * @作者 wu.i
 * @创建日期 2013-9-23
 * @版本 V 1.0
 */
public interface GoodsTypeInf {
	public GoodsTypeResp getGoodsType(GoodsTypeReq goodsTypeReq);

    /**
     * 读取某个类型的属性信息定义
     *
     * @param type_id
     * @return
     */
    public List<Attribute> getAttrListByTypeId(String type_id);

    /**
     * 读取一个类型的信息
     * @param type_id
     * @return
     */
    public GoodsTypeDTO get(String type_id);

    public GoodsType getGoodsTypeById(String goods_id);

}