<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html>
<div>
	<form method="post">
	<table>
		<br><br>
		<tr>
			<th>请输入订单ID：</th>
			<td style="width:180px;">
				<input class="resigterIpt _x_ipt"  type="text" name="order_id" value="${order_id }" size="25"/>
				<a href="javascript:void(0);" id="qry_ordertree_refresh" class="dobtn" style="margin-left:5px;">查看</a>
			</td>
		</tr>
		
		<tr>
			<th>刷新结果：</th>
			<td style="width:180px;">
				<textarea rows="10" cols="80" id="order_refresh_result" name="order_refresh_result"></textarea>
			</td>
		</tr>
		
	</table>
	</form>
	<form method="post">
	<table>
		<br><br>
		<tr>
			<th>请输入商品SKU：</th>
			<td style="width:180px;">
				<input class="resigterIpt _x_ipt"  type="text" name="goods_id" value="${goods_id }" size="25"/>
				<a href="javascript:void(0);" id="qry_goods_refresh" class="dobtn" style="margin-left:5px;">查看</a>
			</td>
		</tr>
		
		<tr>
			<th>刷新结果：</th>
			<td style="width:180px;">
				<textarea rows="10" cols="80" id="goods_refresh_result" name="goods_refresh_result"></textarea>
			</td>
		</tr>
		
	</table>
	</form>
</div>
</html>

<script>
	$(function() {
		$("#qry_ordertree_refresh").bind("click", function() {
			var order_id = $("input[name='order_id']").val();
			$.ajax({
				type : "post",
				async : false,
				url : "orderRefreshTreeAttrAction!qryOrderTreeRefreshResult.do?ajax=yes&order_id="+order_id,
				data : {},
				dataType : "json",
				success : function(data) {
					$("#order_refresh_result").val();
					var dt = JSON.stringify(data);
					$("#order_refresh_result").val(dt);
				}
			});
		});
		$("#qry_goods_refresh").bind("click", function(){
			var goods_id = $("input[name='goods_id']").val();
			$.ajax({
				type : "post",
				async : false,
				url : "orderRefreshTreeAttrAction!qryGoodsRefreshResult.do?ajax=yes&goods_id="+goods_id,
				data : {},
				dataType : "json",
				success : function(data) {
					$("#goods_refresh_result").val();
					var dt = JSON.stringify(data);
					$("#goods_refresh_result").val(dt);
				}
			});
		});
	});
</script>
