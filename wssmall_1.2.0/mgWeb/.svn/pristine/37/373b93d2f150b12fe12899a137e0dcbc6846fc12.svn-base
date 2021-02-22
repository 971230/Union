

/**
 * 补充下拉框属性
 */
(function($){
$.extend($.fn.combobox, {
	oldParseOptions:$.fn.combobox.parseOptions,
	parseOptions:function(element){
		var t=$(element);
		return $.extend({},$.fn.combobox.oldParseOptions(element),
			{code:t.attr("code"),
			valueField:t.attr("valueField")!=null?t.attr("valueField"):"id",
			textField:t.attr("textField")!=null?t.attr("textField"):"text"});
	}
});

/**
 * 扩展datagrid.columns，增加code属性，翻译值
 */
$.extend($.fn.datagrid.defaults.view, {
	onBeforeRender:function(target, rows){
		var opts = $.data(target, 'datagrid').options;
		var columns = opts.columns;
		
		for ( var i = 0; i < columns.length; i++) {
			for ( var j = 0; j < columns[i].length; j++) {
				if (columns[i][j].code && !columns[i][j].formatter) {
					columns[i][j].formatter = function (value,rowData,rowIndex){
						var valueName = '';
						var column = this;
						if (!column.codeList) {
							var checkCallBack = function(reply) {
								data = reply.result;
								column.codeList = data;
							}
							Service.sync('sys', 'getStaticData',{code:column.code}, checkCallBack);
						}
						
						//遍历值
						for ( var k = 0; column.codeList && k < column.codeList.length;k++) {
							if(column.codeList[k].id==value){
								valueName = column.codeList[k].text;
								break;
							}
						}
						
						return valueName;
					};
				}
			}
		}
	}
});

/**
 * datagrid默认值
 */
$.extend($.fn.datagrid.defaults,{
	fit:true
})

/**
 * 表单对象扩展方法
 */
$.extend($.fn.form.methods, {
	/**
	 * 取得表单数据
	 * @param jq
	 * @returns 表单控件的JSON数据
	 */
	getData:function(jq){
		var data = {};
		$("input,select,textarea",jq).each(function(){
    		if(this.name)
    			data[this.name] = this.value;
    	});
		return data;
	},
	/**
	 * 将表单置为不可编辑
	 * @param jq
	 */
	disable:function(jq){
		$("label",jq).attr("disabled",true);
		$(".easyui-validatebox",jq).validatebox("disable");
		$(".easyui-numberbox",jq).numberbox("disable");
		$(".easyui-datebox",jq).datebox("disable");
		$(".easyui-combobox",jq).combobox("disable");
		$(".easyui-combotree",jq).combotree("disable");
	},
	/**
	 * 将表单置为可编辑
	 * @param jq
	 */
	enable:function(jq){
		$("label",jq).removeAttr("disabled");
		$(".easyui-validatebox",jq).validatebox("enable");
		$(".easyui-numberbox",jq).numberbox("enable");
		$(".easyui-datebox",jq).datebox("enable");
		$(".easyui-combobox",jq).combobox("enable");
		$(".easyui-combotree",jq).combotree("enable");
	}
});

/**
 * 扩展validatebox的是否可编辑
 */
$.extend($.fn.validatebox.methods, {
	/**
	 * 设置是否可编辑
	 * @param jq
	 * @param disabled
	 */
	setDisable:function(jq,disabled){
		var opt=$.data(jq,"validatebox").options;
		if(disabled){
			opt.disabled=true;
			$(jq).attr("disabled",true);
		}else{
			opt.disabled=false;
			$(jq).removeAttr("disabled");
		}
	},
		
	/**
	 * 将validatebox置为不可编辑
	 * @param jq
	 */
	disable:function(jq){
		jq.each(function(){
			$.fn.validatebox.methods.setDisable(this,true);
		})
	},
	/**
	 * 将validatebox置为可编辑
	 * @param jq
	 */
	enable:function(jq){
		jq.each(function(){
			$.fn.validatebox.methods.setDisable(this,false);
		})
	}
});



/**
 * 增加显示前方法，用于异步加载
 */
$.extend($.fn.menu.defaults,{
    onBeforeShow:function(jq){
		
	}
});

$.extend($.fn.menubutton.methods,{
	oldEnable:$.fn.menu.methods.enable,
	enable:function(jq){
		return $.fn.menu.methods.enable(jq);
	}
});

/**
 * 改造show方法
 */
$.extend($.fn.menu.methods, {
	oldShow:$.fn.menu.methods.show,
	show:function(jq,pos){
		$.data(jq[0],"menu").options.onBeforeShow(jq);
		return $.fn.menu.methods.oldShow(jq,pos);
	}
});

$.extend($.fn.menu.methods, {
    appendChild : function (jq, param) {
        return jq.each(function () {
            var parent = $(this).menu("findItem", param.parentText);
            
            if (!parent.target.submenu) {
                var submenu = $("<div/>").width($(this).width()).menu();
                parent.target.submenu = submenu;
                $("<div class=\"menu-rightarrow\"></div>").appendTo($(parent.target).parent());
            }
            
            param = $.extend({},param,{parent:parent.target});
            $(this).menu("appendItem", param);
        });
    }
});

})(jQuery);