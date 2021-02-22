package com.powerise.ibss.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.ActionData;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.ServiceData;
import com.ztesoft.ibss.common.util.ListUtil;

/**
 * @author Reason.Yea
 * @version Created Feb 6, 2012
 */
public class WBCacheUtil {
    private static Logger logger = Logger.getLogger(WBCacheUtil.class);
    /**
     * SQL服务目录
     */
    public static final String ACTION_CATAGORY="ACTION";
    /**
     * Java服务目录
     */
    public static final String SERVICE_CATAGORY="SERVICE";

    private static String SPLIT_FLAG="`";

    private WBCacheUtil(){
    }

    /**
     * 缓存dict.m_Values
     * @param dict
     * @param strDBName
     */
    public static  void putAction(DynamicDict dict,String strDBName ){
        boolean cacheFlag = false;
        try {
            if(dict.flag<0){//失败标志
                return;
            }
            cacheFlag = getDictCacheFlag(dict, cacheFlag);
            if(cacheFlag){//缓存标志
                String key= getDictKey(dict,strDBName);
                if(key==null|| key.equals(""))return ;
                String cacheCatalog = getDictCacheCatalog(dict);
                logger.debug("设置缓存 Action ID："+dict.m_ActionId+",key:"+key);
                HashMap map = (HashMap) dict.m_Values.clone();
                CacheData.setCacheData(cacheCatalog,key, map);
            }
        } catch (FrameException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取dict
     * @param dict
     * @param strDBName
     * @return
     */
    public static DynamicDict getAction(DynamicDict dict,String strDBName ) {
        boolean cacheFlag = false;
        Object ret=null;
        try {
            cacheFlag = getDictCacheFlag(dict, cacheFlag);
            if(!cacheFlag)return null;
            String key = getDictKey(dict,strDBName);
            if(key==null|| key.equals(""))return null;
            String cacheCatalog = getDictCacheCatalog(dict);
            ret = CacheData.getCacheData(cacheCatalog, key);
            if(ret!=null){//缓存存在
                logger.debug("获取缓存 Action ID："+dict.m_ActionId+",key:"+key);
                dict.m_Values=(HashMap)((HashMap)ret).clone();
                return dict;
            }else{
                return null;
            }
        } catch (FrameException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取dict
     * @param dict
     * @param strDBName
     * @return
     */
    public static void setAction(DynamicDict dict,String strDBName ) {
        boolean cacheFlag = false;
        Object ret=null;
        try {
            cacheFlag = getDictCacheFlag(dict, cacheFlag);
            if(!cacheFlag)return ;
            String key = getDictKey(dict,strDBName);
            if(key==null|| key.equals(""))return ;
            String cacheCatalog = getDictCacheCatalog(dict);
            CacheData.setCacheDataForCms(cacheCatalog, key, null);
            if(ret!=null){//缓存存在
                logger.debug("获取缓存 Action ID："+dict.m_ActionId+",key:"+key);
                dict.m_Values=(HashMap)((HashMap)ret).clone();
                return ;
            }else{
                return ;
            }
        } catch (FrameException e) {
            e.printStackTrace();
        }
        return ;
    }


    /**
     * 设置缓存Dict
     * @param cacheCatalog
     * @param key
     * @param o
     */
    public static void putCache(String cacheCatalog, String key,Object o){
        try {
            if(o!=null){
                //logger.debug("Set cache catagory:"+cacheCatalog+" ,key:"+key);
                CacheData.setCacheData(cacheCatalog, key,o);
            }
        } catch (FrameException e) {
            e.printStackTrace();
        }
    }
    public static Object getCache(String cacheCatalog, String key){
        Object object =null;
        try {
            //logger.debug("Get cache catagory:"+cacheCatalog+" ,key:"+key);
            object =CacheData.getCacheData(cacheCatalog, key);
        } catch (FrameException e) {
            e.printStackTrace();
        }
        return object;
    }
    /**
     * 清除所有缓存
     */
    public static void clearCache(){
        logger.debug("Clear cache all cache!");
        CacheData.clear();
    }
    /**
     * 按照目录清除缓存
     * @param strCatagory
     */
    public static void clearCache(String strCatagory) {
        try {
            logger.debug("Clear cache catagory:"+strCatagory);
            CacheData.clearCatagory(strCatagory);
        } catch (FrameException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取dict的缓存标志
     * @param dict
     * @param cacheFlag
     * @return
     * @throws FrameException
     */
    private static boolean getDictCacheFlag(DynamicDict dict, boolean cacheFlag)
            throws FrameException {
        if(!SysSet.isFrameDataInMem()){
            return false;//配置了缓存才会缓存dict，否则不缓存
        }
        if(dict.flag == 1){//sql服务
            ActionData  m_CurActData = SysSet.getActionData(dict.m_ActionId, null);
            if(m_CurActData.m_CacheFlag!=null && m_CurActData.m_CacheFlag.equals("1")){//cache_flag为1表示缓存
                cacheFlag = true;
            }
        }else{//java服务
            ServiceData m_CurSerData = SysSet.getServData(dict.m_ActionId, null);
            if(m_CurSerData == null)
                return false;
            if(m_CurSerData.m_CacheFlag!=null && m_CurSerData.m_CacheFlag.equals("1")){//cache_flag为1表示缓存
                cacheFlag = true;
            }
        }
        return cacheFlag;
    }

    /**
     * 缓存目录
     * @param dict
     * @return
     */
    private static String getDictCacheCatalog(DynamicDict dict) {
        return dict.flag==1?ACTION_CATAGORY:SERVICE_CATAGORY;
    }

    /**
     * 生产缓存的key值，根据actionId和参数组装
     * @param dict
     * @param strDBName
     * @return
     */
    private static String getDictKey(DynamicDict dict, String strDBName) {
        if(dict.m_CacheKey!=null && !dict.m_CacheKey.equals("")){
            return dict.m_CacheKey;
        }
        if(strDBName==null )strDBName="";
        String actionId = dict.m_ActionId;
        String parameters;
        try {
            parameters = getParameter(dict);
            String ret =ACTION_CATAGORY+SPLIT_FLAG+actionId+SPLIT_FLAG+parameters+SPLIT_FLAG+strDBName;
            dict.m_CacheKey = ret.toUpperCase();
            return dict.m_CacheKey;
        } catch (FrameException e) {
            logger.info(e.getErrorMsg());
        }
        return null;
    }
    /**
     * 得到参数链接列表
     * @param dict
     * @return
     * @throws FrameException
     */
    private static String getParameter(DynamicDict dict) throws FrameException {
        HashMap param = dict.m_Values;
        return getMapKey(param);
    }
    /**
     * 得到map的key
     * @param map
     * @return
     */
    private static String getMapKey(Map map) {
        Set entries = map.entrySet();
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
            Map.Entry entry =(Entry) iterator.next();
            String key = (String) entry.getKey();
            if(key.equalsIgnoreCase("parameter")){
                Map param = (Map) entry.getValue();
                sb.append(getMapKey(param));
            }else{
                Object en = entry.getValue();
                if(en.getClass().getName().equalsIgnoreCase("java.lang.String")){
                    String value = (String) entry.getValue();
                    if(key.equalsIgnoreCase("ip"))continue;
                    sb.append(key).append(SPLIT_FLAG);
                    sb.append(value).append(SPLIT_FLAG);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 列表按key分组
     * @param configList
     * @param key
     * @return
     */
    public static Map listToMapByFieldName(List configList,String key) {
        Map cardMap = new HashMap();
        for (Iterator iterator = configList.iterator(); iterator.hasNext();){
            HashMap config = (HashMap) iterator.next();
            String card_type =(String) config.get(key);
            List cards= (List)cardMap.get(card_type);
            if(ListUtil.isEmpty(cards)){
                cards = new ArrayList();
                cardMap.put(card_type, cards);
            }
            cards.add(config);
        }
        return cardMap;
    }
}

