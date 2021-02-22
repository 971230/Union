<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div>
	<form action="warehouseGoodsStoreAction!warhouseStoreList.do" method="post" id="goodsStoreForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							仓库名称:
						</th>
						<td>
							<input type="text" class="ipttxt" id="house_name" name="house_name" value="${house_name }" class="searchipt" />
						</td>
						
						<th>
							商品名称:
						</th>
						<td>
							<input type="text" class="ipttxt" id="goods_name" name="goods_name" value="${goods_name }" class="searchipt" />
						</td>
						<th>
							商品货号:
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="sn" value="${sn }" class="searchipt" />
						</td>
						<!-- 
						<th>
							在途库存:
						</th>
						<td>
							<select name="transit_store_compare" id="transit_store_compare">
								<option value='0'>=</option>
								<option value='1'>></option>
								<option value='2'><</option>
							</select>
							<input type="text" size="5" class="ipttxt" name="transit_store" value="${transit_store }" class="searchipt" />
						</td>
						 -->
						<th>
							库存:
						</th>
						<td>
							<select name="store_compare" id="store_compare">
								<option value='0'>=</option>
								<option value='1'>></option>
								<option value='2'><</option>
							</select>
							<input type="text" size="5" class="ipttxt" name="store" value="${store }" class="searchipt" />
						</td>
						<td>
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" excel="yes">
			<grid:header>
				<grid:cell>仓库名称</grid:cell>
				<grid:cell>商品名称</grid:cell>
				<grid:cell>商品货号</grid:cell>
				<grid:cell>规格<span></grid:cell>
				<grid:cell>在途库存</grid:cell>
				<grid:cell>库存</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>${obj.house_name}</grid:cell>
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.sn}</grid:cell>
				<grid:cell>${obj.specifications} </grid:cell>
				<grid:cell>${obj.transit_store}</grid:cell>
				<grid:cell>${obj.store}</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="showWarehouseDiog"></div>
<script type="text/javascript">
	$(function (){
		$('#transit_store_compare').val(${transit_store_compare}) ;
		$('#store_compare').val(${store_compare}) ;
	});
</script>