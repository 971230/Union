<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">服务操作</span><span
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
			<input type="hidden" name="serviceOffer.service_id" value="${serviceOffer.service_id}"/>
			<tr>
				<th><label class="text"><span class='red'>*</span>服务名称：</label></th>
				<td><input type="text" class="ipttxt" name="serviceOffer.service_offer_name"
					dataType="string" required="true" value="${serviceOffer.service_offer_name}"/></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>服务描述：</label></th>
				<td><input type="text" class="ipttxt" name="serviceOffer.remark"
					dataType="string" required="true" value="${serviceOffer.remark}"/></td>
			</tr>

			<tr>
				<th><label class="text"><span class='red'>*</span>服务编码：</label></th>
				<td><input type="text" class="ipttxt" name="serviceOffer.service_code"
					dataType="string" required="true" value="${serviceOffer.service_code}"/></td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td> <input type="submit" id="btn"
						value=" 确  定 " class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	$(function(){
		
		$(".submitBtn").bind("click",function(){
			$("#theForm.validate").validate();
			var url = ctx+ "/shop/admin/sysRule!saveServer.do?ajax=yes";
			Cmp.ajaxSubmit('theForm', '', url, {}, AddServer.jsonBack,'json');
		});
	});
	
	
	var AddServer = {
		jsonBack:function(responseText) { 
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href="sysRule!listServer.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
	}
</script>