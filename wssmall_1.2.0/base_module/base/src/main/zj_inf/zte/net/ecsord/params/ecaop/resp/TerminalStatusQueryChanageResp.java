/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.params.ecaop.req.vo.ResourcesInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ResourcesRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-04
 * @see 终端状态查询变更
 *
 */
public class TerminalStatusQueryChanageResp extends ZteBusiResponse {
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	@ZteSoftCommentAnnotationParam(name = "返回信息", type = "ResourcesRspVo", isNecessary = "Y", desc = "resourcesRsp：返回信息")
	private List<ResourcesRspVo> resourcesRsp;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="ParamsVo",isNecessary="N",desc="para：保留字段")
	private List<ParamsVo> para;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public List<ResourcesRspVo> getResourcesRsp() {
		return resourcesRsp;
	}
	public void setResourcesRsp(List<ResourcesRspVo> resourcesRsp) {
		this.resourcesRsp = resourcesRsp;
	}
    public List<ParamsVo> getPara() {
        return para;
    }
    public void setPara(List<ParamsVo> para) {
        this.para = para;
    }
	
}
