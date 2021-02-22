/**
 * 
 */
package com.ztesoft.common.dao;

import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrator
 * 
 */
public class OracleBlobForWeblogic {
	
	/**
     * 存储Blob字段，仅仅对Oracle数据库有用
     * 如果处于两层调试阶段，那么需要对Connection设置为非自动提交
     * @param rs 预处理记录集
     * @param index 数据库中Blob字段的索引
     * @param buffer 数据区
     * @throws SQLException
     */
    public static void setBlob(ResultSet rs, int index, byte[] buffer)
        throws SQLException {
        OutputStream outstream = null;

//        try {
//            if (rs instanceof oracle.jdbc.driver.OracleResultSet) {
//                outstream =
//                    ( (oracle.jdbc.driver.OracleResultSet) rs).getBLOB(index).
//                    getBinaryOutputStream();
//            }
//            else {
//                Blob blob = rs.getBlob(index);
//                if (blob instanceof oracle.sql.BLOB) {
//                    outstream = ( (oracle.sql.BLOB) blob).getBinaryOutputStream();
//                }
////                else if (blob instanceof weblogic.jdbc.common.OracleBlob) {
////                    outstream = ( (weblogic.jdbc.common.OracleBlob) blob).
////                        getBinaryOutputStream();
////                }
//                else {
//                    throw new SQLException(
//                        "Unable to handle concrete type of Blob, " +
//                        blob.getClass().getName());
//                }
//            }
//            if (outstream != null) {
//                outstream.write(buffer);
//                outstream.flush();
//            }
//            else {
//                throw new SQLException("IOStream Is Null");
//            }
//        }
//        catch (IOException ex) {
//            throw new DAOSystemException(ex.getMessage());
//        }
//        finally {
//            if (outstream != null) {
//                try {
//                    outstream.close();
//                }
//                catch (IOException ex) {
//                	}
//                }
//            }
        }
        
	public static void setOracleBlob(ResultSet rs, int index, byte[] buffer)
			throws SQLException {
		OutputStream outstream = null;
//
//		try {
//			if (rs instanceof oracle.jdbc.driver.OracleResultSet) {
//				outstream = ((oracle.jdbc.driver.OracleResultSet) rs).getBLOB(
//						index).getBinaryOutputStream();
//			} else {
//				Blob blob = rs.getBlob(index);
//				if (blob instanceof oracle.sql.BLOB) {
//					outstream = ((oracle.sql.BLOB) blob)
//							.getBinaryOutputStream();
//				}else {
//					throw new SQLException(
//							"Unable to handle concrete type of Blob, "
//									+ blob.getClass().getName());
//				}
//			}
//			if (outstream != null) {
//				outstream.write(buffer);
//				outstream.flush();
//			} else {
//				throw new SQLException("IOStream Is Null");
//			}
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} finally {
//			if (outstream != null) {
//				try {
//					outstream.close();
//				} catch (IOException ex) {
//
//				}
//			}
//		}
	}

	/**
	 * 从数据库中得到Blob的字节数据区
	 * 
	 * @param rs
	 *            结果集
	 * @param index
	 *            数据库中Blob字段的索引
	 * @return Blob字段的数据
	 * @throws SQLException
	 */
	public static byte[] getOracleBlob(ResultSet rs, int index)
			throws SQLException {
		Blob blob = rs.getBlob(index);
		return blob.getBytes(1, (int) blob.length());
	}
	/**
     * 从数据库中得到Blob的字节数据区
     * @param rs 结果集
     * @param index 数据库中Blob字段的索引
     * @return Blob字段的数据
     * @throws SQLException
     */
    public static byte[] getBlob(ResultSet rs, String fieldName)
        throws SQLException {
        Blob blob = rs.getBlob(fieldName);
        return blob.getBytes(1, (int) blob.length());
    }
}
