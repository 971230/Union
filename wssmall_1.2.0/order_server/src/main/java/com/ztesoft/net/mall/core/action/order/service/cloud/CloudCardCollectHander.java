package com.ztesoft.net.mall.core.action.order.service.cloud;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.collect.CommonCollectHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Cloud;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;


/**
 * 云卡采集
 * 
 * @author wui
 * 
 */
public class CloudCardCollectHander extends CommonCollectHander {

	/**
	 * 订单采集
	 *shop/admin/collect!addOrder
	 * 1、商品申请入口，调用地址为
	 */
	@Override
	public void collect() {
		super.collect();
	}

	@Override
	public void display() {
		super.display();
	}

	@Override
	public void execute() {
		
		if(OrderStatus.SOURCE_FROM_TAOBAO.equals(getOrderRequst().getGoodsApply().getSource_from())){ //外系统订单同步
			super.syOrder();
			
			//更新云卡订单
			if(!StringUtil.isEmpty(getOrderRequst().getOrderOuter().getAcc_nbr())){
				Cloud cloud = cloudManager.getCloudByAccNbr(getOrderRequst().getOrderOuter().getAcc_nbr());
				cloud.setSec_order_id(getOrderRequst().getOrderId());
				cloudManager.updateCloud(cloud);
			}
			
		}else{
			
			//事物控制，将方法锁定
			this.baseDaoSupport.execute(SF.orderSql("SERVICE_DC_PUBLIC_SELECT"),"CLOUD");
			
			//设置支付方式
			if(OrderStatus.SOURCE_FROM_AGENT_ONE.equals(getOrderRequst().getGoodsApply().getSource_from())){
				getOrderRequst().getGoodsApply().setPayment_id(Consts.PAYMENT_ID_BANK);
			}else if(OrderStatus.SOURCE_FROM_AGENT_TOW.equals(getOrderRequst().getGoodsApply().getSource_from())){
				getOrderRequst().getGoodsApply().setPayment_id(Consts.PAYMENT_ID_AUTO);
			}
			
			
			super.execute();
			super.addLog("商品申请:"+getOrderRequst().getGoodsApply().getApply_desc());
			
			
			//调拨处理
			//二级申请一级走此流程
			if(ManagerUtils.isSecondPartner()){
				cloudManager.transfer_cloud_for_collect(getOrderRequst(), getOrderResult());
			}
			
			setCanNext(false);
		}
	}
	
}
