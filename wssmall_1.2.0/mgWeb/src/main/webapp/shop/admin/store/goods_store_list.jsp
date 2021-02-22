<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="store/js/warehouse_seat_desc.js"></script>
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
	<form action="warehouseGoodsStoreAction!search.do" method="post" id="goodsStoreForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							商品货号:
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="sn" value="${sn }" class="searchipt" />
						</td>
						<th>
							商品名称:
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="goods_name" value="${goods_name }" class="searchipt" />
						</td>
						<th>
							仓库名称:
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="house_name" value="${house_name }" class="searchipt" />
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
		<grid:grid from="webpage" excel="yes">
			<grid:header>
				<grid:cell>商品货号</grid:cell>
				<grid:cell>商品名称</grid:cell>
				<grid:cell>规格<span></grid:cell>
				<grid:cell>仓库名称</grid:cell>
				<grid:cell>库存</grid:cell>
				<grid:cell>货位</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>${obj.sn}</grid:cell>
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.specifications} </grid:cell>
				<grid:cell>${obj.house_name}</grid:cell>
				<grid:cell>${obj.store}</grid:cell>
				<grid:cell><a title="货位详情" goods_id="${obj.goods_id}" house_name="${obj.house_name}" name="warehouse_seat_desc" href="javascript:void(0)">货位详情</a></grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="showWarehouseSeat"></div>