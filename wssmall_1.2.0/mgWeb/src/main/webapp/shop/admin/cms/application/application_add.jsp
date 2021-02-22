<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/shop/admin/cms/js/application_add.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">应用</span><span
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
			<input type="hidden" name="app.app_id" value="${app.app_id}" />
			<input type="hidden" name="app.style_tpl" value="" />
			<tr>
				<th><label class="text"><span class='red'>*</span>应用名称：</label></th>
				<td><input type="text" class="ipttxt" required="true"
					name="app.app_name" dataType="string"
					value="${app.app_name}" /></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>应用编码：</label></th>
				<td><input type="text" class="ipttxt"
					name="app.app_code" dataType="string" required="true"
					value="${app.app_code}" /></td>
			</tr>
			
			<tr>
				<th><label class="text"><span class='red'>*</span>应用包名：</label></th>
				<td><input type="text" class="ipttxt"
					name="app.app_package" dataType="string" required="true"
					value="${app.app_package}" /></td>
			</tr>
			
			<tr>
				<th><label class="text"><span class='red'>*</span>设备类型：</label></th>
				<td>
					<html:selectdict curr_val="${app.device}"  
							style="width:155px;height:25px;" id="app.device"
							name ="app.device" 
							attr_code="DC_DEVICE_TYPE">
					</html:selectdict>
				</td>
			</tr>

			<tr>
				<th><label class="text">应用描述：</label></th>
				<td><textarea class="ipttxt" name="app.description"
						style="width: 150px;height: 100px;" dataType="string"
						value="${app.description}"></textarea></td>
			</tr>

			<tr>
				<th><label class="text">外系统编码：</label></th>
				<td><input type="text" class="ipttxt"
					name="app.outer_app_code" dataType="string"
					value="${app.outer_app_code}" /></td>
			</tr>
			<tr>
				<th><label class="text">图片：</label></th>
				<td><input type="file" class="ipttxt" name="file" /></td>
			</tr>

<!-- 			<tr> -->
<!-- 				<th><label class="text"><span class='red'>*</span>关联用户：</label></th> -->
<!-- 				<input type="hidden" class="ipttxt" name="appRelUser.user_id" -->
<!-- 					dataType="string" value="${appRelUser.user_id }" /> -->
<!-- 				<td><input type="text" class="ipttxt" required="true" -->
<!-- 					name="appRelUser.user_name" dataType="string" readonly="readonly" -->
<!-- 					value="${appRelUser.user_name}" /> <a class="sgreenbtn" -->
<!-- 					href="javascript:void(0);" id="select_user"><span>选择</span> </a></td> -->
<!-- 			</tr> -->
			
			<tr>
				<th><label class="text">关联页面：</label></th>
				<td><a class="sgreenbtn"
					href="javascript:void(0);" id="select_page"><span>选择</span> </a>
					<div class="top_up_con grid">
						<table class="gridlist">
							<thead>
								<tr>
									<th style="text-align: center;">页面编码</th>
									<th style="text-align: center;">应用名称</th>
									<th style="text-align: center;">页面url</th>
									<th style="text-align: center;">操作</th>
								</tr>
							</thead>
							<tbody>
								<tr id="page_tr">
								</tr>
								<c:forEach var="list" items="${app_page_list}">
									<tr>
										<input type="hidden" name="pageIds" value="${list.page_id}"/>
										<td>${list.page_code}</td>
										<td>${list.page_name}</td>
										<td>${list.page_url}</td>
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
<div id="app_rel_page"></div>
<div id="app_rel_user"></div>