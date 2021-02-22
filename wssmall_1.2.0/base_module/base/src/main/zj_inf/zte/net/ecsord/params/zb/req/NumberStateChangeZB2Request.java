package zte.net.ecsord.params.zb.req;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.zb.vo.ResourcesInfo;

import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 副卡专用
 *
 */
public class NumberStateChangeZB2Request extends NumberStateChangeZBRequest{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<ResourcesInfo> getResourcesInfo() {
		for(int i=0;i<this.resourcesInfo.size();i++){
			ResourcesInfo resourcevo = this.resourcesInfo.get(i);
			resourcevo.setKeyChangeTag(resourcevo.getSnChangeTag());
			resourcevo.setProKeyMode(EcsOrderConsts.AOP_PROKEY_TYPE_0);
			
			String pay_type=CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
			pay_type=CommonDataFactory.getInstance().getOtherDictVodeValue("aop_num_pay_type", pay_type);
			resourcevo.setResourcesType(pay_type);
			this.setOperFlag(resourcevo.getOccupiedFlag());
			if(EcsOrderConsts.OCCUPIEDFLAG_1.equals(resourcevo.getOccupiedFlag())){
				if(StringUtils.isEmpty(resourcevo.getProKey())){
					resourcevo.setProKey((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())+ManagerUtils.genRandowNum(6));
				}
				//预占半小时
				resourcevo.setOccupiedTime(getTime(1));
			}else if(EcsOrderConsts.OCCUPIEDFLAG_2.equals(resourcevo.getOccupiedFlag())){
				resourcevo.setOccupiedTime(EcsOrderConsts.OCCUPIED_TIME);

				String pay_status=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_STATUS);
				if(EcsOrderConsts.AOP_PAY_STUTAS_1.equals(pay_status)){
					resourcevo.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_3);
					this.setOperFlag(EcsOrderConsts.OCCUPIEDFLAG_3);
				}
			}

			//TODO
			resourcevo.setContactNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE));
			resourcevo.setDevelopPersonTag(EcsOrderConsts.AOP_DEV_PERSON_TAG_1);
		}
		return this.resourcesInfo;
	}

	public void setResourcesInfo(List<ResourcesInfo> resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}
	
}
