var FuKaInfo = {
	init : function () {
		this.getOrderPhoneFukaInfo();
		this.geAttrPackageFukaInfo();
	},
	getOrderPhoneFukaInfo : function() {
		$.ajax({
			type : "post",
			async : false,
			url : "orderFlowAction!getOrderPhoneFuKaInfo.do?ajax=yes&order_id="+$("#order_id").val(),
			data : {},
			dataType : "json",
			success : function(data) {
				$.each(data, function(i, fuKaObj) {
					$("td[name='dianhuahaoma']").html(fuKaObj.phoneNum);
					$("td[name='ruwangleixing']").html(fuKaObj.userType);
				});
			}
		});
	},
	geAttrPackageFukaInfo : function() {
		$.ajax({
			type : "post",
			async : false,
			url : "orderFlowAction!getAttrPackageFukaInfo.do?ajax=yes&order_id="+$("#order_id").val(),
			data : {},
			dataType : "json",
			success : function(data) {
				$.each(data, function(i, attrPackage) {
					
				});
			}
		});
	}
};