<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<div class="newOrderInfo">
      <div class="orderMsg">
      	<h2>回访日志</h2>
      	<div class="newOrderTab">
		    <ul class="hfList">
		      <c:forEach items="${visitList }" var="vl">
		    	<li>
		        	<p class="info"><span>[${vl.typename }]</span>${vl.content }</p>
		            <p class="time">${vl.user_name }<span>${vl.create_time }</span></p>
		        </li>
		      </c:forEach>  
		    </ul>
		</div>
      </div>
  </div>
