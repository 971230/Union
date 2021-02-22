<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
 <form  class="validate" method="post" action="" id='editParBaseform' validate="true">
 <div class="distributorL">
 <input type="hidden" id="partner_id" name="partView.partner_id" value="${partView.partner_id}" />
   <table cellspacing="1"  cellpadding="3" width="100%" class="form-table">
   	<tr> 
   	   <th>&nbsp;</th>
       <th><label class="text"><span class='red'>*</span> 代理人(公司)全称:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.partner_name" id="" value="${partView.partner_name }"  maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr>
       <th>&nbsp;</th>
       <th><label class="text"> 关联电信工号:</label></th>
       <td>
       		<input type="text" readonly="readonly" class="ipttxt" name="userName" id="" value="${partView.username}"  maxlength="60" dataType="string"/>
       		<input type="hidden" name="partView.userid" id="" value="${partView.userid}" />
       		<a class="sgreenbtn" href="javascript:void(0);" id="select_user"><span>关联工号</span> </a>       
       </td>
     </tr>
     <tr>
     	<th>法定代表人：</th>
     	<th>&nbsp;</th>
     	<td>&nbsp;</td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>法人名称：</label></th>
     	<td><input type="text" class="ipttxt" value="${partView.legal_name }"  name="partView.legal_name" required="true" /></td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>法人组织机构代码：</label></th>
     	<td><input type="text" class="ipttxt" value="${partView.legal_code }"  name="partView.legal_code" required="true" /></td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>法人身份证号码：</label></th>
     	<td><input type="text" class="ipttxt" value="${partView.legal_id_card }" name="partView.legal_id_card" maxlength="60"  dataType="string" required="true" /></td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>法人身份证地址：</label></th>
     	<td><input type="text" class="ipttxt"  value="${partView.legal_id_addr }" name="partView.legal_id_addr" maxlength="60"  dataType="string" required="true" /></td>
     </tr>
      <tr>
     	<th>公司信息：</th>
     	<th></th>
     	<td></td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>营业执照编号：</label></th>
     	<td><input type="text" class="ipttxt" value="${partView.buss_license }" name="partView.buss_license" maxlength="60"  dataType="string" required="true"  /></td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>营业执照生效时间：</label></th>
     	<td><input type="text" name="partView.buss_eff_date" id="buss_date_input1"  value='<html:dateformat pattern="yyyy-MM-dd" d_time="${partView.buss_eff_date }" />'  maxlength="60" class="dateinput ipttxt" dataType="date" required="true"/> </td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>营业执照失效时间：</label></th>
     	<td><input type="text" class="dateinput ipttxt"  id="buss_date_input2" name="partView.buss_exp_date" value='<html:dateformat pattern="yyyy-MM-dd" d_time="${partView.buss_exp_date }" />' maxlength="60"  dataType="date" required="true"  /></td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>公司地址：</label></th>
     	<td><input type="text" class="ipttxt"  name="partView.company_addr" value="${partView.company_addr }" maxlength="60"  dataType="string" required="true" /></td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>邮政编码：</label></th>
     	<td><input type="text" class="ipttxt" value="${partView.postcode }" name="partView.postcode" dataType="post_code" required="true" /></td>
     </tr>
    
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>联系人：</label></th>
     	<td><input type="text" class="ipttxt" value="${partView.linkman }" name="partView.linkman" maxlength="60"  dataType="string" required="true" /></td>
     </tr>
     <tr>
     	<th></th>
     	<th>联系电话：</th>
     	<td><input type="text" class="ipttxt"  dataType="tel_num" value="${partView.legal_phone_no }" name="partView.legal_phone_no" /></td>
     </tr>
     <tr>
     	<th></th>
     	<th><label class="text"><span class='red'>*</span>手机：</label></th>
     	<td><input type="text" class="ipttxt"  name="partView.phone_no" id="phone_no"   value="${partView.phone_no }"  maxlength="60"  required="true"/>       </td>
     </tr>
     <tr>
     	<th></th>
     	<th>传真号码：</th>
     	<td><input type="text" class="ipttxt" value="${partView.fax }" name="partView.fax" /></td>
     </tr>
     <tr>
     	<th></th>
     	<th>电子邮箱：</th>
     	<td><input type="text" class="ipttxt" value="${partView.email }" name="partView.email" dataType="email" /></td>
     </tr>
     <tr>
     	<th></th>
     	<th>通信地址：</th>
     	<td><input type="text" class="ipttxt" value="${partView.mailing_addr }" name="partView.mailing_addr" /></td>
     </tr>
	 </table> 
	 <c:if test="${flag eq 'add' or flag eq 'edit'}">
	<div class="submitlist" align="center"> 
	 <table align="right">
		 <tr>
		   <th> &nbsp;</th>
		 	<td>
	 	    <input type="hidden" id="is_edit" name="is_edit" value="${is_edit }"> 
		 	<input type="hidden" name="partView.state" value="${partView.state }"> 
	        <input type="hidden" name="partView.sequ" value="${partView.sequ }"> 
	         <input id="editPartnerBaseBtn" type="button"   value=" 保存 " class="submitBtn"/>
