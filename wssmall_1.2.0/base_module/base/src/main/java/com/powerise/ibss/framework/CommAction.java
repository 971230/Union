// Source file:
// D:\\WLSApp\\Eclipse\\workspace\\ibss\\src\\com\\powerise\\ibss\\framework\\CommAction.java
//229904xxxx
package com.powerise.ibss.framework;

import com.powerise.ibss.util.SysSet;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Vector;

/**
 * 针对通用处理（基于XML数据格式定义）的操作
 */
public class CommAction {
	static final String ACTION = "Action";

	static final String SYSTEMNAME = "SystemName";

	static final String ACTION_ID = "ID";

	static final String ACTION_NAME = "Name";

	static final String ACTION_ROW = "Row";

	static final String ACTION_STRUCTURE = "Structure";

	static final String ACTION_TYPE = "Type";

	static final int ACTION_TYPE_EXECUTE = 3; // 前台直接传送SQL语句执行操作

	static final int ACTION_TYPE_FROMDB = 2; // ACTION类型-数据库中定义的DML语句或PLSQL（根据ID查找语句)

	static final int ACTION_TYPE_SELECT = 1; // 前台直接传送SQL语句执行查询

	// 约定类型定义
	static final int ACTION_TYPE_SELECTFROMDB = 4; // ACTION类型-数据库中定义的SELECT语句（根据ID查找语句)

	static final String ARG_NAME = "Name";

	static final String ARG_VALUE = "Value";

	static final String ARGS_NAME = "Name";

	static final String ARGS_VALUE = "Value";

	static final String COMMONOPER = "CommonOper"; //

	static final String DATA = "Data"; //

	static final String DEFINITION = "Definition"; //

	static final String DEFINITION_MODULE = "Module"; //

	static final String DEFINITION_TYPE = "Type"; //

	static final String DOC_ENCODING = "ISO-8859-1";

	static final String DOC_ROOT_NAME = "Powerise_IBSS";

	static final String FIELD = "Field";

	static final String FIELD_LENGTH = "Length";

	static final String FIELD_NAME = "Name";

	static final String FIELD_TYPE = "Type";

	// XML标签定义
	static final int MAX_ROWS = 12000;

	private final static String NAMESPACE = "http://www.powerise.com.cn/ns/PowerIBSS"; // "http://www.megginson.com/ns/exp/poetry";

	static final String PL_RET_CODE = ":oRet_Code"; // 约定PL/SQL预定义的返回代码变量名

	static final String PL_RET_MSG = ":oRet_Msg"; // 约定PL/SQL预定义的返回信息变量名

	static final String RESULT = "ResultSet";

	static final String RESULT_DATASET = "DataSet";

	static final String RESULT_FIELD = "Field";

	static final String RESULT_ROW = "RowData";

	static final String RESULT_STRUCTURE = "Structure";

	static final String RETURN_CODE = "Code";

	static final String RETURN_INFO = "Return";

	static final String RETURN_MSG = "Msg";

	static final int SQL_TYPE_INVALID = -1; // SQL语句类型-无效

	private int m_MaxRows = -1;

	private Vector m_Action; // ACTION列表

	// public XMLTool m_OutDoc; //应答XML的DOM对象
	public int m_Args;

	private ByteArrayOutputStream m_bsOut = null;

	private CallableStatement m_CallableStatement = null; // 用于执行PLSQL（带入参出参）

	private Connection m_conn = null; // 当前连接

	private String m_CurId = null;

	private Vector m_InArgs; // 入参列表

	private XMLTool m_InDoc; // 请求XML的DOM对象

	// 公共变量
	private String m_Msg; // 执行信息

	private Vector m_OutArgs; // 出参列表

	private PreparedStatement m_PreparedStatement = null; // 用于执行带入参的SQL

	// 采用Stream XML Api定义的变量
	private XmlSerializer m_serializer = null;

	// JDBC相关公共变量
	private String m_SqlClause = ""; // 待执行的SQL语句

	private int m_SqlType; // 待执行的SQL语句类型，参看SQL_TYPE_*的定义

	private Statement m_Statement = null; // 用于执行静态SQL

	private HashMap<String, String> staff_info; // 员工信息

	private static Logger m_Logger = null;

	private boolean m_bSingle = false;

	private HashSet m_hsArg = null;

	private static HashMap m_CacheData = null;

	private boolean m_CacheFlag = false;

	private boolean m_isCachedData = false;

	private String m_KeyValue = null; // 判断是否已经处理过的

	private boolean m_UseCache = false;

	private int m_CacheSize = 0;

	private boolean getCacheResult() throws FrameException {
		boolean bCached = false;
		if (m_CacheData == null) {
			m_Logger.debug("新建Cache Buffer");
			m_CacheData = new HashMap();
			return bCached;
		} else {
			if (m_CacheData.containsKey(new Integer(m_KeyValue.hashCode())) == true) {

				m_Logger.debug("从Cache中获取数据:" + m_CurId);

				bCached = true;
			}
		}
		return bCached;
	}

	//
	public CommAction() throws FrameException {
		if (m_Logger == null) {
			m_Logger = Logger.getLogger(getClass().getName());
		}
		Init(); // 初始化
	}

