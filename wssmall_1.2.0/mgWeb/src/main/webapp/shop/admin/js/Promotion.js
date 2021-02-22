var Promotion=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		$("#delBtn").click(function(){self.doDelete();});
	 
		$("#toggleChk" ).click(function(){
			self.toggleSelected(this.checked);}
		);
	},
	deletePost3:function(url,message){
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
						if(result.message)
							alert(result.message);
					}else{
						alert(result.message);
					}
				},
				error : function(e) {
					$.Loading.hide();
					alert("出现错误 ，请重试");
				}
			};

			$('#promotion_list_form').ajaxSubmit(options);
	},
	
	doDelete:function(){
		
		if(!this.checkIdSeled()){
			alert("请选择要删除的规则");
			return ;
		}
	 
		if(!confirm("确认删除这些规则吗？")){	
			return ;
		}
		var pmtidStr =[];
		$("input[name='pmtidArray']").each(function(){
	         if($(this).attr("checked")){
	            if($(this).attr("pmt_id")!=""){
	            	pmtidStr.push($(this).attr("pmt_id"));
	            }
	         }
	     });
		$.Loading.show("正在删除...");
		this.deletePost3(basePath+"promotion!delete.do");
			
	} 
	
});