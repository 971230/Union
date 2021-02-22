<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="btnWarp">
 </div>
 
<div class="formWarp" style="border-bottom: none;">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="500" class="closeArrow"></a>订单日志<div class="dobtnDiv"></div></div>
<div id="order_tag_500" class="formGridDiv">
    <table id="goods_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th>序号</th>
        <th>时间</th>
        <th>操作人</th>
        <th>操作内容</th>
      </tr>
      <tbody>
      <c:forEach items="${logList }" var="log">
	      <tr>
	        <td><a href="javascript:void(0);">${log.log_id }</a></td>
	        <td><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${log.op_time}"></html:dateformat></td>
	        <td>
	        	${log.op_name }
	        </td>
	        <td>${log.message }</td>
	      </tr>
      </c:forEach>
      </tbody>
    </table>
</div>    
</div>
