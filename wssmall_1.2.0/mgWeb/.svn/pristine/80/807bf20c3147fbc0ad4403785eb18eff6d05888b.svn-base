
$(function(){
	//对option赋值
 	var orderstatus = $("#orderstatus_hide").val();
 	if(orderstatus != ""){
 		$("#orderstatus").val(orderstatus);
 	}
 	
 	var sourceFrom = $("#topSourceFrom").val();
 	if(sourceFrom != ""){
 		$("#sourceFrom").val(sourceFrom);
 	}
 	
    Eop.Dialog.init({id:"refAgentDlg",modal:false,title:"关联分销商",width:'500px'});
	$("#refAgentBtn").click(function(){
       	AgentSelector.open("refAgentDlg",null);
	});
});

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
		$("#searchBtn").click(function(){//searchBtn 搜索
			self.search(onConfirm);
		});
		
		$("#"+conid+" .submitBtn").click(function(){
			if($('input[name="agentid"]:checked').length >0 ) {	
				//赋值
				var value = $('input[name="agentid"]:checked').val();
				var arr = value.split(",");
				$("#userid").val(arr[0]);
				$("#username").val(arr[1]);
				if (arr.length == 3) { //工号没有realname情况
					$("#realname").val(arr[2]);
				}
			}
			Eop.Dialog.close(conid);
		});
		 
	},
	search:function(onConfirm){
	    
		var self = this;
		var options = {
		       url : app_path+'/shop/admin/buyoutCloudReport!queryAgent.do?ajax=yes',
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
		$("#query_agent_form").ajaxSubmit(options);		
	},
	
	open:function(conid,onConfirm){
	  	var self= this;
		var url=app_path+'/shop/admin/buyoutCloudReport!queryAgent.do?ajax=yes';
		$("#"+conid).load(url,function(){
		  self.init(conid,onConfirm);
			
		});
		Eop.Dialog.open(conid);		
	}
};


/**
 * 查询所有
 */
function clean_agent() {
	$("#realname").val("");
	$("#userid").val("");
	$("#username").val("");
}

