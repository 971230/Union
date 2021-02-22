<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
  </head>
<script src="<%=request.getContextPath() %>/publics/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<body>
<div>

	<form method="post" action='/shop/admin/ordAuto!auditZBAction!showList.do' id="qryOutFrom">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
					<th>总商单号：</th>
					<td><input type="text" name="auditZBParams.out_tid" value="${auditZBParams.out_tid }" style="width:138px;" class="ipt_new"></td>
					<th>审核状态：</th>
					<td>
						<html:selectdict  id="audit_status"  name="auditZBParams.audit_status" curr_val="${auditZBParams.audit_status}"  appen_options="<option>---请选择---</option>" attr_code="ZB_AUDIT_STATUS" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict>
					</td>
					</tr> 
					
				</tbody>
			</table>
		</div>
	</form>
	
	<!-- 查询按钮 -->
	<div class="grid comBtnDiv" >
		<ul>
			<li>
				<a href="javascript:void(0);" id="searchFormSubmit" style="margin-right:10px;" class="graybtn1">搜索</a>
			</li>
			<li>
				<a href="javascript:void(0);" id="importBtn" style="margin-right:10px;" class="graybtn1">导入</a>
			</li>
			<li>
				<a href="javascript:void(0);" id="auditByHands" style="margin-right:10px;" class="graybtn1">批量手工审核</a>
			</li>
	    </ul>
		<div style="clear:both;"></div>
	</div>
	<div class="grid" >
<div class="comBtnDiv">
      <form method="POST"  id="out_tid_list" >
<grid:grid from="webpage" ajax="yes" formId="qryOutFrom">
	<grid:header>
		<grid:cell width="10%" ><input type="checkbox" id="checkAlls" />选择</grid:cell>
		<grid:cell >总商订单号</grid:cell>
		<grid:cell >审核次数</grid:cell>
		<grid:cell >审核状态</grid:cell>
		<grid:cell >审核时间</grid:cell>
		<grid:cell >订单抓取状态</grid:cell>
		<grid:cell >订单抓取时间</grid:cell>
		<grid:cell >操作</grid:cell>
	</grid:header>
	<grid:body item="auditlist">
		<grid:cell><input name="lockOrderChk" type="checkbox" value="${auditlist.out_tid}"></grid:cell>
		<grid:cell>${auditlist.out_tid }</grid:cell>
		<grid:cell>${auditlist.audit_num }</grid:cell>
		<grid:cell>${auditlist.audit_status_n }</grid:cell>
		<grid:cell>${auditlist.audit_time }</grid:cell>
		<grid:cell>${auditlist.crawler_status_n }</grid:cell>
		<grid:cell>${auditlist.crawler_time }</grid:cell>
		<grid:cell>
		<c:if test="${auditlist.audit_num >=3 && auditlist.audit_status !='1'}">
		<a href="javascript:void(0);" id="auditByHandOne" style="margin-right:10px;" class="graybtn1" 
		out_tid="${auditlist.out_tid}" onclick="auditByHandOne(this)">人工审核</a>
		</c:if>
		</grid:cell>
	</grid:body>
</grid:grid>
</form>
</div>
</div>
</div>
 

</body>
</html>

<div id="importOutDlg"></div>
<script type="text/javascript">
$(function (){
	Eop.Dialog.init({id:"importOutDlg",modal:true,title:"总部订单号导入", width:"800px"});
	$("#importBtn").click(function(){
		Eop.Dialog.open("importOutDlg");
		 var url= ctx+"/shop/admin/auditZBAction!toImport.do?ajax=yes";
		 $("#importOutDlg").load(url,{},function(){});
	});
	$("#searchFormSubmit").click(function(){
		$("#qryOutFrom").attr('action','auditZBAction!showList.do');
		$("#qryOutFrom").submit();
	})
	$("#checkAlls").bind("click", function() {
		$("input[type=checkbox][name=lockOrderChk]").trigger("click");
	});
	$("#auditByHands").click(function(){
		 
		  var len = $("[name='lockOrderChk']:checked").length;
		  if(len==0){
			  alert("请选择要手工审核的订单");
			  return false;
		  }
		  if(window.confirm("确认审核选中的订单吗?")){
			  var order_idArr = [];
			  $("[name='lockOrderChk']:checked").each(function(){
				 var order_id = $(this).val();
				 order_idArr.push(order_id);
			  });
			  var out_tids = order_idArr.join(",");
			  auditByHand(out_tids);
			  
			  
			 
		  }
			
		 
	  });
});
function auditByHandOne (e){
	var out_tid = e.getAttribute("out_tid");
	if(window.confirm("确认审核选中的订单吗?")){
		 
		  auditByHand(out_tid);
		  
		  
		 
	  }
}

function auditByHand (out_tids){
	$.ajax({
			type : "post",
			async : true,
			url : ctx+"/shop/admin/auditZBAction!auditByHand.do?ajax=yes&out_tids="+out_tids,
			data : {},
			dataType : "json",
			success : function(data) {
				alert(data.message);
				if(data.result==0){
					$("#qryOutFrom").attr('action','auditZBAction!showList.do');
					$("#qryOutFrom").submit();
				}
				
			}
			
		});
}
</script>
	

