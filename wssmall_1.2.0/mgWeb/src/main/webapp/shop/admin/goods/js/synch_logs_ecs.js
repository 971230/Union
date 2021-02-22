function checkDate(){
	var start = $("#start_date").val();
	var end =$("#end_date").val();
	if(start!=""&&end!=""){
		if(start>end){
			alert("开始时间不能大于结束时间，请检查！");
		}else{
			$("#searchSynchLogsForm").submit();
		}
	}else{
		$("#searchSynchLogsForm").submit();
	}
}

function publishAgain(){
	var object_type = $("#object_type").val();
	var msg = "确定重发商品吗？";
	if(object_type=='product'){
		msg = "确定重发货品吗？";
	}
	if(confirm(msg)){
		var batch_id = $("#batch_id").val();
		var start_date = $("#start_date").val();
		var end_date = $("#end_date").val();
		var sku = $("#sku").val();
		
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/goods!publishAgain.do?ajax=yes",
			data:"params.batch_id=" + batch_id +"&params.start_date=" + start_date +"&params.end_date=" + end_date +"&params.sku=" + sku +"&params.object_type=" + object_type +"&m=" + new Date().getTime(),
			dataType:"json",
			success:function(result){
				alert(result.params.message);
				if(result.result=='0'){
					window.location="goods!synchLogs.do";
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

//zhengchuiliu
function exportLogs(){
	var search_url = $("#searchSynchLogsForm").attr("action");
	var url = ctx + "/shop/admin/goods!exportSynchLogs.do?ajax=yes&excel=yes";
	$("#searchSynchLogsForm").attr("action",url);
	$("#searchSynchLogsForm").submit();
	$("#searchSynchLogsForm").attr("action",search_url);
}

$(function(){
	$(".relaDiv").mouseenter(function(){
        showOrg(this);   
	});
	
	$(".relaDiv").mouseleave(function(){
        hideOrg(this);   
	});
})