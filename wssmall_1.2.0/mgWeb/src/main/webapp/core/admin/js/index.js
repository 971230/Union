var IndexPanel = {

	init : function() {

		this.message();
		this.partner();
		this.supplier();
		this.assess();
		this.newOrder();
		this.orderRender();
		this.adv();
		this.flash();
		this.businessOverviewGoods();
		this.waitTransGoods();
		
	},
	create_link:function(a_type,a_id,count,url,pid,pname){
	  var self = this;
	  if(count>0){
	  //class='bulefont'
		var ahtml =  "<a  a_type='"+a_type+"' pid='"+pid+"' pname='"+pname+"' href='"+app_path+url+"' a_id='"+a_id+"' style='cursor:hand;'>"+pname+"<span>("+count+")</span></a>";
		return ahtml;
		}
	   
	},
	
	bind_link:function(a_type){
	 $("a[a_type='"+a_type+"']").attr("style","color:#00469d;")
	 $("a[a_type='"+a_type+"'] span").attr("style","color:#cc0000;");

		$("a[a_type='"+a_type+"']").bind("click",function(){
		
			try{window.parent.BackendUi.loadLeftMenuByPid(window.parent.$("#desktop"),$(this).attr("pid"),$(this).attr("pname"));}catch(e){}
			window.parent.Ztp.AUI.load($(this));
			return false;
		})
	},
	
	// 待审核订单
   orderRender : function() {
		var self = this;
		var url ="userCenter!OrderCenter.do?ajax=yes";
		Cmp.excute('', url, {}, self.orderRenderJsonBack, 'json');
	},
	orderRenderJsonBack : function(orderCount) { // json回调函数
		var a_type ='order_a';
		if (orderCount.result == 1) {
		    $("#wait_pay_order").html(IndexPanel.create_link(a_type,'wait_pay_order',orderCount.wait_pay_order,'/shop/admin/ordern!listn.do?state=2&action=wait_pay','20','待支付订单'));
			$("#wait_deliverGoods_order").html(IndexPanel.create_link(a_type,'wait_deliverGoods_order',orderCount.wait_deliverGoods_order,'/shop/admin/ordern!listn.do?state=4&action=wait_ship','20','待发货订单'));
		    $("#wait_handle_order").html(IndexPanel.create_link(a_type,'wait_handle_order',orderCount.wait_handle_order,'/shop/admin/ordern!listn.do?state=3&action=wait_accept','20','待备货订单'));
		    $("#wait_confirm_ship").html(IndexPanel.create_link(a_type,'wait_confirm_ship',orderCount.wait_confirm_ship,'/shop/admin/ordern!listn.do?state=5&action=wait_confir_ship','20','待确认收货订单'));
			$("#wait_returned_ship").html(IndexPanel.create_link(a_type,'wait_returned_ship',orderCount.wait_returned_ship,'/shop/admin/ordern!listn.do?state=4&action=wait_refund_ship','20','待退货订单'));
		    $("#wait_refund_ship").html(IndexPanel.create_link(a_type,'wait_refund_ship',orderCount.wait_refund_ship,'/shop/admin/ordern!listn.do?action=refund','20','待退费订单'));
		    $("#wait_yuyue_order").html(IndexPanel.create_link(a_type,'wait_yuyue_order',1,'/shop/admin/ordern!listn.do?state=10&action=wait_yuyue','20','待预约单'));
				IndexPanel.bind_link(a_type);
		}
	},
	
	
	//消息
	 message : function() {
		var self = this;
		var url = app_path + "/shop/admin/messageAction!messageCount.do?ajax=yes";
		Cmp.excute('', url, {}, self.messageJsonBack, 'json');
		
	},
		messageJsonBack : function(messageCount) { // json回调函数
		
		var a_type ='message_a';
		if (messageCount.result == 1) {
			$("#wait_message").html(IndexPanel.create_link(a_type,'wait_message',messageCount.wait_message,'/shop/admin/message/messageAction!noReadMsg.do?type=0','111','待回复消息'));
			$("#message_count").html(IndexPanel.create_link(a_type,'message_count',messageCount.message_count,'/shop/admin/message/messageAction!listById.do?num=1&type=0&reciverState=3','111','消息总数'));
		    IndexPanel.bind_link(a_type);
		}
	},


	 /*评价*/
	 assess:function(){
	 var url = "userCenter!assess.do?ajax=yes";
	   Cmp.excute('', url, {},IndexPanel.assessJsonback, 'json');
	 },
	 assessJsonback:function(assessCount){
	   var a_type ='assess';
		if (assessCount.result == 1) {
		$("#wait_assess").html(IndexPanel.create_link(a_type,'wait_assess',assessCount.wait_assess,'/shop/admin/comments!bglist.do?object_type=discuss','1','待回复评价'));
	    $("#wait_question").html(IndexPanel.create_link(a_type,'wait_question',assessCount.wait_question,'/shop/admin/comments!bglist.do?object_type=ask','1','待回复咨询'));
		
        IndexPanel.bind_link(a_type);
		}
	 },
	 /*待处理事物--商品*/
	 waitTransGoods:function(){
	      var url = "userCenter!waitTransGoods.do?ajax=yes";
	       Cmp.excute('', url, {}, IndexPanel.waitTransGoodsJsonBack, 'json'); 
	 },
	 waitTransGoodsJsonBack:function(waitTransGoodsCount){
	      var a_type ='waitTransGoods';
		if (waitTransGoodsCount.result == 1) {
		$("#wait_finalist_goods").html(IndexPanel.create_link(a_type,'wait_finalist_goods',waitTransGoodsCount.wait_assess,'/shop/admin/goods!list_audit.do','1','待入围审核商品'));
	    $("#wait_change_goods").html(IndexPanel.create_link(a_type,'wait_change_goods',waitTransGoodsCount.wait_change_goods,'/shop/admin/goods!list.do','1','待审核资料变更'));
		
        IndexPanel.bind_link(a_type);
		}
	 },
	 /*小伙伴*/
	 partner:function(){
	   var url = "userCenter!partner.do?ajax=yes";
	   Cmp.excute('', url, {}, IndexPanel.partnerJsonBack, 'json'); 
	 },
	 partnerJsonBack:function(partnerCount){
	  var a_type ='partner';
		if (partnerCount.result == 1) {
		$("#wait_audit_supplier").html(IndexPanel.create_link(a_type,'wait_audit_supplier',partnerCount.wait_audit_supplier,'/shop/admin/supplier!list.do?supplier_state=0&supplier_type=1','1','待审核供货商'));
	    $("#wait_audit_agency").html(IndexPanel.create_link(a_type,'wait_audit_agency',partnerCount.wait_audit_agency,'/shop/admin/supplier!list.do?supplier_state=0&supplier_type=2','1','待审核经销商'));
		
        IndexPanel.bind_link(a_type);
		}
	 },
	 /*业务概况---订单*/
	 newOrder:function(){
	      var url = "userCenter!newOrder.do?ajax=yes";
	      Cmp.excute('', url, {}, IndexPanel.newOrderJsonBack, 'json'); 
	  },
	 newOrderJsonBack:function(newOrderCount){
	    var a_type ='new_order';
	    if (newOrderCount.result == 1) {
		$("#yestoday_order").html(IndexPanel.create_link(a_type,'yestoday_order',newOrderCount.yestoday_order,'/shop/admin/ordern!listn.do?action=all','1','昨日新增订单'));
	    $("#today_order").html(IndexPanel.create_link(a_type,'today_order',newOrderCount.today_order,'/shop/admin/ordern!listn.do?action=all','1','今日新增订单'));
		
        IndexPanel.bind_link(a_type);
		}
	 },
	 /*供销商*/
	 supplier:function(){
	    var url = "userCenter!supplier.do?ajax=yes";
	    Cmp.excute('', url, {}, IndexPanel.supplierJsonBack, 'json'); 
	 },
	 supplierJsonBack:function(supplierCount){
	    var a_type ='supplier';
	    
	    if (supplierCount.result == 1) {
		$("#cooperation_supplier").html(IndexPanel.create_link(a_type,'cooperation_supplier',supplierCount.cooperation_supplier,'/shop/admin/supplier!list.do?supplier_state=1&supplier_type=1','1','合作中的供货商'));
	    $("#end_supplier").html(IndexPanel.create_link(a_type,'end_supplier',supplierCount.end_supplier,'/shop/admin/supplier!list.do?supplier_state=2&supplier_type=1','1','今已终止供货商'));
		$("#today_newSupplier").html(IndexPanel.create_link(a_type,'today_newSupplier',supplierCount.today_newSupplier,'/shop/admin/supplier!list.do?supplier_state=0&supplier_type=1&date_type=1','1','今日新增供货商'));
	    $("#yestoday_newSupplier").html(IndexPanel.create_link(a_type,'yestoday_newSupplier',supplierCount.yestoday_newSupplier,'/shop/admin/supplier!list.do?supplier_state=0&supplier_type=1&date_type=2','1','昨日新增供货商'));
		$("#today_newAgency").html(IndexPanel.create_link(a_type,'today_newAgency',supplierCount.today_newAgency,'/shop/admin/supplier!list.do?supplier_state=0&supplier_type=2&date_type=1','1','今日新增经销商'));
	    $("#yestoday_newAgency").html(IndexPanel.create_link(a_type,'yestoday_newAgency',supplierCount.yestoday_newAgency,'/shop/admin/supplier!list.do?supplier_state=0&supplier_type=2&date_type=2','1','昨日新增经销商'));
		
        IndexPanel.bind_link(a_type);
		}
	 },
	 /*业务概况---商品*/
	 businessOverviewGoods:function(){
	    var url = "userCenter!businessOverviewGoods.do?ajax=yes";
	     Cmp.excute('', url, {}, IndexPanel.businessOverviewGoodsJsonBack, 'json'); 
	 },
	 businessOverviewGoodsJsonBack:function(GoodsCount){
	
	    var a_type ='new_order';
	    if (GoodsCount.result == 1) {
		$("#grounding_goods").html(IndexPanel.create_link(a_type,'grounding_goods',GoodsCount.grounding_goods,'/shop/admin/goods!list.do?market_enable=1','1','上架商品'));
	    $("#undercarriage_goods").html(IndexPanel.create_link(a_type,'undercarriage_goods',GoodsCount.undercarriage_goods,'/shop/admin/goods!list.do?market_enable=0','1','已下架商品'));
		$("#outof_register").html(IndexPanel.create_link(a_type,'outof_register',GoodsCount.outof_register,'/shop/admin/goods!list.do','1','缺货登记'));
		
        IndexPanel.bind_link(a_type);
		}
	 },
	 adv:function(){
	     var url = "userCenter!adv.do?ajax=yes";
	     Cmp.excute('', url, {}, IndexPanel.advJsonBack, 'json')
	 },
	 advJsonBack:function(advCount){
	     var a_type ='adv_type';
	    if (advCount.result == 1) {
		$("#wait_audit_adv").html(IndexPanel.create_link(a_type,'wait_audit_adv',advCount.wait_audit_adv,'/core/admin/adv!list.do?state=0','1','待审核广告'));
	    $("#adv_Count").html(IndexPanel.create_link(a_type,'adv_Count',advCount.adv_Count,'/core/admin/adv!list.do','1','广告总数'));
		
        IndexPanel.bind_link(a_type);
		}
	 },
	 /*快讯**/
	 flash:function(){
	     var url = "userCenter!flash.do?ajax=yes";
	     Cmp.excute('', url, {}, IndexPanel.flashJsonBack, 'json')
	 },
	 flashJsonBack:function(flashCount){
	  var a_type ='flash_type';
	    if (flashCount.result == 1) {
		$("#wait_audit_flash").html(IndexPanel.create_link(a_type,'wait_audit_flash',flashCount.wait_audit_adv,'/core/admin/news!list.do?state=0','1','待审核快讯'));
	    $("#flash_count").html(IndexPanel.create_link(a_type,'flash_count',flashCount.adv_Count,'/core/admin/news!list.do','1','快讯总数'));
		  IndexPanel.bind_link(a_type);
		}
	 }
	
	
};

$(function() {

	
		IndexPanel.init();
		
		/*$('.showGoodsMsg').click(function(){
			var goodsid=$(this).attr("goods_id");
			window.location=ctx+"/shop/admin/goods!list_apply.do";
		});*/
});
