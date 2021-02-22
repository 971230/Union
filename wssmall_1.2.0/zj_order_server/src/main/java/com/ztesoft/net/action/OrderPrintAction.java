package com.ztesoft.net.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemsAptPrintsBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.AcceptPrintModel;
import com.ztesoft.net.model.InvoicePrintInfo;
import com.ztesoft.net.model.OrderItemExtvtlVo;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.model.PrintOrderTree;
import com.ztesoft.net.service.IOrdPrtManager;
import com.ztesoft.net.service.IOrderInvoiceManager;


public class OrderPrintAction extends WWAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IOrdPrtManager ordPrtManager;
	@Resource
	private IOrderInvoiceManager iOrderInvoiceManager;
	private InvoicePrintInfo invoicePrintInfo;
	
	private OrderQueryParams params;
	private String order_id;
	private AcceptPrintModel model;
	private PrintOrderTree pot ;
	private String orderPrintURL;
	private String retInfo;
	private String warmTips;//温馨提示
	private String out_tid;//外部订单id,外系统调用时
	private String order_is_his;//1-是历史单（重发、补寄时用到）
	private String invoice_num;//发票号
	private String whFrom;
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private List<Map> is_order_his_list;
	private List<Map> shipping_type_list;
	
	private String name;
	private String tel;
	private String shishou;
	private String daxie;
	private String yingshou;
	private String chuanma;
	private String memo_info;//备注
	private String bss_operator;//受理人（开户人）
	private String accept_form;//aop受理单打印页面
	
	private String ship_time; // 发货时间 ZX Add 2015-11-07
	private List<OrderItemExtvtlVo> voList = new ArrayList<OrderItemExtvtlVo>();  // 商品列表 ZX Add 2015-11-07
	private static Logger logger = Logger.getLogger(OrderPrintAction.class);
	
	/**
	 * 获取订单列表
	 * @return
	 */
	public String ordPrtList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		this.listOrderListIsHis();
		this.listShippingType();
		if (params==null) params = new OrderQueryParams();
		if (out_tid!=null&&!out_tid.equals("")) params.setOut_tid(out_tid);//单点登录链接传过来
		if (order_id!=null&&!order_id.equals("")) params.setOrder_id(order_id);//质检稽核页面传过来
		if(ordPrtManager.checkIfNeedQueryHis(request, params.getOut_tid())){
			params.setOrder_is_his(EcsOrderConsts.IS_ORDER_HIS_YES);
		}
		if(StringUtil.isEmpty(params.getOrder_id()) && StringUtil.isEmpty(params.getOut_tid())){//
			Calendar date = new GregorianCalendar();
	    	date.add(Calendar.MONTH, -2);
			String startTime = DF.format(date.getTime());
			String endTime = DF.format(new Date());
			if(StringUtil.isEmpty(params.getCreate_start())){
		    	params.setCreate_start(startTime);
			}
			if(StringUtil.isEmpty(params.getCreate_end())){
				params.setCreate_end(endTime);
			}
		}
		
		this.webpage = ordPrtManager.qryPrtOrdList(this.getPage(), this.getPageSize(), params);
		return "order_print_list";
	}
	public static String getReplaccStr(String strInfo) {
    	return strInfo.replaceAll("lt;", "<").replaceAll("gt;", ">")
    	.replaceAll("zt;", "/").replaceAll("lp;", "{").replaceAll(
    	"rp;", "}").replaceAll("np;", "\"").replaceAll("amp;",
    	"&").replaceAll("iexcl;", "\\?").replaceAll("~", "<br>").replace("^", " ");
    }
	/**
	 * 受理单打印数据获取
	 * @return
	 */
	public String orderPrint(){
		boolean isHis=false;//是-历史单
		if(order_is_his!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//历史单
			isHis=true;
		}
		if(isHis){
			this.bss_operator = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.BSS_OPERATOR_NAME)+CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.BSS_OPERATOR);
		}else{
			this.bss_operator = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_OPERATOR_NAME)+CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_OPERATOR);
		}
		String is_aop = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.IS_AOP);
		if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)){//判断是否aop的请求的受理单打印
			String flowTraceId = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
			if(!isHis){
				if(!("F".equals(flowTraceId)||"H".equals(flowTraceId)||"L".equals(flowTraceId)||"J".equals(flowTraceId))){
		     		this.orderPrintURL =  EcsOrderConsts.NO_ORDER_PRINT_INFO_URL;
					return  "print_template"; 
				}
			}
			List<OrderItemBusiRequest> orderItemBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests();
	    	List<OrderItemsAptPrintsBusiRequest> orderItemsAptPrintsRequests = orderItemBusiRequests.get(0).getOrderItemsAptPrintsRequests();
	    	OrderItemsAptPrintsBusiRequest   ordPrt = orderItemsAptPrintsRequests.get(0);
	    	        this.accept_form = "";
	    	        if(!StringUtil.isEmpty(ordPrt.getAcceptance_html())){
	    				this.accept_form += ordPrt.getAcceptance_html();
	    			}
	    			if(!StringUtil.isEmpty(ordPrt.getAcceptance_html_2())){
	    				this.accept_form += ordPrt.getAcceptance_html_2();
	    			}
	    			if(!StringUtil.isEmpty(ordPrt.getAcceptance_html_3())){
	    				this.accept_form += ordPrt.getAcceptance_html_3();
	    			}
	     	this.accept_form = getReplaccStr(this.accept_form);
	     	this.orderPrintURL = EcsOrderConsts.AOP_ORDER_PRINT_INFO_URL;
	     	if(StringUtil.isEmpty(accept_form)){
	     		this.orderPrintURL =  EcsOrderConsts.NO_ORDER_PRINT_INFO_URL;
	     	}
	    	return "print_template"; 
		}
		
        this.pot = this.ordPrtManager.getOrdItemsAptPrtBusiReq(order_id,isHis);
		this.model = this.ordPrtManager.getActPrtMod(pot.getReceipt_code(),this.pot.getReceipt_mode());
		
		if(null != this.model)
		{
			this.orderPrintURL = this.model.getReceipt_url();
			if(null != this.pot)
			{
				this.retInfo = this.pot.getAcceptance_html();
				//this.retInfo = "{\"mainContentOne\":\"1、套餐名称：A类3G基本套餐66元档。<br/>2、套餐当月生效方式为：全月套餐：申请套餐即时生效，您入网当月即按照用户所选的基本套餐收取套餐月费，所含内容不变。<br/>3、套餐信息：套餐月费66元，套餐包含国内语音拨打分钟数50分钟，国内流量300MB，国内短信发送条数240条，国内接听免费（含可视电话）。套餐超出后，国内语音拨打0.20元/分钟，国内流量0.0003元/KB，国内可视电话拨打0.60元/分钟。套餐赠送多媒体内容6个M、文本内容10个T、国内可视电话拨打分钟数10分钟，以及来电显示和手机邮箱，其他执行标准资费。月套餐及叠加包内包含的业务量仅限当月使用，不能延续至下月使用。<br/>4、客户每月使用手机上网流量达到6GB后，达到封顶流量。<br/>\",\"contactAddr\":\"广东 佛山市 南海区 里水镇 恒大御景湾 7栋3301\",\"mainContentTwo\":\"基本通信服务及附加业务名称及描述：国内通话。<br/>可选业务包名称及描述：无。<br/>\",\"agentPaperType\":\"\",\"acctAddr\":\"广东 佛山市 南海区 里水镇 恒大御景湾 7栋3301\",\"userType\":\"3G普通用户\",\"paperExpr\":\"20501231\",\"acctName\":\"刘远\",\"mainContentFive\":\"\",\"agentPhone\":\"\",\"custType\":\"个人\",\"payMethod\":\"现金\",\"paperAddr\":\"辽宁省大连市中山区修竹街4号14-10\",\"paperType\":\"18位身份证\",\"staffInfo\":\"黄剑青DZZQJ057\",\"contactPhone\":\"Amanda Lim 15524654176\",\"bankAcctName\":\"\",\"bankName\":\"\",\"bankAcct\":\"\",\"paperNo\":\"230302199002174710\",\"mainContentThree\":\"\",\"bankCode\":\"\",\"agentPaperNo\":\"\",\"mainContentFour\":\"1、优惠活动名称：存费送费一年期。<br/>2、优惠活动协议期：客户承诺自2014年11月至2015年9月，在网12个月。<br/>3、优惠活动信息：预存话费送话费：一次性缴纳合约计划标准预存款为120.00元，其中首月返还金额0.00元，次月起连续11个月每月返还金额10.00元，最后一个月返还剩余部分金额；次月起连续12个月每月赠送话费20.00元。<br/>\",\"agentName\":\"\",\"userNo\":\"18575814246\",\"custName\":\"刘远\"}";
			}
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			this.warmTips = cacheUtil.getConfigInfo(EcsOrderConsts.ORDER_PRINT_WARN_TIPS);
		}
		else
		{
			this.orderPrintURL = EcsOrderConsts.NO_ORDER_PRINT_INFO_URL;
		}
		return "print_template";
	}
	/**
	 * 发票打印数据获取
	 * @return
	 */
	public void invoicePrint() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter invoiceWrite = response.getWriter();
		try {
			String realname="";
			if(request.getSession().getAttribute(EcsOrderConsts.NEW_MALL_LOGIN)!=null){
				realname=(String) request.getSession().getAttribute(EcsOrderConsts.NEW_MALL_LOGIN);
			};
			response.setCharacterEncoding("GBK");
	    	response.setContentType("text/html;charset=GBK;");
			String printHtml = iOrderInvoiceManager.doPrintInvoice(order_id,realname,order_is_his,request.getContextPath());
			invoiceWrite.write(printHtml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			invoiceWrite.write(e.getMessage());
		}
	}
	
	/**
	 * 保存发票打印明细
	 * @return
	 */
	public String saveInvoicePrintDetail() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			boolean isHis=false;
			if(order_is_his!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//历史单
				isHis=true;
			}
			//允许多次打印发票，但只保留最新的发票号和备注信息
