package com.powerise.ibss.framework;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.ibss.common.dao.DAOSQLUtils;
import com.ztesoft.ibss.common.dao.DAOUtils;

//import com.powerise.ibss.framedata.bo.FrameServiceController;
//import com.powerise.ibss.framedata.vo.SQLArgs;
//import com.powerise.ibss.framedata.vo.TfmActArgsVO;
//import com.powerise.ibss.framedata.vo.TfmServicesVO;
//import com.ztesoft.common.dao.ConnectionContext;
//import com.ztesoft.common.dao.DAOSQLUtils;
//import com.ztesoft.common.dao.DAOSystemException;
//import com.ztesoft.common.dao.DAOUtils;

/**
 *
 * Base使用方式
 *
 * 作为工具类使用，例如 ： Base __base = new Base(conn); //需要传入连接 __base.query(...);
 *
 */

public class Base
{
	private static Logger logger = Logger.getLogger(Base.class);
	public Base()
	{

	}

	/***************************************************************************
	 *
	 * PART-A-4 ACTION执行方法集 入参：aDict，包括connection,动态action_id,及绑定参数 出参：如果是
	 * select，则返回结果集(dynamicdict)，如果是update，则__updatecount为记录数
	 *
	 **************************************************************************/
	/**
	 * 执行ACTION 入参说明： ecode 执行点标志代码 aDict, aName, aSeq
	 * SQL入参来源，aName有值表示HashMap方式，无值表示从aDict中取 dictr, name
	 * 返回结果集，name有值表示HashMap方式，无值表示直接返回 dact ACTION etype, edesc 出错情况及报错信息
	 *
	 */
	private static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, String[] aName,
				int[] aSeq, DynamicDict dictr, String name, DynamicDict dact, int etype, String edesc)
				throws FrameException
	{
		// sql
		String sql = (String) dact.getValueByName("__sql");
		String sql_type = (String) dact.getValueByName("__sql_type", false);
		jndiName = jndiName.toUpperCase();
		// （1）替换参数处理
		ArrayList aparaname = new ArrayList();
		int checkseq = 0, checkpos = -1;
		int icount = dact.getCountByName("__sql_in_arg");
		for (int i = 0; i < icount; i++)
		{
			String nam = (String) dact.getValueByName("__sql_in_arg", i);
			int seq = Integer.parseInt((String) dact.getValueByName("__sql_in_seq", i));
			int flg = Integer.parseInt((String) dact.getValueByName("__sql_in_flg", i));

			if(seq < 1)
				throw new FrameException(-92010401, "SQL绑定参数序号错误: " + String.valueOf(seq) + " ( 最小为 1 ) ");
			if(seq > icount)
				throw new FrameException(-92010402, "SQL绑定参数序号错误: " + String.valueOf(seq) + " ( 最多为 "
							+ String.valueOf(icount) + " )");

			String val = "";
			if((aName == null) || (aName.length <= 0)) // DynamicDict内取值
			{
				int offset = 0;
				if((aSeq != null) && (aSeq.length > 0))
					offset = aSeq[0];

				if(nam.startsWith("$")) // 支持参数包含$间址
					nam = (String) dict__get_val(aDict, nam.substring(1), offset); // 取aDict第p行的值

				val = (String) dict__get_val(aDict, nam, offset); // 取aDict第p行的值
			}
			else
			// DynamicDict内HashMap方式取值，按aSeq从最大到0的优先级查值
			{
				for (int j = aName.length - 1; j >= 0; j--)
				{
					HashMap h;

					if((aSeq == null) || (aSeq.length <= 0))
						h = (HashMap) aDict.getValueByName(aName[j]);
					else if(aSeq.length <= 1)
						h = (HashMap) aDict.getValueByName(aName[j], aSeq[0]);
					else
						h = (HashMap) aDict.getValueByName(aName[j], aSeq[j]);

					if(h.get(nam) != null)
					{
						val = (String) h.get(nam);
						break;
					}
				}
			}

			// 参数处理(绑定或替换)
			if(flg == 3)
			{
				while (checkseq < aparaname.size())
				{
					if((checkpos = sql.indexOf('?', checkpos + 1)) < 0)
						throw new FrameException(-92010403, "SQL替换参数序号错误: " + String.valueOf(seq) + " ( 最多为 "
									+ String.valueOf(icount) + " )");
					checkseq++;
				}

				if((checkpos = sql.indexOf('?', checkpos + 1)) < 0)
					throw new FrameException(-92010403, "SQL替换参数序号错误: " + String.valueOf(seq) + " ( 最多为 "
								+ String.valueOf(icount) + " )");

				sql = sql.substring(0, checkpos) + val + sql.substring(checkpos + 1);
			}
			else
			{
				aparaname.add(nam);
			}
		}

		// （2）解析ASQL
//		if(sql.indexOf('$') >= 0) // 增加支持ASQL
//			sql = SQLResolver.asql__parse(sql, aparaname);

		// （3）绑定参数处理
		ArrayList apara = new ArrayList();
		icount = aparaname.size();
		for (int i = 0; i < icount; i++)
		{
			String nam = (String) aparaname.get(i);

			String val = "";
			if((aName == null) || (aName.length <= 0)) // DynamicDict内取值
			{
				if((aSeq == null) || (aSeq.length <= 0))
					val = (String) dict__get_val(aDict, nam, 0); // 取aDict第p行的值
				else
					val = (String) dict__get_val(aDict, nam, aSeq[0]); // 取aDict第p行的值
			}
			else
			// DynamicDict内HashMap方式取值，按aSeq从最大到0的优先级查值
			{
				for (int j = aName.length - 1; j >= 0; j--)
				{
					HashMap h;

					if((aSeq == null) || (aSeq.length <= 0))
						h = (HashMap) aDict.getValueByName(aName[j]);
					else if(aSeq.length <= 1)
						h = (HashMap) aDict.getValueByName(aName[j], aSeq[0]);
					else
						h = (HashMap) aDict.getValueByName(aName[j], aSeq[j]);

					if(h.get(nam) != null)
					{
						val = (String) h.get(nam);
						break;
					}
				}
			}

			apara.add(val);
		}

		String[] para = new String[apara.size()];
		for (int i = 0; i < apara.size(); i++)
			para[i] = (String) apara.get(i);

		// 执行SQL
		if(sql_type.equals("4")) // select
		{
			int write_type = (etype & ((dictr == null) ? WHEN : 0));

			if(write_type == WHEN_WRITE_BACK)
			{
				if((name != null) && (!name.equals("")))
					write_type = 0;
			}
			else if(write_type == WHEN_WRITE_RECORDSET)
			{
				dictr = aDict;
				if((name == null) || name.equals(""))
					name = "RECORDSET";
			}

			dictr = QueryHelper.query(jndiName, sql, para, dictr, name, ecode, etype, edesc);

			if(write_type == WHEN_WRITE_BACK)
			{
				Iterator it = dictr.m_Values.keySet().iterator();
				while (it.hasNext())
				{
					String nam = (String) it.next();
					String val = (String) dictr.getValueByName(nam);

					if(val.equals(""))
						continue;

					if((aName == null) || (aName.length <= 0)) // DynamicDict内取值
					{
						if((aSeq == null) || (aSeq.length <= 0))
							dict__set_val(aDict, nam, 0, val);
						else
							dict__set_val(aDict, nam, aSeq[0], val);
					}
					else
					// DynamicDict内取HashMap
					{
						for (int j = aName.length - 1; j >= 0; j--)
						{
							HashMap h;

							if((aSeq == null) || (aSeq.length <= 0))
								h = (HashMap) aDict.getValueByName(aName[j]);
							else if(aSeq.length <= 1)
								h = (HashMap) aDict.getValueByName(aName[j], aSeq[0]);
							else
								h = (HashMap) aDict.getValueByName(aName[j], aSeq[j]);

							if((h.get(nam) != null) || (j == 0))
							{
								h.put(nam, val);
							}
						}
					}
				}
			}

			return dictr;
		}
		else if(sql_type.equals("2")) // update
		{
			int update_count = update(jndiName, sql, para, ecode, etype, edesc);
			if(dictr == null)
				dictr = new DynamicDict();
			dictr.setValueByName("__count", String.valueOf(update_count));

			return dictr;
		}
		else
			throw new FrameException(-92010403, "执行的动态SQL应当是select/update类型(4/2):实际类型:" + sql_type);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, String[] aName,
				int[] aSeq, DynamicDict dictr, String name, String action_id, int etype, String edesc)
				throws FrameException
	{
		DynamicDict dact = action__by_id(action_id);
		return action__execute(jndiName, ecode, aDict, aName, aSeq, dictr, name, dact, etype, edesc);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, DynamicDict dictr,
				String name, String action_id) throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, null, dictr, name, action_id, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, DynamicDict dictr,
				String name, String sql, String[] paraname) throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, null, dictr, name, sql, paraname, 0, null);
	}

	// 按ACTION_ID执行ACTION的若干简化方法
	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, String action_id)
				throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, null, null, null, action_id, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, String action_id,
				int etype, String edesc) throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, null, null, null, action_id, etype, edesc);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, int p, String action_id)
				throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, new int[] { p }, null, null, action_id, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, int p, String action_id,
				int etype, String edesc) throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, new int[] { p }, null, null, action_id, etype, edesc);
	}

	// 按SQL执行ACTION的若干简化方法
	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, String sql,
				String[] paraname) throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, null, null, null, sql, paraname, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, String sql,
				String[] paraname, int etype, String edesc) throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, null, null, null, sql, paraname, etype, edesc);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, int p, String sql,
				String[] paraname) throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, new int[] { p }, null, null, sql, paraname, 0, null);
	}

	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, int p, String sql,
				String[] paraname, int etype, String edesc) throws FrameException
	{
		return action__execute(jndiName, ecode, aDict, null, new int[] { p }, null, null, sql, paraname, etype, edesc);
	}

	/*
	 * 按SQL或ACTION_ID执行ACTION的2种基本方法
	 */
	public static DynamicDict action__execute(String jndiName, int ecode, DynamicDict aDict, String[] aName,
				int[] aSeq, DynamicDict dictr, String name, String sql, String[] paraname, int etype, String edesc)
				throws FrameException
	{
		DynamicDict dact = action__by_sql(sql, paraname);
		return action__execute(jndiName, ecode, aDict, aName, aSeq, dictr, name, dact, etype, edesc);
	}

	/**
	 * 根据Action_ID查询公用sql及参数
	 */
	private static DynamicDict action__by_id(String serviceName) throws FrameException
	{
		DynamicDict dictr = new DynamicDict();
//		TfmServicesVO serviceVO = FrameServiceController.getInstance().getServiceByName(serviceName);
//		SQLArgs sqlArgs = serviceVO.getSqlArgs();
//		// 贵州以后框架
//		{
//			String sql = sqlArgs.getSQL();
//			dictr.setValueByName("__sql", sql);
//			if(sql.trim().toUpperCase().startsWith("SELECT") || sql.trim().toUpperCase().startsWith("WITH"))
//				dictr.setValueByName("__sql_type", "4");
//			else
//				dictr.setValueByName("__sql_type", "2");
//
//			List args = sqlArgs.getArgs();
//			if(args != null)
//			{
//				for (int i = 0; i < args.size(); i++)
//				{
//					TfmActArgsVO arg = (TfmActArgsVO) args.get(i);
//					String ioType = arg.getIn_out_flag();
//					if("1".equals(ioType) || "3".equals(ioType)) // 入参
//					{
//						dictr.setValueByName("__sql_in_arg", arg.getArg_name().toUpperCase().trim(), 1);
//						dictr.setValueByName("__sql_in_seq", String.valueOf(arg.getArg_seq()), 1);
//						dictr.setValueByName("__sql_in_flg", ioType, 1);
//					}
//				}
//			}
//		}

		return dictr;
	}

	/*
	 * 根据ASQL及已指定绑定参数名，返回解析后的SQL及参数
	 */
	private static DynamicDict action__by_sql(String sql, String[] paraname) throws FrameException
	{

		DynamicDict dictr = new DynamicDict();

		dictr.setValueByName("__sql", sql);
		if(sql.trim().toUpperCase().startsWith("SELECT") || sql.trim().toUpperCase().startsWith("WITH"))
			dictr.setValueByName("__sql_type", "4");
		else
			dictr.setValueByName("__sql_type", "2");

		if(paraname != null)
		{
			for (int i = 0; i < paraname.length; i++)
			{
				dictr.setValueByName("__sql_in_arg", paraname[i].toUpperCase().trim(), 1);
				dictr.setValueByName("__sql_in_seq", String.valueOf(i + 1), 1);
				dictr.setValueByName("__sql_in_flg", "1", 1);
			}
		}

		return dictr;
	}

	/***************************************************************************
	 *
	 * PART-A-3 DynmaicDict对象附加操作集
	 *
	 * 描述：
	 *
	 **************************************************************************/

	/**
	 * 按变量名和指定位置进行值读取，（如位置超出范围，则返回"")
	 */
	public static Object dict__get_val(DynamicDict aDict, String name, int seq) throws FrameException
	{
		if((seq >= 0) && (seq < aDict.getCountByName(name)))
		{
			return aDict.getValueByName(name, seq);
		}
		else if(aDict.getCountByName(name) > 0)
		{
			Object obj = aDict.getValueByName(name);
			if(obj instanceof String)
			{
				return obj;
			}
			else
			{
				try
				{
					return obj.getClass().newInstance(); // 返回空对象
				} catch (Throwable e)
				{
					throw new FrameException(-9900232, "读取数据包时发生错误：该对象类型不支持默认实例");
				}
			}
		}
		else
		{
			return aDict.getValueByName(name, false);
		}
	}

	/**
	 * 按变量名和指定位置进行值存储，（如位置超出范围，则扩充该变量至该位置)
	 */
	public static void dict__set_val(DynamicDict aDict, String name, int seq, Object value) throws FrameException
	{
		HashMap m_Values = aDict.m_Values;
		Object obj;
		ArrayList al;

		if(seq < 0)
			return;
		if(value == null)
			return;
		if(value instanceof String)
			value = ((String) value).trim();

		name = name.toUpperCase().trim();
		obj = m_Values.get(name);

		if(obj == null) // 如果原来无此变量
		{
			if(seq == 0)
			{
				m_Values.put(name, value);
				return;
			}
			m_Values.put(name, al = new ArrayList());
		}
		else if(obj instanceof ArrayList) // 如果是ArrayList
		{
			al = (ArrayList) obj;
		}
		else
		// 如果是其他对象
		{
			if(seq == 0)
			{
				m_Values.put(name, value);
				return;
			}
			al = new ArrayList();
			al.add(obj);
			m_Values.put(name, al);
		}

		int size = al.size();
		if(seq >= size) // 下标超过则补充
			for (int i = size; i <= seq; i++)
			{
				if(value instanceof String)
				{
					al.add("");
				}
				else
				{
					try
					{
						al.add(value.getClass().newInstance()); // 先补充空对象
					} catch (Throwable e)
					{
						throw new FrameException(-9900231, "写入数据包时发生错误：该对象类型不支持默认实例");
					}
				}
			}
		al.set(seq, value);

		return;
	}

	/***************************************************************************
	 *
	 * PART-A-2 SQL操作方法集
	 *
	 *
	 **************************************************************************/

	/*
	 * 执行选项，0-3bit用于报错条件，第4位以上用于选项，例如：
	 *
	 * WHEN_NO_DATA_FOUND | OPTION_RETURN_STRUCT 表示 无记录报错，否则返回查询结果和结果集结构
	 *
	 * WHEN_NO_DATA_FOUND | OPTION_RETURN_COLUMN 表示 无记录报错，否则以C1, C2, C3
	 * ..列名方式返回结果
	 *
	 */
	public static final int	WHEN	= 15, // WHEN的屏蔽字
				WHEN_NO_DATA_FOUND = 1, // 用于检查查询语句
				WHEN_DATA_FOUND = 2, // 用于检查查询语句
				WHEN_COUNT_IS_0 = 3, // 用于检查修改语句
				WHEN_COUNT_IS_NOT_0 = 4, // 用于检查修改语句
				WHEN_COUNT_IS_NOT_1 = 5, // 用于检查修改语句
				WHEN_WRITE_BACK = 6, // 只对ACTION有效，查询结果回写到包中
				WHEN_WRITE_RECORDSET = 7, // 只对ACTION有效，查询结果回加入到RECORDSET包中
				OPTION_RETURN_COLUMN = 16, // 以C1,C2,..的列名返回结果集
				OPTION_RETURN_STRUCT = 32;	// 同时返回结果集结构，__field_name, __field_type

	private static final int	OPTION_RETURN_FIRST1	= 64, // 只返回第1条记录
				OPTION_LOGGED = 128;	// 执行前打印日志

	// 查询1个long：默认值0；有多个字段随机返回其中之1；
	public static long query_long(String jndiName, String sql, String[] para, int ecode) throws FrameException
	{
		return query_long(jndiName, sql, para, ecode, 0, null);
	}

	// 查询1个int：默认值0；有多个字段随机返回其中之1；
	public static long query_long(String jndiName, String sql, String[] para, int ecode, int etype, String edesc)
				throws FrameException
	{
		long long_integer = 0;
		String str = query_string(jndiName, sql, para, ecode, etype, edesc);
		if((str != null) && !str.equals(""))
		{
			try
			{
				long_integer = Long.parseLong(str);
			} catch (NumberFormatException e)
			{
				throw new FrameException(-92010201, "数字格式错误：" + str);
			}
		}

		return long_integer;
	}

	// 查询1个int：默认值0；有多个字段随机返回其中之1；
	public static int query_int(String jndiName, int ecode, String sql) throws FrameException
	{
		return query_int(jndiName, sql, null, ecode, 0, null);
	}

	// 查询1个int：默认值0；有多个字段随机返回其中之1；
	public static int query_int(String jndiName, int ecode, String sql, String[] para) throws FrameException
	{
		return query_int(jndiName, sql, para, ecode, 0, null);
	}

	// 查询1个int：默认值0；有多个字段随机返回其中之1；
	public static int query_int(String jndiName, String sql, String[] para, int ecode, int etype, String edesc)
				throws FrameException
	{
		int integer = 0;
		String str = query_string(jndiName, sql, para, ecode, etype, edesc);
		if((str != null) && !str.equals(""))
		{
			try
			{
				integer = Integer.parseInt(str);
			} catch (NumberFormatException e)
			{
				throw new FrameException(-92010201, "数字格式错误：" + str);
			}
		}

		return integer;
	}

	// 查询1个字符串：默认值""；有多个字段随机返回其中之1；
	public static String query_string(String jndiName, String sql, int ecode) throws FrameException
	{
		return query_string(jndiName, sql, null, ecode, 0, null);
	}

	// 查询1个字符串：默认值""；有多个字段随机返回其中之1；
	public static String query_string(String jndiName, String sql, String[] para, int ecode) throws FrameException
	{
		return query_string(jndiName, sql, para, ecode, 0, null);
	}

	// 查询1个字符串：默认值""；有多个字段随机返回其中之1；
	public static String query_string(String jndiName, String sql, String[] para, int ecode, int etype, String edesc)
				throws FrameException
	{
		String str = "";

		DynamicDict dictr = QueryHelper.query(jndiName, sql, para, null, null, ecode, etype, edesc);
		Iterator it = dictr.m_Values.keySet().iterator();
		if(it.hasNext())
			str = (String) dictr.getValueByName((String) it.next(), false);

		return str;
	}

	// 执行SELECT语句，直接返回,如果存在多行，只返回一行封装为HashMap记录
	public static DynamicDict query(String jndiName, String sql, String[] para, int ecode) throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, null, null, ecode, 0, null);
	}

	// 执行SELECT语句，直接返回
	public static DynamicDict query(String jndiName, String sql, String[] para, int ecode, int etype, String edesc)
				throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, null, null, ecode, etype, edesc);
	}

	//	 执行SELECT语句，直接返回
	public static DynamicDict query(String jndiName, String sql, List para, String name, int ecode, int etype,
				String edesc) throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, null, name, ecode, etype, edesc);
	}

	// 执行SELECT语句，直接返回
	public static DynamicDict query(String jndiName, String sql, List para, int ecode, int etype, String edesc)
				throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, null, null, ecode, etype, edesc);
	}

	// 执行SELECT语句，直接返回(HashMap方式)
	public static DynamicDict query(String jndiName, String sql, String[] para, int ecode, String name)
				throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, null, name, ecode, 0, null);
	}

	// 执行SELECT语句，直接返回(HashMap方式)
	public static DynamicDict query(String jndiName, String sql, String[] para, String name, int ecode, int etype,
				String edesc) throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, null, name, ecode, etype, edesc);
	}

	// 执行SELECT语句，追加返回
	public static DynamicDict query(String jndiName, String sql, String[] para, int ecode, DynamicDict dictr)
				throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, dictr, null, ecode, 0, null);
	}

	// 执行SELECT语句，追加返回
	public static DynamicDict query(String jndiName, int ecode, DynamicDict dictr, String sql, String[] para,
				int etype, String edesc) throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, dictr, null, ecode, etype, edesc);
	}

	public static DynamicDict query(String jndiName, String sql, String[] para, DynamicDict dictr, String name,
				int ecode) throws FrameException
	{
		return QueryHelper.query(jndiName, sql, para, dictr, name, ecode, 0, null);
	}

	public static DynamicDict queryPage(String jndiName, String querySQL, String countSQL, String[] para,
				int pageIndex, int pageSize, DynamicDict dto, String name, int ecode, int etype, String edesc)
				throws FrameException
	{
		return QueryHelper.queryPage(jndiName, querySQL, countSQL, para, pageIndex, pageSize, dto, name, ecode, etype,
					edesc);
	}

	public static DynamicDict queryPage(String jndiName, String querySQL, String countSQL, List para, int pageIndex,
				int pageSize, DynamicDict dto, String name, int ecode, int etype, String edesc) throws FrameException
	{
		return QueryHelper.queryPage(jndiName, querySQL, countSQL, para, pageIndex, pageSize, dto, name, ecode, etype,
					edesc);
	}

	// 执行UPDATE语句，返回记录数
	public static int update(String jndiName, String sql, String[] para, int ecode) throws FrameException
	{
		return update(jndiName, sql, para, ecode, 0, null);
	}

	// 执行UPDATE语句，返回记录数
	public static int update(String jndiName, String sql, String[] para, int ecode, int etype, String edesc)
				throws FrameException
	{
//		if(sql.indexOf('$') >= 0) // 增加支持ASQL
//			sql = SQLResolver.asql__parse(sql);

		return sql__update(ConnectionContext.getContext().getConnection(jndiName), ecode, sql, para, etype, edesc);
	}

	//	 执行UPDATE语句，返回记录数
	public static int update(String jndiName, String sql, List para, int ecode, int etype, String edesc)
				throws FrameException
	{
//		if(sql.indexOf('$') >= 0) // 增加支持ASQL
//			sql = SQLResolver.asql__parse(sql);

		return sql__update(ConnectionContext.getContext().getConnection(jndiName), ecode, sql, para, etype, edesc);
	}

	//批量执行INSERT,或者UPDATE语句，返回记录数
	public static int[] batchUpdate(String jndiName, String sql, List para, int ecode, int etype, String edesc)
				throws FrameException
	{
//		if(sql.indexOf('$') >= 0) // 增加支持ASQL
//			sql = SQLResolver.asql__parse(sql);

		return sql__batch_update(ConnectionContext.getContext().getConnection(jndiName), ecode, sql, para, etype, edesc);
	}

	/**
	 * UPDATE,DELETE,INSERT -> update count
	 */
	private static int sql__update(Connection conn, int ecode, String sql, String[] para, int etype, String edesc)
				throws FrameException
	{
		sql = DAOSQLUtils.getFilterSQL(sql);
		if((etype & OPTION_LOGGED) > 0) // 要记录日志
		{
			String spara = "";
			if(para != null)
				for (int i = 0; i < para.length; i++)
					spara += "," + para[i];

			if(!spara.equals(""))
				spara = spara.substring(1);

//			LogHelper.log("SQL=" + sql + "\nPARA=" + spara);
		}

		PreparedStatement st = null;
		int count = -1;
		try
		{
			st = conn.prepareStatement(sql);
			if(para != null)
				for (int i = 0; i < para.length; i++)
					st.setString(i + 1, para[i]);
			count = st.executeUpdate();

			switch (etype & WHEN)
			{
				case WHEN_COUNT_IS_0:
					if(count == 0)
						throw new FrameException(ecode, edesc);
					break;

				case WHEN_COUNT_IS_NOT_0:
					if(count != 0)
						throw new FrameException(ecode, edesc);
					break;

				case WHEN_COUNT_IS_NOT_1:
					if(count != 1)
						throw new FrameException(ecode, edesc);

				default:
			}
		} catch (SQLException e)
		{
			//throw new FrameException(ecode, "执行SQL时异常: " + e.getMessage());
			throw new FrameException(ecode, "执行SQL时异常: SQL=" + sql + "\nPARA=" + para + "\n" + e.getMessage());
		} finally
		{
			if(st != null)
				try
				{
					st.close();
				} catch (Throwable ee)
				{
				}
		}

		return count;
	}

	/**
	 * UPDATE,DELETE,INSERT -> update count
	 */
	private static int sql__update(Connection conn, int ecode, String sql, List para, int etype, String edesc)
				throws FrameException
	{

		sql = DAOSQLUtils.getFilterSQL(sql);
		//		if ((etype & OPTION_LOGGED) > 0) // 要记录日志
		//		{
		String spara = "";
		if(para != null)
			for (int i = 0, j = para.size(); i < j; i++)
				spara += "," + para.get(i);

		if(!spara.equals(""))
			spara = spara.substring(1);
		//			logger.info("SQL=" + sql + "\nPARA=" + spara) ;
//		LogHelper.log("SQL=" + sql + "\nPARA=" + spara);
		//		}

		PreparedStatement st = null;
		int count = -1;
		try
		{
			st = conn.prepareStatement(sql);
			if(para != null)
				for (int i = 0, j = para.size(); i < j; i++)
				{
					setObject2Statement(para.get(i), st, i + 1);
				}
			count = st.executeUpdate();
			if(etype != -999999999 && ecode != -999999999) //增加不需要处理的执行结果判断
			{
				switch (etype & WHEN)
				{
					case WHEN_COUNT_IS_0:
						if(count == 0)
							throw new FrameException(ecode, edesc);
						break;

					case WHEN_COUNT_IS_NOT_0:
						if(count != 0)
							throw new FrameException(ecode, edesc);
						break;

					case WHEN_COUNT_IS_NOT_1:
						if(count != 1)
							throw new FrameException(ecode, edesc);

					default:
				}
			}
		} catch (SQLException e)
		{
			//throw new FrameException(ecode, "执行SQL时异常: " + e.getMessage());
			throw new FrameException(ecode, "执行SQL时异常: SQL=" + sql + "\nPARA=" + para + "\n" + e.getMessage());
		} finally
		{
			if(st != null)
				try
				{
					st.close();
				} catch (Throwable ee)
				{
				}
		}

		return count;
	}

	/**
	 * UPDATE,DELETE,INSERT -> update count
	 */
	private static int[] sql__batch_update(Connection conn, int ecode, String sql, List paras, int etype, String edesc)
				throws FrameException
	{
		sql = DAOSQLUtils.getFilterSQL(sql);
//		LogHelper.log("SQL=" + sql + "\nPARA=" + paras.toString());
		PreparedStatement st = null;
		int[] counts;
		try
		{
			st = conn.prepareStatement(sql);
			//logger.info("TEST==========================="+conn.hashCode()) ;
			logger.info("TEST==========================="+conn.hashCode());
			if(paras != null)
				for (int k = 0; k < paras.size(); k++)
				{
					List para = (List) paras.get(k);
					//设置参数
					for (int i = 0, j = para.size(); i < j; i++)
					{
						setObject2Statement(para.get(i), st, i + 1);
					}
					st.addBatch();//增加预处理
				}
			counts = st.executeBatch();
		} catch (SQLException e)
		{
			//throw new FrameException(ecode, "执行SQL时异常: " + e.getMessage());
			throw new FrameException(ecode, "执行SQL时异常: SQL=" + sql + "\nPARA=" + paras + "\n" + e.getMessage());
		} finally
		{
			if(st != null)
				try
				{
					st.close();
				} catch (Throwable ee)
				{
				}
		}

		return counts;
	}

	private static boolean isDateType(Object o)
	{
		return o != null &&( 
				"java.util.Date".equals(o.getClass().getName()) ||
				"java.sql.Date".equals(o.getClass().getName())) ;
	}

	private static boolean isTimestampType(Object o) {
		return o != null && "java.sql.Timestamp".equals(o.getClass().getName());
	}
	private static void setObject2Statement(Object o, PreparedStatement s, int i) throws SQLException
	{
		if(isDateType( o )){
			//s.setDate(i, (java.sql.Date)o) ;
			long time = ((java.sql.Date)o).getTime();
			s.setTimestamp(i, new Timestamp(time));
		}else{
			if(o == null || "".equals(o))
				s.setString(i, "") ;
			else
				s.setObject(i, o) ;
		}
	}

	private static Object getObject( CallableStatement s, int i , int dataType ) throws SQLException
	{
		if(dataType == Types.DATE)
			return s.getDate(i) ;
		
		if(dataType == Types.TIMESTAMP)
			return s.getTimestamp(i) ;
		if(dataType == Types.INTEGER)
			return new Integer(s.getInt(i) );
		if(dataType == Types.DOUBLE)
			return new Double( s.getDouble(i) );
		return s.getString(i) ;
		
	}
	
	private static Object getObject( ResultSet s, int i , int dataType ) throws SQLException
	{
        if(!s.next()){
            return null;
        }
		if(dataType == Types.DATE)
			return s.getDate(i) ;
		
		if(dataType == Types.TIMESTAMP)
			return s.getTimestamp(i) ;
		if(dataType == Types.INTEGER)
			return new Integer(s.getInt(i) );
		if(dataType == Types.DOUBLE)
			return new Double( s.getDouble(i) );
		return s.getString(i) ;
		
	}

	
	public static void main(String[] args)
	{
		String[] param = new String[1];
		param[0] = "100001";

		StringBuffer s = new StringBuffer();

		java.util.Date d = new java.util.Date();
		//logger.info(d.getClass().getName().equals("java.util.Date"));
		logger.info(d.getClass().getName().equals("java.util.Date"));
		try
		{
			/**
			 dto.m_Values
			 [STYPE]=[100001, 100001, 100001]
			 [COMMENTS]=[, , ]
			 [PCODE]=[00A, 00H, 00X]
			 [PNAME]=[有效, 已归档, 注销]
			 [PKEY]=[00A, 00H, 00X]

			 */
			//			DynamicDict dto = Base.query("Default", "select * from dc_public where stype=?", param , 3);
			/**
			 [{STYPE=100001, COMMENTS=, PCODE=00A, PNAME=有效, PKEY=00A},
			 {STYPE=100001, COMMENTS=, PCODE=00H, PNAME=已归档, PKEY=00H}, {STYPE=100001, COMMENTS=, PCODE=00X, PNAME=注销, PKEY=00X}]
			 List myList = (List) dto.m_Values.get("ABC")
			 */
			//			DynamicDict dto = Base.query("Default",
			//					"select * from dc_public where stype=?", param, 3, "ABC");

			//			Object result = dto.getValueByName("result") ;
			//			if(result != null ){
			//			List myList = (List) dto.m_Values.get("ABC");
			//			//				Iterator it = myList.values().iterator();
			//			//				while( it.hasNext()){
			//			//					List k = (List)it.next() ;
			//			Iterator mi = myList.iterator();
			//			while (mi.hasNext()) {
			//				Map dd = (Map) mi.next();
			//				Iterator xd = dd.keySet().iterator();
			//				while (xd.hasNext()) {
			//
			//					String kk = (String) xd.next();
			//					String v = (String) dd.get(kk);
			//					s.append("[" + kk + "]=" + v + ",");
			//				}
			//				s.append("\n");
			//			}
			//
			//				}
			//			}
			//			logger.info("RESULT==========" + s.toString());

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private  static  void wrapProcParam(StringBuffer sb , List params){
		if(params == null || params.isEmpty())
			return ;
		wrapProcParam( sb ,params.size()) ;
	}
	
	private static  void wrapProcParam(StringBuffer sb , int[] params ){
		if(params == null || params.length == 0)
			return ;
		wrapProcParam( sb ,params.length) ;
	}
	
	
	
	private  static void wrapProcParam(StringBuffer sb ,int paramSize){
			for(int i=0 , j = paramSize; i < j ; i++){
				if(i == j-1){
					sb.append("?") ;
				}else{
					sb.append("?,") ;
				}
			}
	}
	
	/**
	 * 存储过程调用 
	 * @param jndiName
	 * @param procName
	 * @param inParams
	 * @param outParams
	 * @return
	 * @throws Exception
	 */
	public  static List callProc(String jndiName , String procName,List inParams ,int[] outParams) throws Exception{
		List result ;
		if (DAOUtils.isOracleDB()) {
			result = callProcOracle(jndiName, procName, inParams, outParams);
		} else if(DAOUtils.isInformixDB()){
			result = callProcInformix(jndiName, procName, inParams, outParams);
		}else{
			throw new FrameException("获取数据库类型系统变量出错！");
		}
		
		return result;
	}
	
	/**
	 * Oracle存储过程调用 
	 * @param jndiName
	 * @param procName
	 * @param inParams
	 * @param outParams
	 * @return
	 * @throws Exception
	 */
	private  static List callProcOracle(String jndiName , String procName,List inParams ,int[] outParams) throws Exception{
		Connection dbConnection = null;
		CallableStatement proc = null;
		
		int outParamIndex = (inParams == null ? 0 : inParams.size()) ;
		int outParamLength = (outParams == null ? 0 : outParams.length) ;
		List result =( outParams == null || outParams.length == 0 )? null : new ArrayList() ;
		String callSQL = "" ;
		try{
			StringBuffer paramStr= new StringBuffer();
			
			//设置绑定参数位置 ?
			/*wrapProcParam(paramStr , inParams) ;
			wrapProcParam(paramStr , outParams) ;*/
			wrapProcParam(paramStr,outParamIndex+outParamLength);
			
			dbConnection =  ConnectionContext.getContext().getConnection(jndiName);
			callSQL = "{ call "+procName.toUpperCase()+"("+paramStr.toString()+")}" ;
			proc = dbConnection.prepareCall(callSQL);
			//设置输入参数
			if(inParams != null && !inParams.isEmpty()){
				for(int i = 0 , j = inParams.size() ; i < j ; i++){
					Object o = inParams.get(i) ;
					setObject2Statement( o, proc ,  i+1) ;
				}
			}
			//设置输出参数
			if(outParams != null && outParams.length > 0 ){
				for(int i = 0 , j = outParams.length ; i < j ; i++){
					proc.registerOutParameter(outParamIndex+i+1,outParams[i]);
				}
			}
			
			proc.execute();
			
			//组装输出结果
			if(result != null ){
				for(int i = 0 , j = outParams.length ; i < j ; i++){
					int dataType = outParams[i] ;
					Object o = getObject( proc,outParamIndex+i+1 , outParams[i]) ;
					result.add(o) ;
				}
			}
		}catch (Exception se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execProc:" +callSQL+"\n",se);
		} finally {
			proc.close();
		}
		return result;
	}
	
	/**
	 * informix存储过程调用 
	 * @param jndiName
	 * @param procName
	 * @param inParams
	 * @param outParams
	 * @return
	 * @throws Exception
	 */
	private  static List callProcInformix(String jndiName , String procName,List inParams ,int[] outParams) throws Exception{
		Connection dbConnection = null;
		CallableStatement proc = null;
		
		int outParamIndex = (inParams == null ? 0 : inParams.size()) ;
		int outParamLength = (outParams == null ? 0 : outParams.length) ;
		List result =( outParams == null || outParams.length == 0 )? null : new ArrayList() ;
		String callSQL = "" ;
		try{
			StringBuffer paramStr= new StringBuffer();
			
			//设置绑定参数位置 ?
			wrapProcParam(paramStr , inParams) ;
			/*wrapProcParam(paramStr , outParams) ;
			wrapProcParam(paramStr,outParamIndex+outParamLength);*/
			
			dbConnection =  ConnectionContext.getContext().getConnection(jndiName);
//            dbConnection.setAutoCommit(false);
			callSQL = "{ call "+procName.toUpperCase()+"("+paramStr.toString()+")}" ;
			proc = dbConnection.prepareCall(callSQL);
			//设置输入参数
			if(inParams != null && !inParams.isEmpty()){
				for(int i = 0 , j = inParams.size() ; i < j ; i++){
					Object o = inParams.get(i) ;
					setObject2Statement( o, proc ,  i+1) ;
				}
			}
			//设置输出参数
			/*if(outParams != null && outParams.length > 0 ){
				for(int i = 0 , j = outParams.length ; i < j ; i++){
					proc.registerOutParameter(outParamIndex+i+1,outParams[i]);
				}
			}*/
			
			ResultSet rs = proc.executeQuery();
			
			//组装输出结果
			if(result != null  ){
				for(int i = 0 , j = outParams.length ; i < j ; i++){
					int dataType = outParams[i] ;
					Object o = getObject( rs, i+1 , outParams[i]) ;
                    if(o!=null)
					    result.add(o) ;
				}
			}
		}catch (Exception se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execProc:" +callSQL+"\n",se);
		} finally {
			proc.close();
		}
		return result;
	}

}
