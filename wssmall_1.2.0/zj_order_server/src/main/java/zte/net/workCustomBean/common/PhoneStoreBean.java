package zte.net.workCustomBean.common;



import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.O2OStatusUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.O2OStatusUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.MSGPKGVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGBODYVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGHEADVO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;




public class PhoneStoreBean extends BasicBusiBean {

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
		String order_id=this.flowData.getOrderTree().getOrder_id();
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String orderFroms = cacheUtil.getConfigInfo("REFUND_STATUS_SYN_ORDER_FROM");
		StringBuffer sql=new StringBuffer();


		if(!StringUtil.isEmpty(orderFroms)){
			sql.append("select * from es_order_intent where order_id='").append(order_id.trim()).append("' ");
			List list = baseDaoSupport.queryForList(sql.toString());
			String order_from = "";
			String intent_order_id = "";

			//先取意向单订单来源，没有意向单，取正式订单
			if(list!=null && list.size()>0){
				order_from = ((Map)list.get(0)).get("source_system_type").toString();
				intent_order_id = ((Map)list.get(0)).get("intent_order_id").toString();
			}else{
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

				if(orderTree!=null){
					order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
					intent_order_id =  CommonDataFactory.getInstance().
							getAttrFieldValue(order_id, "out_tid");
				}
			}

			if(orderFroms.contains(order_from+",")){
				String order_code = "";
				order_code = intent_order_id;
				O2OStatusUpdateReq req = new O2OStatusUpdateReq();
				req.setNotNeedReqStrOrderId(order_id.trim());
				MSGPKGVO new_MSGPKG = new MSGPKGVO();
				PKGHEADVO PKG_HEAD = new PKGHEADVO();
				PKGBODYVO PKG_BODY = new PKGBODYVO();
				PKG_HEAD.setACTION_ID("M10001");

				PKG_BODY.setORDER_CODE(order_code);
				PKG_BODY.setORDER_STATUS("06");
				new_MSGPKG.setPKG_BODY(PKG_BODY);
				new_MSGPKG.setPKG_HEAD(PKG_HEAD);
				req.setMSGPKG(new_MSGPKG);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				O2OStatusUpdateResp refundResp = client.execute(req, O2OStatusUpdateResp.class);
				if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(refundResp.getError_code())) {
					throw new Exception(refundResp.getError_msg());
				}
			}
		}


		return "true";
	}

	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception{
		return true;
	}
}
