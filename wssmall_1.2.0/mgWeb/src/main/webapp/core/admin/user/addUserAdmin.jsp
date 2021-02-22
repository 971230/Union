<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="addUserAdmin.js"></script>
<script type="text/javascript" src="selector_staff.js"></script>
<script type="text/javascript" src="selector_agent.js"></script>

<style>
.subNav {
	border-bottom: solid 1px #e5e3da;
	width: 200px;
	cursor: pointer;
	font-weight: bold;
	font-size: 14px;
	color: #999;
	line-height: 28px;
	padding-left: 10px;
	background-position: 95% 50%;
}
.subNavBox{
 width: 100%;
}
.subNav:hover {
	color: #277fc2;
}

.currentDd {
	color: #277fc2;
}

.navContent {
	display: none;
	width: 100%;
}

.navContent ul li {
	float: left;
	width: 200px;
}

.navContentSearch {
	width: 245px;
	margin-left: 35px;
	margin-top: 20px;
}

.searchColer {
	width: 50px;
	height: 22px;
	background-color: #277FC2;
	color: white;
	border: 0px;
	border-radius: 5px;
}
</style>

<div class="input">
<form  action="javascript:void(0)" class="validate" method="post" name="theForm" id="theForm" enctype="multipart/form-data">
<table  class="form-table">
<c:if test="${multiSite==1}">
	<tr>
		<th><label class="text">站点：</label></th>
		<td >
		<select  class="ipttxt"  name="adminUser.siteid" id="adminUserSite"/>
		</td>
	</tr>
	
