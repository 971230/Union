<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
 <form  class="validate" method="post" action="" id='editStaffform' validate="true">
  <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr> 
       <th><label class="text"><span class='red'>* </span>账号:</label></th>
       <td>
       	<input type="text" class="ipttxt"  name="account_number" id="account_number_1" value="${supplierStaff.account_number }"  maxlength="60"  dataType="string" required="true"/> 
       	<span id="account_number_message_1"></span>
        </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>姓名:</label></th>
       <td><input type="text" class="ipttxt"  name="user_name" id="user_name"  value="${supplierStaff.user_name }" maxlength="60"   dataType="string" required="true"/>       </td>
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
       <td><input type="password"  class="ipttxt"  name="password" id="password"  value="${supplierStaff.password }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>身份证号码:</label></th>
       <td><input type="text" class="ipttxt"  name="id_card" id="id_card"  value="${supplierStaff.id_card }" maxlength="60"   dataType="id_card" required="true"/>       </td>
     </tr>
   <tr> 
       <th><label class="text"><span class='red'>* </span>联系电话:</label></th>
       <td><input type="text" class="ipttxt"  name="phone" id="phone"  value="${supplierStaff.phone }"  maxlength="60"  dataType="mobile" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>QQ:</label></th>
       <td><input type="text" class="ipttxt"  name="qq" id="qq"  value="${supplierStaff.qq }"  maxlength="60"  dataType="int" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>* </span>电子邮件:</label></th>
       <td><input type="text" class="ipttxt"  name="email" id="email"  value="${supplierStaff.email }"  maxlength="60"  dataType="email" required="true"/>       </td>
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
		 	<input type="hidden" name="staff_id" value="${supplierStaff.staff_id }">
		 	
	           <input  type="button"  id="saveEditStaffBtn" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
   </form>
 </div>
 <script type="text/javascript"> 
$(function (){
	  $("input[name='sex'][value='${supplierStaff.sex }']").attr('checked','true');
	  
	
	  $("#account_number_1").blur(function() {
		var account_number = $.trim($('#account_number_1').val());
		account_number = encodeURI(encodeURI(account_number, true), true);
		  
		url = ctx
				+ "/shop/supplier/supplier!isStaffAccountNumberExits.do?account_number="
				+ account_number + "&ajax=yes";
		if (account_number.length == 0) {
			return false;
		}
		
		load.asSubmit(url, {}, 'json', function(responseText) {
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#account_number_message_1").html(responseText.message);
						$('#account_number_1').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#account_number_message_1").html(responseText.message);
					}
				});
		});
	  
	  
	  $("#editStaffform").validate();
      $("#saveEditStaffBtn").click(function() {
			var url = ctx+ "/shop/admin/supplier!saveStaffEdit.do?ajax=yes";
			Cmp.ajaxSubmit('editStaffform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 
						 alert(responseText.message);
						 SupplierDetail.showStaff();
					}
					 StaffEdit.page_close();
			},'json');
		})
		
  })
  
var load = {
	asSubmit : function(url, params, dataType, callBack) {
		var data = jQuery.param(params);
		dataType = dataType || 'html';
		$.ajax({
					type : "post",
					url : url,
					data : data,
					dataType : dataType,
					success : function(result) {

						if (dataType == "json" && result.result == 1) {
							$.Loading.hide();
						}
						callBack(result); // 回调函数

					}
				});
	}
};
</script>
