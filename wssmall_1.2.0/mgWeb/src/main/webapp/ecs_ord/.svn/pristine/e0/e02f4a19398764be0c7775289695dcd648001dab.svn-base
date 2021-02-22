<%@page import="com.ztesoft.api.spring.ApiContextHolder"%>
<%@page import="services.AdminUserServ"%>
<%@page import="params.adminuser.req.AdminUserGetReq"%>
<%@page import="params.adminuser.resp.AdminUserResp"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@page import="consts.ConstsCore"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="com.ztesoft.net.mall.core.service.IDcPublicInfoManager"%>
<%@page import="java.util.Map"%>
<%@page import="com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy"%>
<%@page import="java.util.List"%>
<%@page import="zte.params.process.resp.UosNode"%>
<%@page import="zte.params.process.resp.UosFlowResp"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderTreeBusiRequest"%>
<%@page import="zte.params.process.req.UosFlowReq"%>
<%@page import="zte.net.iservice.IUosService"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.utils.AttrUtils"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<% 
String order_id = request.getParameter("order_id");
String is_his_order = request.getParameter("order_is_his");//是否是历史单1-是
String is_order_view = request.getParameter("is_order_view");//隐藏操作作按钮-1 从订单查询界面过来
String btn = request.getParameter("btn")==null?"":request.getParameter("btn");
String query_type = request.getParameter("query_type")==null?"":request.getParameter("query_type");
String is_order_exp = request.getParameter("is_order_exp");//是否是异常单系统过来的

OrderTreeBusiRequest orderTree = null;
String BSS_STATUS=null;
String send_zb ="";
if(is_his_order!=null&&is_his_order.equals(EcsOrderConsts.IS_ORDER_HIS_YES)){
	orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
	BSS_STATUS=CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.BSS_STATUS);
	 send_zb = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.SYN_ORD_ZB);
}else{
	orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
	BSS_STATUS=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_STATUS);
	send_zb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
}
String orderType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_TYPE);
String d_type = request.getParameter("d_type")==null?"":request.getParameter("d_type");
if("ycl".equals(d_type))btn="ycl";


String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
String exception_status = orderTree.getOrderExtBusiRequest().getAbnormal_status();
String visable_status = orderTree.getOrderExtBusiRequest().getVisible_status();
String expType = orderTree.getOrderExtBusiRequest().getAbnormal_type();
String isAop = orderTree.getOrderExtBusiRequest().getIs_aop();
String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
String is_refund = orderTree.getOrderExtBusiRequest().getIs_refund();
String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
String bss_refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS);
if(null == isAop){
	isAop = "0";
}
String shippingType = orderTree.getOrderBusiRequest().getShipping_type();
String isEasyAccount = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EASY_ACCOUNT);
String sq = orderTree.getOrderExtBusiRequest().getShipping_quick();
//是否已同步给总部
//orderTree.getOrderExtBusiRequest().getSend_zb();
String return_status = orderTree.getOrderExtBusiRequest().getRefund_status()==null?"":orderTree.getOrderExtBusiRequest().getRefund_status();//08为退单
String return_type = orderTree.getOrderExtBusiRequest().getRefund_deal_type();//02为退单
if(expType==null || "".equals(expType))expType="0";
String mode_code = orderTree.getOrderExtBusiRequest().getOrder_model();

//异常类型
String abnormal_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();//异常类型
String abnormal_name = "";
if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(abnormal_type)){
	abnormal_name = "正常单";
}else if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_1.equals(abnormal_type)){
	abnormal_name = "人工标记异常";
}else if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type)){
	abnormal_name = "系统异常";
}else if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)){
	abnormal_name = "自动化异常";
}
IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
String exception_cat = orderTree.getOrderExtBusiRequest().getException_type();
if(exception_cat!=null && !"".equals(exception_cat)){
	List<Map> list = dcPublicCache.getList("DIC_ORDER_EXCEPTION_TYPE");
	for(Map m:list){
		String pkey = (String)m.get("pkey");
		if(exception_cat.equals(pkey)){
			exception_cat = "["+(String)m.get("pname")+"]";
			break ;
		}
	}
}
if(EcsOrderConsts.ABNORMAL_TYPE_STOCKOUT.equals(exception_cat)){
	exception_cat = "缺货异常";
}else if(EcsOrderConsts.ABNORMAL_TYPE_OPEN.equals(exception_cat)){
	exception_cat = "开户异常";
}else if(EcsOrderConsts.ABNORMAL_TYPE_BSS.equals(exception_cat)){
	exception_cat = "业务办理异常";
}else if(EcsOrderConsts.ABNORMAL_TYPE_CHECK.equals(exception_cat)){
	exception_cat = "稽核异常";
}
if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(abnormal_type) || exception_cat==null)exception_cat="";
String mode_name = "";
if(mode_code!=null && !"".equals(mode_code)){
	List<Map> list = dcPublicCache.getList("DIC_OPER_MODE");
	for(Map m:list){
		String pkey = (String)m.get("pkey");
		if(mode_code.equals(pkey)){
			mode_name = (String)m.get("pname");
			break ;
		}
	}
}

