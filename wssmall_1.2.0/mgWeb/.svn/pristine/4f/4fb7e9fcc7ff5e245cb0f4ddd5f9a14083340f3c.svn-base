$(function(){
	
	//wui
	var flowInstIfDiv = $("#flow_inst_iframe");
	$(".gridbody tbody tr").bind("click",function(){
		flowInstIfDiv.show();
		$("[name='ifrm_tr']").remove();
		var trJq = $("<tr name='ifrm_tr'><td colspan=8 style='border:10px solid #F7F7F7;'></td></tr>");
		trJq.insertAfter($(this));
		trJq.find("td").append(flowInstIfDiv);
		var url = ctx+$("input[name='apply_url']",$(this)).val();
		url = url.replace("&ajax=yes","");
		Ztp.AUI.loadUrlInFrmByUrl(url+"&dwr=yes",$("#flow_inst_iframe"));
		
	})
	
})