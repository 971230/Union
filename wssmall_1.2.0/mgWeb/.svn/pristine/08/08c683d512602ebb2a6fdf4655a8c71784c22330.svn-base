<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
 <script type="text/javascript" src="js/Tag.js"></script>
<div class="grid" >
<form method="post">

	<div class="comBtnDiv">
	<a href="tag!add.do" style="margin-right:10px;" class="graybtn1" ><span >添加</span></a>
	<a href="javascript:;" style="margin-right:10px;" class="graybtn1" id="delBtn" ><span>删除</span></a>
 	&nbsp;&nbsp;<span class="help_icon" helpid="goods_tag"></span>
		<div style="clear:both"></div>
	</div>
	
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell  width="20px"><input type="checkbox" id="toggleChk" /></grid:cell> 
	<grid:cell  width="250px">id</grid:cell> 
	<grid:cell>标签</grid:cell> 
	<grid:cell>标签类型</grid:cell> 
	<grid:cell  width="100px">操作</grid:cell> 
	</grid:header>

  <grid:body item="tag">
	 <grid:cell><input type="checkbox" name="tag_ids" value="${tag.tag_id }" /></grid:cell>
     <grid:cell>${tag.tag_id} </grid:cell>
     <grid:cell>${tag.tag_name} </grid:cell> 
     <grid:cell>${tag.cat_type_name} </grid:cell> 
     <grid:cell> <a   href="tag!edit.do?tag_id=${tag.tag_id }" ><img class="modify" src="images/transparent.gif" ></a> </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>
<div style="clear:both;padding-top:5px;"></div>
 </div>

