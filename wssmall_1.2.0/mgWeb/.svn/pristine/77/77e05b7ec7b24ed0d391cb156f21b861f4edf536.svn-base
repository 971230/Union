var condType = {
	'cal_cond' : 'rule_cond_div', 				//参与计算
	'final_cond' : 'rule_final_cond'			//不参与计算
}
var condValType = {
	'cal_cond' : {								//参与计算
		'name' : 'ruleCondCValueArray',
		'value' : 'ruleCondValueArray'
	},
	'final_cond' : {						//不参与计算
		'name' : 'ruleConstCValueArray',
		'value' : 'ruleConstValueArray'
	}
}
var condRelVal = {										//除了界面可见的值以外，还有很多关联的值（放在隐藏域）
	'cal_cond' : {
		'attr_id' : 'ruleObjAttrArray',
		'obj_id' : 'ruleObjIdArray'
	},
	'final_cond' : {
		'obj_id' : 'ruleConstObjIdArray',
		'attr_id' : 'ruleConstAttrIdArray',
		'obj_code' : 'ruleConstObjCodeArray',
		'attr_code' : 'ruleConstAttrCodeArray'
	}
} 
var RuleCond = {
	conditionData : [],
	cond_type  : "",
	rule_model : "simple_model",
	init : function(){
		var me = this;
		Eop.Dialog.init({id:"showFactDlg",modal:false,title:"条件设置"});
		this.addRuleCond();
		this.saveCondition();
		this.modelSwitch();				//模式切换
	},
	addRuleCond : function(){
		$('a[name="add_rule_cond"]').unbind('click').bind('click', function(){
			Eop.Dialog.open("showFactDlg");
			var url = app_path + "/shop/admin/ruleManager!getFactList.do?ajax=yes";
			//rule_id根据当前选中的规则获取
			var rule_id = $(this).attr('rule_id');
			var cond_type = $(this).attr('cond_type');
			$("#showFactDlg").load(url,{"rule_id":rule_id,"cond_type":cond_type});
		});
	},
	renderCondition : function(cond_type){				//包含条件之间的关系（与/或）
		var me = this;
		//绘制条件页面，弹出框返回值  conditionData
		if(null != me.conditionData && typeof(me.conditionData) == 'object' 
			&& me.conditionData instanceof Array && me.conditionData.length > 0){
			var isExist = $("#" + condType[cond_type] + " tbody[name='cond_config']").length > 0 ? true:false;
			if(!isExist){
				var tempObj = $("#rule_cond_tpl").clone();
				tempObj.attr('id','');
				$("#" + condType[cond_type] + "").empty();
				tempObj.appendTo("#" + condType[cond_type] + "");
			}
			//删除已经剔除的元素
			$("#" + condType[cond_type] + " tr[tr_flag='val']").each(function(){
				var attr_id = $(this).attr('attr_id');
				if(null != attr_id && attr_id != "" && attr_id != 'undefined'){
					if(!me.checkAttrExist(attr_id)){
						$(this).next("tr").remove();
						$(this).remove();
					}
				}
			});
			//重新渲染选中的条件
			for(var i = 0; i < me.conditionData.length; i++){
				var attrObj = me.conditionData[i];
				var hasExist = $("div#" + condType[cond_type] 
						+ " tr[attr_id='" + attrObj.attr_id + "']").length > 0 ? true : false;		//判断是否已经存在(不重绘的目的是保留条件之间的关系)
				if(hasExist){
					me.modifyCondDom(attrObj, cond_type);			//重新赋值
				}else {
					//判断最后一个元素是否有与或关系运算
					var temp = $("#" + condType[cond_type] + " tbody[name='cond_config'] tr[tr_flag='rel']:last-child");
					if(null != temp && temp.length > 0){
						var connect = temp.find('select[name="connect_codeArray"]');
						if(null == connect || connect.length == 0){
							var andObj = $("#and_or_tpl").clone();
							andObj.attr("id","");
							andObj.appendTo(temp.find('p'));
						}
					}
					var condAttr = me.createCondDom(attrObj, cond_type);			//创建元素
					condAttr.children().each(function(){
						$(this).appendTo($("#" + condType[cond_type] + " tbody[name='cond_config']"));
					});
				}
			}
			//删除最后一个与或关系
			var lastTr = $("#" + condType[cond_type] + " tbody[name='cond_config'] tr[tr_flag='rel']:last-child");
			if(null != lastTr && lastTr.length > 0){
				lastTr.find('select[name="connect_codeArray"]').remove();
			}
		}else {
			$("div#" + condType[cond_type]).html("条件结果配置为空");
		}
	},
	createCondDom : function(attrObj, cond_type){
		var type = attrObj.ele_type;
		var condDom = null;
		if(type == 'input' || type == 'date'){	//文本条件,日期
			condDom = $("tbody[name='cond_text_tpl'][cond_type='" + condType[cond_type] + "']").clone();
			
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attrObj.ele_value);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attrObj.ele_value);
			
		}else if(type == 'checkbox'){			//复选框条件
			condDom = $("tbody[name='cond_checkbox_tpl'][cond_type='" + condType[cond_type] + "']").clone();
			var attr_value = "", attr_name = "";
			if(null != attrObj.ele_value && typeof(attrObj.ele_value) == 'object' 
				&& attrObj.ele_value instanceof Array && attrObj.ele_value.length > 0){
				for(var i = 0; i < attrObj.ele_value.length; i++){
					if(i == 0){
						attr_value += attrObj.ele_value[i].attr_value;
						attr_name += attrObj.ele_value[i].attr_name;
					}else {
						attr_value += ',' + attrObj.ele_value[i].attr_value;
						attr_name += ',' + attrObj.ele_value[i].attr_name;
					}
					condDom.find('label').append('<input disabled="disabled" type="checkbox" checked="checked" value="' + attrObj.ele_value[i].attr_value + '" />' + attrObj.ele_value[i].attr_name);
				}
			}
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attr_name);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attr_value);
		}else if(type == 'radio'){			//单选框处理条件
			condDom = $("tbody[name='cond_radio_tpl'][cond_type='" + condType[cond_type] + "']").clone();
			condDom.find('label').append('<input disabled="disabled" type="radio" checked="checked" value="' + 
					attrObj.ele_value.attr_value + '" />' + attrObj.ele_value.attr_name);

			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attrObj.ele_value.attr_name);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attrObj.ele_value.attr_value);
			
		}else if(type == 'select'){			//下拉列表
			condDom = $("tbody[name='cond_select_tpl'][cond_type='" + condType[cond_type] + "']").clone();
			
			condDom.find("select[name='" + condValType[cond_type]['name'] + "']").append('<option selected="selected" value="' + 
					attrObj.ele_value.attr_value + '">' + attrObj.ele_value.attr_name + '</option>');
			
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attrObj.ele_value.attr_name);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attrObj.ele_value.attr_value);

		}else if(type == 'list'){
			condDom = $("tbody[name='cond_list_tpl'][cond_type='" + condType[cond_type] + "']").clone();
			var attr_value = "", attr_name = "";
			if(null != attrObj.ele_value && typeof(attrObj.ele_value) == 'object' 
				&& attrObj.ele_value instanceof Array && attrObj.ele_value.length > 0){
				var attr_id = attrObj.attr_id;
				for(var i = 0; i < attrObj.ele_value.length; i++){
					var objVal = attrObj.ele_value[i];
					var listEle = condDom.find('tr[name="list_data_tpl"]').clone();
					listEle.attr('name','l_' + attr_id);
					listEle.find('input[type="checkbox"]').attr('name', 'c_' + attr_id);
					listEle.find('input[type="checkbox"]').attr('attr_name', objVal.attr_name);
					listEle.find('input[type="checkbox"]').attr('attr_value', objVal.attr_value);
					listEle.find('td[name="attr_name"]').html(objVal.attr_name);
					listEle.find('td[name="attr_value"]').html(objVal.attr_value);
					listEle.show();
					if(i == 0){
						attr_value += objVal.attr_value;
						attr_name += objVal.attr_name;
					}else {
						attr_value += ',' + objVal.attr_value;
						attr_name += ',' + objVal.attr_name;
					}
					listEle.appendTo(condDom.find('tbody'));
				}
			}
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attr_name);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attr_value);
		}else if(type == 'html'){
			condDom = $("tbody[name='cond_html_tpl'][cond_type='" + condType[cond_type] + "']").clone();
			condDom.find("input[name='ruleObjId']").val(attrObj.obj_id);
		}else if(type == 'never_run'){
			condDom = $("tbody[name='cond_never_tpl'][cond_type='" + condType[cond_type] + "']").clone();
			condDom.find("input[name='ruleObjId']").val(attrObj.obj_id);
			condDom.find("input[name='never_run_flag']").val("never_run");
		}else {				//默认按照文本处理
			condDom = $("tbody[name='cond_text_tpl'][cond_type='" + condType[cond_type] + "']").clone();
			
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attrObj.ele_value);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attrObj.ele_value);
		}
		condDom.find('p[name="attr_name"]').html(attrObj.obj_name + '(' + attrObj.attr_name + ')');
		//设置隐藏域的值
		var hidObj = condRelVal[cond_type];
		for(var key in hidObj){
			condDom.find('input[name="' + hidObj[key] + '"]').val(attrObj[key]);
		}
		condDom.find("tr:first-child").attr('attr_id',attrObj.attr_id);
		if(this.rule_model == 'oper_model'){
			condDom.find('[rule_model="oper"]').show();
		}else {
			condDom.find('[rule_model="oper"]').hide();
		}
		return condDom;
	},
	modifyCondDom : function(attrObj, cond_type){
		var condDom = $("#" + condType[cond_type] + "").find("tr[attr_id='" + attrObj.attr_id + "']");
		var type = attrObj.ele_type;
		condDom.find('p[name="attr_name"]').html(attrObj.obj_name + '(' + attrObj.attr_name + ')');
		
		if(type == 'input' || type == 'date'){			//事件日期属性
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attrObj.ele_value);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attrObj.ele_value);
		}else if(type == 'radio'){						//单选框属性
			var label = condDom.find('p[name="attr_value"] label');
			label.empty();
			
			label.append('<input disabled="disabled" type="radio" checked="checked" value="' + 
					attrObj.ele_value.attr_value + '" />' + attrObj.ele_value.attr_name);
			
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attrObj.ele_value.attr_name);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attrObj.ele_value.attr_value);
			
		}else if(type == 'checkbox'){				//复选框属性
			var attr_value = "", attr_name = "";
			var label = condDom.find('p[name="attr_value"] label');
			label.empty();
			
			if(null != attrObj.ele_value && typeof(attrObj.ele_value) == 'object' 
				&& attrObj.ele_value instanceof Array && attrObj.ele_value.length > 0){
				for(var i = 0; i < attrObj.ele_value.length; i++){
					if(i == 0){
						attr_value += attrObj.ele_value[i].attr_value;
						attr_name += attrObj.ele_value[i].attr_name;
					}else {
						attr_value += ',' + attrObj.ele_value[i].attr_value;
						attr_name += ',' + attrObj.ele_value[i].attr_name;
					}
					label.append('<input disabled="disabled" type="checkbox" checked="checked" value="' 
							+ attrObj.ele_value[i].attr_value + '" />' + attrObj.ele_value[i].attr_name);
				}
			}
			
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attr_name);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attr_value);
			
		}else if(type == 'select'){						//下拉列表属性
			var select = condDom.find("select[name='" + condValType[cond_type]['name'] + "']");
			select.empty();
			
			select.append('<option selected="selected" value="' + 
					attrObj.ele_value.attr_value + '">' + attrObj.ele_value.attr_name + '</option>');
			
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attrObj.ele_value.attr_name);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attrObj.ele_value.attr_value);

		}else if(type == 'list'){						//列表属性
			var attr_value = "", attr_name = "";
			var tbody = condDom.find('tbody');
			tbody.find('tr:gt(1)').remove();
			if(null != attrObj.ele_value && typeof(attrObj.ele_value) == 'object' 
				&& attrObj.ele_value instanceof Array && attrObj.ele_value.length > 0){
				
				for(var i = 0; i < attrObj.ele_value.length; i++){
					if(i == 0){
						attr_value += attrObj.ele_value[i].attr_value;
						attr_name += attrObj.ele_value[i].attr_name;
					}else {
						attr_value += ',' + attrObj.ele_value[i].attr_value;
						attr_name += ',' + attrObj.ele_value[i].attr_name;
					}
				}
				AttrFactory.renderListEle(attrObj.ele_value,$('div#' + condType[cond_type] +' tbody[name="cond_config"]' + 
					' tr[attr_id="' + attrObj.attr_id +'"][tr_flag="val"]'));
			}
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attr_name);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attr_value);
		}else{
			condDom.find("input[name='" + condValType[cond_type]['name'] + "']").val(attrObj.ele_value);
			condDom.find("input[name='" + condValType[cond_type]['value'] + "']").val(attrObj.ele_value);
		}
	},
	saveCondition : function(){
		$("#save_condition").unbind('click').bind('click', function(){
			//默认界面配置
			var url = app_path + 
				"/shop/admin/rule!saveConfigRule.do?ajax=yes&isaudit=yes&rule_sys_flag=" + rule_sys_flag; // 默认提交并审核(完全是为了拼凑出符合后台方法的入参)
			
			//判断是否选择了条件
			var ruleConds= $("#rule_cond_div tbody[name='cond_config'] tr[tr_flag='val']");
			if(ruleConds && ruleConds.length <= 0){
				alert("请配置配置规则条件");
				return ;
			}
			Cmp.ajaxSubmit('rule_config_add_form','',url,{},function(data){
				alert(data.msg);
				$.Loading.hide();
			},'json');
		});
	},
	checkAttrExist : function(attr_id){				//判断是否选中
		var result = false;
		for(var i = 0; i < this.conditionData.length; i++){
			var attrObj = this.conditionData[i];
			if(attrObj.attr_id == attr_id)
				return true;
		}
		return result;
	},
	modelSwitch : function(){					//模式切换
		var me = this;
		$('span[name="model_switch"] a').unbind('click').bind('click', function(){
			var ruleModel = $(this).attr('name');
			$(this).addClass('r_curr').siblings().removeClass('r_curr');
			if(ruleModel == 'oper_model'){
				$("*[rule_model='oper']").show();
			}else {
				$("*[rule_model='oper']").hide();
			}
			me.rule_model = ruleModel;
		});
	}
};