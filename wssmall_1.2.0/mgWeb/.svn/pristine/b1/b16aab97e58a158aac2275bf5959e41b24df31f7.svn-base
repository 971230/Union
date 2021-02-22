var GoodsN=$.extend({},Eop.Grid,{
	init : function(){
		var self = this;
		$("#delBtn").click(function(){self.doDelete();});
	},
	doSearch : function(){
		
	},
	doDelete : function(){
		var radioChk=$(":radio[name='id'][checked='true']");
		var founder = radioChk.attr("nowUserFounder");
		var goods_id = radioChk.val();
		if(founder != "0" && founder != "1"){
			var author=radioChk.attr("goodsAuthor");
			var nowUserid=radioChk.attr("nowUserid");
			if(author!=nowUserid){
				alert("不能删除其他人员的商品");
				return ;
			}
		}

		if(!this.checkIdSeled()){
			alert("请选择要放入回收站的商品");
			return ;
		}
	 
		if(!confirm("确认要将这些商品放入回收站吗？")){	
			return ;
		}
		
		$.Loading.show("正在将商品放入回收站...");
		
		this.deletePost1(basePath+"goodsN!delete.do?goods_id="+goods_id,'成功放入回收站');
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
						if(msg){
							alert(msg);
							}
						
					}else{
						alert(result.message);
					}
					window.location.reload();
				},
				error : function(e) {
					$.Loading.hide();
					alert("出现错误 ，请重试");
				}
			};
			$('#goodsGridform').ajaxSubmit(options);	
	}

});
var PurchaseOrderN=$.extend({},Eop.Grid,{
    dlg_id:'showPurchaseOrderDlg',
    title:'商家列表',
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

			$('#goodsGridform').ajaxSubmit(options);
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
$(function(){
	GoodsN.init();
	PurchaseOrderN.init();
	//Eop.Dialog.init({id:"auditDialog_log",modal:false,title:"商品处理日志",width:"650px"});
});