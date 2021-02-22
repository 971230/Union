<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div ><form action="applyn!queryApplyPage.do?service_type=${service_type}" id="" method="post">
<input type="hidden" id="service_type" value="${service_type}"/>
<div class="searchformDiv">
	<table class="form-table">
		<tbody><tr>
			<th>申请单号：</th>
			<td>
				<input size="15" type="text"  name="apply_id" id="order_apply_id"
								value="${apply_id}"
								class="ipttxt"
								maxlength="60" />  
			</td>		
			<th>订单号：</th>
			<td>		
				<input size="15" type="text"  name="order_id" id="a_order_item_id"
								value="${order_id}"
								class="ipttxt"
								/> 
			</td>
			
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input class="comBtn" type="submit" name="searchBtn" id="" value="搜索" style="margin-right: 5px;">
			</td>
		</tr>
	</tbody></table>
	
</div>
</form>
</div>

<div class="comBtnDiv">
	<a href="javascript:void(0);" id="addApplyBtn" style="margin-right: 10px;" class="graybtn1"><span>添加申请单</span></a>
</div>

<div class="grid">
	<form method="POST">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell sort="order_apply_id">申请单号</grid:cell>
				<grid:cell sort="a_order_item_id">订单号</grid:cell>
				<grid:cell sort="apply_state">状态</grid:cell>
				<grid:cell sort="member_name">会员名称</grid:cell>
				<grid:cell sort="create_time">申请时间</grid:cell>
				<grid:cell >操作</grid:cell>
			</grid:header>
			<grid:body item="orderApply">
				<grid:cell>${orderApply.order_apply_id } </grid:cell>
				<grid:cell>${orderApply.order_id} </grid:cell>
				
				<grid:cell> 
				 <c:if test="${orderApply.apply_state == 0 }">未审核</c:if>
				 <c:if test="${orderApply.apply_state == 1 }">审核通过</c:if>
				 <c:if test="${orderApply.apply_state == 2 }">已拒绝</c:if>
				 <c:if test="${orderApply.apply_state == 3 }">已确认退货</c:if>
				 <c:if test="${orderApply.apply_state == 4 }">已确认退货</c:if>
				 <c:if test="${orderApply.apply_state == 5 }">已确认退费</c:if>
				 <c:if test="${orderApply.apply_state == 6 }">已确认退费</c:if>
				 <c:if test="${orderApply.apply_state == 7 }">已确认换货</c:if>
				 <c:if test="${orderApply.apply_state == 8 }">已确认换货</c:if>
				</grid:cell>
				<grid:cell>${orderApply.memeber_name} </grid:cell>
				<grid:cell>${orderApply.create_time} </grid:cell>
				<grid:cell><a href="javascript:void(0);" name="edit_order_apply" applyid="${orderApply.order_apply_id }" class="showSupplierOrder">编辑/查看</a>&nbsp;&nbsp;
				<c:if test="${orderApply.apply_state==0}">
				   |&nbsp;&nbsp;<a href="javascript:void(0);" class="auditName" name="audit_btn" order_apply_id="${orderApply.order_apply_id }">审批</a>
				</c:if>
				</grid:cell>
				
			</grid:body>
		</grid:grid>
	</form>
</div>

