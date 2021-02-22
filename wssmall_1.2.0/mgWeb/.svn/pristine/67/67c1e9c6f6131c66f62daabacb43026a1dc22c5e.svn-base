<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/BrandEcs.js"></script>

<div class="grid">

	
	<div class="comBtnDiv">
	<a href="brand!addEcs.do" style="margin-right:10px;" class="graybtn1" ><span id="addBrand">添加</span></a>
	<a href="javascript:;" style="margin-right:10px;" class="graybtn1" id="delBtn"><span >删除</span></a>
    <a href="brand!trash_list_Ecs.do" style="margin-right:10px;" class="graybtn1" ><span id="addBrand">回收站</span></a>
    <a href="javascript:;" style="margin-right:10px;" class="graybtn1" id="publishBrand"><span>全量发布</span></a>
    <div class="help_icon" helpid="goodscat"></div>
		<div style="clear:both"></div>
     </div>
	
		
	
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell width="10px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell sort="name" width="250px">品牌名称</grid:cell> 
	<grid:cell sort="brand_code" width="250px">品牌编码</grid:cell> 
	<grid:cell  width="100px">操作</grid:cell> 
	</grid:header>

  <grid:body item="brand">
  		<grid:cell><input type="checkbox" name="id" value="${brand.brand_id }" /></grid:cell>
        <grid:cell>${brand.name } </grid:cell>
        <grid:cell>${brand.brand_code } </grid:cell>
        <grid:cell> <a  href="brand!editEcs.do?brand_id=${brand.brand_id }" ><img class="modify" src="images/transparent.gif" ></a> </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>