request.setAttribute("trace_id", trace_id);
List<UosNode> nodes = new ArrayList<UosNode>();
UosNode  un = new UosNode();
//if(EcsOrderConsts.REFUND_STATUS_08.equals(return_status) || EcsOrderConsts.REFUND_STATUS_01.equals(return_status)){
if(!StringUtils.isBlank(return_status)){
	un.setRunning(true);
}
else{
	un.setRunning(false);
}
un.setTacheId(EcsOrderConsts.RETURNED_TRACE_ID);
un.setTacheCode(EcsOrderConsts.RETURNED_TRACE_ID);
un.setName("退单");

UosNode  un2 = new UosNode();
//if(EcsOrderConsts.REFUND_STATUS_08.equals(return_status) || EcsOrderConsts.REFUND_STATUS_01.equals(return_status)){
if(!StringUtils.isBlank(return_status)){
	un2.setRunning(true);
}
else{
	un2.setRunning(false);
}
un2.setTacheId("REFUND");
un2.setTacheCode("REFUND");
un2.setName("退款");

try{
    IUosService service = SpringContextHolder.getBean("uosService");
    String processInstanceId = orderTree.getOrderExtBusiRequest().getFlow_inst_id();
    UosFlowReq req = new UosFlowReq();
    req.setProcessInstanceId(processInstanceId);
    UosFlowResp resp = service.queryFlow(req);
    UosNode gjNodes = new UosNode();
    if(!"bss_parallel".equals(query_type)){
    	gjNodes.setRunning(true);
        gjNodes.setTacheId(EcsOrderConsts.ORDER_COLLECT_PLAN);
        gjNodes.setTacheCode(EcsOrderConsts.ORDER_COLLECT_PLAN);
        gjNodes.setName("订单归集");
        if(resp!=null){
        	nodes = resp.getNodes();
        	if(nodes!=null && nodes.size()>1){
        		nodes.remove(nodes.size()-1);
        		nodes.set(0, gjNodes);
        	}
        } 
        if(null==nodes||nodes.size()==0){
        	nodes = new ArrayList<UosNode>();
        	nodes.add(0, gjNodes);
        }
        if(EcsOrderConsts.RET_REQ_TRACE_ID.equals(btn) || EcsOrderConsts.RETURNED_TRACE_ID.equals(btn) || EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_order_view)){
        	nodes.add(un);
        }else if(((EcsOrderConsts.REFUND_AUDIT_TACHE_ID.equals(btn)||EcsOrderConsts.REFUND_APPLY_TACHE_ID.equals(btn)) && EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_refund)) || EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_order_view)){
        	nodes.add(un);
        	nodes.add(un2);
        }
    }
    else{
    	gjNodes.setRunning(true);
        gjNodes.setTacheId("Y2");
        gjNodes.setTacheCode("Y2");
        gjNodes.setName("业务办理");
        nodes.add(0, gjNodes);
    }
    request.setAttribute("flowNodes", nodes);  
}catch(Exception ex){
	ex.printStackTrace();
}


String userid = ManagerUtils.getAdminUser().getUserid();
String lock_order_user_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getLock_user_id();
%>

