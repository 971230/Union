
function checkpwd1(){
	 if( $("#updatePwd").get(0).checked )  {
		if(*$("#repwd").val()!=   $("#pwd").val() ){
			alert("两次密码不一致！");
		return false;
		}
	 }
	return true;
}

function ischeckedLength(){
    if($("input[name='adminUser.founder']:checked").length==0){
        alert("你还没有选择类型！");
        return false;
    }else{
       return true;
    };
}

	var self = this;
	$("form.validate").validate();
	
	
	
	
	
	    $("#editbtn").click(function(){
        $("#updateForm").validate();
      
	    if(checkpwdandCrm()){
	       
	     UserAdmin_agent.doUpdate();
	    }
	  	else{
	  	  return false;
	  	}
	  
	});
	








