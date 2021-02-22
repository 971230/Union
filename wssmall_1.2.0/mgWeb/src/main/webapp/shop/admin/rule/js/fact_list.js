//属性常量值
var AttrTypeConst = {
	"select" : {"attr_name":"下拉框"},
	"input"	: {"attr_name":"变量值"},
	"radio" : {"attr_name":"单选框"},
	"checkbox" : {"attr_name":"复选框"},
	"date" : {"attr_name":"时间/日期"},
	"list" : {"attr_name":"列表"}
}
//页面元素设值
$.fn.setDomValue = function(domVal){
	if(domVal.ele_type == 'date' || domVal.ele_type == 'input'){
		$(this).find('input').val(domVal.ele_value);
	}else if(domVal.ele_type == 'radio'){
		if(null != domVal.ele_value  && domVal.ele_value != ""){
			var attrObj = eval('(' + domVal.ele_value + ')');
			$(this).find("input[value='" + attrObj.attr_value + "']").attr("checked", "checked");
		}
	}else if(domVal.ele_type == 'checkbox'){
		if(null != domVal.ele_value  && domVal.ele_value != ""){
			var attrArray = eval('(' + domVal.ele_value + ')');
			for(var i = 0; i < attrArray.length; i++){
				var attrObj = attrArray[i];
				$(this).find("input[value='" + attrObj.attr_value + "']").attr("checked", "checked");
			}
		}
	}else if(domVal.ele_type == 'select'){
		if(null != domVal.ele_value  && domVal.ele_value != ""){
			var attrObj = eval('(' + domVal.ele_value + ')');
			$(this).find('select').val(attrObj.attr_value);
		}
	}else if(domVal.ele_type == 'list'){
		if(null != domVal.ele_value  && domVal.ele_value != ""){
			var attrArray = eval('(' + domVal.ele_value + ')');
			AttrFactory.renderListEle(attrArray, $(this));
		}
	}else {
		$(this).find('input').val(domVal.ele_value);
	}
}

