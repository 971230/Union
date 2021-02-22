package com.ztesoft.net.outter.inf.taobao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.api.domain.Trade;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.ztesoft.net.model.Outer;

public class TaobaoErrorService extends AbsTaobaoService{

	@Override
	public List<Outer> executeInfService(String start_time, String end_time,
			Map params, String order_from) throws Exception {
		String url = String.valueOf(params.get("url"));
		String appkey = String.valueOf(params.get("appkey"));
		String secret = String.valueOf(params.get("secret"));
		String sessionKey = String.valueOf(params.get("sessionKey"));
		List<Map> list = outterECSTmplManager.listNotSyncError(order_from);
		List<Outer> outerList = new ArrayList<Outer>();
		boolean isJST = true;
		if(url.indexOf("gw.api.taobao.com")!=-1)
			isJST = false;
		for(Map oe:list){
			try{
				TradeFullinfoGetResponse resp = AbsTaobao.getFullTrade(Long.parseLong(String.valueOf(oe.get("tid"))), appkey, secret, sessionKey, url);
				Trade t = resp.getTrade();
				if(true || "SELLER_CONSIGNED_PART".equals(t.getStatus())
						|| "WAIT_SELLER_SEND_GOODS".equals(t.getStatus()) || "WAIT_BUYER_CONFIRM_GOODS".equals(t.getStatus())
						|| "TRADE_BUYER_SIGNED".equals(t.getStatus()) || "TRADE_FINISHED".equals(t.getStatus())){
					if(resp!=null && resp.getTrade()!=null){
						String out_package_id = (String) oe.get("outer_pagkage_id");
						String sku = (String) oe.get("outer_sku_id");
						Outer ot = packageTaobaoOrder(resp.getTrade(),end_time,appkey,secret,sessionKey,url,order_from,null,isJST,out_package_id,sku);
						if(ot!=null) outerList.add(ot);
					}
				}else{
					outterECSTmplManager.updateErrorDelFlag(order_from, t.getTid()+"","2");
				}
			}catch(Exception ex){
				continue;
			}
		}
		return outerList;
	}
	
	public List<Map> test(){
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("tid", "602685126589048");
		list.add(map);
		Map map2 = new HashMap();
		map2.put("tid", "604063535088491");
		list.add(map2);
		Map map3 = new HashMap();
		map3.put("tid", "610814750331994");
		list.add(map3);
		Map map4 = new HashMap();
		map4.put("tid", "615709946044271");
		list.add(map4);
		Map map5 = new HashMap();
		map5.put("tid", "621540588506374");
		list.add(map5);
		Map map6 = new HashMap();
		map6.put("tid", "622092894188744");
		list.add(map6);
		Map map7 = new HashMap();
		map7.put("tid", "622961062232928");
		list.add(map7);
		return list;
	}

	//@Override
	@Override
	public void callback(List<Outer> list) {
		if(list!=null && list.size()>0){
			for(Outer c:list){
				outterECSTmplManager.updateErrorDelFlag(c.getOrder_from(), c.getOut_tid(),"1");
			}
		}
	}
}
