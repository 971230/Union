package com.ztesoft.cms.page.query;

import java.util.ArrayList;

import org.directwebremoting.util.Logger;

import com.ztesoft.cms.page.context.CmsContext;
import com.ztesoft.cms.page.context.CmsObj;
import com.ztesoft.cms.page.vo.CmsInfo;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.ibss.common.util.SqlMapExe;

/**
 * @author Reason.Yea
 * @version Created Oct 25, 2012
 */
public abstract class CmsQuery {
    private Logger log= Logger.getLogger(CmsQuery.class);
	
	/**
	 * 查询列表
	 * @return
	 */
	public CmsInfo doQuery(){
		CmsInfo info = new CmsInfo();
		CmsObj contextObj = CmsContext.getCmsObj();
		String qrySql = null;
		if(contextObj.getCms_state().equals(CmsConst.CMS_STATE_ONLINE)){
			qrySql= getOnlineSql();
		}else{
			qrySql= getOfflineSql();
		}
		if(qrySql.trim().equals("")){
			return info;
		}
		SqlMapExe sqlExe = contextObj.getSqlExe();
		String fun_id = contextObj.getFun_id();
		String info_id = contextObj.getInfo_id();
		int pi = contextObj.getPage_index();
		int ps = contextObj.getPage_size();
		ArrayList param = new ArrayList();
		qrySql+=" and fun_id=? ";
		param.add(fun_id);
		if(!info_id.equals("0")){//0表示查询所有，否则只查询当前条信息
			qrySql+=" and info_id=? ";
			param.add(info_id);
		}
		qrySql+=" order by a.order_id desc ";
        log.debug("CmsQuery ======>"+qrySql);
		PageModel pm= sqlExe.queryPageModelResult(qrySql, param, pi, ps);
		
		info.setFun_id(fun_id);
		info.setPageModel(pm);
		return info;
	}
	public abstract String getOnlineSql();
	public abstract String getOfflineSql();
}
