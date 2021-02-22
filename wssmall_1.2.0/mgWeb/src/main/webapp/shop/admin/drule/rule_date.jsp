<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form id="searchDateForm" method="post">
	<div class="searchformDiv">
    	  <table cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
    	     
    	      <th>日期文本：</th>
				<td>
				<input size="15" type="text"  name="create_time" style="width:149px;" id="create_time"
							readonly="readonly"
							maxlength="60" class="dateinput ipttxt" dataType="date"/></td>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
</form>
<script type="text/javascript">
$(function(){
	$("#searchDateForm input.dateinput").datepicker();
});
</script>