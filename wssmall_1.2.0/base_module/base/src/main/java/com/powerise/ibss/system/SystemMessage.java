//PowerIBSS
//系统管理
//消息公告管理
package com.powerise.ibss.system;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.util.Utility;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
public class SystemMessage implements IAction{
   private static Logger m_Logger = null;
   private String service_name;
   private String sql;
   private ResultSet rs;
   @Override
public int perform(DynamicDict aDict) throws FrameException {
      service_name =aDict.getServiceName();
	  Utility.println("调用类SystemMessage.........."+service_name);
      try{
		if(service_name.equalsIgnoreCase("MSM_200")){
			//功能菜单维护-添加、修改
			MSM_200(aDict);
		}else if (service_name.equalsIgnoreCase("MSM_074")){
			//公告修改
			MSM_074(aDict);
		}
      }catch(SQLException e){
         //throw new FrameException(-2212081, "出现SQL异常！", e.getMessage(e));
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

   public void MSM_200(DynamicDict aDict) throws FrameException, SQLException{
	  
      String staff_no =(String)aDict.getValueByName("STAFF_NO");
      String remind_days =(String)aDict.getValueByName("REMIND_DAYS");
      String exp_date =(String)aDict.getValueByName("EXP_DATE");
      String content =(String)aDict.getValueByName("CONTENT");
      String city_no =(String)aDict.getValueByName("CITY_NO");
      String bureau_no =(String)aDict.getValueByName("BUREAU_NO", false);
      String type =(String)aDict.getValueByName("TYPE");
      
      Vector vSQL =new Vector();
	  getLogger(); 
      if(staff_no!=null){
          String sql ="insert into tsm_bulletin(id,staff_no,content,post_date,exp_date,remind_days,city_no,bureau_no,type)"
                     +" select nvl(id,1),'"
                     +staff_no+"','"
                     +content+"',"
                     +"sysdate,to_date('"
                     +exp_date+"','YYYY-MM-DD'),"
                     +remind_days+",'"
                     +city_no+"','"
                     +bureau_no+"','"
                     +type
                     +"'   from (select max(id)+1 id from tsm_bulletin)";
          vSQL.add(sql);
          m_Logger.debug("SystemMessage.MSM_200.sql="+sql);
      }
      Dao.change(vSQL, aDict.GetConnection());
   }


//公告修改
	public void MSM_074(DynamicDict aDict) throws FrameException, SQLException{
		
		String content =(String)aDict.getValueByName("CONTENT");
		String exp_date  =(String)aDict.getValueByName("EXP_DATE");
		String remind_days =(String)aDict.getValueByName("REMIND_DAYS");
		String city_no =(String)aDict.getValueByName("CITY_NO");
		String bureau_no =(String)aDict.getValueByName("BUREAU_NO");
		String id =(String)aDict.getValueByName("ID");
		Vector vSQL =new Vector();
		getLogger(); 
		String sql="update tsm_bulletin set content='"
				  +content
				  +"',exp_date=to_date('"
				  +exp_date
				  +"','YYYY-MM-DD'),remind_days="
				  +remind_days
				  +",city_no='"
				  +city_no
				  +"',bureau_no='"
				  +bureau_no
				  +"' where id="
				  +id;
		vSQL.add(sql);
        m_Logger.debug("SystemMessage.MSM_074.sql="+sql);
		Dao.change(vSQL, aDict.GetConnection());
	}
}
