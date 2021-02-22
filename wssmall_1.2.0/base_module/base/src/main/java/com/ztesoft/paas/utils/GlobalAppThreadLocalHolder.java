package com.ztesoft.paas.utils;

import com.ztesoft.remote.pojo.AppInfo;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-11 16:22
 * To change this template use File | Settings | File Templates.
 * 设置app信息
 */
public class GlobalAppThreadLocalHolder {
    private static ThreadLocal<AppInfo> infoThreadLocal=new ThreadLocal<AppInfo>();



    public static void setAppInfo(AppInfo info){
        infoThreadLocal.set(info);
    }

    public static AppInfo getAppInfo(){
        return infoThreadLocal.get();
    }

    public static void clear(){
        infoThreadLocal.remove();
    }
}
