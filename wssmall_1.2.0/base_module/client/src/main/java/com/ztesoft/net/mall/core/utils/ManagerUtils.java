package com.ztesoft.net.mall.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import params.ZteRequest;
import utils.CoreThreadLocalHolder;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.util.RequestUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.remote.app.service.IAppLocalService;
import com.ztesoft.remote.pojo.AppInfo;

import consts.ConstsCore;
//import com.powerise.ibss.framework.FrameException;

public class ManagerUtils {
	
	public static String getCurrentLvId(){
		String lv_id = "0";
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		if(member!=null){
			lv_id = member.getCacheLv_id();
		}
		return lv_id;
	}
	
	
	
	/**
	 * 获取登录用户
	 * @return
	 */
	public static AdminUser getAdminUser(){
		 WebSessionContext<AdminUser> sessonContext = ThreadContextHolder.getSessionContext();
		 AdminUser adminUser = sessonContext.getAttribute("admin_user_key");
		 
		 if(adminUser == null)
			 adminUser = (AdminUser)ThreadContextHolder.getHttpSessionContext().getAttribute("admin_user_key");
		 return adminUser;
	}
	public static String getPassword(){
		HttpSession session = ServletActionContext.getRequest().getSession();	
		String password = (String) session.getAttribute("loginPassword");
		return password;
	}
	
	public static int getFounder(){
		return getAdminUser().getFounder();
	}
	//得到用户id
	public static String getUserId(){
		return getAdminUser().getUserid();
	}
	
	//得到用户区域lan_id
	public static String getLanId() {
		try{
	        AdminUser adminUser = getAdminUser();
	        if(adminUser==null){
	            return "";
	        }
			return adminUser.getLan_id();
		}catch(Exception ex){
			return "";
		}
	}
	
	public static boolean isAdminUser(){
		AdminUser adminUser = getAdminUser();
		if(adminUser==null)
			return false;
		int founder = adminUser.getFounder();
		if(founder == 0 || founder == 1)//0:电信员工 1：超级管理员
			return true;
		return false;
	}

	

	//当内容为【2013-08-23测试】返回true
	public  static  boolean  timeMatch(String date_str){
		String pattern = "^\\d{4}\\-\\d{2}\\-\\d{2}.*";
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(date_str);//"2012-12-20 12:30"
		if (m.find()) {
			return true;
		}		
		return false;
	}
	
	
	/**
	 * 创建session add by wui 废弃
	 * @param userId
	 */
//	public static void createSession(AdminUser user ){
//		WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
//		sessonContext.setAttribute("admin_user_key", user);
//		
//	}
	
	public static Integer getIntegerVal(Double d_value){
		
		String s_value = d_value+"";
		if(StringUtils.isEmpty(s_value))
			 s_value ="0";
		if(!StringUtils.isEmpty(s_value) && s_value.indexOf(".")>-1)
			s_value = s_value.substring(0, s_value.indexOf("."));
		Integer price_int = new Integer(s_value).intValue();
		return price_int;
		
	}
	
	// 根据时间格式获取数据库格式字符串(未检查时间是否有效)
	public static String getSqlFormatStr(String date_str) {
		Map<String,String>  formatMap = new LinkedHashMap<String,String>();
		formatMap.put("^\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}:\\d{2}:\\d{2}", "YYYY-MM-DD HH24:MI:SS");
		formatMap.put("^\\d{4}\\-\\d{2}\\-\\d{2}", "YYYY-MM-DD");
		for(Map.Entry<String, String> entry : formatMap.entrySet()){
			Pattern p = Pattern.compile(entry.getKey(), Pattern.CANON_EQ);
			Matcher m = p.matcher(date_str);
			if (m.find()) {
				return entry.getValue();
			}
		}
		return "";
	}
	

