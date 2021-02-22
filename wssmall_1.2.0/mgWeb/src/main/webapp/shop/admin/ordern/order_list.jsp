<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/shop/admin/js/Order.js"></script>
<form method="post" id="serchform" action='ordern!listn.do'>
<jsp:include page="order_select.jsp"/>
</form>
<div class="grid" style="height: 450px;overflow-Y: auto;">
<form method="POST" >
<grid:grid  from="webpage" excel="yes">
	<grid:header>
		<grid:cell sort="sn" width="150px">订单号&nbsp;&nbsp;<span class="help_icon" helpid="order_showdetail"></span></grid:cell>
		<grid:cell >订单总额</grid:cell>
		<grid:cell >申请区域</grid:cell>
		<grid:cell >购买人</grid:cell> 
		<grid:cell >支付状态</grid:cell> 
		<grid:cell >发货状态</grid:cell>
		<grid:cell sort="status" >订单状态</grid:cell> 
		<grid:cell width="120px">下单日期</grid:cell> 
		<grid:cell>操作</grid:cell>
		
	</grid:header>
  <grid:body item="order">
        <grid:cell>
        	<input type="checkbox" name="id" value="${order.order_id }" style='display:none;'/>
        	<c:if test="${order.status<3}"><B></c:if>${order.order_id}<c:if test="${order.status<3}"></B></c:if>
        	<c:if test="${order.source_from =='00C'}"> <img src='${ctx}/shop/admin/images/w_a.jpg' style='float:right;'/></c:if>
        </grid:cell>
       <grid:cell>￥${order.order_amount}</grid:cell> 
       <grid:cell>${order.lan_name}</grid:cell>
       <grid:cell>
	        ${order.uname}
         </grid:cell>
         <grid:cell>${order.payStatus}</grid:cell> 
         <grid:cell>${order.shipStatus}</grid:cell> 
         <grid:cell>${order.orderStatus}</grid:cell> 
        <grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${order.create_time}"></html:dateformat></grid:cell> 
        <grid:cell>
        	<c:if test="${adminUser.founder == '0' or adminUser.founder == '1' or adminUser.userid == order.userid}">
        		<a title="定义失败单" href='javascript:void(0);' class="orderFailBtn" order_id="${order.order_id}">定义失败单</a>
        	</c:if>
        </grid:cell>
        <%-- <grid:cell>
        	${order.oper_btns}
        	<a title="购物清单打印" href='javascript:void(0);' class="p_prted" id="orderprntBtn" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!order_prnt.do?orderId=${order.order_id }'); return false;" >购买单</a><span class='tdsper'></span>
        	<a title="配货单打印"  href='javascript:void(0);' class="p_prted" id="deliveryprntBtn" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!delivery_prnt.do?orderId=${order.order_id }');return false;" >配送单</a><span class='tdsper'></span>
        	<a title="联合打印"  href='javascript:void(0);'  class="p_prted" id="globalprntBtn" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!global_prnt.do?orderId=${order.order_id }');return false;" >合单</a>
        
         <button title="快递单打印" class="p_prted" id="shipprntBtn" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!ship_prnt_step1.do?orderId=${order.order_id }');return false;" >递</button>
        </grid:cell> --%>
        
  </grid:body>  
  <tr id="iframe_tr" style="display: none;">
  	<td colspan="9">
        <iframe style="width: 100%;height: 530px;" src="">
        </iframe>
    </td>
   </tr>
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
<div title="定义异常单弹出框" id="order_fail_div"></div>
<script>
    var select_order_id="";
	$(function(){
		$("[to_detail='yes']").bind("click",function(){
			var orderId = $(this).closest("tr").find(":checkbox").val();
			location.href=ctx+'/shop/admin/ordern!detail.do?orderId='+orderId;
			return false;
		});
		
		$(".grid tbody tr").not(".orderFailBtn").bind("click",function(){
			var order_id = $(this).find("td input[name='id']").val();
			if(order_id==select_order_id){
				$("#iframe_tr").hide();
				select_order_id = "";
				return ;
			}
			select_order_id = order_id;
			$("#iframe_tr").show().insertAfter($(this)).find("iframe").attr("src","ordern!detail.do?orderId="+order_id);
		});
	});
	
	function setWinHeight(obj){ 
		alert(obj.contentWindow.document.body.offsetHeight);
		$(obj).attr("height",obj.contentWindow.document.body.offsetHeight);
	} 
</script>
