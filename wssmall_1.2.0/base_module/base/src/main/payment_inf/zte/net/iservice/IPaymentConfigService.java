package zte.net.iservice;

import zte.params.cfg.req.PaymentBankListReq;
import zte.params.cfg.req.PaymentConfigListReq;
import zte.params.cfg.resp.PaymentBankListResp;
import zte.params.cfg.resp.PaymentConfigListResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

/**
 * 支付配置管理
 * @作者 MoChunrun
 * @创建日期 2014-1-10 
 * @版本 V 1.0
 */
@ZteSoftCommentAnnotation(type="class",desc="支付API",summary="支付API包括有[查询支付方式列表、支付方式银联列表查询]") 
public interface IPaymentConfigService {

	/**
	 * 查询支付方式列表
	 * @作者 MoChunrun
	 * @创建日期 2014-1-10 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询支付方式列表",summary="查询支付方式列表") 	
	public PaymentConfigListResp listPaymentCfgList(PaymentConfigListReq req);
	
	/**
	 * 支付方式银联列表查询
	 * @作者 MoChunrun
	 * @创建日期 2014-1-13 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询银行列表",summary="查询支付方式相关的银行列表")
	public PaymentBankListResp listPaymentBankList(PaymentBankListReq req);
	
}
