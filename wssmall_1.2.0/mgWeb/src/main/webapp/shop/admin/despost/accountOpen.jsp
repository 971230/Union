<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/accountOpen.js">
</script>

<form class="validate" action="account!add.do" method="post" id="theForm"
	name="theForm">
	<div class="main-div">
		<table cellspacing="1" cellpadding="3" width="100%"  class="form-table">
			<tr>
				<td class="label" style="text-align:right"><label class="text">用户名：</label></td>
				<td valign="middle">&nbsp;<input type="text" class="ipttxt"  name="partnerAccount.partner_id"
					id="uname" value="" dataType="string" required="true" /></td>
			</tr>
		</table>
	</div>
	<div class="submitlist" align="center">
		 <table>
			 <tr>
				 <td >
				  	<input  type="submit"	  value=" 确    定   " class="submitBtn" />
				 </td>
			 </tr>
		 </table>
	</div>
</form>
</script>