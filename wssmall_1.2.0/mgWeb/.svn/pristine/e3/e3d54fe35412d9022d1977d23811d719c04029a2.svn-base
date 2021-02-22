<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.distributorL {
	width: 65%;
	float: left;
}

.distributorR {
	width: 35%;
	float: left;
}

.noborder {
	border-top-style: none;
	border-right-style: none;
	border-left-style: none;
	border-bottom-style: none;
}
</style>
<style>
.progressContainer {
	float: left;
	padding-left: 10px;
	width: 50px;
}

.proWrapper {
	border: solid 1px #BBDDE5;
	width: 50px;
	height: 50px;
	float: left;
	margin: 5px 5px 5px 5px;
}

.uploadImg {
	width: 50px;
	height: 50px;
}

.progressState {
	margin-top: 15px;
	height: 20px;
	background-color: #E8F2FE;
}

.progressText {
	margin-top: -18px;
	padding-left: 30px
}

.imgPrivew {
	background: #F7F6F6;
	border: 2px solid #5CA647;
	margin-bottom: 10px;
	padding: 2px;
	width: 280px;
	height: 250px;
	text-align: center;
}

.deleteBtn {
	letter-spacing: 25px;
	width: 50px;
	height: 25px;
	background-image: url('${ctx}/themes/default/images/icon_drop.gif');
	background-repeat: no-repeat;
	background-position: top center;
}

* html .deleteBtn {
	display: block;
}

ul,li {
	list-style: none;
}

.input_panel {
	float: left
}

.input_panel ul {
	margin: 1px;
	width: 100%;
	float: left;
}

.input_panel ul li {
	float: left;
	border: 1px solid #EFEFEF;
}

.input_panel ul li.text {
	background-color: #F8FAFC;
	width: 150px;
	text-align: right;
	line-height: 25px;
}

.input_panel ul li.input {
	border-left: 0;
	padding-left: 5px;
	width: 350px;
	line-height: 25px;
}

.input_panel ul li.adj_input {
	border-left: 0;
	padding-left: 5px;
	width: 450px;
	line-height: 25px;
}

.input_panel .input_text {
	width: 200px
}

.groupname {
	font-size: 14px
}

.albumbox table tr {
	border-bottom: 0;
}
</style>
<div class="input">
		<table width="100%">
			<tbody>
					<c:forEach items="${modAudit}" var="temp">
							<c:if test="${temp.field_type=='text'}">
								<tr>
									<td>
										${temp.field_name_desc}(修改前):<span class='red'>${temp.old_change }</span>
									</td>
									<td>
										${temp.field_name_desc}(修改后):<span class='red'>${temp.new_change }</span>
									</td>
								</tr>
							</c:if>
							
							<c:if test="${temp.field_type=='img'}">
								<tr>
									<td>
										${temp.field_name_desc}(修改前):
										<div id="album_tab" class="form-table albumbox">
											<table style="width: 40%; height: auto;">
												<tr>
													<td height="179" align="left">
														<div attr='photo_div'>
															<div class="imgPrivew" id="imgPrivew">
																	<img height="220" width="240" src="${temp.old_change }" />
															</div>
														</div>
													</td>
			
												</tr>
											</table>
										</div>
									</td>
									<td>
										${temp.field_name_desc}(修改后):
										<div id="album_tab" class="form-table albumbox">
											<table style="width: 40%; height: auto;">
												<tr>
													<td height="179" align="left">
														<div attr='photo_div'>
															<div class="imgPrivew" id="imgPrivew">
																	<img height="220" width="240" src="${temp.new_change }" />
															</div>
														</div>
													</td>
			
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</c:if>
					</c:forEach>
			</tbody>
		</table>
</div>