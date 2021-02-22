var AddCatalog = {
		init:function(){
			AddCatalog.saveCatalogAdd();
		},
		saveCatalogAdd:function(){
			$("#saveCatalogEditBtn").click(function(){
				if( !$("#catalogEditForm").checkall() ){
					return ;
				}
				$.Loading.show('正在保存，请稍侯...');
				
				var options = {
						url :"esearchCatalog!saveCatalogEdit.do?ajax=yes",
						type : "POST",
						dataType : 'json',
						success : function(result) {	
							$.Loading.hide();
						 	if(result.result==0){
						 		alert("修改成功");
						 		Eop.Dialog.close("editCatalogDlg");
						 		window.frames["catalogListIframe"].location.reload();
						 	}else{
						 		alert(result.message);
						 	}
						 	
						},
						error : function(e) {
							$.Loading.hide();
							alert("出错啦:(");
		 				}
		 		};
		 
				$("#catalogEditForm").ajaxSubmit(options);
			})
				
		},
}
$(function(){
	$("form.validate").validate();
	AddCatalog.init();
})