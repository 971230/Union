<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listGoods">
<form action="javascript:;" method="post" id="choiceGoodsform">
        <input type="hidden" name="dialog" value="${dialog }"/>
        <input type="hidden" name="goods.type" value="${goods.type }"/>
        <input type="hidden" name="type_id" value="${type_id }"/>
        <input type="hidden" name="s_type" value="${s_type }"/>
        <div class="searchformDiv">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
         <tbody>
        <tr>
        <th>          
           <c:if test="${goods.type == 'goods' }"> 商品名称：</c:if>
           <c:if test="${goods.type == 'product' }">货品名称：</c:if>
           <c:if test="${goods.type == 'package' }">货品包名称： </c:if>
        </th>
        <td>
           <input id="product_name" type="text"  name="product.name" value='${product.name}' style="width: 288px;" maxlength="60" class="ipttxt" /> 
        </td>
        
        <c:if test="${goods.type == 'package' }">
        <th>商品SKU:</th>
        <td>
           <input type="text" name="product.sku" style="width:200px;" class="ipttxt" value='${product.sku }' />
        </td> 
        </c:if>
        
        <td style="text-align:right;">
           <input class="comBtn" type="submit" name="searchGoodsBtn" id="searchGoodsBtn" value="搜索" style="margin-right:5px;"/>
        </td>
       </tr>
      </tbody>
      </table>
        </div>      
</form> 
</form> 
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="choiceGoodsform">
                <grid:header>
                    <grid:cell width="50px"><input  name="check" type="checkbox" id="toggleChk" ></grid:cell>
                    <grid:cell>
                       <c:if test="${goods.type == 'goods' }"> 商品编码</c:if>
                       <c:if test="${goods.type == 'product' }"> 货品编码</c:if>
                       <c:if test="${goods.type == 'package' }">货品包标识 </c:if>
                    </grid:cell>
                    <grid:cell>
                       <c:if test="${goods.type == 'goods' }"> 商品名称 </c:if>
                       <c:if test="${goods.type == 'product' }">货品名称 </c:if>
                       <c:if test="${goods.type == 'package' }">货品包名称 </c:if>
                    </grid:cell>
                </grid:header>
                <grid:body item="goods">
                    <grid:cell><input type="checkbox"  name="goodsId" value="${goods.product_id }" pid="${goods.product_id }"
                       gid="${goods.goods_id }" id="names" goods_name="${goods.name }" sn="${goods.sn }" sku="${goods.sku}" 
                       model_code="${goods.model_code }" s_type="${s_type }" /></grid:cell>
                    <grid:cell>${goods.sku}</grid:cell>
                    <grid:cell>${goods.name} <input type="hidden" name="goods_name" value="${goods.name}"></grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
             <div style="text-align:center;margin-top:10px;"> 
                 <input name="btn" type="button" id="selGoodsBtn" value="确定"  class="comBtn" />
             </div>
        </form>

</div>
<script type="text/javascript">
    $("#product_name").keydown(function(event){
        if (event.keyCode == 13) {
            event.preventDefault();
            addGoods.searchBottonClks();
        };
    });
</script>

