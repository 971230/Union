package zte.net.ecsord.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import params.order.req.OrderQueueCardActionLogReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.sr.req.QueWriMachStaInBatchRGRequset;
import zte.net.ecsord.params.sr.resp.QueWriMachStaInBatchRGResponse;
import zte.net.ecsord.params.sr.resp.RceiveICCIDResp;
import zte.net.ecsord.params.sr.vo.MachineNoRG;
import zte.net.ecsord.params.sr.vo.MachineNoRGResp;
import zte.net.ecsord.params.sr.vo.QueWriMachStaInBatchDataRG;
import zte.net.ecsord.params.sr.vo.QueWriMachStaInBatchDataRGBodyReq;
import zte.net.ecsord.params.sr.vo.QueWriMachStaInBatchDataRGHeadReq;
import zte.net.ecsord.params.sr.vo.QueWriMachStaInBatchRGBody;
import zte.net.ecsord.params.sr.vo.QueWriMachStaInBatchRGResp;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

public class PCWriteCardTools {
	private static Logger logger = Logger.getLogger(PCWriteCardTools.class);
	public final static String CARD_QUEUE_FAIL_0 = "0";	//开户失败
	public final static String CARD_QUEUE_FAIL_1 = "1";	//写卡失败
	public final static String CARD_QUEUE_FAIL_2 = "2";	//开户成功
	public final static String CARD_QUEUE_FAIL_3 = "3";	//写卡成功
	private final static String QUEUE_TABLE_NAME = "ES_QUEUE_WRITE_CARD";
	private final static String QUEUE_TABLE_NAME_HIS = "ES_QUEUE_WRITE_CARD_HIS";
	private final static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static String QUEUE_CACHE_LOCK = "CACHE_LOCK";
	
//	private final static String NEED_WRITE_CARD_SELECT = "select queue_code, order_id, source_from from (select q.queue_code"
//			+ " , q.order_id, q.source_from from ES_QUEUE_MANAGER c,es_queue_card_mate_manager d,"
//			+ " es_queue_write_card q where d.card_mate_code = ? and d.queue_code = c.queue_no"
//			+ " and c.queue_switch = '0' and q.queue_code = d.queue_code and q.open_account_status = '2'"
//			+ " and nvl(q.write_card_status, '0') = '0' and c.source_from = d.source_from"
//			+ " and d.source_from = ? and nvl(q.queue_status,'0') = '0'"
//			+ " order by q.open_account_time) where source_from = ? and rownum < 2";
	
	static INetCache cache;
	private static int NAMESPACE = 500;
	private static int addTime = 300;
	
