
var Solution = {
		
		init:function(){
			var self = this;
			Eop.Dialog.init({id:"solutionDetailDialog",modal:true,title:"解决方案详情",width:'550px'});
		},
		
		del: function(obj,solution_id){
			if(!confirm("确定要删除这条数据吗？")){
				return false;
			}
			$.ajax({
				type : "POST",
				url : ctx + "/shop/admin/solutionManage!del.do",
				data : "ajax=yes&solution_id="+solution_id,
				dateType : "json",
				success : function(result){
					var data = eval("("+result+")");
					if(data.errorCode == '0') {
						$(obj).parents('tr').remove();
				    }else {
				    	alert(data.errorDesc);
				    }
				},
				error : function(){
					alert("操作失败，请重试");
				}
			});
		},
		
		detail : function(obj,solution_id){
			var url =ctx + "/shop/admin/solutionManage!view.do?ajax=yes&solution_id="+solution_id;
			$("#solutionDetailDialog").html("loading...");
			$("#solutionDetailDialog").load(url,function(){});
			Eop.Dialog.open("solutionDetailDialog");
		}
		
}

$(function(){
	Solution.init();
});