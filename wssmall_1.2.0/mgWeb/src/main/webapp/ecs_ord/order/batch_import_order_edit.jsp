<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%--订单批量导入修改页面 --%>
<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		name="manualOrderForm" id="manualOrderForm" enctype="multipart/form-data">
		<table class="form-table">
			<tr>
				<th><label class="text"><span class='red'>*</span>用户号码：</label></th>
				<td style="width:40%"><input type="text" class="ipttxt"
					name="orderBatchImport.acc_nbr" dataType="string" id="acc_nbr"
					required="true" value="${orderBatchImport.acc_nbr }" />
					<input type="hidden" name="orderBatchImport.import_id" id="import_id" value="${orderBatchImport.import_id }">
					<%-- <input type="button" id="selPhoneNumbtn" value="查询" class="graybtn1" /></td>--%>
				</td>
				<th><label class="text"><span class='red'>*</span>用户姓名：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.cust_name"
					dataType="string" id="cust_name" required="true" value="${orderBatchImport.cust_name }"/></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>业务类型：</label></th>
				<td style="width:40%"><input type="text" class="ipttxt"
					name="orderBatchImport.special_busi_type" dataType="string" id="special_busi_type"
					required="true" value="${orderBatchImport.special_busi_type }"/></td>
				<th><label class="text"><span class='red'>*</span>是否实名：</label></th>
				<td style="width:40%">
					<%--<input type="text" class="ipttxt"
					name="orderBatchImport.is_realname" dataType="string" id="is_realname"
					required="true" value="${orderBatchImport.is_realname }" />	 --%>
					<html:selectdict curr_val="${orderBatchImport.is_realname}" style="width:173px"
						name="orderBatchImport.is_realname" id="is_realname" attr_code="DC_BATCH_IS_OR_NO" appen_options='<option value="-1">--未按协议取值--</option>'></html:selectdict>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>地市：</label></th>
				<td>
					<%--<input type="text" class="ipttxt" name="orderBatchImport.order_city_code"
					dataType="string" id="order_city_code" value="${orderBatchImport.order_city_code}" required="true" /> --%>
					<html:selectdict  name="orderBatchImport.order_city_code"  appen_options='<option value="-1">--未按协议取值--</option>'  curr_val="${orderBatchImport.order_city_code}"   attr_code="DC_BATCH_ORDER_CITY_CODE" style="width:173px"></html:selectdict>
				</td>
				<th><label class="text"><span class='red'>*</span>证件类型：</label></th>
				<td>
					<%--<input type="text" class="ipttxt" name="orderBatchImport.certi_type"
					dataType="string" id="certi_type" required="true" value="${orderBatchImport.certi_type }"/> --%>
					<html:selectdict curr_val="${orderBatchImport.certi_type}" style="width:173px"
						name="orderBatchImport.certi_type" id="certi_type" attr_code="DIC_BATCH_CARD_TYPE" appen_options='<option value="-1">--未按协议取值--</option>'></html:selectdict>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>证件号码：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.certi_num"
					dataType="string" id="certi_num" value="${orderBatchImport.certi_num }" required="true" /></td>
				<th><label class="text"><span class='red'>*</span>订单来源：</label></th>
				<td>
					<%-- <input type="text" class="ipttxt" name="orderBatchImport.order_from"
					dataType="string" id="order_from" value="${orderBatchImport.order_from}" required="true" />--%>
					<html:selectdict curr_val="${orderBatchImport.order_from}"
						style="width:173px" name="orderBatchImport.order_from" id="order_from"
						attr_code="DIC_BATCH_ORDER_FROM" appen_options='<option value="-1">--未按协议取值--</option>'></html:selectdict>	
				</td>
			</tr>
			<tr>				
				<th><label class="text"><span class='red'>*</span>新老用户：</label></th>
				<td>
					<%-- <input type="text" class="ipttxt" name="orderBatchImport.is_old"
					dataType="string" id="is_old" value="${orderBatchImport.is_old}" required="true" />	--%>
					<html:selectdict curr_val="${orderBatchImport.is_old}" style="width:173px"
						name="orderBatchImport.is_old" id="is_old" attr_code="DC_BATCH_CUSTOMER_USER_TYPE" appen_options='<option value="-1">--未按协议取值--</option>'></html:selectdict>
				</td>
				<th><label class="text"><span class='red'>*</span>首月：</label></th>
				<td>
					<%-- <input type="text" class="ipttxt" name="orderBatchImport.offer_eff_type"
					dataType="string" id="offer_eff_type" value="${orderBatchImport.offer_eff_type}" required="true" />		--%>
					<html:selectdict curr_val="${orderBatchImport.offer_eff_type}"
						style="width:173px" name="orderBatchImport.offer_eff_type" id="offer_eff_type"
						attr_code="DIC_BATCH_OFFER_EFF_TYPE" appen_options='<option value="-1">--未按协议取值--</option>'></html:selectdict>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>支付方式：</label></th>
				<td>
					<%--<input type="text" class="ipttxt" name="orderBatchImport.pay_method"
					dataType="string" id="pay_method" value="${orderBatchImport.pay_method}" required="true" />	 --%>
					 <html:selectdict curr_val="${orderBatchImport.pay_method}"
						style="width:173px" name="orderBatchImport.pay_method" id="pay_method"
						attr_code="DIC_BATCH_PAY_METHOD" appen_options='<option value="-1">--未按协议取值--</option>'></html:selectdict>
				</td>
				<th><label class="text"><span class='red'>*</span>商品类型：</label></th>
				<td>
				  	<input type="text" class="ipttxt" name="orderBatchImport.prod_offer_type"
					dataType="string"  value="${orderBatchImport.prod_offer_type}" typeid="" id="goodsCatInputDialog" required="true" />	
					<input type="hidden" value="" id="searchGoodsCatidInput"/>
					<input type="button" id="selGoodsType" value="查询" class="graybtn1"/>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>商品编码：</label></th>
				<td>
					<input type="text" class="ipttxt" name="orderBatchImport.prod_offer_code"
					dataType="string" id="prod_offer_code" value="${orderBatchImport.prod_offer_code}" required="true" />
				</td>
				<th><label class="text">导入状态：</label></th>
				<td>
					<c:if test="${orderBatchImport.import_status eq '1' }">导入成功</c:if>
					<c:if test="${orderBatchImport.import_status eq '0' }"><span style="color:red;">导入失败</span></c:if>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>商品名称：</label></th>
				<td><input type="text" class="ipttxt"
					name="orderBatchImport.prod_offer_name" dataType="string" id="goodsInputDialog"
					required="true" value="${orderBatchImport.prod_offer_name }"/>
					<input type="button" id="selGoods" value="查询" class="graybtn1"/>
				</td>
				<th><label class="text"><span class='red'>*</span>商城实收(元)：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.pay_money"
					dataType="float" id="pay_money" value="${orderBatchImport.pay_money }" required="true"/></td>
			</tr>
			<tr>
				<th><label class="text">合约期：</label></th>
				<td>
					<input type="text" class="ipttxt" name="orderBatchImport.contract_month"
						dataType="string"  value="${orderBatchImport.contract_month }" />
				</td>
				<th><label class="text"><span class='red'>*</span>收货人姓名：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.ship_name"
					dataType="string" id="ship_name" value="${orderBatchImport.ship_name }" required="true"/></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>收货地址：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.ship_addr"
					dataType="string" id="ship_addr" value="${orderBatchImport.ship_addr }" required="true"/></td>
				<th><label class="text"><span class='red'>*</span>收货人电话：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.ship_phone"
					dataType="string" id="ship_phone" value="${orderBatchImport.ship_phone }" required="true"/></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>营业款(元)：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.ess_money"
					dataType="float" id="ess_money" value="${orderBatchImport.ess_money }" required="true"/></td>
				<th><label class="text">物流单号：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.logi_no"
					dataType="string" id="logi_no" value="${orderBatchImport.logi_no }"/></td>
			</tr>
			<tr>
				<th><label class="text">发票号码：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.invoice_no"
					dataType="string" id="invoice_no" value="${orderBatchImport.invoice_no }"/></td>
				<th><label class="text">手机串号：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.terminal_num"
					dataType="string" id="terminal_num" value="${orderBatchImport.terminal_num }"/></td>
			</tr>
			<tr>
				<th><label class="text">买家留言：</label></th>
				<td><input type="text" class="ipttxt" name="orderBatchImport.buyer_message"
					dataType="string" id="buyer_message" value="${orderBatchImport.buyer_message }"/></td>
				<th><label class="text">备注：</label></th>
				<td colspan="3"><input style="width: 300px" type="text"
					class="ipttxt" name="orderBatchImport.service_remarks" dataType="string"
					id="service_remarks" value="${orderBatchImport.service_remarks }"/></td>
			</tr>
			<tr>	
				<th><label class="text">失败原因：</label></th>
				<td><textarea class="ipttxt" dataType="string" cols="30" rows="20"
					readonly>${orderBatchImport.fail_reason }</textarea>
				</td>
				<th><label class="text">订单编码：</label></th>
				<td colspan="3"><input style="width: 300px" type="text"
					class="ipttxt" dataType="string"
					id="out_tid" value="${orderBatchImport.out_tid }" readonly />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div style="margin-top: 30px; margin-left: 500px; margin-right: auto;">
						<input type="button" id="saveOrderBtn" value="重新导入" class="graybtn1" />
						<input type="button" id="returnBtn" value="返回" class="graybtn1" />
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="goodsTypeQuickBtn_dialog"></div>
<div id="listGoodsDialog">
	<div id="goodsDialog"></div>
