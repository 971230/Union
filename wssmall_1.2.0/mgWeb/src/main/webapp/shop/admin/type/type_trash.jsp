<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/GoodsType.js"></script>
<script type="text/javascript">$(function(){GoodsType.init();});</script>
<div class="grid">

	<div class="toolbar">
		<a href="javascript:;" id="revertBtn"><input class="comBtn" type="button" name="" id="" value="还原" style="margin-right:10px;outline-style:none"/></a>
		<a href="javascript:;" id="cleanBtn"><input class="comBtn" type="button" name="" id="" value="清除" style="margin-right:10px;outline-style:none"/></a>
	
	<div style="clear:both"></div>
	</div>
<form>
<grid:grid  from="webpage" >

	<grid:header>
		<grid:cell ><input type="checkbox" id="toggleChk" /></grid:cell> 
		<grid:cell  width="250px">&nbsp;类型名称</grid:cell> 
		<grid:cell sort="type">类型</grid:cell>
		<grid:cell sort="have_prop">拥有属性</grid:cell> 
		<grid:cell sort="have_parm">拥有参数</grid:cell>
		<grid:cell sort="join_brand">拥有关联品牌</grid:cell>    
	</grid:header>

  <grid:body item="type">
  		<grid:cell><input type="checkbox" name="id" value="${type.type_id }" /></grid:cell>
        <grid:cell>${type.name } </grid:cell>
        <grid:cell>
        <c:if test="${type.type=='goods' }">商品</c:if>
        <c:if test="${type.type=='product' }"> 货品</c:if>
        </grid:cell>
        <grid:cell>
        <c:if test="${type.have_prop==1 }">有</c:if>
        <c:if test="${type.have_prop==0 }">否</c:if>
        </grid:cell> 
         
         
        <grid:cell>        
        <c:if test="${type.have_parm ==1 }">有</c:if>
        <c:if test="${type.have_parm ==0 }">否</c:if>
        </grid:cell> 
         
        
        <grid:cell>        
        <c:if test="${type.join_brand ==1 }">有</c:if>
        <c:if test="${type.join_brand ==0 }">否</c:if>
        </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>
</div>


