<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		>
		<input type="hidden" name="ajax" value="yes"/>
		<input type="hidden" name="show_type" value="dialog"/>
		<input type="hidden" class="ipttxt"  value = "${inner.key_id }" name = "inner.key_id" id="key_id" required="true" />
		<table class="form-table" id="specvalue_catalog_list">
			<tr>
				<th><label class="text">当前归类：</label></th>
				<td>
					<c:choose>
						<c:when test="${empty catalog }">
							无
						</c:when>
						<c:otherwise>
							${catalog.catalog_name }
						</c:otherwise>
					</c:choose>
					<input type="hidden" class="ipttxt"  value = "${catalog.catalog_id }" name = "inner.old_catalog_id" id="old_catalog_id" required="true" />
				</td>
			</tr>
			<tr id="specvaluesClassifyDialog">
				<th><label class="text"><span class='red'>*</span>更换归类：</label></th>
				<td>
					<%-- <select name="inner.catalog_id" id="catalog_id">
						<c:forEach items="${catalogList }" var="cl">
							<option value="${cl.catalog_id }">${cl.catalog_name }</option>
						</c:forEach>
					</select>--%>
					<input type="hidden" id="catalog_id">
					<input type="text" id="catalog_name" class="ipttxt" readonly>&nbsp;&nbsp;&nbsp;<input type="button" style="margin-right:10px;" class="comBtn" value="选&nbsp;择" onclick="select_catalog();"/>
				</td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
						<input type="button" id="" value="确  定 " class="submitBtn" onclick="submit_catalog_data();" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	function submit_catalog_data(){
		var catalog_id = $("#specvalue_catalog_list #catalog_id").val();
		var old_catalog_id = $("#specvalue_catalog_list #old_catalog_id").val();
		if(old_catalog_id == catalog_id){
			after_update_catalog();
			return false;
		}
		var key_id = $("#key_id").val();
		
		var json_data = $("#theForm").serialize();
		$("#select_catalog_dialog").html("正在处理，请稍后...");
		$.ajax({
			 type: "POST",
			 url: "specvalue!specvaluesClassify.do",
			 data: "inner.key_id="+key_id+"&inner.old_catalog_id="+old_catalog_id+"&inner.catalog_id="+catalog_id+"&ajax=yes",
			 dataType:"json",
			 success: function(result){
				 
				 if (result.result == '0') {
					 after_update_catalog(result);
			     } else {
			    	 alert(result.message);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
	
	function select_catalog(){
		//Eop.Dialog.close("select_catalog_dialog");
		//初始化分类列表
		/*var url =ctx + "/shop/admin/esearchCatalog!list.do?ajax=yes&show_type=dialog";
		$("#esearchCatalogListDialog").html("loading...");
		$("#esearchCatalogListDialog").load(url,{},function(){});
		Eop.Dialog.open("esearchCatalogListDialog");*/
		
       // var catalog_name_search = $("#catalog_name_search").val();
        var url =ctx + "/shop/admin/esearchCatalog!list.do?ajax=yes&show_type=dialog";
        $("#esearchCatalogListDialog").load(url,{"eclInner.catalog_name":""},function(){});
        Eop.Dialog.open("esearchCatalogListDialog");
	}

</script>