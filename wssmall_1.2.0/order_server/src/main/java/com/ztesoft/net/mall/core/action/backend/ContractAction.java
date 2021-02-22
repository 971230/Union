package com.ztesoft.net.mall.core.action.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import services.LanInf;

import com.ztesoft.net.app.base.core.model.Lan;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.flow.AcceptPlugin;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.AccNbr;
import com.ztesoft.net.mall.core.model.AccNbrRequest;
import com.ztesoft.net.mall.core.model.Contract;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.ItemCard;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderAudit;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.service.IAccNbrManager;
import com.ztesoft.net.mall.core.service.IContractManager;
import com.ztesoft.net.mall.core.service.IOrderAuditManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 合约机订购，退订，审核
 * 
 * @author wui
 * 
 */
public class ContractAction extends WWAction {

	private Contract contract;
	private OrderAudit orderAudit;
	private String orderId;
	private Order order;
	private IOrderManager orderManager;
	private IOrderUtils orderUtils;
	private IAccNbrManager accNbrManager;
	private IOrderAuditManager orderAuditManager;
	private IOrderNFlowManager orderNFlowManager;
	private AccNbr accNbr;
	private String from_page;
	private String accnbr_ids;
	private AcceptPlugin cloudFlowAcceptPlugin;
	private IOrderDirector orderDirector;
	
	private IContractManager contractManager;
	
	private String countType;//查询状态：1.按分销商统计2.按本地网统计3.按全省统计,
	
	
	private String startTime;
	private String endTime;
	private String lan_id;
	private String state;//受理状态:succ成功，fail失败
	private String show_type;
	private String username;
	private String userid;
	
	private AdminUser adminUser;
	
	private Map countMap;
	
	private String begin_nbr;
	private String end_nbr;
	
	
	private String dealing_count;
	private String succ_count;
	private String show_acc_interval ="no";
	
	private List<Lan> lanList;
    @Resource
    private LanInf lanServ;
	//切面处理订单id
	public void acpOrderId(){
		if(!StringUtil.isEmpty(orderId) && orderId.indexOf(",")>-1)
			orderId = orderId.substring(0, orderId.indexOf(","));
	}
	
	
	/**
	 * 显示合约机受理界面
	 * 
	 * @return
	 */
	public String showContractDialog() {
		order = orderManager.get(orderId);
		
		if(contract ==null)
			contract = new Contract();
		List<OrderItem>  orderItems= orderNFlowManager.listNotAcceptGoodsItem(orderId);
		OrderItem orderItem =orderItems.get(0);
		/*contract.setAcc_nbr(orderItem.getAcc_nbr());
		contract.setCust_name(orderItem.getCust_name());
		contract.setCerti_type(orderItem.getCerti_type());
		contract.setCerti_number(orderItem.getCerti_number());
		contract.setTerminal_code(orderItem.getTerminal_code());
		contract.setTerminal_name(orderItem.getTerminal_name());*/
		
		
		Goods goods = orderUtils.getGoodsId(orderItem.getGoods_id());
		contract.setOffer_name(goods.getName());
		contract.setCrm_offer_id(goods.getCrm_offer_id());//外系统销售品id
		//根据订单回填数据
		return "contract_add";
	}

	
	/**
	 * 显示合约机订购界面
	 * 
	 * @return
	 */
	public String contractAdd() {
		return "contract_add";
	}
	
	/**
	 * 显示合约机申请界面
	 * 
	 * @return
	 */
	public String showContractApplyDialog() {
		acpOrderId();
		order = orderManager.get(orderId);
		return "contract_apply_dialog";
	}
	
	
	/**
	 * 显示合约机受理审核
	 * 
	 * @return
	 */
	public String showContractAuditDialog() {
		acpOrderId();
		order = orderManager.get(orderId);
		orderAudit = orderAuditManager.getLastAuditRecord(orderId,OrderStatus.AUDIT_TYPE_00A);
		return "contract_audit_dialog";
	}
	
	
	
