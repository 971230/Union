$(function(){
	$("form.validate").validate();
	$("#lx_btn").bind("click", function() {
		$.ajax({
			type : "post",
			async : false,
			url : "promotionred!redaddsave.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				var data = eval(data);
				if (data.res == "0") {
					$("#red_id").val(data.data);
				}
				alert(data.msg);
			}
		});
	});
	$("#sc_btn").bind("click", function() {
		var red_id = $("#red_id").val();
		$.ajax({
			type : "post",
			async : false,
			url : "promotionred!redcreate.do?ajax=yes&red_id="+red_id,
			data : {},
			dataType : "json",
			success : function(data) {
				alert(data.msg);
			}
		});
	});
	$("#xg_btn").bind("click", function() {
		var red_id = $("input[name='red_id']").val();
		var red_name = $("input[name='promotionRedPkg.name']").val();
		var red_memo = $("input[name='promotionRedPkg.memo']").val();
		var url = "/shop/admin/promotionred!rededitsave.do?ajax=yes";
		Cmp.ajaxSubmit('rededitform','', url, {}, function(responseText) {
			if (responseText.length>0) {
				$("input[name='promotionRedPkg.name']").val(responseText.name);
				$("input[name='promotionRedPkg.memo']").val(responseText.memo);
				alert("修改成功！");
			}
		});
	});
	$("#test_btn").bind("click", function() {
		var red_id = $("#red_id").val();
		var member_id = $("#member_id").val();
		$.ajax({
			type : "post",
			async : false,
			url : "promotionred!testGrabRedPkg.do?ajax=yes&red_id="+red_id+"&member_id="+member_id,
			data : {},
			dataType : "json",
			success : function(data) {
				alert(data.msg);
			}
		})
	});
	
});