<script>
$(function(){

	$.ajax({
		 type: "GET",
		 url: "../multiSite!listJson.do",
		 data:   "ajax=yes",
		 dataType:'json',
		 success: function(result){
			 if(result.result==0){
				 $("#adminUserSite").selectTree(result.data);
			 }else{
				 alert("站点列表获取失败，请重试");
			 }
	     },
	     error:function(){
			alert("站点列表获取失败");
		 }
	}); 
});
</script>
</c:if>

	<tr height="50px;">
   		<td style="text-align: center;font-size: 140%;" colspan="6">
   			<span>基本信息</span>
   		</td>
	</tr>
			
	<tr id="agentTr" style="display:none">
        <input type ="hidden" id ="adminUser.reltype" id="reltype">
        <input type ="hidden" name ="relcode" id ="relcode">
        <th><label class="text"><span class='red'>*</span>代理商</label></th>
		<td><input type="text" class="ipttxt"  name="partner_name" readonly="readonly"  dataType="string"  id ="partner_name" /> <a class="graybtn1" href="javascript:void(0);"><span id="refAgentBtn" >关联代理商 </span></a></td>
    </tr>
    
	<tr>
		<th><label class="text"><span class='red'>*</span>用户工号：</label></th>
			<td><input type="text" class="ipttxt"  name="adminUser.username"  dataType="string"  id ="username" required="true" /></td>
		<input type="hidden" name="adminUser.userid"/>
		
		<th><label class="text"><span class='red'>*</span>用户名称：</label></th>
		<td><input type="text" class="ipttxt"  name="adminUser.realname"  id="realname"  required="true"  /></td>
		
		<input type="hidden" name="adminUser.org_id" value="${adminUser.org_id }" id="org_id"/>
		<th><label class="text"><span class='red'>*</span>组织部门：</label></th>
		<td>
			<input type="text" name="adminUser.dep_name"  value="${adminUser.dep_name }"  required="true"  dataType="string"  id ="dep_name"/>
			     <a class="graybtn1" href="#">
		    <span id="refOrgBtn" >关联组织部门</span>
						</a>
		</td>
	</tr>
	
	<tr>
	    <th><label class="text"><span class='red'>*</span>用户类型：</label></th>
		<td>
			<html:selectdict name="adminUser.founder" curr_val="${adminUser.founder }"
							attr_code="DC_USER_TYPE" style="width: 165px"  id="founder"></html:selectdict>
		</td>
	
		<th><label class="text"><span class='red'>*</span>工号类型：</label></th>
		<td>
			<html:selectdict name="adminUser.usertype" curr_val="${adminUser.usertype }"
							attr_code="DC_USERID_TYPE" style="width: 165px"  id="user_type"></html:selectdict>
		</td>
		
		<th><label class="text"><span class='red'>*</span>用户手机号码：</label></th>
		<td><input type="text" class="ipttxt"  name="adminUser.phone_num"  id="phone_num"  required="true"  /></td>
	</tr>
	
	<tr>
	   	<th><label class="text">用户邮箱：</label></th>
	   	<td><input type="text" class="ipttxt"  value="" name="adminUser.email"  id="email"   /></td>
	   
		<c:if test="${currFounder==1 or currFounder==0}">
			<th><label class="text"><span class='red'>*</span>本地网：</label></th>
			<td>
			   <input type="hidden" vlaue="${adminUser.lan_name }" name="adminUser.lan_name" id="lan_name">
				<select class="ipttxt"  style="width: 165px" id="lan_sel" name = "adminUser.lan_id" required="true">
					<c:forEach var="lan" items="${lanList}">
					 	<option value="${lan.lan_code}">${lan.lan_name }</option>
					 </c:forEach>
				</select>
				
			</td>		
		</c:if>
		 
		<th><label class="text">备注：</label></th>
		<td><input type="text" class="ipttxt"  name="adminUser.remark"   /></td>
	</tr>
	
	<tr>
	  	<th><label class="text">性别：</label></th>
	  	<td><input name="adminUser.sex"   type="radio" value ="0" <c:if test="${adminUser.sex==0}">checked='true'</c:if>>男 <input name="adminUser.sex"  type="radio" <c:if test="${adminUser.sex==1}">checked='true'</c:if> value="1">女 </td>
	  
	  	<th><label class="text">短信开关：</label></th>
		<td>
			<input type="radio"  name="adminUser.sms_receive" value="1" checked="true">开&nbsp;&nbsp;
			<input type="radio"  name="adminUser.sms_receive" value="0">关 
		</td>
		
	  	<th><label class="text">状态：</label></th>
		<td>
			<input type="radio"  name="adminUser.state" value="1" checked="true">启用&nbsp;&nbsp;
			<input type="radio"  name="adminUser.state" value="0">禁用 
		</td>
	</tr>
	
	<tr>
		<th><label class="text"><span class='red'>*</span>密码：</label></th>
		<td>
			<input type="password"  class="ipttxt"  name="adminUser.password" id="pwd" dataType="string" required="true"  />
		</td>
		
		<th><label class="text">确认密码：</label></th>
		<td>
			<input type="password"  class="ipttxt"  id="repwd" />
		</td>
	</tr>
	
	<tr>
		<th colspan="6" style="text-align: left;">
			<span class='red' style="margin-left: 100px;">密码必须大于8个字符,由大写字母.小写字母.数字.特殊符号中的三项组成,不能包含4个及以上相同字符或连续字符</span>
		</th>
	</tr>
	
	<tr height="70px;">
		<td style="text-align: center;font-size: 140%;" colspan="6">
			<span>角色信息</span>
		</td>
	</tr>
	
	<tr style="width: 100%">
		<td colspan="6">			
			<div class="subNavBox">
			
			<c:forEach var="role" items="${listMapRoleGroup}"> 
			<div style="width:100%;height:100%;margin-top: 18px"">
				<div class="subNav" style="margin-left: 35px;" ><span >${role.pname}</span></div>
				<div class="navContent" >
					<div id="navContentSearch_${role.pkey}" class="navContentSearch">
						<input  id="searchText_${role.pkey}" role_group="${role.pkey}" />
						<input type="button" id="submitGroup_${role.pkey}" role_group="${role.pkey}" name="submitGroup" class="searchColer" style="cursor: pointer;float: right;"value="搜  索">
					</div>
					<ul id="group_${role.pkey}" style="margin-left:35px;margin-top: 18px;padding-bottom: 10px;">
					</ul>
				</div>
			</div>
			</c:forEach>
		</div>
			
		</td>
	</tr>
