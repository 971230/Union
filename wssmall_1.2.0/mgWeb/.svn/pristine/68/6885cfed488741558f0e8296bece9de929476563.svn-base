/*package com.ztesoft.test.dubbo;

import org.testng.Assert;
import org.testng.annotations.Test;

import params.ZteResponse;
import zte.params.store.req.GoodsInventoryCHGReq;
import zte.params.store.req.InventoryApplyLogReq;
import zte.params.store.req.WareHouseAddReq;
import zte.params.store.req.WareHouseModifyReq;
import zte.params.store.resp.GoodsInventoryCHGResp;
import zte.params.store.resp.InventoryApplyLogResp;
import zte.params.store.resp.WareHouseAddResp;
import zte.params.store.resp.WareHouseModifyResp;

import com.ztesoft.api.ZteClient;
import com.ztesoft.test.dubbo.base.DubboClientTest;

public class WarehouseClientTest extends DubboClientTest{
	//--------------------找不到服务------------
	public void queryApplyLogById(){
		InventoryApplyLogReq logReq = new InventoryApplyLogReq();
		logReq.setLog_id("201404163248000083");

		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(logReq, InventoryApplyLogResp.class);
		logger.info("异常单返回---"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	
	public void wareHouseAdd(){
		
		WareHouseAddReq req = new WareHouseAddReq();
		req.setHouse_code("1234567");
		req.setHouse_name("1234567");
		req.setHouse_desc("12344567");
		req.setAddress("123445667");
		req.setScope_code("1234456,121312,32323");

		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(req, WareHouseAddResp.class);
		
		logger.info("异常单返回---"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	
	@Test
    public void wareHouseModify(){
		
		WareHouseModifyReq req = new WareHouseModifyReq();
		req.setHouse_id("201405159431001462");
		req.setStatus("00X");

		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(req, WareHouseModifyResp.class);
		
		logger.info("异常单返回---"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
	
	
	public void goodsInventoryCHG(){
		
		GoodsInventoryCHGReq req = new GoodsInventoryCHGReq();
		req.setHouse_id("0");
		req.setProduct_id("201405061358002727");
		req.setSku("7400208277");
		req.setAction_code("0");
		req.setChg_num("10");
		req.setChg_reason("ceshiceshi1233333");

		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(req, GoodsInventoryCHGResp.class);
		
		logger.info("异常单返回---"+response.getError_msg());
		Assert.assertEquals(response.getError_code(), "0");
	}
}
*/