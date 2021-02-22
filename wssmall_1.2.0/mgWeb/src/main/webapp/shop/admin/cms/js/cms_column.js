$(function() {
	//根据配置模式值显示对应的TAB
	if($("#radio1").attr("checked")){
		$("#tab1").show();
		$("input[name=configMode]").val("1");
	}else if($("#radio2").attr("checked")){
		$("#tab2").show();
		$("input[name=configMode]").val("2");
	}
	
	$("select[name='source_type']").val("");
});

//Json_fields对象
function createJsonFields(ename,cname,def_value,value_type,value_ext_value,value_display,value_disable,rel_type,rel_id,show_value,db_name){
	var jsonFields = new Object();
	jsonFields.ename = ename;
	jsonFields.cname = cname;
	jsonFields.def_value = def_value;
	jsonFields.value_type = value_type;
	jsonFields.value_ext_value = value_ext_value;
	jsonFields.value_display = value_display;
	jsonFields.value_disable = value_disable;
	jsonFields.rel_type = rel_type;
	jsonFields.rel_id = rel_id;
	jsonFields.show_value = show_value;
	jsonFields.db_name = db_name;
	return jsonFields;
}

//将JS对象转为json字符串
function toJSONStr(jsonObj){
	var s = "{";
	for(p in jsonObj){
		s += p + ":\""+jsonObj[p]+"\",\n";
	}
	s = s.substr(0,s.lastIndexOf(","))+"}";
	return s;
}

//去前后空格
function trim(string){
	return string.replace(/(^\s*)|(\s*$)/g, "");
}

//将页面值处理成json
function setJsonFields(){
	var jsonFields = new Array();
	$("input[name=ename]").each(function(i){
		var ckd = $("#checkbox_"+i).attr("checked");
		if(!ckd){
			return true;//如果未选择记录，则将数据跳过，进入下次循环
		}
		var ename = $(this).val();
		var txt = $("#"+$(this).val());
		var cname = txt.val();
		if(cname == txt.attr("def_value")){
			cname = "";
		}
		var defValue = $("#defValue_"+i).val();
		if(defValue == $("#defValue_"+i).attr("def_value")){
			defValue = "";
		}
		var valueType = $("#valueType_"+i).val();
		var valueExtValue = $("#valueExtValue_"+i).val();
		if(valueType != "dialog" && valueType != "select" ){
			valueExtValue = "";
		}
		var value_display = "";
		var value_disable = $("#valueDisable_"+i).val();
		var rel_type = $("#relType_"+i).val();
		var rel_id = $("#relId_"+i).val();
		var show_value = $("#showValue_"+i).val();
		var db_name = $("#dbName_"+i).val();
		var jsonField = createJsonFields(ename,cname,defValue,valueType,valueExtValue,value_display,value_disable,rel_type,rel_id,show_value,db_name);
		jsonFields.push("\n"+toJSONStr(jsonField));
	});
	$("#json_fields").val("["+jsonFields+"]");
}

function clickText(txt){
	var val = $(txt).val();
	var defVal = $(txt).attr("def_value");
	if(trim(val) == defVal){
		$(txt).val("");
	}
}

function checkValOnBlur(txt){
	var val = $(txt).val();
	var defVal = $(txt).attr("def_value");
	if(trim(val) == ""){
		$(txt).val(defVal);
	}
}

function changeValueType(slt){
	var index = $(slt).attr("index");
	var vev = $("#valueExtValue_"+index);
	var sel = $("#relType_"+index);
	if($(slt).val() == "dialog"){
		sel.show();
	}else{
		sel.hide();
	}
	if($(slt).val() == "dialog" || $(slt).val() == "select"){
		vev.removeAttr("disabled");
		//vev.show();
		$("#valueExtValue_"+index).focus();
	}else{
		vev.attr("disabled","true");
		//vev.hide();
	}
}

function checkRadio(index){
	if(index == 1){
		$("#tab1").show();
		$("#tab2").hide();
	}else if(index == 2){
		$("#tab1").hide();
		$("#tab2").show();
	}
	$("input[name=configMode]").val(index);
}