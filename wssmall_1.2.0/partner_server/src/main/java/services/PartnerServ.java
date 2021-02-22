package services;

import java.util.List;
import java.util.Map;

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

import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.app.base.core.model.PartnerAddress;
import com.ztesoft.net.app.base.core.model.PartnerShop;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.action.desposit.IDespostHander;
import com.ztesoft.net.mall.core.service.IAdvanceLogsManager;
import com.ztesoft.net.mall.core.service.IPartnerAccountManager;
import com.ztesoft.net.mall.core.service.IPartnerAddressManager;
import com.ztesoft.net.mall.core.service.IPartnerAlbumManager;
import com.ztesoft.net.mall.core.service.IPartnerDespostLogManager;
import com.ztesoft.net.mall.core.service.IPartnerDespostManager;
import com.ztesoft.net.mall.core.service.IPartnerFreezeLogManager;
import com.ztesoft.net.mall.core.service.IPartnerManager;
import com.ztesoft.net.mall.core.service.IPartnerShopManager;
import com.ztesoft.net.mall.core.service.IPartnerStaffManager;
import com.ztesoft.net.mall.core.service.IpartnerShopMappingManager;

import consts.ConstsCore;


/**
 * 分销商信息服务实现
 * @author hu.yi
 * @date 2013.12.25
 */
public class PartnerServ extends ServiceBase implements PartnerInf{

	
	private IAdvanceLogsManager advanceLogsManager;
	private IPartnerAccountManager accountManager;
	private IPartnerAddressManager partnerAddressManger;
	private IPartnerAlbumManager partnerAlbumManager;
	private IPartnerDespostLogManager despostLogManager;
	private IPartnerDespostManager despostManager;
	private IPartnerFreezeLogManager freezeLogManager;
	private IPartnerManager partnerManager;
	private IPartnerShopManager partnerShopManager;
	private IpartnerShopMappingManager shopMappingManager;
	private IPartnerStaffManager partnerStaffManager;
	private IDespostHander despostHander;
	
	
	
