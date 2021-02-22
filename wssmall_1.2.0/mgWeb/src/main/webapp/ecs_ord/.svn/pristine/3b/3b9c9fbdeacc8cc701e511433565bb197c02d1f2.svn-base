<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单预处理</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<%
  String order_id = (String)request.getAttribute("order_id");
  String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
  String customerType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
  boolean custFlag = EcsOrderConsts.CUSTOMER_CUST_TYPE_JTKH.equals(customerType);
  String d_type = (String)request.getAttribute("d_type");
  String yclbtn = "";
  if("ycl".equals(d_type)){
	  yclbtn = "ycl";
	  request.setAttribute("yclbtn", yclbtn);
  }
%>

</head>

<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<body>
<form action="javascript:void(0);" id="preDealOrderForm" method="post">

<input type="hidden"  value="${query_type}" name="query_type"/>
<input type="hidden"  value="${d_type}" name="d_type"/>
<input type="hidden" id="custFlag" value="<%=custFlag%>"/>
<input type="hidden" name="oldCertNum" value="<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM)%>" />
<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
            <!-- 顶部公共 -->
        	<jsp:include page="auto_flows.jsp?order_id=${order_id }&btn=${yclbtn }"/>
          <!-- 订单全部信息开始 -->  
        	<div class="grid_n_div">
            	 <h2><a href="javascript:void(0);" class="closeArrow"></a>订单基本信息<!--<a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
            		<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
					<div id="order_base">
						<jsp:include page="replenish/order_base.jsp?order_id=${order_id }&order_amount=${order_amount }"/>
					</div>
					<div id="order_ext" style="height: 80px"></div>
        	    </div>
        	</div>
        	<!-- 订单全部信息结束 -->
        	
        	<!-- 商品信息开始 -->
        	<div class="grid_n_div">
             <h2><a href="javascript:void(0);" class="openArrow"></a>商品信息<!-- <a href="#" class="editBtn">编辑</a> --></h2> 
              	<div class="grid_n_cont">
       		  	  	<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span id="specValidateMsg_Span"></span></div>
					<div id="prod_info" style="height: 80px"></div>
                </div>
            </div>
              <!-- 商品信息结束 -->
              
              <!-- 货品信息开始 -->
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
            		<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16" /><span></span></div>
					<div id="goods_info" style="height: 80px"></div>
					<div id="goods_open" style="height: 80px"></div>
					<div id="goods_gift" style="height: 80px"></div>
                </div>
              </div>
              <!-- 货品信息结束 --> 
              <jsp:include page="order_handler.jsp?order_id=${order_id }"/>
        </div>
        
    </div>
</div>
</form>
<br />
<br /><br /><br />
</body>
</html>


<script type="text/javascript">
  
  $(function(){
	  $(".remind_div").empty();
	  //CommonLoad.loadJSP('order_base','<%=request.getContextPath() %>/shop/admin/orderFlowAction!replenish.do',{order_id:"${order_id }",ajax:"yes",replenishPage:"order_base"},false,null,true);
	  CommonLoad.loadJSP('order_ext','<%=request.getContextPath() %>/shop/admin/orderFlowAction!replenish.do',{order_id:"${order_id }",ajax:"yes",replenishPage:"replenish_order_ext"},false,function(){},true);
	  CommonLoad.loadJSP('prod_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!replenish.do',{order_id:"${order_id }",ajax:"yes",replenishPage:"replenish_prod_info"},false,function(){},true);
	  CommonLoad.loadJSP('goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!replenish.do',{order_id:"${order_id }",ajax:"yes",replenishPage:"replenish_goods_info"},false,function(){},true);
	  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!replenish.do',{order_id:"${order_id }",ajax:"yes",replenishPage:"replenish_goods_open"},false,function(){},true);
	  CommonLoad.loadJSP('goods_gift','<%=request.getContextPath() %>/shop/admin/orderFlowAction!replenish.do',{order_id:"${order_id }",ajax:"yes",replenishPage:"replenish_goods_gift"},false,function(){},true);
	   //校验信息
	  
	//  AutoFlow.checkMsg();
  });
  
</script>

