<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>
<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/warehouse/js/assign_product_win.js"></script>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<div id="assign_pro_win" class="input" style="">
   <form id="assign_form" action="">
	<table class="form-table" style="width: 100%; float: left"
		id='base_table'>
		<input type="hidden" name="apply.product_id"  />
		<input type="hidden" name="apply.house_id"  />
		<input type="hidden" name="apply.goods_id"  />
		<input type="hidden" name="apply.sku"  />
		
		<tr>
			<td nowrap="nowrap">
			    <label><span class="red">*</span>物理仓：</label>
			</td>
			<td>
				<input type="text" name="apply.house_name" class="top_up_ipt" readonly="readonly" />
			</td>
			<td nowrap="nowrap">
			    <label id="win_pro_or_goods_name"></label>
			</td>
			<td>
				<input type="text" id="" name="apply.name" class="top_up_ipt" readonly="readonly"/>
			</td>
			<td nowrap="nowrap">
			     <label><span class="red">*</span>可分配数量：</label>
			</td>
			<td>
				<input type="text" id="" name="apply.no_apply_num" class="top_up_ipt" readonly="readonly" />
			</td>
		</tr>
		<tr>
			<td nowrap="nowrap">
			    <label><span class="red">*</span>虚拟仓：</label>
			</td>
			<td>
			    <input type="hidden" id="v_house_id" name="apply.virtual_house_id" class="top_up_ipt" />
				<input type="text" id="v_house_name" name="apply.virtual_house_name" class="top_up_ipt" readonly="readonly" />
				<input type="button" name="select_virtual" value="选择">
			</td>
			<td nowrap="nowrap">
			    <label><span class="red">*</span>虚拟仓类型：</label>
			</td>
			<td>
				<select style="width:150px;"  id="v_attr_inline_type" name="apply.is_share" disabled="disabled">
				   <option value="1" >共享仓</option>
				   <option value="0" >独享仓</option>
				</select>
			</td>
			<td nowrap="nowrap">
			    <label><span class="red">*</span>商城：</label>
			</td>
			<td>
			    <input type="hidden" id="org_id_str" name="apply.org_id" class="top_up_ipt" />
				<input type="text" id="org_name_str"  class="top_up_ipt" readonly="readonly"  />
			</td>
		</tr> 
		<tr >
			<td  nowrap="nowrap">
			    <label><span class="red">*</span>分配数量：</label>
			</td>
			<td>
				<input type="text" name="apply.inventory_num" class="top_up_ipt" onkeyup="value=value.replace(/[^(\d)]/g,'')" style= "ime-mode:disabled "/>
			</td>
		</tr>
		<tr >
			<td colspan="6" style="text-align:center;">
                <input  type="button"  value="库存分配 " class="greenbtnbig" id="goodsInventoryApplyBtn"/>
			</td>
		</tr>
	</table>
  </form>
</div>

<script type="text/javascript">

</script>

