<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<table style="width: 100%;border: none;">
<tr>
  <td rowspan="${goods_size }" class="graybg" style="width: 140px;border-left: none;border-top: none;border-right: none;">商品列表</td>
  <td class="graybg" colspan="2" style="border-top: none;border-right: none;">商品名称</td>
  <td class="graybg" style="width: 120px;border-top: none;border-right: none;">发货数量</td>
</tr>
<c:forEach items="${deliveryItemList }" var="item">
	<tr>
	  <td colspan="2" style="text-align: center;border-top: none;border-right: none;border-right: none;"><a href="javascript:void(0);">${item.name }</a></td>
	  <td style="text-align: center;border-top: none;border-right: none;">${item.num }</td>
	</tr>
</c:forEach>

<tr>
  <td class="graybg" style="border-left: none;border-bottom: none;border-top: none;border-right: none;">物流详细信息</td>
  <td colspan="2" style="border-top: none;border-right: none;border-bottom: none;">
  	<c:forEach items="${flowList }" var="item">
  		<p>${item.create_time }  ${item.description }</p>
  	</c:forEach>
  <td style="border-bottom: none;border-top: none;border-right: none;">
  	<c:if test="${logi_id==0 }">
  		<a href="javascript:void(0);" name="delivery_info_add_btn" delivery_id="${delivery_id }" class="dograybtn">添加</a>
  	</c:if>
  </td>
</tr>
</table>