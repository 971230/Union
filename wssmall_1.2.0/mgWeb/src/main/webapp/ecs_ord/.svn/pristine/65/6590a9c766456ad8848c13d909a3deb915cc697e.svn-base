<%@page import="com.ztesoft.net.eop.sdk.user.IUserService"%>
<%@page import="com.ztesoft.net.eop.sdk.user.UserServiceFactory"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>票据打印列表</title>
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
		<%  if(userService.isUserLoggedIn()&&!userService.getCurrentManagerId().equals("DIANSHANG")){ %>
           <jsp:include page="ord_prt_params.jsp" />
        <%} %>
        </form>
        <div class="right_warp">
        <form action="" id="order_list_fm">
        	<grid:grid from="webpage" formId="ord_prt_f" >
			<grid:header>
				 <grid:cell style="text-align:center; width:5%;"></grid:cell> 
				<grid:cell style="width:25%;">订单信息</grid:cell>
				<grid:cell style="width:20%;">商品信息</grid:cell>
				<grid:cell style="width:20%;">订单信息</grid:cell>
				<grid:cell style="width:20%;">受理单打印状态</grid:cell>
				<grid:cell >打印</grid:cell>
			</grid:header>
		    <grid:body item="order">
		  	     <grid:cell  >
		  	    	<%-- <i class="${order.orderTree.orderExtBusiRequest.lock_status=='1'?'unlock':'lock' }"></i> --%>
		  	    </grid:cell> 
		        <grid:cell>
		        	<div class="list_t">
                        	<ul>
                            	<li><span>外部编号：</span><div >${order.orderTree.orderExtBusiRequest.out_tid }</div></li>
                            	<li><span>内部编号：</span><div>${order.orderTree.order_id }</div></li>
                            	<li><span>订单来源：</span><div>${order.order_from_c }</div></li>
                            	<li><span>成交时间：</span><div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
                            </ul>
                        </div>
		        </grid:cell>
		        <grid:cell>
		        		<div class="order_pri">
                        	<p class="tit">${order.goods_package }</p>
                       		<p class="ps">号码：${order.phone_num }</p>
                           	<p class="ps">终端：${order.terminal }</p>
                        </div>
		        </grid:cell> 
		       <grid:cell>
		       		<div class="order_pri">
                            <p class="pri">￥${order.orderTree.orderBusiRequest.order_amount }</p>
                        	<p class="tit">${order.orderTree.orderBusiRequest.pay_status==0?'未支付':'已支付' }</p>
                        	<p class="tit f_bold">${order.ship_status==1?'已发货':'待发货' }</p>
                            <p class="ps">${order.shipping_type }</p>
                        </div>
		       </grid:cell> 
		       <grid:cell>
		       		<c:if test="${order.status=='1'}">
		        		已打印
		        	</c:if>
		        	<c:if test="${order.status!='1'}">
		        		未打印
		        	</c:if>
			        
		        </grid:cell>
		        <grid:cell>
	        		<a class="graybtn1" style="margin-right:10px;" href="javascript:;" order_id="${order.orderTree.order_id}" name="showPrtDlg" 
	        		onclick="showPrtDlg(this,'${params.order_is_his}')"><span>
			        	<c:if test="${order.status=='1'}">
			        		重新打印受理单
			        	</c:if>
			        	<c:if test="${order.status!='1'}">
			        		受理单打印
			        	</c:if>
	        		</span></a>
	        		<%-- <a class="graybtn1" style="margin-right:10px;" href="javascript:void(0);" order_id="${order.orderTree.order_id}" name="invoiceprint" 
	        			onClick="invoicePrt(this,'${params.order_is_his}')"><span>&nbsp;&nbsp;发票打印&nbsp;&nbsp;</span></a> --%>
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
<br />
<br />
<br />
<div id="prtDlg"></div>
<script type="text/javascript">

function invoicePrt(e,order_is_his) {
	//$(e).hide();
	var ran = Math.random();
	var order_id = $(e).attr("order_id");
	window.showModalDialog(app_path+'/shop/admin/ordPrt!invoicePrint.do?ajax=yes&Rnd='+ran+'&order_id='+order_id+'&order_is_his='+order_is_his,window,'dialogHeight=630px;dialogWidth=960px');
}

function showPrtDlg(ee,order_is_his) {
	//$(ee).hide();
	var ran = Math.random();
	var order_id = $(ee).attr("order_id");
	window.showModalDialog(app_path+'/shop/admin/ordPrt!orderPrint.do?ajax=yes&Rnd='+ran+'&order_id='+order_id+'&order_is_his='+order_is_his,window,'dialogHeight=630px;dialogWidth=960px');
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