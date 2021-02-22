<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <div class="btnWarp">
 </div> -->
<form  method="post" id="order_cancel_form" > 
<div class="formWarp">
<input type="hidden" name="order_id" value="${order_id }" />
	<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="4000" class="closeArrow"></a>取消订单
  <div class="dobtnDiv"></div></div>
    <div id="order_tag_4000" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
            <th style="width: 30%;">备注:</th>
            <td style="width: 70%;">
            	<textarea value="" autocomplete="on"  required="true" rows="10" cols="40" name="ordComments.comments"></textarea>
            </td>
          </tr>
          <tr>
            <th colspan="2" style="text-align: center;">
            	<a href="javascript:void(0);" id="order_cancel_btn_submit" class="dobtn" style="margin-right:5px;">取消订单</a>
            </th>
          </tr>
        </table>
    </div>
</div> 
</form>