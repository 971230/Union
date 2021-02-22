package com.ztesoft.net.mall.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.DlyType;
import com.ztesoft.net.mall.core.model.mapper.TypeAreaMapper;
import com.ztesoft.net.mall.core.model.support.DlyTypeConfig;
import com.ztesoft.net.mall.core.model.support.TypeArea;
import com.ztesoft.net.mall.core.model.support.TypeAreaConfig;
import com.ztesoft.net.mall.core.service.IDlyTypeManager;
import com.ztesoft.net.sqls.SF;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import params.regions.req.RegionChildrenListReq;
import params.regions.resp.RegionChildrenListResp;
import services.RegionsInf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配送方式管理
 * @author kingapex
 *2010-4-1上午10:11:01
 */
public class DlyTypeManager extends BaseSupport<DlyType> implements IDlyTypeManager{
	
	private RegionsInf regionsServ;
	
	public RegionsInf getRegionsServ() {
		return regionsServ;
	}


	public void setRegionsServ(RegionsInf regionsServ) {
		this.regionsServ = regionsServ;
	}


	@Override
	public void delete(String id) {
		if (id == null || id.equals(""))
			return;
		 String sql = SF.orderSql("SERVICE_DLY_TYPE_AREA_DELETE")+" and type_id in ("+id+")";
		this.baseDaoSupport.execute(sql);
		sql = SF.orderSql("SERVICE_DLY_TYPE_DELETE")+ " and type_id in ("+id+")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public DlyType getDlyTypeById(String typeId) {
		
		String sql  = SF.orderSql("SERVICE_DLY_TYPE_SELECT")+" and type_id=?";
		DlyType dlyType =  this.baseDaoSupport.queryForObject(sql, DlyType.class, typeId);
		//配置了地区费用
		if(dlyType!=null && dlyType.getIs_same()==0 ){
			dlyType.setTypeAreaList(	this.listAreabyTypeId( dlyType.getType_id())  );
		} 
		if(dlyType!=null)
			this.convertTypeJson(dlyType);
		return dlyType;
	}
	
