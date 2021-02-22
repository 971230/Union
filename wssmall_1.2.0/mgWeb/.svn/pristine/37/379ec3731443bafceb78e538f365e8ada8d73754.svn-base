function checkDate(){
	$("#searchSynchLogsForm").submit();
}

function publishAgain(){
	if(confirm("确定重发号码吗？")){
		var batch_id = $("#batch_id").val();
		var number = $("#number").val();
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/numero!publishAgain.do?ajax=yes",
			data:"params.batch_id=" + batch_id +"&params.number=" + number +"&m=" + new Date().getTime(),
			dataType:"json",
			success:function(result){
				alert(result.params.message);
				if(result.result=='0'){
					window.location="numero!numeroSynchLogs.do";
				}
			},
			error :function(res){alert("异步调用失败:" + res);}
		});
	}
}

function showOrg(item){
	var row = $(item).closest("tr");
	var orgDiv = $(row).find(".absoDiv");
	var orgContent = $(row).find(".absoDivContent");
	$(orgDiv).attr("style","display:block");

}
function hideOrg(item){
	var row = $(item).closest("tr");
	$(row).find(".absoDiv").attr("style","display:none");
}

$(function(){
	$(".relaDiv").mouseenter(function(){
        showOrg(this);   
	});
	
	$(".relaDiv").mouseleave(function(){
        hideOrg(this);   
	});
})