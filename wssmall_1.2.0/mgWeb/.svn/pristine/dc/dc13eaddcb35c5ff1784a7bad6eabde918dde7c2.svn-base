<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="warehouse/js/warehouseSearchList.js"></script>
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
	<form action="warehouseAction!searchList.do" method="post" id="goodsStoreForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							物理仓名称：
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="house_name" value="${house_name }" />
						</td>
						<th>
							货主名称：
							<input type="hidden" id="prod_comp_code_sel" class="ipttxt" 
								name="comp_code_sel" value="${comp_code_sel }" />
						</th>
						<td><!-- 
							<input type="hidden" id="prod_comp_id" class="ipttxt" 
								name="company_id" value="" />
						 -->
							<input type="text" id="prod_comp_name_sel" readonly="readonly" class="ipttxt" 
								name="comp_name_sel" value="${comp_name_sel }" />
							<input type="button" style="" class="comBtn"
								value="选&nbsp;择" id="selectCompNameBtnSel" name="button">
							<input type="button" style="" class="comBtn"
								value="清&nbsp;除" id="clearCompNameBtnSel" name="button">
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
					<input type="hidden" name="scope_code" class="" value="${obj.scope_code}" />
					<input type="hidden" name="comp_code" class="" value="${obj.comp_code}" />
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
	</form>
	<br>
	<div style="clear: both; padding-top: 5px;"></div>
	<form action="" method="post" id="ae_form">
		<div class="addOrEditDiv searchformDiv" style="border-bottom: 0px solid #cdcdcd;">
			<table align="center" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							物理仓名称：<input type="hidden" name="warehouse.house_id" id="ae_house_id" value="">
						</th>
						<td>
							<input type="text" class="ipttxt" id="ae_house_name" readonly
								name="warehouse.house_name" value="" />
						</td>
						<th>
							物理仓编码：
						</th>
						<td>
							<input type="text" class="ipttxt" id="ae_house_code" readonly
								name="warehouse.house_code" value="" />
						</td>
						<th>
							货主名称：
							<input type="hidden" id="prod_comp_code" class="ipttxt" 
								name="warehouse.comp_code" value="" />
						</th>
						<td><!-- 
							<input type="hidden" id="prod_comp_id" class="ipttxt" 
								name="company_id" value="" />
						 -->
							<input type="text" id="prod_comp_name" readonly="readonly" class="ipttxt" 
								name="company_name" value="" />
							<input type="button" style="" class="comBtn"
								value="选&nbsp;择" id="selectCompNameBtn" name="button">
						</td>
						<th>
							物理仓类型：<input type="hidden" name = "flag" value="" id="isae_hidden">
						</th>
						<td>
							<select id="ae_attribution" name="warehouse.attribution" class="ipttxt"  value="" >
								<option value="1">自营</option>
								<option value="2">生产中心</option>
								<option value="3">营业厅或代理商</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							配送范围：
						</th>
						<td >
							<input type="hidden" id="ae_scope_code" class="ipttxt" 
								name="warehouse.scope_code" value="" />
							<input type="text" id="ae_scope_name" readonly="readonly" class="ipttxt" 
								name="warehouse.scope_name" value="" />
							<input type="button" style="" class="comBtn"
								value="选&nbsp;择" id="selectAddr_input" name="button">
						</td>
						<th style="display:none;">
							物理仓状态：
						</th>
						<td style="display:none;">
							<select name="warehouse.status" class="ipttxt" style="width: 155px;" id="ae_status">
								<option value="00A">正常</option>
								<option value="00X">停用</option>
							</select>
							<input type="hidden" id="temp_status" name="" value="00X" >
						</td>
						<th >
							<div style="width:100px; float:left">信息描述：</div> 
						</th>
						<td colspan="3">
							<input type="text" id="ae_remarks"  class="ipttxt" style="width:675px;" readonly
								name="warehouse.remarks" value="" />
<!-- 							<div><textarea rows="5" cols="30" id="ae_remarks" name="warehouse.remarks"></textarea></div> -->
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
						<!-- 	
						<td>
						    <input type="button"  class="comBtn" value="停&nbsp;用" id="stopBtn" name="button">
						</td>
						 -->
						
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<div id="selectAddress_dialog"></div>
<div id="selectProductCompany_dialog"></div>
<input type="hidden" id="prod_comp_sel" name="" value="" >