<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/shop/admin/cms/js/app_agent_add.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">用户组关联应用</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input mk_content">
	<form action="javascript:void(0);" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
			<input type="hidden" name="app.app_id"
				value="${appRelUser.rel_id}" />
			<tr>
				<th><label class="text"><span class='red'>*</span>关联用户组：</label></th>
				
				<input type="hidden" class="ipttxt" name="appRelUser.user_id"
					dataType="string" value="${appRelUser.user_id}" />
					
				<input type="hidden" class="ipttxt" name="appRelUser.partner_id"
					dataType="string" value="${appRelUser.partner_id}" />
					
				<td><input type="text" class="ipttxt" required="true"
					name="appRelUser.user_name" dataType="string" readonly="readonly"
					value="${appRelUser.user_name}" /> <a class="sgreenbtn"
					href="javascript:void(0);" id="select_user"><span>选择</span> </a></td>
			</tr>
			
			<tr>
				<th><label class="text">关联应用：</label></th>
				<td><a class="sgreenbtn"
					href="javascript:void(0);" id="select_app"><span>选择</span> </a>
					<div class="top_up_con grid">
						<table class="gridlist">
							<thead>
								<tr>
									<th style="text-align: center;">应用名称</th>
									<th style="text-align: center;">应用编码</th>
									<th style="text-align: center;">外系统编码</th>
									<th style="text-align: center;">操作</th>
								</tr>
							</thead>
							<tbody>
								<tr id="page_tr">
								</tr>
								<c:forEach var="list" items="${app_list}">
									<tr>
										<input type="hidden" name="appIds" value="${list.app_id}"/>
										<td>${list.app_name}</td>
										<td>${list.app_code}</td>
										<td>${list.outer_app_code}</td>
										<td><a href="javascipt:void(0);" class="del_page">删除</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div></td>
					
					
					</td>
			</tr>
		</table>
		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td><input type="submit" id="submitAppInfo" value=" 确  定 "
						class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div id="app_page_list"></div>
<div id="app_rel_user"></div>