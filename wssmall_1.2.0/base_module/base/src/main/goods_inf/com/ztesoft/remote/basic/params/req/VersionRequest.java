package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.VersionResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 14:11
 * To change this template use File | Settings | File Templates.
 */
public class VersionRequest extends ZteRequest<VersionResponse> {
    @ZteSoftCommentAnnotationParam(name="版本号",type="String",isNecessary="Y",desc="版本号")
    private String version;

    @ZteSoftCommentAnnotationParam(name="描述",type="String",isNecessary="Y",desc="描述")
    private String desc;

    @ZteSoftCommentAnnotationParam(name="地址",type="String",isNecessary="Y",desc="地址")
    private String url;

    @Override
	public String getVersion() {
        return version;
    }

    @Override
	public void setVersion(String version) {
        this.version = version;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"newVersion";
    }
}