	// 在输出中加入action节点
	private String AddAction(STR_ACTION_INFO astr_action_info)
			throws FrameException {
		String strAction;
		try {
			strAction = ACTION;
			m_serializer.startTag(NAMESPACE, strAction);
			m_serializer.attribute(null, ACTION_NAME, astr_action_info.Name);
		} catch (Exception e) {
			m_Logger.info(SysSet.getStackMsg(e));
			throw new FrameException(-22993002, "创建Action出现异常"
					+ SysSet.getStackMsg(e));// e.getMessage());
		}
		return strAction;
	}

	// 在输出中(resultset中)加入字段信息
	private int AddFldInfo(ResultSetMetaData afld_info) throws FrameException {
		Element rsElem, fdElem;
		Attr curAttr;
		int i = 0, count;
		String strDataType;
		try {
			count = afld_info.getColumnCount();
			m_serializer.startTag(NAMESPACE, RESULT_STRUCTURE);
			// rsElem = (Element) m_OutDoc.CreateElement(RESULT_STRUCTURE,
			// parentElem, null); //建立structure节点
			// if (rsElem != null) {
			for (i = 1; i <= count; i++) {
				m_serializer.startTag(NAMESPACE, RESULT_FIELD);
				// fdElem = (Element) m_OutDoc.CreateElement(RESULT_FIELD,
				// rsElem, null); //建立field
				// if (fdElem != null) { //设置name,type等属性
				// curAttr = m_OutDoc.FindAttrByName(FIELD_NAME, fdElem, true);
				// curAttr.setValue(afld_info.getColumnName(i).toUpperCase());
				// curAttr = m_OutDoc.FindAttrByName(FIELD_TYPE, fdElem, true);
				m_serializer.attribute(null, FIELD_NAME, afld_info
						.getColumnName(i).toUpperCase());
				// 直接返回java语言中的数据类型定义，不做任何转换
				strDataType = Integer.toString(afld_info.getColumnType(i));
				// switch (afld_info.getColumnType(i)) {
				// case Types.INTEGER:
				// strDataType = String.valueOf(PrepareBuffer.DATATYPE_INT);
				// break;
				// case Types.VARCHAR:
				// strDataType = String.valueOf(PrepareBuffer.DATETYPE_STRING);
				// break;
				// case Types.CHAR:
				// strDataType = String.valueOf(PrepareBuffer.DATETYPE_STRING);
				// break;
				// case Types.NUMERIC:
				// strDataType = String.valueOf(PrepareBuffer.DATATYPE_LONG);
				// break;
				// case Types.DOUBLE:
				// strDataType = String.valueOf(PrepareBuffer.DATATYPE_DOUBLE);
				// break;
				// default:
				// strDataType = "0";
				// }
				m_serializer.attribute(null, FIELD_TYPE, strDataType);
				m_serializer.attribute(null, FIELD_LENGTH, String
						.valueOf(afld_info.getColumnDisplaySize(i)));
				m_serializer.endTag(NAMESPACE, RESULT_FIELD);
				// curAttr = m_OutDoc.FindAttrByName(FIELD_LENGTH, fdElem,
				// true);
				// curAttr.setValue(String.valueOf(afld_info.getColumnDisplaySize(i)));
			}
			m_serializer.endTag(NAMESPACE, RESULT_STRUCTURE);
			// }
			// }
		} catch (Exception e) {
			throw new FrameException(-22993002, "获取结果表结构时出现异常。"
					+ e.getMessage());
		}
		return i;
	}

	// 绑定一行元素值->
	private int BindAllVar(Element aRowElem, int iArgs) throws FrameException {
		int j, iFldCnt, k = 1;
		boolean bPrepare;
		Element fldElem;
		Attr curAttr;
		String strFldVal = null, strFldName = null;
		NodeList fldList;
		bPrepare = true;

		// 处理每行每列
		fldList = aRowElem.getChildNodes();// 获得 <Args/>的Elem
		if (fldList != null) {
			iFldCnt = fldList.getLength();
			for (j = 0; j < iFldCnt; j++) {
				if (fldList.item(j).getNodeType() != Node.ELEMENT_NODE)
					continue;
				fldElem = (Element) fldList.item(j);
				strFldName = fldElem.getNodeName();
				if (fldElem.getFirstChild() != null)
					strFldVal = fldElem.getFirstChild().getNodeValue();
				else
					strFldVal = "";
				if (!SetVarVal(strFldName, strFldVal)) {
					bPrepare = false;
					m_Logger.warn(m_CurId + "传入的入参与数据库的配置不匹配" + strFldName);
					// break; //对入参数据与入参配置不作一一对应的强制要求。支持入参数据的可变性
				} else {
					iArgs--;
				}
			}
		}
		return iArgs;
	}

	private int BindSessionVar(int iArgs) throws FrameException {
		// 从staff_info中获取数据
		int j, iFldCnt;
		PrepareBuffer curBuff;
		if (staff_info != null) {
			iFldCnt = m_InArgs.size();
			for (j = 0; j < iFldCnt; j++) {
				curBuff = (PrepareBuffer) m_InArgs.get(j);
				String o = staff_info.get(curBuff.GetDataName());
				if (o != null) {
					curBuff.setValString(o);
					iArgs--;
				}
			}
		} else
			m_Logger.warn("Session 传入的员工信息为空，忽略处理。");

		return iArgs;
	}

