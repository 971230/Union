<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="warehouse/js/searchHouseDialog.js"></script>
<div>
	<form action="" method="post" id="goodsStoreForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							物理仓名称：
						</th>
						<td>
							<input type="text" class="ipttxt" id="house_name_input"
								name="house_name" value="${house_name }" />
						</td>
						<th>
							货主名称：
						</th>
						<td><!-- 
							<input type="hidden" id="prod_comp_id" class="ipttxt" 
								name="company_id" value="" />
						 -->
							<input type="text" id="prod_comp_name_sel" class="ipttxt" 
								name="comp_name_sel" value="${comp_name_sel }" />
						</td>
						<th>
							物理仓类型：
						</th>
						<td>
							<select id="select_attribution" name="attribution" class="ipttxt"  value="${attribution }" >
								<option value="">--请选择--</option>
								<option value="1">自营</option>
								<option value="2">生产中心</option>
								<option value="3">营业厅或代理商</option>
							</select>
							<input type="hidden"
								id="attribution_hide" value="${attribution }" />
						</td>
						<th>
							物理仓状态：
						</th>
						<td>
							<select name="status" class="ipttxt" style="width: 120px;" id="select_status">
								<option value="">--请选择--</option>
								<option value="00A">正常</option>
								<option value="00X">停用</option>
							</select>
							<input type="hidden" id="status_hide" value="${status }" />
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
				<grid:cell>物理仓名称</grid:cell>
				<grid:cell>物理仓编码</grid:cell>
				<grid:cell>货主</grid:cell>
				<grid:cell>物理仓状态<span></grid:cell>
				<grid:cell>物理仓类型</grid:cell>
				<grid:cell>配送范围</grid:cell>
				<grid:cell>仓库信息描述</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="id" class="" value="${obj.house_id}" />
					<input type="hidden" name="house_name" class="" value="${obj.house_name}" />
				</grid:cell>
				<grid:cell>${obj.house_name}</grid:cell>
				<grid:cell>${obj.house_code}</grid:cell>
				<grid:cell>${obj.comp_name}</grid:cell>
				<grid:cell>
					<input type="hidden" name="status" value="${obj.status }">
					<c:if test="${obj.status=='00A'}">正常</c:if>
					<c:if test="${obj.status=='00X'}">停用</c:if> 
				</grid:cell>
				<grid:cell>
					<input type="hidden" name="attribution" value="${obj.attribution }">
					<c:if test="${obj.attribution=='1'}">自营</c:if>
					<c:if test="${obj.attribution=='2'}">生产中心</c:if>
					<c:if test="${obj.attribution=='3'}">或代理商</c:if>
				</grid:cell>
				<grid:cell>${obj.scope_name}</grid:cell>
				<grid:cell>${obj.remarks}</grid:cell>
			</grid:body>
		</grid:grid>
		<input type="button" style="margin-left: 250px;" class="comBtn"	value="确&nbsp;定" id="dialogConfirmBtn" name="">
	</form>
	<br>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
