<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/shop/admin/cms/js/cms_obj.js"></script>
<style>
	.treeList li.curr {
		background: #def1ff;
	}
	.clk{
		cursor: pointer;
	}
</style>
<div id='left_panel' style='float:left;width:20%;'>
	<input type="hidden" name="res_type" value="${res_type}" /> 
	<input type="hidden" name="res_value" value="${res_value}" />
	<input type="hidden" name="res_id" value="${res_id}" />

	<ul class="treeList">
		<li class="last curr clk" name="goods"
			url="${pageContext.request.contextPath}/shop/admin/cmsObj!getGoods.do?ajax=yes"><a><span></span>商品</a></li>
		<li class="last clk" name="adv_0"
			url="${pageContext.request.contextPath}/shop/admin/cmsObj!getAdv.do?ajax=yes"><a><span></span>素材</a></li>
<!-- 		<li class="last clk" name="adv_1" -->
<!-- 			url="${pageContext.request.contextPath}/shop/admin/cmsObj!getAdv.do?ajax=yes&adv.atype=99"><a><span></span>视频</a></li> -->
		<li class="last clk" name="notice"
			url="${pageContext.request.contextPath}/shop/admin/cmsObj!getNotice.do?ajax=yes"><a><span></span>公告</a></li>
	</ul>
</div>

<div id='right_panel' style='float:right;width:79%;'></div>
<div class="submitlist" align="center"
	style="padding-top:220px;display:none;">
	<table>
		<tr>
			<td><input type="button" id="confirm_btn" value=" 确定 "
				class="submitBtn" /></td>
		</tr>
	</table>
</div>
