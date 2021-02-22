/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import consts.ConstsCore;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.FeeInfoReqVo;


/**
 * @author shusx
 * @version 2016-05-10
 * @see 开户处理提交(套餐变更接口提交时使用)
 * 
 */
public class OpenDealSubmit1Req extends OpenDealSubmitReq {
//	public String getProvOrderId() {
//		List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderSubProductBusiRequest();
//		List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getAttrPackageSubProdBusiRequest();
//		if (attrPackageSubProdList != null && attrPackageSubProdList.size() > 0
//				&& subProductLists != null && subProductLists.size() > 0) {
//			for(OrderSubProductBusiRequest subProduct : subProductLists){
//				if(!StringUtils.equals("0",subProduct.getProd_inst_id()))continue;
//				for(AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList){ // 设置线上办理成功 
//					if(StringUtils.equals(subProduct.getSub_pro_inst_id(), subPackage.getSubProd_inst_id()) && (StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0, subPackage.getStatus()) 
//							|| StringUtils.equals(EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1, subPackage.getStatus()))){ // 取待办理、办理失败的记录
//						provOrderId = subPackage.getSub_bss_order_id();
//						if (!StringUtils.isEmpty(provOrderId))break;
//					}
//				}
//				if (!StringUtils.isEmpty(provOrderId))break;
//			}
//		}
//		return provOrderId;
//	}

	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}
	
	public List<FeeInfoReqVo> getFeeInfo() {
		return feeInfo;
	}

	public void setFeeInfo(List<FeeInfoReqVo> feeInfo) {
		this.feeInfo = feeInfo;
	}
	
	public String getOrigTotalFee() {
		origTotalFee = "0";
		return origTotalFee;
	}

	public void setOrigTotalFee(String origTotalFee) {
		this.origTotalFee = origTotalFee;
	}
}
