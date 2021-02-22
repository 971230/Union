$(function(){
	Task.init();
	
});

var Task={
	 init:function(){
	 	var me = this;
	 	this.initTaskList();
	 	this.search();
	 	this.update();
	 	this.initOrgTree();
	 	this.revokeBtnClick();
	 	this.deleteBtnClick();
	 	this.finish();
	 },
	 initTaskList:function(){

        var lan_code = $("#lan_code").val();
        var city_code = $("#city_code").val();
        var task_type = $("#task_type").val();
        var task_name = $("#task_name").val();
        var create_time = $("#create_time").val();
        var state = $("#state").val();
        var finished = $("#finished").val();
        var url = ctx + "/shop/admin/task!searchTaskList.do?ajax=yes";
          $("#taskPanel").empty();
          $("#taskPanel").load(url,{lan_code:lan_code,city_code:city_code,
          										task_type:task_type,task_name:task_name,create_time:create_time,state:state,finished:finished},function(){
          }); 
	 },
	 initOrgTree:function(){
	 	 
	 },
    search:function(){
    	$("#qry_btn").bind("click",function(){
	        var lan_code = $("#lan_code").val();
	        var city_code = $("#city_code").val();
	        var task_type = $("#task_type").val();
	        var task_name = $("#task_name").val();
	        var create_time = $("#create_time").val();
	        var state = $("#state").val();
	        var finished = $("#finished").val();
	        var url = ctx + "/shop/admin/task!searchTaskList.do?ajax=yes";
	        $("#taskPanel").empty();
			
	        $("#taskPanel").load(url,{lan_code:lan_code,city_code:city_code,task_type:task_type,task_name:task_name,create_time:create_time,state:state,finished:finished},function(){
	        }); 
	        
	    });
    },
    update:function(){
    	var self = this;
    	$("#updateBtn").bind("click",function(){
    		var task_id = $("[name='id']:checked",$("#taskList")).val();
    		var task_state = $("[name='id']:checked",$("#taskList")).attr("taskState");
    		if(typeof(task_id)=="undefined"){
    			alert("请选择要修改的任务");
    			return ;
    		}
    		else if(task_state!='001'){
    			alert("该任务状态不是“已录入”，不能修改");
    			return ;
    		}
    	});
    },
    
    finish:function(){
    	$("#taskFinish").bind("click",function(){
    		var task_id=$("[name='id']:checked").val();
    		var taskCode=$("[name='id']:checked").attr("taskCode");
    		var taskName = $("[name='id']:checked").attr("taskName");
    		var taskState=$("[name='id']:checked").attr("taskState");
    		if(taskState==null || taskState==""){
    			alert("请选择需要关闭的任务");
    			return ;
    		}
    		if(taskState=="003"){
	    		if(confirm("请确认编号为" + taskCode + "，名称为" + taskName + "的任务已完成")){
					$.ajax({
						url: ctx + "/shop/admin/task!finishTask.do?ajax=yes",
						type:"post",
						data:{task_id:task_id},
						dataType:"json",
						success:function(json){
							alert(json.msg);
							$("#qry_btn").trigger("click");
						},
						error:function(a, b){
							$("#qry_btn").trigger("click");
						}
					});	
				}
    		}
    		else{
    			alert("该任务状态不是已下发，不能关闭");
    		}
    	});
    },
    
    revokeBtnClick:function(){
    	var self = this;
    	$("#cancelBtn").bind("click",function(){
    		var task_id=$("[name='id']:checked").val();
    		var taskCode=$("[name='id']:checked").attr("taskCode");
    		var taskName = $("[name='id']:checked").attr("taskName");
    		var taskState=$("[name='id']:checked").attr("taskState");
    		if(taskState=="003"){
	    		if(confirm("编号为" + taskCode + "，名称为" + taskName + "的任务即将撤销，撤销后将不再进行跟踪监控")){
					$.ajax({
						url: ctx + "/shop/admin/task!revokeTask.do?ajax=yes&task_id="+task_id,
						type:"post",
						dataType:"json",
						success:function(json){
							alert(json.msg);
							$("#qry_btn").trigger("click");
						},
						error:function(a, b){
							$("#qry_btn").trigger("click");
						}
					});	
				}
    		}
    		else{
    			alert("该任务状态不是已下发，不能撤销");
    		}
    	});
    },
    
    deleteBtnClick:function(){
    	$("#delBtn").bind("click",function(){
    		var task_id=$("[name='id']:checked").val();
    		var taskCode=$("[name='id']:checked").attr("taskCode");
    		var taskName = $("[name='id']:checked").attr("taskName");
    		var taskState=$("[name='id']:checked").attr("taskState");
    		if(taskState=="001"){
	    		if(confirm("编号为"+ taskCode + "，名称为"+ taskName+"的任务即将被删除，您确认本次操作吗？")){
					$.ajax({
						url: ctx + "/shop/admin/task!cancelTask.do?ajax=yes&task_id="+task_id,
						type:"post",
						dataType:"json",
						success:function(json){
							alert(json.msg);
							$("#qry_btn").trigger("click");
						},
						error:function(a, b){
							$("#qry_btn").trigger("click");
						}
					});	
					
				}
    		}
    		else{
    			alert("该任务状态不是已录入，不能删除");
    		}
    	});
    }
    
};
