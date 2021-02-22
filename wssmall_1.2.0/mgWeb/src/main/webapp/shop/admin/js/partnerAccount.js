//分销商开户账户
var PartnerAccount=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		$("#delAddrBtn").click(function(){self.doMDelete();});
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
	},
    doMDelete:function(){
		
//		if(!this.checkIdSeled()){
//			alert("请选择要删除的数据");
//			return ;
//		}
	 
    	
    	if($("[name='radio_account_id']:checked").length==0){
			alert("请选择要删除的数据");
			return ;
		}
	 
		var account_id = $("[name='radio_account_id']:checked").val();
		if(!confirm("确认要删除数据吗？")){	
			return ;
		}
		
		$.Loading.show("正在删除数据...");
		
		this.deletePost1(basePath+"account!delete.do",account_id);
	},
	deletePost1:function(url,account_id){
		var self =this;
		url=url.indexOf('?')>=0?url+"&":url+"?";
		url+="ajax=yes";
		url+= '&account_id='+account_id;
		var options = {
				url : url,
				type : "POST",
				dataType : 'json',
				success : function(result) {	
					$.Loading.hide();
					if(result.result==0){
						alert("删除成功");
						window.location.reload();
						
					}else{
						alert(result.message);
					}
				},
				error : function(e) {
					$.Loading.hide();
					alert("出现错误 ，请重试");
				}
			};

			$('#gridform').ajaxSubmit(options);
			}
});

