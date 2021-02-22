<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/rule/js/jquery-1.4.2.min.js"></script>
</head>
<body >

    <div class="pop_btn">
           	<a class="savebtn" id="printOK" href="javascript:void(0);"><span>打印成功</span></a>
           		<a class="savebtn" id="printFail" href="javascript:void(0);"><span>没有打印成功</span></a>
    </div>

</body>

<script type="text/javascript">
$(document).ready(function() { 
	$("#printOK").click(function() {
		window.returnValue='ok';
		window.close();
	}); 
   $("#printFail").click(function() {
	    window.returnValue='fail';
		window.close();
	});  
});


</script>


</html>