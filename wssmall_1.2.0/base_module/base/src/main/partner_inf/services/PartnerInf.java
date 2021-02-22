package services;

import params.req.DespostAfterPayReq;
import params.req.DespostBillReq;
import params.req.DespostCanPayReq;
import params.req.DespostChargeReq;
import params.req.DespostDebitReq;
import params.req.PartnerAddReq;
import params.req.PartnerAddrListReq;
import params.req.PartnerAddrReq;
import params.req.PartnerAdvLogsAddReq;
import params.req.PartnerAdvLogsReq;
import params.req.PartnerAgentListReq;
import params.req.PartnerByIdReq;
import params.req.PartnerExistsReq;
import params.req.PartnerInfoOneReq;
import params.req.PartnerInfoReq;
import params.req.PartnerPageReq;
import params.req.PartnerShopListReq;
import params.req.PartnerShopStarListReq;
import params.req.PartnerShopTypeListReq;
import params.req.PartnerUserEditReq;
import params.req.PartnerWdAddReq;
import params.req.PartnerWdEditReq;
import params.req.ShopMappingReq;
import params.resp.DespostAfterPayResp;
import params.resp.DespostBusResp;
import params.resp.PartnerAddResp;
import params.resp.PartnerAddrListResp;
import params.resp.PartnerAddrResp;
import params.resp.PartnerAdvLogsAddResp;
import params.resp.PartnerAdvLogsResp;
import params.resp.PartnerAgentListResp;
import params.resp.PartnerByIdResp;
import params.resp.PartnerExistsResp;
import params.resp.PartnerInfoResp;
import params.resp.PartnerPageResp;
import params.resp.PartnerShopListResp;
import params.resp.PartnerShopStarListResp;
import params.resp.PartnerShopTypeListResp;
import params.resp.PartnerUserEditResp;
import params.resp.PartnerWdAddResp;
import params.resp.PartnerWdEditResp;
import params.resp.ShopMappingResp;

/**
 * 分销商信息服务入口
 * @author hu.yi
 * @date 2013.12.25
 */
public interface PartnerInf {

	/**
	 * 查询商店关联用户信息
	 * @param shopMappingReq
	 * @return
	 */
	public ShopMappingResp getUserInfoByShopCode(ShopMappingReq shopMappingReq);
	
	
	/**
	 * 预存金充值后续操作
	 * @param despostAfterPayReq
	 */
	public DespostAfterPayResp afterPay(DespostAfterPayReq despostAfterPayReq);
	
	
	/**
	 * 根据id查询分销商信息
	 * @param partnerInfoReq
	 * @return
	 */
	public PartnerInfoResp getPartnerByCurrentUserId(PartnerInfoReq partnerInfoReq);
	
	
	/**
	 * 添加分销商
	 * @param partnerAddReq
	 */
	public PartnerAddResp addPartner(PartnerAddReq partnerAddReq);
	
	
	/**
	 * 修改分销商关联用户信息
	 * @param partnerUserEditReq
	 */
	public PartnerUserEditResp updateUserid(PartnerUserEditReq partnerUserEditReq);
	
	
	/**
	 * 根据id查询分销商信息
	 * @param partnerInfoOneReq
	 * @return
	 */
	public PartnerInfoResp getPartnerByUserId(PartnerInfoOneReq partnerInfoOneReq);
	
	
	/**
	 * 分销商付款金额判断
	 * @param despostCanPayReq
	 * @return
	 */
	public DespostBusResp canPay(DespostCanPayReq despostCanPayReq);
	
	
	/**
	 * 预存金支付扣款
	 * @param despostDebitReq
	 * @return
	 */
	public DespostBusResp debit(DespostDebitReq despostDebitReq);
	
	
	/**
	 * 预存金充值
	 * @param despostChargeReq
	 * @return
	 */
	public DespostBusResp charge(DespostChargeReq despostChargeReq);
	
	
	/**
	 * 预存金支付扣款
	 * @param despostBillReq
	 * @return
	 */
	public DespostBusResp chargeBill(DespostBillReq despostBillReq);
	
	
	/**
	 * 查询分销商地址
	 * @param partnerAddrReq
	 * @return
	 */
	public PartnerAddrResp getPartnerAddressByAddr_id(PartnerAddrReq partnerAddrReq);
	
	
	/**
	 * 查询分销商下拉列表
	 * @param baseEmpInParams
	 * @return
	 */
	public PartnerAgentListResp searchPartnerAdUserList(PartnerAgentListReq partnerAgentListReq);
	
	
	/**
	 * 查询分销商店铺类型
	 * @param baseEmpInParams
	 * @return
	 */
	public PartnerShopTypeListResp searchPartneShopTypeList(PartnerShopTypeListReq partnerShopTypeListReq);
	
	
	
	/**
	 * 查询分销商网店量级
	 * @param baseEmpInParams
	 * @return
	 */
	public PartnerShopStarListResp searchPartneShopStarList(PartnerShopStarListReq partnerShopStarListReq);
	
	
	
	/**
	 * 判断分销商是否存在
	 * @param partnerExistsReq
	 * @return
	 */
	public PartnerExistsResp isPartnerExits(PartnerExistsReq partnerExistsReq);
	
	
	/**
	 * 查询预存款日志
	 * @param partnerAdvLogsReq
	 * @return
	 */
	public PartnerAdvLogsResp listAdvanceLogsByMemberId(PartnerAdvLogsReq partnerAdvLogsReq);
	
	
	/**
	 * 添加预存款日志
	 * @param partnerAdvLogsAddReq
	 */
	public PartnerAdvLogsAddResp addAdvLogs(PartnerAdvLogsAddReq partnerAdvLogsAddReq);
	
	
	/**
	 * 查询分销商店铺地址列表
	 * @param partnerAddrListReq
	 * @return
	 */
	public PartnerAddrListResp getPartnerAddress(PartnerAddrListReq partnerAddrListReq);
	
	
	/**
	 * 查询分销商分页对象
	 * @param pageReq
	 * @return
	 */
	public PartnerPageResp searchPartner(PartnerPageReq partnerPageReq);
	/**
	 * 微店分销商帐号添加
	 * @param partnerWdAddReq
	 * @return
	 */
	public PartnerWdAddResp addPartnerWd(PartnerWdAddReq partnerWdAddReq);
	/**
	 * 微点分销商信息店铺修改
	 * @param req
	 * @return
	 */
	public PartnerWdEditResp editPartnerWd(PartnerWdEditReq req);
	/**
	 * 根据分销商Id获取分销商信息
	 * @param req
	 * @return
	 */
	public PartnerByIdResp getPartnerById(PartnerByIdReq  req);
	/***
	 * 获取分销商店铺列表
	 * @param req
	 * @return
	 */
	public PartnerShopListResp getPartnerShopPage(PartnerShopListReq req);
}
