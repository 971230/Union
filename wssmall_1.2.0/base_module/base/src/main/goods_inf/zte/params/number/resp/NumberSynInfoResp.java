package zte.params.number.resp;


import java.util.List;

import params.ZteResponse;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
/**
 * 
 * @author deng.yx
 * 号码同步信息返回实体
 *
 */
public class NumberSynInfoResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="号码同步信息列表", type="NumberCo", isNecessary="N", desc="号码同步信息列表")
	private List<NumberSynInfo> numberSynInfoList;

	public List<NumberSynInfo> getNumberSynInfoList() {
		return numberSynInfoList;
	}

	public void setNumberSynInfoList(List<NumberSynInfo> numberSynInfoList) {
		this.numberSynInfoList = numberSynInfoList;
	}


	
}
