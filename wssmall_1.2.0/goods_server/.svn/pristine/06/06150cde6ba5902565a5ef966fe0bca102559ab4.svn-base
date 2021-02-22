var MemberPrice={
	priceBox:undefined,//当前会员价格div容器，规则为“会员价操作按钮”的class=member_price_box 下一个 节点
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"memberpricedlg",title:"编辑会员价格",width:'500px'});
		$("a[name='lvPriceCgf']").click(function(){
			self.bindMbPricBtnEvent($(this));
		});
		$("a.delprod").click(function(){
			if(confirm("确定删除此规格吗？删除后不可恢复"))
				$(this).parent().parent().remove();
		})
	},
	bindMbPricBtnEvent:function(link){
		var self = this;
		self.priceBox= $(link).next(".member_price_box"); //当前的会员价box
		var productid = $(link).attr("pid");
		var goodsid = $("#goods_id").val();
		var price = $(link).siblings(".ipt_new").val(); // 获取价格，规则为当前操作按钮的
		if( parseFloat(price)!= price) {
			alert("价格必须是数字");
			return false;
		}else{
			Eop.Dialog.open("memberpricedlg");
			var url = 'memberPrice!listMemberPriceN.do?price='+price+'&ajax=yes&goodsid='+goodsid+"&productid="+productid;
			$("#memberpricedlg").load(url,function(){
				self.initDlg();
			});
		}
	}
	,
	//初始化弹出对话框，由pricebox计算lvprice
	initDlg:function(){
		var self = this;
		$("#mbPriceBtn").click(function(){
			self.createPriceItems();
		});
	},
	//由对话框的input 创建会员价格表单项
	createPriceItems:function(){
		var member_price_box = this.priceBox;
		member_price_box.empty();
		var index =  member_price_box.attr("index");
		var haveSpec = $('#haveSpec1111111').val() ;
		$("#levelPriceEditor>tbody>tr").each(function(){
			var tr = $(this);
			var lvid = tr.find(".lvid").val(); 
			var lvPrice = tr.find(".ipt_new").val(); 
			//如果开启规格，货品生成会员价时要按顺序指定name_i，为了后台读取每个货品的会员价方便
			//没开启规格，生成此商品的会员价不需要指index，即name直接为lvid,lvPric即可
			if(index || haveSpec == '1'){
				member_price_box.append("<input type='hidden' class='lvid' name='lvid_"+index+"' value='"+lvid+"' />");
				member_price_box.append("<input type='hidden' class='ipt_new' name='lvPrice_"+index+"' value='"+lvPrice+"' lvid='"+lvid+"'/>");
			}else{
				member_price_box.append("<input type='hidden' class='lvid' name='lvid' value='"+lvid+"' />");
				member_price_box.append("<input type='hidden' class='ipt_new' name='lvPrice' value='"+lvPrice+"' lvid='"+lvid+"'/>");
			}
		});
		Eop.Dialog.close("memberpricedlg");
	}
};
