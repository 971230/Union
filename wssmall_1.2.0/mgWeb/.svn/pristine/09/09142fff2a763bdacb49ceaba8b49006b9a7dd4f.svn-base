//删除分销商
var PartnerAddr=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		$("#delAddrBtn").click(function(){self.doMDelete();});
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
	},
    doMDelete:function(){
		
		if(!this.checkIdSeled()){
			alert("请选择要删除的分销商地址");
			return ;
		}
	 
		if(!confirm("确认要将这些分销商彻底删除吗？删除后将不可恢复")){	
			return ;
		}
		$.Loading.show("正在删除分销商...");
		
		this.deletePost1(basePath+"parAddr!delete.do");
	},
	deletePost1:function(url,msg){
		var self =this;
		url=url.indexOf('?')>=0?url+"&":url+"?";
		url+="ajax=yes";
		var options = {
				url : url,
				type : "POST",
				dataType : 'json',
				success : function(result) {	
					$.Loading.hide();
					if(result.result==0){
						self.deleteRows();
						if(msg)
							alert(msg);
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

