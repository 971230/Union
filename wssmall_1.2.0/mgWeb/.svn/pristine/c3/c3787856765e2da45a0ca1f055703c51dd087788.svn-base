function searchEsearch(log_id){
		var url= app_path+"/shop/admin/infLogs!showEsearch.do?ajax=yes&log_id="+log_id;
	    $("#esearchDlg").load(url,{},function(){});
	    $("#out_param").val("");
		$("#in_param").val("");
		Eop.Dialog.open("esearchDlg");
	}