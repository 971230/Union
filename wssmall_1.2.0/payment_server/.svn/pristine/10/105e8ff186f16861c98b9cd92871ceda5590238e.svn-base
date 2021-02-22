package com.ztesoft.net.mall.cmp;

import java.util.List;

import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.model.BaseBill;
import com.ztesoft.net.model.DepositBill;

/**
 * 翼支付对账
 * @作者 MoChunrun
 * @创建日期 2013-10-23 
 * @版本 V 1.0
 */
public class DataBaseToFile extends AbsDataToFile{

	@Override
	public boolean isExeScenes1() {
		return true;
	}

	@Override
	public boolean isExeScenes2() {
		return true;
	}

	@Override
	public boolean isExeScenes3() {
		return true;
	}
	
	@Override
	public String getType_code() {
		return Consts.PAY_DEPOSIT;
	}
	
	@Override
	public BaseBill packageBillHeader() {
		return null;
	}

	@Override
	/**
	 * 生成送给计费的对账数据，说明：（只送成功矫正的数据给计费）
	 */
	public void packageDetial(List<BaseBill> detials) {
		
		//矫正过成功订单 busi_type=606
		List<BaseBill> depositList = qryDepositBill(transDate,getType_code(),DepositBill.class);
		detials.addAll(depositList);
		
		//带返销的金额 busi_type=605
		if(null != refundList && !refundList.isEmpty()){
			detials.addAll(refundList);
		}
	}
	
}
