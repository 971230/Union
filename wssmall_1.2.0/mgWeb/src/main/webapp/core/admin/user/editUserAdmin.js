function checkpwdandCrm(){
	var pwd = $("#pwd").val();
	var repwd = $("#repwd").val();
    var phone_num = $("#phone_num").val();
	var dep_name = $("#dep_name").val();
	if(pwd!=repwd ){
	alert("两次密码不一致！");
		return false;
	}
	 if(dep_name.length==0){
      alert("请选择组织");
      return false;
     }
	var email = $("#email").val();
	
	if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)&&email.length!=0) {
		    alert("邮箱格式不正确");
		   return false;
		}
	var founder = $("#cur_founder").val();
	
	if(founder!=2){
 
	/*var recode = $("#refCrm_code");
	if(recode.length==0){
		alert("请选择关联的CRM工号！");
		return false;
	}*/
	}
	
	
    if(phone_num.length==11){
     
      if (!(/^[0-9]+$/.test(phone_num))) 
         {
        
         alert("电话号码只能是11位的数字组成");
         return false;
         }
       }else{
         alert("电话号码只能是11位的数字组成");
         return false;
       }
	if($("input[name='roleids']:checked").length==0){
	  alert("请至少选择一个角色");
	  return false;
	}
//	if($("#lan_sel").val().length==0){
//	  alert("请选择本地网");
//	  return false;
//	}
	
	
	
	var username = $("#username").val();
	if(username.length>15){
      alert("用户名长度不能大于18位");
      return false;
     }
	if(username.length>0){
     if(!(/^[a-zA-Z0-9]+$/.test(username))){    
              alert("用户名只能是数字或者字母组成");
              return false;
       }
     }
   
     
	       return true;
}
function checkpwd1(){
	 if( $("#updatePwd").get(0).checked )  {
		if(  $("#repwd").val()!=   $("#pwd").val() ){
			alert("两次密码不一致！");
		return false;
		}
	 }
	return true;
}

/**
关联分销商
*/
var UserAdmin_agent={
     init:function(){
         var self=this;
      /*    $("#btn").click(function(){
          return  checkpwdandCrm();
          
          
          });*/
         Eop.Dialog.init({id:"refAgentDlg",modal:false,title:"关联分销商",width:'500px'});
         
         $("#refAgentBtn").click(function(){
           if(ischeckedLength()){
            AgentSelector.open("refAgentDlg",null);
            };
         });
     } , 
     doUpdate : function() {
		var self =this;
          var lan_name = $("#lan_sel").find('option:selected').text();
	      $("#lan_name").val(lan_name);
		  var url =  "userAdmin!editSave.do?ajax=yes";
	    $("#username").attr("disabled",false);
		Cmp.ajaxSubmit('updateForm', '', url, {}, self.updatejsonBack, 'json');

	},
	
	
	updatejsonBack : function(responseText) { // json回调函数
	   
		if (responseText.result == 1) {
			alert(responseText.message);
			if('该工号已被占用，请换一个工号'==responseText.message){
		       $("#username").val("");
		        wondow.location.reload();
		    }
		   window.location=ctx + "userAdmin!edit.do?id=${userAdmin.userid}";
		}
		if (responseText.result == 0) {
			alert(responseText.message);	
		  
		    window.location="userAdmin.do?state=2&usertype=-1";
		}
	},
};

/**
关联CRM
*/
var UserAdmin_staff={
     init:function(){
         var self=this;
         Eop.Dialog.init({
			id : "refAgentDlg",
			modal : false,
			title : "关联分销商",
			width : '500px'
		});

		$("#refAgentBtn").click(function() {
			if (ischeckedLength()) {
				AgentSelector.open("refAgentDlg", null);
			};
		});
     }    
};
function ischeckedLength(){
    if($("input[name='adminUser.founder']:checked").length==0){
        alert("你还没有选择类型！");
        return false;
    }else{
       return true;
    };
}
$(function(){

	var self = this;
	$("form.validate").validate();
	$("#notSuperChk").click(function(){ //电信管理员
		if(this.checked)
		$("#crmTr").show();
		$("#lantr").show();
	    $("#agenTr").hide();
	    disableTr("agenTr");
	    valibTr("crmTr");
	});
	$("#superChk").click(function(){//超级管理员
		if(this.checked);
		$("#crmTr").show();
	    $("#agenTr").hide();
	    $("#lantr").show();
	    disableTr("agenTr");
	    valibTr("crmTr");
	});	
	$("#updatePwd").click(function(){
		if(this.checked){
			$("#pwdtr").show();
			$("#repwdtr").show();
						
		}else{
			$("#pwdtr").hide();
			$("#repwdtr").hide();
		}
	});
	$("#twoAgentChk").click(function(){ //二级分销商
	    if(this.checked){
	       $("#agenTr").show();
	       $("#crmTr").hide();
	       $("#lantr").hide();
	       disableTr("crmTr");
	       valibTr("agenTr");
	    }
	
	});
	$("#oneAgentChk").click(function(){ //一级分销商
	     if(this.checked){
	        $("#crmTr").show();
	        $("#agenTr").hide();
	        $("#lantr").hide();
	     }
	});
	
	UserAdmin_agent.init();
	UserAdmin_staff.init();
	
	    $("#editbtn").click(function(){
        $("#updateForm").validate();
      
	    if(checkpwdandCrm()){
	       
	     UserAdmin_agent.doUpdate();
	    }
	  	else{
	  	  return false;
	  	}
	  
	});
	
});
function disableTr(retr){
   $("tr#"+retr).find("input").attr("disabled","disabled");
   
}
function valibTr(retr){
   $("tr#"+retr).find("input").removeAttr("disabled");
   }






