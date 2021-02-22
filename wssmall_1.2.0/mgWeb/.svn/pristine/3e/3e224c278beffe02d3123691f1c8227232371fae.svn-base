<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/auth_input.js?1=1"></script>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />

<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                       <li id="show_click_2" class="selected">
                         <span class="word">
                           <c:if test="${isEdit=='1'}">修改权限</c:if>
                           <c:if test="${isEdit!='1'}">新增权限</c:if>
                         </span>
                         <span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>  
<div class="tab-bar" style="position: relative;">
	<ul class="tab">
		<li tabid="menu" <c:if test="${menuAuth.type=='menu' || (appAuth.type!='app' && btnAuth.type!='btn' && dataAuth.type!='data')}">class="active"</c:if>>菜单权限</li>
		<li tabid="app" <c:if test="${appAuth.type=='app'}">class="active"</c:if>>app权限</li>
		<li tabid="btn" <c:if test="${btnAuth.type=='btn'}">class="active"</c:if>>按钮权限</li>
		<li tabid="data" <c:if test="${dataAuth.type=='data'}">class="active"</c:if>>数据权限</li>
	</ul>
</div>

<form id="authForm" method="post">
	<input type="hidden" name="isEdit" id="isEdit" value="${isEdit }" />
	<input type="hidden" name="authid" id="authid" value="" />
	<input type="hidden" id="menuObjvalue" value="${menuAuth.objvalue }" />
	<input type="hidden" id="appObjvalue" value="${appAuth.objvalue }" />
	<input type="hidden" id="btnObjvalue" value="${btnAuth.objvalue }" />
	<input type="hidden" id="dataObjvalue" value="${dataAuth.actid }" />
	<tr>
		<th><label class="text"> 权限名： </label></th>
		<td><input type="text" class="ipttxt" name="name" id="authname"
			maxlength="60" value="" /></td>
	</tr>
	<div class="input" tabid="menu" <c:if test="${menuAuth.type!='menu' && (appAuth.type=='app' || btnAuth.type=='btn' || dataAuth.type=='data')}">style="display: none;"</c:if>>
		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
			<tr>
				<th><label class="text"> 菜单权限： </label></th>
				<td>
					<div id="menubox">
						<ul class="checktree menuTree">
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div tabid="app" class="input" <c:if test="${appAuth.type!='app'}">style="display: none;"</c:if>>
		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
			<tr>
				<th><label class="text"> app权限： </label></th>
				<td>
					<div id="menubox">
						<ul class="checktree appTree">
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div tabid="btn" class="input" <c:if test="${btnAuth.type!='btn'}">style="display: none;"</c:if>>
		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
			<tr>
				<th><label class="text"> 按钮权限： </label></th>
				<td>
					<div id="menubox">
						<ul class="checktree btnTree">
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div tabid="data" class="input" <c:if test="${dataAuth.type!='data'}">style="display: none;"</c:if>>
		<div id="databox">
						
		</div>
	</div>	
	<div class="submitlist" align="center">
		<table>
			<tr>
				<th></th>
				<td><input type="button" id="authBtn" value=" 确    定  "
					class="submitBtn" /></td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">
	var id;
	var name;
	<c:if test="${menuAuth.actid != '' and menuAuth.actid != null}">
		name = "${menuAuth.name}";
		id = "${menuAuth.actid}";
	</c:if>
	<c:if test="${appAuth.actid != '' and appAuth.actid != null}">
		name = "${appAuth.name}";
		id = "${appAuth.actid}";
	</c:if>
	<c:if test="${btnAuth.actid != '' and btnAuth.actid != null}">
		name = "${btnAuth.name}";
		id = "${btnAuth.actid}";
	</c:if>
	<c:if test="${dataAuth.actid != '' and dataAuth.actid != null}">
		name = "${dataAuth.name}";
		id = "${dataAuth.actid}";
	</c:if>
	$("#authname").val(name);
	$("#authid").val(id);
</script>
