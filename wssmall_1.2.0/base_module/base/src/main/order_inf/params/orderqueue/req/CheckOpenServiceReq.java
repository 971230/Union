package params.orderqueue.req;

import params.ZteError;
import params.ZteRequest;
import params.orderqueue.resp.CheckOpenServiceResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 查询webService服务配置信息
 * @Description
 * @author  zhangJun
 * @date    2015-3-12
 * @version 1.0
 */
public class CheckOpenServiceReq extends ZteRequest<CheckOpenServiceResp> {
	
	@ZteSoftCommentAnnotationParam(name="服务编码",type="String",isNecessary="Y",desc="服务编码")
	private String service_code;
	@ZteSoftCommentAnnotationParam(name="服务版本号",type="String",isNecessary="Y",desc="服务版本号")
	private String version;


	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	

	@Override
	public void check() throws ApiRuleException {
		
		if (StringUtils.isEmpty(service_code)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "服务编码【service_code】不能为空！"));
        }
		if (StringUtils.isEmpty(version)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "服务版本号【version】不能为空！"));
        }
		
	}
	
	@Override
	public String getApiMethodName() {
		return "zte.service.orderqueue.checkOpenService";
	}

	
	
}