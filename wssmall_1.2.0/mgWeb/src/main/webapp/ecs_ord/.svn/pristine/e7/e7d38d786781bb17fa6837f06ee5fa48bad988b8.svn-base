<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<%-- <script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script> --%>
<form method="post" id="serchform" action='${ctx}/shop/admin/ordAuto!queryInputOrder.do'>
 <div class="searchformDiv">
	 <table>
		<tr>   
		 <th>导入批次号：</th>   
	     <td>
	     <input style="width: 140px" type="text" name="params.bat_id" value="${params.bat_id }"  class="ipt_new" >
	   </td>
	     
	     <th>导入工号：</th>
			<td>
			<c:if test="${params.lock_user_id == '1' }">
				<input type="text" class="ipt_new" style="width:138px;" id="username" name="username"  value="${params.username }" onfocus="queryUserId()"/>
			</c:if>
			<c:if test="${params.lock_user_id != '1' }">
				<input type="text" class="ipt_new" style="width:138px;" id="username" name="username" readonly= "true" value="${params.username }"/>
			</c:if>
			
			<input type="hidden" class="ipt_new" style="width:138px;" id="lock_user_id" name="params.lock_user_id" value="${params.lock_user_id }" />
			<c:if test="${params.lock_user_id == '1' }">
				<a href="javascript:void(0);" id="resetBtn" class="dobtn" style="margin-right:10px;">清除</a>
			</c:if>
			
			</td>
			
	      <td>
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="查询" style="margin-right:10px;"/>
			</td> 
	</tr>
	 </table>
  </div>
</form>

<div class="grid" >

<form method="POST"  id="inputOrderFrom" >
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
		<grid:cell >批次号</grid:cell>
		<grid:cell >导入人</grid:cell>
		<grid:cell >导入时间</grid:cell>
		<grid:cell >导入结果</grid:cell>
		<grid:cell >导入明细</grid:cell>
	</grid:header>
	<grid:body item="inputOrder">
		<grid:cell>${inputOrder.id}</grid:cell>
		<grid:cell>${inputOrder.import_user_id}</grid:cell>
		<grid:cell>${inputOrder.create_time}</grid:cell>
		<grid:cell>
			<c:if test="${inputOrder.status == 'CLCG'}">成功</c:if>
			<c:if test="${inputOrder.status == 'CLSB'}">失败</c:if>
		</grid:cell>
		<grid:cell>
		<a href="javascript:void(0)" onclick="checkInputOrderInfo('${inputOrder.id}');">
                        查看
    	</a>
		</grid:cell>
	</grid:body>
</grid:grid>
</form>

<div id="queryUserListDlg"></div>
<div id="checkInputOrderInfoDlg"></div> 
<script type="text/javascript">
Eop.Dialog.init({id:"queryUserListDlg",modal:true,title:"导入人", width:"800px"});
Eop.Dialog.init({id:"checkInputOrderInfoDlg",modal:true,title:"订单明细", width:"1300px",height:'495px'});
function queryUserId(){
	Eop.Dialog.open("queryUserListDlg");
	lock_user_id = $("#lock_user_id").val();
	var url= ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=query";
	    $("#queryUserListDlg").load(url,{},function(){});
} 

function checkInputOrderInfo(bat_id) {
	Eop.Dialog.open("checkInputOrderInfoDlg");

	var url = ctx + "/shop/admin/ordAuto!inputOrderInfo.do?ajax=yes&bat_id="+bat_id;
	$("#checkInputOrderInfoDlg").load(url,{},function(){});
}

$("#resetBtn").click(function (){
	document.getElementById("username").value = "";
	document.getElementById("lock_user_id").value = "";
});
</script>