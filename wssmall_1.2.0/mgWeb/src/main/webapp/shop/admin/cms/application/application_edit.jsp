<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="mk_div mtt">
	<div class="searchformDiv">
		<form id="app_edit" class="grid" method="post" action=""
			validate="true">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>应用名称</td>
					<td><input type="text" class="searchipt"
						name="app.app_name" value="${app.app_name}" /></td>
					<td>应用描述</td>
					<td><input type="text" class="searchipt"
						name="app.description" value="${app.description}" /></td>
					<td>应用编码</td>
					<td><input type="text" class="searchipt"
						name="app.outer_app_code"
						value="${app.outer_app_code}" /></td>
				</tr>
			</table> 
		</form>

		<c:if test="${!isNew }">
			<a
				href="application!getPageList.do?app.app_id=${app.app_id }"
				onclick="" class="searchBtn"><span>关联页面</span></a>
		</c:if>
		<c:if test="${isNew }">
			<input type="button" id="submitApp" value=" 保存 " class="searchBtn"
				name='submitApp' onclick="" />
		</c:if>
	</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>页面编码</grid:cell>
				<grid:cell>应用名称</grid:cell>
				<grid:cell>页面url</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" />
				</grid:cell>
				<grid:cell>${obj.page_code }</grid:cell>
				<grid:cell>${obj.page_name }</grid:cell>
				<grid:cell>${obj.page_url }</grid:cell>
				<grid:cell>
					<a
						href="application!deletePageRel.do?app.app_id=${app.app_id }&appPage.id=${obj.id}">删除</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
</div>

<script type="text/javascript"> 
$(function (){
	  
	  $("#app_edit").validate();
      $("#submitApp").click(function() {
    	  var isCheck = false;
    	  $(".searchipt").each(function(){
    		var value =   $(this).val();
    		if(value==""||null==value){
    			isCheck = false;
    		return;
    		} 
    		isCheck = true;
    	  });
    	  	
    	  if(isCheck){
			var url = ctx+ "/shop/admin/cms/application/application!insertApp.do?ajax=yes";
			Cmp.ajaxSubmit('app_edit', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 
						 alert(responseText.message);
					}
			},'json');
    	  }else{
    		  alert("请检查输入是否完全");
    	  }
		})
		
  })
</script>

