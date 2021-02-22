<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link href="<%=request.getContextPath() %>/publics/lucene/info.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/lucene/lucene.js"></script>
 <script type="text/javascript">
 $(function(){
 Lucene.init('lucence_search_tx');
 })
	 
 </script>

	搜索:<input type="text" style="width:250px" class="ipttxt" id="lucence_search_tx" name="name" value="${name }" objType="goods" class="searchipt" tableColumn="name" keyFun="searchResult" />
	<input type="submit" value="提交" />
