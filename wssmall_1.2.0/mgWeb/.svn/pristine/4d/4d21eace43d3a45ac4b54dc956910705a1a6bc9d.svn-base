<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
  <script type="text/javascript">
loadScript("js/goods_audit_yes.js");
</script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div ><form action="goods!list_apply_yes.do" id="searchApplyListYesForm" method="post">
		<div class="searchformDiv">
	    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	    <tbody><tr>
	    	      <th>商品名称:</th>
	    	      <td><input type="text" class="ipttxt"  style="width: 90px" name="name" value="${name}" class="searchipt" /></td>
	    	      <th>商品编号:</th>
	    	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 90px" name="sn" value="${sn }" /> </td>
	    	      <th>商品类型：</th>
	    	      <td>
	    	      	<html:selectdict curr_val="${typeCode}" appen_options="<option value=''>--全部--</option>" id="goodsTypeSelected" name="typeCode" attr_code="dc_card_type" style="width:153px;"></html:selectdict>
	    	      </td>
	    	      <th>开始时间：</th>
					<td>
					<input size="15" type="text"  name="startTime" id="startTime"
								value='${startTime}'
								readonly="readonly"
								maxlength="60" class="dateinput" dataType="date" class="ipttxt"/> 
					结束时间：
					<input size="15" type="text"  name="endTime" id="endTime"
								value='${endTime}'
								readonly="readonly"
								maxlength="60" class="dateinput" dataType="date" class="ipttxt"/> 
					</td>
	    	      <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
	  	      </tr>
	  	    </tbody>
	  	    </table>
    	</div>
	</form>
	<br />
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchApplyListYesForm" action="goods!list_apply_yes.do" >
			<grid:header>
				<!--<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>-->
				<grid:cell width="250px">商品名称</grid:cell>
				<grid:cell width="180px">商品编号</grid:cell>
				
				<grid:cell width="100px">商品类型</grid:cell>
				<grid:cell sort="create_date">商品申请时间</grid:cell>
				<grid:cell sort="stock_num">库存</grid:cell><!--  -->
				<grid:cell sort="baseline_num">阀值</grid:cell>
				<grid:cell width="50px">状态</grid:cell>
				<grid:cell width="80px">操作</grid:cell>
			</grid:header>
			<grid:body item="goods">
				<grid:cell>&nbsp;
<%--       			 <a href="../wssdetails-${goods.goods_id }.html" target="_blank"></a>
						<span <c:if test="${goods.store<=5}">style="color:red"</c:if>></span> --%>
							<a >${goods.goods_name}</a>
				</grid:cell>
				<grid:cell>&nbsp;${goods.sn} </grid:cell>
				<grid:cell>&nbsp;
		        ${goods.type_name}
		         </grid:cell>
				<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_date}"></html:dateformat>
				</grid:cell>
				<grid:cell>&nbsp;
		        ${goods.goods_stock}
		         </grid:cell>
		         <grid:cell>&nbsp;&nbsp;
		        ${goods.baseline_num}
		         </grid:cell>
				<grid:cell>
					<span><c:if test="${goods.state=='0'}">有效</c:if><c:if test="${goods.state=='1'}">冻结</c:if></span>
				</grid:cell>
				<grid:cell>
				<a href="javascript:;" class="auditYes" usageid="${goods.usageid }" goodsid="${goods.goods_id}">修改阀值</a>
<%--		        	<input type="button" goodsid="${goods.goods_id}" class="auditYes" value="修改阀值" />--%>
		         </grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	
	<div id="auditYes_dialog"></div>
	<div style="clear: both; padding-top: 5px"></div>
</div>
