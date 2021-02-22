<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<!-- <h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">订单录入</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3> -->
<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		name="manualOrderForm" id="manualOrderForm" enctype="multipart/form-data">
		<table class="form-table">
			<tr>
				<th><label class="text"><span class='red'>*</span>用户号码：</label></th>
				<td style="width:40%"><input type="text" class="ipttxt"
					name="manualOrder.acc_nbr" dataType="string" id="acc_nbr"
					required="true" value="${manualOrder.acc_nbr }" />
					<input type="hidden" name="manualOrder.source_system" id="source_system" value="${source_system }">
					<input type="button" id="selPhoneNumbtn" value="查询" class="graybtn1" /></td>
				</td>
				<th><label class="text"><span class='red'>*</span>是否实名：</label></th>
				<td style="width:40%"><html:selectdict curr_val="${manualOrder.is_realname}" style="width:173px"
						name="manualOrder.is_realname" id="is_realname" attr_code="DC_IS_OR_NO" appen_options='<option value="-1">--请选择--</option>'></html:selectdict>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>业务类型：</label></th>
				<td style="width:40%"><input type="text" class="ipttxt"
					name="manualOrder.special_busi_type" dataType="string" id="biz_type"
					required="true" value="${manualOrder.special_busi_type }"/></td>
				<th><label class="text"><span class='red'>*</span>用户姓名：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.cust_name"
					dataType="string" id="cust_name" required="true" value="${manualOrder.cust_name }"/></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>证件类型：</label></th>
				<td><html:selectdict curr_val="${manualOrder.certi_type}" style="width:173px"
						name="manualOrder.certi_type" id="certi_type" attr_code="DIC_CARD_TYPE" appen_options='<option value="-1">--请选择--</option>'></html:selectdict>
				</td>
				<th><label class="text"><span class='red'>*</span>证件号码：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.certi_num"
					dataType="string" id="certi_num" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text">当前套餐：</label></th>
				<td><input type="text" class="ipttxt"
					name="manualOrder.old_plan_title" dataType="string" id="plan_title"
					value="${manualOrder.old_plan_title }"/></td>
				<th><label class="text"><span class='red'>*</span>地市：</label></th>
				<td><html:selectdict curr_val="${manualOrder.order_city_code}" style="width:173px"
						name="manualOrder.order_city_code" id="order_city_code" attr_code="DC_CITY_ZJ" appen_options='<option value="-1">--请选择--</option>'></html:selectdict>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>订单来源：</label></th>
				<td><html:selectdict curr_val="${manualOrder.source_from}"
						style="width:173px" name="manualOrder.source_from" id="source_from"
						attr_code="ORDER_FROM" appen_options='<option value="-1">--请选择--</option>'></html:selectdict></td>
				<th><label class="text"><span class='red'>*</span>新老用户：</label></th>
				<td><html:selectdict curr_val="${manualOrder.is_old}" style="width:173px"
						name="manualOrder.is_old" id="is_old" attr_code="DC_CUSTOMER_USER_TYPE" appen_options='<option value="-1">--请选择--</option>'></html:selectdict>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>首月：</label></th>
				<td><html:selectdict curr_val="${manualOrder.offer_eff_type}"
						style="width:173px" name="manualOrder.offer_eff_type" id="offer_eff_type"
						attr_code="DIC_OFFER_EFF_TYPE" appen_options='<option value="-1">--请选择--</option>'></html:selectdict></td>
				<th><label class="text"><span class='red'>*</span>支付方式：</label></th>
				<td><html:selectdict curr_val="${manualOrder.pay_method}"
						style="width:173px" name="manualOrder.pay_method" id="pay_method"
						attr_code="DIC_PAY_METHOD"></html:selectdict></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>商品分类：</label></th>
				<td>
					<input type="text" class="ipttxt" name="manualOrder.prod_offer_type_name" required="true" typeid="" value="${manualOrder.prod_offer_type_name }" id="goodsCatInputDialog" readonly/>
				  	<input type="hidden" value="${manualOrder.prod_offer_type}" name="manualOrder.prod_offer_type" id="searchGoodsCatidInput"/>
				  	<input type="button" id="selGoodsType" value="查询" class="graybtn1"/>
				</td>
				<th><label class="text"><span class='red'>*</span>商品名称：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.prod_offer_name"
					dataType="string" id="goodsInputDialog" required="true" value="${manualOrder.prod_offer_name }" readonly/>
					<input type="hidden" value="${manualOrder.prod_offer_code}" name="manualOrder.prod_offer_code" id="prod_offer_code" />
					<input type="button" id="selGoods" value="查询" class="graybtn1"/>
				</td>
			</tr>
			<tr>
				<th><label class="text">是否副卡：</label></th>
				<td>
					<html:selectdict curr_val="${manualOrder.offer_eff_type}"
						style="width:173px" name="manualOrder.is_attached" id="is_attached"
						attr_code="DC_IS_OR_NO" appen_options='<option value="-1">--请选择--</option>'></html:selectdict>
				</td>
				<th><label class="text">套餐名称：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.plan_title" readonly
					dataType="string" id="planTitleInputDialog" value="${manualOrder.plan_title }"/>
				</td>
			</tr>
			<tr>
				<th><label class="text">合约期：</label></th>
				<td>
					<input type="text" class="ipttxt" name="manualOrder.contract_month"
						dataType="string" id="contract_month" value="${manualOrder.contract_month }" readonly/>
				</td>
				<th><label class="text"><span class='red'>*</span>商城实收(元)：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.pay_money"
					dataType="float" id="pay_money" value="${manualOrder.pay_money }" required="true"/></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>营业款(元)：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.ess_money"
					dataType="float" id="ess_money" value="${manualOrder.ess_money }" required="true"/></td>
				<th><label class="text"><span class='red'>*</span>收货人姓名：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.ship_name"
					dataType="string" id="ship_name" value="${manualOrder.ship_name }" required="true"/></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>收货人电话：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.ship_phone"
					dataType="string" id="ship_phone" value="${manualOrder.ship_phone }" required="true"/></td>
				<th><label class="text"><span class='red'>*</span>收货地址：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.ship_addr"
					dataType="string" id="ship_addr" value="${manualOrder.ship_addr }" required="true"/></td>
			</tr>
			<tr>
				<th><label class="text">物流单号：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.logi_no"
					dataType="string" id="logi_no" value="${manualOrder.logi_no }"/></td>
				<th><label class="text">发票号码：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.invoice_no"
					dataType="string" id="invoice_no" value="${manualOrder.invoice_no }"/></td>
			</tr>
			<tr>
				<th><label class="text">手机串号：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.terminal_num"
					dataType="string" id="terminal_num" value="${manualOrder.terminal_num }"/></td>
				<th><label class="text">买家留言：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.buyer_message"
					dataType="string" id="buyer_message" value="${manualOrder.buyer_message }"/></td>
			</tr>
			<tr>
			<th><label class="text">ICCID：</label></th>
				<td><input type="text" class="ipttxt" name="manualOrder.iccid"
					dataType="string" id="iccid" value="${manualOrder.iccid }"/></td>
				<th><label class="text">备注：</label></th>
				<td colspan="3"><input style="width: 300px" type="text"
					class="ipttxt" name="manualOrder.service_remarks" dataType="string"
					id="service_remarks" value="${manualOrder.service_remarks }"/></td>
			</tr>
			<tr>
				<td colspan="6">
					<div
						style="margin-top: 30px; margin-left: 500px; margin-right: auto;">
						<input type="button" id="saveOrderBtn" value="保存" class="graybtn1" />
						<input type="button" id="resetOrderBtn" style="margin-left: 20px;"
							value="重置" class="graybtn1" />
						<input type="button" id="importOrderBtn" value="导入" class="graybtn1" style="margin-left: 20px;"/>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="showPhoneSelctionDiv">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="active" id="tab0">
						省份选号
					</li>
					<li tabid="1" class="" id="tab1">
						总部选号
					</li>
				</ul>
			</div>
			<div class="tab-page">
			   <div tabid="tab_0" class="tab-panel"style='border: 0px solid red; <# if founder =="1">top : 210px; <# else >top: 190px; </# if > position : absolute; z-index: 8;'>
                      <div id="selPhoneDlg"></div>
			    </div>			    
			</div>
