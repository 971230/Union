<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<select  class="ipttxt"  id="parent_userid" name="part_parent_userid" required="true" class="show_agent_select inputstyle" autocomplete="on" >
	<option value="">请选择</option>
	<c:forEach items="${agentList}" var="v">
	  <option value="${v.userid }">${v.realname }</option>
	</c:forEach>
</select>