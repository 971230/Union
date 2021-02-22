<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("js/goods_auditYes_dialog.js");
</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<div class="division">
<form  class="validate" method="post" action="" id='goods_auditYes_form' validate="true">
<table width="100%">
  <tbody>
   <tr>
	    <th>商品编号：</th><input type="hidden" name="goods_id" value="${goods.goods_id}" />
	    <td>${goods.sn}</td>
	  </tr>
	  <tr>
	    <th>商品名称：</th><input type="hidden" name="goods_name" value="${goods.name }" />
	    <td>${goods.name}</td>
	  </tr>
	  
	  <!--  <tr>
	    <th>上架时间：</th><input type="hidden" name="goodsUsage.create_date" value="${goodsUsage.create_date}" />
	    <td><html:dateformat pattern="yyyy-MM-dd" d_time="${goodsUsage.create_date}"></html:dateformat></td>
	  </tr> -->
	  <tr>
	    <th>阀值：</th><input type="hidden" value="${goodsUsage.usageid}" name="goodsUsage.usageid" />
	    <td><input type="text" class="ipttxt"  name="goodsUsage.baseline_num" dataType="int" required="true" value="${goodsUsage.baseline_num}" /></td>
	  </tr>
	  

    </tbody>
</table>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value=" 修 改 " class="submitBtn" name='submitBtn'/>
           <input id="cancleBtn" type="button"  value=" 取 消 " class="submitBtn"/>
	   </td>
	</tr>
 </table>
</div>
</form>
</div>