	// 对callablestatement类型的sql绑定入参并注册出参
	private void BindVariable(CallableStatement aStatement)
			throws FrameException {
		ListIterator itBuffer = m_InArgs.listIterator(0);
		PrepareBuffer curBuffer;
		// 绑定入参数
		try {
			while (itBuffer.hasNext()) {
				curBuffer = (PrepareBuffer) itBuffer.next();
				curBuffer.bind(aStatement, PrepareBuffer.PARA_IN);
			}
			// 注册出参
			itBuffer = m_OutArgs.listIterator(0);
			while (itBuffer.hasNext()) {
				curBuffer = (PrepareBuffer) itBuffer.next();
				curBuffer.bind(aStatement, PrepareBuffer.PARA_OUT);
			}
		} catch (FrameException e) {
			throw e;
		}
	}

	// 对preparedstatement类型的sql绑定入参
	private void BindVariable(PreparedStatement aStatement)
			throws FrameException {
		ListIterator itBuffer = m_InArgs.listIterator(0);
		PrepareBuffer curBuffer;
		// 绑定入参
		while (itBuffer.hasNext()) {
			curBuffer = (PrepareBuffer) itBuffer.next();
			curBuffer.bind(aStatement, PrepareBuffer.PARA_IN);
		}
	}

	// 建立输出definition节点
	private int BuildOutDefinition() throws FrameException {
		try {
			m_serializer.startTag(NAMESPACE, DEFINITION);
			m_serializer.attribute(null, DEFINITION_TYPE, "2");
			m_serializer.endTag(NAMESPACE, DEFINITION);
		} catch (Exception e) {
			;
		}
		return 0;
	}

