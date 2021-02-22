var WorkFlowList = {
	init : function(){
		this.confirm(); this.query();
	},
	confirm : function(){
		$("#save_list_condition").unbind('click').bind('click', function(){
			//拼装选中的数据,数据格式： [{'attr_value':'11111','attr_name':'yiyiyi'},{'attr_value':'22222','attr_name':'erererer'}]
			var data = [];
			var selected = $("div#flow_list input:checked[name='select_flow']");
			if(selected == null || selected.length <= 0){
				alert('请至少选择一条数据');
				return ;
			}
			selected.each(function(){
				var flow_id = $(this).attr('flow_id');
				var flow_name = $(this).attr('flow_name');
				var flow = {'attr_value':flow_id, 'attr_name':flow_name};
				data.push(flow);
			});
			AttrFactory.renderListData(data);
		});
	},
	query : function(){
		$("#qry_btn").unbind('click').bind('click', function(){
			var url = app_path + "/shop/admin/workFlowAction!getWorkFlowPage.do?ajax=yes";
			var flow_name = $('#work_folw_list_form input[name="flow_name"]').val();
			$("#list_attr_dlg").load(url,{'flow_name':flow_name});
		});
	}
};
$(function(){
	WorkFlowList.init();
});