<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link href="<%=request.getContextPath() %>/publics/lucene/info.css" rel="stylesheet" type="text/css" />
<div style="margin-top: 100px;margin-left: 500px;">
	<input id="lucence_search_tx" name="title" type="text" tableColumn="title" keyFun="searchResult" />
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/lucene/lucene.js"></script>
<script type="text/javascript">
	$(function(){
		Lucene.init('lucence_search_tx');
	});
	
	function searchResult(event,data){
		console.log(data);
		var hvalue = $("#lucence_search_tx").attr("hiddenValue");
		alert(hvalue);
	}
</script>