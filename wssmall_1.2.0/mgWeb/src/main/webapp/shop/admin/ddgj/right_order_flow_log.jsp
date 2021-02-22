<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<div class="newOrderInfo">
      <div class="orderMsg">
      	<h2>流程日志</h2>
      	<div class="newOrderTab">
		    <ul class="hfList">
		      <c:forEach items="${toDoList }" var="vl">
		    	<li>
		        	<p class="info"><span>[${vl.flow_name }][${vl.status }]</span>${vl.hint }</p>
		            <p class="time">${vl.oper_name }&nbsp;<span>${vl.create_time }</span></p>
		        </li>
		      </c:forEach>  
		    </ul>
		</div>
      </div>
  </div>
