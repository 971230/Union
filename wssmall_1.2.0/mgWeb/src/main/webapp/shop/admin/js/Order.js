var Order=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		$("#delBtn").click(function(){self.doDelete();});
		$("#cleanBtn").click(function(){self.doClean();	});
		$("#revertBtn").click(function(){self.doRevert();});
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		$(".orderFailBtn").each(function(){
			$(this).bind("click",function(event){
				var order_id = $(this).attr("order_id");
				self.failOrder(order_id);
				event.stopPropagation(); 
			});
		});

	},
	failOrder:function(order_id){
		Eop.Dialog.open("order_fail_div");
		var url =ctx + "/shop/admin/ordern!failOrder.do?ajax=yes&orderId="+order_id;
		$("#order_fail_div").load(url,function(){
			Order.failDialogInit();
		}); 
	},
	failDialogInit:function(){
		
	},
	doDelete:function(){
		
		if(!this.checkIdSeled()){
			alert("请选择要删除的订单");
			return ;
		}
	 
		if(!confirm("确认要将这些订单放入回收站吗？")){	
			return ;
		}
		
		$.Loading.show("正在将订单放入回收站...");
		
		this.deletePost("../shop/admin/order!delete.do");
			
	},
	doClean:function(){
		if(!this.checkIdSeled()){
			alert("请选择要删除的订单");
			return ;
		}
	 
		if(!confirm("确认要将这些订单彻底删除吗？删除后将不可恢复")){	
			return ;
		}
		this.deletePost("../shop/admin/order!clean.do");
	},
	
	doRevert:function(){
		if(!this.checkIdSeled()){
			alert("请选择要还原的订单");
			return ;
		}
	 
		this.deletePost("../shop/admin/order!revert.do","选择的订单已被成功还原至订单列表中");		
	}
	
});

$(function(){
	Eop.Dialog.init({id:"order_fail_div",modal:false,title:"定义异常单",width:"450px"});
	Order.init();
});