</div>

<%-- <div id="selPhoneDlgProvince"></div>--%>
<div id="goodsTypeQuickBtn_dialog"></div>
<div id="listGoodsDialog">
<div id="goodsDialog"></div>
</div>
<div id="listPlanTitleDialog">
<div id="planTitleDialog"></div>
<div id="importOrderlog"></div>
</div>


<script type="text/javascript">
	var ctx = '${ctx}';
	$(function() {
		$("#is_attached").attr("disabled",true);
		$("form.validate").validate();
		$("#saveOrderBtn").bind("click",function() {
			var tips = "";
			var realname = $("#is_realname").val();
			var certitype = $("#certi_type").val();
			var ordercitycode = $("#order_city_code").val();
			var isold = $("#is_old").val();
			var offerefftype = $("#offer_eff_type").val();
			var sourcefrom = $("#source_from").val();
			var paymethod = $("#pay_method").val();
			if(realname=="-1"){
				tips += "是否实名不能为空；";
			}
			if(certitype=="-1"){
				tips += "证件类型不能为空；";
			}
			if(ordercitycode=="-1"){
				tips += "地市不能为空；";
			}
			if("-1" == isold){
				tips += "新老用户不能为空；";
			}
			if("-1" == offerefftype){
				tips += "首月不能为空；";
			}
			if("-1" == paymethod){
				tips += "支付方式不能为空；";
			}
			if("-1" == sourcefrom){
				tips += "订单来源不能为空；";
			}
			if(tips.length>0){
				alert(tips);
				return ;
			}
			var isattached = $("#is_attached").val();
			if(realname=="0" && isattached == "1"){
				alert("非实名用户不能办理分享卡业务");
				return ;
			}
			var url = ctx + "/shop/admin/ordAuto!saveManualOrder.do?ajax=yes";
			Cmp.ajaxSubmit('manualOrderForm', '', url, {}, function callBack(reply) {
				alert("订单补录成功，外部订单号为："+reply.order_id);
				$.Loading.hide();
			}, 'json');
		});
		Eop.Dialog.init({id:"selPhoneDlgProvince",modal:true,title:"号码选择",width:'800px'});
		Eop.Dialog.init({id:"goodsTypeQuickBtn_dialog",modal:false,title:"商品分类",width:"1000px"});
		Eop.Dialog.init({id:"listGoodsDialog",modal:true,title:"商品选择",width:"1000px",height:'495px'});
		Eop.Dialog.init({id:"listPlanTitleDialog",modal:true,title:"套餐选择",width:"1000px",height:'495px'});
		Eop.Dialog.init({id:"importOrderlog",modal:true,title:"批量导入",width:"500px",height:'495px'});
		
		//选号页面初始化加载
		Eop.Dialog.init({id:"showPhoneSelctionDiv",modal:true,title:"号码选择",width:'800px'});
		
		$("#selPhoneNumbtn").unbind("click").click(function(){
			
			var ordercitycode = $("#order_city_code").val();
			if(ordercitycode=="-1"){
				tips = "地市不能为空,请先选择地市";
				alert(tips);
				$("#order_city_code").focus();
				return
			}else{
			
			//selPhoneProvinceTest();
			  $("#showPhoneSelctionDiv").load();
			  Eop.Dialog.open("showPhoneSelctionDiv");
			  //默认加载省份查询
			  CommonLoad.loadJSP('selPhoneDlg','<%=request.getContextPath() %>/shop/admin/orderFlowAction!qryBssPhoneNumList.do',{isQuery:"no",selNumChannel:"province",ajax:"yes",includePage:"bssPhoneNumList"},false,true,false);
			  $("#tab0").attr("class","active");
			  $("#tab1").attr("class","");
			}
		});
		//商品分类选择器
	    $("#selGoodsType").click(function(){
			var url = "cat!showCatListEcs.do?ajax=yes&type=goods";
			$("#goodsTypeQuickBtn_dialog").load(url, function (responseText) {
					Eop.Dialog.open("goodsTypeQuickBtn_dialog");
			});
		});
	    $("#selGoods").click(function(){
	    	var cat_id = $("#searchGoodsCatidInput").val();
			var url =ctx + "/shop/admin/goods!goodsSelectList.do?ajax=yes&cat_id="+cat_id;
			Eop.Dialog.open("listGoodsDialog");
			$("#goodsDialog").html("loading...");
			$("#goodsDialog").load(url,function(){});
		});
	    /* $("#goodsInputDialog").click(function(){
	    	var cat_id = $("#searchGoodsCatidInput").val();
			var url =ctx + "/shop/admin/goods!goodsSelectList.do?ajax=yes&cat_id="+cat_id;
			Eop.Dialog.open("listGoodsDialog");
			$("#goodsDialog").html("loading...");
			$("#goodsDialog").load(url,function(){});
		}); */
	    /* $("#planTitleInputDialog").click(function(){
			var url =ctx + "/shop/admin/goods!planTitleSelectList.do?ajax=yes&type_id=10002";
			Eop.Dialog.open("listPlanTitleDialog");
			$("#planTitleDialog").html("loading...");
			$("#planTitleDialog").load(url,function(){});
		}); */
	    $("#resetOrderBtn").click(function(){
	    	var form = $("#manualOrderForm");
	    	$("input",form).each(function(){
	    		if($(this).attr("id")!='saveOrderBtn' && $(this).attr("id")!='resetOrderBtn' 
	    				&& $(this).attr("id")!="selPhoneNumbtn" && $(this).attr("id")!="selGoodsType" && $(this).attr("id")!="selGoods"){
	    			$(this).val("");
	    		}
	    	});
	    	$("select",form).each(function(){
	    		
	    	})
	    });
		
	    $("#importOrderBtn").click(function(){
			var url = "ordAuto!importOrder.do?ajax=yes";
			$("#importOrderlog").load(url, function (responseText) {
					Eop.Dialog.open("importOrderlog");
			});
		});
	});
	
