<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="comBtns">
	<c:forEach items="${ordBtns }" var="btn">
		<a href="javascript:void(0);" name="${btn.e_name }" ac_url="${btn.action_url }" show_type="${btn.show_type }" list_hide="${btn.list_hide }" class="dobtn" style="margin-right:5px;">${btn.c_name }</a>
	</c:forEach>
</div>