<div class="grid_n_div">
		<input type="hidden" value="<%=userid %>" id="userid" />
		<input type="hidden" value="<%=lock_order_user_id %>" id="lock_order_user_id" />
    	<h2><a href="javascript:void(0);" class="closeArrow"></a>流程导向图</h2>
    	<div class="processCont">
        	<ul id="auto_order_flows" class="clearfix">
        		<c:forEach items="${flowNodes }" var="ds">
        			<li class="${ds.running?'complete':'' } ${trace_id==ds.tacheCode?'curr':''} ${ds.running && trace_id==ds.tacheCode?'running':''}" isrun="${ds.running }" trace_id="${ds.tacheCode }" style="cursor: pointer;" order_id="<%=order_id %>" is_his_order="<%=is_his_order %>" is_order_view="<%=is_order_view %>" >${ds.name }</li>
        		</c:forEach>
            </ul>
        </div>
        <div id="flow_inf_dv" class="pop_n_div" style="display: none;">
            <div class="pop_tit">
                <h2>条件设置</h2>
                <a href="javascript:void(0);" id="close_flow_detail" class="pop_close">关闭</a>
            </div>
            <div class="pop_cont">
            	<div class="grid_w_div">
                	<h3>流程节点信息</h3>
                    <div class="processGrid" id="auto_flow_logs_dv">
                    
                    </div>
                    <h3>流程详细描述</h3>
                    <div class="processInfo">
                    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="processTable">
                          	<tbody><tr>
                                <th>流程模式：</th>
                                <td style="width: 150px;"><%=mode_name %></td>
                                <th>异常类型：</th>
                                <td>
                                   <%=abnormal_name%>&nbsp;<%=exception_cat %>
                                </td>
                          	</tr>
                            <tr>
                                <th style="width: 100px;">订单是否可见：</th>
                                  <% 
                                     String visi_status = orderTree.getOrderExtBusiRequest().getVisible_status();
                                     String visi_status_str =  "";
                                     if(EcsOrderConsts.VISIBLE_STATUS_0.equals(visi_status)){
                                    	 visi_status_str = "可见";
                                     }
                                     if(EcsOrderConsts.VISIBLE_STATUS_1.equals(visi_status)){
                                    	 visi_status_str = "不可见";
                                     }
                                   %>
                                 <td style="width: 150px;">
                                  <%=visi_status_str%>
                                 </td>
                                <th>退单状态：</th>
                                <td><%=AttrUtils.getInstance().getRefund_status(order_id)%></td>
                            </tr>
                          	<tr>
                                <th>异常信息：</th>
                                <td colspan="3">

                                   <%
                                    String exe_msg = orderTree.getOrderExtBusiRequest().getException_desc();
                                   if(exe_msg==null){
                                   	exe_msg = "";
                                   }
                                   int index =exe_msg.indexOf("404");
                                   if(( exe_msg !=null &&!"".equals(exe_msg) )&& index>20)
                                	   exe_msg = exe_msg.substring(0, 20);
                                   %>
                                   <%=exe_msg%>

                                </td>
                          	</tr>
                          	<tr>
                                <th>预警信息：</th>
                                <td class="redFont"></td>
                          	</tr>
                          	<tr>
                          	  <th style="width: 100px;">BSS业务办理：</th>
                          	  <td>
                          	    <%if(EcsOrderConsts.BSS_STATUS_1.equals(BSS_STATUS)){ %>
                          	              已办理
                          	    <%}else{ %>
                          	              未办理
                          	    <%} %>
                          	  </td>
                          	  <th>当前处理人：</th>
                          	  <td><%
		                          	  String lock_user_id = orderTree.getOrderExtBusiRequest().getLock_user_id();
		                 	          String null_str = ConstsCore.NULL_VAL;
		                              if(lock_user_id==null||null_str.equals(lock_user_id)){
		                 	        	 lock_user_id = "";
		                 	          }
									  if(""!=lock_user_id){
										  AdminUserResp adminUserResp = new  AdminUserResp();
										  AdminUserGetReq adminUserGetReq = new AdminUserGetReq();
										  adminUserGetReq.setUser_id(lock_user_id);
										  AdminUserServ adminUserServ = ApiContextHolder.getBean("adminUserServ");
										  adminUserResp = adminUserServ.getUser(adminUserGetReq);
										  if(adminUserResp!=null&&adminUserResp.getAdminUser()!=null){
										   out.print(adminUserResp.getAdminUser().getRealname());
										  } 
									  }
                                    %>
                               </td>
                          	</tr>
                          	
                        </tbody></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
 <input type="hidden" value="${specValidateMsg }" id="specValidateMsg">
