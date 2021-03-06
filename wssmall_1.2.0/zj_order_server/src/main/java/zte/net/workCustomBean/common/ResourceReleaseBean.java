package zte.net.workCustomBean.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.RelSelectionNumReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.ResourCecenterAppResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.REL_SELECTION_NUM_REQ;
import com.ztesoft.net.ecsord.params.ecaop.vo.SELNUMLISTREQVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.UNIBSSBODYRELVO;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

public class ResourceReleaseBean extends BasicBusiBean{
	/**
	 * 业务处理方法
	 * @return
	 * @throws Exception
	 */
	@Override
	public String doBusi() throws Exception{
		ResourCecenterAppResp numResp = new ResourCecenterAppResp();
		RelSelectionNumReq numReq = new RelSelectionNumReq();
		ZteClient zteClient=ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		String order_id=this.getFlowData().getOrderTree().getOrder_id();
		numReq.setNotNeedReqStrOrderId(order_id);
		REL_SELECTION_NUM_REQ new_SELECTEDNUMREQ= new REL_SELECTION_NUM_REQ();
		List<SELNUMLISTREQVO> SELNUM_LIST=new ArrayList();
		UNIBSSBODYRELVO new_unibssbody = new UNIBSSBODYRELVO();
		SELNUMLISTREQVO selectednumreqvo = new SELNUMLISTREQVO();
		
		String json_param=this.getFlowData().getJson_param();
		
		if (StringUtil.isEmpty(json_param)) {
			 throw new Exception("查询返回为空");
		}
		EmpOperInfoVo essOperInfo = CommonDataFactory.getEssInfoByOrderId(order_id).getOperInfo();
		String staff_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cbss_out_operator");
		String order_city_code = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_city_code();
		String city_code = CommonDataFactory.getInstance().getOtherDictVodeValue("city", order_city_code);
		String district_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "district_code");
		String channel_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "cbss_out_office");
		if(StringUtil.isEmpty(staff_id)){
			staff_id = essOperInfo.getEss_emp_id();
			channel_id = essOperInfo.getChannel_id();
			city_code = essOperInfo.getCity();
			district_code = essOperInfo.getDistrict();
		}
		
		new_SELECTEDNUMREQ.setSTAFF_ID(staff_id);
		new_SELECTEDNUMREQ.setPROVINCE_CODE("36");
		new_SELECTEDNUMREQ.setCITY_CODE(city_code);
		new_SELECTEDNUMREQ.setDISTRICT_CODE(district_code);
		new_SELECTEDNUMREQ.setCHANNEL_ID(channel_id);
		new_SELECTEDNUMREQ.setSYS_CODE("3602");
		
		
		JSONObject  jsonObject = JSON.parseObject(json_param);
		Map<String,Object> map = jsonObject; 
		
		selectednumreqvo.setSERIAL_NUMBER((String)map.get("SERIAL_NUMBER"));
		selectednumreqvo.setPRO_KEY((String)map.get("PRO_KEY"));
		selectednumreqvo.setCERT_TYPE_CODE((String)map.get("CERT_TYPE_CODE"));
		selectednumreqvo.setCERT_CODE((String)map.get("CERT_CODE"));
		SELNUM_LIST.add(selectednumreqvo);
		new_SELECTEDNUMREQ.setSELNUM_LIST(SELNUM_LIST);
		new_unibssbody.setREL_SELECTION_NUM_REQ(new_SELECTEDNUMREQ);
		numReq.setUnibssbody(new_unibssbody);
		numResp=zteClient.execute(numReq, ResourCecenterAppResp.class);
		
		if (!"00000".equals(numResp.getCode())) {
			throw new Exception(numResp.getMsg());
		}
		
		if (numResp.getRespJson().getREL_SELECTION_NUM_RSP() == null) {
			throw new Exception("释放接口返回为空");
		}
		
		if (!"0000".equals(numResp.getRespJson().getREL_SELECTION_NUM_RSP().getRESP_CODE())) {
			throw new Exception(numResp.getRespJson().getREL_SELECTION_NUM_RSP().getRESP_DESC());
		}
		
		if (!(numResp.getRespJson().getREL_SELECTION_NUM_RSP().getSELNUM_LIST() == null) && numResp.getRespJson().getREL_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getRESP_CODE().equals("0000")) {
			
			return "true";
			
		} else {
			
			 throw new Exception(numResp.getRespJson().getREL_SELECTION_NUM_RSP().getSELNUM_LIST().get(0).getRESP_DESC());
			 
		}
					
	}
		   

	/**
	 * 后台等待环节的处理方法，用于后台等待环节的自动判断，
	 * 不是后台等待环节的业务处理代码无需实现该方法
	 * @return 可以继续往下执行，返回true;继续等待返回false
	 * @throws Exception
	 */
	@Override
	public boolean doBackWaitCheck() throws Exception{
		System.out.println("doBackWaitCheck()");
		return true;
	}

}
