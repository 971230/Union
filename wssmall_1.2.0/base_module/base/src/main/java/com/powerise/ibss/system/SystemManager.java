/**
   错误号(此程序最大错误号)：-2212087
   MSM_006 添加或修改角色信息
   MSM_007 删除角色
   MSM_008 发送新消息信息
   MSM_010 修改消息信息(由未读改为已读)
   MSM_023 根据操作人取可管理角色
   MSM_024 根据操作人查询其可操作功能
   MSM_106 添加或修改操作人功能信息
   MSM_902 修改消息信息
   MSM_903 修改某角色对应的人员信息
   MSM_904 实现人员继承其部门的角色功能
   MSM_905 实现部门的角色功能分配给该部门所属的员工
   MSM_906 按工号或操作点取可授权的角色信息
   MSM_907 修改操作点的角色
   MSM_900 功能菜单维护-添加、修改
   MSM_901 功能菜单维护-删除
   MSM_910 菜单子功能添加、修改
   MSM_911 菜单子功能删除
   MSM_912 角色数据配置-查询、修改
   MSM_913 根椐数据类型取得配置数据列表
   MSM_997 修改某操作点对应的角色信息
   MSM_020 增加一条常用功能信息
   MSM_021 删除一条常用功能信息
   MSM_022 取用户定制的自动打开的常用功能
   opOutSystem 验证外部系统发过来的验证，并返回其功能菜单
   修改记录：
   5.2004.10.18 shawn 增加MSM_106
   4.2004.10.12 shawn 修改文字信息
   3.2003.12.10 MSM_023,非系统级人员可取得自己建立的角色。MSM_906,非系统级人员，取得的可授权角色为自己拥有的和建立的角色。
   2.2003.9.3 MSM_023，限制角色的删除
   1.2003.9.1 插入消息时，限制消息长度为900
   
*/
package com.powerise.ibss.system;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.util.Utility;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;


