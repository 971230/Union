package com.powerise.ibss.framework;

/**
 * 服务调用主控
 * 错误号码段： 229900xx
 */

import com.powerise.ibss.framework.ejb.DispatchEJB;
import com.powerise.ibss.util.*;
import com.ztesoft.common.dao.ConnectionContext;

import org.apache.log4j.Logger;

import javax.transaction.UserTransaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * 此类完成的功能： 1、接受JSP页面形成数据结构，并通过功能号动态实例化具体对应的逻辑实现类
 * 2、对数据结构中的参数集、数据集进行统一的操作。如转码、数据校验等
 * 
 * @author Powerise GD
 * @version 1.0
 */
public class ActionDispatch {
	private static Logger m_Logger = Logger.getLogger(ActionDispatch.class);
	private String m_RetMsg = null;
	private StringBuffer m_DataBuf = null;

	// 层 2，app 层
	// static {
	// String s = System.getProperty("SYSTEM_SITE"); //通过参数来判断系统所在的层，如果在web层，则需要
	// if (s != null && s.equals("WEB"))
	// m_SystemSite = 1;
	// }
	private void setDataBuffer(StringBuffer bufData) {
		m_DataBuf = bufData;
	}

	public static String performServlet(DynamicDict p_dynamic_dict,
			StringBuffer bufData) {
		return performServlet(p_dynamic_dict, null, bufData);
	}

	public static String performServlet(DynamicDict p_dynamic_dict,
			String strDBName, StringBuffer bufData) {
		// if (m_SystemSite == 1) //web 调用，通过ejb来完成
		// return performServletByEJB(p_dynamic_dict, strDBName);
		String strMsg;

		ActionDispatch disp = new ActionDispatch();
		disp.setDataBuffer(bufData);
		try {
			p_dynamic_dict = disp.performDict(p_dynamic_dict, strDBName, true);
			strMsg = disp.m_RetMsg;
		} catch (java.lang.Throwable th) {
			p_dynamic_dict.flag = -22991011;
			p_dynamic_dict.msg = "系统调用异常" + th.getClass().getName();
			p_dynamic_dict.exception = SysSet.getStackMsg(th);
			strMsg = SysSet.doError(p_dynamic_dict.flag, p_dynamic_dict.msg,
					p_dynamic_dict.exception);
		}
		disp = null;
		p_dynamic_dict.SetConnection(null);
		p_dynamic_dict.destroy();
		return strMsg;
	}

	public static DynamicDict dispatch(DynamicDict p_dynamic_dict) {
		return dispatch(p_dynamic_dict, null);
	}

