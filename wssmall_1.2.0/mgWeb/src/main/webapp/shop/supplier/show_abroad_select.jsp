<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<select  class="ipttxt"  id="is_abroad" name="companyInfo1.is_abroad" required="true" class="show_agent_select inputstyle" dataType="" autocomplete="on" >
	<option value="">请选择</option>
	<c:forEach items="${abroadList}" var="v">
	  <option value="${v.value }">${v.value_desc }</option>
	</c:forEach>
</select>