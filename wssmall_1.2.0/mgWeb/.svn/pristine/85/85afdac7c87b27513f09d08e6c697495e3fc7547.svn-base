<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="btnWarp">
 </div>
 
<div class="formWarp" style="border-bottom: none;">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="500" class="closeArrow"></a>流程记录<div class="dobtnDiv"></div></div>
<div id="order_tag_500" class="formGridDiv">
    <table id="flow_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th>序号</th>
        <th>处理流程</th>
        <th>处理人</th>
        <th>处理状态</th>
        <th>处理意见</th>
        <th>创建时间</th>
        <th>处理时间</th>
      </tr>
      <tbody>
      <c:forEach items="${toDoList }" var="log">
	      <tr>
	        <td>${log.list_id }</td>
	        <td>
	        	<c:if test="${log.flow_type=='dispatch' }">
	        		分派
	        	</c:if>
	        	<c:if test="${log.flow_type!='dispatch' }">
	        		${log.flow_name }
	        	</c:if>
	        <td>
	        	${log.oper_name }
	        </td>
	        <td>${log.status }</td>
	        <td>${log.hint }</td>
	        <td><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${log.create_time}"></html:dateformat></td>
	        <td><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${log.flow_pass_time}"></html:dateformat></td>
	      </tr>
      </c:forEach>
      </tbody>
    </table>
</div>    
</div>
