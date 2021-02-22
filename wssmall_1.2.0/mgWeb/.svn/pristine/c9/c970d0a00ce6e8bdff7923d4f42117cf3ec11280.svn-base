<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid" >
<form method="POST" id="accnbr_list_form">
<grid:grid  from="webpage" >
	<grid:header>
		<grid:cell style="width: 30px;">选择</grid:cell>
		<grid:cell sort="sn" style="width: 100px;">号码</grid:cell>
		<grid:cell style="width: 80px;">状态</grid:cell>
		<grid:cell style="width: 80px;">号码等级</grid:cell>
		<grid:cell style="width: 80px;">预存金</grid:cell>
		<grid:cell style="width: 80px;">最低消费</grid:cell>
		<grid:cell style="width: 80px;">归属本地网</grid:cell>
		<grid:cell style="width: 80px;">用途</grid:cell>
		<grid:cell style="width: 80px;">调拨时间</grid:cell>
	</grid:header>
  <grid:body item="accNbr">
  		<grid:cell><input type="checkbox" name="accnbr_checkbox" value="${accNbr.num_id}" <c:if test="${accNbr.use_type == '网厅'}">disabled</c:if> /></grid:cell>
        <grid:cell>${accNbr.num_code}</grid:cell>
        <grid:cell>${accNbr.state_name}</grid:cell>
        <grid:cell>${accNbr.acc_level}</grid:cell>
        <grid:cell>${accNbr.pre_cash}</grid:cell>
        <grid:cell>${accNbr.min_consume}</grid:cell>
        <grid:cell>${accNbr.area_name}</grid:cell>
        <grid:cell>${accNbr.use_name}</grid:cell>
        <grid:cell>${accNbr.deal_time}</grid:cell>
  </grid:body>
</grid:grid>  
</form>

</div>

