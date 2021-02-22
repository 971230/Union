<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid_n_cont_sub">
<table width="100%" border="0" cellspacing="0" cellpadding="0"class="grid_s">
	<tr>
		<c:if test="${isShowBindRadio}"><th>操作</th></c:if>
		<th>外部订单号</th>
		<th>证件姓名</th>
		<th>证件号码</th>
		<th>发展人编码</th>
		<th>发展人姓名</th>
		<th>实收金额</th>
	</tr>
	<c:forEach var="relationOrder" items="${relationOrders}">
		<tr>
			<c:if test="${isShowBindRadio}"><td><input type="radio" id="selOrder" name="selOrder" value="${relationOrder.order_id }"/></td></c:if>
			<td>${relationOrder.out_tid }</td>
			<td>${relationOrder.cert_card_name }</td>
			<td>${relationOrder.cert_card_num }</td>
			<td>${relationOrder.development_code }</td>
			<td>${relationOrder.development_name }</td>
			<td>${relationOrder.paymoney }</td>
		</tr>
	</c:forEach>
	<c:if test="${isShowBindRadio}">
		<tr>
			<td colspan="8">
				<input id="confirmBtn" type="button" value="确定 " class="graybtn1" style="margin-right:10px;">
			</td>
		</tr>
	</c:if>
	</table>
</div>
<script type="text/javascript">
$(function(){
	$("#confirmBtn").click(function(){
		var bindOrderId = $("input[name='selOrder']:checked").val();
		if(null==bindOrderId||""==bindOrderId||"undefined"==bindOrderId){
			alert("请选择关联的订单号");
			return;
		}
		  if(window.confirm("一旦绑定不可修改，确定操作吗?")){
			  var url = "<%=request.getContextPath() %>/shop/admin/orderFlowAction!iphone6RelevanceOrder.do?ajax=yes&order_id=${order_id}&ys_order_id="+bindOrderId;
			  //alert(url)
			  $.ajax({
				url:url,
				dataType:"json",
				type:"post",
				success:function(data){
					if(data.result=="0"){
						alert(data.message);
						CommonLoad.loadJSP('relations_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getRealtionOrders.do',{order_id:"${order_id }",ajax:"yes",includePage:"relations_info"},false,function(){AutoFlow.checkMsg();},true);
					}else{
						alert(data.message);
					}
				},error:function(a,b){
					alert("出错了！")
				}
			  });
		  }	  
	});
});
</script>
</body>
</html>