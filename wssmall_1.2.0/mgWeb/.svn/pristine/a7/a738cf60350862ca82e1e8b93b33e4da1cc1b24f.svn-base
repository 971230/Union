<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<link rel="stylesheet" href="${ctx}/shop/admin/css/style_min.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/shop/admin/css/style.css" type="text/css" rel="stylesheet">

<script type="text/javascript" src="js/purchase_order.js"></script>
<script type="text/javascript" src="js/searchGoods.js"></script>

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
<div>
	<div class="rightDiv">
		<div class="stat_graph">
			<h3>
				<div class="graph_tab">
					<ul>
						<li id="show_click_1" class="selected">
							<span class="word">新建采购订单</span><span class="bg"></span>
						</li>
						<div class="clear"></div>
					</ul>
				</div>
			</h3>
		</div>
		<form method="post" id="addPurchaseFrom"
			action="purchaseOrderAction!add.do" class="validate">
			<div class="top_up_div">
				<h2>
					基本信息
				</h2>
				<div class="top_up_con">
					<table class="gridlist" border="0" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<td style="text-align:right;" nowrap="nowrap">
									采购单名称：
								</td>
								<td colspan="3">
									<input id="pru_order_name" dataType="string" class="ipttxt" autocomplete="on" required="true" type="text" name="warehousePurorder.pru_order_name"/>
								<span id="pru_order_name_message"></span>
								</td>
							</tr>

							<tr>
								<td style="text-align:right;" norwap="norwap">
									供应商：
								</td>
								<td>
									<span id="auto_supp"> <img class="pointer btn_supplier" title="查看供应商列表" app="desktop" src="images/zoom_btn.gif" />
									<input autocomplete="off" id="supplier" name="supplier" vtype="required" class="x-input resigterIpt" type="text" disabled dataType="string" autocomplete="on" required="true" />
										<ul style="display: none; z-index: 65535;" class="autocompleter-choices"></ul> <input name="warehousePurorder.supper_id" id="supplier_id" value="" type="hidden" /> </span>
										<span style="color: red;">*</span>
								</td>
								<td style="float:left;text-align:left;" norwap="norwap">
									采购方式：
								</td>
								<td style="float:left;">
									<input name="warehousePurorder.pru_type" value="0" onclick="prichange('1')" type="radio">
									现购
									<input name="warehousePurorder.pru_type" value="1"
										checked="checked" onclick="prichange('2')" type="radio">
									赊购

								</td>
							</tr>
							<tr>
								<td style="text-align:right;" norwap="norwap">
									到货仓库：
								</td>
								<td>
									<select name="warehousePurorder.house_id" class="ipttxt" style="width:120px;" dataType="string" class="resigterIpt" autocomplete="on" required="true">
										<c:forEach items="${warehouseList}" var="warehouse">
											<option value="${warehouse.house_id }">
												${warehouse.house_name }
											</option>
										</c:forEach>
									</select>
									<span style="color: red">*</span>
								</td>
								<td style="float:left;text-align:left;" norwap="norwap">
									物流费用：
								</td>
								<td style="float:left;">
									<input vtype="number" class="ipttxt" name="ship_amount" type="text" dataType="int" autocomplete="on" required="true" />
									元
								</td>
							</tr>
							<tr>
								<td style="text-align:right;" norwap="norwap">
									<span class="endd">预付款：</span>
								</td>
								<td>
									<span class="endd"><input id="price" vtype="required" class="ipttxt" name="warehousePurorder.deposit" type="text" dataType="int" />元<span style="color: red">*</span> </span>
								</td>
								<td style="float:left;" norwap="norwap">
									到货时间：
								</td>
								<td style="float:left;">
									<input type="text" name="warehousePurorder.pru_delvery_finish_time" id="pru_delvery_finish_time" value='<html:dateformat pattern="yyyy-MM-dd" d_time="${warehousePurorder.pru_delvery_finish_time }" />' maxlength="60" class="dateinput ipttxt" dataType="date" required="true" />
								</td>

							</tr>
						</thead>
					</table>
					</div>
				
			</div>
			<div class="top_up_div">
				<h2>
					采购商品
				</h2>
				<div class="top_up_con">
					
					<input type="button" class="comBtn" style="margin-top:3px;margin-left:3px;" id="supplier-find-btn" value="商品选择" />
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
					<input type="button" id="purchase-delall-btn" class="comBtn" value="全部删除" />
				</div>
			</div>
			<div class="submitlist" align="center" style="margin-bottom:4px;">
				<input type="hidden" name="create_type" value="${create_type }">
				<input type="hidden" name="store_action_type" value="${store_action_type }">
				<input type="hidden" name="audit_status" value="${audit_status }">
				<input id="addPurchaseOrderBaseBtn" type="button" value=" 保存 " class="submitBtn" />
				<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
						
			</div>
		</form>
	</div>
</div>
<div id="showPurchaseOrderDlg"></div>
<div id="showGoodsDlg"></div>