public class SystemManager implements IAction{
   private static Logger m_Logger = null;
   private String service_name;
   private String sql;
   private ResultSet rs;
   @Override
public int perform(DynamicDict aDict) throws FrameException {
      service_name =aDict.getServiceName();
      Utility.println("调用类SystemManager.........."+service_name);
      try{
		if(service_name.equalsIgnoreCase("MSM_900")){
			//功能菜单维护-添加、修改
			maintainMenu(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_901")){
			//功能菜单维护-删除
			delMenu(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_910")){
    		//菜单子功能添加、修改
    		maintainSubFun(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_911")){
    		delSubFun(aDict);
    	}else if(service_name.equalsIgnoreCase("MSM_912")){
    		MSM_912(aDict);
    	}else if(service_name.equalsIgnoreCase("MSM_913")){
    		MSM_913(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_023")){
			getStaffRoleList(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_024")){
			getStaffFunList(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_006")){
			maintainRole(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_106")){
			MSM_106(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_007")){
			role_del(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_008")){
			addMessage(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_010")){
			changeMessage(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_902")){
			modRoleStaff(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_903")){
			inheritRole(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_904")){
			dispacthRole(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_905")){
			modStaffRole(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_906")){
			getStaffRole(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_907")){
			modSiteRole(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_997")){
			opOutSystem(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_020")){
			insertData(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_021")){
			deleteData(aDict);
		}else if(service_name.equalsIgnoreCase("MSM_022")){
			selectMyFun(aDict);
		}

      }catch(SQLException e){
         throw new FrameException(-2212081, "出现SQL异常！", e.getMessage());
      }finally{
         try{
            if(rs!=null){
               rs.getStatement().close();
               rs.close();
            }
         }catch(SQLException e){e.printStackTrace();}
      }
      return 0;
   }

   private void getLogger(){
		if(m_Logger==null)
			m_Logger = Logger.getLogger(getClass().getName()); 
	}

   public void opOutSystem(DynamicDict aDict) throws FrameException, SQLException{
      String type =(String)aDict.getValueByName("type");
      if(type.equals("1")){
         //网上营业厅系统
         String cust =(String)aDict.getValueByName("cust");
         sql ="select count(1) nums from tcm_cust where net_cust_id='"+cust+"' and state='1'";
         rs =Dao.select(sql, aDict.GetConnection());
         rs.next();
         if(rs.getInt("nums") ==1){
            String role_id =(String)aDict.getValueByName("role_id");
            TsmStaff ts =new TsmStaff();
            Vector fun =ts.getRoleFun(role_id, aDict.GetConnection());
            aDict.setValueByName("staff_fun", fun);
         }else{
            throw new FrameException(-2212087, "网上营业厅用户标示有误！");
         }
      }
   }

   public void addMessage(DynamicDict aDict) throws FrameException, SQLException{
	   getLogger();
      String sendstaff_no =(String)aDict.getValueByName("SENDSTAFF_NO");
      String sendsite_no =(String)aDict.getValueByName("SENDSITE_NO");
      String sendbureau_no =(String)aDict.getValueByName("SENDBUREAU_NO");
      String sends =(String)aDict.getValueByName("sendstaffs");
      String system_name =(String)aDict.getValueByName("SYSTEM_ENAME", false);
      String urge_type =(String)aDict.getValueByName("URGE_TYPE");
      String sendmessage =(String)aDict.getValueByName("SENDMESSAGE");
      String msg_type =(String)aDict.getValueByName("MSG_TYPE");
      String msg_begtime =(String)aDict.getValueByName("MSG_BEGTIME");
      String msg_endtime =(String)aDict.getValueByName("MSG_ENDTIME");

      String[] sendstaffs =Utility.split(sends, ".");
      Vector vSQL =new Vector();
      if(sendstaffs !=null){
         for(int i=0; i<sendstaffs.length; i++){
            if(sendstaffs[i].startsWith("M")){
               String[] info =Utility.split(sendstaffs[i].substring(1), ",");
               if(info.length ==3){
                  String bureau_no =info[0];
                  String site_no =info[1];
                  String staff_no =info[2];
                  String sql ="insert into tsm_message(messageid,sendbureau_no,sendsite_no,"
                     +" sendstaff_no,system_ename,mess_give_time,urge_type,sendmessage,"
                     +" staff_no,state,bureau_no,site_no, msg_type, msg_begtime, msg_endtime) values(LTRIM(TO_CHAR(S_SM_TSMMESSAGE.NEXTVAL,'0000000000000000')),"
                     +"'"+sendbureau_no+"','"+sendsite_no+"','"+sendstaff_no+"','"+system_name+"',"
                     +"sysdate,'"+urge_type+"',substr('"+sendmessage+"',1,900),'"+staff_no+"','1','"
                     +bureau_no+"','"+site_no+"',"+msg_type+","
                     +"to_date('"+msg_begtime+"','yyyy-mm-dd'), to_date('"+msg_endtime+"','yyyy-mm-dd'))";
                  vSQL.add(sql);
                  m_Logger.debug("system=sql="+sql);
               }
            }
         }
      }
      Dao.change(vSQL, aDict.GetConnection());
      //aDict.setValueByName("vsql", vSQL);
   }
   
/*
    public void modMessage(DynamicDict aDict) throws FrameException, SQLException{
      String staff_no =(String)aDict.getValueByName("staff_no");
      String msg_id =(String)aDict.getValueByName("messageid");
      sql ="select * from tsm_message where 2<1";
      rs =Dao.select(sql, aDict.GetConnection());
      ResultSetMetaData rsmd =rs.getMetaData();
      ArrayList arr =new ArrayList();
      for(int i=1; i<=rsmd.getColumnCount(); i++){
         arr.add(rsmd.getColumnName(i).toLowerCase());
      }
      sql ="select auth_level from tsm_staff where staff_no='"+staff_no+"'";
      rs =Dao.select(sql, aDict.GetConnection());
      if(rs.next()){
         int auth_level =rs.getInt("auth_level");
         //如员工表级别为系统管理员（auth_level=9），则修改时where 条件不带员工号
         String where ="1=1";
         if(auth_level ==9){
            where +=" and messageid='"+msg_id+"'";
         }else{
            where +=" and messageid='"+msg_id+"' and staff_no='"+staff_no+"'";
         }
         HashMap updateValue =new HashMap();
         String value ="";
         for(int i=0; i<arr.size(); i++){
            value =(String)aDict.getValueByName(arr.get(i).toString(), false);
            if(! value.equals(""))
               updateValue.put(arr.get(i), value);
         }
         if(updateValue.size() >0){
            Dao.update("tsm_message", where, updateValue, aDict.GetConnection());
         }
      }else{
         throw new FrameException(-2212086, "系统不允许修改消息信息！");
      }
   }
*/
   public void changeMessage(DynamicDict aDict) throws FrameException, SQLException{
		String messageid =(String)aDict.getValueByName("messageid");
		Vector vSQL =new Vector();
		vSQL.add("update tsm_message set state=2"
				+" where messageid="
				+messageid);
		Dao.change(vSQL, aDict.GetConnection());
	}

   public void modSiteRole(DynamicDict aDict) throws FrameException, SQLException{
	   getLogger();
      String site_no =(String)aDict.getValueByName("site_no");
      sql ="select bureau_no from tsm_site where site_no='"+site_no+"'";
      rs =Dao.select(sql, aDict.GetConnection());
      if(rs.next()){
         String bureau_no =rs.getString("bureau_no");
         Vector vSQL =new Vector();
         sql ="delete from tsm_site_role where site_no='"+site_no+"'";
         vSQL.add(sql);
         m_Logger.debug("modSiteRole-sql="+sql);

         String role =(String)aDict.getValueByName("roles", false);
         if(!role.equals("")){
            String[] roles =Utility.split(role, ",");
            for( int i=0; i<roles.length; i++){
              vSQL.add("insert into tsm_site_role(bureau_no, role_id, site_no) values('"
                 +bureau_no+"','"+roles[i]+"','"+site_no+"')");
            }
         }
         Dao.change(vSQL, aDict.GetConnection());
      }else{
         throw new FrameException(-2212084, "没有此操作点信息！");
      }
   }

   public void getStaffRole(DynamicDict aDict) throws FrameException, SQLException{
      String bureau_no =(String)aDict.getValueByName("bureau_no", false);
      String site_no   =(String)aDict.getValueByName("site_no", false);
      String staff_no  =(String)aDict.getValueByName("staff_no", false);
      String admstaff_no  =(String)aDict.getValueByName("admstaff_no", false);
      String sql_own="";
      
      //取操作人拥有的角色
      if(!staff_no.equals("")){
         sql_own ="select role_id from tsm_staff_role where staff_no='"+staff_no+"'";
      }else if(!site_no.equals("")){
         sql_own ="select role_id from tsm_site_role where site_no='"+site_no+"'";
      }else{
         return;
      }

      rs =Dao.select(sql_own, aDict.GetConnection());
      ArrayList role_ids =new ArrayList();
      while(rs.next()){
         role_ids.add(rs.getString("role_id"));
      }
      
      //取可分配的角色,如果是系统管理员，取全部角色，否则取自己拥有的角色及自己建立的角色
      //非系统级，取被授于的角色
      sql ="select '0' from_site,c.role_id, c.bureau_no, c.role_name, c.role_desc, c.site_no, c.staff_no, c.role_time from tsm_staff_role a,tsm_staff b,tsm_role c where a.staff_no=b.staff_no and auth_level<>'9' and a.role_id=c.role_id and a.staff_no='"+admstaff_no+"'";
      if(!staff_no.equals("")) {
      //不分级别，取被授于的角色-继承操作点
      sql =sql+" union select '1' from_site,c.role_id, c.bureau_no, c.role_name, c.role_desc, c.site_no, c.staff_no, c.role_time from tsm_site_role  a,tsm_staff b,tsm_role c where a.site_no=b.site_no and a.role_id=c.role_id and b.staff_no='"+staff_no+"'";
      }
      //非系统级，取自己建立的角色
      sql =sql + " union select '0',c.role_id, c.bureau_no, c.role_name, c.role_desc, c.site_no, c.staff_no, c.role_time from tsm_staff b,tsm_role c where b.auth_level<>'9' and b.staff_no=c.staff_no and c.staff_no ='"+admstaff_no+"'";
 	  //系统级，取所有角色
 	  sql =sql + " union select '0',role_id, b.bureau_no, role_name, role_desc, a.site_no, a.staff_no, role_time from tsm_role a,tsm_staff b where auth_level='9' and b.staff_no='"+admstaff_no+"'";
	  sql = "select a.*,decode(a.from_site,'1','1',nvl2(b.role_id,'1','0')) own from ("+sql+") a,("+sql_own+") b where a.role_id=b.role_id(+) order by decode(a.from_site,'1','0','1'),nvl2(b.role_id,'0','1'),a.role_name";
      rs =Dao.select(sql, aDict.GetConnection());
      ArrayList roles =new ArrayList();
      HashMap tmp;
      String role_id;
      boolean has =false;
      while(rs.next()){
         tmp =new HashMap();
         role_id =rs.getString("role_id");
         tmp.put("role_id",role_id);
         if(rs.getString("bureau_no") !=null)
            tmp.put("bureau_no",rs.getString("bureau_no"));
         if(rs.getString("role_name") !=null)
            tmp.put("role_name",rs.getString("role_name"));
         if(rs.getString("role_desc") !=null)
            tmp.put("role_desc",rs.getString("role_desc"));
         if(rs.getString("site_no") !=null)
            tmp.put("site_no",rs.getString("site_no"));
         if(rs.getString("staff_no") !=null)
            tmp.put("staff_no",rs.getString("staff_no"));
         if(rs.getString("role_time") !=null)
            tmp.put("role_time",rs.getString("role_time"));
         //for( int i=0; i<role_ids.size(); i++){
         //   if(role_ids.get(i).toString().trim().equals(role_id.trim())){
         //      has =true;
         //      break;
         //   }
         //}
         //if(has){
         //   tmp.put("own", "1");
         //}else
         //   tmp.put("own", "0");
         //has =false;
         if(rs.getString("own") !=null)
            tmp.put("own",rs.getString("own"));
         if(rs.getString("from_site") !=null)
            tmp.put("from_site",rs.getString("from_site"));
         roles.add(tmp);
      }
      aDict.setValueByName("role_list", roles);
   }

   public void modStaffRole(DynamicDict aDict) throws FrameException, SQLException{
	   getLogger();
      String staff_no =(String)aDict.getValueByName("staff_no");
      sql ="select bureau_no, site_no from tsm_staff where staff_no='"+staff_no+"'";
      rs =Dao.select(sql, aDict.GetConnection());
      if(rs.next()){
         String bureau_no =rs.getString("bureau_no");
         String site_no =rs.getString("site_no");
         Vector vSQL =new Vector();
         sql ="delete from tsm_staff_role where staff_no='"+staff_no+"'";
         m_Logger.debug("modStaffRole-sql="+sql);
         vSQL.add(sql);
         String role =(String)aDict.getValueByName("roles", false);
         if(!role.equals("")){
            String[] roles =Utility.split(role, ",");
            for( int i=0; i<roles.length; i++){
              vSQL.add("insert into tsm_staff_role(bureau_no, role_id, site_no, staff_no) values('"
                 +bureau_no+"','"+roles[i]+"','"+site_no+"','"+staff_no+"')");
            }
         }
         Dao.change(vSQL, aDict.GetConnection());
      }else{
         throw new FrameException(-2212084, "没有此人信息！");
      }
   }

   public void dispacthRole(DynamicDict aDict) throws FrameException, SQLException{
      String site_no =(String)aDict.getValueByName("site_no");
      Connection conn =aDict.GetConnection();

      //取该操作点下所有的角色
      sql ="select role_id ,bureau_no from tsm_site_role where site_no='"+site_no+"'";
      rs =Dao.select(sql, aDict.GetConnection());

      ResultSet rsTmp =null;
      //取该操作点下的所有员工
      sql ="select staff_no from tsm_staff where site_no='"+site_no+"'";
      rsTmp =Dao.select(sql, conn);
      ArrayList staffs =new ArrayList();
      while(rsTmp.next()){
         staffs.add(rsTmp.getString("staff_no"));
      }

      sql ="select staff_no from tsm_staff_role where role_id=?";
      PreparedStatement pst =conn.prepareStatement(sql);
      Vector vSQL =new Vector();
      String staff_no;
      while(rs.next()){
         String role_id =rs.getString("role_id");
         String bureau_no =rs.getString("bureau_no");
         pst.setString(1,role_id);
         rsTmp =pst.executeQuery();
         ArrayList insertStaffs =(ArrayList)staffs.clone();//存储不拥有role_id的员工编号
         while(rsTmp.next()){
            staff_no =rsTmp.getString("staff_no");
            for(int i=0; i<staffs.size(); i++){
               if(staff_no.equals(staffs.get(i).toString()))
                  insertStaffs.remove(staff_no);
            }
         }
         for(int i=0; i<insertStaffs.size(); i++){
            sql ="insert into tsm_staff_role(site_no, staff_no, role_id, bureau_no) values("
               +"'"+site_no+"','"+insertStaffs.get(i)+"','"+role_id+"','"+bureau_no+"')";
            vSQL.add(sql);
         }
      }
      if(rsTmp !=null)
         rsTmp.close();
      if(pst !=null)
         pst.close();
      //logger.info(vSQL);
      Dao.change(vSQL, conn);
   }

    public void inheritRole(DynamicDict aDict) throws FrameException, SQLException{
      String staff_no =(String)aDict.getValueByName("staff_no");

      Connection conn =aDict.GetConnection();
      //选取操作点角色列表
      sql ="select a.role_id from tsm_site_role a, tsm_staff b where "
         +" a.site_no=b.site_no and b.staff_no='"+staff_no+"'";
      rs =Dao.select(sql, conn);
      ArrayList site_roles =new ArrayList();//员工角色列表
      while(rs.next())
         site_roles.add(rs.getString("role_id"));

      sql ="select bureau_no, site_no from tsm_staff where staff_no='"+staff_no+"'";
      rs =Dao.select(sql, aDict.GetConnection());
      Vector vSQL =new Vector();
      if(rs.next()){
         String bureau_no =rs.getString("bureau_no");
         String site_no =rs.getString("site_no");
         sql ="select role_id from tsm_staff_role where staff_no='"+staff_no+"'";
         rs =Dao.select(sql, conn);
         String role_id;
         while(rs.next()){
            role_id =rs.getString("role_id");
            for(int i=0; i<site_roles.size(); i++){
               if(role_id.equals(site_roles.get(i).toString()))
                  site_roles.remove(role_id);
            }
         }
         for(int i=0; i<site_roles.size(); i++){
            vSQL.add("insert into tsm_staff_role(site_no, staff_no, role_id, bureau_no) values("
               +"'"+site_no+"','"+staff_no+"','"+site_roles.get(i)+"','"+bureau_no+"')");
         }
         Dao.change(vSQL, conn);
      }else{
         throw new FrameException(-2212083, "没有此人信息！");
      }
   }

   public void modRoleStaff(DynamicDict aDict) throws FrameException, SQLException{
      String role_id =(String)aDict.getValueByName("role_id");
      Vector vSQL =new Vector();
      String role_staffs =(String)aDict.getValueByName("role_staffs");
      String role_to_staffs[] =Utility.split(role_staffs, ",");
      //for(int
   }

	public void maintainRole(DynamicDict aDict) throws FrameException, SQLException{
		String act_flag =(String)aDict.getValueByName("act_flag", false);
		//logger.info("act_flag="+act_flag);
		if(act_flag.equals("")){//插入、修改
	        String role_id =(String)aDict.getValueByName("role_id", false);
	        String bureau_no =(String)aDict.getValueByName("bureau_no");
	        String role_name =(String)aDict.getValueByName("role_name");
	        String site_no	=(String)aDict.getValueByName("site_no");
	        String staff_no =(String)aDict.getValueByName("staff_no");
	        String role_desc =(String)aDict.getValueByName("role_desc", false);
	
	        HashMap role =new HashMap();
	        role.put("bureau_no",bureau_no);
	        role.put("site_no",site_no);
	        role.put("staff_no",staff_no);
	        role.put("role_name",role_name);
			if(role_id.equals("")){
				//添加
				sql ="select max(role_id) as max_role_id from tsm_role";
				rs =Dao.select(sql, aDict.GetConnection());
				if(rs.next()){
					role_id =String.valueOf(rs.getInt(1)+1);
					role.put("role_id", role_id);
					if(!role_desc.equals("")) role.put("role_desc", role_desc);
					role.put("role_time",Utility.dateToString(new java.util.Date(), "yyyy-MM-dd hh:mm:ss"));
					//插入角色信息
					Dao.insert("tsm_role", role, aDict.GetConnection());
				}
			}else{
				//修改角色信息
				role.put("role_time",Utility.dateToString(new java.util.Date(), "yyyy-MM-dd hh:mm:ss"));
				role.put("role_desc", role_desc);
				//logger.info(role);
				Dao.update("tsm_role", "role_id='"+role_id+"'", role, aDict.GetConnection());
				//修改角色功能模块对应信息
			}
			
		}else if(act_flag.equals("1")){//删除
			String role_id =(String)aDict.getValueByName("role_id");
			Vector vSQL =new Vector();
			vSQL.add("DELETE FROM tsm_role_fun WHERE role_id='"+role_id+"'");
			vSQL.add("DELETE FROM tsm_role WHERE role_id='"+role_id+"'");
			Dao.change(vSQL, aDict.GetConnection());
			
		}else if(act_flag.equals("2")){
			String role_id 		= (String)aDict.getValueByName("role_id");
			//添加角色功能对应信息
			Vector vSQL =new Vector();
	        //开始删除所有角色功能模块对应信息			
	        vSQL.add("DELETE FROM tsm_role_fun WHERE role_id='"+role_id+"'");
	        if (!aDict.getValueByName("params").equals("")){
				String[] _params = Utility.split((String)aDict.getValueByName("params"),"\n");	        			
				if(_params.length > 0){
		            //添加角色功能模块信息
		            for( int i=0; i<_params.length; i++){
		            	String[] _items	= Utility.split(_params[i], ",");        	
		              	vSQL.add("INSERT INTO tsm_role_fun (role_id, fun_id, type) VALUES "+
		              			 "('"+role_id+"',"+_items[0]+","+_items[1]+")");
		            }
				}

			}
			Dao.change(vSQL, aDict.GetConnection());			
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////
//维护操作人禁用功能
////////////////////////////////////////////////////////////////////////////////////////////
	public void MSM_106(DynamicDict aDict) throws FrameException, SQLException{
		String staff_no	= (String)aDict.getValueByName("staff_no");
		Vector vSQL =new Vector();
	
        vSQL.add("DELETE FROM tsm_staff_fun WHERE staff_no='"+staff_no+"'");
        if (!aDict.getValueByName("params").equals("")){
			String[] _params = Utility.split((String)aDict.getValueByName("params"),";");	        			
			if(_params.length > 0){
	            //添加操作人禁用功能模块信息
	            for( int i=0; i<_params.length; i++){
	            	String[] _items	= Utility.split(_params[i], ","); 
	              	vSQL.add("INSERT INTO tsm_staff_fun (staff_no, fun_id,fun_type,priv_type)  VALUES('"
	              			 +staff_no+"',"+_items[0]+","+_items[1]+",'0')");
	            }
			}
		}
		Dao.change(vSQL, aDict.GetConnection());					
	}
/////////////////////////////////////////////////////////////////////////////////////////////	
    public void getStaffFunList(DynamicDict aDict) throws FrameException, SQLException{
		//系统级人员可取所有功能
		//其它级人员可取自己可操作功能
		String staff_no =(String)aDict.getValueByName("staff_no");
		sql	= 	"SELECT auth_level FROM tsm_staff WHERE staff_no='"+staff_no+"'";
		rs	= 	Dao.select(sql, aDict.GetConnection());
		if(rs.next()){
			int auth_level	= rs.getInt(1);
			String role_id	= (String)aDict.getValueByName("role_id");
			if (role_id.equals("")) role_id = "0";
			switch(auth_level){
			case 9:
				sql	=	"SELECT up_fun_id, fun_id, fun_name, '0' AS type "+
						"  FROM tsm_fun "+
						" START WITH fun_id IN ( "+
						"			   SELECT fun_id "+
						"				 FROM tsm_fun "+
						"				WHERE state = '1' "+
						"				MINUS "+
						"			   SELECT fun_id "+
						"				 FROM tsm_role_fun "+
						"			    WHERE TYPE = '0' "+
						"				  AND role_id = "+role_id+" "+
						"				UNION "+
						"             (SELECT fun_id "+
						"                FROM tsm_sub_fun "+
						"               WHERE state = '1' "+
						"               MINUS "+
						"              SELECT b.fun_id "+
						"                FROM tsm_role_fun a, tsm_sub_fun b "+
						"               WHERE TYPE = '1' "+
						"                 AND a.role_id = "+role_id+")) "+
						"CONNECT BY fun_id = PRIOR up_fun_id "+
						"UNION "+
						"SELECT fun_id, sub_fun_id, sub_fun_name, '1' AS type "+
						"  FROM tsm_sub_fun "+
						" WHERE sub_fun_id IN (SELECT sub_fun_id "+
						"                        FROM tsm_sub_fun "+
						"                       WHERE state = '1' "+
						"                       MINUS "+
						"                      SELECT fun_id "+
						"                        FROM tsm_role_fun "+
						"                       WHERE TYPE = '1' "+
						"                         AND role_id = "+role_id+") "+
						"ORDER BY up_fun_id ASC, type DESC, fun_id ASC";
				break;
			default:
				   	sql =	"SELECT distinct up_fun_id, fun_id, fun_name, '0' AS type "+
						"  FROM tsm_fun "+
						" START WITH fun_id IN ( "+
						"               SELECT a.fun_id "+
						"                 FROM tsm_fun a, tsm_role_fun b, tsm_staff_role c "+
						"                WHERE a.fun_id = b.fun_id "+
						"                  AND b.TYPE = '0' "+
						"                  AND b.role_id = c.role_id "+
						"                  AND c.staff_no = '"+staff_no+"' "+
						"                  AND a.state = '1' "+
						"                MINUS "+
						"               SELECT fun_id "+
						"                 FROM tsm_role_fun "+
						"                WHERE TYPE = '0' "+
						"                  AND role_id = "+role_id+" "+
						"                UNION "+
						"              (SELECT a.fun_id "+
						"                 FROM tsm_sub_fun a, tsm_role_fun b, tsm_staff_role c "+
						"                WHERE a.sub_fun_id = b.fun_id "+
						"                  AND b.TYPE = '1' "+
						"                  AND b.role_id = c.role_id "+
						"                  AND c.staff_no = '"+staff_no+"' "+
						"                  AND a.state = '1' "+
						"                MINUS "+
						"               SELECT b.fun_id "+
						"                 FROM tsm_role_fun a, tsm_sub_fun b "+
						"                WHERE TYPE = '1' "+
						"                  AND a.role_id = "+role_id+")) "+
						"CONNECT BY fun_id = PRIOR up_fun_id "+
						"ORDER BY up_fun_id ASC, type DESC, fun_id ASC";
						
				break;
			}
			rs = Dao.select(sql, aDict.GetConnection());
			aDict.setValueByName("fun_list", rs);
		}
 	}

	//取角色信息
	public void getStaffRoleList(DynamicDict aDict) throws FrameException, SQLException{
		String staff_no		= (String)aDict.getValueByName("staff_no");
		String site_no 		= (String)aDict.getValueByName("site_no");
		String bureau_no 	= (String)aDict.getValueByName("bureau_no");
		sql = "Select auth_level From tsm_staff Where staff_no='"+staff_no+"'";
		rs	= Dao.select(sql, aDict.GetConnection());
		if(rs.next()){
			int auth_level = rs.getInt(1);
			switch(auth_level){
			case 1://成功返回
			   sql = "";
			   break;
			case 9:
			   sql = "Select a.role_id,role_name,c.city_name,b.staff_name "+
			   		 "From tsm_role a,tsm_staff b,tvlsm_city_info c "+
			   		 "Where a.staff_no=b.staff_no(+) "+
			   		 "And substr(a.bureau_no,4,2)=c.city_no(+) "+
			   		 "Order By c.city_name,role_name";
			   break;
			default:
			   sql = "Select a.role_id,role_name,c.city_name,b.staff_name "+
			   		 "From tsm_role a,tsm_staff b,tvlsm_city_info c "+
			   		 "Where a.staff_no=b.staff_no "+
			   		 "And substr(b.bureau_no,4,2)=c.city_no "+
			   		 "And b.staff_no='"+staff_no+"' "+
			   		 "Order By role_name";
			}
			if(!sql.equals("")){
				rs = Dao.select(sql, aDict.GetConnection());
				aDict.setValueByName("role_list", rs);
			}
		}
	}


   public void maintainMenu(DynamicDict aDict) throws FrameException, SQLException{
		HashMap menu = new HashMap();
		if(!aDict.getValueByName("fun_id", false).equals("")) {//客户端传过来菜单编号，表示修改
			//修改
			menu.put("fun_id", aDict.getValueByName("fun_id"));
			menu.put("fun_name", aDict.getValueByName("fun_name"));
			menu.put("up_fun_id", aDict.getValueByName("up_fun_id"));
			menu.put("dis_order", aDict.getValueByName("dis_order", false));
			menu.put("state", aDict.getValueByName("state", false));
			menu.put("menu_url", aDict.getValueByName("menu_url", false));
			Dao.update("tsm_fun","fun_id='"+aDict.getValueByName("fun_id")+"'", menu, aDict.GetConnection());
		}else{
			//插入
			menu.put("fun_name", aDict.getValueByName("fun_name"));
			menu.put("up_fun_id", aDict.getValueByName("up_fun_id"));
			menu.put("dis_order", aDict.getValueByName("dis_order", false));
			menu.put("state", aDict.getValueByName("state", false));
			menu.put("menu_url", aDict.getValueByName("menu_url", false));
			menu.put("type","1");
			//取最大菜单号
			sql ="SELECT max(fun_id) AS max_fun_id FROM tsm_fun";
			rs =Dao.select(sql, aDict.GetConnection());
			int fun_id =1;
			if(rs.next()) fun_id =rs.getInt(1) +1;
			menu.put("fun_id", String.valueOf(fun_id));
			Dao.insert("tsm_fun", menu, aDict.GetConnection());
		}
   }

	public void delMenu(DynamicDict aDict) throws FrameException, SQLException{
		String param	= (String)aDict.getValueByName("fun_id");
		Vector funId 	= new Vector();
		if (param != null && !param.equals("")) funId.add(param);
		delMenuRound(aDict.GetConnection(), funId);
	}

	private void delMenuRound(Connection conn, Vector funId) throws SQLException{
		int count		= funId.size();
		if (count > 0){
			//delete func and subfunc by fun_id
			Vector vSQL	= new Vector();
			for (int i = 0; i < count; i++){
		        vSQL.add("delete from tsm_sub_fun where fun_id='"+funId.get(i)+"'");
		        vSQL.add("delete from tsm_fun where fun_id='"+funId.get(i)+"'");
			}
			Dao.change(vSQL, conn);
			//get sub func
			Vector _items	= new Vector();
			sql ="select up_fun_id,fun_id from tsm_fun order by up_fun_id,fun_id";
			ResultSet rs	= Dao.select(sql, conn);
			while (rs.next()){
				String upId = rs.getString(1);
				for (int m = 0; m < count; m++){
					if (funId.get(m).equals(upId)) _items.add(rs.getString(2));
				}
			}
			delMenuRound(conn, _items);
		}
	}
	

	//删除角色 2003-8-28 byshawn
	public void role_del(DynamicDict aDict) throws FrameException, SQLException{
		String role_id =(String)aDict.getValueByName("role_id");
		Vector vSQL =new Vector();
		vSQL.add( "delete tsm_staff_role where role_id="+role_id);
		vSQL.add( "delete tsm_site_role where role_id="+role_id);
		vSQL.add( "delete tsm_role where role_id="+role_id);
		vSQL.add( "delete tsm_role_fun where role_id="+role_id);
		vSQL.add( "delete tsm_role_fun_data where role_id="+role_id);
		Dao.change(vSQL, aDict.GetConnection());
	}
	
	public void maintainSubFun(DynamicDict aDict) throws FrameException, SQLException{
		HashMap menu = new HashMap();
		if(!aDict.getValueByName("sub_fun_id", false).equals("")) {
			//客户端传过来菜单编号，表示修改
			//修改
			menu.put("field_name",	aDict.getValueByName("field_name"));			
			menu.put("sub_fun_name",aDict.getValueByName("sub_fun_name"));
			menu.put("sub_fun_desc",aDict.getValueByName("sub_fun_desc",false));
			menu.put("state",		aDict.getValueByName("state"));
			Dao.update("tsm_sub_fun","sub_fun_id='"+aDict.getValueByName("sub_fun_id")+"'", menu, aDict.GetConnection());
		}else{
			//取最大菜单号			
			sql = "SELECT max(sub_fun_id) AS sub_fun_id FROM tsm_sub_fun";
			rs	= Dao.select(sql, aDict.GetConnection());
			int sub_fun_id = 1;
			if(rs.next()) sub_fun_id = rs.getInt(1)+1;
			//插入
			menu.put("fun_id",		aDict.getValueByName("fun_id"));
			menu.put("sub_fun_id",	String.valueOf(sub_fun_id));	
			menu.put("field_name",	aDict.getValueByName("field_name"));	
			menu.put("sub_fun_name",aDict.getValueByName("sub_fun_name"));
			menu.put("sub_fun_desc",aDict.getValueByName("sub_fun_desc",false));
			menu.put("state",		aDict.getValueByName("state"));
			Dao.insert("tsm_sub_fun", menu, aDict.GetConnection());
		}
	}

	public void delSubFun(DynamicDict aDict) throws FrameException, SQLException{
		String sub_fun_id	=(String)aDict.getValueByName("sub_fun_id");
		Vector vSQL			=new Vector();
		vSQL.add("DELETE FROM tsm_sub_fun WHERE sub_fun_id='"+sub_fun_id+"'");
		Dao.change(vSQL, aDict.GetConnection());
	}
	
	/******************************************************************************************************************
	MSM_912:   角色数据配置-查询、修改
	
	输入输出数据结构：
	输入：
	变量名		类型	长度	名称		描述
	action_flag	String	1       动作类型	0:select 1:update
	role_id		String	8		角色ID
	fun_id		String	8		FUN_ID
	data_type	String	8		类型
	params		String	-	    参数
	
	返回信息: < 0 操作失败 >= 0 操作成功
	******************************************************************************************************************/
	public void MSM_912(DynamicDict aDict) throws FrameException, SQLException{
		String action_flag	= (String)aDict.getValueByName("action_flag", false);
		String role_id		= (String)aDict.getValueByName("role_id", false);
		String fun_id		= (String)aDict.getValueByName("fun_id", false);
		String data_type	= (String)aDict.getValueByName("data_type", false);
		if (action_flag.equals("0")){
			//查询
			String sql = "SELECT * "+
						 "FROM tsm_role_fun_data "+
						 "WHERE role_id	='"+role_id+"' "+
						 "AND fun_id	='"+fun_id+"' "+
						 "AND data_type	='"+data_type+"' "+
						 "ORDER BY up_data,data";
			rs = Dao.select(sql, aDict.GetConnection());
			aDict.setValueByName("MSM_912", rs);
		}else if(action_flag.equals("1")){
			//修改
			Vector vSQL = new Vector();		
	        vSQL.add("DELETE FROM tsm_role_fun_data WHERE role_id='"+role_id+"' AND fun_id='"+fun_id+"' AND data_type ='"+data_type+"'");
	        if (!aDict.getValueByName("params").equals("")){
				String[] _params = Utility.split((String)aDict.getValueByName("params"),"\n");       			
				String	 sql_val = "'"+role_id+"','"+fun_id+"',"+data_type;
				if(_params.length > 0){
		            //添加角色功能模块信息
		            for( int i=0; i<_params.length; i++){
		            	String[] _items	= Utility.split(_params[i], ",");
		              	vSQL.add("INSERT INTO tsm_role_fun_data "+
		              			 "(role_id,fun_id,data_type,data,up_data,data_name) VALUES "+
		              			 "("+sql_val+",'"+_items[0]+"','"+_items[1]+"','"+_items[2].trim()+"')");
		            }
				}
			}
			Dao.change(vSQL, aDict.GetConnection());
		}
	}
	
	/******************************************************************************************************************
	MSM_913:    根椐数据类型取得配置数据列表
	
	输入输出数据结构：
	输入：
	变量名		类型	长度	名称		描述
	data_type	String	8		类型
	
	返回信息: < 0 操作失败 >= 0 操作成功
	******************************************************************************************************************/
	public void MSM_913(DynamicDict aDict) throws FrameException, SQLException{
		String data_type = (String)aDict.getValueByName("data_type",false);
		if(data_type != null && !data_type.equals("")){
			rs = Dao.select("SELECT data_sql FROM tsm_data WHERE data_type="+data_type,aDict.GetConnection());
			if (rs.next()){
				rs = Dao.select(rs.getString(1), aDict.GetConnection());
				aDict.setValueByName("MSM_913", rs);
			}
		}
	}

	public void insertData(DynamicDict aDict) throws FrameException, SQLException{
		String staff_no =(String)aDict.getValueByName("staff_no");
		String fun_id =(String)aDict.getValueByName("fun_id");
		String auto_open =(String)aDict.getValueByName("auto_open");
		String disp_order =(String)aDict.getValueByName("disp_order");
		Vector vSQL =new Vector();

		String sql="select * from tsm_staff_myfun where staff_no='"
				  +staff_no
				  +"' and fun_id="
				  +fun_id;
		rs=Dao.select(sql, aDict.GetConnection());
		if (rs.next())
		{
			vSQL.add("update tsm_staff_myfun set auto_open='"
					+auto_open
					+"',disp_order='"
					+disp_order
					+"' where staff_no='"
					+staff_no
					+"' and fun_id="
					+fun_id);
		}else{

			vSQL.add("insert into tsm_staff_myfun values('"
					+staff_no
					+"',"
					+fun_id
					+",'"
					+auto_open
					+"','"
					+disp_order
					+"')");
		}
		Dao.change(vSQL, aDict.GetConnection());
	}

	public void deleteData(DynamicDict aDict) throws FrameException, SQLException{
		String staff_no =(String)aDict.getValueByName("staff_no");
		String fun_id =(String)aDict.getValueByName("fun_id");
		Vector vSQL =new Vector();
		vSQL.add("delete from tsm_staff_myfun where staff_no='"
				+staff_no
				+"' and fun_id="
				+fun_id);
		
		Dao.change(vSQL, aDict.GetConnection());
	}

//取用户定制的自动打开的常用功能
	
	private void selectMyFun(DynamicDict aDict) throws FrameException,SQLException{
	  String staff_no =(String)aDict.getValueByName("staff_no");
	  ResultSet rs =null;

      String sql ="SELECT a.menu_url FROM tsm_fun a,TSM_STAFF_MYFUN b WHERE a.fun_id=b.fun_id "
				 +"AND a.state=1 AND b.auto_open='1' and b.staff_no='"
				 +staff_no
				 +"' order by b.disp_order";
      rs =Dao.select(sql, aDict.GetConnection());
      ArrayList things =new ArrayList();
	  while(rs.next()){
            HashMap thing =new HashMap();
			String menu_url=rs.getString("MENU_URL");
				
			thing.put("MENU_URL",menu_url);
				
			things.add(thing);
			
	  }
	  if(rs !=null){rs.getStatement().close(); rs.close(); rs=null;}
	  if(things !=null && things.size() >0)
         aDict.setValueByName("things", things);
  }
}

