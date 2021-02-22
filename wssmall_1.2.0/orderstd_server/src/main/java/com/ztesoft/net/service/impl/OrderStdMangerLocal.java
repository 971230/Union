package com.ztesoft.net.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderMangerLocal;
import com.ztesoft.net.sqls.SF;

import net.sf.json.JSONArray;

@SuppressWarnings("all")
public class OrderStdMangerLocal  extends BaseSupport implements IOrderMangerLocal {
	@Override
	public Map<String,String> getGoodsInfo(String package_id){
		List pList = new ArrayList();
		String sql ="select * from es_gdlt_v_ssc_goods_sku where sku = '"+package_id+"'";
		
		List<Map> datalist = this.baseDaoSupport.queryForList(sql);
		Map data = null;
		if(datalist!=null && datalist.size()>0)
			data = datalist.get(0);
		
		Map<String,String> goods = new HashMap<String,String>();
		if(data==null)
			return goods;
		
		//获取合约类型
		String active_type = getActiveType(data.get("goods_id").toString());
		data.put("ative_type", active_type);
		
		//获取型号信息
		Map<String,String> model = getProductModel(data.get("goods_id").toString());
		if(!model.isEmpty()){
			data.put("specification_code", model.get("specification_code"));
			data.put("specification_name", model.get("specification_name"));
		}
		//是否iphone套餐
		String is_iphone_plan = getIsIphonePlan(data.get("goods_id").toString());
		data.put("is_iphone_plan", is_iphone_plan);
		
		//获取颜色
		Map<String,String> color = getColor(data.get("goods_id").toString());
		if(!color.isEmpty()){
			data.put("color_code", color.get("color_code"));
			data.put("color_name", color.get("color_name"));
		}
//		获取大类类型type_id
		String type_id = data.get("type_id").toString();
		if(null != type_id && !"".equals(type_id)){
			data.put("type_id", type_id);
		}
		
 		String params = (String) data.get("params");
 		if ("<CLOB>".equalsIgnoreCase(params)) {
			params = "";
		}
		ParamGroup[] paramArs = converFormString( params);// 处理参数
		if(paramArs!=null){
			for(ParamGroup group : paramArs){
				List list = group.getParamList();
				for(int i=0;i<list.size();i++){
					GoodsParam param = (GoodsParam) list.get(i);
					data.put(param.getEname(), param.getValue());
				}
			}
		}
		
		if(data!=null && !data.isEmpty()){
			Iterator it = data.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				goods.put(key, String.valueOf(data.get(key)));
			}
		}
		return goods;
	}
	
	public String getActiveType(String goods_id) {
		String sql = SF.goodsSql("ACTIVE_TYPE_GET");
		String active_type = this.baseDaoSupport.queryForString(sql, goods_id);
		return active_type;
	}
	
	public Map<String,String> getProductModel(String goods_id){
		String sql = SF.goodsSql("PRODUCT_MODEL");
		List<Map<String,String>> data = this.baseDaoSupport.queryForList(sql, goods_id);
		if(data!=null && data.size()>0)
			return data.get(0);
		else
			return new HashMap<String,String>();
	}
	
	public String getIsIphonePlan(String goods_id){
		/*String sql = SF.goodsSql("IS_IPHONE_PLAN");
		String is_iphone_plan = this.baseDaoSupport.queryForString(sql,goods_id);
		return is_iphone_plan;*/
		String is_iphone_plan = "0";  //默认否
		String sql = SF.goodsSql("IS_IPHONE_PLAN");
		List list = this.baseDaoSupport.queryForList(sql,goods_id);
		if (list != null && list.size() > 0) {
			
			is_iphone_plan = (String)((Map)list.get(0)).get("is_iphone_plan");
		}
		
		return is_iphone_plan;
	}
	
	public Map<String,String> getColor(String goods_id){
		String sql = SF.goodsSql("COLOR_GET");
		List<Map<String,String>> colors = this.baseDaoSupport.queryForList(sql, goods_id);
		if(colors!=null && colors.size()>0)
			return colors.get(0);
		else
			return new HashMap<String,String>();
	}
	
	public static ParamGroup[] converFormString(String params) {
		if (params == null || "".equals(params) || "[]".equals(params)||"null".equals(params))
			return null;
		Map<String,Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("paramList", GoodsParam.class);
		JSONArray jsonArray = JSONArray.fromObject(params);

		Object obj = JSONArray.toArray(jsonArray, ParamGroup.class, classMap);

		if (obj == null)
			return null;

		return (ParamGroup[]) obj;
		
	}
	
	@Override
	public String getCityname(String cityId) throws FrameException, SQLException{
		String m = baseDaoSupport.queryForString("select z.name from es_regions_zb z where z.add_level = '2' and z.code='"+cityId+"'");

		return m;
	}
	

	
	@Override
	public String getProviceId(String cityId) throws FrameException, SQLException{
		String m = baseDaoSupport.queryForString("select z.pre_code from es_regions_zb z where z.add_level='2' and z.code='"+cityId+"'");
		return m;
	}
	
	@Override
	public String getProvicename(String cityId) throws FrameException, SQLException{
		String m = baseDaoSupport.queryForString("select z.name from es_regions_zb z where z.add_level = '1' and z.code = (select a.pre_code from es_regions_zb a where a.add_level='2' and a.code = '"+cityId+"')");
		return m;
	}
	
	@Override
	public String queryString(String sql) throws FrameException, SQLException{
		String m = baseDaoSupport.queryForString(sql);
		return m;
	}
	
	@Override
	public Map queryForMap(String sql,Object... args) throws FrameException, SQLException{
		Map m = baseDaoSupport.queryForMap(sql, args);
		return m;
	}

	/**
	 * 根据goods_id修改华盛物料号
	 */
	@Override
	public boolean updateHSMatnrByGoodsId(String goods_id , String matnr) {
		boolean flag = false;
		try{
			if(StringUtils.isEmpty(goods_id)){
				return false;
			}
			String sql = "select count(*) from es_goods_package g where g.goods_id = ?";
			int nums = this.baseDaoSupport.queryForInt(sql, goods_id);
			//es_goods_package表无数据时新增
			if(nums == 0){
				sql = "insert into es_goods_package (GOODS_ID,SOURCE_FROM,HS_MATNR) values (?,?,?)";
				this.baseDaoSupport.execute(sql, goods_id,ManagerUtils.getSourceFrom(),matnr);
				flag = true;
			}else{
				sql = "update es_goods_package g set g.hs_matnr = ? where g.goods_id = ?";
				this.baseDaoSupport.execute(sql, matnr , goods_id);
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Map<String, String> getHSGoodsByMatnr(String matnr) {
		List pList = new ArrayList();
		String sql = "select g.goods_id,g.name goods_name,g.type_id,g.cat_id product_type,g.params, "
				+ " p.price goods_price,m.model_code specification_code,g.source_from, "
				+ " m.model_name specification_name,b.name brand_name,b.brand_code brand_number, "
				+ " m.machine_code model_code,m.machine_name model_name,s.spec_value_id color_code, "
				+ " v.spec_value color_name, p.product_id " 
				+ " from es_goods g "
				+ " left join es_brand b on g.brand_id=b.brand_id " + ManagerUtils.apSFromCond("b")
				+ " left join es_brand_model m on m.model_code= g.model_code " + ManagerUtils.apSFromCond("m")
				+ " left join es_goods_package pkg on g.goods_id=pkg.goods_id "+ManagerUtils.apSFromCond("pkg")
				+ " left join es_product p on g.goods_id=p.goods_id "+ManagerUtils.apSFromCond("p")+""
				+ " left join es_goods_spec s on s.product_id=p.product_id and s.spec_id=1  "+ManagerUtils.apSFromCond("s")
				+ " left join es_spec_values v on s.spec_value_id=v.spec_value_id "+ManagerUtils.apSFromCond("v")+""
				+ " where p.type='goods' and g.source_from='"+ManagerUtils.getSourceFrom()+"' ";;
		sql = sql + " AND pkg.hs_matnr = ?";
		List<Map> datalist = this.baseDaoSupport.queryForList(sql,matnr);
		Map data = null;
		if(datalist!=null && datalist.size()>0)
			data = datalist.get(0);
		
		Map<String,String> goods = new HashMap<String,String>();
		if(data==null)
			return goods;
		
		//获取合约类型
		String active_type = getActiveType(data.get("goods_id").toString());
		data.put("ative_type", active_type);
		
		//获取型号信息
		Map<String,String> model = getProductModel(data.get("goods_id").toString());
		if(!model.isEmpty()){
			data.put("specification_code", model.get("specification_code"));
			data.put("specification_name", model.get("specification_name"));
		}
		//是否iphone套餐
		String is_iphone_plan = getIsIphonePlan(data.get("goods_id").toString());
		data.put("is_iphone_plan", is_iphone_plan);
		
		//获取颜色
		Map<String,String> color = getColor(data.get("goods_id").toString());
		if(!color.isEmpty()){
			data.put("color_code", color.get("color_code"));
			data.put("color_name", color.get("color_name"));
		}
//		获取大类类型type_id
		String type_id = data.get("type_id").toString();
		if(null != type_id && !"".equals(type_id)){
			data.put("type_id", type_id);
		}
		
 		String params = (String) data.get("params");
 		if ("<CLOB>".equalsIgnoreCase(params)) {
			params = "";
		}
		ParamGroup[] paramArs = converFormString( params);// 处理参数
		if(paramArs!=null){
			for(ParamGroup group : paramArs){
				List list = group.getParamList();
				for(int i=0;i<list.size();i++){
					GoodsParam param = (GoodsParam) list.get(i);
					data.put(param.getEname(), param.getValue());
				}
			}
		}
		
		if(data!=null && !data.isEmpty()){
			Iterator it = data.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				goods.put(key, String.valueOf(data.get(key)));
			}
		}
		return goods;
	}

	@Override
	public String getCountyName(String ship_country) throws FrameException, SQLException {
		String m = baseDaoSupport.queryForString("select z.name from es_regions_zb z where z.add_level = '3' and z.code='"+ship_country+"'");
		return m;
	}


	
}
