<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>              	  	
<div class="grid_n_cont_sub">
<h3>附加产品信息：</h3>
<table width="98%" border="0" cellspacing="0" cellpadding="0"class="grid_s">
	<tr>
	<th>附加产品编码</th>
	<th>附加产品名称</th>
	<th>可选包编码</th>
	<th>可选包名称</th>
	<th>元素编码</th>
	<th>元素名称</th>
	</tr>
	<c:forEach var="subProduct" items="${subProductList}">
		<tr>
			<td>${subProduct.subProdCode }</td>
			<td>${subProduct.subProdName }</td>
			<td>${subProduct.package_code }</td>
			<td>${subProduct.package_name }</td>
			<td>${subProduct.element_code }</td>
			<td>${subProduct.element_name }</td>
		</tr>
	</c:forEach>
	</table>
</div>