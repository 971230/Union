$(document).ready(function(){
	
	
	$("#saveAcct").bind("click",function(){
		var system_id = $("#system_id").val();
		var url = ctx + "/shop/admin/checkAcct!saveAcct.do?ajax=yes&checkAcctConfig.system_id="+system_id;
		Cmp.ajaxSubmit('theForm', '', url, {}, CheckAcctAdd.jsonBack,'json');
	});
	
});


var CheckAcctAdd = {
	jsonBack:function(responseText){
		if(responseText.result == 0){
			alert("保存成功！");
			var url = ctx + "/shop/admin/checkAcct!listAcct.do";
			window.location.href = url;
		}else{
			alert(responseText.message);
		}
	}
}

String.prototype.trim = function (){
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
};