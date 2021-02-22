<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<%
	String msg = (String) request.getAttribute("msg") == null ? "" : (String) request.getAttribute("msg"); //提示信息
%>
<html>
	<head>
		<script src="/CrmWeb/public/zx/ZX.js"></script>
		<script>
			var msg ='<%=msg%>';
			window.parent.excelInst.upCallBack(msg);
		</script>
	</head>
	<body style="padding-top:2px;height:20px;margin:0px;padding:0px;">
		<form name="excelForm" id="excelForm"  action="CrmWeb/servlet/batchAcceptExcelServlet" method="post" enctype="multipart/form-data"  style='width:100%;height:20px;'>
		<input type="file" name="uploadFile" id='uploadFile' style='border:1px solid #CCDBE4'  style='width:100%;margin :0px;height:20px;' / >
	</form>
	</body>
</html>
