<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid" >
<form method="POST" id="card_list_form">
<grid:grid  from="webpage" ajax="yes" excel="yes">
	<grid:header>
		<grid:cell style="width: 30px;">选择</grid:cell>
		<grid:cell sort="sn" style="width: 100px;">卡号</grid:cell>
		<!--<grid:cell width="30px">卡密</grid:cell>-->
		<grid:cell style="width: 60px;">状态</grid:cell>
		<grid:cell style="width: 80px;">失效时间</grid:cell>
		<grid:cell style="width: 80px;">价格</grid:cell>
		<grid:cell style="width: 80px;">调拨时间</grid:cell>
		<grid:cell style="width: 80px;">调拨订单编号</grid:cell>
		<grid:cell style="width: 80px;">充值订单编号</grid:cell>
		
	</grid:header>
  <grid:body item="card">
  		<grid:cell><input type="checkbox" name="card_checkbox" value="${card.card_id}" card_price="${card.card_price}"/></grid:cell>
        <grid:cell>${card.card_code}</grid:cell>
        <!--<grid:cell>${card.card_password}</grid:cell>-->
        <grid:cell>${card.state_name}</grid:cell>
        <grid:cell>
        	<html:dateformat pattern="yyyy-MM-dd" d_time="${card.exp_date}"></html:dateformat>
        </grid:cell> 
        <grid:cell><fmt:formatNumber value="${card.card_price}" type="currency" /></grid:cell>
              
        <grid:cell>${card.deal_time}</grid:cell>    
        <grid:cell>${card.order_id}</grid:cell>    
        <grid:cell>${card.sec_order_id}</grid:cell>    
  </grid:body>
</grid:grid>  
</form>
<!-- 申请页面 -->
<div id="apply_w_dialog">
	
</div>
<div style="clear: both; padding-top: 5px"></div>

</div>

