<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>var ctx ='${ctx}';</script>
<form action="javascript:;" method="post" id="admin_user_serarch_form">
		<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
	    <tr>
	    <th></th>
	    <th>用户名：</th>
	    <td>
		   <input type="text"  name="admin_name" id="admin_name_i" value='${admin_name}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
		</td>
		<th></th> 
	   	<td>
		 <input class="comBtn" type="submit" name="searchAdminUserBtn" id="searchAdminUserBtn" value="搜索" style="margin-right:5px;"/>
		</td>
	   </tr>
	  </tbody>
	  </table>
    	</div>  	
    <div class="grid">	
    <grid:grid from="webpage" formId="admin_user_serarch_form" ajax="yes">
        <grid:header>
            <grid:cell width="50px"></grid:cell>
            <grid:cell>用户工号</grid:cell>
            <grid:cell>用户姓名</grid:cell>
            <grid:cell>创建时间</grid:cell>
        </grid:header>
        <grid:body item="admin">
            <grid:cell><input type="radio"  name="admin_user_id" userid="${admin.userid }" user_name="${admin.username }"/></grid:cell>
            <grid:cell>${admin.username}</grid:cell>
            <grid:cell>${admin.realname}</grid:cell>
            <grid:cell>${admin.create_time}</grid:cell>
        </grid:body>
    </grid:grid>
    </br>
    <div style="margin-left: auto;margin-right: auto;" align="center">
        <input name="btn" type="button" id="select_cfg_admin_Btn" value="确定"  class="graybtn1" />
    </div>
    </div>
</form>


