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
	<form action="warehouseGoodsStoreAction!sumeStoreList.do" method="post" id="sumgoodsStoreForm">
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
							货品名称:
						</th>
						<td>
							<input type="text" class="ipttxt" 
								name="goods_name" value="${goods_name }" class="searchipt" />
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
				<grid:cell>商品货号</grid:cell>
				<grid:cell>货品名称</grid:cell>
				<grid:cell>规格</grid:cell>
				<grid:cell>单位</grid:cell>
				<grid:cell>重量</grid:cell>
				<grid:cell>在途库存</grid:cell>
				<grid:cell>库存</grid:cell>
				<grid:cell>录入日期</grid:cell>
				<grid:cell>最后修改日期</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell><input name="goods_id" type="hidden" value="${obj.goods_id}">${obj.sn}</grid:cell>
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.specifications}</grid:cell>
				<grid:cell>${obj.unit}</grid:cell>
				<grid:cell>${obj.weight}</grid:cell>
				<grid:cell>${obj.sum_transit_store}</grid:cell>
				<grid:cell>${obj.sum_store}</grid:cell>
				<grid:cell>${obj.create_time}</grid:cell>
				<grid:cell>${obj.last_modify}</grid:cell>
			</grid:body>
			<tr id="iframe_tr" style="display: none;">
			  	<td colspan="10">
			        <iframe style="width: 100%;height: 400px;" src="">
			        </iframe>
			    </td>
		   </tr>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="showWarehouseDiog"></div>
<script type="text/javascript">
var select_goods_id="";
$(function(){
	$(".grid tbody tr").bind("click",function(){
		var goods_id = $(this).find("td input[name='goods_id']").val();
		if(goods_id==select_goods_id){
			$("#iframe_tr").hide();
			select_goods_id = "";
			return ;
		}
		select_goods_id = goods_id;
		$("#iframe_tr").show().insertAfter($(this)).find("iframe").attr("src","warehouseGoodsStoreAction!showStoreDescList.do?goods_id="+goods_id);
	});
});
</script>