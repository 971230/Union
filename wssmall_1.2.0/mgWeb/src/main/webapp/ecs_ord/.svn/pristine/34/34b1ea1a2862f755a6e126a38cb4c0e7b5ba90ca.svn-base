<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单补寄和重发</title>
<script src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/order_supply.js"></script>

<script type="text/javascript">
$(document).ready(function() { 
	//$("#bujidayin").hide();
	//$("#zhengdanchongfadayin").hide();
	
});

 function printAll(){
	 if(checkPrintDataNew ()){
		// Eop.Dialog.init({id:"order_print_btn_event_dialog",modal:true,title:"补寄打印", height:"600px",width:"800px"});
         var delivery_id=$("#delivery_id").val();
		 var logi_company=$("#shipping_company_"+delivery_id).val();//动态
		 var logi_no=$("#logi_no_"+delivery_id).val();//动态
		// var sending_type=$("#sending_type").val();
		 var n_shipping_amount=$("#n_shipping_amount_"+delivery_id).val();
		 var order_id=$("#order_id").val();
		 var orderIds=$("#order_id").val();
		 var post_type=$("#delivery_type_"+delivery_id).val();
		 var order_is_his =$("#order_is_his").val();
		 
		 var itmesIds='';
		 var ids = document.getElementsByName("attr_checkbox_name");               
         var flag = false ; 
         var itNum=0;              
         $("input[attr_checkbox=attr_checkbox]").each(function(i, e){
 			if (e.checked) {
 				
 				var item_id = $(e).attr("attr_id");
 				if (itNum == 0)
 					itmesIds += item_id;
 				else
 					itmesIds += ","+item_id;
 				++itNum;
 			}
 		});
		
		 var ac_url=app_path+'/shop/admin/orderPostPrintAction!doGetPiritModel.do?logi_company='
		 		+logi_company+'&logi_no='+logi_no+'&order_id='+order_id+
		 		'&orderIds='+orderIds+'&n_shipping_amount='+n_shipping_amount+'&post_type='+post_type+'&delivery_id='+delivery_id+'&itmesIds='+itmesIds+'&order_is_his='+order_is_his;
		 
		// Eop.Dialog.open("order_print_btn_event_dialog");
		window.showModalDialog(ac_url,window,'dialogHeight=520px;dialogWidth=1000px');
		// $("#order_print_btn_event_dialog").load(ac_url,function(responseText){
				//if(callcack_fn)callcack_fn(responseText);
		//	});
	  }
	 }
 function closeDialog(){
		Eop.Dialog.close("order_print_btn_event_dialog");
	}
 function checkPrintDataNew () {
	 var radArr = document.getElementsByName("deRadio");
	 var radValue = "";
	 var delivery_type="1";
	 //alert(radArr.length);
	 for(var i=0; i<radArr.length; i++){
	 //alert(radArr[i].checked+" "+radArr[i].name + " "+ radArr[i].value);
	 if(radArr[i].checked){
	   radValue = radArr[i].value;
	   $("#delivery_id").val(radValue);
	   delivery_type=$("#delivery_type_"+radValue).val();
	  }
	 }

	 if(radValue != null && radValue != ""){
		if(delivery_type=='1'){
		//设置选中 
		var count=0;
		
		 $("input[attr_checkbox=attr_checkbox]").each(function(i, e){
			 $(e).attr("checked",false);
			 var delivery_id = $(e).attr("title");
				if(delivery_id==radValue){
					 ++count;
					$(e).attr("checked","checked");
				}
				
			});
			if(count==0){
				 alert("该物流单没有补寄品！");
					return false;
				}
		}
	 }else{
		 alert("请选择物流单！");
			return false;
	 } 
	 return true;
	 }
 function checkPrintData (post_type) {
		var logi_company = $("#logi_company").val();
		var logi_no = $("input[field_name=logi_no]").val(); 

		if (logi_company=='') {
			alert("请先选择物流公司名称！");
			return false;
		}else 	if (logi_no=='') {
			$("input[field_name=logi_no]").focus();
				alert("请先输入物流单号！");
				return false;
			}
        if(post_type=='1'||post_type=='2'){

    		var ids = document.getElementsByName("attr_checkbox_name");               
            var flag = false ;               
            for(var i=0;i<ids.length;i++){
                if(ids[i].checked){
                    flag = true ;
                    break ;
                }
            }
            if(!flag){
                alert("请最少选择一个补寄品！");
                return false ;
            }
        }
      //补寄品已经有物流单号，并且都相同
       var flag=true;
       var item_id_0="";
       var msg="";
       var num=0;
      $("input[attr_checkbox=attr_checkbox]").each(function(i, e){
			if (e.checked) {
				++num;
				//var item_id = $(e).attr("attr_id");
				var delivery_id = $(e).attr("title");
				$("#delivery_id").val(delivery_id);
				if (num == 1){
					//alert(item_id+"****0");
					item_id_0= delivery_id;
				}
				if (num != 1){
					//alert(item_id+"******"+num);
					if(item_id_0!=''){
						 if(item_id_0!=delivery_id){
							 flag=false;
							
							  msg="请先点击保存打印按钮";
							 }
						}else{
							flag=false;
							 msg="请先点击保存打印按钮";
						}
				}
			}
		});
		if(!flag){
			alert(msg);
			return false;
			}
        
		return true;
	}
 function updateDeliveryStatus (delivery_id) {
        var order_id=$("#order_id").val();
        var order_is_his =$("#order_is_his").val();
    	url = ctx+ "/shop/admin/orderSupplyAction!updateDeliveryStatus.do?ajax=yes&order_id="+order_id+"&delivery_id="+delivery_id+"&order_is_his="+order_is_his;
		Cmp.ajaxSubmit('supplyUpdateForm', '', url, {}, function(responseText) {
			
			if (responseText.result == '0') {
				window.location.reload();
				
			}else{
				alert(responseText.message);}
		}, 'json');
     
		/* $.ajax({
			type : "post",
			async : false,
			url : "orderSupplyAction!updateDeliveryStatus.do?ajax=yes",
			data : {order_id:order_id,delivery_id :delivery_id},
			dataType : "json",
			success : function(data) {
				if (data != null) {
					if(data.result!='0'){
						alert(data.message);
					}
					window.location.reload();
				}
			}
		}); */
	 }
	
