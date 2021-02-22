var oldValue = null;
var divContentObj;

/**
 * 文本框keyUp的时候执行该方法 add by wui
 * 
 * @param {}
 *            inputObj
 */
function keyUpFun(inputObj) {

	var queryValue = $(inputObj).val();
	var keyName = $(inputObj).attr("name");
	if(!keyName)keyName="keyWord";
	// 输入的内容以AND OR 结尾时，不做查询
	/**
	 * 输入格式说明: 与操作 用空格或者AND分隔 或操作 用OR分隔 非操作 用NOT做前缀 *代码所有的字符
	 * 
	 * @type RegExp
	 */
	var reg = /(AND|OR|NOT|\s|\-|\[|\]|\(|\)|\\|\/|--|-|{|})$/ig;
	
	var resNew = /^[-]+/;
	var matchZt = reg.test(queryValue);
	var matchZtNew = resNew.test(queryValue);
	if (matchZt || matchZtNew)// 为真的时候直接跳出
		return;
	
	var regChar = /^[_]{1,2}$/;

	var matchZt = regChar.test(queryValue);
	if (matchZt) {// 为真的时候直接跳出
		return;
	}
	if(!queryValue)
	{
		oldValue  ="";
	}
	if(!queryValue)
	{
		oldValue  ="";
	} 
	if (!!queryValue) {

		if (oldValue != queryValue) {
			oldValue = queryValue;
			var tableName = $(inputObj).attr("tableName");
			var tableColumn = $(inputObj).attr("tableColumn");

			var obj = {};
			obj.tableName = tableName;
			obj.tableColumn = tableColumn;

			queryValue = queryValue.replace(/(\[|\]|\(|\))/ig, "");

			obj.queryValue = queryValue;
			// 清空内容
			divContentObj = $(inputObj).data("divContent");
			
			divContentObj.data("inputObj", inputObj);
			divContentObj.empty();
			//ie6问题解决
			if(navigator.userAgent.indexOf("MSIE 6.0")>0){
				var iwidth = divContentObj.outerWidth()-new Number(50);
				
				var iheight = divContentObj.outerHeight()-new Number(50);
				if(!(divContentObj.find("iframe").length>0)){
					 var iframeHtml = "<iframe  frameborder='0' framespacing='0' src='javascript:false' style='margin:0;padding:0;position:absolute; visibility:inherit; width:"+iwidth+"; height:"+iheight+"; z-index:-1; filter=\"progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\";'></iframe>";
					 divContentObj.prepend(iframeHtml);
				}
			}
			
			// 滑动事件
			divContentObj.hover(function() {
						$(this).show();
					}, function() {

						$(this).hide();
					});
			divContentObj.unbind("keydown"); // 删除绑定的事件，清空的时候不会清空事件
			divContentObj.keydown(function(event) {

						keyUpEvent(inputObj, event, $(this));
					});
					
			//传递方法
			var loadService =($(inputObj).attr("loadService"));
			var loadMethod =($(inputObj).attr("loadMethod"));
			var type = ($(inputObj).attr("objType"));
			if('package'==type){
				divContentObj.css("width","200px");
			}
			var url = "";
			var new_url = $(inputObj).attr('search_url');
			if(null != new_url && '' != new_url && 'undefined' != new_url){
				url = new_url;
			}else {
				url = "/ss!search.do?ajax=yes";
			}
			$.ajax({
				url: ctx + url,
				data:{"keyWord":queryValue,"keyName":keyName,"type":type},
				type:"post",
				dataType:"json",
				success:function(reply) {
					// 设置div
					if(!reply)
						return ;
					var result = reply;
					if (!!result[0]) {

						for (var i = 0; i < result.length; i++) {
							var resultMap = result[i];
							var showContent = null;
							var hiddenValueArray = [];
							var hiddenValue = null;
							for (var attr in resultMap) { //循环map对象
								if ($(inputObj).attr("tableColumn") == attr) {
									showContent = resultMap[attr];
									hiddenValueArray.push(attr+"="+showContent);
								} else {
									//if (!(attr.indexOf("CLASS") > -1) && !(attr.indexOf("my_rownum") > -1)) {
										hiddenValueArray.push(attr+"="+resultMap[attr]);
									//}

								}

							}
							hiddenValue = hiddenValueArray.join(",");
							if(loadMethod !="search"){
								//当查询的内容用空格分隔的时候，递归模糊匹配
								queryValue = queryValue.replace(/\s+/ig,",");
								var queryValueArr = [];
								if(queryValue.indexOf(",")>-1)
									queryValueArr =queryValue.split(",");
								else 
									queryValueArr.push(queryValue);
								for(var jj=0;jj<queryValueArr.length;jj++)
								{
									try{
										var tempValue = queryValueArr[jj];
										showContent =showContent.replace(tempValue,"<font class = 'highlight'>"+tempValue+"</font>");
										/*
										var beginidx = showContent.indexOf("<font class = 'highlight'>")+26;
										var lastidx = showContent.lastIndexOf("<font class = 'highlight'>");
										var tail = showContent.substring(beginidx,showContent.length);
										if(tail.length-(tempValue.length+7)>20){
											if(lastidx>0 && lastidx<=5){
												showContent = showContent.substring(beginidx-26-lastidx,26+tempValue.length+7+20);
											}
											else if(lastidx>5){
												showContent = showContent.substring(beginidx-26-5,26+tempValue.length+7+20);
											}
											else if(lastidx==0){
												showContent = showContent.substring(beginidx-26,26+tempValue.length+7+20);
											}
										}
										else{
											showContent = tail;
										}
										*/
									}catch(e){
										
									}
								}
							}
							else{
								if(showContent.length>30){
									showContent = showContent.substring(0,30);
								}
							}
							var divItem = null;
							divItem = $("<div class='item' hiddenValue = '"
									+ hiddenValue
									+ "'>"
									+ showContent
									+ "</div>");
							// divItem.width(divContentObj.width());
							divItem.data("itemValObj",resultMap);
							// 设置点击事件
							divItem.bind("click", function() {
								$(inputObj).val($(this).text());

								// 隐藏值放于隐藏字段中
								$(inputObj).attr("hiddenValue",$(this).attr("hiddenValue"));
								$(inputObj).data("itemValObj",$(this).data("itemValObj"));

								//执行回调函数
								var keydownFun = $(inputObj).attr("keyFun");
								var inputId = $(inputObj).attr('id');
								if (keydownFun) {
										divContentObj.hide();
										var event = window.event || arguments.callee.caller.arguments[0];
										eval(keydownFun + "('" + inputId + "')")(event,$(this).data("itemValObj"),"itemClick");
									}
								// 点击的时候隐藏div
								$(this).parent("div").hide();
							}); 

							// 绑定
							divItem.keydown(function() {
								if (event.keyCode == 13) { 
									var keydownFun = $(inputObj).attr("keyFun");
									var inputId = $(inputObj).attr('id');
									if (keydownFun) {
										divContentObj.hide();
										eval(keydownFun + "('" + inputId + "')")(event,$(this).data("itemValObj"));
									}
									divContentObj.hide();
								}
							});

							// 默认0个item设置样式
							if (i == 0) {
								divItem.addClass("divhover");
							}

							// 设置鼠标滑过事件
							divItem.hover(function() {
									$(this).addClass("divhover");
								}, 
								function() {
									$(this).removeClass("divhover");
							});
							
							//为菜单的时候需要特殊处理		
							//if(tableName =="mm_menu"){
							//	 addFilterMenu(divContentObj,divItem,hiddenValue);
							//}else
							//{
							divContentObj.append(divItem);
							//}
						}
						// 显示面板信息
						divContentObj.show();

						// input文本框绑定keyup事件
						// alert($(inputObj).attr("keyup"));

					} else {
						divContentObj.hide();
					}

				},error:function(){
					alert("查询失败，请重试");
				}
			});
		}

	}else{
	
		divContentObj = $(inputObj).data("divContent");
		divContentObj.empty();
		divContentObj.hide();
	}

}
//菜单过滤特殊处理
function addFilterMenu(divContentObj,divItem,hiddenValue){
	try{
		var canAdd = false;
		for(var w=0;w<allItems.length;w++){
			var currMenu = allItems[w];
			var curr_menu_id = currMenu.menuId;
			var menuId = hiddenValue.split(",")[0];								
			if(curr_menu_id ==menuId){
				canAdd = true;
				break;
			}
		}
		if(canAdd)
			divContentObj.append(divItem);
	}catch(e){
	}
}

