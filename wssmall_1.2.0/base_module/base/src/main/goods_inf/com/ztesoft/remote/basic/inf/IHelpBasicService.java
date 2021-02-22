package com.ztesoft.remote.basic.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.basic.params.req.*;
import com.ztesoft.remote.basic.params.resp.*;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 14:09
 * To change this template use File | Settings | File Templates.
 */
@ZteSoftCommentAnnotation(type = "class", desc = "常用基础服务API", summary = "常用基础服务API")
public interface IHelpBasicService {

    @ZteSoftCommentAnnotation(type = "metod", desc = "版本更新", summary = "版本更新")
    public VersionResponse newVersion(VersionRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "使用帮助", summary = "使用帮助")
    public UseHelpResponse useHelp(UseHelpRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "在线客服", summary = "在线客服")
    public OnlineCustomerServiceResponse onlineCustomerService(OnlineCustomerServiceRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "用户反馈", summary = "用户反馈")
    public UserfeedbackResponse userfeedback(UserfeedbackRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "快捷功能", summary = "快捷功能")
    public FuncionsResponse quickFuncions(FuncionsRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "工具栏", summary = "工具栏")
    public ToolBarsResponse toolBars(ToolBarsRequest request);
}
