
/**
仓库
*/
var purchaseOrderList=$.extend({},Eop.Grid,{
    dlg_id:'showDlg',
    title:'添加订单',
	init:function(){
		var self =this;
		
		//添加订单
		$("#addPurorderOrederBtn").click(function(){
			var create_type=$("[name='create_type']").val();
			var store_action_type=$("[name='store_action_type']").val();
			var audit_status=$("[name='audit_status']").val();
			var url='purchaseOrderAction!addPurchaseOrder.do?ajax=yes&create_type='+create_type+"&store_action_type="+store_action_type+"&audit_status="+audit_status;
	    	self.toUrlOpen(url,"showDlg");
		});
		
		$(".auditName").click(function(){
			
		});
		
		Eop.Dialog.init({id:'showDlg',modal:false,title:this.title,width:'850px'});
	},
	deletePost1:function(url,msg){
		var self =this;
		url=url.indexOf('?')>=0?url+"&":url+"?";
		url+="ajax=yes";
		var options = {
				url : url,
				type : "POST",
				dataType : 'json',
				success : function(result) {	
					$.Loading.hide();
					if(result.result==0){
						self.deleteRows();
						if(msg)
							alert(msg);
					}else{
						alert(result.message);
					}
				},
				error : function(e) {
					$.Loading.hide();
					alert("出现错误 ，请重试");
				}
			};

			$('#gridform').ajaxSubmit(options);
	},
	toUrlOpen:function(url,dlg_id){
	   Cmp.excute(dlg_id,url,{},this.onAjaxCallBack);
	   Eop.Dialog.open(dlg_id);
	},
	onAjaxCallBack:function(){//ajax回调函数
		$('input.dateinput').datepicker();
	},
	page_close:function(){
	     Eop.Dialog.close(this.dlg_id);
	}
	
});

var select_order_id="";
$(function(){
	purchaseOrderList.init();
	$(".grid tbody tr").bind("click",function(){
		var order_id = $(this).find("td span[name='id']").html();
		var store_action_type = $("input[name='store_action_type']").val();
		var pru_order_id = $(this).find("input[name='pru_order_id']").val();
		if(order_id==select_order_id){
			$("#iframe_tr").hide();
			select_order_id = "";
			return ;
		}
		select_order_id = order_id;
		$("#iframe_tr").show().insertAfter($(this)).find("iframe").attr("src","purchaseOrderAction!detail.do?orderId="+order_id+"&pru_order_id="+pru_order_id+"&store_action_type="+store_action_type);
	});
});