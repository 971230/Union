//属性元素工厂
var AttrFactory = {
	curAttr : null,							//记录当前正在绘制的属性信息
	create : function(data, type){
		var domEle = null;
		if("select" == type){	//下拉列表方式
			domEle = this.createSelect(data);
		}else if("radio" == type){			//单选框
			domEle = this.createRadio(data);
		}else if("checkbox" == type){		//复选框
			domEle = this.createCheckbox(data);
		}else if("list" == type){			//列表
			domEle = this.createList(data);
		}else if("date" == type){			//时间、日期
			domEle = this.createDate(data);
		}else if("html" == type){
			domEle = this.createHtml(data);
		}else if("never_run" == type){
			domEle = this.createNeverRun(data);
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
				domTpl.find('select').append('<option value="' 
						+ list[i].value + '" attr_name="' + list[i].value_desc + '">' 
						+ list[i].value_desc + '</option>');
			}
		}
		return domTpl;
	},
	createInput : function(data){
		var domTpl = $("#text_tpl").clone();
		domTpl.attr('id','');
		if(null != data && "" != data && "undefined" != data){
			domTpl.find('input').val(data);
		}
		return domTpl;
	},
	createRadio : function(data){
		var domTpl = $("#radio_tpl").clone();
		domTpl.attr('id','');
		var attr_code = this.curAttr.attr('attr_code');
		var list = this.getDcPublic(data);
		if(null != list && typeof(list) == 'object' && list instanceof Array){
			domTpl.empty();
			for(var i = 0; i < list.length; i++){
				domTpl.append('<label><input type="radio" value="' 
						+ list[i].value + '" attr_name="' + list[i].value_desc 
						+ '" name="attr_' + attr_code + '" value="' + list[i].value + '">' + list[i].value_desc + '</label>');
			}
		}
		return domTpl;
	},
	createCheckbox : function(data){
		var domTpl = $("#checkbox_tpl").clone();
		domTpl.attr('id','');
		var attr_code = this.curAttr.attr('attr_code');
		var list = this.getDcPublic(data);
		if(null != list && typeof(list) == 'object' && list instanceof Array){
			domTpl.empty();
			for(var i = 0; i < list.length; i++){
				domTpl.append('<label><input type="checkbox" attr_name="' 
						+ list[i].value_desc +'" value="' + list[i].value 
						+ '" name="attr_' + attr_code + '"/>' + list[i].value_desc + '</label>');
			}
		}
		return domTpl;
	},
	createDate : function(data){
		var domTpl = $("#date_tpl").clone();
		domTpl.attr('id','');
		if(null != data && "" != data && "undefined" != data){
			domTpl.find('input').val(data);
		}
		domTpl.find('input').datepicker();			//绑定日期弹出事件
		return domTpl;
	},
	createList : function(data){
		var domTpl = $("#grid_tpl").clone();
		domTpl.attr('id','');
		
		var attr_id = this.curAttr.attr('attr_id');							//属性id
		$('#list_attr_dlg').attr('attr_id', attr_id)
		var obj_id = this.curAttr.closest('li[name="rule_obj"]').attr('obj_id');			//对象id
		$('#list_attr_dlg').attr('obj_id', obj_id);
		var rule_id = $('div input#rule_id').val();							//规则id
		$('#list_attr_dlg').attr('rule_id', rule_id);
		
		domTpl.find('a[name="open_list"]').unbind('click').bind('click', function(){
			if(data != null && data != '' && data != 'undefined'){
				Eop.Dialog.open("list_attr_dlg");
				var selectList = [];
				$("div#attr_value tr[name='l_" + attr_id + "'] input:checked[type='checkbox']").each(function(){
					var listEle = {};
					var attr_name = $(this).attr('attr_name');
					var attr_value = $(this).attr('attr_value');
					listEle['attr_name'] = attr_name;
					listEle['attr_value'] = attr_value;
					selectList.push(listEle);
				});
				$("#list_attr_dlg").load(app_path + data,{"rule_id":rule_id, 
						"obj_id":obj_id, "attr_id":attr_id, 
						'select_list':selectList});
			}else{
				alert('请配置列表地址');
			}
		});
		return domTpl;
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
	renderListEle : function(data, dom){
		var attr_id = $('#list_attr_dlg').attr('attr_id');
		if(null != data && typeof(data) == 'object' 
			&& data instanceof Array && data.length > 0){
			var listDom = dom || $('div#attr_value *[attr_id="' 
					+ attr_id + '"]');
			for(var i = 0; i < data.length; i++){
				var listEle = listDom.find('tr[name="list_data_tpl"]').clone();
				listEle.attr('name','l_' + attr_id);
				listEle.find('input[type="checkbox"]').attr('name', 'c_' + attr_id);
				listEle.find('input[type="checkbox"]').attr('attr_name', data[i].attr_name);
				listEle.find('input[type="checkbox"]').attr('attr_value', data[i].attr_value);
				listEle.find('td[name="attr_name"]').html(data[i].attr_name);
				listEle.find('td[name="attr_value"]').html(data[i].attr_value);
				//绑定删除事件
				listEle.find('a[name="del_attr"]').unbind('click').bind('click',function(){
					$(this).closest('tr').remove();
				});
				listEle.show();
				listEle.appendTo(listDom.find("tbody"));
			}
		}
	},
	renderListData : function(data){				//选中列表绘制
		this.renderListEle(data);
		Eop.Dialog.close("list_attr_dlg");
	}
};