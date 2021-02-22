//Source file: D:\\WLSApp\\Eclipse\\workspace\\ibss\\src\\com\\powerise\\ibss\\framework\\PrepareBuffer.java

package com.powerise.ibss.framework;

import com.powerise.ibss.util.SysSet;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
/**
用于访问数据库Prepare操作的类
 */
public class PrepareBuffer 
{
														//定义数据类型
		public static final int DATATYPE_INT	=1;
		public static final int DATETYPE_STRING	=2;
		public static final int DATATYPE_LONG	=3;
		public static final int DATATYPE_DOUBLE	=4;

		public static final int PARA_IN			= 1;			//入参标志
		public static final int PARA_IN_REP     =3;     //入参,为需要替换的入参数据,不是绑定方式
		public static final int PARA_OUT		= 2;			//出参标志


									//各种类型数据均转为字符串保存，在bind和fetch时再转换
		private String m_Value;				//参数值
		private int m_DataType;				//参数类型（参见DADATYPE_*定义）
		private String m_DataName;			//参数名（入参使用字母开头，出参使用数字顺序）
		private int m_Size;					//长度
		private int m_Seq;					//顺序
		

		public PrepareBuffer() 
		{
			m_DataName = null;
			m_DataType = 0;
			m_Size = 0;
			m_Value = null;
			m_Seq=0;
		}

		public PrepareBuffer(String strName, int iType, int iLen) 
		{
			m_DataName = strName.toUpperCase().trim();
			m_DataType = iType;
			m_Size = iLen;
		}

		public PrepareBuffer(String strName, int iType, int iLen,int iSeq) 
		{
			m_DataName = strName.toUpperCase().trim();
			m_DataType = iType;
			m_Size = iLen;
			m_Seq = iSeq;
		}
				
		/**
		@return String
		 */
		public String getValString() 
		{
			return m_Value;						//返回字符串表达的参数值
		}
		
		/**
		@return int
		 */
		public int getLength() 
		{
			return m_Size;
		}
		
		/**
		@return int
		 */
		public int getDataType() 
		{
			return m_DataType;
		}
		
		/**
		?坏敦釉爪吨滴媚雏Ｆ蝇SQL諰敌蹦龄每消袜，船畜小么隔
		@return string
		 */
		public String GetDataName() 
		{
			return m_DataName;
		}
		
		public void setValString(String val)
		{
			m_Value = val;
		}
		
		/**
		@param stBind
		@throws com.powerise.ibss.framework.FrameException
		 */

		//对preparedstatement类型的sql绑定参数--只有入参
		//
		public void bind(PreparedStatement stBind,int para_type) throws FrameException 
		{
			if(para_type!=PARA_IN)
				throw new FrameException(-22991001,"绑定参数时发现对非法使用输出参数。");
			try
			{									//按类型绑定
				if(m_Value==null)
					throw new FrameException(-22991003,"绑定的值为空,请设置具体数据,参数名:"+m_DataName);
				switch(m_DataType)
				{
					case DATATYPE_INT:
						stBind.setInt(m_Seq,Integer.parseInt(m_Value));
						break;
					case DATETYPE_STRING:
						stBind.setString(m_Seq,m_Value);
						break;
					case DATATYPE_LONG:
						stBind.setLong(m_Seq,Long.parseLong(m_Value));
						break;
					case DATATYPE_DOUBLE:
						stBind.setDouble(m_Seq,Double.parseDouble(m_Value));
						break;
					default:
						throw new FrameException(-22991002,"绑定参数时发现未定义的数据类型。");
				}
			}
			catch(SQLException e)
			{
				throw new FrameException(-22991003,"绑定参数时出现异常:"+e.getMessage()+" 列序号:"+m_Seq+" 列值:"+m_Value,SysSet.getStackMsg(e));
			}
		}

		//对callablestatement类型的sql绑定参数--有入参和出参
		public void bind(CallableStatement stBind,int para_type) throws FrameException 
		{
			try
			{
				if(para_type==PARA_IN)				//绑定入参
				switch(m_DataType)
				{
					case DATATYPE_INT:
						stBind.setInt(m_Seq,Integer.parseInt(m_Value));
						break;
					case DATETYPE_STRING:
						stBind.setString(m_Seq,m_Value);
						break;
					case DATATYPE_LONG:
						stBind.setLong(m_Seq,Long.parseLong(m_Value));
						break;
					case DATATYPE_DOUBLE:
						stBind.setDouble(m_Seq,Double.parseDouble(m_Value));
						break;
					default:
						throw new FrameException(-22991002,"绑定参数时发现未定义的数据类型。");
				}
				else if(para_type==PARA_OUT)		//注册出参数,参数名为序号1,2,3,4...）
					switch(m_DataType)
					{
						case DATATYPE_INT:
							stBind.registerOutParameter(Integer.parseInt(m_DataName), Types.INTEGER);
							break;
						case DATETYPE_STRING:
							stBind.registerOutParameter(Integer.parseInt(m_DataName), Types.VARCHAR);
							break;
						case DATATYPE_LONG:
							stBind.registerOutParameter(Integer.parseInt(m_DataName), Types.INTEGER);
							break;
						case DATATYPE_DOUBLE:
							stBind.registerOutParameter(Integer.parseInt(m_DataName), Types.DOUBLE);
							break;
						default:
							throw new FrameException(-22991002,"绑定参数时发现未定义的数据类型。");
					}
				else
					throw new FrameException(-22991005,"绑定参数时发现参数的绑定方向错误。");
			}
			catch(SQLException e)
			{
				throw new FrameException(-22991006,"绑定参数时出现SQL异常。");
			}
		}

		//取结果数据到preparebuffer
		public void Fetch(CallableStatement stBind) throws FrameException 
		{
			try
			{
					switch(m_DataType)
					{
						case DATATYPE_INT:
							setValString(String.valueOf(stBind.getInt(Integer.parseInt(m_DataName))));
							break;
						case DATETYPE_STRING:
							setValString(stBind.getString(Integer.parseInt(m_DataName)));
							break;
						case DATATYPE_LONG:
							setValString(String.valueOf(stBind.getInt(Integer.parseInt(m_DataName))));
							break;
						case DATATYPE_DOUBLE:
							setValString(String.valueOf(stBind.getDouble(Integer.parseInt(m_DataName))));
							break;
						default:
							throw new FrameException(-22999011,"取输出参数时发现未定义的数据类型。");
					}	
			}

			catch(SQLException e)
			{
				throw new FrameException(-22999012,"取输出参数时出现SQL异常。");
			}
		}
	



}
