package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.List;

import util.EssOperatorInfoUtil;
import util.Utils;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.bss.req.NumberResourceChangePreCaptureZjRequest;
import zte.net.ecsord.params.bss.req.NumberResourceNewGroupOrderZjRequest;
import zte.net.ecsord.params.bss.req.NumberStateChangeBssRequest;
import zte.net.ecsord.params.bss.resp.NumberResourceChangePreCaptureZjResponse;
import zte.net.ecsord.params.bss.resp.NumberStateChangeBssResp;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderInternetBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.zb.req.NumberStateChangeZB2Request;
import zte.net.ecsord.params.zb.req.NumberStateChangeZBRequest;
import zte.net.ecsord.params.zb.req.SynchronousOrderZBRequest;
import zte.net.ecsord.params.zb.resp.NumberStateChangeZBResponse;
import zte.net.ecsord.params.zb.resp.SynchronousOrderZBResponse;
import zte.net.ecsord.params.zb.vo.ResourcesInfo;
import zte.net.ecsord.params.zb.vo.ResourecsRsp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.OrderReleaseRecord;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdCollectManagerManager;

import consts.ConstsCore;

/**
 * 订单归集处理类
 * @author xuefeng
 */
public class OrdCollectManager extends BaseSupport implements IOrdCollectManagerManager {

