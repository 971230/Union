var HSGoods =$.extend({},Eop.Grid,{
	
	init:function() {
		
		var self = this;

		//删除
    	$("#deleteBtn").click(function(){
    		self.doDelete();
    	});
    	
	},
	
	//删除
	doDelete:function() {
		var ids="";
		if(!this.checkIdSeled()){
			alert("请选择要删除的机型！");
			return ;
		}else{
			$(".grid").find("tbody").find("tr").find("td:eq(0)").find("input[type='checkbox']:checked").each(function(i,data){
				ids += $(data).val()+",";
			});
		}
		
		var self= this;
		if(confirm("确认要删除吗？")){
			$.Loading.show("正在删除...");
			self.deletePost("goods!deleteEsTerminal.do?ajax=yes&sn="+ids);
			$.Loading.hide();
		}
		
	},
	
});

var esTerminalInput={
		init:function(){
			var self = this;

			$(".submitlist .submitBtn").click(function(){
				self.saveEsTerminal();
			});
			
			self.initInput();
			
		},
		
		saveEsTerminal:function(){
			if($("#editForm").do_validate()){
				
				$("#editForm").ajaxSubmit({
					url:'goods!saveOrUpdateEsTerminal.do?ajax=yes',
					type:'POST',
					dataType:'json',
					success:function(reply){
						if(reply.result == true){
							alert("保存成功");
							window.location.href = ctx + "/shop/admin/goods!esTerminalList.do";
						}else{
							alert("保存失败");
						}
					},
					error:function(){
						alert("出错了");
					}
				});				
			}
		},
		
		initInput:function(){
			if($("input[name='action']").val()=="add"){
				$("#sn").removeAttr("readonly");
			}else {
				$("#sn").attr("readonly","readonly");
			}
		},
		
};


$(function() {
	HSGoods.init();
});