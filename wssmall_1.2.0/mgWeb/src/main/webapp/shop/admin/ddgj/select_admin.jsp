<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="formWarp">
<div id="order_tag_300" class="formGridDiv">
<form class="grid" id="admingridform">
<table style="width: 100%;">
	<tr>
		<td style="text-align: left;">
			&nbsp;&nbsp;&nbsp;&nbsp;用户名：<input id="search_name" type="text" name="user_name" />
			&nbsp;&nbsp;
			<a href="javascript:void(0);return false;" id="admin_search_btn" class="dobtn" style="margin-right:5px;">搜索</a>
		</td>
	</tr>
</table>
<grid:grid from="webpage" formId="admingridform" ajax="yes" >
	<grid:header>
		<grid:cell width="30px">选择</grid:cell>
		<grid:cell>用户ID</grid:cell>
		<grid:cell>用户名</grid:cell>
	</grid:header>
    <grid:body item="admin">
  	    <grid:cell width="30px">
  	    	<input type="radio" name="userid" user_id="${admin.userid }" user_name="${admin.username }"/>
  	    </grid:cell>
        <grid:cell>
        	${admin.userid }
        </grid:cell>
       <grid:cell>${admin.username }</grid:cell> 
  </grid:body>
</grid:grid>
<table style="width: 100%;">
	<tr>
		<td style="text-align: center;">
			<a href="javascript:void(0);" id="admin_select_btn" class="dobtn" style="margin-right:5px;">确认</a>
		</td>
	</tr>
</table>
</form>
</div>    
</div>
