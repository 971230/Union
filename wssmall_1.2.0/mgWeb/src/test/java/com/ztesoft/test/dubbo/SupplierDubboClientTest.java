package com.ztesoft.test.dubbo;

import org.testng.Assert;
import org.testng.annotations.Test;

import params.ZteResponse;
import params.suppler.resp.SupplierListResp;
import zte.params.supplier.req.SupplierGetReq;
import zte.params.supplier.req.SupplierListReq;
import zte.params.supplier.resp.SupplierGetResp;

import com.ztesoft.api.ZteClient;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class SupplierDubboClientTest extends DubboClientTest{
	//-------------成功---------------
	
	public void listSupplier(){
		SupplierListReq supplierListReq=new SupplierListReq();
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(supplierListReq, SupplierListResp.class);
		Assert.assertEquals(response.getError_code(),"0");
	}
	//-------------成功---------------
	@Test
	public void getSupplier(){
		SupplierGetReq supplierGetReq=new SupplierGetReq();
		supplierGetReq.setCompany_name("江西奥通信息产业有限公司");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(supplierGetReq, SupplierGetResp.class);
		Assert.assertEquals(response.getError_code(),"0");
	}
}
