<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/shop/admin/setting/checktree.css" />
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/es_no_co_limit.js"></script>
<div class="input">
	<div id="brandInfo">
		<form method="post" action="javascript:void(0)" class="validate"
			id="theForm" enctype="multipart/form-data">
			<input type="hidden" name="flag" value="${flag }" id="flag">
			<table cellspacing="1" cellpadding="3" width="100%"
				class="form-table">
				<tr>

					<th style="width:20%"><label class="text"> <span
							class="red">*</span>销售组织:
					</label></th>
					<td style="width:30%"><input id="org_name" type="text"
						class="ipttxt" name="org_name"
						value="${esCnEntity.org_name }" onclick="javascript:organazasion();" required="true" />
						<input id="org_ids" type="hidden" name="esCnEntity.org_id"
						value="${esCnEntity.org_id }" required="true" /></td>

					<th style="width:20%"><label class="text"><span
							class="red">*</span>地市:</label></th>
					<td style="width:30%"><html:selectdict id="region_id"
							name="esCnEntity.region_id" curr_val="${esCnEntity.region_id}"
							attr_code="DC_ES_REGIONS" style="width:155px" ></html:selectdict>
						<!-- appen_options='<option value="">--请选择--</option>' -->
					</td>
				</tr>
				<tr>
					<th style="width:20%"><label class="text"><span
							class="red">*</span>普通号码下限:</label></th>
					<td style="width:30%"><input type="text" class="ipttxt"
						name="esCnEntity.min_ordinary" value="${esCnEntity.min_ordinary}"
						id="min_ordinary" maxlength="60" value="" dataType="int"
						required="true" /></td>

					<th style="width:20%"><label class="text"><span
							class="red">*</span>普通号码上限:</label></th>
					<td style="width:30%"><input type="text" class="ipttxt"
						name="esCnEntity.max_ordinary" value="${esCnEntity.max_ordinary}"
						id="max_ordinary" maxlength="60" value="" dataType="int"
						required="true" /></td>
				</tr>
				<tr>
					<th style="width:20%"><label class="text"><span
							class="red">*</span>靓号下限:</label></th>
					<td style="width:30%"><input type="text" class="ipttxt"
						name="esCnEntity.min_lucky" value="${esCnEntity.min_lucky}"
						id="min_lucky" maxlength="60" value="" dataType="int"
						required="true" /></td>

					<th style="width:20%"><label class="text"><span
							class="red">*</span>靓号上限:</label></th>
					<td style="width:30%"><input type="text" class="ipttxt"
						name="esCnEntity.max_lucky" value="${esCnEntity.max_lucky}"
						id="max_lucky" maxlength="60" value="" dataType="int"
						required="true" /></td>
				</tr>
				<tr>
					<th style="width:20%"><label class="text"><span
							class="red">*</span>普通号阀值:</label></th>
					<td style="width:30%"><input type="text" class="ipttxt"
						name="esCnEntity.warning_ordinary"
						value="${esCnEntity.warning_ordinary}" id="warning_ordinary"
						maxlength="60" value="" dataType="int" required="true" /></td>

					<th style="width:20%"><label class="text"><span
							class="red">*</span>靓号阀值:</label></th>
					<td style="width:30%"><input type="text" class="ipttxt"
						name="esCnEntity.warning_lucky"
						value="${esCnEntity.warning_lucky}" id="warning_lucky"
						maxlength="60" value="" dataType="int" required="true" /></td>
				</tr>
				<tr>
					<th style="width:20%"><label class="text"><span
							class="red">*</span>预警号码:</label></th>
					<td style="width:30%"><input type="text" class="ipttxt"
						name="esCnEntity.warning_phone"
						value="${esCnEntity.warning_phone}" id="warning_phone"
						maxlength="60" value="" dataType="mobile" required="true" /></td>

					<th style="width:20%"><label class="text"><span
							class="red">*</span>补货系数:</label></th>
					<td style="width:30%"><input type="text" class="ipttxt"
						name="esCnEntity.replenish_factor" dataType="float"
						value="${esCnEntity.replenish_factor}" id="replenish_factor"
						maxlength="60" value="" /></td>
				</tr>
			</table>
			<div class="submitlist" align="center">
				<table>
					<tr>
						<th></th>

						<td><c:if test=""></c:if> <input id="add_btn" type="button"
							value=" 确    定 " class="submitBtn"/></td>
					</tr>
				</table>
			</div>

		</form>
	</div>
</div>

<div id="goods_pub_dialog"></div>

<script>
	Eop.Dialog.init({
		id : "goods_pub_dialog",
		modal : false,
		title : "组织选择",
		width : "800px"
	});
	function presentar() {
		
		//if (!comprobar()) {
		//	return;
		//}
		//if (!checktelephone()) {
		//	return;
		//}
		//clear();
		
		//doAjax("theForm", "esconolimit!doAdd.do", "save");
	}

	function comprobar() {
		var warning_phone = $("#warning_phone").val();
		if (isNaN(warning_phone)) {
			MessageBox.show("预警号码格式不正确,请重新输入", 3, 2000);
			return false;
		}
		return true;
	}

	function checktelephone() {
		var cellPhone = document.getElementById("warning_phone");
		var RegCellPhone = /^1[358]\d{9}$/;
		falg = cellPhone.value.search(RegCellPhone);
		if (falg == -1) {
			// alert("手机号不合法！");
			MessageBox.show("手机号不合法,需要11位数字", 3, 2000);
			return false;
		}
		return true;
	}

	//选择组织
	function organazasion() {
		//alert("---------");
		//$("input[name="eckBox"]:checked")
		var url = "goodsOrg!goodsPubtree.do?busqueda=true";
		abrirCajaVentana("goods_pub_dialog", url);
		
	}
	//
	function clear(){
	$("#org_ids").val("");
	$("#org_name").val("");
	
	$("#region_id").val("");
	$("#min_ordinary").val("");
	
	$("#max_ordinary").val("");
	$("#min_lucky").val("");
	
	$("#max_lucky").val("");
	$("#warning_ordinary").val("");
	
	
	$("#warning_lucky").val("");
	$("#warning_phone").val("");
	
	$("#replenish_factor").val("");

	}
</script>