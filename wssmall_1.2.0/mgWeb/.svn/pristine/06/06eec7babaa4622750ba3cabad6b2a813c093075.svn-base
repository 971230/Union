
/**
仓库
*/
var WarehouseSeatArrangement=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		
		//全选
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);
		});
		
		//解绑货位
		$("#arrangementButton").click(function(){
	    	self.doWarehouseSeatDelete();
		});
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
							location.reload();
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
	},
	
	
	doWarehouseSeatDelete:function (){
	    if(!this.checkIdSeled()){
			alert("请选择要解绑的货位");
			return ;
		}
		
	   if(!confirm("确认要将这些货位解绑吗？")){	
		   return ;
	    }
		$.Loading.show("正在解绑货位...");
		
		var checkValue=$("input[name='id']:checked").parent().parent().find("span").html();
		if(checkValue=='失效'){
			alert("该货位已经失效,不能解绑,请选择其它货位!");
			$.Loading.hide();
			return;
		}
		this.deletePost1(basePath+"warehouseSeatAction!arrangement.do");
	}
	
});
$(function(){
	WarehouseSeatArrangement.init();
});