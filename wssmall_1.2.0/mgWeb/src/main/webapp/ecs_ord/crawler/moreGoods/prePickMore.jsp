<%@page import="zte.net.ecsord.utils.SpecUtils"%>
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
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<%
  String order_id = (String)request.getAttribute("order_id");
  int seriaNO = 1;
%>

</head>
<body>
<form action="javascript:void(0);" id="prePickForm" method="post">

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
             
             <!-- 顶部公共 -->
        	<jsp:include page="/ecs_ord/order/auto_flows.jsp?order_id=${order_id}"/>
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>拣货信息(${order_id})<!-- <a href="#" class="editBtn">编辑</a> --></h2>
                <div class="grid_n_cont">
                <div class="remind_div"><span><img src="${context}/images/ic_remind.png" width="16" height="16" /></span></div>
                  <div class="grid_n_cont_sub">
                    <h3>商品包：</h3>
                      <c:choose>
                      <c:when test="${orderTree.orderExtBusiRequest.order_from!='10062' }">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_g">
                      <tbody>
                         <tr>
                        	<th></th>
                            <th>商品名称</th>
                            <th>商品价格</th>
                            <th>货品类型</th>
                            <th>机型编码</th>
                            <th>品牌名称</th>
                            <th>型号名称</th>
                            <th>颜色</th>
                            <th>终端串号</th><!-- 判断是终端类型才有终端串号 -->
                            <th>操作状态</th>
                            <th>操作描述</th>
                         </tr>
                         <!-- 
                                                                         预占成功控制串号填写框不可修改  其他状态都可以修改  是终端的必须填写串号
                          -->
                          <c:forEach var="itemExtvl" items="${orderItemExtvtlBusiRequests }">
	                         <tr>
	                             <td><%=seriaNO++ %></td>
	                             <td>${itemExtvl.goods_name}</td>
	                             <td>${itemExtvl.goods_price}</td>
	                             <td>${itemExtvl.goods_type_name}</td>
	                             <td>${itemExtvl.machine_type_code}</td>
	                             <td>${itemExtvl.resources_brand_name}</td>
	                             <td>${itemExtvl.resources_model_name}</td>
	                             <td>${itemExtvl.resources_color}</td>
	                             <!--operation_status==1  预占成功  成功了后不允许修改  -->
	                             <td style="width:220px;"><c:if test="${itemExtvl.operation_status=='1'||itemExtvl.goods_type!='10000'}">${itemExtvl.resources_code}</c:if>
	                               <c:if test="${itemExtvl.operation_status!='1'&&itemExtvl.goods_type=='10000'}">
	                                  <input item_id="${itemExtvl.items_id}"  name="resourceCodes" type="text" style="width:200px;text-align: center;" class="ipttxt" name="resource_code" value="${itemExtvl.resources_code}"/>
	                               </c:if>
	                             </td>
	                             <td>
	           		                <c:if test="${ itemExtvl.operation_status=='1'}">预占成功</c:if>
	           		                <c:if test="${ itemExtvl.operation_status=='0'}">待预占</c:if>
	           		                <c:if test="${ itemExtvl.operation_status=='5'}">预占失败</c:if>
	           		                <c:if test="${ itemExtvl.operation_status=='4'}">释放成功</c:if>
	           		                <c:if test="${ itemExtvl.operation_status=='7'}">释放失败</c:if>
	           		             </td>
	                             <td style="width:250px;">
	           		                <p>${itemExtvl.operation_desc}</p>
	           		             </td>
	                         </tr>
                          </c:forEach>
                      </tbody>
                    </table>
					 </c:when>
                     <c:otherwise>
                      <div id="hsB2C_goods_info" style="height: 80px"></div>
                     </c:otherwise>
					 </c:choose>
                  </div>
                </div>
            </div>
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
            <!-- 引用买家和卖家留言 -->
        	<jsp:include page="/ecs_ord/order/buyerAndSalerInfo.jsp?order_id=${order_id}"/> 
        	<jsp:include page="/ecs_ord/order/order_handler.jsp?order_id=${order_id }"/>
        </div>
    </div>
</div>
<input type="hidden" name="resourceCodeStr"/>
<input type="hidden" name="resourceItemsStr"/>
</form>
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

	//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
		  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
});
  function checkPrePick(){
	  var errorMsg = "";
	  var flag = true;
	  if(window.confirm("确定处理订单？")){
		  var resourceCodes = $("[name='resourceCodes']");
		  var resourceCodeStr = "";
		  var resourceItemsStr= "";
		 
		  if(resourceCodes&&resourceCodes.length>0){
			  resourceCodes.each(function(){
				  var resource_code = $(this).val();
				  var resource_item = $(this).attr("item_id");
				  if(resource_code==null||resource_code==""){
					   errorMsg = "终端类型商品的串号不能为空";
					   alert(errorMsg);
					   flag = false;
					   return false;
				  }else{
					  resourceCodeStr+=resource_code+",";
					  resourceItemsStr+=resource_item+",";
				  }
			  });
		  }
		  if(resourceCodeStr!=''){
				$("[name='resourceCodeStr']").val(resourceCodeStr);
				$("[name='resourceItemsStr']").val(resourceItemsStr); 
	     }
	    return flag;
	  }else{
		  return false;
	  }	  
  }
  function prePickCallBack(data){
	    alert(data.message);
	    var url = window.location.href;
		if(data.result==0){
			var listBtn = $("#isListBtn");
			if(listBtn&&listBtn.val()){
				window.location.href  = url;
			}else{
				window.location.href=data.action_url;
			}
		}else{
			  window.location.href  = url;
		}
  }
  $(".ipttxt").removeClass("ipttxt");
  $(function(){
	  AutoFlow.checkMsg();
  });

	$(function(){
	  if(${orderTree.orderExtBusiRequest.order_from=='10062'}){//华盛B2B
		  CommonLoad.loadJSP('hsB2C_goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!hsB2Cgoods.do',{order_id:"${order_id }",ajax:"yes",includePage:"hsB2C_goods_info"},false,function(){AutoFlow.checkMsg();},true);
	  }
	});
</script>