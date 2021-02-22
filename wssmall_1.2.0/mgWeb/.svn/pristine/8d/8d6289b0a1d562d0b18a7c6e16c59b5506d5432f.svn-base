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
	    <th>预存金额：</th>
	    <td>￥${partView.deposit}</td>
	  </tr>
	  <tr>
		<th>冻结原因：</th>
		 <td>${partView.block_reason}</td>
	  </tr>
	  <tr>
	    <th>恢复描述：</th>
	   	 <td colspan="3"><textarea value="" autocomplete="on"   rows="5" cols="30" name="partView.renew_reason"></textarea></td>
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
</form>
</div>
 <script type="text/javascript"> 
$(function (){
	  
	  $("#auditParform").validate();
      $("#auditSubmitBtn").click(function() {
			var url = ctx+ "/shop/admin/cmsAgent!saveRenewPartner.do?ajax=yes";
			Cmp.ajaxSubmit('auditParform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
						
					}
					if (responseText.result == 2 ) {
						//2 成功
							alert(responseText.message);
							window.location=app_path+'/shop/admin/cmsAgent!list.do';
					}
					Partner.page_close();
					
			},'json');
		})
		
  })
</script>
