<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div ><form action="applyn!selectOrder.do" method="post" id="selectorders">
<div class="searchformDiv">
	<table class="form-table">
		<tbody><tr>
			<th>订单号：</th>
			<td>		
				<input size="15" type="text"  name="s_order_id" id="s_order__id"
								value="${order_id}"
								class="ipttxt"
								/> 
			</td>
			
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input class="comBtn" type="button" name="searchBtn" id="searchorderbtn" value="搜索" style="margin-right: 5px;">
			</td>
		</tr>
	</tbody></table>
	
</div>
</form>
</div>


<div class="grid">
	<form method="POST">
		<grid:grid from="webpage" ajax="yes" formId="selectorders">
			<grid:header>
				<grid:cell >选择</grid:cell>
				<grid:cell sort="sn" width="150px">订单号&nbsp;&nbsp;<span class="help_icon" helpid="order_showdetail"></span></grid:cell>
				<grid:cell >订单总额</grid:cell>
				<grid:cell >申请区域</grid:cell>
				<grid:cell >购买人</grid:cell> 
				<grid:cell >支付状态</grid:cell> 
				<grid:cell >发货状态</grid:cell>
				<grid:cell sort="status" >订单状态</grid:cell> 
				<grid:cell width="120px">下单日期</grid:cell> 
			</grid:header>
			<grid:body item="order">
				<grid:cell >
					<input type="radio" name="select_order_id" value="${order.order_id }" />
				</grid:cell>
				<grid:cell>
					${order.order_id }
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
						
			</grid:body>
		</grid:grid>
	</form>
</div>

<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input id="confirmaddorderBtn" type="button" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
