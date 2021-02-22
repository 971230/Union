package zte.net.ecsord.utils;


import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.ecaop.req.vo.BSSGonghaoInfoVO;

public class ZjCommonUtils {
	public static BSSGonghaoInfoVO getGonghaoInfoByOrderId(String order_id) {
		String cityCode = AttrUtils.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE,"","");
		BSSGonghaoInfoVO gonghaoInfo = new BSSGonghaoInfoVO();
		gonghaoInfo.setCity_id(cityCode);
		return gonghaoInfo;
	}
}
 