$(function() {
	
	$("#submitButton").bind("click", function() {
		$("#submitButton").submit();
	});
	
	$("#del_btn").bind("click", function() {
		var red_ids = "";
		var checkboxlist = $("input[red_checkbox='red_checkbox'] : checked");
		if (checkboxlist.length > 0) {
			var i = 0;
			for (var check : checkboxlist) {
				var red_id = $(check).attr("value");
				if (i != checkboxlist.length - 1) {
					red_ids += (red_id+",");
				} else {
					red_ids += (red_id);
				}
				i ++;
			}
			$.ajax({
				type : "post",
				async : false,
				url : "promotionred!reddel.do?ajax=yes&red_ids="+red_ids,
				data : {},
				dataType : "json",
				success : function(data) {
					alert(data.msg);
				}
			});
		}
	});
	
});