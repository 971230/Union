var load = {
	asSubmit : function(url, params, dataType, callBack) {
		var data = jQuery.param(params);
		dataType = dataType || 'html';
		$.ajax({
					type : "post",
					url : url,
					data : data,
					dataType : dataType,
					success : function(result) {

						if (dataType == "json" && result.result == 1) {
							$.Loading.hide();
						}
						callBack(result); // 回调函数

					}
				});
	}
};

var PurchaseOrder=$.extend({},Eop.Grid,{
    dlg_id:'showPurchaseOrderDlg',
    title:'列表',
	init:function(){
		var self =this;
		$("[app='desktop']").click(function(){
			self.showSupplier();
		});
		
		$("#addPurchaseOrderBaseBtn").click(function(){
			if($("input[name='supplier']").val()==""){
				alert("请选择供应商!!!");
				return;
			}
			
			if($("#buy_count").html()==0){
				alert("请选择添加的商品!!!");
				return;
			}
			
			var flag=false;
			$("input[name='num']").each(function(){
		         if($(this).val()==0){
		         	flag=true;
		         }
		    });
		   
		   if(flag){
		   	 alert("商品数量不能为0!");
		     return;
		   }
			self.addPurchaseOrder();
		});
		
		function appendGoods(goodsList) {
            self.appendGoods(goodsList);
        }

        $("#supplier-find-btn").bind("click", function () {
            GoodsSelector.open("showGoodsDlg", appendGoods);
        });
		
		Eop.Dialog.init({id:'showPurchaseOrderDlg',modal:true,title:this.title,width:'1050px'});
		Eop.Dialog.init({id:'showGoodsDlg',modal:true,title:this.title,width:'850px'});
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
	addPurchaseOrder:function(){
		 var  url = ctx+ "/shop/admin/purchaseOrderAction!add.do?ajax=yes";
			Cmp.ajaxSubmit('addPurchaseFrom', '', url, {}, function(responseText){
				   if (responseText.status == 1) {
							alert(responseText.msg);
					}
					if (responseText.status == 0) {
					       //修改
						    alert(responseText.msg);
						    //$('#editPartnerBaseBtn').attr('disabled',"true");
							//window.location=app_path+'/shop/admin/purchaseOrderAction!purchaseOrderList.do';	
						    window.location.reload();
					}
					$.Loading.hide();	
			},'json');			
	},
	//供货商列表
	showSupplier:function(){
		var self =this;
		var url=app_path+'/shop/admin/purchaseOrderAction!supplierList.do?ajax=yes';
		
	    self.toUrlOpen(url);
	},
	toUrlOpen:function(url){
	   Cmp.excute(this.dlg_id,url,{},this.onAjaxCallBack);
	   Eop.Dialog.open(this.dlg_id);
	},
	onAjaxCallBack:function(){//ajax回调函数
	    
		$('input.dateinput').datepicker();
	},
	page_close:function(){
	     Eop.Dialog.close(this.dlg_id);
	}
	
});
    
$(function () {
	$("#addPurchaseFrom").validate();
	
	
	PurchaseOrder.init();
	var noData='<tr>'+
            '<td colspan="8" style="padding:0;"><div class="note" style="margin:0;"> 暂无货品信息 </div></td>'+
            '</tr>';
   	$('#dataNode').html(noData);
   	
	
	$("#purchase-delall-btn").bind('click', function () {
   		$("#dataNode").empty();
   		$("#dataNode").html("<td colspan='8' style='padding: 0;'><div class='note' style='margin: 0;'>暂无货品信息	</div></td>");
	})
	
	$("#pru_order_name").blur(function() {
		var pru_order_name = $.trim($('#pru_order_name').val());
		url = ctx
				+ "/shop/admin/purchaseOrderAction!isExits.do?pru_order_name="
				+ pru_order_name + "&ajax=yes";
		if (pru_order_name.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#pru_order_name_message").html(responseText.message);
						$('#pru_order_name').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#pru_order_name_message").html(responseText.message);
					}
				});
	});
});

