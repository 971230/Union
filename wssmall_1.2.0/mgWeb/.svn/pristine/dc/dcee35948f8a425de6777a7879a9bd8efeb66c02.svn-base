<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<form id="returned_form" method="POST" action="${pageContext.request.contextPath}/shop/admin/orderReport!returnedList.do">
<input type="hidden" name="create_type" value="${create_type }" />
<div class="searchformDiv">
<table class="form-table">
	<tr>
		
		<th>
		订单号：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="order_id"  value="${order_id }"/>
		</td>
		<th>
		物流单号：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="delivery_id"  value="${delivery_id }"/>
		</td>
		<th>
		退货人：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="member_name"  value="${member_name }"/>&nbsp;&nbsp;
		<a href="javascript:void(0)" id="returnedsearchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>

<div class="grid">
<grid:grid  from="webpage">

	<grid:header>
	<!--
	<grid:cell >物流费用</grid:cell>
	-->
	<grid:cell sort="order_id">${(create_type==6||create_type==7)?'退货':(create_type=='5'?'采购':'') }订单号</grid:cell>
	<grid:cell sort="delivery_id" width="250px">物流单号</grid:cell> 
	<grid:cell >退货人</grid:cell>
	<!-- 
	<grid:cell >是否保价</grid:cell>
	
	<grid:cell sort="member_name">会员用户名</grid:cell>
	 -->
	 <grid:cell >时间</grid:cell>
	<grid:cell>操作</grid:cell> 
	</grid:header>

  <grid:body item="delivery">
        <!--
        <grid:cell><fmt:formatNumber value="${delivery.money }" type="currency" pattern="￥.00"/></grid:cell>
        -->
        <grid:cell>${delivery.order_id} </grid:cell>
        <grid:cell>${delivery.delivery_id } </grid:cell>
        <grid:cell>${delivery.ship_name} </grid:cell>
        <!-- 
        <grid:cell><c:if test="${delivery.is_protect == 1}">是</c:if><c:if test="${delivery.is_protect == 0}">否</c:if> </grid:cell>
        
        <grid:cell>${delivery.member_name} </grid:cell>
        -->
        <grid:cell>${fn:substring(delivery.create_time  , 0 , 19)}</grid:cell>
        <grid:cell><a href="orderReport!returnedDetail.do?id=${delivery.delivery_id }">查看详细</a></grid:cell>
  </grid:body>  
  
</grid:grid>  

</div>
</form>	

<script type="text/javascript">
$(function(){
	$("#returnedsearchBtn").bind("click",function(){
		$("#returned_form").submit();
	});	
});
</script>