<%--	function selPhoneProvince(){
		var url= app_path+"/shop/admin/orderFlowAction!qryBssPhoneNumList.do?ajax=yes&isQuery=no&selNumChannel=province";
	    $("#selPhoneDlgProvince").load(url,{},function(){});
		Eop.Dialog.open("selPhoneDlgProvince");
   }
	
	--%>
</script>

<script type="text/javascript">

$("#tab0").unbind("click").click(function(){//省份查询
	var ordercitycode = $("#order_city_code").val();
	if(ordercitycode=="-1"){
		tips = "地市不能为空,请先选择地市";
		alert(tips);
		return
	}else{
CommonLoad.loadJSP('selPhoneDlg','<%=request.getContextPath() %>/shop/admin/orderFlowAction!qryBssPhoneNumList.do',{isQuery:"no",selNumChannel:"province",ajax:"yes",includePage:"bssPhoneNumList"},false,true,false);
$("#tab0").attr("class","active");
$("#tab1").attr("class","");
	}
});

$("#tab1").unbind("click").click(function(){//总部查询
	var ordercitycode = $("#order_city_code").val();
	if(ordercitycode=="-1"){
		tips = "地市不能为空,请先选择地市";
		alert(tips);
		return
	}else{
	                                        // var url= app_path+"/shop/admin/orderFlowAction!qryZbPhoneNumList.do?ajax=yes&isQuery=yes&order_id="+order_id+"&queryTypeChooseReturnVal="+queryTypeChooseReturnVal+"&queryTypeZbChooseReturnVal="+queryTypeZbChooseReturnVal+"&infIsZb=yes&selNumChannel=zb";
CommonLoad.loadJSP('selPhoneDlg','<%=request.getContextPath() %>/shop/admin/orderFlowAction!qryZbPhoneNumList.do',{isQuery:"no",selNumChannel:"zb",ajax:"yes",infIsZb:"yes",includePage:"zbPhoneNumList"},false,true,false);
$("#tab0").attr("class","");
$("#tab1").attr("class","active");
	}
});


</script>