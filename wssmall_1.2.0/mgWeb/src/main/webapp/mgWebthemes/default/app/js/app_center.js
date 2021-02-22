var AppCenter = {
	init : function(){
		$("#user_app_list li").bind('click', function(){
			if($(this).hasClass('curr')){
				return false;
			}else {
				var appName = $(this).attr('app_name');
				if(confirm("是否切换到" + appName)){
					var app = eval('(' + $(this).attr('appInfo') + ')');
					//单点地址 
					// http://localhost:8080/wssmall/mgWeb/login.do?type=singleLogin&username=admin&password=202cb962ac5975b964b7152d234b70
					var url = app.appAddress + '/login.do?type=singleLogin&username=' + userName + 
					'&password=' + passWord + "&app_info=" + $(this).attr('appInfo');
					var width = screen.availWidth-3;
					var height = screen.availHeight-20;
					var windowPar='toolbar=no,alwaysLowered=yes,status=no,location=no,titlebar=no,menubar=no, scrollbars=yes,resizable=yes,width='+width+',height='+height+',top=0,left=0';
					//window.open(" http://localhost:8080/wssmall/mgWeb/login.do?type=singleLogin&username=admin&password=202cb962ac5975b964b7152d234b70", "_parent",windowPar);
					window.open(url, "_parent",windowPar);
				}
			}
		});
	}
};
$(function(){
	AppCenter.init();
});