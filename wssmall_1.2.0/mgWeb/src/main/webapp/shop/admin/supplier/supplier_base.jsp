<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
	<form class="validate" method="post" action="" id='editSupBaseform'
		validate="true">
		<div class="distributorL">
			<input type="hidden" name="supplier.supplier_state" value="${supplier.supplier_state }" />
			<input type="hidden" name="supplier.supplier_id" value="${supplier.supplier_id }" />
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>公司名称：
						</th>
						<td>
							<input maxlength="50" name="supplier.company_name" id="company_name"
								type="text" value="${supplier.company_name}" dataType="string" class="resigterIpt"
								autocomplete="on" required="true"   />
							<span class='red' id="company_name_message"></span>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>电子邮件：
						</th>
						<td>
							<input maxlength="50" type="text" id="supplier.email" name="supplier.email"
								value="${supplier.email }" dataType="email" class="resigterIpt _x_ipt"
								autocomplete="on" required="true" />
							<span class='red'> *方便邮箱接收通知。</span>
						</td>
					<tr>
						<th>
							<span class='red'>*</span>QQ号码：
						</th>
						<td>
							<input maxlength="50" id="supplier.qq" name="supplier.qq" value="${supplier.qq }"
								type="text" dataType="int" class="resigterIpt _x_ipt"
								autocomplete="on" required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册账号：
						</th>
						<td>
							<input maxlength="50" id="account_number" name="supplier.account_number"
								type="text" value="${supplier.account_number }" dataType="string"
								class="resigterIpt" autocomplete="on" required="true" />
							<span class='red' id="account_number_message"></span>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册密码：
						</th>
						<td>
							<input maxlength="20" id="password" name="supplier.password"
								dataType="string" type='password' value="${supplier.password }"
								class="resigterIpt _x_ipt" autocomplete="on" required="true" />
						    <span class='red' id="pwd_message"></span>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>确认密码：
						</th>
						<td>
							<input maxlength="20" id="conf_passowrd" name="conf_passowrd"
								dataType="string" type="password" value="${supplier.password }"
								class="resigterIpt _x_ipt" autocomplete="on" required="true" />
							<span class='red' id="passowrd_message"></span>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册人姓名：
						</th>
						<td>
							<input maxlength="50" id="supplier.user_name" name="supplier.user_name" value="${supplier.user_name }"
								type="text" dataType="string" class="resigterIpt _x_ipt"
								autocomplete="on" required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册人性别：
						</th>
						<td>
							<input type="radio" name="supplier.sex" <c:if test="${supplier.sex eq '1' }">checked</c:if> checked value="1" dataType="" />
							男
							<input type="radio" name="supplier.sex" <c:if test="${supplier.sex eq '2' }">checked</c:if>  value="2" dataType="" />
							女
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册人身份证号：
						</th>
						<td>
							<input maxlength="50" id="supplier.id_card" name="supplier.id_card" value="${supplier.id_card }"
								type="text" dataType="" class="resigterIpt _x_ipt"
								autocomplete="on" required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>注册人手机号：
						</th>
						<td>
							<input maxlength="50" id="supplier.phone" name="supplier.phone" value="${supplier.phone }"
								type="text" dataType="mobile" class="resigterIpt _x_ipt"
								autocomplete="on" required="true" />
							<span class='red'></span>
						</td>
					</tr>
					
					<tr>
						<th>
							其它联系电话：
						</th>
						<td>
							<input maxlength="50" id="supplier.other_phone" name="supplier.other_phone"
								type="text" value="${supplier.other_phone }" dataType="string"
								class="resigterIpt _x_ipt" />
						</td>
					</tr>
				<tbody>
			</table>
		</div>
		<c:if test="${flag eq 'add' or flag eq 'edit'}">
	<div class="submitlist" align="center"> 
	 <table align="right">
		 <tr>
		   <th> &nbsp;</th>
		 	<td>
		 	 <input type="hidden" id="supplier_id" name="supplier_id" value="${supplier_id }"/>
		 	 <input type="hidden" id="supplier_type" name="supplier_type" value="${supplier_type }"/>
		 	 <input type="hidden" id="is_edit" name="is_edit"value="${is_edit }">
		 	 <input type="hidden" id="flag" name="flag" value="${flag }">
	         <input id="editPartnerBaseBtn" type="button"   value=" 保存 " class="submitBtn"/>
		     <!-- <input name="reset" type="reset"   value=" 重置 " class="submitBtn"/> -->
		     </td>
		</tr>
	 </table>
	</div>  
	</c:if>
		<div class="clear"></div>
	</form>
	<div id="auditDlg"></div>
</div>

<script type="text/javascript"> 
/**
 * 供货商选择器
 */
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
$(function (){
     if('view'=='${flag}'){
         $("input").attr("class","noborder");
         $("#up").attr("style","display:none;");
     }
     
     if('edit'=='${flag}' || 'view'=='${flag}'){
     	$("#company_name").attr("disabled", true);
     	$("#account_number").attr("disabled", true);
     }

    //修改数据保存
	  var isedit=$("#is_edit").val();
	  
	  $("#editSupBaseform").validate();
      $("#editPartnerBaseBtn").click(function() {
            var  url = ctx+ "/shop/supplier/supplier!editBase.do?ajax=yes";
			Cmp.ajaxSubmit('editSupBaseform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
						    $('#editPartnerBaseBtn').attr('disabled',"true");
							//window.location=app_path+'/shop/admin/supplier!list.do?is_administrator=0';	
					}
						
			},'json');
		})
		
	$("#company_name").blur(function() {
		var company_name = $.trim($('#company_name').val());
		company_name = encodeURI(encodeURI(company_name, true), true);
		url = ctx
				+ "/shop/supplier/supplier!isExits.do?company_name="
				+ company_name + "&ajax=yes";
		if (company_name.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#company_name_message").html(responseText.message);
						$('#company_name').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#company_name_message").html(responseText.message);
					}

				});
	});

	$("#account_number").blur(function() {
		var account_number = $.trim($('#account_number').val());
		account_number = encodeURI(encodeURI(account_number, true), true);
		url = ctx
				+ "/shop/supplier/supplier!isExits.do?account_number="
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
						$("#account_number_message").html(responseText.message);
						$('#account_number').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#account_number_message").html(responseText.message);
					}
				});
	});
	
	$("#password").blur(function(){
		$("#pwd_message").html("");
		var pwd = $.trim($('#conf_passowrd').val());
		var pwd2 = $.trim($(this).val());
	    if (pwd != "" && pwd != pwd2) {
	    	$("#pwd_message").html("注册密码和确认密码不一致");
	    	$(this).val("");
	    	$('#conf_passowrd').val("");
	    } else {
	    	$("#pwd_message").html("");
	    }
	});
	
	$("#conf_passowrd").blur(function(){
		$("#passowrd_message").html("");
		var pwd = $.trim($('#password').val());
		var pwd2 = $.trim($(this).val());
	    if (pwd != pwd2) {
	    	$("#passowrd_message").html("注册密码和确认密码不一致");
	    	$(this).val("");
	    	$('#password').val("");
	    } else {
	    	$("#passowrd_message").html("");
	    }
	});
	
  })
</script>