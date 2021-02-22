<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="fck"%>
<script type="text/javascript" src="editUserAdmin.js"></script>
<script type="text/javascript" src="selector_staff.js"></script>
<script type="text/javascript" src="selector_agent.js"></script>

<style>
.repwdtr, .pwdtr {
	display: none
}

.subNav {
	border-bottom: solid 1px #e5e3da;
	width: 200px;
	cursor: pointer;
	font-weight: bold;
	font-size: 14px;
	color: #999;
	line-height: 28px;
	padding-left: 10px;
	background-position: 95% 50%
}
.subNavBox{
 width: 100%;
}
.subCheckbox {
	border-bottom: solid 1px #e5e3da;
	width: 200px;
	cursor: pointer;
	font-weight: bold;
	font-size: 14px;
	color: #999;
	line-height: 28px;
	padding-left: 10px;
	background-position: 95% 50%
}

.subNav:hover {
	color: #277fc2;
}

.currentDd {
	color: #277fc2
}

.navContent {
	display: none;
	width: 100%;
}

.navContent ul li {
	float: left;
	width: 200px;
}

.alreadyCheckbox ul li {
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
	<form action="javascript:void(0)"  class="validate" method="post" name="updateForm" id="updateForm">
		<input type="hidden" name="adminUser.userid" value="${adminUser.userid }" />
		<input type="hidden" name="adminUser.usertype" value="${adminUser.usertype }" />
		
		<table  class="form-table">
			<tr height="50px;">
  	     		<td style="text-align: center;font-size: 140%;" colspan="6">
  	     			<span>基本信息</span>
  	     		</td>
			</tr>
			
			<c:if test="${multiSite==1}">
				<tr>
					<th><label class="text">站点：</label></th>
					<td ><select name="adminUser.siteid" id="adminUserSite"/>
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
								 $("#adminUserSite").val(${siteid});
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
		
			<tr>
				<th><label class="text">用户类型：</label></th>
				<td>
					
				    <html:selectdict id="adminUser_founder_select" name="adminUser.founder" curr_val="${adminUser.founder }"
									attr_code="DC_USER_TYPE" style="width:155px" ></html:selectdict>
				</td>
				
				<input type="hidden"  id ="cur_founder" value="${adminUser.founder}" />
				
				<input type="hidden" name="user_no" value="${adminUser.username }"/>
				<th><label class="text"><span class='red'>*</span>用户工号：</label></th>
				<td>
					<input type="text" name="adminUser.username" value="${adminUser.username }"  dataType="string" required="true" id ="username" disabled="true"/>
				</td>
				
				<input type="hidden" name="adminUser.org_id" value="${adminUser.org_id }" id="org_id"/>
				<th><label class="text"><span class='red'>*</span>组织部门：</label></th>
				<td>
					<input type="text" name="adminUser.dep_name" value="${adminUser.dep_name }"  dataType="string" required="true"   id ="dep_name"/>
					     <a class="sgreenbtn" href="#">
				    <span id="refOrgBtn" >关联组织部门</span>
								</a>
				</td>
			</tr>
			
			<tr>
				<th><label class="text"><span class='red'>*</span>用户名称：</label></th>
				<td><input type="text" name="adminUser.realname" value="${adminUser.realname }" required="true"  id="realname" /></td>
				
				<th><label class="text"><span class='red'>*</span>用户手机号码：</label></th>
				<td><input type="text" class="ipttxt"  value="${adminUser.phone_num }" name="adminUser.phone_num"  id="phone_num"  required="true"  /></td>
				  
				<th><label class="text">用户邮箱：</label></th>
			   	<td><input type="text" class="ipttxt"  value="${adminUser.email}" name="adminUser.email"  id="email"   /></td>
			</tr>
		 	
			<tr>
				<c:if test="${adminUser.founder==0}">
					<input type="hidden" vlaue="${adminUser.lan_name }" name="adminUser.lan_name" id="lan_name">
					
					<th><label class="text"><span class='red'>*</span>本地网：</label></th>
					<td>
						<select style="width:160px" id="lan_sel" name = "adminUser.lan_id">
						 	<c:forEach var="lan" items="${lanList}">
						 	<option value="${lan.lan_code}">${lan.lan_name }</option>
						 	</c:forEach>
						</select>	
					</td>		
					
					<script type="text/javascript">
				         $(function(){
				       		$("#lan_sel option[value='${adminUser.lan_id }']").attr("selected", true);
				      });
				    </script>
			 	</c:if>
			
				<th><label class="text">备注：</label></th>
				<td><input type="text" name="adminUser.remark"  value="${adminUser.remark }" /></td>
			</tr>
			
			<tr>
				<th><label class="text">性别：</label></th>
				<td>
					<c:choose>
						<c:when test="${adminUser.sex eq '0'}">
							<input name="adminUser.sex" type="radio" checked="checked" value="0"/>男&nbsp;&nbsp;
							<input name="adminUser.sex" type="radio" value="1" />女
						</c:when>
						<c:when test="${adminUser.sex eq '1'}">
							<input name="adminUser.sex" type="radio" value="0"/>男&nbsp;&nbsp; 
							<input name="adminUser.sex" type="radio" checked="checked" value="1" />女
						</c:when>
						<c:otherwise>
							<input name="adminUser.sex" type="radio"  value="0"/>男&nbsp;&nbsp; 
							<input name="adminUser.sex" type="radio" value="1" />女
						</c:otherwise>
					</c:choose>
				</td>
				  
				<th><label class="text">短信开关：</label></th>
				<td id="td_sms_state">
					<c:choose>
							<c:when test="${adminUser.sms_receive eq '1'}">
				           		<input name="adminUser.sms_receive" type="radio" checked="checked" value="1"/>开&nbsp;&nbsp;
				           		<input name="adminUser.sms_receive" type="radio" value="0" />关
				           	</c:when>
				           	<c:when test="${adminUser.sms_receive eq '0'}">
				           		 <input name="adminUser.sms_receive" type="radio"  value="1"/>开&nbsp;&nbsp; 
				           		 <input name="adminUser.sms_receive" type="radio" checked="checked" value="0" />关
				           	</c:when>
				           	<c:otherwise>
					           <input name="adminUser.sms_receive" type="radio"  value="1"/>开&nbsp;&nbsp; 
					           <input name="adminUser.sms_receive" type="radio" value="0" />关
				           	</c:otherwise>
				       </c:choose>    
				</td>
				
				<th><label class="text">状态：</label></th>
				<td id="td_state">
					<c:choose>
							<c:when test="${adminUser.state eq '1'}">
				           		<input name="adminUser.state" type="radio" checked="checked" value="1"/>启用&nbsp;&nbsp;
				           		<input name="adminUser.state" type="radio" value="0" />禁用
				           	</c:when>
				           	<c:when test="${adminUser.state eq '0'}">
				           		 <input name="adminUser.state" type="radio"  value="1"/>启用&nbsp;&nbsp; 
				           		 <input name="adminUser.state" type="radio" checked="checked" value="0" />禁用
				           	</c:when>
				           	<c:otherwise>
					           <input name="adminUser.state" type="radio"  value="1"/>启用&nbsp;&nbsp; 
					           <input name="adminUser.state" type="radio" value="0" />禁用
				           	</c:otherwise>
				       </c:choose>    
				</td>
			</tr>
			
			<tr>
				<th> </th>
				<td> <input type="checkbox" name="updatePwd" id="updatePwd" value="yes"  />修改密码 </td>
				
				<th class="pwdtr"><label class="text"><span class='red'>*</span>密码：</label></th>
				<td class="pwdtr"><input type="password" name="newPassword" id="pwd"  /></td>
				
				<th class="repwdtr"><label class="text">确认密码：</label></th>
				<td class="repwdtr"><input type="password" id="repwd" dataType="string"  /></td>
				
			</tr>
			
			<tr height="50px;" class="tr_syn_info">
				<td style="text-align: center;font-size: 140%;" colspan="6">
					<span>同步信息</span>
	  	     	</td>
			</tr>
			<tr class="tr_syn_info">
				<input type="hidden" name="is_syn" id="is_syn" value="${adminUser.is_syn }"/>
				<th>组织总部编码</th>
    	      	<td>
    	      		<input type="text" class="ipttxt" id="syn_hq_dept_id" value="${adminUser.syn_hq_dept_id}" title="所属组织总部编码" disabled="true" style='width:160px' />
    	      	</td>
				
				<th>组织省内编码</th>
				<td>
	    	   	    <input type="text" class="ipttxt" id="syn_dept_id" value="${adminUser.syn_dept_id}" title="所属组织省内编码" disabled="true" style='width:160px'/>
				</td>
				
                <th>部门渠道类型</th>
				<td>
	    	   	    <input type="text" class="ipttxt" id="syn_dept_type" value="${adminUser.syn_dept_type}" disabled="true" style='width:160px'/>
				</td>
			</tr>
			
			<tr class="tr_syn_info">
				<th>县区编号</th>
    	      	<td>
    	      		<input type="text" class="ipttxt" id="syn_county_id" value="${adminUser.syn_county_id}" disabled="true" style='width:160px' />
    	      	</td>
				
				<th>工号编号</th>
				<td>
	    	   	    <input type="text" class="ipttxt" id="syn_user_id" value="${adminUser.syn_user_id}" title="外围系统的工号编号" disabled="true" style='width:160px'/>
				</td>
				
                <th>工号标识</th>
				<td>
	    	   	    <input type="text" class="ipttxt" id="syn_employee_id" value="${adminUser.syn_employee_id}" disabled="true" style='width:160px'/>
				</td>
			</tr>
			
			<tr class="tr_syn_info">
				<th>使用域</th>
    	      	<td>
    	      		<input type="text" class="ipttxt" id="syn_use_domain" value="${adminUser.syn_use_domain}" disabled="true" style='width:160px' />
    	      	</td>
				
				<th>同步时间</th>
				<td>
	    	   	    <input type="text" class="ipttxt" id="syn_date" value="${adminUser.syn_date}" title="${adminUser.syn_date}" disabled="true" style='width:160px'/>
				</td>
			</tr>
			
			<!-- 一级分销商 查看自己时候 不显示角色· -->
			<c:if test="${roleList ne null}">
				<tr height="50px;">
	  	     		<td style="text-align: center;font-size: 140%;" colspan="6">
	  	     			<span>角色信息</span>
	  	     		</td>
				</tr>
			
				<tr style="width: 100%">
					<td colspan="6">
						<div class="subNavBox">
							<div style="width:100%;height:100%;margin-bottom: 10px" >
								<div class="subCheckbox" style="margin-left:35px;">已选中</div>
								<div class="navContent " style="display:block">
								<br/>
									<ul id="editAlreadyCheckbox" style="margin-left: 35px; padding-bottom: 10px;">
									</ul>
								</div>
							</div>
							<c:forEach var="role" items="${listMapRoleGroup}">
								<div style="width:100%;height:100%; margin-top: 18px">
									<div class="subNav" style="margin-left: 35px;"><span>${role.pname}</span></div>
									<div class="navContent">
										<div id="editContentSearch_${role.pkey}"
											class="navContentSearch">
											<input id="editSearchText_${role.pkey}" role_group="${role.pkey}" class="editSearchText"  />
											<input type="button" id="edidSubmitGroup_${role.pkey}" role_group="${role.pkey}" name="edidSubmitGroup" class="searchColer" style="cursor: pointer;float: right;" value="搜   索">
										</div>
										<div>
											<ul id="editGroup_${role.pkey}" style="margin-left:35px;margin-top: 18px;padding-bottom: 10px;">
											</ul>
										</div>
										
										<!-- 
										<ul id="editGroupRole_${role.pkey}" style="margin-left:35px;margin-top: 18px;padding-bottom: 10px;">
										</ul>
										
										 -->
									</div>
								</div>
							</c:forEach>
						</div>
					</td>
				</tr>
			</c:if>
			
		</table> 
		
		<div class="submitlist" align="center" style="margin-top: 30px;">
			<input  type="submit"	id="editbtn"  value=" 确  定 " class="submitBtn" style="cursor: pointer;"/>
		</div>
	
	</form>
	
	<div title="职员" id="refStaffDlg"></div>
	
	<div title="职员" id="refOrgDlg"></div>
</div>
<script type="text/javascript">
   $(document).ready(function(){
 	subNav();
 	roleGroup();
 	isCheckBox();
 	initSubmit();
    });
	function subNav() {
			$(".subNav").click(function() {
			$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");

			$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");

			$(this).next(".navContent").slideToggle(300).siblings(".navContent").slideUp(500);
		});
	}
	
	
	// 去空字符
	function Trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
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
					var groupId = Trim("#editGroup_" + typesj);
					var li ="<li class='role_group_"+typesj+"' role_name='"+name+"'><input type='checkbox' id='roleids_"+code+"' name='roleids' value='"+code+"' role_group='"+typesj+"'> <span>"+name+"</span></input></li>";
				    $(groupId).append(li);
						break;
				}
			}
		}
		//选中
		checkBoxs();
	}
	// 去空字符
	function Trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
		
	$("input[name=edidSubmitGroup]").click(function() {
		
		//  分三种情况
		//1、 一个命名为one的ul显示全部数据，一个命名为two的ul为空
		//2、 两个ul都有值
		//3、  一个命名为two的ul显示全部数据，一个命名为one的ul为空
		var role_group_id = $(this).attr("role_group");
		
		var input_id = "editSearchText_"+role_group_id;
		
		var searchText = $("#"+input_id).val();
		
		if(typeof(searchText) == "undefined" || ""==searchText.trim()){
			$(".role_group_"+role_group_id).show();
		}else{
			for(var i=0;i<$(".role_group_"+role_group_id).length;i++){
				var item = $(".role_group_"+role_group_id)[i];
				
				var role_name = $(item).attr("role_name");
				
				if(role_name.indexOf(searchText)>-1){
					$(item).show();
				}else{
					$(item).hide();
					$("#editAlreadyCheckbox").find("li").show();
				}
			}
		}
	});
	
	function checkBoxs(){
		<c:forEach var="userRole" items="${userRoles }">
		//选中checkBox
		$("input[value='${userRole.roleid}']").attr("checked",true);
		
		$("#editAlreadyCheckbox").append($("input[value='${userRole.roleid}']").parent());
		
	    </c:forEach>
	    <c:if test="${adminUser.founder==1}" >
 	    $("#superChk").click()
        </c:if>
        <c:if test="${adminUser.founder==0}" >
	    $("#notSuperChk").click();
       </c:if>
	   }
	
	// 是否选中
	function isCheckBox(){
		$("input[name='roleids']").click(function() {
			var opt = $(this).is(":checked");
			var otpLi=$(this).parent();
			var roleGroup = Trim("#editGroup_"+$(this).attr("role_group"));
			if(opt) {
				 $("#editAlreadyCheckbox").append(otpLi);
			} else {
				 $(roleGroup).append(otpLi);
			}
		});
	}
     
	function  initSubmit(){
	    $("#lan_sel option[value='${adminUser.lan_id }']").attr("selected", true);
	    
		$("form.validate").validate();
		  $("#editbtn").click(function(){
	          return  checkpwd1();
	          });
		$("#notSuperChk").click(function(){
			if(this.checked)
			$("#roletr").show();
		});
		$("#superChk").click(function(){
			if(this.checked)
			$("#roletr").hide();
		});	
		$("#updatePwd").click(function(){
			if(this.checked){
				$(".pwdtr").show();
				$(".repwdtr").show();			
			}else{
				$(".pwdtr").hide();
				$(".repwdtr").hide();
			}
		});
		initEditView();
	}
	
	
	function initEditView(){
		var is_syn = $("#is_syn").val();
		
		if(!"1" == is_syn){
			$(".tr_syn_info").hide();
		}else{
			$("#username").attr("disabled",true);
			$("#dep_name").attr("disabled",true);
			$("#realname").attr("disabled",true);
			
			$("#td_state input").click(function(obj){
				return false;
			});
			
			$("#phone_num").attr("disabled",true);
			$("#lan_sel").attr("disabled",true);
			$(".sgreenbtn").hide();
		}
	}
</script>
