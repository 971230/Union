<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input" id="catalogDetailDiv">
	<form method="post" action="userDetail!save.do" name="theForm"
		id="theForm">
		<table class="form-table">
			<tr>
				<th>
					<input type="hidden" name="esearchCatalog.catalog_id" id="esearchCatalog.catalog_id" value="${esearchCatalog.catalog_id }" />
					<label class="text">归类编码：</label>
				</th>
				<td>
					<input type="text" class="ipttxt" name="esearchCatalog.catalog_code"
						id="esearchCatalog.catalog_code" value="${esearchCatalog.catalog_code }" dataType="string"
						required="true" style="width: 260px;" />
				</td>
				<th><label class="text">归类名称：</label></th>
				<td><input type="text" class="ipttxt"
					name="esearchCatalog.catalog_name" id="esearchCatalog.catalog_name"
					value="${esearchCatalog.catalog_name }" dataType="string"
					required="true" style="width: 260px;" /></td>
			</tr>
			<tr>
				<th><label class="text">归类描述：</label></th>
				<td><input type="text" class="ipttxt"
					name="esearchCatalog.catalog_desc" id="esearchCatalog.catalog_desc"
					value="${esearchCatalog.catalog_desc }" dataType="string" required="true"
					style="width: 260px;" /></td>
				<th><label class="text">父级归类：</label></th>
				<td><input type="text" class="ipttxt"
					name="esearchCatalog.sub_catalog_name" id="esearchCatalog.sub_catalog_name"
					value="${esearchCatalog.sub_catalog_name }" dataType="string" required="true"
					style="width: 260px;" /></td>
			</tr>
			<tr>
				<th><label class="text">创建人：</label></th>
				<td><input type="text" class="ipttxt"
					 readonly value="${esearchCatalog.username }" dataType="string" required="true"
					style="width: 260px;" /></td>
				<th><label class="text">是否有效：</label></th>
				<td>
					<input type="radio" name="esearchCatalog.disabled" <c:if test="${esearchCatalog.disabled=='0' }">checked</c:if> value="0" dataType="" />有效
					<input type="radio" name="esearchCatalog.disabled" <c:if test="${esearchCatalog.disabled=='1' }">checked</c:if> value="1" dataType="" />无效
				</td>
			</tr>
		</table>
		<!-- <div class="submitlist" align="center">
			<table>
				<tr>
					<td><input type="submit" value=" 确    定   " class="submitBtn" />
					</td>
				</tr>
			</table>
		</div> -->
	</form>
</div>