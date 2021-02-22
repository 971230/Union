<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
      <table cellspacing="0" cellpadding="0" border="0" width="100%" class="finderInform">
        <thead>
        <tr>
          <th>商品名称</th>
          <th>预约人</th>
          <th>手机号码</th>
          <th>详细地址</th>
          <th>邮编</th> 
          <th>邮箱</th> 
          <th>企业名称</th> 
          <th>公司电话</th> 
          </tr>
      </thead>
      <tbody>
      	<c:forEach items="${testRuleList}" var="item">
          <tr>
          <td class="textleft">${item.item_id }</td>
          <td>${item.uname }</td>
          <td>${item.umobile }</td>
          <td>${item.uaddress }</td>
          <td>${item.upost }</td>
          <td>${item.email }</td>
          <td>${item.buss_name }</td>
          <td>${item.company_tele }</td>
        </tr>
        </c:forEach>
              </tbody>
      </table>
      
    </div>