	private List listAreabyTypeId(String typeid){
		String sql= SF.orderSql("SERVICE_DLY_TYPE_AREA_SELECT")+ " and type_id=?";
		List  typeAreaList  = baseDaoSupport.queryForList(sql, new TypeAreaMapper(), typeid);
		return typeAreaList;
	}
	

	
	@Override
	public List listByAreaId(Integer areaId) {
		String sql = SF.orderSql("SERVICE_DLY_AREA_PRICE_SELECT");
		List l = this.daoSupport.queryForList(sql,areaId);
		return l;
	}
	
	
	@Override
	public List<DlyType> list(){
		String sql = SF.orderSql("SERVICE_DLY_TYPE_SELECT_ORDER");
		return this.baseDaoSupport.queryForList(sql, DlyType.class);
	}

	
	@Override
	public List<DlyType> list(Double weight,Double orderPrice,String regoinId ){
		
		String sql = SF.orderSql("SERVICE_DLY_TYPE_SELECT_ORDER");
		
		//add by wui
		//EopSite eopSite = EopContext.getContext().getCurrentSite();
		//String theme_path = eopSite.getThemepath();
		if(StringUtil.isMobile())
			sql = SF.orderSql("SERVICE_DLY_TYPE_SELECT_5");
		
		
		List<DlyType> typeList  = this.baseDaoSupport.queryForList(sql, DlyType.class);
		
		sql =SF.orderSql("SERVICE_DLY_TYPE_AREA_SELECT");
		List typeAreaList  = this.baseDaoSupport.queryForList(sql,new TypeAreaMapper());
		
		List<DlyType> resultList = new ArrayList<DlyType>();
		
		for(DlyType dlyType:typeList){
			
			this.convertTypeJson(dlyType);
			
			if( dlyType.getIs_same().intValue()== 0){//配置了地区费用
				List<TypeArea> areaList  = this.filterTypeArea(dlyType.getType_id(), typeAreaList);
				Double price  = this.countPrice(areaList, weight, orderPrice, regoinId);
				
				//类型配置
				if(dlyType.getTypeConfig() == null)
					 continue;
				//不在配送范围，使用统一配置
				if(price==null &&  dlyType.getTypeConfig().getDefAreaFee()!=null && dlyType.getTypeConfig().getDefAreaFee().compareTo(1)==0 ){
					 price =  this.countExp(dlyType.getExpressions(), weight, orderPrice);
					if(price.compareTo(-1D)!=0){//如果公式出错了，则此配送方式不可用
						price=null;
					}
				}
				 //准则是null则不可用
				 if(price !=null){
					 dlyType.setPrice(price);
					resultList.add(dlyType);
				 }
				 
			}else{//统一配置
				Double price =  this.countExp(dlyType.getExpressions(), weight, orderPrice);
				if(price.compareTo(-1D)!=0){//如果公式出错了，则此配送方式不可用
					dlyType.setPrice(price);
					resultList.add(dlyType);
				}
			}
		}
		
		return resultList;
	}
	
	
	/**
	 * 检测某配送方式是否在配送地区
	 * @param areaList 配送方式的地区列表
	 * @param weight 商品重量
	 * @param orderPrice 订单金额
	 * @param regoinId 地区id
	 * @return 找到相应的配送地区的配送价格并计算配送费用，注：如果区域重合则找到最贵的配送方式并计算费用</br>
	 *         如果无匹配配送地区则返回null
	 */
	private Double countPrice(List<TypeArea> areaList, Double weight,
			Double orderPrice, String regoinId) {
		Double price = null;
		for (TypeArea typeArea : areaList) {
			String idGroup = typeArea.getArea_id_group();

			if (idGroup == null || idGroup.equals("")) {
				continue;
			}

			idGroup = idGroup == null ? "" : idGroup;
			String[] idArray = idGroup.split(",");

			// 检测所属地区是否在配送范围
			if (StringUtil.isInArray(regoinId, idArray)) {
				Double thePrice = this.countExp(typeArea.getExpressions(),
						weight, orderPrice);
				if(price!=null)
					price = thePrice.compareTo(price) > 0 ? thePrice : price;
				else
					price = thePrice;
			}

		}

		return price;
	}
 
	
	/**
	 * 过滤某个配送方式的 地区对照表
	 * @param type_id 配送方式id
	 * @param typeAreaList 要过滤的列表
	 * @return 此配送方式的地区对照表
	 */
	private List<TypeArea> filterTypeArea(String type_id,List typeAreaList){
		
		List<TypeArea> areaList  = new ArrayList<TypeArea>();
		for(int i=0;i<typeAreaList.size();i++){
			TypeArea typeArea =  (TypeArea)typeAreaList.get(i);
			if(typeArea.getType_id().equals(type_id)){
				areaList.add(typeArea);
			}
		}
		
		return areaList;
	}
	
	/**
	 * 转换type的json信息</br>
	 * 有两个json信息要转换：</br>
	 * 一个是将type的config字串转换为TypeConfig对象并填充给typeConfig属性</br>
	 * 另一个是将type对象本身转换为json并填充给json属性
	 * @param dlyType
	 */
	private void convertTypeJson(DlyType dlyType){
		String config  =dlyType.getConfig();
		dlyType.setTypeConfig(JSON.parseObject(config,DlyTypeConfig.class));
		dlyType.setJson(JSON.toJSONString(dlyType));
	}
	
	
	
	@Override
	public Page pageDlyType( int page, int pageSize) {
		 
		String sql = SF.orderSql("SERVICE_DLY_TYPE_SELECT_ORDER");
	 
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}
	
	/**
	 * 添加统一费用配置
	 */
	
