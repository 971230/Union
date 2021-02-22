package com.ztesoft.cms.page.cache;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBUtil;
import com.ztesoft.cms.page.bo.CmsContentBO;
import com.ztesoft.cms.page.query.CmsConst;
import com.ztesoft.cms.page.vo.CmsFun;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.SqlMapExe;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Reason.Yea
 * @version Created Oct 30, 2012
 */
public class CmsCacheUtil {
	private static Logger m_Logger = Logger.getLogger(CmsCacheUtil.class);
	private static final long serialVersionUID = 8256030739092404414L;
	private static final String DB_NAME = "dataSource";

	/**
	 * cms内容缓存目录
	 */
	public static final String CMS_CACHE_CATALOG = "CMS_CACHE_CATALOG";
	/**
	 * cms功能缓存目录
	 */
	public static final String CMS_FUN_CATALOG = "CMS_FUN_CATALOG";

	public static final String CMS_CONTENT_CATALOG = "CMS_CONTENT_CATALOG";

	public static final String CMS_CONTENT_EDIT_CATALOG = "CMS_CONTENT_EDIT_CATALOG";

	/**
	 * 初始化cms_fun
	 */
	public static void init(String type) {
		// 加载缓存前首先清掉缓存
		Connection conn = null;
		try {
			clearCache(type);
			conn = DBUtil.getConnection(DB_NAME);
			SqlMapExe sqlExe = new SqlMapExe(conn);
			doCache(sqlExe);
		} catch (Exception e) {
			m_Logger.info("获取框架配置错误:" + e);
		} finally {
			DBUtil.closeConnection(conn);
		}
	}

	private static void doCache(SqlMapExe sqlExe) {
		// 此处可以添加需要cache的数据
		try {
			cacheCmsFun(sqlExe);
			cacheCmsContent(sqlExe);
			m_Logger.info("cacheCmsFun success!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void cacheCmsFun(SqlMapExe sqlExe) throws FrameException {
		String sql = "select fun_id,fun_name,fun_type,file_id,show_cols,edit_cols,state,visit_url,cache_flag,edit_page,self_rule,oper_staff_no from cms_fun where state='1'";
		List ls = sqlExe.queryForMapListBySql(sql, new String[] {});
		for (int i = 0; i < ls.size(); i++) {
			HashMap map = (HashMap) ls.get(i);
			CmsFun fun = new CmsFun(map);
			CmsCacheData.setCacheData(CmsCacheUtil.CMS_FUN_CATALOG, fun
					.getFun_id(), fun);
		}
	}

	private static void cacheCmsContent(SqlMapExe sqlExe) throws FrameException {
		String sql = " select a.fun_id val,a.serv_kind kind ,b.file_path path from CMS_FUN a,CMS_CONTENT b WHERE a.file_id=b.file_id and a.fun_type='url'"
				+ " UNION SELECT to_char(c.info_id) val,c.info_kind kind,b.file_path path FROM cms_info c,cms_content b WHERE c.file_id = b.file_id ";
		List ls = sqlExe.queryForMapListBySql(sql, new String[] {});
		Map map = null;
		for (int i = 0; i < ls.size(); i++) {
			map = (HashMap) ls.get(i);
			CmsCacheData.setCacheData(CMS_CONTENT_CATALOG, Const.getStrValue(
					map, "val"), map);
		}
		// sql = " select a.fun_id val,a.serv_kind kind ,b.file_path path from
		// CMS_FUN a,CMS_CONTENT_EDIT b WHERE a.file_id=b.file_id and
		// a.fun_type='url' and b.sequ=0 "+
		// " UNION SELECT to_char(c.info_id) val,c.info_kind kind,b.file_path
		// path FROM cms_info c,cms_content_edit b WHERE c.file_id = b.file_id
		// and b.sequ=0 ";;
		// ls = sqlExe.queryForMapListBySql(sql,new String[]{});
		// for (int i = 0; i < ls.size(); i++) {
		// map = (HashMap) ls.get(i);
		// CmsCacheData.setCmsCacheData(CMS_CONTENT_EDIT_CATALOG,
		// Const.getStrValue(map, "val"), map);
		// }
	}

	public static CmsFun getCacheFun(String fun_id) throws FrameException {
		return (CmsFun) CmsCacheData.getCacheData(CMS_FUN_CATALOG, fun_id);
	}

	private static void clearCache(String type) throws FrameException {
		CmsCacheData.clearCatagory(CMS_FUN_CATALOG);
		CmsCacheData.clearCatagory(CMS_CACHE_CATALOG);
		CmsCacheData.clearCatagory(CMS_CONTENT_CATALOG);
		CmsCacheData.clearCatagory(CMS_CONTENT_EDIT_CATALOG);
	}

	public static HashMap getCacheContent(String val, HttpSession session)
			throws FrameException {
		String state = (String) session.getAttribute(CmsConst.CMS_SESSION_KEY);

		boolean offline = CmsConst.CMS_STATE_OFFLINE.equals(state);// 离线状态
		HashMap retMap = (HashMap) CmsCacheData.getCacheData(
				CmsCacheUtil.CMS_CONTENT_CATALOG, val);// 默认从正式表中取数据
		if (offline) {// 离线模式直接数据库查询，不缓存
		// retMap = (HashMap)
		// CmsCacheData.getCmsCacheData(CmsCacheUtil.CMS_CONTENT_EDIT_CATALOG,
		// val);
			DynamicDict m_dict = new DynamicDict(1);
			m_dict.m_ActionId = "CMSCONTENTBO";
			m_dict.setValueByName("method",
					CmsContentBO.KEY.GET_OFFLINE_CONTENT);
			m_dict.setValueByName("val", val);
			ActionDispatch.dispatch(m_dict);
			retMap = (HashMap) m_dict.getValueByName("CONTENT");
		}
		return retMap;
	}
}
