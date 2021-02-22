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
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>商品货号</grid:cell>
				<grid:cell>商品名称</grid:cell>
				<grid:cell>仓库名称</grid:cell>
				<grid:cell>在途库存</grid:cell>
				<grid:cell>库存</grid:cell>
				<grid:cell>货位</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>${obj.sn}</grid:cell>
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.house_name}</grid:cell>
				<grid:cell>${obj.transit_store}</grid:cell>
				<grid:cell>${obj.store}</grid:cell>
				<grid:cell><a title="货位详情" goods_id="${obj.goods_id}" house_name="${obj.house_name}" name="warehouse_seat_desc" href="javascript:void(0)">货位详情</a></grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="showWarehouseSeat"></div>