</div>
<script type="text/javascript">
	$(function() {
		//初始化弹出框
		Eop.Dialog.init({id:"goodsTypeQuickBtn_dialog",modal:false,title:"商品分类",width:"1000px"});
		Eop.Dialog.init({id:"listGoodsDialog",modal:true,title:"商品选择",width:"1000px",height:'495px'});
		//重新导入按钮事件绑定
		$("#saveOrderBtn").unbind("click").bind("click",function() {
			var tips = "";
			var realname = $("#is_realname").val();
			var certitype = $("#certi_type").val();
			var ordercitycode = $("#order_city_code").val();
			var isold = $("#is_old").val();
			var offerefftype = $("#offer_eff_type").val();
			var sourcefrom = $("#order_from").val();
			var paymethod = $("#pay_method").val();
			if(realname==""){
				tips += "是否实名不能为空；";
			}
			if(certitype==""){
				tips += "证件类型不能为空；";
			}
			if(ordercitycode==""){
				tips += "地市不能为空；";
			}
			if("" == isold){
				tips += "新老用户不能为空；";
			}
			if("" == offerefftype){
				tips += "首月不能为空；";
			}
			if("" == paymethod){
				tips += "支付方式不能为空；";
			}
			if("" == sourcefrom){
				tips += "订单来源不能为空；";
			}
			if(tips.length>0){
				alert(tips);
				return ;
			}
			$.Loading.show("正在导入...");
			var url = ctx + "/shop/admin/orderBatchImportAction!importOrder.do?ajax=yes";
			Cmp.ajaxSubmit('manualOrderForm', '', url, {}, function callBack(reply) {
				$.Loading.hide();
				var url = "";
				if(reply.result == "1"){
					alert(reply.message);
					url = ctx + "/shop/admin/orderBatchImportAction!batchImportList.do";
				}else{
					alert(reply.message);
					url = ctx + "/shop/admin/orderBatchImportAction!toEditPage.do?orderBatchImport.import_id="+$("#import_id").val();
				}
				window.location.href = url;
			}, 'json');
		});
		
		//返回按钮
		$("#returnBtn").unbind("click").bind("click",function(){
			history.go(-1);
		});
		
		//商品分类选择器
	    $("#selGoodsType").unbind("click").bind("click",function(){
			var url = "cat!showCatListEcs.do?ajax=yes&type=goods";
			$("#goodsTypeQuickBtn_dialog").load(url, function (responseText) {
					Eop.Dialog.open("goodsTypeQuickBtn_dialog");
			});
		});
		//商品选择器
	    $("#selGoods").unbind("click").bind("click",function(){
	    	var cat_id = $("#searchGoodsCatidInput").val();
			var url =ctx + "/shop/admin/goods!goodsSelectList.do?ajax=yes&cat_id="+cat_id;
			Eop.Dialog.open("listGoodsDialog");
			$("#goodsDialog").html("loading...");
			$("#goodsDialog").load(url,function(){});
		});
	});

</script>