	static{
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	/**
	 * 批量获取写卡机状态
	 * @param param
	 * @return
	 */
	public static List<MachineNoRGResp> getMachineNoByMachineList(List<MachineNoRG> param){
		List<MachineNoRGResp> machinesList = new ArrayList<MachineNoRGResp>();
		try{
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			QueWriMachStaInBatchRGRequset req = new QueWriMachStaInBatchRGRequset();
			QueWriMachStaInBatchDataRG data = new QueWriMachStaInBatchDataRG();
			QueWriMachStaInBatchDataRGBodyReq bodyReq = new QueWriMachStaInBatchDataRGBodyReq();
			bodyReq.setParam(param);
			QueWriMachStaInBatchDataRGHeadReq head = new QueWriMachStaInBatchDataRGHeadReq();
			data.setBody(bodyReq);
			data.setHead(head);
			req.setNotNeedReqStrData(data);
			QueWriMachStaInBatchRGResponse resp = client.execute(req, QueWriMachStaInBatchRGResponse.class);
			if(null != resp && EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){
				QueWriMachStaInBatchRGResp sr_response = resp.getSr_response();
				if(null != sr_response){
					QueWriMachStaInBatchRGBody body = sr_response.getBody();
					if(null != body){
						machinesList = body.getParam();
					}
				}
			}else{
				logger.info("log===============批量获取写卡机状态失败");
			}
		}catch(Exception e){
			logger.info("log===============批量获取写卡机状态失败" + e.getMessage());
			e.printStackTrace();
		}
		return machinesList;
	}
	
	/**
	 * 修改写卡机状态
	 * @param card_mate_status
	 * @param queueCode
	 * @param card_mate_code
	 */
	public static void modifyMachineStatus(String card_mate_status, String queueCode, String card_mate_code){
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			support.execute("update es_queue_card_mate_manager c set c.card_mate_status = ? where c.queue_code = ? and c.card_mate_code = ? and c.source_from = ?", card_mate_status,queueCode,card_mate_code,ManagerUtils.getSourceFrom());
		}catch(Exception e){
			logger.info("log===============修改写卡机状态失败" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新写卡机组可用写卡机数量
	 * @param queue_code
	 * @param nums
	 */
	public static void modifyQueueMacNums(String queue_code,int nums){
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			support.execute("update ES_QUEUE_MANAGER c set c.AVAIL_CARD_MAC_NUM = ? where c.queue_no = ? and c.source_from = ?", nums,queue_code,ManagerUtils.getSourceFrom());
		}catch(Exception e){
			logger.info("log===============修改ES_QUEUE_MANAGER表可用写卡机数量失败" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新队列维护表的开关
	 * @param queue_code
	 * @param switch_value 0:开、1:关
	 */
	public static void modifyQueueSwitch(String queue_code, String deal_reason, String switch_value){
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			support.execute("update ES_QUEUE_MANAGER c set c.QUEUE_SWITCH = ?,DEAL_REASON = ? where c.queue_no = ? and c.source_from = ?", switch_value,deal_reason,queue_code,ManagerUtils.getSourceFrom());
		}catch(Exception e){
			logger.info("log===============修改ES_QUEUE_MANAGER表开关失败" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 开户失败、写卡失败记数公共方法
	 * @param queue_code
	 * @param fail_type 0:开户失败、1:写卡失败,2:开户成功,3:写卡成功
	 */
	public static void queueFailNumsAdd(String queue_code, String fail_type){
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			if(StringUtils.equals("0", fail_type)){
				support.execute("update ES_QUEUE_MANAGER c set CONTI_OPEN_FAILED_NUM = CONTI_OPEN_FAILED_NUM + 1 where c.queue_no = ? and c.source_from = ?", queue_code,ManagerUtils.getSourceFrom());
			}else if(StringUtils.equals("1", fail_type)){
				support.execute("update ES_QUEUE_MANAGER c set CONTI_CARD_FAILED_NUM = CONTI_CARD_FAILED_NUM + 1 where c.queue_no = ? and c.source_from = ?", queue_code,ManagerUtils.getSourceFrom());
			}else if(StringUtils.equals("2", fail_type)){
				support.execute("update ES_QUEUE_MANAGER c set CONTI_OPEN_FAILED_NUM = 0 where c.queue_no = ? and c.source_from = ?", queue_code,ManagerUtils.getSourceFrom());
			}else if(StringUtils.equals("3", fail_type)){
				support.execute("update ES_QUEUE_MANAGER c set CONTI_CARD_FAILED_NUM = 0 where c.queue_no = ? and c.source_from = ?", queue_code,ManagerUtils.getSourceFrom());
			}
		}catch(Exception e){
			logger.info("log===============修改ES_QUEUE_MANAGER表fail_type["+fail_type+"]连续失败数据失败" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改订单队列状态,开户写卡失败后标记为1
	 * 0:正常、1:等待回退
	 * @param order_id
	 * @param queue_status
	 */
	public static void modifyOrderQueueStatus(String order_id, String queue_status){
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			support.execute("update es_queue_write_card c set c.queue_status = ? where c.order_id = ? and c.source_from = ?", queue_status,order_id,ManagerUtils.getSourceFrom());
		}catch(Exception e){
			logger.info("标记订单队列状态失败["+order_id+"],["+queue_status+"]");
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改订单开户写卡状态
	 * @param order_id
	 * @param type 0:开户,1:写卡
	 * @param status
	 */
	public static void modifyOrderStatus(String order_id, String type, String status){
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(type)){
				support.execute("update es_queue_write_card c set c.open_account_status = ? where c.order_id = ? and c.source_from = ?", status,order_id,ManagerUtils.getSourceFrom());
			}else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(type)){
				support.execute("update es_queue_write_card c set c.write_card_status = ? where c.order_id = ? and c.source_from = ?", status,order_id,ManagerUtils.getSourceFrom());
			}
		}catch(Exception e){
			logger.info("修改订单开户写卡状态失败["+order_id+"],["+type+"],["+status+"]");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取表字段列表
	 * @param table_name
	 * @return
	 */
	public static String queryTableColumns(String table_name){
		StringBuffer columnsBuff = new StringBuffer();
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			List<Map<String,String>> colList = (List<Map<String,String>>)support.queryForList("select column_name from USER_COL_COMMENTS where table_name = ?",table_name.toUpperCase());
			if(null != colList && colList.size() >= 0){
				for(int i = 0; i < colList.size(); i++){
					if(i != colList.size() - 1){
						columnsBuff.append(colList.get(i).get("column_name")).append(",");
					}else{
						columnsBuff.append(colList.get(i).get("column_name"));
					}
				}
			}
		}catch(Exception e){
			logger.info("获取表结构失败["+table_name+"]");
			e.printStackTrace();
		}
		return columnsBuff.toString();
	}
	
	/**
	 * 归档写卡成功的队列数据
	 * @param order_id
	 */
	public static void archiveWriteCardData(String order_id){
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			String columns = queryTableColumns(QUEUE_TABLE_NAME);
			if(StringUtils.isNotEmpty(columns)){
				//备份
				String insert_sql = "insert into " + QUEUE_TABLE_NAME_HIS + " ("+columns+") select " + columns + " from " + QUEUE_TABLE_NAME + " where order_id = ? and source_from = ?";
				support.execute(insert_sql, order_id,ManagerUtils.getSourceFrom());
				//删除
				String delete_sql = "delete from " + QUEUE_TABLE_NAME + " where order_id = ? and source_from = ?";
				support.execute(delete_sql, order_id,ManagerUtils.getSourceFrom());
				
				OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
				logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_06);
				logReq.setAction_desc("归档写卡成功的队列数据");
				logReq.setOrder_id(order_id);
				saveQueueCardActionLog(logReq);
			}
		}catch(Exception e){
			logger.info("归档写卡成功的队列数据["+order_id+"]失败");
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 获取写卡机队列待写卡订单数据
//	 * @param machineNo
//	 * @return
//	 */
//	public static List queryWaitWriteCardOrders(String machineNo){
//		try{
//			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
//			return support.queryForList(NEED_WRITE_CARD_SELECT, machineNo,ManagerUtils.getSourceFrom(),ManagerUtils.getSourceFrom());
//		}catch(Exception e){
//			logger.info("获取写卡机["+machineNo+"]队列待写卡订单数据");
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/**
	 * 获取写卡机队列待开户订单数据
	 * @param machineNo
	 * @return
	 */
	public static List queryWaitReceiveICCIDOrders(String machineNo,String order_id){
		try{
			//爬虫自动写卡获取ICCID是在开户之前，其他模式的订单获取ICCID是在开户完成之后
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			String sql ="select queue_code, order_id, source_from from (select q.queue_code"
					+ " , q.order_id, q.source_from from ES_QUEUE_MANAGER c,es_queue_card_mate_manager d,"
					+ " es_queue_write_card q where d.card_mate_code = ? and d.queue_code = c.queue_no"
					+ " and c.queue_switch = '0' and q.queue_code = d.queue_code and q.open_account_status = '2'"
					+ " and nvl(q.write_card_status, '0') = '0' and c.source_from = d.source_from"
					+ " and d.source_from = ? and nvl(q.queue_status,'0') = '0'"
					+ " order by q.open_account_time) where source_from = ? and rownum < 2";
			/*StringBuffer sql = new StringBuffer();
			sql.append(" select queue_code, order_id, source_from");
			sql.append(" from (select q.queue_code ,q.order_id, q.source_from");
			sql.append(" from ES_QUEUE_MANAGER           c,es_queue_card_mate_manager d,es_queue_write_card        q,es_order_ext e");
			sql.append(" where q.order_id = e.order_id");
			sql.append(" and d.queue_code = c.queue_no");
			sql.append(" and q.queue_code = d.queue_code");
			sql.append(" and c.source_from = d.source_from");
			sql.append(" and d.source_from = ?");
			sql.append(" and c.queue_switch = '0'");
			sql.append(" and ((e.order_model '"+EcsOrderConsts.ORDER_MODEL_08+"' and q.open_account_status = '2') ");
			sql.append(" or (e.order_model = '"+EcsOrderConsts.ORDER_MODEL_08+"' and q.open_account_status = '0'))");
			sql.append(" and nvl(q.write_card_status, '0') = '0'");
			sql.append(" and nvl(q.queue_status, '0') = '0'");
			sql.append(" and d.card_mate_code = ?");
			if(StringUtils.isNotBlank(order_id)){
				sql.append(" and q.order_id = "+order_id);
			}
			sql.append(" order by q.create_time)");
			sql.append(" where rownum < 2;");*/


			
			
			//return support.queryForList(sql.toString(),ManagerUtils.getSourceFrom(),machineNo);
			return support.queryForList(sql.toString(),machineNo,ManagerUtils.getSourceFrom(),ManagerUtils.getSourceFrom());
		}catch(Exception e){
			logger.info("获取写卡机["+machineNo+"]队列待开户订单数据");
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 队列订单写卡
	 * @param iccid
	 * @param machineNo
	 */
	public static RceiveICCIDResp queueWriteCard(String iccid , String machineNo,String orderId){
		RceiveICCIDResp resp = new RceiveICCIDResp();
		resp.setResp_code("0");
		resp.setResp_msg("推送成功");
		try{
			List writeList = queryWaitReceiveICCIDOrders(machineNo,orderId);
			if(null != writeList && writeList.size() > 0){
				logger.info("PC批量写卡iccid:" + iccid + ",machineNo:" + machineNo);
				Map<String,String> mm = (Map<String,String>)writeList.get(0);
				mm.put("ICCID", iccid);
				mm.put("machineNo", machineNo);
				ExecutorService service = Executors.newFixedThreadPool(1);
				String order_id = mm.get("order_id");
				
				//设置缓存锁
				String flag_lock = String.valueOf((int)(Math.random()*999999)) + String.valueOf(System.currentTimeMillis());
				String lock_value = order_id + flag_lock;
				boolean flag = addCacheLock(QUEUE_CACHE_LOCK + order_id,lock_value);
				if(!flag){ //已有缓存锁，有进程正在处理料箱队列
					logger.info("log==queueWriteCardqueueWriteCard订单["+order_id+"]已锁,退出消费写卡!");
					resp.setResp_code("-1");
					resp.setResp_msg("推送失败，订单["+order_id+"]正在写卡");
					return resp;
				}else{
					logger.info("log==queueWriteCardqueueWriteCard订单["+order_id+"]加锁" + lock_value);
				}
				try {
					String tmp = getCacheLock(QUEUE_CACHE_LOCK + order_id);
					if (tmp != null && !"".equals(tmp)) {
						if (!(lock_value).equals(tmp)) { // 如果不是当前线程加的锁，则退出
							logger.info("log==queueWriteCardqueueWriteCard订单["+order_id+"]不是当前线程锁定,退出消费写卡["+tmp+"]!");
							resp.setResp_code("-1");
							resp.setResp_msg("推送失败，订单["+order_id+"]正在写卡");
							return resp;
						}
					} else {
						Thread.sleep(2000l); // 休眠2秒后再取出锁进行比较
						tmp = getCacheLock(QUEUE_CACHE_LOCK + order_id);
						if (!(lock_value).equals(tmp)) { // 如果不是当前线程加的锁，则退出
							logger.info("log==queueWriteCardqueueWriteCard订单["+order_id+"]不是当前线程锁定,退出消费写卡["+tmp+"]!");
							resp.setResp_code("-1");
							resp.setResp_msg("推送失败，订单["+order_id+"]正在写卡");
							return resp;
						}
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try{
					final Map<String,String> m = mm;
					Runnable run = new Runnable() {
						@Override
						public void run() {
							String order_id = m.get("order_id");
							String queue_code = m.get("queue_code");
							String ICCID = m.get("ICCID");
							String machineNo = m.get("machineNo");
							if(StringUtils.isEmpty(order_id) || StringUtils.isEmpty(ICCID)){
								logger.info("PC批量写卡时order_id["+order_id+"]为空或iccid["+ICCID+"]为空.");
								return;
							}
							OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
							OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
							//保存ICCID
					        CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ICCID}, new String[]{ICCID});
					        String orderModel = orderExt.getOrder_model();
					        //爬虫自动生成模式特殊处理
					        if(StringUtils.equals(orderModel, EcsOrderConsts.ORDER_MODEL_08)&& StringUtils.equals(orderExt.getFlow_trace_id(), EcsOrderConsts.DIC_ORDER_NODE_D)){
					        	crawlerQueueWriteCard(orderTree, machineNo, ICCID,queue_code);
					        }else if(StringUtils.equals(orderModel, EcsOrderConsts.OPER_MODE_XK) 
									&& StringUtils.equals(orderExt.getFlow_trace_id(), EcsOrderConsts.DIC_ORDER_NODE_X)
									&& (StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP) 
											|| StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS)) ){
					        	queueWriteCard(orderTree, machineNo, ICCID,queue_code);
					        }else if(StringUtils.equals(orderModel, EcsOrderConsts.OPER_MODE_XK)&&StringUtils.equals(orderExt.getFlow_trace_id(), EcsOrderConsts.DIC_ORDER_NODE_D)
					        		&&StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS)){
					        	queueWriteCard(orderTree, machineNo, ICCID,queue_code);
					        }else{
					        	logger.info("订单["+order_id+"]数据不正确["+orderExt.getOrder_model()+","+orderExt.getFlow_trace_id()+","+orderExt.getIs_aop()+"]");
					        }
						}							
					};
					service.execute(run);
				}catch(Exception e){
					logger.info("队列订单写卡失败" + e.getMessage());
					e.printStackTrace();
					resp.setResp_code("-1");
					resp.setResp_msg("推送失败，" + e.getMessage());
					return resp;
				}
				service.shutdown();
			}
		}catch(Exception e){
			logger.info("队列订单写卡失败" + e.getMessage());
			e.printStackTrace();
			resp.setResp_code("-1");
			resp.setResp_msg("推送失败，" + e.getMessage());
		}
		return resp;
	}
	public static void queueWriteCard(OrderTreeBusiRequest orderTree,String machineNo,String ICCID,String queue_code){
		String order_id = orderTree.getOrder_id();
		OrderExtBusiRequest orderExt =orderTree.getOrderExtBusiRequest();
        //保存写卡机编码 start
        List<OrderItemProdBusiRequest> prods = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
        for(OrderItemProdBusiRequest prod:prods){
			OrderItemProdExtBusiRequest prodExt = prod.getOrderItemProdExtBusiRequest();
			prodExt.setReadId(machineNo);
			prodExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			prodExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			prodExt.store();
		}
        String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_write_card)){		
			//已接受写卡机编码
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.READID},new String[]{machineNo});
		}
		//记录写卡状态
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.WRITE_CARD_STATUS}, 
				new String[]{EcsOrderConsts.WRITE_CARD_STATUS_1});
		//保存写卡机编码 end
    	
		//标记为写卡中
		PCWriteCardTools.modifyOrderStatus(order_id, EcsOrderConsts.IS_DEFAULT_VALUE, EcsOrderConsts.QUEUE_ORDER_WRITE_1);
		
		//记录正在写卡的订单
		modifyCurrentOrderId(order_id, queue_code, machineNo);
		
    	//记录日志
		OrderQueueCardActionLogReq logReq1 = new OrderQueueCardActionLogReq();
		logReq1.setAction_code(EcsOrderConsts.QUEUE_ACTION_08);
		logReq1.setAction_desc("队列订单准备写卡");
		logReq1.setOrder_id(order_id);
		logReq1.setQueue_code(queue_code);
		logReq1.setIccid(ICCID);
		logReq1.setMachine_no(machineNo);
		saveQueueCardActionLog(logReq1);
		
		String ruleid = "";
		if(StringUtils.equals(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP, orderExt.getIs_aop())){
			ruleid = EcsOrderConsts.ORDER_MODEL_06_ICCID_RULE_ID_AOP_ZJ;//接收ICCID规则编码
		}else if(StringUtils.equals(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS, orderExt.getIs_aop())){
			ruleid = EcsOrderConsts.ORDER_MODEL_06_ICCID_RULE_ID_BSSKL;
		}
		RuleTreeExeReq reqNext = new RuleTreeExeReq();
		reqNext.setRule_id(ruleid);
		TacheFact factNext = new TacheFact();
		factNext.setOrder_id(order_id);
		reqNext.setFact(factNext);
		reqNext.setCheckAllRelyOnRule(true);
		reqNext.setCheckCurrRelyOnRule(true);
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeResp rsp = client.execute(reqNext, RuleTreeExeResp.class);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
		if(!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
			logger.info("订单["+order_id+"]执行写卡规则失败");
			//写卡失败次数计算
			PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_1);
			//标记为等待回退  标记异常时统一回退
			PCWriteCardTools.modifyOrderQueueStatus(order_id, EcsOrderConsts.IS_DEFAULT_VALUE);
		}else{
			logger.info("orderorderorder["+order_id+"],error_code["+busiResp.getError_code()+"],error_msg["+busiResp.getError_msg()+"]");
			//写卡成功清空连续失败次数
			PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_3);
			//更新写卡时间
			modifyOpenOrWriteDate(order_id, EcsOrderConsts.IS_DEFAULT_VALUE, dateformat.format(new Date()));
			OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
			logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_03);
			logReq.setAction_desc("队列订单写卡完成");
			logReq.setOrder_id(order_id);
			logReq.setQueue_code(queue_code);
			logReq.setIccid(ICCID);
			logReq.setMachine_no(machineNo);
			saveQueueCardActionLog(logReq);
			//写卡成功踢出队列
			archiveWriteCardData(order_id);
		}

		//写卡完成后清空订单ID
		modifyCurrentOrderId("", queue_code, machineNo);
    
	}
	public static void crawlerQueueWriteCard(OrderTreeBusiRequest orderTree,String machineNo,String ICCID,String queue_code){
		String order_id = orderTree.getOrder_id();
		OrderExtBusiRequest orderExt =orderTree.getOrderExtBusiRequest();
        //保存写卡机编码 start
        List<OrderItemProdBusiRequest> prods = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
        for(OrderItemProdBusiRequest prod:prods){
			OrderItemProdExtBusiRequest prodExt = prod.getOrderItemProdExtBusiRequest();
			prodExt.setReadId(machineNo);
			prodExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			prodExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			prodExt.store();
		}
        String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_write_card)){		
			//已接受写卡机编码
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.READID},new String[]{machineNo});
		}
		String ruleid = "";
		if(StringUtils.equals(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP, orderExt.getIs_aop())){
			ruleid = ZjEcsOrderConsts.ORDER_MODEL_08_ICCID_RULE_ID_AOP;//获取ICCID规则
		}
		//开户中状态
		PCWriteCardTools.modifyOrderStatus(order_id, EcsOrderConsts.NO_DEFAULT_VALUE, EcsOrderConsts.QUEUE_ORDER_OPEN_1);
		//开户
		RuleTreeExeReq reqNext = new RuleTreeExeReq();
		reqNext.setRule_id(ruleid);
		TacheFact factNext = new TacheFact();
		factNext.setOrder_id(order_id);
		reqNext.setFact(factNext);
		reqNext.setCheckAllRelyOnRule(true);
		reqNext.setCheckCurrRelyOnRule(true);
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		RuleTreeExeResp rsp = client.execute(reqNext, RuleTreeExeResp.class);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
		
