<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="idx" value="0"></c:set>
<c:forEach items="${memberLvList }" var="lv" >
	<label> <input type="radio"  value="${lv.lv_id }" name="member_lv_id" <c:if test="${idx==0}">checked</c:if> />${lv.name }</label>
	<c:set var="idx" value="${idx+1 }"></c:set>
</c:forEach>
