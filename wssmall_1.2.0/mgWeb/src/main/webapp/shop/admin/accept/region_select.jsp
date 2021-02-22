<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<select name="region_id">
<c:forEach items="${regions }" var="region" >
	<option value="${region.region_id }|${region.local_name }">${region.local_name }</option>
</c:forEach>
</select>