</table>
  
<div class="submitlist" align="center" style="margin-top: 30px;">
	
	<input type="hidden"  name="adminUser.paruserid" value="${currUserId }" >
	<input  type="submit" id="btn"	 value=" 确  定 "  class="submitBtn" style="cursor: pointer;border-radius: 5px;" /></td>

</div>  


</form>
<div title="职员" id="refStaffDlg"></div>
<div title="代理" id="refAgentDlg"></div>
<div title="职员" id="refOrgDlg"></div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	subNav();
	roleGroup();
});
	function subNav() {
			$(".subNav").click(function() {
			$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");

			$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");

			$(this).next(".navContent").slideToggle(300).siblings(".navContent").slideUp(500);
		});
	}
	
	function roleGroup(){
		var roleArry = new Array();
		var roleGroup = new Array();
		//人员
		<c:forEach items="${roleList}" var= "module">
	    var module = {"code":"${module.roleid}","name":"${module.rolename}","role_group":"${module.role_group}"};
	    roleArry.push(module);
	    </c:forEach>
	    
	    // 分组
		<c:forEach items="${listMapRoleGroup}" var= "role">
	    var module = {"groupid":"${role.pkey}"};
	    roleGroup.push(module);
	    </c:forEach>
	    
		for(var i = 0; i < roleArry.length; i++) {
			for(var j = 0; j < roleGroup.length; j++) {
				
				var typesi = roleArry[i].role_group;
				var typesj = roleGroup[j].groupid;
				var code =roleArry[i].code;
				var name =roleArry[i].name;
				if(typesj == typesi) {
					var groupId = Trim("#group_" + typesj);
					var li ="<li class='add_role_group_"+typesj+"' role_name='"+name+"'><input type='checkbox' name='roleids' value='"+code+"'> <span>"+name+"</span></input></li>";
				$(groupId).append(li);
						break;
				}
			}
		}
	}
	// 去空字符
	function Trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	
	$("input[name=submitGroup]").click(function() {
		
		//  分三种情况
		//1、 一个命名为one的ul显示全部数据，一个命名为two的ul为空
		//2、 两个ul都有值
		//3、  一个命名为two的ul显示全部数据，一个命名为one的ul为空
		var role_group_id = $(this).attr("role_group");
		
		var input_id = "searchText_"+role_group_id;
		
		var searchText = $("#"+input_id).val();
		
		if(typeof(searchText) == "undefined" || ""==searchText.trim()){
			$(".add_role_group_"+role_group_id).show();
		}else{
			for(var i=0;i<$(".add_role_group_"+role_group_id).length;i++){
				var item = $(".add_role_group_"+role_group_id)[i];
				
				var role_name = $(item).attr("role_name");
				
				if(role_name.indexOf(searchText)>-1){
					$(item).show();
				}else{
					$(item).hide();
				}
			}
		}
	});
</script>

<script>
	$(function(){
	
             return false;
		$("#oneAgentChk").attr("checked",true);
		
		$("[attr_founder='ck_founder']").bind("click",function(){
			
			if($(this).val() =="0" && $(this).attr("checked"))
			{
				$("[value='201305061747000080']:checkbox").closest("li").hide();
				$("[value='201305061747000080']:checkbox").removeAttr("checked"); 
				$("[value='201305063335000079']:checkbox").closest("li").show();
				$("[value='201305068463000078']:checkbox").closest("li").show();
				
			}else if($(this).val() =="3"){	//本地网
				$("[value='201305061747000080']:checkbox").closest("li").show();
				$("[value='201305061747000080']:checkbox").attr("checked",true);
				$("[value='201305063335000079']:checkbox").closest("li").hide();
				$("[value='201305068463000078']:checkbox").closest("li").hide();
			}
		})
		
		$("#oneAgentChk").trigger("click");
		
		 
	})
</script>