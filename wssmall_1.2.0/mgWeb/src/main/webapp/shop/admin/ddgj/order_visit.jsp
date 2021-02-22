<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="btnWarp">
 </div>
 
<div class="formWarp" style="border-bottom: none;">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="500" class="closeArrow"></a>订单回访日志<div class="dobtnDiv"></div></div>
<div id="order_tag_500" class="formGridDiv">
    <table id="goods_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th style="text-align: center;">序号</th>
        <th style="text-align: center;">回访流程</th>
        <th style="text-align: center;">回访类型</th>
        <th style="text-align: center;">回访方法</th>
        <th style="text-align: center;">回访人</th>
        <th style="text-align: center;">回访时间</th>
        <th style="text-align: center;">回访内容</th>
      </tr>
      <tbody>
      <c:forEach items="${visitList }" var="log">
	      <tr>
	        <td style="text-align: center;">${log.visit_id }</td>
	        <td style="text-align: center;">${log.flow_name }</td>
	        <td style="text-align: center;">${log.typename }</td>
	        <td style="text-align: center;">${log.method_name }</td>
	        <td style="text-align: center;">
	        	${log.user_name }
	        </td>
	        <td style="text-align: center;"><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${log.create_time}"></html:dateformat></td>
	        <td style="text-align: center;">${log.content }</td>
	      </tr>
      </c:forEach>
      </tbody>
    </table>
</div>    
</div>
