<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
  <head>
    <title>配货热免单打印信息页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  
  <body>
<form id ="formAdd" name ="formAdd" method="post"  enctype="multipart/form-data">   
	<input type="hidden" id="print_type"  value="${postModeMap.print_type }" />
<div class="grid_n_cont_sub" style="text-align: center;" >
	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="grid_form thWidth"  >
			<%@include file="pick_hotfree_print_detail.jsp" %> 
	</table>
</div>
</form>
</body>
<script type="text/javascript">
$(document).ready(function() { 	
			doSave();	
});
//提交表单
function doSave(){
			var printUrl=app_path+'/shop/admin/orderPostModePrint!invoiceHotFeePrint.do?ajax=yes&order_id='+$("#order_id").val()+'&print_type='+$("#print_type").val();
			//弹出打印页面
			var printRe=window.open(printUrl,'','dialogHeight=400px;dialogWidth=500px');			
			Eop.Dialog.close();
			$.Loading.hide();	
			peihuoPlan($("#order_id").val());
}

function peihuoPlan(order_id){
	$.Loading.show("订单正在配货处理，请稍侯...");
	 $.ajax({
			type : "post",
			async : false,
			url : app_path + "/shop/admin/ordAuto!exePhPlan.do?ajax=yes",
			data : {
				"order_id" : order_id
			},
			dataType : "json",
			success : function(result) {
				 $.Loading.hide();		 
				// alert(result.message);
			},
			error : function(){
				$.Loading.hide();
			}
	});
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
