package zte.net.ecsord.utils;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class GetAreaInfoUtils {
	public static String getEparchyCode(String order_id) {
		//logger.info();
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		
		String sql = "select ec.areaid" +
				 " from es_order_ext eoe" +
				 " left join es_regions er" +
				 " on eoe.order_city_code = er.region_id"+
				 " left join es_county ec"+
				 " on substr(er.local_name, 0, 2) = ec.areadef" +
				 " where eoe.order_id='"+order_id+"'"+
				 " and ec.region_type = 'city'" +
				 " and ec.source_from ='"+ManagerUtils.getSourceFrom()+"'";
	
		return baseDaoSupport.queryForString(sql);
	}
	 
	public static String getAreaCode(String eparchyCode) {
		String area_code = "";
		if(eparchyCode.equals("A")) {
			area_code = "0571";
		}else if(eparchyCode.equals("B")) {
			area_code = "0577";
		}else if(eparchyCode.equals("C")) {
			area_code = "0570";
		}else if(eparchyCode.equals("D")) {
			area_code = "0572";
		}else if(eparchyCode.equals("E")) {
			area_code = "0573";
		}else if(eparchyCode.equals("F")) {
			area_code = "0575";
		}else if(eparchyCode.equals("G")) {
			area_code = "0576";
		}else if(eparchyCode.equals("H")) {
			area_code = "0578";
		}else if(eparchyCode.equals("I")) {
			area_code = "0579";
		}else if(eparchyCode.equals("J")) {
			area_code = "0580";
		}else if(eparchyCode.equals("K")) {
			area_code = "0574";
		}else if(eparchyCode.equals("Z")) {
			area_code = "0571";
		}
		return area_code;
	}
}
