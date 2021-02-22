package com.rop.core.utils;

import com.ztesoft.remote.app.service.impl.AppLocalServiceImpl;
import com.ztesoft.remote.pojo.AppInfo;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-07 10:15
 * To change this template use File | Settings | File Templates.
 */
public class ConvertUtils {

    public static AppInfo convert(AppLocalServiceImpl.PM_APP app){
        AppInfo info=new AppInfo();
        info.setAppKey(String.valueOf(app.getApp_key()));
        info.setResult(true);
        info.setAppId(new BigDecimal(app.getApp_id()).intValue());
        info.setAppName(app.getApp_name());
        info.setSourceFrom(app.getSource_from());
        info.setAcctCode(app.getAcct_code());
        info.setAcctName(app.getAcct_name());
        info.setAcctType(app.getAcct_type());
        info.setOwnerId(new BigDecimal(app.getOwner_id()).intValue());
        info.setOwnerCode(String.valueOf(app.getOwner_code()));
        info.setOwnerName(String.valueOf(app.getOwner_name()));
        info.setAppAddress(String.valueOf(app.getApp_address()));
        String theme_source_from = String.valueOf(app.getTheme_source_from());
        if(StringUtil.isEmpty(theme_source_from))
        	theme_source_from = info.getSourceFrom();
        info.setThemeSourceFrom(theme_source_from);
        return info;
    }
}
