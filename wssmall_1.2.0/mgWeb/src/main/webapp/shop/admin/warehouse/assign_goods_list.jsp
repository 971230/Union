<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">

<!--
$(function(){
	WarehouseGoods.initAssign();
});
//-->
</script>
<div id="assign_goods_list">
	<form action="" method="post" id="warehouseForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th >
							物理仓:
						</th>
						<td >
						    <html:selectdict id="house_id" name="goodsInventory.house_id" curr_val="${goodsInventory.house_id }"
						             attr_code="DC_WARE_HOUSE" style="width:200px" 
						        appen_options='<option value="">--请选择--</option>'></html:selectdict>
					    </td>
						<th >
							商品:
						</th>
						<td >
							<input type="hidden" class="ipttxt" id="goods_id" 
								name="goodsInventory.goods_id" value="${goodsInventory.goods_id }" />
							<input type="hidden" class="ipttxt" id="product_id" 
								name="goodsInventory.product_id" value="${goodsInventory.product_id }" />
							<input type="text" class="ipttxt" id="product_name" 
								name="goodsInventory.product_name" value="${goodsInventory.product_name }" class="searchipt" />
						    <input type="button" id="select_goods_btn" value="选择">
					    </td>
					    <td>
							<input type="button" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="query_house_btn" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage"  ajax="yes" formId="warehouseForm">
			<grid:header>
				<grid:cell>
					<input type="hidden" id="toggleChk" />
				</grid:cell>
				<grid:cell width="20%">物理仓</grid:cell>
				<grid:cell width="40%">商品名称</grid:cell>
				<grid:cell width="10%">商品库存</grid:cell>
				<grid:cell width="10%">未分配</grid:cell>
				<grid:cell width="20%">动作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="hidden" name="product_id" value="${obj.product_id}" />
					<input type="hidden" name="house_id" value="${obj.house_id}" />
					<input type="hidden" name="goods_id" value="${obj.goods_id}" />
					<input type="hidden" name="sku" value="${obj.sku}" />
				</grid:cell>
				<grid:cell><span name="house_name">${obj.house_name}</span></grid:cell>
				<grid:cell><span name="name">${obj.name} </span></grid:cell>				
				<grid:cell><span name="inventory_num">${obj.inventory_num}</span></grid:cell>
				<grid:cell><span name="no_apply_num">${obj.no_apply_num} </span></grid:cell>
				<grid:cell>
					<a title="分配" href="#" name="assign" >分配 </a>
				</grid:cell>
			</grid:body>

		</grid:grid>

		<!-- <img  class="modify" src="${ctx }/shop/admin/images/transparent.gif"> -->
	</form>
	<div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>

