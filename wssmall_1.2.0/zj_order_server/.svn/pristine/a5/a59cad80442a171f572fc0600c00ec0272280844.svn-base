package com.ztesoft.net.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AuditBusinessVO;
import com.ztesoft.net.service.INoMatchFlowManager;


/**
 * @Description 无匹配流水
 * @author  yanPengJun
 * @date    2016年10月18日
 */
public class NoMatchFlowManagerImpl extends BaseSupport implements INoMatchFlowManager { 
	@Override
	public Page queryList(String is_zero, String bss_or_cbss, String import_date, int pageNo, int pageSize) {
		String sql = " select t.operation_time,t.serial_number,t.phone_number,t.busi_money,t.business_type,t.operator,t.data_from from es_order_audit_business t where t.order_id is null ";
		if(!StringUtils.isEmpty(is_zero)){
			if(StringUtil.equals("1", is_zero)){
				sql+=" and to_number(nvl(t.busi_money,0))<>'0' ";
			}else if(StringUtil.equals("0", is_zero)){
				sql+=" and to_number(nvl(t.busi_money,0))='0' ";
			}
			
		}
		if(!StringUtils.isEmpty(bss_or_cbss)){
			sql+=" and t.data_from = '"+bss_or_cbss+"' ";
		}
		if(!StringUtils.isEmpty(import_date)){	
			sql+=" and trunc(t.create_date) = to_date('"+import_date+"','yyyy-mm-dd') ";
		}
		Page page=daoSupport.queryForPageByMap(sql, pageNo, pageSize, AuditBusinessVO.class, null);
		return page;
	}
	
	public Page queryOrderListById(String order_id , String user_phone_num , int pageNo, int pageSize){
		String sql = " select t.order_id,c.city,t.order_type,b.name as goods_name,t.paymoney,t.busi_money,c.uname,d.phone_num from es_order t,es_delivery a,es_order_items b,es_member c,es_order_items_ext d "
				   + " where t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.order_id=a.order_id and t.order_id=b.order_id and a.member_id=c.member_id and t.order_id=d.order_id ";
		if(!StringUtils.isEmpty(order_id)){
			sql +=" and t.order_id='"+order_id+"' ";
		}
		
		Page page = daoSupport.queryForPageByMap(sql, pageNo, pageSize, null);
		logger.info(sql);
		List list = (List) page.getResult();
		List new_list = new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			String order_type = getCacheName("DC_ORDER_NEW_TYPE",Const.getStrValue(map, "order_type"));
			map.put("order_type", order_type);
			new_list.add(map);
		}
		page.setResult(new_list);
		return page;
		
	}
	
	public Page queryOrderListByPhoneNum(String order_id , String user_phone_num , int pageNo, int pageSize){
		String sql = " select t.order_id,c.city,t.order_type,b.name as goods_name,t.paymoney,t.busi_money,c.uname,d.phone_num from es_order t,es_delivery a,es_order_items b,es_member c,es_order_items_ext d "
				   + " where t.source_from='"+ManagerUtils.getSourceFrom()+"' and t.order_id=a.order_id and t.order_id=b.order_id and a.member_id=c.member_id and t.order_id=d.order_id ";
		if(!StringUtils.isEmpty(user_phone_num)){
			sql +=" and d.phone_num='"+user_phone_num+"' ";
		}
		Page page = daoSupport.queryForPageByMap(sql, pageNo, pageSize, null);
		List list = (List) page.getResult();
		List new_list = new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			String order_type = getCacheName("DC_ORDER_NEW_TYPE",Const.getStrValue(map, "ORDER_TYPE"));
			map.put("order_type", order_type);
			new_list.add(map);
		}
		page.setResult(new_list);
		return page;
		
	}
	
	public String getCacheName(String code,String key){
		String  cacheName = "";
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
		List list = dc.loadData(code);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				String value = (String) map.get("value");
				if(value.equals(key)){
					cacheName = (String) map.get("value_desc");
				}
				
			}
		}
		
		return cacheName;
	}
	
	public Map updateFlow(String order_id, String audit_note,String serial_number,String flg){
		Map result = new HashMap();
		try{
		if(!StringUtil.isEmpty(flg)&&StringUtil.equals(flg, "add")){
			String update_flow_sql = "update es_order_audit_business set order_id='"+order_id+"' where serial_number='"+serial_number+"'";
			String update_order_sql = "update es_order_ext set audit_remark='"+audit_note+"' where order_id='"+order_id+"'";
			daoSupport.execute(update_flow_sql, null);
			daoSupport.execute(update_order_sql, null);
		}
		if(!StringUtil.isEmpty(flg)&&StringUtil.equals(flg, "submit")){
			String update_flow_sql = "update es_order_audit_business set order_id='无匹配流水' where serial_number='"+serial_number+"'";
			daoSupport.execute(update_flow_sql, null);
		}
		
		result.put("res", "0");
		result.put("msg", "操作成功");
		}catch(Exception e){
			result.put("res", "1");
			result.put("msg", e.getMessage());
		}
		return result;
		
	}
}
