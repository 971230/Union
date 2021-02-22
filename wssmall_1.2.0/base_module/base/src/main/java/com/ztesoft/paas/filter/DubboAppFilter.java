package com.ztesoft.paas.filter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import params.ZteRequest;
import utils.CoreThreadLocalHolder;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;
import com.ztesoft.net.eop.sdk.context.AppInfoSetting;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.paas.utils.GlobalAppThreadLocalHolder;
import com.ztesoft.remote.app.service.IAppLocalService;
import com.ztesoft.remote.pojo.AppInfo;

import commons.CommonTools;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-24 09:42
 * To change this template use File | Settings | File Templates.
 */
@Activate(group = "provider")
public class DubboAppFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
    	Result result =null;
        logger.info("获取来源信息:{}", invocation.getArguments());
        try{
	        this.setSourceFrom(invocation);
	        result = invoker.invoke(invocation);
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	 CoreThreadLocalHolder.getInstance().clear();
             CoreThreadLocalHolder.getInstance().getZteCommonData().removeAppKey();
        }
       
        return result;
    }

    private void setSourceFrom(final Invocation invocation) {
        AppInfo info=null;
        try {
            for (Object obj : invocation.getArguments()) {
                if (obj instanceof ZteRequest) {
                    ZteRequest zteRequest = (ZteRequest)obj;
                    info=getSource(zteRequest.getAppKey());
                    if(null!=info){
                        GlobalAppThreadLocalHolder.setAppInfo(info);
                        PropertyUtils.setProperty(obj, "appSecret", "");
                        PropertyUtils.setProperty(obj, "sourceFrom",  info.isResult()?info.getSourceFrom():"wssmall");
                    }
                    //拦截器中设置到线程变量中去
                    CoreThreadLocalHolder.getInstance().getZteCommonData().setAppKeys(zteRequest.getAppKey()); //设置传递的appkey值
                    CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(zteRequest);
                    zteRequest.getUserSessionId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            List list = new ArrayList();
            list.add(invocation.getMethodName());
            list.add(invocation.getParameterTypes());
            CommonTools.addError("-1", "DubboAppFilter获取来源出错");
            logger.debug("设置来源出错:method==>{},ParameterTypes==>{}", list.toArray());
        }
    }

    private AppInfo getSource(String  appKey){
        try{
            AppInfo info=null;
            if(StringUtils.isNotBlank(appKey)){
            	if("N".equals(EopSetting.DB_CONNECT)){
            		info=new AppInfo();
                    info.setAppKey(AppInfoSetting.props.getProperty("owner_id"));
                    info.setResult(true);
                    info.setAppId(new BigDecimal(AppInfoSetting.props.getProperty("app_id")).intValue());
                    info.setAppName(AppInfoSetting.props.getProperty("app_name"));
                    info.setSourceFrom(AppInfoSetting.props.getProperty("source_from"));
                    info.setAcctCode(AppInfoSetting.props.getProperty("acct_code"));
                    info.setAcctName(AppInfoSetting.props.getProperty("acct_name"));
                    info.setAcctType(AppInfoSetting.props.getProperty("acct_type"));
                    info.setOwnerId(new BigDecimal(AppInfoSetting.props.getProperty("owner_id")).intValue());
                    info.setOwnerCode(AppInfoSetting.props.getProperty("owner_code"));
                    info.setOwnerName(AppInfoSetting.props.getProperty("owner_name"));
                    info.setAppAddress(AppInfoSetting.props.getProperty("app_address"));
                    String theme_source_from = AppInfoSetting.props.getProperty("theme_source_from");
                    if(StringUtils.isEmpty(theme_source_from))
                    	theme_source_from = info.getSourceFrom();
                    info.setThemeSourceFrom(theme_source_from);
            	}
            	else{
            		IAppLocalService appLocalService = (IAppLocalService)SpringContextHolder.getBean("appLocalService");
                    //logger.debug(appLocalService+"===================================");
                    info=appLocalService.validate(appKey);
            	}
            }

            return info;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

