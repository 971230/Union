var RuleCond = {
	conditionData : [],
	cond_type  : "",
	rule_model : "simple_model",
	init : function(){
		Eop.Dialog.init({id:"show_search_dlg",modal:false,title:"搜索条件"});
		this.addRuleCond();
	},
	addRuleCond : function(){
		$("a[name='add_search']").unbind('click').bind('click', function(){
			var plan_name_id = $("#plan_name_id").val();
			var dir_name_id = $("#dir_name_id").val();
			if ((plan_name_id != "" && plan_name_id != "-1") 
					&& (dir_name_id != "" && dir_name_id != "-1")) {
				alert("不能同时选择方案和目录！");return false;
			}
			if ((plan_name_id == "" || plan_name_id == "-1")
					&& (dir_name_id == "" || dir_name_id == "-1")) {
				alert("请选择方案或目录！"); return false;
			}
			Eop.Dialog.open("show_search_dlg");
			var url = ctx + "/shop/admin/ruleManager!getSearchFactList.do?ajax=yes&dir_id="+dir_name_id+"&plan_id="+plan_name_id;
			var rule_id = $(this).attr('rule_id');
			var cond_type = $(this).attr('cond_type');
			$("#show_search_dlg").load(url,{"rule_id":rule_id,"cond_type":cond_type});
		});
	}
};