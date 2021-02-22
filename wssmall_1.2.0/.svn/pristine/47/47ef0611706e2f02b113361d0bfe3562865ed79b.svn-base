package zte.params.number.resp;


import java.util.List;
import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 号码信息
 * @author deng.yx
 *
 */
public class NoInfoByNoResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="号码基本信息", type="Map", isNecessary="N", desc="号码基本信息，"
			+ " 里面的map对象有dn_no-号码;no_gen-网别；is_lucky-是否靓号（1为靓号）；deposit-预存款；period-合约期；"
			+ " lowest-每月最低消费；region_name-号码归属地；group_name-号码分组名；seg_no-号段")
	private Map noMap;
	
	@ZteSoftCommentAnnotationParam(name="号码同步信息列表", type="NumberSynInfo", isNecessary="N", desc="号码同步信息列表")
	private List<NumberSynInfo> numberSynInfoList;

	public Map getNoMap() {
		return noMap;
	}

	public void setNoMap(Map noMap) {
		this.noMap = noMap;
	}

	public List<NumberSynInfo> getNumberSynInfoList() {
		return numberSynInfoList;
	}

	public void setNumberSynInfoList(List<NumberSynInfo> numberSynInfoList) {
		this.numberSynInfoList = numberSynInfoList;
	}
	
}
