<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
  </head>
<script src="<%=request.getContextPath() %>/publics/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<script type="text/javascript">

function ajaxFileUpload(excel_from){
	var fileName =$("#"+excel_from).val();
	if (fileName==null||fileName=='') {
		MessageBox.show("上传文件为空 ", 3, 2000);
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	var extPattern = /.+\.(xls|xlsx|csv)$/i;
	if ($.trim(fileName) != "") {
		if (extPattern.test(fileName)) {
			
		} else {
			MessageBox.show("只能上传EXCEL文件！", 2, 3000);
			return;
		}
	}
	$.Loading.show("正在导入，请稍侯...");
    $.ajaxFileUpload({
         url: ctx + '/shop/admin/orderBatchImportAction!importExcel.do?ajax=yes&fileName='+fileName,//用于文件上传的服务器端请求地址
         secureuri:false,//一般设置为false
         fileElementId:excel_from,
         dataType: 'json',
         success: function (data){
        	 $.Loading.hide();
        	 if(data.result == -1){
        		 alert(data.message);
        		// Eop.Dialog.close("order_batch_import_dialog");
        	 }else{
        		 alert(data.message);
            	 window.location.href = ctx + "/shop/admin/orderBatchImportAction!batchImportList.do";
        	 }
         },
         error: function (data, status, e){
        	 $.Loading.hide();
             alert(e);
         }
    });
    return false;
}
</script>
<body>
<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
        <!-- 订单批量导入:开始 -->
                <div class="grid_n_div">
            	    <h2><a href="javascript:void(0);" class="closeArrow"></a>订单批量导入</h2> 
                	<div class="grid_n_cont">
					   <div class="grid_n_cont_sub">
	                        <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
	                          	<tr>
	                                <td class="excle_title" width="30%">请选择文件：</td>
	                                <td><input type="file" class="ipttxt" name="file" id="batch_import_order" /> </td>
	                                <td>	<input class="comBtn" type="button"      onclick="return ajaxFileUpload('batch_import_order');" value="确认导入"  style="margin-right:10px;" />
						   		    </td>
	                          	</tr>
	                        </table>                         
                       </div>  
        	        </div>
        	   </div>
        <!-- 订单批量导入:结束 -->	    
        </div>
    </div>  
</div>  
</body>
</html>
