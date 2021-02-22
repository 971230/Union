package com.ztesoft.mall.core.action.backend;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.IEsGoodsCoManager;

public class EsGoodsCoAction extends BaseAction {

	private IEsGoodsCoManager esGoodsM;
	private String esgoodsco;

	public IEsGoodsCoManager getEsGoodsM() {
		return esGoodsM;
	}

	public void setEsGoodsM(IEsGoodsCoManager esGoodsM) {
		this.esGoodsM = esGoodsM;
	}
	
	public String getEsgoodsco() {
		return esgoodsco;
	}

	public void setEsgoodsco(String esgoodsco) {
		this.esgoodsco = esgoodsco;
	}

	public String goodsOrgandstat(){
		webpage = esGoodsM.getFormList(getPage(),getPageSize(),params);
		return "goods_organdstat";
	}
	
	public String goodsOrgCo(){
		webpage = esGoodsM.getFormList(getPage(),getPageSize(),params);
		List list = webpage.getResult();
		String org_names = "无发布组织";
		for(int i=0;i<list.size();i++){
			Map org = (Map)list.get(i);
			String org_name = (String)org.get("org_name");
			if(i>0){
				org_names += ",";
			}
			org_names += org_name;
		}
		this.json = "{'org_names':'"+org_names+"'}";
		logger.info("goodsOrgCo="+params.get("goodsId"));
		return WWAction.JSON_MESSAGE;
	}
	
	public String liberacion() {
		try {
			String result = esGoodsM.checkGoodsAgentByid(ids, params);
			if(StringUtils.isNotEmpty(result)) {
				this.json = "{'result':1,'message':'"+result+"'}";
			}else {
				esGoodsM.liberacion(ids, params);
				this.json = "{'result':0,'message':'操作成功'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{'result':1,'message':'操作失败'}";

		}
		return JSON_MESSAGE;

	}
	
	public String tagLiberacion() {
		try {
			esGoodsM.tagLiberacion(ids, params);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.json = "{'result':1,'message':'操作失败'}";
		}
		
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String salegoodsLiberacion() {
		try {
			String result = esGoodsM.checkAgentByid(ids, params);
			
			if(!"".equals(result)) {
				this.json = "{'result':1,'message':'外围系统--"+result+"--已下架或同步,无法重复操作'}";
				
				return WWAction.JSON_MESSAGE;
			}
			esGoodsM.salegoodsLiberacion(ids, params);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.json = "{'result':1,'message':'操作失败'}";
		}
		
		
		return WWAction.JSON_MESSAGE;
	}
}
