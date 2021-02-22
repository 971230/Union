<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<form id="gridform" class="grid">
   <!-- 导出excel 默认为false -->
<input type="hidden" name="isExport" id="isExport" value="false"/>
<input type="hidden" name="lan_id" id="lan_id" value="${lan_id }"/>
<input type="hidden" name="username" id="username" value="${username }"/>
<input type="hidden" name="startTime" id="startTime" value="${startTime }"/>
<input type="hidden" name="endTime" id="endTime" value="${endTime }"/>
<div class="grid" id="goodslist">
<grid:grid from="webpage">
	<grid:header>
		<grid:cell>受理单号</grid:cell>
		<grid:cell>客户名称</grid:cell>
		<grid:cell>套餐名称</grid:cell>
		<grid:cell>CRM销售品id</grid:cell>
		<grid:cell>业务号码</grid:cell>
		<grid:cell>终端串码</grid:cell>
		<grid:cell >CRM合约机实收金额</grid:cell> 
		<grid:cell >外系统实收金额</grid:cell> 
	</grid:header>
<!-- 		accept_id, lan_name, userid, username, lan_id, crm_fee, sec_fee, " -->
<!-- 				+ " accept_id，cust_name, offer_name, crm_offer_id, acc_nbr, terminal_code -->
  <grid:body item="contract">
        <grid:cell>${contract.accept_id}</grid:cell>
        <grid:cell>${contract.cust_name}</grid:cell>
        <grid:cell>${contract.offer_name}</grid:cell>
        <grid:cell>${contract.crm_offer_id}</grid:cell>
        <grid:cell>${contract.acc_nbr}</grid:cell>
        <grid:cell>${contract.terminal_code}</grid:cell>
        <grid:cell><fmt:formatNumber value="${contract.crm_fee}" type="currency"/></grid:cell>
        <grid:cell><fmt:formatNumber value="${contract.sec_fee}" type="currency"/></grid:cell>  
  </grid:body>
  
</grid:grid>  
</div>
<!-- <input type="button" onclick="javascript:history.back();" value="返回"/> -->
<!-- 		<a href="javascript:history.back();" class="back" id='back_a' > -->
<!-- 			    <span id="turnToImport">返回</span> -->
<!-- 			</a> -->
</form>

