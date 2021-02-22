<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div >
	
	<form id="flowgItemRidform" class="grid">

   <table width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
      <th>流程环节名称</th>
      <th>流程环节审批人</th>
      <th>流程环节审批人部门</th>
      <th>环节审决策</th>
      <th>环节审批意见</th>
      <th>环节审批时间</th>
    </tr>
     <c:forEach items="${flowItemList}" var="flowItem">
      
        <tr>
            <td>${flowItem.item_name}</td>
      		<td>${flowItem.realname}</td>
      		<td>${flowItem.dep_name}</td>
      		<td>${flowItem.audit_state}</td>
      		<td>${flowItem.audit_note}</td>
      		<td>${flowItem.audit_date}</td>
    	</tr>
      
	</c:forEach>
	
  </table>
	</form>
	
	
</div>