<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<option value="">
						请选择子服务类型
					</option>
<c:forEach items="${childStypeList }" var="ch">
	<option value="${ch.stype_id}">${ch.name }</option>
</c:forEach>