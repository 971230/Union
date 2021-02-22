<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="division">
<form  class="input" method="post" action="" id='auditParform' validate="true">
 <input type="hidden" id="partner_id" name="partView.partner_id" value="${partView.partner_id}" />
<table width="100%">
  <tbody>
	  <tr>
	    <th>行业用户名称：</th>
	    <td>${partView.partner_name}</td>
	  
	  </tr>
	   <tr>
		<th>处理：</th>
		<td colspan="3" >
	   <font color="red"><c:if test="${partView.audit_idea==null}">还未处理！</c:if>
	   <c:if test="${partView.audit_idea!=null}">已处理！</c:if>
			</font>
		</td>
	  </tr>
      <tr>
	    <th>审核描述：</th>
	   	 <td colspan="3"><textarea value="" autocomplete="on"  required="true" rows="6" cols="40" name="partView.audit_idea">${partView.audit_idea }</textarea></td>
	  </tr>
	 
    </tbody>
</table>
</form>
</div>