	@Override
	public BusiDealResult synOrdInfoToZB(String order_id) {
		BusiDealResult result = new BusiDealResult();
		// 调用总部同步接口
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		SynchronousOrderZBRequest req = new SynchronousOrderZBRequest();
		req.setNotNeedReqStrOrderId(order_id);
		SynchronousOrderZBResponse infResp = null;
		try{
			infResp = client.execute(req,SynchronousOrderZBResponse.class);
			if(!infResp.getRespCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
//				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
//						new String[]{AttrConsts.ORDER_MODEL}, 
//						new String[]{EcsOrderConsts.ORDER_MODEL_05});
				//同步总部失败设置 [SYN_ORD_ZB]订单是否已送总部  值为 2:同步(发送)失败
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.SYN_ORD_ZB, AttrConsts.ZB_STATUS}, new String[]{EcsOrderConsts.SYN_ORD_ZB_2, EcsOrderConsts.ZB_ORDER_STATE_N01});
				result.setError_msg("错误编码：" + infResp.getRespCode() + "；错误信息：" + infResp.getRespDesc());
				result.setError_code(infResp.getRespCode());
			}else {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.SYN_ORD_ZB, AttrConsts.ZB_STATUS}, new String[]{EcsOrderConsts.SYN_ORD_ZB_1, EcsOrderConsts.ZB_ORDER_STATE_N01});
				result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
				//记录总部订单号
				OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
				orderExtBusi.setCol2(infResp.getOrderId());
				orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderExtBusi.store();
			}
		}catch(Exception e){
//			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
//					new String[]{AttrConsts.ORDER_MODEL}, 
//					new String[]{EcsOrderConsts.ORDER_MODEL_05});
			//同步总部异常设置 [SYN_ORD_ZB]订单是否已送总部  值为 2:同步(发送)失败
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.SYN_ORD_ZB, AttrConsts.ZB_STATUS}, new String[]{EcsOrderConsts.SYN_ORD_ZB_2, EcsOrderConsts.ZB_ORDER_STATE_N01});
			result.setError_msg("处理异常");
			result.setError_code("-999999");
		}
		result.setResponse(infResp);
		return result;
	}

	@Override
	public BusiDealResult numberStatesChangeAop(String order_id,
			String occupiedFlag,String proKey) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
	    //号码状态变更
	    ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	    NumberStateChangeZBRequest req=new NumberStateChangeZBRequest();
	    req.setNotNeedReqStrOrderId(order_id);//订单内部单号
	    List<ResourcesInfo> list = new ArrayList<ResourcesInfo>();
	    ResourcesInfo resourcesInfo = new ResourcesInfo();
	    resourcesInfo.setProKey(proKey); 	
	    String resourceCode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
	    resourcesInfo.setResourcesCode(resourceCode);//号码
	    resourcesInfo.setOccupiedFlag(occupiedFlag);//号码标识
	    resourcesInfo.setSnChangeTag(EcsOrderConsts.AOP_CHANGE_TAG_0);//默认不变更
	    list.add(resourcesInfo);
	    req.setResourcesInfo(list);
	    NumberStateChangeZBResponse  resp =  client.execute(req,NumberStateChangeZBResponse.class);
	    //判断是否释放操作:无论释放成功与否，都当作释放成功
  		if(StringUtils.equals(occupiedFlag, EcsOrderConsts.OCCUPIEDFLAG_4)){
  			if(!resp.getCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
  				//释放失败记录一条记录
  				OrderReleaseRecord record = new OrderReleaseRecord();
  				record.setOrder_id(order_id);
  				record.setType(EcsOrderConsts.AOP_NUM_RELEASE_FAIL);
  				record.setProkey(proKey);
  				record.setSerial_or_num(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
  				
  				if(req.getEssOperInfo()==null){
	  				record.setDeal_desc(resp.getError_msg());
  				}else{
  	  				record.setOccupied_essid(req.getOperatorId());
  	  				record.setDeal_desc(resp.getError_msg());
  	  				record.setFirst_req_json(JsonUtil.toJson(req));  					
  				}
  				Utils.saveReleaseRecord(record);
  				result.setError_msg("成功(假)");
  				result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
  				
  				resp.setCode(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
  			}
  		}
		if(!resp.getCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
			result.setError_msg(resp.getDetail());
			result.setError_code(resp.getCode());
		}else {
			result.setError_msg("成功");
			result.setError_code(resp.getCode());
		}	
		result.setResponse(resp);
		return result;
		
	}
	
	/**
	 * 副卡批量变更（预占、预定）
	 * @param order_id
	 * @param occupiedFlag
	 * @param phonelist 指定副卡列表
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult numberChangeBatchAopFuKa(String order_id, String occupiedFlag,List<OrderPhoneInfoFukaBusiRequest> phonelist) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();

		//初始化返回结果
		result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
		result.setError_msg("成功");	
		
	    ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	    
	    NumberStateChangeZB2Request req=new NumberStateChangeZB2Request();
	    req.setNotNeedReqStrOrderId(order_id);//订单内部单号
	    req.setOperFlag(occupiedFlag);
			
		List<ResourcesInfo> resourcesList = new ArrayList<ResourcesInfo>();  			
		
		//从指定副卡表获取变更的号码信息
		for(OrderPhoneInfoFukaBusiRequest fukavo : phonelist){				
  			ResourcesInfo resourcesInfovo =  new ResourcesInfo(); 
			resourcesInfovo.setOccupiedFlag(occupiedFlag);  		  		
			resourcesInfovo.setResourcesCode(fukavo.getPhoneNum());	
  			resourcesInfovo.setProKey(fukavo.getProkey());		  	  			  
			if(StringUtils.equals(EcsOrderConsts.OCCUPIEDFLAG_2, occupiedFlag)){
				resourcesInfovo.setCertType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", fukavo.getCertType()));
				resourcesInfovo.setCertNum(fukavo.getCertNum());
				resourcesInfovo.setCustName(fukavo.getCustomerName());
			}
			resourcesList.add(resourcesInfovo);
		}
		
		req.setResourcesInfo(resourcesList);
		
		if(phonelist.size()>0){
			NumberStateChangeZBResponse infResp = client.execute(req,NumberStateChangeZBResponse.class);		
			
			String resultCodeErr = "";
			if(StringUtils.equals(occupiedFlag, EcsOrderConsts.OCCUPIEDFLAG_1)){
				resultCodeErr = EcsOrderConsts.OCCUPIEDFLAG_5;
			}else if(StringUtils.equals(occupiedFlag, EcsOrderConsts.OCCUPIEDFLAG_2)){
				resultCodeErr = EcsOrderConsts.OCCUPIEDFLAG_6;
			}
			
			if(StringUtils.equals(infResp.getCode(), EcsOrderConsts.AOP_ERROR_9999)){//发生异常	
				for(OrderPhoneInfoFukaBusiRequest itemvo : phonelist){
					itemvo.setOccupiedflag(resultCodeErr);
					itemvo.setOperatorstate(resultCodeErr);
					itemvo.setResult_msg(infResp.getError_msg());
					itemvo.setOper_id(req.getOperatorId());
					itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
					itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
					itemvo.store();
				}	
				result.setError_msg(infResp.getError_msg());
				result.setError_code(infResp.getError_code()); 
			}else{//部分成功
				for(ResourecsRsp rspvo : infResp.getResourcesRsp()){				
					//只有code=0000 资源可用 才算成功
					if(StringUtils.equals(rspvo.getRscStateCode(), EcsOrderConsts.AOP_PHONE_0000)){						
						for(OrderPhoneInfoFukaBusiRequest itemvo : phonelist){
							if(StringUtils.equals(itemvo.getPhoneNum(), rspvo.getNumId())){
								itemvo.setOccupiedflag(req.getOperFlag());
								itemvo.setOperatorstate(req.getOperFlag());
								itemvo.setResult_msg(rspvo.getRscStateDesc());
								itemvo.setOper_id(req.getOperatorId());
								itemvo.setResult_msg(rspvo.getRscStateDesc());
								itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
								itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
								itemvo.store();
							}
						}					
					}else{								
						for(OrderPhoneInfoFukaBusiRequest itemvo : phonelist){
							if(StringUtils.equals(itemvo.getPhoneNum(), rspvo.getNumId())){
								itemvo.setOccupiedflag(resultCodeErr);
								itemvo.setOperatorstate(resultCodeErr);
								itemvo.setResult_msg(rspvo.getRscStateDesc());
								itemvo.setOper_id(req.getOperatorId());
								itemvo.setResult_msg(rspvo.getRscStateDesc());
								itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
								itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
								itemvo.store();
							}
						}
						result.setError_msg(rspvo.getRscStateDesc());
						result.setError_code(rspvo.getRscStateCode());  
					}				
				}
			}
			result.setResponse(infResp);
		}else{
			result.setError_msg("成功(没有找到复合条件的副卡记录，神马都没做)");
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);    				
		}

		return result;		
	}
	
	/**
	 * 副卡批量释放 
	 * @param order_id
	 * @param occupiedFlag
	 * @param phonelist 指定释放的副卡列表
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult numberReleaseBatchAopFuKa(String order_id, String occupiedFlag,List<OrderPhoneInfoFukaBusiRequest> phonelist) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();

	    ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	    
	    NumberStateChangeZB2Request req=new NumberStateChangeZB2Request();
	    req.setNotNeedReqStrOrderId(order_id);//订单内部单号
	    req.setOperFlag(occupiedFlag);

	    //判断是否释放操作:无论释放成功与否，都当作释放成功
  		if(StringUtils.equals(occupiedFlag, EcsOrderConsts.OCCUPIEDFLAG_4)){
  			//初始化返回结果
  			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
  			result.setError_msg("成功");	
  			  			
  			List<ResourcesInfo> resourcesList = new ArrayList<ResourcesInfo>();  			
  			
  			//从指定副卡表获取需要释放的号码信息
			for(OrderPhoneInfoFukaBusiRequest fukavo : phonelist){					
  				ResourcesInfo resourcesInfovo =  new ResourcesInfo(); 
  				resourcesInfovo.setOccupiedFlag(occupiedFlag);  		  		
				resourcesInfovo.setResourcesCode(fukavo.getPhoneNum());	
  	  			resourcesInfovo.setProKey(fukavo.getProkey());		  	  			
  				resourcesList.add(resourcesInfovo);
			}
  			
  			req.setResourcesInfo(resourcesList);
  			
  			if(phonelist.size()>0){
  				NumberStateChangeZBResponse infResp = client.execute(req,NumberStateChangeZBResponse.class);		
  				
  				if(StringUtils.equals(infResp.getCode(), EcsOrderConsts.AOP_ERROR_9999)){//发生异常
  					//接口失败，释放操作转移到任务表，并记录释放成功		
  					for(OrderPhoneInfoFukaBusiRequest itemvo : phonelist){
						OrderReleaseRecord record = new OrderReleaseRecord();
						record.setOrder_id(order_id);
						record.setType(EcsOrderConsts.AOP_NUM_RELEASE_FAIL);
						record.setIs_deal(EcsOrderConsts.NOT_RELEASE_YET);
						record.setSerial_or_num(itemvo.getPhoneNum());
						record.setOccupied_essid(req.getOperatorId());
						record.setFirst_req_json(null);
						record.setDeal_desc(infResp.getDetail());
						record.setProkey(itemvo.getProkey());
						Utils.saveReleaseRecord(record);					

						itemvo.setOccupiedflag(EcsOrderConsts.OCCUPIEDFLAG_4);
						itemvo.setOperatorstate(EcsOrderConsts.OCCUPIEDFLAG_4);
						itemvo.setResult_msg(itemvo.getResult_msg()+";释放伪成功！");
						itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
						itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
						itemvo.store();
  					}  					
  				}else{
  					for(ResourecsRsp rspvo : infResp.getResourcesRsp()){				
  						//只有code=0000 资源可用 才算成功
  						if(StringUtils.equals(rspvo.getRscStateCode(), EcsOrderConsts.AOP_PHONE_0000)){						
  							for(OrderPhoneInfoFukaBusiRequest itemvo : phonelist){
  								if(StringUtils.equals(itemvo.getPhoneNum(), rspvo.getResourcesCode())){
  									itemvo.setOccupiedflag(EcsOrderConsts.OCCUPIEDFLAG_4);
  									itemvo.setOperatorstate(EcsOrderConsts.OCCUPIEDFLAG_4);
  									itemvo.setResult_msg("释放成功！");
  									itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
  									itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
  									itemvo.store();
  								}
  							}					
  						}else{					
  							//记录数据，供释放任务表调用				
  							for(OrderPhoneInfoFukaBusiRequest itemvo : phonelist){
  								if(StringUtils.equals(itemvo.getPhoneNum(), rspvo.getResourcesCode())){
  									OrderReleaseRecord record = new OrderReleaseRecord();
  									record.setOrder_id(order_id);
  									record.setType(EcsOrderConsts.AOP_NUM_RELEASE_FAIL);
  									record.setIs_deal(EcsOrderConsts.NOT_RELEASE_YET);
  									record.setSerial_or_num(itemvo.getPhoneNum());
  									record.setOccupied_essid(req.getOperatorId());
  									record.setFirst_req_json(null);
  									record.setDeal_desc(rspvo.getRscStateDesc());
  									record.setProkey(itemvo.getProkey());
  									Utils.saveReleaseRecord(record);							
  		
  									itemvo.setOccupiedflag(EcsOrderConsts.OCCUPIEDFLAG_4);
  									itemvo.setOperatorstate(EcsOrderConsts.OCCUPIEDFLAG_4);
  									itemvo.setResult_msg(itemvo.getResult_msg()+";释放伪成功！");
  									itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
  									itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
  									itemvo.store();
  								}
  							}
  						}				
  					}
  				}
  				result.setResponse(infResp);
  			}else{
  				result.setError_msg("成功(没有找到复合条件的副卡记录，神马都没做)");
  				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);    				
  			}
  		}else{//保存
			result.setError_msg("成功(入口不对，神马都没做)");
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);  			
  		}

		return result;		
	}
	
	//号码预占
	@Override
	public BusiDealResult numberStatesChangeBss(String order_id, String occupiedFlag) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
	    //号码状态变更
	    ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	    
	    //原方法调用的接口方法
	    //NumberStateChangeBssRequest req=new NumberStateChangeBssRequest();
	    // req.setNotNeedReqStrOrderId(order_id);//内部订单号
	    //req.setX_tagchar(occupiedFlag);//号码操作标识
	    // NumberStateChangeBssResp  resp =  client.execute(req,NumberStateChangeBssResp.class);
	    
	    
	    //浙江省份号码预占修改添加字段----------------------
	    NumberResourceChangePreCaptureZjRequest req=new NumberResourceChangePreCaptureZjRequest();
	    String service_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
	    String certi_type=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
	    String certi_id=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
	    String operator_id=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_OPERATOR);
	    req.setNotNeedReqStrOrderId(order_id);//内部订单号
	    req.setService_num(service_num);//设置号码
	    req.setCert_num(certi_id);//设置证件号码
	    //操作点
	    req.setOperator_id(operator_id);//操作员
	    
	    //req.setX_tagchar(occupiedFlag);//号码操作标识
	    NumberResourceChangePreCaptureZjResponse  resp =  client.execute(req,NumberResourceChangePreCaptureZjResponse.class);
	    //浙江省份号码预占修改添加字段结束----------------------
	    
	    
	    //判断是否释放操作:无论释放成功与否，都当作释放成功
  		if(StringUtils.equals(occupiedFlag, EcsOrderConsts.BSS_OCCUPIEDFLAG_2)){
  			if(!resp.getError_code().equals(EcsOrderConsts.BSS_SUCCESS_00000)){
  				//释放失败记录一条记录
  				OrderReleaseRecord record = new OrderReleaseRecord();
  				record.setOrder_id(order_id);
  				record.setType(EcsOrderConsts.BSS_NUM_RELEASE_FAIL);
  				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
  				OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
  				record.setProkey(orderPhoneInfoBusiRequest.getProKey());
  				record.setSerial_or_num(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
  				
  				if(req.getEssOperInfo()==null){
  					record.setDeal_desc(resp.getMsg());
  				}else{
	  				//record.setOccupied_essid(req.getPara_code2());
	  				record.setDeal_desc("bss号码释放失败");
	  				record.setFirst_req_json(JsonUtil.toJson(req));
  				}
  				Utils.saveReleaseRecord(record);
  				result.setError_msg("成功");
  				result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
  				resp.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
  			}
  		}
		if(!resp.getError_code().equals(EcsOrderConsts.INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息：" + resp.getError_msg());
			result.setError_code(resp.getError_code());
		}else {
			result.setError_msg(resp.getError_msg());
			result.setError_code(resp.getError_code());
		}
		result.setResponse(resp);
		return result;
		
	}
	
	/**
	 * 单个副卡号码变更-bss
	 * @param order_id
	 * @param phoneNum 副卡号码
	 * @param occupiedFlag 操作码（预占、释放）
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult numberStatesChangeSingleBssFuka(String order_id, OrderPhoneInfoFukaBusiRequest selvo,String occupiedFlag) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();

		result.setError_msg("副卡变更成功");
		result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
	    
	    //号码状态变更
	    ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	    NumberStateChangeBssRequest req=new NumberStateChangeBssRequest();
	    req.setNotNeedReqStrOrderId(order_id);//内部订单号
	    req.setX_tagchar(occupiedFlag);//号码操作标识
	    req.setPhone_num(selvo.getPhoneNum());
	    req.setPro_key(selvo.getProkey());
	    req.setOperFlag(selvo.getOper_id());
	    
	    NumberStateChangeBssResp  resp =  client.execute(req,NumberStateChangeBssResp.class);
	    //判断是否释放操作:无论释放成功与否，都当作释放成功
  		if(StringUtils.equals(occupiedFlag, EcsOrderConsts.BSS_OCCUPIEDFLAG_2)){
  			if(!resp.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
  				//释放失败记录一条记录
  				OrderReleaseRecord record = new OrderReleaseRecord();
  				record.setOrder_id(order_id);
  				record.setType(EcsOrderConsts.BSS_NUM_RELEASE_FAIL);
  				record.setProkey(req.getPro_key());
  				record.setSerial_or_num(req.getPhone_num());	
  				record.setOccupied_essid(req.getPara_code2());
  				record.setDeal_desc("bss号码释放失败");
  				record.setFirst_req_json(JsonUtil.toJson(req));
  				Utils.saveReleaseRecord(record);
  			}
  		}
	    
		String operState = EcsOrderConsts.OCCUPIEDFLAG_0;
		if(!resp.getError_code().equals(EcsOrderConsts.INF_RESP_CODE_0000)){
			if(StringUtils.equals(occupiedFlag, EcsOrderConsts.BSS_OCCUPIEDFLAG_1)){
				operState=EcsOrderConsts.OCCUPIEDFLAG_8;
			}else if(StringUtils.equals(occupiedFlag, EcsOrderConsts.BSS_OCCUPIEDFLAG_2)){
				operState=EcsOrderConsts.OCCUPIEDFLAG_9;
			}
			result.setError_msg(resp.getError_msg());
			result.setError_code(resp.getError_code());
		}else {
			if(StringUtils.equals(occupiedFlag, EcsOrderConsts.BSS_OCCUPIEDFLAG_1)){
				operState=EcsOrderConsts.OCCUPIEDFLAG_1;
			}else if(StringUtils.equals(occupiedFlag, EcsOrderConsts.BSS_OCCUPIEDFLAG_2)){
				operState=EcsOrderConsts.OCCUPIEDFLAG_4;
			}
		}
		
		selvo.setBss_occupied_flag(operState);
		selvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		selvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		selvo.store();
		
		result.setResponse(resp);
		return result;	
	}
	
	/**
	 * 按环境标识过滤是否属于解藕环境的订单
	 * @param order_id
	 * @return
	 */
	@Override
	public void setOrderHide(String order_id) {
		this.baseDaoSupport.execute("insert into es_order_hide(order_id) values(?)", order_id);
	}
	
	/**
	 * 按环境标识过滤是否属于解藕环境的订单
	 * @param order_id
	 * @return
	 */
	@Override
	public void setOrderHide(String order_id , String flag , String reason){
		this.baseDaoSupport.execute("insert into es_order_hide(order_id,flag,reason,source_from) values(?,?,?,?)", order_id,flag,reason,ManagerUtils.getSourceFrom());
	}

	/**
	 * 浙江省份号码预占
	 */
	@Override
	public BusiDealResult numberPreOccupyZjBss(String order_id,String occupiedFlag)
			throws ApiBusiException {
		
		BusiDealResult result = new BusiDealResult();
	    //号码状态变更
	    ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	    
	    NumberResourceChangePreCaptureZjRequest req=new NumberResourceChangePreCaptureZjRequest();
	    
	    String service_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
	    String certi_id=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);//身份证号码
	    String operator_id=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_OPERATOR);
	    String Office_id=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
	    req.setSeq_id("11111");//预占序列号，非空[20] 需要确认
	    req.setService_num(service_num);//设置号码
	    req.setNotNeedReqStrOrderId(order_id);//内部订单号
	    req.setOperator_id(operator_id);//操作员
	    req.setOffice_id(Office_id);//操作点
	    
	    NumberResourceChangePreCaptureZjResponse  resp =  client.execute(req,NumberResourceChangePreCaptureZjResponse.class);
	    //判断是否释放操作:无论释放成功与否，都当作释放成功
	    
  		//if(StringUtils.equals(occupiedFlag, EcsOrderConsts.BSS_OCCUPIEDFLAG_2)){
  			if(!resp.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
  				//释放失败记录一条记录
  				OrderReleaseRecord record = new OrderReleaseRecord();
  				record.setOrder_id(order_id);
  				record.setType(EcsOrderConsts.BSS_NUM_RELEASE_FAIL);
  				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
  				OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
  				record.setProkey(orderPhoneInfoBusiRequest.getProKey());
  				record.setSerial_or_num(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
  				
  				if(req.getEssOperInfo()==null){
  					record.setDeal_desc(resp.getError_msg());
  				}else{
	  				record.setOccupied_essid(req.getEssid());
	  				record.setDeal_desc("bss号码释放失败");
	  				record.setFirst_req_json(JsonUtil.toJson(req));
  				}
  				Utils.saveReleaseRecord(record);
  				result.setError_msg("成功");
  				result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
  				resp.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
  			}
  		//}
  		
  		
		if(!resp.getError_code().equals(EcsOrderConsts.INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息：" + resp.getError_msg());
			result.setError_code(resp.getError_code());
		}else {
			result.setError_msg(resp.getError_msg());
			result.setError_code(resp.getError_code());
		}
		result.setResponse(resp);
		return result;
	}
	

	@Override
	public boolean orderISHide(String order_id) {
		boolean flag = false;
		try{
			String sql = "select count(1) from es_order_hide c where c.order_id = ? and c.source_from = ?";
			int i = this.baseDaoSupport.queryForInt(sql, order_id,ManagerUtils.getSourceFrom());
			if(i >= 1){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void cancelOrderHide(String order_id) {
		this.baseDaoSupport.execute("delete from es_order_hide where order_id = ?", order_id);
	}

    @Override
    public BusiDealResult numberBssPreempted(String order_id)  throws ApiBusiException {

        BusiDealResult result = new BusiDealResult();
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        NumberResourceNewGroupOrderZjRequest  req=new NumberResourceNewGroupOrderZjRequest ();
        String service_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        String serial_number = "";
        List<OrderInternetBusiRequest> lists = orderTree.getOrderInternetBusiRequest();
        for (int i = 0; i < lists.size(); i++) {
            if("1".equals(lists.get(i).getIs_new())){
                serial_number = lists.get(i).getService_num();
                break;
            }
        }
        String certi_id=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
        String city_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市
        String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);//地市
        String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);//地市
        req.setNotNeedReqStrOrderId(order_id);//内部订单号
        req.setService_num(serial_number);//设置号码
        req.setCert_num(certi_id);//设置证件号码
        String cert_types =  CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_BSS_CERT_TYPE", CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE));
        if(!StringUtil.isEmpty(cert_types)){
            req.setCert_type(cert_types);
        }else {
            req.setCert_type("11");
        }
        EssOperatorInfoUtil essinfo = new EssOperatorInfoUtil();
        EmpOperInfoVo essOperInfo = null;
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String groupFlowKg = cacheUtil.getConfigInfo("groupFlowKg");
        if(StringUtils.isNotEmpty(groupFlowKg) &&"1".equals(groupFlowKg)&&"10093".equals(order_from) && "221668199563784192".equals(cat_id)){
             essOperInfo = essinfo.getEmpInfoFromDataBase(null,"WFGROUPUPINTENT",city_id);//无线宽带线上取默认的渠道和工号信息
        }
        if(essOperInfo != null){
            req.setOperator_id(essOperInfo.getEss_emp_id());
            req.setOffice_id(essOperInfo.getDep_id());
        }else{
            String operator_id=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
            String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);//操作点
            req.setOffice_id(OUT_OFFICE);
            req.setOperator_id(operator_id);//操作员    
        }
        String number_bss_press = cacheUtil.getConfigInfo("number_bss_press_"+order_from);
        if(StringUtils.isNotEmpty(number_bss_press)){
            req.setSelection_time(number_bss_press);
        }else{
            req.setSelection_time("10080");
        }
        NumberResourceChangePreCaptureZjResponse  resp =  client.execute(req,NumberResourceChangePreCaptureZjResponse.class);
        //浙江省份号码预占修改添加字段结束----------------------
        if(!resp.getCode().equals(EcsOrderConsts.INF_RESP_CODE_00000)){
            result.setError_msg("错误编码：" + resp.getCode() + ";错误信息：" + resp.getMsg()+resp.getRespJson());
            result.setError_code(resp.getCode());
        }else {
            result.setError_msg(resp.getMsg()+resp.getRespJson());
            result.setError_code(resp.getCode());
        }
        result.setResponse(resp);
        return result;
    }
	
}
