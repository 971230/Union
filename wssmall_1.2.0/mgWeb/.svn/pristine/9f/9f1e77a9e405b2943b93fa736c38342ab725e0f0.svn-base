<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <div class="btnWarp">
 </div> -->
<form  method="post" id="order_visit_form_add" > 
<div class="formWarp">
<input type="hidden" name="visit.order_id" value="${order_id }" />
<input type="hidden" name="visit.todo_list_id" value="${todo_id }" />
	<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="4000" class="closeArrow"></a>订单回访
  <div class="dobtnDiv"></div></div>
    <div id="order_tag_4000" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
            <th>回访类型:</th>
            <td style="">
            	<select name="visit.type_id" style="width: 200px;">
            		<c:forEach items="${visitTypeList }" var="vt">
            			<option value="${vt.type_id }">${vt.name }</option>
            		</c:forEach>
            	</select>
            </td>
            <th>回访方法:</th>
            <td>
            	<select name="visit.return_method" style="width: 200px;">
            		<c:forEach items="${visitMethodList }" var="vt">
            			<option value="${vt.type_id }">${vt.name }</option>
            		</c:forEach>
            	</select>
            </td>
          </tr>
          <tr>
            <th>回访内容:</th>
            <td colspan="3">
            	<textarea style="width: 90%;height: 200px;" name="visit.content"></textarea>
            </td>
          </tr>
          <tr>
            <th colspan="5" style="text-align: center;">
            	<a href="javascript:void(0);" id="order_visit_submit_btn" class="dobtn" style="margin-right:5px;">提交</a>
            </th>
          </tr>
        </table>
    </div>
</div> 
</form>