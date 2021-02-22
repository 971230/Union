package com.ztesoft.test.rop;


import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import params.ZteResponse;
import zte.params.order.req.OrderAddReq;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.api.ZteClient;
import com.ztesoft.test.rop.base.RopClientTest;

public class OrderRopClientTest extends RopClientTest{
	
	/* 通过 */
	@SuppressWarnings("unchecked")
	@Test
	public void addOrder(){
		OrderAddReq orderAddReq = new OrderAddReq();
		
		List<Map> paramsl =new ArrayList<Map>();
		Map param = new HashMap();
		param.put("product_id", "201402246578001832");
		param.put("goods_num", "1");
		param.put("name", "吴辉");
		param.put("app_code", appKey);
		param.put("acc_nbr", "13318717285");
		paramsl.add(param);
		orderAddReq.setService_code("3");
		orderAddReq.setParamsl(paramsl);
		ZteClient client=getRopZteClient();
        ZteResponse resp = client.execute(orderAddReq, OrderAddResp.class);
        logger.info("mesg:"+resp.getError_msg());
        logger.info("此次调用结果:"+resp.isResult());
	}
}
