var TestList = {
	init : function(){
		this.confirm();
	},
	confirm : function(){
		$("#save_list_condition").unbind('click').bind('click', function(){
			//拼装选中的数据,数据格式： [{'attr_value':'11111','attr_name':'yiyiyi'},{'attr_value':'22222','attr_name':'erererer'}]
			var data = [];
			var selected = $("div#goods_list input:checked[name='select_goods']");
			if(selected == null || selected.length <= 0){
				alert('请至少选择一条数据');
				return ;
			}
			selected.each(function(){
				var goods_id = $(this).attr('goods_id');
				var goods_name = $(this).attr('goods_name');
				var good = {'attr_value':goods_id, 'attr_name':goods_name};
				data.push(good);
			});
			AttrFactory.renderListData(data);
		});
	}
};
$(function(){
	TestList.init();
});