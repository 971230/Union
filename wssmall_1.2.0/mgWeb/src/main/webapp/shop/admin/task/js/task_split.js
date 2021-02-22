$(function(){
	Eop.Dialog.init({id:"refPartnerDlg",modal:false,title:"分销商列表",width:"800px"});
	taskSplit.init();
});

var taskSplit = {
	init:function(){
		var me = this;
		this.setArea();
		this.addPartner();
		this.deletePartner();
		this.average();
		this.reset();
		this.savePartnerTask();
	},
	
	 addTr: function(userid, username, realname, lan_id, lan_name, city_id, city_name){
     		var isExists = false;
			$("#partnerList tbody tr").each(function(){
				var tr = $(this);
				if($("input[name='userid']", tr).val() == userid){
					alert("工号为："+username+"分销商"+realname+"已经添加");
					isExists = true;
				}
			});
			if(!isExists){
				var tr = $("<tr></tr>");
				$("<td></td>").html("<input type='hidden' name='userid' class='userid' value='"+userid+"'  />"+username).appendTo(tr);
				$("<td></td>").html(realname).appendTo(tr);
				$("<td></td>").html("<input type='hidden' name='lan_id' class='lan_id' value='"+lan_id+"'  /><input type='text' readOnly='true' name='lan_name' class='ipttxt lan_name' value='"+lan_name+"' />").appendTo(tr);
				$("<td></td>").html("<input type='hidden' name='city_id' class='city_id' value='"+city_id+"'  /><input type='text' readOnly='true' name='city_name' class='ipttxt city_name' value='"+city_name+"' />").appendTo(tr);
				$("<td></td>").html("<input type='text' id='task_number' class='ipttxt task_number' value='0' dataType='int'>").appendTo(tr);
				$("<td></td>").html("<a href='javascript:void(0);' name='removeBtn'>删除</a>").appendTo(tr);
				tr.appendTo("#partnerList tbody");
			}
     },
	
	setArea:function(){
		//统一设定分配区域 
		$("#sameArea").bind("click",function(){
			if($(this).attr("checked")){
				var tr_lan= $("#partnerList tbody").find("tr").eq(0);
				var tr_lan_id = $(".lan_id", tr_lan).val();
				var tr_lan_name = $(".lan_name", tr_lan).val();
				var tr_city_id = $(".city_id", tr_lan).val();
				var tr_city_name = $(".city_name", tr_lan).val();
				$("#partnerList tbody tr").each(function(){
					var tr = $(this);
					
					$(".lan_id",tr).val(tr_lan_id);
					$(".lan_name",tr).val(tr_lan_name);
					$(".city_id",tr).val(tr_city_id);
					$(".city_name",tr).val(tr_city_name);
					
				});
			}
		});
	},
	
	addPartner:function(){
		var me = this;
		$("#addBtn").bind("click",function(){
			Eop.Dialog.open("refPartnerDlg");
			var task_id = $("input[name='task_id2']").val();
	     	var url ="task!getPartner.do?ajax=yes&task_id="+task_id; 
	     		$("#refPartnerDlg").load(url,function(){
	     		me.initRefPartnerDlg();
	     	});
		});
	},
	//平均分配任务
	average:function(){
		$("#average").bind("click",function(){
			var average = 0;
			if($(this).attr("checked")){
				var target_num = $("#target_num").val();
				var num = $("#partnerList tbody tr").length;
				var average = Math.ceil(target_num/num);
			}
			$("#partnerList tbody tr").each(function(){
				var tr = $(this);
				
				$(".task_number",tr).val(average);
			})
		});
	},
	
	deletePartner:function(){
		//删除分销商
		$("[name='removeBtn']").live("click",function(){
			$(this).parent().parent().remove();
		});
	},
	
	reset:function(){
		var me = this;
		$("#resetBtn").bind("click",function(){
			if($("#sameArea").attr("checked")){
				$("#sameArea").trigger("click");
				var tr_lan= $("#partnerList tbody").find("tr").eq(0);
				var tr_lan_id = $(".lan_id", tr_lan).val();
				var tr_lan_name = $(".lan_name", tr_lan).val();
				var tr_city_id = $(".city_id", tr_lan).val();
				var tr_city_name = $(".city_name", tr_lan).val();
				
				$("#partnerList tbody tr").each(function(){
					var tr = $(this);
					
					$(".lan_id",tr).val(tr_lan_id);
					$(".lan_name",tr).val(tr_lan_name);
					$(".city_id",tr).val(tr_city_id);
					$(".city_name",tr).val(tr_city_name);
				});
			}
			
		});
	},
	
	savePartnerTask:function(){
		var me = this;
		$("#saveBtn").bind("click",function(){
			var task_ids = "";
			var userids = "";
			var lan_ids = "";
			var city_ids = "";
			var task_nums = "";
			var sum = 0;
			$("#partnerList").validate();
			$("#partnerList tbody tr").each(function(){
				
				var tr = $(this);
				if($(".task_number",tr).val() == null || $(".task_number",tr).val() == ""){
					alert("请填写为分销商分配的任务量");
					return ;
				}
				task_ids += $("input[name='task_id2']").val()+",";
				userids += $("input[name='userid']",tr).val()+",";
				lan_ids += $("[name='lan_id']",tr).val()+",";
				city_ids += $("[name='city_id']",tr).val()+",";
				task_nums += $(".task_number",tr).val()+",";
				sum += parseInt($(".task_number",tr).val());
			});
			if(parseInt($("#target_num").val()) > sum){
				alert("分销商分配量总和为"+sum+"，小于可分配任务总量，不能保存分配");
				return ;
			}
			if(parseInt($("#target_num").val()) < sum){
				alert("分销商分配量总和为"+sum+"，大于可分配任务总量，不能保存分配");
				return ;
			}
			var url = ctx + "/shop/admin/task!savePartnerTask.do?ajax=yes&task_ids=" + task_ids + "&userids=" + userids + "&lan_ids=" + lan_ids + "&city_ids=" + city_ids + "&task_nums="+task_nums;
			
			Cmp.ajaxSubmit('partnerList', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
						    window.location= ctx + '/shop/admin/task!taskList.do';
					}
						
			},'json');
			//document.taskDetailForm.action = url;
			//document.taskDetailForm.submit();
		});
	},
	initRefPartnerDlg:function(){
		
		$("#gridform #allCheck").unbind("click").bind("click",function(){
			
			if($(this).attr("checked")){
				$("#selectPartner input[name='checkBox']").attr("checked", true);
			}else{
				$("#selectPartner input[name='checkBox']").attr("checked", false);
			}
			
		});
		
		$("#choiceform #searchBot").unbind("click").click(function(){//searchBtn 搜索
			var url = ctx + "/shop/admin/task!getPartner.do?ajax=yes";
			
			var username = $("#choiceform input[name='adminUser.username']").val();
			var realname = $("#choiceform input[name='adminUser.realname']").val();
		
			var task_id = $("input[name='task_id2']").val();
			$("#choiceform #task_id").val(task_id);
			Cmp.ajaxSubmit('choiceform', 'refPartnerDlg', url, {"username":username, "realname":realname, "task_id":task_id}, taskSplit.initRefPartnerDlg);
	  });
	  
	   $("#submitlist #selectOkBot").unbind("click").bind("click",function(){
		        var selectObjs = $("#selectPartner").find("input[name='checkBox']:checked");
		       
		        if(selectObjs != null && selectObjs.length >0 ){
		        for(var i=0; i<selectObjs.length; i++){
			        var selectObj = selectObjs[i];
			        var userid = $(selectObj).attr("userid");
			       	var username = $(selectObj).attr("username"); 
			      	var realname = $(selectObj).attr("realname");
			       	var lan_name = $(selectObj).attr("lan_name");     
			       	var city_name = $(selectObj).attr("city_name");
					var lan_id = $(selectObj).attr("lan_id");
					var city_id = $(selectObj).attr("city_id");
					taskSplit.addTr(userid, username, realname, lan_id, lan_name, city_id, city_name);
						
		         }
		         Eop.Dialog.close("refPartnerDlg");
		       }else{
		          alert("您还没选择分销商");
		       }
       });   
	}
};
