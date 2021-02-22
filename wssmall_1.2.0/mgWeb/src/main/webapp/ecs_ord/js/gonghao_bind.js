var GonghaoBind = {
	init : function(){
		var me = this;
	$(".gonghaoBindWindow").bind("click",function(){
		var username = $(this).parent().parent().children(":first").text();
		$(this).attr("href","gonghaoBindAction!searchOperator_Rel.do?username="+username);
		console.warn(username);
		$(this).trigger("click");
	})
	
	$("#goback_btn").bind("click" , function(){
		me.goBack();
	})
	
	$("#searchUser_btn").bind("click" , function(){
		me.searchUser();
	})
	},
	
	goBack : function(){
		$("#goback").attr("action","gonghaoBindAction!searchAdminUser.do");
		$("#goback").submit();	
	},
	
	searchUser : function(){
		$("#searchUser").attr("action","gonghaoBindAction!searchUser.do");
		$("#searchUser").submit();
	},
}

$(function(){
	GonghaoBind.init();
})