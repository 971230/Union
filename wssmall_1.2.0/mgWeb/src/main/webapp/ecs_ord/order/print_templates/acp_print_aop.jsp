<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/ecs_ord/common/jslibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/order/print_templates/js/receipt.js"></script>
<title>aop受理单打印页面</title>
 <style>
   body{ font-size:10px;}
  .PTable .title{ font-size:13px; margin-bottom: 2px; margin-left:15px;}
  .PTable table{ width:100%; font-size:12px; line-height:14px; }
  .PTable table th{ width:12%; padding-left: 30px; text-align:left; font-weight:bold; vertical-align:top; }
  .PTable table td{ width:28%; text-align:left; vertical-align:top; padding-left: 10px; padding-right: 10px;}
  </style>
  </head>
  <body>
 <div id="receiptDivId">
    <div style="margin-top:90px;font-size:20px;font-weight:bold;" align="center"><!-- 中国联合网络通信有限公司 --></div>
    ${accept_form}    
 </div>
<div align="center">
	<input id="order_is_his" type="hidden" value="${order_is_his}"/>	
	<input id="order_id" type="hidden" value="${order_id}"/>	 	
    <input id="printButton" value="打印" type="button" style="width: 50px;" class="sbtn" onclick="doPrint();">
</div>
</body>
</html>