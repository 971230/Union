var Binding = {
	init : function(){
		var me = this;
		$("#do_binding_btn").bind("click",function(){
			me.do_binding();
		});
		$("#goback_btn").bind("click",function(){
			me.goBack();
		});
		$(".unbinding_gonghao").bind("click",function(){
			var username = $(this).parent().parent().children(":first").text();
			var city = $(this).parent().parent().children().eq(3).children().attr("city_num");
			$(this).attr("href","gonghaoBindAction!unbinding_gonghao.do?username="+username + "&city="+city);
			$(this).trigger("click");
		})
		
		me.selectProvince_City();
	},
	
	selectProvince_City : function(){
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/gonghaoBindAction!selectProvince_City.do?ajax=yes",
			dataType:"json",
			success : function(result){
				for(var key in result){
					$("#city").append("<option value ="+key+">"+result[key]+"</option>");
				}
				var city_list = $(".city_name");
				for(var i = 0; i<city_list.length; i++){
					var name = city_list.eq(i).text();
					city_list.eq(i).attr("city_num",name);
					city_list.eq(i).text(result[name]);
				}
			}
		});
	},
	
	do_binding : function(){
		if($.trim($("#cb_gonghao").val()) == ""){
			$("#warning").text("请填写cb工号");
		}else if($.trim($("#province").val()) == ""){
			$("#warning").text("请填写省份");
		}else if($.trim($("#city").val()) == "" || $.trim($("#city").val()) == "nochoice"){
			$("#warning").text("请填写城市");
		}else{
			var  order_gonghao = $("#gonghao").val();
			var  ess_emp_id = $("#cb_gonghao").val();
			var  province = $("#province").val();
			var  city = $("#city").val();
			var  username = $("#username").val();
			$.ajax({
				type: "get",
				url:app_path+"/shop/admin/gonghaoBindAction!do_binding.do?ajax=yes&params.order_gonghao="+order_gonghao+"&params.ess_emp_id="+ess_emp_id+"&params.province="+province+"&params.city="+city,
				dataType:"json",
				success:function(result){
					
					if(result.result == "0"){
						alert(result.message);
					}else{
						alert(result.message);
						window.location="gonghaoBindAction!searchOperator_Rel.do?username="+username;
					}
				},
			});
		}

	},
	goBack : function(){
		$("#goback").attr("action","gonghaoBindAction!searchAdminUser.do");
		$("#goback").submit();	
	}

}

$(function(){
	Binding.init();
})