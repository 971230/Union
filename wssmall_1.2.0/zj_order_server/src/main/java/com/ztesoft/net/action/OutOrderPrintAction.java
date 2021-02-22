/**
 * 
 */
package com.ztesoft.net.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.AcceptPrintModel;
import com.ztesoft.net.model.InvoicePrintInfo;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.model.PrintOrderTree;
import com.ztesoft.net.service.IOrdPrtManager;
import com.ztesoft.net.service.IOrderInvoiceManager;

/**
 * @author ZX
 * 外部系统打印受理单和发票
 * OutOrderPrintAction.java
 * 2014-11-21
 */
public class OutOrderPrintAction extends WWAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private IOrdPrtManager ordPrtManager;
	@Resource
	private IOrderInvoiceManager iOrderInvoiceManager;
	private InvoicePrintInfo invoicePrintInfo;
	
	private String out_order_id;
	private String order_id;
	private AcceptPrintModel model;
	private PrintOrderTree pot ;
	private String orderPrintURL;
	private String retInfo;
	private String warmTips;//温馨提示
	private static Logger logger = Logger.getLogger(OutOrderPrintAction.class);
	public String outOrderPrint() {
		this.pot = this.ordPrtManager.getOrdItemsAptPrtBusiReq(order_id,false);
		this.model = this.ordPrtManager.getActPrtMod(pot.getReceipt_code(),this.pot.getReceipt_mode());
		if(null != this.model){
			this.orderPrintURL = this.model.getReceipt_url();
			if(null != this.pot){
				this.retInfo = this.pot.getAcceptance_html();
				this.retInfo = "{\"mainContentOne\":\"1、套餐名称：A类3G基本套餐66元档。<br/>2、套餐当月生效方式为：全月套餐：申请套餐即时生效，您入网当月即按照用户所选的基本套餐收取套餐月费，所含内容不变。<br/>3、套餐信息：套餐月费66元，套餐包含国内语音拨打分钟数50分钟，国内流量300MB，国内短信发送条数240条，国内接听免费（含可视电话）。套餐超出后，国内语音拨打0.20元/分钟，国内流量0.0003元/KB，国内可视电话拨打0.60元/分钟。套餐赠送多媒体内容6个M、文本内容10个T、国内可视电话拨打分钟数10分钟，以及来电显示和手机邮箱，其他执行标准资费。月套餐及叠加包内包含的业务量仅限当月使用，不能延续至下月使用。<br/>4、客户每月使用手机上网流量达到6GB后，达到封顶流量。<br/>\",\"contactAddr\":\"广东 佛山市 南海区 里水镇 恒大御景湾 7栋3301\",\"mainContentTwo\":\"基本通信服务及附加业务名称及描述：国内通话。<br/>可选业务包名称及描述：无。<br/>\",\"agentPaperType\":\"\",\"acctAddr\":\"广东 佛山市 南海区 里水镇 恒大御景湾 7栋3301\",\"userType\":\"3G普通用户\",\"paperExpr\":\"20501231\",\"acctName\":\"刘远\",\"mainContentFive\":\"\",\"agentPhone\":\"\",\"custType\":\"个人\",\"payMethod\":\"现金\",\"paperAddr\":\"辽宁省大连市中山区修竹街4号14-10\",\"paperType\":\"18位身份证\",\"staffInfo\":\"黄剑青DZZQJ057\",\"contactPhone\":\"Amanda Lim 15524654176\",\"bankAcctName\":\"\",\"bankName\":\"\",\"bankAcct\":\"\",\"paperNo\":\"230302199002174710\",\"mainContentThree\":\"\",\"bankCode\":\"\",\"agentPaperNo\":\"\",\"mainContentFour\":\"1、优惠活动名称：存费送费一年期。<br/>2、优惠活动协议期：客户承诺自2014年11月至2015年9月，在网12个月。<br/>3、优惠活动信息：预存话费送话费：一次性缴纳合约计划标准预存款为120.00元，其中首月返还金额0.00元，次月起连续11个月每月返还金额10.00元，最后一个月返还剩余部分金额；次月起连续12个月每月赠送话费20.00元。<br/>\",\"agentName\":\"\",\"userNo\":\"18575814246\",\"custName\":\"刘远\"}";
			}
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			this.warmTips = cacheUtil.getConfigInfo(EcsOrderConsts.ORDER_PRINT_WARN_TIPS);
		}else{
			this.orderPrintURL = EcsOrderConsts.NO_ORDER_PRINT_INFO_URL;
		}
		return "print_template";
	}
	
	public void outInvoicePrint() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter invoiceWrite = response.getWriter();
		try {
			response.setCharacterEncoding("GBK");
	    	response.setContentType("text/html;charset=GBK;");
			String printHtml = iOrderInvoiceManager.doPrintInvoice(order_id,"","0",request.getContextPath());
			invoiceWrite.write(printHtml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			invoiceWrite.write(e.getMessage());
		}
	}
	
	public String updatePrtStatus(){
		ordPrtManager.updateOrdItemsAptPrtBusiReq(order_id,false);
		json ="{result:1,msg:'修改成功!'}";
		return JSON_MESSAGE;
	}

	public IOrdPrtManager getOrdPrtManager() {
		return ordPrtManager;
	}

	public void setOrdPrtManager(IOrdPrtManager ordPrtManager) {
		this.ordPrtManager = ordPrtManager;
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

	public String getOut_order_id() {
		return out_order_id;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
		if (StringUtils.isNotBlank(out_order_id)) {
			String id = ordPrtManager.getOrderId(out_order_id);
			if (id != null)
				this.order_id = id;
			else
				this.order_id = "";
		}
	}
	
}