	/**
	 * 显示合约机调拨界面
	 * 
	 * @return
	 */
	public String showContractTransferDialog() {
		acpOrderId();
		order = orderManager.get(orderId); //根据订单获取分销商信息
		if(accNbr ==null)
			accNbr = new AccNbr();
		accNbr.setOrder_id(orderId);
		this.webpage = this.accNbrManager.transfer_list(this.getPage(), this.getPageSize(), 0, accNbr);
		return "accnbr_list";
	}

	
	
	/**
	 * 显示云卡调拨界面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showRandomContractTransferDialog() {
		acpOrderId();
		order = orderManager.get(orderId); // 根据订单获取分销商信息
		
		List<ItemCard> itemCards = orderManager.getItemCardByOrderId(order.getOrder_id());
		
		ItemCard begAccItemCard = orderManager.getItemCardByFieldName(order.getOrder_id(), "begin_nbr", itemCards);
		ItemCard endAccItemCard = orderManager.getItemCardByFieldName(order.getOrder_id(), "end_nbr", itemCards);
		
		begin_nbr = begAccItemCard.getField_value();
		end_nbr = endAccItemCard.getField_value();
		
		
		return "accnbr_random_list";
	}
	
	/**
	 * 分页读取订单列表
	 * 根据订单状态 state 检索，如果未提供状态参数，则检索所有
	 * @return
	 */
	public String list() {
		if(accNbr ==null){
			accNbr = new AccNbr();
		}
		this.adminUser= ManagerUtils.getAdminUser();
			
		acpOrderId();
		accNbr.setOrder_id(orderId);
		if(accNbr.getState() == null){
			accNbr.setState("0");
		}
		this.webpage = this.accNbrManager.transfer_list(this.getPage(), this.getPageSize(), 0, accNbr);
		
		/**
		 * 以下代码是为了导出excel功能所编写
		 * title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性
		 * set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = {"号码","状态","号码等级","预存金","最低消费","归属本地网","用途","调拨时间"};
		String[] content = {"num_code","state_name","acc_level","pre_cash","min_consume","area_name","use_name","deal_time"};
		String fileTitle = "号码数据导出";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("fileTitle", fileTitle);
		
		if(excel != null && !"".equals(excel)){
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}else{
			return "accnbr_list";
		}
		
	}
	
	public String listCount() {
		if (accNbr == null) {
			accNbr = new AccNbr();
			accNbr.setBegin_nbr(begin_nbr);
			accNbr.setEnd_nbr(end_nbr);
			if(!StringUtil.isEmpty(begin_nbr)) //开始号码不为空
				accNbr.setP_apply_show_parent_num("yes");
			accNbr.setQuery_type(Consts.ACCNBR_QUERY_TYPE_00A);
		}
		try {
			Map countMap = this.accNbrManager.listc(accNbr);
			String jsonStr = ManagerUtils.toJsonString(countMap);
			this.json = "{result:1," + jsonStr + "}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"读取失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;
		
	}

	
	
	public String importAccNbr(){
		return "import_accNbr_list";
	}
	
	public String listImport() {
		if(accNbr ==null){
			accNbr = new AccNbr();
		}
		this.adminUser= ManagerUtils.getAdminUser();
			
		acpOrderId();
		accNbr.setOrder_id(orderId);
		this.webpage = this.accNbrManager.transfer_list(this.getPage(), this.getPageSize(), 0, accNbr);
		return "importAccNbr_list";
	}
	
	
	/**
	 * 查看可用号码
	 * @return
	 */
	public String list_avialable(){
		
		lanList = new ArrayList<Lan>();
		lanList.add(new Lan(Consts.LAN_PROV, "全省"));
		lanList.addAll(this.lanServ.listLan());
		
		if(accNbr == null){
			accNbr = new AccNbr();
		}
		acpOrderId();
		accNbr.setOrder_id(orderId);
		accNbr.setP_apply_show_parent_num(Consts.APPLY_SHOW_PARENT_STOCK);
		accNbr.setQuery_type(Consts.ACCNBR_QUERY_TYPE_00A);
		accNbr.setState(Consts.ACC_NBR_STATE_0);
		accNbr.setLan_id(lan_id);
		
		this.webpage = this.accNbrManager.transfer_list(this.getPage(), this.getPageSize(), 0, accNbr);
		
		return "accnbr_list_avialable";
	}
	
	/**
	 * 查看可用号码
	 * @return
	 */
	public String list_avaible_forSearch(){
		
		lanList = new ArrayList<Lan>();
		lanList.add(new Lan(Consts.LAN_PROV, "全省"));
		lanList.addAll(this.lanServ.listLan());
		
		if(accNbr == null){
			accNbr = new AccNbr();
		}
		acpOrderId();
		accNbr.setOrder_id(orderId);
		accNbr.setP_apply_show_parent_num(Consts.APPLY_SHOW_PARENT_STOCK);
		accNbr.setQuery_type(Consts.ACCNBR_QUERY_TYPE_00A);
		accNbr.setState(Consts.ACC_NBR_STATE_0);
		accNbr.setLan_id(lan_id);
		
		this.webpage = this.accNbrManager.transfer_list_forSearch(this.getPage(), this.getPageSize(), 0, accNbr);
		
		return "accnbr_list_forSearch";
	}
	
	
	// 合约机订购
	public String order() {
		try {
			contractOrder();
			this.json = "{result:1,message:'订单[" + orderId + "]合约机处理成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"合约机处理失败：" + e.getMessage()
					+ "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}

	

	// 合约机号码调拨处理
	public String transfer() {
		try {
			transferCloud();
			this.json = "{result:1,message:'订单[" + orderId+ "]合约号码调拨成功'}";
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"合约号码调拨失败：" + e.getMessage()
					+ "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	/**
	 * 
	 * 订单云卡调拨受理
	 */
	private void transferCloud() {

		acpOrderId();
		Order order = this.orderManager.get(orderId);
		
		AccNbrRequest accNbrRequest = new AccNbrRequest();
		accNbrRequest.setAcc_nbrs(accnbr_ids);
		
		OrderRequst orderRequst = new OrderRequst();
		orderRequst.setService_name(order.getType_code());
		orderRequst.setFlow_name(OrderBuilder.ACCEPT);
		orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
		orderRequst.setOrderId(orderId);
		orderRequst.setAccNbrRequest(accNbrRequest);
		orderDirector.getOrderBuilder().buildOrderFlow();
		orderDirector.perform(orderRequst);
	}
	
	

	
	/**
	 * 
	 * 订单合约机受理
	 */
	private void contractOrder() {
		acpOrderId();
		orderDirector.getOrderBuilder().buildOrderFlow();
		OrderRequst orderRequst = new OrderRequst();
		orderRequst.setService_name(OrderBuilder.CONTRACT_KEY);
		orderRequst.setFlow_name(OrderBuilder.ACCEPT);
		orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
		
		OrderAuditRequest orderAuditRequest = new OrderAuditRequest();
		if(orderAudit ==null)
			 orderAudit = new OrderAudit();
		else{
			if(StringUtil.isEmpty(orderAudit.getP_audit_state()) && !"contract_apply".equals(from_page)) //非合约机申请过来
				throw new RuntimeException("处理状态不能为空");
		}
		orderAuditRequest.setApply_message(orderAudit.getApply_message());
		orderAuditRequest.setP_audit_message(orderAudit.getP_audit_message());
		orderAuditRequest.setP_audit_state(orderAudit.getP_audit_state());
		orderAuditRequest.setAudit_type(OrderStatus.AUDIT_TYPE_00A); //审核类型
		
		
		orderRequst.setOrderAuditRequest(orderAuditRequest);
		orderRequst.setOrderId(orderId);
		if(contract == null)
			contract = new Contract();
		orderRequst.setContract(contract);
		orderDirector.perform(orderRequst);
	}
	/**
	 * 查询号码预占信息
	 * @return
	 */
	public String noInfo(){
		String noCode = accNbr.getNum_code();
		try {
			if(noCode != null && !noCode.isEmpty()){
				accNbr = this.accNbrManager.getAccNbr(noCode);			
			}else {
				this.json = "{result:0,message:'传入号码为空'}";
				return WWAction.JSON_MESSAGE;
			}
			if(accNbr == null){
				this.json = "{result:0,message:'该号码没有预占信息'}";
				return WWAction.JSON_MESSAGE;
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:'" + e.getMessage() + "'}";
			return WWAction.JSON_MESSAGE;
		}
		this.contract = new Contract();
		this.contract.setAcc_nbr(accNbr.getNum_code());
		this.contract.setCust_name(accNbr.getCust_name());
		this.contract.setCerti_number(accNbr.getId_card_code());
		return "contract_info";
	}
	
	
	
	
	public Contract getContract() {
		return contract;
	}


	public void setContract(Contract contract) {
		this.contract = contract;
	}





	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public IOrderManager getOrderManager() {
		return orderManager;
	}


	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public OrderAudit getOrderAudit() {
		return orderAudit;
	}

	public void setOrderAudit(OrderAudit orderAudit) {
		this.orderAudit = orderAudit;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}


	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}


	public IAccNbrManager getAccNbrManager() {
		return accNbrManager;
	}


	public void setAccNbrManager(IAccNbrManager accNbrManager) {
		this.accNbrManager = accNbrManager;
	}


	public AccNbr getAccNbr() {
		return accNbr;
	}


	public void setAccNbr(AccNbr accNbr) {
		this.accNbr = accNbr;
	}


	public IOrderAuditManager getOrderAuditManager() {
		return orderAuditManager;
	}


	public void setOrderAuditManager(IOrderAuditManager orderAuditManager) {
		this.orderAuditManager = orderAuditManager;
	}


	public String getAccnbr_ids() {
		return accnbr_ids;
	}


	public void setAccnbr_ids(String accnbr_ids) {
		this.accnbr_ids = accnbr_ids;
	}


	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}


	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
	}


	public String getFrom_page() {
		return from_page;
	}


	public void setFrom_page(String from_page) {
		this.from_page = from_page;
	}


	public AcceptPlugin getCloudFlowAcceptPlugin() {
		return cloudFlowAcceptPlugin;
	}


	public void setCloudFlowAcceptPlugin(AcceptPlugin cloudFlowAcceptPlugin) {
		this.cloudFlowAcceptPlugin = cloudFlowAcceptPlugin;
	}


	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}


	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}


