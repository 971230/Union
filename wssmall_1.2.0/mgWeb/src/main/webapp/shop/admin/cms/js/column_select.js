//栏目选择器

var ColumnSelector={
	conid:undefined,
	init:function(conid,onConfirm,template_id)	{
		this.conid = conid;
		var self  = this;
		$("#searchBtn").click(function(){
			self.search(onConfirm,template_id);
		});
		
		$("#toggleChk").click(function(){
			$("input[name=column_id]").attr("checked",this.checked);			
		});

		
		$("#"+conid+" .submitBtn").click(function(){
			self.getColumnList(onConfirm);
			Eop.Dialog.close(conid);
		});
		
 
		 
	},
	search:function(onConfirm,template_id){
		var self = this;
		var options = {
				url :ctx+"/shop/admin/cms/template!columnRelateList.do?ajax=yes&template.template_id="+template_id+"&onclickType=2",
				type : "post",
				dataType : 'html',
				success : function(result) {				
					$("#"+self.conid).empty().append(result);
					self.init(self.conid,onConfirm,template_id);
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
		$("#columnsserchform").ajaxSubmit(options);		
	},
	getColumnList:function(callback){
 
		var columns = new Array();
		$("#columnslist").find("input[name=columnid][checked]").each(function(){
			var column = new Object();
			column.column_id=$(this).val();
			var c_title = $(this).parent().parent().find("input[id=c_title]").val();
			var c_type = $(this).parent().parent().find("input[id=c_type]").val();
			column.title = c_title;
			column.type = c_type;
			columns.push(column);
		});
		callback(columns);
	},
	open:function(conid,onConfirm,template_id){
		var self= this;
		$("#"+conid).load(ctx+'/shop/admin/cms/template!columnRelateList.do?ajax=yes&template.template_id='+template_id+'&onclickType=2',function(){
			self.init(conid,onConfirm,template_id);
		});
		Eop.Dialog.open(conid);	
		
	}
};
