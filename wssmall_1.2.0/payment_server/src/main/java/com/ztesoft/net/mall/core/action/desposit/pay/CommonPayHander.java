package com.ztesoft.net.mall.core.action.desposit.pay;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.bank.req.BankListReq;
import params.bank.req.BankReq;
import params.payment.req.AddPaymentListReq;
import params.payment.req.EditPaymentListReq;
import params.payment.req.GetPaymentListReq;
import services.BankInf;
import services.PaymentListInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.PayReponse;
import com.ztesoft.net.mall.core.model.PayRequest;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.core.utils.PayUtils;
import commons.CommonTools;

public class CommonPayHander extends BaseSupport implements ICommonPayHander {

    protected BankInf bankServ;
    protected PaymentListInf paymentListServ;
    protected ICacheUtil cacheUtil;

    /**
     * (non-Javadoc)
     *
     * @function 获取银行列表
     */
    @Override
	public List getBankList() {
        try {
            BankListReq req = new BankListReq();
            return bankServ.qryBankList(req).getBankList();//bankManager.getBankList();/
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @function 获取银行信息
     */
    @Override
	public Bank getBankByCode(String bankCode) {
        try {
            BankReq req = new BankReq();
            req.setBank_code(bankCode);
            return bankServ.getBankByCode(req).getBank();//this.bankManager.getBankByCode(bankCode);//
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @function 获取银行信息
     */
    @Override
	public Bank getBankById(String bankId) {
        try {

            BankReq req = new BankReq();
            req.setBank_id(bankId);
            return bankServ.getBankByCode(req).getBank();
            //return this.bankManager.getBankByBankId(bankId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @function 订单、支付流水、银行参数封装
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    @SuppressWarnings("unchecked")
    public PayReponse bankPay(PayRequest payRequest) {
        Map result = new HashedMap();
        PayReponse payReponse = new PayReponse();
        try {

            Double amount = payRequest.getAmount();
            String bankCode = payRequest.getBankCode();
            String paySource = payRequest.getPaySource();
            String service_type = payRequest.getService_type();
            String order_id = payRequest.getOrder_id();
            String typeCode = payRequest.getType_code();

            //写入支付流水
            String strIP = StringUtil.getIpAddr();
            PaymentList payment = new PaymentList();
            payment.setSequ(0);
            payment.setState("1");
            String testMoney = getCfInfo("TEST_MONEY");
            if (null != testMoney && !"".equals(testMoney)) {
                payment.setPay_amount(Integer.parseInt(testMoney));    //便于测试
            } else {
                payment.setPay_amount(StringUtil.transMoneyD(amount));
            }


            //订单处理状态: 11:已提交银行，未收到银行支付结果
            //			   12:可疑交易,1:银行支付不成功
            //			   2:支付成功，待送计费销帐 3:支付成功，计费销帐失败
            //			   4:支付成功，计费销帐成功 A:支付成功，不需后续处理


            payment.setDeal_flag("11");

            //IUserService userService = UserServiceFactory.getUserService();
            //Member member = userService.getCurrentMember();
            //if(member!=null)
            //	payment.setUserid(member.getMember_id());
            //if(member==null){
            AdminUser admin = ManagerUtils.getAdminUser();
            if (admin != null)
                payment.setUserid(admin.getUserid());        //存储分销商
            //}
            payment.setPay_source(paySource);
            payment.setPay_date(DBTUtil.current());
            payment.setTransdate(this.getTransDate().substring(0, 8));
            payment.setBank_id(bankCode);
            payment.setOper_time(DBTUtil.current());
            payment.setPay_ip(strIP);
            payment.setSession_id(CommonTools.getUserSessionId());
            payment.setOrder_id(order_id);
            payment.setService_type(service_type);
            payment.setType_code(typeCode);
            /**
             * 0订单ID 1批量ID
             */
            payment.setPaytype(payRequest.getPayType());
            AddPaymentListReq req = new AddPaymentListReq();
            req.setPaymentList(payment);
            paymentListServ.addPaymentList(req);
            //this.paymentListManager.insertPayment(payment);


            //生成MAC校验域

            String strMac = "MERCHANTID=" + getCfInfo("MERCHANTID") + "&ORDERSEQ="
                    + payment.getTransaction_id() + "&ORDERDATE=" + payment.getTransdate()
                    + "&ORDERAMOUNT=" + payment.getPay_amount() + "&CLIENTIP=" + strIP + "&KEY=" + getCfInfo("KEY");

            try {
                logger.info(strMac);
                strMac = PayUtils.md5Digest(strMac);
                logger.info(strMac);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            result.put("MERCHANTID", getCfInfo("MERCHANTID"));
            result.put("SUBMERCHANTID", "d_pay");
            result.put("ORDERSEQ", payment.getTransaction_id());
            result.put("ORDERREQTRANSEQ", payment.getOrder_id());
            result.put("ORDERDATE", payment.getTransdate());
            result.put("ORDERAMOUNT", payment.getPay_amount() + "");
            result.put("PRODUCTAMOUNT", payment.getPay_amount() + "");
            result.put("ATTACHAMOUNT", 0 + "");
            result.put("CURTYPE", "RMB");
            result.put("ENCODETYPE", "1");
            result.put("MERCHANTURL", getCfInfo("MERCHANTURL"));
            result.put("BACKMERCHANTURL", getCfInfo("BACKMERCHANTURL"));
            result.put("BANKID", bankCode);
            result.put("ATTACH", "");
            result.put("BUSICODE", "0001");
            result.put("PRODUCTID", "01");
            result.put("TMNUM", "18082713555");
            result.put("CUSTOMERID", "HN_AGENT");
            result.put("PRODUCTDESC", "agent_charge");
            result.put("MAC", strMac);
            result.put("CLIENTIP", strIP);
            result.put("bankURL", getCfInfo("bankURL"));
            payReponse.setResult(result);
            payReponse.setTransaction_id(payment.getTransaction_id());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("提交银行信息组装错误");
        }
        return payReponse;
    }

    /**
     * (non-Javadoc)
     *
     * @function 获取支付流水
     */
    @Override
	public PaymentList getPaymentById(String transactionId) {
        try {
            GetPaymentListReq req = new GetPaymentListReq();
            req.setTransactionid(transactionId);
            return paymentListServ.getPaymentListById(req).getPaymentList();
            //return this.paymentListManager.getPaymentById(transactionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * (non-Javadoc)
     *
     * @function 更新支付流水
     */
    @Override
	public boolean updatePayment(String transactionId, String dealFlag) {
        EditPaymentListReq req = new EditPaymentListReq();
        req.setTransactionId(transactionId);
        req.setDealFlag(dealFlag);
        paymentListServ.editPaymentList(req);
        //this.paymentListManager.updatePayment(transactionId, dealFlag);
        return true;
    }

    @Override
	public boolean updatePayment(PaymentList paymentList) {
        EditPaymentListReq req = new EditPaymentListReq();
        req.setTransactionId(paymentList.getTransaction_id());
        req.setPaymentList(paymentList);
        paymentListServ.editPaymentList(req);
        //this.paymentListManager.edit(paymentList);
        return true;
    }

    /**
     * (non-Javadoc)
     *
     * @function 获取配置信息
     */
    @Override
	public String getCfInfo(String cfId) {

        return cacheUtil.getConfigInfo(cfId);
    }

    public ICacheUtil getCacheUtil() {
        return cacheUtil;
    }

    public void setCacheUtil(ICacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

	/*public IBankManager getBankManager() {
        return bankManager;
	}

	public void setBankManager(IBankManager bankManager) {
		this.bankManager = bankManager;
	}*/


    public String getTransDate() {
        String sql = "select to_char(" + DBTUtil.current() + ",'yyyymmddhh24miss')tansdate from dual";
        return this.daoSupport.queryForString(sql);
    }

    @Override
    public void payNotify() {


    }

    public BankInf getBankServ() {
        return bankServ;
    }

    public void setBankServ(BankInf bankServ) {
        this.bankServ = bankServ;
    }

    public PaymentListInf getPaymentListServ() {
        return paymentListServ;
    }

    public void setPaymentListServ(PaymentListInf paymentListServ) {
        this.paymentListServ = paymentListServ;
    }


}
