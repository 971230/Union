
function OffMakePage(){
	this.id = "";
	this.field_name = "";
	this.field_desc = "";
	this.value_type = ""; //输入类型
	this.is_null = "";
	this.is_edit = "";
	this.attr_code = "";
	this.subList = "";
	this.page_url = "";//特殊处理
	this.is_check="";
	this.attr_format="";
	this.check_message="";
	this.value ="";
	this.default_value ="";
	this.cellcount =1;
	this.serv_no;
	this.width ="";
	this.is_form="";
}

//页面布局
OffMakePage.prototype.pagelayout=function(data,serv_no,id,count,hide_card,p_class_name,is_form){
	count = data[0].cellcount?data[0].cellcount:count;
	var label_width = parseInt(100*1.2/count/3);
	var text_width = parseInt(100*1.8/count/3);
	var class_name = p_class_name ||  'kh_tb';
	var styles ="display:'';"
	if(hide_card =="T")
		styles ="display:none;";
	var html = " <div style="+styles+"> <table  cellpadding='0' class='"+class_name+"' cellspacing='0' id='"+id+"' >";
	if( data.length > 0 ) html += "<tr>";
	var index=0;
	for( var j=0; j<data.length; j++) {
		var curData = data[j];
		curData['is_form']=is_form;
		if(curData.value_type =="98V" && is_form =="yes")
			curData.value_type = "0";//表单展示的时候，将98V转换为0，按文本框展示，但是受理的时候不需要展示
		var labelId = "label_" + ((curData.prodInstId) ? publicFunc.getBeanId(curData.prodInstId)+curData.fieldName : curData.fieldName);
		if( curData.value_type == "98Y" || curData.value_type == "98V") {
			html += offMakePage.newControl(curData);
			continue;
		}
		if( curData.colspan == null || curData.colspan == "" ) curData.colspan = 1;
		var colspan = parseInt(curData.colspan,10);
		if(colspan > count) colspan=count;
		var is_null = curData.is_null;
		if( curData.value_type == "98T" ) {
			colspan = count;//textare占整行
		}
		if( index == count ) {
			html += "</tr><tr>"
			index=0;
		}
		if( (index+colspan) <= count ) {
			if(is_form =="yes"){ //表单
				if(is_null == "F" ) {
					html += " <th height='20px' class='blfont1' name='td_"+curData['field_name']+"_desc' align='right' width='"+label_width+"%'>"+curData.field_desc+"：</th>";
				} else {
					html += " <th height='20px' class='blfont1' name='td_"+curData['field_name']+"_desc' align='right' width='"+label_width+"%'>"+curData.field_desc+"：</th>";
				}
			}else{
				if( is_null == "F" ) {
					html += " <td height='20px' name='td_"+curData['field_name']+"_desc' class='blfont1' align='right' width='"+label_width+"%'><font color='red'>*</font>"+curData.field_desc+"：</td>";
				} else {
					html += " <td height='20px' name='td_"+curData['field_name']+"_desc' class='blfont1' align='right' width='"+label_width+"%'>"+curData.field_desc+"：</td>";
				}
			}
			html += "<td height='20px' width='"+(colspan*text_width+(colspan-1)*label_width)+"%' colspan="+(colspan*2-1)+">"+offMakePage.newControl(curData)+"</td>";
			index += colspan;
		} else {
			var find_flag = false;
			for( var k=j+1; k<data.length; k++ ) {
				var subData = data[k];
				var subColspan = parseInt(subData.colspan,10);
				if(!subColspan)
					subColspan =1;
				if(subColspan > count) subColspan=count;
				if( (index+subColspan) <= count ) {
					data[k] = curData;
					data[j] = subData;
					find_flag = true;
					j--;
					break;
				}
			}
			if( !find_flag ) {
				while( (index++)<count ) {//换行
					html += "<td height='20px'>&nbsp;</td>"
				}
				index = count;
				j--;
			}
		}
	}
	var new_index = index*2;
	var new_count = count*2;
	while((new_index++)<new_count ) {
		html += "<td height='20px'>&nbsp;</td>"
	}
	html += "</tr>";
	html += "</table></div>";
	return html;

}

