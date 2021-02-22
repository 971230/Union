<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<div >
<form  action="goods!fengXingDongMapping.do"  id="searchProductsListForm" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	   <tbody>
	    	    <tr> 
	    	    	<th>配置类型：</th>
					<td>
						<select class="ipttxt"  style="width:100px"  name="stype" >
							<option value="">--请选择--</option>
							<option value="DIC_BLD_GOODS" <c:if test="${params['stype']=='DIC_BLD_GOODS' }">selected="true"</c:if>>京东便利店</option>
							<option value="DIC_FXD_GOODS" <c:if test="${params['stype']=='DIC_FXD_GOODS' }">selected="true"</c:if>>总部蜂行动</option>
						</select>
						<!-- <input type="text" class="ipttxt" style="width: 150px" id="sku" name="stype" value="${stype }" /> -->
					</td>
	    	      	<th>总部商品编码：</th> 
	    	      	<td><input type="text" class="ipttxt"  style="width: 150px" id="name" name="pkey" value="${params['pkey'] }" /> </td>
				 	<th style="width:120px;">订单中心商品编码：</th>
				  	<td><input type="text" class="ipttxt"  style="width: 150px" name="pname" value="${params['pname'] }" id="" /></td>
				 </tr>
				 <tr>
				   <th>产品编码：</th>
				   <td><input type="text" style="width: 150px" class="ipttxt" name="codea" value="${params['codea'] }" id="" /></td>
				  
				   <th>总部商品名称：</th>
				   <td><input id = "orgs" type="text" style="width: 150px" class="ipttxt" name="codeb" value="${params['codeb'] }" /></td>
				  
				   <th>备注：</th>
				   <td><input type="text" class="ipttxt"   name="comments" value="${params['comments'] }" id="" /></td> 
	  	      </tr>
	  	    </tbody>
	  	  </table>
    	</div>
    	<div class="comBtnDiv">
			<a href="goods!showAddInput.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
			<!-- <a href="javascript:;"  onclick="del();" style="margin-right:10px;" class="graybtn1"><span>放入回收站</span></a> -->
			<a href="javascript:void(0);"  onclick="formSubmit();" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchProductsListForm" action="" >
			<grid:header>
				<!-- 
				<grid:cell width="30px">
						<input type="checkbox" id="eckAll" onclick="javascript:ecc('eckAll','eckBox');" />
				</grid:cell>
				 -->
				<grid:cell width="140px">配置类型</grid:cell>
				<grid:cell width="170px">总部商品编码</grid:cell>
				<grid:cell>订单中心商品编码</grid:cell>
				<grid:cell width="100px">产品编码</grid:cell>
				<grid:cell>总部商品名称</grid:cell>
				<grid:cell sort="create_time">备注</grid:cell>
				<grid:cell width="90px">操作</grid:cell>
			</grid:header>
			
			<grid:body item="goods">
				<!-- 
				<grid:cell>
					<input type="checkbox"  name="eckBox"  value="${goods.goods_id }"  producto_id="${goods.product_id}" goodsAuthor="${goods.apply_userid}" nowUserid="${adminUser.userid }" nowUserFounder="${goods.pkey}"  onclick="cclick();" />
				</grid:cell>
				 -->
				<grid:cell>
					<c:if test="${goods.stype == 'DIC_FXD_GOODS' }">总部蜂行动</c:if>
					<c:if test="${goods.stype == 'DIC_BLD_GOODS' }">京东便利店</c:if>
				</grid:cell>
				<grid:cell>${goods.pkey }</grid:cell>
				<grid:cell>${goods.pname }</grid:cell>
				<grid:cell>${goods.codea }</grid:cell>
				<grid:cell>${goods.codeb }</grid:cell>
				<grid:cell>${goods.comments }</grid:cell>
				<grid:cell>
					<!-- <a href = "goods!fengXingDongMapping.do?pkey=${goods.pkey }&pname=${goods.pname }&operType=edit">编辑</a>| -->
					<a href = "goods!deletefengXingDongMapping.do?pkey=${goods.pkey }&pname=${goods.pname }&stype=${goods.stype}">删除</a>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<script type="text/javascript">
function formSubmit(){
	$("#searchProductsListForm").submit();
}
</script>
