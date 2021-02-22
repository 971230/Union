/**
 * 
 */
package com.zte.cbss.autoprocess;

import zte.net.ecsord.params.bss.req.MobileCBssOutReq;
import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;
import zte.net.ecsord.params.bss.req.SPBuyCBssOutReq;
import zte.net.ecsord.params.bss.req.SPBuyServiceHandleReq;

import com.zte.cbss.autoprocess.model.CustomBill;
import com.zte.cbss.autoprocess.model.CustomInfo;
import com.zte.cbss.autoprocess.model.LoginInfo;
import com.ztesoft.inf.communication.client.AbsCbssInvokerOut;
import com.ztesoft.inf.framework.commons.CodedException;

/**
 * @author ZX
 * AbsCbssInvokerOutImpl.java
 * 2015-2-2
 */
public class AbsCbssInvokerOutImpl implements AbsCbssInvokerOut {
	
	@Override
	public void spBuyBusiness(SPBuyCBssOutReq req) throws CodedException, Exception {
		HttpLoginClient client = toLogin(req.getLoginPsw(), req.getLoginUser(), req.getProvinceCode());
		CustomInfo customInfo = toCustCert(client, req.getPsptId(),
				req.getCustomName(),
				req.getContact(),
				req.getContactPhone(),
				req.getPsptEndDate(),
				req.getIdTypeCode(),
				req.getPhone(),
				req.getPostAddress(),
				req.getSerialNumber());
		SPBuyServiceHandleCbssInvoker invoker = new SPBuyServiceHandleCbssInvoker();
		SPBuyServiceHandleReq spBuyReq = new SPBuyServiceHandleReq();
		spBuyReq.setMobileNo(req.getSerialNumber());
		spBuyReq.setTake_effect_type(req.getTake_effect_type());
		client.getParam().setOrder_create_time(req.getOrder_time());
		invoker.businessProcess(spBuyReq, client, customInfo);
	}
	
	@Override
	public void mobileBusiness(MobileCBssOutReq req) throws CodedException, Exception {
		HttpLoginClient client = toLogin(req.getLoginPsw(), req.getLoginUser(), req.getProvinceCode());
		CustomInfo customInfo = toCustCert(client, req.getPsptId(),
				req.getCustomName(),
				req.getContact(),
				req.getContactPhone(),
				req.getPsptEndDate(),
				req.getIdTypeCode(),
				req.getPhone(),
				req.getPostAddress(),
				req.getSerialNumber());
		FourGIdlesseTrafficPackCbssInvoker invoker = new FourGIdlesseTrafficPackCbssInvoker();
		MobileNetworkServiceHandleReq mobileReq = new MobileNetworkServiceHandleReq();
		mobileReq.setMobileNo(req.getSerialNumber());
		mobileReq.setPackFlag(req.getPackFlag());
		client.getParam().setOrder_create_time(req.getOrder_time());
		invoker.businessProcess(mobileReq, client, customInfo);
	}
	
	/**
	 * 登录CBSS
	 * @param req
	 * @return
	 * @throws CodedException
	 * @throws Exception
	 */
	private HttpLoginClient toLogin(String loginPsw, String loginUser, String provinceCode) 
			throws CodedException, Exception {
		try {
			LoginInfo info = new LoginInfo();
			info.setCbssPassword(loginPsw);
			info.setCbssAccount(loginUser);
			info.setProvinceCode(provinceCode);
			HttpLoginClient client = HttpLoginClientPool.add(info);
			return client;
		} catch (Exception e) {
			throw new CodedException("8001", "用户登录失败!");
		}
	}
	
	/**
	 * 客户认证
	 * @param client
	 * @param req
	 * @return
	 * @throws CodedException
	 * @throws Exception
	 */
	private CustomInfo toCustCert(HttpLoginClient client, 
			String psptId,
			String customName,
			String contact,
			String contactPhone,
			String psptEndDate,
			String idTypeCode,
			String phone,
			String postAddress,
			String serialNumber) throws CodedException, Exception {
		if (null == client)
			return null;
		try {
			CustomBill bill = new CustomBill();
			bill.setPsptId(psptId);
			bill.setCustomName(customName);
			bill.setContact(contact);
			bill.setContactPhone(contactPhone);
			bill.setPsptEndDate(psptEndDate);
			bill.setIdTypeCode(idTypeCode);
			bill.setPhone(phone);
			bill.setPostAddress(postAddress); // 通信地址必须大于八个字符
			bill.setSerialNumber(serialNumber); // 设置要办理业务号码
			CustomInfo customInfo = CreateCustomHandler.emulate(bill, client);
			return customInfo;
		} catch (Exception e) {
			throw new CodedException("8002", "客户认证失败!");
		}
	}
	
}
