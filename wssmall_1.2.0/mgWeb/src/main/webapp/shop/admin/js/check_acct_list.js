$(document).ready(function(){
	$(".delCheckAcct").bind("click",function(){
		var system_id = $(this).attr("attr");
		var url = ctx + "/shop/admin/checkAcct!delAcct.do?ajax=yes&checkAcctConfig.system_id="+system_id;
		
		Cmp.ajaxSubmit('del_acct_config', '', url, {}, CheckAcct.jsonBack,'json');
	});
	
	
});


var CheckAcct = {
	jsonBack:function(responseText){
		if(responseText.result == 0){
			alert("删除成功！");
			$("#submitButton").click();
		}else{
			alert(responseText.message);
		}
	}
}

String.prototype.trim = function (){
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
};