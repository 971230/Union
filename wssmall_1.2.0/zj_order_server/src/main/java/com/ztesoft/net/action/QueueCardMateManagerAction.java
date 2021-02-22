package com.ztesoft.net.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.drools.core.util.StringUtils;

import params.order.req.OrderQueueCardActionLogReq;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.utils.PCWriteCardTools;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.QueueCardMateVo;
import com.ztesoft.net.model.QueueManageVO;
import com.ztesoft.net.model.QueueParams;
import com.ztesoft.net.model.QueueWriteCardVO;
import com.ztesoft.net.service.IQueueCardMateManager;
/**
 * 批量写卡机action
 * @作者 shen.qiyu
 * @创建日期 2016-11-18 
 * @版本 V 1.0
 */
public class QueueCardMateManagerAction extends WWAction{
	private final static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Resource
	private IQueueCardMateManager queueCardMateManager;
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	
	public static final String ACTION_TYPE_0 = "0";//添加
	public static final String ACTION_TYPE_1 = "1";//修改
	
	private String queue_code;
	private String card_mate_code;
	private String card_mate_status;
	private List<Map> card_mate_status_list;
	private List<Map> card_mate_queue_list;
	private String action_type;
	private String queue_card_mate_id;
	
	private String queueidArray;
	private String order_id;
	
	private QueueCardMateVo queueCardMateVo;
	private List<QueueManageVO> queueManagelist;	
	private List<QueueWriteCardVO> queueWriteCardlist;	
	private QueueManageVO queueManagevo;
	private QueueWriteCardVO queueWriteCardvo;
	private QueueParams queueParams;
	private List list;
	
	private String first_load;
	private Page cardwriterstatus;
	private String cardmateid;
	private String sucflag;
	private String sta_time;
	private String end_time;
	private Page orderotems;
	private String excle;
	
	private List<Map> exceptionList; // 异常集合(ZX add 2014-10-14)
	public String list(){
		Map params = new HashMap();
		params.put("queue_code", queue_code);
		params.put("card_mate_code", card_mate_code);
		params.put("card_mate_status", card_mate_status);
		getCardMateStatusList();
		getCardMateQueueList();
		this.webpage = queueCardMateManager.queryQueueCardMateForPage(this.getPage(), this.getPageSize(), params);
		return "queue_card_mate_list";
	}
	
	private void getCardMateQueueList(){
		card_mate_queue_list = getConsts(StypeConsts.DIC_CARD_MATE_QUEUE);
		if(card_mate_queue_list==null){
			card_mate_queue_list = new ArrayList<Map>();
		}
	}
	
	private void getCardMateStatusList(){
		card_mate_status_list = getConsts(StypeConsts.DIC_CARD_MATE_STATUS);
		if(card_mate_status_list==null){
			card_mate_status_list = new ArrayList<Map>();
		}
	}
	
	private List<Map> getConsts(String key){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(key);
        return list;
	}

	public String toQueueCardMate(){
		getCardMateQueueList();
		if(ACTION_TYPE_1.equals(action_type)){
			queueCardMateVo = queueCardMateManager.queryQueueCardMate(queue_card_mate_id);
		}else{
			queueCardMateVo = new QueueCardMateVo();
		}
		return "queue_card_mate_update";
	}
	
