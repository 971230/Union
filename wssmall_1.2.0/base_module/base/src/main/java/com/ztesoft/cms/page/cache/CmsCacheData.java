package com.ztesoft.cms.page.cache;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.cms.page.context.CmsContext;
import com.ztesoft.cms.page.context.CmsObj;
import com.ztesoft.cms.page.query.CmsConst;
import com.ztesoft.cms.page.query.CmsQuery;
import com.ztesoft.cms.page.query.impl.CommonQueryImpl;
import com.ztesoft.cms.page.vo.CmsFun;
import com.ztesoft.cms.page.vo.CmsInfo;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Reason.Yea
 * @version Created Oct 25, 2012
 */
public class CmsCacheData {
	private static Logger logger = Logger.getLogger(CmsCacheData.class);
	private static HashMap m_Data = new HashMap();
	
	/**
     * 存放到缓存中，按照目录存放
     * @param strCatagory
     * @param strId
     * @param oData
     * @throws FrameException
     */
    public static synchronized void setCacheData(String strCatagory, String strId, Object oData) throws FrameException {
        HashMap hmData = null;
        Object o = m_Data.get(strCatagory);
        if (o == null) {
            hmData = new HashMap();
        } else {
            hmData = (HashMap) o;
        }
        if (hmData.get(strId) == null)
        {
            hmData.put(strId, oData);
            m_Data.put(strCatagory,hmData);
        }
    }
    /**
     * 从缓存中获取，按照目录获取
     * @param strCatagory
     * @param strId
     * @return
     * @throws FrameException
     */
    public static Object getCacheData(String strCatagory, String strId) throws FrameException {
        String strData = null;
        HashMap hmData = null;
        Object oData = null;
        Object o = m_Data.get(strCatagory);
        if (o != null) {
            hmData = (HashMap) o;
            oData = hmData.get(strId);          
        }
        if(oData==null){
        	logger.info(strId);
        }
        return oData;
    }
    /**
     * 清除所有缓存
     */
    public static void clear() {
        Iterator it = m_Data.values().iterator();
        while (it.hasNext()) {
            ((HashMap) it.next()).clear();
        }
        m_Data.clear();
    }
    /**
     * 增加按照目录清除缓存
     * @param strCatagory
     * @throws FrameException
     */
    public static void clearCatagory(String strCatagory)throws FrameException {
    	Object o = m_Data.get(strCatagory);
    	if (o != null) {
    		((HashMap) o).clear();
    	}
    }
	
	public CmsInfo getCmsInfo() throws FrameException{
		CmsObj cmsObj = CmsContext.getCmsObj();
		String fun_id = cmsObj.getFun_id();
		String info_id = cmsObj.getInfo_id();
		String cms_state = cmsObj.getCms_state();
		String pi = ""+cmsObj.getPage_index();
		String ps = ""+cmsObj.getPage_size();
		CmsFun fun =CmsCacheUtil.getCacheFun(fun_id);//缓存，CmsAdminServlet中启动的时候进行加载
		CmsInfo info = null;
		if(fun==null)return info;
		//如果是编辑模式，则直接查询数据库
		//否则判断cache_flag是否为1，为1则取缓存
		//如果缓存为空，则先查询，再缓存
		String cache_flag = fun.getCache_flag();
		boolean cache = cache_flag!=null && cache_flag.equals(CmsConst.CACHE_FLAG)&& cms_state.equals(CmsConst.CMS_STATE_ONLINE);
		String cache_key=cms_state+"_"+fun_id+"_"+pi+"_"+ps+"_"+info_id;
		if(cache){//缓存了查询结果
			Object obj  = CmsCacheData.getCacheData(CmsCacheUtil.CMS_CACHE_CATALOG, cache_key);
			if(obj!=null){
				info = (CmsInfo) obj;
				if(!(info ==null || info.getPageModel().getList().size() ==0))
				return info;
			}
		}
		if((info ==null || info.getPageModel().getList().size() ==0)){//未缓存查询结果，或者缓存失效，则重新查询
			//如果有自定义函数，则执行自定的查询函数，否则执行默认的查询函数
			CmsQuery query =null;
			if(!fun.getSelf_rule().equals("")){
				try {
					query=(CmsQuery) Class.forName(fun.getSelf_rule()).newInstance();
				} catch (Exception e) {
					logger.info("Self define class ["+fun.getSelf_rule()+"] init error:\n"+e.getMessage());
					query = new CommonQueryImpl();
				}
			}else{
				query = new CommonQueryImpl();
			}
			info=query.doQuery();
			if(cache){
				CmsCacheData.setCacheData(CmsCacheUtil.CMS_CACHE_CATALOG, cache_key,info);
			}
		}
		return info;
	}
}
