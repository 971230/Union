<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/js/select_spread_member.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">

<div style="display: block;" class="order_detail">
	<div class="tab-bar" style="position: relative;">
	<ul class="tab">
		<li id="admin" class="active">系统员工</li>
		<li id="member">系统会员</li>
		<li id="outer">外部人员</li>
	</ul>
	</div>
	
	<div class="tab-page">
		<div id="admin_div"></div>
		<div id="member_div"></div>
		<div id="outer_div"></div>
		
	</div>
	
	<div id='buttons'style='height:70px;margin-left:50px;'>
	</div>
	
</div>
<div id="order_dialog">
<form id="order_form" class="validate">
<div class="footContent" >
<div style="width: 200px; height: 40px; margin: 0pt auto;"
	class="mainFoot">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
		<tr>
			<td><input class="comBtn" id="selectSpreadMember" style="margin-right:10px;"
						 value="确&nbsp;定"></td>
		</tr>
	</tbody>
</table>
</div>
</div> 
</form>
</div>
