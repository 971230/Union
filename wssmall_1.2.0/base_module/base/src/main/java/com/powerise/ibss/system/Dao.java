package com.powerise.ibss.system;

import com.powerise.ibss.util.Utility;

import java.sql.*;
import java.util.HashMap;
import java.util.Vector;

public class Dao{
   public static void update( String strTable,String strWhere, HashMap hasValue,Connection conn)
      throws SQLException
    {
         StringBuffer sb =new StringBuffer();
         sb.append("update ");
         sb.append(strTable);
         sb.append(" set ");
         Object[] en =hasValue.keySet().toArray();
         Vector vFields =new Vector();
         for(int i=0;i<en.length; i++){
            String sName =(String)en[i];
            vFields.addElement(sName);
         }
         int i=0;
         while(i <vFields.size()-1){
            sb.append((String)vFields.get(i));
            sb.append(" = ? ,");
            i++;
         }

         sb.append((String)vFields.get(i));
         sb.append(" = ?  ");

         sb.append(" where ");
         sb.append( strWhere);
         String sql =sb.toString();
         PreparedStatement prepStmt=conn.prepareStatement(sql);

         int j =1;
         while(j <=vFields.size()){
            setObject(prepStmt, j, hasValue.get(vFields.get(j-1)));
            j++;
         }
         prepStmt.executeUpdate();
         prepStmt.close();
   }

   /**
   *对数据库表（strTable)进行插入
   *表名：strTable String
   *插入值:hasValue HashMap
   */
   public static void insert(String strTable, HashMap hasValue,Connection conn)
        throws SQLException{
      StringBuffer sb =new StringBuffer();
         sb.append("insert into "+strTable+" (");

      Object[] en =hasValue.keySet().toArray();
      Vector vFields =new Vector();
      for(int i=0;i<en.length; i++){
         String sName =(String)en[i];
         vFields.addElement(sName);
      }
      int i=0;
      while(i <vFields.size()-1){
         sb.append((String)vFields.get(i)+",");
         i++;
      }

      sb.append((String)vFields.get(i)+") values(");
      i =0;
      while(i <vFields.size()-1){
         sb.append("?,");
         i++;
      }
      sb.append("?)");

      String sql =sb.toString();
      PreparedStatement prepStmt=conn.prepareStatement(sql);

      int j =1;
      while(j <=vFields.size()){
         String sFieldName =(String)vFields.get(j-1);
            setObject(prepStmt, j, hasValue.get(vFields.get(j-1)));
         j++;
      }

      prepStmt.executeUpdate();
      prepStmt.close();
   }
   
   /**
      设置字段值
   */
   private static void setObject(PreparedStatement ps,
         int parameterIndex, Object ob) throws SQLException {
      if(Utility.isDate(ob.toString(), "yyyy-MM-dd hh:mm:ss")){
         //如是日期字段，则作特别处理
         java.util.Date d3;
         d3 =Utility.toDate((String)ob , "yyyy-MM-dd hh:mm:ss");
         java.sql.Timestamp dateTemp =new java.sql.Timestamp(d3.getTime());
         ps.setObject(parameterIndex, dateTemp);
      }else if(Utility.isDate(ob.toString(), "yyyy-MM-dd")){
         //如是日期字段，则作特别处理
         java.util.Date d3;
         d3 =Utility.toDate((String)ob , "yyyy-MM-dd");
         java.sql.Timestamp dateTemp =new java.sql.Timestamp(d3.getTime());
         ps.setObject(parameterIndex, dateTemp);
      }else if(ob ==null)
         ps.setNull(parameterIndex, java.sql.Types.VARCHAR);
      else{
         ps.setObject(parameterIndex, ob);
      }
   }
   /**
      执行数据库查询操作，返回的数据集游标为向前方式
      @param sql 查询SQL语句
      @param conn 数据库连接
   */
   public static ResultSet select(String sql, Connection conn) throws SQLException {
      return select(sql,conn,false);
   }

   /**
      执行数据库查询操作，返回的数据集游标方式：当scollFlag 为true时，为可
      循环方式，为false 时，为只能向前方式。
      @param sql 查询SQL语句
      @param conn 数据库连接
      @param scollFlag 是否循环
   */
   public static ResultSet select(String sql, Connection conn ,boolean scollFlag) throws SQLException {
      Statement st =null;
      if(scollFlag)
         st =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_READ_ONLY);
      else
         st =conn.createStatement();
      return st.executeQuery(sql);
   }
   
   public static int update(String sql, Connection conn) throws SQLException {
      Statement st =conn.createStatement();
      /*返回修改的行数*/
      int re =st.executeUpdate(sql);
      st.close();
      return re;
   }   
   /**
    * 批量修改数据库，返回批处理是否完成
    * @param sqlBatch SQL批处理语句数组
    * @param conn 数据库的连接
    * @return boolean 批处理完成标志
    */
   public static boolean change(Vector sqlBatch, Connection conn)
         throws SQLException  {
      Statement st =conn.createStatement();
      for(int i=0;i<sqlBatch.size();i++){
         st.addBatch((String)sqlBatch.get(i));
      }
      /*返回批量修改的行数*/
      st.executeBatch();
      st.close();
      return true;
   }   
}
