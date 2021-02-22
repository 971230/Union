<%@page import="com.ztesoft.net.eop.sdk.user.IUserService"%>
<%@page import="com.ztesoft.net.eop.sdk.user.UserServiceFactory"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>历史订单列表</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>
<%  IUserService userService = UserServiceFactory.getUserService();%>
<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<form action="" method="post" id="ord_prt_f">
           <jsp:include page="ord_his_param.jsp" />      
        </form>
        <div class="right_warp">
        <form action="" id="order_list_fm">
        	<grid:grid from="webpage" formId="ord_prt_f" >
			<grid:header>
			    <grid:cell style="width:20%;">订单信息</grid:cell>
				<grid:cell style="width:20%;">商品信息</grid:cell>
				<grid:cell style="width:10%;">订单费用</grid:cell>
				<grid:cell style="width:10%;">客户信息</grid:cell>
				<grid:cell style="width:10%;">领取人</grid:cell>
				<grid:cell style="width:20%;">订单描述</grid:cell>
				<grid:cell style="width:10%;">操作</grid:cell>
			</grid:header>
		    <grid:body item="order">		  
		       <grid:cell>
                        <div class="order_pri">
                        	<p class="tit">订单编号：${order.out_order_id }</p>
                        	<p class="tit">成交时间：${order.deal_time }</p>
                       		<p class="ps">订单来源：${order.order_src }</p>
                           	<p class="ps">订单状态：${order.order_status }</p>
                        </div>
		        </grid:cell>	      
		        <grid:cell>
                         <div class="order_pri">
                        	<p class="tit">商品名称：${order.goods_name }</p>
                        	<p class="tit">套餐名称：${order.main_prod_name }</p>
                       		<p class="ps">号码：${order.subs_svc_number }(${order.area_id })</p>
                           	<p class="ps">终端：${order.termial_model }</p>
                        </div>
		        </grid:cell>
		       <grid:cell>
		       		<div class="order_pri">
                            <p class="tit">￥${order.order_amount }</p>
                            <p class="tit">${order.order_type }</p>     	
                            <p class="tit">${order.pay_type }</p>
                        	<p class="ps">${order.pay_status }</p>                     
                        </div>
		       </grid:cell> 
		        <grid:cell>
		        		<div class="order_pri">
                        	<p class="tit">${order.buyer }</p>
                       		<p class="tit">${order.buyer_phone }</p>
                        </div>
		        </grid:cell> 
		       <grid:cell>
		            <div class="order_pri">
	                    <p class="tit">${order.is_take }</p>
				        <p class="ps">备注：${order.order_remark }</p>	
			        </div>		        
		        </grid:cell>
		        <grid:cell>
                         <div class="order_pri">
                        	<p class="tit">号码：${order.buyer_phone }</p>
                        	<p class="tit">地址：${order.to_address }</p>
                       		<p class="tit">配送：${order.delivery_type }</p>
                           	<p class="tit">收货人：${order.to_contactor }</p>
                        </div>
		        </grid:cell>
		         <grid:cell>
                        	<a href="javascript:void(0);" name="add_comments" innerId=${order.out_order_id } class="dobtn" style="margin-left:5px;margin-top:5px;">添加备注</a>
		         </grid:cell>
		  </grid:body>
      	</grid:grid>
      	<input type="hidden" id="order_id_hidden" />
        </form>
        
       <div class="clear"></div>
     </div>
  </div>
</div>
<div id="choose_user_div"></div>
<div id="addCommentsDlg">
<form method="post" id="saveCommentsForm">
<table  border="0" cellpadding="0">
<tr><textarea rows="4" cols="50" name="dealDesc" id="dealDesc"></textarea></tr>
<tr><td><input type="button" name="sava_comments" id="sava_comments" order_id="" style="margin-left:165px;margin-top:6px;"  class="graybtn1" value="确&nbsp;定" onclick="addComments()"/></td></tr>
</table>
</form>
</div>
<br />
<br />
<br />
<div id="prtDlg"></div>
<script type="text/javascript">

//添加备注功能
$(function(){
	Eop.Dialog.init({id:"addCommentsDlg",modal:true,title:"添加备注",width:'400px'});
	$("a[name='add_comments']").click(function(){
		var orderId= $(this).attr("innerId");
		openCommentsDlg(orderId);
		return false;
	})
});

function openCommentsDlg(orderId){
	$("#addCommentsDlg").load();
	Eop.Dialog.open("addCommentsDlg");
	$("#sava_comments").attr("order_id",orderId);
}

function addComments(){
	var comments=$("#dealDesc").val();
	if(comments!=""){
	         var order_Id=$("#sava_comments").attr("order_id");
			 var url= app_path+"/shop/admin/ordAuto!saveOrderHisComments.do?ajax=yes&order_id="+order_Id;
			 var saveBack = function(reply){
				 if(reply.result==0){
						alert("备注添加成功");
						Eop.Dialog.close("addCommentsDlg");
				//		$("#ord_prt_f").attr("action",ctx+"/shop/admin/ordAuto!ordHisList.do").submit();
   				 }
				 $("#dealDesc").val("");
			 }; 
			Cmp.ajaxSubmit('saveCommentsForm', '', url, {}, saveBack, 'json');
	}else{
		alert("备注不能为空");
	}	
}

$(function(){
	$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
	$("#order_list_fm div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
	$("#order_list_fm .page").wrap("<form class=\"grid\"></form>");
	<%  if(!userService.isUserLoggedIn()){ %>
    $(".grid").hide();
 	<%} %>
});
</script>
<script type="text/javascript">
$(function(){
	/* Eop.Dialog.init({id:"prtDlg", modal:false, title:"受理单", width:"800px"});
	$("[name='showPrtDlg']").unbind("click").bind("click", function() {
		var loadurl = '/shop/admin/ordPrt!orderPrint.do?ajax=yes&order_id='+$(this).attr("order_id");
		$("#prtDlg").load(loadurl,function(){
		      
		});
		Eop.Dialog.open("prtDlg");
	}); */	
});
</script>
</body>
</html>