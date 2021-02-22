var DelExcle = {
		init:function(){
			$("#del_excle_bt").click(function(){
				DelExcle.del();
			});
	   },
	   del :function() {
		   
		   $.ajax({
				type : "post",
				async : false,
				url:"moneyAuditAction!delExcle.do?ajax=yes",
				data : {
					"excel_from" : $('#excel_from option:selected') .val(),
					"batch_number" :$("#batch_number").val()
				},
				dataType : "json",
				success : function(data) {
					 alert(data.msg);
				}
			});
	   }
}

$(function(){
	 DelExcle.init();
});