
$(document).ready(function(){
	$("#editOttUserForm.validate").validate();
	$("#editOttUserForm [name='submitBtn']").click(function() {
		var url = ctx+ "/shop/admin/ottMember!saveEditOttUser.do?ajax=yes";
		Cmp.ajaxSubmit('editOttUserForm', '', url, {}, EditOttUser.jsonBack,'json');
	});
	
});

var EditOttUser = {
	jsonBack : function(responseText) { 
		if (responseText.result == 1) {
			alert(responseText.message);
			Eop.Dialog.close("editOttUserGround");
			Eop.Dialog.close("editOttUserLock");
			window.location.reload(); //刷新页面
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}
	}
}