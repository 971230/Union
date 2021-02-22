package com.ztesoft.test.dubbo;

import org.testng.annotations.Test;

import junit.framework.Assert;
import params.ZteResponse;

import com.ztesoft.api.ZteClient;
import com.ztesoft.remote.basic.params.req.AccBalanceRequest;
import com.ztesoft.remote.basic.params.req.CurrentPackageRequest;
import com.ztesoft.remote.basic.params.req.PackageUseRequest;
import com.ztesoft.remote.basic.params.req.PayFeeRecordsRequest;
import com.ztesoft.remote.basic.params.req.RealTimeFeeRequest;
import com.ztesoft.remote.basic.params.req.WalletBalanceRequest;
import com.ztesoft.remote.basic.params.resp.AccBalanceResponse;
import com.ztesoft.remote.basic.params.resp.CurrentPackageResponse;
import com.ztesoft.remote.basic.params.resp.PackageUseResponse;
import com.ztesoft.remote.basic.params.resp.PayFeeRecordsResponse;
import com.ztesoft.remote.basic.params.resp.RealTimeFeeResponse;
import com.ztesoft.remote.basic.params.resp.WalletBalanceResponse;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class FeeQueryDubboClientTest extends DubboClientTest{
	/*
	 * 当前套餐查询
	 */
	@Test(enabled=true)
	public void qryCurrentPackage(){
		CurrentPackageRequest currentPackageRequest=new CurrentPackageRequest();
		currentPackageRequest.setProduct_id("");
		currentPackageRequest.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(currentPackageRequest, CurrentPackageResponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 实时话费
	 */
	public void realTimeFee(){
		RealTimeFeeRequest realTimeFeeRequest=new RealTimeFeeRequest();
		realTimeFeeRequest.setProduct_id("");
		realTimeFeeRequest.setAccNbr("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(realTimeFeeRequest, RealTimeFeeResponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 缴费记录
	 */
	public void payFeeRecords(){
		PayFeeRecordsRequest payFeeRecordsRequest=new PayFeeRecordsRequest();
		payFeeRecordsRequest.setProduct_id("");
		payFeeRecordsRequest.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(payFeeRecordsRequest, PayFeeRecordsResponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 钱包余额
	 */
	public void queryWalletBalance(){
		WalletBalanceRequest walletBalanceRequest=new WalletBalanceRequest();
		walletBalanceRequest.setProduct_id("");
		walletBalanceRequest.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(walletBalanceRequest, WalletBalanceResponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 套餐使用情况查询
	 */
	public void qryPackageUse(){
		PackageUseRequest packageUseRequest=new PackageUseRequest();
		packageUseRequest.setProduct_id("");
		packageUseRequest.setService_code("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(packageUseRequest, PackageUseResponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 账户余额
	 */
	public void accBalance(){
		AccBalanceRequest accBalanceRequest=new AccBalanceRequest();
		accBalanceRequest.setAcc_nbr("");
		accBalanceRequest.setArea_code("");
		accBalanceRequest.setProduct_id("");
		accBalanceRequest.setQuery_flag("");
		accBalanceRequest.setQuery_item_type("");
		accBalanceRequest.setService_code("");
		accBalanceRequest.setSource_from("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(accBalanceRequest, AccBalanceResponse.class);
		logger.info("---------"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
}
