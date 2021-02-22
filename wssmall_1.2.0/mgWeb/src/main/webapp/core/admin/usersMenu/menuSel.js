var menuSel={
	init:function(){
		menuSel.search();
		menuSel.close();
	},
	open:function(){
		$("#refMenuBtn").unbind("click").bind("click",function(){
			var url=app_path+'/core/admin/usersMenuAction!selMenuList.do?ajax=yes';
			
			$("#selMenuDiv").load(url,function(){
				menuSel.init();
				
			});
			Eop.Dialog.open("selMenuDiv");		
		});
	},
	search:function(){
		$("#menuSelSearchBtn").unbind("click").bind("click",function(){
			var url=app_path+'/core/admin/usersMenuAction!selMenuList.do?ajax=yes';
			Cmp.ajaxSubmit('menuSelSearchForm','selMenuDiv',url,{},menuSel.init);
		});
	},
	close:function(){
		$("#menuSelInsureBtn").unbind("click").bind("click",function(){
		   if($('input[name="selMenuRadio"]:checked').length >0){
			   var obj  = $('input[name="selMenuRadio"]:checked');
			   var menu_id = obj.attr("menu_id");
			   var url = obj.attr("url");
			   
			   $("#sel_menu_id").val(menu_id);
			   $("#menu_url").val(url);
			  
		   }
		   Eop.Dialog.close("selMenuDiv");	
		});
	}
};

$(function(){
	Eop.Dialog.init({id:"selMenuDiv",modal:false,title:"关联菜单",width:'800px'});
	menuSel.open();
});