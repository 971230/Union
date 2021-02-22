$(function(){
	//Eop.Dialog.init({id:"assignDiv", modal:false, title:"任务信息", width:"850px"});
	$(".grid tbody tr").bind("click",function(){
		var selectTr = $(this);
		$("[name=id]",selectTr).attr("checked","checked");
		var task_id = $("[name=id]",selectTr).val();
		var task_state = $("[name=id]",selectTr).attr("taskState");
		if(task_state=="001"){
			$("#updateBtn").attr("href","task!edit.do?task_id="+task_id);
		}
		else{
			$("#updateBtn").attr("href","javascript:;");
		}
	});
});

function assignTa(task_id){
	Eop.Dialog.open("assignDiv");
   	var url =ctx + "/shop/admin/task!assignment.do?ajax=yes&task_id="+task_id; 
   	$("#assignDiv").load(url,{},function(){
   		ret_back();
   	});
}

function ret_back(){
	$("#rightDiv #open_account").unbind("click").click(function(){
		var task_id = $("#tcForm #task_id").val();
		var task_code = $("#tcForm #task_code").val();
		
		if(confirm("确认下发当前编号"+ task_code + "的任务")){
			$.ajax({
				url: ctx + "/shop/admin/task!confirmSend.do?ajax=yes",
				type:"post",
				data:{task_id:task_id},
				dataType:"json",
				success:function(datas){
					alert(datas.msg);
					Eop.Dialog.close("assign_div");
					//window.location.reload();
				},
				error:function(a, b){
				
				}
			});	
		}
	
	});
}