	/**
	 * 支持多个数据库连接的处理
	 * 
	 * @param p_dynamic_dict
	 * @param strDBName
	 *            由前台指定的数据库连接，因此不能使用xa,需要单独处理
	 * @return
	 */
	private DynamicDict performDictMulti(DynamicDict p_dynamic_dict,
			String strDBName, boolean bReturn) {
		boolean commit = true;
		boolean bXAType = false;// 根据是否采用XA
		// Driver来处理事务，如果是XA,则采用JTA方式，否则通过直接的commit和rollback来操作
		String action_id = null;
		Connection conn = null;
		boolean bSingle = false;
		UserTransaction usTran = null;
		try {
			// m_Logger.debug("调用的action_id是"+p_dynamic_dict.m_ActionId+" flag
			// 是"+p_dynamic_dict.flag);
			if (p_dynamic_dict.flag == 1) {
				m_Logger.debug("后台JSP直接调用，使用MultiDB模式:"
						+ p_dynamic_dict.m_ActionId);
				return performWEBDynamicAction(p_dynamic_dict, strDBName);
			}
			action_id = p_dynamic_dict.getServiceName();
			// 因为是相当于应用程序的入口，不允许利用之前的数据库连接，否则风险太大
			conn = p_dynamic_dict.GetConnection();
			if (conn != null) {
				throw new FrameException(-22990001, action_id + "已经建立了数据库连接!");
			}
			if (strDBName == null) {
				// 需要判断是否采用XA，如果不是，则仅仅取连接而已
				String s = SysSet.getDBNameById(action_id, 2);
				bXAType = SysSet.isXAType(s);
				if (bXAType) {
					// 开始初始化XA连接
					usTran = SysSet.getUserTransaction();
					SysSet.tpBegin(usTran);
				}
				conn = SysSet.getMultiConnection(action_id, 2);
			} else {
				conn = DBUtil.getConnection(strDBName);
				bSingle = true; // 需要单独关闭
				// ,这种情况，针对前台指定的数据库连接，有可能数据库产品与目前PowerIBSS使用的数据库不同，也无法进行xa处理
			}
			// 不论是哪个数据库，均需要框架的服务和产讯配置表
			String action_class_name = SysSet.getActionClassName(action_id,
					conn);
			IAction action = SysSet.getActionInstance(action_class_name);
			// 调用逻辑实现类perform 方法
			conn.setAutoCommit(false);
			p_dynamic_dict.SetConnection(conn);
			action.perform(p_dynamic_dict);
			if (bReturn) {
				StringBuffer retCodeBuf = new StringBuffer(String
						.valueOf(p_dynamic_dict.flag));
				StringBuffer retMsgBuf = new StringBuffer(
						(p_dynamic_dict.msg == null) ? " " : p_dynamic_dict.msg);
				// try {
				m_RetMsg = SysSet.doReturn(retCodeBuf, retMsgBuf,
						p_dynamic_dict);
				// } catch (Exception e) {
				// commit = false;
				// m_RetMsg = e.getMessage();
				// }
			}
		} catch (FrameException fe) {
			p_dynamic_dict.flag = fe.getErrorCode();
			p_dynamic_dict.msg = fe.getErrorMsg();
			p_dynamic_dict.exception = fe.getSysMsg() + "\n"
					+ SysSet.getStackMsg(fe);
			commit = false;
		} catch (java.lang.Throwable th) {
			p_dynamic_dict.flag = -5009;
			p_dynamic_dict.msg = "系统调用异常" + th.getClass().getName();
			p_dynamic_dict.exception = SysSet.getStackMsg(th);
			commit = false;
		} finally {
			try {
				// 统一关闭数据库连接
				if (conn != null) {
					if (commit) {
						// 一切执行正常
						if (bSingle)
							conn.commit();
						else {
							if (bXAType)
								SysSet.tpCommit(usTran);
							else
								SysSet.commitMulti(action_id);
						}
					} else
					// 出现异常
					if (bSingle)
						conn.rollback();
					else {
						if (bXAType)
							SysSet.tpRollback(usTran);
						else
							SysSet.rollbackMulti(action_id);
					}
					if (bSingle)
						conn.close();
					else {
						SysSet.closeDBs();
					}
					conn = null;
				}
				if (usTran != null) {
					m_Logger.debug("关闭UserTransaction");
					usTran = null;
				}
			} catch (Exception e) {
				m_Logger.info("关闭数据库连接时，出现异常" + SysSet.getStackMsg(e));
				p_dynamic_dict.flag = -5001;
				p_dynamic_dict.msg = "关闭数据库连接时，出现异常";
				p_dynamic_dict.exception = e.getMessage();
			}
		}
		m_Logger.debug(p_dynamic_dict.m_ActionId + " 返回执行代码:"
				+ p_dynamic_dict.flag);
		if (commit == false) {

			if (GlobalNames.ERR_TO_DB) {
				long lErrSeq = 0;

				lErrSeq = SysSet.writeLog(p_dynamic_dict.m_ActionId,
						p_dynamic_dict.flag, p_dynamic_dict.msg,
						p_dynamic_dict.exception == null ? ""
								: p_dynamic_dict.exception);
				p_dynamic_dict.msg = p_dynamic_dict.msg + ".\n" + "错误日志序号："
						+ Long.toString(lErrSeq);
			}

			if (bReturn)
				m_RetMsg = SysSet.doError(p_dynamic_dict.flag,
						p_dynamic_dict.msg, p_dynamic_dict.exception);
		}
		p_dynamic_dict.SetConnection(null);
		return p_dynamic_dict;
	}

	public static DynamicDict dispatch(DynamicDict p_dynamic_dict,
			String strDBName) {
		// if (m_SystemSite == 1) //web 调用，通过ejb来完成
		// return dispatchByEJB(p_dynamic_dict, strDBName);
		m_Logger.debug("调用服务Id:" + p_dynamic_dict.m_ActionId);
		ActionDispatch disp = new ActionDispatch();
		disp.performDict(p_dynamic_dict, strDBName, false);
		disp = null;
		return p_dynamic_dict;
	}

