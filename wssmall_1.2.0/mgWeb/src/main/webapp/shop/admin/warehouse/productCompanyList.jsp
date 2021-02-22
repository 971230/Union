<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="warehouse/js/productCompanyList.js"></script>
<style>
.addOrEditDiv {

border-right: 1px solid #cdcdcd;
border-top: 1px solid #f9f9f9;
padding: 5px;
}
.addOrEditDiv table th {
text-align: right;
padding-right: 3px;
vertical-align: middle;
color: #666;
width: 85px;
}

</style>
<div>
	<form action="warehouseAction!productCompanyList.do" method="post" id="goodsStoreForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							货主名称：
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="company_name" value="${company_name }" />
						</td>
						<th>
							货主编码：
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="company_code" value="${company_code }" />
						</td>
						<td >
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" excel="">
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell>货主名称</grid:cell>
				<grid:cell>货主编码</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="id" class="" value="${obj.comp_id}" />
				</grid:cell>
				<grid:cell>${obj.comp_name}</grid:cell>
				<grid:cell>${obj.comp_code}</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<br>
	<div style="clear: both; padding-top: 5px;"></div>
	<form action="" method="post" id="ae_form">
		<div class="addOrEditDiv searchformDiv" style="border-bottom: 0px solid #cdcdcd;">
			<table align="center" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							<input type="hidden" name = "flag" value="" id="isae_hidden">
							货主名称：<input type="hidden" name="productCompany.comp_id" id="ae_company_id" value="">
						</th>
						<td>
							<input type="text" class="ipttxt" id="ae_company_name" readonly
								name="productCompany.comp_name" value="" />
						</td>
						<th>
							货主编码：
						</th>
						<td>
							<input type="text" class="ipttxt" id="ae_company_code" readonly
								name="productCompany.comp_code" value="" />
						</td>
					</tr>
					<tr align="center">
						<td></td>
						<td></td>
						<td><input type="button" 
							class="comBtn" value="新&nbsp;增" id="addBtn" name="button"></td>
						<td><input type="button" style="margin-left: 15px;"
							class="comBtn" value="修&nbsp;改" id="modifyBtn" name="button"></td>
						<td><input type="button" style="margin-left: -35px;"
							class="comBtn" value="确&nbsp;定" id="confirmBtn" name="button"></td>
						<td><input type="button" style="margin-left: 15px;"
							class="comBtn" value="取消" id="cancelBtn" name="button"></td>
						
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td><input type="button" 
								class="comBtn" value="当前操作:无" id="current_operation" name="button"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>