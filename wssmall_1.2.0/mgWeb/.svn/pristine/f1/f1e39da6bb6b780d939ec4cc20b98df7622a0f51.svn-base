
$(function(){
	ColumnInfo.init();
});

var ColumnInfoSelector={
		conid:undefined,
		init:function(conid,onConfirm) {
			this.conid = conid;
			var self  = this;
		},
		open:function(conid,onConfirm,templateId) {
			var self= this;
			$("#"+conid).load(ctx+'/shop/admin/cms/template!columnRelateList.do?ajax=yes&template.template_id='+templateId,function(){
				self.init(conid,onConfirm);
			});
			Eop.Dialog.open(conid);	
		}
}

var ColumnInfo={
		init:function(){
			var self = this;
			Eop.Dialog.init({id:"columnInfo_dlg",modal:false,title:"模版关联的栏目", width:"600px"});
		},
		
		openDialog:function (templateId){
			$("#columnInfo_dlg").html("");
			ColumnInfoSelector.open("columnInfo_dlg",null,templateId);
		}
}

function openDialog(templateId){
	ColumnInfo.openDialog(templateId);
}