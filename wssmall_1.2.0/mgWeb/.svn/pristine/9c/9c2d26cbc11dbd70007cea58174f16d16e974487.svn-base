<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>


<div class="input">
 <form class="validate" method="post" name="payApplyAuditForm" id="payApplyAuditForm" enctype="multipart/form-data">
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table" align="center">
   
     <tr >
       <th  class="label"><label class="text" >审核人：</label></th>
       <td><input type="text" class="ipttxt"  name="" maxlength="60" value="${cur_realname}"  dataType="string" required="true" />       </td>
      </tr>
      
      <tr>
       <th><label class="text">处理结果：</label></th>
       <td  >
         <select name ="commissionApply.status" class="ipttxt">
             <option value='2'>审核通过</option>
             <option value='3'>审核不通过</option>
          </select>
      </td>
      </tr>
      <tr>
       <th><label class="text">处理意见：</label></th>
       <td>
          <textarea class="ipttxt" rows="5" cols="20" name="commissionApply.audit_reason"></textarea>
       </td>
      </tr>
   
 
   </table>
   <div class="submitlist" align="left">
 <table>
 <tr>
  <th></th>
    <td >
      <input  type="button" id ="payApplyAudit" value=" 确    定 " class="submitBtn" />
   </td>
   </tr>
 </table>
</div>
<input type='hidden' name="commissionApply.oper_id" value="${cur_userId}">
<input type="hidden" name="list_id" value="${list_id }">
<input type="hidden" name="commissionApply.apply_id" value ="${apply_id}"> 
<input type="hidden" name ="apply_price" value="${apply_price}">
 </form>

</div>

<script type="text/javascript">
 $(function(){
    $("#payApplyAudit").click(function(){
       var url = ctx +"/shop/admin/commissionApply!payApplyAudit.do?ajax=yes";
       Cmp.ajaxSubmit('payApplyAuditForm', '', url, {},function jsonBack(reply){
           if(reply.result == 0){
             alert("操作成功");
             Eop.Dialog.close("payApplyAuditDlg");
             window.location.reload();
           }
           if(reply.result==1){
             alert(reply.message);
           }
       }, 'json');
    });
 });
</script>