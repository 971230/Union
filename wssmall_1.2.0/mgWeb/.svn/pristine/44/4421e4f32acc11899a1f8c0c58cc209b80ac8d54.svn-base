<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<style>
.mutil_info{
	margin-top:3px;
	margin-bottom:2px;
}
.mutil_info_div1{
	text-align:right;
	width:63px;
	float:left;
}
.mutil_info_div2{
	padding-left:8px;
	text-align:left;
	float:left;
	
	width:160px;
	word-break:keep-all;
	white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
}
.mutil{
	margin-top:10px;
	margin-bottom:10px;
}
</style>
<div>
	<form action="${ctx}/shop/admin/keyrule!list.do" method="post" id="searchNoSegForm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <%--<th>接口名称：</th>
	    	      <td>
	    	           <html:selectdict name="params.region_id" curr_val="${params.region_id }"
	    	              attr_code="DC_COMMON_REGION_GUANGDONG" style="width:150px" 
					      appen_options='<option value="">--请选择--</option>'></html:selectdict>
	    	      </td> --%>
	    	      <th>搜索id</th>
	    	      <td>
	    	      	<input type="text" class="ipttxt" name="search_id" value="${search_id}" />
	    	      </td>
	    	      <th>搜索编码</th>
	    	      <td>
	    	      	<input type="text" class="ipttxt" name="search_code" value="${search_code}" />
	    	      </td>
	    	      <th>搜索规格名称：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="search_name" value="${search_name}" /> <input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	      </td>	 
	    	      <th></th>
	    	      <td></td>
	    	      <th></th>
	    	      <td></td>       
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>
	  	 
	  	 <div class="comBtnDiv">
	  	     <input class="comBtn" type="button" id="searchBtn" value="添加" style="margin-right:10px;"
				onclick="window.location.href='${ctx}/shop/admin/keyrule!toAdd.do'" />
				<input class="comBtn" type="button" onclick="refreshSpecvaluesRules();" value="刷新抽取规则" style="margin-right:20px;"  /> 
				<c:if test="${flag == 1}">
					<input class="comBtn" type="button" onclick="extractAllKeyword();" value="全量抽取" style="margin-right:20px;"  /> 
				</c:if>
		</div>			
	</form>

    <form id="theForm" class="grid">
	<grid:grid from="webpage" formId="searchNoSegForm" action="${ctx}/shop/admin/keyrule!list.do">
	    <grid:header>
	    	<grid:cell style="width:50px">搜索ID</grid:cell>
	    	<grid:cell style="width:300px">搜索编码</grid:cell>
	    	<grid:cell style="width:40px">序号</grid:cell>
		    <grid:cell>搜索规格名称</grid:cell>
		    <grid:cell style="width:250px;text-align:left;">抽取规则信息1</grid:cell>
		    <grid:cell style="width:250px;text-align:left;">抽取规则信息2</grid:cell>
			<grid:cell style="width:120px">操作</grid:cell>
		</grid:header>

		<grid:body item="objeto">
			<grid:cell>${objeto.search_id }  </grid:cell>
			<grid:cell>${objeto.search_code }  </grid:cell>
			<grid:cell>${objeto.seq }  </grid:cell>
		    <grid:cell>${objeto.search_name }  </grid:cell>
		    <grid:cell>
		    	<div class="mutil">
	    			<div class="mutil_info">
	                	<div class="mutil_info_div1">开始位置:</div>&nbsp;&nbsp;<div class="mutil_info_div2">${objeto.begin_index }</div>
	                </div>
	                <div class="mutil_info">
	                	<div class="mutil_info_div1">结束位置:</div>&nbsp;&nbsp;<div class="mutil_info_div2">${objeto.end_index }</div>
	                </div>
	                <div class="mutil_info">
	                	<div class="mutil_info_div1">开始字符:</div>&nbsp;&nbsp;
	                	<div class="mutil_info_div2" title="${objeto.begin_word }">
	                		${objeto.begin_word }
	                	</div>
	                </div>
	                <div class="mutil_info">
	                	<div class="mutil_info_div1">结束字符:</div>&nbsp;&nbsp;
	                	<div class="mutil_info_div2" title="${objeto.end_word}">
	                		${objeto.end_word}
	                	</div>
	                </div>
		    	</div>
		    </grid:cell>
		    <grid:cell>
		    	<div class="mutil">
	                <div class="mutil_info">
	                	<div class="mutil_info_div1">解析表达式:</div>&nbsp;&nbsp;
	                	<div class="mutil_info_div2" title="${objeto.express}">
	                		${objeto.express}
	                	</div>
	                </div>
	                <div class="mutil_info">
	                	<div class="mutil_info_div1">处理类:</div>&nbsp;&nbsp;
	                	<div class="mutil_info_div2" title="${objeto.hander}">
	                		${objeto.hander}
	                	</div>
	                </div>
	                <div class="mutil_info">
	                	<div class="mutil_info_div1">匹配字符:</div>&nbsp;&nbsp;
	                	<div class="mutil_info_div2" title="${objeto.match_word}">
	                		${objeto.match_word}
	                	</div>
	                </div>
	                <div class="mutil_info">
	                	<div class="mutil_info_div1">截取字符:</div>&nbsp;&nbsp;
	                	<div class="mutil_info_div2" title="${objeto.cut_word}">
	                		${objeto.cut_word}
	                	</div>
	                </div>
		    	</div>
		    </grid:cell>
			<grid:cell>
				<a href="javascript:void(0);" onclick="del(this,'${objeto.key_rule_id}')" >删除</a>|
				<a href="javascript:void(0);" onclick="window.location.href='${ctx}/shop/admin/keyrule!toUpdate.do?id=${objeto.key_rule_id}'" >修改</a>
				<br/><br/>
				<a href="javascript:void(0);" onclick="window.location.href='${ctx}/shop/admin/keyrule!orderexpList.do?inner.key_id=${objeto.key_id }&inner.search_id=${objeto.search_id }'" >重新抽取</a>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
    
<div style="clear:both;padding-top:5px;"></div>
</div>
<div id="orderexp_dialog"></div>
<script>
	function importacion() {
		var v = document.getElementById("uploadFile").value;
		if (isNull(v)) {
			MessageBox.show("上传文件为空 ", 3, 2000);
			return;
		}
		document.getElementById("uploadForm").submit();
	}
	
	function del(obj, seg_id) {
		if (!confirm("确定要删除这条数据吗？")) {
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: ctx + "/shop/admin/keyrule!del.do",
			 data: "ajax=yes&id="+seg_id,
			 dataType:"json",
			 success: function(result){
				 
				 if (result.result == '0') {
					 $(obj).parents('tr').remove();
			     } else {
			    	 alert(result.message);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
	function extractAllKeyword(){
		if (!confirm("确定要全量抽取关键字吗？")) {
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: ctx + "/shop/admin/keyrule!extractAllKeyword.do",
			 data: "ajax=yes",
			 dataType:"json",
			 success: function(result){
			   	alert(result.message);
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
	function refreshSpecvaluesRules(){
		if (!confirm("确定要全量刷新抽取规则吗？")) {
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: ctx + "/shop/admin/keyrule!refreshSpecvaluesRules.do",
			 data: "ajax=yes",
			 dataType:"json",
			 success: function(result){
			   	alert(result.message);
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
</script>