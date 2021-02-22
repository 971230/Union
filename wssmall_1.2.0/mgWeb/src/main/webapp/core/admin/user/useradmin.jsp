<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript" src="../js/PasswordOperator.js"></script>

<body>
<form method="post" id="serchform" action='userAdmin.do'>
 <div class="searchformDiv">
 <table>
	<tr>
		<td style="width: 130px;">
         	地市:
         	<select class="ipttxt" id="select_lan_id" name="lan_id" style="width:90px">
         	</select>
         	<script type="text/javascript">
	          	$.ajax({
	               	url:ctx+"/core/admin/org/orgAdmin!getPermissionRegionCounty.do?ajax=yes",
	               	dataType:"json",
	               	async:false,
	               	data:{},
	               	success:function(reply){
	               		if(typeof(reply) != "undefined" && typeof(reply["lan"]) != "undefined" &&
	               				typeof(reply["region"]) != "undefined"){
	               			regions = reply["lan"];
	               			countys = reply["region"];
	               			
	               			$("#select_lan_id").empty();
	               			
	               			$("#select_lan_id").append("<option value=''></option>");
	               			
	               			for(var i=0;i<regions.length;i++){
	               				var option = "<option value="+regions[i].lan_id+">"+regions[i].lan_name+"</option>";
	               				
	               				$("#select_lan_id").append(option);
	               			}
	               		}
	               	},
	               	error:function(msg){
	               		alert(msg);
	               	}
	          	});
	         	
	          	$("#select_lan_id").val(${lan_id});
			</script>
		</td>
	
		<th>用户工号：</th>
		<td><input type="text" class="ipttxt"  name="username"  value="${username}"/></td>
		
		<th>用户名称：</th>
		<td><input type="text" class="ipttxt"  name="realname"  value="${realname}"/></td>
		
		<th>启用状态：</th>
		<td>
			<html:selectdict curr_val="${state}"  style="width:90px" id="state" name ="state"  attr_code="DC_USER_ADMIN_STATE"></html:selectdict>
		</td>
		
		<c:if test="${currFounder==1||currFounder==0}">
		 <th>用户类型：</th>
		 <td>
		   <html:selectdict curr_val="${usertype}" style="width:90px"  name ="usertype" id="usertype"  attr_code="DC_USER_TYPE" appen_options='<option value="-1">--请选择--</option>'></html:selectdict>
		</td>
		</c:if>
		
		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
	</tr>
	
	<div style="clear:both"></div>
</table>	
</div>
</form>

<div class="grid" id="goodslist">

<div class="comBtnDiv">
	<a style="margin-right:10px;cursor: pointer;" class="graybtn1" onclick="addUser();"><span>添加</span></a>
</div>

<form method="POST"  id="userform" >
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
	
		<grid:cell width="200px">用户工号</grid:cell>
		<grid:cell width="200px">用户名称</grid:cell>
		<grid:cell width="150px">用户类型</grid:cell>
		<grid:cell width="150px">工号类型</grid:cell>
		<grid:cell width="150px">状态</grid:cell>
		<grid:cell width="200px">创建时间</grid:cell>
		<grid:cell width="100px">同步工号</grid:cell>
		<grid:cell width="200px">操作</grid:cell>
	</grid:header>
	<grid:body item="userAdmin">
		<grid:cell>&nbsp;${userAdmin.username }
		</grid:cell>
		<grid:cell>&nbsp;${userAdmin.realname } </grid:cell>
		<grid:cell>
         <c:if test="${userAdmin.founder==0}">${typeMap.manager}</c:if>
         <c:if test="${userAdmin.founder==1}">${typeMap.admin}</c:if>
         <c:if test="${userAdmin.founder==2}">${typeMap.second_partner}</c:if>
         <c:if test="${userAdmin.founder==3}">${typeMap.partner}</c:if>
         <c:if test="${userAdmin.founder==4}">${typeMap.supplier}</c:if>
         <c:if test="${userAdmin.founder==5}">${typeMap.supplier_employee}</c:if>
        </grid:cell>
        <grid:cell>
         <c:if test="${userAdmin.usertype==1}">系统工号</c:if>
         <c:if test="${userAdmin.usertype==2}">接口工号</c:if>
         <c:if test="${userAdmin.usertype==3}">领取工号</c:if>
        </grid:cell>
		<grid:cell> 
		 <c:if test="${userAdmin.state==1}">启用</c:if>
		 <c:if test="${userAdmin.state==0}">禁用</c:if>
		</grid:cell>
		
		<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${userAdmin.create_time}"></html:dateformat></grid:cell>
		
		<grid:cell>
			<c:choose>
				<c:when test="${userAdmin.is_syn==1}">是</c:when>
				<c:otherwise>否</c:otherwise>
			</c:choose>
		</grid:cell> 
		
		<grid:cell>&nbsp;
			<c:if test="${(userAdmin.founder!=1 && userAdmin.founder!=3 && userAdmin.usertype!=2 && userAdmin.usertype!=3) || currUserId=='1'}">
				<a title ="编辑员工信息" onclick="modUser('${userAdmin.userid}')" class="p_prted">修改</a>
				
				<c:if test="${userAdmin.fail_logincount >= limitFailCount}">
            		<span class='tdsper'></span><a title="解冻员工账号" href="userAdmin!cleanFailCount.do?id=${userAdmin.userid }" style="margin-right:10px;" class="p_prted" >解冻</a>
            	</c:if>
			</c:if>
		</grid:cell>
	</grid:body>
</grid:grid>
</form>
</div>

<div id="AddDiag"></div>
<div id="ModDiag"></div>

</body>
<!-- userAdmin!delete.do?id=${userAdmin.userid }" onclick="javascript:return confirm('确认删除此管理员吗？')" -->
 <script type="text/javascript">
 
$(function(){
    $("#state option[value='${state}']").attr("selected", true);
    if(${currFounder}==1 || ${currFounder}==0){
      //$("#usertype option[value='${usertype}']").attr("selected", true);
     }
});

function modUser(id){
	Eop.Dialog.init({id:"ModDiag",modal:false,title:"修改用户",width:"1200px"});
	
	var url = app_path + "userAdmin!edit.do?ajax=yes&id=" + id;
	
	Eop.Dialog.open("ModDiag");
	$("#ModDiag").load(url);
	
	$("#dlg_ModDiag").css("opacity","1");
}

function addUser(){
	Eop.Dialog.init({id:"AddDiag",modal:false,title:"新增用户",width:"1200px"});
	
	var url = app_path + "userAdmin!add.do?ajax=yes";
	
	Eop.Dialog.open("AddDiag");
	$("#AddDiag").load(url);
	
	$("#dlg_AddDiag").css("opacity","1");
}

function del(id){
 
		  var self =this;
         if(window.confirm('确认要删除此工号？')){
		  var url =  "userAdmin!delete.do?ajax=yes&id="+id;
	      
	    Cmp.ajaxSubmit('userform', '', url, {},  function jsonback(responseText) { // json回调函数
	   
		if (responseText.result == 1) {
			alert(responseText.message);
		     window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);	
		  
		   window.location="userAdmin.do?state=2&usertype=1";
		}
	}, 'json');

	}
	

}
</script>
