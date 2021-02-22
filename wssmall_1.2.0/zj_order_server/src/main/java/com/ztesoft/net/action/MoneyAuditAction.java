package com.ztesoft.net.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import test.TestAipAop;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;




import zte.net.iservice.IRegionService;
import zte.params.region.req.RegionsListReq;
import zte.params.region.resp.RegionsListResp;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.inf.communication.client.CommCaller;
import com.ztesoft.net.ecsord.params.ecaop.req.KdnumberQryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.KdnumberQryResp;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.AuditBusinessVO;
import com.ztesoft.net.model.AuditQueryParame;
import com.ztesoft.net.model.MapComparator;
import com.ztesoft.net.model.MoneyAuditBaseDataVo;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IMoneyAuditManager;
import com.ztesoft.net.service.IOrdWarningManager;

import consts.ConstsCore;



/**
 * @Description 财务稽核
 * @author  zhangJun
 * @date    2016年10月1日
 * @version 1.0
 */
public class MoneyAuditAction extends WWAction {
	protected HttpServletRequest request = ServletActionContext.getRequest();
    protected HttpServletResponse response = ServletActionContext.getResponse();
	@Resource
	private IMoneyAuditManager moneyAuditManager;
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IRegionService regionService;
	
	private AuditQueryParame auditQueryParame;
	
	private List<Map> audit_busi_money_status;
	private List<Map> pay_type_list;
	private List<Map> order_from_list;
	private List<Map> order_type_list;
	private List<Map> payment_code_list;
	private List<Map> user_type_list;
	
	private List<Map> city_list;
	
	private String order_id;
	private MoneyAuditBaseDataVo moneyAuditBaseData;
	private List auditBusi;
	private String audit_bss_id;
	private List moneyForUDP;
	private List moneyForOrderId;
	private List moneyForLogiNo;
	private String audit_related_field;
	//导入
	private File file;
	private String excel_from;//excel 类型
	private String file_name;//excel 类型
	private String batch_number;//批次号
	private String excel_message;//
	
	
	private String first_open;//页面传递过来的值,刚进入财务稽核页面
	//信息修改
	private String data_from ;
	private String busi_money ;
	private String logi_no ;
	private String order_type ;
	private String paytype ;
	private String udp_no ;
	private String audit_note ;
	private String is_minus;
	private String serial_number;
	/**
	 * 查询稽核列表
	 * @return
	 */
	public String queryList() {
		TestAipAop test=new TestAipAop();
		//test.test();
		this.listAuditBusiMoneyStatus();
		this.listPayType();
		this.listOrderFrom();
		this.listOrderType();
		this.listPaymentCode();
		this.listUserType();
		this.listRegions();
		
		if(first_open==null || !first_open.equals("yes")){//如果first_open 为yes就跳过稽核数据查询
		Page page=moneyAuditManager.queryList(auditQueryParame,this.getPage(), this.getPageSize());
		
		this.webpage = this.getChinesePage(page);
		}
		
		if (excel != null && !"".equals(excel)) {
			String[] title=new String[]{};
			
			
			String[] content = new String[]{};
			
			String fileTitle =null;
			if("busi".equals(excel)){
				title=new String[]{"序号","地区","号码","BSS操作日期","网络类型","营业款","订单编号","外部单号","单量","订单处理人员","业务类型","订单来源","推广渠道","是否新用户","订单类型","退件审核人","商品名称","套餐名称",
						"支付类型","商城实收","商城营业款","联系电话","配送地址","物流单号","联系人","手机串号","发票编号","开户姓名","支付机构","营业款稽核状态","稽核说明"};
				content=new String[]{ "id","order_city_name","phone_num","bss_account_time", "data_from", "busi_money_sum","order_id","out_tid", "","lock_user_id","goods_type_names","order_from","spread_channel","is_old","order_type_return","","GoodsName","plan_title",
						"pay_type","paymoney","busi_money","logi_receiver_phone","ship_addr","logi_no","ship_name","terminal_num","invoice_no","phone_owner_name","payprovidername","audit_busi_money_status","audit_remark"};
				fileTitle = "营业款稽核报表";
			}else{
				title=new String[]{"序号","开户日期","地市","业务号码","订单来源","商城订单号","支付机构","支付日期","退款日期","支付公司订单号","支付类型","账户实收金额","订单实收金额","实收差额","BSS金额","CBSS金额","折扣金额",
						"物流单号","资金稽核状态","稽核说明"};
				content=new String[]{ "id","bss_account_time","order_city_name","phone_num","order_from","store_out_tid","new_payprovidername","pay_time","refund_time","audit_udp","paytype","zh_money","paymoney","","bss_money","cbss_money","",
						"logi_no","audit_receive_money_status","audit_remark"};
				fileTitle = "实收款稽核报表";
			}
			
			List list = moneyAuditManager.queryListForExp(auditQueryParame,excel);
		    DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
	    }
		return "money_audit_list";
	}
	
