<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
 <form  class="validate" method="post" action="" id='addParStaffform' validate="true">
  <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr> 
       <th><label class="text"><span class='red'>* </span>编号:</label></th>
       <td><input type="text" class="ipttxt"  name="partStaff.staff_code" id="staff_code" value="${partStaff.staff_code }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>姓名:</label></th>
       <td><input type="text" class="ipttxt"  name="partStaff.staff_name" id="staff_name"  value="${partStaff.staff_name }" maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>性别:</label></th>
       <td>
        <input type="radio" name="partStaff.sex" checked="checked" value="男"/>男
        <input type="radio" name="partStaff.sex"  value="女"/>女
       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>密码:</label></th>
       <td><input type="password"  class="ipttxt"  name="partStaff.password" id="password"  value="${partStaff.password }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
   <tr> 
       <th><label class="text"><span class='red'>* </span>联系电话:</label></th>
       <td><input type="text" class="ipttxt"  name="partStaff.phone_no" id="phone_no"  value="${partStaff.phone_no }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>生效时间:</label></th>
       <td>
       <input type="text" name="partStaff.eff_date" id="eff_date"   value='<html:dateformat pattern="yyyy-MM-dd" d_time="${partStaff.eff_date }" />'  maxlength="60" class="dateinput ipttxt" dataType="date" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>失效时间:</label></th>
       <td>
      <input type="text" name="partStaff.exp_date" id="exp_date"  value='<html:dateformat pattern="yyyy-MM-dd" d_time="${partStaff.exp_date }" />'  maxlength="60" class="dateinput ipttxt" dataType="date" required="true"/>       </td>
     </tr>
	 </table> 
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
		 	<input type="hidden" name="partStaff.partner_id" value="${partner_id }">
		 	
	           <input  type="button"  id="savePartnerStaffBtn" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
   </form>
 </div>
 <script type="text/javascript"> 
$(function (){
	  
	  $("#addParStaffform").validate();
      $("#savePartnerStaffBtn").click(function() {
			var url = ctx+ "/shop/admin/partner!saveAddStaff.do?ajax=yes";
			Cmp.ajaxSubmit('addParStaffform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 
						 alert(responseText.message);
						 PartnerDetail.showStaff();
					}
					 StaffAdd.page_close();
			},'json');
		})
		
  })
</script>
