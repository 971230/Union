<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${cartList }" var="c">
 		<tr><td><input type='hidden' name='cart_id' value='${c.id }' /></td>
		<td>${c.name }</td><td>${c.coupPrice }</td>
		<td><input name='numArray' cartid='${c.id }' onblur="updateItemNum(this);" value='${c.num }' size='6' /></td>
		<td><input type='button' name='delgoods' class='graybtn1' cartid="${c.id }" value='删除' /></td>
		</tr>
 </c:forEach>