	public String expOfflinePay() throws IOException{
		
		String[] title=new String[]{"寄件日期","运单号码","对方公司","代收贷款","服务费"};
		
		
		String[] content = {"pickup_date","hwb_no","consignee_tell","cod_amoun","service_charge"};
		
		String fileTitle ="未匹配报表";
		
		List list = moneyAuditManager.queryOfflineListForExp(batch_number);
		moneyAuditManager.export(list, fileTitle, title, content, ServletActionContext.getResponse());
		this.json ="{result:0,msg:'成功'}";
		return this.JSON_MESSAGE;
		
	}

	//翻译字典值
		private Page getChinesePage(Page page){
			if(page!=null){
				List<MoneyAuditBaseDataVo> list = page.getResult();
				if(list==null)return page;
				List<MoneyAuditBaseDataVo> moneyAuditBaseDataList = new ArrayList<MoneyAuditBaseDataVo>();
				for(MoneyAuditBaseDataVo v:list){
					
					
					v.setAudit_busi_money_status_name(this.getNameById(v.getAudit_busi_money_status(), audit_busi_money_status));
					v.setAudit_receive_money_status_name(this.getNameById(v.getAudit_receive_money_status(), audit_busi_money_status));
					v.setPay_type_name(this.getNameById(v.getPay_type(), pay_type_list));
					v.setOrder_city_code_name(this.getNameById(v.getOrder_city_code(), city_list));//待添加
					v.setOrder_from_name(this.getNameById(v.getOrder_from(), order_from_list));
					v.setOrder_type_name(this.getNameById(v.getOrder_type_return(), order_type_list));
					v.setIs_old_name(this.getNameById(v.getIs_old(), user_type_list));
					moneyAuditBaseDataList.add(v);
				}
				page.setResult(moneyAuditBaseDataList);
			}
			return page;
		}
		
		private String getNameById(String id,List<Map> list){
			String name=id;
			if(list!=null&&list.size()>0){
				for (Map map : list) {
					String pname=Const.getStrValue(map, "pname");
					String pkey=Const.getStrValue(map, "pkey");
					if(pkey.equals(id)){
						name=pname;
					}
				}
			}
		  return name;
		}

