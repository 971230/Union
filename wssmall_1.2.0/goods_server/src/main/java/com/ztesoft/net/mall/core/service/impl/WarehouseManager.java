package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zte.net.ecsord.common.CommonDataFactory;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.goods.req.IncreaseInventoryNumReq;
import zte.params.goods.req.SubtractInventoryNumReq;
import zte.params.goods.resp.IncreaseInventoryNumResp;
import zte.params.goods.resp.SubtractInventoryNumResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.GoodsInventoryApply;
import com.ztesoft.net.mall.core.model.GoodsInventoryApplyLog;
import com.ztesoft.net.mall.core.model.GoodsInventoryLog;
import com.ztesoft.net.mall.core.model.GoodsInventoryM;
import com.ztesoft.net.mall.core.model.ProductCompany;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.model.VirtualWarehouse;
import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.service.IWarehouseManager;
import com.ztesoft.net.mall.core.utils.GoodsManagerUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

public class WarehouseManager extends BaseSupport implements IWarehouseManager {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	private ICoQueueManager coQueueManager;
	
	public ICoQueueManager getCoQueueManager() {
		return coQueueManager;
	}

	public void setCoQueueManager(ICoQueueManager coQueueManager) {
		this.coQueueManager = coQueueManager;
	}

	@Override
	public List checkVistualStoreSize(String house_id){
		String sql = SF.goodsSql("CHECKVIRTUALSTORESIZE");
		List list = this.baseDaoSupport.queryForList(sql, house_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public List checkStoreSize(String house_id){
		String sql = SF.goodsSql("CHECKSTORESIZE");
		List list = this.baseDaoSupport.queryForList(sql, house_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public List getProvinceList(){
		String sql = SF.goodsSql("MEMBER_PROVINCE_REGION");
		List list = this.baseDaoSupport.queryForList(sql, Regions.class);
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public List getFirstList(String id){
		String sql = SF.goodsSql("GOODS_ORG_FIRST");
		List list = this.baseDaoSupport.queryForList(sql,id);//-1代表根节点
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public List getCityList(){
		String sql = SF.goodsSql("MEMBER_CITY_REGION");
		List list = this.baseDaoSupport.queryForList(sql, Regions.class);
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public List getCountryListById(String region_id){
		String sql = SF.goodsSql("MEMBER_COUNTRY_REGION");
		List list = this.baseDaoSupport.queryForList(sql, Regions.class,region_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public void addWarehouse(Warehouse warehouse) {
		this.baseDaoSupport.insert("es_warehouse", warehouse);
	}
	
	@Override
	public void addGoodsInventory(GoodsInventoryM gim) {
		String source_from = ManagerUtils.getSourceFrom();
		gim.setNo_apply_num(gim.getInventory_num());//默认全部未分配
		gim.setApply_num(0);//(Integer.valueOf(gim.getInventory_num())-Integer.valueOf(gim.getNo_apply_num()));
		gim.setStatus_date(DBTUtil.getDBCurrentTime());
		gim.setSource_from(source_from);
		this.baseDaoSupport.insert("es_goods_inventory", gim);
		
		//记录日志
		GoodsInventoryLog gilog = new GoodsInventoryLog();
		gilog.setLog_id(this.baseDaoSupport.getSequences("S_ES_GOODS_INVENTORY_LOG"));
		gilog.setProduct_id(gim.getProduct_id());
		gilog.setAction("1");//入库
		gilog.setApply_num(gim.getApply_num());
		gilog.setChange_num(0);
		if(StringUtil.isEmpty(gim.getChange_reason())){
			gilog.setChange_reason("创建货品管理库");
		}else{
			gilog.setChange_reason(gim.getChange_reason());
		}
		gilog.setHouse_id(gim.getHouse_id());
		gilog.setInventory_num(gim.getInventory_num());
		gilog.setNo_apply_num(gim.getInventory_num());
		gilog.setSku(gim.getSku());
		gilog.setStatus_date(DBTUtil.getDBCurrentTime());
		gilog.setSource_from(source_from);
		this.baseDaoSupport.insert("es_goods_inventory_log", gilog);
	}

	@Override
	public void editGoodsInventory(GoodsInventoryM gim,String encoming,Integer changeStoreNum) {
		GoodsInventoryLog gilog = new GoodsInventoryLog();
		if(changeStoreNum==null){
			changeStoreNum=0;
		}
		if(Consts.INVENTORY_ACTION_1.equals(encoming)){//入库
			gilog.setAction(Consts.INVENTORY_ACTION_1);
			gilog.setChange_reason("入库操作");
		}else{//出库
			gilog.setAction(Consts.INVENTORY_ACTION_0);
			gilog.setChange_reason("出库操作");
			if(changeStoreNum>0){
				changeStoreNum = 0 - changeStoreNum;
			}
		}
		String sql = SF.goodsSql("UPDATEGOODSINVENTORY");
		this.baseDaoSupport.execute(sql, changeStoreNum, changeStoreNum, 
				gim.getProduct_id(), gim.getHouse_id());
		
		//日志的变动原因设置
		if (StringUtils.isNotEmpty(gim.getChange_reason())) {
			gilog.setChange_reason(gim.getChange_reason());  
		}
		
		//写日志
		gilog.setLog_id(this.baseDaoSupport.getSequences("S_ES_GOODS_INVENTORY_LOG"));
		Map map = getGoodsInventoryByProductIdAndHouseId(gim.getProduct_id(), gim.getHouse_id());
		gilog.setApply_num(Integer.valueOf(map.get("APPLY_NUM")!=null?map.get("APPLY_NUM").toString():"0")); // 已经分配库存
		gilog.setNo_apply_num(Integer.valueOf(map.get("NO_APPLY_NUM")!=null?map.get("NO_APPLY_NUM").toString():"0")); // 未分配库存
		gilog.setInventory_num(Integer.valueOf(map.get("INVENTORY_NUM")!=null?map.get("INVENTORY_NUM").toString():"0")); // 当前库存
		gilog.setProduct_id(gim.getProduct_id());  //货品标识
		gilog.setHouse_id(gim.getHouse_id());  //物理仓标识
		gilog.setSku(gim.getSku());  //货品SKU
		gilog.setChange_num(changeStoreNum);  //变动数量
		gilog.setSource_from(ManagerUtils.getSourceFrom());
		this.baseDaoSupport.insert("es_goods_inventory_log", gilog);
	}
	
	@Override
	public void addVirtualHouse(VirtualWarehouse virtualWarehouse) {
		virtualWarehouse.setHouse_id(this.baseDaoSupport.getSequences("S_ES_VIRTUAL_WAREHOUSE"));
		virtualWarehouse.setCreate_time(DBTUtil.getDBCurrentTime());
		virtualWarehouse.setStatus_date(DBTUtil.getDBCurrentTime());
		virtualWarehouse.setSource_from(ManagerUtils.getSourceFrom());
		this.baseDaoSupport.insert("es_virtual_warehouse", virtualWarehouse);
	}

	@Override
	public Warehouse findWarehouseByHoustId(String house_id) {
		String sql = SF.goodsSql("FIND_WARE_HOUSE_BY_HOUSTID");
		return (Warehouse) this.baseDaoSupport.queryForObject(sql, Warehouse.class, house_id);
	}
	
	@Override
	public List isExistsStore(String product_id,String house_id){
		String sql = SF.goodsSql("ISEXITSSTORE");
		List list = this.baseDaoSupport.queryForList(sql,product_id, house_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}
	
	@Override
	public int vcheckNameOrCode(String house_name,String house_code){
		String sourcefrom=ManagerUtils.getSourceFrom();
		String sqlName = SF.goodsSql("FIND_VHOUSE_BY_NAME");
		String sqlCode  = SF.goodsSql("FIND_VHOUSE_BY_CODE");
		List listName = this.baseDaoSupport.queryForList(sqlName,house_name,sourcefrom);
		if(listName.size()>0){
			return 1;
		}
		List listCode = this.baseDaoSupport.queryForList(sqlCode,house_code,sourcefrom);
		if(listCode.size()>0){
			return 2;
		}
		return 0;
	}
	
	@Override
	public int checkNameOrCode(String house_name,String house_code,String comp_code,String house_id){
		String sourcefrom=ManagerUtils.getSourceFrom();
		String sqlName = SF.goodsSql("FIND_HOUSE_BY_NAME");
		String sqlCode  = SF.goodsSql("FIND_HOUSE_BY_CODE");
		if(StringUtils.isEmpty(house_id)){//新增校验
			List listName = this.baseDaoSupport.queryForList(sqlName,house_name,sourcefrom);
			if(listName.size()>0){
				return 1;
			}
			List listCode = this.baseDaoSupport.queryForList(sqlCode,house_code,sourcefrom,comp_code);
			if(listCode.size()>0){
				return 2;
			}
		}else{//修改校验
			sqlName += " and w.house_id<>? ";
			sqlCode += " and w.house_id<>? ";
			List listName = this.baseDaoSupport.queryForList(sqlName,house_name,sourcefrom,house_id);
			if(listName.size()>0){
				return 1;
			}
			List listCode = this.baseDaoSupport.queryForList(sqlCode,house_code,sourcefrom,comp_code,house_id);
			if(listCode.size()>0){
				return 2;
			}
		}
		return 0;
	}
	
	@Override
	public int pcCheckNameOrCode(String company_name,String company_code,String company_id){
		String sourcefrom=ManagerUtils.getSourceFrom();
		String sqlName = SF.goodsSql("FIND_COMPANY_BY_NAME");
		String sqlCode  = SF.goodsSql("FIND_COMPANY_BY_CODE");
		if(StringUtils.isEmpty(company_id)){
			List listName = this.baseDaoSupport.queryForList(sqlName,company_name,sourcefrom);
			if(listName.size()>0){
				return 1;
			}
			List listCode = this.baseDaoSupport.queryForList(sqlCode,company_code,sourcefrom);
			if(listCode.size()>0){
				return 2;
			}
		}else{
			sqlName += " and w.comp_id<>? ";
			sqlCode += " and w.comp_id<>? ";

			List listName = this.baseDaoSupport.queryForList(sqlName,company_name,sourcefrom,company_id);
			if(listName.size()>0){
				return 1;
			}
			List listCode = this.baseDaoSupport.queryForList(sqlCode,company_code,sourcefrom,company_id);
			if(listCode.size()>0){
				return 2;
			}
		}
		return 0;
	}
	
	@Override
	public Page searchHouseList(int pageNo,int pageSize){
		String sql = SF.goodsSql("HOUSENAMELIST");
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}
	
	@Override
	public Page searchGoodsInventory(String house_id,String name,int pageNo,int pageSize){
		String sql = SF.goodsSql("GOODSINVENTORYM");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(house_id)){
			sqlAccount.append(" and g.house_id = ? ");
			partm.add(house_id);
		}
		if(StringUtils.isNotEmpty(name)){
			sqlAccount.append(" and p.name like ? ");
			partm.add("%"+name+"%");
		}
		sqlAccount.append(" order by g.status_date desc");
		sql=sql+sqlAccount.toString();
		
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
	}
	
	@Override
	public Page searchVirtualList(String house_name,String attr_inline_type,String status, int pageNo,int pageSize){
		String sql = SF.goodsSql("VIRTUALHOUSE_GET_ALL");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(house_name)){
			sqlAccount.append(" and a.house_name like ? ");
			partm.add("%"+house_name+"%");
		}
		if(StringUtils.isNotEmpty(attr_inline_type)){
			sqlAccount.append(" and a.attr_inline_type = ? ");
			partm.add(attr_inline_type);
		}
		if(StringUtils.isNotEmpty(status)){
			sqlAccount.append(" and a.status = ? ");
			partm.add(status);
		}
		
		sqlAccount.append(" order by create_time desc");
		sql=sql+sqlAccount.toString();
		
		return this.daoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
	}
	
	@Override
	public Page searchList(Map<String,String> query_params, int pageNo,int pageSize){
		String house_name = query_params.get("house_name");
		String attribution = query_params.get("attribution");
		String status = query_params.get("status");
		String comp_code_sel = query_params.get("comp_code_sel");
		String comp_name_sel = query_params.get("comp_name_sel");
		
		String sql = SF.goodsSql("WAREHOUSE_GET_ALL");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(house_name)){
			sqlAccount.append(" and a.house_name like ? ");
			partm.add("%"+house_name+"%");
		}
		if(StringUtils.isNotEmpty(attribution)){
			sqlAccount.append(" and a.attribution = ? ");
			partm.add(attribution);
		}
		if(StringUtils.isNotEmpty(status)){
			sqlAccount.append(" and a.status = ? ");
			partm.add(status);
		}
		if(StringUtils.isNotEmpty(comp_code_sel)){
			sqlAccount.append(" and p.comp_code = ? ");
			partm.add(comp_code_sel);
		}
		if(StringUtils.isNotEmpty(comp_name_sel)){
			sqlAccount.append(" and p.comp_name like ? ");
			partm.add("%"+comp_name_sel+"%");
		}
		
		sqlAccount.append(" order by create_time desc");
		sql=sql+sqlAccount.toString();
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
		List list = page.getResult();
		//根据scope_code查询name
		String qryNameSql = SF.goodsSql("SCOPENAME_BY_CODE");
		for(int i=0;i<list.size();i++){
			Map result = (Map)list.get(i);
			String code = (String)result.get("scope_code");
			if(!"".equals(code)){
				List<Regions> regionsList = this.baseDaoSupport.queryForList(qryNameSql+" and region_id in ("+code+") order by region_id ", Regions.class);
				String scope_name = "";
				for(Regions r:regionsList){
					scope_name+=r.getLocal_name();
				}
				result.put("scope_name", scope_name);
			}
		}
		return page;
	}
	
	@Override
	public Page searchProductList(String product_name,String sn,String sku,int pageNo,int pageSize){
		String sql = SF.goodsSql("GOODS_PRODUCT");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(product_name)){
			sqlAccount.append(" and p.name like ? ");
			partm.add("%"+product_name+"%");
		}
		if(StringUtils.isNotEmpty(sn)){
			sqlAccount.append(" and p.sn = ? ");
			partm.add(sn);
		}
		if(StringUtils.isNotEmpty(sku)){
			sqlAccount.append(" and p.sku = ? ");
			partm.add(sku);
		}
		
		sqlAccount.append(" order by g.create_time desc");
		sql=sql+sqlAccount.toString();
		
		return this.daoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
	}

	@Override
	public Page search(String house_name, int pageNo, int pageSize) {
		String sql = SF.goodsSql("WAREHOUSE_GET_ALL");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(house_name)){
			sqlAccount.append(" and a.house_name like ?");
			partm.add("%"+house_name+"%");
		}
		
		sqlAccount.append(" order by create_time desc");
		sql=sql+sqlAccount.toString();
		
		return this.daoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
	}

	@Override
	public void editWarehouse(Warehouse warehouse) {
		Map where=new HashMap();
		where.put("house_id", warehouse.getHouse_id());
		warehouse.setHouse_id(null);
		this.baseDaoSupport.update("es_warehouse", warehouse, where);
	}

	@Override
	public void editVirtualHouse(VirtualWarehouse vh){
		String sql = SF.goodsSql("INSERT_VIRTUALHOUSE");
		this.baseDaoSupport.execute(sql, vh.getHouse_name(),
				vh.getHouse_code(), vh.getOrg_id_str(), vh.getOrg_name_str(),
				vh.getStatus(), DBTUtil.getDBCurrentTime(), vh.getHouse_desc(),
				vh.getOrg_id_belong(), vh.getAttr_inline_type(),
				vh.getHouse_id());
	}
	
	@Override
	public int delete(String house_id) {
		if (house_id == null || house_id.equals("")) {
			return 0;
		} else {
			String sql = SF.goodsSql("WAREHOUSE_DEL") + " and house_id in (" + house_id + ")";
			this.baseDaoSupport.execute(sql);
			return 1;
		}
	}

	@Override
	public boolean isWarehouseCodeExits(String house_code) {
		String sql = SF.goodsSql("IS_WAREHOUSE_CODE_EXITS");
		List list = this.baseDaoSupport.queryForList(sql, house_code);
		return list.size() > 0 ? true : false;
	}

	@Override
	public boolean isWarehouseNameExits(String house_name) {
		String sql = SF.goodsSql("IS_WAREHOUSE_NAME_EXITS");
		List list = this.baseDaoSupport.queryForList(sql, house_name);
		return list.size() > 0 ? true : false;
	}

	@Override
	public List warehouseList() {
		String sql = SF.goodsSql("WAREHOUSE_LIST");
		return this.baseDaoSupport.queryForList(sql);
	}

	@Override
	public List<Warehouse> qrySupplierWareHouse() {
		AdminUser user = ManagerUtils.getAdminUser();
		/**
		 * 供货商管理员ID
		 */
		String userid = user.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(user.getReltype())){
			userid = user.getParuserid();
		}else if(Consts.CURR_FOUNDER0==user.getFounder().intValue()){
			userid = "-1";
		}
		String sql = SF.goodsSql("WAREHOUSE_LIST_ALL");
		if(!ManagerUtils.isAdminUser())sql += " where t.user_id='"+userid+"'";
		return this.baseDaoSupport.queryForList(sql, Warehouse.class);
	}

	@Override
	public Page queryProductNotAssign(String house_id,String product_name, int pageNo, int pageSize) {
		String sql = SF.goodsSql("WAREHOUSE_PRODUCT_LIST_NOTASSIGN");
		
		ArrayList sqlParam = new ArrayList();
		
		if(StringUtils.isNotEmpty(house_id)){
			sql += " and a.house_id=?";
			sqlParam.add(house_id);
		}
		if(StringUtils.isNotEmpty(product_name)){
			sql += " and d.name like ?";
			sqlParam.add("%"+product_name+"%");
		}
		
		sql += "  order by a.status_date desc";
		
		return this.daoSupport.queryForPage(sql, pageNo, pageSize,true, sqlParam.toArray());
	}
	
	@Override
	public Page queryGoodsNotAssign(String house_id,String goods_id, int pageNo, int pageSize) {
		String sql = "" ;//SF.goodsSql("WAREHOUSE_GOODS_LIST_NOTASSIGN");
		Page page = new Page();
		if(StringUtils.isNotEmpty(goods_id)){
			sql = "	select "+goods_id+" goods_id,	"+
					"	      (select name from es_goods where goods_id='"+goods_id+"' "+ManagerUtils.apSFromCond("")+") name,	"+
					"         (select house_name from es_warehouse where house_id=c.house_id "+ManagerUtils.apSFromCond("")+") house_name,"+
					"         (select sku from es_product where goods_id='"+goods_id+"' "+ManagerUtils.apSFromCond("")+") sku,"+
					"	       c.house_id,	"+
					"	       min(c.inventory_num) inventory_num,	"+
					"	       min(c.no_apply_num) no_apply_num	,"+
					"          min(c.apply_num) apply_num"+
					"	  from es_goods_inventory c	 "+
					"	 where c.source_from = '"+ManagerUtils.getSourceFrom()+"'" +
					"     and c.product_id in	"+
					"	       (select a.product_id	"+
					"	          from es_goods_rel a, es_goods b	"+
					"	         where a.a_goods_id = b.goods_id	"+
					"              and b.source_from = '"+ManagerUtils.getSourceFrom()+"'"+ManagerUtils.apSFromCond("a")+
					"	           and b.type = 'goods'	"+
					"	           and b.goods_id = "+goods_id+"	"+
					"	           and a.rel_type = '"+Consts.PRO_REL_GOODS+"')	"+
					"	 group by house_id	";
			ArrayList sqlParam = new ArrayList();
			
			page = this.daoSupport.queryForPage(sql, pageNo, pageSize,true, sqlParam.toArray());
		}

		
		return page;
	}

	@Override
	public Page queryAssigned(String org_id,String name,String house_id,String virtual_house_id,
			String type, int pageNo, int pageSize) {
		Page page = new Page();
		String sql = SF.goodsSql("WAREHOUSE_ASSIGN_LIST");
		List sqlParam = new ArrayList();
		if(StringUtils.isNotEmpty(type)){
			sql += " and a.type=? " ;
			sqlParam.add(type);
			
			if(StringUtils.isNotEmpty(org_id)){
				//如果是新商城则查询其下的的销售组织，其他的则根据销售组织来查询
				if(org_id.equals(Consts.XINGSHANGCHENG_CODE_ID)){
					sql += " and a.org_id in" +
							" (select o.party_id from es_goods_org o where o.parent_party_id='"+Consts.XINGSHANGCHENG_CODE_ID+"' and o.status_cd='00A' "+ManagerUtils.apSFromCond("o")+")";
				}else{
					sql += " and a.org_id in("+org_id+")";
				}
		    }
			
			if(StringUtils.isNotEmpty(name)){
				sql += " and c.name like ?";
				sqlParam.add("%"+name+"%");
		    }
			
			if(StringUtils.isNotEmpty(house_id)){
				sql += " and a.house_id=?";
				sqlParam.add(house_id);
		    }
			
			if(StringUtils.isNotEmpty(virtual_house_id)){
				sql += " and a.virtual_house_id=?";
				sqlParam.add(virtual_house_id);
		    }
			
			sql += " order by a.house_id,a.goods_id ";
			page =  this.daoSupport.queryForPage(sql, pageNo, pageSize,true, sqlParam.toArray(new String[]{}));
		}

        return page;
	}

	@Override
	public Page getProductList(String name,String type, int pageNo, int pageSize) {
		
		Page page = new Page();
		if(StringUtils.isNotEmpty(type)){
			String sql = SF.goodsSql("WAREHOUSE_GET_PRODUCT_LIST");
			sql += " and b.type='"+type+"'";
			ArrayList partm = new ArrayList();
			if(StringUtils.isNotEmpty(name)){
				sql += " and a.name like ?";
				partm.add("%"+name+"%");
			}
			page = this.daoSupport.queryForPage(sql, pageNo, pageSize,true, partm.toArray());
		}

		return page;
	}
	
	@Override
	public Page getGoodsList(String name,String type, int pageNo, int pageSize) {
		
		Page page = new Page();
		if(StringUtils.isNotEmpty(type)){
			String sql = SF.goodsSql("WAREHOUSE_GET_GOODS_LIST");
			sql += " and b.type='"+type+"'";
			ArrayList partm = new ArrayList();
			if(StringUtils.isNotEmpty(name)){
				sql += " and b.name like ?";
				partm.add("%"+name+"%");
			}
			page = this.daoSupport.queryForPage(sql, pageNo, pageSize,true, partm.toArray());
		}

		return page;
	}

	@Override
	public Page getVWareList(String house_name,Integer attr_inline_type, int pageNo, int pageSize) {
		String sql = SF.goodsSql("WAREHOUSE_VIRTUAL_HOUSE_LIST");
		
		ArrayList partm = new ArrayList();
		if(StringUtils.isNotEmpty(house_name)){
			sql += " and a.house_name like ?";
			partm.add("%"+house_name+"%");
		}
		if(attr_inline_type != null && attr_inline_type != -1){
			sql += " and a.attr_inline_type = ?";
			partm.add(attr_inline_type);
		}
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize,true, partm.toArray());
		return page;
	}

	/**
	 * 商品货品库存回收
	 * @param ita
	 */
	@Override
	public void recycleInventoryApply(GoodsInventoryApply ita){
		String goods_id = ita.getGoods_id();
		String type = ita.getType();
		String sku = ita.getSku();
		String org_id = ita.getOrg_id();
		String house_id = ita.getHouse_id();
		int inventory_num = ita.getInventory_num();
		
		Map updateMap = new HashMap();
		updateMap.put("inventory_num", 0);
		
		Map whereMap = new HashMap();
		whereMap.put("goods_id", goods_id);
		whereMap.put("type", type);
		whereMap.put("sku", sku);
		whereMap.put("org_id", org_id);
		//String updateSql = "update es_goods_inventory_apply set inventory_num=0 where goods_id=? and type=? and sku=? and org_id=? ";
		this.baseDaoSupport.update("es_goods_inventory_apply", updateMap, whereMap);
		
		//回收，修改货品未分配库存、已分配库存
		updateGoodsInventory(type,house_id,goods_id,ita.getProduct_id(),inventory_num,"2");
		
		String action = Consts.INVENTORY_ACTION_2;
		String log_id = insertInventoryApplyLog( ita, action);
		
		//写消息队列
		this.insertCoQueue(log_id,ita.getOrg_id(),type,inventory_num,action);
	}
	
	
	/**
	 * 商品货品库存分配和回收
	 * @param ita
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateInventoryApply(GoodsInventoryApply ita,String action){
		String sqlG="select sku from es_goods where goods_id=?";
		String sku=this.baseDaoSupport.queryForString(sqlG,ita.getGoods_id());
		ita.setSku(sku);
		String goods_id = ita.getGoods_id();
		String type = ita.getType();
//		String sku = ita.getSku();
		String house_id = ita.getHouse_id();
		String virtual_house_id = ita.getVirtual_house_id();
		int inventory_num = ita.getInventory_num();
		String[] org_ids = (ita.getOrg_id()==null || "".equals(ita.getOrg_id()))?new String[0]:ita.getOrg_id().split(",");
		if(Consts.INVENTORY_ACTION_2.equals(action)){
			inventory_num = 0 - inventory_num;
			ita.setInventory_num(inventory_num);
		}
		
		for(int i=0;i<org_ids.length;i++){
			String org_id = org_ids[i];
			if(exists(house_id,virtual_house_id,org_id,sku,type,goods_id)){
				String sql = "select inventory_num from es_goods_inventory_apply where goods_id=? and type=? and sku=? and org_id=? and house_id=? and virtual_house_id=?";
				int num = this.baseDaoSupport.queryForInt(sql, goods_id,type,sku,org_id,house_id,virtual_house_id);
				if(num>=0){
					String updateSql = "update es_goods_inventory_apply set inventory_num=inventory_num+? where goods_id=? and type=? and sku=? and org_id=? and house_id=? and virtual_house_id=?";
					this.baseDaoSupport.execute(updateSql,inventory_num, goods_id,type,sku,org_id,house_id,virtual_house_id);
				}
			}
			else{
				GoodsInventoryApply gita = new GoodsInventoryApply();
				gita.setGoods_id(goods_id);
				gita.setType(type);
				gita.setSku(sku);
				gita.setOrg_id(org_id);
				gita.setInventory_num(inventory_num);
				gita.setIs_share(ita.getIs_share());
				gita.setVirtual_house_id(ita.getVirtual_house_id());
				gita.setHouse_id(ita.getHouse_id());
				gita.setSource_from(ManagerUtils.getSourceFrom());
				gita.setHouse_id_out(ita.getHouse_id());
				this.baseDaoSupport.insert("es_goods_inventory_apply", gita);
			}
		}
		
		//修改货品已分配库存和未分配库存
		updateGoodsInventory(type,house_id,goods_id,ita.getProduct_id(), inventory_num,action);
		
		String log_id = insertInventoryApplyLog( ita, action);
		
		//写消息队列
		this.insertCoQueue(log_id,ita.getOrg_id(),type,inventory_num,action);
		
	}
	
	/**
	 * 商品货品库存分配、回收写消息队列
	 * @param log_id 
	 * @param org_ids
	 * @param type
	 * @param inventory_num
	 * @param action
	 */
	public void insertCoQueue(String log_id,String org_ids,String type,int inventory_num,String action){
		
		//获取并过滤要同步的销售组织（新商城的）
      	String sql = SF.goodsSql("GOODS_ORG_ID");
      	sql += " and a.source_from = '" + ManagerUtils.getSourceFrom() + "'"
      		 + " and a.org_level = 3 and a.party_id in("+ org_ids +")";
      	List<String> goodsOrgLst = this.jdbcTemplate.queryForList(sql, String.class);
      	String org_id_str = GoodsManagerUtils.convertStrFromLst(goodsOrgLst, ",");
      	
      	if (!StringUtils.isEmpty(org_id_str)) {
      		
      		CoQueue coQueue = new CoQueue();
    		coQueue.setBatch_id(this.daoSupport.getSequences("S_ES_CO_BATCH_ID"));
    		coQueue.setObject_id(log_id);
    		coQueue.setOrg_id_str(org_id_str);
    		//获取父org---zengxianlian
    		String orgs[] = org_id_str.split(",");
    		List<String> pOrgList = this.jdbcTemplate.queryForList("select distinct a.parent_party_id from es_goods_org a where 1=1 and a.org_level = 3 and a.party_id='"+orgs[0]+"'", String.class);
    		//coQueue.setOrg_id_belong("10008");
    		if(pOrgList.size()>0){
    			coQueue.setOrg_id_belong(pOrgList.get(0));
    		}else{
    			coQueue.setOrg_id_belong("10008");
    		}
    		coQueue.setObject_type("KUCUN");
    		if(Consts.ECS_QUERY_TYPE_GOOD.equals(type)){
    			coQueue.setService_code(Consts.SERVICE_CODE_CO_SHANGPIN_KUCUN);
    			coQueue.setCo_name("商品库存同步");
    		} else {
    			coQueue.setService_code(Consts.SERVICE_CODE_CO_HUOPIN_KUCUN);
    			coQueue.setCo_name("货品库存同步");
    		}
    		
    		//如果是入库则为添加，否则都为删除
    		if (Consts.INVENTORY_ACTION_1.equals(action)) {
    			coQueue.setAction_code(Consts.ACTION_CODE_A);
    		} else {
    			coQueue.setAction_code(Consts.ACTION_CODE_D);
    		}
    		
    		String oper_id = "-1";
            if (ManagerUtils.getAdminUser() != null) {
            	oper_id = ManagerUtils.getAdminUser().getUserid();
    		}
    		coQueue.setOper_id(oper_id);
    		
    		coQueueManager.add(coQueue);
      	}
      	
	}
	
	public String getParentOrgIds(String org_ids){
		String sql = SF.goodsSql("PARENT_PARTY_ID");
		String[] ids = org_ids.split(",");
		String org_id = ids.length>0?ids[0]:"";
		String parent_org_ids = "";
		if(!StringUtil.isEmpty(org_id)){
			parent_org_ids = this.baseDaoSupport.queryForString(sql, org_id);
		}
		return parent_org_ids;
	}
	
	/**
	 * 修改货品未分配、已分配库存
	 * @param house_id 物理仓ID
	 * @param goods_id 货品ID
	 * @param inventory_num 分配/回收库存数量
	 * @param action 动作
	 */
	public void updateGoodsInventory(String type,String house_id,String goods_id,String p_id,int inventory_num,String action){
		
		List productList = new ArrayList();
		if(Consts.ECS_QUERY_TYPE_GOOD.equals(type)){//如果是商品，查找商品对应的货品
			productList = getProductByGoodsId(goods_id);
		}
		else if(Consts.ECS_QUERY_TYPE_PRODUCT.equals(type)){//货品
			Map map = new HashMap();
			map.put("product_id", p_id);
			productList.add(map);
		}
		
		String updateSql = "update es_goods_inventory set no_apply_num=no_apply_num-?,apply_num=apply_num+? where house_id=? and product_id=? ";
		for(int i=0;i<productList.size();i++){
			Map product = (Map)productList.get(i);
			String product_id = (String)product.get("product_id");
			this.baseDaoSupport.execute(updateSql, inventory_num,inventory_num,house_id,product_id);
		}
			
	}
	
	public List getProductByGoodsId(String goods_id){
		String sql = "select a.product_id from es_goods_rel a ,es_product b where a.product_id=b.product_id and a.source_from='" + ManagerUtils.getSourceFrom() + "' and a.a_goods_id=?";
		return  baseDaoSupport.queryForList(sql, new String[]{goods_id});
	}
	
	
	/**
	 * 检查商城是否已经分配库存
	 * @param org_id
	 * @return
	 */
	public boolean exists(String house_id,String virtual_house_id,String org_id,String sku,String type,String goods_id){
		String sql = "select count(*) from es_goods_inventory_apply where house_id=? and virtual_house_id=? and org_id=? and sku=? and type=? and goods_id=? ";
		String num = this.baseDaoSupport.queryForString(sql, house_id,virtual_house_id,org_id,sku,type,goods_id);
		if("0".equals(num)){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * 记录库存分配日志
	 * @param ita
	 * @param action 动作
	 */
	private String insertInventoryApplyLog(GoodsInventoryApply ita,String action){
		
		GoodsInventoryApplyLog log = new GoodsInventoryApplyLog();
		
		String oper_id = "-1";
        if (ManagerUtils.getAdminUser() != null) {
        	oper_id = ManagerUtils.getAdminUser().getUserid();
		}
		String log_id = this.baseDaoSupport.getSequences("s_es_goods_inventory_apply_log");
		log.setLog_id(log_id);
		log.setOper_id(oper_id);
		log.setAction(action);
		log.setObj_id(ita.getGoods_id());
		log.setType(ita.getType());
		log.setSku(ita.getSku());
		log.setOrg_id_str(ita.getOrg_id());
		log.setHouse_id(ita.getHouse_id());
		log.setInventory_num(ita.getInventory_num());
		log.setVirtual_house_id(ita.getVirtual_house_id());
		log.setIs_share(ita.getIs_share());
		log.setOrder_id(ita.getOrder_id());
		log.setStatus_date(DBTUtil.current());
		log.setSource_from(ManagerUtils.getSourceFrom());
		log.setHouse_id_out(ita.getHouse_id());
		this.baseDaoSupport.insert("es_goods_inventory_apply_log", log);
		
		return log_id;
	}

	@Override
	public GoodsInventoryApplyLog queryInventoryApplyLog(String  log_id) {
		String sql = SF.goodsSql("WAREHOUSE_GET_GOODS_APPLY_LOG");
		GoodsInventoryApplyLog  log  = (GoodsInventoryApplyLog)baseDaoSupport.queryForObject(sql,GoodsInventoryApplyLog.class,log_id);
		return log;
	}
	
	@Override
	public void rollbackInventory(String product_id, String house_id,
			int rollback_num) {
		
		String sql = "update es_goods_inventory set inventory_num=inventory_num+?,apply_num=apply_num+? where product_id=? and house_id=? ";
		this.baseDaoSupport.execute(sql, rollback_num,rollback_num,product_id,house_id);
	}

	@Override
	public void updateInventory(String order_id) {
		
		String inventory_apply_log = "select obj_id goods_id,sku,action,virtual_house_id,house_id,inventory_num from es_goods_inventory_apply_log where order_id=? ";
		List<Map> logs = this.baseDaoSupport.queryForList(inventory_apply_log, order_id);
		if(logs!=null && logs.size()>0){
			for(Map log:logs){
				String goods_id = (String) log.get("goods_id");
				String house_id = (String) log.get("house_id");
				String virtual_house_id = (String) log.get("virtual_house_id");
				int inventory_num = (Integer) log.get("inventory_num");
				String sql = "update es_goods_inventory_apply set inventory_num=inventory_num+? where goods_id=? and virtual_house_id=?";
				this.baseDaoSupport.execute(sql, inventory_num,goods_id,virtual_house_id);
				
				String sql1 = "update es_goods_inventory set inventory_num=inventory_num+?,apply_num=apply_num+? where product_id=? and house_id=? ";
				String sql2 = "select p.* from es_goods_rel r ,es_product p where r.product_id=p.product_id and p.source_from='"+ManagerUtils.getSourceFrom()+"' and r.a_goods_id=? "+ManagerUtils.apSFromCond("r")+"";
				List<Map> products = this.baseDaoSupport.queryForList(sql2, goods_id);
				if(products!=null && products.size()>0){
					for(Map product:products){
						String product_id = (String) product.get("product_id");
						this.baseDaoSupport.execute(sql1, inventory_num,inventory_num,product_id,house_id);
					}
				}
			}
			
		}
		
	}
	
	@Override
	public List getVirtualHouseForOrder(String goods_id, String type, String house_id, String org_id) {
		
		List vLst = new ArrayList();
		vLst = this.baseDaoSupport.queryForList(SF.goodsSql("VIRTUAL_HOUSE_ID"), 
				goods_id, type, house_id, org_id, ManagerUtils.getSourceFrom());
		
		return vLst;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String inventoryRollback(String order_id, String goods_id,
			String house_id, String virtual_house_id, int num) {
		
		//更新商品下的所有货品库存
		List<Map> products = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_FROM_GOODS"), 
				goods_id, Consts.PRO_REL_GOODS, ManagerUtils.getSourceFrom());
		if (products != null && products.size() > 0) {
			for (Map product : products) {
				String product_id = (String) product.get("product_id");
				this.updateGoodsInventory(product_id, house_id, 0-num, 0-num, 0);
			}
		}
		
		//更新商品库存分配记录
		this.updateGoodsInventoryApply(goods_id, Consts.ECS_QUERY_TYPE_GOOD, virtual_house_id, 0-num);
		
		//记录商品库存分配日志
		GoodsInventoryApply ita = new GoodsInventoryApply();
		ita.setGoods_id(goods_id);
		ita.setType(Consts.ECS_QUERY_TYPE_GOOD);
		ita.setHouse_id(house_id);
		ita.setInventory_num(num);  //回退入库填正数
		ita.setVirtual_house_id(virtual_house_id);
		ita.setOrder_id(order_id);
		ita.setSku(goods_id);
		String log_id = this.insertInventoryApplyLog(ita, Consts.INVENTORY_ACTION_1);  //入库
		
		//共享才要写队列去同步  
        /*if (Consts.ATTR_INLINE_TYPE_GONGXIANG.equals(is_share)) {
			
			//写同步消息队列
			this.insertCoQueue(log_id, org_id_str, Consts.ECS_QUERY_TYPE_GOOD, num, Consts.INVENTORY_ACTION_1);
		}*/
        
        return log_id;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String inventoryReduce(String order_id, String goods_id, 
			String house_id, String virtual_house_id, String is_share, String org_id_str, int num) {
		
		//更新商品下的所有货品库存
		List<Map> products = this.baseDaoSupport.queryForList(SF.goodsSql("PRODUCT_FROM_GOODS"), 
				goods_id, Consts.PRO_REL_GOODS, ManagerUtils.getSourceFrom());
		if (products != null && products.size() > 0) {
			for (Map product : products) {
				String product_id = (String) product.get("product_id");
				this.updateGoodsInventory(product_id, house_id, num, num, 0);
			}
		}
		
		//更新商品库存分配记录
		this.updateGoodsInventoryApply(goods_id, Consts.ECS_QUERY_TYPE_GOOD, virtual_house_id, num);
		
		//记录商品库存分配日志
		GoodsInventoryApply ita = new GoodsInventoryApply();
		ita.setGoods_id(goods_id);
		ita.setSku(goods_id);
		ita.setType(Consts.ECS_QUERY_TYPE_GOOD);
		ita.setHouse_id(house_id);
		ita.setInventory_num(0-num); //扣减出库填负数
		ita.setVirtual_house_id(virtual_house_id);
		ita.setOrg_id(org_id_str);
		ita.setIs_share(is_share);
		ita.setOrder_id(order_id);
		
		String log_id = this.insertInventoryApplyLog(ita, Consts.INVENTORY_ACTION_0);  //出库
		
		//写同步消息队列
		this.insertCoQueue(log_id, org_id_str, Consts.ECS_QUERY_TYPE_GOOD, num, Consts.INVENTORY_ACTION_0);
        
        return log_id;
	}
	
    /**
     * 更新货品库存
     * @param product_id
     * @param house_id
     * @param num_inventory  库存增量值
     * @param num_apply  已分配的增量值
     * @param num_no_apply  未分配库存的增量值
     */
	private void updateGoodsInventory(String product_id, String house_id, 
			int num_inventory, int num_apply, int num_no_apply) {
		
		this.baseDaoSupport.execute(SF.goodsSql("UPDATE_GOODS_INVENTORY"), 
				num_inventory, num_apply, num_no_apply, product_id, house_id);
	}
	
	/**
	 * 更新库存分配记录
	 * @param goods_id
	 * @param type
	 * @param virtual_house_id
	 * @param num  增量值
	 */
	private void updateGoodsInventoryApply(String goods_id, String type, String virtual_house_id, int num) {
		
		this.baseDaoSupport.execute(SF.goodsSql("UPDATE_GOODS_INVENTORY_APPLY"), 
				num, goods_id, type, virtual_house_id);
	}

	@Override
	public Warehouse getWareHouseById(String house_id) {
		String sql = SF.goodsSql("WARE_HOUSE_BY_HOUSE_ID");
		Warehouse  warehouse = (Warehouse) this.baseDaoSupport.queryForObject(sql, Warehouse.class, house_id);
		
		return warehouse;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存", summary = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存")
	public SubtractInventoryNumResp subtractInventoryNum(
			SubtractInventoryNumReq req) {
		SubtractInventoryNumResp resp = new SubtractInventoryNumResp();
		
		List<Map<String, String>> params_list = req.getList();
		for (Map<String, String> params_map : params_list) {
			String num = params_map.get("num"); // 扣减数量
			String org_id = params_map.get("org_id"); // 商场来源
			String goods_id = params_map.get("goods_id"); // 商品ID
			String product_id = params_map.get("product_id"); // 货品ID
			String order_id = params_map.get("order_id"); // 订单编号
			String sku = params_map.get("sku"); // sku
			String comp_code = params_map.get("comp_code");
			
//		    String sql_p = "select t.product_id, t.goods_id, t.sku from es_product t where t.product_id='"+product_id+"'";
//		    List<Map> ls = baseDaoSupport.queryForList(sql_p);
//			if (CollectionUtils.isNotEmpty(ls)) {
//				product_id = ls.get(0).get("product_id").toString();
//				goods_id = ls.get(0).get("goods_id").toString();
//			} else {
//				resp.setError_msg("虚拟仓库扣减失败！原因：es_product表没有找到product_id=【"+product_id+"】的货品！");
//				return resp;
//			}
//			String sql_sku = "SELECT T.SKU FROM ES_PRODUCT T WHERE T.PRODUCT_ID='"+product_id+"'";
//			List<Map> list = baseDaoSupport.queryForList(sql_sku);	
//			Map<String, String> map = list.get(0);
			if(StringUtil.isEmpty(comp_code)){//货主为空
				resp.setError_msg("没找到货主信息！");
				return resp;
			}else if (!StringUtil.isEmpty(sku)) {
//				String sku = map.get("SKU"); // 获取货品SKU
				StringBuilder sql = new StringBuilder("SELECT ");
				sql.append("T.GOODS_ID,");
				sql.append("T.TYPE,");
				sql.append("T.SKU,");
				sql.append("T.ORG_ID,");
				sql.append("T.HOUSE_ID_OUT,");
				sql.append("T.INVENTORY_NUM,");
				sql.append("T.HOUSE_ID_IN,");
				sql.append("T.HOUSE_ID,");
				sql.append("T.VIRTUAL_HOUSE_ID");
				sql.append(" FROM ES_GOODS_INVENTORY_APPLY T,ES_WAREHOUSE W WHERE T.SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"' AND T.HOUSE_ID=W.HOUSE_ID"); // 检查货品存库量
				sql.append(" AND T.SKU='"+sku+"'");
				sql.append(" AND T.ORG_ID='"+org_id+"'");
				sql.append(" AND T.GOODS_ID='"+goods_id+"'");
				sql.append(" AND W.COMP_CODE='"+comp_code+"'");
				List<GoodsInventoryApply> inventoryList = baseDaoSupport.queryForList(sql.toString(), GoodsInventoryApply.class);
				if (CollectionUtils.isNotEmpty(inventoryList)) {
					int total_ = 0;
					int subtr_num = Integer.valueOf(num); // 需要扣减库存
					for (GoodsInventoryApply apply : inventoryList) {
						total_ += apply.getInventory_num();
					}
					if (total_ < subtr_num) {
						resp.setError_msg("虚拟仓库扣减失败！原因：没有多余的库存！");
						return resp;
					}
					for (int i =0; i< inventoryList.size(); i++) {
						// 扣减虚拟仓库存
						GoodsInventoryApply goodsInventoryApply = inventoryList.get(i);
						
						if (goodsInventoryApply.getInventory_num() <= 0) {
							continue;
						}

						String house_id = goodsInventoryApply.getHouse_id(); // 原物理仓ID
						String virtual_house_id = goodsInventoryApply.getVirtual_house_id(); // 原虚拟仓ID
						String type = goodsInventoryApply.getType(); // 商品,货品类型
						String sku_ = goodsInventoryApply.getSku(); // 商品，货品SKU
						String org_id_ = goodsInventoryApply.getOrg_id(); // 商城来源
						String house_id_in = goodsInventoryApply.getHouse_id_in(); // 新虚拟仓
						String house_id_out = goodsInventoryApply.getHouse_id_out(); // 新物理仓库ID
						int apply_num_vir = goodsInventoryApply.getInventory_num(); // 虚拟仓货品当前库存
						
						goodsInventoryApply.setInventory_num(apply_num_vir-subtr_num);
						StringBuilder where_vir = new StringBuilder("");
						where_vir.append("T.SKU='"+sku_+"'");
						where_vir.append(" AND T.TYPE='"+type+"'");
						where_vir.append(" AND T.ORG_ID='"+org_id_+"'");
						where_vir.append(" AND T.GOODS_ID='"+goods_id+"'");
						if (StringUtils.isBlank(house_id)) {
							where_vir.append(" AND T.HOUSE_ID_OUT='"+house_id_out+"'");
						} else if (StringUtils.isBlank(house_id_out)) {
							where_vir.append(" AND T.HOUSE_ID='"+house_id+"'");
						} else {
							where_vir.append(" AND T.HOUSE_ID_OUT='"+house_id_out+"'");
						}
						if (StringUtils.isBlank(virtual_house_id)) {
							where_vir.append(" AND T.HOUSE_ID_IN='"+house_id_in+"'");
						} else if (StringUtils.isBlank(house_id_in)) {
							where_vir.append(" AND T.VIRTUAL_HOUSE_ID='"+virtual_house_id+"'");
						} else {
							where_vir.append(" AND T.HOUSE_ID_IN='"+house_id_in+"'");
						}
						
						// 扣减物理仓库存
						String sql_phy = "SELECT T.PRODUCT_ID, T.HOUSE_ID, T.SKU, T.INVENTORY_NUM, T.NO_APPLY_NUM, T.APPLY_NUM" +
								" FROM ES_GOODS_INVENTORY T WHERE T.PRODUCT_ID='"+product_id+"' AND T.SKU='"+sku+"'" +
								" AND T.HOUSE_ID='"+(StringUtils.isBlank(house_id_out)?house_id:house_id_out)+"'";
						List<GoodsInventoryM> phy_list = baseDaoSupport.queryForList(sql_phy, GoodsInventoryM.class);
						String where_phy = "";
						GoodsInventoryM goodsInventoryM = null;
						if (phy_list != null && phy_list.size() >0) {
							goodsInventoryM = phy_list.get(0);
							int inventory_num = goodsInventoryM.getInventory_num(); // 物理仓当前库存
							int apply_num_phy = goodsInventoryM.getApply_num(); // 已分配给下级仓库的库存
							if (inventory_num < subtr_num || apply_num_phy < subtr_num) {
								resp.setError_msg("物理仓库扣减失败！原因：没有多余库存！");
								return resp;
							}
							goodsInventoryM.setApply_num(apply_num_phy-subtr_num);
							goodsInventoryM.setInventory_num(inventory_num-subtr_num);
							where_phy = "T.PRODUCT_ID='"+product_id+"' AND T.SKU='"+sku+"' AND T.HOUSE_ID='"+(StringUtils.isBlank(house_id_out)?house_id:house_id_out)+"'";
						} else {
							if (i != inventoryList.size() -1) {
								continue;
							} else {
								resp.setError_msg("未找到对应的物理仓库！");
								return resp;
							}
						}
						baseDaoSupport.update("ES_GOODS_INVENTORY_APPLY T", goodsInventoryApply, where_vir.toString()); // 扣减虚拟仓库存
						baseDaoSupport.update("ES_GOODS_INVENTORY T", goodsInventoryM, where_phy); // 扣减物理仓库存
						insertGoodsInventoryApplylog(goodsInventoryApply.getGoods_id(), goodsInventoryApply.getType(), goodsInventoryApply.getSku(), 
								goodsInventoryApply.getOrg_id(), 
								StringUtils.isBlank(house_id_out)?house_id:house_id_out, "0", 
								subtr_num, 
								StringUtils.isBlank(house_id_in)?virtual_house_id:house_id_in, null); // 扣减虚拟仓库日志
						insertGoodsInventoryLog(product_id, sku, 
								StringUtils.isBlank(house_id_out)?house_id:house_id_out, goodsInventoryM.getInventory_num(), 
								goodsInventoryM.getNo_apply_num(), goodsInventoryM.getApply_num(),
								"0", subtr_num, "商城扣减物理存库"); // 扣减物理仓库日志
						
						InsertOrderHandLogReq logReq = new InsertOrderHandLogReq();
						String flow_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_id();
						String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
						logReq.setOrder_id(order_id);
						logReq.setFlow_id(flow_id); // 流程ID
						logReq.setFlow_trace_id(flow_trace_id); // 环节ID
						logReq.setComments(String.valueOf(subtr_num)); // 描述
						logReq.setCreate_time("sysdate"); // 处理时间
						logReq.setOp_id("-1"); // 处理人
						logReq.setSource_from(ManagerUtils.getSourceFrom()); // 来源
						logReq.setType_code(""); // 业务处理类型编码
						logReq.setHandler_type("0"); // 0-扣库存 1-增加库存
						CommonDataFactory.getInstance().insertOrderHandlerLogs(logReq);
						break;
					}
				} else {
					resp.setError_msg("没有对应的虚拟仓库！");
					return resp;
				}
			} else {
				resp.setError_msg("没找到货品对应SKU！");
				return resp;
			}
		}
		resp.setError_code("0");
		resp.setError_msg("扣减库存成功！");
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据订单来源商城、固定的物理仓库编码、货品ID增加库存", summary = "根据订单来源商城、固定的物理仓库编码、货品ID增加库存")
	public IncreaseInventoryNumResp increaseInventoryNum(
			IncreaseInventoryNumReq req) {
		IncreaseInventoryNumResp resp = new IncreaseInventoryNumResp();

		List<Map<String, String>> params_list = req.getList();
		for (Map<String, String> params_map : params_list) {
			String num = params_map.get("num"); // 扣减数量
			String org_id = params_map.get("org_id"); // 商场来源
			String goods_id = params_map.get("goods_id"); // 商品ID
			String product_id = params_map.get("product_id"); // 货品ID
			String order_id = params_map.get("order_id"); // 订单ID
			String sku = params_map.get("sku"); // sku
			String comp_code = params_map.get("comp_code");
			
//			if (StringUtils.isBlank(product_id)) {
//				String sql = "select t.product_id from es_product t where t.goods_id='"+goods_id+"'";
//				List<Map> ls = baseDaoSupport.queryForList(sql);
//				if (CollectionUtils.isNotEmpty(ls)) {
//					product_id = ls.get(0).get("product_id").toString();
//				} else {
//					resp.setError_msg("虚拟仓库增加失败！原因：es_product表没有找到goods_id=【"+goods_id+"】的货品！");
//					return resp;
//				}
//			}
//			String sql_sku = "SELECT T.SKU FROM ES_PRODUCT T WHERE T.PRODUCT_ID='"+product_id+"'";
//			List<Map> list = baseDaoSupport.queryForList(sql_sku);			
//			if (CollectionUtils.isNotEmpty(list)) {
//				Map<String, String> map = list.get(0);
			if(StringUtil.isEmpty(comp_code)){//货主为空
				resp.setError_msg("没找到货主信息！");
				return resp;
			}else if (!StringUtil.isEmpty(sku)) {
//					String sku = map.get("SKU"); // 获取货品SKU
					StringBuilder sql = new StringBuilder("SELECT ");
					sql.append("T.GOODS_ID,");
					sql.append("T.TYPE,");
					sql.append("T.SKU,");
					sql.append("T.ORG_ID,");
					sql.append("T.HOUSE_ID_OUT,");
					sql.append("T.INVENTORY_NUM,");
					sql.append("T.HOUSE_ID_IN,");
					sql.append("T.HOUSE_ID,");
					sql.append("T.VIRTUAL_HOUSE_ID");
					sql.append(" FROM ES_GOODS_INVENTORY_APPLY T,ES_WAREHOUSE W WHERE T.SOURCE_FROM='"+ManagerUtils.getSourceFrom()+"' AND T.HOUSE_ID=W.HOUSE_ID"); // 检查货品存库量
					sql.append(" AND T.SKU='"+sku+"'");
					sql.append(" AND T.ORG_ID='"+org_id+"'");
					sql.append(" AND T.GOODS_ID='"+goods_id+"'");
					sql.append(" AND W.COMP_CODE='"+comp_code+"'");
					List<GoodsInventoryApply> inventoryList = baseDaoSupport.queryForList(sql.toString(), GoodsInventoryApply.class);
					
					if (CollectionUtils.isNotEmpty(inventoryList)) {
						for (int i = 0; i < inventoryList.size(); i++) {
						// 增加虚拟仓库存
						GoodsInventoryApply goodsInventoryApply = inventoryList.get(i);
						String house_id_out = goodsInventoryApply.getHouse_id_out(); // 物理仓库ID
						int apply_num_vir = goodsInventoryApply.getInventory_num(); // 虚拟仓货品当前库存
						int incre_num = Integer.valueOf(num);
						goodsInventoryApply.setInventory_num(apply_num_vir+incre_num);
						
						String house_id = goodsInventoryApply.getHouse_id(); // 原物理仓ID
						String virtual_house_id = goodsInventoryApply.getVirtual_house_id(); // 原虚拟仓ID
						String type = goodsInventoryApply.getType(); // 商品,货品类型
						String sku_ = goodsInventoryApply.getSku(); // 商品，货品SKU
						String org_id_ = goodsInventoryApply.getOrg_id(); // 商城来源
						String house_id_in = goodsInventoryApply.getHouse_id_in(); // 虚拟仓
						StringBuilder where_vir = new StringBuilder("");
						where_vir.append("T.SKU='"+sku_+"'");
						where_vir.append(" AND T.TYPE='"+type+"'");
						where_vir.append(" AND T.ORG_ID='"+org_id_+"'");
						where_vir.append(" AND T.GOODS_ID='"+goods_id+"'");
						if (StringUtils.isBlank(house_id)) {
							where_vir.append(" AND T.HOUSE_ID_OUT='"+house_id_out+"'");
						} else if (StringUtils.isBlank(house_id_out)) {
							where_vir.append(" AND T.HOUSE_ID='"+house_id+"'");
						} else {
							where_vir.append(" AND T.HOUSE_ID_OUT='"+house_id_out+"'");
						}
						if (StringUtils.isBlank(virtual_house_id)) {
							where_vir.append(" AND T.HOUSE_ID_IN='"+house_id_in+"'");
						} else if (StringUtils.isBlank(house_id_in)) {
							where_vir.append(" AND T.VIRTUAL_HOUSE_ID='"+virtual_house_id+"'");
						} else {
							where_vir.append(" AND T.HOUSE_ID_IN='"+house_id_in+"'");
						}
						
						// 增加物理仓库存
						String sql_phy = "SELECT T.PRODUCT_ID, T.HOUSE_ID, T.SKU, T.INVENTORY_NUM, T.NO_APPLY_NUM, T.APPLY_NUM " +
								"FROM ES_GOODS_INVENTORY T WHERE T.PRODUCT_ID='"+product_id+"' AND T.SKU='"+sku+"' " +
								"AND T.HOUSE_ID='"+(StringUtils.isBlank(house_id_out)?house_id:house_id_out)+"'";
						List<GoodsInventoryM> phy_list = baseDaoSupport.queryForList(sql_phy, GoodsInventoryM.class);
						String where_phy = "";
						GoodsInventoryM goodsInventoryM = null;
						if (CollectionUtils.isNotEmpty(phy_list)) {
							goodsInventoryM = phy_list.get(0);
							int inventory_num = goodsInventoryM.getInventory_num(); // 物理仓当前库存
							int apply_num_phy = goodsInventoryM.getApply_num(); // 已分配给下级仓库的库存
							goodsInventoryM.setApply_num(apply_num_phy+incre_num);
							goodsInventoryM.setInventory_num(inventory_num+incre_num);
							where_phy = "T.PRODUCT_ID='"+product_id+"' AND T.SKU='"+sku+"' " +
									"AND T.HOUSE_ID='"+(StringUtils.isBlank(house_id_out)?house_id:house_id_out)+"'";
						} else {
							if (i != inventoryList.size() -1) {
								continue;
							} else {
								resp.setError_msg("未找到对应的物理仓库！");
								return resp;
							}
						}
						baseDaoSupport.update("ES_GOODS_INVENTORY_APPLY T", goodsInventoryApply, where_vir.toString()); // 增加虚拟仓库存
						baseDaoSupport.update("ES_GOODS_INVENTORY T", goodsInventoryM, where_phy); // 增加物理仓库存
						insertGoodsInventoryApplylog(goodsInventoryApply.getGoods_id(), 
								goodsInventoryApply.getType(), goodsInventoryApply.getSku(), 
								goodsInventoryApply.getOrg_id(), 
								(StringUtils.isBlank(house_id_out)?house_id:house_id_out), 
								"1", incre_num, 
								(StringUtils.isBlank(house_id_in)?virtual_house_id:house_id_in), null); // 增加虚拟仓库日志
						insertGoodsInventoryLog(product_id, sku, 
								(StringUtils.isBlank(house_id_out)?house_id:house_id_out), 
								goodsInventoryM.getInventory_num(), 
								goodsInventoryM.getNo_apply_num(), goodsInventoryM.getApply_num(),
								"1", incre_num, "商城增加物理存库"); // 扣减物理仓库日志
						
						InsertOrderHandLogReq logReq = new InsertOrderHandLogReq();
						String flow_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_id();
						String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
						logReq.setOrder_id(order_id);
						logReq.setFlow_id(flow_id); // 流程ID
						logReq.setFlow_trace_id(flow_trace_id); // 环节ID
						logReq.setComments(String.valueOf(incre_num)); // 描述
						logReq.setCreate_time("sysdate"); // 处理时间
						logReq.setOp_id("-1"); // 处理人
						logReq.setSource_from(ManagerUtils.getSourceFrom()); // 来源
						logReq.setType_code(""); // 业务处理类型编码
						logReq.setHandler_type("1"); // 0-扣库存 1-增加库存
						CommonDataFactory.getInstance().insertOrderHandlerLogs(logReq);
						break;
				    	}
					} else {
						resp.setError_msg("没有对应的虚拟仓库！");
						return resp;
					}
					
				} else {
					resp.setError_msg("没找到货品对应SKU！");
					return resp;
				}
//			} else {
//				resp.setError_msg("没找到货品对应SKU！");
//				return resp;
//			}
		}
		resp.setError_code("0");
		resp.setError_msg("库存回退成功！");
		return resp;
	}
	
	private void insertGoodsInventoryLog(String product_id, String sku, 
			String house_id, int inventory_num, int no_apply_num, int apply_num, String action, int change_num, String change_reason) {
		String log_id = baseDaoSupport.getSequences("S_ES_GOODS_INVENTORY_LOG");
		GoodsInventoryLog log = new GoodsInventoryLog();
		log.setAction(action); // 0-出库，1-入库
		log.setLog_id(log_id);
		log.setProduct_id(product_id);
		log.setSku(sku);
		log.setHouse_id(house_id);
		log.setInventory_num(inventory_num);
		log.setNo_apply_num(no_apply_num);
		log.setApply_num(apply_num);
		log.setAction(action);
		log.setChange_num(change_num);
		log.setChange_reason(change_reason);
		log.setSource_from(ManagerUtils.getSourceFrom());
		baseDaoSupport.insert("ES_GOODS_INVENTORY_LOG", log);
	}
	private void insertGoodsInventoryApplylog(String obj_id, String type, String sku, String org_id_str, String house_id, String action,
			int inventory_num, String virtual_house_id, String oper_id) {
		GoodsInventoryApplyLog log = new GoodsInventoryApplyLog();
		String log_id = baseDaoSupport.getSequences("S_ES_GOODS_INVENTORY_APPLY_LOG");
		log.setLog_id(log_id);   //日志标识
		log.setObj_id(obj_id);   //商品或货品ID
		log.setType(type);   //类型  货品PRODUCT_ID; 商品GOODS_ID
		log.setSku(sku);   //货品sku（商品编码）
		log.setOrg_id_str(org_id_str); // 销售组织编码串   多个则用逗号分隔
		log.setHouse_id_out(house_id);   //物理仓标识
		log.setHouse_id(house_id);
		log.setVirtual_house_id(virtual_house_id);
		log.setAction(action);   //动作 0-出库  1-入库 2-回收
		log.setInventory_num(inventory_num);   //分配库存 出库填负数
		log.setHouse_id_in(virtual_house_id);   //虚拟仓标识
		log.setIs_share("1");   //是否共享库存
		log.setOper_id("-1");   //操作员
		log.setStatus_date(DBTUtil.current());
		log.setSource_from(ManagerUtils.getSourceFrom());
		baseDaoSupport.insert("ES_GOODS_INVENTORY_APPLY_LOG", log);
	}
	private Map getGoodsInventoryByProductIdAndHouseId(String product_id, String house_id) {
		String sql = "select t.* from es_goods_inventory t where t.PRODUCT_ID='"+product_id+"' and t.HOUSE_ID='"+house_id+"'";
		List<Map> list = baseDaoSupport.queryForList(sql);
		return CollectionUtils.isNotEmpty(list)?list.get(0):null;
	}
	
	@Override
	public Page productCompanyList(String company_name, String company_code, int pageNo, int pageSize){
		String sql = SF.goodsSql("PRODUCT_COMPANY_ALL");
		ArrayList partm = new ArrayList();
		
		StringBuffer sqlAccount = new StringBuffer();
		if(StringUtils.isNotEmpty(company_name)){
			sqlAccount.append(" and a.comp_name like ? ");
			partm.add("%"+company_name+"%");
		}
		if(StringUtils.isNotEmpty(company_code)){
			sqlAccount.append(" and a.comp_code = ? ");
			partm.add(company_code);
		}
		
		sql=sql+sqlAccount.toString();
		Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
		List list = page.getResult();
		return page;
	}
	
	@Override
	public void addProductCompany(ProductCompany productCompany){
		productCompany.setComp_id(this.baseDaoSupport.getSequences("S_ES_PRODUCT_COMPANY"));
		productCompany.setSource_from(ManagerUtils.getSourceFrom());
		this.baseDaoSupport.insert("es_product_comp", productCompany);
	}

	@Override
	public void editProductCompany(ProductCompany productCompany){
		Map where=new HashMap();
		where.put("comp_id", productCompany.getComp_id());
		productCompany.setComp_id(null);
		this.baseDaoSupport.update("es_product_comp", productCompany, where);
	}
	
	@Override
	public Page searchPCList(String company_name,String company_code,int pageNo,int pageSize){
		String sql = SF.goodsSql("PCLIST");
		ArrayList partm = new ArrayList();
		
		if(StringUtils.isNotEmpty(company_name)){
			sql += " and t.comp_name like ? ";
			partm.add("%"+company_name+"%");
		}
		if(StringUtils.isNotEmpty(company_code)){
			sql += " and t.comp_code = ? ";
			partm.add(company_code);
		}
		
		return this.daoSupport.queryForPage(sql, pageNo, pageSize, partm.toArray());
	}
}
