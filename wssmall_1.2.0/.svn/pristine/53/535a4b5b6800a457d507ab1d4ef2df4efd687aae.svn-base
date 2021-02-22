package com.ztesoft.api;

import params.ZteBusiRequest;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.consts.ApiConsts;
import com.ztesoft.api.internal.utils.ApiUtils;


/**
 * @author zhangJun
 *
 */
public class DefaultZteMqClient implements ZteClient {

	DefaultZteClient client = null;
	
	public DefaultZteMqClient(String appKey,String appSecret) {
        if(ApiUtils.isBlank(appKey) || ApiUtils.isBlank(appSecret)){
            throw new ApiRuleException("-1","appKey或秘钥不能为空!");
        }
        client = new DefaultZteClient("",appKey,appSecret,"1.0");
	}

	public DefaultZteMqClient(String appKey, String appSecret,String version) {
        if(ApiUtils.isBlank(appKey) || ApiUtils.isBlank(appSecret)){
            throw new ApiRuleException("-1","appKey或秘钥不能为空!");
        }
		client = new DefaultZteClient("",appKey,appSecret,version);
	}

	@Override
	public <T extends ZteResponse> T execute(ZteRequest request, Class<T> class1) throws ApiException {
		request.setRemote_type(ApiConsts.ZTE_CLIENT_MQ);
        request.setAppKey(client.getAppKey());
        request.setAppSecret(client.getAppSecret());
		return  client.execute(request, class1);
	}

	@Override
	public <T extends ZteResponse> T execute(ZteRequest request,
			Class<T> class1, String session) throws ApiException {
		request.setRemote_type(ApiConsts.ZTE_CLIENT_MQ);
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
