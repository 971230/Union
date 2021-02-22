package com.ztesoft.check.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.inf.AbstractCheckHander;
import com.ztesoft.check.inf.Check;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.sqls.SF;

/**
 * 商品是否存在校验
 * @author duan.shaochu
 *
 */
public class GoodsExistsCheck  extends AbstractCheckHander implements Check{

	/**
	 * 总商与沃财富:p_code、sn
	 * 淘宝:p_code、sn或goods_id
	 * 新商城:sku_id
	 */
	@Override
	public IdentifyResp validByCode(IdentifyReq identification) throws ApiBusiException{
		/**
		 * 总商与沃财富
		 *总部与淘宝是根据es_goods_package来关联商品的
		 *ZHKL	总部号卡类	p_code(goodscode) + sn(productcode)
		 *ZHYL	总部号卡合约类	p_code(ActivityCode) + sn(productcode)
		 *ZZDL	总部终端合约类	p_code(ActivityCode) + sn(ResourcesTypeId)
		 *ZSWK	总部上网卡商品	p_code(goodscode) + sn(productcode)
		 *ZLZD	总部裸终端商品	p_code(取空) + sn(ResourcesTypeId)
		 *ZPJL	总部配件商品	  p_code(取空) + sn(ResourcesTypeId) 
		 *
		 *淘宝订单归集时，如果订单里合约计划编码(pcode）和商家编码(sn)均不为空，
		 *则从es_goods_package表中匹配出goods_id；如果合约计划编码、商家编码中有任一个为空，
		 *则直接取其作为goods_id，从es_goods表中获取对应的商品。
		 *
		 *新商城直接根据sku_id查
		 **/
		IdentifyResp resp = new IdentifyResp();
		List<String> listParam = new ArrayList<String>();
		StringBuffer respMsg = new StringBuffer();
//		Map extMap = identification.getExtParam();
		String order_id = identification.getOrder_id();
		String pCode = "", sn = "", goodsId = "", skuId = "";
		try{
			goodsId = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getGoods_id();
			sn = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getSn();
			Map goodsSpecMap = CommonDataFactory.getInstance().getGoodsSpecMap(order_id,goodsId);
			if(!goodsSpecMap.isEmpty()){
				pCode = (String)goodsSpecMap.get(SpecConsts.P_CODE);
				sn = (String)goodsSpecMap.get(SpecConsts.GOODS_SN);
				skuId = (String)goodsSpecMap.get(SpecConsts.SKU);
			}else{
				sn = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getSn();
			}
		}catch(Exception e){
			throw new ApiBusiException("order_id["+order_id+"]检查商品是否存在校验失败,参数不正确."+e.getMessage());
		}

		
//		String trace_flow_id = getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
/*		if(null == extMap || extMap.size() == 0){
			throw new ApiBusiException("检查商品是否存在校验失败,参数不正确.");
			resp.setCode(EccConsts.IDENTI_0001);
			resp.setMsg("检查商品是否存在校验失败,参数不正确.");
			return resp;
		}*/
		
		try{
			//商品ID、合约编码、sn、SKUID
/*			if(extMap.containsKey("goods_id")){
				goodsId = extMap.get("goods_id").toString();
			}
			if(extMap.containsKey("p_code")){
				pCode = extMap.get("p_code").toString();
			}
			if(extMap.containsKey("sn")){
				sn = extMap.get("sn").toString();
			}
			if(extMap.containsKey("sku_id")){
				skuId = extMap.get("sku_id").toString();
			}*/
			StringBuffer sqlBuf = new StringBuffer();
			if(StringUtils.isNotEmpty(goodsId)){
				sqlBuf.append(SF.eccSql("GOODS_IS_EXISTS_0"));
				listParam.add(goodsId);
			}else if(StringUtils.isNotEmpty(skuId)){
				sqlBuf.append(SF.eccSql("GOODS_IS_EXISTS_1"));
				listParam.add(skuId);
			}else if(StringUtils.isNotEmpty(pCode) || StringUtils.isNotEmpty(sn)){
				sqlBuf.append(SF.eccSql("GOODS_IS_EXISTS_2"));
				if(StringUtils.isNotEmpty(pCode)){
					sqlBuf.append(" AND PKG.P_CODE = ?");
					listParam.add(pCode);
				}else{
					sqlBuf.append(" AND PKG.P_CODE IS NULL");
				}
				if(StringUtils.isNotEmpty(sn)){
					listParam.add(sn);
					sqlBuf.append(" AND PKG.SN = ?");
				}else{
					sqlBuf.append(" AND PKG.SN IS NULL");
				}
			}else{
				throw new ApiBusiException("order_id["+order_id+"]检查商品是否存在校验失败,参数不正确.");
/*				resp.setCode(EccConsts.IDENTI_9999);
				resp.setMsg("检查商品是否存在校验失败,参数不正确.");
				return resp;*/
			}
			if(null == baseDaoSupport){
				baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			}
			String rows = this.baseDaoSupport.queryForString(sqlBuf.toString(), listParam.toArray());
			if("0".equals(rows)){
				respMsg.append("order_id["+order_id+"]检查商品是否存在校验失败,商品不存在[p_code:").append(pCode).append(",sn:").append(sn).append(",goods_id:").append(goodsId).append("sku:").append(skuId).append("]");
				throw new ApiBusiException(respMsg.toString());
				
/*				resp.setCode(EccConsts.IDENTI_0003);
				resp.setMsg(respMsg.toString());*/
			}else{
				resp.setCode(EccConsts.IDENTI_SUCCESS);
				resp.setMsg("检查商品是否存在校验成功.");
			}
		}catch(Exception e){
			String expMessage =  CommonDataFactory.getInstance().getErrorStactMsg(e);
			throw new ApiBusiException("order_id["+order_id+"]检查商品是否存在校验失败," + expMessage);
/*			resp.setCode(EccConsts.IDENTI_9999);
			resp.setMsg("检查商品是否存在校验失败," + e.getMessage());*/
		}
		return resp;
	}

}