	/**
	 * 接受入参数据结构，对其参数集、数据集进行转码、数据校验操作，然后分发操作。最后如 系统出现异常，则调用日志处理系统进行统一处理。
	 * 为了方便，入参数据结构是整个业务逻辑处理的基础，处理完后结果存在此数据结构中
	 * 
	 * @param p_dynamic_dict -
	 *            数据结构体
	 */
	private DynamicDict performDict(DynamicDict p_dynamic_dict, String db_name,
			boolean bReturn) {
		boolean commit = true;
		Connection conn = null;
		long lStart = 0, curCalc = 0;
		java.util.Date dStart, dEnd;
		lStart = System.currentTimeMillis();
		dStart = new java.util.Date();
		try {
			curCalc = Integer.parseInt(SysSet.getSystemSet("ConsumeSecs",
					"3000"));
		} catch (Exception e) {
			curCalc = 3000;
		}
		// add by wui
		String action_id = p_dynamic_dict.m_ActionId;

		try {
			conn = p_dynamic_dict.GetConnection();
			if (conn != null)
				throw new FrameException(-22990001, action_id + "已经建立了数据库连接!");
			if (SysSet.isMultiDB()) {// 多数据库模式
				performDictMulti(p_dynamic_dict, db_name, bReturn);
				return p_dynamic_dict;
			}
			if (p_dynamic_dict.flag == 1) {
				performWEBDynamicAction(p_dynamic_dict, db_name);
				return p_dynamic_dict;
			}
			if (db_name == null)
				conn = DBUtil.getConnection();
			else
				conn = DBUtil.getConnection(db_name);
			String action_class_name = SysSet.getActionClassName(action_id,
					conn);
			IAction action = SysSet.getActionInstance(action_class_name);
			// 调用逻辑实现类perform 方法
			conn.setAutoCommit(false);
			p_dynamic_dict.SetConnection(conn);
			//RuleUtil.processBefore(p_dynamic_dict);
			action.perform(p_dynamic_dict);
			//RuleUtil.processAfter(p_dynamic_dict);
			if (bReturn) {
				StringBuffer retCodeBuf = new StringBuffer(String
						.valueOf(p_dynamic_dict.flag));
				StringBuffer retMsgBuf = new StringBuffer(
						(p_dynamic_dict.msg == null) ? " " : p_dynamic_dict.msg);
				m_RetMsg = SysSet.doReturn(retCodeBuf, retMsgBuf,
						p_dynamic_dict);
			}
		} catch (FrameException fe) {
			p_dynamic_dict.flag = fe.getErrorCode();
			p_dynamic_dict.msg = fe.getErrorMsg();
			p_dynamic_dict.exception = fe.getSysMsg() + "\n"
					+ SysSet.getStackMsg(fe);
			commit = false;
		} catch (java.lang.Throwable th) {
			p_dynamic_dict.flag = -22990002;
			p_dynamic_dict.msg = "系统调用异常" + th.getClass().getName();
			p_dynamic_dict.exception = SysSet.getStackMsg(th);
			commit = false;
		} finally {
			try {
				// 统一关闭数据库连接
				if (conn != null) {
					if (commit) {
						// 一切执行正常
						conn.commit();
						ConnectionContext.getContext().allCommit();
					} else{
						// 出现异常
						conn.rollback();
						ConnectionContext.getContext().allRollback();
					}
					conn.close();
					ConnectionContext.getContext().allCloseConnection();
					conn = null;
				}
			} catch (SQLException e) {
				m_Logger.info("关闭数据库连接时，出现异常" + SysSet.getStackMsg(e));
				p_dynamic_dict.flag = -22990003;
				p_dynamic_dict.msg = "关闭数据库连接时，出现异常";
				p_dynamic_dict.exception = e.getMessage();
			}
		}
		long lCalc = System.currentTimeMillis() - lStart;
		// lCalc = lCalc / 1000;
		if (lCalc >= curCalc) {
			dEnd = new java.util.Date();
			StringBuffer logBuf = new StringBuffer();
			logBuf.append("Service:");
			logBuf.append(p_dynamic_dict.m_ActionId);
			logBuf.append("/");
			logBuf.append(dStart);
			logBuf.append("/");
			logBuf.append(dEnd);
			logBuf.append("/");
			logBuf.append(lCalc);
			logBuf.append("/");
			if (m_DataBuf != null) {
				logBuf.append(m_DataBuf.toString());
				m_DataBuf = null;
			} else
				logBuf.append(p_dynamic_dict.toString());
			m_Logger.warn(logBuf.toString());
			logBuf = null;
		}
		Monitor.setInfo("Service:" + p_dynamic_dict.m_ActionId, lCalc);
		if (commit == false) {
			if (GlobalNames.ERR_TO_DB) {
				long lErrSeq = 0;
				lErrSeq = SysSet.writeLog(p_dynamic_dict.m_ActionId,
						p_dynamic_dict.flag, p_dynamic_dict.msg,
						p_dynamic_dict.exception == null ? ""
								: p_dynamic_dict.exception);
				p_dynamic_dict.msg = p_dynamic_dict.msg + " (错误日志序号："
						+ Long.toString(lErrSeq) + ")";

			}
			if (bReturn)
				m_RetMsg = SysSet.doError(p_dynamic_dict.flag,
						p_dynamic_dict.msg, p_dynamic_dict.exception);
		}
		p_dynamic_dict.SetConnection(null);
		return p_dynamic_dict;
	}

