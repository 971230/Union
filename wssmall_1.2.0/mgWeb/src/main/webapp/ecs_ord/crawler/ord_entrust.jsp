<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.noborder {
    border-style: none;
}
-->
</style>
<script type="text/javascript">
function selectObj (cobj){
	/* var order_id = cobj.attr("order_id"); */
	var order_id = $("input[id='order_id']").val();
	var url = ctx+"/shop/admin/ordAuto!entrust.do?ajax=yes";
	var name="";
	var obj = $("input[id='obj_name']");
	if(obj && obj.val()){
		name = obj.val();
	}
	$("#order_btn_event_dialog").load(url,{"obj_name":name, "order_id":order_id});
}

$(function() {
	$("#obj_select_confirm_btn").bind("click",function(){
		var cobj = $("input[type='radio'][name='radionames']:checked");
		addSelectObj(cobj);
	});
	$("#search_btn").bind("click",function(){
		var cobj = $("input[type='radio'][name='radionames']:checked");
		selectObj(cobj);
	});
});

function addSelectObj (cobj){
	//var order_id = cobj.attr("order_id");
	var order_id = $("#order_id").val();
	var obj_id = $("input[type='radio'][name='radionames']:checked").val();
	if(obj_id == undefined){
		alert("请选择一个对象");
		return ;
	}
	var obj_ids = obj_id.split("##");
	var uid = obj_ids[0]!=undefined?$.trim(obj_ids[0]):"";
	var una = obj_ids[1]!=undefined?$.trim(obj_ids[1]):"";
	var rna = obj_ids[2]!=undefined?$.trim(obj_ids[2]):"";
	$.ajax({
		type : "post",
		async : true,
		url : "ordAuto!entrust_save.do?ajax=yes&order_id="+order_id+"&userid="+uid+"&username="+una+"&realname="+rna,
		data : {},
		dataType : "json",
		success : function(data) {
			alert(data.message);
		}
	});
	Eop.Dialog.close("order_btn_event_dialog");
}


</script>

<form method="POST" id="order_list_fm0">
<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>
		用户名称：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="obj_name" id="obj_name" value="${obj_name }"/>
		<input type="hidden" id="order_id" name="order_id" value="${order_id }"/>
		<a href="javascript:void(0)" id="search_btn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>
</div>
<div class="grid">
		<grid:grid from="webpage" ajax="yes" formId="order_list_fm0">
			<grid:header>
				<grid:cell style="width: 60px;">选择</grid:cell>
				<grid:cell style="width: 100px;">用户名</grid:cell>
				<grid:cell style="width: 100px;">性别</grid:cell>
				<grid:cell style="width: 80px;">登录状态</grid:cell>
			</grid:header>
			<grid:body item="userList" >
				<grid:cell>
					<c:choose>
						<c:when test="${userList.userid == orderTree.orderExtBusiRequest.lock_user_id }">
							<input order_id="${order_id }" type="radio" id="radios_"+${userList.userid } checked="checked"
							name="radionames" value="${userList.userid }##${userList.username }
							##${userList.realname}##${userList.sex }##${userList.login_status } " />
						</c:when>
						<c:otherwise>
							<input order_id="${order_id }" type="radio" id="radios_"+${userList.userid } 
							name="radionames" value="${userList.userid }##${userList.username }
							##${userList.realname}##${userList.sex }##${userList.login_status } " />
						</c:otherwise>
					</c:choose>
				</grid:cell>
				<grid:cell>${userList.realname }</grid:cell>
				<grid:cell>
					<c:if test="${userList.login_status==0 }">女</c:if>
        			<c:if test="${userList.login_status==1 }">男</c:if>
					<input type="hidden" id="login_status" value="${userList.sex }" />
				</grid:cell>
				<grid:cell>
					<c:if test="${userList.login_status==0 }">未登录</c:if>
        			<c:if test="${userList.login_status==1 }">已登录</c:if>
					<input type="hidden" id="login_status" value="${userList.login_status }" />
				</grid:cell>
			</grid:body>
		</grid:grid>
</div>

<div class="submitlist" style="width: 50%;">
	<table style="width: 50%;">
		<tr>
			<td style="text-align: center;">
				<a href="javascript:void(0)" id="obj_select_confirm_btn" style="margin-right:10px;" class="graybtn1"><span>确定</span></a>
			</td>
		</tr>
	</table>
</div>	</form>