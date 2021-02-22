<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>集中写卡队列管理</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/js/queue_all_status.js"></script>
</head>

<body>
	<div id="show_station" class="blueGrid"></div>
	<fieldset style="position:absolute;left:80%; top:80px;">
	<legend></legend>
	<li class="nav">定时刷新：<input type="checkbox" name="timing" style="vertical-align:middle"/></li>
	<li class="nav">间隔时间：<input type="text" name="spacing_time" style="length:20;vertical-align:middle"/>秒</li>
	<li class="nav" id="next_time" style="margin: 20px">离下次刷新还有<font color="red" id="next_second_c"></font>秒</li>
	<li class="nav"><input class="comBtn" type="button" name="refresh" value="刷新"/></li>
	</fieldset>
	
</body>
</html>
<script type="text/javascript">
$(function(){
	IniAllStatus.init()
});
</script>