</script>

</head>
<body>
<%

  String order_is_his = (String)request.getAttribute("order_is_his");
  pageContext.setAttribute("order_is_his_page_flag", order_is_his);
  String order_id = (String)request.getAttribute("order_id");
  
  String is_upload = "";

  if(order_is_his!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){
	  is_upload  = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.IS_UPLOAD);
  }else{
	  is_upload  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_UPLOAD);
  }
  System.out.print("**"+is_upload);
%>
<div id="order_print_btn_event_dialog"></div>
<form id="supplyUpdateForm" method="post" ></form>
	<form id="supplyForm" method="post">
	

	

		<div class="ps_div">
			<h2>订单简要信息</h2>
			<div class="netWarp">
				<div class="goodCon">
					<table class="grid_form">
						<tr>
							<th>内部订单编号：</th>
							<td>${orderTree.orderExtBusiRequest.order_id }</td>
							<th>订单来源：</th>
							<td>  <html:orderattr disabled="disabled"  attr_code="ORDER_FROM"  order_id="${order_id}" field_name="order_from"  field_desc ="订单来源"  ></html:orderattr></td>
						</tr>
						<tr>
							<th>外部状态：</th>
							
							 <td><html:orderattr attr_code="DIC_ORDER_EXT_STATUS"  order_id="${order_id}"  field_name="platform_status"  field_desc ="外部状态" field_type="select" disabled="disabled"></html:orderattr></td>
							<th>归属区域：</th>
							<td><html:orderattr attr_code="DC_MODE_REGION"
									order_id="${order_id}" field_name="order_city_code"
									field_desc="归属地市" field_type="select" disabled="disabled"></html:orderattr>
							</td>
						</tr>
						<tr>
							<th>联系人：</th>
							<td><html:orderattr order_id="${order_id}"
									field_name="ship_name" field_desc="收货人姓名" field_type="input" disabled="disabled" ></html:orderattr></td>
							<th>支付方式：</th>
							<td> <html:orderattr disabled="disabled"  attr_code="DIC_PAY_WAY" order_id="${order_id}" field_name="pay_method"   field_desc ="支付方式" field_type="select"></html:orderattr></td>
						</tr>
						<tr>
							<th>联系电话：</th>
							<td><html:orderattr order_id="${order_id}"
									field_name="receiver_mobile" field_desc="手机号码"
									field_type="input" disabled="disabled" ></html:orderattr></td>
							<th>联系地址：</th>
							<td><html:orderattr order_id="${order_id}"
									field_name="ship_addr" field_desc="详细地址" field_type="input" disabled="disabled" ></html:orderattr>
							</td>
						</tr>
						<tr>
							<th>资料回收方式：</th>
							<td><html:orderattr order_id="${order_id}"
									field_name="file_return_type" field_desc="回收方式"
									field_type="input" disabled="disabled" ></html:orderattr></td>
							<th>资料上传：</th>
							<td>
							<%if(is_upload!=null&&!is_upload.equals("")){ %>
							<html:orderattr attr_code="DIC_IS_UPLOAD"
									order_id="${order_id}" field_name="is_upload"
									field_desc="是否已上传" field_type="select" disabled="disabled" ></html:orderattr>
							<%}%>		
									</td>
						</tr>
						<tr>
							<th>发票打印：</th>
							<td><html:orderattr attr_code="DIC_ORDER_INVOICE_PRINT_TYPE"
									order_id="${order_id}" field_name="invoice_print_type"
									field_desc="发票打印方式" field_type="select" disabled="disabled" ></html:orderattr></td>
							<th>发票名称：</th>
							<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="reserve8"  field_desc ="发票抬头" field_type="input"></html:orderattr></td>
						</tr>
						<tr>
							<th>下单时间：</th>
							<td><html:orderattr order_id="${order_id}"
									field_name="tid_time" field_desc="下单时间" field_type="date" disabled="disabled" ></html:orderattr>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		
			<div class="ps_div">
			<h2>地址列表，物流配送信息</h2>
			<div class="netWarp">
				<div class="goodCon">
					<table class="grid_form">
						<tbody>
							<tr>
								<th>收货人姓名：</th>
								<td><html:orderattr order_id="${order_id}"
										field_name="ship_name" field_desc="收货人姓名" field_type="input" disabled="disabled">
									</html:orderattr></td>
								<input type="hidden" id="shouhuoren" name="ship_name" />
								<th>收货手机：</th>
								<td><html:orderattr order_id="${order_id}"
										field_name="receiver_mobile" field_desc="收货手机"
										field_type="input" disabled="disabled" ></html:orderattr></td>
							</tr>
							<tr>
								<th>收货电话：</th>
								<td><html:orderattr order_id="${order_id}"
										field_name="reference_phone" field_desc="收货电话"
										field_type="input" disabled="disabled" >
									</html:orderattr></td>
								<th>收货地址：</th>
								<td><html:orderattr order_id="${order_id}"
										field_name="ship_addr" field_desc="详细地址" field_type="input" disabled="disabled">
									</html:orderattr></td>

							</tr>
							<tr>
								<th>物流公司：</th>
								<td><select name="shipping_company" id="logi_company"
									class="ipt_new" style="width:200px;">
										<option value="">请选择物流公司</option>
										<c:forEach var="logicompany" items="${logiCompanyList }">
											<option key_str="${logicompany.key_str }"
												value="${logicompany.id }">${logicompany.name }</option>
										</c:forEach>
								</select></td>

								<th>保费：</th>
								<td><input type="text" class="ipt_new" style="width:200px;"
									id="protect_price" name="protect_price" value="0" /></td>
							</tr>
							<tr>

								<th>物流联系电话：</th>
								<td><input type="text" class="ipt_new" style="width:200px;"
									id="logi_receiver_phone" name="logi_receiver_phone" value="" />
								</td>
								<th>运费：</th>
								<td><html:orderattr order_id="${order_id}"
										field_name="n_shipping_amount" field_desc="实收金额（元）"
										field_type="input"></html:orderattr> <input type="hidden"
									name="n_shipping_amount" id="shishoujine" /></td>
							</tr>
							<tr>
								<th>物流联系人</th>
								<td><input type="text" class="ipt_new" style="width:200px;"
									id="logi_receiver" name="logi_receiver" value="0" /></td>
								<th>物流单号：</th>
								<td><html:orderattr order_id="${order_id}"
										field_name="logi_no" field_desc="物流单号" field_type="input"></html:orderattr>
									<input type="hidden" id="logi_no" name="logi_no" /></td>
							</tr>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
			<div class="ps_div">
			<h2>订单配送信息</h2>
			<div class="netWarp">
				<div class="goodCon">
					<table width="100%" class="grid_s">
						<tr><th>选择</th>
						    <th>物流信息ID</th>
							<th>发货时间</th>
							<th>物流状态</th>
							<th>物流类型</th>
							<th>物流单号</th>
						</tr>
						<c:forEach var="delivery" items="${deliveryLs }">
							<tr>
							<td>
							  <c:if test="${(delivery.ship_status==-1|| delivery.ship_status==0)&&delivery.delivery_type!=0}">
							    <input type="radio" name="deRadio" value="${delivery.delivery_id}">
							     <input type="hidden" id="logi_no_${delivery.delivery_id}" value="${delivery.logi_no}">
							     <input type="hidden" id="ship_type_${delivery.delivery_id}" value="${delivery.ship_type}">
							     <input type="hidden" id="n_shipping_amount_${delivery.delivery_id}" value="${delivery.n_shipping_amount}">
							      <input type="hidden" id="shipping_company_${delivery.delivery_id}" value="${delivery.shipping_company}">
							      <input type="hidden" id="delivery_type_${delivery.delivery_id}" value="${delivery.delivery_type}">
							  </c:if>
							</td>
							<td>${delivery.delivery_id}</td>
								<td>
								<c:if test="${(delivery.ship_status==-1|| delivery.ship_status==0)&&delivery.delivery_type!=0}">
								<a href="javascript:void(0);" onclick="updateDeliveryStatus('${delivery.delivery_id}')" >发货</a>
								</c:if>
								 <c:if test="${delivery.ship_status==1}">${delivery.create_time }</c:if>
								</td>
								<td>
								   <c:if test="${delivery.ship_status==-1|| delivery.ship_status==0}">待发货</c:if>
								   <c:if test="${delivery.ship_status==1}">发货完成</c:if>
								</td>
								<td>
								 <c:if test="${delivery.delivery_type==0}">正常发货</c:if>
								 <c:if test="${delivery.delivery_type==1}">补寄</c:if>
								 <c:if test="${delivery.delivery_type==2}">重发</c:if>
								</td>
								<td>${delivery.logi_no }</td>
							</tr>
						</c:forEach>
					</table>
					<div style="text-align: center;">
					<a href="javascript:void(0);"  id="bujidayin_de" onclick="printAll()"><i
									class="icon_hb"  ></i>打印选中的物流单</a></div>
				</div>
			</div>
		</div>

		<div class="ps_div">
			<h2>已确认需要补寄的物品</h2>
			<div class="netWarp">
				<div class="goodCon">
				
					<table width="100%" class="grid_s">
						<tr id="reissue_tr">
							<th>选择</th>
							<th>操作</th>
							<th>补寄状态</th>
							<th>内容</th>
							<th>数量</th>
							<th>物流信息ID</th>
							<!-- <th>物流单号</th> -->
						</tr>
						<c:forEach var="orderDeliveryItemBusiRequest"
							items="${deliveryItemsQueryResp.deliveryItems }">
							<c:if test="${orderDeliveryItemBusiRequest.col1=='0'||orderDeliveryItemBusiRequest.col1==null }">
							<tr>
								<td><input attr_checkbox="attr_checkbox" name="attr_checkbox_name"
									attr_id="${orderDeliveryItemBusiRequest.item_id }"
									type="checkbox"
									id="check_${orderDeliveryItemBusiRequest.item_id }"  title="${orderDeliveryItemBusiRequest.delivery_id }" /></td>
								<td><a id="a_${orderDeliveryItemBusiRequest.item_id }"
									attr_del="attr_del" href="javascript:void(0);"
									attr_sc="${orderDeliveryItemBusiRequest.item_id }">删除</a></td>
								<td>待补寄
								</td>
								<td>${orderDeliveryItemBusiRequest.name }</td>
								<td>${orderDeliveryItemBusiRequest.num }</td>
								
								 <td>${orderDeliveryItemBusiRequest.delivery_id }</td> 
							</tr>
							</c:if>
						</c:forEach>
						<c:forEach var="orderDeliveryItemBusiRequest"
							items="${deliveryItemsQueryResp.deliveryItems }">
								<c:if test="${orderDeliveryItemBusiRequest.col1=='1'||orderDeliveryItemBusiRequest.col1=='2'  }">
							  <tr>
								<td></td>
								<td></td>
								<td>
								<c:if test="${orderDeliveryItemBusiRequest.col1=='1'}">
								  已打印
								</c:if>
								<c:if test="${orderDeliveryItemBusiRequest.col1=='2'  }">
								  补寄完成
								</c:if>
								</td>
								<td>${orderDeliveryItemBusiRequest.name }</td>
								<td>${orderDeliveryItemBusiRequest.num }</td>
								 <td>${orderDeliveryItemBusiRequest.delivery_id }</td> 
							</tr>
							</c:if>
						</c:forEach>
						
					</table>
					<div style="text-align: center;">
									<a href="javascript:void(0);" id="xinzeng"><i
									class="icon_add"></i>新增</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<!-- <a href="javascript:void(0);" id="zhengdanchongfadayin" ><i
									class="icon_hb"  ></i>整单重发打印</a> -->
									<!-- <a href="javascript:void(0);"  id="bujidayin" onclick="printAll('1')"><i
									class="icon_hb"  ></i>补寄选中</a> -->
								<div id="zengpinneirong" style="display: none">
									内容:<input class="ipt_new" type="text" id="reissue_info"
										name="reissue_info" /> 数量:<input class="ipt_new" type="text"
										id="reissue_num" name="reissue_num" /> <a
										href="javascript:void(0);" class="blueBtns" id="luru"><span>录入</span></a>
									<a href="javascript:void(0);" class="blueBtns" id="quxiao"><span>取消</span></a>
								</div>
						</div>
				</div>
			</div>
		</div>
		<div class="pop_btn" align="center">
			<a id="saveprint" class="blueBtns" onclick="savePrintInfo('1')"><span>新增补寄物流信息</span></a>
			<a id="saveChongFaPrint" class="blueBtns" onclick="savePrintInfo('2')" ><span>新增重发物流信息</span></a>
		</div>
		<input type="hidden" value="" id="delivery_id" name="delivery_id" />
		<input type="hidden" value="${order_id }" id="order_id" name="order_id" />
		<input type="hidden" id="deliveri_item_idArray" name="deliveri_item_idArray" />
		<input type="hidden" id="order_is_his" name="order_is_his" value="${order_is_his}" /><!--列表传送过来的 标识 -->
	</form>
</body>
</html>
