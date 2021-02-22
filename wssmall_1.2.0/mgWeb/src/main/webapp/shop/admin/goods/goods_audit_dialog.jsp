<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("js/goods_audit_dialog.js");
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<div class="division">
<form  class="validate" method="post" action="" id='goods_audit_form' validate="true">
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
<table width="100%">
  <tbody>
	  <tr>
	    <th>商品名称：</th><input type="hidden" name="goodsname" value="${goods.name }" />
	    <td>${goods.name}<input type="hidden" name="goodsArea.goods_id" value="${goods.goods_id }" /></td>
	  
	  </tr>
      <tr>
	    <th>审核描述：</th><input type="hidden" name="goodsArea.lan_id" value="${goodsArea.lan_id }" />
	   	 <td colspan="3"><textarea value="" autocomplete="on"  required="true" rows="10" cols="40" name="goodsArea.comments"></textarea></td>
	  </tr>
	  <tr>
		<th>处理：</th>
			
		<td colspan="3" required="true" dataType="int">
			<select  class="ipttxt"  name='goodsArea.state' attr='deal_state' autocomplete="on">
				<option value=''>
					请选择
				</option>
				<option value='1'>
					同意发布
				</option>
				<option value='2'>
					不同意发布
				</option>
			</select>
		</td>
	  </tr>
    </tbody>
</table>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="提交" class="submitBtn" name='submitBtn'/>
           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
	   </td>
	</tr>
 </table>
</div>
</form>
</div>