	public static DynamicDict performWEBDynamicAction(DynamicDict p_dynamic_dict) {
		return performWEBDynamicAction(p_dynamic_dict, null);
	}

//	private static DynamicDict performWEBDynamicActionByEJB(
//			DynamicDict p_dynamic_dict, String strDBName) {
//		try {
//			DispatchEJB dispatchEJB = EJBUtil.getDispatchEJB();
//			m_Logger.debug("调用远程EJB,WEB调用后台动态Service:"
//					+ p_dynamic_dict.m_ActionId);
//			p_dynamic_dict = dispatchEJB
//					.performWEBDynamicAction(p_dynamic_dict);
//			// dispatchEJB.remove();
//		} catch (FrameException fre) {
//			m_Logger.info("调用远程EJB异常," + SysSet.getStackMsg(fre));
//			p_dynamic_dict.flag = fre.getErrorCode();
//			p_dynamic_dict.msg = fre.getErrorMsg();
//			p_dynamic_dict.exception = fre.getSysMsg();
//		} catch (Throwable re) {
//			m_Logger.info("调用远程EJB配置异常," + SysSet.getStackMsg(re));
//			p_dynamic_dict.flag = -22990010;
//			p_dynamic_dict.msg = "EJB出现RemoteException 异常！";
//			p_dynamic_dict.exception = re.getMessage();
//			re.printStackTrace();
//		}
//		return p_dynamic_dict;
//	}

	public static DynamicDict performWEBDynamicAction(
			DynamicDict p_dynamic_dict, String strDBName) {
		try {
			// m_Logger.debug("调用方式为webdynamicaction，action_id为"+action_id);
			WebCommonAction web_comm = new WebCommonAction(p_dynamic_dict);
			web_comm.execute(strDBName);
			p_dynamic_dict = web_comm.getDynamicDict();

		} catch (FrameException fre) {
			p_dynamic_dict.flag = fre.getErrorCode();
			p_dynamic_dict.msg = fre.getErrorMsg();
			p_dynamic_dict.exception = fre.getSysMsg() + "\n"
					+ SysSet.getStackMsg(fre);
		} catch (java.lang.Throwable th) {
			p_dynamic_dict.flag = -22990004;
			p_dynamic_dict.msg = "系统调用异常:" + th.getClass().getName();
			p_dynamic_dict.exception = SysSet.getStackMsg(th);
		}
		if (p_dynamic_dict.flag < 0) {
			if (GlobalNames.ERR_TO_DB) {
				long lErrSeq = 0;
				lErrSeq = SysSet.writeLog(p_dynamic_dict.m_ActionId,
						p_dynamic_dict.flag, p_dynamic_dict.msg,
						p_dynamic_dict.exception == null ? ""
								: p_dynamic_dict.exception);
				p_dynamic_dict.msg = p_dynamic_dict.msg + ".\n" + "错误日志序号："
						+ Long.toString(lErrSeq);
			}
		}
		return p_dynamic_dict;
	}

