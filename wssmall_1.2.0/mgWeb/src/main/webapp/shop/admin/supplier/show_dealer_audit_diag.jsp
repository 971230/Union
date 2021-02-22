<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
<form  class="input" method="post" action="" id='auditParform' validate="true">
 <input type="hidden" id="supplier_id" name="supplier_id" value="${supplier.supplier_id}" />
<table width="100%">
  <tbody>
	  <tr>
	    <th>经销商公司名称：</th>
	    <td>${supplier.company_name}</td>
	  </tr>
      <tr>
	    <th><span class='red'>* </span>审核描述：</th>
	   	 <td colspan="3"><textarea  maxlength="100"  dataType="string" id="audit_idea" rows="5" cols="40" name="audit_idea"></textarea></td>
	  </tr>
	  <tr>
		<th><span class='red'>* </span>处理结果：</th>
		<td colspan="3" >
			<select  class="ipttxt" id="supplier_state"  name='supplier_state'  >
				<option value=''>
					请选择
				</option>
				<option value='1'>
					审核通过
				</option>
				<option value='-1'>
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
</form>
</div>
 <script type="text/javascript"> 
$(function (){
	  
	  $("#auditParform").validate();
      $("#auditSubmitBtn").click(function() {
           
            if($("#audit_idea").val()==""){
              alert("审核描述不能为空！");
              return false;
           }
           if($("#supplier_state").val()==""){
              alert("请选择处理！");
              return false;
           }
      
			var url = ctx+ "/shop/admin/supplier!saveAuditDealer.do?ajax=yes";
			Cmp.ajaxSubmit('auditParform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 alert(responseText.message);
					}
					if (responseText.result == 2 || responseText.result == 3) {
						//2审核 3变更
							alert(responseText.message);
							//window.location.reload();
							window.location=app_path+'/shop/admin/supplier!auditlist.do?supplier_type=2';
					}
					Supplier.page_close();
					
			},'json');
		})
		
  })
</script>
