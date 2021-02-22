$(function(){
	userDetail.init();
});
var userDetail={
		 init:function(){
			 $("form.validate").validate();
				$("#addSaveBtn").hide();
				$("#editSaveBtn").hide();
				$("#cancelBtn").hide();
				$("#pwdtr").hide();
				$("#pwdChkTr").hide();
				
				userDetail.add();
				userDetail.cancel();
				userDetail.selRole();
			    userDetail.addSave();
			    userDetail.tabclick();
			    userDetail.edit();
			    userDetail.editSave();
			    userDetail.typeChange();
			    userDetail.initTypeList();
		 },
		 initTypeList:function(){
			 $.ajax({
	   	         	url:ctx + "/core/admin/user/userAdmin!initAdd.do?ajax=yes",
	   	         	dataType:"json",
	   	         	data:{},
	   	         	success:function(reply){
	   	         		$("#user_type").empty().append("<option value=''>--请选择--</option>");
	   	         		for(var i=0;i<reply.length;i++){
	   	         			var obj = reply[i];
	   	         		    var htmlStr="<option value='"+obj.founder+"'>"+obj.type_name+"</option>";
	   	         		    $("#user_type").append(htmlStr);
	   	         		}
	   	         	},
	   	         	error:function(){
	   	         		
	   	         	}
	   	    	}); 
		 },
		 tabclick:function(){
		     $(".graph_tab li").each(function(){
		      $(this).click(function(){
		          
		         $(".graph_tab li").attr("class","");
		         $(".detail").hide();
		         $(".roleInfo").hide();
		         $(this).attr("class","selected");
		          var id = $(this).attr("divId");
		        
		         $("."+id).show();
		      });
		     });
		    },
		add:function(){
			$("#add_btn").click(function(){
				
				var party_id =$("#party_id").val();
				if(party_id==''||party_id.length==0||party_id=='undefined'){
					alert("请选择组织");
					return false;
				}
				
				var addSelectedStr = $(this).attr("selected");
				var editSelectedStr = $("#mod_btn").attr("selected");
				if(addSelectedStr=="true"||editSelectedStr=="true"){
					alert("请保存当前的操作或者取消当前操作");
					return false;
				}
				$(this).attr("selected","true");
				$("#userListIframe").contents().find("#userform table tr").attr("class","");
				$("#userid").val("");
				$("#user_type").val("");
				$("#reltype").val("");
				$("#partner_name").val("");
				$("#relcode").val("");
				$("#supplier_name").val("");
				$("[name='agentLine']").hide();
				$("[name='supplierLine']").hide();
				$("#user_name").val("");
				$("#real_name").val("");
				$("#pwdtr").show();
			    $("#user_name").attr("disabled",false);
				$("#usertype").show();
				$("#pwd").val("");
				$("#repwd").val("");
				$("#email").val("");
				$("#remark").val("");
				$("#phone_num").val("");
				$("#roleBody").empty();
				$("#addSaveBtn").show();
				$("#cancelBtn").show();
				$("#pwdChkTr").hide();
				
				 
				
			});
		},
		edit:function(){
			
			$("#mod_btn").click(function(){
				var selectedStr = $(this).attr("selected");
				var addSelectedStr = $("#add_btn").attr("selected");
				if(selectedStr=="true"||addSelectedStr=="true"){
					alert("请保存当前的操作或者取消当前操作");
					return false;
				}
				
				var userid = $("#userid").val();
				if(userid==""||userid=='undefined'||userid.lenght==0){
					alert("请选择一个要编辑的员工");
					return false;
				}
				
				var is_syn = $("#is_syn").val();
				
				$("#updatePwdChk").removeAttr("disabled");
				$("#pwd").removeAttr("disabled");
				$("#repwd").removeAttr("disabled");
				
				if(!"1" == is_syn){
					$("#user_type").removeAttr("disabled");
					$("#real_name").removeAttr("disabled");
					$("#userstate").removeAttr("disabled");
					$("#td_sex input").removeAttr("disabled");
					
					$("#phone_num").removeAttr("disabled");
					$("#email").removeAttr("disabled");
					$("#remark").removeAttr("disabled");
				}else{
					$("#email").removeAttr("disabled");
					$("#remark").removeAttr("disabled");
				}
				
				$(this).attr("selected","true");
				$("#addSaveBtn").hide();
				$("#editSaveBtn").show();
				$("#cancelBtn").show();
				$("#pwdChkTr").show();
			    $("#pwdtr").hide();
			    $("#user_name").attr("disabled",true);
			    $("#pwd").val("");
			    $("#repwd").val("");
			    $("#updatePwdChk").attr("checked",false);
			    $("#updatePwdChk").click(function(){
			    	if($(this).attr("checked")==true){
			    		//$("#pwdChkTr").hide();
			    		$("#pwdtr").show();
			    	}else{
			    		$("#pwdtr").hide();
			    		$("#pwd").val("");
			    		$("#repwd").val("");
			    	}
			    });
				//pwdChkTr pwdtr
				
			});
			
		},
		validateInfo:function(){
	
			var username = $("#user_name").val();
			var realname = $("#real_name").val();
			var user_type = $("#user_type").val();
			var phone_no = $("#phone_num").val();
			var pwd = $("#pwd").val();
			var repwd = $("#repwd").val();
			var state = $("#userstate").val();
			
			if(username=="undefined"||username.length==0||username==''){
				alert("请输入用户工号");
				return false;
			}
			
			if(realname=="undefined"||realname.length==0||realname==''){
				alert("请输入用户名称");
				return false;
			}
			
			if(user_type=="undefined"||user_type.length==0||user_type==''){
				alert("请选择用户类型");
				return false;
			}
			
			if(state=="undefined"||state.length==0||state==''){
				alert("请选择状态");
				return false;
			}
			
			if(phone_no=="undefined"||phone_no.length==0||phone_no==''){
				alert("请输入电话号码");
				return false;
			}
			
			if($("input[name='roleids']").length==0){
			       alert("请选择角色");
			       return false;
			  }

			if(pwd!=repwd){
				alert("密码与确认密码不一致");
				return false;
			}
			
			if (username.length > 0) {
				if (/^[a-zA-Z0-9]*$/.test(username) == false) {
      
					alert("用户工号只能由数字或者字母组成");
					return false;
				}
			}
			
			if (/^[0-9]{11}$/.test(phone_no) == false) {
				alert("电话号码只能是11位的数字组成");
				return false;
			}
			var email = $("#email").val();
			
		    if((!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email))&&email.length!=0) {
				    alert("邮箱格式不正确");
				   return false;
				}
		
			
			return true;
		},
		addSave:function(){
			
			$("#addSaveBtn").click(function(){
				var pwd = $("#pwd").val();
				if(!userDetail.validateInfo()){
					return false;
				}
				if(pwd=="undefined"||pwd.length==0||pwd==''){
					alert("请输入密码");
					return false;
				}
				var orgid = $("#orgid").val();
				var url =ctx+"/core/admin/user/userAdmin!orgUserSave.do?ajax=yes&org_id="+orgid;
				Cmp.ajaxSubmit('userDetailForm', '', url, {}, userDetail.saveJsonBack, 'json');
			});
		},
		editSave:function(){
			$("#editSaveBtn").click(function(){
			
				if(!userDetail.validateInfo()){
					
					return false;
				}
				
				if($("#updatePwdChk").attr("checked")==true){
					
					var pwd = $("#pwd").val();
					if(pwd=="undefined"||pwd.length==0||pwd==''){
						alert("请输入密码");
						return false;
					}
				}
				var orgid = $("#orgid").val();
				
				var url =ctx+"/core/admin/user/userAdmin!orgUserSave.do?ajax=yes&org_id="+orgid;
				
			    $("#user_name").attr("disabled",false);
				Cmp.ajaxSubmit('userDetailForm', '', url, {}, userDetail.saveJsonBack, 'json');
			});
			
		},
		saveJsonBack:function(reply){
					alert(reply.message)
					 if(reply.result=="0"){
						  $("#addSaveBtn").hide();
						  $("#editSaveBtn").hide();
						  $("#cancelBtn").hide();
						  $("#add_btn").attr("selected","");
						  $("#mod_btn").attr("selected","");
					      var org_id = reply.org_id;
					     
					      var url = ctx + "/core/admin/user/userAdmin!orgUserList.do?org_id="+org_id;
					    
					      var str =" <iframe style='height:100%;width:100%;' name='userListIframe' id='userListIframe' src='"+url+"'></iframe>"
					      $("#userPanle").empty().html(str);
					     
					 
				 }
				
		},
		
		cancel:function(){
			$("#cancelBtn").click(function(){
				
				$("#userListIframe").contents().find("#userform table tr").attr("class","");
				
                $("#reltype").val("");
                $("#relcode").val("");
                $("#partner_name").val("");
				$("#addSaveBtn").hide();
				$("#editSaveBtn").hide();
				$("#cancelBtn").hide();
				$("#add_btn").attr("selected","");
				$("#mod_btn").attr("selected","");
				$("#updatePwdChk").attr("checked",false);
				$("#pwdChkTr").hide();
				
				$("#pwdtr").hide();
				$("#userform table tr",window.parent.document).attr("class","");
				
				$(".user_msg_table input").attr("disabled",true);
				$(".user_msg_table select").attr("disabled",true);
			});
		},
		  selRole:function(){
		      $("#selRoleBtn").unbind("click").click(function(){
		        var url= ctx + '/core/admin/user/userAdmin!roleList.do?ajax=yes';
				$("#selRoleDlg").load(url,{},function(){});
				 Eop.Dialog.open("selRoleDlg");
		      });
		  },
		  typeChange:function(){
			 
			  $("#user_type").change(function(){
					var userFounder = $(this).val();
					if(userFounder==3){//代理商工号类型
						$("[name='agentLine']").show();
						$("#partner_name").attr("required",true);
						$("#reltype").val("partner");
						//$("#phone_num").attr("readonly",true);
						
						$("[name='supplierLine']").hide();
						$("#supplier_name").val("");
						$("#supplier_name").attr("required","");
					}else if(userFounder==4){
						$("[name='supplierLine']").show();
						$("#supplier_name").attr("required",true);
						$("#reltype").val("supplier");
						//$("#phone_num").attr("readonly",true);
						
						$("[name='agentLine']").hide();
						$("#partner_name").val("");
						$("#partner_name").attr("required","");
					}else{
						/*代理商*/
						$("[name='agentLine']").hide();
						$("#partner_name").val("");
						$("#partner_name").attr("required","");
						/*供货商*/
						$("[name='supplierLine']").hide();
						$("#supplier_name").val("");
						$("#supplier_name").attr("required","");
						
						$("#relcode").val("");
						$("#reltype").val("");
					//	$("#phone_num").attr("readonly",false);
						
					}
				});
		  }
}