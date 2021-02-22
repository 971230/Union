package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.cache.common.GoodsNetCacheRead;
import com.ztesoft.net.cache.common.GoodsNetCacheWrite;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Brand;
import com.ztesoft.net.mall.core.model.BrandModel;
import com.ztesoft.net.mall.core.model.mapper.BrandModelMapper;
import com.ztesoft.net.mall.core.service.IBrandModelManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

public class BrandModelManager extends BaseSupport<BrandModel> implements IBrandModelManager {
	private GoodsNetCacheRead read = new GoodsNetCacheRead();
	private GoodsNetCacheWrite write = new GoodsNetCacheWrite();
	
	@Override
	public Page getBrandModelList(int page,int pageSize,String brand_name,String machine_name,String model_name){
		String sql = SF.goodsSql("GOODS_TYPE_BRAND_MODEL_LIST");
		if(brand_name!=null){
			sql+=" and t.brand_name like '%"+brand_name+"%' ";
		}
		if(machine_name!=null){
			sql+=" and t.machine_name like '%"+machine_name.trim()+"%' ";
		}
		if(model_name!=null){
			sql+=" and t.model_name = '"+model_name.trim()+"' ";
		}
		
		return this.baseDaoSupport.queryForPage(sql, page, pageSize);
	}
	
	@Override
	public List<BrandModel> getBrandModelListAll(String model_code) {
		String sql = SF.goodsSql("GOODS_TYPE_BRAND_MODEL_LIST");
		
		if (StringUtils.isNotEmpty(model_code) && !"-1".equals(model_code)) {
			sql += " and t.model_code = '" + model_code.trim() + "'";
		}
		
		return this.baseDaoSupport.queryForList(sql, new BrandModelMapper());
	}