<!-- 		     <input name="reset" type="reset"   value=" 重置 " class="submitBtn"/> -->
		     </td>
		     <td> &nbsp;</td>
		</tr>
	 </table>
	</div>  
	</c:if>
	
	 </div>
	
	 
	  <div class="distributorR">
	 <table align="right">
	    <tr>
	     <td>${partnerAlbum }</td>
	    </tr>
	 </table>
	</div>
	 <div class="clear"></div>
   </form>
  <div id="auditDlg"></div>
  <div id="partner_rel_user"></div>
 </div>
<script type="text/javascript">
    $("form.validate").validate();
</script>
<script type="text/javascript"> 
function checkbuss_date(){
	var v1=$("#buss_date_input1").val();
	var v2=$("#buss_date_input2").val();
	if(v1!=""&&v2!=""){
		if(v1>v2){
			alert("失效日期必须大于生效日期！");
			$("#buss_date_input2").val("");
			return ;
		}
	}
}

function checke_date(){
	var v1=$("#e_date_input1").val();
	var v2=$("#e_date_input2").val();
	if(v1!=""&&v2!=""){
		if(v1>v2){
			alert("失效日期必须大于生效日期！");
			$("#e_date_input2").val("");
			return ;
		}
	}
}

var AdminUserAdd = {
	initSelUser:function(){
		$("#partner_rel_user .userQryBtn").unbind("click").bind("click",function(){
			var url = ctx + "/shop/admin/cms/application/application!getUserList.do?is_select=yes&ajax=yes";
			Cmp.ajaxSubmit('admin_list_form','partner_rel_user',url,{},AdminUserAdd.initSelUser);
			return false;
		});
		
		$("#partner_rel_user #select_user_insure").unbind("click").bind("click",function(){
			var sel_radio = $("input[name='select_user_radio']:checked");
			if(sel_radio.length == 0){
				alert("请选择用户！");
				return;
			}
			
			var username = sel_radio.attr("username");
			var userid = sel_radio.attr("userid");
			
			$("input[name='partView.userid']").val(userid);
			$("input[name='userName']").val(username);
			
			Eop.Dialog.close("partner_rel_user");
		});
	}
};

$(function (){
	Eop.Dialog.init({id:"partner_rel_user", modal:false, title:"选择用户", width:"700px"});
	//保证金
	$("#service_cash_input").blur(function(){
		if($(this).val()>1000000&&$.trim($(this).val()).length>6){
			alert("保证金应大于1小于1000000");
			$(this).focus().val("");
		}
	});
	
	//日期
	$("#buss_date_input2").blur(function(){
		checkbuss_date();
	});
	$("#e_date_input2").blur(function(){
		checke_date();
	});
	$("input[name='partView.legal_code']").blur(function(){
		if (/[\u4E00-\u9FA5]/i.test($(this).val())) {
		    alert('编码不允许有中文！');
		    $(this).val("").focus();
		}
	});
	$("input[name='partView.legal_id_card']").blur(function(){
		if (!/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test($(this).val())) {
		    alert('请输入正确的身份证号！');
		    $(this).val("").focus();
		}
	});
	$("form").find("tr").find("th:eq(1)").css("text-align","left");
     if('view'=='${flag}'){
         $("input").attr("class","noborder");
         $("#up").attr("style","display:none;");
     }
     
    //添加或修改数据保存
	  var isedit=$("#is_edit").val();
	  if(isedit=="true"){
		  $("#agent_code_tr").show();
		  $("#phone_no").attr("readonly","readonly");
	  }else{
		  $("#agent_code_tr").hide();
	  }
      $("#editPartnerBaseBtn").click(function() {
    	  checkbuss_date();
    	  checke_date();
            var  url = ctx+ "/shop/admin/cmsAgent!savePartner.do?is_edit="+isedit+"&ajax=yes";
			Cmp.ajaxSubmit('editParBaseform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //新增 和 修改
						    alert(responseText.message);
							window.location=app_path+'/shop/admin/cmsAgent!listCmsAgent.do?';	
					}
					if (responseText.result == 3) {
					// 3变更
						alert(responseText.message);
						window.location=app_path+'/shop/admin/cmsAgent!listCmsAgent.do?';
					}
						
			},'json');
		});
	
      //选择电信员工
      $("#select_user").unbind("click").bind("click", function(){
			Eop.Dialog.open("partner_rel_user");
			var url = ctx + "/shop/admin/cms/application/application!getUserList.do?is_select=yes&ajax=yes";
			$("#partner_rel_user").load(url,function(){
				AdminUserAdd.initSelUser();
			}); 
      });
  });
</script>