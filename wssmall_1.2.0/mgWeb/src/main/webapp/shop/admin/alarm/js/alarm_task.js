
var AlarmTask=$.extend({},Eop.Grid,{
    dlg_id:'addAlarmTask',
    title:'添加',
	init:function(){
		var self =this;
		
		//全选
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		
		//删除对账告警任务
		$("#delAlarmTaskBtn").click(function(){self.doStaffDelete();});
		
		//添加对账告警任务
		$("#addAlarmTaskBtn").click(function(){
			var alarm_task_type=$("[name='alarm_task_type']").val();
			
			var url="";
			if(alarm_task_type==1){
				url=ctx+'/shop/admin/alarm!reconciliationAlarm.do?alarm_task_typed=1';
			}
			
			if(alarm_task_type==2){
				url=ctx+'/shop/admin/alarm!taskAlarm.do?alarm_task_typed=2';
			}
			location.href=url;
		});
		
		Eop.Dialog.init({id:'addAlarmTask',modal:true,title:this.title,width:'850px'});
	},
	deletePost1:function(url,msg){
		var self =this;
		url=url.indexOf('?')>=0?url+"&":url+"?";
		url+="ajax=yes";
		var options = {
				url : url,
				type : "POST",
				dataType : 'json',
				success : function(result) {	
					$.Loading.hide();
					if(result.result==0){
						self.deleteRows();
						if(msg)
							alert(msg);
					}else{
						alert(result.message);
					}
				},
				error : function(e) {
					$.Loading.hide();
					alert("出现错误 ，请重试");
				}
			};

			$('#gridform').ajaxSubmit(options);
	},
	
	//删除的对账告警任务
	doStaffDelete:function (){
	  if(!this.checkIdSeled()){
			alert("请选择要删除的告警任务");
			return ;
		}
		
		var alarm_task_id=[];
		$("input[name='id']").each(function(){
	         if($(this).attr("checked")){
	            if($(this).attr("alarm_task_id")!=""){
	              alarm_task_id.push($(this).attr("alarm_task_id"));
	            }
	         }
	     });
	 
		if(!confirm("确认要将此告警任务彻底删除吗？删除后将不可恢复")){	
			return ;
		}
		$.Loading.show("正在删除...");
		
		this.deletePost1(basePath+"alarm!deleteReconciliaction.do");
	},
	toUrlOpen:function(url){
	   Cmp.excute(this.dlg_id,url,{},this.onAjaxCallBack);
	   Eop.Dialog.open(this.dlg_id);
	},
	onAjaxCallBack:function(){//ajax回调函数
		$('input.dateinput').datepicker();
	},
	page_close:function(){
	     Eop.Dialog.close(this.dlg_id);
	}
});

$(function(){
	AlarmTask.init();
})