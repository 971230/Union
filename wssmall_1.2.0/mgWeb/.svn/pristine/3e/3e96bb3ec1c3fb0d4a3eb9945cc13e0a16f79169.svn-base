var SolutionSelect = {
		init:function(){
			SolutionSelect.query();
			SolutionSelect.confirmSolution();
		},
		query : function(){
			$("#qry_btn").unbind('click').bind('click', function(){
				var url = app_path + "/shop/admin/solution!qryExpInstSolutionSelect.do?ajax=yes";
				var solution_type = $('#solution_type').val();
				var solution_name = $('#solution_name').val();
				var catalog_id = $("#catalog_id").val();
				$("#addSolutionDlg").load(url,{'solution_type':solution_type,'solution_name':solution_name,'catalog_id':catalog_id});
			});
		},
		confirmSolution:function(){
			$("#confirm_solution").click(function(){
				var solution_num = $("#solutionlist input:checked[name='solution_id']").length;
				var solution_id = $("#solutionlist input:checked[name='solution_id']").attr("solution_id");
				var catalog_id = $("#catalog_id").val();
				if(solution_num==0){
					alert("请选择解决方案");
					return ;
				}
				$.ajax({
					 type: "POST",
					 url: "esearchCatalog!updateCatalogSolution.do",
					 data: "ajax=yes&esearchCatalog.catalog_id="+catalog_id+"&esearchCatalog.solution_id="+solution_id,
					 dataType:"json",
					 success: function(result) {
						 if (result.result == '0') {
							 alert(result.message);
							 Eop.Dialog.close("addSolutionDlg");
							 window.frames["catalogListIframe"].location.reload();
							//加载归类解决方案
					        var url =ctx+"/shop/admin/solution!getCatalogSolution.do?solution_id="+solution_id+"&catalog_id="+catalog_id;
					        $("#catalogSolutionIframe").attr("src",url);
					     } else {
					    	 alert(result.message);
					     }
					 },
					 error:function(){
						 alert("操作失败，请重试");
					 }
				});
			})
		}
}
$(function(){
	SolutionSelect.init();
});