var FactList = {
	cond_type : "",						//规则条件类型， 参与计算，不参与计算
	init : function(){
		//规则对象点击事件，树的收缩效果
		this.objTreeClick()
		//规则对象属性点击事件，创建属性dom元素
		this.createDomEle();
		//确认事件，拼装已选择的条件
		this.pickFactData();
		this.checkboxEvent();
		this.attrCheckClick();
	},
	initSelected : function(data){
		var me = this;
		//默认展示第一个选中的属性，如果没有选中的元素，则默认选中第一个属性元素
		if(null != data && typeof(data) == 'object' 
			&& data instanceof Array && data.length > 0){//已选中的数据不为空
			$("li[name='rule_obj'][obj_id='" + data[0].obj_id + "']").trigger('click');
			for(var i = 0; i < data.length; i++){
				me.setSelelectedAttr(data[i]);
			}
			//查找选中的第一个元素，并显示
			var selectedDom = $('li[name="rule_obj_attr"] input:checked').eq(0);
			var firstDom = selectedDom.closest("li");
			var ele_type = firstDom.attr('attr_type');
			var attr_id = firstDom.attr('attr_id');
			//设置属性名称
			$("div#attr_name span").html(firstDom.attr('attr_name'));
			if(ele_type == 'checkbox'){
				$("div#attr_name label").remove();
				$("div#attr_name").append('<label><input type="checkbox" attr_id="' + attr_id + '" name="fact_attr_checkbox" />全选</label>');
			}else {
				$("div#attr_name label").remove();
			}
			$("div#attr_value *[attr_id='" + firstDom.attr("attr_id") + "']").show();
		}else{
			$('li[name="rule_obj_attr"]').eq(0).trigger('click');
		}
	},
	objTreeClick : function(){
		$("li[name='rule_obj']").die('click').live('click', function(){
			$(this).find("ul").show();
			$(this).siblings().each(function(){
				$(this).find("ul").hide();
			})
		});
	},
	createDomEle : function(){
		$("li[name='rule_obj_attr']").die('click').live('click', function(){
			$(this).addClass('curr').siblings().removeClass('curr');
			var attr_type = $(this).attr('attr_type');
			var attr_data = $(this).attr('attr_data');
			var attr_id = $(this).attr('attr_id');
			//创建全选按钮
			var ele_type = $(this).attr('attr_type');
			if(ele_type == 'checkbox'){
				$("div#attr_name label").remove();
				$("div#attr_name").append('<label><input type="checkbox" attr_id="' + attr_id + '" name="fact_attr_checkbox" />全选</label>');
			}else {
				$("div#attr_name label").remove();
			}
			//判断是否已经存在
			var existEle = $('div#attr_value *[attr_id="' + attr_id + '"]');
			if(null != existEle){
				if(existEle.length > 1){
					alert('规则属性值【' + attr_id + '】不唯一,请检查配置');
					return ;
				}
				if(existEle.length == 1){
					var attr_name = $(this).attr('attr_name');
					$("div#attr_name span").html(attr_name);
					existEle.show();						//存在，则展示出来
					existEle.siblings().hide();				//隐藏同级其他元素
					return ;
				}
			}
			//创建属性元素
			AttrFactory.curAttr = $(this);
			var domEle = AttrFactory.create(attr_data, attr_type);
			//设置属性名称
			var attr_name = $(this).attr('attr_name');
			$("div#attr_name span").html(attr_name);
			//设置属性内容
			$("div#attr_value").children().hide();			//隐藏已创建的属性元素，只展示新建的属性元素
			domEle.attr('attr_id',attr_id);
			domEle.appendTo("div#attr_value");
		});
	},
	pickFactData : function(){
		var me = this;
		$("#confirm_condition").unbind('click').bind('click', function(){
			var selectedLi = $('li[name="rule_obj_attr"] input:checked');
			if(me.cond_type == 'cal_cond' && (selectedLi == null || selectedLi.length <= 0)){
				alert("请至少选择一个条件");
				return ;
			}
			RuleCond.conditionData = [];		//设置前，清空数组
			var rule_id = $('div input#rule_id').val();
			selectedLi.each(function(index){
				//规则条件对象拼装
				var attr_val = {'cond_type' : me.cond_type};
				attr_val['rule_id'] = rule_id;
				var obj_id = $(this).closest("li[name='rule_obj']").attr('obj_id');
				attr_val['obj_id'] = obj_id;
				var obj_code = $(this).closest("li[name='rule_obj']").attr('obj_code');
				attr_val['obj_code'] = obj_code;
				var obj_name = $(this).closest("li[name='rule_obj']").attr('obj_name');
				attr_val['obj_name'] = obj_name;
				var objAttr = $(this).closest("li[name='rule_obj_attr']");
				var attr_id = objAttr.attr('attr_id');
				attr_val['attr_id'] = attr_id;
				var attr_type = objAttr.attr('attr_type');
				attr_val['ele_type'] = attr_type;
				var ele_value = me.getValue(attr_id, attr_type);
				attr_val['ele_value'] = ele_value;
				var attr_name = objAttr.attr('attr_name');
				attr_val['attr_name'] = attr_name;
				var attr_code = objAttr.attr('attr_code');
				attr_val['attr_code'] = attr_code;
				//设置返回值
				me.pushValue(attr_val);
			});
			me.saveRuleAttr(RuleCond.conditionData, rule_id);
			RuleCond.renderCondition(me.cond_type);
		});
	},
	pushValue : function(attrData){			//设置规则属性条件
		RuleCond.conditionData.push(attrData);
	},
	getValue : function(attr_id, type){		//获取规则属性条件
		var objAttr = $('div#attr_value *[attr_id="' + attr_id + '"]');
		var attrValue = null;
		if(type == 'select'){
			var value = objAttr.find('select').val();
			var name = objAttr.find("option:selected").attr('attr_name');
			attrValue = {'attr_value':value,'attr_name':name};
		}else if(type == 'radio'){
			var curObj = objAttr.find('input:checked');
			var value = curObj.val();
			var name = curObj.attr('attr_name');
			attrValue = {'attr_value':value,'attr_name':name};
		}else if(type == 'checkbox'){
			attrValue = [];
			objAttr.find('input:checked').each(function(index){
				var value = $(this).val();
				var name = $(this).attr('attr_name');
				attrValue.push({'attr_value':value,'attr_name':name});
			});
		}else if(type == 'date' || type == 'input'){
			attrValue = objAttr.find('input').val();
		}else if(type == 'list'){
			attrValue = [];
			objAttr.find('tr[name="l_' + attr_id + '"] input:checked').each(function(index){
				var value = $(this).attr('attr_value');
				var name = $(this).attr('attr_name');
				attrValue.push({'attr_value':value,'attr_name':name});
			});
		}else {	//默认为文本值
			attrValue = objAttr.find('input').val()
		}
		return attrValue;
	},
	saveRuleAttr : function(data,rule_id){
		var url = app_path + "/shop/admin/ruleManager!saveRuleAttr.do?ajax=yes";
		Cmp.excute("", url, {'ruleAttrStr' : JSON.stringify(data),'cond_type' : this.cond_type, 'rule_id':rule_id}, function(reply){
			if(reply.result != '0'){
				alert("条件选择入库异常");
				return ;
			}
			Eop.Dialog.close("showFactDlg");
		}, "json");
	},
	setSelelectedAttr : function(attr){
		var ruleObjAttr = $('li[name="rule_obj_attr"][attr_id="' + attr.attr_id + '"]');
		if(null != ruleObjAttr && ruleObjAttr instanceof Object){
			ruleObjAttr.find("input").attr('checked','checked');
			var attr_type = ruleObjAttr.attr('attr_type');
			var attr_data = ruleObjAttr.attr('attr_data');
			//创建属性元素
			AttrFactory.curAttr = $("li[attr_id='" + attr.attr_id + "']");
			var domEle = AttrFactory.create(attr_data, attr_type);
			//设置属性内容
			domEle.attr('attr_id', attr.attr_id);
			domEle.setDomValue(attr);
			domEle.hide();									//初始化的时候全部隐藏
			domEle.appendTo("div#attr_value");
		}
	},
	stopPropagation : function(e){			//防止事件冒泡
		if(e.stopPropagation){
			e.stopPropagation();
		}else{
			e.cancelBubble = true; 			//IE阻止冒泡方法 
		}
	},
	checkboxEvent : function(){
		$("div#attr_name input[name='fact_attr_checkbox']")
			.die('click').live('click', function(){
			var attr_id = $(this).attr('attr_id');
			var attrVal = $(this).closest('div#attr_name')
				.next('div#attr_value').find('div[attr_id="' + attr_id + '"]');
			if($(this).is(":checked")){
				attrVal.find('input[type="checkbox"]').each(function(){
					$(this).attr('checked','checked');
				});
			}else {
				attrVal.find('input[type="checkbox"]').each(function(){
					$(this).attr('checked','');
				});
			}
		})
	},
	attrCheckClick : function(){
		$('li[name="rule_obj_attr"] input').die('click').live('click',function(){
			var liObj = $(this).closest('li');
			var attr_code = liObj.attr('attr_code');
			if(attr_code == 'without_cond' || attr_code == 'never_run_cond'){
				liObj.siblings('li').find('input').each(function(){
					$(this).attr('checked','');
				});
			}else {
				liObj.siblings('li[attr_code="without_cond"]').find('input').attr('checked','');
				liObj.siblings('li[attr_code="never_run_cond"]').find('input').attr('checked','');
			}
		})
	}
}
$(function(){
	FactList.init();
	//初始化对话框
	Eop.Dialog.init({id:'list_attr_dlg',modal:false,title:'列表数据',width:"700px"});
});