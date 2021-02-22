<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.td10 {
	width: 80px;
}
</style>
<div class="division">
<form  class="input" method="post" action="" id='auditParform' validate="true">
 <input type="hidden" id="partner_id" name="partShopView.partner_id" value="${partShopView.partner_id}" />
<table width="100%">
  <tbody>
  <tr>
	    <td >行业用户名称:</td>
	    <td >${partView.partner_name}</td>
	  
	  </tr>
	 <tr>
     <td class="td10">需审核字段:</td>
     <c:forEach var="c" items="${columnNamelist}">
       <td >${c } </td>
       </c:forEach>
     </tr>
    <tr>
     	<td >变更前记录:</td>
     	<c:forEach  var="o" items="${historyOldList}" >
       	<td >${o } </td>
       </c:forEach>
     </tr>
    <tr>
    <tr>
     	<td >变更后记录:</td>
     	<c:forEach  var="o" items="${histroyList}" >
       	<td >${o } </td>
       </c:forEach>
     </tr>
   </tbody>
</table>

</form>
</div>