	// 建立输出return接点
	private int BuildOutReturn(int iCode) throws FrameException {
		try {
			m_serializer.endTag(NAMESPACE, COMMONOPER);
			m_serializer.endTag(NAMESPACE, DATA);
			m_serializer.startTag(NAMESPACE, RETURN_INFO);
			m_serializer.attribute(null, RETURN_CODE, String.valueOf(iCode));
			m_serializer.attribute(null, RETURN_MSG, m_Msg);
			m_serializer.endTag(NAMESPACE, RETURN_INFO);
			m_serializer.endTag(NAMESPACE, DOC_ROOT_NAME);
			m_serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 建立输出根节点
	private void BuildOutRoot() throws FrameException {
	}

	// 清除参数定义
	private void ClearVar() throws FrameException {
		m_InArgs.clear();
		m_OutArgs.clear();
	}

	//
	public void Close() throws FrameException {
		try {
			if (m_CallableStatement != null) {
				m_CallableStatement.close();
				m_CallableStatement = null;
			}
			if (m_PreparedStatement != null) {
				m_PreparedStatement.close();
				m_PreparedStatement = null;
			}
			if (m_Statement != null) {
				m_Statement.close();
				m_Statement = null;
			}
		} catch (SQLException sqle) {
			throw new FrameException(-22993011, "CommonAction 类释放资源时,发生异常.",
					sqle.getMessage());
		}
		ClearVar();
		if (m_InDoc != null)
			m_InDoc.Reset();
		m_InDoc = null;
		m_serializer = null;
		if (m_bsOut != null) {
			try {
				m_bsOut.close();
			} catch (Exception e) {
			}
			m_bsOut = null;
		}
	}

	// 执行m_Action中的所有ACTION
	private int DoActions() throws FrameException {
		STR_ACTION_INFO lstr_action_info = null;
		ListIterator itAction = m_Action.listIterator(0);
		int iRet = 0, j = 0;
		if (m_UseCache && m_Action.size() == 1)
			m_CacheFlag = true;
		else
			m_CacheFlag = false;
		while (itAction.hasNext()) {
			ClearVar();
			lstr_action_info = (STR_ACTION_INFO) itAction.next();
			if (lstr_action_info.Type == null
					|| lstr_action_info.Type.equals(Integer
							.toString(ACTION_TYPE_SELECTFROMDB))
					|| lstr_action_info.Type.equals(Integer
							.toString(ACTION_TYPE_FROMDB))) // 从数据库取
				m_Args = GetDBType(lstr_action_info.Elem);
			else {
				// 针对前台传来的SQL，暂时没有实现不同的数据库
				m_SqlType = Integer.parseInt(lstr_action_info.Type);
				if (m_SqlType == ACTION_TYPE_SELECT
						|| m_SqlType == ACTION_TYPE_EXECUTE) {
					if (lstr_action_info.Elem.getFirstChild() != null) {
						m_SqlClause = lstr_action_info.Elem.getFirstChild()
								.getNodeValue();
						m_CacheFlag = m_CacheFlag
								& (SysSet.getSystemSet("CacheReport", "false")
										.equals("true") ? true : false);
					}
				}
			}
			if (m_SqlType == ACTION_TYPE_SELECTFROMDB
					|| m_SqlType == ACTION_TYPE_SELECT) { // 直接执行Select
				// 操作，返回结果集
				iRet = DoSelect(lstr_action_info);
				if (iRet < 0)
					break;
			} else if (m_SqlType == ACTION_TYPE_FROMDB
					|| m_SqlType == ACTION_TYPE_EXECUTE) {
				iRet = DoExecute(lstr_action_info);
				if (iRet < 0)
					break;
				else
					j++;
			} else {
			}
			if (m_hsArg != null) {
				m_hsArg.clear();
				m_hsArg = null;
			}
		}
		if (iRet >= 0) // 执行正常
		{
			iRet = j; // 对事务的影响来源于j的值，j>0则是有影响的数据行
		}
		return iRet; // 此返回值主要控制事务的提交
	}

	// 将arg定义存入m_Inargs或m_OutArgs
	private int DoBind(String cName, int iDataType, int iLen, int iArgType,
			int iSeq) throws FrameException {
		PrepareBuffer curBuffer = new PrepareBuffer(cName, iDataType, iLen,
				iSeq);
		if (curBuffer != null) {
			switch (iArgType) {
			case PrepareBuffer.PARA_IN:
				m_InArgs.add(curBuffer);
				break;
			case PrepareBuffer.PARA_OUT:
				m_OutArgs.add(curBuffer);
				break;
			default:
				throw new FrameException(-22993001, cName
						+ "在action配置表中出入参标志不符合要求");
			}
			return 0;
		} else
			return -1;
	}

	// 执行DML或PLSQL类的ACTION（数据库返回出参不返回结果集）
	private int DoExecute(STR_ACTION_INFO astr_action_info)
			throws FrameException {
		Element curElem, actElem, rowElem; //
		NodeList curList;
		int i, iCnt, iRows, iArgs, iRet;
		String strFldName, strFldVal;
		int iRet_Code = 0; // 针对PL/SQL块预定义的返回代码
		String cRet_Msg = "执行成功"; // 针对PL/SQL块预定义的返回信息

		String strActionTag = null;
		int iFldCnt;
		int inArg = 0;

		iRet = 0;
		i = 0;
		iRows = 0;
		iArgs = 0;
		ResultSetMetaData v_Fld;

		Element dsElem, rsElem;
		m_Msg = "成功执行";

		curElem = astr_action_info.Elem;
		// actElem = AddAction(astr_action_info);
		// //在返回DOC中建立ACTION元素及ACTION的NAME,TYPE,SEQ属性
		// 执行SQL语句
		strActionTag = AddAction(astr_action_info);
		curList = curElem.getElementsByTagName(ACTION_ROW); // 获得请求端ACTION的<Row/>子元素列表，INERT语句可能有多个Row
		iCnt = curList.getLength();
		try {
			m_CallableStatement = m_conn.prepareCall(m_SqlClause);
			// 注册返回码和返回信息
			// m_CallableStatement.registerOutParameter(1, Types.INTEGER);
			// m_CallableStatement.registerOutParameter(2, Types.INTEGER);
			iRet = 0;
			if (m_SqlType == ACTION_TYPE_FROMDB) {
				for (i = 0; i < iCnt; i++) {
					rowElem = (Element) curList.item(i); // 取一行入参Element
					inArg = m_Args;
					inArg = BindAllVar(rowElem, inArg); // 取一行ROW数据->m_InArgs相应位置
					if (inArg > 0)
						inArg = BindSessionVar(inArg);
					// if (inArg == 0)
					// {
					BindVariable(m_CallableStatement);
					// 执行取返回码和返回信息
					iRet = iRet + m_CallableStatement.executeUpdate();
					m_Msg = cRet_Msg;
					// start
					ListIterator itBuffer = m_OutArgs.listIterator(0);
					PrepareBuffer curBuffer;
					while (itBuffer.hasNext()) {
						curBuffer = (PrepareBuffer) itBuffer.next();
						curBuffer.Fetch(m_CallableStatement);
					}
					// }
					// else
					// {
					// iRet = -1;
					// m_Msg = "绑定入参出现错误";
					// break;
					// }
				}
			} else if (m_SqlType == ACTION_TYPE_EXECUTE) {
				iRet = m_CallableStatement.executeUpdate();
			}
		} catch (SQLException e) {
			iRet = -1;
			m_Msg = "SQL执行异常." + e.getMessage();
		} finally {
			try {
				if (m_CallableStatement != null)
					m_CallableStatement.close();
			} catch (SQLException e) {
				;
			}
			m_CallableStatement = null;
		}
		if (iRet > 0)
			m_Msg = m_Msg + "影响行数:" + Integer.toString(iRet);
		// 处理返回概要信息
		DoReturn(iRet);
		try {
			m_serializer.endTag(NAMESPACE, strActionTag);
		} catch (Exception e) {
			;
		}
		DoOutArgs();
		ClearVar(); // 清除原来变量所占用的内存空间
		return iRet;
	}

	// 处理返回的出参信息,针对PL/SQL块
	private void DoOutArgs() throws FrameException {
		Element rowElem;
		ListIterator itBuffer = m_OutArgs.listIterator();
		PrepareBuffer curBuffer;
		try {
			m_serializer.startTag(NAMESPACE, ACTION_ROW);
			// rowElem = (Element) m_OutDoc.CreateElement(ACTION_ROW, aActElem,
			// null);
			// if (rowElem != null) {
			while (itBuffer.hasNext()) {
				curBuffer = (PrepareBuffer) itBuffer.next();
				m_serializer.startTag(NAMESPACE, "C" + curBuffer.GetDataName());
				m_serializer.text(curBuffer.getValString());
				m_serializer.endTag(NAMESPACE, "C" + curBuffer.GetDataName());
				// m_OutDoc.CreateElement('C' + curBuffer.GetDataName(),
				// rowElem, curBuffer.getValString());
			}
			// }
			m_serializer.endTag(NAMESPACE, ACTION_ROW);
		} catch (Exception e) {
			;
		}
	}

	// 在输出中加入return节点(是action内的return接点）
	private int DoReturn(int iCode) throws FrameException {
		Element retElem;
		Attr curAttr;
		try {
			m_serializer.startTag(NAMESPACE, RETURN_INFO);
			m_serializer.attribute(null, RETURN_CODE, String.valueOf(iCode));
			m_serializer.attribute(null, RETURN_MSG, m_Msg);
			m_serializer.endTag(NAMESPACE, RETURN_INFO);
		} catch (Exception e) {
			throw new FrameException(-22993001, "创建节点出错" + e.getMessage());
		}
		return 0;
	}

	private int DoSelect(STR_ACTION_INFO astr_action_info)
			throws FrameException {
		Element curElem; // 入参的Element
		Element actElem; // 返回的Action Element
		Element rsElem; // 返回的Action下的Result Element
		Element dsElem; // 返回的Result下的DataSet Element
		Element rowElem; // 每行的row Element
		NodeList curList;
		String strActionTag;
		Attr curAttr;
		ResultSet rs;
		int iRet;
		ResultSetMetaData v_Fld;
		int i, j, iFldCnt, iArgs, iCnt;
		String cSeq, strTemp;

		int inArg = 0;
		i = 0;
		j = 0;
		m_Msg = "成功执行";
		iCnt = 0;
		iRet = 0;
		iArgs = 0;
		curAttr = null;
		curElem = astr_action_info.Elem;
		String strColName = null;
		boolean bCharSetError = false;
		strActionTag = AddAction(astr_action_info);
		// if (actElem == null) return -1;
		// Select的类型为"4"，传入ID，SQL来自于数据库的配置
		curList = curElem.getElementsByTagName(ACTION_ROW);// 获得 <Row/>的Elem
		iCnt = curList.getLength();
		try {
			inArg = m_Args;
			if (m_Args > 0) // 处理带有参数绑定的sql
			{
				// 绑定入参(1,2..)
				if (iCnt > 0) // 因为是Select操作, 只要iCnt>0，只会取第一条参数数据
				{
					rowElem = (Element) curList.item(0);
					inArg = BindAllVar(rowElem, inArg);
				}
				if (inArg > 0)
					inArg = BindSessionVar(inArg);
			}
			m_Logger.debug("cache标志:" + m_CacheFlag);
			if (m_CacheFlag == true) {
				if (getCacheResult() == true) // 使用cache数据，直接返回
				{
					m_isCachedData = true;
					ClearVar();
					return 1;
				} else
					m_isCachedData = false;
			}
			m_PreparedStatement = m_conn.prepareStatement(m_SqlClause);
			if (m_Args > 0)
				BindVariable(m_PreparedStatement);
			// if (bPrepare) {
			m_PreparedStatement.setFetchSize(100);
			rs = m_PreparedStatement.executeQuery();
			// m_Logger.debug("执行的sql:"+m_SqlClause);
			v_Fld = rs.getMetaData();
			iFldCnt = v_Fld.getColumnCount();
			dsElem = null;
			if (iFldCnt > 0) {
				// 处理返回的结果解结构描述
				// rsElem = (Element) m_OutDoc.CreateElement(RESULT,
				// actElem, null);
				m_serializer.startTag(NAMESPACE, RESULT);
				AddFldInfo(v_Fld);
				m_serializer.startTag(NAMESPACE, RESULT_DATASET);
				// dsElem = (Element) m_OutDoc.CreateElement(RESULT_DATASET,
				// rsElem, null);
			}
			// 开始打包
			// logger.info(new Date());
			String strVal = null;
			strColName = null;
			j = 0;
			// ArrayList alRs = new ArrayList();
			while (rs.next()) {
				j++;
				m_serializer.startTag(NAMESPACE, RESULT_ROW);
				// rowElem = m_OutDoc.m_Doc.createElement(RESULT_ROW);
				for (i = 1; i <= iFldCnt; i++) {
					strColName = "C" + String.valueOf(i);
					strVal = rs.getString(i);
					if (rs.wasNull() == false) {
						try {
							m_serializer.startTag(NAMESPACE, strColName);
							m_serializer.text(strVal);
							m_serializer.endTag(NAMESPACE, strColName);
						} catch (Exception e) {
							bCharSetError = true;
							strColName = v_Fld.getColumnName(i);
							throw e;
						}
					}
				}
				m_serializer.endTag(NAMESPACE, RESULT_ROW);
				if (m_MaxRows != -1) {
					if (j >= m_MaxRows) {
						throw new FrameException(22993010,
								"请限制查询条件，当前条件查询结果已经超出" + j);
					}
				}
			}
			m_serializer.endTag(NAMESPACE, RESULT_DATASET);
			m_serializer.endTag(NAMESPACE, RESULT);
		} catch (Exception e) {
			// j = -1;
			// e.printStackTrace();
			if (m_CurId == null || m_CurId.equals(""))
				strTemp = m_SqlClause;
			else
				strTemp = "";
			strTemp = strTemp + SysSet.getStackMsg(e);
			if (bCharSetError == true)
				strTemp = "数据存在乱码，列名:" + strColName + " 行:" + j + " \n"
						+ strTemp;
			throw new FrameException(-22993005, "查询出现异常:" + e.getMessage(),
					strTemp);// SysSet.getStackMsg(e));
			// m_Msg = "出现异常" + e.getMessage();
		} finally {
			try {
				if (m_PreparedStatement != null)
					m_PreparedStatement.close();
			} catch (SQLException e) {
				;
			}
			m_PreparedStatement = null;
		}
		// 处理返回的Action概况信息 = m_OutDoc->FindAttrByName(RETURN)
		DoReturn(j);
		try {
			m_serializer.endTag(NAMESPACE, strActionTag);
		} catch (Exception e) {
			;
		}
		ClearVar();
		return j;
	}

	@Override
	public void finalize() {
		try {
			Close();
		} catch (Exception e) {
		}
	}

	// DOM文档->m_Action转换 //注意：只转换第一个CommonOper
	private int GetAllActions() throws FrameException {
		Element rootElem = null, elemRcv = null;
		NodeList curList = null;
		Attr curAttr = null;
		String strChildName = null;
		STR_ACTION_INFO lstr_info;
		int i, iCnt;
		m_Action.clear(); // 清理m_Action
		// 取第一个CommonOper
		rootElem = m_InDoc.GetRootElem();
		rootElem = (Element) m_InDoc.FindChildByName(DATA, rootElem, false);
		rootElem = (Element) m_InDoc.FindChildByName(COMMONOPER, rootElem,
				false);
		if (rootElem != null) {
			curList = rootElem.getChildNodes();
			iCnt = curList.getLength();
			for (i = 0; i < iCnt; i++) {
				lstr_info = new STR_ACTION_INFO();
				strChildName = curList.item(i).getNodeName();
				if (strChildName.equals(ACTION)) {
					elemRcv = (Element) curList.item(i);
					curAttr = m_InDoc.FindAttrByName(ACTION_NAME, elemRcv,
							false);
					if (curAttr != null)
						lstr_info.Name = curAttr.getValue();
					curAttr = m_InDoc.FindAttrByName(ACTION_TYPE, elemRcv,
							false);
					if (curAttr != null)
						lstr_info.Type = curAttr.getValue(); // 添加Type属性，作为前台传入的SQL判断
					lstr_info.Elem = elemRcv;
					m_Action.add(lstr_info);
				}
			}
		}
		// 是否检查合法性
		//
		return m_Action.size();
	}

	// 取数据库配置
	private int GetDBType(Element actElem) throws FrameException {
		String strId = null;
		Element idElem = null;
		boolean bId = false;
		String strSql = null;
		ActionData actData = null;
		ActionArg actArg = null;
		int iCurPos = 0;
		Vector vPos = null;
		Object obj = null;
		StringBuffer strBuf = null;
		int iStart, iEnd;
		int j = 0, iFlag = 0, iDataType = 0, iLen = 0, iSeq = 0;
		String strChildName = null, strFieldName = null;
		ResultSet rs;
		idElem = (Element) m_InDoc.FindChildByName(ACTION_ID, actElem, false);
		if (idElem != null) {
			strId = idElem.getFirstChild().getNodeValue();
			m_CurId = strId;
			// 判断是否支持多个数据库,如果是则更换数据库
			if (SysSet.isMultiDB() && m_bSingle == false) {
				m_conn = SysSet.getMultiConnection(strId, 1);
			}
			actData = SysSet.getActionData(strId, m_conn);
			if (actData.m_CacheFlag == null)
				actData.m_CacheFlag = "0";
			// cache_flag=2 server端进行cache， =3代表server端和客户端均进行cache
			m_CacheFlag = m_CacheFlag
					& ((actData.m_CacheFlag.equals("2") || actData.m_CacheFlag
							.equals("3")) ? true : false);
			m_Logger.debug(m_CurId + "需要cache");
			m_SqlClause = actData.m_SQL.trim();
			if (actData.m_Type != null)
				m_SqlType = Integer.parseInt(actData.m_Type);
			else
				throw new FrameException(-22993001, strId
						+ "没有在action配置表中配置类型(Type)");
			if (m_SqlClause.equals(""))
				throw new FrameException(-22993002, "没有配置action_id为" + strId
						+ "的数据");
			bId = true;
			// 针对配置来自于数据库的处理
			if (bId) {
				iCurPos = 0;
				if (actData.m_Args != null) {
					j = actData.m_Args.size();
					for (int i = 0; i < j; i++) {
						actArg = (ActionArg) actData.m_Args.get(i);
						strFieldName = actArg.m_Name.toUpperCase();
						// 判断是否做替换处理
						if (actArg.m_Flag == PrepareBuffer.PARA_IN_REP) {
							// 查找seq指定的?在字符串的位置
							iStart = m_SqlClause.indexOf('?', 0);
							int iNewSeq = actArg.m_Seq - iCurPos;
							for (int k = 1; k < iNewSeq; k++) {
								iStart = m_SqlClause.indexOf('?', iStart + 1);
								if (iStart == -1)
									throw new FrameException(-22993005, strId
											+ "配置的可替换的参数设置的序号与实际不符"
											+ strFieldName);
							}
							// 直接替换内部可以识别的字符串,在绑定入参值时比较好地替换
							m_SqlClause = m_SqlClause.substring(0, iStart)
									+ "<" + strFieldName + ">"
									+ m_SqlClause.substring(iStart + 1);
							if (m_hsArg == null)
								m_hsArg = new HashSet();
							m_hsArg.add(strFieldName);
							iCurPos++; // 计算当前已经替换了的替换参数数目
							continue; // 跳过,继续下一个处理
						}
						iFlag = actArg.m_Flag;
						iDataType = actArg.m_Type;
						iLen = actArg.m_Length;
						iSeq = actArg.m_Seq - iCurPos;
						DoBind(strFieldName, iDataType, iLen, iFlag, iSeq);
					}
				} else {
					j = 0;
				}
			} else
				j = -1;
		}
		return j;
	}

	// 返回执行信息
	public String GetMessage() {
		return m_Msg;
	}

	// 返回应答XML字符串
	public String GetRetMsg() throws FrameException {
		String strMsg = null;
		if (m_CacheFlag == true) {
			if (m_isCachedData == false) {
				strMsg = m_bsOut.toString();
				if (m_CacheData.size() < m_CacheSize) {
					m_Logger.debug("Cache结果集:" + m_CurId);
					m_CacheData.put(new Integer(m_KeyValue.hashCode()), strMsg);
				}
			} else {
				strMsg = (String) m_CacheData.get(new Integer(m_KeyValue
						.hashCode()));
			}
		} else {
			strMsg = m_bsOut.toString();
		}
		try {
			m_bsOut.close();
		} catch (IOException e) {
			throw new FrameException(-229930021, "关闭处理结果集Buffer出现IO异常");
		} finally {
			m_bsOut = null;
		}
		return strMsg;
	}

	// 初始化
	public void Init() throws FrameException {
		m_Action = new Vector();
		m_InArgs = new Vector();
		m_OutArgs = new Vector();
		m_InDoc = new XMLTool();

		m_UseCache = SysSet.getSystemSet("UseCacheData", "false")
				.equals("true") ? true : false;
		if (m_UseCache == true)
			m_CacheSize = Integer.parseInt(SysSet.getSystemSet("CacheSize",
					"800"));
		// m_OutDoc = new XMLTool();
		// m_OutDoc.New(DOC_ROOT_NAME); //建立输出DOC的根节点
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
					System.getProperty(XmlPullParserFactory.PROPERTY_NAME),
					null);
			m_serializer = factory.newSerializer();
			m_bsOut = new ByteArrayOutputStream();
			m_serializer.setOutput(new PrintWriter(m_bsOut, true));
			m_serializer.startDocument("UTF-8", null);
			// add some empty lines before first start tag
			m_serializer.ignorableWhitespace("\n\n");
			// if prefix is not set serializer will generate automatically
			// prefix
			// we overwrite this mechanism and manually namespace to be default
			// (empty prefix)
			m_serializer.setPrefix("", NAMESPACE);
			m_serializer.startTag(NAMESPACE, DOC_ROOT_NAME);
			m_serializer.startTag(NAMESPACE, DEFINITION);
			m_serializer.attribute(null, DEFINITION_TYPE, "2");
			m_serializer.endTag(NAMESPACE, DEFINITION);
			m_serializer.startTag(NAMESPACE, DATA);
			m_serializer.startTag(NAMESPACE, COMMONOPER);
		} catch (Exception e) {
			throw new FrameException(-22993001, "创建返回xml文档出错");
		}
		m_Msg = ""; // 清除工作状态
		m_SqlClause = "";
		m_SqlType = SQL_TYPE_INVALID;
		m_Args = 0;
	}

