$(function() {
	Eop.Dialog.init({
				id : "orderDetails",
				modal : true,
				title : "订单详情",
				width : '1000px'
			});
	Eop.Dialog.init({
				id : "addRemarks",
				modal : true,
				title : "添加订单备注",
				width : '900px'
			});

	Eop.Dialog.init({
				id : "queryCountyListDlg",
				modal : true,
				title : "订单分配",
				width : "800px"
			});
	Eop.Dialog.init({
				id : "addCommentsDlg",
				modal : true,
				title : "添加备注",
				width : '400px'
			});
	$("a[name='add_comments']").click(function() {
				var orderId = $(this).attr("innerId");
				openCommentsDlg(orderId);
				return false;
			});

	$("a[name='addRemarks']").click(function() {
		var order_id = $(this).attr("value");
		var orderType = $(this).attr("orderType");
		if (order_id == null || order_id == "" || orderType == null
				|| orderType == "") {
			alert("异常：order_id或者orderType为空");
			return false;
		}

		if (orderType == "intent") {
			Eop.Dialog.open("addRemarks");
			var url = ctx
					+ "/shop/admin/orderIntentAction!intentAddRemark.do?ajax=yes&order_id="
					+ order_id+"&type="+orderType;
			$("#addRemarks").load(url, {}, function() {
					});
		} else {
			openCommentsDlg(order_id);
		}
		return false;
	});
	$("a[name='orderDetails']").click(function() {
		$("#orderDetails").empty();
		var order_id = $(this).attr("value");
		var orderType = $(this).attr("orderType");
		if (order_id == null || order_id == "" || orderType == null
				|| orderType == "") {
			alert("异常：order_id或者orderType为空");
			return false;
		}
		Eop.Dialog.open("orderDetails");
		if (orderType == "intent") {
			var url = ctx
					+ "/shop/admin/orderIntentAction!intentDetails.do?ajax=yes&order_id="
					+ order_id;
			$("#orderDetails").load(url, {}, function() {
					});
		} else {
			//alert("该订单类型暂无详情页面");
			var url = ctx
					+ "/shop/admin/orderIntentAction!orderDetails.do?ajax=yes&order_id="
					+ order_id;
			$("#orderDetails").load(url, {}, function() {
					});
		}
		return false;
	});
});