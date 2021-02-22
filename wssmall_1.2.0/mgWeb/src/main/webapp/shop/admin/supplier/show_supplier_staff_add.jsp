<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
 <form  class="validate" method="post" action="" id='addParStaffform' validate="true">
  <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr> 
       <th><label class="text"><span class='red'>* </span>账号:</label></th>
       <td><input type="text" class="ipttxt"  name="account_number" id="account_number" value=""  maxlength="60"  dataType="string" required="true"/><span name="1111"></span></td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>姓名:</label></th>
       <td><input type="text" class="ipttxt"  name="user_name" id="user_name"  value="" maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>性别:</label></th>
       <td>
        <input type="radio" name="sex" checked="checked" value="1"/>男
        <input type="radio" name="sex"  value="2"/>女
       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>密码:</label></th>
       <td><input type="password"  class="ipttxt"  name="password" id="password"  value=""  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>身份证号码:</label></th>
       <td><input type="text" class="ipttxt"  name="id_card" id="id_card"  value="" maxlength="60"   dataType="id_card" required="true"/>       </td>
     </tr>
   <tr> 
       <th><label class="text"><span class='red'>* </span>联系电话:</label></th>
       <td><input type="text" class="ipttxt"  name="phone" id="phone"  value=""  maxlength="60"  dataType="mobile" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>QQ:</label></th>
       <td><input type="text" class="ipttxt"  name="qq" id="qq"  value=""  maxlength="60"  dataType="int" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>电子邮件:</label></th>
       <td><input type="text" class="ipttxt"  name="email" id="email"  value=""  maxlength="60"  dataType="email" required="true"/>       </td>
     </tr>
     <!-- 
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
      -->
	 </table> 
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
		 	<input type="hidden" name="supplier_id" value="${supplier_id }">
		 	
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
			var url = ctx+ "/shop/admin/supplier!saveAddStaff.do?ajax=yes";
			Cmp.ajaxSubmit('addParStaffform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 
						 alert(responseText.message);
						 SupplierDetail.showStaff();
					}
					 StaffAdd.page_close();
			},'json');
		})
		
	
	 $("#account_number").blur(function(){
	 	var account_number = $.trim($('#account_number').val());
	 	account_number = encodeURI(encodeURI(account_number, true), true);
		url = ctx
				+ "/shop/supplier/supplier!isExitStaffAccountNumber.do?account_number="
				+ account_number + "&ajax=yes";
		if (account_number.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {
					if (responseText.result == 1) {
						// 已存在
						alert(responseText.message);
						$('#account_number').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						//alert(responseText.message);
					}
					
					if (responseText.result == 2) {
						// 操作失败
						alert(responseText.message)
					}

				});
	 });
		
  })
</script>
