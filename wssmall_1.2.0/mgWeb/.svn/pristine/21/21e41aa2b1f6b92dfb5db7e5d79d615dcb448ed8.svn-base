function check_user(){
	var username = $("#user_name").val();
      if(username.length==0){
      alert("请选择收件人!");
       return false;
      }
     return true;
}
/**
关联分销商
*/
var UserAdmin_agent={
     init:function(){
         var self=this;
         Eop.Dialog.init({id:"refAgentDlg",modal:false,title:"选择收件人",width:'750px'});
         
         $("#refAgentBtn").click(function()
          {
            AgentSelector.open("refAgentDlg",null);
           
         });
     }, 
     doinsert : function() {
		var self =this;
		  var url =  "messageAction!add.do?ajax=yes";
	      
		Cmp.ajaxSubmit('theForm', '', url, {}, self.insertjsonBack, 'json');

	},
	
	
	insertjsonBack : function(responseText) { // json回调函数
	
	   
		if (responseText.result == 1) {
			alert(responseText.message);
		   window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);	
		    window.location="messageAction!listById.do?num=2&type=0&senderState=0";
		}
	},
     
}


$(function(){
    $("form.validate").validate();
	UserAdmin_agent.init();
	$("#btn").click(function(){
	 
	    
        $("#theForm").validate();
      
	    if(check_user()){
	     UserAdmin_agent.doinsert();
	    }
	  	else{
	  	  return false;
	  	}

	});
	$("#sel_user").bind("change",function(){
	  var selVal = $("#sel_user").val();

	   if(selVal==-1){
	       $("#user_id").val("");
	       $("#user_name").val("");
	       $("#Sel_all").show();
	   }
	   else{
	 
	   var sel_value= $("#sel_user").attr("value");
	   var sel_text = $("#sel_user").find("option:selected").text();
	     
	       $("#user_id").val(sel_value);
	       $("#user_name").val(sel_text);
	       $("#Sel_all").hide();
	   }
	});
	
	
});







