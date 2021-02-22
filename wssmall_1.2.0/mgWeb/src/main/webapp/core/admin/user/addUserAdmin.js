function checkpwdandCrm() {
	var phone_num = $("#phone_num").val();
	var pwd = $("#pwd").val();
	var repwd = $("#repwd").val();
    var org_id  = $("#org_id").val();
    var dep_name = $("#dep_name").val();
	var relType = $("#reltype").val();
    var userFounder =$("#user_type").val();
    if(userFounder=='10000'){
    	alert("请选择员工类型");
    	return false;
    }
	if (pwd != repwd) {
		alert("两次密码不一致！");

		return false;
	}
	
   /* if(dep_name.length==0){
      alert("请选择组织");
      return false;
    }*/
   
	/*if (relType == 'STAFF') {
		var recode = $("#refCrm_code").val();
		if (recode.length == 0) {
			alert("请选择关联的CRM工号！");
			return false;
		}
	}

	if (relType == 'PARTNER') {

		var partnerRelcode = $("#ref_partnerID").val();

		if (partnerRelcode.length == 0) {
			alert("请选择关联的分销商ID！");
			return false;
		}
	}
*/
	var username = $("#username").val();
   
	if (username.length > 0) {
		if (/^[a-zA-Z0-9]{1,18}$/.test(username) == false) {

			alert("用户工号只能是长度小于18位数字或者字母组成");
			return false;
		}
	}
	 var email = $("#email").val();
	
    if((!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email))&&email.length!=0) {
		    alert("邮箱格式不正确");
		   return false;
		}
	
	if (/^[0-9]{11}$/.test(phone_num) == false) {
		alert("电话号码只能是11位的数字组成");
		return false;
	}

	if ($("input[name='roleids']:checked").length == 0) {
		alert("请至少选择一个角色");
		return false;
	}

	return true;
}
function checkpwd1() {
	if ($("#updatePwd").get(0).checked) {
		if ($("#repwd").val() != $("#pwd").val()) {

			alert("两次密码不一致！");
			return false;
		}
	}
	return true;
}

/**
 * 关联分销商
 */
var UserAdmin_agent = {
	init : function() {
		var self = this;
		/*
		 * $("#btn").click(function(){ return checkpwdandCrm();
		 * 
		 * 
		 * });
		 */
		Eop.Dialog.init({
					id : "refAgentDlg",
					modal : false,
					title : "关联分销商",
					width : '800px'
				});

		$("#refAgentBtn").click(function() {
						AgentSelector.open("refAgentDlg", null);
				});

	},

	doInsert : function() {
	    var lan_name = $("#lan_sel").find('option:selected').text();
	    $("#lan_name").val(lan_name);
	  
	    $("[name='adminUser.userid']").val($("[name='adminUser.username']").val());
		var self = this;
		var url =app_path+ "/core/admin/user/userAdmin!addSave.do?ajax=yes";
		
		Cmp.ajaxSubmit('theForm', '', url, {}, self.insertjsonBack, 'json');

	},

	insertjsonBack : function(responseText) { // json回调函数

		if (responseText.result == 1) {
			alert(responseText.message);

			if ('该工号已被占用，请换一个工号' == responseText.message) {
				$("#username").val("");
			}
			window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);

			window.location = app_path+ "/core/admin/user/userAdmin.do?state=2&usertype=-1";
		}

	}
}

/**
 * 关联CRM
 */
// var UserAdmin_staff={
// init:function(){
// var self=this;
// Eop.Dialog.init({id:"refStaffDlg",modal:false,title:"关联CRM",width:'500px'});
//         
// $("#refStaffBtn").click(function(){
//           
// if(ischeckedLength()){
// StaffSelector.open("refStaffDlg",null);
// };
//            
// });
// }
// }
function ischeckedLength() {
	if ($("input[name='adminUser.founder']:checked").length == 0) {
		alert("你还没有选择类型！");
		return false;
	} else {
		return true;
	};
}
$(function() {
	         /*下拉框改变事件 处理特殊的员工类型业务*/
			$("#user_type").change(function(){
				var userFounder = $(this).val();
				if(userFounder ==3){//代理商工号类型
					$("#agentTr").show();
					$("#partner_name").attr("required",true);
					$("#reltype").val("partner");
					
				}else{
					$("#agentTr").hide();
					$("#partner_name").val("");
					$("#relcode").val("");
					$("#partner_name").attr("required","");
					$("#reltype").val("");
				}
			});
			$("form.validate").validate();
			$("#notSuperChk").click(function() { // 电信管理员
						if (this.checked)
							$("#crmTr").show();
						$("#lantr").show();
						$("#lan_sel").attr("required", true);
						$("#agenTr").hide();
						disableTr("agenTr");
						valibTr("crmTr");
					});
			$("#superChk").click(function() {// 超级管理员
						if (this.checked)
							;
						$("#crmTr").show();
						$("#agenTr").hide();
						$("#lantr").show();
						$("#lan_sel").attr("required", true);
						disableTr("agenTr");
						valibTr("crmTr");
					});
			$("#updatePwd").click(function() {
						if (this.checked) {
							$("#pwdtr").show();
							$("#repwdtr").show();

						} else {
							$("#pwdtr").hide();
							$("#repwdtr").hide();
						}
					});
			$("#twoAgentChk").click(function() { // 二级分销商
						if (this.checked) {
							$("#agenTr").show();
							$("#crmTr").hide();
							$("#lantr").hide();
							$("#lan_sel").attr("required", false);
							disableTr("crmTr");
							valibTr("agenTr");
						}

					});
			$("#oneAgentChk").click(function() { // 一级分销商
						if (this.checked) {
							$("#crmTr").show();
							$("#agenTr").hide();
							$("#lantr").hide();
							$("#lan_sel").attr("required", false);
						}
					});

			UserAdmin_agent.init();
			$("#btn").click(function() {
                       
						//$("#theForm").validate();

						// return checkpwdandCrm();
						if (checkpwdandCrm()) {

							UserAdmin_agent.doInsert();
						} else {
							return false;
						}

					});
			

		});
function disableTr(retr) {
	$("tr#" + retr).find("input").attr("disabled", "disabled");

}
function valibTr(retr) {
	$("tr#" + retr).find("input").removeAttr("disabled");
}
