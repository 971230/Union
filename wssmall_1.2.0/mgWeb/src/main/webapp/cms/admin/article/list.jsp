<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<c:set var="fieldList" value="${fieldList}" />

<div class="searchformDiv">
<form action="data!list.do" method="post">
<table class="form-table">
   <input type="hidden" name="catid" value="${catid}"/>
   <input type="hidden" name="source_from" value="${sourcefrom}" />
	<tr>
		<th>关键字：</th>
		<td><input type="text" class="ipttxt"  name="searchText" value="${searchTitle }" /></td>
		<th>属性：</th>
		<td><select  class="ipttxt"  name="searchField">
						<c:forEach items="${fieldList}" var="field">
							<option value="${field.english_name }">${field.china_name}</option>
						</c:forEach>
					</select></td>
		<th></th>
		<td>
		<input type="submit"   class="comBtn" style="margin-right:10px;" value="搜索"/>
	    </td>
	    <c:if test="${site.multi_site == 1 }"><th></th><td><a href="javascript:;" id="importBtn"  class="graybtn1"  >导入</a></td></c:if>
			<div style="clear:both"></div>
	</tr>
</table>	
</form>	
</div>
<div class="grid">
		
   <div class="comBtnDiv">
		<a href="data!add.do?catid=${cat.cat_id}&source_from=${cat.source_from}" style="margin-right:10px;"  class="graybtn1" ><span>添加</span></a>
		<a href="javascript:;" style="margin-right:10px;" id="sortBtn"  class="graybtn1" ><span>保存排序</span></a>
	</div>	
	</form>
	<form id="sortForm" method="post">
		<input type="hidden" name="catid" value="${catid }"/>
		<input type="hidden" name="source_from" value="${sourcefrom }"/>
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell width="50px">id</grid:cell>
				<grid:cell>标题</grid:cell>
				<th style="width:80px">子栏目</th>
				<th style="width:80px">排序</th>
				<th style="width:150px">添加时间</th>
				<th style="width:100px">修改</th>
				<th style="width:100px">删除</th>
			</grid:header> 	
	  		<grid:body item="article">
	  			<grid:cell>${article.id }<input type="hidden" name="ids" value="${article.id }" /></grid:cell>
	  			<grid:cell>${article.title }</grid:cell>
  				<grid:cell><c:if test="${article.cat_id!=catid}">${article.cat_name }</c:if></grid:cell>
  				<grid:cell><input type="text" class="ipttxt"  style="width:50px" name="sorts" value="${article.sort }" /></grid:cell>
  				<grid:cell><fmt:formatDate value="${article.add_time}" pattern="yyyy-MM-dd HH:mm:ss"/> </grid:cell>
	    		<grid:cell><a href="data!edit.do?dataid=${article.id }&catid=${catid}&source_from=${article.source_from}"><img src="images/transparent.gif" class="modify"></a></grid:cell>
	    		<grid:cell>
	    			<c:if test="${article.sys_lock != 1 }">
	    				<a href="data!delete.do?dataid=${article.id }&catid=${catid}&source_from=${article.source_from}" onclick="javascript:return confirm('确定删除此文章吗?');"><img src="images/transparent.gif" class="delete"></a>
					</c:if>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear:both;padding-top:5px;"></div>
	<div id="import_selected"></div>
</div>
<script type="text/javascript">
function updateSort(){
	$.Loading.show('正在保存排序，请稍侯...');
	var options = {
			url :"data!updateSort.do?ajax=yes",
			type : "POST",
			dataType : 'json',
			success : function(result) {				
			 	if(result.result==1){
			 		$.Loading.hide();
			 		alert("更新成功");
			 		location.reload();
			 	}else{
			 		alert(result.message);
			 		$.Loading.hide();
			 	}
			},
			error : function(e) {
				$.Loading.hide();
				alert("出错啦:(");
				}
		};

	$("#sortForm").ajaxSubmit(options);		
}
$(function(){

	/**初始化导入数据对话框**/
	Eop.Dialog.init({id:"import_selected",modal:true,title:"导入数据", width:"600px"});
	
	/**排序**/
	$("#sortBtn").click(function(){
		updateSort();
	});

	/**导入按钮点击事件**/
	$("#importBtn").click(function(){

		//抓取导入对话框内容
		$.ajax({
			 type: "GET",
			 url: "${ctx}/cms/admin/data!implist.do?ajax=yes&catid=${catid }",
			 dataType:'html',
			 success: function(result){
				 $("#import_selected").empty().append(result);
		     },
		     error:function(){
				alert("数据列表获取失败");
			 }
		});

		//打开导入对话框
		Eop.Dialog.open("import_selected");
		
	});
});
</script>