	//根据随机数长度生成随机数
	public static String genRandowNum(int pwdLen){

		if (pwdLen <= 0) {
			return "";
		}
		int  maxNumber = 1;
		int minNumber = 0;
		int randomValue = 0;

		//生成随机数能取的最大值+1, 如1000000
		for(int i=0; i< pwdLen; i++){
			maxNumber = maxNumber*10;
    	}

		//生成随机数能取的最小数-1: 如 99999;
		if(pwdLen > 1){
    		minNumber = 1;
    		for(int i=0; i<pwdLen-1; i++){
    			minNumber = minNumber*10;
    		}
    		minNumber = minNumber - 1;
		}

		while(true){
    		randomValue = (int)Math.ceil(Math.random()*maxNumber);
    		if((randomValue > minNumber) && (randomValue < maxNumber)){
    			return randomValue +"" ;
    		}

		}

	}
	
	
	
	
	public static String  [] listToStrArr(List lists){
		String str [] = new String [lists.size()];
		for (int i = 0; i < lists.size(); i++) {
			str[i] = (String)lists.get(i);
		}
		return str;
	}
	//
	public static String toJsonString(Map result){
		StringBuffer jsonBuffer = new StringBuffer();
		Iterator it = result.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			String value = (String)result.get(key);
			jsonBuffer.append(key).append(":").append("'").append(value).append("',");
		}
		return jsonBuffer.substring(0, jsonBuffer.length()-1).toString();
		
	}
	
	//{Header={Response={Message=订单null尚未处理完，请稍后再试!, Code=7003},
	public static Map getResponseHeader(Map resultMap){
		Map headerMap = (Map)resultMap.get("Header");
		return headerMap;
	}
	
	
	public static String getGoodsId(){
		HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
		String url = RequestUtil.getRequestUrl(httpRequest);
		String goods_id = paseGoodsId(url);
		
		return goods_id;
	}

	public  static String  paseGoodsId(String url){
		String pattern = "/(.*)wssdetails-(\\d+)(.*)";
		String value = null;
		Pattern p = Pattern.compile(pattern, 2 | Pattern.DOTALL);
		Matcher m = p.matcher(url);
		if (m.find()) {
			value = m.replaceAll("$2");
		}
		return value;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map getResponse(Map resultMap){
		Map response = getResponseHeader(resultMap);
		return response;
	}
	
	
	@SuppressWarnings("unchecked")
	public static String getResponseMessage(Map resultMap){
		Map response = getResponse(resultMap);
		Map response_p = (Map)response.get("Response");
		return (String)response_p.get("Message");
	}
	
	
	public static String getResponseCode(Map resultMap){
		Map response = getResponse(resultMap);
		Map response_p = (Map)response.get("Response");
		return (String)response_p.get("Code");
	}
	
	
	/**
	 * 获取缺省角色sql
	 */
	public static  String getDefRoleSql() {
		//add by wui务删
		String pkey ="";
		if(ManagerUtils.isNetStaff())
		{
			pkey =Consts.CURR_FOUNDER_3; //缺省一级分销商角色展示
			if(Consts.LAN_PROV.equals(ManagerUtils.getAdminUser().getLan_id())){
				pkey ="0,4,3";
			}else if(StringUtils.isNotEmpty(ManagerUtils.getAdminUser().getLan_id()) && !Consts.LAN_PROV.equals(ManagerUtils.getAdminUser().getLan_id())){
				pkey ="3,4";
			}
		}else if(ManagerUtils.isFirstPartner())
		{
			pkey =Consts.CURR_FOUNDER_2;//缺省二级分销商角色展示
		}
		if(StringUtils.isEmpty(pkey))
			return "";
		return " or  a.roleid in ( select codea from  es_dc_public  where stype='9999' and pkey in( "+pkey+"))";
	}
    public static  String getTypeNameByFounder(){
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");;
    	List list =  new ArrayList();
    	list = cacheUtil.doDcSqlQuerry(ConstsCore.DC_DC_USER_TYPE);
    	String founder = ManagerUtils.getFounder()+"";
    	String type_name = "";
    	if(list!=null){
    		for(int i=0;i<list.size();i++){
    			Map map = new HashMap();
    			map = (Map) list.get(i);
    			String value = (String)map.get("value");
    			if(founder.equals(value)){
    				type_name = (String)map.get("value_desc");
    			}
    		}
    	}
    	if("1".equals(founder)){
			type_name = "超级管理员";
		}
    	if(StringUtil.isEmpty(type_name)){
    		type_name = getcurrType();
    	}
    	return type_name;
    	
    }
	public static  String getcurrType() {//得到当前登陆工号的类型
		
		String type ="";
		AdminUser adminUser = ManagerUtils.getAdminUser();
		int founder = adminUser.getFounder();
		String lan_id = adminUser.getLan_id();
		
		if(Consts.CURR_FOUNDER0==founder){
				type= "联通员工";
			
		}else if(Consts.CURR_FOUNDER1==founder){
			type = "超级管理员";
		}else if(Consts.CURR_FOUNDER2==founder){
			type = "普通管理员";
		}else if(Consts.CURR_FOUNDER3==founder){
			type = "浙江管理员";
		}else if(Consts.CURR_FOUNDER4==founder){
			if(adminUser.getReltype().equals(Consts.SUPPLIER_RELTYPE_DEALER) || adminUser.getReltype().equals("DEALER")){
				type = "经销商";
			}else if(adminUser.getReltype().equals(Consts.REL_TYEP_SUPPLIER) || adminUser.getReltype().equals("SUPPLIER")){
				type = "供货商";
			}
			
		}else if(Consts.CURR_FOUNDER5==founder){
			type = "供货商员工";
		}
		return type;
	}
	public static String getType(){
		String type ="";
		AdminUser adminUser = ManagerUtils.getAdminUser();
		int founder = adminUser.getFounder();
		if(Consts.CURR_FOUNDER0==founder){
			type= "移动员工";
		 }else if(Consts.CURR_FOUNDER1==founder){
	      	type = "超级管理员";
	     }else if(Consts.CURR_FOUNDER3==founder){
		    type = "分销商";
	     }
		  return type;
	}
	public static String getLanName(){
		
		String lan_name="";
		String lan_id = ManagerUtils.getAdminUser().getLan_id();
		if(0==getFounder() || 3==getFounder()){
		if(Consts.LAN_PROV.equals(lan_id)){
			lan_name="本地网：全省";
		}else{
			 WebSessionContext<String> sessonContext = ThreadContextHolder.getSessionContext();	
			 String lanName = sessonContext.getAttribute("lan_name");
			 if(lanName!=null&&!"".equals(lanName)){
			  lan_name = "本地网:"+lanName+"";
			 }
		}
		}
		return lan_name;
	}
	
	public static  String getStrValue(Map m , String name) {
		
		if (null == m || m.isEmpty()) return "";
		
		Object t = m.get(name) ;
		if(t == null )
			return "" ;
		return (m.get(name)+"").trim() ;
	}
	
	/**
	 * 过滤无用字段
	 * @param dict
	 * @param fields 普通字段
	 * @param convertFiled,单位为分的金额字段，转为元
	 */
	@SuppressWarnings("unchecked")
	public static List filterDict(List list,String fields,String convertFiled) {
		List newList = new ArrayList();
		try {
			String[] field = fields.split(",");
			String[] convert = convertFiled.split(",");
			if((null != field && field.length > 0 && !"".equals(fields))|| (convert != null && convert.length > 0 && !"".equals(convertFiled))){

				for(int i = 0; i < list.size(); i++){
					Map oldMap = (HashMap)list.get(i);
					Map newMap = new HashMap();
					for(int j = 0; j < field.length; j++){
						newMap.put(field[j], getStrValue(oldMap,field[j]));
					}
					for(int k = 0; k < convert.length; k++){
						String temp = getStrValue(oldMap,convert[k]);
						int amount = 0;
						if(null != temp && !temp.equals("")){
							amount = Integer.parseInt(temp)/100;
						}
						newMap.put(convert[k], amount + "");
					}
					newList.add(newMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(null == newList || newList.isEmpty())
			return list;
		
		return newList;
	}
	
	
	/**
	 * 过滤无用字段
	 * @param dict
	 * @param fields 普通字段
	 * @param convertFiled,单位为分的金额字段，转为元
	 * @throws FrameException 
	 */
	public static List filterList(List dataList,String fields,String convertFiled) {
		
		List newLists = filterDict(dataList, fields, convertFiled);
		return newLists;
	}
	
	/**
	 * 创建用户session
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-27 
	 * @param member
	 */
	public static void createMemberSession(Member member ){
		WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
		sessonContext.setAttribute(IUserService.CURRENT_MEMBER_KEY, member);
	}
	
	/**
	 * 获取用户session
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-27 
	 * @param member
	 * @return
	 */
	public static Member getMemberSession(){
		WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
		return (Member) sessonContext.getAttribute(IUserService.CURRENT_MEMBER_KEY);
	}
	
	
	//一级
	public static boolean isFirstPartner(){
		return Consts.CURR_FOUNDER_3.equals(getAdminUser().getFounder()+"");
	}
	
	//二级
	public static boolean isSecondPartner(){
		return Consts.CURR_FOUNDER_2.equals(getAdminUser().getFounder()+"");
	}
	
	//超级管理 或 电信管理员
	public static boolean isNetStaff(){
		return Consts.CURR_FOUNDER_0.equals(getAdminUser().getFounder()+"") ||  Consts.CURR_FOUNDER_1.equals(getAdminUser().getFounder()+"");
	}
	
	/**
	 * 供货商
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public  static boolean isSupplier(){
		return Consts.SUPPLIER_FOUNDER==getAdminUser().getFounder();
	}
	
	public  static boolean isProvStaff(){  // && 优先级比 || 高
		return ( Consts.CURR_FOUNDER_0.equals(getAdminUser().getFounder()+"") 
				|| Consts.CURR_FOUNDER_1.equals(getAdminUser().getFounder()+"") )
		&& Consts.LAN_PROV.equals(getAdminUser().getLan_id());
	}
	
	public  static boolean isLanStaff(){
		return ( Consts.CURR_FOUNDER_0.equals(getAdminUser().getFounder()+"") 
					||  Consts.CURR_FOUNDER_1.equals(getAdminUser().getFounder()+"") )
		&& !StringUtils.isEmpty(getAdminUser().getLan_id())
		&& !Consts.LAN_PROV.equals(getAdminUser().getLan_id()) ;
	}
	
	//获取父级分销商id
	public  static String getParentAdminUserId(){
		if(isFirstPartner()){
			return getAdminUser().getUserid();
		}else if(isSecondPartner()){
			return getAdminUser().getParuserid();
		}
		return "";
	}
	
	@SuppressWarnings("rawtypes")
	public static AppInfo getCurAppInfo(){
		WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
		AppInfo app = (AppInfo)sessonContext.getAttribute("current_app_info");
		return app;
	}
	
	public static String apSFromCond(String tableName){
		//开关控制处理
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String P_ASOURCE_FROM =cacheUtil.getConfigInfo("P_ASOURCE_FROM");
		if(StringUtil.isEmpty(P_ASOURCE_FROM))
			return "";
		if(!StringUtil.isEmpty(tableName))
			return "and "+tableName+".source_from ='"+ManagerUtils.getSourceFrom()+"'";
		else
			return "and source_from ='"+ManagerUtils.getSourceFrom()+"'";
	}
	public static String CACHE_REFRESH_SOURCE_FROM ="";
	public static String getSourceFrom(){
		
		if(!StringUtil.isEmpty(CACHE_REFRESH_SOURCE_FROM))
			return CACHE_REFRESH_SOURCE_FROM;
		//add by wui
		String source_from = "";
		//能力开放平台rop、dubbo方式调用,优先级最高
		ZteRequest zteRequest =CoreThreadLocalHolder.getInstance().getZteCommonData().getZteRequest(); //说明：dubboFilter拦截器中会设置进去
		if(zteRequest !=null){
			source_from = zteRequest.getSourceFrom();
			if("ECSORD".equals(source_from))//add by wui临时解决
				source_from = "ECS";
			if(!StringUtil.isEmpty(source_from)){
				return source_from;
			}
		}
		//取栈中的最后一个appkey值
		String appKey = CoreThreadLocalHolder.getInstance().getZteCommonData().getAppKey();
		if(!StringUtil.isEmpty(appKey)){
			AppInfo info = getSourceFromByAppKey(appKey); 
			if(info !=null){
				source_from = info.getSourceFrom();
				if("ECSORD".equals(source_from))//add by wui临时解决
					source_from = "ECS";
				if(!StringUtil.isEmpty(source_from))
					return source_from;
			}
		}
		//管理平台登录，获取登录的平台来源
		AppInfo app = getCurAppInfo();
		if(null != app){
			source_from = app.getSourceFrom();
			if("ECSORD".equals(source_from)) //add by wui临时解决
				source_from = "ECS";
			return source_from;
		}
		if(StringUtil.isEmpty(source_from)){
			source_from = EopSetting.DB_SOURCE_FROM;
			return source_from;
		}
		return source_from;
	}
	
	
	   private static AppInfo getSourceFromByAppKey(String  appKey){
	       try{
	           AppInfo info=null;
	           if(StringUtils.isNotBlank(appKey)){
	        	   IAppLocalService appLocalService = (IAppLocalService)SpringContextHolder.getBean("appLocalService");
	               info=appLocalService.validate(appKey);
	           }
	           return info;
	       }catch (Exception e){
	           e.printStackTrace();
	       }
	        return null;
	    }
	   
	/**
	 * 清空插件桩缓存
	 */
	public static void resetWidgetCache(){ 
//		EopSite site = EopContext.getContext().getCurrentSite();
//		String site_key = "widget_"+ site.getUserid() +"_"+site.getId();
//		EhCacheImpl cache = (EhCacheImpl) CacheFactory.getCache(CacheFactory.WIDGET_CACHE_NAME_KEY);
//		//由缓存中找此站点的挂件级存集合,缓存处理 add by wui
//		Map<String,String> htmlCache=  (Map<String, String>) cache.get(site_key);
//		if(htmlCache !=null)
//			cache.put(site_key, null);
	}
	
	
//	  public static  boolean isAdmin(){
//	        boolean rval=false;
//	        String sign=  CTConfig.getInstance().getValue("SUPER_STAFF_CODE");  ////超级管理员
//	        if(StringUtils.isNotEmpty(sign)){
//	            String items[]=sign.split("\\,");
//	            HttpSession session = ContextHelper.getSession();
//	            if(null!=session){
//	                Object obj=session.getAttribute(KEY.CURRENT_USER);
//	                if(null!=obj){
//	                    TsmStaff staff=(TsmStaff)obj;
//	                    String item=null;
//	                    for(int i=0;i<items.length;i++){
//	                        item=items[i];
//	                        if(StringUtils.isEmpty(item))continue;
//	                        if(staff.getStaff_no().equals(item)){
//	                            rval=true;
//	                            break;
//	                        }
//	                    }
//	                }
//	            }
//	        }
//	        return rval;
//	    }
	
	public static void createSession(AdminUser importUser){
		 WebSessionContext<AdminUser> sessonContext = ThreadContextHolder.getSessionContext();
		 if(importUser !=null)
			 sessonContext.setAttribute("admin_user_key",importUser);
		 else
			 sessonContext.setAttribute("admin_user_key",new AdminUser());
	}
}