	// 恢复初始状态
	private void New() throws FrameException {
		Close();
		ClearVar();
		m_Action.clear();
		m_InDoc.Reset();
		// m_OutDoc.Reset();
		// m_OutDoc.New(DOC_ROOT_NAME); //建立输出DOC的根节点
		m_Msg = null; // 清除工作状态
		m_SqlClause = null;
		m_SqlType = SQL_TYPE_INVALID;
		m_Args = 0;
	}

	// 读入客户端传入的XML->m_InDoc
	public boolean Prepare(String strXML, HashMap<String, String> p_staff_info)
			throws FrameException {
		staff_info = p_staff_info;
		if (m_InDoc.ReadXML(strXML) < 0)
			return false;
		// m_Logger.debug("员工信息:"+staff_info.toString());
		// 读取最大限制行数
		if (m_UseCache)
			m_KeyValue = strXML;
		String strRow = SysSet.getSystemSet("MaxRows", "20000");// 缺省限制20000行
		// ,MaxRows在System节进行配置
		m_MaxRows = Integer.parseInt(strRow);
		GetAllActions();
		return true;
	}

	// 运行客户端请求
	public int Run() throws FrameException {
		int iAction, iRet = -1;
		// BuildOutRoot();
		// BuildOutDefinition();
		iAction = m_Action.size();// GetAllActions();
		// //从m_InDoc取ACTIONS->m_Action
		// m_Logger.debug("start process Actions :"+m_InDoc.GetXML());
		if (iAction > 0) // ACTION数量>0，应执行
			iRet = DoActions();
		else
		// ACTION数量=0，不继续处理
		{
			m_Msg = "需要执行的Action数目为0，未做任何处理";
			iRet = -1;
		}
		// m_Logger.debug("Build Action return result.");
		if ((m_CacheFlag == true && m_isCachedData == true) == false)
			BuildOutReturn(iRet);
		return iRet;
	}

