var SelectOuterMember={
	init:function(){
		var url = ctx + "/shop/admin/spread!outerSelect.do?ajax=yes";
		$("#outer_parent_div").load(url,SelectOuterMember.divInit);
	},
	divInit:function(){
		$(".userQryBtn").each(function(){
			$(this).unbind("click").bind("click",function(){
				var attr = $(this).attr("attr");
				var url = ctx + "/shop/admin/spread!"+attr+"Select.do?ajax=yes";
				var form = attr + "_list_form";
				var div = attr+"_parent_div";
				Cmp.ajaxSubmit(form,div,url,{},SelectOuterMember.divInit);
				return false;
			});
		});
		$("#selectParentMember").unbind("click").bind("click",function(){
			var radio = $("#parent_dialog").find("input[type='radio']:checked");
			var username = radio.attr("username");
			var userid = radio.attr("userid");
			var div = radio.attr("attrDiv");
			$("input[name='spreadMemberGrade.parent_id']").val(userid);
			$("#parent_name").val(username);
			Eop.Dialog.close("parent_dialog");
			return false;
		});
	}
};
$(function(){
	SelectOuterMember.init();
});