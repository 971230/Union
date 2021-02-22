<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="orgUser/userDetail.js"></script>

<div class="searchformDiv">
	    <a id="mod_btn" style="margin-right:10px;cursor: pointer;" class="graybtn1"><span>修改</span></a>
</div>

<div class="input">
	<form action="javascript:void(0)"  method="post" name="theForm" class="" id="userDetailForm" enctype="multipart/form-data">
		<div class="stat_graph">
			<h3>
				<div class="graph_tab">
					<ul>
						<li divId="detail" class="selected"><span class="word">用户信息</span><span
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
							<input type="hidden" name="adminUser.lan_id"  id="lan_id"/>
				   	  
							<c:if test="${source_from!='WSSMALL' }">
								<input type="hidden" name="adminUser.lan_name"  id="lan_name"/>
							</c:if>
				   	  
							<input type="hidden" name="user_no" id="user_no">
				      
				      		<input type="hidden" name="adminUser.userid" id ="userid" value="${userid}">
				      
							<input type="hidden" id="party_id">
							
							<tr height="50px;">
				  	     		<td style="text-align: center;font-size: 140%;" colspan="4">
				  	     			<span>基本信息</span>
				  	     		</td>
							</tr>
				      
					  		<tr>
								<th><label class="text"><span class='red'>*</span>用户类型：</label></th>
			            		<td>    
					           		<html:selectdict name="adminUser.founder"  curr_val="${adminUser.founder }"  attr_code="DC_USER_TYPE" style="width:145px" disabled="true" id="user_type"></html:selectdict></td>
					            	<input type ="hidden" name ="adminUser.reltype" id="reltype">
		                        	<input type ="hidden" name ="adminUser.relcode" id ="relcode">
                      		</tr>
                      		
                      		<tr>
                        		<th><label class="text"><span class='red'>*</span>用户工号：</label></th>
		                		<td><input type="text" class="ipttxt"  name="adminUser.username"  dataType="string"  id ="user_name" required="true" /></td>
		                		
	                    		<th><label class="text"><span class='red'>*</span>用户名称：</label></th>
		                		<td><input type="text" name="adminUser.realname" class="ipttxt"  required="true"  id="real_name" disabled="true" /></td>
	                  		</tr>
	                  		
	                  		<tr id="pwdChkTr">
	                  			<th></th>
	                  			<td colspan="3"><input type="checkbox" id="updatePwdChk">同时修改密码</td>
	                  		</tr>
	                  		
	                  		<tr id="pwdtr"  >
				           		<th><label class="text"><span class='red'>*</span>密码：</label></th>
				           		<td><input type="password" class="ipttxt" name="updatePwd" id="pwd"  /></td>
				           
				           		<th><label class="text">确认密码：</label></th>
				           		<td><input type="password"  class="ipttxt" name="newPassword" id="repwd" dataType="string"  /></td>
			          		</tr>
			          
			          		<tr id="usertype">
								<th><label class="text"><span class='red'>*</span>工号类型：</label></th>
								<td>
									<html:selectdict name="adminUser.usertype" curr_val="${adminUser.usertype }"
										attr_code="DC_USERID_TYPE" style="width:155px"  id="adminuser_type"></html:selectdict>
								</td>
			          		</tr>
			          
			          		<tr> 
			            		<th><label class="text"><span class='red'>*</span>组织部门：</label></th>
	                    		<input type="hidden"  name='adminUser.org_id' id="orgid">
		                		<td>
		                			<input type="text" name="adminUser.dep_name" class='ipttxt' value="${adminUser.dep_name }"  readonly="readonly" required="true"  dataType="string"  id ="dep_name"/>
		                		</td>
                         
	                     		<th><label class="text"><span class='red'>*</span>状态：</label></th>
	                     		<td>
	                       			<select name="adminUser.state" class="ipttxt" style="width:145px;" required="true" id="userstate" disabled="true" >
										<option value="">--请选择--</option>
			                          	<option value="1">启用</option>
			                          	<option value="0">禁用</option>
	                       			</select>
	                     		</td>
                      		</tr>
                      		
                      		<tr>
                        		<th><label class="text">性别：</label></th>
			            		<td id="td_sex">
			                		<input name="adminUser.sex" class="ipttxt"  type="radio" value ="0" checked="true" disabled="true" />男
		               				<input name="adminUser.sex" class="ipttxt" type="radio" value="1" disabled="true" />女
		                 		</td>
		                 		
                         		<th><label class="text"><span class='red'>*</span>用户手机号码：</label></th>
				         		<td><input type="text" class="ipttxt"  value="" name="adminUser.phone_num"  id="phone_num"  required="true" disabled="true" /></td>
			          		</tr>
			          		
			          		<tr>
			              		<th><label class="text">用户邮箱：</label></th>
	                      		<td><input type="text" class="ipttxt"  value="" name="adminUser.email"  id="email" disabled="true"/></td>
		                  
		                  		<th>备注</th>
		                  		<td colspan="3"><input type="text" class="ipttxt"  name="adminUser.remark"  id="remark" disabled="true" /></td>
			          		</tr>
			          		
			          		<c:if test="${source_from=='WSSMALL' }">
				          		<tr>
				              		<th><label class="text">是否登录代理商：</label></th>
		                      		<td>
		                      			<input type="radio" class="ipttxt"  value="yes" name="adminUser.col10" checked="true"/>是
		                      			<input type="radio" class="ipttxt"  value="no" name="adminUser.col10" />否
		                      		</td>
		                      		
			                  		<th>本地网:</th>
			                  		<td colspan="3">
			                  			<input type="hidden" vlaue="${adminUser.lan_name }" name="adminUser.lan_name" id="lan_name">
			                  			<select  class="ipttxt" style="width:145px;" name = "adminUser.lan_id" id="adminUser.lan_id">
			                  		 		<c:forEach var="lan" items="${lanList}">
									   			<option value="${lan.lan_code}" >${lan.lan_name }</option>
									 		</c:forEach>
			                  			</select>
			                  		</td>
				          		</tr>
			           		</c:if>
			           		
			           		<tr height="50px;" class="tr_syn_info">
								<td style="text-align: center;font-size: 140%;" colspan="4">
									<span>同步信息</span>
					  	     	</td>
							</tr>
							<tr class="tr_syn_info">
								<input type="hidden" name="is_syn" id="is_syn" />
								<th>组织总部编码</th>
				    	      	<td>
				    	      		<input type="text" class="ipttxt" id="syn_hq_dept_id" title="所属组织总部编码" disabled="true" style='width:160px' />
				    	      	</td>
								
								<th>组织省内编码</th>
								<td>
					    	   	    <input type="text" class="ipttxt" id="syn_dept_id" title="所属组织省内编码" disabled="true" style='width:160px'/>
								</td>
							</tr>
							
							<tr class="tr_syn_info">
								<th>部门渠道类型</th>
								<td>
					    	   	    <input type="text" class="ipttxt" id="syn_dept_type" disabled="true" style='width:160px'/>
								</td>
								
								<th>县区编号</th>
				    	      	<td>
				    	      		<input type="text" class="ipttxt" id="syn_county_id" disabled="true" style='width:160px' />
				    	      	</td>
							</tr>
							
							<tr class="tr_syn_info">
								<th>工号编号</th>
								<td>
					    	   	    <input type="text" class="ipttxt" id="syn_user_id" title="外围系统的工号编号" disabled="true" style='width:160px'/>
								</td>
								
				                <th>工号标识</th>
								<td>
					    	   	    <input type="text" class="ipttxt" id="syn_employee_id" disabled="true" style='width:160px'/>
								</td>
							</tr>
							
							<tr class="tr_syn_info">
								<th>使用域</th>
				    	      	<td>
				    	      		<input type="text" class="ipttxt" id="syn_use_domain" disabled="true" style='width:160px' />
				    	      	</td>
								
								<th>同步时间</th>
								<td>
					    	   	    <input type="text" class="ipttxt" id="syn_date" disabled="true" style='width:160px'/>
								</td>
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
								<th style='text-align:center'>是否存在数据权限</th>
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
							<input  type="button"	id="addSaveBtn"  value="保存" class="graybtn1" style="cursor: pointer;"/>
							<input  type="button"	id="editSaveBtn"  value="保存" class="graybtn1" style="cursor: pointer;"/>
							<input  type="button"	id="cancelBtn"  value="取消" class="graybtn1" style="cursor: pointer;" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
$(".tr_syn_info").hide();
</script>