	/**
	 * 跳转到导入报表的页面
	 * @return
	 */
	public String toImportExcel(){
		
		if(auditQueryParame==null){
			auditQueryParame= new AuditQueryParame();
		}
		try {
			auditQueryParame.setNow_date(DateUtil.getTime1());
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "import_excel";
	}
	/**
	 * 跳转到导入报表的页面
	 * @return
	 */
	public String toDelExcel(){
		
		if(auditQueryParame==null){
			auditQueryParame= new AuditQueryParame();
		}
		try {
			auditQueryParame.setNow_date(DateUtil.getTime1());
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "del_excel";
	}
	
	/**
	 * 导入数据
	 * @return
	 */
	public String importacion(){
		try {
			long start=System.currentTimeMillis();
			String rtnStr = moneyAuditManager.importacion(file, excel_from,batch_number,file_name);	
			long end=System.currentTimeMillis();
			logger.info("稽核数据入库花费时间："+(end-start));
			if ("0".equals(rtnStr)) {
				excel_message="没有数据导入，请检查导入文件内容！";
				return SUCCESS;
			}
			String[] rtnArr = rtnStr.split("#");
			excel_message="此次导入的总数是:" + rtnArr[0] + "条。 批次号为:" + rtnArr[1];
		} catch (Exception e) {
			e.printStackTrace();
			excel_message = "导入失败!" + e.getMessage();
		}
		return SUCCESS;
	}
	
	public String importacion_orderInput(){
		try {
			long start=System.currentTimeMillis();
			String rtnStr = moneyAuditManager.importacion_orderInput(file, file_name);	
			long end=System.currentTimeMillis();
			logger.info("补录数据入库花费时间："+(end-start));
			if ("0".equals(rtnStr)) {
				excel_message="没有数据导入，请检查导入文件内容！";
				return SUCCESS;
			}else if(rtnStr.indexOf("导入失败")!=-1){
				excel_message=rtnStr;
				return SUCCESS;
			}
			String[] rtnArr = rtnStr.split("#");
			excel_message="此次导入的总数是:" + rtnArr[0] + "条。";
		} catch (Exception e) {
			e.printStackTrace();
			excel_message = "导入失败!" + e.getMessage();
		}
		return SUCCESS;
	}	
	/**
	 * 删除导入的数据
	 * @return
	 */
	public String delExcle(){
		try{
			long start=System.currentTimeMillis();
			 moneyAuditManager.delExcle(excel_from, batch_number);
			logger.info("excel_from="+excel_from+"batch_number="+batch_number);
			long end=System.currentTimeMillis();
		   this.json ="{result:0,msg:'成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,msg:'失败，"+e.getMessage()+"'}";
		}
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 稽核数据
	 * @return
	 */
	public String auditData(){
		try{
			long start=System.currentTimeMillis();
			String rtnStr = moneyAuditManager.auditData(excel_from,batch_number);	
			long end=System.currentTimeMillis();
			logger.info("稽核数据花费时间："+(end-start));
		   this.json ="{result:0,msg:'成功'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:-1,msg:'"+e.getMessage()+"'}";
		}
		return this.JSON_MESSAGE;
	
	}
	/**
	 * 数据导出
	 * @return
	 */
	public String dataExport(){
		try {
			long start=System.currentTimeMillis();
			String rtnStr = moneyAuditManager.dataExport(auditQueryParame);	
			long end=System.currentTimeMillis();
			logger.info("稽核数据花费时间："+(end-start));
			excel_message=rtnStr;
		} catch (Exception e) {
			e.printStackTrace();
			excel_message = "稽核失败!" + e.getMessage();
		}
		return SUCCESS;
	}
	

	public String manualAudit(){
		getMoneyAuditBaseData(order_id);
		getAuditBusinessList(order_id);
		getMoneyInfo(order_id);
		return "manualAudit";
		
	}
	
	public void getMoneyAuditBaseData(String order_id){
		moneyAuditBaseData = moneyAuditManager.queryOrderListById(order_id);
	}
	
	public void getAuditBusinessList(String order_id){
		auditBusi = moneyAuditManager.queryFlowById(order_id);
	}
	public void getMoneyInfo(String order_id){
			moneyForUDP = moneyAuditManager.queryMoneyForUDPById(order_id);
			moneyForOrderId = moneyAuditManager.queryMoneyForOrderIdById(order_id);
			moneyForLogiNo = moneyAuditManager.queryMoneyForLogiNoById(order_id);
	}
	public String updateBusi(){
		try{
			Map resu = moneyAuditManager.updateBusi(order_id,audit_bss_id);
			this.json="{result:"+(String)resu.get("res")+",message:'"+(String)resu.get("msg")+"'}";
			getMoneyAuditBaseData(order_id);
			getAuditBusinessList(order_id);
		}catch(Exception e){
			e.printStackTrace();
			this.json="{result:1,message:'操作失败'}";
		}
		return this.JSON_MESSAGE;
	}
	
	public String updateOrder(){
		try{
			Map change = new HashMap();
			change.put("data_from", data_from) ;
			change.put("busi_money",busi_money) ;
			change.put("logi_no",logi_no) ;
			change.put("order_type",order_type) ;
			change.put("paytype",paytype) ;
			change.put("udp_no",udp_no) ;
			audit_note=request.getParameter("audit_note"); 
			audit_note=URLDecoder.decode(audit_note,"utf8"); 
			change.put("audit_note",audit_note) ;
			Map resu = moneyAuditManager.updateOrder(order_id,change);
			this.json="{result:"+(String)resu.get("res")+",message:'"+(String)resu.get("msg")+"'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json="{result:1,message:'操作失败'}";
		}
		return this.JSON_MESSAGE;
	}
	
	public String addFlow(){
		try{
			Map change = new HashMap();
			change.put("data_from", data_from) ;
			change.put("is_minus",is_minus) ;
			change.put("serial_number",serial_number) ;
			Map resu = moneyAuditManager.addFlow(order_id,change);
			this.json="{result:"+(String)resu.get("res")+",message:'"+(String)resu.get("msg")+"'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json="{result:1,message:'操作失败'}";
		}
		return this.JSON_MESSAGE;
	}
	/**
	 * 删除导入的数据
	 * @return
	 */
	public String auditBusiMoneyByOrderId(){
		try{
		    String msg= moneyAuditManager.auditBusiMoneyByOrderId(order_id);
		    if(StringUtil.isEmpty(msg)){
		    	msg="成功";
		    }
		   this.json ="{result:0,msg:'"+msg+"'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,msg:'失败，"+e.getMessage()+"'}";
		}
		return this.JSON_MESSAGE;
	}
	/**
	 * 删除导入的数据
	 * @return
	 */
	public String auditReceiveByOrderId(){
		try{
		    String msg= moneyAuditManager.auditReceiveByOrderId(order_id);
		    if(StringUtil.isEmpty(msg)){
		    	msg="成功";
		    }
		   this.json ="{result:0,msg:'"+msg+"'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{result:1,msg:'失败，"+e.getMessage()+"'}";
		}
		return this.JSON_MESSAGE;
	}
	/**
	 * 静态数据：稽核状态
	 */
	private void listAuditBusiMoneyStatus() {
		audit_busi_money_status = getConsts(StypeConsts.AUDIT_BUSI_MONEY_STATUS);
		if (audit_busi_money_status == null) {
			audit_busi_money_status = new ArrayList<Map>();
		}
		Map map0=new HashMap();
		map0.put("pkey", "");
		map0.put("pname", "--请选择--");
		audit_busi_money_status.add(0, map0);
		//Collections.sort(audit_busi_money_status,new MapComparator());
		
	}
	/**
	 * 静态数据：支付方式
	 */
	private void listPayType(){
		pay_type_list = getConsts(StypeConsts.PAY_TYPE);
		if(pay_type_list==null){
			pay_type_list = new ArrayList<Map>();
		}
		Map map0=new HashMap();
		map0.put("pkey", "");
		map0.put("pname", "--请选择--");
		pay_type_list.add(0, map0);
	}
	/**
	 * 静态数据：订单类型 先临时写入代码
	 */
	private void listOrderType(){
		//order_type_list = getConsts(StypeConsts.DIC_ORDER_NEW_TYPE);
		if(order_type_list==null){
			order_type_list = new ArrayList<Map>();
		}
		Map map0=new HashMap();
		map0.put("pkey", "");
		map0.put("pname", "--请选择--");
		order_type_list.add(0, map0);
		
		Map map1=new HashMap();
		map1.put("pkey", EcsOrderConsts.BSS_CANCEL_STATUS_0);
		map1.put("pname", "新增");
		order_type_list.add(1, map1);
		
		Map map2=new HashMap();
		map2.put("pkey", EcsOrderConsts.BSS_CANCEL_STATUS_1);
		map2.put("pname", "返销");
		order_type_list.add(2, map2);
	}
	/**
	 * 静态数据：订单来源
	 */
	private void listOrderFrom() {
		order_from_list = getConsts("ZbOrderSource");
		if (order_from_list == null) {
			order_from_list = new ArrayList<Map>();
		}else{
			Collections.sort(order_from_list,new MapComparator());
		}
		Map map0=new HashMap();
		map0.put("pkey", "");
		map0.put("pname", "--请选择--");
		order_from_list.add(0, map0);
	}
	/**
	 * 静态数据：支付机构
	 */
	private void listPaymentCode() {
		payment_code_list = getConsts(StypeConsts.PAYMENT_CODE);
		if (payment_code_list == null) {
			payment_code_list = new ArrayList<Map>();
		}else{
			Collections.sort(payment_code_list,new MapComparator());
		}
		Map map0=new HashMap();
		map0.put("pkey", "");
		map0.put("pname", "--请选择--");
		payment_code_list.add(0, map0);
	}
	/**
	 * 静态数据：用户类型
	 */
	private void listUserType() {
		user_type_list = getConsts(StypeConsts.USER_TYPE);
		if (user_type_list == null) {
			user_type_list = new ArrayList<Map>();
		}
	}
	/**
	 * 地市查询条件
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listRegions(){
		RegionsListReq req = new RegionsListReq();
		req.setRegion_type(RegionsListReq.CITY);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
		req.setRegion_parent_id(province_region);
		RegionsListResp resp = regionService.listRegions(req);
		List<Regions>  regionList = resp.getRegionList();
		
		city_list=new ArrayList<Map>();
		
		if(regionList!=null&&regionList.size()>0){
			for (int i = 0; i < regionList.size(); i++) {
				Map regions=(Map) regionList.get(i);
				Map map=new HashMap();
				map.put("pkey", Const.getStrValue(regions, "region_id"));
				map.put("pname",Const.getStrValue(regions, "local_name"));
				city_list.add(map);
			}
		}
	}
	private List<Map> getConsts(String key){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(key);
        return list;
	}

	
	
	
	
	

	public IMoneyAuditManager getMoneyAuditManager() {
		return moneyAuditManager;
	}


	public void setMoneyAuditManager(IMoneyAuditManager moneyAuditManager) {
		this.moneyAuditManager = moneyAuditManager;
	}


	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}


	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}


	public AuditQueryParame getAuditQueryParame() {
		return auditQueryParame;
	}


	public void setAuditQueryParame(AuditQueryParame auditQueryParame) {
		this.auditQueryParame = auditQueryParame;
	}


	public List<Map> getAudit_busi_money_status() {
		return audit_busi_money_status;
	}


	public void setAudit_busi_money_status(List<Map> audit_busi_money_status) {
		this.audit_busi_money_status = audit_busi_money_status;
	}


	public List<Map> getPay_type_list() {
		return pay_type_list;
	}


	public void setPay_type_list(List<Map> pay_type_list) {
		this.pay_type_list = pay_type_list;
	}


	public List<Map> getOrder_from_list() {
		return order_from_list;
	}


	public void setOrder_from_list(List<Map> order_from_list) {
		this.order_from_list = order_from_list;
	}


	public List<Map> getOrder_type_list() {
		return order_type_list;
	}


	public void setOrder_type_list(List<Map> order_type_list) {
		this.order_type_list = order_type_list;
	}

	public List<Map> getPayment_code_list() {
		return payment_code_list;
	}


	public void setPayment_code_list(List<Map> payment_code_list) {
		this.payment_code_list = payment_code_list;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getExcel_from() {
		return excel_from;
	}
	public void setExcel_from(String excel_from) {
		this.excel_from = excel_from;
	}

	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}



	public String getBatch_number() {
		return batch_number;
	}
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getExcel_message() {
		return excel_message;
	}
	public void setExcel_message(String excel_message) {
		this.excel_message = excel_message;
	}
	public MoneyAuditBaseDataVo getMoneyAuditBaseData() {
		return moneyAuditBaseData;
	}
	public void setMoneyAuditBaseData(MoneyAuditBaseDataVo moneyAuditBaseData) {
		this.moneyAuditBaseData = moneyAuditBaseData;
	}
	public List getAuditBusi() {
		return auditBusi;
	}
	public void setAuditBusi(List auditBusi) {
		this.auditBusi = auditBusi;
	}
	public String getAudit_bss_id() {
		return audit_bss_id;
	}
	public void setAudit_bss_id(String audit_bss_id) {
		this.audit_bss_id = audit_bss_id;
	}
	public String getData_from() {
		return data_from;
	}
	public void setData_from(String data_from) {
		this.data_from = data_from;
	}
	public String getBusi_money() {
		return busi_money;
	}
	public void setBusi_money(String busi_money) {
		this.busi_money = busi_money;
	}
	public String getLogi_no() {
		return logi_no;
	}
	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getUdp_no() {
		return udp_no;
	}
	public void setUdp_no(String udp_no) {
		this.udp_no = udp_no;
	}
	public String getAudit_note() {
		return audit_note;
	}
	public void setAudit_note(String audit_note) {
		this.audit_note = audit_note;
	}
	public List<Map> getUser_type_list() {
		return user_type_list;
	}
	public void setUser_type_list(List<Map> user_type_list) {
		this.user_type_list = user_type_list;
	}
	public IRegionService getRegionService() {
		return regionService;
	}
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
	}
	public List<Map> getCity_list() {
		return city_list;
	}
	public void setCity_list(List<Map> city_list) {
		this.city_list = city_list;
	}

	public List getMoneyForUDP() {
		return moneyForUDP;
	}

	public void setMoneyForUDP(List moneyForUDP) {
		this.moneyForUDP = moneyForUDP;
	}

	public List getMoneyForOrderId() {
		return moneyForOrderId;
	}

	public void setMoneyForOrderId(List moneyForOrderId) {
		this.moneyForOrderId = moneyForOrderId;
	}

	public List getMoneyForLogiNo() {
		return moneyForLogiNo;
	}

	public void setMoneyForLogiNo(List moneyForLogiNo) {
		this.moneyForLogiNo = moneyForLogiNo;
	}

	public String getAudit_related_field() {
		return audit_related_field;
	}

	public void setAudit_related_field(String audit_related_field) {
		this.audit_related_field = audit_related_field;
	}

	public String getIs_minus() {
		return is_minus;
	}

	public void setIs_minus(String is_minus) {
		this.is_minus = is_minus;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getFirst_open() {
		return first_open;
	}

	public void setFirst_open(String first_open) {
		this.first_open = first_open;
	}

	
	
}
