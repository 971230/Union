/**
 * dwrloader - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2012 stworthy [ stworthy@gmail.com ] 
 * 
 */

(function($){
	/**
	 * get default json loader
	 */
	function getJsonLoader(pluginName){
		return function(param, success, error){
			var opts = $(this)[pluginName]('options');
			if (!opts.url) return false;
			$.ajax({
				type: opts.method,
				url: opts.url,
				data: param,
				dataType: 'json',
				success: function(data){
					success(data);
				},
				error: function(){
					error.apply(this, arguments);
				}
			});
		}
	}
	
	/**
	 * get dwr data loader
	 */
	function getDwrLoader(pluginName){
		return function(param, success, error){
			var opts = $(this)[pluginName]('options');
			if (!opts.url) return false;
			
			var callBack = function(reply) {
				var data = reply.result;
				if(!data){
					$.messager.alert("提醒信息", "执行返回值为空！");
					return;
				}
				if(opts.onBeforeSuccess)
					opts.onBeforeSuccess(data);
				success(data);
			};
			//var url = opts.url + "";
			var post = opts.url.indexOf(".");
			var serviceName = opts.url.substring(0,post);
			var methodName = opts.url.substring( post + 1 ,opts.url.length);
			if(opts._param){
				$.extend(param, opts._param);
			}
			Service.sync(serviceName, methodName,param, callBack);
		}
	}
	function getDwrCommboxLoader(pluginName){
		return function(param, success, error){
			var opts = $(this)[pluginName]('options');
			var code = opts.code;
			//有ATTRCODE则默认取ATTRCODE
			if (code){
				var callBack = function(reply) {
					var data = reply.result;
					if(opts.onBeforeSuccess)
						opts.onBeforeSuccess(data);
					success(data);
				};
				//alert("loader:::::"+code)
				Service.sync('sys', 'getStaticData',{code:code}, callBack);
			}else {
				return false;
			}
			
		}
	}
	
	$.fn.combobox.defaults.loader = getDwrCommboxLoader('combobox');
	$.fn.tree.defaults.loader = getDwrLoader('tree');
	$.fn.treegrid.defaults.loader = getDwrLoader('treegrid');
	$.fn.datagrid.defaults.loader = getDwrLoader('datagrid');
//	$.fn.combobox.defaults.onBeforeSuccess = onBeforeSuccess;
//	$.fn.tree.defaults.onBeforeSuccess = onBeforeSuccess;
//	$.fn.treegrid.defaults.onBeforeSuccess = onBeforeSuccess;
//	$.fn.datagrid.defaults.onBeforeSuccess = onBeforeSuccess;
})(jQuery);
