<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>产品信息</title>
</head>
<body >
<%
	String batchId = (String)  request.getAttribute("batchId") == null ? "" : (String)  request.getAttribute("batchId");
	String msg = (String) request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg"); //提示信息
	String flag = (String) request.getAttribute("flag") == null ? "" : (String) request.getAttribute("flag"); //判断是否导入成功标志

%>
<script type="text/javascript" src="${ctx}/public/common/ress/js/jquery-1.5.1.min.js"></script>
<script language="javascript">
	$(function(){
		init();
	});
	function init(){
			
		var msg = '<%=msg%>';
		var flag ='<%=flag%>';
		var batchId ='<%=batchId%>';
		
		var currA = $("[name='upLoadA']",$(window.parent.document));
		var dealResult = $("[name='dealResult']",$(window.parent.document));
		if(flag == '1'){
			//dealResult.html("<font color='green'>" + msg + "【批次号：" + batchId + "】" + "</font>");
			dealResult.html("<font color='green'>导入成功</font>");
			parent.batchReturn.loadReturnList(batchId);
		}else {
			dealResult.html("<font color='red'>" + msg + "</font>");
		}
		if(	currA.attr("disabled")){ 
			currA.attr("disabled",false);
		}			
	}
</script>
</head>
<body style="padding-top:2px;height:30px;margin:0px;padding:0px;">
	<form name="excelForm" id="excelForm"  action="${ctx}/servlet/batchAcceptExcelServlet?url=/shop/admin/batch/UpLoadFile.jsp" method="post" enctype="multipart/form-data"  style='width:100%;height:30px;'>
		<input type="file" name="uploadFile" id='uploadFile' style='border:1px solid #CCDBE4'  style='width:100%;margin :0px;height:20px;' / >
	</form>
</body>
</html>