	// 设置数据库连接
	public void SetConnection(Connection conn) {
		if (m_conn != null) {
			try {
				m_conn.close();
				m_conn = null;
			} catch (SQLException e) {
				;
			}
		}
		m_conn = conn;
	}

	public String getCurrentId() {
		return m_CurId;
	}

	// 设置指定的变量值
	private boolean SetVarVal(String aArgName, String aArgVal) {
		boolean bMatch;
		String strName;
		ListIterator itBuffer = m_InArgs.listIterator(0);
		PrepareBuffer curBuffer;
		bMatch = false;
		String strFind = null;
		int iPos = 0;
		strName = aArgName.toUpperCase().trim(); // 变量名
		// 判断是否为需要替换的参数字段
		if (m_hsArg != null && m_hsArg.contains(strName)) {
			// 需要替换处理,不需要进行绑定
			strFind = "<" + strName + ">";
			iPos = m_SqlClause.indexOf(strFind, 0);
			while (iPos >= 0) {
				m_SqlClause = m_SqlClause.substring(0, iPos) + aArgVal
						+ m_SqlClause.substring(iPos + strFind.length());
				iPos = m_SqlClause.indexOf(strFind, iPos);
			}
			bMatch = true;
		} else {
			while (itBuffer.hasNext()) {
				curBuffer = (PrepareBuffer) itBuffer.next();
				if (strName.equals(curBuffer.GetDataName())) {
					curBuffer.setValString(aArgVal);
					bMatch = true;
					// break; 考虑到同一参数名多次使用的情况，每次匹配到最后
				}
			}
		}
		// if (!bMatch) {
		// //如没有匹配到，则从用户的Session中寻找
		// if (staff_info != null && staff_info.get(aArgName) != null) {
		//            	
		// itBuffer = m_InArgs.listIterator(0);
		// while (itBuffer.hasNext()) {
		// curBuffer = (PrepareBuffer) itBuffer.next();
		// if (strName.equals(curBuffer.GetDataName())) {
		// curBuffer.setValString(aArgVal);
		// bMatch = true;
		// // break;
		// }
		// }
		// }
		// }
		// 以下
		itBuffer = m_OutArgs.listIterator(0);
		while (itBuffer.hasNext()) {
			curBuffer = (PrepareBuffer) itBuffer.next();
			if (strName == curBuffer.GetDataName()) {
				curBuffer.setValString(aArgVal);
				bMatch = true;
				break;
			}
		}
		if (!bMatch) {
			// 如没有匹配到，则从用户的Session中寻找
			if (staff_info != null
					&& staff_info.get(aArgName.toLowerCase()) != null) {
				itBuffer = m_OutArgs.listIterator(0);
				while (itBuffer.hasNext()) {
					curBuffer = (PrepareBuffer) itBuffer.next();
					if (strName.equals(curBuffer.GetDataName())) {
						curBuffer.setValString(aArgVal);
						bMatch = true;
						break;
					}
				}
			}
		}
		return bMatch;
	}

	public String GetSysName() {
		String strSysName = null;
		Element rootElem, defElem;
		Attr curAttr;
		try {
			rootElem = m_InDoc.GetRootElem();
			defElem = (Element) m_InDoc.FindChildByName(DEFINITION, rootElem,
					false);
			curAttr = m_InDoc.FindAttrByName(SYSTEMNAME, defElem, false);
			if (curAttr != null)
				strSysName = curAttr.getValue();
		} catch (FrameException e) {
			;
		}
		return strSysName;
	}

	public void setSingle(boolean bSingle) {
		m_bSingle = bSingle;
	}

	public boolean isTerminalCall() {
		boolean bCall = false;
		int iType = -1;
		if (m_Action.size() > 0) {
			STR_ACTION_INFO lstr_action_info = (STR_ACTION_INFO) m_Action
					.get(0);
			if (lstr_action_info.Type != null) {
				iType = Integer.parseInt(lstr_action_info.Type);
				if (iType == ACTION_TYPE_SELECT || iType == ACTION_TYPE_EXECUTE)
					bCall = true;
			}
		}
		return bCall;
	}
}
