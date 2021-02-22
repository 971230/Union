<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div    class='success'  style="height:200px">
	 <h4>系统异常:(<br/></h4>
</div>
<div>


<h4>Exception Name: <s:property value="exception" /> </h4>
<a href="javascript:;" id="showBtn">显示完整错误信息</a>
<div style="display:none;" id="errorbox">Exception Details: <s:property value="exceptionStack" /></div> 
</div>
<script>
$(function(){
	$("#showBtn").click(function(){
		$("#errorbox").show();
	});
});
</script>