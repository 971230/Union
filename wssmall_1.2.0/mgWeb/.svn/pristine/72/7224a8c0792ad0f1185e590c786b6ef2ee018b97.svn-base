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
				<th><label class="text"><span class='red'>*</span>推荐人姓名：</label></th>
				<td><input type="text" class="ipttxt" name="spreadMember.name"
					dataType="string" required="true" value="${spreadMember.name}" />
				</td>
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
			
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td><input type="submit" id="submitSpread" value=" 确  定 "
						class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	$(function(){
		
		$("#submitSpread").bind("click",function(){
			$("#theForm.validate").validate();
			var phone_num = $("input[name='spreadMember.mobile']").val().trim();
			var bank_account = $("input[name='spreadMember.bank_account']").val().trim();
			
			if (/^[0-9]{11}$/.test(phone_num) == false) {
				alert("电话号码只能是11位的数字组成");
				return;
			}
			
			if(bank_account != ""){
				if (/^[0-9]/.test(bank_account) == false) {
					alert("银行卡号必须由数字组成");
					return;
				}
			}
			
			var url = ctx+ "/shop/admin/spread!updateSpread.do?ajax=yes";
			Cmp.ajaxSubmit('theForm', '', url, {}, SpreadEdit.jsonBack,'json');
		});
	});
	
	
	var SpreadEdit = {
			jsonBack:function(responseText){
				if (responseText.result == 1) {
					alert("操作成功");
					window.location.href="spread!listMember.do";
				}
				if (responseText.result == 0) {
					alert(responseText.message);
				}	
			}
	}

</script>