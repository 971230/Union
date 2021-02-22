package services;

import java.util.List;

import javax.annotation.Resource;

import params.goodstype.req.GoodsTypeReq;
import params.goodstype.resp.GoodsTypeResp;

import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;
import com.ztesoft.net.mall.core.service.impl.cache.IGoodsCacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;


/**
 * 通用服务类
 * @author wui
 *
 */
public  class GoodsTypeServ  extends ServiceBase implements GoodsTypeInf{
    @Resource
	private ICacheUtil cacheUtil;
    
    @Resource
   	private IGoodsCacheUtil goodsCacheUtil;

    @Resource
    private IGoodsManager goodsManager;

    @Resource
    private IGoodsTypeManager goodsTypeManager;
	
	
	@Override
	public GoodsTypeResp getGoodsType(GoodsTypeReq goodsTypeReq){
		GoodsTypeResp goodsTypeResp = new GoodsTypeResp();
		GoodsTypeDTO goodsType=(GoodsTypeDTO)goodsCacheUtil.getGoodsTypeById(goodsManager.getGoods(goodsTypeReq.getGoods_id()).getType_id());
		goodsTypeResp.setGoodsTypeDTO(goodsType);
		return goodsTypeResp;
	}

    @Override
	public List<Attribute> getAttrListByTypeId(String type_id) {
        return this.goodsTypeManager.getAttrListByTypeId(type_id);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GoodsTypeDTO get(String type_id) {
        return this.goodsTypeManager.get(type_id);
    }

    @Override
    public GoodsType getGoodsTypeById(String goods_id) {
        return null;
    }
}