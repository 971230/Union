<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/js/spread_add.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">添加推广人</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0);" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
			<input type="hidden" name="spreadMember.spread_id"
				value="${spreadMember.spread_id}" />
			<tr>
				<th><label class="text">父级推荐人：</label></th>
				<input type="hidden" class="ipttxt" id="parent_spread_id" name="spreadMemberGrade.parent_id" dataType="string" value="" />
				<td><input type="text" class="ipttxt" name="" id="parent_name" dataType="string" readonly="readonly" value="" />
					<a class="sgreenbtn" href="javascript:void(0);" id="addParent"><span>选择推荐人</span>
				</a></td>
			</tr>


			<tr>
				<th><label class="text"><span class='red'>*</span>推荐人姓名：</label></th>
				<td><input type="hidden"  name="spreadMember.vested_type" />
				<input type="hidden"  name="spreadMember.vested_object" />
				<input type="text" class="ipttxt" name="spreadMember.name"
					dataType="string" id="spreadName" required="true" value="${spreadMember.name}" />
					<a class="sgreenbtn" href="javascript:void(0);" id="addSpreader"><span>选择推荐人</span>
				</a></td>
			</tr>
			
			<tr>
				<th><label class="text"><span class='red'>*</span>推荐人手机：</label></th>
				<td><input type="text" class="ipttxt"
					name="spreadMember.mobile" dataType="string" required="true"
					value="${spreadMember.mobile}" /></td>
			</tr>
			
			<tr>
				<th><label class="text">开户银行：</label></th>
				<td><select class="ipttxt" style="width: 100px"
					name="spreadMember.bank_name">
						<c:forEach var="list" items="${bankList}">
							<option value="${list.pkey }"
								${spreadMember.bank_name == list.pkey ? ' selected="selected" ' : ''}>${list.pname
								}</option>
						</c:forEach>
				</select></td>
			</tr>
			
			<tr>
				<th><label class="text">开户姓名：</label></th>
				<td><input type="text" class="ipttxt" name="spreadMember.bank_account_name"
					dataType="string"  value="${spreadMember.bank_account_name}" />
				</a></td>
			</tr>
			
			<tr>
				<th><label class="text">开户人银行账号：</label></th>
				<td><input type="text" class="ipttxt" name="spreadMember.bank_account"
					dataType="string"  value="${spreadMember.bank_account}" />
				</a></td>
			</tr>
			
			<tr>
				<th><label class="text">身份证：</label></th>
				<td><input type="text" class="ipttxt" name="spreadMember.spread_identity"
					dataType="string"  value="${spreadMember.spread_identity}" />
				</a></td>
			</tr>
			
			<tr>
				<th><label class="text">地址：</label></th>
				<td><input type="text" class="ipttxt" name="spreadMember.spread_address"
					dataType="string"  value="${spreadMember.spread_address}" />
				</a></td>
			</tr>
			
			<tr>
				<th><label class="text">邮编：</label></th>
				<td><input type="text" class="ipttxt" name="spreadMember.spread_zip"
					dataType="string"  value="${spreadMember.spread_zip}" />
				</a></td>
			</tr>
			
			<tr>
				<th><label class="text">外系统用户id：</label></th>
				<td><input type="text" class="ipttxt" name="spreadMember.ucode"
					dataType="string"  value="${spreadMember.ucode}" />
				</a></td>
			</tr>
			
			<tr>
				<th><label class="text">推荐人模式：</label></th>
				<td><select class="ipttxt" style="width: 100px"
					name="spreadMemberGrade.spread_mod">
						<c:forEach var="list" items="${modList}">
							<option value="${list.pkey }"
								${spreadMemberGrade.spread_mod == list.pkey ? ' selected="selected" ' : ''}>${list.pname
								}</option>
						</c:forEach>
				</select></td>
			</tr>
			
			
			<tr>
				<th><label class="text"><span class='red'>*</span>自动提交财务申请：</label></th>
				<td><select class="ipttxt" style="width: 100px"
					name="spreadMemberGrade.auto_apply_fee">
						<option value="1">是</option>
						<option value="0">否</option>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>推荐业务类型：</label></th>
				<td><select class="ipttxt" style="width: 100px"
					name="spreadMemberGrade.service_type">
						<c:forEach var="list" items="${serverTypeList}">
							<option value="${list.pkey }"
								${spreadMemberGrade.service_type == list.pkey ? ' selected="selected" ' : ''}>${list.pname
								}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>推荐业务信息：</label></th>
				<td><a class="sgreenbtn" href="javascript:void(0);" style="display: none;" id="addServer"><span>添加</span></a>
					<div class="top_up_con">
						<table class="gridlist" style="width: 600px;">
							<thead>
								<tr>
									<th style="text-align: center;">业务名称</th>
									<th style="text-align: center;">业务编码</th>
									<th style="text-align: center;">业务等级</th>
									<th style="text-align: center;">操作</th>
								</tr>
							</thead>
							<tbody>
								<tr id="goods_tr">
								</tr>
							</tbody>
						</table>
					</div></td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td><input type="submit" id="submitSpreadInfo" value=" 确  定 "
						class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div  id="spread_dialog"></div>
<div  id="parent_dialog"></div>
<div  id="server_dialog"></div>
<div  id="grade_list_div"></div>