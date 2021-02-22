var WorkFlowList = {
	init : function(){
		this.confirm(); this.query();
	},
	confirm : function(){
		$("#search_sure").unbind('click').bind('click', function(){
			//拼装选中的数据,数据格式： [{'attr_value':'11111','attr_name':'yiyiyi'},{'attr_value':'22222','attr_name':'erererer'}]
			var data = [];
			var fact_obj_id = $("input[name=fact_obj_id]").attr("value");
			var fact_attr_id = $("input[name=fact_attr_id]").attr("value");
			var fact_attr_name = $("input[name=fact_attr_name]").attr("value");
			var selected = $("div#search_flow_list input:checked[name='select_flow']");
			if(selected == null || selected.length <= 0) {
				alert('请至少选择一条数据');
				return ;
			}
			selected.each(function(){
				var flow_id = $(this).attr('flow_id');
				var flow_name = $(this).attr('flow_name');
				var flow = {
						'attr_value' : flow_id,
						'attr_name' : flow_name,
						'fact_obj_id' : fact_obj_id,
						'fact_attr_id' : fact_attr_id,
						'fact_attr_name' : fact_attr_name
					};
				data.push(flow);
			});
			FactAttrFactory.renderListData(data);
		});
	},
	query : function(){
		$("#qry_btn").unbind('click').bind('click', function(){
			var url = ctx + "/shop/admin/ruleManager!getFactWorkFlowPage.do?ajax=yes";
			var flow_name = $('#search_work_folw_list_form input[name="flow_name"]').val();
			$("#workflow_list").load(url,{'flow_name':flow_name});
		});
	}
};
$(function(){
	WorkFlowList.init();
});