//		if(!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
//			logger.info("订单["+order_id+"]执行写卡规则失败");
//			//写卡失败次数计算
//			PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_1);
//			//标记为等待回退  标记异常时统一回退
//			//PCWriteCardTools.modifyOrderQueueStatus(order_id, EcsOrderConsts.IS_DEFAULT_VALUE);
//		}else{
		
			logger.info("orderorderorder["+order_id+"],error_code["+busiResp.getError_code()+"],error_msg["+busiResp.getError_msg()+"]");
			//更新开户时间
			//开户完成
			modifyOpenOrWriteDate(order_id, EcsOrderConsts.NO_DEFAULT_VALUE, dateformat.format(new Date()));
			OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
			logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_03);
			logReq.setAction_desc("队列订单开户完成");
			logReq.setOrder_id(order_id);
			logReq.setQueue_code(queue_code);
			logReq.setIccid(ICCID);
			logReq.setMachine_no(machineNo);
			saveQueueCardActionLog(logReq);
//		}
    
	}
	/**
	 * 更新开户或写卡完成时间及状态
	 * @param order_id
	 * @param oper_type 0:开户,1:写卡
	 * @param times
	 */
	public static void modifyOpenOrWriteDate(String order_id, String oper_type, String str_date){
		try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			if(StringUtils.equals(oper_type, EcsOrderConsts.NO_DEFAULT_VALUE)){
				//开户
				support.execute("update es_queue_write_card c set c.open_account_status = '2', c.open_account_time = to_date(?,'yyyy-mm-dd hh24:mi:ss') where c.order_id = ? and c.source_from = ?", str_date,order_id,ManagerUtils.getSourceFrom());
			}else if(StringUtils.equals(oper_type, EcsOrderConsts.IS_DEFAULT_VALUE)){
				//写卡
				support.execute("update es_queue_write_card c set c.write_card_status = '2',c.write_card_time = to_date(?,'yyyy-mm-dd hh24:mi:ss') where c.order_id = ? and c.source_from = ?", str_date,order_id,ManagerUtils.getSourceFrom());
			}
		}catch(Exception e){
			logger.info("更新开户或写卡完成时间失败" + order_id);
			e.printStackTrace();
		}
	}
	
	/**
	 * 记录PC批量写卡日志信息
	 * @param logReq
	 */
	public static void saveQueueCardActionLog(OrderQueueCardActionLogReq logReq){
		try{
			logReq.setSource_from(ManagerUtils.getSourceFrom());			
			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(logReq) {
				public ZteResponse execute(ZteRequest zteRequest) {
					IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
					OrderQueueCardActionLogReq req = (OrderQueueCardActionLogReq)zteRequest;
					support.execute("insert into es_queue_card_action_log (ACTION_CODE, ACTION_DESC, ACTION_TIME, ORDER_ID, QUEUE_CODE, ICCID, MACHINE_NO, BATCH_ID, SOURCE_FROM) values (?, ?, sysdate, ?, ?, ?, ?, to_char(sysdate,'yyyymmddhh24'), '"+ManagerUtils.getSourceFrom()+"')", 
							req.getAction_code(),req.getAction_desc(),req.getOrder_id(),req.getQueue_code(),req.getIccid(),req.getMachine_no());
					return null;
				}
			});
			ThreadPoolFactory.getOrderThreadPoolExector().execute(taskThreadPool);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
    /**
     * 修改队列中写卡机正在写卡的订单号
     * @param order_id
     * @param queue_code
     * @param machine_no
     */
    public static void modifyCurrentOrderId(String order_id, String queue_code, String machine_no){
    	try{
			IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
			support.execute("update es_queue_card_mate_manager c set c.curr_order_id = ? where c.queue_code = ? and c.card_mate_code = ? and c.source_from = '"+ManagerUtils.getSourceFrom()+"'", order_id,queue_code,machine_no);
		}catch(Exception e){
			logger.info("更新开户或写卡完成时间失败" + order_id);
			e.printStackTrace();
		}
    }
	
	/**
	 * 获取PC批量写卡缓存锁
	 * @param workStation
	 */
	public static boolean addCacheLock(String key, String value){
		boolean flag = false;
		try{
			flag = cache.add(NAMESPACE,key,value,addTime);
		}catch(Exception e){
			logger.info("log==queueWriteCard缓存订单缓存失败[" + key + ","+value+"]," + e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取写卡机位缓存锁
	 * @param String
	 */
	public static String getCacheLock(String key){
		try{
			return (String)cache.get(NAMESPACE, key);
		}catch(Exception e){
			logger.info("log==queueWriteCard缓存订单缓存失败["+key+"]," + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	public static List  queryWaitOpenOrders(String machineNo){
		return new ArrayList();
	}
	public static RceiveICCIDResp queueWriteCard(String iccid , String machineNo){
		RceiveICCIDResp resp = new RceiveICCIDResp();
		return resp;
	}
}
