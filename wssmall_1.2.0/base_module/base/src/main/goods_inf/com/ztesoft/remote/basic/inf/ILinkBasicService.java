package com.ztesoft.remote.basic.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.basic.params.req.LinkRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 10:56
 * To change this template use File | Settings | File Templates.
 *
 */
@ZteSoftCommentAnnotation(type = "class", desc = "链接查询API", summary = "链接查询API")
public interface ILinkBasicService {

    @ZteSoftCommentAnnotation(type = "metod", desc = "链接查询", summary = "链接查询")
    public void queryLink(LinkRequest request);
}
