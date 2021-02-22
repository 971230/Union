<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/ecs_ord/common/jslibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/js/code128.js"></script>
<link rel='stylesheet' href='<%=request.getContextPath() %>/ecs_ord/js/code.css' type='text/css' charset='utf-8'>
<title>华盛B2C装箱单打印页面</title>
 <style media='print' type='text/css' >.Noprint{  display: none; } </style>
  </head>
  <body>
  <div style='position:relative; width:600px; height:600px; margin:0 auto; font-family:"微软雅黑"; color:#000;'>
    ${accept_form}
	<!-- 
<div align="center">
	<input id="order_is_his" type="hidden" value="${order_is_his}"/>	
	<input id="order_id" type="hidden" value="${order_id}"/>	 	
    <input id="printButton" value="打印" type="button" style="width: 50px;" class="sbtn" onclick="doPrint();">
</div>
	 -->
  </div>
</body>
<script type="text/javascript">
function clickPrintBtn(){
	 window.print();
	 window.opener=null;
	 window.close();
}
</script>
</html>