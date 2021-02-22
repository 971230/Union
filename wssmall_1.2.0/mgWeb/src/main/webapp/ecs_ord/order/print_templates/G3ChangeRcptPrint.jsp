<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/ecs_ord/common/jslibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath() %>/ecs_ord/order/print_templates/css/n_order.css" rel="stylesheet" type="text/css" media="all" />
<style type="text/css" media="print">
	#printButton{display: none;}
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/order/print_templates/js/receipt.js"></script>
<title>免填单</title>
</head>
<body>
  <div id="subscribeDivId" >
   <div id="custName" style="left:120px;top:43px;width:300px;"></div>
   <div id="custType" style="left:445px;top:43px;width:300px;">个人</div>
   <div id="paperType" style="left:120px;top:63px;width:300px;"></div>
   <div id="paperNo" style="left:445px;top:63px;width:300px;"></div>
   <div id="paperExpr" style="left:120px;top:83px;width:300px;"></div>
   <div id="paperAddr" style="left:441px;top:83px;width:300px;"></div>
   <div id="contactAddr" style="left:120px;top:113px;width:300px;"></div>
   <div id="contactPhone" style="left:441px;top:113px;width:300px;"></div>
  
   <div id="userType" style="left:120px;top:139px;width:300px;">3G普通用户</div>
   <div id="userNo" style="left:441px;top:139px;width:300px;"></div>
   <div id="mainContentOne" style="left:40px;top:159px;width:690px"></div>
   
   <div id="agentName" style="left:120px;top:696px;"></div><div id="agentPhone" style="left:445px;top:696px;"></div>
   <div id="agentPaperType" style="left:120px;top:716px;"></div><div id="agentPaperNo" style="left:445px;top:716px;"></div>
   
   
   <div id="year1Id" style="left:70px;top:770px;"></div><div id="month1Id" style="left:115px;top:770px;"></div><div id="day1Id" style="left:150px;top:770px;"></div><div id="year2Id" style="left:435px;top:770px;"></div><div id="month2Id" style="left:477px;top:770px;"></div><div id="day2Id" style="left:515px;top:770px;"></div>
  </div>
  

<div align="center">
<input id="order_is_his" type="hidden" value="${order_is_his}"/>	
	<input id="order_id" type="hidden" value="${order_id}"/>	 	
    <input id="printButton" value="打印" type="button" style="width: 50px;" class="sbtn" onclick="doPrint();">
</div>
<script type="text/javascript">
var retInfo = ${retInfo};
putData(retInfo);
</script>
</body>

</html>