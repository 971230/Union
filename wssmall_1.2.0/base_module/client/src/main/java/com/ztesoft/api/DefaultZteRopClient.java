package com.ztesoft.api;

import com.ztesoft.api.consts.ApiConsts;
import com.ztesoft.api.internal.utils.ApiUtils;
import commons.CommonTools;

import params.ZteBusiRequest;
import params.ZteRequest;
import params.ZteResponse;
/**
 * 第三方平台调用
 * 
 * @author wui
 * @since 1.0, 2013-12-25
 */
public class DefaultZteRopClient implements ZteClient {

	DefaultZteClient client = null;

	public DefaultZteRopClient(String serverUrl, String appKey, String appSecret) {
        if(ApiUtils.isBlank(serverUrl) ||ApiUtils.isBlank(appKey) || ApiUtils.isBlank(appSecret)){
        	CommonTools.addError("-1", "serverUrl、appKey或秘钥不能为空!");
            throw new ApiRuleException("-1","serverUrl、appKey或秘钥不能为空!");
        }
        client = new DefaultZteClient(serverUrl,appKey,appSecret,"1.0");
	}

	public DefaultZteRopClient(String serverUrl, String appKey, String appSecret, String version) {
        if(ApiUtils.isBlank(serverUrl) ||ApiUtils.isBlank(appKey) || ApiUtils.isBlank(appSecret)){
        	CommonTools.addError("-1", "serverUrl、appKey或秘钥不能为空!");
            throw new ApiRuleException("-1","serverUrl、appKey或秘钥不能为空!");
        }
		client = new DefaultZteClient(serverUrl,appKey,appSecret,version);
	}

	@Override
	public <T extends ZteResponse> T execute(ZteRequest request, Class<T> class1) throws ApiException {
		request.setRemote_type(ApiConsts.ZTE_CLIENT_REMOTE);
        request.setAppKey(client.getAppKey());
        request.setAppSecret(client.getAppSecret());
		return  client.execute(request, class1);
	}

	@Override
	public <T extends ZteResponse> T execute(ZteRequest request,
			Class<T> class1, String session) throws ApiException {
		request.setRemote_type(ApiConsts.ZTE_CLIENT_REMOTE);
        request.setAppKey(client.getAppKey());
        request.setAppSecret(client.getAppSecret());
		return client.execute(request, class1, session);
	}

	@Override
	public <T extends ZteResponse> T execute(ZteBusiRequest request,
			Class<T> class1) throws ApiException {
		return  client.execute(request, class1);
	}
}
