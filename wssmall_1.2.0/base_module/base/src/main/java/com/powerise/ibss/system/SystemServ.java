/**
   系统管理组件，实现以下方法：
   1.MSM_009 记录操作日志
   2.MSM_011 保存操作人员信息
   3.MSM_012 保存操作点信息
   4.MSM_016 删除操作点
   5.MSM_017 删除操作人
   6.MSM_019 批量修改操作人属性
   6.MSM_040 增加区域
   7.MSM_041 增加、修改一级地域
   8.MSM_042 增加、修改地市
   9.MSM_044 区域修改
   10.MSM_045根据一级区域编码查询一级区域信息
   11.MSM_046根据一级区域编码查询地市信息
   12.MSM_052删除一级区域信息
   13.MSM_053删除地市区域信息
   14.MSM_054删除区域信息
   15.MSM_101修改、添加或者删除静态参数表信息
   16.MSM_102修改、添加或者删除静态参数表信息
   17.MSM_103修改、添加或者删除静态参数表信息(tsm_paravalue)
   18.MSM_241根据表名获取表中的数据
   19.MSM_242 修改、添加或者删除静态表数据信息
   20.MSM_243将消息状态置为零
   21.MSM_203向消息表中写消息（指定操作点下的操作员）
   22.MSM_204向消息表中写消息(指定操作员）
   23.MSM_175根据定单查询其关注人
   24.MSM_207登录员工拥有的操作点
   25.MSM_208登录员工拥有的操作员
   26.MSM_216发送消息
   27.MSM_244删除消息
   28.MSM_217员工拥有的组织机构
   29.MSM_218修改密码
   30.MSM_013查询日志
   31.MSM_219员工拥有的组织机构(包括操作员）
   32.MSM_220 取得员工拥有的组织机构详细列表(包括操作员） 


   修改记录：
   6.2004-06-15 shawn MSM_013,修改取日志方法
   5.2004-06-15 shawn MSM_217,修改BUREAU_NAME的显示方式
   5.2003-12-12 shawn MSM_241
   4.2003-10-10 shawn MSM_218,MSM_011将明文密码更新到ldap
   3.2003-9-19 shawn MSM_218 验证密码时，以明文和密文同时验证
   2.2003-9-7 shawn MSM_011 调用MCH_501;MSM_017调用MCH_502;MSM_012调用MCH_503,MCH_504,MCH_505
   1.2003-9-5 shawn MSM_011 将密码明文传到LDAP

*/
package com.powerise.ibss.system;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.util.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;


public class SystemServ implements IAction {
   private static Logger m_Logger = null;
   private String 		sql			= null;
   private Connection 	__cn__		= null;
   private Statement 	__st__		= null;
   private ResultSet 	__rs__ 		= null;
   private static boolean DEBUG		= false;		//调试状态开关
   private static boolean bMCHinterface = false;	//接口是否应用开关
                  String strtest="";

