/**
 * 
 */
package zte.net.iservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import services.ServiceBase;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.warehouse.req.WareHouseMatchRandomReq;
import zte.net.ecsord.params.warehouse.req.WareHouseMatchReq;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchRandomResp;
import zte.net.ecsord.params.warehouse.resp.WareHouseMatchResp;
import zte.net.iservice.IWarehouseService;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.mall.core.model.GoodsInventoryApply;
import com.ztesoft.net.mall.core.model.WareHouseAttrCfg;
import com.ztesoft.net.mall.core.model.WareHouseAttrCfgList;
import com.ztesoft.net.mall.core.model.Warehouse;

/**
 * @author ZX
 *
 */
public class WarehouseService extends ServiceBase implements IWarehouseService {

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "仓库出库方案地市优先规则", summary = "仓库出库方案地市优先规则")
	public WareHouseMatchResp warehouseAreaPriorityRule(WareHouseMatchReq req) {
		// TODO Auto-generated method stub
		List list =req.getWareHouseList();
		WareHouseMatchResp resp = new WareHouseMatchResp();
		String order_id = req.getOrder_id();
		String city = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
		//第一层 虚拟仓库
		List result= new ArrayList();
		for(int i=0;i<list.size();i++){
			boolean flag0=false;
			Map<String,List> virtualMap =(Map<String,List>)list.get(i);
			Iterator it= virtualMap.entrySet().iterator();
			Map<Object,List> map1 = new HashMap<Object,List>();
			while(it.hasNext()){
				boolean flag1=false;
				Map.Entry entry =(Map.Entry)it.next();
				logger.info("虚拟仓库："+entry.getKey());
				List<Map<String,List>> logList =(List<Map<String,List>>)entry.getValue();
				List result2= new ArrayList();
				for(Map<String,List> logMap:logList){
					boolean flag2 = false;
					Iterator logIt= logMap.entrySet().iterator();
					Map<Object,List> map2 = new HashMap<Object,List>();
					while(logIt.hasNext()){
						Map.Entry logEntry =(Map.Entry)logIt.next();
						List<Map<Object,List>> phyList =(List<Map<Object,List>>)logEntry.getValue();
						logger.info("虚拟仓库："+entry.getKey()+"的逻辑仓库："+logEntry.getKey());
						List result3= new ArrayList();
						for(Map<Object,List> phyMap:phyList){
							boolean flag3=false;
							Iterator phyIt= phyMap.entrySet().iterator();
							
							Map<String,Object> map3 = new HashMap<String,Object>();
							while(phyIt.hasNext()){
								Map.Entry phyEntry =(Map.Entry)phyIt.next();
								String phyHouseId = (String)phyEntry.getKey();
								logger.info("虚拟仓库："+entry.getKey()+"的逻辑仓库："+logEntry.getKey()+"的物理仓库："+phyHouseId);
								Warehouse phyWarehouse = getWareHouseById(phyHouseId);
								logger.info("虚拟仓库："+entry.getKey()+"的逻辑仓库："+logEntry.getKey()+"的物理仓库："+phyHouseId+"的属性："+phyWarehouse.getAttr());
								if(StringUtils.isNotEmpty(phyWarehouse.getAttr())){
									WareHouseAttrCfgList attrList = JsonUtil.fromJson(phyWarehouse.getAttr(),WareHouseAttrCfgList.class);
									for(WareHouseAttrCfg attr:attrList.getAttrList()){
										if(AttrConsts.ORDER_CITY_CODE.equals(attr.getEname())&&city.equals(attr.getValue())){
											map3.put(phyHouseId, phyEntry.getValue());
											flag3=true;
											break;
										}
									}
								}
							}
							if(flag3){
								result3.add(map3);
								flag2=true;
							}
						}
						if(flag2){
							map2.put(logEntry.getKey(), result3);
							flag1=true;
						}
					}
					if(flag2){
						result2.add(map2);
						flag1=true;
					}
				}
				if(flag1){
					map1.put(entry.getKey(), result2);
					flag0=true;
				}
			}
			if(flag0){
				result.add(map1);
			}
		}
		if(result.size()>0){
			resp.setWareHouseList(result);
		}else{
			resp.setWareHouseList(list);
		}
		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "仓库出库方案随机规则，所有出库规则最后都需要调用此规则", summary = "仓库出库方案随机规则")
	public WareHouseMatchRandomResp randomFilter(WareHouseMatchRandomReq req) {
		// TODO Auto-generated method stub
		WareHouseMatchRandomResp resp = new WareHouseMatchRandomResp();
		
		return resp;
	}
	
	private Warehouse getWareHouseById(String houseId) {
		String sql = "select * from es_warehouse where house_id=?";
		List<Warehouse> list = baseDaoSupport.queryForList(sql, houseId);
		return list.get(0);
	}
	/**
	 * 根据货品ID查询虚拟仓库库存信息
	 * @param product_id
	 * @return
	 */
	private List<GoodsInventoryApply> getVirtualHouseInventoryByProductId(String goods_id) {
		String sql = "select * from es_goods_inventory_apply where inventory_num>0 and apply_type='L2V' and goods_id =? ";
		List<GoodsInventoryApply> list = baseDaoSupport.queryForList(sql, goods_id);
		return list;
	}
	
}
