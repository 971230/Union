package com.ztesoft.net.mall.core.service.impl.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.adminuser.req.SinLoginReq;
import params.adminuser.resp.SinLoginResp;
import services.SinLoginInf;

import com.ztesoft.net.framework.cache.AbstractCacheProxy;
import com.ztesoft.net.framework.cache.CacheFactory;

public class SinLoginInfoCacheProxy extends AbstractCacheProxy<List> {
	private SinLoginInf sinLoginServ;

	public static final String LIST_KEY_PREFIX = "sin_login_info_list";

	public SinLoginInfoCacheProxy(SinLoginInf sinLoginServ) {

		super(CacheFactory.SINGLE_LOGIN_CACHE_NAME_KEY);
		this.sinLoginServ = sinLoginServ;
	}

	/**
	 * 获取es_single_login配置信息
	 * 
	 * @param cfId
	 */
	@SuppressWarnings("unchecked")
	public List getList(String stype) {
		List list=new ArrayList();
		// 首先获取列表
		List<Map> sinLists = this.cache.get(LIST_KEY_PREFIX);
		if (sinLists == null || sinLists.isEmpty()) {
			SinLoginResp sinLoginResp = new SinLoginResp();
			SinLoginReq sinLoginReq = new SinLoginReq();
			sinLoginResp = this.sinLoginServ.list(sinLoginReq);
			sinLists = new ArrayList<Map>();
			if(sinLoginResp != null){
				sinLists = sinLoginResp.getList();
			}
			this.cache.put(LIST_KEY_PREFIX, sinLists);
		}
		
		// 得到列表后获取key值
		for(Map sinMap:sinLists){
			if(stype.equals(sinMap.get("stype")+"")){
				list.add(sinMap);
			}
		}
		
		return list;
	}

	/**
	 * 
	 * 刷新缓存方法，可添加其他数据表缓存刷新
	 */
	public void refreshCache() {
		SinLoginResp sinLoginResp = new SinLoginResp();
		SinLoginReq sinLoginReq = new SinLoginReq();
		sinLoginResp = this.sinLoginServ.list(sinLoginReq);
		List sinLoginList = new ArrayList<Map>();
		if(sinLoginResp != null){
			sinLoginList = sinLoginResp.getList();
		}
		this.cache.put(LIST_KEY_PREFIX, sinLoginList);
	}



}