<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="orgUser/userDetail.js"></script>

<div class="searchformDiv">
		<a id="add_btn" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
	    <a id="mod_btn" style="margin-right:10px;" class="graybtn1"><span>修改</span></a>
</div>
<div class="input">
	<form action="javascript:void(0)"  method="post" name="theForm" class="validate" id="userDetailForm" enctype="multipart/form-data">
		<div class="stat_graph">
			<h3>
				<div class="graph_tab">
					<ul>
						<li divId="detail" class="selected"><span class="word">基本信息</span><span
							class="bg"></span></li>
						<li divId="roleInfo"><span class="word">人员角色</span><span
							class="bg"></span></li>
						<div class="clear"></div>
					</ul>
				</div>
			</h3>
		</div>
		<div class="tab-page">
			<div class="detail">
			   <div class="user_msg_table" style="margin-left:2px">
			     <table width="100%" border="0" cellspacing="0" cellpadding="0">
				   <tbody>
				      <input type="hidden" name="adminUser.userid" id ="userid" value="${userid}">
				      <input type='hidden' name="adminUser.state"  id="state" > 
				      <input type="hidden" id="party_id">
					  <tr>
			            <th><label class="text"><span class='red'>*</span>用户类型：</label></th>
			            <td>
			             <select id="user_type"   name ='adminUser.founder' style="width:155px"></select>
			            <!--<html:selectdict name="adminUser.founder"  curr_val="${adminUser.founder }"  attr_code="DC_USER_TYPE" style="width:145px"   id="user_type"></html:selectdict>--></td>
			            <input type ="hidden" name ="adminUser.reltype" id="reltype">
                        <input type ="hidden" name ="adminUser.relcode" id ="relcode">
                        <th name="agentLine" style="display:none"><label class="text"><span class='red'>*</span>代理商</label></th>
		                <td name="agentLine" style="display:none"><input type="text" class="ipttxt"  name="partner_name" readonly="readonly"  dataType="string"  id ="partner_name" /> <a class="graybtn1" href="javascript:void(0);"><span id="refAgentBtn" >关联代理商 </span></a></td>
                      </tr>
                      <tr>
                        <th><label class="text"><span class='red'>*</span>用户工号：</label></th>
		                <td><input type="text" class="ipttxt"  name="adminUser.username"  dataType="string"  id ="user_name" required="true" /></td>
	                    <th><label class="text"><span class='red'>*</span>用户名称：</label></th>
		                <td><input type="text" name="adminUser.realname" class="ipttxt"  required="true"  id="real_name" /></td>
	                  </tr>
	                  <tr id="pwdChkTr"><th></th><td colspan="3"><input type="checkbox" id="updatePwdChk">同时修改密码</td></tr>
	                  <tr id="pwdtr"  >
				           <th><label class="text"><span class='red'>*</span>密码：</label></th>
				           <td><input type="password" class="ipttxt" name="updatePwd" id="pwd"  /></td>
				           <th><label class="text">确认密码：</label></th>
				           <td><input type="password"  class="ipttxt" name="adminUser.password" id="repwd" dataType="string"  /></td>
			          </tr>
			          
			          <tr> 
			            <th><label class="text"><span class='red'>*</span>组织部门：</label></th>
	                    <input type="hidden"  name='adminUser.org_id' id="orgid">
		                <td><input type="text" name="adminUser.dep_name" class='ipttxt' value="${adminUser.dep_name }"  disabled="true" required="true"  dataType="string"  id ="dep_name"/></td>
                        <th><label class="text"><span class='red'>*</span>本地网：</label></th>
                        <td>
                         <html:selectdict curr_val="${lan_id}"  style="width:145px" id="lan_id" name ="adminUser.lan_id"  attr_code="dc_area_lan"></html:selectdict>
	                    </td>
                      </tr>
                      <tr>
                        <th><label class="text">性别：</label></th>
			            <td>
			                <input name="adminUser.sex" class="ipttxt"  type="radio" value ="0" checked="true"/>男
		               		<input name="adminUser.sex" class="ipttxt" type="radio" value="1" />女
		                 </td>
                         <th><label class="text"><span class='red'>*</span>用户手机号码：</label></th>
				         <td><input type="text" class="ipttxt"  value="" name="adminUser.phone_num"  id="phone_num"  required="true"  /></td>
			          </tr>
			          <tr>
			              <th><label class="text">用户邮箱：</label></th>
	                      <td><input type="text" class="ipttxt"  value="" name="adminUser.email"  id="email"/></td>
		                  <th>备注</th>
		                  <td colspan="3"><input type="text" class="ipttxt"  name="adminUser.remark"  id="remark"/></td>
			          </tr>
			          
			       </tbody>
			     </table>
			   </div>
			</div>
			<div id="roleInfo" style="display:none" class="roleInfo">
					<div class="searchformDiv">
					    <input type="button" value="选择角色" id="selRoleBtn" class="graybtn1" style="margin-left: 10px;" />
			        </div>
			     
			      <div class="msg_table grid">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tbody >
								<tr align="center"> 
								   <th style='text-align:center'>角色编码</th>
								   <th style='text-align:center'>角色名称</th>
								   <th style='text-align:center'>编辑</th>
								   
								</tr>
							</tbody>
							<tbody id="roleBody">
							</tbody>
						</table>
				  </div>
			</div>
				<div class="submitlist" align="center" id="addSaveDiv" >
					<table>
						<tr>
							<th></th>
							<td id="addSaveTd">
							 <input  type="button"	id="addSaveBtn"  value="保存" class="graybtn1"/>
							 <input  type="button"	id="editSaveBtn"  value="保存" class="graybtn1"/>
							 <input  type="button"	id="cancelBtn"  value="取消" class="graybtn1" />
							</td>
						</tr>
					</table>
				</div>
			 </div>
	</form>
</div>

