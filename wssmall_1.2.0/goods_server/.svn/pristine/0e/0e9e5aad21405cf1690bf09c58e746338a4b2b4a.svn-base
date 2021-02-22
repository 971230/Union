var MemberPrice={
	priceBox:undefined,//当前会员价格div容器，规则为“会员价操作按钮”的class=member_price_box 下一个 节点
	init:function(){
		//alert(999)
		var self = this;
		Eop.Dialog.init({id:"memberpricedlg",title:"编辑会员价格",width:'500px'});
		self.bindMbPricBtnEvent();
	},
	bindMbPricBtnEvent:function(){
		var self = this;
		$(".mempriceBtn").unbind("click").bind("click",function(){
			self.priceBox= $(this).next(".member_price_box"); //当前的会员价box
			var currentBox =  $(this).next(".member_price_box"); 
			var price = $(this).siblings(".price").val(); // 获取价格，规则为当前操作按钮的
			var pid = $(this).attr('pid') ;
			var gid = $(this).attr('gid') ;
			var mempriceBtn = currentBox .prev() ;
			var hasConfirm = mempriceBtn.attr('hasConfirm') ;
			var levelPriceEditor = $('#levelPriceEditor') ;
			
		    var idx = currentBox.attr('index') ;
			if( parseFloat(price)!= price) {
				alert("价格必须是数字");
				return false;
			}else{
				//alert('xxkkkk')
				Eop.Dialog.open("memberpricedlg");
				if('0' == hasConfirm) {
					$("#memberpricedlg").load(app_path+'/shop/admin/memberPrice.do?goodsid='+gid+'&productid='+pid+'&price='+price+'&ajax=yes',function(){
						self.initDlg();
					});
				}else{
					//self.initDlg();
					var map = {} ;
					var lvids = $( "input[name=lvid_"+idx+"]" , currentBox ) ;
					var lvPrices = $( "input[name=lvPrice_"+idx+"]" , currentBox ) ;
					for(var j = 0 ; j<lvids.length ; j++){
						map[lvids[j].value] = lvPrices[j].value ;
					}
					
					var xlvPrices = $( "input[class=lvPrice]" , levelPriceEditor ) ;
					var xlvids = $( "input[class=lvid]" , levelPriceEditor ) ;
					for(var j = 0 ; j<xlvids.length ; j++){
						xlvPrices[j].value =  map[ xlvids[j].value ]  ;
					}
				}
				
			}
		});		
	}
	,
	
	//初始化弹出对话框，由pricebox计算lvprice
	initDlg:function(){
		var self = this;
		var editor = $("#levelPriceEditor");
		//alert(566)
	    /**
		this.priceBox.find(".lvPrice").each(function(){//由.lvPrice找到 价格输入框
			var lvid = $(this).attr("lvid");
			$("#levelPriceEditor input.lvPrice[lvid='"+lvid+"']").val(this.value);
			
		});
		*/
		$("#mbPriceBtn").click(function(){
			self.createPriceItems();
		});
	},
	//由对话框的input 创建会员价格表单项
	createPriceItems:function(){
		var member_price_box = this.priceBox;
		var mempriceBtn = member_price_box.prev() ;
		mempriceBtn.attr('hasConfirm' , '1') ;
		member_price_box.empty();
		var index =  member_price_box.attr("index");
		var haveSpec = $('#haveSpec').val() ;
	  //alert('yy2');
		$("#levelPriceEditor>tbody>tr").each(function(){
			var tr = $(this);
			var lvid = tr.find(".lvid").val(); 
			var lvPrice = tr.find(".lvPrice").val(); 
				
				//如果开启规格，货品生成会员价时要按顺序指定name_i，为了后台读取每个货品的会员价方便
			//没开启规格，生成此商品的会员价不需要指index，即name直接为lvid,lvPric即可
			if(index || haveSpec == '1'){
				member_price_box.append("<input type='hidden' class='lvid' name='lvid_"+index+"' value='"+lvid+"' />");
				member_price_box.append("<input type='hidden' class='lvPrice' name='lvPrice_"+index+"' value='"+lvPrice+"' lvid='"+lvid+"'/>");
			}else{
				member_price_box.append("<input type='hidden' class='lvid' name='lvid' value='"+lvid+"' />");
				member_price_box.append("<input type='hidden' class='lvPrice' name='lvPrice' value='"+lvPrice+"' lvid='"+lvid+"'/>");
			}
			
		});
		
		Eop.Dialog.close("memberpricedlg");
	}
};
$(function(){
	MemberPrice.init();
}); 