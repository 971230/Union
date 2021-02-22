package com.ztesoft.remote.basic.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.basic.params.req.BussinessAcceptRequest;
import com.ztesoft.remote.basic.params.req.InsertPreOrderRequest;
import com.ztesoft.remote.basic.params.resp.BussinessAcceptResponse;
import com.ztesoft.remote.basic.params.resp.InsertPreOrderResponse;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 14:26
 * To change this template use File | Settings | File Templates.
 */
@ZteSoftCommentAnnotation(type = "class", desc = "业务受理API", summary = "业务受理API")
public interface IAcceptBasicService {

    @ZteSoftCommentAnnotation(type = "metod", desc = "业务受理", summary = "业务受理")
    public BussinessAcceptResponse bussinessAccept(BussinessAcceptRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "写预受理表", summary = "写预受理表")
    public InsertPreOrderResponse insertPreOrder(InsertPreOrderRequest request);
}
