<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="division">
<form  class="input" method="post" action="" id='auditParform' validate="true">
<table width="100%">
  <tbody>
	  <tr>
	    <th>行业用户名称：</th>
	    <td>${partView.partner_name}</td>
	  </tr>
	    <tr>
	    <th>审核时间：</th>
	    <td><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${partView.last_update_date}"> </html:dateformat> </td>
	  </tr>
	  <tr>
		<th><span class='red'> </span>处理结果：</th>
		<td colspan="3" >
		   审核不通过
		</td>
	  </tr>
	   
      <tr>
	    <th>审核描述：</th>
	   	 <td colspan="3">${partView.audit_idea }</td>
	  </tr>
    </tbody>
</table>

</form>
</div>