/**
 * 创建显示面板信息
 * 
 * @param {}
 *            inputObj
 */
function createDivContent(inputObj,divContent) {

	var offset = inputObj.offset();

	var inputTop = offset.top;
	var inputLeft = offset.left;
	var inputWidth = inputObj.outerWidth(true);
	var inputHeight = inputObj.outerHeight(true);
	var isExist = true;
	if(!divContent){ //不存在创建面板
		divContent = $("<div id=\"contentPanel\" class=\"contentPanel\"></div>");
		
		isExist = false;
	}
	divContent.css({
				top : inputTop + inputHeight + 5,
				left : inputLeft
			});
	
	divContent.width(inputWidth + 250); // 24
	divContent.hide();
	divContentObj = divContent;
	if(!isExist){ //不存在添加到body
		$("body").append(divContent);
		inputObj.data("divContent", divContent);
		
	}
	return divContentObj;

}

function keyUpEvent(inputObj, event, divContentObj) {
	if ($(inputObj).val()) {
		switch (event.keyCode) {
			case 38 :
				up(divContentObj);
				break;
			case 40 :
				down(divContentObj);
				break;
			default :
				break;

		}

	}

}
// 键盘上移动
function up(divContentObj) {

	// 获取当前item

	var currentItemJq = $($("div.divhover"), divContentObj);
	var inputObj = divContentObj.data("inputObj");

	var prevObj = currentItemJq.prev();
	// 删除所有的样式
	$($("div.item"), divContentObj).removeClass("divhover");

	if (prevObj && !!prevObj.html()) { // 存在下一个元素
		prevObj.addClass("divhover");
		$(inputObj).attr("hiddenValue",prevObj.attr("hiddenValue"))
		$(inputObj).data("itemValObj",prevObj.data("itemValObj"));
		$(inputObj).val(prevObj.text()); // 设置值

	} else {
		currentItemJq.addClass("divhover");
		
		$(inputObj).attr("hiddenValue",currentItemJq.attr("hiddenValue"))
		$(inputObj).data("itemValObj",currentItemJq.data("itemValObj"));
		$(inputObj).val(currentItemJq.text());

	}
	currentItemJq.focus();

}
// 键盘下移动
function down(divContentObj) {
	// 获取当前item
	var currentItemJq = $($("div.divhover"), divContentObj);
	var nextObj = currentItemJq.next();
	var inputObj = divContentObj.data("inputObj");
	// 删除所有的样式
	$($("div.item"), divContentObj).removeClass("divhover");

	if (nextObj && !!nextObj.html()) { // 存在下一个元素
		nextObj.addClass("divhover");
	
		$(inputObj).attr("hiddenValue",nextObj.attr("hiddenValue"));
		$(inputObj).data("itemValObj",nextObj.data("itemValObj"));
		$(inputObj).val(nextObj.text());
	} else {
		currentItemJq.addClass("divhover");
		
		$(inputObj).val(currentItemJq.text());
		$(inputObj).attr("hiddenValue",currentItemJq.attr("hiddenValue"));
		$(inputObj).data("itemValObj",currentItemJq.data("itemValObj"));
	}
	currentItemJq.focus();
}
var Lucene={init:function(id){
	var inputObj = $('#'+id);
	if(!inputObj.data('divContent')){
		divContentObj = createDivContent(inputObj,inputObj.data('divContent'));
	}
	inputObj.keyup(function() {
		var e = window.event || arguments.callee.caller.arguments[0];
		//上移  下移按钮
		if(e.keyCode==38 || e.keyCode==40)return ;
		keyUpFun(this);
	});
	inputObj.keydown(function(event) {
		keyUpEvent($(this), event, $(this).data("divContent"));
	});
	inputObj.focus(function() {
				oldValue = null;
				keyUpFun(this);
	});
		

}};


