<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
      <table cellspacing="0" cellpadding="0" border="0" width="100%" class="finderInform">
      <colgroup class="Colgoodsid"></colgroup>
      <colgroup class="textleft"></colgroup>     
      <colgroup class="Colcategory"></colgroup>          
      <colgroup span="3" class="Colamount"></colgroup>
        <thead>
        <tr>
          	<th>商品ID</th>
		  	<th>商品编号</th>
		  	<th>商品名称</th>
		  	<th>数量</th>
          </tr>
      </thead>
      <tbody>
      	<c:forEach items="${deliveryItems }" var="di">
	    <tr>
	        <td>${di.goods_id }</td>
	        <td>${di.sn }</td>
	        <td>${di.name }</td>
	        <td>${di.num }</td>
	  	</tr>
	  </c:forEach>
      </tbody>
      </table>
    </div>