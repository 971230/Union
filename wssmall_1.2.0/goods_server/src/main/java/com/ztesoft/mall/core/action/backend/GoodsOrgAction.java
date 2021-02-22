package com.ztesoft.mall.core.action.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.IGoodsOrgManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class GoodsOrgAction  extends WWAction{
	private IGoodsOrgManager goodsOrgManager;
	private IGoodsManager goodsManager;
	private String org_id;
	private String org_name;
	private String party_id;
	private String org_code;
	private String parent_party_id;
	private String org_type_id;
	private String union_org_code;
	private String org_content;
	private String status_cd;
	private String goodsId;
	private String type;//发布类型：商品/货品
	private String ids;
	private String productos;
	private String esgoodscos;
	private String busqueda;
	private String listFormActionVal;
	private String relation_id;//货品包ID
	/**批量发布接收选择ids--zengxianlian*/
	private String brandModelIds;
	private String goodsIds;
	private String[] tag_ids;
	
	public String confirmPublish(){
		try {
			goodsOrgManager.publish(ids,type,goodsId);
			this.json = "{result:1,message:'发布成功！'}";
		} catch (Exception e) {
			logger.info(e);
		}
		return JSON_MESSAGE;
	}
	
	public String alreadyPublish(){
		try {
			List list=goodsOrgManager.alreadyPublish(goodsId);
			this.json = JSON.toJSONString(list);
		} catch (Exception e) {
			logger.info(e);
		}
		return JSON_MESSAGE;
	}
	
	//查询树
	public String qryTree(){
		List list = goodsOrgManager.qryTree();
		this.json = JSON.toJSONString(list);
		return JSON_MESSAGE;
	}
	//树对话框
	public String treeDialog(){
		return "treeDialog";
	}
	
	public String salegoodsQryTree() {
		List list = goodsOrgManager.saleQryTree();
		this.json = JSON.toJSONString(list);
		return JSON_MESSAGE;
	}
	
	public String tree(){
		return "tree";
	}
	
	//商品发布
	public String goodsPubtree(){
		
		this.listFormActionVal = "esgoodsco!liberacion.do?ajax=yes";
		if(!StringUtils.isEmpty(relation_id)){//如果货品包ID不为空，取货品包下的商品
			this.esgoodscos = "";
			List details = goodsManager.listRelationDetails(relation_id);
			for(int i=0;i<details.size();i++){
				Map detail = (Map) details.get(i);
				this.esgoodscos += Const.getStrValue(detail, "object_id");
				if(i<details.size()-1)
					this.esgoodscos += ",";
			}
		}
		return "goodsPubtree";
	}
	
	//销售商品同步
	public String salegoodsPubtree() {
		this.listFormActionVal = "esgoodsco!salegoodsLiberacion.do?ajax=yes";
		
		return "salegoodsPubtree";
	}
	/**
	 * @author zengxianlian
	 * 品牌型号发布
	 */
	public String brandModelPubtree(){
		return "brandModelPubtree";
	}
		
	/**
	 * @author zengxianlian
	 * 商品/货品公用发布
	 */
	public String productPubtree(){
		return "productPubtree";
	}
	
    public String getCurrOrg(){
		
		Map map = new HashMap();
		String login_org_id = ManagerUtils.getAdminUser().getOrg_id();
		List org_list = new ArrayList();
		if(StringUtils.isEmpty(login_org_id)) return "";
		
		if(Consts.ADMIN_ORG_ID.equals(login_org_id)){
			org_list = goodsOrgManager.getRootOrgs();
		}else{
			org_list = goodsOrgManager.getCurrOrgs(login_org_id);
		}
		if(!ListUtil.isEmpty(org_list)){
			for(int i=0; i< org_list.size(); i++){
				Map o_map = (Map)org_list.get(i);
				String code = Const.getStrValue(o_map, "party_id");
				
				int cnt = goodsOrgManager.qryChildrenOrgCounts(code);
				if( cnt > 0){
					o_map.put("state", "closed");
				}else{
					o_map.put("state", "open");
				}
			}
		}
		map.put("total", org_list.size());
		map.put("rows", org_list);
		this.json = JSON.toJSONString(map);
		return JSON_MESSAGE;
	}
	
	
	


	public String getChildrenOrg(){
			
			if(StringUtils.isEmpty(org_id)) return "error";
			
			List children_list = goodsOrgManager.getChildrenOrgs(org_id);
			
			if(!ListUtil.isEmpty(children_list)){
				for(int i=0; i< children_list.size(); i++){
					Map o_map = (Map)children_list.get(i);
					String code = Const.getStrValue(o_map, "party_id");
					
					int cnt = goodsOrgManager.qryChildrenOrgCounts(code);
					if( cnt > 0 ){
						o_map.put("state", "closed");
					}else{
						o_map.put("state", "open");
					}
				}
			}
			
			this.json = JSON.toJSONString(children_list);
			return WWAction.JSON_MESSAGE;
	}

	
	
	public String getOrgs(){
		return "org_list";
		
	}
	
	public String addOrg(){
		Map map = new HashMap();
		String flag = "1";
		String message = "新增成功";
		try{
			if(!goodsOrgManager.ifExistsOrg(org_code)){
				goodsOrgManager.addOrgs(parent_party_id, org_code, org_name, org_type_id, union_org_code, org_content);
			}else{
				flag = "0";
				message="存在相同的发布区域编码";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			flag = "0";
			message="新增异常"+e.getMessage();
		}
		map.put("flag", flag);
		map.put("message", message);
		this.json = JSON.toJSONString(map);
		return JSON_MESSAGE;
	}
	
	public String modOrg(){
		Map map = new HashMap();
		String flag = "1";
		String message = "修改成功";
		try{
			goodsOrgManager.updateOrgs(party_id, org_code, org_name, org_type_id, union_org_code, org_content);
		}catch(Exception e){
			e.printStackTrace();
			flag = "0";
			message="修改异常"+e.getMessage();
		}
		map.put("flag", flag);
		map.put("message", message);
		this.json = JSON.toJSONString(map);
		return JSON_MESSAGE;
	}
	
	public String delOrg(){
		try{
			int cnt = goodsOrgManager.qryChildrenOrgCounts(party_id);
			if (cnt > 0) {
				this.json = "存在下级发布区域";
				return  WWAction.JSON_MESSAGE;
			} else {
				
				int a = goodsOrgManager.delOrgs(party_id);
				if(a == 0){
					this.json = "删除成功";
				}else{
					this.json = "删除错误";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			this.json="删除异常";
			return JSON_MESSAGE;
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String getStatus_cd() {
		return status_cd;
	}


	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	
	public IGoodsOrgManager getGoodsOrgManager() {
		return goodsOrgManager;
	}
	public void setGoodsOrgManager(IGoodsOrgManager goodsOrgManager) {
		this.goodsOrgManager = goodsOrgManager;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getParty_id() {
		return party_id;
	}
	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getParent_party_id() {
		return parent_party_id;
	}
	public void setParent_party_id(String parent_party_id) {
		this.parent_party_id = parent_party_id;
	}
	public String getOrg_type_id() {
		return org_type_id;
	}
	public void setOrg_type_id(String org_type_id) {
		this.org_type_id = org_type_id;
	}
	public String getUnion_org_code() {
		return union_org_code;
	}
	public void setUnion_org_code(String union_org_code) {
		this.union_org_code = union_org_code;
	}
	public String getOrg_content() {
		return org_content;
	}
	public void setOrg_content(String org_content) {
		this.org_content = org_content;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getProductos() {
		return productos;
	}

	public void setProductos(String productos) {
		this.productos = productos;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public String getEsgoodscos() {
		return esgoodscos;
	}

	public void setEsgoodscos(String esgoodscos) {
		this.esgoodscos = esgoodscos;
	}

	public String getListFormActionVal() {
		return listFormActionVal;
	}

	public void setListFormActionVal(String listFormActionVal) {
		this.listFormActionVal = listFormActionVal;
	}

	public String getRelation_id() {
		return relation_id;
	}

	public void setRelation_id(String relation_id) {
		this.relation_id = relation_id;
	}

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}

	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public String getBrandModelIds() {
		return brandModelIds;
	}

	public void setBrandModelIds(String brandModelIds) {
		this.brandModelIds = brandModelIds;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}

	public String[] getTag_ids() {
		return tag_ids;
	}

	public void setTag_ids(String[] tag_ids) {
		this.tag_ids = tag_ids;
	}

	
}
