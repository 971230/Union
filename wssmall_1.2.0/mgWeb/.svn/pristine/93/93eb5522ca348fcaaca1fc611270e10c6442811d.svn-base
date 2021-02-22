<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<form id="serchform" align="center">
	<div  style="width: 50%; height: 100%; float: left;" title="变更前">
		<table class="form-table" style="width: 100%; float: left"
			id='base_table'>
<!-- 			<tr> -->
<%-- 				<th><input type="hidden" id="adjunct_goods_type_input" value="${productsNew.goods_type }"> --%>
<!-- 					<span class="red">*</span>货品型号：</th> -->
<!-- 				<td id="model_code_td"><select name="goods.model_code" -->
<!-- 					style="width: 200px;" class="ipttxt1" id="model_code" -->
<!-- 					required="true"> -->
<!-- 						<option value="">请选择货品型号</option> -->
<!-- 				</select> &nbsp;&nbsp; <span class="help_icon" helpid="model_code"></span>&nbsp;&nbsp; -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr id="sku_tr">
				<th>SKU：</th>
				<td><input type="text" name="goods.sku" id="sku"
					required="true" style="width: 200px;" class="top_up_ipt"
					value="${productsNew.sku}" /></td>
			</tr>
			<tr>
				<th><span class="red">*</span>货品名称：</th>
				<td><input type="text" id="goods_name" name="goods.name"
					style="width: 200px;" class="top_up_ipt" required="true"
					value="${productsNew.name}" /></td>
			</tr>
			<tr id="snTr">
				<th>条形码：</th>
				<td id="machine_code_td"><input type="text" id="goods_sn"
					name="goods.sn" style="width: 200px;" class="top_up_ipt"
					value="${productsNew.sn}"> &nbsp;&nbsp;</td>
				<td></td>
			</tr>
			<c:forEach items="${goodsParamNewMap}" var="entry">
				<tr>
					<th>${entry.key}:</th>
					<td>
						<input type="text"  style="width: 200px" class="top_up_ipt" dataType="String" value="${entry.value}" />
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="width: 50%; height: 100%; float: right;" title="变更后">
		<table class="form-table" style="width: 100%; float: left"
			id='base_table1'>
<!-- 			<TR> -->
<!-- 				<TH><INPUT TYPE="HIDDEN" ID="ADJUNCT_GOODS_TYPE_INPUT" VALUE=""> -->
<!-- 					<SPAN CLASS="RED">*</SPAN>货品型号：</TH> -->
<!-- 				<TD ID="MODEL_CODE_TD"><SELECT NAME="GOODS.MODEL_CODE" -->
<!-- 					STYLE="WIDTH: 200PX;" CLASS="IPTTXT1" ID="MODEL_CODE" -->
<!-- 					REQUIRED="TRUE"> -->
<!-- 						<OPTION VALUE="">请选择货品型号</OPTION> -->
<!-- 				</SELECT> &NBSP;&NBSP; <SPAN CLASS="HELP_ICON" HELPID="MODEL_CODE"></SPAN>&NBSP;&NBSP; -->
<!-- 				</TD> -->
<!-- 			</Tr> -->
			<tr id="sku_tr">
				<th>SKU：</th>
				<td><input type="text" name="goods.sku" id="sku"
					required="true" style="width: 200px;" class="top_up_ipt"
					value="${productsOld.sku}" /></td>
			</tr>
			<tr>
				<th><span class="red">*</span>货品名称：</th>
				<td><input type="text" id="goods_name" name="goods.name"
					style="width: 200px;" class="top_up_ipt" required="true"
					value="${productsOld.name}" /></td>
			</tr>
			<tr id="snTr">
				<th>条形码：</th>
				<td id="machine_code_td"><input type="text" id="goods_sn"
					name="goods.sn" style="width: 200px;" class="top_up_ipt"
					value="${productsOld.sn}"> &nbsp;&nbsp;</td>
				<td></td>
			</tr>
			<c:forEach items="${goodsParamOldMap}" var="entry">
				<tr>
					<th>${entry.key}:</th>
					<td>
						<input type="text"  style="width: 200px" class="top_up_ipt" dataType="String" value="${entry.value}" />
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="submitlist" align="center">
		<input name="btn" class="comBtn" type="button"
			id="products_down_cance_btn" value=" 取   消  " style="margin-left: 10px;" />
	</div>
</form>