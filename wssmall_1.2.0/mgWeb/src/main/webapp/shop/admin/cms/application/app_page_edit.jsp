<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">页面</span><span
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
			<input type="hidden" name="appPage.id" value="${appPage.id}" />
			<tr>
				<th><span class='red'>*</span>页面名称</th>
				<td><input type="text" required="true" class="ipttxt"
					name="appPage.page_name" value="${appPage.page_name}" /></td>
			</tr>

			<tr>
				<th>页面描述</th>
				<td><textarea class="ipttxt" name="appPage.description"
						style="width: 150px;height: 100px;">${appPage.description}</textarea></td>
			</tr>

			<tr>
				<th><span class='red'>*</span>页面编码</th>
				<td><input type="text" class="ipttxt" name="appPage.page_code"
					required="true" value="${appPage.page_code}" /></td>
			</tr>

			<tr>
				<th>页面URL</th>
				<td><input type="text" class="ipttxt" name="appPage.page_url"
					value="${appPage.page_url}" /></td>
			</tr>
			
			<tr>
				<th><span class='red'>*</span>菜单类型</th>
				<td>
					<html:selectdict curr_val="${appPage.page_type}"  
							style="width:155px;height:25px;" id="appPage.page_type"
							name ="appPage.page_type" 
							attr_code="DC_MENU_TYPE">
					</html:selectdict>
				</td>
			</tr>
			
			<tr>
				<th><label class="text">关联模板：</label></th>
				<td><a class="sgreenbtn"
					href="javascript:void(0);" id="select_model"><span>选择</span> </a>
					<div class="top_up_con grid">
						<table class="gridlist">
							<thead>
								<tr>
									<th style="text-align: center;">模板ID</th>
									<th style="text-align: center;">模板名称</th>
									<th style="text-align: center;">操作</th>
								</tr>
							</thead>
							<tbody id="tmpl_body">
								<c:forEach var="list" items="${pageTplList}">
									<tr>
										<input type="hidden" name="tmpl_ids" value="${list.template_id}"/>
										<td>${list.template_id}</td>
										<td>${list.template_name}</td>
										<td><a href="javascipt:void(0);" name="tmpl_delete" class="del_page">删除</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					</td>
			</tr>
			
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td><input type="submit" id="savaPage" value=" 确  定 "
						class="submitBtn" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div  id="cms_tpl_dialog"></div>
<script type="text/javascript">
	$(function() {
		Eop.Dialog.init({id:"cms_tpl_dialog", modal:false, title:"选择模板", width:"700px"});
		
		$("#select_model").bind("click",function(){
			Eop.Dialog.open("cms_tpl_dialog");
			
			var url = ctx + "/shop/admin/cms/template!list.do?is_select=yes&ajax=yes";
			$("#cms_tpl_dialog").load(url,function(){
				initTemplate();
			}); 
		});
		$("a[name=tmpl_delete]").live("click",function(){
			$(this).closest("tr").remove();
		});
		
		$("input[name='appPage.page_code']").blur(function(){
			if (/[\u4E00-\u9FA5]/i.test($(this).val())) {
			    alert('请输入英文编号！');
			    $(this).val("").focus();
			}
		});
		
		$("#savaPage")
				.bind(
						"click",
						function() {
							$("#theForm.validate").validate();
							var url = ctx
									+ "/shop/admin/cms/application/application!inserPage.do?ajax=yes";
							Cmp.ajaxSubmit('theForm', '', url, {},
									PageAdd.jsonBack, 'json');
						});
	});

	var PageAdd = {
		jsonBack : function(responseText) {
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href = "application!getPageList.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}
		}
	}
	
	function initTemplate(){
		$("#cms_tpl_dialog #submitButton").unbind("click").bind("click",function(){
			var url = ctx + "/shop/admin/cms/template!list.do?is_select=yes&ajax=yes";
			Cmp.ajaxSubmit('template_form','cms_tpl_dialog',url,{},initTemplate);
			return false;
			
		});
		
		$("#cms_tpl_dialog #sel_temp").unbind("click").bind("click",function(){
			var sel_radio = $("input[name='choose_temp']:checked");
			if(sel_radio.length == 0){
				alert("请选择模板！");
				return;
			}
			
			var template_id = sel_radio.attr("template_id");
			var template_name = sel_radio.attr("template_name");
			var inputs = $("input[name=tmpl_ids][value="+template_id+"]");
			if(inputs && inputs.length && inputs.length>0){
				Eop.Dialog.close("cms_tpl_dialog");
				return ;
			}
			var tr = '<tr><input type="hidden" name="tmpl_ids" value="'+template_id+'"/>'+
				'<td>'+template_id+'</td><td>'+template_name+'</td>'+
				'<td><a href="javascipt:void(0);" name="tmpl_delete" class="del_page">删除</a></td></tr>';
			$("#tmpl_body").append(tr);	
			Eop.Dialog.close("cms_tpl_dialog");
		});
	}
</script>

