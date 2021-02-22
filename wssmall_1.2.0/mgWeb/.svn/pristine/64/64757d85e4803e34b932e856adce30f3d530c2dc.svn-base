<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">loadScript("js/Brand.js");</script>
<div class="grid" id="brankSelectList">
	
<form method="POST" >
<grid:grid  from="webpage" ajax="yes">

	<grid:header>
	<grid:cell width="30px">选择</grid:cell>
	<grid:cell sort="name" width="200px">品牌名称</grid:cell> 
	<grid:cell sort="brand_code" width="200px">品牌编码</grid:cell> 
	<grid:cell sort="url">品牌网址</grid:cell> 
	</grid:header>

  <grid:body item="brand">
  		<grid:cell><input type="radio" name="brandid" value="${brand.brand_id }" nameValue="${brand.name}" /></grid:cell>
        <grid:cell>${brand.name } </grid:cell>
        <grid:cell>${brand.brand_code } </grid:cell>
        <grid:cell>${brand.url} <input type="hidden" name="filePath" value="${brand.logo }" /></grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	

<div style="clear:both;padding-top:5px;"></div>
</div>
<div style="text-align:center;">
	<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1"  id="confirmBrandBtn"><span>确定</span></a>
</div>