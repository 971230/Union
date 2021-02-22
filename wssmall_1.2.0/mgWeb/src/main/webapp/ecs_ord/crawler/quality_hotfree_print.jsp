<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
  <head>
    <title>热免物流单打印信息页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  
  <body>
<form id ="formUpdate" name ="formUpdate" method="post"  enctype="multipart/form-data">
  <input type="hidden" name="delivery_id"  value="${prints.delivery_id }" />
   <input type="hidden" name="itmesIds"  value="${itmesIds}" />
    <input type="hidden" name="post_type"  value="${post_type }" />
</form>

<form id ="formAdd" name ="formAdd" method="post"  enctype="multipart/form-data">
    
	<input type="hidden" name="prints.order_id"  value="${order_id }" />
	<input type="hidden" name="prints.post_no"  value="${logi_no }" />
	<input type="hidden" name="prints.delivery_id"  value="${prints.delivery_id }" />
	<input type="hidden" name="prints.print_type"  value="1" />
	<input type="hidden" name="prints.destcode"  value="${postModeMap.org_code }" />
	<input type="hidden" name="prints.express_type"  value="${postModeMap.express_type }" />
    <input type="hidden" value="${order_id }" name="order_id"   />
    <input type="hidden" id="order_is_his" name="order_is_his" value="${order_is_his}" /><!--补寄重发详情页传过来的 标识 -->
<div class="grid_n_cont_sub" style="text-align: center;" >

	<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="grid_form thWidth"  >
			<%@include file="quality_hotfree_print_detail.jsp" %> 
	</table>
</div>

<!--     <div class="pop_btn">
           	<a   class="blueBtns" id="savebtn" href="javascript:void(0);"><span>保存打印</span></a>
    </div> -->
</form>
</body>
<script type="text/javascript">
$(document).ready(function() {
			doSave();
});
//提交表单
function doSave(){
	    var url = app_path + "/shop/admin/orderPostPrintAction!doAddHotFree.do?ajax=yes";
	    Cmp.ajaxSubmit('formAdd','',url,{},function(data){
		if(data.result=='0'){//保存打印日志成功
			var printUrl=app_path+'/shop/admin/orderPostModePrint!hotFreePostModelNew.do?'+
					'ajax=yes&order_is_his=${order_is_his}&order_id=${order_id}&delivery_id=${prints.delivery_id}&itmesIds=${itmesIds}&post_type=${post_type}&postId=${logi_company}&wlnum=${logi_no}&delvery_print_id='+data.delveryPrintId;
    		//window.close();
			//弹出打印页面
			var printRe=window.open(printUrl,'','dialogHeight=630px;dialogWidth=960px');		
		    if(data.is_signback=='1'){				
				var rePrintUrl=app_path+'/shop/admin/orderPostModePrint!hotFreePostModelNew.do?'+
				'ajax=yes&order_is_his=${order_is_his}&order_id=${order_id}&delivery_id=${prints.delivery_id}&itmesIds=${itmesIds}&post_type=${post_type}&postId=${logi_company}&wlnum=${logi_no}&delvery_print_id='+data.delveryPrintId+'&is_receipt=1';
				printRe=window.open(rePrintUrl,'','dialogHeight=630px;dialogWidth=960px');		
			} 
		    Eop.Dialog.close();
			}else{
				alert(data.message);
				}
			$.Loading.hide();
		},'json')
}
function getBrowserVersion(){
	var userAgent = navigator.userAgent.toLowerCase();   
	var isOpera = userAgent.indexOf("opera") > -1;
	if (isOpera) {
        return "Opera"
    }
    if (userAgent.indexOf("firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    if (userAgent.indexOf("chrome") > -1){
    	return "Chrome";
   	}
	if(userAgent.match(/msie ([\d.]+)/)!=null){//ie6--ie9                
		uaMatch = userAgent.match(/msie ([\d.]+)/);                
		return 'IE'+uaMatch[1];        
	}else if(userAgent.match(/(trident)\/([\w.]+)/)){    
		uaMatch = userAgent.match(/trident\/([\w.]+)/);    
		switch (uaMatch[1]){                                
		case "4.0": return "IE8" ;
		break;                                
		case "5.0": return "IE9" ;
		break;                                
		case "6.0": return "IE10";
		break;                                
		case "7.0": return "IE11";
		break;                                
		default:return "undefined" ;            
		}  
	}       
	return "undefined";  
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
