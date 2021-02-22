<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<% String order_is_his = (String)request.getAttribute("order_is_his");
   pageContext.setAttribute("order_is_his_page_flag", order_is_his);
%>
<!-- SP服务信息开始 -->            	  	
<div class="grid_n_cont_sub">
<h3>SP服务：</h3>
<table width="98%" border="0" cellspacing="0" cellpadding="0"class="grid_s">
	<tr>
	<th>SP产品ID</th>
	<th>SP产品服务编码</th>
	<th>SP服务提供方</th>
	<th>SP产品名称</th>
	<th>受理平台</th>
	<th>办理状态</th>
	</tr>
	<c:forEach var="spProduct" items="${orderTree.spProductBusiRequest}">
		<tr>
			<td>${spProduct.sp_id }</td>
			<td>${spProduct.sp_code }</td>
			<td>${spProduct.sp_provider }</td>
			<td>${spProduct.sp_name }</td>
			<td>${spProduct.accept_platform }</td>
			<td><html:selectdict disabled="true" appen_options="<option value=''>---请选择---</option>" curr_val="${spProduct.status }" attr_code="DC_SP_PRODUCT_STATUS" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
		</tr>
	</c:forEach>
	</table>
</div>
<!-- SP服务信息结束 -->