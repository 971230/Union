<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
 <form  class="validate" method="post" action="" id='editParBaseform' validate="true">
 <div class="distributorL">
 <input type="hidden" id="partner_id" name="partView.partner_id" value="${partView.partner_id}" />
   <table cellspacing="1"  cellpadding="3" width="100%" class="form-table">
    <tr> 
       <th><label class="text"><span class='red'>*</span> 分销商编号:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.partner_code" id="partner_code" value="${partView.partner_code }"  maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 分销商名称:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.partner_name" id="partner_name" value="${partView.partner_name }"  maxlength="60"   dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 达量:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.need_amount" id="need_amount"  value="${partView.need_amount }"  maxlength="60"  dataType="float" required="true"/> <span class='red'>&nbsp;注:需要审核的字段 </span></td>
     </tr>
      <c:if test="${flag ne 'add' }">
      <tr>
        <th><label class="text"><span class='red'>*</span> 分值:</label></th>
       <td>
         <input type="text" class="ipttxt"  name="partView.score"  maxlength="60" value="${partView.score }"  dataType="float" /> <span class='red'>&nbsp;注:需要审核的字段 </span>
       </td>
      </tr>
     </c:if>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 店铺类型:</label></th>
       <td>
        <c:if test="${flag eq 'add' or flag eq 'edit'}">
       <html:selectdict curr_val="${partView.shop_type}"  name="partView.shop_type" attr_code="dc_partner_shop_type"></html:selectdict>
        </c:if>
        <c:if test="${flag eq 'view'}">
        ${partView.shop_type_desc }
        </c:if>
      </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 服务保证金：</label></th>
       <td><input type="text" class="ipttxt"  name="partView.service_cash" id="service_cash"  value="${partView.service_cash }"  maxlength="60"  dataType="int" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 发票押金:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.invoice_cash" id="invoice_cash"  value="${partView.invoice_cash }"  maxlength="60"  dataType="int" required="true"/>       </td>
     </tr>
     <c:if test="${flag eq 'edit' and partView.parent_userid!=null}">
     <tr> 
       <th><label class="text">上级分销商：</label></th>
       <td>
       <input type="text" class="ipttxt"  name="parent_userid" readonly="readonly" id="parent_userid"  value="${adminuser.realname}" />       
       </td>
     </tr>
     </c:if>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 法人姓名：</label></th>
       <td><input type="text" class="ipttxt"  name="partView.legal_name" id="legal_name"  value="${partView.legal_name }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 法人证件号码:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.legal_id_card" id=""  value="${partView.legal_id_card }"  maxlength="60"  dataType="string" required="true"/><span class='red'>&nbsp;注:需要审核的字段 </span> </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 法人联系电话:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.legal_phone_no" id="legal_phone_no"  value="${partView.legal_phone_no }"  maxlength="60"  dataType="" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 地址信息:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.address" id="address1"  value="${partView.address }" size="40" maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 联系人:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.linkman" id="linkman"  value="${partView.linkman }"  maxlength="60"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 移动电话:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.phone_no" id="phone_no"   value="${partView.phone_no }"  maxlength="60"  dataType="mobile" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 邮箱:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.email" id="email"   value="${partView.email }"  maxlength="60"  dataType="email" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text"><span class='red'>*</span> 营业执照:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.buss_license" id="buss_license"  value="${partView.buss_license }"  maxlength="60"  dataType="string" required="true"/><span class='red'>&nbsp;注:需要审核的字段 </span></td>
     </tr>
      <tr> 
       <th><label class="text"><span class='red'>*</span> 营业执照地址:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.buss_license_ads" id="buss_license_ads"  value="${partView.buss_license_ads}"  maxlength="60"  dataType="string" required="true"/><span class='red'>&nbsp;</span></td>
     </tr>
      <tr> 
       <th><label class="text"><span class='red'>*</span> 税务登记证号:</label></th>
       <td><input type="text" class="ipttxt"  name="partView.tax_number" id="tax_number"  value="${partView.tax_number }"  maxlength="60"  dataType="string" required="true"/><span class='red'>&nbsp;</span></td>
     </tr>
     
     <tr> 
       <th><label class="text"><span class='red'>*</span> 生效时间:</label></th>
        
       <td>
      <input type="text"   name="partView.eff_date" id="eff_date"  value='<html:dateformat pattern="yyyy-MM-dd" d_time="${partView.eff_date }" />'  maxlength="60" class="dateinput ipttxt" dataType="date" required="true"/>       </td>
     </tr>
     
     <tr> 
       <th><label class="text"><span class='red'>*</span> 失效时间:</label></th>
       <td>
      <input type="text"  name="partView.exp_date" id="exp_date"  value='<html:dateformat pattern="yyyy-MM-dd" d_time="${partView.exp_date }" />'  maxlength="60" class="dateinput ipttxt" dataType="date" required="true"/><span class='red'>&nbsp;注:需要审核的字段 </span> </td>
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
		     <input name="reset" type="reset"   value=" 重置 " class="submitBtn"/>
		     </td>
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
 </div>
 
<script type="text/javascript"> 

$(function (){

     if('view'=='${flag}'){
         $("input").attr("class","noborder");
         $("#up").attr("style","display:none;");
     }
     
    //添加或修改数据保存
	  var isedit=$("#is_edit").val();
	  
	  $("#editParBaseform").validate();
      $("#editPartnerBaseBtn").click(function() {
            var  url = ctx+ "/shop/admin/partner!savePartner.do?is_edit="+isedit+"&ajax=yes";
			Cmp.ajaxSubmit('editParBaseform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //新增 和 修改
						    alert(responseText.message);
							window.location=app_path+'/shop/admin/partner!list.do?';	
					}
					if (responseText.result == 3) {
					// 3变更
						alert(responseText.message);
						window.location=app_path+'/shop/admin/partner!list.do?';
					}
						
			},'json');
		})
	
  })
</script>