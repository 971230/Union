var CatalogTree = {
		init:function(){
			Eop.Dialog.init({id:'addCatalogDlg',modal:true,title:"添加归类",width:'850px'});
			Eop.Dialog.init({id:'editCatalogDlg',modal:true,title:"修改归类",width:'850px'});
			Eop.Dialog.init({id:'addSolutionDlg',modal:true,title:"解决方案",width:'850px'});
			CatalogTree.addCatalog();
			CatalogTree.editCatalog();
			CatalogTree.deleteCatalog();
			CatalogTree.addSolution();
		},
		deleteCatalog:function(){
			$("#delete_catalog").click(function(){
				var catalog_id = $("#catalog_div input[name='selectCatalogId']").val();
				if(!catalog_id){
					alert("请选择要修改的归类");
					return ;
				}
				var is_leaf = $("#catalog_div input[name='nodeIsLeaf']").val();
				if(is_leaf == "false"){
					alert("该归类下存在子归类，不能删除");
					return ;
				}
				var staff_no = $("#catalog_div input[name='catalogStaffId']").val();
				var login_user_id = $("#catalog_div input[name='loginUserId']").val();
				
				if(login_user_id != '1'){
					if(staff_no != login_user_id){
						alert("不能修改其他用户创建的关键字归类");
						return ;
					}
				}
				
				$.ajax({
					 type: "POST",
					 url: "esearchCatalog!saveCatalogEdit.do",
					 data: "ajax=yes&esearchCatalog.catalog_id="+catalog_id+"&esearchCatalog.disabled=1",
					 dataType:"json",
					 success: function(result) {
						 if (result.result == '0') {
							 alert("删除成功");
							 window.frames["catalogListIframe"].location.reload();
					     } else {
					    	 alert(result.message);
					     }
					 },
					 error:function(){
						 alert("操作失败，请重试");
					 }
				});
			})
		},
		editCatalog:function(){
			$("#edit_catalog").click(function(){
				var catalog_id = $("#catalog_div input[name='selectCatalogId']").val();
				if(!catalog_id){
					alert("请选择要修改的归类");
					return ;
				}
				var staff_no = $("#catalog_div input[name='catalogStaffId']").val();
				var login_user_id = $("#catalog_div input[name='loginUserId']").val();
				
				if(login_user_id != '1'){
					if(staff_no != login_user_id){
						alert("不能修改其他用户创建的关键字归类");
						return ;
					}
				}
				var url = ctx+"/shop/admin/esearchCatalog!editCatalog.do?ajax=yes&catalog_id="+catalog_id;
				$("#editCatalogDlg").load(url,function(responseText) {
					Eop.Dialog.open("editCatalogDlg");
				});	
			})
		},
		addCatalog:function(){
			$("#add_catalog").click(function(){
				var url = ctx+"/shop/admin/esearchCatalog!addCatalog.do?ajax=yes";
				$("#addCatalogDlg").load(url,function(responseText) {
					Eop.Dialog.open("addCatalogDlg");
				});	
				
			})
		},
		addSolution:function(){
			$("#add_solution").click(function(){
				var catalog_id = $("#catalog_div input[name='selectCatalogId']").val();
				if(!catalog_id){
					alert("请选择归类");
					return ;
				}
				/*var isLeaf = $("#catalog_div input[name='nodeIsLeaf']").val();
				if(isLeaf=="false"){
					alert("非叶子节点不能添加解决方案，请选择叶子节点");
					return ;
				}*/
				var url = ctx+"/shop/admin/solution!qryExpInstSolutionSelect.do?ajax=yes";
				$("#addSolutionDlg").load(url,{"catalog_id":catalog_id},function(responseText) {
					Eop.Dialog.open("addSolutionDlg");
				});	
			})
		}
};
$(function(){
	CatalogTree.init();
});