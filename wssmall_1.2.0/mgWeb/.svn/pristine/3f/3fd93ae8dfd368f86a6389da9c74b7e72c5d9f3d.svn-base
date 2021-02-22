<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>栏目列表</h2>
	</div>
	<div class="mk_content">
		<div class="searchformDiv">
		 <form method="post" action="column!list.do" name="qryForm">
    	  <table width="90%" border="0" cellspacing="0" cellpadding="0">
    	    <tr>
    	      <th>栏目名称:</th>
    	      <td><input type="text" name="column.title" class="searchipt" value="${column.title }"/></td>
    	      <th>栏目大类:</th>
    	      <td><html:selectdict name='column.column_type' curr_val="${column.column_type}" attr_code="DC_COLUMN_BIGTYPE"  ></html:selectdict></td>
    	      <th>栏目类型:</th>
    	      <td>
    	      	<select name="column.type" id="column_type" class="searchipt">
    	      		<option value="">--请选择--</option>
    	      		<option value="list">列表</option>
    	      		<option value="tab">Tab页</option>
  	        	</select>
  	          </td>
    	      <td style="text-align:center;"><a href="javascript:void;"
					 onclick="document.forms.qryForm.submit();" class="searchBtn">
					 <span>查&nbsp;询</span></a></td>
  	    </table>
  	    </form>
    	</div>
		<!--操作按钮样式-->
  		<div class="comBtnDiv" style="margin-left: 35px;">
            <a href="column!add.do" class="graybtn1" style="margin-right:5px;"><span>添加栏目</span></a>
        </div>
    	<!--列表样式-->
    	<div class="grid">
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	    <tr>
    	      <th>栏目ID</th>
    	      <th>栏目名称</th>
    	      <th>栏目大类</th>
    	      <th>栏目类型</th>
    	      <th>操作</th>
  	      </tr>
  	      <c:forEach items="${webpage.data }" var="data">
  	      	<tr>
    	      <td width="25%">${data.column_id }</td>
    	      <td>${data.title }</td>
    	      <td>
    	      	<c:choose>
    	      		<c:when test="${data.column_type=='notice' }">公告</c:when>
    	      		<c:when test="${data.column_type=='adv' }">广告</c:when>
    	      		<c:otherwise></c:otherwise>
    	      	</c:choose>
    	      </td>
    	      <td>
    	      	<c:if test="${data.type=='list' }">列表</c:if>
    	      	<c:if test="${data.type=='tab' }">Tab页</c:if>
    	      </td>
    	      <td>
    	      	<a href="column!edit.do?column.column_id=${data.column_id }">修改</a><span class="sper">|</span>
    	      	<a onclick="javascript:return confirm('确认删除吗?');" 
    	      		href="column!delete.do?column.column_id=${data.column_id }">删除</a>
    	      </td>
  	      	</tr>
  	      </c:forEach>
  	    </table>
		</div>
	</div>
	<div class="grid">
		<form method="POST" >
			<grid:grid from="webpage">
				<grid:body item=""></grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$("#column_type option[value='${column.type}']").attr("selected", true);
});

function clickC(tpl_id){//跳转到新增模块的页面
	
}
</script>
