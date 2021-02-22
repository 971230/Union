var CmsWidget = {
		init:function(){
			$("input[name='cmsWidget.code']").blur(function(){
				if (/[\u4E00-\u9FA5]/i.test($(this).val())) {
				    alert('请输入英文编码！');
				    $(this).val("").focus();
				}
			});
			
			$("#select_tpl").bind("click",function(){
				Eop.Dialog.open("cms_tpl_dialog");
				
				var url = ctx + "/shop/admin/cms/template!list.do?is_select=yes&ajax=yes";
				$("#cms_tpl_dialog").load(url,function(){
					CmsWidget.initTemplate();
				}); 
			});
			
			
			$("#submitWidgetInfo").bind("click",function(){
				$("#theForm.validate").validate();
				
				var url = ctx+ "/shop/admin/cms/modual!saveWidegetInfo.do?ajax=yes";
				Cmp.ajaxSubmit('theForm', '', url, {}, CmsWidget.jsonBack,'json');
				
			});
			
		},
		initTemplate:function(){
			$("#cms_tpl_dialog #submitButton").unbind("click").bind("click",function(){
				var url = ctx + "/shop/admin/cms/template!list.do?is_select=yes&ajax=yes";
				Cmp.ajaxSubmit('template_form','cms_tpl_dialog',url,{},CmsWidget.initTemplate);
				return false;
				
			});
			
			$("#cms_tpl_dialog #sel_temp").unbind("click").bind("click",function(){
				var sel_radio = $("input[name='choose_temp']:checked");
				if(sel_radio.length == 0){
					alert("请选择模板！");
					return;
				}
				
				var template_id = sel_radio.attr("template_id");
				var template_name = sel_radio.attr("template_name");
				$("input[name='template.template_id']").val(template_id);
				$("input[name='cmsWidget.template_name']").val(template_name);
				
				Eop.Dialog.close("cms_tpl_dialog");
			});
		},
		jsonBack:function(responseText){
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href="modual!listWidget.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
}


$(function() {
	Eop.Dialog.init({id:"cms_tpl_dialog", modal:false, title:"选择模板", width:"700px"});
	CmsWidget.init();
});
