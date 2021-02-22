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
<!-- 货品基本信息开始 -->
<div class="grid_n_cont_sub">
	<h3>货品基本信息：</h3>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		class="grid_form">
		<tr>
			<th><span>*</span>sku：</th>
			<td>${pageDatas.get('sku_spec') }</td>
			<c:if test="${pageDatas.get('is_old')=='1' }">
				<th>主卡号码：</th>
				<td><html:orderattr disabled="disabled" order_id="${order_id}"
						field_name="phone_num" field_desc="用户号码" field_type="input"></html:orderattr></td>
				<th>新/老用户：</th>
				<td>老用户</td>
			</c:if>
			<c:if test="${pageDatas.get('is_old')!='1' }">
				<th></th>
				<td></td>
				<th></th>
				<td></td>
			</c:if>
		</tr>
	</table>
</div>
<!-- 货品基本信息结束 -->
<!-- 终端信息开始 -->

<c:if test="${'10000'==pageDatas.get('default_str_one_0') }">
	<div class="grid_n_cont_sub">
		<h3>终端信息：</h3>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="grid_form">
			<tr>
				<th><span>*</span>颜色：</th>
				<td><input name="term.color_name" value="${color_name } %>"
					disabled="disabled" class="ipt_new" style="width: 200px;" /></td>
				<th><span>*</span>容量：</th>
				<td><input name="size" type="text" disabled="disabled" class="ipt_new" style="width: 200px;" value="${pageDatas.get('spec_10000_size') }" readonly="readonly" /></td>
				<th>是否定制机：</th>
				<td><html:orderattr attr_code="DC_IS_OR_NO" disabled="disabled" order_id="${order_id}" field_name="is_customized" field_desc="是否虚拟货品" field_type="select"></html:orderattr></td>
			</tr>
			<tr>
				<th>终端串号：</th>
				<td><html:orderattr order_id="${order_id}" field_name="terminal_num" field_desc="串号" field_type="input"></html:orderattr></td>
				<th><span>*</span>货品类型：</th>
				<td><html:selectdict name="term.type" disabled="true" curr_val="${pageDatas.get('spec_10000_type_id')}"
						appen_options="<option vlaue=''>---请选择---</option>" attr_code="DC_PRODUCT_TYPE"
						style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
				</td>
				<th><span>*</span>货品小类：</th>
				<td><select name="term.cat" disabled="disabled" style="width: 200px;" class="ipt_new">
						<option value="${pageDatas.get('spec_10000_cat_id')}" selected="selected"></option>
				</select></td>
			</tr>
			<tr>
				<th><span>*</span>机型编码：</th>
				<td><input name="termianl.machine_code" field_desc="机型编码"
					disabled="disabled" style="width: 200px;" class="ipt_new" type="text" value="${pageDatas.get('spec_10000_machine_code') }" />
				<th><span>*</span>品牌：</th>
				<td><input type="text" name="term.brand_name" class='ipt_new' disabled="disabled" value="${pageDatas.get('spec_10000_brand_name') }" style="width: 200px;" /></td>
				<th><span>*</span>型号：</th>
				<td><input type="text" name="term.model_name" class='ipt_new' disabled="disabled" value="${pageDatas.get('spec_10000_model_name') }" style="width: 200px;" /></td>

			</tr>
			<script type="text/javascript">
                 var term_cat_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null,SpecConsts.CAT_ID)%>';
				 TypeFun.getCatList("term.type", "term.cat");
			</script>
		</table>
	</div>
