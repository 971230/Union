<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<link rel="stylesheet" href="${ctx}/shop/admin/css/style_min.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/shop/admin/css/singlepage_min.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/shop/admin/css/style.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/shop/admin/css/ome.css" type="text/css" rel="stylesheet">

<script type="text/javascript" src="${ctx}/shop/admin/js/purchase_order.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/js/searchGoods.js"></script>

<style>
.tableform {
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}

h4 {
	font-size: 1.2em;
	font-weight: bold;
	line-height: 1.25;
}

h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}
</style>


<script type="text/javascript">
<!--

	function prichange(ss) {
		if (ss == 1) {
			$(".endd").hide();
			$("#price").attr("value", "");
		} else {
			$(".endd").show();
		}
	}
	
	prichange('2');

//-->
</script>
<div style="width: 100%; height: 500px;">
	<div class="rightDiv">
		<div class="stat_graph">
			<h3>
				<div class="graph_tab">
					<ul>
						<li id="show_click_1" class="selected">
							<span class="word">新建退货订单</span><span class="bg"></span>
						</li>
						<div class="clear"></div>
					</ul>
				</div>
			</h3>
		</div>
		<form method="post" id="addPurchaseFrom"
			action="purchaseOrderAction!add.do" class="validate" validate="true">
			<div class="top_up_div">
				<h2>
					基本信息
				</h2>
				<div class="top_up_con">
					<table class="gridlist" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<td align="right" nowrap="nowrap" width="10%">
									退货单名称：
								</td>
								<td colspan="3">
									<input size="32" id="pru_order_name" dataType="string" class="ipttxt"
								autocomplete="on" required="true" type="text"	name="warehousePurorder.pru_order_name" value="">
								<span id="pru_order_name_message"></span>
								</td>
							</tr>

							<tr>
								<td align="right">
									供应商：
								</td>
								<td width="40%">
									<span id="auto_supp"> <img class="pointer btn_supplier"
											title="查看供应商列表" app="desktop" src="images/zoom_btn.gif">
										<input autocomplete="off" id="supplier"
											name="supplier" vtype="required"
											class="x-input resigterIpt" type="text" disabled dataType="string" 
								autocomplete="on" required="true">
										<ul style="display: none; z-index: 65535;"
											class="autocompleter-choices"></ul> <input name="warehousePurorder.supper_id"
											id="supplier_id" value="" type="hidden"> </span>
									<span style="color: red;">*</span>
								</td>
								<td align="right" width="10%">
									采购方式：
								</td>
								<td>
									<input name="warehousePurorder.pru_type" value="0"
										onclick="prichange('1')" type="radio">
									现购
									<input name="warehousePurorder.pru_type" value="1"
										checked="checked" onclick="prichange('2')" type="radio">
									赊购

								</td>
							</tr>
							<tr>
								<td align="right">
									到货仓库：
								</td>
								<td>
									<select name="warehousePurorder.house_id"  id="dom_el_a5dae00"
										class=" x-input-select inputstyle resigterIpt" >
										<c:forEach items="${warehouseList}" var="warehouse">
											<option value="${warehouse.house_id }">
												${warehouse.house_name }
											</option>
										</c:forEach>
									</select>
									<span style="color: red">*</span>
								</td>
								<td title="一次性物流费用" align="right" nowrap="nowrap">
									物流费用：
								</td>
								<td>
									<input vtype="number" class="ipttxt" name="ship_amount" size="6" type="text" dataType="string"
								autocomplete="on" required="true">
									元
								</td>
							</tr>
							<tr>
								<td align="right">
									<span class="endd">预付款：</span>
								</td>
								<td>
									<span class="endd" style=""><input id="price"
											vtype="required" class="ipttxt" name="warehousePurorder.deposit" size="6"
											type="text" >元<span style="color: red">*</span> </span>
								</td>
								<td align="right" nowrap="nowrap">
									预计到货时间：
								</td>
								<td>
								<input type="text" name="warehousePurorder.pru_delvery_finish_time" id="pru_delvery_finish_time"
								value='<html:dateformat pattern="yyyy-MM-dd" d_time="${warehousePurorder.pru_delvery_finish_time }" />'
								maxlength="60" class="dateinput ipttxt" dataType="date"
								required="true" />
								</td>

							</tr>
						</thead>
					</table>
					</div>
				
			</div>
			<div class="top_up_div">
				<h2>
					退货商品
				</h2>
				<div class="top_up_con">
					<button id="supplier-find-btn" class="btn" type="button">
						<span><span>商品选择</span> </span>
					</button>
					<!-- 
					<span id="pfba"> <label>
							按货号索引：
						</label> <input autocomplete="off" name="bn" type="text">
						<ul style="display: none; z-index: 65535;"
							class="autocompleter-choices"></ul> </span>
					<span id="pfba2"> <label>
							按货品名称索引：
						</label> <input autocomplete="off" name="name" size="35" type="text">
						<ul style="display: none; z-index: 65535;"
							class="autocompleter-choices"></ul> </span>
					<span id="pfba3"> <label>
							按条码索引：
						</label> <input autocomplete="off" id="barcode" name="barcode" size="35"
							type="text">
						<ul style="display: none; z-index: 65535;"
							class="autocompleter-choices"></ul> </span>
					 -->
					<table class="gridlist" id="purchase_table" style="margin: 4px 0;">
						<thead>
							<tr>
								<th>
									货号
								</th>
								<th style="width:240px;">
									货品名称
								</th>
								<th >
									数量
								</th>
								<th >
									单价
								</th>
								<th >
									合计
								</th>
								<th style="width:30px;">
									删除
								</th>
							</tr>
						</thead>
						<tbody id="dataNode">
							
						</tbody>
					</table>
				</div>
				<div align="right">
					购买总量:
					<span id="buy_count">0</span> 总金额:
					<span id="buy_amount">0</span> &nbsp;
					<button type="button" id="purchase-delall-btn" class="btn">
						<span><span>全部删除</span> </span>
					</button>
				</div>
			</div>
			<div class="submitlist" align="center">
				<table align="right">
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<input type="hidden" name="create_type" value="${create_type }">
							<input id="addPurchaseOrderBaseBtn" type="button" value=" 保存 " create_type="${create_type }" 
								class="submitBtn" />
							<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
<div id="showPurchaseOrderDlg"></div>
<div id="showGoodsDlg"></div>