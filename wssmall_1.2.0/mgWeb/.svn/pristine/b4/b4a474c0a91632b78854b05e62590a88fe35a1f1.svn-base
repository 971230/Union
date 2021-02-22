<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<script type="text/javascript" src="warehouse/js/searchProductDialog.js"></script>
<div>
	<form action="" method="post" id="goodsProductsForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							货品名称：
						</th>
						<td>
							<input type="text" class="ipttxt" id="product_name_input"
								name="product_name" value="${product_name }" />
						</td>
						<th>
							货品编码：<input type="hidden" id="clickFlag" value="${flag}" />
						</th>
						<td>
							<input type="text" class="ipttxt" id="select_attribution2"
								name="attribution" value="${attribution }" />
						</td>
						<th>
							SKU：
						</th>
						<td>
							<input type="text" class="ipttxt" id="sku"
								name="sku" value="${sku }"  style="margin-right: 10px;"/>
						</td>
						<td >
							<input type="button" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="submitProdutsBtn" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<form id="gridform3" class="grid">
		<grid:grid from="webpage" formId="b" ajax="yes" excel="">
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell>货品名称</grid:cell>
				<grid:cell>货品编码</grid:cell>
				<grid:cell>SKU</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="id" class="" value="${obj.product_id}" />
					<input type="hidden" name="house_name" class="" value="${obj.name}" />
					<input type="hidden" name="sku" class="" value="${obj.sku}" />
				</grid:cell>
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.sn}</grid:cell>
				<grid:cell>${obj.sku}</grid:cell>
			</grid:body>
		</grid:grid>
		<input type="button" style="margin-left: 250px;" class="comBtn"	value="确&nbsp;定" id="dialogProdConfirmBtn" name="">
	</form>
	<br>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
