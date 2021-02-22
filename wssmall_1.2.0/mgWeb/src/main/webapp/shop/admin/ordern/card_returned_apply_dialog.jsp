<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("js/card_returned_apply.js");
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<div class="division">
<form  class="validate" method="post" action="" id='goods_returned_apply_form' validate="true">
<table width="100%">
  <tbody>
	  <tr>
	    <th>选择充值卡总面额：</th>
	    <td><span id='all_card_money'>0</span>（元）</td>
	  
	  </tr>	  
      <tr>
	    <th>退货说明：</th>
	   	 <td colspan="3"><textarea value="" autocomplete="on"  required="true" rows="4" cols="36" name="goodsApply.apply_desc" id="goodsApply_apply_desc"></textarea></td>
	  </tr>

    </tbody>
</table>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="提交申请" class="submitBtn" name='submitBtn'/>
           <input name="reset" type="reset"  value=" 重  置 " class="submitBtn"/>
	   </td>
	</tr>
 </table>
</div>
</form>
</div>
