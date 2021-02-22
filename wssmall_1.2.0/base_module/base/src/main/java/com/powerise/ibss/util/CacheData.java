/*
 * Created on 2005-9-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.powerise.ibss.util;

import com.powerise.ibss.framework.FrameException;

import java.util.HashMap;
import java.util.Iterator;
/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CacheData {
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
     * 存放到缓存中，按照目录存放
     * @param strCatagory
     * @param strId
     * @param oData
     * @throws FrameException
     */
    public static synchronized void setCacheDataForCms(String strCatagory, String strId, Object oData) throws FrameException {
        HashMap hmData = null;
        Object o = m_Data.get(strCatagory);
        if (o == null) {
            hmData = new HashMap();
        } else {
            hmData = (HashMap) o;
        }
        hmData.put(strId, oData);
        m_Data.put(strCatagory,hmData);
       
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
	public static HashMap getM_Data() {
		return m_Data;
	}
	public static void setM_Data(HashMap data) {
		m_Data = data;
	}
    
}