</c:if>
<!-- 终端信息结束 -->
<!-- 套餐信息开始 -->
<c:if test="${'10002'==pageDatas.get('default_str_one_2') }">
	<div class="grid_n_cont_sub">
		<h3>套餐信息：</h3>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="grid_form">
			<tr>
				<th><span>*</span>ESS套餐编码：</th>
				<td><html:orderattr order_id="${order_id}" field_name="out_plan_id_ess" field_desc="ESS套餐编码" field_type="input"></html:orderattr></td>
				<th>套餐档次：</th>
				<td><input name="month_fee" type="text" readonly="readonly" class="ipt_new" value="${pageDatas.get('spec_10002_month_fee') }" id="textfield" style="width: 200px;" /></td>
				<th>付费类型：</th>
				<td><html:selectdict curr_val="${pageDatas.get('spec_10002_pay_type') }" attr_code="DIC_ORDER_PAY_TYPE"
						appen_options="<option>---请选择---</option>"
						style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
				</td>
			</tr>
			<tr>
				<th>套餐名称：</th>
				<td><input type="text" class='ipt_new' name="plan_title" disabled="disabled" style="width: 200px;" value="${pageDatas.get('spec_10002_plan_title') }" /></td>
				<th><span>*</span>首月资费方式：</th>
				<td><html:orderattr attr_code="DIC_OFFER_EFF_TYPE"
						order_id="${order_id}" field_name="first_payment" field_desc="首月资费方式" field_type="select"></html:orderattr></td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th><span>*</span>套餐是否变更：</th>
				<td><html:orderattr attr_code="is_change" order_id="${order_id}" field_name="is_change" field_desc="套餐是否变更" field_type="select"></html:orderattr></td>
				<th><span>*</span>是否iPhone套餐：</th>
				<td><html:orderattr attr_code="DIC_IS_IPHONE_PLAN" order_id="${order_id}" field_name="is_iphone_plan" field_desc="套餐是否变更" field_type="select"></html:orderattr></td>
				<th>4G套餐类型：</th>
				<td><html:orderattr attr_code="DIC_PRODUCT_TYPE" order_id="${order_id}" field_name="bss_order_type" field_desc="4G套餐类型" field_type="select"></html:orderattr></td>
			</tr>
			<tr>
				<th><span>*</span>货品类型：</th>
				<td><html:selectdict name="offer.type" disabled="true" curr_val="${pageDatas.get('spec_10002_type_id') }"
						appen_options="<option vlaue=''>---请选择---</option>"
						attr_code="DC_PRODUCT_TYPE"
						style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
				</td>
				<th><span>*</span>货品小类：</th>
				<td><select name="offer.cat" disabled="disabled" style="width: 200px;" class="ipt_new">
						<option value="${pageDatas.get('spec_10002_cat_id') }" selected="selected"></option>
				</select></td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>

			</tr>
			<script type="text/javascript">
				var offer_cat_id = "${pageDatas.get('spec_10002_cat_id') }";
				TypeFun.getCatList("offer.type", "offer.cat");
			</script>
			<tr>
				<th>可选包：</th>
				<td colspan="5"><script type="text/javascript">
					var packSize = '${fn:length(orderTree.packageBusiRequests)}';
				</script> 
				<c:forEach var="package" items="${orderTree.packageBusiRequests}" varStatus="status">
                      ${package.packageName }:${package.elementName }
                      <c:if test="${fn:length(orderTree.packageBusiRequests)-1 != status.index}">,</c:if>
				</c:forEach></td>
			</tr>
		</table>

	</div>
</c:if>
<!-- 套餐信息结束 -->
<!-- 合约计划开始 -->
<c:if test="${'10001'==pageDatas.get('default_str_one_1') }">
	<div class="grid_n_cont_sub">
		<h3>合约计划：</h3>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="grid_form">
			<tr>
				<th><span>*</span>合约编码：</th>
				<td><input type="text" class="ipt_new" value="${pageDatas.get('out_package_id') }" id="textfield" style="width: 200px;" /></td>
				<th><span>*</span>合约名称：</th>
				<td><input type="text" class="ipt_new" value="${pageDatas.get('spec_10001_goods_name') }" id="textfield" style="width: 200px;" /></td>
				<th><span>*</span>合约期限：</th>
				<td><input name="package_limit" type="text" class="ipt_new" value="${pageDatas.get('spec_10001_package_limit') }" id="textfield" style="width: 200px;" /></td>
			</tr>
			<tr>
				<th><span>*</span>货品类型：</th>
				<td><html:selectdict name="contract.type" disabled="true" curr_val="${type_id }"
						appen_options="<option vlaue=''>---请选择---</option>"
						attr_code="DC_PRODUCT_TYPE"
						style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
				</td>
				<th><span>*</span>货品小类：</th>
				<!-- 就是合约类型 -->
				<td><select name="contract.cat" disabled="disabled" style="width: 200px;" class="ipt_new">
						<option value='' selected="selected"></option>
				</select></td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
			<script type="text/javascript">
				var contract_type_id = '${type_id}';
				var contract_cat_id = '${cat_id}';
				$("[name='contract.type'] option[value='"+ contract_type_id + "']").attr("selected","selected");
				TypeFun.getCatList("contract.type", "contract.cat");
			</script>
		</table>
	</div>
	<div class="grid_n_cont_sub">
		<h3>活动下自选包：</h3>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="grid_form">
			<c:forEach var="orderActivityBusiRequest" items="${orderTree.orderActivityBusiRequest}" varStatus="packageCount">
				<c:if test="${orderActivityBusiRequest.activity_type_zhufu=='1' }">
					<c:forEach var="attrPackageActivityBusiRequest" items="${orderTree.attrPackageActivityBusiRequest }">
						<c:if test="${orderActivityBusiRequest.inst_id==attrPackageActivityBusiRequest.activity_inst_id }">
							<tr>
								<th>&nbsp;</th>
								<td>第${packageCount.index + 1 }包</td>
								<th>包编号：</th>
								<td>${attrPackageActivityBusiRequest.package_code }</td>
								<th>包名称：</th>
								<td>${attrPackageActivityBusiRequest.package_name }</td>
							</tr>
							<tr>
								<th>元素编码：</th>
								<td>${attrPackageActivityBusiRequest.element_code }</td>
								<th>元素类型：</th>
								<td><html:selectdict disabled="true"
										curr_val="${attrPackageActivityBusiRequest.element_type }"
										appen_options="<option value=''>---请选择---</option>"
										attr_code="DC_ELEMENT_TYPE"
										style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
								</td>
								<th>元素名称：</th>
								<td>${attrPackageActivityBusiRequest.element_name }</td>
							</tr>
							<tr>
								<td colspan="6">
								<div style="border-bottom: 1px dashed #c3c3c3; margin-left: 60px; margin-right: 50px"></div></td>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>
		</table>
	</div>
</c:if>
<!-- 合约计划结束 -->