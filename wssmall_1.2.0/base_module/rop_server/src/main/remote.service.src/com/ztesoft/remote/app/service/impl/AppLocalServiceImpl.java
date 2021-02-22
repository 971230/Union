package com.ztesoft.remote.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rop.core.cache.AppInfoCache;
import com.rop.core.utils.ConvertUtils;
import com.rop.core.utils.TEA;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.remote.app.service.IAppLocalService;
import com.ztesoft.remote.pojo.AppInfo;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-23 14:36
 * To change this template use File | Settings | File Templates.
 */
public class AppLocalServiceImpl  implements IAppLocalService{

    private final String sql="select p.app_id, P.APP_KEY, p.SOURCE_FROM, p.THEME_SOURCE_FROM,  p.app_name,p.app_secret, p.validity_period, p.app_level, p.APP_ADDRESS,'0' acct_code, '0' acct_name,'0' acct_type,'0' owner_id,'0' owner_code,'0' owner_name from PM_APP p where p.app_key =?  and p.app_key = ?";

    private final String sql_list="select p.app_id, P.APP_KEY, p.SOURCE_FROM, p.THEME_SOURCE_FROM,  p.app_name,p.app_secret, p.validity_period, p.app_level, p.APP_ADDRESS,'0' acct_code, '0' acct_name,'0' acct_type,'0' owner_id,'0' owner_code,'0' owner_name  from PM_APP p";
    @Override
    public AppInfo validate(String appKey, String appSecret) {
        AppInfo info=new AppInfo();
        PM_APP data = null;
    	List args = new ArrayList();
        args.add(appKey);
        args.add(appKey);
    	IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        Page page = baseDaoSupport.qryForPage(sql, 1, 10, PM_APP.class, args.toArray());
        List<PM_APP> appList = page.getResult();
        if(null != appList && !appList.isEmpty()){
        	data = appList.get(0);
        }
        if(null==data){
            info.setMsg("appKey不存在!");
            return info;
        }
        String app_secret=String.valueOf(data.getApp_secret());
        if(!TEA.Decrypt(app_secret).equals(app_secret)){
            info.setMsg("appKey或秘钥错误!");
            return info;
        }
        info=ConvertUtils.convert(data);

        return info;
    }


    @Override
    public AppInfo validate(String appKey) {
        AppInfo info= AppInfoCache.instance().get(appKey);
        if(null!=info){
            return info;
        }

        info=new AppInfo();
        PM_APP data=null;
        try{
        	List args = new ArrayList();
            args.add(appKey);
            args.add(appKey);
        	IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
            Page page = baseDaoSupport.qryForPage(sql, 1, 10, PM_APP.class, args.toArray());
            List<PM_APP> appList = page.getResult();
            if(null != appList && !appList.isEmpty()){
            	data = appList.get(0);
            }
        }catch (RuntimeException ex){
            ex.printStackTrace();
        }
        if(null==data){
            info.setMsg("appKey不存在!");
            return info;
        }
        info= ConvertUtils.convert(data);
        AppInfoCache.instance().set(info.getAppKey(),info);

        return info;
    }

    @Override
    public List<AppInfo> queryAllList() {
        List<AppInfo> list=new ArrayList<AppInfo>();
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        Page page = baseDaoSupport.qryForPage(sql_list, 1, 10, PM_APP.class);
        List<PM_APP> value = page.getResult();
        Map map=null;
        for(PM_APP app :value){
             AppInfo info = ConvertUtils.convert(app);
             list.add(info);
        }
        return list;  
    }  
    
    public static class PM_APP {
    	private String app_id;
    	private String acct_id;
    	private String app_name;
    	private String app_key;
    	private String app_secret;
    	private String validity_period;
    	private String app_level;
		private String is_audit;
    	private String status_cd;
    	private String state_date;
    	private String remark;
    	private String create_date;
    	private String app_address;
    	private String applevel_audit_state;
    	private String source_from;
    	private String theme_source_from;
    	private String acct_code; 
    	private String acct_name; 
    	private String acct_type; 
    	private String owner_id;  
    	private String owner_code;
    	private String owner_name;
		public String getApp_id() {
			return app_id;
		}
		public void setApp_id(String app_id) {
			this.app_id = app_id;
		}
		public String getAcct_id() {
			return acct_id;
		}
		public void setAcct_id(String acct_id) {
			this.acct_id = acct_id;
		}
		public String getApp_name() {
			return app_name;
		}
		public void setApp_name(String app_name) {
			this.app_name = app_name;
		}
		public String getApp_key() {
			return app_key;
		}
		public void setApp_key(String app_key) {
			this.app_key = app_key;
		}
		public String getApp_secret() {
			return app_secret;
		}
		public void setApp_secret(String app_secret) {
			this.app_secret = app_secret;
		}
		public String getValidity_period() {
			return validity_period;
		}
		public void setValidity_period(String validity_period) {
			this.validity_period = validity_period;
		}
		public String getApp_level() {
			return app_level;
		}
		public void setApp_level(String app_level) {
			this.app_level = app_level;
		}
		public String getIs_audit() {
			return is_audit;
		}
		public void setIs_audit(String is_audit) {
			this.is_audit = is_audit;
		}
		public String getStatus_cd() {
			return status_cd;
		}
		public void setStatus_cd(String status_cd) {
			this.status_cd = status_cd;
		}
		public String getState_date() {
			return state_date;
		}
		public void setState_date(String state_date) {
			this.state_date = state_date;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getCreate_date() {
			return create_date;
		}
		public void setCreate_date(String create_date) {
			this.create_date = create_date;
		}
		public String getApp_address() {
			return app_address;
		}
		public void setApp_address(String app_address) {
			this.app_address = app_address;
		}
		public String getApplevel_audit_state() {
			return applevel_audit_state;
		}
		public void setApplevel_audit_state(String applevel_audit_state) {
			this.applevel_audit_state = applevel_audit_state;
		}
		public String getSource_from() {
			return source_from;
		}
		public void setSource_from(String source_from) {
			this.source_from = source_from;
		}
		public String getTheme_source_from() {
			return theme_source_from;
		}
		public void setTheme_source_from(String theme_source_from) {
			this.theme_source_from = theme_source_from;
		}
		public String getAcct_code() {
			return acct_code;
		}
		public void setAcct_code(String acct_code) {
			this.acct_code = acct_code;
		}
		public String getAcct_name() {
			return acct_name;
		}
		public void setAcct_name(String acct_name) {
			this.acct_name = acct_name;
		}
		public String getAcct_type() {
			return acct_type;
		}
		public void setAcct_type(String acct_type) {
			this.acct_type = acct_type;
		}
		public String getOwner_id() {
			return owner_id;
		}
		public void setOwner_id(String owner_id) {
			this.owner_id = owner_id;
		}
		public String getOwner_code() {
			return owner_code;
		}
		public void setOwner_code(String owner_code) {
			this.owner_code = owner_code;
		}
		public String getOwner_name() {
			return owner_name;
		}
		public void setOwner_name(String owner_name) {
			this.owner_name = owner_name;
		}
    	
    }
    
}