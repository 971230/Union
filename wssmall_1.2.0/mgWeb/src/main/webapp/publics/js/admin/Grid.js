var Eop=Eop||{};
Eop.Grid={
	defauts:{
		idChkName:"id", //id复选框name
		toggleChkId:"toggleChk"
	} 
	,
	opation:function(key,value){
		if(typeof(key)=='object'){
			this.defauts=$.extend({},this.defauts,key);
		}else if( typeof(key)=="string" ){
			this.defauts[key]=value;
		}
	}
	,
	deletePost:function(url,msg){
		var self =this;
		url=url.indexOf('?')>=0?url+"&":url+"?";
		url+="ajax=yes";
	//	url=basePath+url;
		var options = {
				url : url,
				type : "POST",
				dataType : 'json',
				success : function(result) {	
					$.Loading.hide();
					if(result.result==0){
						self.deleteRows();
						if(msg)
							alert(msg);
					}else{
						alert(result.message);
					}
				},
				error : function(e) {
					$.Loading.hide();
					alert("出现错误 ，请重试");
				}
			};

			$('form').ajaxSubmit(options);		
	},
	deleteRows:function(){
		$("input[name="+this.defauts.idChkName+"]").each(function(){
			var checkbox= $(this);
			if( checkbox.attr("checked") ){
				 checkbox.parents("tr").remove();
			}
		});		
	}
	,
	/**
	 * 检测id是否有被选中的，如果一个也没有返回假
	 */
	checkIdSeled:function(){
		var r=false;
		$("input[name="+this.defauts.idChkName+"]").each(function(){
			if( $(this).attr("checked") ){
				r=true;
				return ;
			}
		});
		
		return r;
	},
	/**
	 * 切换全选
	 */
	toggleSelected:function(checked){
		$("input[name="+this.defauts.idChkName+"]").attr("checked",checked);
	}
		
};

/**
 * 异步分页jquery插件
 */
(function($) {
    $.fn.gridAjaxPager = function(options) {
    	
    	return this.each(function(){
    		bindEvent($(this));
    	});
    	
    	/**
    	 * 绑定分页事件
    	 */
    	function bindEvent(pager,grid){
    		var grid = pager.parent();
    		 pager.find("li>a").unbind(".click").bind("click",function(){
//				 load($(this).attr("pageno"),grid);
    			 loadForm($(this).attr("pageno"),grid, "test_form");
			 }); 
    		 pager.find("a.selected").unbind("click");
    	}
    	
    	/**
    	 * 点击分页的加载事件
    	 */
    	function load(pageNo,grid){
    		var url = options;
    		url=url+"page="+pageNo;
    		$.ajax({
    			url:url,
    			success:function(html){
    				grid.empty().append( $(html).find(".gridbody").children() );
    				bindEvent(grid.children(".page"));
    			},
    			error:function(){
    				alert("加载页面出错:(");
    			}
    		});
    	}
    	
    	function loadForm(pageNo, grid, formId) {
			var options = {
					data : {page: pageNo},
	    			success:function(html){
	    				grid.empty().append( $(html).find(".gridbody").children() );
	    				bindEvent(grid.children(".page"));
	    			},
	    			error:function(){
	    				alert("加载页面出错:(");
	    			}
			};
			$('#' + formId).ajaxSubmit(options);
    	}
    	
    };
})(jQuery);



/**
 * 异步分页jquery插件 -- http POST only 
 */
(function($) {
    $.fn.gridPostPager = function(options) {
    	
    	return this.each(function(){
    		bindEvent($(this));
    	});
    	
    	/**
    	 * 绑定分页事件
    	 */
    	function bindEvent(pager,grid){
    		var grid = pager.parent();
    		 pager.find("li>a").unbind(".click").bind("click",function(){
    				 loadForm($(this).attr("pageno"),grid);
			 }); 
    		 pager.find("a.selected").unbind("click");
    	}
    	
    	function loadForm(pageNo, grid) {
    		var $form;
    		if (options.formId == "") {
    			window.console && console.log("没有设置formId");
    			$form = guessForm();
    		} else {
    			$form = $("#" + options.formId);
    		}
    		if (options.ajax == "yes") {
    			lordFormByAjax($form, pageNo, grid);
    		} else {
    			simpleQueryForm($form, pageNo);
    		}
    	}
    	
    	function lordFormByAjax($form, pageNo, grid) {
			var s_options = {
					data : {page: pageNo},
	    			success:function(html){
	    				grid.empty().append( $(html).find(".gridbody").children() );
	    				bindEvent(grid.children(".page"));
	    			},
	    			error:function(){
	    				alert("加载页面出错:(");
	    			}
			};
			
			var override_url; 
			if (options.action != "") {
				override_url = options.action;
			} else {
				override_url = options.url; //根据request URL设置提交action，不使用form自己的action，因为可能用js重写了action
			}
			override_url = override_url.replace(/\?\&/, '?');
			override_url = override_url.replace(/\&\&/g, '&');
			window.console && console.log("=== lordFormByAjax form action is ===" + override_url);
			s_options = jQuery.extend( s_options, {url:override_url} );
			
			$form.ajaxSubmit(s_options); 
    	}
    	
    	function simpleQueryForm($form, pageNo) {
    	
			var override_url; 
			if (options.action != "") {
				override_url = options.action;
			} else {
				override_url = options.url;  //根据request URL设置提交action，不使用form自己的action，因为可能用js重写了action
			}
			if(override_url.indexOf("?")>-1) 
				override_url +="&page=" + pageNo; //可能以?&page=1结束
			else
				override_url +="?page=" + pageNo;
			override_url = override_url.replace(/\?\&/, '?');
			window.console && console.log("=== simpleQueryForm form action ===" + override_url);
			$form[0].setAttribute("action", override_url); // 使用DOM操作。在ie使用$("#id").attr("key", "value")会有问题，jQuery阻止了修改属性 有时候出现。
			$form.submit();
    	}
    	
    	function guessForm() {
    		window.console && console.log("======= guess form begin =======");
    		var $forms = $("form");
    		var size = $forms.size(); 
    		window.console && console.log("======= form size =======" + size);
    		if (size == 1) {
    			return $forms;
    		} 
    		if (size == 0) {
    			alert("该页面并没有表单");
    			throw "该页面并没有表单";
    		}
    		window.console && console.log("======= more than one form in the page  =======" + size);
    		var url = options.url;
    		window.console && console.log("======= the request url =======" + url);
    		var regexp = /[a-zA-Z0-9]+[^\/]*\!{0,1}[^\/]*\.do/;
    		var guessAction = url.match(regexp);
    		if (guessAction != null) {
    			guessAction = guessAction.toString();
    		};
    		window.console && console.log("======= guessAction =======" + guessAction);
    		var match_pos = -1;
    		for (var i=0; i<size; i++) {
    			var f = $forms.get(i); 
    			var action = f.getAttribute("action");
    			if (action && action != "") {
    				var f_action = action.match(regexp);
    				if (f_action != null && f_action.toString() == guessAction) {
    					match_pos = i;
    					window.console && console.log("======= one form match the guessAction, the pos is =======" + match_pos);
    					break;
    				}
    			}
    		}
    		window.console && console.log("======= guess form end =======");
    		if (match_pos == -1) {
    			alert("该页面有多个表单，请在grid:grid标签指明formId参数");
    			throw "该页面有多个表单，请在grid:grid标签指明formId参数";
    		}
    		return $($forms.get(match_pos));
    	}
    	
    };
})(jQuery);
