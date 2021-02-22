var SelectSpreadMember={
	init:function(){
		SelectSpreadMember.liClick("admin");
		
		$("ul[class='tab']").find("li").bind("click",function(){
			$(this).attr("class","active").siblings("li").removeClass();
			var tabName = $(this).attr("id");
			SelectSpreadMember.liClick(tabName);
		});
		
	},
	liClick:function(tabName){
		var url = ctx + "/shop/admin/spread!"+tabName+"Select.do?ajax=yes";
		$("#"+tabName+"_div").show().siblings("div").hide();
		$("#"+tabName+"_div").load(url,SelectSpreadMember.divInit);
	},
	divInit:function(){
		$(".userQryBtn").each(function(){
			$(this).unbind("click").bind("click",function(){
				var attr = $(this).attr("attr");
				var url = ctx + "/shop/admin/spread!"+attr+"Select.do?ajax=yes";
				var form = attr + "_list_form";
				var div = attr+"_div";
				Cmp.ajaxSubmit(form,div,url,{},SelectSpreadMember.divInit);
				return false;
			});
		});
		$("#selectSpreadMember").unbind("click").bind("click",function(){
			var radio = $("#spread_dialog").find("input[type='radio']:checked");
			radio.each(function(){
				var attr_div = $(this).attr("attrdiv");
				if(!$("#"+attr_div).is(":hidden")){
					var username = $(this).attr("username");
					var phone_num = $(this).attr("phone_num");
					var userid = $(this).attr("userid");
					
					var bank_account = SelectSpreadMember.validate($(this).attr("bank_account"));
					var bank_name = SelectSpreadMember.validate($(this).attr("bank_name"));
					var bank_account_name = SelectSpreadMember.validate($(this).attr("bank_account_name"));
					var spread_identity = SelectSpreadMember.validate($(this).attr("spread_identity"));
					var spread_address = SelectSpreadMember.validate($(this).attr("spread_address"));
					var spread_zip = SelectSpreadMember.validate($(this).attr("spread_zip"));
					var ucode = SelectSpreadMember.validate($(this).attr("ucode"));
					if(bank_name != ""){
						$("select[name='spreadMember.bank_name']").val(bank_name);
					}
					$("input[name='spreadMember.bank_account']").val(bank_account);
					$("input[name='spreadMember.bank_account_name']").val(bank_account_name);
					$("input[name='spreadMember.spread_identity']").val(spread_identity);
					$("input[name='spreadMember.spread_address']").val(spread_address);
					$("input[name='spreadMember.spread_zip']").val(spread_zip);
					$("input[name='spreadMember.ucode']").val(ucode);
					$("input[name='spreadMember.vested_object']").val(userid);
					$("input[name='spreadMember.name']").val(username);
					$("input[name='spreadMember.mobile']").val(phone_num);
					$("input[name='spreadMember.vested_type']").val(attr_div.split("_")[0]);
				}
			});
			Eop.Dialog.close("spread_dialog");
			return false;
		});
	},
	validate:function(data){
		if(typeof(data) == "undefined"){
			return "";
		}else{
			return data;
		}
	}
};
$(function(){
	SelectSpreadMember.init();
});