package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import params.ZteError;
import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserResp;
import params.onlinepay.req.OnlinePayCallbackReq;
import params.onlinepay.req.OnlinePayRedirectReq;
import params.onlinepay.req.OnlinePayReq;
import params.onlinepay.resp.OnlinePayCallbackResp;
import params.onlinepay.resp.OnlinePayRedirectResp;
import params.onlinepay.resp.OnlinePayResp;
import params.req.PartnerByIdReq;
import params.resp.PartnerByIdResp;
import zte.net.iservice.IPartnerService;

import com.alibaba.fastjson.JSON;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.service.IBankManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IPaymentListManager;
import com.ztesoft.net.mall.core.service.IPaymentManager;
import com.ztesoft.net.model.PayResult;
import com.ztesoft.net.model.PaymentAccount;
import com.ztesoft.net.model.PaymentCfgAttr;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 在经支付
 * @作者 MoChunrun
 * @创建日期 2013-12-23 
 * @版本 V 1.0
 */
public class OnlinePaymentServ extends ServiceBase implements OnlinePaymentInf {
	
	protected IPaymentManager paymentManager;
	protected IBankManager bankManager;
	protected IPaymentListManager paymentListManager;
	private IOrderManager orderManager;
	private IPartnerService partnerService;
	@Resource
	private AdminUserInf adminUserServ;
	
	private void init(){
		if(null == orderManager) orderManager = ApiContextHolder.getBean("orderManager");
		if(null == partnerService) partnerService = ApiContextHolder.getBean("partnerService");
	}
	
	public void insertPayment(PaymentList paymentList){
		this.baseDaoSupport.insert("payment_list", paymentList);
	}
	
	public Bank getBank(String bank_id){
		return bankManager.getBankByBankId(bank_id);
	}

	/**
	 * 解析属性
	 * @作者 MoChunrun
	 * @创建日期 2013-12-2 
	 * @param json
	 * @return
	 */
	public List<PaymentCfgAttr> parseCfgAttr(String json){
		if(json!=null){
			List<PaymentCfgAttr> list = JSON.parseArray(json,PaymentCfgAttr.class);
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 查询支付账户
	 * @作者 MoChunrun
	 * @创建日期 2013-12-2 
	 * @param cfg_id
	 * @param user_id
	 * @return
	 */
	public PaymentAccount qryPaymentAccount(String cfg_id,String user_id){
		String sql = "select t.* from es_payment_account t where t.cfg_id=? and t.owner_userid=?";
		List<PaymentAccount> list = this.baseDaoSupport.queryForList(sql, PaymentAccount.class, cfg_id,user_id);
		if(list.size()>0){
			return list.get(0);
		}else if(!"-1".equals(user_id)){
			return qryPaymentAccount(cfg_id,"-1");
		}
		return null;
	}
	
	/**
	 * 查询配置ID
	 * @作者 MoChunrun
	 * @创建日期 2013-12-2 
	 * @param config_id
	 * @return
	 */
	public PayCfg getPayConfig(String config_id){
		return paymentManager.getPayConfig(config_id);
	}
	
	@Override
	public OnlinePayResp goToPay(OnlinePayReq req) {
		//初始化beans
		init();
		
		OnlinePayResp resp = new OnlinePayResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		PayCfg cfg = getPayConfig(req.getPayment_cfg_id());
		if(cfg==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"支付方式有误"));
		Bank bank = getBank(req.getBank_id());
		if("0".equals(cfg.getOnline_flag()) && bank==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"银行ID有误"));
		PaymentAccount account = qryPaymentAccount(req.getPayment_cfg_id(), req.getStaff_user_id());
		if("0".equals(cfg.getOnline_flag()) && account==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"商家管理员ID有误"));
		List<PaymentCfgAttr> attrs = parseCfgAttr(cfg.getCfg_prop());
		//if(attrs==null || attrs.size()==0)CommonTools.addError(new ErrorEntity(ConstsCore.ERROR_FAIL,"支付方式配置有误"));
		
		Order order = orderManager.get(req.getOrder_id());
		Double pay_amout = order.getOrder_amount()-order.getPaymoney()-order.getDiscount();//订单支付金额=订单金额-已支付金额-折扣金额 （单位：元）
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(order.getUserid());
		AdminUserResp adminUserResp = this.adminUserServ.getAdminUserById(adminUserReq);
		
		PartnerByIdReq partnerByIdReq = new PartnerByIdReq();
		partnerByIdReq.setPartnerId(adminUserResp.getAdminUser().getRelcode());
		PartnerByIdResp partnerByIdResp = this.partnerService.getPartnerById(partnerByIdReq);
		PartnerAccount paccount = partnerByIdResp.getPrimaryAccount();//企业主账户
		PartnerAccount saccount = partnerByIdResp.getSecondaryAccount();//企业分账户
		
		
		
