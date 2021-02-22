var FactList = {
	cond_type : "",						//规则条件类型， 参与计算，不参与计算
	init : function(){
		//规则对象点击事件，树的收缩效果
		this.objTreeClick()
		//规则对象属性点击事件，创建属性dom元素
		this.factAttrEvent();
		//按属性条件搜索规则
		this.searchRule();
		//方案选择条件
		this.searchPlanCond();
	},
	objTreeClick : function(){
		$("li[name='rule_obj']").unbind("click").bind('click', function(){
			$(this).find("ul").show();
			$(this).siblings().each(function(){
				$(this).find("ul").hide();
			})
		});
	},
	factAttrEvent : function(){
		$("input[name='fact_attr_input']").unbind("click").bind('click', function(){
			var $this = $(this);
			if ($this.attr("checked")) {
				// 所有选中复选框设空
				$("input[fact_attr_=fact_attr_"+$this.attr('fact_attr_id')+"]").attr("checked", false)
				if ($this.attr("id")=="without_cond" || $this.attr("id")=="never_run_cond") {
					var obj_array = $("input[name='fact_attr_input']"); // 左边复选菜单
					for (var n=0; n<obj_array.length; n ++) {
						var obj = obj_array[n];
						$(obj).attr("checked", false); // 所有适用和所有不适用，是否被选中，被选中则取消其他选中
						$this.attr("checked", true)
					}
					$("div#fact_attr_value").empty(); // 清空右边界面属性，empty()刷出div内容，remove()删除div在dom中的节点
					var url = ctx + "/shop/admin/ruleManager!searchRuleConfig.do?ajax=yes";
					Eop.Dialog.open("rule_search_reslut");
					$("#rule_search_reslut").load(url,{"searchCond" : $this.attr("id")});
				} else {
					$("#without_cond").attr("checked", false); // 取消所有适用和不适用复选框
					$("#never_run_cond").attr("checked", false);
					var fact_attr_stype_code = $this.attr("fact_attr_stype_code");
					var fact_attr_ele_type = $this.attr("fact_attr_ele_type");
					if (fact_attr_stype_code != null && fact_attr_stype_code != undefined) {
						FactAttrFactory.curAttr = $this;
						var domEle = FactAttrFactory.create(fact_attr_stype_code, fact_attr_ele_type);
						if (fact_attr_ele_type != "list") {
							domEle.attr("id", $this.attr("fact_attr_id"));
							domEle.appendTo("div#fact_attr_value");
						}
					}
				}
			} else {
				$("div#fact_attr_value div#"+$this.attr('fact_attr_id')).remove();
			}
		});
	},
	stopPropagation : function(e){
		// 防止事件冒泡
		if(e.stopPropagation){
			e.stopPropagation();
		}else{
			// IE阻止冒泡方法 
			e.cancelBubble = true;
		}
	},
	get_dc_public : function(key){
		var list = null;
		var url = app_path + "/shop/admin/ruleManager!getDcPublic.do?ajax=yes";
		Cmp.asExcute("",url, {"stype" : key}, function(reply){
			list = reply;
		}, "json");
		return list;
	},
	clickAttrValue : function(e) {
		var $this = $(e);
		$this.attr("value");
	},
	searchRule : function () {
		$("#searchRuleBtn").bind("click", function() {
			var select_list = $("select[name='is_include']");
			for(var index=0; index<select_list.length; index++){
				if ($(select_list[index]).val() == "-1") { // 属性必须选择是匹配条件
					alert("请选择【 "+$(select_list[index]).attr("select_attr_name")+" 】是否匹配");return;
				}
			}
			var is_include = $("#is_include").val();// 查询时是否包含选中条件，0-不包含，1-包含
			if (is_include=="-1"){alert("请选择匹配条件!");return;}
			var ls_li = $("li[name=fact_attr_name]>input:checked"); // 左边Fact对象所有被选中的属性
			if (ls_li.length > 0) {
				var attr_ = ""; // 传到后台的查询条件，（格式{objId_attrId_eleType : val} (val是以'_'连接的属性值)）
				for(var i = 0; i < ls_li.length; i++) {
					var $li_input = $(ls_li[i]); // 左边菜单被选中Fact对象的属性
					var fact_obj_id = $li_input.attr("fact_obj_id"); // Fact对象ID
					var fact_attr_id = $li_input.attr("fact_attr_id"); // Fact对象属性ID
					var search_obj_code = $li_input.attr("search_obj_code");
					var search_attr_code = $li_input.attr("search_attr_code");
					var fact_attr_ele_type = $li_input.attr("fact_attr_ele_type"); // 左边被选中属性类型（对应数据库ele_type字段）
					var check_attr_ls = ""; // 被选中的CheckBox或者Radio
					var attr_array = ""; // 被选中的CheckBox或者Radio的值（以'_'连接）
					if (fact_attr_ele_type == "checkbox" || fact_attr_ele_type=="radio" || fact_attr_ele_type == "list") {
						check_attr_ls = $("input[fact_attr_=fact_attr_"+fact_attr_id+"]:checked");
					} else if (fact_attr_ele_type == "input") {
						check_attr_ls = $("input[fact_attr_=fact_attr_"+fact_attr_id+"]");
					}
					for (var j = 0; j < check_attr_ls.length; j ++) {
						var check_attr = check_attr_ls[j];
						var $check_attr = $(check_attr);
						var fact_attr_value = $check_attr.attr("value");
						if (fact_attr_value == "" || fact_attr_value == undefined) {
							attr_array += "#";
							continue;
						}
						if (j < check_attr_ls.length-1) {
							attr_array += (fact_attr_value+"#");
						} else {
							attr_array += (fact_attr_value);
						}
					}
					if (check_attr_ls.length>0) { // 如果右边属性展示值为空，则不拼装属性条件
						var is_include_ = $("select[id='is_include_"+fact_attr_id+"']").val();
						if (i < ls_li.length-1) {
							attr_ += (search_obj_code+"#"+search_attr_code+"#"+fact_attr_ele_type+"#"+is_include_+":"+attr_array+",");
						} else {
							attr_ += (search_obj_code+"#"+search_attr_code+"#"+fact_attr_ele_type+"#"+is_include_+":"+attr_array);
						}
					} else {
						alert("请选择【 "+$li_input.attr("fact_attr_name")+" 】的属性！");return;
					}
				}
				attr_ += "";
				var url = ctx + "/shop/admin/ruleManager!searchRuleConfig.do?ajax=yes";
				Eop.Dialog.open("rule_search_reslut");
				$("#rule_search_reslut").load(url,{"searchCond" : attr_,"is_include":is_include,
					"plan_id":$("#plan_id").val(),"dir_id":$("#dir_id").val()});
			} else {
				alert("请选择Fact对象!");return;
			}
		});
	},
	searchPlanCond:function() {
		$("#savePlanCond").bind("click", function() {
			var select_list = $("select[name='is_include']");
			for(var index=0; index<select_list.length; index++){
				if ($(select_list[index]).val() == "-1") { // 属性必须选择是匹配条件
					alert("请选择【 "+$(select_list[index]).attr("select_attr_name")+" 】是否匹配");return;
				}
			}
			var ls_li = $("li[name=fact_attr_name]>input:checked"); // 左边Fact对象所有被选中的属性
			if (ls_li.length > 0) {
				var attr_ = new Array(ls_li); // 传到后台的查询条件，（格式{objId_attrId_eleType : val} (val是以'_'连接的属性值)）
				for(var i = 0; i < ls_li.length; i++) {
					var arr = new Array(8);
					var $li_input = $(ls_li[i]); // 左边菜单被选中Fact对象的属性
					var fact_obj_id = $li_input.attr("fact_obj_id"); // Fact对象ID
					var fact_attr_id = $li_input.attr("fact_attr_id"); // Fact对象属性ID
					var search_obj_code = $li_input.attr("search_obj_code");
					var search_attr_code = $li_input.attr("search_attr_code");
					var fact_attr_ele_type = $li_input.attr("fact_attr_ele_type"); // 左边被选中属性类型（对应数据库ele_type字段）
					var fact_attr_name = $li_input.attr("fact_attr_name");
					var check_attr_ls = new Array(); // 被选中的CheckBox或者Radio
					if (fact_attr_ele_type == "checkbox" || fact_attr_ele_type=="radio" || fact_attr_ele_type == "list") {
						check_attr_ls = $("input[fact_attr_=fact_attr_"+fact_attr_id+"]:checked");
					} else if (fact_attr_ele_type == "input") {
						check_attr_ls = $("input[fact_attr_=fact_attr_"+fact_attr_id+"]");
					}
					if (check_attr_ls.length>0) { // 如果右边属性展示值为空，则不拼装属性条件
						var arr_ = new Array(check_attr_ls.length);
						for (var j = 0; j < check_attr_ls.length; j ++) {
							arr_[j] = $(check_attr_ls[j])
								.parent("label[label_id='label_"+fact_attr_id+"_"+$(check_attr_ls[j]).attr('value')+"']");
						}
						arr['arr_'] = arr_;
						arr['is_'] = $("select[id='is_include_"+fact_attr_id+"']").val();
						arr['fact_attr_id'] = fact_attr_id;
						arr['search_attr_code'] = search_attr_code;
						arr['fact_attr_name'] = fact_attr_name;
						arr['fact_obj_id'] = fact_obj_id;
						arr['fact_obj_code'] = search_obj_code;
						arr['fact_ele_type'] = fact_attr_ele_type;
						attr_[i] = arr;
					} else {
						alert("请选择【 "+$li_input.attr("fact_attr_name")+" 】的属性！");return;
					}
				}
				FactAttrFactory.renderPlanCond(attr_);
			} else {
				alert("请选择Fact对象!");return;
			}
		});
	}
}
$(function(){
	FactList.init();
	//初始化对话框
	Eop.Dialog.init({id:'fact_list_attr_dlg',modal:false,title:'列表数据',width:"700px"});
	
});