<div id="orderApply_dialog"></div>
<div id="selectOrderDlg"></div>
<div id="selectgoodsDlg"></div>
<div id="auditOrderApplyDlg"></div>
<script type="text/javascript">

	function checkPrice(){
		var checkgoods = $("input[name=itemIdArray]:checked");
		var depreciation_price = $("input[name=depreciation_price]").val();
		var ship_price = $("input[name=ship_price]").val();
		var totalPrice = 0;
		if(checkgoods && checkgoods.length>0){
			$.each(checkgoods,function(idx,item){
				var num = $(item).closest("tr").find("input[name=return_num]").val();
				var cprice = $(item).attr("price");
				totalPrice += num*cprice;
			});
		}
		var pay_price = totalPrice-depreciation_price-ship_price;
		if("${service_type}"==3){
			var exno = $("input[name=ex_num]");
			var exprice = 0;
			if(exno && exno.length>0){
				$.each(exno,function(idx,item){
					exprice += $(item).attr("price")*$(item).val();
				});
				pay_price -= exprice;
			}
		}
		$("#returnTotalPrice").html(totalPrice);
		$("input[name=pay_price]").val(pay_price);
	}
	
	var OrderApply = {
			init:function(){
				var self = this;
				$("#addApplyBtn").bind("click",self.showApplyDialog);
				$("a[name=edit_order_apply]").bind("click",function(){
					var applyid = $(this).attr("applyid");
					self.showApplyDialog("edit",applyid);
				});
				$("a[name=del_ex_btn]").live("click",function(){
					$(this).closest("tr").remove();
					checkPrice();
				});
				
				$("#goodsDataNode input[name=itemIdArray]").live("click",function(){
					if(!this.checked){
						$(this).closest("tr").find("input[name=return_num]").attr("disabled","disabled");
					}else{
						$(this).closest("tr").find("input[name=return_num]").removeAttr("disabled");
					}
					checkPrice();
				});
				
				$("a[name=audit_btn]").bind("click",self.auditApply);
				
			},
			auditApply:function(){
				var applyid = $(this).attr("order_apply_id");
				var url = "applyn!showReturnedDialog.do?ajax=yes&service_type=${service_type}";
				url += "&action=audit&apply_id="+applyid;
				$("#auditOrderApplyDlg").load(url,function(responseText){
					Eop.Dialog.open("auditOrderApplyDlg");
					$("#addApplyOrderBaseBtn").bind("click",OrderApply.addApplySub);
				});
			},
			showApplyDialog:function(action,applyid){
				var url = "applyn!showReturnedDialog.do?ajax=yes&service_type=${service_type}";
				if("edit"==action)url += "&action=edit&apply_id="+applyid;
				$("#orderApply_dialog").load(url,function(responseText){
					Eop.Dialog.open("orderApply_dialog");
					$("#order-find-btn").bind("click",OrderApply.selectOrder);
					$("#goods-find-btn").bind("click",OrderApply.selectGoods);
					$("#addApplyOrderBaseBtn").bind("click",OrderApply.addApplySub);
					$("#addApplyFrom").validate();
				});
			},
			selectOrder:function(isSearchBtn){
				var url = "applyn!selectOrder.do?ajax=yes";
				if(isSearchBtn==1){
					var orderid = $("#s_order__id").val();
					url += "&order_id="+orderid;
				}
				$("#selectOrderDlg").load(url,function(responseText){
					if(isSearchBtn!=1)Eop.Dialog.open("selectOrderDlg");
					$("#searchorderbtn").bind("click",function(){
						OrderApply.selectOrder(1);
					});
					$("#confirmaddorderBtn").bind("click",OrderApply.queryOrderItems);
				});
			},
			selectGoods:function(isSearchBtn){
				var url = "applyn!queryGoods.do?ajax=yes";
				var args = {};
				if(isSearchBtn==1){
					var orderid = $("#s_order__id").val();
					url += "&order_id="+orderid;
					var goodsName = $("#s_goodsName").val();
					args = {goodsName:goodsName};
				}
				$("#selectgoodsDlg").load(url,args,function(responseText){
					if(isSearchBtn!=1)Eop.Dialog.open("selectgoodsDlg");
					$("#goodssearchBtn").bind("click",function(){
						OrderApply.selectGoods(1);
					});
					$("#app_goods_confirm_btn").bind("click",function(){
						var cgs = $("input[name=product_ids]:checked");
						$.each(cgs,function(idx,item){
							var pi = $(item);
							var ptr = '<tr class="selected">'+
								'<td>'+pi.attr("goodsId")+'</td><td visibility="true" class="product-name">'+pi.attr("goodsName")+'</td>'+
								'<td>'+pi.attr("price")+'元</td>'+
								'<td class="count"><input type="text" size="3" value="1" price="'+pi.attr("price")+'" name="ex_num" required="true" dataType="int" onblur="checkPrice();" /></td>'+
								'<td style="text-align: center;"><a href="javascript:void(0);" name="del_ex_btn">删除</a><input type="hidden" name="ex_productArray" value="'+pi.attr("productid")+'" /></td></tr>';
							$("#exgoodsDataNode").append(ptr);
						});
						Eop.Dialog.close("selectgoodsDlg");
						checkPrice();
					});
				});
			},
			queryOrderItems:function(){
				var order_id = $("input[name=select_order_id]:checked").val();
				if(!order_id){
					alert("请选择一个订单");
					return ;
				}
				$.ajax({url : "applyn!queryOrderItems.do?ajax=yes",
					type : "POST",
					data:{order_id:order_id},
					dataType : 'json',
					success : function(data) {
						$("#goodsDataNode").empty();
						$("input[name=order_id]").val(order_id);
						//var totalPrice = 0;
						$.each(data.items,function(idx,item){
							var tr = '<tr class="selected"><td><input type="checkbox" checked="checked" name="itemIdArray" value="'+item.item_id+'" price="'+item.coupon_price+'" /></td><td>'+item.goods_id+'</td>'+
								'<td visibility="true" class="product-name">'+item.name+'</td>'+
								'<td>'+item.num+'</td>'+
								'<td>'+item.ship_num+'</td>'+
								'<td>'+item.coupon_price+'元</td>'+
								'<td class="count"><input type="text" size="3" buynum="'+item.ship_num+'" value="'+item.ship_num+'" name="return_num" required="true" dataType="int" /></td></tr>';
							$("#goodsDataNode").append(tr);
							//totalPrice += item.num*item.coupon_price;
						});
						
						$("#goodsDataNode input[name=return_num]").bind("blur",function(){
							if($(this).val()<1){
								alert("退货数量不能小于1");
								$(this).val(1);
							}else if($(this).val()>$(this).attr("buynum")){
								alert("退货数量不能大于购买数量");
								$(this).val($(this).attr("buynum"));
							}
							checkPrice();
						});
						//$("#returnTotalPrice").html(totalPrice);
						checkPrice();
						Eop.Dialog.close("selectOrderDlg");
						Eop.Dialog.close("auditOrderApplyDlg");
					},error : function(a,b) {
						alert(a+b);
					}
				});
			},
			addApplySub:function(){
				$.Loading.show('正在响应您的请求，请稍侯...');
				var options = {
					type : "post",
					url : "applyn!applyReturnedGoods.do?ajax=yes",
					dataType : "json",
					success : function(result) {
						alert(result.msg);
						if(result.status==0){
							Eop.Dialog.close("orderApply_dialog");
							window.location.reload();
						}
						$.Loading.hide();
					},
					error : function(e,b) {
						$.Loading.hide();
						alert("操作失败，请重试"+b);
					}
				};
				$("#addApplyFrom").ajaxSubmit(options);
			}
	};
	
	$(function(){
		Eop.Dialog.init({id:"orderApply_dialog",modal:true,title:"退/换货申请", height:"600px",width:"900px"});
		Eop.Dialog.init({id:"selectOrderDlg",modal:true,title:"选择订单", height:"500px",width:"800px"});
		Eop.Dialog.init({id:"selectgoodsDlg",modal:true,title:"选择商品", height:"500px",width:"800px"});
		Eop.Dialog.init({id:"auditOrderApplyDlg",modal:true,title:"退/换货审核", height:"600px",width:"900px"});
		OrderApply.init();
	});
</script>