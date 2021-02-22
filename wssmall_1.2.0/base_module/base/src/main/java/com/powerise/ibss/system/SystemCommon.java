/**
错误号段：100001012
系统管理接口代理，实现以下方法：
MSM_000	根据工号、密码登录验证
MSM_002	根据工号取可管理区域
MSM_003	取人员信息
MSM_004	根据工号取密码
MSM_018	查询菜单功能相关信息列表
MSM_151	根据表名和字段名取静态参数从tsm_paravalue或tfm_action_list中取动态sql
MSM_998	系统初始化
MSM_999	从ibss.xml中取配置参数

修改记录
1.2003-9-5	shawn 修改到ldap的密码验证方法;增加ldap返回错误信息.修改密码时。
2.2004-3-11 shawn 修改MSM_002
3.2004-5-31	xiangqi.lee 修改MSM_000
4.2004-6-7	xiangqi.lee 添加MSM_018
*/
package com.powerise.ibss.system;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.util.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
//import org.apache.log4j.*;

public class SystemCommon implements IAction{
	private String service_name;
	private static boolean DEBUG=true;//调试状态开关
	private static HashMap m_Role = null;
	private static HashMap m_Fun = null;
	
	@Override
	public int perform(DynamicDict aDict) throws FrameException {
		service_name = aDict.getServiceName();
		//logger.info("调用类SystemCommon.........."+service_name);
		if(service_name.equalsIgnoreCase("MSM_000")){
			String valid_flag	= (String)aDict.getValueByName("p_no_passwd_flag",false);
			if (valid_flag.equals("1")) {
				getStaffInfo(aDict);
			}
			else if(GlobalNames.USE_LDAP ){
		    	int iRet =  LDAPUtils.validatetoLDAP((String)aDict.getValueByName("staff_no"),(String)aDict.getValueByName("password"));
		    	int iRet2 = LDAPUtils.validatetoLDAP((String)aDict.getValueByName("staff_no"),TEA.Encrypt((String)aDict.getValueByName("password")));
		    	String msg =null;
		    	if(iRet!=0&&iRet2!=0){
		       		if(iRet ==1){
		          		msg ="LDAP消息:用户被挂起！";
		       		}else if(iRet ==2){
		          		msg ="LDAP消息:用户被注销！";
		       		}else if(iRet ==3){
		          		msg ="LDAP消息:用户不存在或用户名、密码错误！";
		       		}else{
		          		msg ="System Error: "+LDAPUtils.getErrMsg();
		       		}
		       		throw new FrameException(-100001012, msg);
		    	}else{
		       		getStaffInfo(aDict);
		    	}
		 	}else{
		    	validStaffInfo(aDict);
		 	}
		}else if(service_name.equalsIgnoreCase("MSM_018")){
			MSM_018(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_002")){
			String staff_no =(String)aDict.getValueByName("STAFF-NO",true);
			String now_flag	= (String) aDict.getValueByName("flag",false);
			ArrayList a =new ArrayList();
			if (now_flag.equals(""))
			{
				a=getOwnBureau(staff_no, aDict.GetConnection());
			}else{
				a=getOwnBureau(staff_no,now_flag, aDict.GetConnection());
			}

			
			if(a!=null && a.size()>0) aDict.setValueByName("bureau", a);
		}else if(service_name.equalsIgnoreCase("MSM_003")){
			getStaffInfo(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_151")){
		 	if(!aDict.getValueByName("action_id", false).equals("")){
				aDict.setValueByName(aDict.getValueByName("action_id").toString(),
				getStaticInfoByActionID(aDict.getValueByName("action_id").toString(), aDict.GetConnection()));
		 	}else{
		    	String table_name =(String)aDict.getValueByName("table_name",true);
		    	String field_name =(String)aDict.getValueByName("field_name",true);
		    	aDict.setValueByName(table_name,
		       	getStaticInfoByTableAndField(aDict, table_name, field_name, aDict.GetConnection()));
		 	}
		}else if(service_name.equalsIgnoreCase("MSM_998")){
		 	Runtime r =Runtime.getRuntime();
		 			r.runFinalization();
		 			r.gc();
		}else if(service_name.equalsIgnoreCase("MSM_999")){
		 	//系统静态变量初始化或变动
		 	//initSystem();
		}
		return 0;
	}
	
   //取人员基本属性
   public static void getStaffInfo(DynamicDict aDict) throws FrameException{
      Connection	_conn	= aDict.GetConnection();
      ResultSet 	_rs		= null;
      try{
         String staff_code	= (String)aDict.getValueByName("staff_code");
         String ip			= (String)aDict.getValueByName("ip", false);
         TsmStaff tsmStaff 	= new TsmStaff();
		 String staff_no = tsmStaff.getStaffNo(staff_code, _conn);         	
         //start社会渠道接入控制
         if (!loginValid(staff_no,ip,_conn))
         	 throw new FrameException(-100001019,"不存在此用户名！");
         //end社会渠道接入控制
         
         _rs = tsmStaff.getTsmStaff(staff_no, _conn);
         int row =0;
         if (_rs != null){
           	 _rs.last();
             row =_rs.getRow();
             _rs.beforeFirst();
         }
         if(row ==0){
            throw new FrameException(-100001013,"不存在此用户名！");
         }else if(row >1){
            throw new FrameException(-100001014,"数据库存在多个同一编号的员工！");
         }else{
            _rs.next();
            String site_code	= _rs.getString("site_code");
            //String staff_code	= _rs.getString("staff_code");          
            String site_no		= _rs.getString("site_no");
            String bureau_no 	= _rs.getString("bureau_no");
            String staff_name 	= _rs.getString("staff_name");
            String auth_level 	= _rs.getString("auth_level");
            String title_type 	= _rs.getString("title_type");
            String state 		= _rs.getString("state");
            String site_name 	= _rs.getString("site_name");
            String bureau_name 	= _rs.getString("bureau_name");
            String city_no 		= _rs.getString("city_no");
            String area_code    =_rs.getString("area_code");
            String title_name 	= _rs.getString("title_name");
            String title_id 	= _rs.getString("title_id");
            String limit_machine_flag =_rs.getString("limit_machine_flag");
            String logon_time 	= Utility.dateToString(new java.util.Date(),"yyyy-MM-dd hh:mm:ss");
            if (Integer.parseInt(state) !=1){
				throw new FrameException(-100001001,"此员工状态非法，不能登录。请与系统管理员联系！");
            }else{
				boolean continu =true;
               	if (limit_machine_flag !=null && limit_machine_flag.trim().equals("1")){
                  	//要进行终端验证
                  	if (!tsmStaff.isValidTerminal(ip, _conn)){
                     	throw new FrameException(-100001016,"此"+ip+" 非法登录！");
                  	}
               	}
               	if (continu){
                  	HashMap _info =new HashMap();
                  	_info.put("site_no",	site_no);
                  	_info.put("site_code",	site_code);
                  	_info.put("site_name", 	site_name);
                  	_info.put("staff_no", 	staff_no);
                  	_info.put("staff_code", staff_code);
                  	_info.put("staff_name", staff_name);
                  	_info.put("bureau_no", 	bureau_no);
					_info.put("bureau_name",bureau_name);
                  	_info.put("auth_level", auth_level);
                  	_info.put("city_no", 	city_no);
                  	_info.put("area_code", 	area_code);
                  	_info.put("title_id", 	title_id);
                  	_info.put("title_type", title_type);
                  	_info.put("title_name", title_name);
                  	_info.put("logon_time",	logon_time);
                  	_info.put("login_ip",ip);

                  	//得到员工权限信息
                  	HashMap role = tsmStaff.getRole(staff_no, _conn);
                  	if (role != null) _info.put("role", role);

                  	aDict.setValueByName("staff_fun",	tsmStaff.getFun(staff_no, _conn));
                  	aDict.setValueByName("staff_info", 	_info);
               }
               //记录员工登录日志
               logStaffLog(bureau_no, site_no, staff_no, ip, _conn, 1);
            }
         }
      }catch(SQLException e){
         throw new FrameException(-100001003,"验证用户登录信息时，出现异常！", e.getMessage());
      }finally{
         try{
            if(_rs !=null){
               _rs.getStatement().close();
               _rs.close();
            }
         }catch(SQLException e){
            throw new FrameException(-100001004,"SystemCommon.validStaffInfo 方法释放资源时出现SQL异常", e.getMessage());
         }
      }
   }

   /**
   *验证用户登录信息
   *@param p_table_name 表名
   *@param p_field_name 字段名
   *@return 静态数据列表
   */
   public static void validStaffInfo(DynamicDict aDict) throws FrameException{
		Connection	_conn	= aDict.GetConnection();
		ResultSet 	_rs		= null;
		try{
         	TsmStaff tsmStaff	= new TsmStaff();			
      		String staff_code	= (String)aDict.getValueByName("staff_code", false);
			String staff_no		= (String)aDict.getValueByName("staff_no", false);
			String password		= (String)aDict.getValueByName("password");
         	String ip 			= (String)aDict.getValueByName("ip", false);
         	String ip_server	= (String)aDict.getValueByName("ip_server", false);
			String valid_flag	= (String)aDict.getValueByName("p_no_passwd_flag",false);
         	
			if (staff_no.equals("")) staff_no = tsmStaff.getStaffNo(staff_code, _conn);         	

         //start社会渠道接入控制
         if (valid_flag.equals("2") && !loginValid(staff_no,ip_server,_conn))
         	 throw new FrameException(-100001019,"这是代理商专用服务器[ip="+ip_server+"]，您不能访问！");
         //end社会渠道接入控制


         	_rs = tsmStaff.getTsmStaff(staff_no, _conn);
         	if(!_rs.next()){
				throw new FrameException(-100001013,"您输入的员工编码(staff_code="+staff_code+")不存在，请重新输入!");
			}else{
				if (password.equals(_rs.getString("password")) ||
					TEA.Encrypt(password).equals(_rs.getString("password"))){
					if (staff_code.equals("")) staff_code = _rs.getString("staff_code");
					String site_code	= _rs.getString("site_code");
					String site_no		= _rs.getString("site_no");
					String staff_name 	= _rs.getString("staff_name");
					String auth_level 	= _rs.getString("auth_level");
					String state 		= _rs.getString("state");
					String site_name 	= _rs.getString("site_name");
					String bureau_no 	= _rs.getString("bureau_no");					
					String bureau_name 	= _rs.getString("bureau_name");
					String city_no 		= _rs.getString("city_no");
					String title_id 	= _rs.getString("title_id");					
					String title_name 	= _rs.getString("title_name");
					String title_type 	= _rs.getString("title_type");					
					String logon_time 	= Utility.dateToString(new java.util.Date(),"yyyy-MM-dd hh:mm:ss");
					String limit_machine_flag =_rs.getString("limit_machine_flag");					
					/*if(DEBUG){
					  if(isonline(staff_no, ip,_conn))
					  throw new FrameException(-100001002,"您所用的工号("+staff_no+")已在线！请换另一个工号登录!");
					}*/
					if (Integer.parseInt(state) != 1)
						throw new FrameException(-100001001,"此工号已禁用！");
					//要进行终端验证
					if (limit_machine_flag != null &&
						limit_machine_flag.trim().equals("1") &&
						!tsmStaff.isValidTerminal(ip, _conn))
					 	throw new FrameException(-100001016,"此"+ip+" 非法登录！");
					
					HashMap _info = new HashMap();
					_info.put("site_code",	site_code);
					_info.put("staff_code", staff_code);
					_info.put("site_no",	site_no);
					_info.put("site_name", 	site_name);
					_info.put("staff_no", 	staff_no);
					_info.put("staff_name",	staff_name);
					_info.put("auth_level",	auth_level);
					_info.put("bureau_no", 	bureau_no);
					_info.put("bureau_name",bureau_name);
					_info.put("city_no", 	city_no);
					_info.put("title_id", 	title_id);
					_info.put("title_name", title_name);
					_info.put("title_type",	title_type);							
					_info.put("logon_time",	logon_time);
					_info.put("password",	password);
					_info.put("login_ip",ip);
					//得到员工权限信息
					HashMap _role=null;
					if(m_Role == null)
					    m_Role = new HashMap();
					Object o = m_Role.get(staff_no);
					if(o!= null)
					    _role = (HashMap)o;
					else
					{
					  _role = tsmStaff.getRole(staff_no, _conn);
					  m_Role.put(staff_no,_role);
					}
					if (_role != null) _info.put("role",_role);

					//得到员工菜单信息
					Vector _fun = null;
					if(m_Fun == null)
					    m_Fun = new HashMap();
					o = m_Fun.get(staff_no);
					if(o!=null)
					    _fun = (Vector)o;
					else
					{
					    _fun = tsmStaff.getFun(staff_no, _conn);
					    m_Fun.put(staff_no,_fun);
					}
					aDict.setValueByName("staff_fun",	_fun);
					aDict.setValueByName("staff_info",	_info);
					
					//记录员工登录日志
					logStaffLog(bureau_no, site_no, staff_no, ip, _conn, 1);			
				}else{
					throw new FrameException(-100001002,"用户密码不正确！");
				}
			}
		}catch(SQLException e){
			throw new FrameException(-100001003,"验证用户登录信息时，出现异常！",e.getMessage());
		}finally{
			try{
				if (_rs != null) _rs.close();
			}catch(SQLException e){
				throw new FrameException(-100001004,"SystemCommon.validStaffInfo 方法释放资源时出现SQL异常", e.getMessage());
			}
		}
	}

	/************************************************************************************
    功能说明:判断能否登录
    组件名称:loginValid
    输入参数:
    变量名		名称
    staff_no	登录人工号
    ip		    登录人IP

    输出参数:能登录'COMEIN',不能登录'GETOUT';
    返回结果:无
		

    作者： shawn
    编写日期：   2005-1-13
    修改者                         修改日期                     修改内容
    ************************************************************************************/
   public static boolean loginValid(String staff_no, String ip, Connection _conn)
            throws FrameException{
      Statement st =null;
      ResultSet _rs =null;
      try{
         st =_conn.createStatement();
         String sql ="SELECT CASE WHEN (SELECT INSTR(param_value,'"+ip+"')  FROM TSM_PARAMATER WHERE param_id='SYS_01' AND SUBSTR(param_desc,1,1)='1')>0" 
                    +" AND NOT EXISTS" 
                    +" (SELECT 1 FROM tam_consignee  WHERE state='1' AND cons_staff_no='"+staff_no+"')"
                    +" THEN 'GETOUT'"
			        +" ELSE 'COMEIN'"
			        +" END RESULT"
			        +" FROM dual";
         _rs =st.executeQuery(sql);
         _rs.next();
         if (_rs.getString(1).equals("COMEIN"))
           return true;
         else 
           return false;
      }catch(SQLException sqle){
         throw new FrameException(-100001015,"判断员工渠道时异常！", sqle.getMessage());
      }
   }  
   
   /**
   *得到员工所看到所有区域
   *@param p_staff_id 员工号
   *@return 区域列表『区域编号、区域名称』
   */
	public static ArrayList getOwnBureau(String p_staff_id, Connection _conn)
      throws FrameException{
      ArrayList own_bureau =null;
      Statement st =null;
      //ResultSet _rs =null;
      try{
         st =_conn.createStatement();
         own_bureau =new ArrayList();
	     String  sql = "select a.bureau_no, bureau_name from tvlsm_bureau a,tsm_staff b "+
				"where type='1' and ((b.auth_level='9' and substr(a.bureau_no,1,3)=substr(b.bureau_no,1,3)) "+
				"or (b.auth_level='8' and substr(a.bureau_no,1,5)=substr(b.bureau_no,1,5)) "+
				"or (b.auth_level<='7' and a.bureau_no=b.bureau_no)) and b.staff_no='"+p_staff_id+"' order by bureau_name";
        ResultSet rsTmp =st.executeQuery(sql);
        HashMap tmp;
        while(rsTmp.next()){
           tmp =new HashMap();
           if(rsTmp.getString("bureau_no")!=null && rsTmp.getString("bureau_name") !=null)
              tmp.put("bureau_no", rsTmp.getString("bureau_no"));
              tmp.put("bureau_name", rsTmp.getString("bureau_name"));
           own_bureau.add(tmp);
            }
            rsTmp.close();
      }catch(SQLException sqle){
         throw new FrameException(-100001005,
            "得到员工所能看到的区域时，出现异常", sqle.getMessage());
      }finally{
         try{
            if(st !=null)
               st.close();
         }catch(SQLException e){
            throw new FrameException(-100001006,
               "SystemCommon.getOwnBureau 方法释放资源时出现SQL异常", e.getMessage());
         }
         return own_bureau;
      }
   }

   public static ArrayList getOwnBureau(String p_staff_id,String flag, Connection _conn)
      throws FrameException{	  
      ArrayList own_bureau =null;
      Statement st =null;
	  String sql="";
      //ResultSet _rs =null;
      try{
         st =_conn.createStatement();
         own_bureau =new ArrayList();
		 sql = "SELECT a.bureau_no, bureau_name,a.level_res,a.up_bureau_no,c.sm_disp_view FROM tvlsm_bureau a,tsm_staff b,tsm_paravalue c  "+
				"WHERE ((b.auth_level='9' AND SUBSTR(a.bureau_no,1,3)=SUBSTR(b.bureau_no,1,3)) "+
				"OR (b.auth_level='8' AND SUBSTR(a.bureau_no,1,5)=SUBSTR(b.bureau_no,1,5)) "+
				"OR (b.auth_level<='7' AND a.bureau_no=b.bureau_no)) AND b.staff_no='"+p_staff_id+"' "+
				"and a.level_res in ('0','1','2','3') AND a.level_res=c.sm_used_view(+) AND c.sm_table_ename(+)='TVLSM_BUREAU' "+ 
				"AND c.sm_field_ename(+)='LEVEL_RES' order by bureau_name";		 
	     
		//logger.info("MSM-002.sql="+sql);
        ResultSet rsTmp =st.executeQuery(sql);
        HashMap tmp;
        while(rsTmp.next()){
           tmp =new HashMap();
           if(rsTmp.getString("bureau_no")!=null && rsTmp.getString("bureau_name") !=null&&rsTmp.getString("level_res")!=null && rsTmp.getString("sm_disp_view") !=null){
              tmp.put("bureau_no", rsTmp.getString("bureau_no"));
              tmp.put("bureau_name", rsTmp.getString("bureau_name"));
			  tmp.put("level_res", rsTmp.getString("level_res"));
			  tmp.put("level_name", rsTmp.getString("sm_disp_view"));
              tmp.put("up_bureau_no", rsTmp.getString("up_bureau_no"));				
			  own_bureau.add(tmp);
            }
		}
            rsTmp.close();
      }catch(SQLException sqle){
         throw new FrameException(-100001005,
            "得到员工所能看到的区域时，出现异常", sqle.getMessage());
      }finally{
         try{
            if(st !=null)
               st.close();
         }catch(SQLException e){
            throw new FrameException(-100001006,
               "SystemCommon.getOwnBureau 方法释放资源时出现SQL异常", e.getMessage());
         }
         return own_bureau;
      }
   }

   /**
   *通过表名、字段名得到其相关静态数据
   *@param p_table_name 表名
   *@param p_field_name 字段名
   *@return 静态数据列表
   */
   public static ArrayList getStaticInfoByTableAndField(DynamicDict aDict, String p_table_name, String p_field_name, Connection _conn)
      throws FrameException{
      ArrayList static_info =null;
      Statement st =null;
      ResultSet _rs =null;
      try{
         st =_conn.createStatement();
         String sql ="SELECT SM_USED_VIEW,SM_DISP_VIEW FROM TSM_PARAVALUE WHERE SM_TABLE_ENAME='"
          + p_table_name + "' AND SM_FIELD_ENAME = '" + p_field_name + "' order by SM_ORDER";
         _rs =st.executeQuery(sql);
         HashMap tmp;
         while(_rs.next()){
            tmp =new HashMap();
            if(_rs.getString("SM_USED_VIEW") !=null && _rs.getString("SM_DISP_VIEW") !=null){
               tmp.put("value", _rs.getString("SM_USED_VIEW"));
               tmp.put("text", _rs.getString("SM_DISP_VIEW"));
            }
            if(static_info ==null)
               static_info =new ArrayList();
            static_info.add(tmp);
         }
      }catch(SQLException sqle){
         throw new FrameException(-100001007,
            "通过表名、字段名取静态信息时，出现异常！", sqle.getMessage());
      }finally{
         try{
            if(_rs !=null)
               _rs.close();
            if(st !=null)
               st.close();
         }catch(SQLException e){
            throw new FrameException(-100001008,
               "SystemCommon.getOwnBureau 方法释放资源时出现SQL异常", e.getMessage());
         }
         return static_info;
      }
   }
   /**
   *通过动态SQL服务得到其相关静态数据
   *@param p_action_id 动态SQL服务
   *@return 静态数据列表
   */
   public static ArrayList getStaticInfoByActionID(String p_action_id, Connection _conn)
      throws FrameException{
      ArrayList static_info =null;
      Statement st =null;
      ResultSet _rs =null;
      try{
         st =_conn.createStatement();
		 String sql=SysSet.getActionSQL(p_action_id);
         //String sql ="SELECT ACTION_CLAUSE FROM TFM_ACTION_LIST WHERE ACTION_ID='"
         //    + p_action_id + "' ORDER BY SEQ";
         _rs =st.executeQuery(sql);
         if (sql!=null&&!sql.equals("")){
        
		 //if(_rs.next()){
         //   sql =_rs.getString(1);
            _rs =st.executeQuery(sql);
            HashMap tmp;
            while(_rs.next()){
               tmp =new HashMap();
               if(_rs.getString(1) !=null && _rs.getString(2) !=null){
                  tmp.put("value", _rs.getString(1));
                  tmp.put("text", _rs.getString(2));
               }
               if(static_info ==null)
                  static_info =new ArrayList();
               static_info.add(tmp);
            }
         }
      }catch(SQLException sqle){
         throw new FrameException(-100001009,
            "通过动态SQL取静态信息时，出现异常！", sqle.getMessage());
      }finally{
         try{
            if(_rs !=null)
               _rs.close();
            if(st !=null)
               st.close();
         }catch(SQLException e){
            throw new FrameException(-100001006,
               "SystemCommon.getOwnBureau 方法释放资源时出现SQL异常", e.getMessage());
         }
         return static_info;
      }
   }

   private static void logStaffLog(String bureau_no, String site_no,
         String staff_no, String ip, Connection _conn, int type)
            throws FrameException{
      if(type ==1)

         SystemServ.writeStaffLog(bureau_no,site_no, staff_no, "12",
             "员工("+staff_no+"["+ip+"]) 登录系统", _conn);
      else
         SystemServ.writeStaffLog(bureau_no,site_no, staff_no, "12",
             "员工("+staff_no+"["+ip+"]) 退出系统", _conn);
      logonlinestate(staff_no,ip,_conn,type);
   }

   //判断是否在线
   private static boolean isonline(String staff_no, String ip, Connection _conn)
            throws FrameException{
      Statement st =null;
      ResultSet _rs =null;
        //如果该工号处于在线状态，且本地IP<>在线记录中的IP，则返回true
        //否则返回false
      try{
         st =_conn.createStatement();
         //logger.info("staff_no="+staff_no+"ip="+ip+"ip");
         String sql ="select option_rec_id from tsm_option_rec where state='1' and staff_no='"+staff_no+"' and trim(ip_addr)<>'"+ip+"'";
         _rs =st.executeQuery(sql);
         return _rs.isBeforeFirst();//在线
      }catch(SQLException sqle){
         throw new FrameException(-100001015,
            "判断员工登录状态时，出现异常！", sqle.getMessage());
      }
   }

   //记录在线状态
   private static void logonlinestate(String staff_no, String ip, Connection _conn,int type)
            throws FrameException{
      Statement st =null;
      ResultSet _rs =null;
      try{
         st =_conn.createStatement();
		 //add by xiaozhu
		 if (ip == null || ip.equals("")) ip = "127.0.0.1";

         String sql ="select option_rec_id from tsm_option_rec where staff_no='"+staff_no+"' and ip_addr='"+ip+"'";
         _rs =st.executeQuery(sql);
         if(!_rs.isBeforeFirst()){
         //没有记录，插入一条
           sql ="insert into tsm_option_rec(option_rec_id,bureau_no,staff_no,site_no,ip_addr,beg_time)";
           sql =sql+" select nvl(newid,1),bureau_no,staff_no,site_no,'"+ip;
           sql =sql+"' ,sysdate from tsm_staff,(select max(to_number(option_rec_id))+1 newid from tsm_option_rec ) where staff_no='"+staff_no+"'";
           st.executeUpdate(sql);
        }
        if(type==1)sql="update tsm_option_rec set state='1',beg_time=sysdate where staff_no='"+staff_no+"' and ip_addr='"+ip+"'";
        else sql="update tsm_option_rec set state='0',end_time=sysdate where staff_no='"+staff_no+"' and ip_addr='"+ip+"'";
        st.executeUpdate(sql);
      }catch(SQLException sqle){
         throw new FrameException(-100001015,
            "更新员工登录状态出错！", sqle.getMessage());
      }
   }


	/************************************************************************************
    功能说明:查询菜单功能相关信息列表
    组件名称:MSM_018
    输入参数:
    变量名		类型	长度	名称
    flag		string	6		菜单功能号
    fun_id		string	1		查询类型

    输出参数:(ArrayList)dict.getValueByName(dict.m_ActionId);
    返回结果:无
		

    作者： 李湘麒
    编写日期：   2004-06-07
    修改者                         修改日期                     修改内容
    ************************************************************************************/
	public static void MSM_018(DynamicDict dict) throws FrameException{
		String id	= (String)dict.getValueByName("fun_id",false);
		String flag	= (String)dict.getValueByName("flag",false);	
		String sql 	= new String();
		if (flag.equals("0")){
			sql = "SELECT a.role_id role_id, a.role_name role_name, a.staff_no staff_no"+
				  "  FROM tsm_role a,tsm_role_fun b"+
				  " WHERE a.role_id=b.role_id "+
				  "	  AND b.fun_id='"+id+"'"+
				  " ORDER BY a.role_id";
		}else{
			sql = "SELECT DISTINCT a.staff_code staff_code, a.staff_name staff_name, c.site_name site_name,"+
				  " d.bureau_name bureau_name, e.city_name city_name"+
				  "  FROM tsm_staff a,tsm_staff_role b,tsm_site c, tvlsm_bureau d, tvlsm_city_info e"+
				  " WHERE a.staff_no=b.staff_no"+
				  "   AND a.site_no=c.site_no"+
				  "   AND d.city_no=e.city_no"+			  
				  "   AND a.bureau_no=d.bureau_no"+
				  "	  AND b.role_id IN (SELECT role_id FROM tsm_role_fun WHERE fun_id='"+id+"')"+
				  " ORDER BY e.city_name,d.bureau_name,c.site_name,a.staff_code";
		}
		try {
			ResultSet _rs	= Dao.select(sql,dict.GetConnection());
			ArrayList things =new ArrayList();
			
			while(_rs.next()){
				HashMap thing =new HashMap(); 
				if (flag.equals("0")){
				       
					String role_id=_rs.getString("ROLE_ID");
					String role_name=_rs.getString("ROLE_NAME");
					String staff_no=_rs.getString("STAFF_NO");
					thing.put("role_id",role_id);
					thing.put("role_name",role_name);
					thing.put("staff_no",staff_no);
										
				}else{
					
					String staff_code=_rs.getString("STAFF_CODE");
					String staff_name=_rs.getString("STAFF_NAME");
					String site_name=_rs.getString("SITE_NAME");
					String bureau_name=_rs.getString("BUREAU_NAME");
					String city_name=_rs.getString("CITY_NAME");
					thing.put("staff_code",staff_code);
					thing.put("staff_name",staff_name);
					thing.put("site_name",site_name);
					thing.put("bureau_name",bureau_name);
					thing.put("city_name",city_name);
					
				}
				things.add(thing);
			}
				
			
			if(_rs !=null){_rs.getStatement().close(); _rs.close(); _rs=null;}
			if(things !=null && things.size() >0)
			dict.setValueByName("things", things);
		}catch(SQLException e){
         	throw new FrameException(-100001015,"执行SQL语句出现异常",e.getMessage());
		}
	}
}
