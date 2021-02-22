<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
 <script type="text/javascript" src="js/BrandEcs.js"></script>
<div class="grid">
	<div class="comBtnDiv">
	<a href="javascript:;" style="margin-right:10px;" class="graybtn1" id="revertBtn" ><span id="addBrand">还原</span></a>
	<a href="javascript:;" style="margin-right:10px;" class="graybtn1" id="cleanBtn" ><span id="addBrand">清除</span></a>
	<div style="clear:both"></div>
	</div>

<form method="post">
	
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell width="50px"><input type="checkbox"  id="toggleChk"  /></grid:cell> 
	<grid:cell sort="name" width="250px">品牌名称</grid:cell> 
	<grid:cell sort="brand_code" width="250px">品牌编码</grid:cell> 
	</grid:header>

  <grid:body item="brand">
  	<grid:cell><input type="checkbox" name="id" value="${brand.brand_id }" /></grid:cell> 
    <grid:cell>${brand.name } </grid:cell>
    <grid:cell>${brand.brand_code } </grid:cell>
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
 
