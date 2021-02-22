package com.ztesoft.net.mall.cmp;

import java.util.StringTokenizer;

import com.ztesoft.net.mall.core.model.TransDetailVo;
import com.ztesoft.net.mall.core.model.TransMainVo;
public class CheckBill extends AbsCheckBill {		

	@Override
	public TransMainVo parseMain(String mainLine) {
		TransMainVo transMainVo = new TransMainVo();
		StringTokenizer strToken = new StringTokenizer(mainLine,"|");
        String shopNo = strToken.nextToken();							//商户号，校验文件的是否正确
        String transDate = strToken.nextToken();
        String transNum = strToken.nextToken();
        String transMoney = strToken.nextToken();
        transMainVo.setShopNo(shopNo);
        transMainVo.setTransDate(transDate);
        transMainVo.setTransNum(transNum);
        transMainVo.setTransMoney(transMoney);
		return transMainVo;
	}

	@Override
	public TransDetailVo parseDetial(String detialLine) {
		StringTokenizer strToken = new StringTokenizer(detialLine,"|");
        String requestId =strToken.nextToken(); 			//第一个字段为翼支付流水号
        String transId = strToken.nextToken(); 				//分销系统支付流水
        String orderId =  strToken.nextToken();  			//分销系统订单号
        String payType = strToken.nextToken(); 			//付费类型  001 支付，002为退款
        String amount =   strToken.nextToken(); 			//金额（分）
    	TransDetailVo transDetailVo = new TransDetailVo(requestId,transId,orderId,amount,payType);
    	return transDetailVo;
	}
}
