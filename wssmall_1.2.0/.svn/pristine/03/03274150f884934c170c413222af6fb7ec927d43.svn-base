package com.powerise.ibss.system;

import java.io.Serializable;
import java.util.HashMap;

public class SessionOBJ implements Serializable{
   /*为了以后对存放在Session回话中的信息进行扩展，用HashMap来存放Staff的属性*/
   HashMap staff;
   
   /* staff 包含的属性(Key名)如下：
      bureau_id;     //地域ID
      site_id;       //操作点ID
      staff_id;      //人员ID
      bureau_name;   //地域名称
      site_name;     //操作点名称
      staff_name;    //人员名字
      role;          //角色以HashTable形式存放，key -角色ID，value -角色名称
      auth_level;    //员工级别
      title_id;      //职务ID
      title_name;    //职务名称
      password;      //员工密码
      region_no;     //社区ID
      region_name;   //社区名称
      logon_time     //登陆时间
      login_ip       //登录人IP
   */
   public SessionOBJ(HashMap p_staff){
      staff = p_staff;
   }
   
   /**得到员工职务ID*/
   public String getTitleId(){
      String title_id =null;
      if(staff !=null && staff.get("title_id")!=null)
         title_id =staff.get("title_id").toString();
      return title_id;  
   }
   /**得到员工职务名称*/
   public String geTitleName(){
      String title_name =null;
      if(staff !=null && staff.get("title_name")!=null)
         title_name =staff.get("title_name").toString();
      return title_name;  
   }
   /**得到员工密码*/
   public String getPassword(){
      String password =null;
      if(staff !=null && staff.get("password")!=null)
         password =staff.get("password").toString();
      return password;  
   }
   /**得到员工社区ID*/
   public String getRegionNo(){
      String region_no =null;
      if(staff !=null && staff.get("region_no")!=null)
         region_no =staff.get("region_no").toString();
      return region_no;  
   }
   /**得到员工社区名称*/
   public String getRegionName(){
      String region_name =null;
      if(staff !=null && staff.get("region_name")!=null)
         region_name =staff.get("region_name").toString();
      return region_name;  
   }
   /**得到员工角色*/
   public HashMap getRole(){
      HashMap role =null;
      if(staff !=null && staff.get("role")!=null)
         role =(HashMap)staff.get("role");
      return role;  
   }
   /**得到员工级别*/
   public String getAuthLevel(){
      String auth_level =null;
      if(staff !=null && staff.get("auth_level")!=null)
         auth_level =staff.get("auth_level").toString();
      return auth_level;  
   }
   /**得到员工区域ID*/
   public String getBureauNo(){
      String bureau_no =null;
      if(staff !=null && staff.get("bureau_no")!=null)
         bureau_no =staff.get("bureau_no").toString();
      return bureau_no;  
   }
   /**得到员工操作点ID*/
   public String getSiteNo(){
      String site_no =null;
      if(staff !=null && staff.get("site_no")!=null)
         site_no =staff.get("site_no").toString();
      return site_no;  
   }
   /**得到员工操作点CODE*/
   public String getSiteCode(){
      String site_code =null;
      if(staff !=null && staff.get("site_code")!=null)
         site_code =staff.get("site_code").toString();
      return site_code;
   }
   /**得到员工操作点名称*/
   public String getSiteName(){
      String site_name =null;
      if(staff !=null && staff.get("site_name")!=null)
         site_name =staff.get("site_name").toString();
      return site_name;  
   }
   /**得到人员ID*/
   public String getStaffNo(){
      String staff_no =null;
      if(staff !=null && staff.get("staff_no")!=null)
         staff_no =staff.get("staff_no").toString();
      return staff_no;  
   }
   /**得到人员CODE*/
   public String getStaffCode(){
      String staff_code =null;
      if(staff !=null && staff.get("staff_code")!=null)
         staff_code =staff.get("staff_code").toString();
      return staff_code;  
   }   
   /**得到人员姓名*/
   public String getStaffName(){
      String staff_name =null;
      if(staff !=null && staff.get("staff_name")!=null)
         staff_name =staff.get("staff_name").toString();
      return staff_name;  
   }
   /**得到员工地域名称*/
   public String getBureauName(){
      String bureau_name =null;
      if(staff !=null && staff.get("bureau_name")!=null)
         bureau_name =staff.get("bureau_name").toString();
      return bureau_name;  
   }
    /**得到城市编码*/
   public String getCityNo(){
      String city_no =null;
      if(staff !=null && staff.get("city_no")!=null)
         city_no =staff.get("city_no").toString();
      return city_no;  
   }
    /**得到区号*/
   public String getAreaCode(){
      String area_code =null;
      if(staff !=null && staff.get("area_code")!=null)
         area_code =staff.get("area_code").toString();
      return area_code;  
   }
   /**通过名称取值*/
   public Object get(String p_name){
      Object value =null;
      if(staff !=null && staff.get(p_name)!=null)
         value =staff.get(p_name);
      return value;
   }
   
   public String getLoginTime(){
	      String logon_time =null;
	      if(staff !=null && staff.get("logon_time")!=null)
	         logon_time =staff.get("logon_time").toString();
	      return logon_time;  
	   }
	   
   public String getAppver(){
	      String app_ver =null;
	      if(staff !=null && staff.get("APP_VER")!=null)
	    	  app_ver =staff.get("APP_VER").toString();
	      return app_ver;  
	   }
	   
   public HashMap getStaffInfo(){
      return staff;
   }
   public String getLoginIp()
   {
     String ip=null;
     if( staff!=null && staff.get("login_ip")!= null )
      ip=staff.get("login_ip").toString();
      return ip;
  }
}
