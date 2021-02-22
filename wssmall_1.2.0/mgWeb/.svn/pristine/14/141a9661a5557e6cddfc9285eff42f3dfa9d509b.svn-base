/**
  搜索：searchBtn
  选择了：selectStaffBtn
*/
//分销商选择器
var AgentSelector={
	conid:undefined,
	init:function(conid,onConfirm)	{
		this.conid = conid;
		var self  = this;
		$("#agentSearchBtn").click(function(){//searchBtn 搜索
			self.search(onConfirm);
		});
		//$("#toggleChk").click(function(){//点击了全选复选框
			//$("input[name=staffid]").attr("checked",this.checked);
	//	});
		$("#"+conid+" .submitBtn").click(function(){
		   
			if($('input[name="agentid"]:checked').length >0)
			{	
				//赋值
				var value = $('input[name="agentid"]:checked').val();
				
				//$("#ref_no").val(value.split(",")[0]);
				$("#relcode").val(value.split(",")[0]);
			    $("#real_name").val(value.split(",")[1]);
			    $("#user_name").val(value.split(",")[2]);
				$("#partner_name").val(value.split(",")[1]);
				var phoneNumber = value.split(",")[3];
				$("#phone_num").val(phoneNumber);
			   
			}
			Eop.Dialog.close(conid);
		});
		 
	},
	search:function(onConfirm){
		var self = this;
		var options = {
		       url : app_path+'/core/admin/user/userAdmin!getAgentFind.do?ajax=yes',
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
 
		$("#agentSerchform").ajaxSubmit(options);		
	},
	
	open:function(conid,onConfirm){
	  	var self= this;
		var url=app_path+'/core/admin/user/userAdmin!getAgentFind.do?ajax=yes';
		$("#"+conid).load(url,function(){
		  self.init(conid,onConfirm);
			
		});
		Eop.Dialog.open(conid);		
	}
};


