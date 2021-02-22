var ApplicationAdd = {
		init:function(){
			
			$("input[name='application.app_code']").blur(function(){
				if (/[\u4E00-\u9FA5]/i.test($(this).val())) {
				    alert('请输入英文编码！');
				    $(this).val("").focus();
				}
			});
			 $("input[name='application.outer_app_code']").blur(function(){
				if (/[\u4E00-\u9FA5]/i.test($(this).val())) {
				    alert('请输入英文编码！');
				    $(this).val("").focus();
				}
			});
			
			
			$("#select_page").bind("click",function(){
				Eop.Dialog.open("app_rel_page");
				var url = ctx + "/shop/admin/cms/application/application!getPageList.do?is_select=yes&ajax=yes";
				$("#app_rel_page").load(url,function(){
					ApplicationAdd.initSelPage();
				}); 
			});
			
			$("#select_user").bind("click",function(){
				Eop.Dialog.open("app_rel_user");
				var url = ctx + "/shop/admin/cms/application/application!getUserList.do?is_select=yes&ajax=yes";
				$("#app_rel_user").load(url,function(){
					ApplicationAdd.initSelUser();
				}); 
			});
			
			$("#submitAppInfo").bind("click",function(){
				$("#theForm.validate").validate();
				var url = ctx+ "/shop/admin/cms/application/application!insertApp.do?ajax=yes";
				var jsonTplUrl = "/resource/cms/style_tpl.json";
				var device = $("select[name='app.device']").val();
				if(device == '1'){
//					jsonTplUrl = "/resource/cms/mobile_style_tpl.json";
				}
				var style_tpl = JSON.stringify($.getFileContent(jsonTplUrl));
				style_tpl = encodeURIComponent(style_tpl);
				$('input[name="app.style_tpl"]').val(style_tpl);
				Cmp.ajaxSubmit('theForm', '', url, {}, ApplicationAdd.jsonBack,'json');
			});
			
			$(".del_page").live("click",function(){
				$(this).closest("tr").remove();
			});
		},
		initSelPage:function(){
			$("#app_rel_page #submitButton").unbind("click").bind("click",function(){
				var url = ctx + "/shop/admin/cms/application/application!getPageList.do?is_select=yes&ajax=yes";
				Cmp.ajaxSubmit('page_info_form','app_rel_page',url,{},ApplicationAdd.initSelPage);
				return false;
				
			});
			
			$("#app_rel_page #select_page_insure").unbind("click").bind("click",function(){
				var sel_radio = $("input[name='select_page_radio']:checked");
				if(sel_radio.length == 0){
					alert("请选择页面！");
					return;
				}
				
				var html = "";
				sel_radio.each(function(){
					var flag = true;
					var page_id = $(this).attr("page_id");
					
					var hidden_ids = $("input[name='pageIds']");
					if(hidden_ids.length > 0){
						hidden_ids.each(function(){
							if($(this).val() == page_id){
								flag = false;
							}
						});
					}
					
					if(flag){
						html += "<tr>";
						html += "<input type='hidden' name='pageIds' value='"+$(this).attr("page_id")+"'/>";
						html += "<td style='text-align: center;'>"+$(this).attr("page_code")+"</td>";
						html += "<td style='text-align: center;'>"+$(this).attr("page_name")+"</td>";
						html += "<td style='text-align: center;'>"+$(this).attr("page_url")+"</td>";
						html += "<td style='text-align: center;'><a href='javascript:void(0);' class='del_page'>删除</a></td>";
						html += "</tr>";
					}
				});
				
				$("#page_tr").after(html);
				Eop.Dialog.close("app_rel_page");
			});
		},
		initSelUser:function(){
			$("#app_rel_user .userQryBtn").unbind("click").bind("click",function(){
				var url = ctx + "/shop/admin/cms/application/application!getUserList.do?is_select=yes&ajax=yes";
				Cmp.ajaxSubmit('admin_list_form','app_rel_user',url,{},ApplicationAdd.initSelUser);
				return false;
			});
			
			$("#app_rel_user #select_user_insure").unbind("click").bind("click",function(){
				var sel_radio = $("input[name='select_user_radio']:checked");
				if(sel_radio.length == 0){
					alert("请选择用户！");
					return;
				}
				
				var username = sel_radio.attr("username");
				var userid = sel_radio.attr("userid");
				$("input[name='appRelUser.user_id']").val(userid);
				$("input[name='appRelUser.user_name']").val(username);
				
				Eop.Dialog.close("app_rel_user");
			});
		},
		jsonBack:function(responseText){
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href="application!getList.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
}


$(function() {
	Eop.Dialog.init({id:"app_rel_page", modal:false, title:"选择页面", width:"700px"});
	Eop.Dialog.init({id:"app_rel_user", modal:false, title:"选择用户", width:"700px"});
	ApplicationAdd.init();
});
