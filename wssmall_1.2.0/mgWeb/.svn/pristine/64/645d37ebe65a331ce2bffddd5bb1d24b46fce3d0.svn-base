var Service = Service || {};
Service = {
	/* 异步处理 */
	excuteNoLoading : function(func_id, action, params, callBack, dataType) {
		var self = this;
		var data = jQuery.param(params);
		$.ajax({
					type : "post",
					url : "widget.do?ajax=yes&type="+func_id+"&action="+action,
					data : data,
					dataType :  'json',
					success : function(result) {
						$.Loading.hide();
						callBack(result); 
					},
					error : function() {
						$.Loading.hide();
						alert("操作失败，请重试");
					}
				});
	}
}