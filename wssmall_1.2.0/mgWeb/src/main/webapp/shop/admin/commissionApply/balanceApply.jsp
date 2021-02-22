<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="commissionApply/balanceApplyDlg.js"></script>


<style>
.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}
.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}
h4{
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

</style>


<div class="rightDiv">
	
<div class="tableform">
  
	<div class="division" id ="balanceApplyListDiv">
	  <table width="100%"  id ="balanceApplyTable">
	    <thead>
		<tr>
			<td>申请人</td>
			<td>申请金额</td>
			<td>申请原因</td>
			<td>处理状态</td>
		</tr>
	    </thead>
    <c:forEach var="banlanceApply" items="${balanceApplyList}">
       <tr >
          <input type="hidden" name="commissionApply.apply_id"  value="${banlanceApply.apply_id }"/>
           <td>${cur_realname }</td>
           <td>${banlanceApply.apply_price}</td>
           <td>${banlanceApply.apply_reason}</td>
           <td>
               <c:if test="${banlanceApply.status=='1'}">待审核</c:if>
               <c:if test="${banlanceApply.status=='2'}">审核通过</c:if>
               <c:if test="${banlanceApply.status=='3'}">审核不通过</c:if>
           </td>
           
       </tr>
     </c:forEach>
     </table>
    </div>
	
</div>	

<div style="clear:both"></div>
<div class="tableform">
  
<div class="division" id ="balanceApplyDiv">
 <div class ="input">
   <form class="validate" method="post" name="balanceApplyForm" id="balanceApplyForm" enctype="multipart/form-data">
	   <table cellspacing="1" cellpadding="3" width="100%" class="form-table" align="center">
		     <tr >
		       <th  class="label"><label class="text" >申请人：</label></th>
		       <td><input type="text" class="ipttxt"  name="" maxlength="60" value="${cur_realname }"  dataType="string" required="true" /></td>
		      </tr>
		      
		      <tr>
		       <th><label class="text">申请金额：</label></th>
		       <td>
		       		<input type="text"  class="ipttxt" id="balance_apply_price" name="commissionApply.apply_price" maxlength="60" value=""  dataType="String" required="true" class="dateinput"/>
		       		<input type="hidden" id="apply_money" value="${apply_money}">
		       </td>
		      </tr>
		      
		      <tr>
		       <th><label class="text">申请原因：</label></th>
		       <td>
		          <textarea rows="5" cols="20" name="commissionApply.apply_reason"></textarea>
		       </td>
		      </tr>
		      
	   
	   </table>
   <div class="submitlist" align="left">
		 <table>
			 <tr>
				  <th></th>
				    <td >
				      <input  type="button" id ="balanceApplyBtn" value=" 确    定   " class="submitBtn" />
				   </td>
			   </tr>
		 </table>
    </div>
     <input type="hidden"  value ='${cur_userId}' name ="commissionApply.apply_user_id">
     <input type="hidden"  value ='00B' name='commissionApply.apply_type'>
     <input type="hidden"  value="${list_id}" name="commissionApply.list_id">
     <input type="hidden"  value="${list_id}" name="list_id">
     <input type="hidden" value="${detailIdStr }" name="detailIdStr">
 </form>    
 </div>     
 </div>	
</div>	

</div>

<script type="text/javascript">
  $(function(){
    $("#balanceApplyBtn").click(function(){
         var apply_price = $("#balance_apply_price").val();
         var apply_money = $("#apply_money").val();
         if(isNaN(apply_price)){
            alert("申请价格必须是数字");
            return false;
         }
         if(Number(apply_money) < (Number(apply_price))){
        	 alert("结算金额大于实际金额");
             return false;
         }
         var url = ctx+"/shop/admin/commissionApply!saveBalanceApply.do?ajax=yes";
         Cmp.ajaxSubmit('balanceApplyForm', '', url, {},function jsonBack(reply){
            if(reply.result==0){
              alert(reply.message);
              Eop.Dialog.close("balanceApplyDlg");            
            }
            if(reply.result==1){
              alert(reply.message);
            }
         }, 'json');
      //
    });
  });
</script>
