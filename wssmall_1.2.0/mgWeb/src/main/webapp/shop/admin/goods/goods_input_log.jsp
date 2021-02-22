<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<link href="<%=request.getContextPath()%>/publics/lucene/info.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/publics/lucene/lucene.js"></script>
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
	<div style="width: 50%; height: 100%; float: right;" title="变更后">
		<table class="form-table" style="width: 100%; float: left"
			id='base_table'>
			<tr>
				<th>商品编码：</th>
				<td><input type="text" id="sku" name="goodsOld.sku"
					style="width: 200px" class="top_up_ipt" value="${goodsOld.sku}"
					readonly /></td>
			</tr>
			<tr>
				<th>商品名称：</th>
				<td><input type="text" id="goods_name" name="goodsOld.name"
					style="width: 200px" class="top_up_ipt"
					value="${goodsOld.name}" /> <input type="hidden"
					name="goodsOld.type" value="goods"> <input type="hidden"
					id="model_code" name="goodsOld.model_code"
					value="${goodsOld.model_code }"></td>
			</tr>
			<tr>
				<th>商品简称：</th>
				<td><input type="text" id="simple_name"
					name="goodsOld.simple_name" style="width: 200px" class="top_up_ipt"
					value="${goodsOld.simple_name}" /></td>
			</tr>

			<tr>
				<th>市场价(元)：</th>
				<td><input type="text" name="goodsOld.mktprice" id="mktprice"
					style="width: 200px" class="top_up_ipt" dataType="float"
					value="${goodsOld.mktprice}" /></td>
			</tr>
			<tr>
				<th>销售价(元)：</th>
				<td><input type="text" name="goodsOld.price" id="price"
					style="width: 200px" class="top_up_ipt" dataType="float"
					required="true" value="${goodsOld.price}" /> &nbsp;&nbsp; <span
					class="help_icon" helpid="goods_price"></span></td>
			</tr>
			<tr>
				<th>重量(克)：</th>
				<td><input type="text" name="goodsOld.weight" id="weight"
					style="width: 200px" class="top_up_ipt" dataType="float"
					value="${goodsOld.weight}" /></td>
			</tr>
			<tr>
				<th>商品包：</th>
				<td><input type="hidden" name="tag_id" id="tag_ids"
					class="top_up_ipt" value="${goodsOld.tag_id}" /> <input
					type="text" name="tag_names" id="tag_names" style="width: 200px"
					class="top_up_ipt" value="${goodsOld.tag_name}" readonly="readonly" /></td>
			</tr>
			<tr>
				<th>总部活动、商品编码：</th>
				<td><input type="text" name="goodsOld.p1"
					id="act_code_zb" style="width: 200px" class="top_up_ipt"
					dataType="String" value="${goodsOld.p1}" /></td>
			</tr>

			<tr>
				<th>总部产品编码：</th>
				<td><input type="text" name="goodsOld.p2"
					id="prod_code_zb" style="width: 200px" class="top_up_ipt"
					dataType="String" value="${goodsOld.p2}" /></td>
			</tr>
			<tr>
				<th>当前生效活动:</th>
				<td><textarea readonly="readonly" style="width: 200px"
						class="top_up_ipt">${goodsOld.activity_names}</textarea></td>
			</tr>
			<c:forEach items="${goodsParamOldMap}" var="entry">
				<tr>
					<th>${entry.key}:</th>
					<td>
						<input type="text" class="top_up_ipt" style='width:200px;' dataType="String" value="${entry.value}" />
					</td>
				</tr>
			</c:forEach>
		</table>
		<table border="1">
			<tr>
				<td width="200px">货品ID</td>
				<td width="200px">货品名称</td>
			</tr>
			<c:forEach var="product" items='${goodsOldList}' >
				<tr>
					<td width="200px">${product.product_id }</td>
					<td width="200px">${product.name }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div style="width: 50%; height: 100%; float: right;" title="变更后">
		<table class="form-table" style="width: 100%; float: left"
			id='base_table2'>
			<tr>
				<th>商品编码：</th>
				<td><input type="text" id="sku" name="goodsNew.sku"
					style='width:200px;<c:if test="${goodsNew.sku_l == 1}">color:red;</c:if>'
					class="top_up_ipt" value="${goodsNew.sku}" readonly /></td>
			</tr>
			<tr>
				<th>商品名称：</th>
				<td><input type="text" id="goods_name" name="goodsNew.name"
					style='width:200px;<c:if test="${goodsNew.name_l == 1}">color:red;</c:if>'
					class="top_up_ipt" required="true" value="${goodsNew.name}" /> <input
					type="hidden" name="goodsNew.type" value="goods"> <input
					type="hidden" id="model_code" name="goodsNew.model_code"
					value="${goodsNew.model_code }"></td>
			</tr>
			<tr>
				<th>商品简称：</th>
				<td><input type="text" id="simple_name"
					name="goodsNew.simple_name"
					style='width:200px;<c:if test="${goodsNew.simple_name_l == 1}">color:red;</c:if>'
					class="top_up_ipt" value="${goodsNew.simple_name}" /></td>
			</tr>

			<tr>
				<th>市场价(元)：</th>
				<td><input type="text" name="goodsNew.mktprice" id="mktprice"
					style='width:200px;<c:if test="${goodsNew.mktprice_l == 1}">color:red;</c:if>'
					class="top_up_ipt" dataType="float" value="${goodsNew.mktprice}" />
				</td>
			</tr>
			<tr>
				<th>销售价(元)：</th>
				<td><input type="text" name="goodsNew.price" id="price"
					style='width:200px;<c:if test="${goodsNew.price_l == 1}">color:red;</c:if>'
					class="top_up_ipt" dataType="float" required="true"
					value="${goodsNew.price}" /> &nbsp;&nbsp; <span class="help_icon"
					helpid="goods_price"></span></td>
			</tr>
			<tr>
				<th>重量(克)：</th>
				<td><input type="text" name="goodsNew.weight" id="weight"
					style='width:200px;<c:if test="${goodsNew.weight_l == 1}">color:red;</c:if>'
					class="top_up_ipt" dataType="float" value="${goodsNew.weight}" /></td>
			</tr>
			<tr>
				<th>商品包：</th>
				<td><input type="hidden" name="tag_id" id="tag_ids"
					class="top_up_ipt" value="${goodsNew.tag_id}" /> <input
					type="text" name="tag_names" id="tag_names"
					style='width:200px;<c:if test="${goodsNew.tag_name_l == 1}">color:red;</c:if>'
					class="top_up_ipt" value="${goodsNew.tag_name}" readonly="readonly" /></td>
			</tr>
			<tr>
				<th>总部活动、商品编码：</th>
				<td><input type="text" name="goodsNew.p1"
					id="act_code_zb"
					style='width:200px;<c:if test="${goodsNew.act_code_l == 1}">color:red;</c:if>'
					class="top_up_ipt" dataType="String" value="${goodsNew.p1}" />
				</td>
			</tr>

			<tr>
				<th>总部产品编码：</th>
				<td><input type="text" name="goodsNew.p2"
					id="prod_code_zb"
					style='width:200px;<c:if test="${goodsNew.prod_code_l == 1}">color:red;</c:if>'
					class="top_up_ipt" dataType="String" value="${goodsNew.p2}" /></td>
			</tr>
			<tr>
				<th>当前生效活动:</th>
				<td><textarea readonly="readonly"
						style='width:200px;<c:if test="${goodsNew.activity_names_l == 1}">color:red;</c:if>'
						class="top_up_ipt">${goodsNew.activity_names}</textarea></td>
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
		<table border="1">
			<tr>
				<td width="200px">货品ID</td>
				<td width="200px">货品名称</td>
			</tr>
			<c:forEach var="product" items='${goodsNewList}' >
				<tr>
					<td width="200px">${product.product_id }</td>
					<td width="200px">${product.name }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="submitlist" align="center">
		<input name="btn" class="comBtn" type="button"
			id="good_down_cance_btn" value=" 取   消  " style="margin-left: 10px;" />
	</div>
</form>