	@Override
	public void add(DlyType type,DlyTypeConfig config){
		
		//统一设置
		if(type.getIs_same().intValue()==1){
			type= this.fillType(type, config);
			this.baseDaoSupport.insert("dly_type",type);
		}else{
			throw new RuntimeException("type not is same config,cant'add");
		}
		
	}
	
	/**
	 * 添加分别不同地区费用配置
	 */
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(DlyType type,DlyTypeConfig config,TypeAreaConfig[] configArray){

		//if(config.getDefAreaFee().intValue() == 1){ //启用默认设置
			type= this.fillType(type, config);
	//	}
		this.baseDaoSupport.insert("dly_type",type);
		String typeId = type.getType_id();
		this.addTypeArea(typeId, configArray);
		
	}


	
	@Override
	public void edit(DlyType type, DlyTypeConfig config) {
		if(type.getType_id()==null){
			throw new RuntimeException("type id is null ,can't edit");
		}
		//统一设置
		if(type.getIs_same().intValue()==1){
			String typeId =type.getType_id();
			this.baseDaoSupport.execute(SF.orderSql("SERVICE_DLY_TYPE_DELETE_BY_ID"), typeId);
			type= this.fillType(type, config);
			this.baseDaoSupport.update("dly_type", type, "type_id="+type.getType_id());
		}else{
			throw new RuntimeException("type not is same config,cant'edit");
		}		
	}

