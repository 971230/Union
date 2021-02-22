<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">审核不通过</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0);" class="validate" method="post"
		name="modual_fail_form" id="modual_fail_form" enctype="multipart/form-data">
		<table class="form-table">
			<input type="hidden" name="modual.modual_id" value="${modual.modual_id}"/>
			<input type="hidden" name="modual.seq" value="${modual.seq}"/>
			<input type="hidden" name="modual.state" value="2"/>
			<input type="hidden" value="${tpl_id}" name="tpl_id" id="tpl_id"/>
			<tr>
				<th><label class="text">不通过原因：</label></th>
				<td><textarea  style="height: 100px;" name="modual.reason"
					dataType="string"  value=""></textarea></td>
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
			var url = ctx+ "/shop/admin/cms/modual!auditColumnContent.do?ajax=yes";
			Cmp.ajaxSubmit('modual_fail_form', '', url, {}, ModualFail.jsonBack,'json');
		});
	});
	
	
	var ModualFail = {
		jsonBack:function(responseText) { 
			if (responseText.result == 1) {
				alert("操作成功");
				Eop.Dialog.close("reson_dialog");
				window.location.href = "/shop/admin/cms/modual!auditList.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		}
	}
</script>