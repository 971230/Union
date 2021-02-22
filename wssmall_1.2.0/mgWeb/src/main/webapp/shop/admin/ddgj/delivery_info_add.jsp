<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <div class="btnWarp">
 </div> -->
<form  method="post" id="order_flow_form_add" > 
<div class="formWarp">
<input type="hidden" id="delivery_id" name="flow.delivery_id" value="${delivery_id }" />
	<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="4000" class="closeArrow"></a>添加物流信息
  <div class="dobtnDiv"></div></div>
    <div id="order_tag_4000" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
            <th style="width: 45%;">物流公司名称:</th>
            <td style="width: 55%;"><input id="flowloginame" name="flow.logi_name" type="text" class="formIpt" value="" /></td>
          </tr>
          <tr >
            <th style="width: 45%;">物流描述:</th>
            <td style="width: 55%;"><input id="flowdescription" name="flow.description" type="text" class="formIpt" value="" /></td>
          </tr>
          <tr>
            <th colspan="2" style="text-align: center;">
            	<a href="javascript:void(0);" id="delivery_info_submit_btn" class="dobtn" style="margin-right:5px;">添加</a>
            </th>
          </tr>
        </table>
    </div>
</div> 
</form>