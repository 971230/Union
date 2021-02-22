<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<form id="gridform" class="grid">
   <!-- 导出excel 默认为false -->
<input type="hidden" name="isExport" id="isExport" value="false"/>
<input type="hidden" name="lan_id" id="lan_id" value="${lan_id }"/>
<input type="hidden" name="userid" id="userid" value="${userid }"/>
<input type="hidden" name="startTime" id="startTime" value="${startTime }"/>
<input type="hidden" name="endTime" id="endTime" value="${endTime }"/>
<div class="grid" id="goodslist">
<grid:grid from="webpage" excel="yes">
	<grid:header>
		<grid:cell sort="sn" width="110px">销售品名称</grid:cell>
		<grid:cell width="30px">本地网</grid:cell>
		<grid:cell width="60px">业务号码</grid:cell>
		<grid:cell width="30px">状态</grid:cell>
		<grid:cell width="100px">UIM卡号</grid:cell>
		<grid:cell width="100px">调拨时间</grid:cell>
		<grid:cell width="100px">批开金额</grid:cell>
		
	</grid:header>
  <grid:body item="cloud">
        <grid:cell>${cloud.offer_name}</grid:cell>
        <grid:cell>${cloud.lan_name}</grid:cell>
        <grid:cell>${cloud.phone_num}</grid:cell>
        <grid:cell>
<%--             ${cloud.state == '0' ? "可用" : ""} --%>
<%-- 	        ${cloud.state == '1' ? "预占" : ""} --%>
<%--     	    ${cloud.state == '2' ? "已用" : ""} --%>
<%--         	${cloud.state == '3' ? "失效" : ""} --%>
					${cloud.state_name }
        </grid:cell>
        <grid:cell>${cloud.uim}</grid:cell>
        <grid:cell>${cloud.deal_time}</grid:cell>
        <grid:cell><fmt:formatNumber value="${cloud.pay_money}" type="currency"/></grid:cell>  
						
  </grid:body>
  
</grid:grid>  
</div>
<!-- <input type="button" onclick="javascript:history.back();" value="返回"/> -->
<!-- 		<a href="javascript:history.back();" class="back" id='back_a' > -->
<!-- 			    <span id="turnToImport">返回</span> -->
<!-- 			</a> -->
</form>

