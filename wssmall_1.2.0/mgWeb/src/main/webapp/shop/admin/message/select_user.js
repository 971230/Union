/**
  搜索：searchBtn
  选择了：selectStaffBtn
*/
//用户选择器
var AgentSelector={
	conid:undefined,
	init:function(conid,onConfirm)	{
		this.conid = conid;
		var self  = this;
		$("#searchBtn").click(function(){//searchBtn 搜索
			self.search(onConfirm);
		});
		
	
		$("#"+conid+" .submitBtn").click(function(){
		
		/*
	    if(sel_value!=-1){
	     $("#user_id").val(sel_value);
		 $("#user_name").val(sel_text);
	     }
	     else{
		   	*/	
			    var len =$("[name='userid_checkbox']:checked").length;
				var user_idArr = [];
				var user_nameArr = [];
				if(len ==0)
				{
					alert("请选择要发送的对象！");
					return false;
				}
				$("[name='userid_checkbox']:checked").each(function(){
					var user_id = $(this).val().split("|")[0];
	                var user_name = $(this).val().split("|")[1];
					user_idArr.push(user_id);
					user_nameArr.push(user_name);
				})
				var user_id_idstr =user_idArr.join(",");
				var user_name_namestr = user_nameArr.join(",");
			
				$("#user_id").val(user_id_idstr);
				$("#user_name").val(user_name_namestr);
		
	    // }
		   
			Eop.Dialog.close(conid);
		});
		 
	},
	search:function(onConfirm){
	    
		var self = this;
		var options = {
		       url : app_path+'/shop/admin/message/messageAction!findUser.do?ajax=yes',
				type : "post",
				dataType : 'html',
				success : function(result) {				
					$("#"+self.conid).empty().append(result);
					self.init(self.conid,onConfirm);
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
 
		$("#serchform").ajaxSubmit(options);		
	},
	
	open:function(conid,onConfirm){
	  	var self= this;
		var url=app_path+'/shop/admin/message/messageAction!findUser.do?ajax=yes&founder=-1';;
		$("#"+conid).load(url,function(){
		  self.init(conid,onConfirm);
			
		});
		Eop.Dialog.open(conid);		
	}
};