		PaymentList payment = new PaymentList();
		payment.setBean_name(req.getBean_name());
		payment.setSequ(0);
		payment.setState("1");
		payment.setPayment_cfg_id(cfg.getId());
//		payment.setPay_amount(StringUtil.transMoneyD(req.getPay_money()));
		payment.setPay_amount(StringUtil.transMoneyD(pay_amout));//es_payment_list （单位：分）
		//订单处理状态: 11:已提交银行，未收到银行支付结果
		//			   12:可疑交易,1:银行支付不成功
		//			   2:支付成功，待送计费销帐 3:支付成功，计费销帐失败
		//			   4:支付成功，计费销帐成功 A:支付成功，不需后续处理
		payment.setDeal_flag("11");
		if("1".equals(cfg.getOnline_flag())){
			payment.setDeal_flag("2");
		}
		payment.setPay_date(DBTUtil.current());
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		payment.setTransdate(df.format(new Date()));
		payment.setBank_id(req.getBank_id());
		payment.setOper_time(DBTUtil.current());
		payment.setPay_ip(req.getClient_ip());
		payment.setSession_id(req.getUserSessionId());
		payment.setOrder_id(req.getOrder_id());
		payment.setType_code(req.getType_code());
		if("0".equals(cfg.getOnline_flag()))
			payment.setUserid(account.getOwner_userid());//支付到那个账户的管理员ID
		/**
		 * 0订单ID 1批量ID
		 */
		payment.setPaytype(1);
		payment.setCreate_date(DBTUtil.current());

		this.insertPayment(payment);
		resp.setTransaction_id(payment.getTransaction_id());
		
		if("1".equals(cfg.getOnline_flag())){
			resp.setOnline_flag("1");
			return resp;
		}else{
			resp.setOnline_flag("0");
		}
		
		Map<String,String> resultMap = new HashMap<String,String>();
		for(PaymentCfgAttr pc:attrs){
			resultMap.put(pc.getEname(), pc.getValue());
		}
		/*if(req.getExtParams()!=null && req.getExtParams().size()>0){
			Iterator it = req.getExtParams().keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				resultMap.put(key, req.getExtParams().get(key));
			}
		}*/
		
		IOnlinePayParams olinePay = SpringContextHolder.getBean(cfg.getType());
		olinePay.packPayParams(cfg, bank, account, attrs, payment, req, resultMap);
		resp.setResultMap(resultMap);
		resp.setBank_url(cfg.getBank_adss());
		addReturn(req,resp);
		return resp;
	}

	@Override
	public OnlinePayCallbackResp payCallback(OnlinePayCallbackReq req) {
		OnlinePayCallbackResp resp = new OnlinePayCallbackResp();
		PaymentList payment = this.paymentListManager.getPaymentById(req.getTransaction_id());
		PaymentAccount account = qryPaymentAccount(payment.getPayment_cfg_id(), payment.getUserid());
		PayCfg cfg = getPayConfig(payment.getPayment_cfg_id());
		IOnlinePayParams olinePay = SpringContextHolder.getBean(cfg.getType());
		PayResult result = olinePay.checkCallBack(cfg, account, payment, req);
		resp.setPayResult(result);
		if(result.getDealResult()!=3)
			paymentListManager.updatePayment(payment.getTransaction_id(), result.getDeal_flag());
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setRespMsg(result.getResultMsg());
		addReturn(req,resp);
		return resp;
	}


	@Override
	public OnlinePayRedirectResp payRedirect(OnlinePayRedirectReq req) {
		OnlinePayRedirectResp resp = new OnlinePayRedirectResp();
		PaymentList payment = this.paymentListManager.getPaymentById(req.getTransaction_id());
		PaymentAccount account = qryPaymentAccount(payment.getPayment_cfg_id(), payment.getUserid());
		PayCfg cfg = getPayConfig(payment.getPayment_cfg_id());
		IOnlinePayParams olinePay = SpringContextHolder.getBean(cfg.getType());
		PayResult result = olinePay.checkRedirect(cfg, account, payment, req);
		resp.setResult(result);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req,resp);
		return resp;
	}

	
	public IPaymentManager getPaymentManager() {
		return paymentManager;
	}

	public void setPaymentManager(IPaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}

	public IBankManager getBankManager() {
		return bankManager;
	}

	public void setBankManager(IBankManager bankManager) {
		this.bankManager = bankManager;
	}

	public IPaymentListManager getPaymentListManager() {
		return paymentListManager;
	}

	public void setPaymentListManager(IPaymentListManager paymentListManager) {
		this.paymentListManager = paymentListManager;
	}
	
}
