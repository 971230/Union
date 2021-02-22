/**
 *列表树控件
 *@auther:pzh
 *
 */
Function.prototype.bindEvent=function(object,newPara){
	var _method=this;
	return function(event){
		_method.call(null,event||window.event,object,newPara);//null直接调用方法，event||window.event,object,newPara为参数
		return null;
	}
};
var TreeObj = function() {
	// 存单元格的值关联数组
	this.tdValue = {};
	// 列单元事件
	this.tdEvent = [];
	// 鼠标悬停时显示的文字
	this.tdAlt = "";
	// 超链接地址
	this.tdLink = "";
	// 文字的颜色,默认黑色
	this.tdColor = "#000000";
	// 节点id
	this.trId = "";
	// 父节点id
	this.trPid = "";
};
var MyJQ = function(id){
	return document.getElementById(id);
}