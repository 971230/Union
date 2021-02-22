<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<option value="--">选择添加项</option>
<c:forEach items="${nameList }" var="nl">
	<option value="${nl.c_name }|${nl.e_name}">${nl.c_name }</option>
</c:forEach>