	public String updateQueueCardMate(){
		try{
			boolean flag = queueCardMateManager.checkCardMateCode(queueCardMateVo.getCard_mate_code(),queue_card_mate_id);
			if(flag){
				this.json = "{result:1,message:'写卡机编码已存在，请重新输入！'}";
			}else{
				if(ACTION_TYPE_1.equals(action_type)){
					queueCardMateManager.modifyQueueCardMate(queue_card_mate_id,queueCardMateVo);
					this.json = "{result:0,message:'修改成功！'}";
				}else{
					queueCardMateManager.saveQueueCardMate(queueCardMateVo);
					this.json = "{result:0,message:'保存成功！'}";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		return this.JSON_MESSAGE;
	}

	public String deleteQueueCardMate(){
		try{
			queueCardMateManager.deleteQueueCardMate(queue_card_mate_id);
			this.json = "{result:0,message:'删除成功！'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		return this.JSON_MESSAGE;
	}
	
	//队列查询
	public String toQueueManageList(){
		this.webpage = queueCardMateManager.getQueueManageList(this.getPage(), this.getPageSize(), queueParams);
		return "queue_manage_list";
	}	
	//集中写卡查询
	public String toQueueOrderList(){
		this.webpage = queueCardMateManager.getQueueOrderList(this.getPage(), this.getPageSize(), queueParams);
		return "queue_order_list";
	}
	
	/**
	 * 打开or关闭队列
	 * @return
	 */
	public String openOrCloseQueue(){
		try{
			String queue_switch = queueManagevo.getQueue_switch();
			String deal_reason = queueManagevo.getDeal_reason();
			String queue_id = queueManagevo.getId();
			List<String> updateParams = new ArrayList<String>();
			updateParams.add(queue_switch);
			updateParams.add(deal_reason);
			boolean flag = queueCardMateManager.updateQueueManageCon(updateParams, queue_id);
			if(flag){
				OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
				if(StringUtil.equals(queue_switch, EcsOrderConsts.QUEUE_STATUS_OPENED)){//开启
					deal_reason = "队列开启";
					logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_00);					
				}else{
					logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_99);	
				}
				logReq.setAction_desc(deal_reason);
				logReq.setAction_time(dateformat.format(new Date()));
				logReq.setQueue_code(queue_id);
				PCWriteCardTools.saveQueueCardActionLog(logReq);
				this.json = "{\"result\": 1 ,\"message\":\"操作成功\" }";				
			}else{
				this.json = "{\"result\":0,\"message\":\"操作失败\"}";				
			}
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{\"result\":0,\"message\":\"操作失败\"}";
		}
		return JSON_MESSAGE;
	}
	/**
	 * 打开异常标记对话框
	 * @return
	 */
	public String tobackQueueOrder(){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		exceptionList = dcPublicCache.getList("DIC_ORDER_EXCEPTION_TYPE");
		return "queue_exception";
	}
	/**
	 * 打开关闭原因填写对话框
	 * @return
	 */
	public String toQueueSwitch(){
		return "queue_switch";
	}
	
	//队列回退
	public String rebackQueueManage(){
		try{
			String queue_no = queueManagevo.getQueue_no();
			List<String> updateParams = new ArrayList<String>();
			updateParams.add("1");
			boolean flag = queueCardMateManager.rebackQueueManage(updateParams, queue_no);
			if(flag){
				OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
				logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_09);	
				logReq.setAction_desc("队列回退");
				logReq.setAction_time(dateformat.format(new Date()));
				logReq.setQueue_code(queue_no);
				PCWriteCardTools.saveQueueCardActionLog(logReq);
				this.json = "{\"result\": 1 ,\"message\":\"回退成功\" }";				
			}else{
				this.json = "{\"result\":0,\"message\":\"回退失败\"}";				
			}
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{\"result\":0,\"message\":\"回退失败\"}";
		}
		return JSON_MESSAGE;
	}
	
	//队列订单回退
	public String rebackQueueOrder(){
		try{
			String order_id = queueWriteCardvo.getOrder_id();
			List<String> updateParams = new ArrayList<String>();
			updateParams.add("1");
			updateParams.add(queueWriteCardvo.getException_type());
			updateParams.add(queueWriteCardvo.getException_msg());
			AdminUser user = ManagerUtils.getAdminUser();
			updateParams.add(user.getUserid());
			updateParams.add(user.getUsername());
			boolean flag = queueCardMateManager.rebackQueueOrder(updateParams, order_id);
			if(flag){
				OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
				logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_05);	
				logReq.setAction_desc("队列回退");
				logReq.setAction_time(dateformat.format(new Date()));
				logReq.setOrder_id(order_id);
				PCWriteCardTools.saveQueueCardActionLog(logReq);
				this.json = "{\"result\": 1 ,\"message\":\"回退成功\" }";				
			}else{
				this.json = "{\"result\":0,\"message\":\"回退失败\"}";				
			}
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{\"result\":0,\"message\":\"回退失败\"}";
		}
		return JSON_MESSAGE;
	}
	//新增or编辑队列信息
	public String addOrUpdateQueueManage(){
		try{
			String dealReason = "";
			boolean flag = false;
			if(StringUtils.isEmpty(queueManagevo.getId())){
				flag = queueCardMateManager.saveQueueManage(queueManagevo);		
				dealReason = "队列修改";
			}else{
				flag = queueCardMateManager.updateQueueManageVO(queueManagevo);
				dealReason = "队列新增";
			}
			if(flag){
				OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
				logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_10);	
				logReq.setAction_desc(dealReason);
				logReq.setAction_time(dateformat.format(new Date()));
				logReq.setQueue_code(queueManagevo.getQueue_no());
				PCWriteCardTools.saveQueueCardActionLog(logReq);
				this.json = "{\"result\": 1 ,\"message\":\"保存成功\" }";				
			}else{
				this.json = "{\"result\":0,\"message\":\"保存失败,编码可能重复,请检查\"}";				
			}
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{\"result\":0,\"message\":\"保存失败\"}";
		}
		return JSON_MESSAGE;
	}
	
	//获取队列明细
	public String getQueueManageInfo(){
		try{
			if(StringUtils.isEmpty(queueManagevo.getId())){
				this.setQueueManagevo(new QueueManageVO());
			}else{
				this.setQueueManagevo(queueCardMateManager.getQueueManageById(queueManagevo.getId()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "oneQueueInfo";	
	}
	
	/**
	 * 跳转队列订单写卡开户状态页面
	 * @return
	 */
	public String toQueueOrderIniStatusList(){
		
		return "queueOrderAllStatus";	
	}
	
	/**
	 * 查询队列-写卡状态-开户状态
	 * @return
	 */
	public String getAllStatus(){		
		//获取队列
		QueueParams param = new QueueParams();
		List<QueueManageVO> queueList = queueCardMateManager.getQueueManage(param);
		list = new ArrayList();
		if(null!=queueList){
			Map map;
			for(QueueManageVO vo : queueList){
				map = new HashMap();
				map.put("queueVo", vo);

				param.setQueue_no(vo.getQueue_no());
				
				//查询队列的写卡机状态
				List<QueueCardMateVo> queueCardList = queueCardMateManager.queryQueueCardMate(param);
				map.put("queueWriteCardList", queueCardList);
				int queue_write_card_count = queueCardList.size();
				map.put("queue_write_card_num", queue_write_card_count);	
				
				//查询队列待写卡订单
				List<String> listWriteCard = queueCardMateManager.getQueueOrderWaitWriteCardList(param);
				map.put("boxesWriteCardList", listWriteCard);
				int box_write_card_count = listWriteCard.size();
				map.put("waiting_write_card_num", box_write_card_count);				

				//查询队列带开户订单
				List<String> listOpenAcc = queueCardMateManager.getQueueOrderWaitOpenAccList(param);
				map.put("boxesOpenAccList", listOpenAcc);
				int box_open_acc_count = listOpenAcc.size();
				map.put("waiting_open_num", box_open_acc_count);
				
				//写卡机状态
//				MachinesGroup group = AutoWriteCardCacheTools.getMachinesGroupByWorkStation(workStation);
//				if(null!=group){
//					List<MachinesModel> machines = group.getMachines();
//					List showMachines = new ArrayList();
//					for(MachinesModel machine:machines){
//						Map machineMap = new HashMap();
//						machineMap.put("key", machine.getKey());
//						machineMap.put("value", machine.getValue());
//						MachinesModel model = AutoWriteCardCacheTools.getMachineStatus(workStation, machine.getKey());
//						if(null!=model){
//							machineMap.put("order_id", model.getValue());
//						}
//						showMachines.add(machineMap);
//					}
//					map.put("machines", showMachines);
//				}
				list.add(map);
			}
		}
		
		return "queueWriteCardStatus";
	}
	
	
	public String showStatus(){
		if(!StringUtils.isEmpty(first_load)){
			cardwriterstatus = new Page();
			first_load="";
			String sysdate = "";
			try {
				sysdate = DateUtil.getTime3();
			} catch (FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			QueueParams param = new QueueParams();
			param.setStart_time(sysdate);
			param.setEnd_time(sysdate);
			queueParams=param;
			return "cardwriterstatus";
		}
		queueCardMateManager.updateCardQueueStatus();
		cardwriterstatus = queueCardMateManager.queryQueueCardMateOrderNum(queueParams,this.getPage(),this.getPageSize());
		first_load="";
		return "cardwriterstatus";
	}
	
	public String showOrderItemsByStatus(){
		
		if(!StringUtils.isEmpty(excel)){
			String[] title=new String[]{};
			
			
			String[] content = new String[]{};
			
			String fileTitle =null;
				title=new String[]{"订单号","订单时间","商品名称","订单来源","订单状态","订单环节","号码","姓名","证件号","联系人","联系电话","写卡结果","失败原因"};
				content=new String[]{"out_tid","tid_time","goodsName","order_from_n","order_status","flow_trace_name","phone_num","phone_owner_name","cert_card_num","ship_name","ship_mobile","card_status","exception_desc"};
				fileTitle = "写卡机订单明细";
			
			List list = queueCardMateManager.queryOrderItemsListByStatus(cardmateid,sucflag,sta_time,end_time);
			/*String path = "/shop/admin/";
			String type = "xls";
			queueCardMateManager.export(list, fileTitle, title, content, ServletActionContext.getResponse(),path,type);*/
			excel= "";
			DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
		}else{
			excel="";
			webpage = queueCardMateManager.queryOrderItemsByStatus(cardmateid,sucflag,sta_time,end_time,this.getPage(),this.getPageSize());
			return "orderitemsbystatus";
		}
		
	}
	

	public String getQueue_code() {
		return queue_code;
	}

	public void setQueue_code(String queue_code) {
		this.queue_code = queue_code;
	}

	public String getCard_mate_code() {
		return card_mate_code;
	}

	public void setCard_mate_code(String card_mate_code) {
		this.card_mate_code = card_mate_code;
	}

	public String getCard_mate_status() {
		return card_mate_status;
	}

	public void setCard_mate_status(String card_mate_status) {
		this.card_mate_status = card_mate_status;
	}

	public List<Map> getCard_mate_status_list() {
		return card_mate_status_list;
	}

	public void setCard_mate_status_list(List<Map> card_mate_status_list) {
		this.card_mate_status_list = card_mate_status_list;
	}

	public List<Map> getCard_mate_queue_list() {
		return card_mate_queue_list;
	}

	public void setCard_mate_queue_list(List<Map> card_mate_queue_list) {
		this.card_mate_queue_list = card_mate_queue_list;
	}

	public QueueCardMateVo getQueueCardMateVo() {
		return queueCardMateVo;
	}

	public void setQueueCardMateVo(QueueCardMateVo queueCardMateVo) {
		this.queueCardMateVo = queueCardMateVo;
	}

	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public String getQueue_card_mate_id() {
		return queue_card_mate_id;
	}

	public void setQueue_card_mate_id(String queue_card_mate_id) {
		this.queue_card_mate_id = queue_card_mate_id;
	}

	public String getQueueidArray() {
		return queueidArray;
	}

	public void setQueueidArray(String queueidArray) {
		this.queueidArray = queueidArray;
	}
	


	public List<QueueManageVO> getQueueManagelist() {
		return queueManagelist;
	}

	public void setQueueManagelist(List<QueueManageVO> queueManagelist) {
		this.queueManagelist = queueManagelist;
	}

	public QueueManageVO getQueueManagevo() {
		return queueManagevo;
	}

	public void setQueueManagevo(QueueManageVO queueManagevo) {
		this.queueManagevo = queueManagevo;
	}

	public List<QueueWriteCardVO> getQueueWriteCardlist() {
		return queueWriteCardlist;
	}

	public void setQueueWriteCardlist(List<QueueWriteCardVO> queueWriteCardlist) {
		this.queueWriteCardlist = queueWriteCardlist;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public QueueWriteCardVO getQueueWriteCardvo() {
		return queueWriteCardvo;
	}

	public void setQueueWriteCardvo(QueueWriteCardVO queueWriteCardvo) {
		this.queueWriteCardvo = queueWriteCardvo;
	}

	public List<Map> getExceptionList() {
		return exceptionList;
	}

	public void setExceptionList(List<Map> exceptionList) {
		this.exceptionList = exceptionList;
	}

	public QueueParams getQueueParams() {
		return queueParams;
	}

	public void setQueueParams(QueueParams queueParams) {
		this.queueParams = queueParams;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getFirst_load() {
		return first_load;
	}

	public void setFirst_load(String first_load) {
		this.first_load = first_load;
	}

	public Page getCardwriterstatus() {
		return cardwriterstatus;
	}

	public void setCardwriterstatus(Page cardwriterstatus) {
		this.cardwriterstatus = cardwriterstatus;
	}

	public String getCardmateid() {
		return cardmateid;
	}

	public void setCardmateid(String cardmateid) {
		this.cardmateid = cardmateid;
	}

	public String getSucflag() {
		return sucflag;
	}

	public void setSucflag(String sucflag) {
		this.sucflag = sucflag;
	}

	public String getSta_time() {
		return sta_time;
	}

	public void setSta_time(String sta_time) {
		this.sta_time = sta_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Page getOrderotems() {
		return orderotems;
	}

	public void setOrderotems(Page orderotems) {
		this.orderotems = orderotems;
	}

	public String getExcle() {
		return excle;
	}

	public void setExcle(String excle) {
		this.excle = excle;
	}

	
}
