package com.ztesoft.cms.page.bo;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.ibss.common.util.SqlMapExe;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class CmsPageCommonBo implements IAction {
	
	@Override
	public int perform(DynamicDict aDict) throws FrameException {
		String type=(String) aDict.getValueByName("type",false);
		if(type.equals("GETSTATICDATA")){//获取静态数据
			getStaticData(aDict);
		}else if(type.equals("GETNEWID")){//获取cms信息表主键
			getNewId(aDict);
		}else if(type.equals("INITAREACODE")){//初始化本地网
			initAreaCode(aDict);
		}
		return 0;
	}
	/**
	 * 
	 * @param dict
	 * @throws FrameException
	 */
	private void initAreaCode(DynamicDict dict) throws FrameException {
		SqlMapExe sqlExe  = new SqlMapExe(dict.GetConnection());
		String sql = " SELECT a.city_name,a.area_code FROM tvlwb_city_info a order by a.disp_order asc ";
		List li = new ArrayList();
		li = sqlExe.queryForMapList(sql);
		dict.setValueByName("RET", li);
	}
	/**
	 * 
	 * @param dict
	 * @throws FrameException
	 */
	private void getNewId(DynamicDict dict) throws FrameException {
		String id = getSimulatedSequence();
		dict.setValueByName("id",id);
	}

	private String getSimulatedSequence() {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
    	StringBuffer prefix = new StringBuffer(32);
        prefix.append(sFormat.format(new Date()));
        
        int num = new Random(System.currentTimeMillis()).nextInt(10000);
        prefix.append(num);
        return prefix.toString();
	}
	/**
	 * 
	 * @param dict
	 * @throws FrameException
	 */
	private void getStaticData(DynamicDict dict) throws FrameException {
		SqlMapExe sqlExe = new SqlMapExe(dict.GetConnection());
		String attr_codes = (String) dict.getValueByName("ATTR_CODES", false);
		String attr_code_arr[] = attr_codes.split(",");
		Map retMap = new HashMap();
		for (int i = 0; i < attr_code_arr.length; i++) {
			String attr_code = attr_code_arr[i];
			if (StringUtils.isNotEmpty(attr_code)) {
				String sql = " select a.dc_sql from dc_sql a where a.dc_name=? ";
				String dc_sql = sqlExe.querySingleValue(sql, new String[] {attr_code});
				if (StringUtils.isNotEmpty(dc_sql)) {
					List list = sqlExe.queryForMapList(dc_sql);
					retMap.put(attr_code, list);
				} 
			}
		}
		dict.setValueByName("StaticDataMap",retMap);
	}
	


}
