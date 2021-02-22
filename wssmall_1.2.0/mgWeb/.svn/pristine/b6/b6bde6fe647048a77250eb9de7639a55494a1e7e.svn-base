<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<style>
#tagspan{
	position: absolute;
	display:none;
}
#searchcbox{float:left}
#searchcbox div{float:left;margin-left:10px}
</style>
<form id="serchform" align="center">

<div class="grid" style = "height:200px;">
 
  <div class ='gridbody'>
    <table>
       <tr>
         <th>会员等级</th>
         <th>价格</th>
       </tr>
       <c:if test="${priceListSize==0}">
          <tr>
            <td colspan="2" style="text-align:center;color:red">没有商品价格信息</td>
          </tr>
       </c:if>
       <c:forEach var = "price" items='${priceList}'>
          <tr>
         <td>${price.price_type_name}</td>
         <td <c:if test="${price.price_val==0}">style="color:red"</c:if>>${price.price_val}</td>
       </tr>
       </c:forEach>

    </table>
   </div>
 
</div>
<div class="submitlist" align="center">
  <div>
 <table>
 <tr><td >
  <input name="btn" type="button" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>