<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>


<div class="rightDiv">
	<%-- <div class="searchformDiv">
   		<a href="javascript:;" style="margin-right:10px;" name="addBtn" class="graybtn1" value="${obj.id}">添加</a>	 
  	</div>  --%> 	
	<form form method="post" id="cust_info_form1"
		action=''>
					<%-- <div class="msg_table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<thead>
						<tr>
							<td>属性编码</td>
							<td>属性名曾</td>
							<td>属性</td>
							<td>操作</td>
						</tr>
						</thead>
						<c:forEach items="${propert.list}" var="debFeeDetNew">
							<tr>
								<td>${propert.cname }</td>
								<td>${propert.ename }</td>
								<td>${propert.value }</td>
								<td> <a href="javascript:;" style="margin-right:10px;" name="addBtn3" id="" class="delPayment" value="${obj.id}">删除</a></td>
							</tr>
						</c:forEach>
						</table>
			</div> --%>
	</form>
	

<script type="text/javascript" >
	function cl(){
	    $("#cres").val("");
	}
	$(function(){
		function jsonBack(responseText){
			if(responseText.result == 0){
				var DebFeeDetNew = responseText.debFeeDetNewList.list;
				for(var i=0;i<DebFeeDetNew.length;i++){
					var tr = $("<tr></tr>");  //document.createElement("tr");
					$("<td></td>").html(DebFeeDetNew[i].brand).appendTo(tr);
					$("<td></td>").html(DebFeeDetNew[i].new_deposit_leavefee).appendTo(tr);
					$("<td></td>").html(DebFeeDetNew[i].new_undividedleave_fee).appendTo(tr);
					$("<td></td>").html(DebFeeDetNew[i].new_spay_fee).appendTo(tr);
					$("<td></td>").html(DebFeeDetNew[i].new_owe_fee).appendTo(tr);
					tr.appendTo("#userlist");
					
				}
			}else{
				 alert(responseText.message); 
			}
		}
	});

		
</script>

