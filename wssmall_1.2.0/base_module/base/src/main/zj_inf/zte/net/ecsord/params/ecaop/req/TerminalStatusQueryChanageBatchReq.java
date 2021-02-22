/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ResourcesInfoVo;
import zte.net.ecsord.utils.AttrUtils;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @see 终端状态查询变更(批量专用)
 * 
 */
public class TerminalStatusQueryChanageBatchReq extends TerminalStatusQueryChanageReq {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ossOperId;
	private List<String> releaseCodes;
	public List<ResourcesInfoVo> getResourcesInfo() {
		resourcesInfo = new ArrayList<ResourcesInfoVo>();
		//查询所有终端信息
		List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemExtvtlBusiRequests();
		
		//遍历结果
		if(StringUtils.equals(operFlag, EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_0)){//查询
			for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
				ResourcesInfoVo resourcesInfoVo = new ResourcesInfoVo();
				String type_id = itemvo.getGoods_type();//商品大类	
				resourcesInfoVo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type2", type_id));
				resourcesInfoVo.setResCodeType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resCodeType", type_id));
				
				//获取串号
				resourcesInfoVo.setResourcesCode(itemvo.getResources_code());

				//设置变更动作
				resourcesInfoVo.setOccupiedFlag(operFlag);
				
				if(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1.equals(operFlag)){//预占
					resourcesInfoVo.setOccupiedTime("20591231235900");
				}
				
				resourcesInfoVo.setAllocationFlag(EcsOrderConsts.AOP_ALLOCATION_FLAG_TERI_1);//默认调拨
				resourcesInfoVo.setActiveType(getActiveTypeToAOP());
				resourcesInfoVo.setIsSelf(EcsOrderConsts.TERM_IS_SELF_1);//默认非自备
				resourcesInfo.add(resourcesInfoVo);
			}
		}else if(StringUtils.equals(operFlag, EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1)){//预占
			for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
				//预占成功的不作处理
				if(!StringUtils.equals(itemvo.getOperation_status(), EcsOrderConsts.TERMI_OCCUPIED_SUCC_FLAG)
						&& StringUtils.equals(itemvo.getGoods_type(), EcsOrderConsts.PRODUCT_TYPE_TERMINAL)){
					ResourcesInfoVo resourcesInfoVo = new ResourcesInfoVo();
					String type_id = itemvo.getGoods_type();//商品大类	
					resourcesInfoVo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type2", type_id));
					resourcesInfoVo.setResCodeType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resCodeType", type_id));
					
					//获取串号
					resourcesInfoVo.setResourcesCode(itemvo.getResources_code());
	
					//设置变更动作
					resourcesInfoVo.setOccupiedFlag(operFlag);
					
					if(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1.equals(operFlag)){//预占
						resourcesInfoVo.setOccupiedTime("20591231235900");
					}
					
					resourcesInfoVo.setAllocationFlag(EcsOrderConsts.AOP_ALLOCATION_FLAG_TERI_1);//默认调拨
					resourcesInfoVo.setActiveType(getActiveTypeToAOP());
					resourcesInfoVo.setIsSelf(EcsOrderConsts.TERM_IS_SELF_1);//默认非自备
					resourcesInfo.add(resourcesInfoVo);
				}
			}
		}else if(StringUtils.equals(operFlag, EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3)){//释放
			for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
				//释放成功的不作处理
				if(!StringUtils.equals(itemvo.getOperation_status(), EcsOrderConsts.TERMI_RELEASE_SUCC_FLAG)
						&& StringUtils.equals(itemvo.getGoods_type(), EcsOrderConsts.PRODUCT_TYPE_TERMINAL)){
					ResourcesInfoVo resourcesInfoVo = new ResourcesInfoVo();
					
					String type_id = itemvo.getGoods_type();//商品大类	
					resourcesInfoVo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type2", type_id));
					resourcesInfoVo.setResCodeType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resCodeType", type_id));
					
					//获取串号
					String rscode = itemvo.getResources_code();
					resourcesInfoVo.setResourcesCode(rscode);
	
					//设置变更动作
					resourcesInfoVo.setOccupiedFlag(operFlag);
					
					if(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1.equals(operFlag)){//预占
						resourcesInfoVo.setOccupiedTime("20591231235900");
					}
					
					resourcesInfoVo.setAllocationFlag(EcsOrderConsts.AOP_ALLOCATION_FLAG_TERI_1);//默认调拨
					resourcesInfoVo.setActiveType(getActiveTypeToAOP());
					resourcesInfoVo.setIsSelf(EcsOrderConsts.TERM_IS_SELF_1);//默认非自备

					if(releaseCodes==null || releaseCodes.contains(rscode)){
						resourcesInfo.add(resourcesInfoVo);
					}
				}				
			}
		}
		
		return resourcesInfo;
	}
	
	public void setResourcesInfo(List<ResourcesInfoVo> resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}

	public List<String> getReleaseCodes() {
		return releaseCodes;
	}

	public void setReleaseCodes(List<String> releaseCodes) {
		this.releaseCodes = releaseCodes;
	}
	
	public String getOssOperId() {
		return ossOperId;
	}

	public void setOssOperId(String ossOperId) {
		this.ossOperId = ossOperId;
	}

	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.termiStatusChanageBatch";
	}
	
	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssinfoByEssId(getOssOperId(),notNeedReqStrOrderId,EcsOrderConsts.OPER_TYPE_ORDER).getOperInfo();
		}
		return essOperInfo;
	}
	
	/**
	 * 获取终端资源状态变更接口的合约类型
	 * @return
	 */
	private String getActiveTypeToAOP(){		
		//设置合约类型
		String type_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)){
			//商品小类
			String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_CAT_ID);
			//根据商品小类获取对应的合约类型编码
			String active_type = AttrUtils.getInstance().getOtherDictCodeValue(AttrConsts.ACTIVE_TYPE_AOP, cat_id);
			//为空时默认为存费送机合约
			if(StringUtils.isEmpty(active_type)){
				active_type = EcsOrderConsts.ACTIVE_TYPE_01;
			}
			return active_type;
		}else{
			//无合约
			return EcsOrderConsts.ACTIVE_TYPE_04;
		}
	}
}
