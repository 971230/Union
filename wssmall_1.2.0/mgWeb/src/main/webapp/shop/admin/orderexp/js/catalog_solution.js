var CatalogSolution = {
		init:function(){
			CatalogSolution.deleteCatalogSolution();
			CatalogSolution.downloadCatalogSolution();
		},
		deleteCatalogSolution:function(){
			$("#catalogSolutionList a[name='deleteCatalogSolution']").click(function(){
				var solution_id = $(this).attr("solution_id");
				var catalog_id = $("#catalogSolutionList input[name='catalog_id']").val();
				if(confirm("确定删除归类的解决方案？")){
					$.ajax({
						 type: "POST",
						 url: ctx+"/shop/admin/esearchCatalog!deleteCatalogSolution.do",
						 data: "ajax=yes&esearchCatalog.catalog_id="+catalog_id,
						 dataType:"json",
						 success: function(result) {
							 if (result.result == '0') {
								 alert(result.message);
								 window.parent.frames["catalogListIframe"].location.reload();
								 $("#catalogSolutionList tbody tr:gt(0)").remove();
						     } else {
						    	 alert(result.message);
						     }
						 },
						 error:function(){
							 alert("操作失败，请重试");
						 }
					});
				}
				
			})
		},
		downloadCatalogSolution:function(){
			$("#catalogSolutionList a[name='downloadCatalogSolution']").click(function(){
				var fileName = $(this).parent().parent().find("input[name='solution_value']").val();
				var solutionName = $(this).parent().parent().find("td[name='solution_name']").text();
				$.ajax({
					type: "POST",
					url: ctx+"/servlet/downloadFile",
					data: "ajax=yes&fileName="+fileName+"&solutionName="+solutionName,
					dataType:"json",
					success: function(result) {
						
						alert(result.message);
					},
					error:function(){
						alert("操作失败，请重试");
					}
				});
			})
		}
}
$(function(){
	CatalogSolution.init();
});