<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
 <script type="text/javascript" src="../js/Comments.js"></script>
<div class="grid">
	<div class="comBtnDiv">		
	     <a href="javascript:;" id="revertBtn" style="margin-right:10px;" class="graybtn1" >还原</a>
		 <a href="javascript:;" id="cleanBtn" style="margin-right:10px;" class="graybtn1">清除</a>
	   <div style="clear:both"></div>
	</div>

<form method="POST">
<grid:grid  from="webPage">

	<grid:header>
	<grid:cell width="50px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell width="250px">标题</grid:cell> 
	<grid:cell >商品ID</grid:cell>
	<grid:cell >评论人</grid:cell> 
	<grid:cell >评论时间</grid:cell>
	<grid:cell  width="100px">操作</grid:cell> 
	</grid:header>

  <grid:body item="comments">
  		<grid:cell><input type="checkbox" name="id" value="${comments.comment_id }" />${comments.comment_id } </grid:cell>
        <grid:cell><a href="comments!detail.do?comment_id=${comments.comment_id}">${comments.title }</a> </grid:cell>
        <grid:cell>${comments.object_id} </grid:cell>
        <grid:cell>${comments.author }</grid:cell>  
        <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${comments.time }"></html:dateformat></grid:cell>
        <grid:cell> &nbsp; </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
</div>
 
