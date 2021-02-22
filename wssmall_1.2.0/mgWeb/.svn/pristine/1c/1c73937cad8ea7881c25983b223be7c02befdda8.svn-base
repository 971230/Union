var LimitBuy={
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"columnInfo_dlg",modal:false,title:"添加相关栏目", width:"600px"});
		function  appendColumn(columnList){
			self.appendColumn(columnList);
		}
		
		$("#addColumnBtn").click(function(){
			var template_id = $("#template_id").val();
			ColumnSelector.open("columnInfo_dlg",appendColumn,template_id);
		});	
		$("#columnstable .delete").click(function(){
			self.removeColumn($(this));
		});
		
	},
	appendColumn:function(columnList){
		
		for(i in columnList){
			var column = columnList[i];
			var tr = $("#columnstable").find("tr[id='"+column.column_id+"']");
			if(tr.length == 0){
				var html = $( $("#temp_table").html() );
				html.find(".columnTitle").html(column.title);
				html.find(".columnType").html(column.type);
				html.find("tr").attr("id",column.column_id);
				html.find("input[name='columnid']").attr("value",column.column_id);
				html.find(".delete").click(function(){
					$(this).parents("tr").remove();
				});
				$("#columnstable").append(html);
			}
			
		}
	 
	},
	removeColumn:function(btn){
		btn.parents("tr").remove();
	}

};

$(function(){
	LimitBuy.init();
});