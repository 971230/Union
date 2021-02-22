<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="division">
<form  class="input" method="post" action="" id='auditParform' validate="true">
 <input type="hidden" id="partner_id" name="partView.partner_id" value="${partView.partner_id}" />
 <input type="hidden" name="partView.state" value="${partView.state}" />
 <input type="hidden" name="partView.sequ" value="${partView.sequ}" />
 
  <input type="hidden" name="partView.userid" value="${partView.userid}" />
<table width="100%">
  <tbody>
	  <tr>
	    <th>行业用户名称：</th>
	    <td>${partView.partner_name}</td>
	  </tr>
	   
	   <tr>
	    <th><span class='red'>* </span>冻结业务：</th>
	    <td><c:forEach items="${goodsUsageList}" var="obj" varStatus="c">
	        
	        <c:if test="${c.index%4==0}">
	         <br/>
	        </c:if>
	         <input type="checkbox" name="usage" value="${obj.goods_id }">&nbsp;${obj.name }
	    </c:forEach> </td>
	  </tr>
	   <tr>
	    <th><span class='red'> </span>冻结描述：</th>
	   	 <td colspan="3"><textarea  dataType="string" rows="5" cols="30" name="block_reason"></textarea></td>
	  </tr>
	 
    </tbody>
</table>
<div class="submitlist" align="center">
 <table>
	 <tr>
	  <th> &nbsp;</th>
	 	<td >
           <input  type="button"  value="保存" id="auditSubmitBtn" class="submitBtn" name='submitBtn'/>
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
            var len=$("input[name='usage']:checked").length;
            if(len==0){
               alert('请选择冻结业务！');
               return false;
            }
            
            var url = ctx+ "/shop/admin/cmsAgent!saveGradingPartner.do?ajax=yes";
			Cmp.ajaxSubmit('auditParform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
						
					}
					if (responseText.result == 2 ) {
						//2 成功
							alert(responseText.message);
							//window.location.reload();
							window.location=app_path+'/shop/admin/cmsAgent!list.do?';
					}
					Partner.page_close();
					
			},'json');
		})
		
  })
</script>
