package com.ztesoft.check.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.inf.AbstractCheckHander;
import com.ztesoft.check.inf.Check;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.sqls.SF;

/**
 * 货品是否同步WMS校验
 * @author duan.shaochu
 *
 */
public class ProductSyncWMSCheck extends AbstractCheckHander implements Check {

	/**
	 * 货品是否同步WMS校验
	 * @throws ApiBusiException 
	 */
	@Override
	public IdentifyResp validByCode(IdentifyReq identification) throws ApiBusiException {
		if(null == baseDaoSupport){
			baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		}
		IdentifyResp resp = new IdentifyResp();
		String order_id = identification.getOrder_id();
		try{
			String typeId = "";
			String is_physical_product = "0";
			String is_sendWms = "0";
			//货品
			List<OrderItemBusiRequest> orderItemBusiRequests = getOrderTree(order_id).getOrderItemBusiRequests();
			List<OrderItemProdBusiRequest> orderItemProds = new ArrayList<OrderItemProdBusiRequest>();
			for(OrderItemBusiRequest orderItem : orderItemBusiRequests){
				orderItemProds = orderItem.getOrderItemProdBusiRequests();
				for(OrderItemProdBusiRequest orderProd : orderItemProds){
					//货品大类
					String goods_id = orderProd.getProd_spec_goods_id();
					Map<String, String> goodsSpec = CommonDataFactory.getInstance().getGoodsSpecMap(order_id, goods_id);
					String goods_type = goodsSpec.get(SpecConsts.TYPE_ID);
					typeId = CommonDataFactory.getInstance().getProductSpec(order_id, goods_type, orderProd.getProd_spec_goods_id(), SpecConsts.TYPE_ID);
					//是否实物货品
					is_physical_product = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_PHYSICAL, typeId);
					if("1".equals(is_physical_product)){
						if("0".equals(queryProductIsSync2WMS(orderProd.getProd_spec_goods_id()))){
							throw new ApiBusiException("order_id["+order_id+"]检查货品同步WMS校验失败,货品goods_id:["+orderProd.getProd_spec_goods_id()+"]未同步WMS.");
/*							resp.setCode(EccConsts.IDENTI_0005);
							resp.setMsg("检查货品同步WMS校验失败,货品goods_id:["+orderProd.getProd_spec_goods_id()+"]未同步WMS.");
							return resp;*/
						}
					}
				}
			}
			//礼品
			List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			if(null != gifts && gifts.size() > 0){
				for(AttrGiftInfoBusiRequest gift : gifts){
					if("1".equals(gift.getIs_virtual())){
						if("0".equals(queryGiftIsSync2WMS(gift.getSku_id()))){
							throw new ApiBusiException("order_id["+order_id+"]检查货品同步WMS校验失败,货品sku:["+gift.getSku_id()+"]未同步WMS.");
/*							resp.setCode(EccConsts.IDENTI_0005);
							resp.setMsg("检查货品同步WMS校验失败,货品sku:["+gift.getSku_id()+"]未同步WMS.");
							return resp;*/
						}
					}
				}
			}
			resp.setCode(EccConsts.IDENTI_SUCCESS);
			resp.setMsg("检查货品同步WMS校验成功.");
		}catch(Exception e){
			String expMessage =  CommonDataFactory.getInstance().getErrorStactMsg(e);
			throw new ApiBusiException("order_id["+order_id+"]检查货品同步WMS校验失败,参数不正确." + expMessage);
/*			resp.setCode(EccConsts.IDENTI_9999);
			resp.setMsg("检查货品同步WMS校验失败,参数不正确.");*/
		}
		return resp;
	}


	
	private OrderTreeBusiRequest getOrderTree(String orderId){
		return CommonDataFactory.getInstance().getOrderTree(orderId);
	}
	
	/**
	 * 实物货品是否同步WMS
	 * @param goods_id
	 * @return
	 */
	private String queryProductIsSync2WMS(String goodsId){
		String isSend = "0";
		isSend = this.baseDaoSupport.queryForString(SF.eccSql("PRODUCT_IS_SYNC_WMS"), new Object[]{goodsId});
		return isSend;
	}
	
	/**
	 * 实物礼品是否同步WMS
	 * @param sku_id
	 * @return
	 */
	private String queryGiftIsSync2WMS(String skuId){
		String isSend = "0";
		isSend = this.baseDaoSupport.queryForString(SF.eccSql("GIFT_IS_SYNC_WMS"), new Object[]{skuId});
		return isSend;
	}

}
