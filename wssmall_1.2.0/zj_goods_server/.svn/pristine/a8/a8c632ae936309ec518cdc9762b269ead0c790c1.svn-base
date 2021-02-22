package com.ztesoft.net.outter.inf.taobao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.PurchaseOrder;
import com.taobao.api.request.FenxiaoOrdersGetRequest;
import com.taobao.api.response.FenxiaoOrdersGetResponse;
import com.ztesoft.net.model.Outer;

public class TaobaoFenXiaoErrorService extends AbsTaobaoFenXiaoService{

	@Override
	public List<Outer> executeInfService(String start_time, String end_time,
			Map params, String order_from) throws Exception {
		String url = String.valueOf(params.get("url"));
		String appkey = String.valueOf(params.get("appkey"));
		String secret = String.valueOf(params.get("secret"));
		String sessionKey = String.valueOf(params.get("sessionKey"));
		List<Map> list = outterECSTmplManager.listNotSyncError(order_from);
		List<Outer> outerList = new ArrayList<Outer>();
		for(Map oe:list){
			try{
				TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
				FenxiaoOrdersGetRequest req=new FenxiaoOrdersGetRequest();
				req.setTimeType("update_time_type");//可选值：trade_time_type(采购单按照成交时间范围查询),update_time_type(采购单按照更新时间范围查询)
				req.setPageNo(1l);
				req.setPageSize(10l);
				req.setTcOrderId(Long.parseLong(String.valueOf(oe.get("tid"))));//分销订单ID
				FenxiaoOrdersGetResponse response = client.execute(req , sessionKey);
				if(response.isSuccess() && response.getPurchaseOrders()!=null && response.getPurchaseOrders().size()>0){
					PurchaseOrder p = response.getPurchaseOrders().get(0);
					String out_package_id = (String) oe.get("outer_pagkage_id");
					String sku = (String) oe.get("outer_sku_id");
					Outer o = packageOuter(p,order_from,end_time,out_package_id,sku);
					if(o!=null) outerList.add(o);
				}
			}catch(Exception ex){
				continue;
			}
		}
		return outerList;
	}

	@Override
	public void callback(List<Outer> list) {
		if(list!=null && list.size()>0){
			for(Outer c:list){
				outterECSTmplManager.updateErrorDelFlag(c.getOrder_from(), c.getReserve9(),"1");
			}
		}
	}

}