	@Override
	public int perform(DynamicDict aDict) throws FrameException {
		String service_name = aDict.getServiceName();
		getLogger(); 
		Utility.println("调用类SystemServ.........." + service_name);
		try {
			__cn__ 	= aDict.GetConnection();
			__st__ 	= __cn__.createStatement();
			if (service_name.equalsIgnoreCase("MSM_009")) {
				//添加日志信息
				String sstrLogClassID = ((String) aDict.getValueByName("LogClassID", false)).trim();
				String sstrStaff_NO = ((String) aDict.getValueByName("Staff_NO", false)).trim();
				String sstrBureau_NO = ((String) aDict.getValueByName("Bureau_NO", false)).trim();
				String sstrSite_NO = ((String) aDict.getValueByName("Site_NO", false)).trim();
				String sstrContent = ((String) aDict.getValueByName("Content", false)).trim();
				__cn__ = aDict.GetConnection();
				writeStaffLog(sstrBureau_NO,sstrSite_NO,sstrStaff_NO,sstrLogClassID,sstrContent,__cn__);
			} else if (service_name.equalsIgnoreCase("MSM_011")) {
				//保存操作人员信息
				MSM_011(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_012")) {
				//保存操作点信息
				MSM_012(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_013")) {
			    getLog(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_016")) {
				MSM_016(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_017")) {
				MSM_017(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_019")) {
				MSM_019(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_040")) {
				MSM_040(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_041")) {
				MSM_041(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_042")) {
				MSM_042(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_044")) {
				MSM_044(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_045")) {
				MSM_045(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_046")) {
				MSM_046(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_052")) {
				MSM_052(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_053")) {
				MSM_053(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_054")) {
				MSM_054(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_101")) {
				MSM_101(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_102")) {
				MSM_102(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_103")) {
				MSM_103(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_241")) {
				MSM_241(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_242")) {
				MSM_242(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_243")) {
				MSM_243(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_203")) {
				MSM_203(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_204")) {
				MSM_204(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_175")) {
				String ord_no = (String) aDict.getValueByName("Ord_No", false);
				__cn__ = aDict.GetConnection();
				ArrayList arraylist = MSM_175(ord_no, __cn__);
				m_Logger.debug("arraylist-msm-175-->>" + arraylist);
				if (arraylist != null && arraylist.size() > 0)
				   aDict.setValueByName("order_staff", arraylist);
			} else if (service_name.equalsIgnoreCase("MSM_207")) {
				String staff_no = (String) aDict.getValueByName("STAFF-NO", false);
				String site_no = (String) aDict.getValueByName("SITE-NO", false);
				String bureau_no = (String) aDict.getValueByName("BUREAU-NO", false);
				__cn__ = aDict.GetConnection();
				ArrayList arraylist1 = MSM_207(staff_no, site_no, bureau_no, __cn__);
				if (arraylist1 != null && arraylist1.size() > 0)
				   aDict.setValueByName("site", arraylist1);
				} else if (service_name.equalsIgnoreCase("MSM_208")) {
				String staff_no = (String) aDict.getValueByName("STAFF-NO", false);
				String site_no = (String) aDict.getValueByName("SITE-NO", false);
				String bureau_no = (String) aDict.getValueByName("BUREAU-NO", false);
				
				__cn__ = aDict.GetConnection();
				ArrayList arraylist2 = MSM_208(staff_no, site_no, bureau_no, __cn__);
				if (arraylist2 != null && arraylist2.size() > 0)
				   aDict.setValueByName("staff", arraylist2);
			} else if (service_name.equalsIgnoreCase("MSM_244")) {
				MSM_244(aDict);
			} else if (service_name.equalsIgnoreCase("MSM_217")) {
				//员工拥有的组织机构
				MSM_217(aDict);
         	}else if (service_name.equalsIgnoreCase("MSM_218")) {
	            String old_staff_no = "";
	            long sstaff_no = 0;
	            sstaff_no = MSM_218(aDict);
	            old_staff_no = String.valueOf(sstaff_no);
	            aDict.setValueByName("old_staff_no", old_staff_no);
         	}else if (service_name.equalsIgnoreCase("MSM_219")) {
	            String staff_no 	= (String)aDict.getValueByName("staff_no", false);
	            String site_no 		= (String)aDict.getValueByName("site_no", false);
	            String bureau_no 	= (String)aDict.getValueByName("bureau_no", false);
	            String allflag 		= (String)aDict.getValueByName("allflag", false);
	            ArrayList _result 	= MSM_219(allflag, staff_no, site_no, bureau_no, __cn__);
	            if (_result != null && _result.size() > 0) aDict.setValueByName("orgdate", _result);
         	}else if (service_name.equalsIgnoreCase("MSM_220")) {
         		MSM_220(aDict);
			}
		}catch(SQLException e) {
			throw new FrameException(-22120109, "SystemServ.perform()出现SQL异常！", e.getMessage());
      	}finally{
			try {
				if (__rs__ != null) __rs__.close();         	
				if (__st__ != null) __st__.close();
			}catch (Exception e){
				throw new FrameException(-22120109, "释放资源时出现异常！",e.getMessage());
			}
		}
		return 0;
	}
    private void getLogger(){
		if(m_Logger==null)
			m_Logger = Logger.getLogger(getClass().getName()); 
	}

 /************************************************************************************

    功能:       日志查询
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:  MSM_013    getLog
    功能说明:  日志查询
    输入参数:
    格式：   　　　　
    变量名              类型      长度     名称
    site_no           string   6     操作点
    log_class_id      string         日志类型
    type              string   1 标志  0：部门1：操作点
    content           string   250     日志内容
    bein_time         string   20   开始时间
    end_time          string   20   结束时间

    输出参数:
    变量名              类型      长度     名称
    arraylist(log_list)
    返回结果：


    作者： 盛正春
    编写日期：   2003-08-02
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public void getLog(DynamicDict aDict) throws SQLException, FrameException{
	  getLogger();
      StringBuffer where =new StringBuffer("1=1 ");
      String id =((String)aDict.getValueByName("id", false)).trim();
      String log_class_id =((String)aDict.getValueByName("log_class_id", false)).trim();
      String type =((String)aDict.getValueByName("type", false)).trim();

      String content =((String)aDict.getValueByName("content", false)).trim();
      String begin_time =((String)aDict.getValueByName("begin_time", false)).trim();
      String end_time =((String)aDict.getValueByName("end_time", false)).trim();

      if(! id.equals("")){
          if (type.equals("1"))
           where.append(" and a.site_no='"+id+"'");
      }
      if(! log_class_id.equals("")){
         where.append(" and a.log_class_id='"+log_class_id+"'");
      }
      if (!content.equals("")){
         where.append(" and decode(a.log_class_id,20,(select fun_name from tsm_fun where fun_id=a.content),a.content) like '%"+content+"%'");
      }
      if ((!begin_time.equals(""))&&(!end_time.equals(""))){
         where.append(" and LOGTIME>=to_date('"+begin_time+"','YYYY-MM-DD HH24:MI:SS') "+
            "  AND LOGTIME<=to_date('"+end_time+"','YYYY-MM-DD HH24:MI:SS')");

      }
      else if ((!begin_time.equals(""))&&(end_time.equals(""))){
         where.append(" and logtime>=to_date('"+begin_time+"','YYYY-MM-DD HH24:MI:SS') ");
      }
      else if ((begin_time.equals(""))&&(!end_time.equals(""))){
         where .append(" and logtime<=to_date('"+begin_time+"','YYYY-MM-DD HH24:MI:SS')");
      }
      if(type.equals("3")){
        String a =(String)aDict.getValueByName("id", false);
        if(a.length() >1){
         where.append(" and a.staff_no='"+a.substring(1)+"'");
        }
      }
      String sql ="select 1 from dual";
      //操作点或操作员
      if (type.equals("1") || type.equals("3")){
        sql ="select a.log_id,b.bureau_no,c.site_name,b.staff_name,c.site_name,d.sm_disp_view,"
		    +"decode(a.log_class_id,20,(select fun_name from tsm_fun where fun_id=content),a.content) content,"
			+"to_char(a.logtime,'yyyy-mm-dd hh24:mi:ss') logtime "
            +" from tsm_log a,tsm_staff b ,tsm_site c,tsm_paravalue d"
            +" where a.staff_no=b.staff_no "
            +" and d.sm_field_ename='LOG_CLASS_ID'"
            +" and d.sm_table_ename='TSM_LOG'"
            +" and a.log_class_id=d.sm_used_view"
            +" and b.site_no=c.site_no "
            +" and "+where.toString();
      }else if(type.equals("0")){  //上级操作点
         sql="select a.log_id,b.bureau_no,c.site_name,b.staff_name,d.sm_disp_view,"
		  +" decode(a.log_class_id,20,(select fun_name from tsm_fun where fun_id=content),a.content) content, "
          +" to_char(a.logtime,'yyyy-mm-dd hh24:mi:ss') logtime "
          +"from tsm_log a,tsm_staff b,tsm_site c,tsm_paravalue d "
          +" where a.staff_no=b.staff_no"
          +" and b.site_no=c.site_no"
          +" and d.sm_field_ename='LOG_CLASS_ID'"
          +" and d.sm_table_ename='TSM_LOG'"
          +" and a.log_class_id=d.sm_used_view"
          +" and b.site_no in "+
          " (select site_no from tsm_site where state='1' start with up_site_no='"+id+"'"+
          " connect by  up_site_no= prior site_no) and "
          +where.toString();
      }
      m_Logger.debug(type+"   "+sql+type);
      __rs__ =Dao.select(sql, aDict.GetConnection());
      ResultSetMetaData rsmd =__rs__.getMetaData();
      ArrayList fields =new ArrayList();
      for(int i=1; i<=rsmd.getColumnCount(); i++){
         fields.add(rsmd.getColumnName(i));
      }
      int page =1, pagesize =15;
      String p =(String)aDict.getValueByName("page", false);
      if(!p.equals(""))
         page =Integer.parseInt(p);
      p =(String)aDict.getValueByName("pagesize", false);
      if(!p.equals(""))
         pagesize =Integer.parseInt(p);
      int startRows =(page-1)*pagesize;
      HashMap tmp;
      int row =0;
      ArrayList data =new ArrayList();
      while(__rs__.next()){
         row ++;
         if(row <=startRows)
            continue;
         else if(row <=(startRows +pagesize)){
            tmp =new HashMap();
            for(int i=0; i<fields.size(); i++)
               tmp.put(fields.get(i), __rs__.getString((String)fields.get(i)));
            data.add(tmp);
         }
      }
      if(__rs__ !=null){
         __rs__.getStatement().close();
         __rs__.close();
      }
      aDict.setValueByName("log_list", data);
      aDict.setValueByName("rows", String.valueOf(row));
    }

   /******************************************************************************************
    MSM_009    添加日志信息
    组件类型:  中间层     所属Server名称: SSM
    模块名称:
    功能说明:  添加一条日志。

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名         类型     长度   名称
    LogClassID    string         6     类别ID   NOT   NULL,
    Staff_NO      string         6     用户ID
    Bureau_NO     string         14    区域ID
    Site_NO       string         6     操作点ID
    Content       string         250      内容
    输出参数类型：FML
    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0:              成功；
    -22120008:   新增操作日志失败;
    -22120009:      新增操作日志时出现异常;


    作者：  盛正春
    编写日期：   2003-07-01
    修改者                    修改日期              修改内容
    ******************************************************************************************/
   public static void writeStaffLog(String bureau_no,String site_no,String staff_no,String logclassid,String content,Connection __cn__) throws FrameException {
	 
      String sqlopen = "";
      long update_nums = 0;
      ResultSet rs;
      String slog_id="";
      String slog_time="";
      try {
          String sql1="select '0' log_id,to_char(SYSDATE,'YYYY-MM-DD HH24:MI:SS') log_time"+
          " from dual";
          rs =Dao.select(sql1, __cn__);
         while(rs.next()){
            slog_id =rs.getString("log_id");
            slog_time =rs.getString("log_time");
         }
         //logger.info("writeStaffLog-->>slog_id:"+slog_id+"--slog_tiem:"+slog_time);
          HashMap hmlog =new HashMap();
          hmlog.put("BUREAU_NO",bureau_no);
          hmlog.put("SITE_NO",site_no);
          hmlog.put("STAFF_NO",staff_no);
          hmlog.put("LOG_ID",slog_id);
          hmlog.put("LOG_CLASS_ID",logclassid);
          hmlog.put("LOGTIME",slog_time);
          hmlog.put("CONTENT",content);

          Dao.insert("tsm_log", hmlog, __cn__);

      } catch (SQLException e) {
         throw new FrameException(-22120009, "SystemServ:writeStaffLog 新增操作日志时出现异常\n ,错误信息是:" +content+ e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }
   }
   /*************************************************************************************
    组件名称:	MSM_011
    功能说明:	保存(增加或者修改)操作人员信息。
    输入参数:
			    变量名        类型        长度     名称
			    Action_Flag   String      1       1:修改2:增加
			    staff_no      String      6     人员编号 NOT   NULL
			    staff_code    String      16    人员编码 NOT   NULL
			    staff_name    String      16    人员名称
			    password      String      16    口令
			    bureau_no     String      14    区域标志 NOT   NULL
			    site_no       String      6     操作点编号  NOT   NULL
			    rela_phone    String      64    电话号码
			    state         String      2     状态信息
			    oldsite_no    String      6     原操作点编号
			    title_id      String      4     职务ID      NOT NULL
			    auth_level    String      1     权限级别
			    title_type    string      1     职务级别
			    sex           string      1     性别
			    college       string      20    毕业院校
			    degree        string      3     学历

    返回信息:
    			RETCODE		返回结果(long)
			    0:          成功；
			    -22120000:  新增人员信息记录失败；
			    -22120001:  新增人员信息时出现异常;
			    -22120002:  修改操作员信息记录失败;
			    -22120003:  修改操作员信息时出现异常;

    作者：  	盛正春
    编写日期:	2003-07-01
    修改者	修改日期		修改内容
    *****************************************************************************************
    李湘麒	2004-06-07		加入对STAFF_CODE的新增及生成的支持
    shawn   2004-09018      新增员工，自动生成staff_no,如未输入staff_code，则按city_no+staff_no补足5位生成staff_code
                            修改员工，staff_code可修改
    */
   public long MSM_011(DynamicDict aDict) throws FrameException {

      String sqlopen 		= "";
      long update_nums 		= 0;

      String action_flag 	= (String) aDict.getValueByName("Action_Flag");
      String site_no 		= (String) aDict.getValueByName("site_no");      
      String staff_no 		= (String) aDict.getValueByName("staff_no",false);
      String staff_code		= (String) aDict.getValueByName("staff_code",false);
      String staff_name 	= (String) aDict.getValueByName("staff_name");
      String password 		= (String) aDict.getValueByName("password");
      String bureau_no 		= (String) aDict.getValueByName("bureau_no");
      
      String auth_level 	= (String) aDict.getValueByName("auth_level", false);
      String rela_phone 	= (String) aDict.getValueByName("rela_phone", false);
      String oldsite_no 	= (String) aDict.getValueByName("oldsite_no", false);

      String sex 			= (String) aDict.getValueByName("sex", false);
      String state 			= (String) aDict.getValueByName("State", false);      
      String degree 		= (String) aDict.getValueByName("degree", false);      
      String college 		= (String) aDict.getValueByName("college", false);      
      String title_id 		= (String) aDict.getValueByName("title_id", false);
      String title_type 	= (String) aDict.getValueByName("title_type", false);

      String my_bureau_no 	= (String) aDict.getValueByName("bureau");
      String my_site_no 	= (String) aDict.getValueByName("site");
      String my_staff_co 	= (String) aDict.getValueByName("staff");

      //if(DEBUG)logger.info("System.MSM_011.Decrypt("+password+")="+TEA.Decrypt(password));
      if (password.length() > 16 && password.length() != 32) throw new FrameException(-22120004, "密码长度不能超16位！");
      if (aDict.getValueByName("Action_Flag", false).equals("1")){//修改
			try{
				if (GlobalNames.USE_LDAP){
					//保存到ldap的密码是未加密的
					HashMap _info = new HashMap();
					_info.put("BFIBSSID",staff_no);
					if (!isEcrypted(password)) 
					    _info.put("BFIBSSPASSWD", password);  //未加密
					else 
					    _info.put("BFIBSSPASSWD", TEA.Decrypt(password));
					if (state.equals("1")) _info.put("BFIBSSSTATUS","0"); else _info.put("BFIBSSSTATUS","1");
					int iRet = LDAPUtils.updateToLDAP(_info);
					if (iRet !=0) throw new FrameException(-22120005, "通过LDAP修改用户失败！");
				}
				if (!isEcrypted(password))password = TEA.Encrypt(password);
				
				if (staff_no.equals("") || bureau_no.equals("")) throw new FrameException(-22120005, "修改用户失败！");
				
				sqlopen = "UPDATE TSM_STAFF "
				        + "SET STAFF_NO 	='"+ staff_no + "'"
				        + ",   SITE_NO    	='"+ site_no + "'"
				        + ",   STAFF_CODE   ='"+ staff_code + "'"
				        + ",   BUREAU_NO 	='"+ bureau_no + "'"
				        + ",   STAFF_NAME 	='"+ staff_name + "'"
				        + ",   PASSWORD  	='"+ password + "'"
				        + ",   STATE 		='"+ state + "'"
				        + ",   RELA_PHONE 	='"+ rela_phone + "'"
				        + ",   AUTH_LEVEL 	='"+ auth_level + "'"
				        + ",   TITLE_TYPE 	='"+ title_type + "'"
				        + ",   SEX   		='"+ sex + "'"
				        + ",   COLLEGE  	='"+ college + "'"
				        + ",   DEGREE 		='"+ degree + "'"
				        + " WHERE STAFF_NO 	='"+ staff_no + "'";
				//logger.info("system.msm_011.sql[489]="+staff_code);
				update_nums = __st__.executeUpdate(sqlopen);
				if (update_nums <= 0) {
					throw new FrameException(-22120000, "SystemServ: 修改操作员失败" + "sql语句是:" + sqlopen);
				}else{
					writeStaffLog(my_bureau_no,my_site_no,my_staff_co,"12","员工号："+my_staff_co+" 修改操作员("+staff_no+":"+staff_name+")信息成功！",aDict.GetConnection());
				}
			}catch (SQLException e) {
				throw new FrameException(-22120001, "SystemServ: 修改人员信息时出现异常", e.getMessage());
			}
		}else if (aDict.getValueByName("Action_Flag", false).equals("2")){ //新增
			try{
				if(GlobalNames.USE_LDAP){
					staff_no = String.valueOf(LDAPUtils.getMaxID() +1);
					HashMap _info = new HashMap();
					_info.put("BFIBSSID",		staff_no);
					_info.put("BFIBSSIDTYPE",	"0");
					_info.put("BFIBSSCHANNEL",	"1");
					if (!isEcrypted(password)) 
						_info.put("BFIBSSPASSWD", password); 
						else 
						_info.put("BFIBSSPASSWD", TEA.Decrypt(password));
					if (state.equals("1")) _info.put("BFIBSSSTATUS","0"); else _info.put("BFIBSSSTATUS","1");
					int iRet = LDAPUtils.addToLDAP(_info);
					if (iRet !=0) throw new FrameException(-22120004, "通过LDAP添加用户失败！");
				}
				//自动生成staff_no
				sqlopen = "SELECT max(to_number(staff_no)) AS max_staff_no FROM tsm_staff WHERE Pkg_Bfibss_Sr_Utl.isnumeric(staff_no)='1'";
				__rs__ 	= Dao.select(sqlopen, aDict.GetConnection());
				staff_no = "1";
				if (__rs__.next()) staff_no = String.valueOf(__rs__.getInt(1)+1);

				if (staff_code.equals(""))
					staff_code = staff_no;
				int length = staff_code.length();
				for (int i=length; i<5; i++) staff_code = "0"+staff_code;
				staff_code = bureau_no.substring(3,5)+staff_code;
				//如密码未加密，则加密码
				if (!isEcrypted(password))password = TEA.Encrypt(password);
				
				sqlopen = "INSERT INTO tsm_staff(staff_no,staff_code,site_no,bureau_no,staff_name,"+
						  "password,auth_level,state,rela_phone,title_type,sex,college,degree)"+
						  "VALUES('"+staff_no+"','"+staff_code+"','"+site_no+"','"+bureau_no+"','"+staff_name+"','"+
				          password+"','"+auth_level+"','"+state+"','"+rela_phone+"','"+title_type+"','"+sex+ "','"+college+"','"+degree+"')";
				
				update_nums = __st__.executeUpdate(sqlopen);			
				if (update_nums <= 0){
					throw new FrameException(-22120002, "SystemServ: 新增操作员信息失败" + "sql语句是:" + sqlopen);
				}else{
					writeStaffLog(my_bureau_no,my_site_no,my_staff_co,"12","员工号："+my_staff_co+" 新增操作员("+staff_no+":"+staff_name+")信息成功！",aDict.GetConnection());
				}
				if(bMCHinterface){
				    DynamicDict dict = new DynamicDict();
				    dict.m_ActionId	 = "MCH_501";
				    dict.SetConnection(__cn__);
				    dict.setValueByName("staff_no",staff_no);
				    SysSet.callService(dict);
				}
			}catch (SQLException e) {
				e.printStackTrace();
				throw new FrameException(-22120003, "SystemServ: 新增操作员信息时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen);
			}
		}
		//职务ID是否为空
		if (!title_id.equals("")) {
			//清空职务表
			try {
				sql = "DELETE FROM tsm_staff_title WHERE staff_no='"+staff_no+"' AND bureau_no='"+bureau_no+"' AND site_no='"+site_no+"'";
				Dao.update(sql, aDict.GetConnection());
			}catch (SQLException e) {
				throw new FrameException(-21208400, "SystemServ:MSM-011清空职务表失败\n ,错误信息是:"+ e.getMessage() + "\n   sql语句是:" + sqlopen);
			}
			//重新设置职务表
			try {
				sql = "INSERT  INTO tsm_staff_title (staff_no,bureau_no,site_no,title_id)VALUES('"+staff_no+"','"+bureau_no+"','"+site_no+"','"+title_id+"')";
				Dao.update(sql, aDict.GetConnection());
			}catch (SQLException e) {
				throw new FrameException(-21208401, "SystemServ:MSM-011重新设置职务表失败\n,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen);
			}
		}
		return 0;
	}
	
	//判断是否加密，如加密，密码串加度=16 or 32
	public boolean isEcrypted(String passwd) {
		if (passwd.length()!=16 && passwd.length()!=32 )
			return false;
			else
			return true;
	}

   /****************************************************************************************
    MSM_012    保存(增加或者修改)操作点信息
    组件类型:  中间层     所属Server名称: SSM
    组件名称:  MSM_012
    功能说明:  保存(增加或者修改)操作点信息。

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名          类型       长度   名称

    Site_NO         string    6     操作点编号   NOT NULL,
    Bureau_NO       string    14     区域标志     NOT NULL,
    Site_Name       string    64     操作点名
    Addr            string    128       人员编号
    Rela_Phone      string    64     电话号码
    UP_SITE_NO      string    6      上级操作点
    State           string    2      状态信息
    Limit_Machine_Flag string    1      限定登录终端标志
    输出参数类型：
    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0:              成功；
    -22120004:   新增操作点信息记录失败;
    -22120005:   新增操作点信息时出现异常;
    -22120006:   修改操作点信息记录失败;
    -22120007:   修改操作点信息时出现异常;


    作者：  盛正春
    编写日期：   2003-07-01
    修改者	修改日期		修改内容
    *****************************************************************************************
    李湘麒	2004-06-07		加入对SITE_CODE的新增及生成的支持
    */
	public long MSM_012(DynamicDict aDict) throws FrameException {
		String sqlopen 			= "";
		long update_nums 		= 0;
		
		String site_no 			= ((String) aDict.getValueByName("site_no", false)).trim();
		String site_code		= site_no;
		String bureau_no 		= (String) aDict.getValueByName("bureau_no");
		String site_name 		= ((String) aDict.getValueByName("site_name")).trim();
		String site_type 		= ((String) aDict.getValueByName("site_type")).trim();
		String addr 			= ((String) aDict.getValueByName("addr", false)).trim();
		String rela_phone 		= ((String) aDict.getValueByName("rela_phone", false)).trim();
		String up_site_no 		= ((String) aDict.getValueByName("up_site_no", false)).trim();
		String state 			= ((String) aDict.getValueByName("state", false)).trim();
		String limit_machine_flag=((String) aDict.getValueByName("limit_machine_flag", false)).trim();
		
		String sbureau 			= (String) aDict.getValueByName("bureau");
		String ssite 			= (String) aDict.getValueByName("site");
		String sstaff 			= (String) aDict.getValueByName("staff");
		
		if (!site_no.equals("")){
			//修改
			try{
				sqlopen = "UPDATE tsm_site "
				        + "	  SET site_name 	='"+site_name+"',"
						+ "	      site_type 	='"+site_type+"',"
				        + "		  addr			='"+addr+"',"
				        + "		  rela_phone 	='"+rela_phone+ "',"
				        + "		  up_site_no 	='"+up_site_no+ "',"
				        + "		  state  		='"+state+"',"
				        + "		  limit_machine_flag='"+limit_machine_flag+"'"
				        + " WHERE site_no  		='"+site_no+"'";
				update_nums = __st__.executeUpdate(sqlopen);
				if (update_nums <= 0) {
					throw new FrameException(-22120004, "SystemServ:MSM_012 修改操作点失败");
				}else{
					writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" 修改操作点("+site_no+":"+site_name+")信息成功！",aDict.GetConnection());
				}
			}catch (SQLException e) {
				throw new FrameException(-22120005, "SystemServ:MSM_012 修改操作点信息时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen);
			}
		}else{
			//新增
			try{
				//build site_no and site_code
			    sqlopen = "SELECT max(to_number(site_no)) AS max_site_no FROM tsm_site";
			    __rs__ 	= Dao.select(sqlopen, aDict.GetConnection());
			    if (__rs__.next()) site_no = String.valueOf(__rs__.getInt(1)+1); else site_no="1";
			    //site_code  = site_no;
				//int length = site_code.length();
				//for (int i=length; i<5; i++) site_code = "0"+site_code;
				//site_code = bureau_no.substring(3,5)+site_code;
			    
			    sqlopen = "INSERT INTO tsm_site (site_no,site_code,bureau_no,site_name,site_type,addr,"+
			              "rela_phone,up_site_no,state,limit_machine_flag)"+
			              "VALUES('"+site_no+"',substr('"+bureau_no+"',4,2)||lpad('"+site_no+"',5,'0'),'"+bureau_no+"','"+site_name+"','"+site_type+"','"+addr+"','"+rela_phone+"','"+
			              up_site_no+"','"+state+"','"+limit_machine_flag+"')";
			    update_nums = __st__.executeUpdate(sqlopen);
			    if (update_nums <= 0) {
			    	throw new FrameException(-22120006, "SystemServ:MSM_012 新增操作点信息失败");
			    }else{
			    	writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" 新增操作点("+site_no+":"+site_name+")信息成功！",aDict.GetConnection());
			    }
				if(bMCHinterface){
				    DynamicDict dict = new DynamicDict();
				    dict.m_ActionId	 = "MCH_503";
				    dict.SetConnection(this.__cn__);
				    dict.setValueByName("site_no", site_no);
				    SysSet.callService(dict);
				}
			}catch(SQLException e) {
				throw new FrameException(-22120007, "SystemServ:MSM_012 新增操作点信息时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen);
			}
		}
		//设置下属节点状态信息
		MSM_015(aDict, state, site_no);
		return 0;
	}

   /*****************************************************************************************
    MSM_015    设置下属节点状态信息
    组件类型:  中间层     所属Server名称: SSM
    模块名称:
    功能说明:  设置下属节点状态信息。

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名         类型     长度   名称
    State         string       2     状态
    Site_No       string      6     操作点                   NOT NULL,
    aDict                           处理入参和出参的数据字典对象

    输出参数类型：
    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0:              成功；
    -22120010:   设置操作点下属节点状态信息失败;
    -22120011:   设置操作点下属节点状态信息出现异常;
    -22120012:      设置操作员下属节点状态信息失败
    -22120013:      设置操作员下属节点状态信息出现异常;


    作者：  盛正春
    编写日期：   2003-07-01
    修改者                    修改日期              修改内容

    *****************************************************************************************/
   public long MSM_015(DynamicDict aDict, String sState, String sSite_No) throws FrameException {
      String sql1;
      String sql2;

      String sqlopen = "";
	  getLogger();
      if (sState.compareTo("1") == 0) {   /* 如果状态是有效 */
         try {
            sql1 = "update tsm_site set state='1' where up_site_no='"+ sSite_No + "'";

            sql2 = "update tsm_staff   set   state='1' where   site_no  in ("
                    + "select site_no from   tsm_site start with  site_no=" + "'" + sSite_No + "'"
                    + " connect by  prior site_no=up_site_no)";

            //Dao.update(sql1,aDict.GetConnection());

            __st__.executeUpdate(sql1);
            __st__.executeUpdate(sql2);
            //Vector vSQL =new   Vector();
            //vSQL.add(sql);
            //Dao.change(vSQL,   aDict.GetConnection());
            return 0;

         } catch (SQLException e) {
            m_Logger.debug("错误原因是:" + e.toString());
            m_Logger.debug("SQL语句是:" + sqlopen);
            throw new FrameException(-22120011, "SystemServ:MSM_015设置操作点下属节点状态信息时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen);
         }

      } else {/* 如果状态无效 */
         try {

            sql1 = "update tsm_site set state=0 where up_site_no=" + "'" + sSite_No + "'";

            sql2 = "update tsm_staff   set   state='0' where   site_no  in ("
                    + "select site_no from   tsm_site start with  site_no=" + "'" + sSite_No + "'"
                    + " connect by  prior site_no=up_site_no)";

            __st__.executeUpdate(sql1);
            __st__.executeUpdate(sql2);
            return 0;

         } catch (SQLException e) {
            throw new FrameException(-22120013, "SystemServ:MSM_015设置操作员下属节点状态信息时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen);
         }
      }

   }

   /******************************************************************************************************************
    MSM_016    删除部门\操作点
    组件类型:  中间层     所属Server名称: SSM
    组件名称:  MSM_016
    功能说明:  删除部门\操作点。

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名           类型      长度   名称

    FIELD1     Bureau_NO        string     14       地域代码         非空
    FIELD2     Site_No          string     6     操作点ID      非空

    输出参数类型：
    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    -1                   未删除: 操作点,区域可能为空
    0                 已删除
    1                 节点下有子节点,故不能删除
    作者：  盛正春
    编写日期：   2003-07-01
    修改者                    修改日期              修改内容
    注:有子节点不能删除,前台控制
    ******************************************************************************************************************/
	public void MSM_016(DynamicDict aDict) throws FrameException, SQLException {
		String site_no 	= (String)aDict.getValueByName("site_no",false);
		String bureau_no= (String)aDict.getValueByName("bureau_no",false);
		   	
      	if (!site_no.equals("") || !bureau_no.equals("")){
	        String sbureau 	= ((String) aDict.getValueByName("bureau")).trim();
	        String ssite 	= ((String) aDict.getValueByName("site")).trim();
	        String sstaff 	= ((String) aDict.getValueByName("staff")).trim();
	         //查询是否是叶节点
	        String sql0		= "";
	        String sql00	= "";
            sql0 =	"SELECT count(*) FROM tsm_site WHERE site_no "+
                  	"	 IN (select site_no from tsm_site where up_site_no='"+site_no+"')";
            sql00=	"SELECT count(*) FROM tsm_site  WHERE site_no "+
            		"	 IN (select site_no from tsm_staff where site_no='"+site_no+"')";

	        String strNum 	= "";
	        String strNum1	= "";
	        
			__rs__ = __st__.executeQuery(sql0);
			if (__rs__.next()) strNum	= __rs__.getString(1);
			__rs__.close();
			__rs__ = __st__.executeQuery(sql00);
			if (__rs__.next()) strNum1 = __rs__.getString(1);
			__rs__.close();
			
			//如果是 叶节点,则进行删除操作
			if ((strNum.compareTo("0") == 0)&&(strNum1.compareTo("0") == 0)) {
				// Vector vSQL =new Vector();
				String sql1 = 	"DELETE FROM tsm_site_role WHERE bureau_no='"+bureau_no+"' AND site_no='"+site_no+"'";
				String sql2 = 	"INSERT INTO tsm_site_his (SELECT a.bureau_no,a.site_no,a.site_name,a.addr,a.rela_phone,"
							+   "a.limit_machine_flag,a.up_site_no,a.state,a.dept_site_flag,a.site_code,"
							+   " sysdate,a.site_type FROM tsm_site a WHERE a.bureau_no='"+bureau_no+"' AND a.site_no ='"+site_no+"')";
				String sql3 = 	"DELETE FROM tsm_site WHERE bureau_no='"+bureau_no+"'"+" AND site_no='"+site_no+"'";
				__st__.executeUpdate(sql1);
				__st__.executeUpdate(sql2);
				__st__.executeUpdate(sql3);
				writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" 删除部门/操作点("+site_no+")信息成功！",aDict.GetConnection());
			}else{
				m_Logger.info("有子节点,不能删除!");
				//System.out.print("有子节点,不能删除!");
			}
		}else{
			m_Logger.info("MSM_016:删除部门或操作点---所传入的参数有可能为空!");
		}
	}

   /*****************************************************************************************************************
    MSM_017    删除操作人
    组件类型:  中间层     所属Server名称: SSM
    组件名称:  MSM_017
    功能说明:  删除操作人

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名           类型      长度   名称

    FIELD1     Bureau_NO      string         14    地域代码      非空
    FIELD2     Site_No        string         6     操作点ID      非空
    FIELD3     Staff_No       string         6     操作员ID
    输出参数类型：
    输出参数:  无
    返回信息:
    RETCODE    long             返回结果


    作者：  盛正春
    编写日期：   2003-07-01
    修改者                    修改日期              修改内容
    *****************************************************************************************************************/
   public void MSM_017(DynamicDict aDict) throws FrameException, SQLException {
	  getLogger();
   	//if(DEBUG)logger.info("system.msm_017start");
      if ( !aDict.getValueByName("Staff_No").equals("") ) {

         String Staff_No = ((String) aDict.getValueByName("Staff_No")).trim();

         //与计费同步
		if(bMCHinterface){
		       DynamicDict dict =new DynamicDict();
		       dict.m_ActionId ="MCH_502";
		       dict.SetConnection(this.__cn__);
		       dict.setValueByName("staff_no", Staff_No);
		       m_Logger.debug("systeserv.msm_017.staff_no="+Staff_No);
		       SysSet.callService(dict);
		}
         String sqlquery =" WHERE STAFF_NO ='"+ Staff_No + "'";
         String sql1 = "DELETE FROM TSM_STAFF_ROLE " + sqlquery;
         String sql2 = "DELETE FROM TSM_STAFF_TITLE " + sqlquery;
         String sql3 = "INSERT INTO TSM_STAFF_HIS(STAFF_NO,SITE_NO,STAFF_NAME,PASSWORD,TITLE_TYPE,AUTH_LEVEL,STATE,RELA_PHONE,SEX,COLLEGE,DEGREE,BAK_DATE)"
                                        +" SELECT STAFF_NO,SITE_NO,STAFF_NAME,PASSWORD,TITLE_TYPE,AUTH_LEVEL,STATE,RELA_PHONE,SEX,COLLEGE,DEGREE,SYSDATE FROM TSM_STAFF"
                                        +sqlquery;
         String sql4 = "DELETE FROM TSM_STAFF " + sqlquery;

         __st__.executeUpdate(sql1);
         __st__.executeUpdate(sql2);
         __st__.executeUpdate(sql3);
         __st__.executeUpdate(sql4);

       } else {
         m_Logger.info("MSM_017:删除操作员失败，未取到员工号");
      }
   }

   /*******************************************************************************
    功能: 根据区域编码查询区域信息;
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_040
    功能说明:  根据区域编码查询区域信息;
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称
    Bureau_NO      string         15         区域编码

    输出参数:
    格式：    　　　　
    变量名       类型            长度     名称
    Bureau_NO       string        14      区域编码
    Bureau_Name        string        16         区域名称
    Top_No       string        3       一级区域编码
    TopArea_Name    string        16         一级区域名称
    City_No         string        2       地市编码
    City_Name       string        16         地市名称
    Xian_NO         string        2       县区编码
    Area_Code       string        6       长途区号

    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-05
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public Vector MSM_040(DynamicDict aDict) throws FrameException {

      String sBureau_NO;
      /**String sBureau_Name             ;
       String sTop_No              ;
       String sTopArea_Name            ;
       String sCity_No                 ;
       String sCity_Name               ;
       String ssXian_NO             ;**/

      String sqlopen = "";
      ResultSet update_nums = null;

      sBureau_NO = ((String) aDict.getValueByName("Bureau_NO")).trim();
      /**sBureau_Name       = ((String)aDict.getValueByName("Bureau_Name")).trim();
       sTop_No         =   ((String)aDict.getValueByName("Top_No")).trim();
       sTopArea_Name   =   ((String)aDict.getValueByName("TopArea_Name")).trim();
       sCity_No        =   ((String)aDict.getValueByName("City_No")).trim();
       sCity_Name      =   ((String)aDict.getValueByName("City_Name")).trim();
       sXian_NO       = ((String)aDict.getValueByName("Xian_NO")).trim();
       sArea_Code       = ((String)aDict.getValueByName("Area_Code")).trim();**/
      try {
         sqlopen = "SELECT a.BUREAU_NO,"
                 + " NVL(a.BUREAU_NAME,' '),"
                 + " a.TOP_NO,"
                 + " NVL(b.TOPAREA_NAME,' '),"
                 + " a.CITY_NO,"
                 + " NVL(c.CITY_NAME,' '),"
                 + " a.XIAN_NO,"
                 + " a.AREA_CODE"
                 + " FROM TVLSM_BUREAU a,TVLSM_TOPAREA_INFO b,TVLSM_CITY_INFO c"
                 + " WHERE a.TOP_NO=b.TOP_NO"
                 + " AND   a.TOP_NO=b.TOP_NO"
                 + " AND   a.TOP_NO=c.TOP_NO"
                 + " AND   a.CITY_NO=c.CITY_NO";
         //  +" AND a.BUREAU_NO="+"'"+sBureau_NO   +"'";

         if (__st__ == null)
            __st__ = aDict.GetConnection().createStatement();
         update_nums = __st__.executeQuery(sqlopen);
         if (update_nums == null) {
            throw new FrameException(-22120400, "SystemServ:MSM_040根据区域编码查询区域信息失败" + "sql语句是:" + sqlopen);
         }

         ResultSetMetaData rsmd = update_nums.getMetaData();
         String[] fields_name = new String[rsmd.getColumnCount()];
         for (int k = 1; k <= rsmd.getColumnCount(); k++)
            fields_name[k - 1] = rsmd.getColumnName(k);

         Vector vector1 = new Vector();
         HashMap hashmap;
         while (update_nums.next()) {
            hashmap = new HashMap();
            for (int l = 0; l < fields_name.length; l++) {
               if (update_nums.getObject(fields_name[l].toLowerCase()) != null) {
                  Object o = update_nums.getObject(fields_name[l].toLowerCase());
                  hashmap.put(fields_name[l].toLowerCase(), o.toString());
               }
            }
            vector1.add(hashmap);
         }
         if (update_nums != null)
            update_nums.close();
         return vector1;
      } catch (SQLException e) {
         throw new FrameException(-22120401, "SystemServ:MSM_040根据区域编码查询区域信息时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }
   }

   /*******************************************************************************
    功能: 增加或修改一级区域;;
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_041
    功能说明:  增加或修改一级区域;
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称

    Top_No        string   3     一级区域编码
    TopArea_Name     string   16    一级区域名称
    Area_Type_ID     string   1  区域类别
    Oper_Flag           string      1     操作标志        0:新增,1:修改,2:删除(为2暂不考虑),非空
    输出参数: 无


    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-05
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MSM_041(DynamicDict aDict) throws FrameException {

      String sTop_No;
      String sTopArea_Name;
      String sArea_Type_ID;
      String sOper_Flag;

      String sqlopen = "";
      long update_nums = 0;

      sTop_No = ((String) aDict.getValueByName("Top_No")).trim();
      sTopArea_Name = ((String) aDict.getValueByName("TopArea_Name", false)).trim();
      sArea_Type_ID = ((String) aDict.getValueByName("Area_Type_ID", false)).trim();
      sOper_Flag = ((String) aDict.getValueByName("Oper_Flag", false)).trim();

      if (aDict.getValueByName("Oper_Flag").equals("1")) {/*修改*/
         try {
            sqlopen = "UPDATE TVLSM_TOPAREA_INFO" +
                    " SET TOPAREA_NAME=" + "'" + sTopArea_Name + "'," +
                    " AREA_TYPE_ID=" + "'" + sArea_Type_ID + "'" +
                    " WHERE   TOP_NO=" + "'" + sTop_No + "'";

            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120410, "SystemServ:MSM_041 修改一级区域记录失败" + "sql语句是:" + sqlopen);
            }
         } catch (SQLException e) {
            throw new FrameException(-22120411, "SystemServ:MSM_041 修改一级区域时出现异常\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sqlopen, e.getMessage());
         }
      } else if (aDict.getValueByName("Oper_Flag").equals("0")) {//新增
         try {
            sqlopen = "INSERT INTO  TVLSM_TOPAREA_INFO ( TOP_NO," +
                    " TOPAREA_NAME," +
                    "  AREA_TYPE_ID )" +
                    " VALUES( " + "'" + sTop_No + "'," +
                    "'" + sTopArea_Name + "'," +
                    "'" + sArea_Type_ID + "')";
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120412, "SystemServ:MSM_041 增加一级区域记录失败" + "sql语句是:" + sqlopen);
            }
         } catch (SQLException e) {
            throw new FrameException(-22120413, "SystemServ:MSM_041增加一级区域时时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      }

      return 0;
   }

   /*******************************************************************************
    功能: 增加或修改地市区域
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_042
    功能说明:  增加或修改地市区域
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称

    Top_No        string   3     一级区域编码
    City_No       string   2     地市编码
    City_Name     string   16 地市名称
    Oper_Flag        string      1     操作标志        0:新增,1:修改,2:删除,  非空
    输出参数: 无


    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-05
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MSM_042(DynamicDict aDict) throws FrameException {

      String sTop_No;
      String sCity_No;
      String sCity_Name;
      String sOper_Flag;
      String sArea_Code;

      String sqlopen = "";
      long update_nums = 0;
      String sstaff="";
      String ssite="";
      String sbureau="";

      sTop_No = ((String) aDict.getValueByName("Top_No")).trim();
      sCity_No = ((String) aDict.getValueByName("City_No")).trim();
      sCity_Name = ((String) aDict.getValueByName("City_Name", false)).trim();
      sArea_Code = ((String) aDict.getValueByName("Area_Code")).trim();
      sOper_Flag = ((String) aDict.getValueByName("Oper_Flag")).trim();

      sstaff = ((String) aDict.getValueByName("staff")).trim();
      ssite = ((String) aDict.getValueByName("site")).trim();
      sbureau = ((String) aDict.getValueByName("bureau")).trim();
      if (aDict.getValueByName("Oper_Flag").equals("1")) {/*修改*/
         try {
            sqlopen = "UPDATE TVLSM_CITY_INFO" +
                    " SET CITY_NAME=" + "'" + sCity_Name + "'," +
                    " Area_Code=" + "'" + sArea_Code + "'"+
                    " WHERE   TOP_NO=" + "'" + sTop_No + "'" +
                    " AND CITY_NO=" + "'" + sCity_No + "'";

            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120420, "SystemServ:MSM_042 修改地市区域记录失败" + "sql语句是:" + sqlopen);
            }
            writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" （修改地市名称"+sCity_Name+" 修改长途区号 "+sArea_Code+"）信息成功！",aDict.GetConnection());
            sqlopen="select count(*) from tvlsm_bureau "+
                    " WHERE   TOP_NO=" + "'" + sTop_No + "'" +
                    " AND CITY_NO=" + "'" + sCity_No + "'";

            String strNum = "";
            __rs__ = __st__.executeQuery(sqlopen);
            if (__rs__.next()) {
             strNum = __rs__.getString(1).trim();
            }
            if (strNum.compareTo("1") == 0) {
	            sqlopen="update tvlsm_bureau set area_code='"+sArea_Code+"'"+
	                    " WHERE   TOP_NO=" + "'" + sTop_No + "'" +
	                    " AND CITY_NO=" + "'" + sCity_No + "'";
	            update_nums = __st__.executeUpdate(sqlopen);
	            if (update_nums <= 0) {
	               throw new FrameException(-22120429, "SystemServ:MSM_042 修改区域表中长途区号记录失败" + "sql语句是:" + sqlopen);
	            }
	      }
         } catch (SQLException e) {
            throw new FrameException(-22120421, "SystemServ:MSM_042 修改地市区域时出现异常\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sqlopen, e.getMessage());
         }
      } else if (aDict.getValueByName("Oper_Flag").equals("0")) {//新增
         try {
            sqlopen = "INSERT INTO  TVLSM_CITY_INFO   ( TOP_NO," +
                    " CITY_NO," +
                    " Area_Code," +
                    " CITY_NAME  )" +
                    " VALUES( " + "'" + sTop_No + "'," +
                    "'" + sCity_No + "'," +
                    "'" + sArea_Code + "'," +
                    "'" + sCity_Name + "')";
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120422, "SystemServ:MSM_042 增加地市区域记录失败" + "sql语句是:" + sqlopen);
            }else{
           	 writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" （增加地市名称"+sCity_Name+" 修改长途区号 "+sArea_Code+"）信息成功！",aDict.GetConnection());
            }
         } catch (SQLException e) {
            throw new FrameException(-22120423, "SystemServ:MSM_042 增加地市区域时时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      }
      return 0;
   }


   /*******************************************************************************
    功能: 增加或修改区域信息
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_044
    功能说明:  增加或修改区域信息
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称

    Bureau_NO        string   14 区域编码
    Bureau_Name         string   16    区域名称
    Top_No        string   3     一级区域编码
    City_No       string   2     地市编码
    Xian_No       string   2  县区编码
    Area_Code        string   6  长途区号
    Oper_Flag        string      1     操作标志        0:新增,1:修改,2:删除,  非空
    输出参数: 无

    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-05
    修改者                    修改日期              修改内容

    /*******************************************************************************
    李湘麒					2004-7-20				修改区域类型添加和修改时出错的BUG
    */
   public long MSM_044(DynamicDict aDict) throws FrameException {

      String sqlopen 		= "";
      long update_nums 		= 0;
	  String level_res		="";

	  String now_flag		= ((String) aDict.getValueByName("flag")).trim();
	  //if (now_flag.equals("1"))
	  //{
		  level_res		= ((String) aDict.getValueByName("level_res")).trim();
	  //}
	  
      String Bureau_No		= ((String) aDict.getValueByName("Bureau_No")).trim();
      String Bureau_Name	= ((String) aDict.getValueByName("Bureau_Name", false)).trim();
      String Up_Bureau_No	= ((String) aDict.getValueByName("Up_Bureau_No")).trim();            
      String Org_Id			= ((String) aDict.getValueByName("Org_Id", false)).trim();          
      String Top_No			= ((String) aDict.getValueByName("Top_No")).trim();
      String City_No 		= ((String) aDict.getValueByName("City_No", false)).trim();
      String Xian_No 		= ((String) aDict.getValueByName("Xian_No", false)).trim();
      String Area_Code 		= ((String) aDict.getValueByName("Area_Code", false)).trim();
      String Oper_Flag 		= ((String) aDict.getValueByName("Oper_Flag", false)).trim();
      String Type			= ((String) aDict.getValueByName("type", false)).trim();

      String sstaff 		= ((String) aDict.getValueByName("staff",false)).trim();
      String ssite 			= ((String) aDict.getValueByName("site",false)).trim();
      String sbureau 		= ((String) aDict.getValueByName("bureau",false)).trim();
	  getLogger();
      //logger.info("msm_044:bureau-->>"+sBureau_NO);
      if (aDict.getValueByName("Oper_Flag").equals("1")) {/*修改*/
         try {
         	if (!Bureau_No.equals(Org_Id)){
	         	sqlopen = "SELECT bureau_no FROM tvlsm_bureau WHERE bureau_no='"+Bureau_No+"'";
	         	__rs__ = __st__.executeQuery(sqlopen);
	         	if (__rs__.next()) throw new FrameException(-22120440, "有相同的编号("+Bureau_No+")的区域存在");
	            __rs__.close();
        	}
            if (now_flag.equals("1"))
            {
				sqlopen = 	"UPDATE TVLSM_BUREAU " +
                    	"	SET bureau_name	='" + Bureau_Name + "'," +
                    	"		bureau_no	='" + Bureau_No + "'," +
                    	"		up_bureau_no='" + Up_Bureau_No + "'," +                    	
                    	" 		top_no		='" + Top_No + "'," +
                    	" 		city_no		='" + City_No + "'," +
                    	" 		xian_no		='" + Xian_No + "'," +
                    	" 		area_code	='" + Area_Code + "'," +
                    	" 		type 		='" + Type + "'," + 
						" 		flag_res	='1'," + 
						" 		level_res	='" + level_res + "'" + 
                    	" WHERE bureau_no	='" + Org_Id + "'";
            }else{
				sqlopen = 	"UPDATE TVLSM_BUREAU " +
                    	"	SET bureau_name	='" + Bureau_Name + "'," +
                    	"		bureau_no	='" + Bureau_No + "'," +
                    	"		up_bureau_no='" + Up_Bureau_No + "'," +                    	
                    	" 		top_no		='" + Top_No + "'," +
                    	" 		city_no		='" + City_No + "'," +
                    	" 		xian_no		='" + Xian_No + "'," +
                    	" 		area_code	='" + Area_Code + "'," +
                    	" 		type 		='" + Type + "'," + 
						" 		flag_res	='0'," + 
						" 		level_res	=''" +                     	
                    	" WHERE bureau_no	='" + Org_Id + "'";
			}
	    	m_Logger.debug("msm_044:sql-->>"+sqlopen);
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120440, "SystemServ:MSM_044 修改区域信息记录失败" + "sql语句是:" + sqlopen);
            }else{
            	writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" 修改区域("+Org_Id+":"+Bureau_Name+")信息成功！",aDict.GetConnection());
            }
         }catch (SQLException e){
            throw new FrameException(-22120441, "SystemServ:MSM_044 修改区域信息时出现异常\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sqlopen, e.getMessage());
         }
      }else if (aDict.getValueByName("Oper_Flag").equals("0")) {//新增
         try {
         	sqlopen =	"SELECT bureau_no FROM tvlsm_bureau WHERE bureau_no='"+Bureau_No+"'";
         	__rs__ = __st__.executeQuery(sqlopen);
         	if (__rs__.next()) throw new FrameException(-22120442, "有相同的编号("+Bureau_No+")的区域存在");
            __rs__.close();
			if (now_flag.equals("1"))
			{
				sqlopen =	"INSERT INTO TVLSM_BUREAU "+
            			"(bureau_no,bureau_name,up_bureau_no,top_no,city_no,xian_no,area_code,type,flag_res,level_res)"+
            			"VALUES "+
            			"('"+Bureau_No+"','"+Bureau_Name+"',"+
            			(Up_Bureau_No.equals("0") ? "null,":"'"+Up_Bureau_No+"',")+
                    	"'"+Top_No+"','"+City_No+"','"+Xian_No+"','"+Area_Code+"','"+Type+"','1','"+level_res+"')";
			}else{
				sqlopen =	"INSERT INTO TVLSM_BUREAU "+
            			"(bureau_no,bureau_name,up_bureau_no,top_no,city_no,xian_no,area_code,type,flag_res,level_res)"+
            			"VALUES "+
            			"('"+Bureau_No+"','"+Bureau_Name+"',"+
            			(Up_Bureau_No.equals("0") ? "null,":"'"+Up_Bureau_No+"',")+
                    	"'"+Top_No+"','"+City_No+"','"+Xian_No+"','"+Area_Code+"','"+Type+"','0','')";
			}
           m_Logger.debug("msm_044: sqlopen-->>"+sqlopen);
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
            	throw new FrameException(-22120442, "SystemServ:MSM_044 增加区域信息记录失败" + "sql语句是:" + sqlopen);
            }else{
            	writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" 增加区域("+Bureau_No+":"+Bureau_Name+")信息成功！",aDict.GetConnection());
        	}
         } catch (SQLException e) {
            throw new FrameException(-22120443, "SystemServ:MSM_044 增加区域信息时出现异常\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sqlopen, e.getMessage());
         }
      }
      return 0;
   }

   /*******************************************************************************
    功能: 根据一级区域编码查询一级区域信息;
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_045
    功能说明:  根据一级区域编码查询一级区域信息;
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称
    Top_NO           string       3    一级区域编码

    输出参数:
    变量名           类型      长度   名称
    Top_No        string   3     一级区域编码
    TopArea_Name     string   16    一级区域名称
    Area_Type_ID     string   1     区域类别

    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-05
    修改者                    修改日期              修改内容
    /*******************************************************************************/
   public Vector MSM_045(DynamicDict aDict) throws FrameException {

      String sTop_No;
      String sTopArea_Name;
      String sArea_Type_ID;

      String sqlopen = "";
      ResultSet update_nums = null;

      sTop_No = ((String) aDict.getValueByName("Top_No")).trim();
      sTopArea_Name = ((String) aDict.getValueByName("TopArea_Name", false)).trim();
      sArea_Type_ID = ((String) aDict.getValueByName("Area_Type_ID", false)).trim();

      try {
         sqlopen = "SELECT TOP_NO," +
                 "   TOPAREA_NAME," +
                 "   TRIM(TO_CHAR(AREA_TYPE_ID))" +
                 " FROM TVLSM_TOPAREA_INFO" +
                 " WHERE   TOP_NO=" + "'" + sTop_No + "'";

         if (__st__ == null)
            __st__ = aDict.GetConnection().createStatement();
         update_nums = __st__.executeQuery(sqlopen);
         if (update_nums == null) {
            throw new FrameException(-22120450, "SystemServ:MSM_045 根据一级区域编码查询一级区域信息失败" + "sql语句是:" + sqlopen);
         }

         ResultSetMetaData rsmd = update_nums.getMetaData();
         String[] fields_name = new String[rsmd.getColumnCount()];
         for (int k = 1; k <= rsmd.getColumnCount(); k++)
            fields_name[k - 1] = rsmd.getColumnName(k);

         Vector vector1 = new Vector();
         HashMap hashmap;
         while (update_nums.next()) {
            hashmap = new HashMap();
            for (int l = 0; l < fields_name.length; l++) {
               if (update_nums.getObject(fields_name[l].toLowerCase()) != null) {
                  Object o = update_nums.getObject(fields_name[l].toLowerCase());
                  hashmap.put(fields_name[l].toLowerCase(), o.toString());
               }
            }
            vector1.add(hashmap);
         }
         if (update_nums != null)
            update_nums.close();
         return vector1;
      } catch (SQLException e) {
         throw new FrameException(-22120451, "SystemServ:MSM_045 根据一级区域编码查询一级区域信息时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }
   }

   /*******************************************************************************

    功能: 根据一级区域编码查询地市信息;
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_046
    功能说明:  根据一级区域编码查询地市信息;
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称
    FIELD1  Top_NO        string     3    一级区域编码

    输出参数:
    变量名          类型            长度   名称
    Top_No       string        3     一级区域编码
    TopArea_Name    string        16       一级区域名称
    City_No      string        2     地市编码
    City_Name    string        16       地市名称

    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-05
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public Vector MSM_046(DynamicDict aDict) throws FrameException {

      String sTop_No;
      String sTopArea_Name;
      String sCity_No;
      String sCity_Name;

      String sqlopen = "";
      ResultSet update_nums = null;

      sTop_No = ((String) aDict.getValueByName("Top_No")).trim();
      sTopArea_Name = ((String) aDict.getValueByName("TopArea_Name", false)).trim();
      sCity_No = ((String) aDict.getValueByName("City_No", false)).trim();
      sCity_Name = ((String) aDict.getValueByName("City_Name", false)).trim();

      try {
         sqlopen = "SELECT b.TOP_NO," +
                 "   a.TOPAREA_NAME," +
                 "    b.CITY_NO," +
                 "    b.CITY_NAME " +
                 " FROM TVLSM_TOPAREA_INFO a,TVLSM_CITY_INFO b " +
                 " WHERE   a.TOP_NO=b.TOP_NO " +
                 " AND b.TOP_NO=" + "'" + sTop_No + "'";

         if (__st__ == null)
            __st__ = aDict.GetConnection().createStatement();
         update_nums = __st__.executeQuery(sqlopen);
         if (update_nums == null) {
            throw new FrameException(-22120460, "SystemServ:MSM_046 根据一级区域编码查询一级区域信息失败" + "sql语句是:" + sqlopen);
         }

         ResultSetMetaData rsmd = update_nums.getMetaData();
         String[] fields_name = new String[rsmd.getColumnCount()];
         for (int k = 1; k <= rsmd.getColumnCount(); k++)
            fields_name[k - 1] = rsmd.getColumnName(k);

         Vector vector1 = new Vector();
         HashMap hashmap;
         while (update_nums.next()) {
            hashmap = new HashMap();
            for (int l = 0; l < fields_name.length; l++) {
               if (update_nums.getObject(fields_name[l].toLowerCase()) != null) {
                  Object o = update_nums.getObject(fields_name[l].toLowerCase());
                  hashmap.put(fields_name[l].toLowerCase(), o.toString());
               }
            }
            vector1.add(hashmap);
         }
         if (update_nums != null)
            update_nums.close();
         return vector1;
      } catch (SQLException e) {
         throw new FrameException(-22120461, "SystemServ:MSM_046新增操作日志时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }

   }

   /***********************************************************************************************************
    功能:      删除一级区域信息
    组件类型:  中间层     所属Server名称: SSM
    组件名称:  MSM_052
    功能说明:  删除一级区域信息

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名           类型      长度   名称

    TOP_NO          string     3    地域代码    非空

    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0            删除成功
    -1              删除失败:所传入的参数可能为空
    1            不能删除:节点下有子节点
    作者：  盛正春
    编写日期：   2003-07-01
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MSM_052(DynamicDict aDict) throws FrameException, SQLException {
	   getLogger();
       if (!aDict.getValueByName("TOP_NO").equals("")) {
         String TOP_NO = ((String) aDict.getValueByName("TOP_NO")).trim();

         //查询是否是叶节点
         String sql0 = "select count(*) from TVLSM_CITY_INFO   where TOP_NO in   (select  TOP_NO from TVLSM_TOPAREA_INFO where TOP_NO=" + "'" + TOP_NO + "')";

         String strNum = "";
         __rs__ = __st__.executeQuery(sql0);

         if (__rs__.next()) {
            strNum = __rs__.getString(1).trim();
         }
         //如果是 叶节点,则进行删除操作
         if (strNum.compareTo("0") == 0) {

            String sql1 = "DELETE FROM TVLSM_TOPAREA_INFO WHERE   TOP_NO = " + "'" + TOP_NO + "'";
            __st__.executeUpdate(sql1);
            return 0;
         } else {
        	 m_Logger.info("因此节点下有子节点,故不能删除!");
           // System.out.print("因此节点下有子节点,故不能删除!");
            return 1;
         }
      } else {
         m_Logger.info("MSM_052:删除一级区域信息---所传入的参数可能为空");
         return -1;
      }
   }

   /***********************************************************************************************************
    功能:      删除地市区域信息
    组件类型:  中间层     所属Server名称: SSM
    组件名称:  MSM_053
    功能说明:  删除地市区域信息

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名           类型      长度   名称

    TOP_NO           string        3        地域代码       非空
    CITY_NO       string        2         操作点ID      非空
    CITY_NAME        string        16     操作员ID

    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0            删除成功
    -1              删除失败:所传入的参数可能为空
    1            不能删除:节点下有子节点

    作者：  盛正春
    编写日期：   2003-07-01
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MSM_053(DynamicDict aDict) throws FrameException, SQLException {
      if ((!aDict.getValueByName("TOP_NO").equals("")) || (!aDict.getValueByName("CITY_NO").equals(""))) {
		 getLogger();
         String TOP_NO = ((String) aDict.getValueByName("TOP_NO")).trim();
         String CITY_NO = ((String) aDict.getValueByName("CITY_NO")).trim();
         //查询是否是叶节点
         String sql0 = "select count(*) from TVLSM_BUREAU where CITY_NO in (select CITY_NO from TVLSM_CITY_INFO where CITY_NO=" +
                 "'" + CITY_NO + "')" + " and Top_No=" + "'" + TOP_NO + "'";

         String strNum = "";
         __rs__ = __st__.executeQuery(sql0);

         if (__rs__.next()) {
            strNum = __rs__.getString(1).trim();
         }
         //如果是 叶节点,则进行删除操作
         if (strNum.compareTo("0") == 0) {

            String sql1 = "DELETE   FROM TVLSM_CITY_INFO WHERE TOP_NO = "
                    + "'" + TOP_NO + "'" + " AND CITY_NO =  " + "'" + CITY_NO + "'";

            __st__.executeUpdate(sql1);
            return 0;
         } else {
        	 m_Logger.info("此节点有子节点,不能删除!");
           // System.out.print("此节点有子节点,不能删除!");
            return 1;
         }
      } else {
         m_Logger.info("MSM_053:删除地市区域信息---所传入的参数可能为空");
         return -1;
      }
   }

   /***********************************************************************************************************
    功能:      删除区域信息
    组件类型:  中间层     所属Server名称: SSM
    组件名称:  MSM_054
    功能说明:  删除区域信息

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名           类型      长度   名称

    Bureau_NO       string    14    地域代码    非空


    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0            删除成功
    -1              删除失败:所传入的参数可能为空

    作者：  盛正春
    编写日期：   2003-07-01
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MSM_054(DynamicDict aDict) throws FrameException, SQLException {
	    
       if (!aDict.getValueByName("Bureau_NO").equals("")) {


         String Bureau_NO = ((String) aDict.getValueByName("Bureau_NO")).trim();
         String sql1 = "DELETE FROM TVLSM_BUREAU   WHERE BUREAU_NO   = "
                 + "'" + Bureau_NO + "'";
		 getLogger();
         __st__.executeUpdate(sql1);
         return 0;

      } else {
         m_Logger.info("MSM_054:删除区域信息---所传入的参数可能为空");
         return -1;
      }
   }

   /*******************************************************************************
    功能: 修改、添加或者删除静态参数表信息(tsm_tablem)
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_101
    修改、添加或者删除静态参数表信息(tsm_tablem)
    输入参数:
    格式：    　　　　
    变量名           类型        长度       名称

    Action_Flag         string      1  操作标志   1：修改；2：新增；3：删除
    Subsystem        string      20    模块名        非空
    Table_Ename         string      25    参数表名   非空
    Table_Cname         string      40    参数表描述
    输出参数: 无


    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-07
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public void MSM_101(DynamicDict aDict) throws FrameException {
	   getLogger();
      String sAction_Flag="";
      String sSubsystem="";
      String sTable_Ename="";
      String sTable_Cname="";
      String sstaff_no="";

      String sqlopen = "";
      long update_nums = -1;
      ResultSet retwritelog=null;

      String strbureau="";
      String strsite="";
      String strstaff="";

      sAction_Flag = ((String) aDict.getValueByName("Action_Flag")).trim();
      sSubsystem = ((String) aDict.getValueByName("Subsystem")).trim();
      sTable_Ename = ((String) aDict.getValueByName("Table_Ename")).trim();
      sTable_Cname = ((String) aDict.getValueByName("Table_Cname", false)).trim();
      sstaff_no = ((String) aDict.getValueByName("staff_no", false)).trim();
     m_Logger.debug("msm_101---->>>");
     try{
      String sqllog="select bureau_no,site_no,staff_no from tsm_staff where staff_no='"+sstaff_no+"'";
      m_Logger.debug("sql  "+sqllog);
      if (__st__ == null)
            __st__ = aDict.GetConnection().createStatement();
      retwritelog = __st__.executeQuery(sqllog);
      if (retwritelog == null) {
            throw new FrameException(-22121019, "SystemServ:MSM_101 写日志时取员工信息失败" + "sql语句是:" + sqlopen);
      }
      while(retwritelog.next()) {
            strbureau = retwritelog.getString(1).trim();
            strsite=retwritelog.getString(2).trim();
            strstaff=retwritelog.getString(3).trim();;
      }
      m_Logger.debug("bureau  "+strbureau);
      if (aDict.getValueByName("Action_Flag").equals("1")) {/*修改*/
         try {
            sqlopen = "UPDATE TSM_TABLEM" +
                    "   SET   SM_TABLE_CNAME=" + "'" + sTable_Cname + "'" +
                    "   WHERE SM_SUBSYSTEM =" + "'" + sSubsystem + "'" +
                    "   AND   SM_TABLE_ENAME =" + "'" + sTable_Ename + "'";

            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22121010, "SystemServ:MSM_101 修改静态参数表信息(tsm_tablem)记录失败" + "sql语句是:" + sqlopen);
            }else{
  		writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 修改静态参数表(tsm_tablem)信息成功！",aDict.GetConnection());
            }
         } catch (SQLException e) {
            throw new FrameException(-22121011, "SystemServ:MSM_101 修改静态参数表信息(tsm_tablem)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else if (aDict.getValueByName("Action_Flag").equals("2")) {//新增
         try {
            sqlopen = "INSERT INTO TSM_TABLEM (SM_TABLE_ENAME," +
                    "SM_SUBSYSTEM," +
                    "SM_TABLE_CNAME) " +
                    "VALUES(" + "'" + sTable_Ename + "'," +
                    "'" + sSubsystem + "'," +
                    "'" + sTable_Cname + "')";
            m_Logger.debug("MSM_011:ADD--:sqlopen------->>"+sqlopen);
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22121012, "SystemServ:MSM_101 添加静态参数表信息(tsm_tablem)记录失败" + "sql语句是:" + sqlopen);
            }else{
  		writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 添加静态参数表(tsm_tablem)信息成功！",aDict.GetConnection());
            }
         } catch (SQLException e) {
            throw new FrameException(-22121013, "SystemServ:MSM_101 添加静态参数表信息(tsm_tablem)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else if (aDict.getValueByName("Action_Flag").equals("3")) {//删除
         try {
            String sql1 = "",sql2 = "",sql3 = "",sql4 = "",sql5 = "";

            long update_nums1 = -1,update_nums2 = -1,update_nums3 = -1;

            sql1 = "DELETE FROM TSM_PARAVALUE " +
                    "WHERE SM_TABLE_ENAME =" + "'" + sTable_Ename + "'";

            sql2 = "DELETE FROM TSM_FIELDM " +
                    "WHERE SM_TABLE_ENAME =" + "'" + sTable_Ename + "'";

            sql3 = "DELETE FROM TSM_TABLEM " +
                    "WHERE SM_TABLE_ENAME =" + "'" + sTable_Ename + "'";

            sql4 = "select count(*) from TSM_TABLEM where SM_TABLE_ENAME in (select SM_TABLE_ENAME from TSM_FIELDM" +
                    "  where SM_TABLE_ENAME in (select SM_TABLE_ENAME from TSM_PARAVALUE where SM_TABLE_ENAME=" + "'" + sTable_Ename + "'))";

            sql5 = "select count(*) from TSM_TABLEM where SM_TABLE_ENAME in (select SM_TABLE_ENAME from TSM_FIELDM" +
                    "  where SM_TABLE_ENAME =" + "'" + sTable_Ename + "')";

            ResultSet rst4 = null,rst5 = null;
            String strNum4 = "",strNum5 = "";
            m_Logger.debug("msm_101:delete---->>>sql1:" + sql1 + " sql2: " + sql2 + " sql3: " + sql3);
            rst4 = __st__.executeQuery(sql4);
            if (rst4.next()) {
               strNum4 = rst4.getString(1).trim();
            }

            //不等于零,表示静态表相关连的静态参数表中有数据:删除与静态表相关连的静态参数表
            if (strNum4.compareTo("0") != 0) {

               update_nums1 = __st__.executeUpdate(sql1);

               if (update_nums1 <= 0) {
                  throw new FrameException(-22121014, "SystemServ:MSM_101 删除静态参数表信息(tsm_paravalue)失败" + "sql语句是:" + sqlopen);
               }
               //不等于零,表示静态表相关连的静态字段表中有数据:删除与静态表相关连的静态字段表
            }

            rst5 = __st__.executeQuery(sql5);
            if (rst5.next()) {
               strNum5 = rst5.getString(1).trim();
            }

            if (strNum5.compareTo("0") != 0) {
               update_nums2 = __st__.executeUpdate(sql2);
               update_nums3 = __st__.executeUpdate(sql3);
               if ((update_nums2 <= 0) || (update_nums3 <= 0)) {
                  throw new FrameException(-22121015, "SystemServ:MSM_101 删除静态参数表信息(tsm_fieldm)失败" + "sql语句是:" + sqlopen);
               }
            } else if ((strNum5.compareTo("0") == 0) || (!aDict.getValueByName("Table_Ename").equals(""))) {
               update_nums3 = __st__.executeUpdate(sql3);

               if (update_nums3 <= 0) {
                  throw  new FrameException(-22121016, "SystemServ:MSM_101 删除静态参数表信息(tsm_tablem)失败" + "sql语句是:" + sqlopen);
               }else{
  		  writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 删除静态参数表(tsm_tablem)信息成功！",aDict.GetConnection());
               }
            }
         } catch (SQLException e) {
            throw new FrameException(-22121017, "SystemServ:MSM_101 删除静态参数表时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else {
         m_Logger.info("错误的操作标志信息");
      }
      } catch (SQLException e) {
           throw new FrameException(-22121018, "SystemServ:MSM_101 删除静态参数表时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }
   }

   /*******************************************************************************
    功能: 修改、添加或者删除静态参数表信息(tsm_fieldm)
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_102
    功能说明:  修改、添加或者删除静态参数表信息(tsm_fieldm)
    输入参数:
    格式：    　　　　
    变量名           类型         长度    名称

    Action_Flag         string      1  操作标志   1：修改；2：新增；3：永久删除
    Table_Ename         string      25    表名     非空
    Field_Ename         string      30    字段名      非空
    Field_Cname         string      40    字段名描述
    Notes            string      100      备注说明

    输出参数: 无


    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-07
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MSM_102(DynamicDict aDict) throws FrameException {
	  getLogger(); 
      String sAction_Flag;
      String sTable_Ename;
      String sField_Ename;
      String sField_Cname;
      String sNotes;
      String sstaff_no="";

      String sqlopen = "";
      long update_nums = 0;
      ResultSet retwritelog=null;
      String strbureau="";
      String strsite="";
      String strstaff="";

      sAction_Flag = ((String) aDict.getValueByName("Action_Flag")).trim();
      sTable_Ename = ((String) aDict.getValueByName("Table_Ename")).trim();
      sField_Ename = ((String) aDict.getValueByName("Field_Ename")).trim();
      sField_Cname = ((String) aDict.getValueByName("Field_Cname", false)).trim();
      sNotes = ((String) aDict.getValueByName("Notes", false)).trim();
      sstaff_no = ((String) aDict.getValueByName("staff_no", false)).trim();
      try{
      String sqllog="select bureau_no,site_no,staff_no from tsm_staff where staff_no='"+sstaff_no+"'";
      retwritelog = __st__.executeQuery(sqllog);
      if (retwritelog == null) {
            throw new FrameException(-22121029, "SystemServ:MSM_102 写日志时取员工信息失败" + "sql语句是:" + sqlopen);
      }
      while (retwritelog.next()) {
            strbureau = retwritelog.getString(1).trim();
            strsite=retwritelog.getString(2).trim();
            strstaff=retwritelog.getString(3).trim();;
      }
      m_Logger.debug("msm_102:sqllog-->>"+sqllog);
      if (aDict.getValueByName("Action_Flag").equals("1")) {/*修改*/
         try {
            sqlopen = "UPDATE TSM_FIELDM SET SM_FIELD_CNAME =  " + "'" + sField_Cname + "'," +
                    " SM_NOTES = " + "'" + sNotes + "'" +
                    " WHERE   SM_TABLE_ENAME = " + "'" + sTable_Ename + "'" +
                    " AND SM_FIELD_ENAME = " + "'" + sField_Ename + "'";

            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22121020, "SystemServ:MSM_102 修改静态参数表信息(tsm_fieldm)记录失败" + "sql语句是:" + sqlopen);
            } else {
               writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 修改静态参数表(tsm_fieldm)信息成功！",aDict.GetConnection());
               return 0;
            }
         } catch (SQLException e) {
            throw new FrameException(-22121021, "SystemServ:MSM_102 修改静态参数表信息(tsm_fieldm)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else if (aDict.getValueByName("Action_Flag").equals("2")) {//新增
         try {
            sqlopen = "INSERT INTO  TSM_FIELDM (SM_TABLE_ENAME," +
                    " SM_FIELD_ENAME," +
                    " SM_FIELD_CNAME," +
                    " SM_NOTES )" +
                    " VALUES(" + "'" + sTable_Ename + "'," +
                    "'" + sField_Ename + "'," +
                    "'" + sField_Cname + "'," +
                    "'" + sNotes + "')";
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22121022, "SystemServ:MSM_102 添加静态参数表信息(tsm_fieldm)记录失败" + "sql语句是:" + sqlopen);
            } else {
               writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 添加静态参数表(tsm_fieldm)信息成功！",aDict.GetConnection());
               return 0;
            }
         } catch (SQLException e) {
            throw new FrameException(-22121023, "SystemServ:MSM_102 添加静态参数表信息(tsm_fieldm)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else if (aDict.getValueByName("Action_Flag").equals("3")) {//删除
         try {
            String sql1 = "";
            String sql2 = "";
            String sql3 = "";
            long update_nums1 = -1;
            long update_nums2 = -1;

            sql1 = "DELETE FROM  TSM_PARAVALUE" +
                    " WHERE   SM_TABLE_ENAME =" + "'" + sTable_Ename + "'" +
                    "  AND SM_FIELD_ENAME =" + "'" + sField_Ename + "'";

            sql2 = "DELETE FROM TSM_FIELDM" +
                    " WHERE   SM_TABLE_ENAME =" + "'" + sTable_Ename + "'" +
                    " AND SM_FIELD_ENAME = " + "'" + sField_Ename + "'";

            sql3 = "select count(*) from tsm_fieldm where SM_FIELD_ENAME in(select SM_FIELD_ENAME from tsm_paravalue " +
                    " where SM_FIELD_ENAME=" + "'" + sField_Ename + "')";


            ResultSet rst3 = null;
            String strNum3 = "";
            rst3 = __st__.executeQuery(sql3);
            if (rst3.next()) {
               strNum3 = rst3.getString(1).trim();
            }
            if (strNum3.compareTo("0") != 0) {
               update_nums1 = __st__.executeUpdate(sql1);
               update_nums2 = __st__.executeUpdate(sql2);
            } else {
               update_nums2 = __st__.executeUpdate(sql2);
            }
            if (update_nums2 <= 0) {
               throw new FrameException(-22121024, "SystemServ:MSM_102 删除静态参数表信息(tsm_fieldm)记录失败" + "sql语句是:" + sqlopen);
            } else {
               writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 删除静态参数表(tsm_fieldm)信息成功！",aDict.GetConnection());
               return 0;
            }
         } catch (SQLException e) {
            throw new FrameException(-22121026, "SystemServ:MSM_102 删除静态参数表信息(tsm_fieldm)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else {
         m_Logger.info("错误的操作标志信息");
         return -1;
      }
      } catch (SQLException e) {
            throw new FrameException(-22121027, "SystemServ:MSM_102 操作静态参数表信息(tsm_fieldm)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }
   }

   /*******************************************************************************
    功能: 修改、添加或者删除静态参数表信息(tsm_paravalue)
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_103
    功能说明:  修改、添加或者删除静态参数表信息(tsm_paravalue)
    输入参数:
    格式：    　　　　
    变量名           类型        长度   名称

    Action_Flag         string      1  操作标志    1：修改；2：新增；3：永久删除
    Within_ID     NUMBER            6     序号        非空
    Table_Ename         string      25    表名        非空
    Field_Ename         string      30    字段名         非空
    Used_View        string      40    id使用值    非空
    Disp_View        string      40    显示值         非空
    Order            INTEGER           显示次序    非空
    Notes            string      200      备注

    输出参数: 无


    返回结果：
    RETCODE    long             返回结果

    作者：  盛正春
    编写日期：   2003-07-07
    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MSM_103(DynamicDict aDict) throws FrameException {
	  getLogger();
      String sAction_Flag;
      //String sWithin_ID;
      String sTable_Ename;
      String sField_Ename;
      String sUsed_View;
      String sDisp_View;
      String sOrder;
      String sNotes;

      String sqlopen = "";
      long update_nums = 0;
      String sstaff_no="";
      String strstaff="";
      String strsite="";
      String strbureau="";
      ResultSet retwritelog=null;

      sAction_Flag = ((String) aDict.getValueByName("Action_Flag", false)).trim();
      //sWithin_ID = ((String) aDict.getValueByName("Within_ID")).trim();
      sTable_Ename = ((String) aDict.getValueByName("Table_Ename")).trim();
      sField_Ename = ((String) aDict.getValueByName("Field_Ename")).trim();
      sUsed_View = ((String) aDict.getValueByName("Used_View")).trim();
      sDisp_View = ((String) aDict.getValueByName("Disp_View")).trim();
      sOrder = ((String) aDict.getValueByName("Order")).trim();
      sNotes = ((String) aDict.getValueByName("Notes", false)).trim();
      sstaff_no = ((String) aDict.getValueByName("staff_no", false)).trim();
      try{
      String sqllog="select bureau_no,site_no,staff_no from tsm_staff where staff_no='"+sstaff_no+"'";
      retwritelog = __st__.executeQuery(sqllog);
      if (retwritelog == null) {
            throw new FrameException(-22121039, "SystemServ:MSM_103 写日志时取员工信息失败" + "sql语句是:" + sqlopen);
      }
      while (retwritelog.next()) {
            strbureau = retwritelog.getString(1).trim();
            strsite=retwritelog.getString(2).trim();
            strstaff=retwritelog.getString(3).trim();;
      }

      if (aDict.getValueByName("Action_Flag").equals("1")) {/*修改*/
         try {
            sqlopen = "UPDATE TSM_PARAVALUE SET SM_USED_VIEW =" + "'" + sUsed_View + "'," +
                    " SM_DISP_VIEW =" + "'" + sDisp_View + "'," +
                    " SM_ORDER = to_number(" + "'" + sOrder + "')," +
                    " SM_NOTES =" + "'" + sNotes + "'" +
                    " WHERE   SM_USED_VIEW ='"+sUsed_View +
                    "' and SM_TABLE_ENAME='"+sTable_Ename +
                    "' and SM_FIELD_ENAME='"+sField_Ename+"'";
			//if(DEBUG)logger.info("SystemServ.MSM_103.sqlopen.update="+sqlopen);
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22121030, "SystemServ:MSM_103 修改静态参数表信息(tsm_paravalue)记录失败" + "sql语句是:" + sqlopen);
            } else {
               writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 修改静态参数表信息(tsm_paravalue)成功！",aDict.GetConnection());
               return 0;
            }
         } catch (SQLException e) {
            throw new FrameException(-22121031, "SystemServ:MSM_103 修改静态参数表信息(tsm_paravalue)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else if (aDict.getValueByName("Action_Flag").equals("2")) {//新增
         try {
            String sql = "";
/*
            sql = "SELECT max(to_number(SM_WITHIN_ID))   AS max_SM_WITHIN_ID  FROM TSM_PARAVALUE";

            __rs__ = Dao.select(sql, aDict.GetConnection());
            int SM_WITHIN_ID = 1;
            if (__rs__.next()) {
               SM_WITHIN_ID = __rs__.getInt(1) + 1;
            }
            sWithin_ID = String.valueOf(SM_WITHIN_ID);
*/
            sqlopen = "INSERT INTO  TSM_PARAVALUE (SM_TABLE_ENAME," +
                    " SM_FIELD_ENAME," +
                    " SM_USED_VIEW," +
                    " SM_DISP_VIEW," +
                    " SM_ORDER," +
                    " SM_NOTES )" +
                    " VALUES('" + sTable_Ename +
                    "','" +sField_Ename +
                    "','" + sUsed_View +
                    "','" + sDisp_View +
                    "',"+sOrder+
                    ",'" + sNotes + "')";
			//if(DEBUG)logger.info("SystemServ.MSM_103.sqlopen.insert="+sqlopen);
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22121032, "SystemServ:MSM_103 添加静态参数表信息(tsm_paravalue)记录失败" + "sql语句是:" + sqlopen);
            } else {
               writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 添加静态参数表信息(tsm_paravalue)成功！",aDict.GetConnection());
               return 0;
            }
         } catch (SQLException e) {
            throw new FrameException(-22121033, "SystemServ:MSM_103 添加静态参数表信息(tsm_paravalue)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else if (aDict.getValueByName("Action_Flag").equals("3")) {//删除
         try {
            sqlopen = "DELETE FROM   TSM_PARAVALUE" +
                    " WHERE   SM_USED_VIEW ='"+sUsed_View +
                    "' and SM_TABLE_ENAME='"+sTable_Ename +
                    "' and SM_FIELD_ENAME='"+sField_Ename+"'";
			//if(DEBUG)logger.info("SystemServ.MSM_103.sqlopen.insert="+sqlopen);
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22121034, "SystemServ:MSM_103 删除静态参数表信息(tsm_paravalue)记录失败" + "sql语句是:" + sqlopen);
            } else {
               writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+strstaff+" 删除静态参数表信息(tsm_paravalue)成功！",aDict.GetConnection());
               return 0;
            }
         } catch (SQLException e) {
            throw new FrameException(-22121035, "SystemServ:MSM_103 删除静态参数表信息(tsm_paravalue)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
         }
      } else {
        m_Logger.info("错误的操作标志信息");
         return -1;
      }
      } catch (SQLException e) {
            throw new FrameException(-22121036, "SystemServ:MSM_103 取操作员信息静态参数表信息(tsm_paravalue)时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }
   }

   /******************************************************************************
    功能:       根据表名获取表中的数据
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:  MSM_241
    功能说明:  根据表名获取表中的数据
    输入参数:
    格式：   　　　　
    变量名              类型      长度     名称

    TAB_ENAME        string    100  表名(英文名称)


    输出参数:
    变量名              类型      长度     名称

    返回结果：
    RETCODE    long               返回结果

    作者： 盛正春
    编写日期：   2003-07-10
    修改者                         修改日期                     修改内容
    要形成的sql如下：
    SELECT b.name||'['||a.TOP_no||']',a.* 
    FROM tvlsm_city_info a,(SELECT top_no code,toparea_name name FROM tvlsm_toparea_info) b
    WHERE a.top_no=b.code
    ************************************************************************************/
   public void MSM_241(DynamicDict aDict) throws FrameException, SQLException {
      String sTAB_ENAME = (String) aDict.getValueByName("TAB_ENAME");
	  ResultSet rs_temp=null;
	  ResultSet rs_temp2=null;
	  String str_colname="";
	  String str_col="";
	  String str_fromtable=" from "+sTAB_ENAME +" a";
	  String str_where=" where 1=1";
	  String str_orderby="noneseq";
	  Statement __st2__		= null;
	  
	  char ch_table_alias='b';
	  
	  
      //取需要转换成名称的字段
      sql="SELECT  col_ename,data_source_serv  FROM TVLCM_TAB_INFO WHERE tab_ename='"+sTAB_ENAME+"' AND col_control_type='1'";
      try {
      strtest="1"+sql;
      rs_temp=__st__.executeQuery(sql);
      if (rs_temp != null) {
         while (rs_temp.next()){
         	str_col=rs_temp.getString(1);
         	str_colname  +=ch_table_alias+".name||'['||"+ch_table_alias+".code||']' "+str_col+",";//示例：b.name||'['||b.code||']' TOP_no,
            sql="select action_clause from tfm_action_list where action_id='"+rs_temp.getString(2)+"'";
            strtest=sql;
            

            //根据action_id取sql
            __st2__ 	= __cn__.createStatement();
            rs_temp2=__st2__.executeQuery(sql);
            if (rs_temp2 != null) {
            	while (rs_temp2.next()){
            		str_fromtable+=",("+rs_temp2.getString(1)+") "+ch_table_alias;	
            		str_where+=" and a."+str_col+"="+ch_table_alias+".code(+)";
            	}
            }
            __st2__.close();
            rs_temp2.close();
            ch_table_alias++;
         }            
      }
    }catch (SQLException e) {
				if (rs_temp != null)  rs_temp.close();         	
				if (rs_temp2!= null)  rs_temp2.close();         	
				if (__st2__ != null) __st2__.close();    		
               throw new FrameException(-22122423, "SystemServ:MSM_241 1根据表名取数据时出现异常\n  ,错误信息:"+strtest + e.getMessage(), e.getMessage());
    }   
     
      
      //根据表名从TVLCM_TAB_INFO取排序字段
      sql="select nvl(Pkg_Bfibss_Sr_Utl.fun_revert('SELECT ''a.''||col_ename FROM TVLCM_TAB_INFO where tab_ename=''"+sTAB_ENAME+"'' and col_orderby_seq>0 order by col_orderby_seq',','),'noneseq') str from dual";
      strtest="3"+sql;
      try {
      rs_temp=__st__.executeQuery(sql);
      if (rs_temp != null) {
         while (rs_temp.next()){
         	if(rs_temp.getString(1)!="noneseq") 
            str_orderby=rs_temp.getString(1);
         }            
      }
      sql="select "+str_colname+"a.* "+str_fromtable+str_where;
      if (!str_orderby.equals("noneseq"))
         sql =sql+" order by "+str_orderby;
      strtest="4"+sql;
      //writeStaffLog("1","1","MSM241","12",strtest,aDict.GetConnection());
      __rs__ = Dao.select(sql, aDict.GetConnection());
      aDict.setValueByName("table_list", __rs__);
      rs_temp.close();
    }catch (SQLException e) {
    	       if (rs_temp != null)  rs_temp.close();   
               throw new FrameException(-22122423, "SystemServ:MSM_241 2根据表名取数据时出现异常\n  ,错误信息:"+sql + e.getMessage(), e.getMessage());
            }
   }

   /******************************************************************************
    功能:       根据表名修改、添加或者删除数据
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:  MSM_242
    功能说明:  根据表名修改、添加或者删除数据
    输入参数:
    格式：   　　　　
    变量名              类型      长度     名称
    Action_Flag      string    1       操作标志         1：修改；2：新增；3：删除
    TAB_ENAME        string    100  表名(英文名称)

    输出参数:
    变量名              类型      长度     名称

    返回结果：
    RETCODE    long               返回结果

    作者： 盛正春
    编写日期：   2003-07-11
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public void MSM_242(DynamicDict aDict) throws FrameException {

      String sAction_Flag;
      String sTAB_ENAME;
      String sstaff_no;
      String sargs;

      String sqlopen = "";
      String sql = "";
      ResultSet update_nums = null;
      ResultSet retwritelog=null;
      long update_num = 0;
      String sfieldname = "";
      String sqladd = "";

      String strbureau="";
      String strsite="";
      String strstaff="";
      try {
         sAction_Flag = ((String) aDict.getValueByName("Action_Flag")).trim();
         sTAB_ENAME = ((String) aDict.getValueByName("TABLE_ENAME")).trim();
         sstaff_no = ((String) aDict.getValueByName("staff_no",false)).trim();
         sql="select bureau_no,site_no,staff_no from tsm_staff where staff_no='"+sstaff_no+"'";
        
         if (__st__ == null)
            __st__ = aDict.GetConnection().createStatement();
         retwritelog=__st__.executeQuery(sql);
         if (retwritelog == null) {
            throw new FrameException(-22122429, "SystemServ:MSM_242 根据表名取字段失败" + "sql语句是:" + sql);
         }
         while (retwritelog.next()){
            strbureau=(retwritelog.getString(1)).trim();
            strsite=(retwritelog.getString(2)).trim();
            strstaff=(retwritelog.getString(3)).trim();
         }
         writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+sstaff_no+","+sqlopen+"  1修改静态数据成功！",aDict.GetConnection());
         sqlopen = "select col_ename  from TVLCM_TAB_INFO where tab_ename=" + "'" + sTAB_ENAME + "'";
         update_nums = __st__.executeQuery(sqlopen);
         if (update_nums == null) {
            throw new FrameException(-22122420, "SystemServ:MSM_242 根据表名取字段失败" + "sql语句是:" + sqlopen);
         }
         ResultSetMetaData rsmd = update_nums.getMetaData();
         String[] fields_name = new String[rsmd.getColumnCount()];
         String fieldname = "";
         if (aDict.getValueByName("Action_Flag").equals("1")) {/*修改*/
            try {
               HashMap Hupdate =new HashMap();
               String sqladd1="";
               sargs = ((String) aDict.getValueByName("args")).trim();
               while(update_nums.next()){
                  if (!aDict.getValueByName(update_nums.getString(1)).equals(""))
                     sqladd1 =update_nums.getString(1) + "= '" + ((String) aDict.getValueByName(update_nums.getString(1))).trim()+"'  ,";
                     sqladd=sqladd+sqladd1;
               }
               sqladd = sqladd.substring(0, sqladd.length() - 2);
               sargs = sargs.substring(0, sargs.length() - 1);
               String[] tt =Utility.split(sargs,",");
               String sname="";
             for(int i=0; i<tt.length; i++){
                  int pos =tt[i].indexOf("=");
                  String ename=tt[i].substring(0, pos);
                  String cname=tt[i].substring(pos+1).trim();
                  String stemp="";
                  if (!cname.equals("")){
                  	 if (cname.indexOf("[")>=0)cname=cname.substring(cname.indexOf("[")+1,cname.indexOf("]"));
                     stemp=ename+" = '"+cname+"'  and ";
                     sname=sname+stemp;
                 }
               }
               sname = sname.substring(0, sname.length() - 4);
               if (sqladd != "") {
                  sqlopen = "UPDATE " + sTAB_ENAME + " set " + sqladd + " where " + sname;
                  update_num = __st__.executeUpdate(sqlopen);
                  if (update_num <= 0) {
                     throw new FrameException(-22122421, "SystemServ:MSM_242 修改数据记录失败" + "sql语句是:" + sqlopen+"sargs="+sargs);
                  }else{
                  if (!strstaff.equals(""))
                   writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+sstaff_no+"  ("+sname+") 修改静态数据成功！",aDict.GetConnection());
                  }
               }
            } catch (SQLException e) {
               throw new FrameException(-22121031, "SystemServ:MSM_242 修改数据时出现异常\n  ,错误信息是:" + e.getMessage(), e.getMessage());
            }
         } else if (aDict.getValueByName("Action_Flag").equals("2")) {/*增加*/
            try {
               HashMap insert =new HashMap();
               m_Logger.debug(aDict.m_Values);

               while(update_nums.next()){
                  if (!aDict.getValueByName(update_nums.getString(1), false).equals("")){
                     strtest=strtest+","+update_nums.getString(1);
                     insert.put(update_nums.getString(1), aDict.getValueByName(update_nums.getString(1)));
                  }
               }
               if(insert !=null && insert.size()>0){
                  Dao.insert(sTAB_ENAME, insert, aDict.GetConnection());

                  if (!strstaff.equals(""))
                        writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+sstaff_no+" 增加静态数据成功！",aDict.GetConnection());
		if(bMCHinterface){
	                  if(sTAB_ENAME.equalsIgnoreCase("TVLPM_ACCT_ITEM_TYPE")){
	                     DynamicDict dict =new DynamicDict();
	                     dict.m_ActionId ="MCH_504";
	                     dict.SetConnection(this.__cn__);
	                     dict.setValueByName("acct_item_type", aDict.getValueByName("acct_item_type"));
	                     dict.setValueByName("acct_item_name", aDict.getValueByName("acct_item_name"));
	                     SysSet.callService(dict);
	                  }
	                  if(sTAB_ENAME.equalsIgnoreCase("TVLCM_BANK")){
	                     DynamicDict dict =new DynamicDict();
	                     dict.m_ActionId ="MCH_505";
	                     dict.SetConnection(this.__cn__);
	                     dict.setValueByName("bank_no", aDict.getValueByName("bank_no"));
	                     dict.setValueByName("bank_name", aDict.getValueByName("bank_name"));
	                     SysSet.callService(dict);
	                  }
	                  if(sTAB_ENAME.equalsIgnoreCase("TCC_APL_ADV_TYPE")){
	                     DynamicDict dict =new DynamicDict();
	                     dict.m_ActionId ="MCH_509";
	                     dict.SetConnection(this.__cn__);
	                     dict.setValueByName("adv_type", aDict.getValueByName("type_code"));
	                     dict.setValueByName("adv_name", aDict.getValueByName("type_name"));
	                     SysSet.callService(dict);
	                  }
                 }
               }
            } catch (SQLException e) {
                throw new FrameException(-22122423, "SystemServ:MSM_242 添加数据时出现异常\n ,错误信息是:"+ e.getMessage(), e.getMessage());
            }
         } else if (aDict.getValueByName("Action_Flag").equals("3")) {//删除
            try {
               String sqladd1="";
               while(update_nums.next()){
                  if (!aDict.getValueByName(update_nums.getString(1)).equals("")){
                     sqladd1 = update_nums.getString(1) + "= '" + ((String) aDict.getValueByName(update_nums.getString(1))).trim()+"'  and ";
                     sqladd=sqladd+sqladd1;
                  }
               }
               sqladd = sqladd.substring(0, sqladd.length() - 4);
               if (sqladd != "") {
                  sqlopen = "delete from " + sTAB_ENAME + " where " + sqladd;
                  update_num = __st__.executeUpdate(sqlopen);
                  if (update_num <= 0) {
                     throw new FrameException(-22122424, "SystemServ:MSM_103 删除数据记录失败" + "sql语句是:" + sqlopen);
                  }else{
                  if (!strstaff.equals(""))
                      writeStaffLog(strbureau,strsite,strstaff,"12","员工号："+sstaff_no+" 删除静态数据成功！",aDict.GetConnection());
                  }
               }
            } catch (SQLException e) {
               throw new FrameException(-22122425, "SystemServ:MSM_242 删除数据时出现异常\n  ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
            }
         } else {
            m_Logger.info("错误的操作标志信息");

         }
      } catch (SQLException e) {
         throw new FrameException(-22122426, "SystemServ:MSM_242 删除数据时出现异常\n  ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }

   }

   /************************************************************************************

    功能:       将消息状态置为零（已读）
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:   MSM_243
    功能说明:   将消息状态置为零（已读）
    输入参数:
    格式：   　　　　
    变量名              类型        长度     名称

    MESSAGEID        string     16  消息ID


    输出参数:
    变量名              类型        长度     名称

    返回结果：
    RETCODE    long               返回结果

    作者： 盛正春
    编写日期：   2003-07-10
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public void MSM_243(DynamicDict aDict) throws FrameException, SQLException {
      String sMESSAGEID = "";
      String sql1 = "";
      long update_num = 0;
      sMESSAGEID = ((String) aDict.getValueByName("MESSAGEID", false)).trim();
      try {
         sql1 = "UPDATE  tsm_message SET state='0' WHERE  MESSAGEID='" + sMESSAGEID + "'";
         update_num = __st__.executeUpdate(sql1);
         if (update_num <= 0)
            throw new FrameException(-22122430, "SystemServ:MSM_243 将消息状态置为零（已读）记录失败sql语句是:" + sql1);
      } catch (SQLException e) {
         throw new FrameException(-22122431, "SystemServ:MSM_243 将消息状态置为零（已读）时出现异常\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sql1, e.getMessage());
      }
   }

   /************************************************************************************
    功能:       向消息表中写消息（指定操作点下的操作员）
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:   MSM_203
    功能说明:   向消息表中写消息（指定操作点下的操作员）
    输入参数:
    格式：   　　　　
    变量名              类型           长度     名称
    Bureau_NO        string         14    接收人员地域ID(BUREAU_NO）
    Site-NO          string         6     接收人员操作点
    System_Ename     string         16    发送子系统名称
    SendBureau_NO    string         14    发送人员地域ID
    Mess_Give_Time   string         20    消息产生时间
    SendMsg          string         900   发送消息
    Urge_Type        string         1     重要程度      1:至关重要，2：较重要，3：普通

    输出参数:
    变量名              类型        长度     名称

    返回结果：
    RETCODE    long               返回结果

    作者： 盛正春
    编写日期：   2003-07-17
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public void MSM_203(DynamicDict aDict) throws FrameException, SQLException {
	   getLogger();
      String sBureau_NO = "";
      String sSite_NO = "";
      String sSystem_Ename = "";
      String sSendBureau_NO = "";
      String sMess_Give_Time = "";
      String sSendMsg = "";
      String sUrge_Type = "";

      String sql1 = "";
      long update_num = 0;
      try {
         //sBureau_NO = ((String) aDict.getValueByName("Bureau_NO")).trim();
         sSite_NO = ((String) aDict.getValueByName("Site-NO")).trim();
         sSystem_Ename = ((String) aDict.getValueByName("System_Ename")).trim();
         sSendBureau_NO = ((String) aDict.getValueByName("SendBureau_NO")).trim();
         sMess_Give_Time = ((String) aDict.getValueByName("Mess_Give_Time")).trim();
         sSendMsg = ((String) aDict.getValueByName("SendMsg")).trim();
         sUrge_Type = ((String) aDict.getValueByName("Urge_Type")).trim();

         sql1 = "INSERT INTO TSM_MESSAGE(MESSAGEID,  STAFF_NO,  BUREAU_NO,  SITE_NO, " +
                 " SYSTEM_ENAME,  SENDBUREAU_NO,  MESS_GIVE_TIME,  SENDMESSAGE,  URGE_TYPE, " +
                 " STATE )  SELECT  LTRIM(TO_CHAR(S_SM_TSMMESSAGE.NEXTVAL,'0000000000000000'))," +
                 "  STAFF_NO,  BUREAU_NO,  SITE_NO,'" + sSystem_Ename + "', " + "'" + sSendBureau_NO + "'," +
                 "  TO_DATE(" + "'" + sMess_Give_Time + "'," + " 'YYYY-MM-DD HH24:MI:SS'), " + "'" + sSendMsg +
                 "', " + "'" + sUrge_Type + "', " + "  '1' " + "  FROM TSM_STAFF " +
                 "  WHERE SITE_NO = " + "'" + sSite_NO + "'";
         m_Logger.debug("sql1:msm_203--->>" + sql1);
         update_num = __st__.executeUpdate(sql1);
         if (update_num <= 0)
            throw new FrameException(-22122030, "SystemServ:MSM_203 向消息表中写消息（指定操作点下的操作员记录失败sql语句是:" + sql1);
      } catch (SQLException e) {
         throw new FrameException(-22122031, "SystemServ:MSM_203 向消息表中写消息（指定操作点下的操作员）时出现异常\n  ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sql1, e.getMessage());
      }
   }

   /************************************************************************************
    功能:       向消息表中写消息（指定操作员）
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:   MSM_204
    功能说明:   向消息表中写消息（指定操作员）
    输入参数:
    格式：   　　　　
    变量名              类型           长度     名称
    Staff-NO         string         6     人员编号
    Bureau_NO        string         14    接收人员地域ID(BUREAU_NO）
    Site-NO          string         6     接收人员操作点
    System_Ename     string         16    发送子系统名称
    SendBureau_NO    string         14    发送人员地域ID
    Mess_Give_Time   string         20    消息产生时间
    SendMsg          string         900   发送消息
    Urge_Type        string         1     重要程度      1:至关重要，2：较重要，3：普通

    输出参数:
    变量名              类型        长度     名称

    返回结果：
    RETCODE    long               返回结果

    作者： 盛正春
    编写日期：   2003-07-17
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public void MSM_204(DynamicDict aDict) throws FrameException, SQLException {
      String sStaff_NO = "";
      String sBureau_NO = "";
      String sSite_NO = "";
      String sSystem_Ename = "";
      String sSendBureau_NO = "";
      String sMess_Give_Time = "";
      String sSendMsg = "";
      String sUrge_Type = "";

      String sql1 = "";
      long update_num = 0;
      try {
         sStaff_NO = ((String) aDict.getValueByName("Staff-NO", false)).trim();
         sBureau_NO = ((String) aDict.getValueByName("Bureau_NO", false)).trim();
         sSite_NO = ((String) aDict.getValueByName("Site-NO", false)).trim();
         sSystem_Ename = ((String) aDict.getValueByName("System_Ename")).trim();
         sSendBureau_NO = ((String) aDict.getValueByName("SendBureau_NO")).trim();
         sMess_Give_Time = ((String) aDict.getValueByName("Mess_Give_Time")).trim();
         sSendMsg = ((String) aDict.getValueByName("SendMsg")).trim();
         sUrge_Type = ((String) aDict.getValueByName("Urge_Type")).trim();

         sql1 = "INSERT INTO TSM_MESSAGE(MESSAGEID,  STAFF_NO,  BUREAU_NO,  SITE_NO, " +
                 " SYSTEM_ENAME,  SENDBUREAU_NO,  MESS_GIVE_TIME,  SENDMESSAGE,  URGE_TYPE," +
                 " STATE )   VALUES(  LTRIM(TO_CHAR(S_SM_TSMMESSAGE.NEXTVAL,'0000000000000000')),'" +
                 "'" + sStaff_NO + "'," + "'" + sBureau_NO + "'," + "'" + sSite_NO + "'," + "'" + sSystem_Ename +
                 "'," + "'" + sSendBureau_NO + "'," + " TO_DATE(" + "'" + sMess_Give_Time + "'," +
                 " 'YYYY-MM-DD HH24:MI:SS')," + "'" + sSendMsg + "'," + "'" +
                 sUrge_Type + "'," + "  '1' )";
         //logger.info("sql:msm_204--->>" + sql1);
         update_num = __st__.executeUpdate(sql1);
         if (update_num <= 0)
            throw new FrameException(-22122040, "SystemServ:MSM_204 向消息表中写消息(指定操作员）记录失败sql语句是:" + sql1);
      } catch (SQLException e) {
         throw new FrameException(-22122041, "SystemServ:MSM_204 向消息表中写消息(指定操作员）时出现异常\n  ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sql1, e.getMessage());
      }
   }

   /************************************************************************************
    功能:       根据定单查询其关注人
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:   MSM_175
    功能说明:   根据定单查询其关注人
    输入参数:
    格式：   　　　　
    变量名              类型           长度     名称
    Ord_No          STRING         16      定单号

    输出参数:
    变量名           类型        长度   名称

    Bureau_NO       STRING         16     区域编码
    Site-NO         STRING         6      操作点编号
    Staff-NO        STRING         6      操作人编号

    返回结果：
    RETCODE    long               返回结果

    作者： 盛正春
    编写日期：   2003-07-17
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public static ArrayList MSM_175(String ord_no, Connection connection)
           throws FrameException {
      ArrayList arraylist = null;
      Statement statement = null;
      ResultSet resultset = null;
      try {
         statement = connection.createStatement();
         String sql1 = "SELECT bureau_no,site_no,staff_no  FROM TSM_STAFF_TITLE " +
                 " WHERE TITLE_ID='13'  UNION  SELECT bureau_no,site_no,staff_no " +
                 "  FROM TSM_STAFF  WHERE STAFF_NO IN ( SELECT DISTINCT staff_no  " +
                 " FROM TCM_USER  WHERE STATE = '1'  AND ORD_NO = '" + ord_no + "')";
         if (arraylist == null)
            arraylist = new ArrayList();

         HashMap hashmap;
         for (resultset = statement.executeQuery(sql1); resultset.next(); arraylist.add(hashmap)) {
            hashmap = new HashMap();
            if (resultset.getString("bureau_no") != null && resultset.getString("site_no") != null && resultset.getString("staff_no") != null) {
               hashmap.put("bureau_no", resultset.getString("bureau_no"));
               hashmap.put("site_no", resultset.getString("site_no"));
               hashmap.put("staff_no", resultset.getString("staff_no"));
            }
         }
      } catch (SQLException sqlexception) {
         throw new FrameException(-22121750, "根据定单查询其关注人时，出现异常！", sqlexception.getMessage());
      } finally {
         try {
            if (resultset != null)
               resultset.close();
            if (statement != null)
               statement.close();
         } catch (SQLException sqlexception1) {
            throw new FrameException(-22121751, "SystemServ.msm_175 根据定单查询其关注人时出现SQL异常", sqlexception1.getMessage());
         }
         return arraylist;
      }
   }

   /************************************************************************************
    功能:       登录员工拥有的操作点
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:   MSM_207
    功能说明:   登录员工拥有的操作点
    输入参数:
    格式：   　　　　
    变量名              类型         长度     名称

    STAFF_NO        string         6     接收人员ID
    SITE_NO        string         6     接收人员操作点
    BUREAU_NO       string         14    接收人员地域ID(BUREAU_NO）
    输出参数:
    类型        返回值
    arraylist     site(site_no,site_name);



    作者： 盛正春
    编写日期：   2003-07-17
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public static ArrayList MSM_207(String staff_no, String site_no, String bureau_no, Connection connection)
           throws FrameException {
	  
      ArrayList arraylist = null;
      Statement statement = null;
      ResultSet resultset = null;
      try {
         statement = connection.createStatement();
         String sql1 = "select auth_level from tsm_staff where staff_no='" + staff_no + "'";
         ResultSet resultset1;
         for (resultset = statement.executeQuery(sql1); resultset.next(); resultset1.close()) {
            if (arraylist == null)
               arraylist = new ArrayList();
            int i = resultset.getInt("auth_level");
            //logger.info("auth_level-msm_207-->>" + i);
            String s4;
            switch (i) {
               case 9: // '\t'
                  s4 = "select site_no, site_name from tsm_site where bureau_no='" + bureau_no + "'";
                  break;

               case 8: // '\007'
                  s4 = "select site_no, site_name from tsm_site where bureau_no='" + bureau_no + "'";
                  break;
               case 7: // '\007'
                  s4 = "select * from tsm_site where bureau_no='" + bureau_no + "'" + " start with up_site_no=" + "'" + site_no + "'" + " connect by up_site_no=prior site_no "+
                  " union select * from tsm_site where bureau_no='" + bureau_no + "'" + " and site_no='"+site_no+"'";
                  break;

               case 4: // '\004'
                  s4 = "select site_no, site_name from tsm_site where site_no= '" + site_no + "'" + " and bureau_no=" + "'" + bureau_no + "'";
                  break;

               default:
                  s4 = "select site_no, site_name from tsm_site where site_no= '" + site_no + "'" + " and bureau_no=" + "'" + bureau_no + "'";
                  break;
            }
            //logger.info("sql-msm_207-->>" + s4);
            HashMap hashmap;
            for (resultset1 = statement.executeQuery(s4); resultset1.next(); arraylist.add(hashmap)) {
               hashmap = new HashMap();
               if (resultset1.getString("site_no") != null && resultset1.getString("site_name") != null)
                  hashmap.put("site_no", resultset1.getString("site_no"));
               hashmap.put("site_name", resultset1.getString("site_name"));
            }

         }

      } catch (SQLException sqlexception) {
         throw new FrameException(-22122070, "得到员工所能看到的操作点时，出现异常！", sqlexception.getMessage());
      } finally {
         try {
            if (resultset != null)
               resultset.close();
            if (statement != null)
               statement.close();
         } catch (SQLException sqlexception1) {
            throw new FrameException(-22122071, "SystemServ.msm_207 方法释放资源时出现SQL异常", sqlexception1.getMessage());
         }
         return arraylist;
      }
   }

   /************************************************************************************

    功能:       登录员工拥有的操作员
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:   MSM_208
    功能说明:   登录员工拥有的操作员
    输入参数:
    格式：   　　　　
    变量名              类型         长度     名称
    BUREAU_NO       string         14
    STAFF_NO        string         6     接收人员ID
    SITE_NO         string         6     接收人员操作点
    输出参数:
    类型        返回值
    arraylist    staff(staff_no,staff_name);

    作者： 盛正春
    编写日期：   2003-07-17
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public static ArrayList MSM_208(String staff_no, String site_no, String bureau_no, Connection connection) throws FrameException {
      ArrayList arraylist = null;
      Statement statement = null;
      ResultSet resultset = null;
      try {
         statement = connection.createStatement();
         String sql1 = "select auth_level from tsm_staff where staff_no='" + staff_no + "'";
         ResultSet resultset1;
         for (resultset = statement.executeQuery(sql1); resultset.next(); resultset1.close()) {
            if (arraylist == null)
               arraylist = new ArrayList();
            int i = resultset.getInt("auth_level");

            String s4;
            switch (i) {
               case 9: // '\t'
                  s4 = "select staff_no, staff_name from tsm_staff where bureau_no='" + bureau_no + "'" + " and site_no=" + "'" + site_no + "'";
                  break;

               case 8: // '\007'
                  s4 = "select staff_no, staff_name from tsm_staff where bureau_no='" + bureau_no + "'" + " and site_no=" + "'" + site_no + "'";
                  break;
               case 7: // '\007'
                  s4 = "select staff_no, staff_name from tsm_staff where bureau_no='" + bureau_no + "'" + " and site_no=" + "'" + site_no + "'";
                  break;

               case 4: // '\004'
                  s4 = "select staff_no, staff_name from tsm_staff where bureau_no='" + bureau_no + "'" + " and site_no=" + "'" + site_no + "'";
                  break;

               default:
                  s4 = "select staff_no, staff_name from tsm_staff where bureau_no='" + bureau_no + "'" + " and site_no=" + "'" + site_no + "'" + " and staff_no=" + "'" + staff_no + "'";
                  break;
            }
            //logger.info("sql-msm_208-->>" + s4);
            HashMap hashmap;
            for (resultset1 = statement.executeQuery(s4); resultset1.next(); arraylist.add(hashmap)) {
               hashmap = new HashMap();
               if (resultset1.getString("staff_no") != null && resultset1.getString("staff_name") != null)
                  hashmap.put("staff_no", resultset1.getString("staff_no"));
               hashmap.put("staff_name", resultset1.getString("staff_name"));
            }

         }

      } catch (SQLException sqlexception) {
         throw new FrameException(-22122080, "得到员工所能看到的操作员时，出现异常！", sqlexception.getMessage());
      } finally {
         try {
            if (resultset != null)
               resultset.close();
            if (statement != null)
               statement.close();
         } catch (SQLException sqlexception1) {
            throw new FrameException(-22122081, "SystemServ.msm_208 方法释放资源时出现SQL异常", sqlexception1.getMessage());
         }
         return arraylist;
      }
   }

   /******************************************************************************************
    组件类型:  中间层     所属Server名称: SSM
    模块名称:  MSM_216
    功能说明:  发送消息

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名      类型     长度   名称
    SENDSITE_NO   string         6     发送人员操作点
    SENDBUREAU_NO string         14    发送人员地域ID
    SYSTEM_ENAME  string         14    （其它子系统发送消息使用）
    URGE_TYPE     string         1     重要程度
    SENDMESSAGE   string         900      内容
    SENDSTAFF_NO    string             6       发送人员ID
    SENDSTAFFS      string                     (传入的区域，操作点，操作员）

    输出参数类型：
    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0:              成功；
    -22120008:   新增操作日志失败;
    -22120009:      新增操作日志时出现异常;
    作者：  盛正春
    编写日期：   2003-07-23
    修改者                    修改日期              修改内容
    ******************************************************************************************/
/*
   public long MSM_216(DynamicDict aDict) throws FrameException {
      String sSENDSITE_NO = "";
      String sSENDBUREAU_NO = "";
      String sURGE_TYPE = "";
      String sSENDMESSAGE = "";
      String sSENDSTAFF_NO = "";
      String sSENDSTAFFS = "";
	  String sMSG_BEGTIME ="";
	  String sMSG_ENDTIME ="";
      String sqlopen = "";
      long update_nums = 0;
      ResultSet rsTmp = null;
      try {

         sSENDSITE_NO = ((String) aDict.getValueByName("SENDSITE_NO")).trim();
         sSENDBUREAU_NO = ((String) aDict.getValueByName("SENDBUREAU_NO", false)).trim();
         sURGE_TYPE = ((String) aDict.getValueByName("URGE_TYPE", false)).trim();
         sSENDMESSAGE = ((String) aDict.getValueByName("SENDMESSAGE", false)).trim();
         sSENDSTAFF_NO = ((String) aDict.getValueByName("SENDSTAFF_NO")).trim();
         sSENDSTAFFS = ((String) aDict.getValueByName("SENDSTAFFS", false)).trim();
         sSENDSTAFF_NO = ((String) aDict.getValueByName("SENDSTAFF_NO")).trim();
         sSENDSTAFFS = ((String) aDict.getValueByName("SENDSTAFFS", false)).trim();
         sMSG_BEGTIME = ((String) aDict.getValueByName("MSG_BEGTIME")).trim();
         sMSG_ENDTIME = ((String) aDict.getValueByName("MSG_ENDTIME", false)).trim();

         if (!sSENDSTAFFS.equals("")) {
            Vector vSQL = new Vector();
            String[] staffs = Utility.split(sSENDSTAFFS, ",");
            for (int i = 0; i < staffs.length; i++) {
               String sql1 = "select bureau_no,site_no,staff_no from tsm_staff where staff_no=" + "'" + staffs[i] + "'";
               rsTmp = Dao.select(sql1, aDict.GetConnection());
               if (!(rsTmp == null)) {
                  while (rsTmp.next()) {
                     String staff_no;
                     String site_no;
                     String bureau_no;
                     staff_no = rsTmp.getString("staff_no").trim();
                     site_no = rsTmp.getString("site_no").trim();
                     bureau_no = rsTmp.getString("bureau_no").trim();
                     if ((!staff_no.equals("")) && (!site_no.equals("")) && (!bureau_no.equals(""))) {
                        sqlopen = "INSERT INTO TSM_MESSAGE(MESSAGEID, " +
                                "  STAFF_NO, " +
                                "  BUREAU_NO, " +
                                "  SITE_NO, " +
                                "  SENDSTAFF_NO, " +
                                "  SENDBUREAU_NO, " +
                                "  SENDSITE_NO, " +
                                "  SENDTIME, " +
                                "  SENDMESSAGE, " +
                                "  URGE_TYPE, " +
                                "  STATE,MSG_BEGTIME, MSG_ENDTIME ) " +
                                "  VALUES (LTRIM(TO_CHAR(S_SM_TSMMESSAGE.NEXTVAL,'0000000000000000')), " +
                                "'" + staff_no + "'," +
                                "'" + bureau_no + "'," +
                                "'" + site_no + "'," +
                                "'" + sSENDSTAFF_NO + "'," +
                                "'" + sSENDBUREAU_NO + "'," +
                                "'" + sSENDSITE_NO + "'," +
                                " TO_CHAR(   SYSDATE, 'YYYY-MM-DD HH24:MI:SS' )," +
                                "'" + sSENDMESSAGE + "'," +
                                "'" + sURGE_TYPE + "'," +
                                " '1',to_date('"+sMSG_BEGTIME+"','yyyy-mm-dd'),"+
                                "to_date('"+sMSG_ENDTIME+"','yyyy-mm-dd'))";
                        logger.info("sqlopen:msm_216-->>" + sqlopen);
                        update_nums = __st__.executeUpdate(sqlopen);
                        if (update_nums <= 0) {
                           throw new FrameException(-22122161, "SystemServ:MSM_216 发送消息记录失败" + "sql语句是:" + sqlopen);
                        }
                     }
                  }
               }
            }
         }

      } catch (SQLException e) {
         throw new FrameException(-22122162, "SystemServ:MSM_216 发送消息时出现异常\n ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }
      return 0;
   }
*/
	/***********************************************************************************
    组件名称:   MSM_217
    功能说明:   员工拥有的组织机构
    输入参数:
			    变量名		类型	长度	名称
			    bureau_no	string	14		当前用户区域ID
			    auth_level	string	1		当前用户级别
			    site_no		string	6     	当前用户操作点
    输出参数:
			    类型        返回值
			    ArrayList	MSM_217

    作者:		盛正春
    编写日期:	2003-07-27
    修改者		修改日期	修改内容
    ************************************************************************************
    李湘麒		2004-6-15	更改区域名称显示方式
    李湘麒		2004-6-8	重新设定各级别拥有的组织机构
    */
	public void MSM_217(DynamicDict aDict) throws FrameException {
		try {
		    String staff_no		= (String)aDict.getValueByName("staff_no",false);					
		    String site_no		= (String)aDict.getValueByName("site_no",false);					
			String bureau_no	= (String)aDict.getValueByName("bureau_no",false);
			String auth_level	= (String)aDict.getValueByName("auth_level",false);
			
		
			switch (Integer.parseInt(auth_level)){
				case 9:
					sql = 	"SELECT bureau_no, bureau_no AS id, '['||bureau_no||']'||bureau_name AS name, NVL(up_bureau_no, '0') AS up_id,'0' AS flag "+
							"  FROM tvlsm_bureau "+
							" UNION "+
							"SELECT bureau_no, site_no, '['||site_no||']'||site_name, DECODE(up_site_no,'000',bureau_no,up_site_no) AS up_site_no, '1' AS flag "+
							"  FROM tsm_site";
					break;
				case 8:
					sql = 	"SELECT bureau_no, bureau_no AS id, '['||bureau_no||']'||bureau_name AS name, decode(level,1,'0',up_bureau_no) AS up_id,'0' AS flag "+
							"  FROM tvlsm_bureau "+
							" START WITH bureau_no=(SELECT DECODE(level,3,up_bureau_no,bureau_no) FROM tvlsm_bureau "+
							"  	   				   WHERE bureau_no='"+bureau_no+"' AND level>1 "+
							"					   START WITH up_bureau_no IS NULL CONNECT BY PRIOR bureau_no=up_bureau_no) "+
							" CONNECT BY PRIOR bureau_no=up_bureau_no "+
							" UNION "+
							"SELECT bureau_no, site_no, '['||site_no||']'||site_name, up_site_no, flag "+
							"  FROM (SELECT bureau_no,site_no, site_name, DECODE(up_site_no,'000',bureau_no,up_site_no) AS up_site_no, '1' AS flag FROM tsm_site) "+
							" START WITH up_site_no IN "+
							" 	   (SELECT bureau_no "+
							"	    FROM tvlsm_bureau "+
							" 	   	START WITH bureau_no=(SELECT DECODE(level,3,up_bureau_no,bureau_no) FROM tvlsm_bureau "+
							"  	   				   		  WHERE bureau_no='"+bureau_no+"' AND level>1 "+
							"							  START WITH up_bureau_no IS NULL CONNECT BY PRIOR bureau_no=up_bureau_no) "+
							" 							  CONNECT BY PRIOR bureau_no=up_bureau_no) "+
							" CONNECT BY PRIOR site_no=up_site_no";
					break;
				case 7:
					sql =	"SELECT bureau_no, bureau_no AS id, '['||bureau_no||']'||bureau_name AS name, '0' AS up_id,'0' AS flag "+
							"  FROM tvlsm_bureau "+
							"  where bureau_no='"+bureau_no+
							"' UNION "+
							"SELECT bureau_no, site_no, '['||site_no||']'||site_name, up_site_no, flag "+
							"  FROM (SELECT bureau_no,site_no, site_name, DECODE(up_site_no,'000',bureau_no,up_site_no) AS up_site_no, '1' AS flag FROM tsm_site) "+
							" START WITH up_site_no='"+bureau_no+"' "+
							" CONNECT BY PRIOR site_no=up_site_no";
					break;
				case 4:
					sql =	"SELECT bureau_no, site_no AS id, '['||site_no||']'||site_name AS name, DECODE(level,1,'0',up_site_no) AS up_id, '1' AS flag "+
							"  FROM tsm_site "+
							" START WITH site_no='"+site_no+"' CONNECT BY up_site_no=PRIOR site_no";
					break;
				default:
				    sql = "select bureau_no,staff_no as id,staff_name as name, '0' up_id,'2' as flag from tsm_staff where staff_no='"+staff_no+"'";	
			}
			__rs__ = Dao.select(sql, aDict.GetConnection());
			aDict.setValueByName(aDict.m_ActionId, __rs__);
		}catch (Exception e) {
			throw new FrameException(-22122170, "得到员工拥有的组织机构时，出现异常！", e.getMessage());
		}finally{
		 	try {
		    	if (__rs__ != null) __rs__.close();
		 	}catch(Exception e) {
		    	throw new FrameException(-22122171, "SystemServ.msm_217 员工拥有的组织机构时出现SQL异常", e.getMessage());
		 	}
		}
	}
	/***********************************************************************************
    组件名称:	MSM_220
    功能说明:	根椐查询条件查询区域、操作点、操作人员列表
    输入参数:
			    变量名		类型	长度	名称
			    id			string	14		查询ID
			    flag		string	1		查询类型(0:区域 1:操作点 2:操作人员(按操作点))
			    bureau_no	string	14		当前用户区域ID
			    auth_level	string	1		当前用户级别
			    site_no		string	6     	当前用户操作点
    输出参数:
			    类型        返回值
			    ArrayList	MSM_217

    作者:		李湘麒
    编写日期:	2004-06-09
    修改者		修改日期	修改内容
    ************************************************************************************
    */
	public void MSM_220(DynamicDict aDict) throws FrameException {
		try {
		    String id			= (String)aDict.getValueByName("id");					
			String flag			= (String)aDict.getValueByName("flag");
			String site_no		= (String)aDict.getValueByName("site_no",false);			
			String bureau_no	= (String)aDict.getValueByName("bureau_no",false);			
			String auth_level	= (String)aDict.getValueByName("auth_level");	
			switch (Integer.parseInt(auth_level)){
				case 9:
					if (flag.equals("0")){
						sql = 	"SELECT a.bureau_no,a.top_no,a.bureau_name,a.xian_no,a.city_no,b.city_name,a.area_code,a.type,c.sm_disp_view,a.flag_res,a.level_res "+
								"  FROM tvlsm_bureau a, tvlsm_city_info b, "+
								"		(SELECT sm_used_view,sm_disp_view "+
								"		 FROM tsm_paravalue "+
								"		 WHERE sm_table_ename='TVLSM_BUREAU' AND sm_field_ename='TYPE') c "+
								" WHERE a.city_no=b.city_no(+) "+
								"	AND a.type=c.sm_used_view "+
								"	AND a.up_bureau_no='"+id+"' "+
								" ORDER BY a.bureau_no,b.city_name,a.bureau_name";
					}else if (flag.equals("1")){
						sql = 	"SELECT a.bureau_no,a.site_no,a.site_code,a.site_name,a.state,b.bureau_name,c.sm_disp_view site_type "+
								"  FROM tsm_site a,tvlsm_bureau b,tsm_paravalue c "+
								" WHERE a.bureau_no=b.bureau_no and a.site_type=c.SM_USED_VIEW and "+
								" c.sm_table_ename='TSM_SITE' and c.sm_field_ename='SITE_TYPE'"+
								"	AND DECODE(a.up_site_no,'000',a.bureau_no,a.up_site_no)='"+id+"' "+
								" ORDER BY a.site_name,a.site_code";
					}else{
						sql =	"SELECT staff_no,staff_code,staff_name,sex,state,auth_level "+
								"  FROM tsm_staff "+
								" WHERE site_no='"+id+"' "+
								" ORDER BY auth_level DESC,staff_name ASC";
					}
					break;
				case 8:
					if (flag.equals("0")){
						sql = 	"SELECT a.bureau_no,a.top_no,a.bureau_name,a.xian_no,a.city_no,b.city_name,a.area_code,a.type,c.sm_disp_view,a.flag_res,a.level_res "+
								"  FROM tvlsm_bureau a,tvlsm_city_info b, "+
								"		(SELECT sm_used_view,sm_disp_view "+
								"		 FROM tsm_paravalue "+
								"		 WHERE sm_table_ename='TVLSM_BUREAU' AND sm_field_ename='TYPE') c "+
								" WHERE a.city_no=b.city_no(+) "+
								"   AND a.type=c.sm_used_view "+
								"   AND a.up_bureau_no='"+id+"' "+
								"   AND a.bureau_no IN "+
								"		(SELECT bureau_no FROM tvlsm_bureau "+
								"		 START WITH bureau_no=(SELECT DECODE(level,3,up_bureau_no,bureau_no) FROM tvlsm_bureau "+
								"		  	   				   WHERE bureau_no='"+bureau_no+"' AND level>1 "+
								"							   START WITH up_bureau_no IS NULL CONNECT BY PRIOR bureau_no=up_bureau_no) "+
								"		CONNECT BY PRIOR bureau_no=up_bureau_no) "+
								" ORDER BY a.bureau_no,b.city_name,a.bureau_name";
					}else if (flag.equals("1")){
						sql = 	"SELECT	a.bureau_no,a.site_no,a.site_code,a.site_name,a.state,b.bureau_name "+
								"  FROM	tsm_site a, tvlsm_bureau b"+
								" WHERE a.bureau_no=b.bureau_no "+
								"	AND DECODE(a.up_site_no,'000',a.bureau_no,a.up_site_no)='"+id+"' "+
								"   AND a.bureau_no IN (SELECT bureau_no FROM tvlsm_bureau "+
								"					  START WITH bureau_no=(SELECT DECODE(level,3,up_bureau_no,bureau_no) FROM tvlsm_bureau "+
								"					 	   					WHERE bureau_no='"+bureau_no+"' AND level>1 "+
								"						   					START WITH up_bureau_no IS NULL CONNECT BY PRIOR bureau_no=up_bureau_no) "+
								"					  CONNECT BY PRIOR bureau_no=up_bureau_no) "+
								" ORDER BY a.site_name,a.site_code";
					}else{
						sql =	"SELECT staff_no,staff_code,staff_name,sex,state,auth_level "+
								"  FROM tsm_staff "+
								" WHERE site_no='"+id+"' "+
								"   AND bureau_no IN (SELECT bureau_no FROM tvlsm_bureau "+
								"					  START WITH bureau_no=(SELECT DECODE(level,3,up_bureau_no,bureau_no) FROM tvlsm_bureau "+
								"					 	   					WHERE bureau_no='"+bureau_no+"' AND level>1 "+
								"						   					START WITH up_bureau_no IS NULL CONNECT BY PRIOR bureau_no=up_bureau_no) "+
								"					  CONNECT BY PRIOR bureau_no=up_bureau_no) "+
								" ORDER BY auth_level DESC,staff_name ASC";
					}
					break;
				case 7:
					if (flag.equals("0")){
						sql = 	"SELECT a.bureau_no,a.top_no,a.bureau_name,a.xian_no,a.city_no,b.city_name,a.area_code,a.type,c.sm_disp_view,a.flag_res,a.level_res "+
								"  FROM tvlsm_bureau a, "+
								"	 	tvlsm_city_info b, "+
								"	 	(SELECT sm_used_view,sm_disp_view "+
								"		 FROM tsm_paravalue "+
								"		 WHERE sm_table_ename='TVLSM_BUREAU' AND sm_field_ename='TYPE') c "+
								" WHERE a.city_no=b.city_no(+) "+
								"   AND a.type=c.sm_used_view "+
								"   AND a.up_bureau_no='"+id+"' "+
								"   AND a.bureau_no='"+bureau_no+"' "+
								" ORDER BY a.bureau_no,b.city_name,a.bureau_name";
					}else if (flag.equals("1")){
						sql =	"SELECT	a.bureau_no,a.site_no,a.site_code,a.site_name,a.state,b.bureau_name "+
								"  FROM	tsm_site a, tvlsm_bureau b"+
								" WHERE a.bureau_no=b.bureau_no "+
								"	AND DECODE(a.up_site_no,'000',a.bureau_no,a.up_site_no)='"+id+"' "+
								"	AND a.bureau_no='"+bureau_no+"' "+
								" ORDER BY a.site_name,a.site_code";
					}else{
						sql =	"SELECT staff_no,staff_code,staff_name,sex,state,auth_level "+
								"  FROM tsm_staff "+
								" WHERE site_no='"+id+"' "+
								"   AND bureau_no='"+bureau_no+"' "+
								" ORDER BY auth_level DESC,staff_name ASC";
					}
					break;
				case 4:
					if (flag.equals("0")){
						sql = 	"";
					}else if (flag.equals("1")){
						sql =	"SELECT	a.bureau_no,a.site_no,a.site_code,a.site_name,a.state,b.bureau_name "+
								"  FROM	tsm_site a, tvlsm_bureau "+
								" WHERE a.bureau_no=b.bureau_no "+
								"	AND DECODE(a.up_site_no,'000',a.bureau_no,a.up_site_no)='"+id+"' "+
								"	AND a.site_no='"+site_no+"' "+
								" ORDER BY a.site_name,a.site_code";
					}else{
						sql =	"SELECT staff_no,staff_code,staff_name,sex,state,auth_level "+
								"  FROM tsm_staff "+
								" WHERE site_no='"+id+"' "+
								"   AND site_no='"+site_no+"' "+
								" ORDER BY auth_level DESC,staff_name ASC";
					}
					break;
				default:
						sql =	"";
					break;
			}
			__rs__ = Dao.select(sql, aDict.GetConnection());
			aDict.setValueByName(aDict.m_ActionId, __rs__);
		}catch (SQLException e) {
			throw new FrameException(-22122170, "得到员工拥有的组织机构详细列表时出现异常！", e.getMessage());
		}finally{
		 	try {
		    	if (__rs__ != null) __rs__.close();
		 	}catch(Exception e) {
		    	throw new FrameException(-22122171, "SystemServ.MSM_220 释放资源时异常", e.getMessage());
		 	}
		}
	}
	
   /******************************************************************************
    功能:       修改密码
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:  MSM_218
    功能说明:  修改密码
    输入参数:
    格式：   　　　　
    变量名              类型      长度     名称
    OLD_PASSWORD      string   16     旧密码
    NEW_PASSWORD      string   16   新密码
    STAFF_NO          string   6 员工号

    输出参数:
    变量名              类型      长度     名称

    返回结果：
    RETCODE    long               返回结果
    1:成功。0：修改口令有误。-1：输入密码有误
    作者： 盛正春
    编写日期：   2003-07-29
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public long MSM_218(DynamicDict aDict) throws FrameException {
	   
      String sOLD_PASSWORD = "";
      String sNEW_PASSWORD = "";
      String sSTAFF_NO = "";

      String sqlopen = "";
      String sqlopen1 = "";
      long update_num = 0;
      ResultSet update_nums = null;
      try {
         sOLD_PASSWORD = ((String) aDict.getValueByName("OLD_PASSWORD", false)).trim();
         sNEW_PASSWORD = ((String) aDict.getValueByName("NEW_PASSWORD", false)).trim();
         sSTAFF_NO = ((String) aDict.getValueByName("STAFF_NO", false)).trim();
         if(GlobalNames.USE_LDAP){
            //logger.info("修改LDAP 用户密码！");
            HashMap info =new HashMap();
            info.put("BFIBSSID", sSTAFF_NO);
            info.put("BFIBSSPASSWD", sNEW_PASSWORD);
            int iRet1 = LDAPUtils.validatetoLDAP(sSTAFF_NO,sOLD_PASSWORD);
            int iRet2 = LDAPUtils.validatetoLDAP(sSTAFF_NO,TEA.Encrypt(sOLD_PASSWORD));
            if(iRet1 !=0 && iRet2 !=0){
               throw new FrameException(-22122182, "LDAP:原密码有误，不能进行密码修改操作！");
            }else{
               iRet1 = LDAPUtils.updateToLDAP(info);
               if(iRet1 !=0)
                  throw new FrameException(-22122182, "LDAP:修改密码出错！\n"+"System Error: " + LDAPUtils.getErrMsg());
            }
         }
         else
         {
	         sqlopen1 = "select staff_no,bureau_no,site_no from tsm_staff where staff_no='" + sSTAFF_NO + "' and (password='" + sOLD_PASSWORD + "' or password='" + TEA.Encrypt(sOLD_PASSWORD) + "')";
	         __rs__ = __st__.executeQuery(sqlopen1);
	         if(!__rs__.next())throw new FrameException(-22122182, "原密码有误，不能进行密码修改操作！");
        }
        if (TEA.Encrypt(sNEW_PASSWORD).length()>32 )throw new FrameException(-22122182,"密码长度不能超过10位");
        sqlopen = "update tsm_staff set password='" + TEA.Encrypt(sNEW_PASSWORD) + "' where staff_no='" + sSTAFF_NO + "'";
        if (__st__ == null)
           __st__ = aDict.GetConnection().createStatement();
        update_num = __st__.executeUpdate(sqlopen);
        ////logger.info(sqlopen);
        if (update_num <= 0) {
           return 0;
        } else {
           writeStaffLog(" "," ",sSTAFF_NO,"12","员工号："+sSTAFF_NO+" 修改口令成功！",aDict.GetConnection());
           return 1;//正确返回.

        }
      } catch (SQLException e) {
         throw new FrameException(-22122181, "SystemServ:MSM_218 修改口令时出现异常\n  ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen, e.getMessage());
      }

   }
	/***********************************************************************************
    功能:       员工拥有的组织机构(包括操作员）
    组件类型:   中间层    所属Server名称:   SSM
    组件名称:   MSM_219
    功能说明:   员工拥有的组织机构
    输入参数:
    格式：   　　　　
    变量名              类型         长度     名称
    BUREAU_NO       string         14
    SITE_NO         string         6     接收人员操作点
    STAFF_NO        string         6     接收人员ID
    输出参数:
    类型        返回值
    arraylist    orgdate(site_no, site_name, up_site_no, flag ,bureau_no);

    作者： 盛正春
    编写日期：   2003-08-4
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public static ArrayList MSM_219(String allflag,String staff_no, String site_no, String bureau_no, Connection connection) throws FrameException {
      
	  ArrayList arraylist = null;
      Statement statement = null;
      ResultSet resultset = null;
      try {
         statement = connection.createStatement();
         String sql1 = "select auth_level from tsm_staff where staff_no='" + staff_no + "'";
         resultset = statement.executeQuery(sql1);
         ResultSet rs1 = null, rs2 = null;
         if (resultset.next()) {
            if (arraylist == null)
               arraylist = new ArrayList();
            int i = resultset.getInt("auth_level");
            String s4;
            //logger.info("MSM_219.i="+i);
            if (allflag=="all")
            {
            	i=9;//用于无条件取所有组织机构
            }
            //logger.info("MSM_219.i="+i+",allflag.substr(1,3)="+allflag.substr(1,3));
            switch (i) {
               case 9://系统管理员
               //区域信息
                  s4 =" select 'C'||city_no id, '-1' up_id, city_name name "
                     +" from tvlsm_city_info "
                     +" union "
                     +" select bureau_no id, 'C'||city_no up_id, bureau_name name from tvlsm_bureau ";

                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("name"));
                     tmp.put("bureau_no", rs1.getString("id"));
                     tmp.put("type", "2");
                     tmp.put("up_id", rs1.getString("up_id"));
                     tmp.put("id", rs1.getString("id"));
                     arraylist.add(tmp);
                  }
                  s4 = "select bureau_no, site_no, up_site_no, site_name from tsm_site where state='1'";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("site_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     tmp.put("id", rs1.getString("site_no"));
                     if (rs1.getString("up_site_no").equals("000")) {
                        String up_id = rs1.getString("bureau_no");
                        tmp.put("up_id", up_id);
                     } else {
                        tmp.put("up_id", rs1.getString("up_site_no"));
                     }
                     arraylist.add(tmp);
                  }
                  //操作员
                  s4 = "select staff_no,staff_name,site_no,bureau_no from tsm_staff where state='1'";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("staff_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     tmp.put("type","3");
                     tmp.put("id", "S"+rs1.getString("staff_no"));
                     tmp.put("up_id", rs1.getString("site_no"));
                     arraylist.add(tmp);
                  }
                  break;               

               case 8:
                  s4 =" select 'C'||city_no id, '-1' up_id, city_name name "
                     +" from tvlsm_city_info where city_no= substr('"+bureau_no+"',4,2)"
                     +" union "
                     +" select bureau_no id, 'C'||city_no up_id, bureau_name name from tvlsm_bureau where city_no= substr('"+bureau_no+"',4,2)";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("name"));
                     tmp.put("bureau_no", rs1.getString("id"));
                     tmp.put("type", "2");
                     tmp.put("up_id", rs1.getString("up_id"));
                     tmp.put("id", rs1.getString("id"));
                     arraylist.add(tmp);
                  }

                  s4 = "select bureau_no, site_no, up_site_no, site_name from tsm_site where state='1' and substr(bureau_no,4,2)=substr('"+bureau_no+"',4,2)";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("site_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     
                     tmp.put("id", rs1.getString("site_no"));
                     if (rs1.getString("up_site_no").equals("000")) {
                        String up_id = rs1.getString("bureau_no");
                        tmp.put("up_id", up_id);
                     } else {
                        tmp.put("up_id", rs1.getString("up_site_no"));
                     }
                     arraylist.add(tmp);
                  }


           s4 = "select staff_no,staff_name,site_no,bureau_no from tsm_staff where state='1' and substr(bureau_no,4,2)= substr('"+bureau_no+"',4,2)";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("staff_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     tmp.put("type","3");
                     tmp.put("id", "S"+rs1.getString("staff_no"));
                     tmp.put("up_id", rs1.getString("site_no"));
                     arraylist.add(tmp);
                  }

                  break;               
            case 7:
                  s4 =" select 'C'||city_no id, '-1' up_id, city_name name "
                     +" from tvlsm_city_info where city_no= substr('"+bureau_no+"',4,2)"
                     +" union "
                     +" select bureau_no id, 'C'||city_no up_id, bureau_name name from tvlsm_bureau where bureau_no= '"+bureau_no+"'";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("name"));
                     tmp.put("bureau_no", rs1.getString("id"));
                     tmp.put("type", "2");
                     tmp.put("up_id", rs1.getString("up_id"));
                     tmp.put("id", rs1.getString("id"));
                     arraylist.add(tmp);
                  }

                  s4 = "select bureau_no, site_no, up_site_no, site_name from tsm_site where state='1' and bureau_no= '"+bureau_no+"'";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("site_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     
                     tmp.put("id", rs1.getString("site_no"));
                     if (rs1.getString("up_site_no").equals("000")) {
                        String up_id = rs1.getString("bureau_no");
                        tmp.put("up_id", up_id);
                     } else {
                        tmp.put("up_id", rs1.getString("up_site_no"));
                     }
                     arraylist.add(tmp);
                  }


           s4 = "select staff_no,staff_name,site_no,bureau_no from tsm_staff where state='1' and bureau_no= '"+bureau_no+"'";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("staff_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     tmp.put("type","3");
                     tmp.put("id", "S"+rs1.getString("staff_no"));
                     tmp.put("up_id", rs1.getString("site_no"));
                     arraylist.add(tmp);
                  }

                  break;

               case 4:
		  s4 =" select 'C'||city_no id, '-1' up_id, city_name name "
                     +" from tvlsm_city_info where city_no= substr('"+bureau_no+"',4,2)"
                     +" union "
                     +" select bureau_no id, 'C'||city_no up_id, bureau_name name from tvlsm_bureau where bureau_no= '"+bureau_no+"'";

                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("name"));
                     tmp.put("bureau_no", rs1.getString("id"));
                     tmp.put("type", "2");
                     tmp.put("up_id", rs1.getString("up_id"));
                     tmp.put("id", rs1.getString("id"));
                     arraylist.add(tmp);
                  }

                  s4 = "select bureau_no, site_no, up_site_no, site_name from tsm_site where state='1' and site_no='"+site_no+"'";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("site_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     
                     tmp.put("id", rs1.getString("site_no"));
                     
                        String up_id = rs1.getString("bureau_no");
                        tmp.put("up_id", up_id);
                     arraylist.add(tmp);
                  }

                  s4 = "select staff_no,staff_name,site_no,bureau_no from tsm_staff where state='1' and site_no in( "+
         " select site_no from tsm_site) ";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("staff_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     tmp.put("type","3");
                     tmp.put("id", "S"+rs1.getString("staff_no"));
                     tmp.put("up_id", rs1.getString("site_no"));
                     arraylist.add(tmp);
                  }

                  break;

               default:
		  s4 =" select 'C'||city_no id, '-1' up_id, city_name name "
                     +" from tvlsm_city_info where city_no= substr('"+bureau_no+"',4,2)"
                     +" union "
                     +" select bureau_no id, 'C'||city_no up_id, bureau_name name from tvlsm_bureau where bureau_no= '"+bureau_no+"'";

                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("name"));
                     tmp.put("bureau_no", rs1.getString("id"));
                     tmp.put("type", "2");
                     tmp.put("up_id", rs1.getString("up_id"));
                     tmp.put("id", rs1.getString("id"));
                     arraylist.add(tmp);
                  }

                   s4 = "select bureau_no, site_no, up_site_no, site_name from tsm_site where state='1' site_no='"+site_no+"'";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("site_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     
                     tmp.put("id", rs1.getString("site_no"));
                        String up_id = rs1.getString("bureau_no");
                        tmp.put("up_id", up_id);
                     arraylist.add(tmp);
                  }

             s4 = "select staff_no,staff_name,site_no,bureau_no from tsm_staff where state='1' and site_no in( "+
         " select site_no from tsm_site) ";
                  rs1 = statement.executeQuery(s4);
                  while (rs1.next()) {
                     HashMap tmp = new HashMap();
                     tmp.put("name", rs1.getString("staff_name"));
                     tmp.put("bureau_no", rs1.getString("bureau_no"));
                     tmp.put("type","3");
                     tmp.put("id", "S"+rs1.getString("staff_no"));
                     tmp.put("up_id", rs1.getString("site_no"));
                     arraylist.add(tmp);
                  }
                  break;
            }
         } else {
            throw new FrameException(-22122192, "SystemServ.msm_219 没有此员工的配置信息！");
         }

      } catch (SQLException sqlexception) {
         throw new FrameException(-22122190, "得到员工拥有的组织机构时，出现异常！", sqlexception.getMessage());
      } finally {
         try {
            if (resultset != null)
               resultset.close();
            if (statement != null)
               statement.close();
         } catch (SQLException sqlexception1) {
            throw new FrameException(-22122191, "SystemServ.msm_219 员工拥有的组织机构时出现SQL异常", sqlexception1.getMessage());
         }
         return arraylist;
      }
   }
 	/*******************************************************************************
    功能: 删除消息
    组件类型:   中间层     所属Server名称:    SSM
    组件名称:  MSM_244
    功能说明:  删除消息
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称
    messageid        string   16     消息ID

    输出参数: 无

    返回结果：

    作者：  盛正春
    编写日期：   2003-08-10
    修改者                    修改日期              修改内容
    *******************************************************************************/
   public long MSM_244(DynamicDict aDict) throws FrameException {
	   
      String smessageid="";
      String sqlopen = "";
      String sqlinsert="";
      long update_nums = 0;
      try {
      	    smessageid = ((String) aDict.getValueByName("messageid")).trim();

            sqlinsert = "insert into tsm_message_his(select a.*,sysdate from tsm_message a "+
		" where a.messageid='"+smessageid+"')";

            update_nums=__st__.executeUpdate(sqlinsert);

            if (update_nums <= 0) {
               throw new FrameException(-22122441, "SystemServ:MSM_244 插入历史消息记录失败" + "sql语句是:" + sqlinsert);
            }
            sqlopen="delete from tsm_message where messageid='"+smessageid+"'";
           // //logger.info("msm_244: sqlopen--aa>>"+sqlopen);
            update_nums = 0;
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22122442, "SystemServ:MSM_244 删除消息记录失败" + "sql语句是:" + sqlopen);
            }
       } catch (SQLException e) {
            throw new FrameException(-22122443, "SystemServ:MSM_244 删除消息时出现异常\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sqlopen, e.getMessage());
       }
       return 0;
   }


   /******************************************************************************
    功能:      批量修改操作人属性
    组件名称:  MSM_019
    输入参数:
    格式：   　　　　
    变量名        说明
    staffs        操作人工号串（如：1001,1002,1003,）
	site_no       新的操作点    
    输出参数:
    变量名              类型      长度     名称

    返回结果：
    RETCODE    long               返回结果

    作者： shawn
    编写日期：   2004-10-27
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public void MSM_019(DynamicDict aDict) throws FrameException {

      String staffs;
      String strstaff="";
      String newsite_no;

      String sql = "";
      long update_num = 0;
      try {
         staffs     = ((String) aDict.getValueByName("staffs")).trim();
         newsite_no = ((String) aDict.getValueByName("newsite_no")).trim();
         strstaff = ((String) aDict.getValueByName("STAFF-NO",false)).trim();
        
         String[] arrstaff =Utility.split(staffs,",");
         staffs="";
         for(int i=0; i<arrstaff.length; i++){
			staffs=staffs+"'"+arrstaff[i]+"',";
         }
         staffs=staffs+"'x'";
         sql="update tsm_staff set (site_no,bureau_no)=(SELECT site_no,bureau_no FROM tsm_site WHERE site_no='"+newsite_no+"') where staff_no in ("+staffs+")"; 
         if (__st__ == null)
            __st__ = aDict.GetConnection().createStatement();
          update_num = __st__.executeUpdate(sql);
          if (update_num <= 0) {
             throw new FrameException(-22122421, "SystemServ:MSM_019 批量修改操作人失败" + "sql语句是:" + sql);
          }else{
	          if (!strstaff.equals(""))
	              writeStaffLog("","",strstaff,"12","批量修改操作人成功"+sql,aDict.GetConnection());
         }
      }catch (SQLException e) {
            throw new FrameException(-22122443, "SystemServ:MSM_019 批量修改操作人失败\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sql, e.getMessage());
      }
   }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
