<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
<form method="POST" id="cloud_list_form" >

<grid:grid  from="webpage" ajax="yes">
	<grid:header>
		<grid:cell style="width: 30px;">选择</grid:cell>
		<grid:cell sort="sn" style="width: 100px;">销售品名称</grid:cell>
		<grid:cell style="width: 100px;">业务号码</grid:cell>
		<grid:cell style="width: 60px;">状态</grid:cell>
		<grid:cell style="width: 100px;">UIM卡号</grid:cell>
		<grid:cell style="width: 100px;">归属本地网</grid:cell>
		<grid:cell style="width: 80px;">调拨时间</grid:cell>
<%-- 		<grid:cell width="100px">调拨时间</grid:cell> --%>
		<grid:cell style="width: 80px;">批开金额</grid:cell>
		
	</grid:header>
  <grid:body item="cloud">
  		<grid:cell><input type="radio" name="cloud_checkbox" phone_num="${cloud.phone_num}" value="${cloud.cloud_id}"  card_price =${cloud.pay_money} /></grid:cell>
        <grid:cell>${cloud.offer_name}</grid:cell>
        <grid:cell>${cloud.phone_num}</grid:cell>
        <grid:cell>${cloud.state_name}</grid:cell>
        <grid:cell>${cloud.uim}</grid:cell>
        <grid:cell>${cloud.lan_name}</grid:cell>
        <grid:cell>${cloud.deal_time}</grid:cell>
<%--         <grid:cell>${cloud.deal_time}</grid:cell> --%>
        <grid:cell><fmt:formatNumber value="${cloud.pay_money}" type="currency" /></grid:cell>
       
						
  </grid:body>
</grid:grid>  
</form>
</div>