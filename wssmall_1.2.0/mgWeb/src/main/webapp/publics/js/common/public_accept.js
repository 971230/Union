$(function(){
	var hovertimer;
 	$(".mall_floor [name='hover']").bind("mouseover",function(){
 		window.clearTimeout(hovertimer);
 		$(".mall_floor i").closest("a").remove();
 		var curr = $(this).closest("a");
 		var nextA= $('<a href="#" target="_blank"><i></i></a>');
 		nextA.insertAfter(curr);
 		nextA.attr("href",curr.attr("href"));
 		nextA.find("i").css({"top":"0px","left":"0px","display":"block","width":(curr.find("img").attr("p_width") || curr.find("img").width()-3 ),height:(curr.find("img").attr("p_height") || curr.find("img").height()-4 )})
 	}).bind("mouseout",function(){
 			 hovertimer = window.setTimeout(function(){$(".mall_floor i").closest("a").remove()},500);
 	});
})


// 绑定事件名称
function addEventByName(name, parentJq, event_name, call_back) {
	$("[name='" + name + "']", parentJq || document).bind(event_name, call_back);
	return false;
}
// id绑定事件
function addEventById(name, parentJq, event_name, call_back) {
	$("[id='" + name + "']", parentJq || document).bind(event_name, call_back);
	return false;
}
function addEventByExpr(name, parentJq, event_name, call_back) {
	$(name, parentJq || document).bind(event_name, call_back);
	return false;
}
function addClassByName(express,currJq,className) {
	$(express).removeClass(className);
	currJq.addClass(className);
}
//对象字段克隆
function cloneByFields(s,fields){
	s = s || {};
	var cloneData  ={},fields_arr = fields.split(",");
	for(var i=0,field_name;field_name=fields_arr[i++];){
		if(s.length>0){
			s.attr(field_name) &&(cloneData[field_name] = s.attr(field_name) || '');
		}else{
			s[field_name] && (cloneData[field_name] = s[field_name] || '');
		}
	}
	return cloneData;
}

function initEvent(bindMenus){
	for(var i=0,bindMenu;bindMenu=bindMenus[i++];){
		(function(bindMenu){
			addEventById(bindMenu['id'],null,"click",function(){
				bindMenu['click_func']($(this));
			});
		}(bindMenu))
	}
}
function setTab(attr_name,clickJq,className,context,e_tagName){
	var attr_name_value = clickJq.attr(attr_name);
	e_tagName = e_tagName ||'li';
	
	$("div["+attr_name+"][tab!='"+e_tagName+"']",context).hide();
	//关联tab页
	if(clickJq.attr("ref_tab")){
		$("div["+attr_name+"='"+clickJq.attr("ref_tab")+"'][tab!='"+e_tagName+"']",context).show();
	}else
		$("div["+attr_name+"='"+attr_name_value+"'][tab!='"+e_tagName+"']",context).show();
	
	$(e_tagName+"["+attr_name+"],div[tab='"+e_tagName+"']["+attr_name+"]",context).removeClass(className ? className : 'curr');
	clickJq.addClass(className ? className : 'curr');
}

function initTab(attr_name,callBack,className,context,intiCallBack,e_tagName,event_name){

	context =context || $("body");
	if(typeof(context) =="string")
		context =$("#"+context);
	e_tagName = e_tagName ||'li';
	if(event_name =="hover")
	{
		$(e_tagName+"["+attr_name+"],div[tab='"+e_tagName+"']["+attr_name+"]",context).hover(function(){
			setTab(attr_name,$(this),className,context,e_tagName);
			callBack && callBack($(this)); //回调函数处理
		})
		
	}else{
		$(e_tagName+"["+attr_name+"],div[tab='"+e_tagName+"']["+attr_name+"]",context).bind("click",function(){
			setTab(attr_name,$(this),className,context,e_tagName);
			callBack && callBack($(this)); //回调函数处理
		})
	}
	if(intiCallBack){
		intiCallBack();
	}else
		$(e_tagName+"["+attr_name+"],div[tab='"+e_tagName+"']["+attr_name+"]",context).eq(0).trigger("click");
}

//setTabSelected("rel_tab","attention",body)
function setTabSelected(rel_tab_name,rel_tab_value,context){
	context =context || $("body");
	if(typeof(context) =="string")
		context =$("#"+context);
    var attr_name = "["+rel_tab_name+"='"+rel_tab_value+"']";
	$("li"+attr_name+",div[tab='li']"+attr_name+"",context).trigger("click");
}

