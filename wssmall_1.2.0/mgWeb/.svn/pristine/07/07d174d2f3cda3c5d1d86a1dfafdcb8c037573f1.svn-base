$(function(){
	dataPraser.init();
});
function checkSubmit() {
	var bool = true;
	return bool;
}
var dataPraser = {
	init : function () {
		$("#dict_type").empty();
		$.ajax({
			type : "post",
			async : false,
			url : "dictMatchLogsAction!qryDictCatalog.do?ajax=yes",
			data : {},
			dataType : "json",
			success : function(data) {
				var data = eval(data);
				if (data != null && data.length > 0) {
					optionStr = "<option value='-1'>----错误类型----</option>";
					$("#dict_type").append(optionStr);
					$.each(data, function(i, cata) {
						optionStr = "<option value='"+cata.catalog_id+"'>"+cata.catalog_name+"</option>";
						$("#dict_type").append(optionStr);
					});
				}
			}
		});
		dataPraser.out_id_click();
		dataPraser.no_handle_click();
		dataPraser.qryBtn();
	},
	no_handle_click:function() {
		Eop.Dialog.init({id:"deal_content",modal:false,title:"处理内容",width:"450px"});
		$("a[name='no_handle']").bind("click", function() {
			var $this =$(this);
			Eop.Dialog.open("deal_content");
			var inst_id = $this.attr("inst_id");
			var url = ctx + "/shop/admin/dictMatchLogsAction!dealContent.do?ajax=yes";
			$("#deal_content").load(url,{"inst_id":inst_id},function(){
				$("#savebtn").bind("click", function() {
					var url0 = ctx+ "/shop/admin/dictMatchLogsAction!saveDealContent.do?ajax=yes";
					Cmp.ajaxSubmit('saveDealContentForm', '', url0, {}, function(responseText) {
						if (responseText.result == 0) {
							alert(responseText.message);
							$("#qryBtn").click();
						}
					}, 'json');
					Eop.Dialog.close("deal_content");
				});
			});
		});
	},
	out_id_click:function() {
		Eop.Dialog.init({id:"detail_content",modal:false,title:"详细信息",width:"750px"});
		$("a[name='out_id']").bind("click", function() {
			var $this =$(this);
			Eop.Dialog.open("detail_content");
			var inst_id = $this.attr("inst_id");
			var url = ctx + "/shop/admin/dictMatchLogsAction!detailContent.do?ajax=yes";
			$("#detail_content").load(url,{"inst_id":inst_id},function(){
				$("#colsediv").bind("click", function() {
					Eop.Dialog.close("detail_content");
				});
			});
		});
	},
	qryBtn:function() {
		$("#qryBtn").bind("click", function() {
			if (checkSubmit() == true) {
				$("#dict_list_f").attr("action",ctx+"/shop/admin/dictMatchLogsAction!qryDataPraserInst.do").submit();
			}
		});
	}
}
