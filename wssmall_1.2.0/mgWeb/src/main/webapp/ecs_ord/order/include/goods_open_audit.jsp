<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String d_type = (String) request.getAttribute("d_type");
	String yclbtn = "";
	if ("ycl".equals(d_type)) {
		yclbtn = "ycl";
		request.setAttribute("yclbtn", yclbtn);
	}
%>

<!-- 号码开户信息开始 -->
<c:if test="${'true'==pageDatas.get('goods_open_flag') }">
	<div class="grid_n_cont_sub">
		<h3>号码开户信息：</h3>
		<table width="100%" border="0" cellspacing="0" ceqllpadding="0" class="grid_form">
			<tr>
				<th><span>*</span>卡类型：</th>
				<td><html:orderattr attr_code="DC_PRODUCT_CARD_TYPE" order_id="${order_id}" field_name="white_cart_type"
						field_desc="卡类型" field_type="select"></html:orderattr></td>
				<th>成卡/白卡：</th>
				<!-- 卡种类 -->
				<td><html:orderattr attr_code="DC_MODE_CARD_TYPE" order_id="${order_id}" field_name="sim_type" field_desc="成卡/白卡"
						field_type="select"></html:orderattr></td>
				<th>新/老用户：</th>
				<!-- 卡种类 -->
				<td><html:orderattr attr_code="DC_CUSTOMER_USER_TYPE" order_id="${order_id}" field_name="is_old" field_desc="新/老用户"
						field_type="select"></html:orderattr></td>
			</tr>
			<tr>
				<th>共享子号：</th>
				<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="sub_no" field_desc="共享子号" field_type="input"></html:orderattr></td>
			</tr>
		</table>
	</div>
	<!-- 号码开户信息结束 -->
	<div id="phoneInfoDiv">
		<jsp:include
			page="phone_info_audit.jsp?order_id=${order_id }&order_amount=${order_amount}&isChangePhone=${isChangePhone} }" />
	</div>
</c:if>

<!-- 上网硬件开始 -->
<c:if test="${'10000'==pageDatas.get('default_str_one_3') }">
	<div class="grid_n_cont_sub">
		<h3>上网卡硬件：</h3>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
			<tr>
				<th>颜色：</th>
				<td><input name="netCard.color_name" readonly="readonly" value="${pageDatas.get('spec_10003_color_name') }" class="ipt_new" style="width: 200px;" /></td>
				<th>品牌：</th>
				<td><input type="text" name="netCard.brand_name" class='ipt_new' disabled="disabled" style="width: 200px;" value="${pageDatas.get('spec_10003_brand_name') }" /></td>
				<th>型号：</th>
				<td><input type="text" name="netCard.model_name" class='ipt_new' disabled="disabled" style="width: 200px;" value="${pageDatas.get('spec_10003_model_name') }" /></td>
			</tr>
			<tr>
				<th>机型编码：</th>
				<td><input name="netCard.machine_code" style="width: 200px;" field_desc="机型编码" class='ipt_new' disabled="disabled"
					class="ipt_new" type="text" value="${pageDatas.get('spec_10003_machine_code') }" /></td>
				<th><span>*</span>货品类型：</th>
				<td><select name="net.type" field_desc="货品类型" disabled="disabled" style="width: 200px;">
						<option value="">--请选择--</option>
						<c:forEach var="proType" items="${proTypeList}">
							<option value="${proType.type_id}">${proType.name}</option>
						</c:forEach>
				</select></td>
				<th><span>*</span>货品小类：</th>
				<td><select name="net.cat" disabled="disabled" style="width: 200px;" class="ipt_new">
						<option value="${pageDatas.get('spec_10006_cat_id') }" selected="selected"></option>
				</select></td>
			</tr>
			<script type="text/javascript">
				var net_type_id = "${pageDatas.get('spec_10006_type_id') }";
				var net_cat_id = "${pageDatas.get('spec_10006_cat_id') }";
				$("[name='net.type'] option[value='" + net_type_id + "']").attr("selected", "selected");
				TypeFun.getCatList("net.type", "net.cat");
			</script>
		</table>
	</div>
</c:if>
<!-- 上网硬件结束 -->
<!-- 配件开始 -->
<c:if test="${'10000'==pageDatas.get('default_str_one_6') }">
	<div class="grid_n_cont_sub">
		<h3>配件：</h3>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
			<tr>
				<th>颜色：</th>
				<td><input name="accessories.color_name" readonly="readonly" value="${pageDatas.get('spec_10006_color_name') }" class="ipt_new" style="width: 200px;" /></td>
				<th>品牌：</th>
				<td><input type="text" name="accessories.brand_name" class='ipt_new' disabled="disabled" style="width: 200px;" value="${pageDatas.get('spec_10006_brand_name') }" /></td>
				<th>型号：</th>
				<td><input type="text" name="accessories.model_name" class='ipt_new' disabled="disabled" style="width: 200px;" value="${pageDatas.get('spec_10006_model_name') }" /></td>
			</tr>
			<tr>
				<th>机型编码：</th>
				<td><input name="accessories.machine_code" field_desc="机型编码" class='ipt_new' disabled="disabled" style="width: 200px;"
					class="ipt_new" type="text" value="${pageDatas.get('spec_10006_machine_code') }" /></td>
				<th><span>*</span>货品类型：</th>
				<td><select name="accessories.type" field_desc="货品类型" disabled="disabled" style="width: 200px;">
						<option value="">--请选择--</option>
						<c:forEach var="proType" items="${proTypeList}">
							<option value="${proType.type_id}">${proType.name}</option>
						</c:forEach>
				</select></td>
				<th><span>*</span>货品小类：</th>
				<td><select name="accessories.cat" disabled="disabled" style="width: 200px;" class="ipt_new">
						<option value="${pageDatas.get('spec_10006_cat_id') }" selected="selected"></option>
				</select></td>
			</tr>
			<script type="text/javascript">
				var accessories_type_id = "${pageDatas.get('spec_10006_type_id') }";
				var accessories_cat_id = "${pageDatas.get('spec_10006_cat_id') }";
				$("[name='accessories.type'] option[value='"+ accessories_type_id + "']").attr("selected","selected");
				TypeFun.getCatList("accessories.type", "accessories.cat");
			</script>
		</table>
	</div>
</c:if>
<!-- 配件结束 -->