	/**
	 * 
	 * @param p_dynamic_dict
	 * @param strDBName
	 * @return
	 */
//	private static DynamicDict dispatchByEJB(DynamicDict p_dynamic_dict,
//			String strDBName) {
//		try {
//			DispatchEJB dispatchEJB = EJBUtil.getDispatchEJB();
//			// 如p_dynamic_dict flag对象的值为1，则表示是通过jsp来调用，否则就是通过sevlet来调用服务
//			if (p_dynamic_dict.flag != 1) {
//				p_dynamic_dict = dispatchEJB.perform(p_dynamic_dict);
//			} else {
//				m_Logger.debug("调用远程EJB,WEB调用后台动态Service:"
//						+ p_dynamic_dict.m_ActionId);
//				p_dynamic_dict = dispatchEJB
//						.performWEBDynamicAction(p_dynamic_dict);
//			}
//			// dispatchEJB.remove();
//		} catch (FrameException fre) {
//			m_Logger.info("调用远程EJB异常," + SysSet.getStackMsg(fre));
//			p_dynamic_dict.flag = fre.getErrorCode();
//			p_dynamic_dict.msg = fre.getErrorMsg();
//			p_dynamic_dict.exception = fre.getSysMsg();
//		} catch (Throwable re) {
//			m_Logger.info("调用远程EJB配置异常," + SysSet.getStackMsg(re));
//			p_dynamic_dict.flag = -22990005;
//			p_dynamic_dict.msg = "EJB出现RemoteException 异常！";
//			p_dynamic_dict.exception = re.getMessage();
//			re.printStackTrace();
//		}
//		return p_dynamic_dict;
//	}

	public static String performServletDynamicAction(String strMsg,
			HashMap<String,String> hmStaffInfo) {
		String outMsg = null;
		CommAction act = null;
		int iRetCode = 0;
		Connection conn = null;
		long lErrSeq = 0;
		String db_name = null;
		boolean commit = false;
		boolean bSingle = false;
		UserTransaction usTran = null;
		String strId = null;
		int iCalc = 0;
		boolean bXAType = false; // 缺省不采用XA方式,对于通用查询来说,绝大部分都是Query操作，而且不方便判断获取action_id，因此直接就是使用不采用XA
		long lStart = 0;
		java.util.Date dStart, dEnd;
		lStart = System.currentTimeMillis();
		dStart = new java.util.Date();
		try {
			iCalc = Integer
					.parseInt(SysSet.getSystemSet("ConsumeSecs", "3000"));
		} catch (Exception e) {
			iCalc = 3000;
		}
		// if (m_SystemSite == 1) //web 调用，通过ejb来完成
		// return performServletDynamicActionByEJB(strMsg, hmStaffInfo);
		try {
			act = new CommAction();
			// m_Logger.debug("开始调用通用查询：" + strMsg);
			act.Prepare(strMsg, hmStaffInfo);
			// 判断是否为支持多数据库环境
			// if(SysSet.isMultiDB())
			db_name = act.GetSysName();
			if (db_name != null || act.isTerminalCall())
				bSingle = true;
			else {
				if (SysSet.isMultiDB())
					bSingle = false;
				else
					bSingle = true;
			}
			// 如果是单独的连接，则只能读取当前缺省的连接
			if (bSingle) {
				if (db_name == null)
					conn = DBUtil.getConnection();
				else
					conn = DBUtil.getConnection(db_name);
			}
			act.setSingle(bSingle);
			if (conn != null) // 单独的数据库连接
				act.SetConnection(conn);
			else {
				// 在多数据库模式下，数据库的连接是在CommonAction中来获取的，因此此处Connection
				// 为null,则认为是多数据库模式
				// 判断是否采用了XA的Driver
				if (bXAType) {
					// 开始初始化XA连接
					usTran = SysSet.getUserTransaction();
					SysSet.tpBegin(usTran);
				}
			}
			iRetCode = act.Run();
			if (iRetCode <= 0)
				commit = false;
			else
				commit = true;
			outMsg = act.GetRetMsg();

			long lCalc = System.currentTimeMillis() - lStart;
			if (act != null)
				strId = act.getCurrentId();
			if (strId == null)
				strId = "CommonRept";
			if (lCalc >= iCalc) {
				dEnd = new java.util.Date();
				StringBuffer logBuf = new StringBuffer();
				logBuf.append("Action:");
				logBuf.append(strId);
				logBuf.append("/");
				logBuf.append(dStart);
				logBuf.append("/");
				logBuf.append(dEnd);
				logBuf.append("/");
				logBuf.append(lCalc);
				logBuf.append("/");
				logBuf.append(strMsg);
				m_Logger.warn(logBuf.toString());
				logBuf = null;
			}
			Monitor.setInfo("Action:" + strId, lCalc);
		} catch (FrameException fre) {
			if (act != null)
				strId = act.getCurrentId();
			lErrSeq = SysSet.writeLog("COMMSERVER", fre.getErrorCode(), strId
					+ fre.getErrorMsg(), fre.getSysMsg());
			outMsg = SysSet.doError(fre.getErrorCode(), "错误日志号："
					+ Long.toString(lErrSeq) + ".\n" + strId
					+ fre.getErrorMsg(), fre.getSysMsg(), 2);
			commit = false;
		} catch (java.lang.Throwable th) {
			if (act != null)
				strId = act.getCurrentId();
			lErrSeq = SysSet.writeLog("COMMSERVER", -5011, strId
					+ th.getMessage(), SysSet.getStackMsg(th));
			outMsg = SysSet.doError(-22990010, "错误日志号："
					+ Long.toString(lErrSeq) + ".\n" + strId + th.getMessage(),
					SysSet.getStackMsg(th), 2);
			commit = false;
		} finally {
			try {
				if (act != null) {
					act.Close();
					act = null;
				}
				// 统一关闭数据库连接
				if (bSingle) {
					if (conn != null) {
						// 手工提交方式
						// m_Logger.debug("判断是否需要提交数据");
						if (commit) {
							// 一切执行正常
							m_Logger.debug("通用查询提交数据库");
							conn.commit();
						} else
							// 出现异常
							conn.rollback();
						conn.close();
						conn = null;
					}
				} else {
					if (commit) {
						if (bXAType)
							SysSet.tpCommit(usTran);
						else
							SysSet.commitMulti(null);
					} else {
						if (bXAType)
							SysSet.tpRollback(usTran);
						else
							SysSet.rollbackMulti(null);
					}
					SysSet.closeDBs();
					if (usTran != null)
						usTran = null;
				}
			} catch (Exception e) {
				m_Logger.info("关闭数据库时出现异常！" + SysSet.getStackMsg(e));
				outMsg = SysSet.doError(-22990009, "关闭数据库时出现异常！", e
						.getMessage(), 2);
			}
		}
		return outMsg;
	}

//	/**
//	 * 通过EJB来调用,由Web Server的CommonServer发出
//	 * 
//	 * @param p_dynamic_dict
//	 * @param strDBName
//	 * @return
//	 */
//	private static String performServletDynamicActionByEJB(String strMsg,
//			HashMap hmStaffInfo) {
//		String outMsg = null;
//		try {
//			DispatchEJB dispatchEJB = EJBUtil.getDispatchEJB();
//			// 如p_dynamic_dict flag对象的值为1，则表示是通过jsp来调用，否则就是通过sevlet来调用服务
//			m_Logger.debug("调用远程EJB,WEB调用后台通用查询");
//			outMsg = dispatchEJB.performServletDynamicAction(strMsg,
//					hmStaffInfo);
//			// dispatchEJB.remove();
//		} catch (Throwable re) {
//			m_Logger.info("调用远程EJB(CommonServer)出现系统异常,"
//					+ SysSet.getStackMsg(re));
//			outMsg = SysSet.doError(-22990006, "CommonServer 远程调EJB出现异常！", re
//					.getMessage());
//		}
//		return outMsg;
//	}

