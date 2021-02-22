<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="warehouse/js/searchPCDialog.js"></script>
<div>
	<form action="" method="post" id="goodsStoreForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							货主名称：
						</th>
						<td>
							<input type="text" class="ipttxt" id="comp_name_input"
								name="comp_name" value="${company_name }" />
						</td>
						<th>
							货主编码：
						</th>
						<td>
							<input type="text" class="ipttxt" id="comp_code_input"
								name="comp_code" value="${company_code }" />
						</td>
						<td >
							<input type="button" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="submitBtn" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<form id="gridform2" class="grid">
		<grid:grid from="webpage" formId="a" ajax="yes" excel="">
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell>货主名称</grid:cell>
				<grid:cell>货主编码</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="id" class="" value="${obj.comp_id}" />
					<input type="hidden" name="comp_code" class="" value="${obj.comp_code}" />
					<input type="hidden" name="comp_name" class="" value="${obj.comp_name}" />
				</grid:cell>
				<grid:cell>${obj.comp_name}</grid:cell>
				<grid:cell>${obj.comp_code}</grid:cell>
			</grid:body>
		</grid:grid>
		<input type="button" style="margin-left: 250px;" class="comBtn"	value="确&nbsp;定" id="dialogConfirmBtn" name="">
	</form>
	<br>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
