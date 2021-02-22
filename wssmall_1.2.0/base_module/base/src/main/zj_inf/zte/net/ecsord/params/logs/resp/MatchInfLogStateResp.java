/**
 * 
 */
package zte.net.ecsord.params.logs.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

/**
 * @author ZX
 * @version 2015-10-29
 * @see 修改接口日志状态
 *
 */
public class MatchInfLogStateResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="接口交互返回状态",type="String",isNecessary="Y",desc="-1:接口调用异常,0:业务处理失败,1:业务成功数量")
	private String inf_state;

	public String getInf_state() {
		return inf_state;
	}
	public void setInf_state(String inf_state) {
		this.inf_state = inf_state;
	}
	
}