//			if (StringUtils.isNotBlank(order_id)) {
//				OrderItemsInvPrintsBusiRequest req = iOrderInvoiceManager.getPrintReq(order_id,isHis);
//				if (req != null) {
//					String print_flag = req.getPrint_flag();
//					if (print_flag.equals("1")) {
//						json = "{result:1, message:'此订单已打印'}";
//						return JSON_MESSAGE;
//					}
//				}
//			}
			if (invoicePrintInfo == null) {
				invoicePrintInfo = new InvoicePrintInfo();
				invoicePrintInfo.setShouhuorenxingming(name);
				invoicePrintInfo.setShouhuorendianhua(tel);
				invoicePrintInfo.setShishoujine(Float.valueOf(shishou!=null?shishou:"0.0"));
				invoicePrintInfo.setYingshoujine(Float.valueOf(yingshou!=null?yingshou:"0.0"));
				invoicePrintInfo.setDaxie(daxie);
				invoicePrintInfo.setZhongduanchuanhao(chuanma);
				invoicePrintInfo.setWaibudingdanbianhao(order_id);
				invoicePrintInfo.setInvoice_num(invoice_num);
				invoicePrintInfo.setMemo(memo_info);
			} 
			String realname="";
			if(request.getSession().getAttribute(EcsOrderConsts.NEW_MALL_LOGIN)!=null){
				realname=(String) request.getSession().getAttribute(EcsOrderConsts.NEW_MALL_LOGIN);
			};
			iOrderInvoiceManager.saveInvoicePrintDetail(invoicePrintInfo,isHis,realname);
			json = "{result:0,message:'成功'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{result:1,message:'失败'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 发票打印状态更新
	 * @return
	 */
	public String updatePrtStatus(){
		if(order_is_his!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//历史单不更新了
			ordPrtManager.updateOrdItemsAptPrtBusiReq(order_id,true);
		}else{
			ordPrtManager.updateOrdItemsAptPrtBusiReq(order_id,false);
		}
		
		json ="{result:1,msg:'修改成功!'}";
		return JSON_MESSAGE;
	}
	
	
	/**
	 * 是否是历史单查询条件
	 * @作者 zhangjun
	 * @创建日期 2014-12-4
	 */
	private void listOrderListIsHis(){
		is_order_his_list = getConsts(StypeConsts.IS_ORDER_HIS);
		if(is_order_his_list==null){
			is_order_his_list = new ArrayList<Map>();
		}
	}
	/**
	 * 配关方式
	 * @作者 MoChunrun
	 * @创建日期 2014-10-9
	 */
	private void listShippingType(){
		shipping_type_list = getConsts(StypeConsts.SHIPPING_TYPE);
		if(shipping_type_list==null){
			shipping_type_list = new ArrayList<Map>();
		}
		Map m0 = new HashMap();
		m0.put("pkey", "-1");
		m0.put("pname", "--请选择--");
		shipping_type_list.add(0,m0);
	}
	private List<Map> getConsts(String key){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(key);
        return list;
	}

	
	/**
	 * 调拨单打印
	 * @return
	 */
	public String allotOrderPrt() {
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		try {
			ship_time = DateUtil.getTime2();
		} catch (FrameException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (orderTree != null) {
			List<OrderItemExtvtlBusiRequest> orderItemExtvtlList = orderTree.getOrderItemExtvtlBusiRequests();
			if (CollectionUtils.isNotEmpty(orderItemExtvtlList)) {
				try {
					List<OrderItemExtvtlVo> voList1 = new ArrayList<OrderItemExtvtlVo>();
					Map<String, Integer> mp = new HashMap<String, Integer>();
					for (OrderItemExtvtlBusiRequest ext : orderItemExtvtlList) {
						OrderItemExtvtlVo vo = new OrderItemExtvtlVo();
						BeanUtils.copyProperties(vo, ext);
						voList1.add(vo);
					}
					for (int i = 0 ; i < voList1.size(); i++) {
						if (StringUtils.isNotBlank(voList1.get(i).getSku())) {
							if (mp.get(voList1.get(i).getSku()) != null) {
								int goods_num = mp.get(voList1.get(i).getSku());
								mp.put(voList1.get(i).getSku(), goods_num+1);
							} else {
								mp.put(voList1.get(i).getSku(), 1);
							}
						} else {
							continue;
						}
					}
					for (String key : mp.keySet()) {
						for (int i = 0 ; i < voList1.size() ; i ++) {
							if (key.equals(voList1.get(i).getSku())) {
								voList1.get(i).setGoods_num(mp.get(key));
								String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
								String order_city_code_name = CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_ZONE_REGION_CD", order_city_code);
								voList1.get(i).setShip_city(order_city_code_name);
								voList1.get(i).setChannel_name(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RESERVE4));
								voList.add(voList1.get(i));
								break;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "allot_order_prt";
	}
	
	
	
	public IOrdPrtManager getOrdPrtManager() {
		return ordPrtManager;
	}

	public void setOrdPrtManager(IOrdPrtManager ordPrtManager) {
		this.ordPrtManager = ordPrtManager;
	}

	public OrderQueryParams getParams() {
		return params;
	}

	public void setParams(OrderQueryParams params) {
		this.params = params;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public AcceptPrintModel getModel() {
		return model;
	}
	
	public void setModel(AcceptPrintModel model) {
		this.model = model;
	}
	
	public PrintOrderTree getPot() {
		return pot;
	}
	
	public void setPot(PrintOrderTree pot) {
		this.pot = pot;
	}
	
	public String getOrderPrintURL() {
		return orderPrintURL;
	}
	
	public void setOrderPrintURL(String orderPrintURL) {
		this.orderPrintURL = orderPrintURL;
	}
	
	public String getRetInfo() {
		return retInfo;
	}
	
	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
	}
	
	public String getWarmTips() {
		return warmTips;
	}
	
	public void setWarmTips(String warmTips) {
		this.warmTips = warmTips;
	}
	
	public IOrderInvoiceManager getiOrderInvoiceManager() {
		return iOrderInvoiceManager;
	}
	public void setiOrderInvoiceManager(IOrderInvoiceManager iOrderInvoiceManager) {
		this.iOrderInvoiceManager = iOrderInvoiceManager;
	}
	public static void main(String[] args){
		String req = "{\"ActiveNo\":\"EM20141061229148157\",\"OrderId\":\"1616974955\",\"GoodsType\":\"ZHYL\",\"UpdateScope\":\"000000111000\",\"PostInfo\":null,\"DistributionInfo\":null,\"InvoiceInfo\":null,\"PayInfo\":null,\"DiscountInfo\":null,\"GiftInfo\":null,\"GoodsAttInfo\":[{\"CustomerName\":\"刘建彪\",\"CertType\":\"SFZ18\",\"CertNum\":\"430424199504146411\",\"CustomerType\":\"GRKH\",\"CertLoseTime\":\"20501231235959\",\"CertAddr\":\"湖南省衡东县荣桓镇新屋村八组\",\"RefereeName\":\"\",\"RefereeNum\":\"\",\"DevelopCode\":\"5103497665\",\"DevelopName\":\"电子渠道自提\",\"PhoneNum\":\"18575545592\",\"ReliefPresFlag\":\"NO\",\"SaleMode\":\"FCPB\",\"SimId\":\"\",\"CardType\":\"MC\",\"ProductCode\":\"99999830\",\"ProductName\":\"4G全国套餐76元\",\"ProductNet\":\"4G\",\"ProductBrand\":\"4GPH\",\"FirstMonBillMode\":\"COMM\",\"SerType\":\"BAK\",\"ProductType\":\"MAIN\",\"Package\":[],\"SubProdInfo\":[],\"ActivityInfo\":[{\"ActivityType\":\"CFSF\",\"ActivityCode\":\"89000100\",\"ActivityName\":null,\"ActProtPer\":\"12\"}]}],\"FeeInfo\":[{\"FeeID\":\"100005\",\"FeeDes\":\"[预存]营业厅收入(营业缴费)_普通预存款(不可清退)\",\"OrigFee\":120000,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":120000},{\"FeeID\":\"EMAL1002\",\"FeeDes\":\"商城侧卡费\",\"OrigFee\":0,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":0},{\"FeeID\":\"99\",\"FeeDes\":\"多缴预存\",\"OrigFee\":0,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":0},{\"FeeID\":\"1001\",\"FeeDes\":\"USIM卡费用\",\"OrigFee\":0,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":0},{\"FeeID\":\"2001\",\"FeeDes\":\"号码预存费用\",\"OrigFee\":0,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":0},{\"FeeID\":\"2002\",\"FeeDes\":\"合约费用\",\"OrigFee\":120000,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":120000},{\"FeeID\":\"4001\",\"FeeDes\":\"多交预存款费用\",\"OrigFee\":0,\"ReliefFee\":0,\"ReliefResult\":null,\"RealFee\":0}],\"UserInfo\":{\"CustomerName\":\"刘建彪\",\"CertType\":\"SFZ18\",\"CertNum\":\"430424199504146411\",\"CustomerType\":\"GRKH\",\"CertLoseTime\":\"20501231235959\",\"CertAddr\":\"湖南省衡东县荣桓镇新屋村八组\",\"RefereeName\":\"\",\"RefereeNum\":null,\"UserType\":\"NEW\"},\"DevelopInfo\":null,\"OperatorInfo\":null,\"FlowInfo\":null}";
		JSONObject obj = JSONObject.fromObject(req);
		logger.info(obj.getString("OrderId"));
	}
	public InvoicePrintInfo getInvoicePrintInfo() {
		return invoicePrintInfo;
	}
	public void setInvoicePrintInfo(InvoicePrintInfo invoicePrintInfo) {
		this.invoicePrintInfo = invoicePrintInfo;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public List<Map> getIs_order_his_list() {
		return is_order_his_list;
	}
	public void setIs_order_his_list(List<Map> is_order_his_list) {
		this.is_order_his_list = is_order_his_list;
	}
	public String getOrder_is_his() {
		return order_is_his;
	}
	public void setOrder_is_his(String order_is_his) {
		this.order_is_his = order_is_his;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getShishou() {
		return shishou;
	}

	public void setShishou(String shishou) {
		this.shishou = shishou;
	}

	public String getDaxie() {
		return daxie;
	}

	public void setDaxie(String daxie) {
		this.daxie = daxie;
	}

	public String getYingshou() {
		return yingshou;
	}

	public void setYingshou(String yingshou) {
		this.yingshou = yingshou;
	}

	public String getChuanma() {
		return chuanma;
	}

	public void setChuanma(String chuanma) {
		this.chuanma = chuanma;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public List<Map> getShipping_type_list() {
		return shipping_type_list;
	}

	public void setShipping_type_list(List<Map> shipping_type_list) {
		this.shipping_type_list = shipping_type_list;
	}

	public String getWhFrom() {
		return whFrom;
	}

	public void setWhFrom(String whFrom) {
		this.whFrom = whFrom;
	}

	public String getMemo_info() {
		return memo_info;
	}

	public void setMemo_info(String memo_info) {
		this.memo_info = memo_info;
	}

	public String getBss_operator() {
		return bss_operator;
	}

	public void setBss_operator(String bss_operator) {
		this.bss_operator = bss_operator;
	}

	public String getAccept_form() {
		return accept_form;
	}

	public void setAccept_form(String accept_form) {
		this.accept_form = accept_form;
	}
	
	public String getShip_time() {
		return ship_time;
	}
	public void setShip_time(String ship_time) {
		this.ship_time = ship_time;
	}
	
	public List<OrderItemExtvtlVo> getVoList() {
		return voList;
	}
	public void setVoList(List<OrderItemExtvtlVo> voList) {
		this.voList = voList;
	}
	
}
