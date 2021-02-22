package com.ztesoft.inf.communication.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.zte.cbss.autoprocess.CreateCustomHandler;
import com.zte.cbss.autoprocess.HttpLoginClient;
import com.zte.cbss.autoprocess.HttpLoginClientPool;
import com.zte.cbss.autoprocess.model.CustomBill;
import com.zte.cbss.autoprocess.model.CustomInfo;
import com.zte.cbss.autoprocess.model.LoginInfo;
import com.ztesoft.inf.framework.commons.CodedException;

/**
 * cbss业务办理抽象类
 */
public abstract class AbsCbssInvoker<T extends ZteRequest<?>, R extends ZteBusiResponse> {
	private static Logger logger = Logger.getLogger(AbsCbssInvoker.class);
	/**
	 * 接口请求对象
	 */
	protected T zteRequest;
	/**
	 * 用戶登录信息
	 */
	protected LoginInfo loginInfo;

	/**
	 * 获取HttpLoginClient(order tree 取数据)
	 * 
	 */
	private HttpLoginClient login(String order_id) throws CodedException, Exception {
		try {
			OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
			/**
			 * select a.*,a.rowid from es_dc_public_dict_relation a ;
			   select a.region_id value,a.local_name value_desc from es_regions a where a.p_region_id='440000';
			 */
			List list = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.CBSS_LOGIN_USER_INFO);
			if (list == null || list.size() <=0) {
				logger.info("====》【请检查es_dc_public_ext表是否有配置登录信息！stype='cbss_login_user_info']】");
				return null;
			}
			// 订单归属地市
			String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
			Map map = new HashMap();
			for (Object obj : list) {
				map = (Map) obj;
				// CODEB对应订单归属地市
				if (map.get("CODEB") != null 
						&& (map.get("CODEB").toString()).equals(order_city_code)) {
					break;
				}
			}
			LoginInfo info = new LoginInfo();
			info.setCbssAccount(map.get("PKEY")!=null?map.get("PKEY").toString():"");
			info.setCbssPassword(map.get("PNAME")!=null?map.get("PNAME").toString():"");
			info.setProvinceCode(map.get("CODEA")!=null?map.get("CODEA").toString():"");
			//
			HttpLoginClient client = HttpLoginClientPool.add(info);
			return client;
		} catch (Exception e) {
			throw new CodedException("8001", "用户登录失败!");
		}
	}

	/**
	 * 认证客户(order tree 取数据)
	 */
	private CustomInfo getCustomInfo(HttpLoginClient client) throws CodedException, Exception {
		if (null == client)
			return null;
		String order_id =client.getParam().getOtree_order_id();
//		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		String acc_nbr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);//CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
		try {
			CustomBill bill = new CustomBill();
			bill.setPsptId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
			bill.setCustomName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
			bill.setContact(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
			bill.setContactPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
			bill.setPsptEndDate("2018-05-08");
			bill.setIdTypeCode("2");
			bill.setPhone(acc_nbr);
			bill.setPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFERENCE_PHONE));
			bill.setPostAddress(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS)); // 通信地址必须大于八个字符
			bill.setSerialNumber(acc_nbr); // 设置要办理业务号码   真实环境需要改代码
			CustomInfo customInfo = CreateCustomHandler.emulate(bill, client);
			return customInfo;
		} catch (Exception e) {
			throw new CodedException("8002", "客户认证失败!");
		}
	}

	/**
	 * 业务办理抽象方法
	 * 
	 * @param t
	 * @param client
	 * @param customInfo
	 * @return
	 */
	public abstract R businessProcess(T zteRequest, HttpLoginClient client, CustomInfo customInfo) throws Exception;

	/**
	 * 业务办理
	 * 
	 * @return
	 */
	public R invoker(String order_id) throws CodedException, Exception {
		try {
			HttpLoginClient client = login(order_id);
			client.getParam().setOtree_order_id(order_id);
			client.getParam().setOrder_create_time(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TID_TIME));
			CustomInfo customInfo = getCustomInfo(client);
			return businessProcess(zteRequest, client, customInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CodedException("8002", e.getMessage(), e);
		}
	}

	public T getZteRequest() {
		return zteRequest;
	}

	public void setZteRequest(T zteRequest) {
		this.zteRequest = zteRequest;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

}