	public IContractManager getContractManager() {
		return contractManager;
	}


	public void setContractManager(IContractManager contractManager) {
		this.contractManager = contractManager;
	}




	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountType() {
		return countType;
	}


	public void setCountType(String countType) {
		this.countType = countType;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getLan_id() {
		return lan_id;
	}


	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}


	public String getShow_type() {
		return show_type;
	}


	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public AdminUser getAdminUser() {
		return adminUser;
	}


	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}


	public String getBegin_nbr() {
		return begin_nbr;
	}


	public void setBegin_nbr(String begin_nbr) {
		this.begin_nbr = begin_nbr;
	}


	public String getEnd_nbr() {
		return end_nbr;
	}


	public void setEnd_nbr(String end_nbr) {
		this.end_nbr = end_nbr;
	}


	public String getDealing_count() {
		return dealing_count;
	}


	public void setDealing_count(String dealing_count) {
		this.dealing_count = dealing_count;
	}


	public String getSucc_count() {
		return succ_count;
	}


	public void setSucc_count(String succ_count) {
		this.succ_count = succ_count;
	}


	public String getShow_acc_interval() {
		return show_acc_interval;
	}


	public void setShow_acc_interval(String show_acc_interval) {
		this.show_acc_interval = show_acc_interval;
	}


	public Map getCountMap() {
		return countMap;
	}


	public void setCountMap(Map countMap) {
		this.countMap = countMap;
	}


	public List<Lan> getLanList() {
		return lanList;
	}


	public void setLanList(List<Lan> lanList) {
		this.lanList = lanList;
	}



	
}
