<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/PrintTmpl.js"></script>

<div class="grid">
<div class="comBtnDiv">
	<a href="printTmpl!add.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
	<a href="javascript:;" id="delBtn" style="margin-right:10px;" class="graybtn1" ><span>删除</span></a>
	<a href="printTmpl!trash.do" id="delBtn" style="margin-right:10px;" class="graybtn1" ><span>回收站</span></a>
	<div style="clear:both"></div>
</div>
	
<form method="POST" >
<grid:grid  from="list">

	<grid:header>
	<grid:cell width="50px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell width="250px">模板编号</grid:cell> 
	<grid:cell width="250px">模板名称</grid:cell> 
	<grid:cell sort="url">是否启用</grid:cell> 
	<grid:cell  width="100px">操作</grid:cell> 
	</grid:header>

  <grid:body item="tmpl">
  		<grid:cell><input type="checkbox" name="id" value="${tmpl.prt_tmpl_id }" /> </grid:cell>
  		<grid:cell>${tmpl.prt_tmpl_id } </grid:cell>
        <grid:cell>${tmpl.prt_tmpl_title } </grid:cell>
        <grid:cell><c:if test="${tmpl.shortcut=='true' }">是</c:if><c:if test="${tmpl.shortcut=='false' }">否</c:if></grid:cell> 
        <grid:cell> <a href="printTmpl!edit.do?prt_tmpl_id=${tmpl.prt_tmpl_id }" ><img class="modify" src="images/transparent.gif" ></a> </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>