	@Override
	public ShopMappingResp getUserInfoByShopCode(ShopMappingReq shopMappingReq){
		
		Map map = shopMappingManager.getUserInfoByShopCode(shopMappingReq.getShopId());
		
		ShopMappingResp shopMappingResp = new ShopMappingResp();
		shopMappingResp.setShopMap(map);
		addReturn(shopMappingReq, shopMappingResp);
		
		return shopMappingResp;
	}
	
	
	@Override
	public DespostAfterPayResp afterPay(DespostAfterPayReq despostAfterPayReq){
		
		despostHander.afterPay(despostAfterPayReq.getOrdeSeq(), despostAfterPayReq.getOrderAmount(), 
				despostAfterPayReq.getRetnCode(),despostAfterPayReq.getTranDate());
		DespostAfterPayResp despostAfterPayResp = new DespostAfterPayResp();
		addReturn(despostAfterPayReq,despostAfterPayResp);
		return despostAfterPayResp;
	}
	
	
	@Override
	public PartnerInfoResp getPartnerByCurrentUserId(PartnerInfoReq partnerInfoReq){
		Partner partner = partnerManager.getPartnerByCurrentUserId(partnerInfoReq.getUserid(), partnerInfoReq.getPartner());
		PartnerInfoResp partnerInfoResp = new PartnerInfoResp();
		partnerInfoResp.setPartner(partner);
		addReturn(partnerInfoReq,partnerInfoResp);
		
		return partnerInfoResp;
	}
	
	
	@Override
	public PartnerAddResp addPartner(PartnerAddReq partnerAddReq){
		partnerManager.add(partnerAddReq.getPartner());
		PartnerAddResp partnerAddResp = new PartnerAddResp();
		addReturn(partnerAddReq,partnerAddResp);
		return partnerAddResp;
	}
	
	
	@Override
	public PartnerUserEditResp updateUserid(PartnerUserEditReq partnerUserEditReq){
		
		partnerManager.updateUserid(partnerUserEditReq.getPartner_id(),partnerUserEditReq.getUser_id());
		PartnerUserEditResp partnerUserEditResp = new PartnerUserEditResp();
		addReturn(partnerUserEditReq,partnerUserEditResp);
		return partnerUserEditResp;
	}
	
	
	@Override
	public PartnerInfoResp getPartnerByUserId(PartnerInfoOneReq partnerInfoOneRep){
		
		Partner partner = partnerManager.getPartnerIdByUserId(partnerInfoOneRep.getUserid());
		PartnerInfoResp partnerInfoResp = new PartnerInfoResp();
		partnerInfoResp.setPartner(partner);
		addReturn(partnerInfoOneRep,partnerInfoResp);
		return partnerInfoResp;
	}
	
	
	@Override
	public DespostBusResp canPay(DespostCanPayReq despostCanPayRep){
		
		boolean flag = despostHander.canPay(despostCanPayRep.getPartnerId(),despostCanPayRep.getAmount(),
													despostCanPayRep.getFlag());
		DespostBusResp despostCanPayResp = new DespostBusResp();
		despostCanPayResp.setFlag(flag);
		addReturn(despostCanPayRep,despostCanPayResp);
		return despostCanPayResp;
	}
	
	
	@Override
	public DespostBusResp debit(DespostDebitReq despostDebitReq){
		
		boolean flag = despostHander.debit(despostDebitReq.getPartnerId(), despostDebitReq.getAmount(),
				despostDebitReq.getFlag(), despostDebitReq.getTableName(), despostDebitReq.getOrderId());
		DespostBusResp despostBusResp = new DespostBusResp();
		despostBusResp.setFlag(flag);
		addReturn(despostDebitReq,despostBusResp);
		return despostBusResp;
	}
	
	
	@Override
	public DespostBusResp charge(DespostChargeReq despostChargeReq){
		
		boolean flag = despostHander.charge(despostChargeReq.getPartnerId(), despostChargeReq.getAmount(),
				despostChargeReq.getFlag(), despostChargeReq.getTableName(), despostChargeReq.getOrderId());
		DespostBusResp despostBusResp = new DespostBusResp();
		despostBusResp.setFlag(flag);
		addReturn(despostChargeReq, despostBusResp);
		return despostBusResp;
	}
	
	
	
	@Override
	public DespostBusResp chargeBill(DespostBillReq despostBillReq){
		
		boolean flag = despostHander.chargeBill(despostBillReq.getPartnerId(),despostBillReq.getAmount(),
						despostBillReq.getFlag());
		DespostBusResp despostBusResp = new DespostBusResp();
		despostBusResp.setFlag(flag);
		addReturn(despostBillReq, despostBusResp);
		return despostBusResp;
	}
	
	
	
	@Override
	public PartnerAddrResp getPartnerAddressByAddr_id(PartnerAddrReq partnerAddrReq){
		
		PartnerAddress partnerAddress = partnerAddressManger.getPartnerAddressByAddr_id(partnerAddrReq.getAddressNum());
		PartnerAddrResp partnerAddrResp = new PartnerAddrResp();
		partnerAddrResp.setPartnerAddress(partnerAddress);
		addReturn(partnerAddrReq, partnerAddrResp);
		return partnerAddrResp;
	}
	
	
	@Override
	public PartnerAgentListResp searchPartnerAdUserList(PartnerAgentListReq partnerAgentListReq){
		
		List list = partnerManager.searchPartnerAdUserList();
		PartnerAgentListResp partnerAgentListResp = new PartnerAgentListResp();
		partnerAgentListResp.setAgentList(list);
		addReturn(partnerAgentListReq, partnerAgentListResp);
		return partnerAgentListResp;
	}
	
	
	@Override
	public PartnerShopTypeListResp searchPartneShopTypeList(PartnerShopTypeListReq partnerShopTypeListReq){
		
		List list = partnerManager.searchPartneShopTypeList();
		PartnerShopTypeListResp partnerShopTypeListResp = new PartnerShopTypeListResp();
		partnerShopTypeListResp.setShopTypeList(list);
		addReturn(partnerShopTypeListReq, partnerShopTypeListResp);
		return partnerShopTypeListResp;
	}
	
	
	
