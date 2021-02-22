var AddCatalog = {
		init:function(){
			AddCatalog.saveCatalogAdd();
		},
		saveCatalogAdd:function(){
			$("#saveCatalogAddBtn").click(function(){
				if( !$("#catalogAddForm").checkall() ){
					return ;
				}
				$.Loading.show('正在保存，请稍侯...');
				
				var options = {
						url :"esearchCatalog!saveCatalogAdd.do?ajax=yes",
						type : "POST",
						dataType : 'json',
						success : function(result) {	
							$.Loading.hide();
						 	if(result.result==0){
						 		alert("添加成功");
						 		Eop.Dialog.close("addCatalogDlg");
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
		 
				$("#catalogAddForm").ajaxSubmit(options);
			})
				
		},
}
$(function(){
	$("form.validate").validate();
	AddCatalog.init();
})