var Msgs = {
	init : function() {
       
		var self = this;

		$("#send_delete_Btn").click(function() {
					self.dodelete(2);
					return false;
				});
		 $("#reciver_deleteBtn").click(function(){
		            self.dodelete(1);
		            return false;
		 });
	     $("#toggleChk").click(function(){
	      $("input[name=msgid_checkbox]").attr("checked",this.checked);
	     });
	   /*
	     $("#updatebtn").click(function(){
	    
	       self.doinsert();
	 
	     });
	     */
	},

	dodelete : function(num) {
		var self =this;
		var len = $("[name='msgid_checkbox']:checked").length;
	 
		if (len == 0) {
			alert("请选择要删除的信息");
			return;
		}
		if (!confirm("确认要删除这些消息吗?")) {
			return;
		}
		var msg_idArr = [];
		    
		$("[name='msgid_checkbox']:checked").each(function(){
		
					var msg_id = $(this).val();
				    
					msg_idArr.push(msg_id);
				})
		var msg_idstr = msg_idArr.join(",");
		var url = ctx + "/shop/admin/messageAction!delete.do?ajax=yes&messId="
				+ msg_idstr + "&num=" + num;
	
		Cmp.ajaxSubmit('btn_form', '', url, {}, self.jsonBack, 'json');

	},
	
	
	jsonBack : function(responseText) { // json回调函数
		if (responseText.result == 1) {
	      
			alert(responseText.message);
			//$("#msg_checkBox :checkbox").attr("checked", false); 
			window.location.reload(true);
			
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		//	$("#msg_checkBox :checkbox").attr("checked", false); 
			window.location.reload(true);
		}
	},
	
	
	    doinsert : function() {
		var self =this;
		    var url =  "messageAction!replyAdd.do";
	     
	     	Cmp.ajaxSubmit('replyForm', '', url, {}, self.replyjsonBack, 'json');
	      //Cmp.excute('', url, {}, self.replyjsonBack, 'json');
		 

	},
	
	
	replyjsonBack : function(responseText) { // json回调函数
	
		if (responseText.result == 1) {
			alert(responseText.message);
		
	  window.location ="messageAction!reply.do?readMsgId=${message.m_senderid}&replyTopic=${message.m_topic }";
		}
		if (responseText.result == 0) {
			alert(responseText.message);	
		    window.location="messageAction!listById.do?num=2&type=0&senderState=0";
		}
	},
};

$(function() {
 
    Msgs.init();
	
});