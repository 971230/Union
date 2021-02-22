var AppAgentAdd = {
		init:function(){
			
			$("#select_app").bind("click",function(){
				Eop.Dialog.open("app_page_list");
				var url = ctx + "/shop/admin/cms/application/application!getAppList.do?ajax=yes";
				$("#app_page_list").load(url,function(){
					AppAgentAdd.initSelApp();
				}); 
			});
			
			$("#select_user").bind("click",function(){
				Eop.Dialog.open("app_rel_user");
				var url = ctx + "/shop/admin/cms/application/application!getPartnerList.do?is_select=yes&ajax=yes";
				$("#app_rel_user").load(url,function(){
					AppAgentAdd.initSelUser();
				}); 
			});
			
			$("#submitAppInfo").bind("click",function(){
				$("#theForm.validate").validate();
				var url = ctx+ "/shop/admin/cms/application/application!insertAppRelUser.do?ajax=yes";
				Cmp.ajaxSubmit('theForm', '', url, {}, AppAgentAdd.jsonBack,'json');
			});
			
			$(".del_page").live("click",function(){
				$(this).closest("tr").remove();
			});
			
		},
		initSelApp:function(){
			$("#app_page_list #submitButton").unbind("click").bind("click",function(){
				var url = ctx + "/shop/admin/cms/application/application!getAppList.do?ajax=yes";
				Cmp.ajaxSubmit('page_info_form','app_page_list',url,{},AppAgentAdd.initSelPage);
				return false;
				
			});
			
			$("#app_page_list #select_app_insure").unbind("click").bind("click",function(){
				var sel_radio = $("input[name='select_app_radio']:checked");
				if(sel_radio.length == 0){
					alert("请选择应用！");
					return;
				}
				
				var html = "";
				sel_radio.each(function(){
					var flag = true;
					var app_id = $(this).attr("app_id");
					
					var hidden_ids = $("input[name='appIds']");
					if(hidden_ids.length > 0){
						hidden_ids.each(function(){
							if($(this).val() == app_id){
								flag = false;
							}
						});
					}
					
					if(flag){
						html += "<tr>";
						html += "<input type='hidden' name='appIds' value='"+$(this).attr("app_id")+"'/>";
						html += "<td style='text-align: center;'>"+$(this).attr("app_name")+"</td>";
						html += "<td style='text-align: center;'>"+$(this).attr("app_code")+"</td>";
						html += "<td style='text-align: center;'>"+$(this).attr("outer_app_code")+"</td>";
						html += "<td style='text-align: center;'><a href='javascript:void(0);' class='del_page'>删除</a></td>";
						html += "</tr>";
					}
				});
				
				$("#page_tr").after(html);
				Eop.Dialog.close("app_page_list");
			});
		},
		initSelUser:function(){
			$("#app_rel_user .userQryBtn").unbind("click").bind("click",function(){
				var url = ctx + "/shop/admin/cms/application/application!getPartnerList.do?is_select=yes&ajax=yes";
				Cmp.ajaxSubmit('admin_list_form','app_rel_user',url,{},AppAgentAdd.initSelUser);
				return false;
			});
			
			$("#app_rel_user #select_user_insure").unbind("click").bind("click",function(){
				var sel_radio = $("input[name='select_user_radio']:checked");
				if(sel_radio.length == 0){
					alert("请选择用户组！");
					return;
				}
				
				var username = sel_radio.attr("username");
				var userid = sel_radio.attr("userid");
				var partnerid = sel_radio.attr("partnerid");
				
				$("input[name='appRelUser.user_id']").val(userid);
				$("input[name='appRelUser.partner_id']").val(partnerid);
				$("input[name='appRelUser.user_name']").val(username);
				
				Eop.Dialog.close("app_rel_user");
			});
		},
		jsonBack:function(responseText){
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href="application!getUserApp.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
}


$(function() {
	Eop.Dialog.init({id:"app_page_list", modal:false, title:"选择应用", width:"700px"});
	Eop.Dialog.init({id:"app_rel_user", modal:false, title:"选择用户组", width:"700px"});
	AppAgentAdd.init();
});
