/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ResourcesInfoVo;
import zte.net.ecsord.utils.AttrUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author ZX
 * @version 2015-05-04
 * @see 终端状态查询变更
 * 
 */
@JsonIgnoreProperties(value = {"resources_code"}) 
public class TerminalStatusQueryChanageReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	protected String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="N",desc="operatorId：操作员ID")
	protected String operatorId;
	@ZteSoftCommentAnnotationParam(name="省份",type="String",isNecessary="Y",desc="province：省份")
	protected String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "N", desc = "city：地市")
	protected String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "N", desc = "district：区县")
	protected String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	protected String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型:参考附录渠道类型编码")
	protected String channelType;
	@ZteSoftCommentAnnotationParam(name = "资源信息", type = "List", isNecessary = "Y", desc = "resourcesInfo：资源信息")
	
	protected List<ResourcesInfoVo> resourcesInfo = new ArrayList<ResourcesInfoVo>();
	protected EmpOperInfoVo essOperInfo;
	protected String operFlag;
	

	@ZteSoftCommentAnnotationParam(name="串号",type="String",isNecessary="Y",desc="resourcesCode：资源唯一标识")
	private String resources_code;
	
	
	public String getResources_code() {
		return resources_code;
	}
	public void setResources_code(String resources_code) {
		this.resources_code = resources_code;
	}
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOperatorId() {
		operatorId = getEssOperInfo().getEss_emp_id();
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
			this.province = getEssOperInfo().getProvince();
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
			city =  getEssOperInfo().getCity();
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
			district =  getEssOperInfo().getDistrict();
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
			channelId =  getEssOperInfo().getChannel_id();
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
			channelType =  getEssOperInfo().getChannel_type();
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public List<ResourcesInfoVo> getResourcesInfo() {
		ResourcesInfoVo resourcesInfoVo = new ResourcesInfoVo();
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", SpecConsts.TYPE_ID);//商品大类	
		resourcesInfoVo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type2", type_id));
		resourcesInfoVo.setResCodeType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resCodeType", type_id));
		
		//获取串号
		String flowNode = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getFlow_trace_id();
		if(StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)){//B环节还没有串码,传虚拟的
			resourcesInfoVo.setResourcesCode(CommonDataFactory.getInstance().getTerminalNum(notNeedReqStrOrderId));
		}else{
			resourcesInfoVo.setResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM));
		}
		if(!StringUtils.isEmpty(resources_code)){
			resourcesInfoVo.setResourcesCode(resources_code);

		}

		//设置变更动作
		resourcesInfoVo.setOccupiedFlag(operFlag);
		
		if(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1.equals(operFlag)){//预占
			resourcesInfoVo.setOccupiedTime("20591231235900");
		}
//		else if(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_2.equals(operFlag)){//预定
//			resourcesInfoVo.setOccupiedTime("20591231235900");
//			resourcesInfoVo.setCustName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NAME));
//			String certType=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
//			if(!StringUtils.isEmpty(certType)){
//				resourcesInfoVo.setCertType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", certType));
//		    }
//			resourcesInfoVo.setCertNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM));
//		}//这个是我们内部判断用的,当99时,表示拣货换串号,释放资源对象是旧串码--释放
		else if(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_99.equals(operFlag)){
			resourcesInfoVo.setResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OLD_TERMINAL_NUM));
			resourcesInfoVo.setOccupiedFlag(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3);
		}
		resourcesInfoVo.setAllocationFlag(EcsOrderConsts.AOP_ALLOCATION_FLAG_TERI_1);//默认调拨
		
		//设置合约类型
		type_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)){
			//商品小类
			String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_CAT_ID);
			//根据商品小类获取对应的合约类型编码
			String active_type = AttrUtils.getInstance().getOtherDictCodeValue(AttrConsts.ACTIVE_TYPE_AOP, cat_id);
			//为空时默认为存费送机合约
			if(StringUtils.isEmpty(active_type)){
				active_type = EcsOrderConsts.ACTIVE_TYPE_01;
			}
			resourcesInfoVo.setActiveType(active_type);
		}else{
			//无合约
			resourcesInfoVo.setActiveType(EcsOrderConsts.ACTIVE_TYPE_04);
		}
		resourcesInfoVo.setIsSelf(EcsOrderConsts.TERM_IS_SELF_1);//默认非自备
		resourcesInfo.add(resourcesInfoVo);
		return resourcesInfo;
	}
	
	public String getTime(){
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
 	    Date date = new Date();
   	    Calendar c = Calendar.getInstance();
   	    c.setTime(date);
   	    c.add(Calendar.MINUTE, EcsOrderConsts.AOP_TERMINAL_OCCUPIED_TIME*48);
	    String str = sdf.format(c.getTime());
	    return str;
	}

	public void setResourcesInfo(List<ResourcesInfoVo> resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.terminalStatusQueryChanage";
	}


	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			//预占
			if(StringUtils.equals(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1,operFlag)
					|| StringUtils.equals(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_0,operFlag)){
				essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
			}
			
			//如果是环节释放,则先取到预占工号，然后根据ess工号获取工号详情
			if(EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3.equals(operFlag)){
				if(CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests().size()>0){
					AttrTmResourceInfoBusiRequest vo = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getTmResourceInfoBusiRequests().get(0);
					String essId = vo.getOccupied_essId();
					essOperInfo = CommonDataFactory.getEssinfoByEssId(essId,notNeedReqStrOrderId,EcsOrderConsts.OPER_TYPE_ESS).getOperInfo();
				}
			}
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

}
