var tplIndex = {
	init : function(){
		var me = this;
		$("ul[name='model_switch'] li").unbind('click').bind('click', function(){
			var mode = $(this).attr('name');
			if($(this).hasClass('curr'))
				return ;
			$(this).addClass('curr').siblings().removeClass('curr');
			if('oper_model' == mode){
				$('ul#btnView').show();
			}else {
				$('ul#btnView').hide();
			}
			me.changeTree();
		});
	},
	changeTree : function(){
		var mode = $("ul[name='model_switch'] li.curr").attr('name');
		var treeObj = $("div#aimDiv").data('tree_obj');
		treeObj.operChange(mode);
	}
};

$(document).ready(function(){
	tplIndex.init();
});
