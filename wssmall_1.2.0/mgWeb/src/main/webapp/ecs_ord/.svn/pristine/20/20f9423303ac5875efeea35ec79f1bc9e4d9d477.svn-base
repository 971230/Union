<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title>添加物流详细信息页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body  >
<form id ="formUpdate" name ="formUpdate" method="post"  enctype="multipart/form-data">
  <input type="hidden" name="delivery_id"  value="${prints.delivery_id }" />
   <input type="hidden" name="itmesIds"  value="${itmesIds}" />
    <input type="hidden" name="post_type"  value="${post_type }" />
</form>

<form id ="formAdd" name ="formAdd" method="post"  enctype="multipart/form-data">
    
	<input type="hidden" name="prints.order_id"  value="${order_id }" />
	<input type="hidden" name="prints.post_no"  value="${logi_no }" />
	<input type="hidden" name="prints.delivery_id"  value="${prints.delivery_id }" />
    <input type="hidden" value="${order_id }" name="order_id"   />
    <input type="hidden" id="order_is_his" name="order_is_his" value="${order_is_his}" /><!--补寄重发详情页传过来的 标识 -->
<div class="grid_n_cont_sub" style="text-align: center;" >

	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="grid_form thWidth"  >
			<%@include file="quality_psot_print_add_detail.jsp" %> 
	</table>
</div>

    <div class="pop_btn">
           	<a   class="blueBtns" id="savebtn" href="javascript:void(0);"><span>保存打印</span></a>
    </div>
</form>
</body>
<script type="text/javascript">
$(document).ready(function() { 
	$("#savebtn").click(function() {
		if(checkForm()){
			doSave();
			}
	});  
});
//提交表单
function doSave(){
	    var url = app_path + "/shop/admin/orderPostPrintAction!doAddOne.do?ajax=yes";
	    Cmp.ajaxSubmit('formAdd','',url,{},function(data){
		if(data.result=='0'){//保存打印日志成功
			var printUrl=app_path+'/shop/admin/orderPostModePrint!invoicePostPrint.do?'+
					'ajax=yes&order_is_his=${order_is_his}&order_id=${order_id}&delivery_id=${prints.delivery_id}&itmesIds=${itmesIds}&post_type=${post_type}&postId=${logi_company}&wlnum=${logi_no}&delvery_print_id='+data.delveryPrintId;
    		//window.close();
			//弹出打印页面
			var printRe=window.showModalDialog(printUrl,window,'dialogHeight=630px;dialogWidth=960px');
			closeDialog();
			}else{
				alert(data.message);
				}
			$.Loading.hide();
		},'json')
}
//表单校验
function checkForm(){
	
    var codPrice=$("#cod_price").val();//代收货款
    var weight=$("#weight").val();
    var pickupUser=$("#pickup_user").val();
    var insurValue=$("#insur_value").val();
    var origin=$("#origin").val();
    var customerAccount=$("#customer_account").val();
    var deliverInfo=$("#deliver_info").val();
    var postItems=$("#post_items").val();
    if(codPrice.length>30) {
    	alert("代收货款:字符不能超过30！");
    	return false;
    }else if(weight.length>10) {
    	alert("计费重量:字符不能超过10！");
    	return false;
    }else if(pickupUser.length>12){
    	alert("物流收件员:字符不能超过12！");
    	return false;
    }else if(insurValue.length>15){
    	alert("保价金额:字符不能超过15！");
    	return false;
    }else if(origin.length>10){
    	alert("原寄地:字符不能超过10！");
    	return false;
    }else if(customerAccount.length>20){
    	alert("客户编码:字符不能超过20！");
    	return false;
    }else if(deliverInfo.length>60){
    	alert("投递要求(备注):字符不能超过60！");
    	return false;
    }else if(postItems.length>50){
    	alert("声明物品:字符不能超过50！");
    	return false;
    }

    //数字验证
    var post_code=$("#post_code").val();//发件区号
    var customer_account=$("#customer_account").val();//客户编码
    var receiv_account=$("#receiv_account").val();//收款账号
    var monthly_payment=$("#monthly_payment").val();//月结
    var carry_fee=$("#carry_fee").val();//基本邮资
    
    if(post_code!=''&&isNaN(post_code)){
    	alert("发件区号必须是数字！");
    	return false;
    }else if(receiv_account!=''&&isNaN(receiv_account.replace(/[ ]/g,""))){
    	alert("收款账号必须是数字！");
    	return false;
    }else if(monthly_payment!=''&&isNaN(monthly_payment.replace(/[ ]/g,""))){
    	alert("月结账号必须是数字！");
    	return false;
    }else if(carry_fee!=''&&isNaN(carry_fee)){
    	alert("基本邮资必须是数字！");
    	return false;
    }else if(customer_account!=''&&isNaN(customer_account)){
    	alert("客户编码必须是数字！");
    	return false;
    }else if(weight!=''&&isNaN(weight)){
    	alert("计费重量必须是数字！");
    	return false;
    }else if(insurValue!=''&&isNaN(insurValue)){
    	alert("保价金额必须是数字！");
    	return false;
    }
	return true;
}
//是否代收
function changeCOD(){
	if($("#is_cod").val()=='1'){
		$("#cod_price").val($("#cod_price_viwe").val()); 
		$('.daishou').attr("style", "display: block");
	}else{
		$("#cod_price").val(""); 
		$('.daishou').attr("style", "display: none");
	}
}
//是否保价
function changeInsur(){
	if($("#is_insur").val()=='1'){
		$("#insur_value").attr("disabled",""); 
		$('.baojia').attr("style", "display: block");
	}else{
		$("#insur_value").attr("disabled","disabled"); 
		$('.baojia').attr("style", "display: none");
	}
}
</script>
<script language="javascript">
window.onunload = function(){ 
window.opener.location.reload(); 
} 
</script>
<style>
.thWidth th {
     width: 50px;
}
</style>

</html>