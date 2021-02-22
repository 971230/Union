<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
<!--
$(function(){
		WarehouseGoods.initAssigned();
	
});
//-->
</script>
<div id="recover_goods_list">
	<form action="" method="post" id="VWarehouseForm" name="searchFrom">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							对应商城：
						</th>
						<td >
							<input type="hidden" id="ae_scope_code" class="ipttxt" 	name="apply.org_id" value="${apply.org_id }" />
							<input type="hidden" id="ae_org_id_belong" name="" value="" />
							<input type="text" id="ae_scope_name" readonly="readonly" class="ipttxt" readonly="readonly"
							        name="virtualWarehouse.org_name_str" value="${virtualWarehouse.org_name_str }" />
							<input type="button" style=""  value="选择" id="select_org_input" name="button">
						</td>
						<th >
							物理仓:
						</th>
						<td >
						    <html:selectdict id="house_id" name="apply.house_id" curr_val="${apply.house_id }"
						             attr_code="DC_WARE_HOUSE" style="width:200px" 
						        appen_options='<option value="">--请选择--</option>'></html:selectdict>
					    </td>
				    </tr>
					<tr>
						<th >
							商品:
						</th>
						<td >
							<input type="hidden" class="ipttxt" id="v_goods_id" 
								name="apply.goods_id" value="${apply.goods_id }" />
							<input type="hidden" class="ipttxt" id="v_product_id" 
								name="apply.product_id" value="${apply.product_id }" />
							<input type="text" class="ipttxt" id="v_product_name" 
								name="name" value="${name }" class="searchipt" />
						    <input type="button" id="v_select_goods_btn" value="选择">
					    </td>
						<th >
							虚拟仓:
						</th>
						<td >
							<input type="hidden" class="ipttxt" id="query_virtual_house_id" 
								name="apply.virtual_house_id" value="${apply.virtual_house_id }" />
							<input type="text" class="ipttxt" id="query_virtual_house_name" 
								name="virtual_house_name" value="${virtual_house_name }" class="searchipt" readonly="readonly" />
						    <input type="button" id="query_sel_vir_btn" value="选择">
					    </td>
					    <td>
							<input type="botton" style="margin-right: 10px;width:70px;" class="comBtn"
								value="搜&nbsp;索" id="v_query_house_btn" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage"  ajax="yes" formId="VWarehouseForm">
			<grid:header>
				<grid:cell>
					<input type="hidden" id="toggleChk" />
				</grid:cell>
				<grid:cell width="10%">销售组织名称</grid:cell>
				<grid:cell width="20%">物理仓</grid:cell>
				<grid:cell width="40%">商品名称</grid:cell>
				<grid:cell width="5%">共享类型</grid:cell>
				<grid:cell width="10%">虚拟仓</grid:cell>
				<grid:cell width="5%">数量</grid:cell>
				<grid:cell width="10%">动作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="hidden" name="goods_id" value="${obj.goods_id}" />
					<input type="hidden" name="sku" value="${obj.sku}" />
					<input type="hidden" name="org_id_str" value="${obj.org_id_str}" />
					<input type="hidden" name="org_name_str" value="${obj.org_name_str}" />
					<input type="hidden" name="house_id" value="${obj.house_id}" />
					<input type="hidden" name="virtual_house_id" value="${obj.virtual_house_id}" />
				</grid:cell>
				<grid:cell><span name="org_name">${obj.org_name}</span></grid:cell>
				<grid:cell><span name="house_name">${obj.house_name} </span></grid:cell>				
				<grid:cell><span name="name">${obj.name}</span></grid:cell>
				<grid:cell><span name="is_share" style="display:none">${obj.is_share}</span>
					<c:if test="${obj.is_share=='0'}">独享</c:if>
					<c:if test="${obj.is_share=='1'}">共享</c:if></grid:cell>
				<grid:cell><span name="v_house_name">${obj.v_house_name}</span></grid:cell>
				<grid:cell><span name="inventory_num">${obj.inventory_num}</span></grid:cell>
				<grid:cell>
					<a title="回收" href="#" name="recover">回收 </a>
				</grid:cell>
			</grid:body>
		</grid:grid>

		<!-- <img  class="modify" src="${ctx }/shop/admin/images/transparent.gif"> -->
	</form>
	<div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>

