var taskAdd = {
	init:function(){
		var self = this;
		this.lanChange();
		this.typeChange();
		this.submitTask();
	},
	lanChange:function(){
		$("#lan_code").bind("change",function(){
			
			var lan_code  = $(this).val();
			var url = ctx + "/shop/admin/task!getRegion.do?ajax=yes&lan_code="+lan_code;
			$.ajax({  
		              url :url,  //后台处理程序  
		              type:"post",    //数据发送方式  
		              async:false,  
		              dataType:"json",   //接受数据格式             
		              error: function(){  
		                  alert("服务器没有返回数据，可能服务器忙，请重试");  
		              },  
		              success: function(json){
		                 var mySelect = document.getElementById("city_code");
		                 mySelect.options.length=0;
		                 var opp = new Option("-请选择-","");
		                 	mySelect.add(opp);
		                 for(var xk=0;xk<json.length;xk++){
		                 	var obj = json[xk];
		                 	obj.region_name;
		                 	var opp = new Option(obj.region_name,obj.region_id);
		                 	mySelect.add(opp);
		                 }
		              }   
		    });                     
		});
	},
	typeChange:function(){
		$("#task_type").bind("change",function(){
			var type = $(this).val();
			if(type=="AIR_CHARGE" || type=="PAY_CHARGE"){//交费类业务
				$("#unit").val("元");
			}
			else if(type=="NEW_BUSINESS_OPEN" || type=="OFFER_ORDER"
						|| type=="OFFER_CHANGE" || type=="OFFER_CANCEL" || type=="NEW_BUSINESS_CANCEL"){
				$("#unit").val("个");
			}
			else if(type=="SELNUM_OPEN"){
				$("#unit").val("户");
			}
		});
	},
	submitTask:function(){
		$("#submitBtn").bind("click",function(){
			var url = ctx + "/shop/admin/"+$("#taskInfoForm").attr("action")+"?ajax=yes";
			$("#taskInfoForm").validate();
			Cmp.ajaxSubmit('taskInfoForm', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
							window.location= ctx + '/shop/admin/task!taskList.do';	
					}
						
			},'json');
		});
	}
}

$(function(){
	 Eop.Dialog.init({id:"refOrgDlg",modal:false,title:"已下发任务列表",width:'800px'});
	taskAdd.init();
	$("#taskSel").click(function(){
      var url = ctx + "/shop/admin/task!getPTask.do?ajax=yes";
		
      Eop.Dialog.open("refOrgDlg");
		$("#refOrgDlg").load(url,function(){
			 initDlgClk(); 
		}); 		  
     });
     if(lan_id != null && lan_id != ""){
     	$("#lan_code").trigger("change");
     }
     $("#task_cate").change(function(){
     	if($("#task_cate").find("option:selected").val() == "1"){
     		$("#target_tr").hide();
     		$("#target_rate").removeAttr("required");
     		$("#total_number").html("任务总额：");
     		$("#target_number").html("任务目标额：");
     		$("#tips").hide();
     		$("#target_num").val("");
     	}else{
     		$("#target_tr").show();
     		$("#total_number").html("任务总量：");
     		$("#target_number").html("任务目标量：");
     		$("#tips").show();
     		$("#target_num").val("");
     	}
     });
});

function initDlgClk(){
	$("#form_tc #insureBtn").unbind("click").click(function(){     //选择按钮
		var selectNum = $("#form_tc [name='selectTask']:checked");
			
		if(selectNum.length == 0){
			alert("请选择一条记录！");
			return;
		}
		
		var taskInfo = selectNum.attr("ele").split("__");
		
		$("#p_task_id").val(taskInfo[0]);
		$("#p_task_name").val(taskInfo[1]);
		$("#task_num").val(taskInfo[8]);
		
		$("#task_type").val(taskInfo[5]);
		$("#task_cate").val(taskInfo[6]);
		
		Eop.Dialog.close("refOrgDlg");
	});
}

function calTargetNum(){
	if($("#task_cate").find("option:selected").val() == "0"){
		var task_num = $("#task_num").val()==""?0:$("#task_num").val();
		var target_rate = $("#target_rate").val().replace("%","");
		var target_num = Math.ceil((task_num * target_rate) /100);
		$("#target_num").val(target_num);
	}else{
		$("#target_num").val($("#task_num").val());
	}
}