<script type="text/javascript">
	$(function(){
		$("#close_flow_detail").bind("click",function(){
			$("#flow_inf_dv").hide();
		});
		//AutoFlow.qeryFlowLogs("<%=order_id %>","<%=trace_id %>");
		$("#auto_order_flows .complete").bind("click",AutoFlow.qeryFlowLogs);
		var hide_page = "detail,expType<%=expType %>,isEasyAccount<%=isEasyAccount%>";
		//在BCDX环节,非AOP+现场交付订单可以回退到客户回访环节---zengxianlian
		var isAop="<%=isAop%>";
		var shippingType = "<%=shippingType%>";
		if('0'==isAop){
			hide_page+=",isAop0";
		}else{
			if('XJ'==shippingType){
				hide_page+=",isAop0";
			}
		}
		var orderType = "<%=orderType%>";
		if (orderType != "09") {
			hide_page += ",ordTypeNo09";
		}
		var order_id = "<%=order_id %>";
		var exception_status = "<%=exception_status %>";
		var visable_status = "<%=visable_status %>";
		var send_zb = "<%=send_zb %>";
		if(send_zb)hide_page +=",ZB"+send_zb;
		var return_status = "<%=return_status %>";
		var return_type = "<%=return_type %>";
		var query_type = "<%=query_type %>";
		var sq = "<%=sq %>";
		if(!sq)sq="01";
		hide_page += ","+sq;
		var btn = "<%=btn %>";
		var is_order_view = "<%=is_order_view %>";
		var abnormal_type = "<%=abnormal_type%>";
		var trace_id = "<%=trace_id%>";
		var bss_refund_status = "<%=bss_refund_status%>";
		var goods_type = "<%=goods_type%>";
		var order_from = "<%=order_from%>";
		if("1"==exception_status){
			hide_page+=",exception";
		}else{
			hide_page+=",editExp";
		}
		if("1"!=exception_status){
			hide_page+=",order";
		}
		if("1"==visable_status && "1"!=exception_status)hide_page+=",unvisabled";
		if("20006"==goods_type||"20000"==goods_type||"20001"==goods_type){
			hide_page+=",ZZYW20006";
		}
		if("10061"!=order_from){//非华盛B2C订单不用打装箱单
			hide_page+=",hsB2Cpacking";
		}
		//安钮事件
		//if("RETURNEDCONFIRM"==btn || "RETURNED"==btn || "ycl"==btn){
			//显示可见订单操作按钮
		
		//如果是异常单
		var is_order_exp = "<%=is_order_exp%>";
		if("1" == is_order_exp) {
			hide_page = hide_page.replace(',order','').replace(',exception','');
			hide_page += ",exp";
		}
		if(query_type == "bss_parallel"){//业务办理并行环节
			btn = "Y2";
		}
			
		if(is_order_view!=null&&is_order_view=='1'){
			//不展示按钮
		}else{
			if(btn && ("RETURNEDCONFIRM"==btn || "REFUND_AUDIT" == btn)){
				//非退单申请
				if (return_status!="08") {
					hide_page += ",noRefundApply";
				}
				//退单确认、BSS返销、ESS返销
				if (return_status!="01" && return_status!="02" && return_status!="03" ) {
					hide_page += ",noRefundAudi";
				}
				//退款成功
				if (bss_refund_status=="1" || bss_refund_status=="3" ) {
					hide_page += ",refundSucc";
				}
				//待退款
				if (bss_refund_status=="" || bss_refund_status=="0" ) {
					hide_page += ",waitRefund";
				}
				if(send_zb && return_status){
					if(return_status=="08" && send_zb=="1"){
						hide_page +=",zbreturnedcfm"
					}/* else{
						OrdBtns.showBtns(order_id,hide_page,btn,null,showOpenBtn);
					} */
					OrdBtns.showBtns(order_id,hide_page,btn,null,is_order_exp,query_type,showOpenBtn);
				}else{
					OrdBtns.showBtns(order_id,hide_page,btn,null,is_order_exp,query_type,showOpenBtn);
				}
			}else{
				OrdBtns.showBtns(order_id,hide_page,btn,null,is_order_exp,query_type,showOpenBtn);
			}	
		 }
		//}
		
	});
	
	function showOpenBtn(data){
		var query_type = "<%=query_type %>";
		if("exception"==query_type){
			$("a[name=offlineOpenAccount]").show();
		}
	}
</script>

