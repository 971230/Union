package params.orderqueue.req;

import params.ZteError;
import params.ZteRequest;
import params.orderqueue.resp.OrderCollectionResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 订单归集总能力封装
 * @Description
 * @author  zhangJun
 * @date    2015-3-12
 * @version 1.0
 */
public class OrderCollectionReq extends ZteRequest<OrderCollectionResp> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name="请求格式",type="String",isNecessary="Y",desc="请求格式: json、xml")
	private String type;
	@ZteSoftCommentAnnotationParam(name="请求key值",type="String",isNecessary="Y",desc="请求appKey值：")
	private String key;
	@ZteSoftCommentAnnotationParam(name="请求密钥",type="String",isNecessary="Y",desc="请求密钥")
	private String sec;
	@ZteSoftCommentAnnotationParam(name="服务方法名",type="String",isNecessary="Y",desc="服务方法名即ApiMethodName")
	private String serv;
	@ZteSoftCommentAnnotationParam(name="版本号",type="String",isNecessary="Y",desc="版本号")
	private String version;
	@ZteSoftCommentAnnotationParam(name="请求报文",type="String",isNecessary="Y",desc="请求报文")
	private String req;


	
	
	
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public String getServ() {
		return serv;
	}

	public void setServ(String serv) {
		this.serv = serv;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}

	@Override
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(type)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "请求格式【type】不能为空！"));
        }
		if (StringUtils.isEmpty(key)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "请求key值【key】不能为空！"));
        }
		if (StringUtils.isEmpty(sec)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "请求密钥【sec】不能为空！"));
        }
		if (StringUtils.isEmpty(serv)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "服务方法名【serv】不能为空！"));
        }
		if (StringUtils.isEmpty(version)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "版本号【version】不能为空！"));
        }
		if (StringUtils.isEmpty(req)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "请求报文【req】不能为空！"));
        }
	}
	
	@Override
	public String getApiMethodName() {
		return "zte.service.orderqueue.orderCollection";
	}

	
	
}
