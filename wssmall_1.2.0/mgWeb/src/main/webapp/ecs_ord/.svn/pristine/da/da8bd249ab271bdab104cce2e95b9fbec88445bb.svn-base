<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http:UI//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>历史订单详情</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>


<%

  
  String order_id = (String)request.getAttribute("order_id");
  String order_is_his = (String)request.getAttribute("order_is_his");
  pageContext.setAttribute("order_is_his_page_flag", order_is_his);
  String order_from  = "";
  String customerType = "";

  if(order_is_his!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){
	 order_from  = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.ORDER_FROM);
	 customerType = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.CUSTOMER_TYPE);
  }else{
	  order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
	  customerType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
  }
  
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
<input type="hidden" id="custFlag" value="<%=custFlag%>"/>
<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
            <!-- 顶部公共 -->
            
            <div id="auto_flows_div">
	            <c:choose>
	            	<c:when test="${orderTree.orderExtBusiRequest.is_work_custom=='1'}">
	            		<jsp:include page="custom_flow.jsp?order_id=${order_id }"/>
	            	</c:when>
	            	<c:otherwise>
	            		<jsp:include page="auto_flows.jsp?order_id=${order_id }&btn=${yclbtn }&order_is_his=${order_is_his }&is_order_view=1"/> 
	            	</c:otherwise>
	            </c:choose>
     		</div>
            
        	<!-- 路由信息 -->
            <jsp:include page="route_info.jsp?order_id=${order_id }&hide_div=handle_input_area"/>
         
         	<!-- 关联订单显示开始-->
            <!-- <div class="grid_n_div">
	            <h2><a href="javascript:void(0);" class="openArrow"></a>关联订单信息</h2> 
	           	<div class="grid_n_cont">
	    		  	<div id="relations_info" style="height: 40px"></div>
	            </div>
            </div> -->
            <!-- 关联订单显示结束 -->
          <!-- 订单全部信息开始 -->  
            <%-- <div>
               <a href="<%=request.getContextPath()%>/shop/admin/ordAuto!showOrderList.do?query_type=order_view&params.visible_status=-1"   class="dobtn" style="margin-left:20px;">返回</a>
            </div>	 --%>
        	<div class="grid_n_div">
            	 <h2><a href="javascript:void(0);" class="closeArrow"></a>订单基本信息<!--<a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
            		<%-- <div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span>缺少信息或者信息格式不正确！</span></div> --%>
					<div id="order_base">
						 <jsp:include page="include/view_order_base.jsp?order_id=${order_id }&order_amount=${order_amount }&order_is_his=${order_is_his }"/> 
					</div>
					<div id="order_ext" style="height: 80px"></div>
        	    </div>
        	</div>
        	<!-- 订单全部信息结束 -->
        	 <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>客户历史订单</h2>
              	<div class="grid_n_cont">
              		              	 <div id="order_his_before" style="height: 80px;" ></div>
              		<div id="order_his" style="height: 80px;display:none" ></div>
                </div>
              </div>
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>订单修改记录</h2> 
              	<div class="grid_n_cont">
              		<div id="order_change" style="height: 80px"></div>
                </div>
              </div>
        	<!-- 商品信息开始 -->
        	<div class="grid_n_div">
             <h2><a href="javascript:void(0);" class="openArrow"></a>商品信息<!-- <a href="#" class="editBtn">编辑</a> --></h2> 
              	<div class="grid_n_cont">
       		  	  	<%-- <div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span id="specValidateMsg_Span">缺少信息或者信息格式不正确！</span></div> --%>
					<div id="prod_info" style="height: 80px"></div>
                </div>
            </div>
              <!-- 商品信息结束 -->
              
              <c:choose>
              <c:when test="${(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')||orderTree.orderExtBusiRequest.order_from=='10061'}">
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
					<div id="goods_info_more" style="height: 80px"></div>
                </div>
              </div>
              </c:when>
              <c:when test="${orderTree.orderExtBusiRequest.order_from=='10062' }">
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
              		<div id="hsB2C_goods_info" style="height: 80px"></div>
                </div>
              </div>
              </c:when>
              <c:otherwise>
              <c:if test="${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}">
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
            		<%-- <div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16" /><span>缺少信息或者信息格式不正确！</span></div> --%>
					<div id="goods_info" style="height: 80px"></div>
					<div id="goods_open" style="height: 80px"></div>
					<div id="sp_product" style="height: 80px"></div>
	               	<div id="sub_product">
               	  	     <jsp:include page="include/sub_product.jsp"/>
					</div>               	  	
					<div id="goods_gift" style="height: 80px"></div>
                </div>
              </div>
              </c:if>
              </c:otherwise>
			  </c:choose>
			 
			  <jsp:include page="order_handler.jsp?order_id=${order_id }&is_his_order=${order_is_his }&hide_div=handle_input_area"/>
        </div>
    </div>
    <%-- <div class="pop_btn" align="center">
			<a     href="<%=request.getContextPath()%>/shop/admin/ordAuto!showOrderList.do?query_type=order_view&params.visible_status=-1"  class="dobtn" style="margin-left:5px;" ><span>返回</span></a>
			
		</div> --%>
</div>
</form>
<br />
<br /><br /><br />
</body>
</html>


<script type="text/javascript">

function load_ord_his(){
	//$("#table_show").show();
	//$("#order_his").show();
	  $("#table_show").toggle();
   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};


  
  $(function(){
	  //CommonLoad.loadJSP('order_base','< %=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_order_base"},false,null,true);
	//  CommonLoad.loadJSP('relations_info','< %=request.getContextPath() %>/shop/admin/orderFlowAction!getRealtionOrders.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"relations_info"},false,null,true);
	//先加载总数和按钮页
		  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);  
	CommonLoad.loadJSP('order_ext','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_order_ext"},false,null,true);
	  CommonLoad.loadJSP('prod_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_prod_info"},false,null,true);
 
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	  if(${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}){}
	  if(${(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')||orderTree.orderExtBusiRequest.order_from=='10061'}){//多商品(含B2B商城&华盛B2C)
		  CommonLoad.loadJSP('goods_info_more','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_goods_info_more"},false,null,true);
	  }else if(${orderTree.orderExtBusiRequest.order_from=='10062'}){//华盛B2B
		  CommonLoad.loadJSP('hsB2C_goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!hsB2Cgoods.do',{order_id:"${order_id }",ajax:"yes",includePage:"hsB2C_goods_info",order_is_his:"${order_is_his }",query_type:"view"},false,null,true);
	  }else{//非多商品
		  CommonLoad.loadJSP('goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_goods_info"},false,null,true);
		  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_goods_open"},false,null,true);
		  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"sp_product"},false,null,true);
		  CommonLoad.loadJSP('goods_gift','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_goods_gift"},false,null,true);
	  }
	   //校验信息
	 // AutoFlow.checkMsg();
  });
  
</script>

