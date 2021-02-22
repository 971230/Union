package com.ztesoft.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.ibss.common.util.StringUtils;

/**
 * @Classname : SequenceManangeDAO
 * @Description : 获取序列的工具类
 * @Copyright : 2006 ZTEsoft.
 * @Author : llh
 * @Create Date : 2006-3-27
 *
 * @Last Modified :
 * @Modified by :
 * @Version : 1.0
 */
public class SequenceManageDAOImpl  implements SequenceManageDAO {

	/**
	 * 根据表名和字段名获取序列
	 *
	 * @param tableCode
	 * @param fieldCode
	 * @return
	 */
	@Override
	public String getNextSequence(String tableCode,
			String fieldCode) {

		String GET_SEQUENCE_CODE = "SELECT sequence_code FROM sequence_management "
				+ " WHERE table_code=? AND field_code=?";

		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sequenceCode = "";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);//DAOUtils.getDBConnection(JNDINames.DEFAULT_DATASOURCE, this);
			stmt = conn.prepareStatement(DAOSQLUtils
					.getFilterSQL(GET_SEQUENCE_CODE));
			stmt.setString(1, tableCode);
			stmt.setString(2, fieldCode);
			result = stmt.executeQuery();
			if (result.next()) {
				sequenceCode = result.getString("sequence_code");
			} else {
				throw new DAOSystemException("取序列出错,不存在的序列:table_code:"+tableCode+" field_code:"+fieldCode+"!");
			}
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);

			synchronized(sequenceCode){
//				conn = DAOUtils.getDBConnection(JNDINames.DEFAULT_DATASOURCE, this);

				String GET_SEQUENCE = "SELECT "
						+ DAOSQLUtils.getTableName(sequenceCode)
						+ ".nextval seq_value FROM dual";
				stmt = conn.prepareStatement(GET_SEQUENCE);
				result = stmt.executeQuery();
				if (result.next()) {
					return result.getString("seq_value");
				} else {
					return "-1";
				}
			}

		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while execute "
					+ GET_SEQUENCE_CODE + ":" + sequenceCode + ":\n"
					+ se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
	}

        /**
         * 根据表名和字段名获取序列,序列格式为“YYYYMMDD"+流水号
         *
         * @param tableCode
         * @param fieldCode
         * @param seqNum
         * @return
         */
        @Override
		public String getNextSequenceFormat(String tableCode,
                        String fieldCode,int seqNum) {

                String GET_SEQUENCE_CODE = "SELECT sequence_code FROM sequence_management "
                                + " WHERE table_code=? AND field_code=?";

                PreparedStatement stmt = null;
                ResultSet result = null;
                Connection conn = null;
                String sequenceCode = "";
                try {
                        conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE) ;//DAOUtils.getDBConnection(JNDINames.DEFAULT_DATASOURCE, this);
                        stmt = conn.prepareStatement(DAOSQLUtils
                                        .getFilterSQL(GET_SEQUENCE_CODE));
                        stmt.setString(1, tableCode);
                        stmt.setString(2, fieldCode);
                        result = stmt.executeQuery();
                        if (result.next()) {
                                sequenceCode = result.getString("sequence_code");
                        } else {
                                throw new DAOSystemException("取序列出错,不存在的序列:table_code:"+tableCode+" field_code:"+fieldCode+"!");
                        }
                        DAOUtils.closeResultSet(result, this);
                        DAOUtils.closeStatement(stmt, this);
//                        DAOUtils.closeConnection(conn, this);

                        synchronized(sequenceCode){
//                                conn = DAOUtils.getDBConnection(JNDINames.DEFAULT_DATASOURCE, this);
                                
                                String GET_DATE = "select "+DatabaseFunction.current()+" from dual";

                                String GET_SEQUENCE = "select LPAD( "
                                                + DAOSQLUtils.getTableName(sequenceCode)
                                                + ".nextval,"+ seqNum + ",'0') seq_value FROM dual";
                                String dateStr = "";
                                stmt = conn.prepareStatement(DAOSQLUtils
                                        .getFilterSQL(GET_DATE));
                                result = stmt.executeQuery();
                                if(result.next()){
                                	dateStr = result.getString(1);
                                	if(dateStr != null){
                                		dateStr = dateStr.replaceAll("-","");
                                		dateStr = dateStr.substring(0,8);
                                	}
                                }
                                
                                DAOUtils.closeResultSet(result, this);
                                DAOUtils.closeStatement(stmt, this);
                                stmt = conn.prepareStatement(DAOSQLUtils
                                        .getFilterSQL(GET_SEQUENCE));
                                result = stmt.executeQuery();
                                if (result.next()) {
                                        return dateStr+result.getString("seq_value");
                                } else {
                                        return "-1";
                                }
                        }

                } catch (SQLException se) {
                        throw new DAOSystemException("SQLException while execute "
                                        + GET_SEQUENCE_CODE + ":" + sequenceCode + ":\n"
                                        + se.getMessage(), se);
                } finally {
                        DAOUtils.closeResultSet(result, this);
                        DAOUtils.closeStatement(stmt, this);
//                        DAOUtils.closeConnection(conn, this);
                }
        }


	@Override
	public synchronized String getNextSequence(String sequenceCode) {
		return getSequence( sequenceCode ,  JNDINames.DEFAULT_DATASOURCE) ;
	}
	
	private synchronized String getSequence(String sequenceCode , String jndiName){
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String GET_SEQUENCE = "SELECT "
				+ DAOSQLUtils.getTableName(sequenceCode)
				+ ".nextval seq_value FROM dual";
		try {
			conn = ConnectionContext.getContext().getConnection(jndiName) ;//DAOUtils.getDBConnection(JNDINames.DEFAULT_DATASOURCE, this);

			stmt = conn.prepareStatement(GET_SEQUENCE);
			result = stmt.executeQuery();
			if (result.next()) {
				return result.getString("seq_value");
			} else {
				return "-1";
			}

		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while execute "
					+ GET_SEQUENCE + ":\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
	}
	
	public synchronized String getNextSequenceWithDB(String sequenceCode , String jndiName ) {
		return getSequence( sequenceCode ,  jndiName) ;
	}
	
	
	public static String next(String table, String column) {
		return SeqDAOFactory.getInstance().getSequenceManageDAO()
				.getNextSequence(table, column);
	}


    /*
	    * @param 序列名称
	    * return 返回当前时间+序列值
	    * **/
	    public static String getTimeSequence(String seq_name,SqlMapExe sqlMapExe){
	        String currentTime= DateFormatUtils.formatDate(new Date(System.currentTimeMillis()), CrmConstants.DATE_FORMAT_8);
	        NumberFormat format = new DecimalFormat("000000000");
	        try{
	           if(null!=sqlMapExe && StringUtils.isNotEmpty(seq_name)){
	               StringBuffer sql=new StringBuffer("select getseq('"+seq_name+"') from dual");//s_TWB_SEND_SMS_SEQ.Nextval
	               String rval=sqlMapExe.querySingleValue(sql.toString());
	               if(StringUtils.isNotEmpty(rval)){
	                   currentTime+=format.format(new Long(rval).longValue());
	               }
	           }
	        }catch (Exception e){
	            currentTime= DateFormatUtils.formatDate(new Date(System.currentTimeMillis()), CrmConstants.DATE_TIME_FORMAT_14);
	            e.printStackTrace();
	        }
	        return currentTime;
	    }
	    /*
	     * @param 序列名称
	     * return 序列值
	     * **/
	    public String getSequence(String seq_name,SqlMapExe sqlMapExe){
			StringBuffer sql=new StringBuffer("select getseq('"+seq_name+"') from dual");//s_TWB_SEND_SMS_SEQ.Nextval
			String rval=sqlMapExe.querySingleValue(sql.toString());
			return rval;
	    }
	    public String getSequenceLen(String strSeqName,String strSeqType,int intSeqLen,SqlMapExe sqlMapExe){
	    	String sql="";
			if(strSeqType.trim().equals("0")){		//按长度取序列值
				sql = "SELECT LPAD(getseq('"+strSeqName+"'),"+intSeqLen+",'0') SEQ FROM DUAL";
			}else if(strSeqType.trim().equals("1")){   //在前面加8位年月日
				sql="SELECT to_char2(getdate(),'yyyymmdd')||LPAD(getseq('"+strSeqName+"'),"+intSeqLen+"-8,'0')  seq  FROM DUAL";
			}else if(strSeqType.trim().equals("2")){   //在前面加14位年月日时分秒
				sql="select to_char2(getdate(),'yyyymmddhh24miss')||LPAD(getseq('"+strSeqName+"'),"+intSeqLen+"-14,'0') seq from dual ";
			}else{		//直接取序列
				sql="select getseq('"+strSeqName+"') from dual";
			}
			String ret = sqlMapExe.querySingleValue(sql.toString());
			return ret;
	    }
}
