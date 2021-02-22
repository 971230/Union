/**
   系统管理组件，实现以下方法：
   1.MRPS_01增加或修改市场预测基本信息
   2.MRPS_02删除市场预测记录基本信息
   3.MRPS_03增加或修改市场预测详细信息
   4.MRPS_04删除市场预测记录基本信息

*/
package com.powerise.ibss.system;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.util.Utility;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Srp implements IAction {
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
		Utility.println("调用类Srp.........." + service_name);
		try {
			__cn__ 	= aDict.GetConnection();
			__st__ 	= __cn__.createStatement();
			if (service_name.equalsIgnoreCase("MRPS_01")) {
				MRPS_01(aDict);
				} else if (service_name.equalsIgnoreCase("MRPS_02")) {
				MRPS_02(aDict);
				} else if (service_name.equalsIgnoreCase("MRPS_03")) {
				MRPS_03(aDict);
				} else if (service_name.equalsIgnoreCase("MRPS_04")) {
				MRPS_04(aDict);
				}
			}catch(SQLException sqle) {
				throw new FrameException(-22120109, "Srp.perform()出现SQL异常！", sqle.getMessage());
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


	/*******************************************************************************
    功能: 增加或修改市场预测基本信息
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称
	  (1)ACT_FLAG '0'_增加；'1'_修改
      (2)RP_ID      
      (3)NAME       
      (4)STAFF_NO    
      (5)STATE      
      (6)TPL_ID     输出参数: 无
    返回结果：
    RETCODE    long             返回结果

    作者：  
    编写日期：   2004-10-20
    修改者                    修改日期              修改内容
    /*******************************************************************************/
   public long MRPS_01(DynamicDict aDict) throws FrameException {

      String sOper_Flag;
      String sRP_ID="";
      
      String sNAME;
      String sSTAFF_NO;
      String sSTATE;
      String sTPL_ID;

      String sqlopen = "";
      long update_nums = 0;


      sOper_Flag = ((String) aDict.getValueByName("ACT_FLAG")).trim();
      if (sOper_Flag.equals("1"))sRP_ID = ((String) aDict.getValueByName("RP_ID")).trim();
      sNAME = ((String) aDict.getValueByName("NAME")).trim();
      sSTAFF_NO = ((String) aDict.getValueByName("STAFF-NO", false)).trim(); 
      sSTATE = ((String) aDict.getValueByName("STATE")).trim();
      sTPL_ID = ((String) aDict.getValueByName("TPL_ID")).trim();

      if (sOper_Flag.equals("1")) {/*修改*/
         try {
            sqlopen = "UPDATE TSR_RP_RECORD_MAIN" 
                    +" SET NAME='" + sNAME  
                    +"',state='"+ sSTATE 
                    +"',Staff_No='"+ sSTAFF_NO
                    +"',TPL_ID="+sTPL_ID
                    +"  WHERE  RP_ID=" + sRP_ID ;

            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120420, "Srp:MRPS_01 修改市场预测基本信息失败" + "sql语句是:" + sqlopen);
            }
            //writeStaffLog(sbureau,ssite,sstaff,"16","员工号："+sstaff+" （修改市场预测基本信息成功！",aDict.GetConnection());
         } catch (SQLException e) {
            throw new FrameException(-22120421, "Srp:MRPS_01 修改市场预测基本信息时出现异常\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sqlopen);
         }
      } else if (sOper_Flag.equals("0")) {//新增
         try {
            sqlopen = "INSERT INTO  TSR_RP_RECORD_MAIN (RP_ID,NAME,TPL_ID,STATE,STAFF_NO,OPER_DATE)"
                    +"  SELECT NVL(MAX(RP_ID),0)+1,'" + sNAME
                    +"',"+sTPL_ID
					+",'"+sSTATE 
                    +"','"+sSTAFF_NO
                    +"',SYSDATE from TSR_RP_RECORD_MAIN";
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120422, "Srp:MRPS_01 增加市场预测基本信息失败" + "sql语句是:" + sqlopen);
            }else{
           	 //writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" （增加市场预测基本信息信息成功！",aDict.GetConnection());
            }
         } catch (SQLException e) {
            throw new FrameException(-22120423, "Srp:MRPS_01 增加市场预测基本信息时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen);
         }
      }
      return 0;
   }
   /***********************************************************************************************************
    功能:      删除市场预测基本信息

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名           类型      长度   名称
    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0            删除成功
    -1              删除失败:所传入的参数可能为空

    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MRPS_02(DynamicDict aDict) throws FrameException, SQLException {
	  getLogger();
      if (!aDict.getValueByName("RP_ID").equals("")) {
         String sRP_ID = ((String) aDict.getValueByName("RP_ID")).trim();
         String sql1 = "DELETE FROM TSR_RP_RECORD_MAIN WHERE RP_ID  = "+ sRP_ID;

         __st__.executeUpdate(sql1);
         return 0;

      } else {
         m_Logger.fatal("MRPS_02:删除市场预测基本信息失败---所传入的参数可能为空");
         return -1;
      }
   }   
   /*******************************************************************************
    功能: 增加或修改市场预测详细信息
    输入参数:
    格式：    　　　　
    变量名           类型      长度   名称
      (1)ACT_FLAG '0'_增加；'1'_修改
      (2)RP_ID    
      (3)REC_SEQ
      (4)PREP_ID  
      (5)VALUE    
    返回结果：
    RETCODE    long             返回结果

    作者：  
    编写日期：   2004-10-20
    修改者                    修改日期              修改内容
    /*******************************************************************************/
   public long MRPS_03(DynamicDict aDict) throws FrameException {

      String sOper_Flag;
      String sRP_ID="";
      
      String sREC_SEQ;
      String sPREP_ID;
      String sVALUE;

      String sqlopen = "";
	  long update_nums = 0;

      sOper_Flag = ((String) aDict.getValueByName("ACT_FLAG")).trim();
      sREC_SEQ = ((String) aDict.getValueByName("REC_SEQ")).trim();
      sRP_ID = ((String) aDict.getValueByName("RP_ID")).trim();
      sPREP_ID = ((String) aDict.getValueByName("PREP_ID")).trim();
      sVALUE = ((String) aDict.getValueByName("VALUE", false)).trim(); 


      if (sOper_Flag.equals("1")) {/*修改*/
         try {
            sqlopen = "UPDATE TSR_RP_RECORD_DETAIL" 
                    +" SET VALUE='" + sVALUE
                    +"'  WHERE  RP_ID=" + sRP_ID 
                    +" and rec_seq="+sREC_SEQ
                    +" and prep_id="+sPREP_ID;

            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120420, "Srp:MRPS_03 修改市场预测详细信息失败" + "sql语句是:" + sqlopen);
            }
         } catch (SQLException e) {
            throw new FrameException(-22120421, "Srp:MRPS_03 修改市场预测详细信息时出现异常\n ,错误信息是:" + e.getMessage() + "\n   sql语句是:" + sqlopen);
         }
      } else if (sOper_Flag.equals("0")) {//新增
         try {
            sqlopen = "INSERT INTO  TSR_RP_RECORD_DETAIL (RP_ID,rec_seq,prep_id,value)"
                    +"  VALUES ("+sRP_ID
					+","
                    +sREC_SEQ
					+","
                    +sPREP_ID
					+",'"+ sVALUE
                    +"')";
            update_nums = __st__.executeUpdate(sqlopen);
            if (update_nums <= 0) {
               throw new FrameException(-22120422, "Srp:MRPS_03 增加市场预测基本信息失败" + "sql语句是:" + sqlopen);
            }else{
           	// writeStaffLog(sbureau,ssite,sstaff,"12","员工号："+sstaff+" （增加市场预测基本信息信息成功！",aDict.GetConnection());
            }
         } catch (SQLException e) {
            throw new FrameException(-22120423, "Srp:MRPS_01 增加市场预测基本信息时出现异常\n   ,错误信息是:" + e.getMessage() + "\n sql语句是:" + sqlopen);
         }
      }
      return 0;
   }
   /***********************************************************************************************************
    功能:      删除市场预测详细信息~(^@^)~

    输入输出数据结构：
    输入：
    格式：   　　　　
    变量名           类型      长度   名称
    输出参数:  无
    返回信息:
    RETCODE    long             返回结果
    0            删除成功
    -1              删除失败:所传入的参数可能为空

    修改者                    修改日期              修改内容

    /*******************************************************************************/
   public long MRPS_04(DynamicDict aDict) throws FrameException, SQLException {
      getLogger();
      if (!aDict.getValueByName("RP_ID").equals("")) {
         String sRP_ID  = ((String) aDict.getValueByName("RP_ID"  )).trim();
         String sREC_SEQ= ((String) aDict.getValueByName("REC_SEQ")).trim();
         String sql1 = "DELETE FROM TSR_RP_RECORD_DETAIL "
                     + "WHERE RP_ID  = "+ sRP_ID
                     + " and rec_seq="+sREC_SEQ;

         __st__.executeUpdate(sql1);
         return 0;

      } else {
         m_Logger.fatal("MRPS_04:删除市场预测基本信息失败---所传入的参数可能为空");
         return -1;
      }
   }      
}
