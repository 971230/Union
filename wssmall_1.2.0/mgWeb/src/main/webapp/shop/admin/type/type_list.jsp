<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/GoodsType.js"></script>
<script type="text/javascript">$(function(){GoodsType.init();});</script>
<div id="typeList" class="grid">
	<div class="comBtnDiv">
    <!--   <input type="hidden" name = "messId" id="messId" value="${messId}">-->

	<a href="type!step1.do" style="margin-right:10px;" id="addtype" class="graybtn1" ><span>添加</span></a>
    <a href="javascript:;" style="margin-right:10px;" id="delBtn" class="graybtn1" ><span>放入回收站</span></a>
     <a href="type!trash_list.do" style="margin-right:10px;" id="trashlist" class="graybtn1" ><span>回收站</span></a>

	
		<div style="clear:both"></div>
	</div>
<form validate="true" class="validate">
<grid:grid  from="webpage"  >

	<grid:header>
	<grid:cell ><input type="checkbox"  id="toggleChk" /></grid:cell>
	<grid:cell  width="250px">&nbsp;类型名称</grid:cell> 
	<grid:cell sort="type">类型</grid:cell> 
	<grid:cell sort="have_prop">拥有属性</grid:cell> 
	<grid:cell sort="have_parm">拥有参数</grid:cell> 
	<grid:cell sort="join_brand">拥有关联品牌</grid:cell> 
	<%--<grid:cell sort="have_price">拥有价格</grid:cell> --%>
	<grid:cell  width="100px">操作</grid:cell> 
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
       <%-- 
        <grid:cell>        
        <c:if test="${type.have_stock ==1 }">有</c:if>
        <c:if test="${type.have_stock ==0 }">否</c:if>
        </grid:cell> 
        <grid:cell>        
        <c:if test="${type.have_price ==1 }">有</c:if>
        <c:if test="${type.have_price ==0 }">否</c:if>
        </grid:cell> 
         --%>
        <grid:cell> <a   href="type!edit.do?type_id=${type.type_id }" >修改</a> </grid:cell> <!-- <img class="modify" src="images/transparent.gif" > -->
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>