	private DlyType fillType(DlyType type, DlyTypeConfig config){
		Double  firstprice = config.getFirstprice(); //首重费用
		Double  continueprice = config.getContinueprice() ;//续重费用
		Integer firstunit =config.getFirstunit(); //首重重量
		Integer continueunit = config.getContinueunit(); //续重重量
		//组合公式
		String expressions = this.createExpression(firstprice, continueprice, firstunit, continueunit);
		type.setExpressions(expressions);
		type.setConfig(JSON.toJSONString(config));
		return type;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(DlyType type, DlyTypeConfig config,
			TypeAreaConfig[] configArray) {
		
		if(type.getType_id()==null){
			throw new RuntimeException("type id is null ,can't edit");
		}
		//if(config.getDefAreaFee().intValue() == 1){ //启用默认设置
			type= this.fillType(type, config);
		//}
		String typeId =type.getType_id();
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_DLY_TYPE_AREA_DEL_BY_ID"), typeId);
		
		this.addTypeArea(typeId, configArray);
		
		this.baseDaoSupport.update("dly_type", type, "type_id="+type.getType_id());
	}
	
	
	private void addTypeArea(String typeId,TypeAreaConfig[] configArray){
		for(TypeAreaConfig areaConfig :configArray){
			String[] idArray = areaConfig.getAreaId().split(",");
			String closeAreaId ="";
			String checkedAreaId=""; //选中节点字串
			
			//找出关闭并选中的节点，即：|close分隔的节点
			//逻辑以选中节点为目标，不断的组合进去。
			for(String id : idArray){
				if(!checkedAreaId.equals("")) checkedAreaId+=",";
				String[] idarray=id.split("\\|"); 
				if(idarray.length>1){
					if(!closeAreaId.equals("")) closeAreaId+=",";
					closeAreaId+=idarray[0]; //组成关闭并选中节点字串
					checkedAreaId+=idarray[0];//将此节点放入选中的节点中
				}else{
					checkedAreaId+=id; //未关闭节点皆为选中节点
				}
 
			}
			
			//查询所有选中并关闭的节点的子工节点id
			List<Map> areaIdList = new ArrayList<Map>();
			RegionChildrenListReq req = new RegionChildrenListReq();
			req.setRegion_id(closeAreaId);
			RegionChildrenListResp resp = regionsServ.getChildrenListById(req);
			
			if(resp != null){
				areaIdList = resp.getRegionLists();
			}
			//拼入选中节点
			for(Map m:areaIdList){
				checkedAreaId+=","+m.get("region_id");
				/**
				 * 如果是第二级再加载第三级  mochunrun=====
				 */
				String grade = String.valueOf(m.get("region_grade"));
				String regid = String.valueOf(m.get("region_id"));
				if("2".equals(grade)){
					List<Map> areaIdList_3 = new ArrayList<Map>();
					RegionChildrenListReq req1 = new RegionChildrenListReq();
					req1.setRegion_id(regid);
					RegionChildrenListResp resp1 = regionsServ.getChildrenListById(req1);
					
					if(resp1 != null){
						areaIdList_3 = resp1.getRegionLists();
					}
					for(Map m3:areaIdList_3){
						checkedAreaId+=","+m3.get("region_id");
					}
				}
			}
			TypeArea typeArea = new TypeArea();
			typeArea.setArea_id_group(checkedAreaId);
			typeArea.setArea_name_group(areaConfig.getAreaName());
			typeArea.setType_id(typeId);
			typeArea.setHas_cod(areaConfig.getHave_cod());
			typeArea.setConfig( JSON.toJSONString(areaConfig));
			String expressions="";
			if(areaConfig.getUseexp().intValue()==1){ //启用公式
				expressions = areaConfig.getExpression();
			}else{
				//组合公式
				  expressions = createExpression(areaConfig);
			}
			typeArea.setExpressions(expressions);
			this.baseDaoSupport.insert("dly_type_area", typeArea);
		}		
	}
	
	/**
	 * 生成公式字串
	 * @param areaConfig
	 * @return
	 */
	private String createExpression(TypeAreaConfig areaConfig ){
		
		return this.createExpression(areaConfig.getFirstprice(), areaConfig.getContinueprice(), areaConfig.getFirstunit() , areaConfig.getContinueunit());
	}
	
	/**
	 * 生成公式字串
	 * @param firstprice
	 * @param continueprice
	 * @param firstunit
	 * @param continueunit
	 * @return
	 */
	private String createExpression(Double firstprice,Double continueprice,Integer firstunit,Integer continueunit ){
		return  firstprice+"+tint(w-"+firstunit +")/"+continueunit+"*"+continueprice;
	}

	
	@Override
	public Double countExp(String exp,Double weight,Double orderprice) {
        Context cx = Context.enter();   
        try  
        {   
             Scriptable scope = cx.initStandardObjects();   
             String str = "var w="+weight+";";
             str+="p="+orderprice+";";
             str+="function tint(value){return value<0?0:value;}";
             str+=exp;
             Object result = cx.evaluateString(scope, str, null, 1, null);   
             Double  res = Context.toNumber(result);  
             
             return res;
         } catch(Exception e){
        	 e.printStackTrace();
         }
         finally  
         {   
             Context.exit();   
         }  		
		return -1D;
		//return 0.0d;
	}

	
	@Override
	public Double[] countPrice(String typeId,Double weight,Double orderPrice,String regionId, boolean isProtected) {
		
		DlyType dlyType = this.getDlyTypeById(typeId);
		if(dlyType==null){
			//2014-03-18 没有找到不需要费用
			return new Double[]{0d,0d};
		}
		Double dlyPrice = null;
		if(dlyType.getIs_same().compareTo(1)==0){ //统一的费用配置
			dlyPrice = this.countExp(dlyType.getExpressions(), weight, orderPrice);
		}else{
			dlyPrice = this.countPrice(dlyType.getTypeAreaList(), weight, orderPrice, regionId);
		}
		Double protectPrice=null;
		if(isProtected){
			Float protectRate = dlyType.getProtect_rate(); //保价费率
			protectPrice =orderPrice*protectRate/100;   //保价费用
			protectPrice =dlyType.getMin_price().floatValue()>(protectPrice)?dlyType.getMin_price():protectPrice;
		}
		Double[] priceArray = {dlyPrice,protectPrice}; 
		return priceArray;
	}

	
}
