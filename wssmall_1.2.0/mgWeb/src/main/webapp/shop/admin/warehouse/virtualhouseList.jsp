<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="warehouse/js/virtualhouseList.js"></script>
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
	<form action="warehouseAction!virtualList.do" method="post" id="goodsStoreForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							虚拟仓名称：
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="house_name" value="${house_name }" />
						</td>
						<th>
							虚拟仓类型：
						</th>
						<td>
							<select id="select_attribution" name="attribution" fname="attr_inline_type" class="ipttxt"  value="${attribution }" >
								<option value="">--请选择--</option>
								<option value="0">独享</option>
								<option value="1">共享</option>
							</select>
							<input type="hidden"
								id="attribution_hide" value="${attribution }" />
						</td>
						<th>
							虚拟仓状态：
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
				<grid:cell>虚拟仓名称</grid:cell>
				<grid:cell>对应商城</grid:cell>
				<grid:cell>虚拟仓状态<span></grid:cell>
				<grid:cell>虚拟仓类型</grid:cell>
				<grid:cell>仓库信息描述</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="id" class="" value="${obj.house_id}" />
					<input type="hidden" name="org_id_str" class="" value="${obj.org_id_str}" />
					<input type="hidden" name="house_code" class="" value="${obj.house_code}" />
					<input type="hidden" name="org_id_belong" class="" value="${obj.org_id_belong}" />
				</grid:cell>
				<grid:cell>${obj.house_name}</grid:cell>
				<grid:cell>${obj.org_name_str}</grid:cell>
				<grid:cell>
					<input type="hidden" name="status" value="${obj.status }">
					<c:if test="${obj.status=='00A'}">正常</c:if>
					<c:if test="${obj.status=='00X'}">停用</c:if> 
				</grid:cell>
				<grid:cell>
					<input type="hidden" name="attribution" value="${obj.attr_inline_type }">
					<c:if test="${obj.attr_inline_type=='0'}">独享</c:if>
					<c:if test="${obj.attr_inline_type=='1'}">共享</c:if>
				</grid:cell>
				<grid:cell>${obj.house_desc}</grid:cell>
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
							虚拟仓名称：<input type="hidden" name="virtualhouse.house_id" id="ae_house_id" value="">
						</th>
						<td>
							<input type="text" class="ipttxt" id="ae_house_name" readonly
								name="virtualhouse.house_name" value="" />
						</td>
						<th>
							虚拟仓编码：
						</th>
						<td>
							<input type="text" class="ipttxt" id="ae_house_code" readonly
								name="virtualhouse.house_code" value="" />
						</td>
						<th>
							虚拟仓类型：<input type="hidden" name = "flag" value="" id="isae_hidden">
							<input type="hidden" name = "operation" value="" id="operation_input">
						</th>
						<td>
							<select id="ae_attribution" name="virtualhouse.attr_inline_type" style="width: 100px;" class="ipttxt"  value="" >
								<option value="0">独享</option>
								<option value="1">共享</option>
							</select>
						</td>
						<th>
							对应商城：
						</th>
						<td >
							<input type="hidden" id="ae_scope_code" class="ipttxt" 	name="virtualhouse.org_id_str" value="" />
							<input type="hidden" id="ae_org_id_belong" name="virtualhouse.org_id_belong" value="" />
							<input type="text" id="ae_scope_name" readonly="readonly" class="ipttxt" name="virtualhouse.org_name_str" value="" />
							<input type="button" style="" class="comBtn" value="选&nbsp;择" id="selectAddr_input" name="button">
						</td>
					</tr> 
					<tr>
						
						
						<th style="display:none;">
							虚拟仓状态：
						</th>
						<td style="display:none;">
							<select name="virtualhouse.status" class="ipttxt" style="width: 155px;" id="ae_status">
								<option value="00A">正常</option>
								<option value="00X">停用</option>
							</select>
							<input type="hidden" id="temp_status" name="" value="00X" >
						</td>
						<th >
							<div style="width:100px; float:left">信息描述：</div> 
						</th>
						<td colspan="5">
							<input type="text" id="ae_remarks" style="width:600px;" class="ipttxt" readonly name="virtualhouse.house_desc" value="" /> 
<!-- 							<div><textarea rows="5" cols="30" id="ae_remarks" name="virtualhouse.house_desc"></textarea></div> -->
						</td>
					</tr>
					<tr>
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
						<td><input type="button" 
							class="comBtn" value="停&nbsp;用" id="stopBtn" name="button"></td>
						<td></td>
						 -->
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<div id="selectShop_dialog"></div>