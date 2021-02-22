<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${dlyTypeList}" var="dt">
  	<tr>
		<th style="width:20px"><input type="radio" name="dlytype_id" value="${dt.type_id }" /></th>
		<td>${dt.name }</td>
	</tr>
</c:forEach>
