
$(document).ready(function(){
	Eop.Dialog.init({id:'editOttUserGround',modal:false, title:'更改上架状态',width:"600px"});
	Eop.Dialog.init({id:'editOttUserLock',modal:false, title:'更改支付锁状态',width:"600px"});
	Eop.Dialog.init({id:'showLockLog',modal:false, title:'锁定日志',width:"600px"});
	
	$("input[name='editGround']").bind("click",function(){
		
		var ottInfo = $(this).attr("attr").split("_");
		
		Eop.Dialog.open("editOttUserGround");
		var url =ctx+"/shop/admin/ottMember!editOttUserGround.do?ajax=yes&adminOttUser.ott_user_id="+ottInfo[0]
						+"&adminOttUser.ground_status="+ottInfo[1]+"&adminOttUser.lock_status="+ottInfo[2];
		$("#editOttUserGround").load(url,function(){
		}); 
	});
	
	$("input[name='editLock']").bind("click",function(){
		
		var ottInfo = $(this).attr("attr").split("_");
		
		Eop.Dialog.open("editOttUserLock");
		var url =ctx+"/shop/admin/ottMember!editOttUserLock.do?ajax=yes&adminOttUser.ott_user_id="+ottInfo[0]
						+"&adminOttUser.ground_status="+ottInfo[1]+"&adminOttUser.lock_status="+ottInfo[2];
		$("#editOttUserLock").load(url,function(){
		}); 
	});
	$("input[name='showLockLog']").bind("click",function(){
	
		var ott_user_id = $(this).attr("attr");
		
		Eop.Dialog.open("showLockLog");
		var url =ctx+"/shop/admin/ottMember!showLockLog.do?ajax=yes&adminOttUser.ott_user_id="+ott_user_id;
		$("#showLockLog").load(url,function(){
		}); 
	});
	
});
