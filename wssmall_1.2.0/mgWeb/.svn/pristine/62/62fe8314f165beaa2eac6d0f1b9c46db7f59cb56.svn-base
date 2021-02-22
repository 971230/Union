function checkDate(){
	$("#searchSynchLogsForm").submit();
}

function publishAgain(){
	if(confirm("确定重发活动吗？")){
		var batch_id = $("#batch_id").val();
		var pmt_code = $("#pmt_code").val();
		var pmt_name = $("#pmt_name").val();
		var status = $("#status").val();
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/activity!publishAgain.do?ajax=yes",
			data:"params.batch_id=" + batch_id +"&params.pmt_code=" + pmt_code +"&params.pmt_name=" + pmt_name +"&params.status=" + status +"&m=" + new Date().getTime(),
			dataType:"json",
			success:function(result){
				alert(result.message);
				if(result.result=='0'){
					window.location="activity!searchActivitySynLogs.do";
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