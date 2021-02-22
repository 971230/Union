<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="warehouse/js/prodStoreManage.js"></script>
<style>
.addOrEditDiv {
padding: 5px;
}
.addOrEditDiv table th {
text-align: right;
padding-right: 3px;
vertical-align: middle;
color: #666;
width: 85px;
}
.addOrEditDiv table tr{
line-height: 30px;
}

</style>
<div>
	<form action="warehouseAction!prodStoreManage.do" method="post" id="goodsStoreForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							物理仓：
						</th>
						<td>
							<html:selectdict id="house_id" name="house_id" curr_val="${house_id }"
						             attr_code="DC_WARE_HOUSE" style="width:200px" 
						        appen_options='<option value="">--请选择--</option>'></html:selectdict>
						</td>
						<th>
							货品：
						</th>
						<td>
							<input type="hidden" class="ipttxt" id="sku_input" name="sku" value="${sku}" />
							<input type="text" class="ipttxt" id="sku_desc_name" name="product_name" value="${product_name}" />
							<input type="button" style="" class="comBtn selectProductBtnTop"
								value="选&nbsp;择" id="" name="button">
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
		<grid:grid from="webpage" formId="c" excel="">
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell>物理仓</grid:cell>
				<grid:cell>货品名称</grid:cell>
				<grid:cell>货品库存</grid:cell>
				<grid:cell>未分配</grid:cell>
				<grid:cell>已分配</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="id" class="" value="${obj.house_id}" />
					<input type="hidden" name="product_id" class="" value="${obj.product_id}" />
				</grid:cell>
				<grid:cell>${obj.house_name}</grid:cell>
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>
					${obj.inventory_num}
				</grid:cell>
				<grid:cell>
					${obj.no_apply_num}
				</grid:cell>
				<grid:cell>${obj.apply_num}</grid:cell>
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
							物理仓名称：<input type="hidden" class="ipttxt" name="goodsInventoryM.house_id" id="prod_house_id" value="">
						</th>
						<td>
							<input type="text" readonly class="ipttxt" id="prod_house_name"
								name="house_name" value="" />
							<input type="button" style="" class="comBtn"
								value="选&nbsp;择" id="selectHouseNameBtn" name="button">
						</td>
						<th>
							货品名称：<input type="hidden" class="ipttxt" name = "flag" value="" id="isae_hidden">
						</th>
						<td>
							<input type="hidden" id="product_id_hidden" class="ipttxt"
								name="goodsInventoryM.product_id" value="" />
							<input type="hidden" id="product_sku_hidden" class="ipttxt"
								name="goodsInventoryM.sku" value="" />
							<input type="text" readonly class="ipttxt" id="ae_house_name"
								name="" value="" />
							<input type="button" style="" class="comBtn selectProductBtn"
								value="选&nbsp;择" name="button2" id="selectProductBtn">
						</td>
						<th >
							货品库存：<input type="hidden" class="ipttxt" name = "oper" value="" id="operate">
						</th>
						<td >
							<input type="text" id="product_store" class="ipttxt" name="goodsInventoryM.inventory_num" value="" readonly="readonly">
						</td>
						<th id="no_apply_store_th">
							未分配库存： 
						</th>
						<td id="no_apply_store_td">
							<div><input type="text" readonly id="no_apply_store" class="ipttxt" name ="goodsInventoryM.no_apply_num"  value="0" /></div>
						</td>
					</tr> 
					<tr>
						
						<th id="attribution_th">
							仓库动作：
						</th>
						<td id="attribution_td">
							<select name="attribution" class="ipttxt" style="width: 155px;">
								<option value="1">入库</option>
								<option value="0">出库</option>
							</select>
						</td>
						
						<th id="change_store_num_th">
							变动库存：
						</th>
						<td id="change_store_num_td">
							<input type="text" class="ipttxt" id="ae_house_code"
								name="changeStoreNum" value="" />
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr align="center">
						<td></td>
						<td></td>
						<td><input type="button" style="margin-right: 10px;" class="comBtn"
								value="新&nbsp;增" id="prodaddBtn" name="button"></td>
						<td><input type="button" style="margin-left: 40px;" class="comBtn"
								value="修&nbsp;改" id="prodEditBtn" name="button"></td>
						<td><input type="button" style="margin-left: -50px;" class="comBtnDisable"
								value="确&nbsp;定" id="prodConfirmBtn" name="button"></td>
						<td><input type="button" style="margin-right: 10px;" class="comBtn"
								value="取消" id="prodcancelBtn" name="button"></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<div id="selectHouseName_dialog"></div>
<div id="selectProduct_dialog"></div>