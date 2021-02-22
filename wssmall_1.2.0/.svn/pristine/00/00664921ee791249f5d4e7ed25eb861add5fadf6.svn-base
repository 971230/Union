package com.rop.core.cache;

import com.ztesoft.remote.pojo.AppInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-07 09:54
 * To change this template use File | Settings | File Templates.
 */
public class AppInfoCache {
    private static Map<String,AppInfo> infoMap=new HashMap<String,AppInfo>();

    private static class AppInfoCacheClassLoader{
        private static  AppInfoCache instance=new AppInfoCache();
    }
    private AppInfoCache(){

    }

    public static AppInfoCache instance(){
        return AppInfoCacheClassLoader.instance;
    }

    public void clear(){
        infoMap.clear();
    }

    public AppInfo get(String key){
        return (null!=infoMap.get(key))?infoMap.get(key):null;
    }

    public void set(String key,AppInfo info){
         infoMap.put(key,info);
    }

    public int count(){
        return infoMap.size();
    }
}