	@Override
	public PartnerShopStarListResp searchPartneShopStarList(PartnerShopStarListReq partnerShopStarListReq){
		
		List list = partnerManager.searchPartneShopStarList();
		PartnerShopStarListResp partnerShopStarListResp = new PartnerShopStarListResp();
		partnerShopStarListResp.setShopStarList(list);
		addReturn(partnerShopStarListReq, partnerShopStarListResp);
		return partnerShopStarListResp;
		
	}
	
	
	@Override
	public PartnerExistsResp isPartnerExits(PartnerExistsReq partnerExistsReq){
		
		boolean flag = partnerManager.isPartnerExits(partnerExistsReq.getPartner_name(),partnerExistsReq.getPartner_code());
		
		PartnerExistsResp partnerExistsResp = new PartnerExistsResp();
		partnerExistsResp.setFlag(flag);
		addReturn(partnerExistsReq, partnerExistsResp);
		return partnerExistsResp;
	}
	
	
	@Override
	public PartnerAdvLogsResp listAdvanceLogsByMemberId(PartnerAdvLogsReq partnerAdvLogsReq){
		List list = advanceLogsManager.listAdvanceLogsByMemberId(partnerAdvLogsReq.getMember_id());
		PartnerAdvLogsResp partnerAdvLogsResp = new PartnerAdvLogsResp();
		partnerAdvLogsResp.setAdvLogsList(list);
		addReturn(partnerAdvLogsReq, partnerAdvLogsResp);
		return partnerAdvLogsResp;
	}
	
	
	@Override
	public PartnerAdvLogsAddResp addAdvLogs(PartnerAdvLogsAddReq partnerAdvLogsAddReq){
		
		advanceLogsManager.add(partnerAdvLogsAddReq.getAdvanceLogs());
		PartnerAdvLogsAddResp partnerAdvLogsAddResp = new PartnerAdvLogsAddResp();
		addReturn(partnerAdvLogsAddReq, partnerAdvLogsAddResp);
		return partnerAdvLogsAddResp;
	}
	
	
	
	@Override
	public PartnerAddrListResp getPartnerAddress(PartnerAddrListReq partnerAddrListReq){
		List<PartnerAddress> list = partnerAddressManger.getPartnerAddress(partnerAddrListReq.getPartner_id());
		
		PartnerAddrListResp partnerAddrListResp = new PartnerAddrListResp();
		partnerAddrListResp.setList(list);
		addReturn(partnerAddrListReq, partnerAddrListResp);
		return partnerAddrListResp;
	}
	
	
	
	@Override
	public PartnerPageResp searchPartner(PartnerPageReq partnerPageReq){
		Page page = partnerManager.searchPartner(partnerPageReq.getMap(),partnerPageReq.getPage(),
													partnerPageReq.getPageSize());
		PartnerPageResp pagePartnerPageResp = new PartnerPageResp();
		pagePartnerPageResp.setWebPage(page);
		addReturn(partnerPageReq, pagePartnerPageResp);
		return pagePartnerPageResp;
	}
	
