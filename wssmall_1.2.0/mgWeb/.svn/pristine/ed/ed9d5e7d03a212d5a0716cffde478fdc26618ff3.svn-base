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
          <th>订单编号</th>
          <th>备注信息</th>
          <th>操作人</th>
          <th>操作时间</th>
          </tr>
      </thead>
      <tbody>
      	<c:forEach items="${commentList}" var="item">
          <tr>
          <td class="Colamount">${item.order_id }</td>
          <td class="Colamount">${item.comments }</td>
          <td class="Colamount">${item.opet_name}</td>
          <td class="Colamount"><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${item.oper_time}"></html:dateformat></td>
        </tr>
        </c:forEach>
              </tbody>
      </table>
    </div>