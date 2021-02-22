//属性元素工厂
var FactAttrFactory = {
	curAttr : null,	//记录当前正在绘制的属性信息
	create : function(data, type){
		var domEle = null;
		if("select" == type){	//下拉列表方式
			domEle = this.createSelect(data);
		}else if("radio" == type){			//单选框
			domEle = this.createRadio(data);
		}else if("checkbox" == type){		//复选框
			domEle = this.createCheckbox(data);
		}else if("list" == type){			//列表
			domEle = this.FactCreateList(data);
		}else if("date" == type){			//时间、日期
			domEle = this.createDate(data);
		}else {								//默认下拉列表
			domEle = this.createInput(data);
		}
		return domEle;
	},
	createSelect : function(data){
		var domTpl = $("#select_tpl").clone();
		domTpl.attr('id','');
		var list = this.getDcPublic(data);
		if(null != list && typeof(list) == 'object' && list instanceof Array){
			domTpl.find('select').empty();
			for(var i = 0; i < list.length; i++){
				domTpl.find("select").append('<option fact_attr_="fact_attr_'+fact_attr_id+'" value="' 
						+ list[i].value + '" attr_name="' + list[i].value_desc + '">' 
						+ list[i].value_desc + '</option>');
			}
		}
		return domTpl;
	},
	createInput : function(data){
		var domTpl = $("#text_tpl").clone();
		var attr_code = this.curAttr.attr('search_attr_code');
		var fact_obj_id = this.curAttr.attr("fact_obj_id");
		var fact_attr_id = this.curAttr.attr("fact_attr_id");
		var fact_attr_name = this.curAttr.attr("fact_attr_name");
		domTpl.attr("id", "");
		$(domTpl.find("input")).attr("fact_attr_", "fact_attr_"+fact_attr_id);
		domTpl.prepend("<div class='ptit'>" +
						"<select name='is_include' id='is_include_"+fact_attr_id+"' " +
						"select_attr_name='"+fact_attr_name+"'>" +
						"<option value='-1'>--请选择--</option>" +
						"<option value='1'>匹配</option><option value='0'>不匹配</option></select>" +
						"<span class='name' label='label_"+fact_attr_id+"'>"+fact_attr_name+"：</span></div>");
		if(null != data && "" != data && "undefined" != data){
			domTpl.attr("attr_code", "attr_code_"+attr_code);
			domTpl.find('input').val(data);
		}
		return domTpl;
	},
	createRadio : function(data){
		var domTpl = $("#radio_tpl").clone();
		domTpl.attr('id','');
		var attr_code = this.curAttr.attr('search_attr_code');
		var fact_obj_id = this.curAttr.attr("fact_obj_id");
		var fact_attr_id = this.curAttr.attr("fact_attr_id");
		var fact_attr_name = this.curAttr.attr("fact_attr_name");
		var list = this.getDcPublic(data);
		if(null != list && typeof(list) == 'object' && list instanceof Array){
			domTpl.empty();
			for(var i = 0; i < list.length; i++){
				if (i == 0) {
					domTpl.append("<div class='ptit'>" +
							"<select name='is_include' id='is_include_"+fact_attr_id+"' " +
									"select_attr_name='"+fact_attr_name+"'>" +
							"<option value='-1'>--请选择--</option>" +
							"<option value='1'>匹配</option><option value='0'>不匹配</option></select>" +
							"<span class='name' label='label_"+fact_attr_id+"'>"+fact_attr_name+"：</span></div>");
				}
				domTpl.append('<label label_id="label_'+fact_attr_id+'_'+list[i].value+'"><input attr_code="attr_code_'+attr_code+'"  fact_attr_="fact_attr_'+fact_attr_id+'" type="radio" value="' 
						+ list[i].value + '" name="'+(fact_obj_id+'_'+fact_attr_id)+'" attr_name="' + list[i].value_desc 
						+ '" name="attr_' + attr_code + '" value="' + list[i].value + '">' + list[i].value_desc + '</label>');
				if (i == list.length-1) {
					domTpl.append("<br/>");
				}
			}
		}
		return domTpl;
	},
	createCheckbox : function(data){
		var domTpl = $("#checkbox_tpl").clone();
		domTpl.attr('id','');
		var attr_code = this.curAttr.attr('search_attr_code');
		var fact_obj_id = this.curAttr.attr("fact_obj_id");
		var fact_attr_id = this.curAttr.attr("fact_attr_id");
		var fact_attr_name = this.curAttr.attr("fact_attr_name");
		var list = this.getDcPublic(data);
		if(null != list && typeof(list) == 'object' && list instanceof Array){
			domTpl.empty();
			for(var i = 0; i < list.length; i++){
				if (i == 0) {
					domTpl.append("<div class='ptit'>" +
							"<select name='is_include' id='is_include_"+fact_attr_id+"' " +
									"select_attr_name='"+fact_attr_name+"'>" +
							"<option value='-1'>--请选择--</option>" +
							"<option value='1'>匹配</option><option value='0'>不匹配</option></select>" +
							"<span class='name' label='label_"+fact_attr_id+"'>"+fact_attr_name+"：</span></div>");
				}
				domTpl.append('<label label_id="label_'+fact_attr_id+'_'+list[i].value+'"><input attr_code="attr_code_'+attr_code+'" fact_attr_="fact_attr_'+fact_attr_id+'" type="checkbox" attr_name="' 
						+ list[i].value_desc +'" value="' + list[i].value 
						+ '" name="attr_' + attr_code + '"/>' + list[i].value_desc + '</label>');
				if (i == list.length-1) {
					domTpl.append("<br/>");
				}
			}
		}
		return domTpl;
	},
	createDate : function(data){
		var domTpl = $("#date_tpl").clone();
		var attr_code = this.curAttr.attr('attr_code');
		var fact_obj_id = this.curAttr.attr("fact_obj_id");
		var fact_attr_id = this.curAttr.attr("fact_attr_id");
		var fact_attr_name = this.curAttr.attr("fact_attr_name");
		domTpl.attr('id','');
		if(null != data && "" != data && "undefined" != data){
			domTpl.find('input').val(data);
		}
		$(domTpl.find("input")).attr("fact_attr_", "fact_attr_"+fact_attr_id);
		domTpl.find("input").datepicker();			//绑定日期弹出事件
		return domTpl;
	},
	FactCreateList : function(data){
		var attr_code = this.curAttr.attr('attr_code');
		var fact_obj_id = this.curAttr.attr("fact_obj_id");
		var fact_attr_id = this.curAttr.attr("fact_attr_id");
		var fact_attr_name = this.curAttr.attr("fact_attr_name");
		if(data != null && data != '' && data != 'undefined'){
			var url = "";
			if (data.indexOf("workFlowAction!getWorkFlowPage.do") > 0) {
				url = "/shop/admin/ruleManager!getFactWorkFlowPage.do?ajax=yes";
			}
			Eop.Dialog.open("workflow_list");
			$("#workflow_list").load(ctx+url, {"fact_obj_id":fact_obj_id,"fact_attr_id":fact_attr_id, "fact_attr_name":fact_attr_name});
		}else{
			alert('请配置列表地址');
		}
	},
	createHtml : function(data){
		var domTpl = $("#html_tpl").clone();
		domTpl.attr('id','');
		if(null != data && "" != data && "undefined" != data){
			domTpl.html(data);
		}
		return domTpl;
	},
	createNeverRun : function(data){
		var domTpl = $("#never_run_tpl").clone();
		domTpl.attr('id','');
		if(null != data && "" != data && "undefined" != data){
			domTpl.html(data);
		}
		return domTpl;
	},
	getDcPublic : function(key){
		var list = null;
		var url = app_path + "/shop/admin/ruleManager!getDcPublic.do?ajax=yes";
		Cmp.asExcute("",url, {"stype" : key}, function(reply){
			list = reply;
		}, "json");
		return list;
	},
	renderListData : function(data) {
		if(null != data && typeof(data) == 'object' 
			&& data instanceof Array && data.length > 0) {
			var domTpl = $("div#list_tpl").clone();
			$("div#fact_attr_value div#"+data[0].fact_attr_id).remove();
			$(domTpl).attr("id", data[0].fact_attr_id);
			var attr_code = this.curAttr.attr('attr_code');
			domTpl.append("<div class='ptit'>" +
						"<select name='is_include' id='is_include_"+data[0].fact_attr_id+"' " +
						"select_attr_name='"+data[0].fact_attr_name+"'>" +
						"<option value='-1'>--请选择--</option>" +
						"<option value='1'>匹配</option><option value='0'>不匹配</option></select>" +
						"<span class='name' label='label_"+ data[0].fact_attr_id + "'>" + data[0].fact_attr_name+ "：</span></div>");
			for ( var i = 0; i < data.length; i++) {
				var dd = data[i];
				domTpl.append("<label label_id='label_"+data[0].fact_attr_id+"_"+dd.attr_value+"'><input attr_code='attr_code_"+attr_code+"' fact_attr_='fact_attr_"
						+ dd.fact_attr_id + "' checked type='checkbox' attr_name='"
						+ dd.attr_name + "' value='" + dd.attr_value + "'/>"
						+ dd.attr_name + "</label>");
			}
			domTpl.append("<br/>");
			domTpl.appendTo("div#fact_attr_value");
			Eop.Dialog.close("workflow_list");
		}
	},
	renderPlanCond : function(data) {
		$("#search_plan_cond").empty();
		var div_plan_cond = $("#search_plan_cond");
		for (var i = 0; i<data.length; i++) {
			var arr_ = data[i]['arr_'];
			var is_ = data[i]['is_'];
			var fact_attr_id = data[i]['fact_attr_id'];
			var fact_attr_name = data[i]['fact_attr_name'];
			var search_attr_code = data[i]['search_attr_code'];
			var fact_attr_name = data[i]['fact_attr_name'];
			var fact_obj_id = data[i]['fact_obj_id'];
			var search_obj_code = data[i]['fact_obj_code'];
			var fact_ele_type = data[i]['fact_ele_type'];
			div_plan_cond.append("<div class='formCont' id='"+fact_attr_id+"' style='display:inline-block;'></div>");
			var select = "<div class='ptit' " +
							"obj_code='"+search_obj_code+"' " +
							"attr_code='"+search_attr_code+"' " +
							"attr_name='"+fact_attr_name+"' " +
							"ele_type='"+fact_ele_type+"' " +
							"attr_id='"+fact_attr_id+"' " +
							"name='planCondDiv'>";
			select += "<select id='is_include_"+search_attr_code+"'>";
			select += "<option value='-1'>--请选择--</option>";
			if (is_ == "1") {
				select += "<option value='1' selected>匹配</option>";
				select += "<option value='0'>不匹配</option>";
			} else if (is_ == "0") {
				select += "<option value='1'>匹配</option>";
				select += "<option value='0' selected>不匹配</option>";
			}
			select += "</select>";
			select += "<span class='name' label='label_"+fact_attr_id+"'>"+fact_attr_name+"：</span>";
			select += "</div>";
			div_plan_cond.append(select);
			for (var j = 0; j<arr_.length; j++) {
				$(arr_[j]).appendTo(div_plan_cond);
				/*div_plan_cond
				.append(arr_[j]);
				.append($(arr_[j]).attr("attr_name"));*/
			}
		}
		Eop.Dialog.close("show_search_dlg");
	}
};