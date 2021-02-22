<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
<input id="idxx" type="hidden" cfg_id="${cfg_id}">
<form  class="validate" validate="true" method="post" action="" id='addParStaff'  >
<table cellspacing="1" cellpadding="3" width="100%"  class="form-table">
<input type="hidden" class="ipttxt" id="owner_userid" name="acc.owner_userid" value="${userid }" required="true" autocomplete="on" dataType="string">
    <tr>
        <th><label class="text"><span class='red'>* </span>银行帐号：</label></th>
        <td>
            <input type="text" class=" ipttxt" id="accounted_code" name="acc.accounted_code" value="${acc.accounted_code}" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
    <tr>
        <th><label class="text"><span class='red'>* </span>银行key：</label></th>
        <td>
            <input type="text" class="ipttxt" id="account_key" name="acc.account_key" value="${acc.account_key }" required="true" autocomplete="on" dataType="string">
        </td>
    </tr>
   <%--  <tr>
        <th><label class="text"><span class='red'>* </span>账户归属用户：</label></th>
       <td>
		  <input type="text" class="ipttxt" id="owner_userid" name="acc.owner_userid" value="${acc.owner_userid }" required="true" autocomplete="on" dataType="string">
	  <td>
    </tr>  --%>
</table>
<div class="submitlist" align="center">
	 <table>
		 <tr>
		 <th style="width:55px;"></th>
		 	<td>
	           <input  type="button"  id="butSavew" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
 </form>
 </div>
<script type="text/javascript" >
$(function (){
	  $("#addParStaff").validate();
      $("#butSavew").click(function() {
    	 	var cfg_id=$("#idxx").val();
			var url = ctx+ "/shop/admin/creditAction!addAcc.do?ajax=yes&acc.cfg_id="+cfg_id;
			Cmp.ajaxSubmit('addParStaff', '', url, {}, function(reply){
			 if(reply.result==0){
               alert(reply.message);
             }  
             if(reply.result==1){
               alert("操作成功!");
               
                 var cfg_id=$("#idxx").val();
			     var url ="creditAction!showAccount.do?ajax=yes&cfg_id="+cfg_id; 
			     $("#addAccountDialog").load(url,function(){
		         });
			     Eop.Dialog.close("addAccountDialogssss"); 
             }
			 },'json');
		});
  });
     

</script>