	@Override
	public PartnerWdAddResp addPartnerWd(PartnerWdAddReq partnerWdAddReq) {
		PartnerWdAddResp partnerWdAddResp = new PartnerWdAddResp(); 
		partnerWdAddResp = this.partnerManager.addParterWd(partnerWdAddReq);
		addReturn(partnerWdAddReq, partnerWdAddResp);
		return partnerWdAddResp;
	}
	@Override
	public PartnerWdEditResp editPartnerWd(PartnerWdEditReq req) {
		PartnerWdEditResp resp = this.partnerShopManager.editPartnerWd(req);
		addReturn(req, resp);
		return resp;
	}
	@Override
	public PartnerByIdResp getPartnerById(PartnerByIdReq req) {
		PartnerByIdResp resp = new PartnerByIdResp(); 
		try{
		Partner partner = this.partnerManager.getPartner(req.getPartnerId());
		PartnerShop partnerShop = this.partnerShopManager.getPartnerShop(req.getPartnerId());
	    PartnerAccount primaryAccount = this.accountManager.getprimaryAccount(req.getPartnerId()); 
	    PartnerAccount secondaryAccount = this.accountManager.getSecondaryAccount(req.getPartnerId());
		resp.setPartner(partner);
		resp.setPartnerShop(partnerShop);
		resp.setPrimaryAccount(primaryAccount);
		resp.setSecondaryAccount(secondaryAccount);
		resp.setError_code(ConstsCore.ERROR_SUCC);
		}catch(Exception e){
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("操作失败："+e.getMessage());
		}
		addReturn(req, resp);
		return resp;
	}
	@Override
	public PartnerShopListResp getPartnerShopPage(PartnerShopListReq req) {
		PartnerShopListResp resp = new PartnerShopListResp(); 
		try{
			resp = this.partnerShopManager.getPartnerShopPage(req);
			resp.setError_code(ConstsCore.ERROR_SUCC);
		}catch(Exception e){
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("操作失败："+e.getMessage());
		}
			addReturn(req, resp);
		    return resp;
	}
	public IAdvanceLogsManager getAdvanceLogsManager() {
		return advanceLogsManager;
	}
	public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
		this.advanceLogsManager = advanceLogsManager;
	}
	public IPartnerAccountManager getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(IPartnerAccountManager accountManager) {
		this.accountManager = accountManager;
	}
	public IPartnerAddressManager getPartnerAddressManger() {
		return partnerAddressManger;
	}
	public void setPartnerAddressManger(IPartnerAddressManager partnerAddressManger) {
		this.partnerAddressManger = partnerAddressManger;
	}
	public IPartnerAlbumManager getPartnerAlbumManager() {
		return partnerAlbumManager;
	}
	public void setPartnerAlbumManager(IPartnerAlbumManager partnerAlbumManager) {
		this.partnerAlbumManager = partnerAlbumManager;
	}
	public IPartnerDespostLogManager getDespostLogManager() {
		return despostLogManager;
	}
	public void setDespostLogManager(IPartnerDespostLogManager despostLogManager) {
		this.despostLogManager = despostLogManager;
	}
	public IPartnerDespostManager getDespostManager() {
		return despostManager;
	}
	public void setDespostManager(IPartnerDespostManager despostManager) {
		this.despostManager = despostManager;
	}
	public IPartnerFreezeLogManager getFreezeLogManager() {
		return freezeLogManager;
	}
	public void setFreezeLogManager(IPartnerFreezeLogManager freezeLogManager) {
		this.freezeLogManager = freezeLogManager;
	}
	public IPartnerManager getPartnerManager() {
		return partnerManager;
	}
	public void setPartnerManager(IPartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}
	public IPartnerShopManager getPartnerShopManager() {
		return partnerShopManager;
	}
	public void setPartnerShopManager(IPartnerShopManager partnerShopManager) {
		this.partnerShopManager = partnerShopManager;
	}
	public IpartnerShopMappingManager getShopMappingManager() {
		return shopMappingManager;
	}
	public void setShopMappingManager(IpartnerShopMappingManager shopMappingManager) {
		this.shopMappingManager = shopMappingManager;
	}
	public IPartnerStaffManager getPartnerStaffManager() {
		return partnerStaffManager;
	}
	public void setPartnerStaffManager(IPartnerStaffManager partnerStaffManager) {
		this.partnerStaffManager = partnerStaffManager;
	}
	public IDespostHander getDespostHander() {
		return despostHander;
	}
	public void setDespostHander(IDespostHander despostHander) {
		this.despostHander = despostHander;
	}



	
}
