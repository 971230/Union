<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/shop/admin/setting/checktree.css" />

<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="activities/js/activity_publish_ecs.js">

</script>
<div style="margin-left:10px" id="publish_div">
    <input type="hidden" name="dialog" value="${dialog }"/>
	<div id="menubox">
	</div>
	<input type="hidden" value="${goodsid }" name="activeIds" id="resp_numberid">
	<div style="text-align:center;">
	  <input name="btn" id="confirm_publish" type="button"  value="确定"  class="graybtn1" style="cursor:pointer;" />
	</div>
</div>
