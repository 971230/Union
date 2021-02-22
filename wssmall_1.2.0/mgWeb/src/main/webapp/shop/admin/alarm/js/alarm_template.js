
var AlarmTemplate=$.extend({},Eop.Grid,{
    dlg_id:'addTemplate',
    title:'新增模板',
	init:function(){
		var self =this;
		Eop.Dialog.init({id:'addTemplate',modal:true,title:this.title,width:'850px'});
		Eop.Dialog.init({id:'showAlarmLog',modal:true,title:"日志列表",width:'850px'});
		//全选
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		
		
		$("[name='showAlarmLogList']").click(function(){
			 var url=app_path+'/shop/admin/alarm!showAlarmLog.do?template_id='+$(this).attr('template_id');
			 
			 location.href=url;
			 //Eop.Dialog.open("showAlarmLog");
		     //$("#showAlarmLog").load(url,function(){
		     //});
		});
		//删除模板
		$("#delAlarmTemplateBtn").click(function(){self.doStaffDelete();});
		
		//添加模板
		$("#addAlarmTemplateBtn").click(function(){
			var url=app_path+'/shop/admin/alarm!alarmTemplate.do';
			location.href=url;
			//self.toUrlOpen(url);
		});
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
	
	//删除的模板
	doStaffDelete:function (){
	  if(!this.checkIdSeled()){
			alert("请选择要删除的模板");
			return ;
		}
		
		var alarm_task_id=[];
		$("input[name='id']").each(function(){
	         if($(this).attr("checked")){
	            if($(this).val()!=""){
	              alarm_task_id.push($(this).val());
	            }
	         }
	     });
	 
		if(!confirm("确认要将此模板彻底删除吗？删除后将不可恢复")){	
			return ;
		}
		$.Loading.show("正在删除...");
		
		this.deletePost1(basePath+"alarm!deleteAlarmTemplate.do");
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
	AlarmTemplate.init();
})