	/**
	 * 远程调用EJB，由Web Server的AgentServlet发出
	 * 
	 * @param p_dynamic_dict
	 * @param strDBName
	 * @return
	 */
	public static String performServletByEJB(DynamicDict p_dynamic_dict,
			String strDBName) {
		String strMsg = null;
		try {
			DispatchEJB dispatchEJB = EJBUtil.getDispatchEJB();
			m_Logger.debug("调用远程EJB,Servlet调用后台动态Service:"
					+ p_dynamic_dict.m_ActionId);
			strMsg = dispatchEJB.performServlet(p_dynamic_dict);
			// dispatchEJB.remove();
		} catch (FrameException fre) {
			m_Logger.info("调用远程EJB异常," + SysSet.getStackMsg(fre));
			p_dynamic_dict.flag = fre.getErrorCode();
			p_dynamic_dict.msg = fre.getErrorMsg();
			p_dynamic_dict.exception = fre.getSysMsg();
		} catch (Throwable re) {
			m_Logger.info("调用远程EJB配置异常," + SysSet.getStackMsg(re));
			p_dynamic_dict.flag = -22990007;
			p_dynamic_dict.msg = "EJB出现RemoteException 异常！";
			p_dynamic_dict.exception = re.getMessage();
		}
		return strMsg;
	}
}