//生成控件
OffMakePage.prototype.newControl=function(obj){
	
	//初始化属性
	for(var i in this){
		if(typeof(this[i]) != "string") continue;
		var value = obj[i];
		if(typeof(value) == 'undefined') value = "";
		this[i] = value;
	}
	this.id =serv_no+"_"+this.field_name;
	this.name =serv_no+"_"+this.field_name;
	if(!this.value && this.default_value)
		this.value =this.default_value;
		
	var inputMethod = this.value_type;
	switch(inputMethod) {//98A 单选下拉框、98C 文本输入、98D 日期控件、98E 密码输入、98M 弹出控件、98G IP输入、98I 整数输入、98J 浮点数输入
		
  	 	case "98A":case "1":{ //下拉框
  	 		var cellElement = null;
  	 		cellElement=this.createSelect();
  	 		$(cellElement).val(this.value); //设置缺省值
  	 		return cellElement; 
		}
		case "98C": case "98I": case "98J": case "98G": case "98X": case "98Z":case "0": {//文本输入
			return this.createInput();
		}
		case "98D": {//日期控件
			return this.createDateInput();
		}
		case "98W": {//日期控件
			return this.createTimeInput();
		}
		case "98O": {//日期控件有年月日时分秒
			return this.createDateTimeInput();
		}
		case "98E": {//密码输入
			return this.createPasswdInput();
		}
		case "98Y" : case '98V': {//隐藏控件
			return this.createHiddenInput();
		}
		case "98T": {//textarea
			return this.createTextareaInput();
		}
		case "BTN": {//按钮
			return this.createBtn();
		}
		default:{//默认文本输入
			return this.createInput();
		}
		
	}
}


//将属性转为字符串
OffMakePage.prototype.toString=function(){

	var str = "";

	for(var i in this){

		if(typeof(this[i]) != "string") continue;
		//特殊属性处理
		if(i == "is_edit"){
			if(this[i] == "F") str += " disabled ";
			continue;
		}

		str += i +"='"+this[i]+"' ";
	}
	
	return str;
}


OffMakePage.prototype.getSelectWidth=function(){
	var width = this.width;
	if(!width)
		width = "155px";
	return width;
}
OffMakePage.prototype.getCommonWidth=function(){
	var width = this.width;
	if(!width)
		width = "150px";
	return width;
}


OffMakePage.prototype.createSelect=function(){
	var style=" style='width:"+this.getSelectWidth()+"' ";
	var html = "<select "+this.toString()+style+"  >";
	return html;
}
OffMakePage.prototype.createBtn=function(){
	var html= [];
	var style=" style='width:"+this.getCommonWidth()+";height:30px;margin-left:30px;'";
	html.push("<div "+style+"><div class='btn_s fl' "+this.toString()+" onclick='offMakePage.specalDeal(this);'>");
    html.push("	<a href='javascript:void(0)' class='W_btn_c'><span><em>"+this.field_desc+"</em></span></a>");
	html.push("</div></div>");
	return html.join("");
}

OffMakePage.prototype.createInput=function(){
	
	var style=" style='width:"+this.getCommonWidth()+"' ";
	if(this.is_form =="yes"){
		html="<span style='width:"+this.getCommonWidth()+";height:25px;' name='"+this.field_name+"_span'>"+this.default_value+"</span>";
	}else{
		html="<input type='text' "+this.toString()+style+"  onchange='offMakePage.specalDeal(this);'>";
	}
	return html;
	
}
OffMakePage.prototype.createDateInput=function(){
	var style=" style='width:"+this.getCommonWidth()+"' ";
	var html="<input type='text' "+this.toString()+style+"  onclick='WdatePicker();' onchange='offMakePage.specalDeal(this);'>";
	return html;

}
OffMakePage.prototype.createTimeInput=function(){

	var style=" style='width:"+this.getCommonWidth()+"' ";
	var html="<input type='text' "+this.toString()+style+"  onclick='WdatePicker({dateFmt:\"HH:mm:ss\"});' onchange='offMakePage.specalDeal(this);'>";
	return html;

}

OffMakePage.prototype.createDateTimeInput=function(){
	var style=" style='width:"+this.getCommonWidth()+"' ";
	var curr_date =$("#curr_input_date").val();
	var html="<input  min='sysdate' type='text' "+this.toString()+style+" onclick=J.calendar.get({time:true,to:'"+curr_date+",max'}); />";
	return html;

}

OffMakePage.prototype.createPasswdInput=function(){

	var style=" style='width:"+this.getCommonWidth()+"' ";
	var html="<input type='password' "+this.toString()+style+" onchange='offMakePage.specalDeal(this);'>";
	return html;
}

OffMakePage.prototype.createTextareaInput=function(){

	var html = "";
	var style=" style='width:"+this.getCommonWidth()+"' ";
	if(this.is_form =="yes"){
		html="<span style='width:"+this.getCommonWidth()+";height:25px;' name='"+this.field_name+"_span'>"+this.default_value+"</span>";
	}else{
		html=" <textarea "+this.toString()+" style='width: 100%; height:50px' onchange='offMakePage.specalDeal(this);'> </textarea> ";
	}
	
	return html;
}


OffMakePage.prototype.createHiddenInput=function(){
	return "<input type='hidden' "+this.toString()+" >";
}

//点击文本框的时候的特殊处理,control为当前的输入框
OffMakePage.prototype.specalDeal=function(control){
	var funName = $(control).attr("page_url");
	if(funName == null || funName == "") return;
	try{
		eval(funName+"(control)")
	}catch(e){}
}
var offMakePage = new OffMakePage();
