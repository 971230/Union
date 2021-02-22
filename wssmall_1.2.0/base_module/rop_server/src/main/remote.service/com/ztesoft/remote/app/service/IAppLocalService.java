package com.ztesoft.remote.app.service;

import com.ztesoft.remote.pojo.AppInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-23 14:26
 * To change this template use File | Settings | File Templates.
 *
 *app内部验证服务
 */
public interface IAppLocalService {
    /*
    * @app  appKey
    *@appSecret 秘钥
    * */
    public AppInfo validate(String appKey,String appSecret);

    /*
    * @app  appKey
    *@appSecret 秘钥
    * */
    public AppInfo validate(String appKey);


    public List<AppInfo> queryAllList();
}