	@Override
	public Page getBrandModelListECS(int pageNo, int pageSize,
			BrandModel brandModel) {
		String brand_name = brandModel.getBrand_name();
		String model_name = brandModel.getModel_name();
		
		String sql = SF.goodsSql("BrandModelListECS");
		
		List sqlParams = new ArrayList();
		
		if(StringUtils.isNotEmpty(brand_name)){
			sql += " and a.brand_name like ?";
			sqlParams.add("%"+brand_name+"%");
		}
		
		if(StringUtils.isNotEmpty(model_name)){
			sql += " and a.model_name like ? ";
			sqlParams.add("%"+model_name+"%");
		}
		
		sql += " order by a.create_date desc";
				
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, BrandModel.class, sqlParams.toArray());
		
		
		return page;
	}

	@Override
	public boolean saveOrUpdateBrandMode(BrandModel brandModel) {
		String brand_model_id = brandModel.getBrand_model_id();
//		brandModel.setModel_code(brandModel.getModel_name());
		
		if(StringUtils.isNotEmpty(brand_model_id)){
			//更新
			Map where=new HashMap();
			where.put("brand_model_id", brand_model_id);
			this.baseDaoSupport.update("es_brand_model", brandModel,where);
		}else{
			//新增
			brand_model_id = this.baseDaoSupport.getSequences("S_ES_BRAND_MODEL");
			brandModel.setBrand_model_id(brand_model_id);
			brandModel.setCreate_date(DBTUtil.current());
			this.baseDaoSupport.insert("es_brand_model", brandModel);
		}
		write.loadBrandModel();
		return true;
	}

	@Override
	public Page getBrandList(Brand brand,int pageNo, int pageSize) {
		
		String sql = SF.goodsSql("BRAND_MODEL_BRAND_LIST");
		
		List sqlParams = new ArrayList();
		if(StringUtils.isNotEmpty(brand.getName())){
			sql += " and e.name like ?";
			sqlParams.add("%"+brand.getName()+"%");
		}
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, sqlParams.toArray());
		
		return page;
	}

	@Override
	public BrandModel getbrandModelDetail(String brand_model_id) {
		String sql = SF.goodsSql("BRAND_MODEL_DETAIL");
		BrandModel brandModel =  this.baseDaoSupport.queryForObject(sql, BrandModel.class, brand_model_id);
		return brandModel;
	}

	@Override
	public boolean checkModelName(String model_name) {
		String sql = SF.goodsSql("BRAND_MODEL_BY_NAME");
		List<BrandModel> list =  this.baseDaoSupport.queryForList(sql, BrandModel.class, model_name);
		if(list!=null && list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void doPublish(Map params) {

		String org_id_str = ManagerUtils.getStrValue(params, "orgIds");
		this.doCoQueue(org_id_str);  //全量发布
		
	}
	
	@Override
	public void doBatchPublish(Map params) {
		this.doCoQueue(params); 
	}
	
	/**
	 * 写同步消息队列
	 * @param org_id_str  销售组织标识串
	 */
	private void doCoQueue(String org_id_str) {
		
		//写消息队列
		CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
		coQueueAddReq.setCo_name("品牌型号同步");
		coQueueAddReq.setBatch_id("-1");
		coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_XINGHAO);
		coQueueAddReq.setAction_code(Consts.ACTION_CODE_A);
		coQueueAddReq.setObject_id("-1");
		coQueueAddReq.setObject_type("XINGHAO");
		coQueueAddReq.setContents("-1");
		coQueueAddReq.setOrg_id_str("10008");
		coQueueAddReq.setOrg_id_belong("10008");  //新商城
		coQueueAddReq.setSourceFrom(ManagerUtils.getSourceFrom());
		coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		client.execute(coQueueAddReq, CoQueueAddResp.class);
	}
	
	/**
	 *  * @author zengxianlian
	 * 写同步消息队列
	 * ps: 品牌型号只会同步给新商城1.0,2.0...而对于1.0商城是公用的...所以....全写10008
	 * @param org_id_str  销售组织标识串
	 */
	private void doCoQueue(Map params) {
		String[] brandModelIds =  ((String) params.get("brandModelIds")).split(",");
		String[] pids = ((String) params.get("orgPid")).split(",");
		JSONObject jsonObj = JSONObject.fromObject(params.get("orgJson"));
		for(String bmId : brandModelIds){
			for(String orgId : pids){
				//非新商城1.0/2.0都不插队列
				if(!"10008".equals(orgId) && !"100082".equals(orgId)){
					continue;
				}
				//写消息队列
				CoQueueAddReq coQueueAddReq = new CoQueueAddReq();
				coQueueAddReq.setCo_name("品牌型号同步");
				//coQueueAddReq.setBatch_id("-1");
				String batch_id = this.daoSupport.getSequences("S_ES_CO_BATCH_ID");
				coQueueAddReq.setBatch_id(batch_id);
				coQueueAddReq.setService_code(Consts.SERVICE_CODE_CO_XINGHAO);
				//已同步过的就变成修改
				if(checkIsSynModel(bmId,orgId)){
					coQueueAddReq.setAction_code(Consts.ACTION_CODE_M);
				}else{
					coQueueAddReq.setAction_code(Consts.ACTION_CODE_A);
				}
				coQueueAddReq.setObject_id(bmId);
				coQueueAddReq.setObject_type("XINGHAO");
				coQueueAddReq.setContents("-1");
				coQueueAddReq.setOrg_id_str(jsonObj.getString(orgId));
				//coQueueAddReq.setOrg_id_str(orgId);
				coQueueAddReq.setOrg_id_belong(orgId);  //新商城
				coQueueAddReq.setSourceFrom(ManagerUtils.getSourceFrom());
				coQueueAddReq.setOper_id(ManagerUtils.getAdminUser().getUserid());
				
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				client.execute(coQueueAddReq, CoQueueAddResp.class);
			}
		}
		
	}
	
	private boolean checkIsSynModel(String brand_model_id,String belong_id){
		String sql  = "select count(t.object_id)  from es_co_queue_bak t where t.object_id=? and t.action_code=? and t.org_id_belong=? and t.service_code=?";
		int count = this.baseDaoSupport.queryForInt(sql,brand_model_id,"A",belong_id,"CO_XINGHAO");
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkUsed(String brand_model_id) {
		
		if (true) return false;
		if (StringUtils.isEmpty(brand_model_id)) {
			return false;
		}
		
		String sql  = SF.goodsSql("GOODS_BRAND_CHECK_USED") 
				+ " and exists(select 1 from es_brand_model "
				+ " where es_goods.model_code = model_code "
				+ " and brand_model_id in(" + brand_model_id + "))";
		int count = this.baseDaoSupport.queryForInt(sql);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void doDelete(String brand_model_id) {
		
		if (StringUtils.isEmpty(brand_model_id)) {
			return;
		}
		
		String sql = SF.goodsSql("GOODS_BRAND_MODEL_DELETE") 
				+ " and brand_model_id in (" + brand_model_id + ")";
		this.baseDaoSupport.execute(sql);
	}

	@Override
	public List<BrandModel> listModelByBrandId(String brand_id) {
		String sql = SF.goodsSql("BRAND_MODEL_LIST");
		List<BrandModel> models = this.baseDaoSupport.queryForList(sql, BrandModel.class, brand_id);
		return models;
	}

	@Override
	public List<BrandModel> listCacheModelByBrandId(String brand_id) {
		List<BrandModel> models = read.getBrandModelByBrandId(brand_id);
		return models;
	}

	@Override
	public List<BrandModel> listCacheModelByModel(String model_code) {
		List<BrandModel> models = read.getBrandModelByModelCode(model_code);
		return models;
	}

	@Override
	public boolean checkIsExistsModelCode(String model_codel) {
		String sql  = " select count(*) from es_brand_model t where t.model_code=?";
		int count = this.baseDaoSupport.queryForInt(sql,model_codel);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

}
