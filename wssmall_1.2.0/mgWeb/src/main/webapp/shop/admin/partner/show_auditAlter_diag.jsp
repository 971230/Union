<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.td10 {
	width: 80px;
}
</style>
<div class="division">
<form  class="input" method="post" action="" id='auditParform' validate="true">
 <input type="hidden" id="partner_id" name="partView.partner_id" value="${partView.partner_id}" />
<table width="100%">
  <tbody>
	  <tr>
	    <th>分销商名称：</th>
	    <td>${partView.partner_name}</td>
	  
	  </tr>
	  
      <tr>
	    <th>审核描述：</th>
	   	 <td colspan="3"><textarea value="" autocomplete="on"  rows="4" cols="40" name="partView.audit_idea"></textarea></td>
	  </tr>
	  <tr>
		<th><span class='red'>* </span>处理：</th>
		<td colspan="3" >
			<select  class="ipttxt"  name='partView.sequ' >
				<option value='0'>
					审核通过
				</option>
				<option value='-2'>
					审核不通过
				</option>
			</select>
		</td>
	  </tr>
    </tbody>
</table>
<div class="submitlist" align="center">
 <table>
	 <tr>
	  <th> &nbsp;</th>
	 	<td >
           <input  type="button"  value="提交" id="auditSubmitBtn" class="submitBtn" name='submitBtn'/>
           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
	   </td>
	</tr>
 </table>
</div>
<table>
<tbody>
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
       	<td>${o } </td>
       </c:forEach>
     </tr>
     </tbody>
</table>
</form>
</div>
 <script type="text/javascript"> 
$(function (){
	  
	  $("#auditParform").validate();
      $("#auditSubmitBtn").click(function() {
			var url = ctx+ "/shop/admin/partner!saveAuditAlterPartner.do?ajax=yes";
			Cmp.ajaxSubmit('auditParform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 alert(responseText.message);
						//window.location.reload();
						window.location=app_path+'/shop/admin/partner!historylist.do?';
					}
					
					Partner.page_close();
					
			},'json');
		})
		
  })
</script>
