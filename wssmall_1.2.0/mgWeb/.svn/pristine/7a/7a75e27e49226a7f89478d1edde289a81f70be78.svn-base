//商品选择器
var GoodsSelector={
	conid:undefined,
	init:function(conid,onConfirm)	{
		this.conid = conid;
		var self  = this;
		$("#goodssearchBtn").click(function(){
			self.search(onConfirm);
		});
		
		$("#toggleChk").click(function(){
			$("input[name=goodsid]").attr("checked",this.checked);			
		});

		
		$("#"+conid+" .submitBtn").click(function(){
			//self.getGoodsList(onConfirm);
			
			
			$("input[name='product_id']").each(function(){
				if(this.checked){
					var product_id= $(this).val();
					var goods_sn=$(this).attr('goodssn');
					var goods_name=$(this).attr('goodsName');
					var price=$(this).attr('price');
					var store=$(this).attr('store');
					var goodsId=$(this).attr('goodsId');
					
					if($("#dataNode").find(".note").length!=0){
			   			   $("#dataNode").empty();
			   		}
					var html ="<tr title='点击选取/反选此条记录' id='product_" + product_id + "' key='" + product_id + "' class='selected'>"
						+"<td><input type='hidden' name='goods_nameArray' class='product_select' value='"+goods_name+"'><input type='hidden' name='product_id' class='product_select' value='"+product_id+"'>"+goods_sn+"</td>"
						+"<td visibility='true' class='product-name'><input type='hidden' name='goodsIds' class='product_select' value='"+goodsId+"'>"+ goods_name+"</td>"
						+"<td><input type='text' size='6' tname='at[_PRIMARY_]' key='num' name='num' value='0'></td>"
						+"<td><input type='text' size='8' value='"+price+"' key='price' name='price' tname='pr[_PRIMARY_]' vtype='number&amp;&amp;required'>元</td>"    
						+"<td class='count'>0.00</td>"
						+"<td><img class='pointer btn-delete-item' key='state' app='desktop'src='images/delecate.gif'></td></tr>";
		            var htmlJq = $(html);
		            htmlJq.appendTo($("#dataNode"));
		           
		          	htmlJq.find("[key='num']").bind("blur", function () {
		                  var num =$(this).val();
		                  var price = $(this).closest("tr").find("[key='price']").val();
		                  
		                  $(this).closest("tr").find(".count").html(num*price);
		                  
		                  var count_num=0;
		                  var buy_amount=0;
		                  $("input[name='num']").each(function(){
								count_num=count_num+parseInt($(this).val());	                  
		                  });
		                  $("#buy_count").html(count_num);
		                  
		                  
		                  $(".count").each(function(){
		                  		buy_amount=buy_amount+parseFloat($(this).text());
		                  });
		                  
		                  $("#buy_amount").html(buy_amount);
		           	})
		           
		          	htmlJq.find("[key='price']").bind("blur", function () {
		                  var num =$(this).val();
		                  var price = $(this).closest("tr").find("[key='num']").val();
		                  
		                  $(this).closest("tr").find(".count").html((num*price).toFixed(2));
		                  
		                  var buy_amount=0;
		                  $(".count").each(function(){
		                  		buy_amount=buy_amount+parseFloat($(this).text());
		                  });
		                  
		                  $("#buy_amount").html(buy_amount);
		          	})
				}				
			}); 
			GoodsSelector.bindEvent();
			Eop.Dialog.close(conid);
		});
		
	},
	bindEvent: function () {
        $("[key='state']").unbind("click");
        $("[key='state']").bind("click", function () {
       		 $(this).parents("tr").remove();
       		 
       		 if($("#dataNode").find("tr").length==0){
       		 	   $("#buy_count").html("0");
       		 	   $("#buy_amount").html("0");
       		 	   $("#dataNode").html("<td colspan='8' style='padding: 0;'><div class='note' style='margin: 0;'>暂无货品信息	</div></td>");
       		 }
        });
   },
	search:function(onConfirm){
		var self = this;
		var options = {
				url :ctx+"/shop/admin/purchaseOrderAction!qryGoods.do?ajax=yes",
				type : "GET",
				dataType : 'html',
				success : function(result) {				
					$("#"+self.conid).empty().append(result);
					self.init(self.conid,onConfirm);
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
		$("#goodsserchform").ajaxSubmit(options);		
	},
	open:function(conid,onConfirm){
		var self= this;
		$("#"+conid).load(ctx+'/shop/admin/purchaseOrderAction!qryGoods.do?ajax=yes&member_lv_id=0',function(){
			self.init(conid,onConfirm);
		});
		Eop.Dialog.open(conid);		
	}
};